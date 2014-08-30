/*
 * Created on Jun 18, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fairchild.nestle.cache;

import java.util.*;

public class DistributedCacheConfigLoader extends CacheConfigLoader {
	static String cache_properties_string = null;
	static String channel_factory_class = null;
	static String cache_persistent = null;
	static int state_timeout = Integer.MIN_VALUE;

	public static void parserExtendedConfigs(Properties props) {
		String cache_properties_string = null;
		if ((null == props.getProperty("cache_properties_string")) || (props.getProperty("cache_properties_string").length() == 0)) {
			throw new RuntimeException("cache_properties_string is not set.");
		} else {
			cache_properties_string = props.getProperty("cache_properties_string");
		}

		String channel_factory_class = null;
		if ((null == props.getProperty("channel_factory_class")) || (props.getProperty("channel_factory_class").length() == 0)) {
			throw new RuntimeException("channel_factory_class is not set.");
		} else {
			try {
				CacheConfigLoader.class.getClassLoader().loadClass(props.getProperty("channel_factory_class")).newInstance();
			} catch (Exception e) {
				throw new RuntimeException("channel_factory_class is illegal." + "\n" + e.getMessage());
			}
			cache_properties_string = props.getProperty("channel_factory_class");
		}

		if (isBoolean(props.getProperty("cache_persistent"))) {
			cache_persistent = props.getProperty("cache_persistent");
		} else {
			throw new RuntimeException("cache_persistent is illegal.");
		}
		try {
			state_timeout = Integer.parseInt(props.getProperty("state_timeout"));
		} catch (NumberFormatException nfe) {
			throw new RuntimeException("state_timeout is illegal.");
		}
	}

	public static CacheConfig[] create() {
		DistributedCacheConfig[] configs = new DistributedCacheConfig[cacheNames.size()];
		for (int i = 0; i < configs.length; i++) {
			DistributedCacheConfig dcc = new DistributedCacheConfig();
			dcc.set_name((String) cacheNames.get(i));
			dcc.set_cache_synchronized_strategy(cache_synchronized_strategy);

			dcc.set_control_size_of_object_cached(control_size_of_object_cached);
			dcc.set_maximal_size_of_object_cached(maximal_size_of_object_cached);
			dcc.set_objectcached_outofsize_rate(objectcached_outofsize_rate);
			dcc.set_means_of_calculating_size_of_object_cached(means_of_calculating_size_of_object_cached);
			// calculate acture maximal_size_of_object_cached
			dcc.set_maximal_size_of_object_cached_calculated();
			dcc.setCachedObjectFether();

			dcc.set_control_size_of_cache(control_size_of_cache);
			dcc.set_maximal_size_of_cache(maximal_size_of_cache);
			dcc.set_cache_overload_rate(cache_overload_rate);

			dcc.set_control_capability_of_cache(control_capability_of_cache);
			dcc.set_maximal_capability(maximal_capability);

			dcc.set_customize_hashmap(customize_hashmap);
			dcc.set_hashmap_accessOrder(hashmap_accessOrder);
			dcc.set_hashmap_initial_capability(hashmap_initial_capability);
			dcc.set_hashmap_loadfactor(hashmap_loadfactor);

			dcc.set_expired_time(expired_time);
			dcc.set_clearup_expired_objects_daemonthread_sleeptime(clearup_expired_objects_daemonthread_sleeptime);

			dcc.set_support_interface_fetching_object_cached(support_interface_fetching_object_cached);
			dcc.set_impl_class_fetching_object_cached(impl_class_fetching_object_cached);

			dcc.set_support_null_key(support_null_key);
			dcc.set_support_null_value(support_null_value);
			dcc.set_use_softreference(use_softreference);

			dcc.set_channel_factory_class(channel_factory_class);
			dcc.set_cache_properties_string(cache_properties_string);
			dcc.set_cache_persistent(cache_persistent);
			dcc.set_state_timeout(state_timeout);

			configs[i] = dcc;
		}
		return configs;

	}
}
