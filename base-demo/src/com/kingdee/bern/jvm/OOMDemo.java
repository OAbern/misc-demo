package com.kingdee.bern.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 制造了堆内存不足，引起的OOM
 * <p>可以使用Eclipse Memory Analyzer查看堆转储文件
 * <p>运行时加上虚拟机参数：
 * -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
 * @author sola
 *
 */
public class OOMDemo {
	static class OOMObject {
	}
	
	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<>();
		while(true) {
			list.add(new OOMObject());
		}
	}
}
