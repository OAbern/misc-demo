package com.kingdee.bern.fastjson.utils;

import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * json操作的工具类
 * @author sola
 *
 */
public class JsonUtil {
	
	/**
	 * 将java对象转换成json字符串
	 * <p>所有对象的excludeProperty字段都不输出
	 * <p>开启null值输出，关掉循环引用检测
	 * @param object 需要转换的java对象
	 * @param format 是否格式化输出，true为格式化
	 * @param excludeProperty 需要排除的全局字段(每个类相应匹配的字段都会被过滤掉)
	 * @return json字符串
	 */
	public static String toJSONString(Object object,boolean format, String... excludeProperty) {
		SerializeFilter filter = new CustomPropertyFilter(excludeProperty);
		return JsonUtil.toJSONStringStandard(object, format, filter);
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>只有属于clazz这个类的excludeProperty字段不输出
	 * <p>开启null值输出，关掉循环引用检测
	 * @param object 需要转换的java对象
	 * @param format 是否格式化输出，true为格式化
	 * @param clazz 需要过滤字段所属的类
	 * @param excludeProperty 需要排除的某个类字段
	 * @return json字符串
	 */
	public static String toJSONString(Object object, boolean format, Class<?> clazz, String excludeProperty) {
		SerializeFilter filter = new CustomPropertyFilter(clazz,excludeProperty);
		return JsonUtil.toJSONStringStandard(object, format, filter);
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>excludePropertyMap中key为null表示所有的类都要过滤的字段
	 * <p>开启null值输出，关掉循环引用检测
	 * @param object 需要转换的java对象
	 * @param format 是否格式化输出，true为格式化
	 * @param excludePropertyMap key为类的类型，value为属于这个类需要排除的字段数组
	 * @return json字符串
	 */
	public static String toJSONString(Object object, boolean format, Map<Class<?>, String[]> excludePropertyMap) {
		if(excludePropertyMap==null || excludePropertyMap.size()==0) {
			return JsonUtil.toJSONStringStandard(object, format);
		}
		SerializeFilter[] filterArray = new SerializeFilter[excludePropertyMap.size()];
		Iterator<Class<?>> it = excludePropertyMap.keySet().iterator();
		int i = 0;
		while(it.hasNext()) {
			Class<?> clazz = it.next();
			SerializeFilter filter = new CustomPropertyFilter(clazz, excludePropertyMap.get(clazz));
			filterArray[i] = filter;
			i++;
		}
		return JsonUtil.toJSONStringStandard(object, format, filterArray);
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>propertyName和changeName在数组中必须一一对应
	 * <p>开启null值输出，关掉循环引用检测
	 * @param object object 需要转换的java对象
	 * @param format 是否格式化输出，true为格式化
	 * @param propertyName java字段的名字
	 * @param changeName json中的key
	 * @return json字符串
	 */
	public static String toJSONString(Object object, boolean format, String[] propertyName, String[] changeName) {
		SerializeFilter filter = new ChangeNameFilter(propertyName, changeName);
		return JsonUtil.toJSONStringStandard(object, format, filter);
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>propertyName和changeName在数组中必须一一对应，所有对象的excludeProperty字段都不输出
	 * <p>开启null值输出，关掉循环引用检测
	 * @param object object 需要转换的java对象
	 * @param format 是否格式化输出，true为格式化
	 * @param propertyName java字段的名字
	 * @param changeName json中的key
	 * @param excludeProperty 需要排除的全局字段(每个类相应匹配的字段都会被过滤掉)
	 * @return json字符串
	 */
	public static String toJSONString(Object object, boolean format,String[] propertyName, String[]changeName, String[] excludeProperty) {
		SerializeFilter filter1 = new ChangeNameFilter(propertyName,changeName);
		SerializeFilter filter2 = new CustomPropertyFilter(excludeProperty);
		SerializeFilter[] filterArray = {filter1, filter2};
		return JsonUtil.toJSONStringStandard(object, format, filterArray);
	}
	
	/**
	 * 
	 * @param object
	 * @param format
	 * @param propertyName
	 * @param changeName
	 * @param excludePropertyMap
	 */
	public static String toJSONString(Object object, boolean format, String[] propertyName, String[]changeName, Map<Class<?>, String[]> excludePropertyMap) {
		if(excludePropertyMap==null || excludePropertyMap.size()==0) {
			return JsonUtil.toJSONString(object, format, propertyName, changeName);
		}
		SerializeFilter[] filterArray = new SerializeFilter[excludePropertyMap.size()+1];
		SerializeFilter changeNameFilter = new ChangeNameFilter(propertyName, changeName);
		filterArray[0] = changeNameFilter;
		Iterator<Class<?>> it = excludePropertyMap.keySet().iterator();
		int i = 1;
		while(it.hasNext()) {
			Class<?> clazz = it.next();
			SerializeFilter filter = new CustomPropertyFilter(clazz, excludePropertyMap.get(clazz));
			filterArray[i] = filter;
			i++;
		}
		return JsonUtil.toJSONStringStandard(object, format, filterArray);
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>输出的json字符串没有格式化；
	 * <p>开启null值输出，关掉循环引用检测
	 * @param object 需要转换的java对象
	 * @return json字符串
	 */
	public static String toJSONStringStandard(Object object) {
		return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>开启null值输出；关掉循环引用检测
	 * @param object 需要转换的java对象
	 * @param format 是否格式化输出，true为格式化
	 * @return json字符串
	 */
	public static String toJSONStringStandard(Object object, boolean format) {
		if(format) {
			return JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
		} else {
			return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
		}
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>输出的json字符串没有格式化；
	 * <p>开启null值输出，关掉循环引用检测
	 * @param object 需要转换的java对象
	 * @param filters 过滤器
	 * @return json字符串
	 */
	public static String toJSONStringStandard(Object object, SerializeFilter... filters) {
		return JSON.toJSONString(object,filters,SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>开启null值输出；关掉循环引用检测
	 * @param object 需要转换的java对象
	 * @param format 是否格式化输出，true为格式化
	 * @param filters 过滤器
	 * @return json字符串
	 */
	public static String toJSONStringStandard(Object object, boolean format, SerializeFilter... filters) {
		if(format) {
			return JSON.toJSONString(object,filters,SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
		} else {
			return JSON.toJSONString(object,filters,SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
		}
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>输出的json字符串没有格式化；null值不输出；如果有对象循环引用，则输出的字符串中包含循环引用；
	 * <p>原生态，未添加任何的操作，只是简单的封装
	 * @param object 需要转换的java对象
	 * @return json字符串
	 */
	public static String toJSONString(Object object) {
		return JSON.toJSONString(object);
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>原生态，未添加任何的操作，只是简单的封装
	 * @param object 需要转换的java对象
	 * @param features 序列化的选项
	 * @return json字符串
	 */
	public static String toJSONString(Object object, SerializerFeature... features) {
		return JSON.toJSONString(object, features);
	}
	
	/**
	 * 将java对象转换成json字符串
	 * <p>原生态，未添加任何的操作，只是简单的封装
	 * @param object 需要转换的java对象
	 * @param filters 过滤器
	 * @param features 序列化的选项
	 * @return json字符串
	 */
	public static String toJSONString(Object object, SerializeFilter[] filters, SerializerFeature... features) {
		return JSON.toJSONString(object, filters, features);
	}
	
	/**
	 * 将json字符串转换为java对象
	 * <p>原生态，未添加任何的操作，只是简单的封装
	 * @param text 要转换的json文本
	 * @param clazz 需要转换的类型
	 * @return 转换后的对象
	 */
	public static <T> T parseObject(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}
	
	/**
	 * 将json字符串转换为java对象
	 * <p>原生态，未添加任何的操作，只是简单的封装
	 * @param text 要转换的json文本
	 * @param clazz 需要转换的类型
	 * @return 转换后的对象
	 */
	public static JSONObject parseObject(String text) {
		return JSON.parseObject(text);
	}
	
	
}
