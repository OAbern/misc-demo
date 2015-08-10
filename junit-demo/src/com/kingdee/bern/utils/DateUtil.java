package com.kingdee.bern.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期的工具类
 * @author sola
 *
 */
public class DateUtil {
	
	/**
	 * 计算两个日期之间相差的月份数
	 * @param date1
	 * @param date2
	 * @return 相差的月份数,如果参数为空，返回-1；
	 */
	public static int computeMonthSpace(Date date1,	Date date2) {
		int result = 0;
		if(date1==null || date2==null) {
			return -1;
		}
		
		if(date1.after(date2)) {		//确保date1在date2之前
			Date temp = date1;
			date1 = date2;
			date2 = temp;
		}
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(date1);
		end.setTime(date2);
		
		int ySpace = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);		//计算相差的年份
		int mSpace = 0;
		if(ySpace == 0) {		//相同年份
			mSpace = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
			result = mSpace;
		} else {		//不同年份
			mSpace = end.get(Calendar.MONTH) + (12 - start.get(Calendar.MONTH)); 
			result = 12 * (ySpace - 1)  + mSpace;
		}
		
		return result;
	}
}
