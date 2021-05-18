package lab_6;

import java.util.*;

public class Parser {
	// Recursive descent parser that inputs a C++Lite program and
	// generates its abstract syntax. Each method corresponds to
	// a concrete syntax grammar rule, which appears as a comment
	// at the beginning of the method.

	Token token; // current token from the input stream
	Lexer lexer;

	public Parser(Lexer ts) { // Open the C++Lite source program
		lexer = ts; // as a token stream, and
		token = lexer.next(); // retrieve its first Token
	}

	private String match(TokenType t) {
		String value = token.value();
		if (token.type().equals(t))
			token = lexer.next();
		else
			error(t);
		return value;
	}

	private void error(TokenType tok) {
		System.err.println("Syntax error: expecting: " + tok + "; saw: " + token);
		System.exit(1);
	}

	private void error(String tok) {
		System.err.println("Syntax error: expecting: " + tok + "; saw: " + token);
		System.exit(1);
	}

	public Program program() {
		// Program --> void main ( ) '{' Declarations Statements '}'
		TokenType[] header = { TokenType.Int, TokenType.Main, TokenType.LeftParen, TokenType.RightParen };
		for (int i = 0; i < header.length; i++) // bypass "int main ( )"
			match(header[i]);
		match(TokenType.LeftBrace);
		Declarations decs = declarations();
		Block block = statements();
		match(TokenType.RightBrace);
		return new Program(decs, block);
	}

	private Declarations declarations() {
		// Declarations --> { Declaration }
		Declarations decs = new Declarations();
		while (this.isType() && token != Token.eofTok) {
			this.declaration(decs);
			token = lexer.next();
		}
		return decs;
	}

	private void declaration(Declarations ds) {
		// Declaration --> Type Identifier { , Identifier } ;
		Type t = this.type();
		token = lexer.next();
		boolean ident = false;
		while (token.type().equals(TokenType.Identifier)) {
			ident = true;
			Variable v = new Variable(match(token.type()));
			ds.add(new Declaration(v, t));
			if (token.type().equals(TokenType.Comma))
				token = lexer.next();
		}
		if (!ident)
			error("Identifier");
		if (!token.type().equals(TokenType.Semicolon))
			error("; ");
	}

	private Type type() {
		// Type --> int | bool | float | char
		Type t = null;
		switch (token.type()) {
		case Int:
			t = Type.INT;
			break;
		case Float:
			t = Type.FLOAT;
			break;
		case Char:
			t = Type.CHAR;
			break;
		case Bool:
			t = Type.BOOL;
			break;
		}
		return t;
	}

	private Statement statement() {
		// Statement --> ; | Block | Assignment | IfStatement | WhileStatement
		Statement s = new Skip();
		// student exercise
		switch (token.type()) {
		case Identifier:
			s = this.assignment();
			break;
		case If:
			s = this.ifStatement();
			break;
		case While:
			s = this.whileStatement();
			break;
		case LeftBrace:
			token = lexer.next();
			s = this.statements();
			token = lexer.next();
			break;
		}
		return s;
	}

	private Block statements() {
		// Block --> '{' Statements '}'
		Block b = new Block();
		while (!token.type().equals(TokenType.RightBrace) && token != Token.eofTok)
			b.members.add(statement());
		return b;
	}

	private Assignment assignment() {
		// Assignment --> Identifier = Expression ;
		Variable v = new Variable(match(token.type()));
		match(TokenType.Assign);
		Expression s = this.expression();
		match(TokenType.Semicolon);
		return new Assignment(v, s);
	}

	private Conditional ifStatement() {
		// IfStatement --> if ( Expression ) Statement [ else Statement ]
		token = lexer.next();
		match(TokenType.LeftParen);
		Expression t = expression();
		match(TokenType.RightParen);
		Statement s = this.statement();
		if (token.type().equals(TokenType.Else)) {
			token = lexer.next();
			return new Conditional(t, s, this.statement());
		}
		return new Conditional(t, s);
	}

	private Loop whileStatement() {
		// WhileStatement --> while ( Expression ) Statement
		token = lexer.next();
		match(TokenType.LeftParen);
		Expression t = expression();
		match(TokenType.RightParen);
		return new Loop(t, this.statement());
	}

