package eu.kimb_technologies.usf;

/**
 * A real to be saved in USF.
 * @author kimbtech
 */
public class Real extends Atom implements Comparable<Double> {
	/**
	 * Storage Attributes
	 */
	private double val = 0.0;
	
	/**
	 * Create a new Real, internally Double
	 * @param value of the Real
	 */
	public Real(double value) throws USFSyntaxException{
		this.setValue(value);
	}

	/**
	 * Create Real 0.0
	 */
	public Real(){
	}
	
	/**
	 * Get the value
	 * @return the value
	 */
	public double getValue(){
		return this.val;
	}
	
	/**
	 * Set the value.
	 * @param val the new value, decimal point represenation 1.0, 1.25, 11.223
	 */
	public void setValue( double val ) {
		this.val = val;
	}

	/**
	 * make a valid USF String
	 * @return the usf String
	 */
	@Override
	public String toUSF() {
		return "=" + Double.toString( this.val ) + "=";
	}

	/**
	 * Read a valid USFString
	 * @param usf the usf String
	 */
	@Override
	public Atom loadUSF(String usf) throws USFSyntaxException {
		try{
			usf = usf.substring(1, usf.length()-1 );
			this.val = Double.parseDouble( usf );
			return this;
		}
		catch( Exception e){
			throw new USFSyntaxException("Can not parse USF Real.");
		}
	}
	
	//Comparable
	
	@Override
	public int compareTo(Double o) {
		return ((Double) this.val).compareTo( o );
	}
	
	//Object
	
	@Override
	public boolean equals(Object a){
		// is an Integer?
		if( a instanceof Real){
			//check val
			return this.getValue() == ((Real) a).getValue();
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
