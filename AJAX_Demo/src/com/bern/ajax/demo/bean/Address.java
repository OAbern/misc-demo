package com.bern.ajax.demo.bean;

import java.util.ArrayList;
import java.util.List;

public class Address {
	private int id;
	private int pid;
	private String name;
	private List<Address> addresses;
	
	public Address() {
		
	}
	
	public Address(int id, int pid, String name) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Address> getAddresses() {
		if(addresses == null) {
			addresses = new ArrayList<Address>();
		}
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "id:"+id+", pid:"+pid+", name:"+name+", 下属行政区数量:"+addresses.size();
	}
	
}
