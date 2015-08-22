package com.kingdee.bern.fastjson.example;

import com.alibaba.fastjson.JSON;
import com.kingdee.mobile.credit.entity.Student;

public class Example2 {
	public static void main(String[] args) {
		Student student1 = new Student(2015, "sola", 20, "广东深圳");
		String json1 = JSON.toJSONString(student1);
		
		Student student2 = JSON.parseObject(json1, Student.class);
		System.out.println(student2.toString());
	}
}
