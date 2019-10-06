/*
 * Created on May 25, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fruits.nestle.cache.test;

import java.util.*;
import EDU.oswego.cs.dl.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
	private static long startTime;

	public static void main(String[] args) {
		Map map = new HashMap();
		// Map map = Collections.synchronizedMap(new HashMap());
		for (int i = 0; i < 1000; i++) {
			map.put(String.valueOf(i), "#" + String.valueOf(i));
		}

		CyclicBarrier barrier = new CyclicBarrier(1000, new CalculateCostTime());
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			new HashMapReadThread(String.valueOf(i), map, barrier).start();
		}

	}

	private static class HashMapReadThread extends Thread {
		private String key;
		private Map map;
		private CyclicBarrier barrier;

		public HashMapReadThread(String key, Map map, CyclicBarrier barrier) {
			this.key = key;
			this.map = map;
			this.barrier = barrier;
		}

		public void run() {
			map.get(key);
			try {
				barrier.barrier();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	private static class CalculateCostTime implements Runnable {
		public void run() {
			System.out.println(System.currentTimeMillis() - startTime);
		}
	}
}
