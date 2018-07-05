package eu.kimb_technologies.usf.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.kimb_technologies.usf.USFInteger;
import eu.kimb_technologies.usf.USFSyntaxException;

@DisplayName("USF Integer")
class USFIntegerTest {
	
	USFInteger iPlus, iMinus, iNull;

	@BeforeEach
	public void setUp() throws Exception {
		iPlus = new USFInteger(100);
		iMinus = new USFInteger(-992);
		iNull = new USFInteger();
	}

	@Test
	@DisplayName("Getter Test")
	public void testGet() {
		assertEquals( iPlus.getValue(), 100 );
		assertEquals( iMinus.getValue(), -992);
		//usf int default is 0!
		assertEquals( iNull.getValue(), 0 );
	}
	
	@Test
	@DisplayName("Setter Test")
	public void testSet(){
		iNull.setValue(20000);
		assertEquals( iNull.getValue(), 20000 );
		
		iMinus.setValue( iMinus.getValue() + 20 );
		assertEquals( iMinus.getValue(), -992 + 20 );
	}
	
	@Test
	@DisplayName("USF Export Test")
	public void testToUSF() {
		assertEquals( iPlus.toUSF(), "100" );
		assertEquals( iMinus.toUSF(), "-992" );
		assertEquals( iNull.toUSF(), "0" );
	}
	
	@Test
	@DisplayName("USF Import Test")
	public void testLoadUSF() throws USFSyntaxException {
		iNull.loadUSF("+20");
		iPlus.loadUSF("20");
		iMinus.loadUSF("-2");
		assertEquals(iNull.getValue(), iPlus.getValue());
		assertEquals(iMinus.getValue(), -2);
	}
	
	@Test
	@DisplayName("USF Import Exception Test")
	public void testLoadUSFEx(){
		assertThrows(USFSyntaxException.class,()->{
			iNull.loadUSF("2.34");
		});
		assertThrows(USFSyntaxException.class,()->{
			iNull.loadUSF("\"234\"");
		});
		assertThrows(USFSyntaxException.class,()->{
			iNull.loadUSF("  33 ");
		});
		assertThrows(USFSyntaxException.class,()->{
			iNull.loadUSF("33E2");
		});
	}
	
	@Test
	@DisplayName("Equals Test")
	public void testEquals() {
		assertTrue( iPlus.equals( new USFInteger(100) ) );
		assertFalse( iMinus.equals( new Integer(100) ) );
	}
	
	@Test
	@DisplayName("Compare To Test")
	public void testCompareTo() {
		assertEquals( iPlus.compareTo( new Integer(-100) ), (new Integer(100) ).compareTo( new Integer(-100) ) );
		assertEquals( iMinus.compareTo( new Integer(-992) ), (new Integer(-992) ).compareTo( new Integer(-992) ) );
	}
	
	@Test
	@DisplayName("Equals to String")
	public void testToString() {
		assertEquals( iNull.toString(), iNull.toUSF() );
	}
}
