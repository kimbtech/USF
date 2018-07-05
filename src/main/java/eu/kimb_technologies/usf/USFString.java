package eu.kimb_technologies.usf;

/**
 * A String to be exported as USF.
 * @author kimb
 */
public class USFString extends Atom {
	
	/**
	 * Storage Attributes
	 */
	private String val = "";
	
	/**
	 * Create a new String
	 * @param value of the String
	 */
	public USFString(String value){
		this.setValue(value);
	}
	
	/**
	 * Create empty String
	 */
	public USFString(){
	}
	
	/**
	 * Get the value
	 * @return the value
	 */
	public String getValue(){
		return this.val;
	}
	
	/**
	 * Set the value.
	 * @param val the new value
	 */
	public void setValue( String val ){
		this.val = val;
	}

	/**
	 * make a valid USF String
	 * @return the usf String
	 */
	@Override
	public String toUSF() {
		return USFString.escapeString(this.val);
	}

	/**
	 * Read a valid USFString
	 * @param usf the usf String
	 */
	public Atom loadUSF(String usf) throws USFSyntaxException {
		if( usf.charAt(0) != '"' || usf.charAt(usf.length()-1) != '"') {
			throw new USFSyntaxException("USF String have to start and end with \"!");
		}
		try{
			this.val = USFString.unescapeString(usf);
			return this;
		}
		catch( Exception e){
			throw new USFSyntaxException("Can not parse USF String.");
		}
	}
	
	/**
	 * Method used to escape a String in USF 
	 * @param string The string to escape
	 * @return the escaped String
	 */
	public static String escapeString(String string){
		//empty String
		if (string == null || string.length() == 0) {
			return "\"\"";
		}
		
		//init vars, to walk trought string
		char bst = 0;
		int index;
		//	plus 4 because we will have to add two " and most times some more
		StringBuilder builder = new StringBuilder(string.length() + 4);

		//start with "
		builder.append('"');
		for (index = 0; index < string.length(); index++) {
			//escape each character
			bst = string.charAt(index);
			switch (bst) {
				case '\\':
				case '"':
					//just escape with \
					builder.append('\\');
					builder.append(bst);
					break;
				//escape all types of breaks, we don't want to have breaks in our usf files
				case '\t':
					builder.append("\\t");
					break;
				case '\n':
					builder.append("\\n");
					break;
				case '\r':
					builder.append("\\r");
					break;
				default:
					//just add normal character
					builder.append(bst);
			}
		}
		//add closing "
		builder.append('"');
		return builder.toString();
	}
	
	/**
	 * Unescape a escaped String (via escapeString)
	 * @param string the escaped String
	 * @return the unesacaped String
	 */
	public static String unescapeString(String string){
		//detect empty strings
		if (string == "\"\"") {
			return "";
		}

		//init vars for walking trought string
		char bst = 0, bstNext = 0;
		int index;
		StringBuilder builder = new StringBuilder(string.length() - 2);

		//start at index 1, we don't care about starting "
		//	also end one before end, ending "
		for (index = 1; index < string.length() - 1; index++) {
			//get the current char
			bst = string.charAt(index);
			//is this an escape sign?
			if( bst == '\\' ){  
				//get the next, the escaped char
				bstNext = string.charAt(index+1);
				switch (bstNext){
					case 't':
					//add tab
					builder.append('\t');
					break;
					case 'n':
					//add break
					builder.append('\n');
					break;
					case 'r':
					//add break
					builder.append('\r');
					break;
					default:
					//add the escaped char
					builder.append(bstNext);
				}
				//do a second ++, because we already added the next char
				index++;
			}
			else{
				//add this char
				builder.append(bst);
			}
		}
		//done
		return builder.toString();
	}
	
	/**
	 * Checks if String equals to other String
	 */
	@Override
	public boolean equals(Object a){
		// is an String?
		if( a instanceof USFString){
			//check name an val
			return this.getValue().equals(((USFString) a).getValue());
		}
		else{
			//no string => not equal
			return false;
		}
	}
}
