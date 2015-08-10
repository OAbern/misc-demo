package com.kingdee.bern.utils.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.kingdee.bern.utils.DateUtil;

/**
 * 演示多参数运行器
 * @author sola
 *
 */
@RunWith(Parameterized.class)
public class ParameterizedTest {
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
	private String param1, param2;
	private int result;

	@Before
	public void setUp() throws Exception {
	}
	
	public ParameterizedTest(String param1, String param2, int result) {
		super();
		this.param1 = param1;
		this.param2 = param2;
		this.result = result;
	}
	
	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection initData() {
		Object[][] data = {{"201501","201502",1},	{"201412","201502",2},	{"201302","201502",24}};
		return Arrays.asList(data);
	}
	
	@Test
	public void testComputeMonthSpace() {
		Date d1 = null;
		Date d2 = null;
		
		try {
			d1 = dateFormat.parse(param1);
			d2 = dateFormat.parse(param2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertEquals(result, DateUtil.computeMonthSpace(d1, d2));
	}
	
	@Test(timeout = 1000)
	public void testTimeOut() {
		for(;;);
	}
	
	@Ignore
	@Test
	public void testIgnore() {
		
	}

}
