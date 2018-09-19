package eu.kimb_technologies.usf;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Some type of HashMap which can be exported and imported as usf.
 * Interally uses java.util.HashMap<K,V>
 * 
 * @author kimbtech
 *
 * @param <K> the type of the keys, subtype of Atom
 * @param <V> the type of the values, subtype of Atom
 */
public class MapList <K extends Atom, V extends Atom> extends Atom implements Map<K,V> {
	
	private HashMap<K,V> map = new HashMap<K,V>();	
	
	/*
	 * USF part
	 */
	
	@Override
	public String toUSF() {
		return this.genUSFList().toUSF();
	}

	@Override
	public String toHumFrieUSF(int indent) {
		return this.genUSFList().toHumFrieUSF(indent);
	}

	@Override
	public Atom loadUSF(String usf) throws USFSyntaxException {
		USFList data = new USFList();
		try {
			data = (USFList) USFParser.parse( usf );
			
			//put each value in map, under it's name
			for( Atom pair : data ) {
				if( pair instanceof USFPair ) {
						//the name must be a string
						this.map.put(
								(K) ((USFPair) pair).getName(),
								(V) ((USFPair) pair).getValue()
						);
				}
				else {
					throw new USFSyntaxException("Not a valid MapList!");
				}
			}
		}
		catch( ClassCastException e) {
			throw new USFSyntaxException("Not a valid MapList!");
		}
		return this;
	}

	@Override
	public boolean equals(Object o) {
		return this.map.equals(o);
	}
	
	/**
	 * Generates a USF data list outof the internal hashmap.
	 * It is a list of pairs, where each pair has key and value of one hashmap entry.
	 * @return the usf List of the Map
	 */
	private USFList genUSFList() {
		USFList l = new USFList();
		for (Map.Entry<K, V> entry : this.map.entrySet()) {
			l.add(new USFPair(entry.getKey(), entry.getValue()));
		}
		return l;
	}
	
	/*
	 * Map part
	 */

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.map.containsKey( key );
	}

	@Override
	public boolean containsValue(Object value) {
		return this.map.containsKey( value );
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return this.map.entrySet();
	}

	@Override
	public V get(Object key) {
		return this.map.get( key );
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return this.map.keySet();
	}

	@Override
	public V put(K key, V value) {
		return this.map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		this.map.putAll(map);
	}

	@Override
	public V remove(Object key) {
		return this.map.remove(key);
	}

	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public Collection<V> values() {
		return this.map.values();
	}
}
