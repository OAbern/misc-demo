package com.kingdee.bern.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.kingdee.bern.util.PrintUtil;

public class LinkedHashMapAccess {
	public static void main(String[] args) {
		String[] strings = {"aaa","bbb","ccc","ddd","eee"};
		display1(strings);
		display2(strings);
		display3(strings);
	}
	
	/**
	 * 遍历LinkedHashMap
	 * @param strings
	 */
	public static void display1(String[] strings) {
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
		
		for(String s : strings) {
			linkedHashMap.put(s, s);
		}
		System.out.println("访问了："+linkedHashMap.get(strings[0]));
		
		Iterator<String> it = linkedHashMap.keySet().iterator();
		System.out.println("LinkedHashMap Travel:");
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		PrintUtil.printBorder();
	}
	
	/**
	 * 遍历HashMap
	 * @param strings
	 */
	public static void display2(String[] strings) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		
		for(String s : strings) {
			hashMap.put(s, s);
		}
		
		Iterator<String> it = hashMap.keySet().iterator();
		System.out.println("HashMap Travel:");
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		PrintUtil.printBorder();
	}
	
	/**
	 * 遍历LinkedHashMap
	 * 
	 * @param strings
	 */
	public static void display3(String[] strings) {
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>(16, 0.75f, true);
		
		for(String s : strings) {
			linkedHashMap.put(s, s);
		}
		System.out.println("访问了："+linkedHashMap.get(strings[0]));
		
		Iterator<String> it = linkedHashMap.keySet().iterator();
		System.out.println("LinkedHashMap Travel:");
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		PrintUtil.printBorder();
	}
}
