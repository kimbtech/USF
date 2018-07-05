package eu.kimb_technologies.usf;

/**
 * Saves a Pair of two Atoms, Name : Value
 * @author kimbtech
 */
public class USFPair extends Atom {
	
	/**
	 * Save name and value
	 */
	Atom name = null, val = null;
	
	/**
	 * Create empty pair
	 */
	public USFPair(){
	}
	
	/**
	 * Create Pair
	 * @param name the name
	 * @param val the value
	 */
	public USFPair(Atom name, Atom val){
		this.name = name;
		this.val = val;
	}
	
	/**
	 * Get the name of the Pair
	 * @return the name
	 */
	public Atom getName(){
		return this.name;
	}
	
	/**
	 * Get value of the Pais
	 * @return the value
	 */
	public Atom getValue(){	
		return this.val;
	}
	
	//USF
	
	/**
	 * Translates Pair to USF
	 */
	@Override
	public String toUSF(){
		if( this.name != null && this.val != null ){
			return "{" + this.name.toUSF() + ":" + this.val.toUSF() + "}";
		}
		else{
			return "{:}";
		}
	}

	/**
	 * Loads USF-String and returns Atom (Pair)
	 */
	@Override
	public Atom loadUSF(String usf) throws USFSyntaxException {
		if( usf.charAt(0) != '{' || usf.charAt(usf.length()-1) != '}') {
			throw new USFSyntaxException("USF Pair has to start and end with {} !");
		}
		try{
			//name : value
			
			//init vars, to walk trough string
			char bst = 0, bstBefore = 0;
			int index;
			// zero means not in any pairs or lists (you can be in multiple lists, pairs)
			int inPair = 0, inList = 0;
			//not in a String (you can only be in one String)
			boolean inString = false;
			//	two String Builder for both parts
			StringBuilder builderFirst = new StringBuilder( (int) (usf.length() * 0.25) ), builderSecond = new StringBuilder( (int) (usf.length() * 0.75) );
			//	start with first builder
			StringBuilder builder = builderFirst;
				
			//starting after { and ending before }
			for (index = 1; index < usf.length() - 1; index++) {
				bst = usf.charAt(index);
				bstBefore = usf.charAt(index-1);
					
				//count lists, we go inside
				if( bst == '[' && !inString) { inList++; }
				//count pairs, we go inside
				else if( bst == '{' && !inString) { inPair++; }
					
				//go in String and leave string
				if( bst == '"' && bstBefore != '\\') { inString = !inString; }
					
				//count lists we leave
				if( bst == ']' && !inString) { inList--; }
				//count pairs we leave
				else if( bst == '}' && !inString) { inPair--; }				
					
				//split at this char?
				//	must be a : and we dont want to be in any Strings, Lists, Pairs
				if( (bst == ':' && inPair == 0 && inList == 0 && !inString) ) {
					if( builder == builderSecond ) { throw new USFSyntaxException("Pair must constist of two parts!"); }
					//second builder
					builder = builderSecond;
				}
				else {
					//dont split here, add char
					builder.append( bst );
				}
			}
			if( inString || inPair != 0 || inList != 0 ) { throw new USFSyntaxException("String, Pair or List not ended!"); }
			if( builder == builderFirst ) { throw new USFSyntaxException("Pair must constist of two parts!"); }
			
			//to string
			String partA = builderFirst.toString(), partB = builderSecond.toString();
			
			if( partA.length() == 0 && partB.length() == 0){ return new USFPair(); }
				
			//parse to Objects and place in this pair
			this.name = USFParser.parse(partA);
			this.val = USFParser.parse(partB);
			return this;
		}
		catch( Exception e){
			throw new USFSyntaxException("Can not parse USF Pair.");
		}
	}
	
	//Object

	/**
	 * Checks if Name and Value equals to other Pair
	 */
	@Override
	public boolean equals(Object a){
		// is an Pair?
		if( a instanceof USFPair){
			//check name an val
			return this.getName().equals( ((USFPair) a).getName() ) && this.getValue().equals(((USFPair) a).getValue());
		}
		else{
			//no pair => not equal
			return false;
		}
	}
	
	/**
	 * like toUSF()
	 */
	@Override
	public String toString(){
		return this.toUSF() + "\n";
	}
}

