/*
 * Created on May 19, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fairchild.nestle.cache.recyclebin;

import java.util.*;

public class IteratorConditionTest {
	public static void main(String[] args) {
		Set set = new HashSet();
		Iterator iter = set.iterator();
		for (; iter.hasNext();) {
			System.out.println(iter.next());
		}
	}
}
