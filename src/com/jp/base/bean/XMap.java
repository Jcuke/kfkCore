package com.jp.base.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: XMap
 * @Description: 自定义Map key全部为小写
 * @author: liwei
 * @date: 2016年8月2日 下午3:12:19
 * @param <K>
 * @param <V>
 */
public class XMap<K, V> extends HashMap<K, V> implements Map<K, V> {
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	public XMap() {
		int n = 0;
		n++;
	}

	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		Object k = key.toString().toLowerCase();
		return super.put((K) k, value);
	}

	// public Integer getInt(K key) {
	// try {
	// if (containsKey(key)) {
	// V v = get(key);
	// if (v instanceof Integer)
	// return (Integer) v;
	// if (v instanceof String) {
	// if (v.toString().matches("[0-9]+"))
	// return Integer.parseInt(v.toString());
	//
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
}
