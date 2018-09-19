package eu.kimb_technologies.usf.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.kimb_technologies.usf.Atom;
import eu.kimb_technologies.usf.Bool;
import eu.kimb_technologies.usf.USFInteger;
import eu.kimb_technologies.usf.USFString;
import eu.kimb_technologies.usf.USFSyntaxException;

@DisplayName("USF Atom")
class AtomTest {
	
	private Atom a1, a2, a3, a4;
	
	@BeforeEach
	public void setUp() throws Exception {
		a1 = new USFInteger(12);
		a2 = new USFInteger(12);
		a3 = new USFInteger(223);
		a4 = new USFString("aa");
	}
	
	@Test
	@DisplayName("Equals Test")
	public void testEquals() {
		assertTrue( a1.equals( a2 ) );
		assertFalse( a1.equals( a3 ) );
		assertFalse( a1.equals( a4 ) );
		assertFalse( a4.equals( new String("aa") ) );
	}
}
