package eu.kimb_technologies.usf;

/**
 * This is the abstract class which defines all Types which can be saved by USF.
 * @author kimbtech
 */
public abstract class Atom {
	
	/**
	 * Exports the current data in this Atom to a parseable String.
	 * @return the parseable String
	 */
	public abstract String toUSF();
	
	/**
	 * Exports the current data in this Atom to a parseable, human friendly String.
	 * @param the indention of the data
	 * @return the parseable String (human friendly parseable)
	 */
	public abstract String toHumFrieUSF( int indent );
	
	/**
	 * Imports data from an parseable Atom String.
	 * @param usf the String to parse
	 * @return the Atom data
	 * @throws USFSyntaxException thrown on Synatay errors
	 */
	public abstract Atom loadUSF( String usf ) throws USFSyntaxException;
	
	@Override
	public abstract boolean equals( Object o );
	
	@Override
	public String toString() {
		//human friendly print
		return this.toUSF();
	}
}
