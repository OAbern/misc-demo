package com.kingdee.bern.collection;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.kingdee.bern.util.PrintUtil;

/**
 * 
 * @author sola
 *
 */
public class TreeMapDemo {
	public static void main(String[] args) {
		display1();
		display2();
		usage1();
		usage2();
	}
	
	public static void display1() {
		Map<Integer, String> map = new TreeMap<>();
		map.put(2, "2");
		map.put(4, "4");
		map.put(1, "1");
		map.put(3, "3");
		Iterator<?> it = map.keySet().iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		PrintUtil.printBorder();
	}
	
	public static void display2() {
		Map<String, String> map = new TreeMap<>();
		map.put("201508", "201508");
		map.put("20140701", "201407");
		map.put("201506", "201506");
		map.put("201412", "201412");
		Iterator<?> it = map.keySet().iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		PrintUtil.printBorder();
	}
	
	/**
	 * TreeMap用法一：
	 * key实现Comparable的借口
	 */
	public static void usage1() {
		Map<Student, String> map = new TreeMap<>();
		map.put(new Student("20122140", "sola"), "20122140");
		map.put(new Student("20122039", "bern"), "20122039");
		map.put(new Student("20122137", "joe"), "20122137");
		Iterator<?> it = map.keySet().iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		PrintUtil.printBorder();
	}
	
	/**
	 * TreeMap用法二：
	 * 构造参数传入Comparator的实例
	 */
	public static void usage2() {
		Map<Teacher, String> map = new TreeMap<>(new TeacherComparator());
//		Map<Teacher, String> map = new TreeMap<>();			//运行出错
		map.put(new Teacher("20122140", "sola"), "20122140");
		map.put(new Teacher("20122039", "bern"), "20122039");
		map.put(new Teacher("20122137", "joe"), "20122137");
		Iterator<?> it = map.keySet().iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		PrintUtil.printBorder();
	}
}

class Student implements Comparable<Student> {
	private String id;
	private String name;
	
	public Student(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public int compareTo(Student o) {
		if(o == null) 
			return -1;
		
		return this.id.compareTo(o.id);	
	}

	@Override
	public String toString() {
		return "student-id:"+id+", name:"+name;
	}
}

class Teacher {
	private String id;
	private String name;
	
	public Teacher(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "teacher-id:"+id+", name:"+name;
	}

	public String getId() {
		return id;
	}
	
}

class TeacherComparator implements Comparator<Teacher> {

	@Override
	public int compare(Teacher t1, Teacher t2) {
		if(t1==null && t2!=null)
			return -1;
			
		if(t1!=null && t2==null)
			return 1;	
			
		if(t1==null && t2==null)
			return 0;		
			
		return t1.getId().compareTo(t2.getId());
	}
	
}