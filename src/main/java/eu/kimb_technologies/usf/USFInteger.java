package eu.kimb_technologies.usf;

/**
 * A Integer to be saved in USF.
 * @author kimbtech
 */
public class USFInteger extends Atom implements Comparable<Integer> {
	/**
	 * Storage Attributes
	 */
	private int val = 0;
	
	/**
	 * Create a new Integer
	 * @param value of the Integer
	 */
	public USFInteger(int value){
		this.setValue(value);
	}
	
	/**
	 * Create Integer 0
	 */
	public USFInteger(){
	}
	
	/**
	 * Get the value
	 * @return the value
	 */
	public int getValue(){
		return this.val;
	}
	
	/**
	 * Set the value.
	 * @param val the new value
	 */
	public void setValue( int val ){
		this.val = val;
	}

	/**
	 * make a valid USF String
	 * @return the usf String
	 */
	@Override
	public String toUSF() {
		return Integer.toString(this.val);
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
		try{
			this.val = Integer.parseInt(usf);
			return this;
		}
		catch( Exception e){
			throw new USFSyntaxException("Can not parse USF Integer.");
		}
	}
	
	//Comparable
	
	@Override
	public int compareTo(Integer o) {
		return ((Integer) this.getValue()).compareTo(o);
	}
	
	//Object
	
	@Override
	public boolean equals(Object a){
		// is an Integer?
		if( a instanceof USFInteger){
			//check val
			return this.getValue() == ((USFInteger) a).getValue();
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
