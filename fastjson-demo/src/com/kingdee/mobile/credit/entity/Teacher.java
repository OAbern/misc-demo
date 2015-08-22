package com.kingdee.mobile.credit.entity;

import java.io.Serializable;
import java.util.List;

public class Teacher implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int teaId;
	private String name;
	private int age;
	private String address;
	
	private List<Course> courses;
	private List<Student> students;
	
	public Teacher() {
		super();
	}
	
	public Teacher(int teaId, String name, int age, String address) {
		super();
		this.teaId = teaId;
		this.name = name;
		this.age = age;
		this.address = address;
	}
	
	public int getTeaId() {
		return teaId;
	}
	public void setTeaId(int teaId) {
		this.teaId = teaId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
}
