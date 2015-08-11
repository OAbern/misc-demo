package com.kingdee.bern.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 列出基本常用的容器
 * 注意观察容器的继承体系，以及查看容器的源码
 * @author sola
 *
 */
public class AllCollection {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Collection collection;
		
		//List各种
		List list;
		ArrayList<String> arrayList;
		LinkedList<String> linkedList;
		Vector<String> vector;
		Stack<String> stack;
		
		//Map各种
		Map map;
		HashMap<String, Object> hashMap;
		LinkedHashMap<String, Object> linkedHashMap;
		TreeMap<String, Object> treeMap;
		
		//Set各种
		Set set;
		HashSet<String> hashSet;
		LinkedHashSet<String> linkedHashSet;
		TreeSet<String> treeSet;
		
		//并发容器
		ConcurrentHashMap<String, Object> concurrentHashMap;
		ConcurrentLinkedQueue<String> concurrentLinkedQueue;
		ConcurrentLinkedDeque<String> concurrentLinkedDeque;
		CopyOnWriteArrayList<String> copyOnWriteArrayList;
		CopyOnWriteArraySet<String> copyOnWriteArraySet;
		
		//阻塞容器
		LinkedBlockingQueue<String> linkedBlockingQueue;
		LinkedBlockingDeque<String> linkedBlockingDeque;
		
	}
}
