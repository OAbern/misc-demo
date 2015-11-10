package com.kingdee.bern.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;

public class CreatFile {
	
	private static void create(Long times) throws Exception {
		File file = new File("error.log");
		if(!file.exists()) {
			file.createNewFile();
		}
		System.out.println(file.getAbsolutePath());
		FileWriter writer = new FileWriter(file, true);
		try(BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
			while(times-- > 0) {
				bufferedWriter.write(times + new Date().toString());
				bufferedWriter.newLine();
			}
			System.out.println("end");
		}
	}
	
	public static void main(String[] args) {
		try {
//			create((long) (10000*10000));		//10000*10000 can make 3.4G, just take 3 ~ 4 min;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
