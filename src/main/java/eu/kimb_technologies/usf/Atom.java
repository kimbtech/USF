package eu.kimb_technologies.usf;

/**
 * This is the abstract class which defines all Types which can be saved by USF.
 * @author kimbtech
 */
public abstract class Atom {
	public abstract String toUSF();
	public abstract Atom loadUSF( String usf ) throws USFSyntaxException;
}
