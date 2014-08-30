/*
 * Created on 2004-11-12
 *
 */
package com.fairchild.nestle.cache.recyclebin;

/**
 * @author TomHornson@hotmail.com
 *
 */
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class HashMapIteratorFastFailTestcase {
	public static void main(String[] args) {
		Map map = new LinkedHashMap(20, .80f, true);
		map.put("1", "TomHornson1");
		map.put("2", "TomHornson2");
		map.put("3", "TomHornson3");
		map.put("4", "TomHornson4");
		System.out.println(map.get("3"));
		System.out.println(map.size());
		/*
		 * for(Iterator it = map.keySet().iterator(); it.hasNext();) { 
		 *   String key = (String)it.next(); 
		 *   if(key.equals("2"))
		 *     //map.remove(key); 
		 *     it.remove(); 
		 * }
		 */
		System.out.println(map);
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			// System.out.println(itr.next());
			/*
			 * itr.next(); itr.remove();
			 */if (key.equals("2"))
				 it.remove();
			// System.out.println(itr.next());
		}
		System.out.println(map);
		System.out.println(map.get("1"));
		System.out.println(map);
	}
}
