package com.fruits.nestle.cache.recyclebin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestSyncMT {
	public static int n = 10000;
	public static long begin = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassA a = new ClassA();
		Map map = new HashMap();
		Map syncMap = Collections.synchronizedMap(new HashMap());

		Map mp = syncMap;
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 5; j++) {
				map.put(new Person(i, "#" + j), new Person(i, "#" + j));
			}
		}

		a.setMap(mp);

		begin = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			new Thread(a).start();
		}
		
		System.out.println(a.counter);
	}

	private static class ClassA implements Runnable {
		public volatile int counter = n;
		private Map map;

		public void setMap(Map map) {
			this.map = map;
		}

		private Object key;

		public void setKey(Object key) {
			this.key = key;
		}

		public void run() {
			Random ageGen = new Random();
			Random nameGen = new Random();
			int age = ageGen.nextInt(1000);
			int name = nameGen.nextInt(5);

			map.get(new Person(age, "#" + name));

			counter--;
			if (counter == 0) {
				System.out.println(System.currentTimeMillis() - begin);
			}
		}
	}

	private static class Person {
		private int age;
		private String name;

		public Person(int age, String name) {
			this.age = age;
			this.name = name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public int hashCode() {
			return this.age;
		}

		public boolean equals(Object obj) {
			if ((null != obj) && (obj instanceof Person)) {
				String thatName = ((Person) obj).getName();
				return (null == thatName) ? (null == this.name) : thatName.equals(this.name);
			} else {
				return false;
			}
		}
	}
}
