package eu.kimb_technologies.usf;

/**
 * Boolean for USF
 * @author kimbtech
 */
public class Bool extends Atom implements Comparable<Boolean>{
	/**
	 * Storage Attributes
	 */
	private boolean val;
	
	/**
	 * Create a new Boolean
	 * @param value of the Boolean
	 */
	public Bool(boolean value){
		this.setValue(value);
	}
	
	/**
	 * Create empty boolean
	 */
	public Bool(){
	}
	
	/**
	 * Get the value
	 * @return the value
	 */
	public boolean getValue(){
		return this.val;
	}
	
	/**
	 * Set the value.
	 * @param val the new value
	 */
	public void setValue( boolean val ){
		this.val = val;
	}

	/**
	 * make a valid USF String
	 * @return the usf String
	 */
	@Override
	public String toUSF() {
		return this.val ? "true" : "false";
	}
	
	@Override
	public String toHumFrieUSF(int indent) {
		return USFString.stringRepeat(indent, "\t") + this.toUSF();
	}

	/**
	 * Read a valid USFString
	 * @param usf the usf String
	 */
	@Override
	public Atom loadUSF(String usf) throws USFSyntaxException {
		if( !usf.equals( "true" ) && !usf.equals( "false" ) ) {
			throw new USFSyntaxException("Can not parse USF Boolean.");
		}
		this.val = usf.equals("true");
		return this;
	}

	public int compareTo(Boolean o) {
		return ((Boolean) this.getValue()).compareTo(o);
	}
	
	//Object

	@Override
	public boolean equals(Object a){
		// is an Pair?
		if( a instanceof Bool){
			//check val
			return ((Boolean) this.getValue()).equals(((Bool) a).getValue());
		}
		else{
			//no bool => not equal
			return false;
		}
	}
	
	@Override
	public String toString(){
		return this.toUSF();
	}
}
