package eu.kimb_technologies.usf;

/**
 * This interface should be implemented by all classes which
 * could be exported as USF.
 * @author kimbtech
 */
public interface USFSaveable {
	/**
	 * Method to save an object as USF-File by USF-Parser
	 * @return an Atom containing all important attributes of the object
	 */
	Atom toAtom();
	
	/**
	 * Methode to recreate an object from USF-File
	 * @param atom an Atom containing all important attributes ot the object 
	 */
	void fromAtom(Atom atom);
}
