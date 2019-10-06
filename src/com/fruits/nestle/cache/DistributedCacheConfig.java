/*
 * Created on Jun 18, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fruits.nestle.cache;

import java.util.Collections;
import java.util.Map;

import org.jgroups.ChannelException;

import com.fruits.nestle.cache.maps.ConcurrentReaderHashMap;
import com.fruits.nestle.cache.maps.DistributedExtendedLinkedHashMap;
import com.fruits.nestle.cache.maps.DistributedHashMap;

public class DistributedCacheConfig extends CacheConfig {
	String cache_properties_string = null;
	String channel_factory_class = null;
	String cache_persistent = null;
	int state_timeout = Integer.MIN_VALUE;

	public String get_cache_persistent() {
		return cache_persistent;
	}

	public void set_cache_persistent(String cache_persistent) {
		this.cache_persistent = cache_persistent;
	}

	public String get_cache_properties_string() {
		return cache_properties_string;
	}

	public void set_cache_properties_string(String cache_properties_string) {
		this.cache_properties_string = cache_properties_string;
	}

	public String get_channel_factory_class() {
		return channel_factory_class;
	}

	public void set_channel_factory_class(String channel_factory_class) {
		this.channel_factory_class = channel_factory_class;
	}

	public int get_state_timeout() {
		return state_timeout;
	}

	public void set_state_timeout(int state_timeout) {
		this.state_timeout = state_timeout;
	}

	public Map getMap() {
		try {
			if ((null != this.control_capability_of_cache) || (null != this.control_size_of_cache)) {
				if (this.cache_synchronized_strategy.equals("none")) {
					if (this.customize_hashmap != null) {
						return new DistributedExtendedLinkedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout, this.hashmap_initial_capability, this.hashmap_loadfactor, Boolean.parseBoolean(this.hashmap_accessOrder), this.maximal_capability);
					} else {
						/*
						 * 这里，首先假设control_capability_of_cache始终为true。
						 */
						return new DistributedExtendedLinkedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout, this.maximal_capability, 1, true, Integer.MAX_VALUE);
					}
				} else if (this.cache_synchronized_strategy.equals("write")) {
					/*
					 * 这里，有待实现CurrentReaderLinkedHashMap。
					 */
					if (null != this.customize_hashmap) {
						return Collections.synchronizedMap(new DistributedExtendedLinkedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout, this.hashmap_initial_capability, this.hashmap_loadfactor, Boolean.parseBoolean(this.hashmap_accessOrder), this.maximal_capability));
					} else {
						return Collections.synchronizedMap(new DistributedExtendedLinkedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout, this.maximal_capability, 1, true, Integer.MAX_VALUE));
					}
				} else if (this.cache_synchronized_strategy.equals("read")) {
					if (null != this.customize_hashmap) {
						return Collections.synchronizedMap(new DistributedExtendedLinkedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout, this.hashmap_initial_capability, this.hashmap_loadfactor, Boolean.parseBoolean(this.hashmap_accessOrder), this.maximal_capability));
					} else {
						return Collections.synchronizedMap(new DistributedExtendedLinkedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout, this.maximal_capability, 1, true, Integer.MAX_VALUE));
					}
				} else {
					/*
					 * (this._cache_synchronized_strategy.equals("application_level"))
					 * 
					 * 这里，有待实现事务级别的LinkedHashMap。
					 */
					if (null != this.customize_hashmap) {
						return Collections.synchronizedMap(new DistributedExtendedLinkedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout, this.hashmap_initial_capability, this.hashmap_loadfactor, Boolean.parseBoolean(this.hashmap_accessOrder), this.maximal_capability));
					} else {
						return Collections.synchronizedMap(new DistributedExtendedLinkedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout, this.maximal_capability, 1, true, Integer.MAX_VALUE));
					}
				}
			} else {
				if (this.cache_synchronized_strategy.equals("none")) {
					if (this.customize_hashmap != null) {
						/*
						 * 这里，目前的实现，当用户希望自定义Map的时候，必须三个参数都设置。
						 * 而事实上，有些情况只需要设置initial_capability。
						 * 这个问题，有待进一步处理。
						 */
						return new DistributedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout);
					} else {
						return new DistributedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout);
					}
				} else if (this.cache_synchronized_strategy.equals("write")) {
					if (this.customize_hashmap != null) {
						return new ConcurrentReaderHashMap(this.hashmap_initial_capability, this.hashmap_loadfactor);
					} else {
						return new ConcurrentReaderHashMap(this.maximal_capability, 1);
					}
				} else if (this.cache_synchronized_strategy.equals("read")) {
					if (this.customize_hashmap != null) {
						return Collections.synchronizedMap(new DistributedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout));
					} else {
						return Collections.synchronizedMap(new DistributedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout));
					}
				} else {
					if (null != this.customize_hashmap) {
						/*
						 * (this._cache_synchronized_strategy.equals("application_level"))
						 * 这里，有待实现事务级别的HashMap。
						 */
						return Collections.synchronizedMap(new DistributedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout));
					} else {
						return Collections.synchronizedMap(new DistributedHashMap(this.name, null, this.cache_properties_string, Boolean.parseBoolean(this.cache_persistent), (long) this.state_timeout));
					}
				}
			}
		} catch (ChannelException e) {
			throw new RuntimeException(e);
		}
	}
}
