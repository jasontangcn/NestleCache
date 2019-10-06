/*
 * Created on May 31, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fruits.nestle.cache.recyclebin;

import java.net.*;

public class ClassGetResourceTest {
	public static void getResource() {
		URL url = ClassGetResourceTest.class.getClass().getResource("/com/fruits/nestle/cache/default.memory");
		System.out.println(url.getPath());
	}

	public static void main(String[] args) {
		getResource();
	}
}
