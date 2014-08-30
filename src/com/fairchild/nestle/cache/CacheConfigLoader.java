/*
 * Created on Jun 2, 2005
 * Author: TomHornson(at)hotmail.com
 */
package com.fairchild.nestle.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

public class CacheConfigLoader {
	static ArrayList cacheNames = new ArrayList();
	static String cache_synchronized_strategy = null;

	static String control_size_of_object_cached = null;
	static int maximal_size_of_object_cached = Integer.MIN_VALUE;
	static float objectcached_outofsize_rate = Float.MIN_VALUE;
	static String means_of_calculating_size_of_object_cached = null;

	static String control_size_of_cache = null;
	static int maximal_size_of_cache = Integer.MIN_VALUE;
	static float cache_overload_rate = Float.MIN_VALUE;

	static String control_capability_of_cache = null;
	static int maximal_capability = Integer.MIN_VALUE;

	static String customize_hashmap = null;
	static int hashmap_initial_capability = Integer.MIN_VALUE;
	static float hashmap_loadfactor = Float.MIN_VALUE;
	static String hashmap_accessOrder = null;

	static int expired_time = Integer.MIN_VALUE;
	static String use_softreference = null;
	static int clearup_expired_objects_daemonthread_sleeptime = Integer.MIN_VALUE;

	static String support_interface_fetching_object_cached = null;
	static String impl_class_fetching_object_cached = null;
	static String support_null_key = null;
	static String support_null_value = null;

	public static CacheConfig[] parseConfigs(File f) {
		File file = f;
		InputStream is = null;
		Properties props = new Properties();
		try {
			is = new FileInputStream(file);
			props.load(is);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		} finally {
			try {
				if (null != is)
					is.close();
			} catch (IOException ioe) {
			}
		}
		/* Get cache names */
		String cache_names_string = null;
		if ((null == props.getProperty("cache_names")) || (props.getProperty("cache_names").length() == 0)) {
			throw new RuntimeException("No cache name has been configured.");
		} else {
			cache_names_string = props.getProperty("cache_names");
		}
		StringTokenizer st = new StringTokenizer(cache_names_string, ",");
		while (st.hasMoreTokens()) {
			cacheNames.add(st.nextToken());
		}

		/* Get cache_synchronized_strategy */
		if (match(props.getProperty("cache_synchronized_strategy"), new String[] { "none", "read", "write", "application_level" })) {
			cache_synchronized_strategy = props.getProperty("cache_synchronized_strategy");
		} else {
			throw new RuntimeException("cache_synchronized_strategy is illegal.");
		}

		if (isTrue(props.getProperty("control_size_of_object_cached"))) {
			control_size_of_object_cached = props.getProperty("control_size_of_object_cached");
			try {
				maximal_size_of_object_cached = Integer.parseInt(props.getProperty("maximal_size_of_object_cached"));
			} catch (NumberFormatException nfe) {
				throw new RuntimeException("maximal_size_of_object_cached is illegal.");
			}
			try {
				objectcached_outofsize_rate = Float.parseFloat(props.getProperty("objectcached_outofsize_rate"));
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				throw new RuntimeException("objectcached_outofsize_rate is illegal.");
			}
			if (!match(props.getProperty("means_of_calculating_size_of_object_cached"), new String[] { "reflect", "implement_cacheable", "self_help" }))
				throw new RuntimeException("means_of_calculating_size_of_object_cached is illegal.");
		}

		if (isTrue(props.getProperty("control_size_of_cache"))) {
			control_size_of_cache = props.getProperty("control_size_of_cache");
			try {
				maximal_size_of_cache = Integer.parseInt(props.getProperty("maximal_size_of_cache"));
			} catch (NumberFormatException nfe) {
				throw new RuntimeException("maximal_size_of_cache is illegal.");
			}
			try {
				cache_overload_rate = Float.parseFloat(props.getProperty("cache_overload_rate"));
			} catch (NumberFormatException nfe) {
				throw new RuntimeException("cache_overload_rate is illegal.");
			}
		}

		if (isTrue(props.getProperty("control_capability_of_cache"))) {
			control_capability_of_cache = props.getProperty("control_capability_of_cache");
			try {
				maximal_capability = Integer.parseInt(props.getProperty("maximal_capability"));
			} catch (NumberFormatException nfe) {
				throw new RuntimeException("maximal_capability is illegal.");
			}
		}

		if (isTrue(props.getProperty("customize_hashmap"))) {
			customize_hashmap = props.getProperty("customize_hashmap");
			try {
				hashmap_initial_capability = Integer.parseInt(props.getProperty("hashmap_initial_capability"));
			} catch (NumberFormatException nfe) {
				throw new RuntimeException("hashmap_initial_capability is illegal.");
			}
			try {
				hashmap_loadfactor = Float.parseFloat(props.getProperty("hashmap_loadfactor"));
			} catch (NumberFormatException nfe) {
				throw new RuntimeException("hashmap_loadfactor is illegal.");
			}
			if (isBoolean(props.getProperty("hashmap_accessOrder"))) {
				hashmap_accessOrder = props.getProperty("hashmap_accessOrder");
			} else {
				throw new RuntimeException("hashmap_accessOrder is illegal.");
			}
		}

		try {
			expired_time = Integer.parseInt(props.getProperty("expired_time"));
		} catch (NumberFormatException nfe) {
			throw new RuntimeException("expired_time is illegal.");
		}
		
		if (isBoolean(props.getProperty("use_softreference"))) {
			use_softreference = props.getProperty("use_softreference");
		} else {
			throw new RuntimeException("use_softreference is illegal.");
		}
		
		try {
			clearup_expired_objects_daemonthread_sleeptime = Integer.parseInt(props.getProperty("clearup_expired_objects_daemonthread_sleeptime"));
		} catch (NumberFormatException nfe) {
			throw new RuntimeException("clearup_expired_objects_daemonthread_sleeptime is illegal.");
		}
		
		if (isTrue(props.getProperty("support_interface_fetching_object_cached"))) {
			support_interface_fetching_object_cached = props.getProperty("support_interface_fetching_object_cached");
			impl_class_fetching_object_cached = props.getProperty("support_interface_fetching_object_cached");
			try {
				CacheConfigLoader.class.getClassLoader().loadClass(impl_class_fetching_object_cached).newInstance();
			} catch (Exception e) {
				throw new RuntimeException("impl_class_fetching_object_cached is illegal.\n" + e.getMessage());
			}
		}
		if (isBoolean(props.getProperty("support_null_key"))) {
			support_null_key = props.getProperty("support_null_key");
		} else {
			throw new RuntimeException("support_null_key is illegal.");
		}
		if (isBoolean(props.getProperty("support_null_value"))) {
			support_null_value = props.getProperty("support_null_value");
		} else {
			throw new RuntimeException("support_null_value is illegal.");
		}

		parserExtendedConfigs(props);
		return create();
	}

