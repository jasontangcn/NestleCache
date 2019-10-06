/*
 * Created on 2004-10-31
 *
 */
package com.fruits.nestle.cache.backup;

/**
 * @author TomHornson@hotmail.com
 *
 */
import java.util.*;
import java.io.*;

import com.fruits.nestle.cache.maps.ExtendedLinkedHashMap;

/*
 * 对于同步，我这里对我的策略做一个总体的描述：
 * 这里我对Collection的任何访问不提供同步。
 * 因为 不同步对Cache系统的使用从业务逻辑(非关键)上来说没有太大影响。
 * 而如果同步太多，就违背了Cache用来提高应用性能的初衷。
 * 
 * 当然,我在考虑用并发HashMap来保证线程安全和性能.
 * 
 */
public class DistributedCache {
	/*
	 * 使用ExtendLinkedHasMap，保证缓存最大容量。 
	 * 当objsCached超过规定容量时，会删除最近最少访问的对象。
	 * LinkedHashMap工作原理，刚插入的对象，其迭代顺序为最末，而刚访问的对象，其迭代顺序也为最末。
	 */
	private static Map caches = new HashMap();
	// private Map objectsCached = new ExtendedLinkedHashMap(100, .80f, true);
	private Map objectsCached;

	private volatile long hitted = 0L;
	private volatile long missed = 0L;

	static {
		String filesDir = System.getProperty("config_files_dir");
		if (null == filesDir)
			throw new RuntimeException("Can not get env variant: config_files_dir.");
		File configDir = new File(filesDir);
		File[] configFiles = configDir.listFiles(new FilenameFilter() {
			public boolean accept(File file, String name) {
				if (name.endsWith(".distributed")) {
					return true;
				} else {
					return false;
				}
			}
		});
		if (configFiles.length == 0)
			throw new RuntimeException("Can not get any config file for distributed cache.");

		for (int i = 0; i < configFiles.length; i++) {
			CacheConfig[] configs = DistributedCacheConfigLoader.parseConfigs(configFiles[i]);
			for (int j = 0; j < configs.length; j++) {
				caches.put(configs[j].get_name(), new Cache(configs[j]));
			}
		}
	}

	public static DistributedCache getCache(String name) {
		if (null == name)
			return null;
		return (DistributedCache) caches.get(name);
	}

	public DistributedCache(DistributedCacheConfig config) {
		this.config = config;
		new Thread(new ClearupExpiredObjectsDaemonThread(config.get_clearup_expired_objects_daemonthread_sleeptime())).start();
		objectsCached = config.getMap();
	}

	public DistributedCacheConfig config = null;

	public DistributedCacheConfig getCacheConfigure() {
		return config;
	}


	public void put(Object objId, Object objCached) {
		/*
		 * 不同步，可能导致同key的Entity，虽然几率非常低。 在高规格系统中，需要同步最好使用"并发"HashMap，兼顾线程安全和性能。
		 */
		objectsCached.put(objId, new CachedObject(objCached, config.get_expired_time(), Boolean.parseBoolean(config.get_use_softreference())));
	}

	private void put(Object objId, Object objCached, int expiredMilliseconds) {
		objectsCached.put(objId, new CachedObject(objCached, expiredMilliseconds, Boolean.parseBoolean(config.get_use_softreference())));
	}

	private void put(Object objId, Object objCached, boolean useSoftReference) {
		objectsCached.put(objId, new CachedObject(objCached, config.get_expired_time(), useSoftReference));
	}

	private void put(Object objId, Object objCached, int expiredMilliseconds, boolean useSoftReference) {
		/*
		 * 通过反射检测objCached是否实现了Cacheable。如果它实现了Cacheable，那么对objCached的大小进行控制。
		 * 这里也可以通过反射来判断objCached是否有size()方法，如果有，那么调用size()来对objCached的大小进行控制。
		 * 这种方法更加通用。 不过很显然，这里使用反射对性能会产生多大的影响，还有待考察。
		 */
		if (null != config.get_control_size_of_object_cached()) {
			if (config.get_means_of_calculating_size_of_object_cached().equals("self_help")) {
				boolean flag = true;
				try {
					int size = ((Integer) objCached.getClass().getMethod("size", new Class[0]).invoke(objCached, null)).intValue();
					if (size > config.get_maximal_size_of_object_cached_calculated())
						flag = false;
				} catch (Throwable e) {
					e.printStackTrace();
					flag = false;
				}
				if (!flag)
					return;
			} else if (config.get_means_of_calculating_size_of_object_cached().equals("implement_cacheable")) {
				boolean flag = true;
				try {
					if ((((Cacheable) objCached).size()) > config.get_maximal_size_of_object_cached_calculated())
						flag = false;
					// if(((Cacheable)objCached).size()>CacheConfiguration.CACHEDOBJCacheConfigure*CacheConfiguration.CACHE_OCacheConfigure)
				} catch (Throwable e) {
					e.printStackTrace();
					flag = false;
				}
				if (!flag)
					return;
			} else {
				/*
				 * Reflect有待实现，因为理论上这种方法的性能肯定是不可接受的。
				 */
			}
		}

		/* handle NULL key/NULL value */
		if ((null == config.get_support_null_key()) && (null != objId)) {
			return;
		}
		if ((null == config.get_support_null_value()) && (null == objCached)) {
			return;
		}

		objectsCached.put(objId, new CachedObject(objCached, expiredMilliseconds, useSoftReference));
	}

