/*
 * Created on May 22, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fruits.nestle.cache.test;

import com.fruits.nestle.cache.Cache;

public class CacheTest {

	public static void main(String[] args) {
		Cache cache = Cache.getCache("default");
		Person p1 = new Person("C1", "TomHornson", "Male", 24, "ShenZhen");
		Person p2 = new Person("C2", "Louis", "Male", 24, "ShenZhen");
		Person p3 = new Person("C3", "Alan", "Male", 24, "ShenZhen");
		Person p4 = new Person("C4", "Lily", "Male", 24, "ShenZhen");
		cache.put(new Integer(1), p1);
		cache.put(new Integer(2), p2);
		cache.put(new Integer(3), p3);
		cache.put(new Integer(4), p4);

		Person p = (Person) cache.get(new Integer(3));
		System.out.println(p.getName());
	}
}
