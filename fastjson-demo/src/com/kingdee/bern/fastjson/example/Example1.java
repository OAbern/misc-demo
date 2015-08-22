package com.kingdee.bern.fastjson.example;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.kingdee.mobile.credit.entity.Student;

/**
 * 示例一：<br>
 * java2json的基本操作示例
 * @author sola
 *
 */
public class Example1 {
	public static void main(String[] args) {
		Student student1 = new Student(2015, "sola", 20, "广东深圳");
		Student student2 = new Student(2016, "sola", 20, "广东深圳");
		List<Student> list = new ArrayList<Student>();
		list.add(student1);
		list.add(student2);
		
		String json1 = JSON.toJSONString(student1);
		String json2 = JSON.toJSONString(list);
		System.out.println(json1);
		System.out.println("=====================================");
		System.out.println(json2);
		
	}
	
}
