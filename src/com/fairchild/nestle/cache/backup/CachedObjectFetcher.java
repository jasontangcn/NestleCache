/*
 * Created on May 20, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fairchild.nestle.cache.backup;

public interface CachedObjectFetcher {
	/*
	 * 很显然objId和CacheableObject一般来说,应该是有关联的.
	 */
	public Object fetch(Object objId);
}
