package com.kingdee.bern.fastjson.example;

import java.io.File;
import java.io.IOException;

/**
 * 获取各种路径的工具类
 * @author sola
 *
 */
public class PathUtils {
	public static String PROJECT_PATH;
	public static String RES_PATH;
	static {
		File file = new File("");
		try {
			PROJECT_PATH = file.getCanonicalPath()+"/";
			RES_PATH = PROJECT_PATH + "resource"+"/";
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("项目的根路径："+PathUtils.PROJECT_PATH);
		System.out.println("资源的路径："+PathUtils.RES_PATH);
	}
}