	private Expression expression() {
		// Expression --> Conjunction { || Conjunction }
		Expression e = this.conjunction();

		return e;
	}

	private Expression conjunction() {
		// Conjunction --> Equality { && Equality }
		Expression e = equality();
		while (token.type().equals(TokenType.And) || token.type().equals(TokenType.Or)) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = equality();
			e = new Binary(op, e, term2);
		}
		return e;
	}

	private Expression equality() {
		// Equality --> Relation [ EquOp Relation ]
		Expression e = relation();
		while (this.isEqualityOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = relation();
			e = new Binary(op, e, term2);
		}
		return e;
	}

	private Expression relation() {
		// Relation --> Addition [RelOp Addition]
		Expression e = addition();
		while (this.isRelationalOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = addition();
			e = new Binary(op, e, term2);
		}
		return e;
	}

	private Expression addition() {
		// Addition --> Term { AddOp Term }
		Expression e = term();
		while (isAddOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = term();
			e = new Binary(op, e, term2);
		}
		return e;
	}

	private Expression term() {
		// Term --> Factor { MultiplyOp Factor }
		Expression e = factor();
		while (isMultiplyOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term2 = factor();
			e = new Binary(op, e, term2);
		}
		return e;
	}

	private Expression factor() {
		// Factor --> [ UnaryOp ] Primary
		if (isUnaryOp()) {
			Operator op = new Operator(match(token.type()));
			Expression term = primary();
			return new Unary(op, term);
		} else
			return primary();
	}

	private Expression primary() {
		// Primary --> Identifier | Literal | ( Expression )
		// | Type ( Expression )
		Expression e = null;
		if (token.type().equals(TokenType.Identifier)) {
			e = new Variable(match(TokenType.Identifier));
		} else if (isLiteral()) {
			e = literal();
		} else if (token.type().equals(TokenType.LeftParen)) {
			token = lexer.next();
			e = expression();
			match(TokenType.RightParen);
		} else if (isType()) {
			Operator op = new Operator(match(token.type()));
			match(TokenType.LeftParen);
			Expression term = expression();
			match(TokenType.RightParen);
			e = new Unary(op, term);
		} else
			error("Identifier | Literal | ( | Type");
		return e;
	}

	private Value literal() {
		Value v = null;
		switch (token.type()) {
		case IntLiteral:
			v = new IntValue(Integer.parseInt(match(token.type())));
			break;
		case FloatLiteral:
			v = new FloatValue(Float.parseFloat(match(token.type())));
			break;
		case CharLiteral:
			v = new CharValue(match(token.type()).charAt(0));
			break;
		}
		return v; // student exercise
	}

	private boolean isAddOp() {
		return token.type().equals(TokenType.Plus) || token.type().equals(TokenType.Minus);
	}

	private boolean isMultiplyOp() {
		return token.type().equals(TokenType.Multiply) || token.type().equals(TokenType.Divide);
	}

	private boolean isUnaryOp() {
		return token.type().equals(TokenType.Not) || token.type().equals(TokenType.Minus);
	}

	private boolean isEqualityOp() {
		return token.type().equals(TokenType.Equals) || token.type().equals(TokenType.NotEqual);
	}

	private boolean isRelationalOp() {
		return token.type().equals(TokenType.Less) || token.type().equals(TokenType.LessEqual)
				|| token.type().equals(TokenType.Greater) || token.type().equals(TokenType.GreaterEqual);
	}

	private boolean isType() {
		return token.type().equals(TokenType.Int) || token.type().equals(TokenType.Bool)
				|| token.type().equals(TokenType.Float) || token.type().equals(TokenType.Char);
	}

	private boolean isLiteral() {
		return token.type().equals(TokenType.IntLiteral) || isBooleanLiteral()
				|| token.type().equals(TokenType.FloatLiteral) || token.type().equals(TokenType.CharLiteral);
	}

	private boolean isBooleanLiteral() {
		return token.type().equals(TokenType.True) || token.type().equals(TokenType.False);
	}

	public static void main(String args[]) {
		System.out.println("Begin parsing... " + args[0] + "\n");
		Parser parser = new Parser(new Lexer(args[0]));
		Program prog = parser.program();
		prog.display(); // display abstract syntax tree
	} // main

} // Parser
