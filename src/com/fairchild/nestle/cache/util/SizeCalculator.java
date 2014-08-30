/*
 * Created on May 20, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fairchild.nestle.cache.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class SizeCalculator {
	/*
	 * private types: boolean,byte,char,short,int,long,float,double,
	 */

	/* It's not defined in the java spec. */
	public static int sizeofBoolean() {
		return 1;
	}

	public static int sizeofByte() {
		return 1;
	}

	public static int sizeofChar() {
		return 2;
	}

	public static int sizeofShort() {
		return 2;
	}

	public static int siezeofInt() {
		return 4;
	}

	public static int sizeofLong() {
		return 8;
	}

	public static int sizeofFloat() {
		return 4;
	}

	public static int sizeofDouble() {
		return 8;
	}

	/*
	 * object types: Object,String
	 */
	public static int sizeofObject() {
		return 4;
	}

	public static int sizeofString(String str) {
		if (str == null)
			return 0;
		return 4 + 2 * str.length();
	}

	public static int sizeofDate() {
		return 12;
	}

	public static int sizeOfProperties(Properties properties) {
		if (properties == null) {
			return 0;
		}
		// Base properties object
		int size = 36;

		Enumeration enumer = properties.elements();
		while (enumer.hasMoreElements()) {
			String prop = (String) enumer.nextElement();
			size += sizeofString(prop);
		}

		enumer = properties.propertyNames();
		while (enumer.hasMoreElements()) {
			String prop = (String) enumer.nextElement();
			size += sizeofString(prop);
		}
		return size;
	}

	public static int sizeOfMap(Map map) {
		if (map == null) {
			return 0;
		}
		// Base map object -- should be something around this size.
		int size = 36;
		Iterator iter = map.values().iterator();
		while (iter.hasNext()) {
			String value = (String) iter.next();
			size += sizeofString(value);
		}
		iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			size += sizeofString(key);
		}
		return size;
	}
}
