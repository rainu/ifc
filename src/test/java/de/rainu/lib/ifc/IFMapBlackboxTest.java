package de.rainu.lib.ifc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class IFMapBlackboxTest {
	private final static boolean DELETE_ON_EXIT = true;
	private final static String DB_PATH = "/tmp/IFC";
	
	@Before
	public void before(){
		//die (bestehende) Datenbank vorher entfernen
		new File(DB_PATH + ".h2.db").delete();
	}
	
	private <T extends Serializable> void testSimpleUse(IFMap<String, T> map, T value, T value2){
		final String testKey = "Test";
		
		assertNull(map.get(testKey));
		
		T preValue = map.put(testKey, value);
		
		assertTrue(map.containsKey(testKey));
		assertEquals(value, map.get(testKey));
		assertTrue(map.size() == 1);
		assertTrue(map.containsValue(value));
		assertFalse(map.containsValue(value2));
		assertNull(preValue);
		
		preValue = map.put(testKey, null);
		
		assertNull(map.get(testKey));
		assertTrue(map.size() == 1);
		assertFalse(map.containsValue(value));
		assertFalse(map.containsValue(value2));
		assertTrue(map.containsValue(null));
		assertEquals(value, preValue);
		
		preValue = map.put(testKey, value2);
		
		assertEquals(value2, map.get(testKey));
		assertTrue(map.size() == 1);
		assertFalse(map.containsValue(value));
		assertTrue(map.containsValue(value2));
		assertFalse(map.containsValue(null));
		assertNull(preValue);
		
		boolean iter = false;
		for(String key : map.keySet()){
			iter = true;
		}
		assertTrue(iter);
		
		map.clear();
		assertTrue(map.size() == 0);
		
		iter = false;
		for(String key : map.keySet()){
			iter = true;
		}
		assertFalse(iter);
		
		map.put(testKey, value);
		preValue = map.remove(testKey);
		assertEquals(value, preValue);
		assertFalse(map.containsKey(testKey));
	}
	
	private <T extends Serializable> void testPutAll(IFMap<String, T> map, Map<String, T> toPut){
		IFMap<String, T> iMap = new IFMap<String, T>(DB_PATH, null, DELETE_ON_EXIT);
		
		int expectedCount = map.size() + toPut.size();
		
		map.putAll(toPut);
		iMap.putAll(map);
		
		for(String key : toPut.keySet()){
			assertEquals(toPut.get(key), map.get(key));
			assertEquals(toPut.get(key), iMap.get(key));
		}
		
		assertTrue(map.size() == iMap.size());
		assertTrue(expectedCount == map.size());
	}
	
	@Test
	public void testIntegerSimpleUse() {
		IFMap<String, Integer> iMap = new IFMap<String, Integer>(DB_PATH, null, DELETE_ON_EXIT);
		testSimpleUse(iMap, 13121989, 13082010);
		testPutAll(iMap, new HashMap<String, Integer>(){{
			put("1", 13121989); put("2", 13041990); put("3", 13082010);
		}});
	}
	
	@Test
	public void testLongSimpleUse() {
		IFMap<String, Long> lMap = new IFMap<String, Long>(DB_PATH, null, DELETE_ON_EXIT);
		testSimpleUse(lMap, 13121989L, 13082010L);
		testPutAll(lMap, new HashMap<String, Long>(){{
			put("1", 13121989L); put("2", 13041990L); put("3", 13082010L);
		}});
	}
	
	@Test
	public void testFloatSimpleUse() {
		IFMap<String, Float> fMap = new IFMap<String, Float>(DB_PATH, null, DELETE_ON_EXIT);
		testSimpleUse(fMap, 12.13f, 13.10f);
		testPutAll(fMap, new HashMap<String, Float>(){{
			put("1", 13.121989f); put("2", 13.041990f); put("3", 13.082010f);
		}});
	}
	
	@Test
	public void testDoubleSimpleUse() {
		IFMap<String, Double> dMap = new IFMap<String, Double>(DB_PATH, null, DELETE_ON_EXIT);
		testSimpleUse(dMap, 12.13d, 13.10d);
		testPutAll(dMap, new HashMap<String, Double>(){{
			put("1", 13.121989d); put("2", 13.041990d); put("3", 13.082010d);
		}});
	}
	
	@Test
	public void testByteSimpleUse() {
		IFMap<String, Byte> bMap = new IFMap<String, Byte>(DB_PATH, null, DELETE_ON_EXIT);
		testSimpleUse(bMap, (byte)13, (byte)12);
		testPutAll(bMap, new HashMap<String, Byte>(){{
			put("1", (byte)89); put("2", (byte)90); put("3", (byte)10);
		}});
	}
	
	@Test
	public void testCharSimpleUse() {
		IFMap<String, Character> cMap = new IFMap<String, Character>(DB_PATH, null, DELETE_ON_EXIT);
		testSimpleUse(cMap, '\u03FA', '\u03FB');
		testPutAll(cMap, new HashMap<String, Character>(){{
			put("1", '\u04FA'); put("2", '\u05FA'); put("3", '\u06FA');
		}});
	}
	
	@Test
	public void testBooleanSimpleUse() {
		IFMap<String, Boolean> bMap = new IFMap<String, Boolean>(DB_PATH, null, DELETE_ON_EXIT);
		testSimpleUse(bMap, true, false);
		testPutAll(bMap, new HashMap<String, Boolean>(){{
			put("1", true); put("2", false); put("3", true);
		}});
	}
	
	@Test
	public void testStringSimpleUse() {
		IFMap<String, String> sMap = new IFMap<String, String>(DB_PATH, null, DELETE_ON_EXIT);
		testSimpleUse(sMap, "13121989", "13108010");
		testPutAll(sMap, new HashMap<String, String>(){{
			put("1", "13121989"); put("2", "13041990"); put("3", "13082010");
		}});
	}
	
	@Test
	public void testObjectSimpleUse() {
		IFMap<String, Serializable> oMap = new IFMap<String, Serializable>(DB_PATH, null, DELETE_ON_EXIT, true);
		testSimpleUse(oMap, new BigInteger("1312198900000000000"), new BigInteger("1310801000000000000"));
		testPutAll(oMap, new HashMap<String, Serializable>(){{
			put("1", new BigInteger("13121989")); 
			put("2", new BigInteger("13041990")); 
			put("3", new BigInteger("13082010"));
		}});
	}
}
