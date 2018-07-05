package eu.kimb_technologies.usf.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.kimb_technologies.usf.Bool;
import eu.kimb_technologies.usf.USFSyntaxException;

@DisplayName("USF Bool")
class BoolTest {
	
	Bool bFalse, bTrue, bNull;

	@BeforeEach
	public void setUp() throws Exception {
		bFalse = new Bool(false);
		bTrue = new Bool(true);
		bNull = new Bool();
	}

	@Test
	@DisplayName("Getter Test")
	public void testGet() {
		assertFalse( bFalse.getValue() );
		assertTrue( bTrue.getValue());
		//boolean default is false!
		assertEquals( bFalse.getValue(), bNull.getValue() );
	}
	
	@Test
	@DisplayName("Setter Test")
	public void testSet(){
		bNull.setValue(true);
		assertTrue( bNull.getValue() );
		
		bNull.setValue(false);
		assertFalse( bNull.getValue() );
	}
	
	@Test
	@DisplayName("USF Export Test")
	public void testToUSF() {
		assertEquals( bFalse.toUSF(), "false" );
		assertEquals( bTrue.toUSF(), "true" );
	}
	
	@Test
	@DisplayName("USF Import Test")
	public void testLoadUSF() throws USFSyntaxException {
		bFalse.loadUSF("true");
		bTrue.loadUSF("false");
		assertTrue( bFalse.getValue() );
		assertFalse( bTrue.getValue() );
	}
	
	@Test
	@DisplayName("USF Import Exception Test")
	public void testLoadUSFEx(){
		assertThrows(USFSyntaxException.class,()->{
			bFalse.loadUSF("bla");
		});
	}
	
	@Test
	@DisplayName("Equals Test")
	public void testEquals() {
		assertEquals( bFalse.equals( new Bool(false) ), (new Boolean(false) ).equals(false) );
		assertEquals( bTrue.equals( new Integer(3) ), (new Boolean(true) ).equals(false) );
	}
	
	@Test
	@DisplayName("Compare To Test")
	public void testCompareTo() {
		assertEquals( bFalse.compareTo( new Boolean(false) ), (new Boolean(false) ).compareTo(false) );
	}
	
	@Test
	@DisplayName("Equals to String")
	public void testToString() {
		assertEquals( bNull.toString(), bNull.toUSF() );
	}
	
	

}
