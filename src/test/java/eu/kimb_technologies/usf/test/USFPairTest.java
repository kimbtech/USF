package eu.kimb_technologies.usf.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.kimb_technologies.usf.*;

@DisplayName("USF Pair")
class USFPairTest {
	
	USFPair main, second, data;

	@BeforeEach
	public void setUp() throws Exception {
		main = new USFPair();
		
		second = new USFPair();
		second = (USFPair) second.loadUSF("{\"Haus\":[\"B\\\"lu\",{100:200},true]}");
		
		data = new USFPair(main, new Bool(true));
	}
	
	@Test
	@DisplayName("Data in Pair")
	public void testPairData() {
		assertEquals(main.toString(), "{:}\n");
		
		assertEquals( second.getName(), new USFString("Haus"));
		
		try {
			assertEquals( ((USFList) second.getValue()).size(), ((USFList) USFParser.parse("[\"B\\\"lu\",{100:200},true]")).size() );
		} catch (USFSyntaxException e) {
			e.printStackTrace();
		}
		
		assertNull( ((USFPair) data.getName() ).getName() );
		
		assertTrue( ( (Bool) data.getValue() ).getValue());
	}
	
	@Test
	@DisplayName("Pair to USF Export")
	public void testImExport() {
		try {
			data = (USFPair) data.loadUSF("{:}");
		} catch (USFSyntaxException e) {
			e.printStackTrace();
		}
		assertEquals(data.toUSF(), "{:}");
		assertEquals( second.toUSF(), "{\"Haus\":[\"B\\\"lu\",{100:200},true]}" );
	}
	
	@Test
	@DisplayName("USF List Import Errors")
	public void testLoadUSF() {
		
		assertThrows(USFSyntaxException.class,()->{
			main.loadUSF("0,1");
		});
		assertThrows(USFSyntaxException.class,()->{
			main.loadUSF("{00:00:00}");
		});
		assertThrows(USFSyntaxException.class,()->{
			main.loadUSF("{00:[true}");
		});
		assertThrows(USFSyntaxException.class,()->{
			main.loadUSF("{}");
		});
	}
	
	
}
