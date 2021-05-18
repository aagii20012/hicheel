package lab_3;

/**
 * Exception that should be thrown if an invalid grade is passed in.
 *
 */
public class InvalidGradeException extends Exception {
	
	public InvalidGradeException(String string) {
		  super(string);
	}
}