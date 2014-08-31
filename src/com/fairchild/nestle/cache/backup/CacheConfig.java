/*
 * Created on May 31, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fairchild.nestle.cache.backup;

import java.util.*;

import com.fairchild.nestle.cache.maps.ConcurrentReaderHashMap;
import com.fairchild.nestle.cache.maps.ExtendedLinkedHashMap;

public class CacheConfig {
	String name = null;

	String cache_synchronized_strategy = null;

	String control_size_of_object_cached = null;
	int maximal_size_of_object_cached = Integer.MIN_VALUE;
	float objectcached_outofsize_rate = Float.MIN_VALUE;
	String means_of_calculating_size_of_object_cached = null;

	String control_size_of_cache = null;
	int maximal_size_of_cache = Integer.MIN_VALUE;
	float cache_overload_rate = Float.MIN_VALUE;

	String control_capability_of_cache = null;
	int maximal_capability = Integer.MIN_VALUE;

	String customize_hashmap = null;
	int hashmap_initial_capability = Integer.MIN_VALUE;
	float hashmap_loadfactor = Float.MIN_VALUE;
	String hashmap_accessOrder = null;

	int expired_time = Integer.MIN_VALUE;
	String use_softreference = null;
	int clearup_expired_objects_daemonthread_sleeptime = Integer.MIN_VALUE;

	String support_interface_fetching_object_cached = null;
	String impl_class_fetching_object_cached = null;
	String support_null_key = null;
	String support_null_value = null;

	float maximal_size_of_object_cached_calculated = Float.MIN_VALUE;
	CachedObjectFetcher cachedObjectFeather = null;

	public String get_name() {
		return name;
	}

	public void set_name(String name) {
		this.name = name;
	}

	public String get_cache_synchronized_strategy() {
		return cache_synchronized_strategy;
	}

	public void set_cache_synchronized_strategy(String cache_synchronized_strategy) {
		this.cache_synchronized_strategy = cache_synchronized_strategy;
	}

	/*---------------------------------------------------------------------------*/
	public String get_control_size_of_object_cached() {
		return control_size_of_object_cached;
	}

	public void set_control_size_of_object_cached(String control_size_of_object_cached) {
		this.control_size_of_object_cached = control_size_of_object_cached;
	}

	public int get_maximal_size_of_object_cached() {
		return maximal_size_of_object_cached;
	}

	public void set_maximal_size_of_object_cached(int maximal_size_of_object_cached) {
		this.maximal_size_of_object_cached = maximal_size_of_object_cached;
	}

	public float get_objectcached_outofsize_rate() {
		return objectcached_outofsize_rate;
	}

	public void set_objectcached_outofsize_rate(float objectcached_outofsize_rate) {
		this.objectcached_outofsize_rate = objectcached_outofsize_rate;
	}

	public String get_means_of_calculating_size_of_object_cached() {
		return means_of_calculating_size_of_object_cached;
	}

	public void set_means_of_calculating_size_of_object_cached(String means_of_calculating_size_of_object_cached) {
		this.means_of_calculating_size_of_object_cached = means_of_calculating_size_of_object_cached;
	}

	/*---------------------------------------------------------------------------*/
	public String get_control_size_of_cache() {
		return control_size_of_cache;
	}

	public void set_control_size_of_cache(String control_size_of_cache) {
		this.control_size_of_cache = control_size_of_cache;
	}

	public int get_maximal_size_of_cache() {
		return maximal_size_of_cache;
	}

	public void set_maximal_size_of_cache(int maximal_size_of_cache) {
		this.maximal_size_of_cache = maximal_size_of_cache;
	}

	public float get_cache_overload_rate() {
		return cache_overload_rate;
	}

	public void set_cache_overload_rate(float cache_overload_rate) {
		this.cache_overload_rate = cache_overload_rate;
	}

	public String get_control_capability_of_cache() {
		return control_capability_of_cache;
	}

	public void set_control_capability_of_cache(String control_capability_of_cache) {
		this.control_capability_of_cache = control_capability_of_cache;
	}

	public int get_maximal_capability() {
		return maximal_capability;
	}

	public void set_maximal_capability(int maximal_capability) {
		this.maximal_capability = maximal_capability;
	}

	/*---------------------------------------------------------------------------*/
	public String get_customize_hashmap() {
		return customize_hashmap;
	}

	public void set_customize_hashmap(String customize_hashmap) {
		this.customize_hashmap = customize_hashmap;
	}

	public int get_hashmap_initialCapability() {
		return hashmap_initial_capability;
	}

	public void set_hashmap_initial_capability(int capability) {
		hashmap_initial_capability = capability;
	}

	public float get_hashmap_loadfactor() {
		return hashmap_loadfactor;
	}

	public void set_hashmap_loadfactor(float hashmap_loadfactor) {
		this.hashmap_loadfactor = hashmap_loadfactor;
	}

	public String get_hashmap_accessOrder() {
		return hashmap_accessOrder;
	}

	public void set_hashmap_accessOrder(String order) {
		hashmap_accessOrder = order;
	}

	/*---------------------------------------------------------------------------*/
	public int get_expired_time() {
		return expired_time;
	}

	public void set_expired_time(int expired_time) {
		this.expired_time = expired_time;
	}

	public String get_use_softreference() {
		return use_softreference;
	}

	public void set_use_softreference(String use_softreference) {
		this.use_softreference = use_softreference;
	}

	public int get_clearup_expired_objects_daemonthread_sleeptime() {
		return clearup_expired_objects_daemonthread_sleeptime;
	}

	public void set_clearup_expired_objects_daemonthread_sleeptime(int clearup_expired_objects_daemonthread_sleeptime) {
		this.clearup_expired_objects_daemonthread_sleeptime = clearup_expired_objects_daemonthread_sleeptime;
	}

	/*---------------------------------------------------------------------------*/
	public String get_support_interface_fetching_object_cached() {
		return support_interface_fetching_object_cached;
	}

	public void set_support_interface_fetching_object_cached(String _support_interface_fetching_object_cached) {
		this.support_interface_fetching_object_cached = _support_interface_fetching_object_cached;
	}

	public String get_impl_class_fetching_object_cached() {
		return impl_class_fetching_object_cached;
	}

	public void set_impl_class_fetching_object_cached(String _impl_class_fetching_object_cached) {
		this.impl_class_fetching_object_cached = _impl_class_fetching_object_cached;
	}

	/*---------------------------------------------------------------------------*/
	public String get_support_null_key() {
		return support_null_key;
	}

	public void set_support_null_key(String _support_null_key) {
		this.support_null_key = _support_null_key;
	}

	public String get_support_null_value() {
		return support_null_value;
	}

	public void set_support_null_value(String _support_null_value) {
		this.support_null_value = _support_null_value;
	}

	/*-----------------------------------------------------------------------------*/
	public float get_maximal_size_of_object_cached_calculated() {
		return this.maximal_size_of_object_cached_calculated;
	}

	public void set_maximal_size_of_object_cached_calculated() {
		if (this.control_size_of_cache != null) {
			float calculate_value = Float.MIN_VALUE;
			if (this.control_size_of_cache != null) {
				calculate_value = this.maximal_size_of_cache * this.objectcached_outofsize_rate;
			}
			this.maximal_size_of_object_cached_calculated = Math.min(this.maximal_size_of_object_cached, calculate_value);
		}
	}

	public CachedObjectFetcher getCachedObjectFether() {
		return this.cachedObjectFeather;
	}

	public void setCachedObjectFether() {
		try {
			cachedObjectFeather = (CachedObjectFetcher)CacheConfigLoader.class.getClassLoader().loadClass(this.impl_class_fetching_object_cached).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("impl_class_fetching_object_cached can't be initialized." + "\n" + e.getMessage());
		}
	}

	/*-----------------------------------------------------------------------------*/
	public Map getMap() {
		if ((null != this.control_capability_of_cache) || (null != this.control_size_of_cache)) {
			if (this.cache_synchronized_strategy.equals("none")) {
				if (null != this.customize_hashmap) {
					return new ExtendedLinkedHashMap(this.hashmap_initial_capability, this.hashmap_loadfactor, Boolean.parseBoolean(this.hashmap_accessOrder), this.maximal_capability);
				} else {
					/*
					 * 这里首先假设control_capability_of_cache始终为true。
					 */
					return new ExtendedLinkedHashMap(this.maximal_capability, 1, true, Integer.MAX_VALUE);
				}
			} else if (this.cache_synchronized_strategy.equals("write")) {
				/*
				 * 这里，有待实现CurrentReaderLinkedHashMap。
				 */
				if (null != this.customize_hashmap) {
					return Collections.synchronizedMap(new ExtendedLinkedHashMap(this.hashmap_initial_capability, this.hashmap_loadfactor, Boolean.parseBoolean(this.hashmap_accessOrder), this.maximal_capability));
				} else {
					return Collections.synchronizedMap(new ExtendedLinkedHashMap(this.maximal_capability, 1, true, Integer.MAX_VALUE));
				}
			} else if (this.cache_synchronized_strategy.equals("read")) {
				if (null != this.customize_hashmap) {
					return Collections.synchronizedMap(new ExtendedLinkedHashMap(this.hashmap_initial_capability, this.hashmap_loadfactor, Boolean.parseBoolean(this.hashmap_accessOrder), this.maximal_capability));
				} else {
					return Collections.synchronizedMap(new ExtendedLinkedHashMap(this.maximal_capability, 1, true, Integer.MAX_VALUE));
				}
			} else {
				/*
				 * (this._cache_synchronized_strategy.equals("application_level"))
				 * 
				 * 这里，有待实现事务级别的LinkedHashMap。
				 */
				if (this.customize_hashmap != null) {
					return Collections.synchronizedMap(new ExtendedLinkedHashMap(this.hashmap_initial_capability, this.hashmap_loadfactor, Boolean.parseBoolean(this.hashmap_accessOrder), this.maximal_capability));
				} else {
					return Collections.synchronizedMap(new ExtendedLinkedHashMap(this.maximal_capability, 1, true, Integer.MAX_VALUE));
				}
			}
		} else {
			if (this.cache_synchronized_strategy.equals("none")) {
				if (null != this.customize_hashmap) {
					/*
					 * 这里，目前的实现，当用户希望自定义Map的时候，必须三个参数都设置。
					 * 而事实上，有些情况只需要设置initial_capability。
					 * 这个问题，有待进一步处理。
					 */
					return new HashMap(this.hashmap_initial_capability, this.hashmap_loadfactor);
				} else {
					return new HashMap(this.maximal_capability, 1);
				}
			} else if (this.cache_synchronized_strategy.equals("write")) {
				if (null != this.customize_hashmap) {
					return new ConcurrentReaderHashMap(this.hashmap_initial_capability, this.hashmap_loadfactor);
				} else {
					return new ConcurrentReaderHashMap(this.maximal_capability, 1);
				}
			} else if (this.cache_synchronized_strategy.equals("read")) {
				if (null != this.customize_hashmap) {
					return Collections.synchronizedMap(new HashMap(this.hashmap_initial_capability, this.hashmap_loadfactor));
				} else {
					return Collections.synchronizedMap(new HashMap(this.maximal_capability, 1));
				}
			} else {
				if (null != this.customize_hashmap) {
					/*
					 * (this._cache_synchronized_strategy.equals("application_level"))
					 * 这里，有待实现事务级别的HashMap。
					 */
					return Collections.synchronizedMap(new HashMap(this.hashmap_initial_capability, this.hashmap_loadfactor));
				} else {
					return Collections.synchronizedMap(new HashMap(this.maximal_capability, 1));
				}
			}
		}
	}
}