	/*
	 * 这里，对ojbId有要求：
	 * 我们将使用HashMap(或LinkedHashMap)来存储缓存的对象，所以将作为key的objId，必须重载hashCode和equals函数。
	 */
	/*
	 * 一般来说，cachedObj中应该存在objId的决定性信息，譬如，一个Role对象，它含有主键id(int类型)成员变量，
	 * 而我们希望将id封装为Integer然后作为HashMap的key，那么就不需要单独传进objId对象，用户相对来说方便一些，
	 * 但是，出于通用性的考虑(作为key的objId和缓存对象没有任何关系)，还是将objId单独传入。
	 */
	public Object get(Object objId) {
		CachedObject cacheObject = (CachedObject) objectsCached.get(objId);
		if (null != cacheObject) {
			if ((cacheObject).hasBeenExpired()) {
				/*
				 * 不同步，这里也可能出现问题。 譬如，这里另外一个线程穿插进来，插入一个以objId为key的新Entity。
				 * 在髙规格应用中,需要同步,最好使用"并发"HashMap,兼顾线程安全和性能.
				 */
				objectsCached.remove(objId);
				missed++;

				if (null != config.get_support_interface_fetching_object_cached()) {
					if (null != config.getCachedObjectFether()) {
						Object cachedObj = config.getCachedObjectFether().fetch(objId);
						put(objId, cachedObj);
						return cachedObj;
					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				Object obj = cacheObject.getValue();
				/*
				 * 使用SoftReference，所以有可能已经被回收。 但是,另外一种可能就是：Cacheable本来就为NULL.
				 */
				if ((null == obj) && !cacheObject.cachedObjIsNull()) {
					objectsCached.remove(objId);
					missed++;
					if (null != config.get_support_interface_fetching_object_cached()) {
						if (null != config.getCachedObjectFether()) {
							Object cachedObj = config.getCachedObjectFether().fetch(objId);
							put(objId, cachedObj);
							return cachedObj;
						} else {
							return null;
						}
					} else {
						return null;
					}
				} else {
					hitted++;
					return obj;
				}
			}
		} else {
			missed++;
			if (null != config.get_support_interface_fetching_object_cached()) {
				if (null != config.getCachedObjectFether()) {
					Object cachedObj = config.getCachedObjectFether().fetch(objId);
					put(objId, cachedObj);
					return cachedObj;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}

	public Object remove(Object objId) {
		/*
		 * HashMap允许key，value为NULL，但是我考虑，为了包容更多的应用场景，允许NULL进入HashMap， 包括 NULL
		 * key，NULL value，NULL key + NULL value。
		 */
		CachedObject cacheObject = (CachedObject) objectsCached.remove(objId);
		/*
		 * cacheObject为NULL的情况是不会出现的，因为这需要程序员保证的。
		 */
		if (null != cacheObject) {
			if (!cacheObject.hasBeenExpired()) {
				return cacheObject.getValue();
			} else {
				return null;
			}
		} else {
			missed++;
			return null;
		}
	}

	public boolean contains(Object objId) {
		CachedObject cachedObject = (CachedObject) objectsCached.get(objId);
		if (null != cachedObject) {
			if ((cachedObject).hasBeenExpired()) {
				objectsCached.remove(objId);
				return false;
			} else {
				Object obj = cachedObject.getValue();
				if ((null == obj) && !cachedObject.cachedObjIsNull()) {
					objectsCached.remove(objId);
					return false;
				} else {
					return true;
				}
			}
		} else {
			return false;
		}
	}

	public int size() {
		return objectsCached.size();
	}

	public void clear() {
		objectsCached.clear();
		missed = 0L;
		hitted = 0L;
	}

	public long getHitted() {
		return hitted;
	}

	public long getMissed() {
		return missed;
	}

	public float getHitRate() {
		long total = hitted + missed;
		if (total != 0) {
			return hitted / total;
		} else {
			return 0F;
		}
	}

	public void delayExpiredTime(Object objId, int milliseconds) {
		CachedObject cacheObject = (CachedObject) objectsCached.get(objId);
		if (null != cacheObject) {
			if ((cacheObject).hasBeenExpired()) {
				objectsCached.remove(objId);
			} else {
				Object obj = cacheObject.getValue();
				if ((null == obj) && !cacheObject.cachedObjIsNull()) {
					objectsCached.remove(objId);
				} else {
					cacheObject.delayExpiredTime(milliseconds);
				}
			}
		}
	}

	public void delayAllExpiredTime(int milliseconds) {
		Set keyset = objectsCached.keySet();
		for (Iterator iter = keyset.iterator(); iter.hasNext();) {
			delayExpiredTime(iter.next(), milliseconds);
		}
	}

	/*
	 * 此方法意义有限。 因为每个CacheObject都可以定义SoftReference策略。 
	 * 所以往往没有必要统一。
	 */
	public void unifySoftReferenceStrategy(boolean useSoftReference) {
		Set entrySet = objectsCached.entrySet();
		Iterator iter = entrySet.iterator();
		CachedObject cacheObject = null;
		Map.Entry entry = null;
		for (; iter.hasNext();) {
			entry = (Map.Entry) iter.next();
			cacheObject = (CachedObject) entry.getValue();
			if (!cacheObject.setUseSoftReference(useSoftReference)) {
				iter.remove();
			}
		}
	}

	public void resize(int size) {
		/*
		if (size <= 0) {
			if (CacheConfiguration.CACHE_OCacheConfigure > 0)
				CacheConfiguration.CACHE_OCacheConfigure = 0;
		} else {
			if (CacheConfiguration.CACHE_OCacheConfigure <= 0) {
				if (size >= objectsCached.size()) {
					CacheConfiguration.CACHE_OCacheConfigure = size;
				} else {
					int decreaseCounter = objectsCached.size() - size;
					for (Iterator itr = objectsCached.keySet().iterator(); itr.hasNext();) {
						if (decreaseCounter > 0) {
							itr.remove();
							decreaseCounter--;
						}
					}
				}
			} else {
				if (size >= CacheConfiguration.CACHE_OCacheConfigure) {
					CacheConfiguration.CACHE_OCacheConfigure = size;
				} else {
					if (size >= objectsCached.size()) {
						CacheConfiguration.CACHE_OCacheConfigure = size;
					} else {
						int decreaseCounter = objectsCached.size() - size;
						for (Iterator itr = objectsCached.keySet().iterator(); itr.hasNext();) {
							if (decreaseCounter > 0) {
								itr.remove();
								decreaseCounter--;
							}
						}
					}
				}
			}
		}
    */
		if (size <= 0) {
			if (objectsCached instanceof ExtendedLinkedHashMap) {
				((ExtendedLinkedHashMap) objectsCached).setMaxinalCapability(Integer.MAX_VALUE);
			}
		} else {
			if (objectsCached instanceof ExtendedLinkedHashMap) {
				ExtendedLinkedHashMap tmp = ((ExtendedLinkedHashMap) objectsCached);
				if (size > tmp.size()) {
					tmp.setMaxinalCapability(size);
				} else {
					int decreaseCounter = objectsCached.size() - size;
					for (Iterator itr = objectsCached.keySet().iterator(); itr.hasNext();) {
						if (decreaseCounter > 0) {
							itr.remove();
							decreaseCounter--;
						}
					}
					tmp.setMaxinalCapability(size);
				}
			}
		}
	}

	private final class ClearupExpiredObjectsDaemonThread implements Runnable {
		final int sleepTimeInMilliseconds;

		ClearupExpiredObjectsDaemonThread(int sleepTimeInMilliseconds) {
			super();
			this.sleepTimeInMilliseconds = sleepTimeInMilliseconds;
		}

		public void run() {
			for (;;) {
				try {
					/*
					 * itr.hasNext()条件首先判断
					 */
					Set entrys = objectsCached.entrySet();
					for (Iterator iter = entrys.iterator(); iter.hasNext();) {
						// Object objId = iter.next();
						// Object objectCached = Cache.objectsCached.get(objId);
						/*
						 * Iterator的fast-fail特性，在non synchronized concurrent modification的情况，不保证一定产生作用。
						 * Fail-fast iterators throw ConcurrentModificationException on a best-effort basis.
						 * 因此这里，我们说remove可能出现太多种意外情况。
						 */
						if (((CachedObject)(((Map.Entry) iter.next()).getValue())).hasBeenExpired()) {
							/* objectsCached.remove(objId) *//* 陷阱  */
							iter.remove();
						}
						// break;
					}
					Thread.sleep(sleepTimeInMilliseconds);
					/*
					 * 线程打断之后，是否还需要继续工作？
					 * 这个问题，有待仔细斟酌。
					 * 无论什么异常发生都不能因为这个异常使整个系统停止工作。
					 */
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
		}
	}

}
