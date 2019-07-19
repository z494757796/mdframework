package cn.mdsoftware.mdframework.common.utils;

import cn.mdsoftware.mdframework.common.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 日期工具
 * @author Jax
 *
 */
public class DateUtils {
	
	private static Map<String, DateTimeFormatter> dateTimeFormatters = new HashMap<>();

	private static ZoneId defaultZone = ZoneId.systemDefault();

	public static Calendar thisSunday() {
		Calendar calendar = Calendar.getInstance();
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (1 == day_of_week) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day - 1);
		return calendar;
	}
	public static Calendar nextSunday() {
		Calendar calendar = thisSunday();
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		return calendar;
	}

	/**
	 * 获取指定格式的当前时间：为空时格式为yyyy-mm-dd HH:mm:ss
	 * @author chenssy
	 * @date Dec 30, 2013
	 * @param format
	 * @return Date
	 */
	public static Date getCurrentDate(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateS = sdf.format(new Date());
		Date date = null;
		try {
			date = sdf.parse(dateS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取指定格式的当前时间：为空时格式为yyyy-mm-dd HH:mm:ss
	 * @author chenssy
	 * @date Dec 30, 2013
	 * @param format
	 * @return Date
	 */
	public static Date getCurrentDate(String dateStr,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}



	/**
	 * 获取指定格式指定时间的日历
	 * @author chenssy
	 * @date Dec 30, 2013
	 * @param date 时间
	 * @param format 格式
	 * @return Calendar
	 */
	public static Calendar getCalendar(Date date,String format){
		if(date == null){
			date = getCurrentDate(format);
		}

		Calendar calender = Calendar.getInstance();
		calender.setTime(date);

		return calender;
	}

	/**
	 * 给指定日期增加天数，为空时默认当前时间
	 * @author chenssy
	 * @date Dec 31, 2013
	 * @param day 增加天数 正数相加、负数相减
	 * @param date 指定日期
	 * @param format 日期格式 为空默认 yyyy-mm-dd HH:mm:ss
	 * @return String
	 */
	public static String addDayToDate(int day,Date date,String format) {
		Calendar calendar = getCalendar(date, format);
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		calendar.add(Calendar.DATE, day);

		return sdf.format(calendar.getTime());
	}

	public static DateTimeFormatter getDateTimeFormatter(String pattern) {
		DateTimeFormatter f = dateTimeFormatters.get(pattern);
		if (f == null) {
			f = DateTimeFormatter.ofPattern(pattern);
			dateTimeFormatters.put(pattern, f);
		}
		return f;
	}
	
	public static Date from(Instant i) {
		return new Date(i.toEpochMilli());
	}

	public static Date from(LocalDateTime ldt) {
		return from(ldt.atZone(defaultZone).toInstant());
	}
	
	public static Date from(String time, String pattern) {
		LocalDateTime ldt = LocalDateTime.parse(time, getDateTimeFormatter(pattern));
		return from(ldt);
	}

	public static LocalDateTime from(Date d) {
		return LocalDateTime.ofInstant(d.toInstant(), defaultZone);
	}

	public static LocalDateTime from(long l) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(l), defaultZone);
	}
	
	public static long toMillis(String dateTime, String... pattern) {
		String p = Constants.DatetimePattern.DP10;
		if (pattern.length != 0) {
			p = pattern[0];
		}
		DateTimeFormatter f = getDateTimeFormatter(p);
		LocalDateTime ldt = LocalDateTime.parse(dateTime, f);
		return ldt.atZone(defaultZone).toInstant().toEpochMilli();
	}

	public static String toDate(long mills, String... pattern) {
		String p = Constants.DatetimePattern.DP10;
		if (pattern.length != 0) {
			p = pattern[0];
		}
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(mills), defaultZone);
		DateTimeFormatter f = getDateTimeFormatter(p);
		return ldt.format(f);
	}
	
	public static String toDate(Date time, String pattern) {
		return toDate(time.getTime(),pattern);
	}
	
	public static void main(String[] args) {
//		System.out.println(addDayToDate(365,new Date(),"yyyy-MM-dd"));


		System.out.println(compareDate("2018-07-23","2018-07-20",0,Constants.DatetimePattern.DP10));
	}



	/**
	 * 比较两个日期相隔多少天(月、年) <br>
	 * 例：<br>
	 * &nbsp;compareDate("2009-09-12", null, 0);//比较天 <br>
	 * &nbsp;compareDate("2009-09-12", null, 1);//比较月 <br>
	 * &nbsp;compareDate("2009-09-12", null, 2);//比较年 <br>
	 *
	 * @author chenssy
	 * @date Dec 31, 2013
	 * @param startDay 需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12
	 * @param endDay 被比较的时间  为空(null)则为当前时间
	 * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年
	 * @return int
	 */
	public static int compareDate(String startDay,String endDay,int stype,String simpleDateFormat) {
		int n = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(simpleDateFormat);

		String formatStyle = simpleDateFormat;
		if(1 == stype){
			formatStyle = "yyyy-MM";
		}else if(2 == stype){
			formatStyle = "yyyy";
		}

		endDay = endDay==null ? toDate(new Date(),simpleDateFormat) : endDay;

		DateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(startDay));
			c2.setTime(df.parse(endDay));
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (!c1.after(c2)) {                   // 循环对比，直到相等，n 就是所要的结果
			n++;
			if(stype==1){
				c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1
			}
			else{
				c1.add(Calendar.DATE, 1);           // 比较天数，日期+1
			}
		}
		n = n-1;
		if(stype==2){
			n = (int)n/365;
		}
		return n;
	}

	//获取当月所有星期天
	public static List<String> sundayOfMonth(Integer year,Integer month) throws ParseException {
		List<String> sundayList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DatetimePattern.DP10);

		month = month - 1;
		Calendar calendar = new GregorianCalendar(year, month, 1);
		int i = calendar.get(Calendar.WEEK_OF_YEAR);
		while (calendar.get(Calendar.MONTH) < month + 1) {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			if (calendar.get(Calendar.MONTH) == month) {
				Date day = calendar.getTime();
				sundayList.add(sdf.format(day));
			}
			calendar.set(Calendar.WEEK_OF_YEAR, ++i);
//            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
//            if (calendar.get(Calendar.MONTH) == month) {
//                System.out.printf("星期六：%tF%n", calendar);
//            }
		}

		return sundayList;
	}
}
