package com.kingdee.bern.fastjson.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

/**
 * 字段的过滤器
 * 
 * @author sola
 *
 */
public class CustomPropertyFilter implements PropertyPreFilter {
	/**
	 * 要过滤的字段所属的类
	 * <p>当clazz为空null时，表示所有的类都过滤这个字段
	 */
	private final Class<?> clazz;
	
	/**
	 * 不排除的字段集合，即在json中输出的字段
	 */
    private final Set<String> includes = new HashSet<String>();
    
    /**
	 * 排除的字段集合，即不在json中输出的字段
	 */
    private final Set<String> excludes = new HashSet<String>();
    
    /**
     * 构造排除的字段集合
     * <p>默认将property添加进排除的字段集合；当clazz为空null时，表示所有的类都过滤这个字段
     * @param clazz 过滤字段所属的类
     * @param properties 过滤的字段
     */
    public CustomPropertyFilter(Class<?> clazz, String... properties) {
        this(clazz, true, properties);
    }
    
    /**
     * 构造排除字段的集合
     * <p>默认是所有的类都过滤的字段，默认将property添加进排除的字段集合
     * @param properties 过滤的字段
     */
    public CustomPropertyFilter(String... properties) {
        this(null, true, properties);
    }
    
    /**
     * 构造过滤字段的集合
     * <p>当clazz为空null时，表示所有的类都过滤这个字段
     * @param exclude 当exclude为true时，添加的元素为排除的字段；否则为不排除的字段
     * @param properties 过滤的字段
     */
    public CustomPropertyFilter(Class<?> clazz, boolean exclude, List<String> properties) {
    	this(clazz, exclude, (String[])properties.toArray());
    }
    
    /**
     * 构造过滤字段的集合
     * <p>默认是所有的类都过滤的字段
     * @param exclude 当exclude为true时，添加的元素为排除的字段；否则为不排除的字段
     * @param properties 过滤的字段
     */
    public CustomPropertyFilter(boolean exclude, String... properties) {
        this(null, exclude, properties);
    }
    
    /**
     * 构造过滤的字段集合
     * <p>当clazz为空null时，表示所有的类都过滤这个字段
     * @param clazz 过滤字段所属的类
     * @param exclude 当exclude为true时，添加的元素为排除的字段；否则为不排除的字段
     * @param properties 过滤的字段
     */
    public CustomPropertyFilter(Class<?> clazz, boolean exclude, String... properties) {
        super();
        this.clazz = clazz;
        if(properties != null) {
	        for (String item : properties) {
	        	if (item != null) {
	        		if(exclude) {
		                this.excludes.add(item);
		            }else {
		            	this.includes.add(item);
		            }
	        	}
	        }
        }
    }
    
    /**
     * 添加不排除的字段
     * @param propertys 不排除的字段
     */
    public void addInclude(String... propertys) {
    	if(propertys != null) {
    		for(String p : propertys) {
    			this.includes.add(p);
    		}
    	}
    }
    
    /**
     * 添加排除的字段
     * @param propertys 排除的字段
     */
    public void addExclude(String... propertys) {
    	if(propertys != null) {
    		for(String p : propertys) {
    			this.excludes.add(p);
    		}
    	}
    }
    
    /**
     * fastjson底层自动调用的代码，用来判断字段是否需要在json中输出
     */
    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }

        if (clazz != null && !clazz.isInstance(source)) {
            return true;
        }

        if (this.excludes.contains(name)) {
            return false;
        }

        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }

        return false;
    }
	
    public Class<?> getClazz() {
        return clazz;
    }
    
    /**
     * 获取不排除的字段集合
     */
    public Set<String> getIncludes() {
        return includes;
    }
    
    /**
     * 获取排除的字段集合
     */
    public Set<String> getExcludes() {
        return excludes;
    }
}
