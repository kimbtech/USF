package eu.kimb_technologies.usf.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.kimb_technologies.usf.USFString;
import eu.kimb_technologies.usf.USFSyntaxException;

@DisplayName("USF String")
class USFStringTest {
	
	USFString sOne, sTwo, sNull;

	@BeforeEach
	public void setUp() throws Exception {
		sOne = new USFString("Hallo");
		sTwo = new USFString("Hallo \"Max\",\r\n das ist wichtig!");
		sNull = new USFString();
	}

	@Test
	@DisplayName("Getter Test")
	public void testGet() {
		assertEquals( sOne.getValue(), "Hallo" );
		assertEquals( sTwo.getValue(), "Hallo \"Max\",\r\n das ist wichtig!");
		//usf int default is 0!
		assertEquals( sNull.getValue(), "" );
	}
	
	@Test
	@DisplayName("Setter Test")
	public void testSet(){
		sNull.setValue("null");
		assertEquals( sNull.getValue(), "null" );
		
		sOne.setValue( "Tschüss, ..." );
		assertEquals( sOne.getValue(), "Tschüss, ..." );
	}
	
	@Test
	@DisplayName("USF Escape Test")
	public void escapeTest() {
		assertEquals( USFString.escapeString(""), "\"\"");
		assertEquals( USFString.escapeString(null), "\"\"");
		assertEquals( USFString.escapeString(".."), "\"..\"");
		assertEquals( USFString.escapeString("\t  \n__\r"), "\"\\t  \\n__\\r\"");
		assertEquals( USFString.escapeString("\\ @ \\"), "\"\\\\ @ \\\\\"");
	}
	
	@Test
	@DisplayName("USF Escape and Unescape Test")
	public void unAndEscapeTest() {
		String[] arr = {
				"shdad \\ adfs",
				"\"Hallo\" HHüüh \t \r dd++üä",
				"FF_🇫🇷_FF",
				"",
				"nrt"
		};
		for(String s : arr) {
			assertEquals( USFString.unescapeString(USFString.escapeString(s)), s);
		}
	}
	
	@Test
	@DisplayName("USF Export Test")
	public void testToUSF() {
		assertEquals( sOne.toUSF(), USFString.escapeString(sOne.getValue()) );
		assertEquals( sTwo.toUSF(), USFString.escapeString(sTwo.getValue()) );
		assertEquals( sNull.toUSF(), USFString.escapeString(sNull.getValue()) );
	}
	
	@Test
	@DisplayName("USF Import Test")
	public void testLoadUSF() throws USFSyntaxException {
		sNull.loadUSF("\"Test\"");
		assertEquals( sNull.getValue(), "Test");	
	}
	
	@Test
	@DisplayName("USF Import Exception Test")
	public void testLoadUSFEx(){
		assertThrows(USFSyntaxException.class,()->{
			sNull.loadUSF("234");
		});
		assertThrows(USFSyntaxException.class,()->{
			sNull.loadUSF("\"234");
		});
		assertThrows(USFSyntaxException.class,()->{
			sNull.loadUSF("Hallo");
		});
	}
	
	@Test
	@DisplayName("Equals Test")
	public void testEquals() {
		assertTrue( sOne.equals( new USFString( "Hallo" ) ) );
		assertFalse( sNull.equals( "" ) );
	}
}
