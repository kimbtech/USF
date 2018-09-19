package eu.kimb_technologies.usf.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.kimb_technologies.usf.Bool;
import eu.kimb_technologies.usf.Real;
import eu.kimb_technologies.usf.USFSyntaxException;

@DisplayName("USF Real")
class RealTest {
	
	Real iPlus, iMinus, iNull;

	@BeforeEach
	public void setUp() throws Exception {
		iPlus = new Real(100.110);
		iMinus = new Real(-100.025);
		iNull = new Real();
	}

	@Test
	@DisplayName("Getter Test")
	public void testGet() {
		assertEquals( iPlus.getValue(), 100.110 );
		assertEquals( iMinus.getValue(), -100.025);
		//usf int default is 0!
		assertEquals( iNull.getValue(), 0.0 );
	}
	
	@Test
	@DisplayName("Setter Test")
	public void testSet(){
		iNull.setValue( Double.NaN );
		assertEquals( iNull.getValue(), Double.NaN );
		
		iMinus.setValue( iMinus.getValue() + 20 );
		assertEquals( iMinus.getValue(), -100.025 + 20 );
	}
	
	@Test
	@DisplayName("USF Export Test")
	public void testToUSF() {
		assertEquals( iPlus.toUSF(), "=100.11=" );
		assertEquals( iMinus.toUSF(), "=-100.025=" );
		assertEquals( iNull.toUSF(), "=0.0=" );
	}
	
	@Test
	@DisplayName("USF Import Test")
	public void testLoadUSF() throws USFSyntaxException {
		iMinus.loadUSF("=-1.0E-4=");
		iNull.loadUSF("=0.0=");
		iPlus.loadUSF("=112.025=");
		
		assertEquals(iNull.getValue(), 0.0);
		assertEquals(iPlus.getValue(), 112.025);
		assertEquals(iMinus.getValue(), -0.0001);
	}
	
	@Test
	@DisplayName("USF Import Exception Test")
	public void testLoadUSFEx(){
		assertThrows(USFSyntaxException.class,()->{
			iNull.loadUSF("2.0"); // should be =2.0=
		});
		assertThrows(USFSyntaxException.class,()->{
			iNull.loadUSF("*2");
		});
		assertThrows(USFSyntaxException.class,()->{
			iNull.loadUSF("234,12");
		});
		assertThrows(USFSyntaxException.class,()->{
			iNull.loadUSF("  33- ");
		});
		assertThrows(USFSyntaxException.class,()->{
			iNull.loadUSF("3.3.2");
		});
	}
	
	@Test
	@DisplayName("Equals Test")
	public void testEquals() throws USFSyntaxException {
		assertTrue( iPlus.equals( new Real(100.110) ) );
		assertFalse( iMinus.equals( new Real(-110.025) ) );
		assertEquals( new Real(), iNull );
		assertFalse( iMinus.equals( new Double(12.0) ) );
	}
	
	@Test
	@DisplayName("Compare To Test")
	public void testCompareTo() {
		assertEquals( iPlus.compareTo( new Double(-110) ), (new Double(100.110) ).compareTo( new Double(-110) ) );
		assertEquals( iMinus.compareTo( new Double(-992) ), (new Double(-100.025) ).compareTo( new Double(-992) ) );
	}
	
	@Test
	@DisplayName("Equals to String")
	public void testToString() {
		assertEquals( iNull.toString(), iNull.toUSF() );
	}
}
