/*
 * Created on 2004-10-31
 *
 */
package com.fruits.nestle.cache;

/**
 * @author TomHornson@hotmail.com
 */
/*
 * 这里,对于缓存的对象,需要继承Cacheable,看上去不是一个很好的方案. 等下,改进之.
 */
public interface Cacheable {
	public int size();
}
