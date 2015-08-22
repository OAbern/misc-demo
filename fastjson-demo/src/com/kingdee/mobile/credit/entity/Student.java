package com.kingdee.mobile.credit.entity;

import java.io.Serializable;

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int stuId;
	private String name;
	private int age;
	private String address;
	
	public Student() {	}
	
	public Student(int stuId, String name, int age, String address) {
		super();
		this.stuId = stuId;
		this.name = name;
		this.age = age;
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Student [stuId=" + stuId + ", name=" + name + ", age=" + age + ", address=" + address + "]";
	}
	
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
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
	
}
