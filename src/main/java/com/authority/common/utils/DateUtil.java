package com.authority.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间工具类
 * 
 * @author yu_qhai
 * 
 */
public class DateUtil {
	private static String deaultDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	private static Logger log = LoggerFactory.getLogger(DateUtil.class);
	private static String deaultTimeFormat = "HH:mm:ss";
	private static String deaultDateFormat = "yyyy-MM-dd";
	private int year;
	private int month;
	GregorianCalendar gc = new GregorianCalendar();

	public static Date getCurrentTime() {
		Date currentTime = new Date(new Date().getTime());

		return currentTime;
	}

	/**
	 * 获取当前时间-TimeStamp
	 * @return
	 */
	public static String getTimeStamp() {
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat(deaultDateTimeFormat);
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 把时间转换成字符串
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		String retDate = dateToOriStr(date);
		if (retDate.equals("1900-01-01")) {
			retDate = "";
		}
		return retDate;
	}

	private static String dateToOriStr(Date date) {
		if (date == null)
			return "1900-01-01";
		SimpleDateFormat dateFormat = new SimpleDateFormat(deaultDateFormat);

		return dateFormat.format(date);
	}

	/**
	 * 把事件转换成对应类型的字符串
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateToStr(Date date, String formatStr) {
		if (date == null)
			return "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);

		return dateFormat.format(date);
	}

	/**
	 * 把字符串转换成Date
	 * @param str
	 * @return
	 */
	public static Date strToDate(String str) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(deaultDateFormat);
		GregorianCalendar calendar;
		try {
			return dateFormat.parse(str);
		} catch (ParseException ex) {
			calendar = new GregorianCalendar();
			calendar.set(1900, 1, 1);
		}
		return calendar.getTime();
	}

	/**
	 * 把字符串转换成Date
	 * @param str
	 * @return
	 */
	public static Date strToDateTime(String str) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(deaultDateTimeFormat);
		GregorianCalendar calendar;
		try {
			return dateFormat.parse(str);
		} catch (ParseException ex) {
			calendar = new GregorianCalendar();
			calendar.set(1900, 1, 1);
		}
		return calendar.getTime();
	}
	/**
	 * 在给定的时间内加上几天
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date,int days){
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);  
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	/**
	 * 获取给定时间的去年的时间
	 * @param date		事件字符串
	 * @return
	 */
	public static synchronized String getLastYear(String date) {
		GregorianCalendar gregoriancalendar = (GregorianCalendar) Calendar
				.getInstance();
		gregoriancalendar.setTime(strToDate(date));
		gregoriancalendar.add(1, -1);
		return dateToOriStr(gregoriancalendar.getTime());
	}

	/**
	 * date1 与 date2 距离几天 天数>=1（即date2晚于date1）
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static synchronized int toCompare(String date1, String date2) {
		GregorianCalendar gregoriancalendar = (GregorianCalendar) Calendar
				.getInstance();
		gregoriancalendar.setTime(strToDate(date1));
		GregorianCalendar gregoriancalendar1 = (GregorianCalendar) Calendar
				.getInstance();
		gregoriancalendar1.setTime(strToDate(date2));
		int day = 0;
		int year = gregoriancalendar1.get(1) - gregoriancalendar.get(1);
		if (year == 0) {
			day = gregoriancalendar1.get(6) - gregoriancalendar.get(6);
		}
		if (year > 0) {
			int i = 0;
			if (gregoriancalendar.isLeapYear(gregoriancalendar.get(1)))
				day = 366 - gregoriancalendar.get(6);
			else {
				day = 365 - gregoriancalendar.get(6);
			}
			for (i = 1; i < year; ++i) {
				int sd = gregoriancalendar.get(1) + i;
				if (gregoriancalendar.isLeapYear(sd))
					day += 366;
				else {
					day += 365;
				}
			}
			if (i == year) {
				day += gregoriancalendar1.get(6);
			}
		}
		return (day + 1);
	}

	/**
	 * 给定时间的前几天		YYYY-MM-DD
	 * @param date		时间字符串
	 * @param j			天数
	 * @return
	 */
	public static synchronized String getHBDate(String date, int j) {
		GregorianCalendar gregoriancalendar = (GregorianCalendar) Calendar
				.getInstance();
		gregoriancalendar.setTime(strToDate(date));
		gregoriancalendar.add(5, -j);
		return dateToOriStr(gregoriancalendar.getTime());
	}

	/**
	 * 给定时间的前一天
	 * @param date
	 * @return
	 */
	public static synchronized Date getFirstDayOfMonth(Date date) {
		GregorianCalendar gregoriancalendar = (GregorianCalendar) Calendar
				.getInstance();
		gregoriancalendar.setTime(date);
		gregoriancalendar.set(5, 1);
		return gregoriancalendar.getTime();
	}

	/**
	 * 这个月有几天
	 * @param theMonth	月份
	 * @return
	 */
	public int daysInMonth(int theMonth) {
		int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		daysInMonths[1] += ((this.gc.isLeapYear(this.year)) ? 1 : 0);
		return daysInMonths[(theMonth - 1)];
	}

	/**
	 * 返回给定时间的最后一天时间	
	 * @param thisDate		YYYY-MM-DD
	 * @return
	 */
	public String lastDayInMonth(String thisDate) {
		this.month = Integer.parseInt(thisDate.substring(5, 7));
		this.year = Integer.parseInt(thisDate.substring(0, 4));
		this.gc = new GregorianCalendar(this.year, this.month, 1);
		int lastday = daysInMonth(this.month);
		return thisDate.substring(0, 8) + lastday;
	}

	

	/**
	 * 获取给定时间的前两个月的日期
	 * @param thisDate		YYYY-MM-DD
	 * @return
	 */
	public static String getMonthbeforeLast(String thisDate) {
		int monthtemp = Integer.parseInt(thisDate.substring(5, 7));
		int yeartemp = Integer.parseInt(thisDate.substring(0, 4));
		Calendar calendar = Calendar.getInstance();
		calendar.set(yeartemp, monthtemp - 3, 1);
		String mydate = dateToOriStr(calendar.getTime());
		return mydate;
	}
	/**
	 * 判断两个时间是否是同一天
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean theSameDay(Date time1,Date time2){
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(time1);
		calendar2.setTime(time2);
		return calendar1.get(Calendar.DAY_OF_MONTH)==calendar2.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 输入：2012-08-12 12:21:22，输出：2012-07-01
	 * @param thisDate
	 * @return
	 */
	public static String getMonthbefore(String thisDate) {
		int monthtemp = Integer.parseInt(thisDate.substring(5, 7));
		int yeartemp = Integer.parseInt(thisDate.substring(0, 4));
		Calendar calendar = Calendar.getInstance();
		calendar.set(yeartemp, monthtemp - 2, 1);
		String mydate = dateToOriStr(calendar.getTime());
		return mydate;
	}
	
	/**
	 * 输入：2012-08-12 12:21:22，输出：2012-09-01
	 * @param thisDate
	 * @return
	 */
	public static String getMonthafter(String thisDate) {
		int monthtemp = Integer.parseInt(thisDate.substring(5, 7));
		int yeartemp = Integer.parseInt(thisDate.substring(0, 4));
		Calendar calendar = Calendar.getInstance();
		calendar.set(yeartemp, monthtemp, 1);
		String mydate = dateToOriStr(calendar.getTime());
		return mydate;
	}

	/**
	 * 获取给定时间的前一天的时间
	 * @param date		给定时间
	 * @return
	 */
	public static synchronized Date getYesterday(Date date) {
		GregorianCalendar gregoriancalendar = (GregorianCalendar) Calendar
				.getInstance();
		gregoriancalendar.setTime(date);
		gregoriancalendar.add(5, -1);
		return gregoriancalendar.getTime();
	}

	/**
	 * 获取给定时间的前两天的时间
	 * @param date
	 * @return
	 */
	public static synchronized Date getDayBeforeYesterday(Date date) {
		GregorianCalendar gregoriancalendar = (GregorianCalendar) Calendar
				.getInstance();
		gregoriancalendar.setTime(date);
		gregoriancalendar.add(5, -2);
		return gregoriancalendar.getTime();
	}

	/**
	 * 根据给定类型把字符串转换成时间
	 * @param str			时间字符串
	 * @param formatStr		时间类型
	 * @return
	 */
	public static Date strToDate(String str, String formatStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
		GregorianCalendar calendar;
		try {
			return dateFormat.parse(str);
		} catch (ParseException ex) {
			calendar = new GregorianCalendar();
			calendar.set(1900, 1, 1);
		}
		return calendar.getTime();
	}

	/**
	 * 把时间转成time
	 * @param time
	 * @return
	 */
	public static String timeToStr(Date time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(deaultDateTimeFormat);
		return dateFormat.format(time);
	}
	public static String dateToTime(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat(deaultTimeFormat);
		return dateFormat.format(date);
	}
	/**
	 * 获取当前时间time
	 * @return
	 */
	public static String timeToStr() {
		return timeToStr(new Date());
	}
	
	/**
	 * 时间差多少分钟
	 * @param last
	 * @param current
	 * @return
	 */
	public static double betweenmm(Date last,Date current,boolean all){
		long between  = current.getTime()-last.getTime();
	    long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        if(all){
        	return ((between / (60 * 1000)) + day * 24 * 60 + hour * 60);
        }else{
        	return ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        }
	}
	public static boolean diffmm(Date last,Date current){
		Calendar cale1 = Calendar.getInstance();
		cale1.setTime(last);
		Calendar cale2 = Calendar.getInstance();
		cale2.setTime(current);
		return cale1.get(Calendar.MINUTE)==cale2.get(Calendar.MINUTE);
	}
	/**
	 * 时间差多少小时
	 * @param last
	 * @param current
	 * @return
	 */
	public static double betweenHH(Date last,Date current,boolean all){
		long between  = current.getTime()-last.getTime();
	    long day = between / (24 * 60 * 60 * 1000);
        if(all){
        	return (between / (60 * 60 * 1000) + day * 24);
        }else{
        	return (between / (60 * 60 * 1000) - day * 24);
        }
	}
	/**
	 * 与当前时间差几天
	 * @param date
	 * @return
	 */
	public static int betweendd(Date last,Date date){
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		Calendar cales = Calendar.getInstance();
		cales.setTime(last);
//		return cale.get(Calendar.DAY_OF_YEAR)-cales.get(Calendar.DAY_OF_YEAR);
		long l = cales.getTimeInMillis() - cale.getTimeInMillis();
		int days = new Long(l / (1000 * 60 * 60 * 24)).intValue()+1;
		return days;
	}
	
	/**
	 * 判断给定的时间是否在start、end时间段内
	 * @param date
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean betweenmm(Date date ,String start,String end){
		try{
			if(date.after(HHmmToDate(date,start)) && date.before(HHmmToDate(date,end))){
				return true;
			}
		}catch (Exception e) {
			log.error(start+":"+end+"  时间异常");
		}
		return false;
	}
	/**
	 * @param date
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean $betweenmm(Date date ,String start,String end){
		try{
			if(date.before(HHmmToDate(date,start)))//在开始时间之前
				return false;
			if(date.after(HHmmToDate(date,end)))//在结束时间之前
				return false;
			return true;
		}catch (Exception e) {
			log.error(start+":"+end+"  时间异常");
		}
		return false;
	}
	public static boolean sameDay(String time){
		return betweendd(strToDateTime(time),new Date())==0;
		
	}
	/**
	 * 把时分转化为时间
	 * @param HHmm
	 * @return
	 * @throws RuntimeException
	 */
	public static Date HHmmToDate(Date date,String HHmm)throws RuntimeException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(HHmm.substring(0,2)));
		calendar.set(Calendar.MINUTE, Integer.parseInt(HHmm.substring(3,4)));
		return calendar.getTime();
		
	}
	public static String yyyyMM(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		return df.format(date);
	}
	
	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		return date;
	}
	public static Date getNextMonth(Date date,int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		date = calendar.getTime();
		return date;
	}
	
	
	/**
	 * 返回给定时间所在的天、星期、月、季、年的起止时间
	 * @param date		开始时间
	 * @param mode		类型：day/week/month/season/year
	 * @return			时间数组：[0]开始时间;[1]结束时间;
	 */
	public static Date[] getBegainAndEndDate(Date date, String mode) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date begin = new Date();
		Date end = new Date();
		if (mode == null || mode.equalsIgnoreCase("日")
				|| mode.equalsIgnoreCase("day")) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			begin = calendar.getTime();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			end = calendar.getTime();
		}else if("分".equals(mode) ||"minute".equalsIgnoreCase(mode)){
			calendar.add(Calendar.MINUTE, -30);
			begin = calendar.getTime();
			calendar.add(Calendar.MINUTE, 60);
			end = calendar.getTime();
		} else if (mode.equalsIgnoreCase("周") || mode.equalsIgnoreCase("week")) {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			begin = calendar.getTime();
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			calendar.add(Calendar.DAY_OF_WEEK, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			end = calendar.getTime();
		} else if (mode.equalsIgnoreCase("月") || mode.equalsIgnoreCase("month")
				|| mode.equalsIgnoreCase("0")) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			begin = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			end = calendar.getTime();
		} else if (mode.equalsIgnoreCase("季")
				|| mode.equalsIgnoreCase("season")) {
			int month = calendar.get(Calendar.MONTH) + 1;
			if (month % 3 == 0) {// 季度结束月 6(3,4,5)
				calendar.set(Calendar.MONTH, month - 3);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				begin = calendar.getTime();
				calendar.set(Calendar.MONTH, month - 1);
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				end = calendar.getTime();
			} else if (month % 3 == 1) {// 季度起始月 4(3,4,5)
				calendar.set(Calendar.MONTH, month - 1);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				begin = calendar.getTime();
				calendar.set(Calendar.MONTH, month + 1);
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				end = calendar.getTime();
			} else if (month % 3 == 2) {// 季度中间月 5(3,4,5)
				calendar.set(Calendar.MONTH, month - 2);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				begin = calendar.getTime();
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				end = calendar.getTime();
			}
		} else if (mode.equalsIgnoreCase("年") || mode.equalsIgnoreCase("year")
				|| mode.equalsIgnoreCase("1")) {
			calendar.set(Calendar.DAY_OF_YEAR, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			begin = calendar.getTime();
			calendar.set(Calendar.DAY_OF_YEAR,
					calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			end = calendar.getTime();
		}
		return new Date[] { begin, end };
	}

	/**
	 * 根据时间获取上一天/周/月/季/年的 开始日期
	 * @param date		跟定的时间
	 * @param mode		类型同方法 {@link getBegainAndEndDate}
	 * @return
	 */
	public static Date getBeforeDate(Date date,String mode){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date begin = new Date();
		if (mode == null || mode.equalsIgnoreCase("日")
				|| mode.equalsIgnoreCase("day")) {
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			begin = calendar.getTime();
		} else if (mode.equalsIgnoreCase("周") || mode.equalsIgnoreCase("week")) {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			calendar.add(Calendar.WEEK_OF_YEAR, -1);
			begin = calendar.getTime();
		} else if (mode.equalsIgnoreCase("月") || mode.equalsIgnoreCase("month")
				|| mode.equalsIgnoreCase("0")) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, -1);
			begin = calendar.getTime();
		} else if (mode.equalsIgnoreCase("季")
				|| mode.equalsIgnoreCase("season")) {
			int month = calendar.get(Calendar.MONTH) + 1 -3;
			if (month % 3 == 0) {// 季度结束月 6(3,4,5)
				calendar.set(Calendar.MONTH, month - 3);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
			} else if (month % 3 == 1) {// 季度起始月 4(3,4,5)
				calendar.set(Calendar.MONTH, month - 1);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
			} else if (month % 3 == 2) {// 季度中间月 5(3,4,5)
				calendar.set(Calendar.MONTH, month - 2);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
			}
			begin = calendar.getTime();
		} else if (mode.equalsIgnoreCase("年") || mode.equalsIgnoreCase("year")
				|| mode.equalsIgnoreCase("1")) {
			calendar.set(Calendar.DAY_OF_YEAR, 1);
			calendar.add(Calendar.YEAR, -1);
			begin = calendar.getTime();
		}
		return begin;
	}
	
	/**
	 * 根据时间获取下一天/周/月/季/年的 循环周期的时间
	 * @param date		跟定的时间
	 * @param mode		类型同方法 {@link getBegainAndEndDate}
	 * @return
	 */
	public static Date getNextCycleDate(Date date,String mode){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date begin = new Date();
		if (mode == null || mode.equalsIgnoreCase("日")
				|| mode.equalsIgnoreCase("day")) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			begin = calendar.getTime();
		} else if (mode.equalsIgnoreCase("周") || mode.equalsIgnoreCase("week")) {
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			begin = calendar.getTime();
		} else if (mode.equalsIgnoreCase("月") || mode.equalsIgnoreCase("month")
				|| mode.equalsIgnoreCase("0")) {
			calendar.add(Calendar.MONTH, 1);
			begin = calendar.getTime();
		} else if (mode.equalsIgnoreCase("季")
				|| mode.equalsIgnoreCase("season")) {
			calendar.add(Calendar.MONTH, 3);
			begin = calendar.getTime();
		} else if (mode.equalsIgnoreCase("年") || mode.equalsIgnoreCase("year")
				|| mode.equalsIgnoreCase("1")) {
			calendar.add(Calendar.YEAR, 1);
			begin = calendar.getTime();
		}
		return begin;
	}
}