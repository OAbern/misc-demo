package com.kingdee.bern.fastjson.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.kingdee.mobile.credit.entity.Teacher;

/**
 * 示例4：
 * 复杂json对象2java
 * @author sola
 *
 */
public class Example4 {
	public static void main(String[] args) {
		File file = new File("resource/fastjson.json");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder builder = new StringBuilder();
			String json = br.readLine();
			while(json != null) {
				builder.append(json);
				json = br.readLine();
			}
			json = builder.toString();
			br.close();
			
			Teacher user = JSON.parseObject(json, Teacher.class);
//			JSONObject teacher = JSON.parseObject(json);
			
			System.out.println(user.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
