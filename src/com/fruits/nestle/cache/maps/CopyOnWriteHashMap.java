/*
 * Created on Jun 6, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fruits.nestle.cache.maps;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Collections;

public class CopyOnWriteHashMap implements Map {

	protected volatile Map mapToRead = Collections.unmodifiableMap(getNewMap());

	// you can override it as new TreeMap();

	protected Map getNewMap() {
		return new HashMap();
	}

	// copy mapToWrite to mapToRead
	protected Map copy() {
		Map newMap = getNewMap();
		newMap.putAll(mapToRead);
		return newMap;
	}

	// read methods

	public int size() {
		return mapToRead.size();
	}

	public boolean isEmpty() {
		return mapToRead.isEmpty();
	}

	public boolean containsKey(Object key) {
		return mapToRead.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return mapToRead.containsValue(value);
	}

	public Collection values() {
		return mapToRead.values();
	}

	public Set entrySet() {
		return mapToRead.entrySet();
	}

	public Set keySet() {
		return mapToRead.keySet();
	}

	public Object get(Object key) {
		return mapToRead.get(key);
	}

	// write methods
	public synchronized void clear() {
		mapToRead = getNewMap();
	}

	public synchronized Object remove(Object key) {
		Map map = copy();
		Object o = map.remove(key);
		mapToRead = map;
		return o;
	}

	public synchronized Object put(Object key, Object value) {
		Map map = copy();
		Object o = map.put(key, value);
		mapToRead = map;
		return o;
	}

	public synchronized void putAll(Map t) {
		Map map = copy();
		map.putAll(t);
		mapToRead = map;
	}

}
