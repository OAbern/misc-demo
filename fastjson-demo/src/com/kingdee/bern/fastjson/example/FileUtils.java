package com.kingdee.bern.fastjson.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 操作文件的工具类
 * @author sola
 *
 */
public class FileUtils {
	/**
	 * 从序列化的文件中获取object对象
	 * @param path
	 * @return
	 */
	public static Object getUserFromFile(String path) {
		ObjectInputStream inputStream = null;
		Object object = null;
		try {
			File file = new File(path);
			if(!file.exists()) {
				System.out.println("文件不存在！");
				return null;
			}
			inputStream = new ObjectInputStream(new FileInputStream(file));
			
			object = inputStream.readObject();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return object;
	}
	
	/**
	 * 将字符串写入文件中
	 * @param text
	 * @param path
	 */
	public static void writeStringToFile(String text, String path) {
		File file = new File(path);
		
		FileOutputStream outputStream;
		try {
			
			outputStream = new FileOutputStream(file);
			outputStream.write(text.getBytes());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从文件中读取json的字符串
	 * @param path
	 * @return
	 */
	public static String getStringFromFile(String path) {
		File file = new File(path);
		BufferedReader br = null;
		String json = null;
		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			StringBuilder builder = new StringBuilder();
			json = br.readLine();
			while(json != null) {
				builder.append(json);
				json = br.readLine();
			}
			json = builder.toString();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return json;
		
	}
	
}
