package com.kingdee.bern.date;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期的工具类
 * @author fengdi
 *
 */
public class DateUtil {
	
	/**
	 * 判断日期是否在某一个时间段里面（闭区间）
	 * <p>即判断judgeDate是否在（borderDate1，borderDate1+month）或（borderDate1+month，borderDate1）
	 * @param judgeDate 需要判断的日期
	 * @param nowDate 时间段的边界
	 * @param month 时间段的月数，可以为正负
	 * @return 判断的结果
	 */
	public static boolean judgeDate(Date judgeDate, Date borderDate1, int month) {
		if(judgeDate==null || borderDate1==null) {
			return false;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(borderDate1);
		calendar.add(Calendar.MONTH, month);
		Date borderDate2 = calendar.getTime();
		if(month > 0) {
			if(judgeDate.compareTo(borderDate1)>=0 && judgeDate.compareTo((borderDate2))<=0)
				return true;
		}else {
			if(judgeDate.compareTo(borderDate1)<=0 && judgeDate.compareTo((borderDate2))>=0)
				return true;
		} 
		return false;
	}
	
	/**
	 * 判断日期是否在某一个时间段里面（闭区间）
	 * <p>即判断judgeDate是否在（borderDate1，borderDate1+month的最后一天）或（borderDate1+month的第一天，borderDate1）
	 * <p>e.g1：judgeDate=20150714, borderDate1=20150815, month=-1，方法将判断20150714是否在（20150701，20150815），返回true；
	 * <p>e.g2：judgeDate=20150916, borderDate1=20150815, month=1，方法将判断20150715是否在（20150815,20150930），返回true；
	 * @param judgeDate 需要判断的日期
	 * @param nowDate 时间段的边界
	 * @param month 时间段的月数，可以为正负
	 * @return 判断的结果
	 */
	public static boolean judgeDateIgnoreDay(Date judgeDate, Date borderDate1, int month) {
		if(judgeDate==null || borderDate1==null) {
			return false;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(borderDate1);
		if(month > 0) {			//month大于0，获取（borderDate1+month）的最后一天
			calendar.add(Calendar.MONTH, month+1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}else {
			calendar.add(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH, 1);			//month小于0，获取（borderDate1+month）的第一天
		}
		Date borderDate2 = calendar.getTime();
		if(month > 0) {
			if(judgeDate.compareTo(borderDate1)>=0 && judgeDate.compareTo((borderDate2))<=0)
				return true;
		}else {
			if(judgeDate.compareTo(borderDate1)<=0 && judgeDate.compareTo((borderDate2))>=0)
				return true;
		} 
		return false;
	}
	
	/**
	 * 计算相差的月份数
	 * @param date1 
	 * @param date2 
	 * @return 月份差数
	 */
	public static int computeMonthSpace(Date date1, Date date2) {
        int result = 0;
        int flag = 0;//日期标记
        try {
            if (date1 == null || date2 == null) {
                return -1;
            }

            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(date1);

            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(date2);

            if (objCalendarDate2.equals(objCalendarDate1))
                return 0;
            //确保日期顺序
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            //判断日期
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
                flag = 1;
            //计算月份差
            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
                result = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - flag) - objCalendarDate1.get(Calendar.MONTH);
            else
                result = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
