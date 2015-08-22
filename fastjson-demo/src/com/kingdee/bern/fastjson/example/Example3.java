package com.kingdee.bern.fastjson.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PascalNameFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kingdee.mobile.credit.entity.Teacher;

/**
 * 示例3：
 * 复杂java对象2json
 * @author sola
 *
 */
public class Example3 {
	public static void main(String[] args) {
		
		Teacher teacher = (Teacher) FileUtils.getUserFromFile("resource/teacher.serli");
		
		
		//java2json的主要API
		String json1 = JSON.toJSONString(teacher, SerializerFeature.DisableCircularReferenceDetect, 
				 SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue);	//关闭循环引用检测，便于js识别
		
		//java2json的过滤器，默认进include
//		SerializeFilter filter = new SimplePropertyPreFilter(Teacher.class, "name");
		SerializeFilter filter = new PascalNameFilter();
		json1 = JSON.toJSONString(teacher, filter, SerializerFeature.PrettyFormat);
		
//		Set<String> excludes = filter.getExcludes();
//		excludes.add("");
		
		FileUtils.writeStringToFile(json1, "resource/fastjson.json");
		
	}
}
