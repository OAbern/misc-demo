package com.kingdee.bern.fastjson.example;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kingdee.bern.fastjson.utils.ChangeNameFilter;
import com.kingdee.bern.fastjson.utils.CustomPropertyFilter;
import com.kingdee.bern.fastjson.utils.JsonUtil;
import com.kingdee.mobile.credit.entity.Course;
import com.kingdee.mobile.credit.entity.Student;
import com.kingdee.mobile.credit.entity.Teacher;

/**
 * 过滤字段，修改名字
 * @author sola
 *
 */
public class Example5 {
	public static void main(String[] args) {
		Teacher teacher = (Teacher) FileUtils.getUserFromFile("resource/teacher.serli");
		
		//创建排除字段的过滤器
		SerializeFilter filter1 = new CustomPropertyFilter(Student.class,"age");
		
		//创建改变名字的过滤器
		ChangeNameFilter filter2 = new ChangeNameFilter(new String[]{"teaId"}, new String[]{"teacherId"});
		
		SerializeFilter[] filtersArray = new SerializeFilter[] {filter1, filter2};
		
		//转换
		String json1 = JSON.toJSONString(teacher, filtersArray, SerializerFeature.DisableCircularReferenceDetect, 
				 SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue);
		
		//改名的测试
		String json2 = JsonUtil.toJSONString(teacher, true, new String[]{"teaId"}, new String[]{"teacherId"});
		
		//过滤字段的测试1
		String json3 = JsonUtil.toJSONString(teacher, true, "name","teaId");
		
		//过滤字段的测试2
		String json4 = JsonUtil.toJSONString(teacher, true, Course.class, "id");
		
		//过滤字段的测试3
		Map<Class<?>, String[]> map = new HashMap<Class<?>, String[]>();
		map.put(Teacher.class, new String[]{"name"});
		map.put(Course.class, new String[]{"description"});
		map.put(null, new String[]{"id"});
		String json5 = JsonUtil.toJSONString(teacher, true, map);
		
		
		FileUtils.writeStringToFile(json5, "resource/fastjson.json");
	
	}
}
