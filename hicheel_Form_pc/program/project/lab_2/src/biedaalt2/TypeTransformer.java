package biedaalt2;

import java.util.*;


public class TypeTransformer {

    public static Program T (Program p, TypeMap tm) {
        Block body = (Block)T(p.body, tm);
        return new Program(p.decpart, body);
    } 

    public static Expression T (Expression e, TypeMap tm) {
        if (e instanceof Value) 
            return e;
        if (e instanceof Variable) 
            return e;
        if (e instanceof Binary) {
            Binary b = (Binary)e; 
            Type typ1 = StaticTypeCheck.typeOf(b.term1, tm);
            Type typ2 = StaticTypeCheck.typeOf(b.term2, tm);
            Expression t1 = T (b.term1, tm);
            Expression t2 = T (b.term2, tm);
            if(b.op.BooleanOp()) return new Binary(b.op, t1,t2);
            if (typ1 == Type.INT) 
                return new Binary(b.op.intMap(b.op.val), t1,t2);
            else if (typ1 == Type.FLOAT) 
                return new Binary(b.op.floatMap(b.op.val), t1,t2);
            else if (typ1 == Type.CHAR) 
                return new Binary(b.op.charMap(b.op.val), t1,t2);
            else if (typ1 == Type.BOOL) 
                return new Binary(b.op.boolMap(b.op.val), t1,t2);
            throw new IllegalArgumentException("should never reach here");
        }
        if (e instanceof Unary) {
        	Unary u = (Unary)e; 
            Type typ1 = StaticTypeCheck.typeOf(u.term, tm);
            Expression t1 = T (u.term, tm);
            if (typ1 == Type.INT) 
                return new Unary(u.op.intMap(u.op.val), t1);
            else if (typ1 == Type.FLOAT) 
                return new Unary(u.op.floatMap(u.op.val), t1);
            else if (typ1 == Type.CHAR) 
                return new Unary(u.op.charMap(u.op.val), t1);
            else
            throw new IllegalArgumentException("should never reach here");
        }
        // student exercise
        throw new IllegalArgumentException("should never reach here");
    }

    public static Statement T (Statement s, TypeMap tm) {
        if (s instanceof Skip) return s;
        if (s instanceof Assignment) {
            Assignment a = (Assignment)s;
            Variable target = a.target;
            Expression src = T (a.source, tm);
            Type ttype = (Type)tm.get(a.target);
            Type srctype = StaticTypeCheck.typeOf(a.source, tm);
            if (ttype == Type.FLOAT) {
                if (srctype == Type.INT) {
                    src = new Unary(new Operator(Operator.I2F), src);
                    srctype = Type.FLOAT;
                }
            }
             if (ttype == Type.INT) {
                if (srctype == Type.FLOAT) {
                    src = new Unary(new Operator(Operator.I2F), src);
                    srctype = Type.INT;
                }
            } 
            //BD2 nuhuh heseg
            StaticTypeCheck.check( ttype == srctype,
                      "bug in assignment to " + target);
            return new Assignment(target, src);
        } 
        if (s instanceof Conditional) {
            Conditional c = (Conditional)s;
            Expression test = T (c.test, tm);
            Statement tbr = T (c.thenbranch, tm);
            Statement ebr = T (c.elsebranch, tm);
            return new Conditional(test,  tbr, ebr);
        }
        if (s instanceof Loop) {
            Loop l = (Loop)s;
            Expression test = T (l.test, tm);
            Statement body = T (l.body, tm);
            return new Loop(test, body);
        }
        if (s instanceof Block) {
            Block b = (Block)s;
            Block out = new Block();
            for (Statement stmt : b.members)
                out.members.add(T(stmt, tm));
            return out;
        }
        throw new IllegalArgumentException("should never reach here");
    }
    

    public static void main(String args[]) {
        Parser parser  = new Parser(new Lexer("C:\\Users\\R5 3600\\Desktop\\hicheel\\hicheel_Form_pc\\program\\project\\lab_2\\src\\biedaalt2\\factorial.cpp"));
        Program prog = parser.program();
        prog.display();           
        System.out.println("\nBegin type checking...");
        System.out.println("Type map:");
        TypeMap map = StaticTypeCheck.typing(prog.decpart);
        map.display();    
        StaticTypeCheck.V(prog);
        Program out = T(prog, map);
        System.out.println("Output AST");
         out.display();    
    } //main

    } // class TypeTransformer

    
