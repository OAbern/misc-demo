package com.kingdee.bern.fastjson.utils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.serializer.NameFilter;

/**
 * 修改名字的过滤器
 * 
 * <p>修改java2json时,输出的json中key的名字
 * @author fengdi 
 *
 */
public class ChangeNameFilter implements NameFilter {
	
	/**
	 * java中java中字段的名字与json中的key的映射，
	 * map中的key为java中字段的名字，value为json的key
	 */
	private final Map<String, String> nameMapping = new HashMap<String, String>();
	
	/**
	 * 构造了一个java中字段和json中key的映射
	 * 
	 * <p>property和change中的元素必须一一对应
	 * 
	 * <p>如果两个参数数组的元素个数不相同，将按最小的元素个数建立映射
	 * 
	 * @param property java中字段的名字，即修改前的名字
	 * @param change json中的key，即修改后的名字
	 */
	public ChangeNameFilter(String[] property, String[] change) {
		if(property.length!=0 && change.length!=0) {
			int min = property.length <= change.length ? property.length : change.length;	//取数组个数较小的一个
			
			for(int i=0; i<min; i++) {
				nameMapping.put(property[i], change[i]);
			}
		}
	}
	
	/**
	 * 构造了一个java中字段和json中key的映射
	 * 
	 * <p>Map参数中的key必须为java中字段的名字，value为json中key的名字
	 * 
	 * @param mapping 字段和json中key的映射
	 */
	public ChangeNameFilter(Map<String, String> mapping) {
		if(mapping!=null && mapping.size()!=0) {
			nameMapping.putAll(mapping);
		}
	}
	
	public Map<String, String> getNameMapping() {
		return nameMapping;
	}
	
	/**
	 * 添加一个映射
	 * @param property java中字段的名字
	 * @param change json中key的名字
	 * @return 如果参数有null，则返回false
	 */
	public boolean put(String property, String change) {
		if(property==null || change==null) {
			return false;
		}
		nameMapping.put(property, change);
		return true;
	}
	
	/**
	 * fastjson底层自动调用的方法,用于进行对json输出做修改
	 */
	public String process(Object object, String name, Object value) {
		if(nameMapping.containsKey(name)) {
			return nameMapping.get(name);
		}
		return name;
	}
	
}