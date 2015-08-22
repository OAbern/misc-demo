package com.kingdee.bern.jackson.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingdee.bern.fastjson.example.PathUtils;
import com.kingdee.mobile.credit.entity.Teacher;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 示例1：
 * 复杂对象的转换
 * @author sola
 *
 */
public class Example1 {
	public static void main(String[] args) {
		Teacher teacher = null;
		try {
			File file = new File("resource/teacher.serli");
			if(!file.exists()) {
				System.out.println("文件不存在！");
				return;
			}
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
			
			teacher = (Teacher) inputStream.readObject();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		File file2 = new File(PathUtils.RES_PATH+"jackson.json");
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file2);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
//		try {
//			mapper.writeValue(outputStream, teacher);
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
