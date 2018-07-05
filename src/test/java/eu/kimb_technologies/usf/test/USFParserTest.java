package eu.kimb_technologies.usf.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.kimb_technologies.usf.*;

@DisplayName("USF Parser")
class USFParserTest {

	Atom data;

	@BeforeEach
	public void setUp() throws Exception {
		this.data = USFParser.parse("[true,false,100,20,-12,{\"test\":{\"a\":false}}]");
	}

	@Test
	@DisplayName("Parse Test")
	public void testParse() throws USFSyntaxException {
		Object[] array = ((USFList) this.data).toArray();

		assertEquals(array[0], new Bool(true));
		assertEquals(array[1], new Bool(false));
		assertEquals(array[2], new USFInteger(100));
		assertEquals(array[3], new USFInteger(20));
		assertEquals(array[4], new USFInteger(-12));
		assertEquals(array[5], new USFPair().loadUSF("{\"test\":{\"a\":false}}"));
	}

	@Test
	@DisplayName("File test")
	public void testFile() throws Exception {
		TestClassSavable saveab = new TestClassSavable();
		saveab.fromAtom(data);

		USFParser.saveToFile(System.getProperty("user.home") + "/testfile.usf", saveab);

		Atom data = USFParser.loadFromFile(System.getProperty("user.home") + "/testfile.usf");
		
		assertEquals( data.toUSF() , "[true,false,100,20,-12,{\"test\":{\"a\":false}}]");
		
		//delete test file
		new File( System.getProperty("user.home") + "/testfile.usf" ).delete();
	}
}

class TestClassSavable implements USFSaveable {

	private Atom atom = null;

	@Override
	public Atom toAtom() {
		return atom;
	}

	@Override
	public void fromAtom(Atom atom) {
		this.atom = atom;
	}

}
