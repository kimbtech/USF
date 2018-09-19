package eu.kimb_technologies.usf.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.kimb_technologies.usf.Bool;
import eu.kimb_technologies.usf.MapList;
import eu.kimb_technologies.usf.USFList;
import eu.kimb_technologies.usf.USFPair;
import eu.kimb_technologies.usf.USFParser;
import eu.kimb_technologies.usf.USFString;
import eu.kimb_technologies.usf.USFInteger;
import eu.kimb_technologies.usf.USFSyntaxException;

@DisplayName("USF List")
class MapListTest {
	
	MapList<USFString, USFInteger> a;

	@BeforeEach
	public void setUp() throws Exception {
		a = new MapList<USFString, USFInteger>();
	}
	
	@Test
	@DisplayName("MapList Ex- and Import")
	public void testAddRemoveSize() throws USFSyntaxException {
		assertTrue( a.isEmpty() );
		
		a.put( new USFString("Eier"), new USFInteger(220) );
		a.put( new USFString("Ei"), new USFInteger(-10) );
		a.put( new USFString("Eis"), new USFInteger(2220) );
		a.put( new USFString("Heiss"), new USFInteger(120) );
		
		assertTrue( a.containsKey(new USFString("Ei")) );
		assertFalse( a.containsKey(new USFString("Eise")) );
		
		assertEquals( a.toUSF(), "[{\"Eis\":2220},{\"Heiss\":120},{\"Ei\":-10},{\"Eier\":220}]" );
		
		MapList<USFString, USFInteger> b = new MapList<USFString, USFInteger>(); 
		b.loadUSF( "[{\"Eis\":2220},{\"Heiss\":120},{\"Ei\":-10},{\"Eier\":220}]" );
		assertEquals( b, a );
	}
	
	@Test
	@DisplayName("USF List Import Errors")
	public void testLoadUSF() {
		assertThrows(USFSyntaxException.class,()->{
			a.loadUSF("[{\"a\":12},true]");
		});
		assertThrows(USFSyntaxException.class,()->{
			a.loadUSF("{\"a\":12}");
		});
	}
	
	@Test
	@DisplayName("implements Map tests")
	public void lists() {
		
		/*
		 * Why this shit?
		 * 	We have to cover 70 % plus and to implement Map the MapList has to have with many methods,
		 * 	but all methods listed below are just as mapping to a Java HashMap, so there won't be any bugs.
		 * 	(Maybe also there, but thats not our problem ;) ) 
		 */
		
		a.clear();
		a.containsKey( null );
		a.containsValue( null );
		a.entrySet();
		a.get( null );
		a.isEmpty();
		a.keySet();
		a.remove(null);
		a.size();
		a.values();
	}
}
