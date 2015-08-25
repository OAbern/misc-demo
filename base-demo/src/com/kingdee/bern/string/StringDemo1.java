package com.kingdee.bern.string;

/**
 * 演示String的特殊性
 * String在java内存中是存储在常量池里面的
 * @author sola
 *
 */
public class StringDemo1 {
	public static void main(String[] args) {
		String a = "hello";
		String b = "hello";
		String c = new String("hello");
		final String d = "hello";
		String e = "hello2";
		String f = a + 2;
		String g = d + 2;
		
		
		System.out.println(a == b);
		System.out.println(a == c);
		System.out.println(a == c.intern());
		System.out.println(a == d);
		System.out.println(e == f);
		System.out.println(e == g);
		System.out.println(f == g);
	}
}
