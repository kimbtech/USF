package eu.kimb_technologies.usf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A List for USF
 * @author kimb
 */
public class USFList extends Atom implements List<Atom> {

	/**
	 * The List
	 */
	private ArrayList<Atom> list; 
	
	/**
	 * Create empty List
	 */
	public USFList(){
		this.list = new ArrayList<Atom>();
	}
	
	// USF

	@Override
	public String toUSF() {
		String usf = "[";
		for( Atom element : this.list  ){
			usf = usf.concat( element.toUSF() + "," );
		}
		//remove last ,
		if( usf.length() > 2){
			usf = usf.substring(0, usf.length() - 1);
		}
		return usf.concat( "]" );
	}
	
	@Override
	public String toHumFrieUSF( int indent ) {
		String usf = USFString.stringRepeat(indent, "\t") + "[\r\n";
		indent++;
		for( Atom element : this.list  ){
			usf = usf.concat( element.toHumFrieUSF(indent) + ",\r\n" );
		}
		//remove last ,
		if( usf.length() > 2){
			usf = usf.substring(0, usf.length() - 3) + "\r\n";
		}
		indent--;
		return usf.concat( USFString.stringRepeat(indent, "\t") + "]" );
	}

	@Override
	public Atom loadUSF(String usf) throws USFSyntaxException {
		if( usf.charAt(0) != '[' || usf.charAt(usf.length()-1) != ']') {
			throw new USFSyntaxException("USF List has to start and end with []!");
		}
		USFList ret = new USFList();
		if(usf.length() == 2) {
			return ret; // empty list
		}
		try{
			//init vars, to walk trough string
			char bst = 0, bstBefore = 0;
			int index;
			// zero means not in any pairs or lists (you can be in multiple lists, pairs)
			int inPair = 0, inList = 0;
			//not in a String (you can only be in one String)
			boolean inString = false;
			StringBuilder builder = new StringBuilder();
			
			//starting after [ and ending before ]
			for (index = 1; index < usf.length() - 1; index++) {
				bst = usf.charAt(index);
				bstBefore = usf.charAt(index-1);
				
				//count lists, we go inside
				if( bst == '[' && !inString) { inList++;}
				//count pairs, we go inside
				else if( bst == '{' && !inString) { inPair++; }
				
				//go in String and leave string
				if( bst == '"' && bstBefore != '\\') { inString = !inString; }
				
				//count lists we leave
				if( bst == ']' && !inString) { inList--; }
				//count pairs we leave
				else if( bst == '}' && !inString) { inPair--; }				
				
				//split at this char?
				//	must be a , and we dont want to be in any Strings, Lists, Pairs
				if( (bst == ',' && inPair == 0 && inList == 0 && !inString) ) {
					//parse read string until here
					ret.add(USFParser.parse(builder.toString()));
					//new string
					builder = new StringBuilder();
				}
				else {
					//dont split here, add char
					builder.append( bst );
				}
			}
			if( inString || inPair != 0 || inList != 0 ) {
				throw new USFSyntaxException("String, Pair or List not ended!");
			}
			
			//parse the last part after last , 
			ret.add(USFParser.parse(builder.toString()));
			
			return ret;
		}
		catch( Exception e){
			e.printStackTrace();
			throw new USFSyntaxException("Can not parse USF List.");
		}
	}
	
	@Override
	public boolean equals( Object o ) {
		if( o instanceof USFList ) {
			return Arrays.equals( this.list.toArray(), ((USFList) o).list.toArray() );
		}
		else {
			return false;
		}
	}
	
	// all Methods from List
	
	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	@Override
	public Iterator<Atom> iterator() {
		return this.list.iterator();
	}

	@Override
	public Object[] toArray() {
		return this.list.toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Atom[] toArray(Object[] a) {
		return this.list.toArray( (Atom[]) a);
	}

	@Override
	public boolean remove(Object o) {
		return this.list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Atom> c) {
		return this.list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Atom> c) {
		return this.list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.list.retainAll(c);
	}

	@Override
	public void clear() {
		this.list.clear();
	}

	@Override
	public Atom get(int index) {
		return this.list.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	@Override
	public ListIterator<Atom> listIterator() {
		return this.list.listIterator();
	}

	@Override
	public ListIterator<Atom> listIterator(int index) {
		return this.list.listIterator(index);
	}

	@Override
	public List<Atom> subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

	@Override
	public boolean add(Atom e) {
		return this.list.add(e);
	}

	@Override
	public void add(int index, Atom element) {
		this.list.add(index, element);		
	}

	@Override
	public Atom remove(int index) {
		return this.list.remove(index);
	}

	@Override
	public Atom set(int index, Atom element) {
		return this.list.set(index, element);
	}
}
