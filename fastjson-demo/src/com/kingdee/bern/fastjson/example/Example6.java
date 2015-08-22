package com.kingdee.bern.fastjson.example;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.kingdee.bern.fastjson.utils.JsonUtil;
import com.kingdee.mobile.credit.entity.Teacher;

/**
 * 反序列化 容错
 * @author sola
 *
 */
public class Example6 {
	public static void main(String[] args) {
		String json = FileUtils.getStringFromFile("resource/fastjson.json");
		
		ParseProcess process = new TypeErrorProcessor();
		Teacher teacher = JSON.parseObject(json, Teacher.class,  process, Feature.UseBigDecimal);
		
		Teacher t = JsonUtil.parseObject(json, Teacher.class);
		
		JSONObject jsonObject = JSON.parseObject(json);
		
		Object object = jsonObject.get("courses");
		
		System.out.println(jsonObject);
		System.out.println(teacher.toString());
	}
}

class TypeErrorProcessor implements ExtraTypeProvider , ExtraProcessor {

	public void processExtra(Object object, String key, Object value) {
		System.out.println("2");
		System.out.println("key:"+key+",  value:"+value);
	}
	
	public Type getExtraType(Object object, String key) {
		System.out.println("1");
		return null;
	}

}


