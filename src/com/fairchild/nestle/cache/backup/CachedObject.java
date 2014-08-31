/*
 * Created on 2004-10-31
 *
 */
package com.fairchild.nestle.cache.backup;

/**
 * @author TomHornson@hotmail.com
 */
import java.lang.ref.SoftReference;
import java.util.Calendar;
import java.util.Date;
import java.io.*;

/*
 * 用户可以extends CacheObject，覆写hasExpired等缺省实现。
 */
public class CachedObject implements Serializable {
	private Object objId;
	private Object cachedObj;
	private boolean cachedObjIsNull;
	private Date expiredTime;
	private boolean useSoftReference;

	/*
	 * 这里，对ojbId有要求：
	 * 我们将使用HashMap(或LinkedHashMap)来存储缓存的对象，所以将作为key的objId，必须重载hashCode和equals函数。
	 */
	/*
	 * 一般来说，cachedObj中应该存在objId的决定性信息，譬如，一个Role对象，它含有主键id(int类型)成员变量，
	 * 而我们希望将id封装为Integer然后作为HashMap的key，那么就不需要单独传进objId对象，用户相对来说方便一些，
	 * 但是，出于通用性的考虑(作为key的objId和缓存对象没有任何关系)，还是将objId单独传入。
	 */
	/*
	 * secondsExpired表示对象存活的多少秒，但是对于对象存活时间往往有更高精度要求，譬如用毫秒来计量，所以扩展的时候
	 * 这里我们会照顾到存活时间以毫秒计量的应用。 [2005-5-20] expiredMilliseconds：毫秒计量
	 */
	public CachedObject(Object cachedObj, int expiredMilliseconds, boolean useSoftReference) {
		if (null == cachedObj)
			cachedObjIsNull = true;
		if (useSoftReference) {
			this.cachedObj = new SoftReference(cachedObj);
			this.useSoftReference = useSoftReference;
		} else {
			this.cachedObj = cachedObj;
		}

		if (expiredMilliseconds > 0) {
			Calendar cld = Calendar.getInstance();
			cld.setTime(new Date());
			cld.add(Calendar.MILLISECOND, expiredMilliseconds);
			expiredTime = cld.getTime();
		}
	}

	public boolean cachedObjIsNull() {
		return cachedObjIsNull;
	}

	public Object getValue() {
		if (useSoftReference) {
			return ((SoftReference) cachedObj).get();
		} else {
			return this.cachedObj;
		}
	}

	public boolean setUseSoftReference(boolean useSoftReference) {
		boolean flag = true;
		if (this.useSoftReference != useSoftReference) {
			if (this.useSoftReference) {
				Object obj = ((SoftReference) cachedObj).get();
				if ((null == obj) && (!cachedObjIsNull)) {
					flag = false;
				} else {
					cachedObj = obj;
				}
			} else {
				cachedObj = new SoftReference(cachedObj);
			}
		}
		return flag;
	}

	public boolean hasBeenExpired() {
		if (null != expiredTime) {
			if (expiredTime.before(new Date())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void delayExpiredTime(int milliseconds) {
		if (null != expiredTime) {
			if (milliseconds <= 0) {
				expiredTime = null;
			} else {
				Calendar cld = Calendar.getInstance();
				cld.setTime(expiredTime);
				cld.add(Calendar.MILLISECOND, milliseconds);
				expiredTime = cld.getTime();
			}
		}
	}
}