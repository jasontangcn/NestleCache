/*
 * Created on May 22, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fruits.nestle.cache.test;

import com.fruits.nestle.cache.DistributedCache;

public class DistributedCacheTest {

	public static void main(String[] args) throws Exception {
		DistributedCache cache = DistributedCache.getCache("distributedA");
		Person p1 = new Person("C1", "TomHornson", "Male", 24, "ShenZhen");
		Person p2 = new Person("C2", "Louis", "Male", 24, "ShenZhen");
		Person p3 = new Person("C3", "Alan", "Male", 24, "ShenZhen");
		Person p4 = new Person("C4", "Lily", "Male", 24, "ShenZhen");
		cache.put(new Integer(1), p1);
		cache.put(new Integer(2), p2);
		cache.put(new Integer(3), p3);
		cache.put(new Integer(4), p4);

		Person p5 = (Person) cache.get(new Integer(5));
		if (null != p5)
			System.out.println(p5.getName());
		try {
			Thread.sleep(8 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		p5 = (Person) cache.get(new Integer(5));
		if (p5 != null)
			System.out.println(p5.getName());
	}
}