	public static void parserExtendedConfigs(Properties props) {

	}

	public static CacheConfig[] create() {
		CacheConfig[] configs = new CacheConfig[cacheNames.size()];

		for (int i = 0; i < configs.length; i++) {
			CacheConfig cc = new CacheConfig();
			cc.set_name((String) cacheNames.get(i));
			cc.set_cache_synchronized_strategy(cache_synchronized_strategy);

			cc.set_control_size_of_object_cached(control_size_of_object_cached);
			cc.set_maximal_size_of_object_cached(maximal_size_of_object_cached);
			cc.set_objectcached_outofsize_rate(objectcached_outofsize_rate);
			cc.set_means_of_calculating_size_of_object_cached(means_of_calculating_size_of_object_cached);
			// calculate acture maximal_size_of_object_cached
			cc.set_maximal_size_of_object_cached_calculated();
			
			if("True".equals(cc.get_support_interface_fetching_object_cached()))
			  cc.setCachedObjectFether();

			cc.set_control_size_of_cache(control_size_of_cache);
			cc.set_maximal_size_of_cache(maximal_size_of_cache);
			cc.set_cache_overload_rate(cache_overload_rate);

			cc.set_control_capability_of_cache(control_capability_of_cache);
			cc.set_maximal_capability(maximal_capability);

			cc.set_customize_hashmap(customize_hashmap);
			cc.set_hashmap_accessOrder(hashmap_accessOrder);
			cc.set_hashmap_initial_capability(hashmap_initial_capability);
			cc.set_hashmap_loadfactor(hashmap_loadfactor);

			cc.set_expired_time(expired_time);
			cc.set_clearup_expired_objects_daemonthread_sleeptime(clearup_expired_objects_daemonthread_sleeptime);

			cc.set_support_interface_fetching_object_cached(support_interface_fetching_object_cached);
			cc.set_impl_class_fetching_object_cached(impl_class_fetching_object_cached);

			cc.set_support_null_key(support_null_key);
			cc.set_support_null_value(support_null_value);
			cc.set_use_softreference(use_softreference);

			configs[i] = cc;
		}
		return configs;

	}

	public static boolean isBoolean(String s) {
		if ((null != s) && (s.equals("true") || s.equals("false"))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isTrue(String s) {
		if ((null != s) && s.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean match(String s, String[] arr) {
		if ((null == s) || (null == arr))
			return false;
		boolean flag = false;
		for (int i = 0; i < arr.length; i++) {
			if (s.equals(arr[i])) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}
