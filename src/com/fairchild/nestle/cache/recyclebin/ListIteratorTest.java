/*
 * Created on 2004-11-1
 *
 */
package com.fairchild.nestle.cache.recyclebin;

/**
 * @author TomHornson@hotmail.com
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListIteratorTest {
	public static void main(String[] args) {
		ListIterator it = null;
		try {
			List list = new ArrayList();
			list.add("1");
			list.add("2");
			it = list.listIterator();
			// list.add("3");
			it.add("3");
			// itr.remove();
			for (; it.hasNext();) {
				System.out.println(it.next());
				it.remove();
			}

			it = list.listIterator();
			for (; it.hasNext();) {
				System.out.println(it.next());
			}

			/*
			 * Vector v = new Vector(); 
			 * Iterator i = v.iterator(); 
			 * v.add("1");
			 * System.out.println(i.next()); 
			 * i.next(); 
			 * //v.add("3");
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
