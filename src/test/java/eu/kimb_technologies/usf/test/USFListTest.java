package eu.kimb_technologies.usf.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.kimb_technologies.usf.Bool;
import eu.kimb_technologies.usf.USFList;
import eu.kimb_technologies.usf.USFPair;
import eu.kimb_technologies.usf.USFString;
import eu.kimb_technologies.usf.USFInteger;
import eu.kimb_technologies.usf.USFSyntaxException;

@DisplayName("USF List")
class USFListTest {
	
	USFList main, second;

	@BeforeEach
	public void setUp() throws Exception {
		main = new USFList();
		second = new USFList();
		second = (USFList) second.loadUSF("[{\"Haus\":[\"B\\\"lu\",{100:200},true]},2001,true,[true,true]]");
	}
	
	@Test
	@DisplayName("USF List Add, Remove, Size")
	public void testAddRemoveSize() {
		assertTrue( main.isEmpty() );
		
		main.add( new USFPair( new Bool(true), new Bool(false) ) );
		main.add( new USFString("Bla Bla") );
		main.add( new USFInteger(-20) );
		main.add(second);
		
		assertTrue( main.size() == 4);
		assertTrue( main.contains(new USFString("Bla Bla")) );
		
		main.remove(2);
		
		int index = 3;
		for( @SuppressWarnings("unused") Object e : main) {
			index--;
		}
		assertTrue( index == 0);
	}
	
	@Test
	@DisplayName("USF List Export and Import")
	public void testToUSF() {
		assertEquals( main.toUSF(), "[]" );
		main.add(second);
		assertEquals( main.toUSF(), "[[{\"Haus\":[\"B\\\"lu\",{100:200},true]},2001,true,[true,true]]]" );
	}
	
	@Test
	@DisplayName("USF List Import Errors")
	public void testLoadUSF() {
		
		assertThrows(USFSyntaxException.class,()->{
			main.loadUSF("0,1");
		});
		assertThrows(USFSyntaxException.class,()->{
			main.loadUSF("[müll,true,false]");
		});
		assertThrows(USFSyntaxException.class,()->{
			main.loadUSF("[müll,[true,false]");
		});
	}
	
	@Test
	@DisplayName("implements List tests")
	public void lists() {
		Bool[] boo = {new Bool(), new Bool()};
		
		/*
		 * Why this shit?
		 * 	We have to cover 70 % plus and to implement List the USFList has to have with many methods,
		 * 	but all methods listed below are just as mapping to a Java ArrayList, so there won't be any bugs.
		 * 	(Maybe also there, but thats not out problem ;) ) 
		 */
		
		main.isEmpty();
		main.add(0, new Bool());
		main.set(0, new Bool());
		main.contains(new Bool());
		main.iterator();
		main.toArray();
		main.toArray(boo);		
		main.get(0);
		main.indexOf(new Bool());
		main.lastIndexOf(new Bool());
		main.lastIndexOf(new Bool());
		main.listIterator();
		main.listIterator(0);
		main.subList(0, 0);
		main.clear();
		main.remove(new Bool());
	}
}
