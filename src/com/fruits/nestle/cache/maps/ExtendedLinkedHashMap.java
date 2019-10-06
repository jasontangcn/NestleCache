/*
 * Created on 2004-10-31
 *
 */
package com.fruits.nestle.cache.maps;

/**
 * @author TomHornson@hotmail.com
 *
 */
import java.util.LinkedHashMap;
import java.util.Map;

public class ExtendedLinkedHashMap extends LinkedHashMap {
	private int maxinalCapability = Integer.MAX_VALUE;

	public ExtendedLinkedHashMap() {
		super();
	}

	public ExtendedLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder, int maxinalCapability) {
		super(initialCapacity, loadFactor, accessOrder);
		this.maxinalCapability = maxinalCapability;
	}

	protected boolean removeEldestEntry(Map.Entry eldest) {
		return size() > maxinalCapability;
	}

	public void setMaxinalCapability(int maxinalCapability) {
		this.maxinalCapability = maxinalCapability;
	}

	public int getMaxinalCapability() {
		return this.maxinalCapability;
	}
}
