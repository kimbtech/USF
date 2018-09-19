package eu.kimb_technologies.usf;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * This static class parses USF-String and loads the from files.
 * Also it creates and saves USF-Files or Strings.
 * @author kimbtech
 */
public class USFParser {
	
	/**
	 * Exports a given Atom to USF.
	 * @param usf The atom that's supposed to become a usf
	 * @return the USF String
	 */
	public static String toUSF( Atom usf ){
		return usf.toUSF();
	}
	
	/**
	 * Exports a given Atom to human friendly USF.
	 * @param usf The atom that's supposed to become a usf
	 * @return the USF (human friendly) String
	 */
	public static String toHumFrieUSF( Atom usf ){
		return usf.toHumFrieUSF( 0 );
	}
	
	/**
	 * Parser for human friendly usf strings 
	 * @param usf the usf String to parse (can be a normal or a human friendly one)
	 * @return the Atom data
	 */
	public static Atom parseHumFrie(String usf) throws USFSyntaxException {
		StringBuilder builder = new StringBuilder( usf.length() );
		try{
			//init vars, to walk trough string
			char bst = 0, bstBefore = 0;
			int index;
			//not in a String (you can only be in one String)
			boolean inString = false;
			
			//starting after [ and ending before ]
			for (index = 0; index < usf.length(); index++) {
				bst = usf.charAt(index);
				bstBefore = index > 0 ? usf.charAt(index-1) : 0;
								
				//go in String and leave string
				if( bst == '"' && bstBefore != '\\') { inString = !inString; }				
				
				// if in string, add all
				if( inString ) {
					builder.append( bst );
				}
				else if( !( bst == '\n' || bst == '\t' || bst == '\r' ) ) {
					builder.append( bst );
				}
			}
			if( inString ) {
				throw new USFSyntaxException("String not ended!");
			}
		}
		catch( Exception e ) {
			throw new USFSyntaxException("Unable to parse human friendly data.");
		}
		return USFParser.parse(builder.toString());
	}
	
	/**
	 * Exports a given Class to USF
	 * @param usf The USFSaveable that's supposed to be exported to usf
	 * @return the USF String
	 */
	public static String toUSF( USFSaveable usf ){
		return USFParser.toUSF( usf.toAtom() );
	}
	
	/**
	 * Parses a given in String in USF to Atom data
	 * @param usf the given String (must not be human friendly!)
	 * @return the Atom data
	 * @throws USFSyntaxException if syntax error in string
	 */
	public static Atom parse( String usf ) throws USFSyntaxException {
		char first = usf.charAt(0);
		char last = usf.charAt(usf.length()-1);
		
		if( first == '[' && last == ']' ){
			//List
			return new USFList().loadUSF(usf);
		}
		else if(first == '{' && last == '}'){
			//Pair
			return new USFPair().loadUSF(usf);
		}
		else if( first == '"' && last == '"' ){
			//String
			return new USFString().loadUSF(usf);
		}
		else if( first == '=' && last == '=' ){
			//Real
			return new Real().loadUSF(usf);
		}
		else if( usf.equals( "true" ) || usf.equals( "false" ) ){
			//Bool
			return new Bool().loadUSF(usf);
		}
		else if( usf.matches("(\\+|-)?[0-9]+") ){
			//Integer
			return new USFInteger().loadUSF(usf);
		}
		else{
			throw new USFSyntaxException("Unknown Syntax!");
		}
	}
	
	/**
	 * Reads a USF File to Memory and return its content
	 * @param filename The absolute path to the USF file (must not be human friendly usf contents!)
	 * @return Data the parsed data
	 * @throws USFSyntaxException Get's thrown if the Syntax of the file is not valid
	 * @throws IOException Get's thrown if the file can't be found
	 */
	public static Atom loadFromFile(String filename) throws USFSyntaxException, IOException{
			return USFParser.loadFromStream( new FileInputStream( filename ) );
	}
	
	/**
	 * Parses a USF File given as Stream
	 * @param stream the input Stream (must not be human friendly usf contents!)
	 * @return the USF as Atom
	 * @throws USFSyntaxException if syntax error in file
	 */
	public static Atom loadFromStream( InputStream stream ) throws USFSyntaxException, IOException {
		//read file to String using utf 8
		BufferedReader in = new BufferedReader(new InputStreamReader( stream, "UTF-8"));
		//usf files have only one first line
		String filecont = in.readLine();
		in.close();
		
		return USFParser.parse(filecont);
	}
	
	/**
	 * Saves a USF.Atom to file under path filename
	 * @param filename file path
	 * @param atom the data to save
	 */
	public static void saveToFile( String filename, Atom atom) throws FileNotFoundException, SecurityException {
		try{
			//Datei oeffnen, write to utf 8
			PrintWriter out = new PrintWriter( filename, "UTF-8" );
			//schreiben
			out.print( USFParser.toUSF( atom ) );
			//Datei schliessen
			out.close();
		} catch (UnsupportedEncodingException e) {
			// utf-8 has to be supported by all java version!
		}
	}
	
	/**
	 * Saves a USFSaveable to file under path filename
	 * @param filename file path
	 * @param usf the savable object
	 * @throws SecurityException 
	 * @throws FileNotFoundException 
	 */
	public static void saveToFile( String filename, USFSaveable usf) throws FileNotFoundException, SecurityException{
		USFParser.saveToFile(filename, usf.toAtom());
	}
}
