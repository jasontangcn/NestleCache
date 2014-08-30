/*
 * Created on May 31, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fairchild.nestle.cache.recyclebin;

import java.net.*;
import java.io.*;

public class ClassGetResourceTest {
	public static void getResource() {
		URL url = ClassGetResourceTest.class.getClass().getResource("/com/fairchild/nestle/cache/default.memory");
		System.out.println(url.getPath());
	}

	public static void main(String[] args) {
		getResource();
	}
}
