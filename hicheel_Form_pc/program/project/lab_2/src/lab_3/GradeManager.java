package lab_3;

import java.util.*;

/** 
 * A GradeManager will create a command-line prompt that will let someone add grades
 * and display grades in histogram format.
 * 
 */

public class GradeManager {
	
	// Keeps track of the number of each grade this has
	private HashMap<Integer,LetterGrade> allGrades;
	public int size=1;

	/**
	 * Creates a new GradeManager.
	 */
	public GradeManager() {
		this.allGrades = new HashMap<Integer,LetterGrade>();
	}
		
	/**
	 * Adds grade to this GradeManager.
	 * @param grade - grade to add to this grad manager
	 */
	public void addGrade(String grade) throws InvalidGradeException {
				
					if (grade.equals("a")) {
						allGrades.put(size,LetterGrade.A);
						size++;
					} else if (grade.equals("b")) {
						allGrades.put(size,LetterGrade.B);
						size++;
					} else if (grade.equals("c")) {
						allGrades.put(size,LetterGrade.C);
						size++;
					} 	else if (grade.equals("d")) {
						allGrades.put(size,LetterGrade.D);
						size++;
					} 	else if (grade.equals("f")) {
						allGrades.put(size,LetterGrade.F);
						size++;
					}else throw new InvalidGradeException(
					          "Incorrect grade : " + grade);
					 
	}

	/**
	 * Prints out a histogram of the grades to the console.
	 *
	 */
	public void printHistogram() {
		// TODO: YOUR CODE HERE
		String sb =getHistString();
		System.out.println(sb);
		//throw new RuntimeException("GradeManger.printHistogram() not yet implemented!");
	}
	
	/**
	 * Returns a string representation of the histogram of the grades.
	 * @return a string representation of the histogram of the grades.
	 */
	public String getHistString() {
		StringBuffer sb = new StringBuffer();
		allGrades.forEach((key, value) -> {
			sb.append("\n"+value);
		 });
		//for (int i = 0; i < size; i++) {
		//	sb.append("*");
		//}
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * Simple loop that accepts 3 commands from System.in:
	 *    add <some grade> : for example, "add a" or "add b"
	 *                       adds the given grade to the GradeManager
	 *    print            : prints out all the grades in this GradeManager
	 *                       in a histogram format
	 *    exit             : exits the program
	 * @param args
	 * @throws InvalidGradeException 
	 */
	public static void main(String[]  args) throws InvalidGradeException {
		GradeManager gm = new GradeManager();
		
		//BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
		Scanner scan=new Scanner(System.in);
		System.out.println("Starting the grade manager");
		
		try
		{
			while (true) {
				String input = scan.nextLine();
				if (input.startsWith("add")) {
					String[] str=input.split(" ");
					gm.addGrade(str[1]);
				} else if (input.equals("print")) {
					gm.printHistogram();
				}  else if (input.equals("exit")) {
					break;
			}
		
		}
		}catch(InvalidGradeException e) {
			throw new InvalidGradeException(
			          "You entered Incorrect grade \n"+e);
		}
	}

	private static int parseInteger(String nextLine) {
		// TODO Auto-generated method stub
		return 0;
	}

}