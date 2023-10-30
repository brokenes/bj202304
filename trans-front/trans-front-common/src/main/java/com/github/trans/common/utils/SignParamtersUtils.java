package com.github.trans.common.utils;


import com.github.trans.common.annotation.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/***
 * 快捷支付参数对象验证
 * 
 * @author Administrator
 *
 */
public class SignParamtersUtils {

	protected final static Logger logger = LoggerFactory.getLogger(SignParamtersUtils.class);

	/***
	 * 验证参数方法
	 * @param t
	 * @throws T
	 */
	public static <T> void verifyParameters(T t){
		Class<?> clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Signature sign = field.getAnnotation(Signature.class);
			Object value = getFieldsValue(t, field);
			String fieldName = field.getName();
			if (sign != null) {
				if (value == null) {
					logger.info("对象名称:{} --> 属性:{}为空!", t.getClass().getSimpleName(), fieldName);
//					throw new PaymentException(PaymentErrorCode.PARAM_NOT_NULL.getCode(),
//							String.format(PaymentErrorCode.PARAM_NOT_NULL.getMessage(),fieldName));
				}
			}
		}

	}


	public static <T> T getFieldsValue(Object target, Field field) {
		field.setAccessible(true);
		T value = null;
		try {
			value = (T) field.get(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 将目标对象的所有属性转换成加密Map对象
	 * @param target 目标对象
	 * @return Map
	 */
	public static <T> Map<String, T> toUnEncryptionMap(Object target,boolean ignoreEmptyValue,String ... ignoreProperties) {
		return toUnEncryptionMap(target, true, ignoreEmptyValue, ignoreProperties);
	}

	/**
	 * 将目标对象的所有属性转换成加密Map对象
	 * @param target 目标对象
	 * @return Map
	 */
	public static <T> Map<String, T> toEncryptionMap(Object target,boolean ignoreEmptyValue,String ... ignoreProperties) {
		return toEncryptionMap(target, true, ignoreEmptyValue, ignoreProperties);
	}

	public static <K, V> String mapToSignParams(Map<K, V> map) {
		StringBuffer sb = new StringBuffer();
		String operator = "=";
		String ampersand = "&";
		int size = 0;
		for (Map.Entry<K, V> entry : map.entrySet()) {
			size++;
			if (size == map.size()) {
				sb.append(entry.getKey());
				sb.append(operator);
				sb.append(entry.getValue());
			} else {
				sb.append(entry.getKey());
				sb.append(operator);
				sb.append(entry.getValue());
				sb.append(ampersand);
			}
		}
		return sb.toString();
	}

	public static <T> Map<String, T> toEncryptionMap(Object target,
			boolean ignoreParent, boolean ignoreEmptyValue,
			String... ignoreProperties) {
		final Map<String, T> map = new TreeMap<String, T>((s1,s2) -> s1.compareTo(s2));
//				new Comparator<String>() {
//					public int compare(String obj1, String obj2) {
//						return obj1.compareTo(obj2);
//					}
//				});
		if (target == null) {
			return map;
		}
		final Map<String, String> ignoreMap = getIgnorePropertiesMap(ignoreProperties);
		final List<Field> fields = getClassFields(ignoreParent, target.getClass());
		for (Field field : fields) {
			T value = getFieldsValue(target, field);
			if (isIgnoreEmpty(value, ignoreEmptyValue)
					|| isIgnoreProperty(field.getName(), ignoreMap)) {
				continue;
			}
			// 需要签名的数据
			Signature sign = field.getAnnotation(Signature.class);
			if (sign != null) {
				if (sign.required() == true) {
					map.put(field.getName(), value);
				}
			}
		}
		return map;
	}

	public static <T> Map<String, T> toUnEncryptionMap(Object target,
			boolean ignoreParent, boolean ignoreEmptyValue,
			String... ignoreProperties) {
		 final Map<String, T> treeMap = new TreeMap<String, T>((s1,s2) -> s1.compareTo(s2));
//				new Comparator<String>() {
//					public int compare(String obj1, String obj2) {
//						return obj1.compareTo(obj2);
//					}
//				});
		if (target == null) {
			return treeMap;
		}
		final Map<String, String> ignoreMap = getIgnorePropertiesMap(ignoreProperties);
		final List<Field> fields = getClassFields(ignoreParent,
				target.getClass());
		// 对属性排序
		Collections.sort(fields, new FieldComparator());
		for (Field field : fields) {
			T value = getFieldsValue(target, field);
			if (isIgnoreEmpty(value, ignoreEmptyValue)
					|| isIgnoreProperty(field.getName(), ignoreMap)) {
				continue;
			}
			treeMap.put(field.getName(), value);
		}
		return treeMap;
	}

	private static class FieldComparator implements Comparator<Field> {
		@Override
		public int compare(Field o1, Field o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}

	}

	/**
	 * 是否忽略属性
	 * @param fieldName 属性字段名
	 * @param ignoreMap 忽略属性MAP
	 * @return
	 */
	private static <T> boolean isIgnoreProperty(String fieldName,
			final Map<String, String> ignoreMap) {
		return ignoreMap.containsKey(fieldName);
	}

	/**
	 * 是否忽略空值
	 * 
	 * @param value
	 *            属性值
	 * @param ignoreEmptyValue
	 *            是否不把空值添加到Map中
	 * @return
	 */
	private static <T> boolean isIgnoreEmpty(T value, boolean ignoreEmptyValue) {
		boolean isEmpty = (value == null || value.toString().equals(""));
		boolean isCollectionEmpty = (value instanceof Collection && ((Collection<?>) value)
				.isEmpty());
		boolean isMapEmpty = (value instanceof Map && ((Map<?, ?>) value)
				.isEmpty());
		return ignoreEmptyValue && (isEmpty || isCollectionEmpty || isMapEmpty);
	}

	/**
	 * 获取类实例的属性
	 * 
	 * @param ignoreParent
	 *            是否忽略父类属性
	 * @param clazz
	 *            类名
	 * @return
	 */
	private static List<Field> getClassFields(boolean ignoreParent,
			Class<?> clazz) {
		final List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		if (!ignoreParent) {
			fields.addAll(getParentClassFields(fields, clazz.getSuperclass()));
		}
		return fields;
	}

	/**
	 * 获取类实例的父类的属性
	 * @param fields 类实例的属性
	 * @param clazz 类名
	 * @return
	 */
	private static List<Field> getParentClassFields(List<Field> fields,
			Class<?> clazz) {
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		if (clazz.getSuperclass() == null) {
			return fields;
		}
		return getParentClassFields(fields, clazz.getSuperclass());
	}

	private static Map<String, String> getIgnorePropertiesMap(
			final String[] ignoreProperties) {
		final Map<String, String> resultMap = new HashMap<String, String>();
		for (String ignoreProperty : ignoreProperties) {
			resultMap.put(ignoreProperty, null);
		}
		return resultMap;
	}

	/**
	 * txnSubType=00&
	 * signature=WQtmodMffWSdk6t9FrsI3A==&
	 * respMsg=5Y+C5pWw6ZSZ6K+vKOS4jeaUr+aMgeivt+axguWPguaVsG1lcktleSk=&
	 * txnType=71&merId=200000000000001&version=1.0.0&
	 * respCode=0001&
	 * signMethod=MD5
	 * 将反人类的返回结果转map
	 * @param str
	 * @return
	 */
	public static <T> Map<String,String> respToMap(String str){
		String[] arry = str.split("&");
		Map<String,String> map = new HashMap<>();
		for (String string:arry){
			int first = string.indexOf("=");
			String key = string.substring(0,first);
			String value = string.substring(first+1);
			map.put(key,value);

		}
		return map;
	}

}
