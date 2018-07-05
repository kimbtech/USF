package eu.kimb_technologies.usf;

/**
 * Parse error for USF
 * @author kimbtech
 */
public class USFSyntaxException extends Exception {
	private static final long serialVersionUID = 2658840419806149533L;

	/**
	 * Generates a new Error
	 * @param msg Error message
	 */
	public USFSyntaxException(String msg){
		super(msg);
	}
}
