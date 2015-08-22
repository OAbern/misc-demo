package com.kingdee.bern.collection;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * 演示arraylist产生ConcurrentModificationException
 * @author sola
 *
 */
public class ArrayListCME {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<10; i++)
			list.add(i);
		
		display1(list);
		display2(list);
	}
	
	public static void display1(List<Integer> list) {
		Thread t1 = new Thread(new Travl_1(list));
		t1.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		list.remove(6);
	}
	
	public static void display2(List<Integer> list) {
		Thread t1 = new Thread(new Travl_2(list));
		t1.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		list.remove(6);
	}
}

/**
 * 使用Iter遍历
 */
class Travl_1 implements Runnable {
	private List<Integer> list;
	
	public Travl_1(List<Integer> list) {
		this.list = list;
	}

	@Override
	public void run() {
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				System.out.println("Travl1----Iter遍历:"+it.next());
			} catch(ConcurrentModificationException e) {
				System.out.println(e);
				System.out.println("Travl1----Iter遍历结束");
				return;
			}
		}
	}
}

/**
 * 使用普通的for循环遍历
 */
class Travl_2 implements Runnable {
	private List<Integer> list;
	
	public Travl_2(List<Integer> list) {
		this.list = list;
	}

	@Override
	public void run() {
		int size = list.size();
		for(int i=0; i<size; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Travl2普通遍历:"+list.get(i));
		}
	}
	
}
