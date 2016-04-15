package com.ehaoyao.logistics.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	
	public final static String DATEFORMATYMD = "yyyy-MM-dd";
	public final static String DATEFORMATHMS = "yyyy-MM-dd HH:mm:ss";
	public final static String DATEFORMATHMSS = "yyyy-MM-dd HH:mm:ss:SSS";
	public final static String DATEFORMATCH = "yyyy年MM月dd日";
	public final static String DATEFORMATMCH = "yyyy年MM月dd日 HH时mm分ss秒";
	public static DateFormat FORMAT_TIME = null;

	public static Calendar c = Calendar.getInstance();;

	public DateUtil() {
	}

	/**
	 * 判断是否闰年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean LeapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDayOfBegin(Date date) {
		String temp = getDate(date, 2, "");
		if (temp.substring(11, temp.length()).equals("00:00:00")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到格式化后的日期
	 * 
	 * @param date需要格式化的日期
	 * @param marked
	 *            标示格式 （1:yyyy-MM-dd; 2:yyyy-MM-dd HH:mm:ss; 3:yyyy年MM月dd日;
	 *            4:yyyy年MM月dd日 HH时mm分ss秒 5:自定义格式） 当marked=5时，参数dateFormat为必填项
	 *            默认状态为2
	 * */
	public static String getDate(Date date, int marked, String dateFormat) {
		switch (marked) {
		case 1:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATYMD);
			break;
		case 2:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATHMS);
			break;
		case 3:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATCH);
			break;
		case 4:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATMCH);
			break;
		case 5:
			FORMAT_TIME = new SimpleDateFormat(dateFormat);
			break;
		case 6:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATHMSS);
			break;
		default:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATHMS);
			break;
		}
		if (date == null)
			date = new Date();
		return FORMAT_TIME.format(date);
	}

	/**
	 * 获得当前的年
	 * */
	public static int getYear() {
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获得当前月份
	 * 
	 * @return
	 */
	public static int getMonth() {
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当期日期的前一个月
	 * */
	public static int getPreMoth() {
		c.clear();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得今天在本年的第几天
	 * 
	 * @return
	 */
	public static int getDayOfYear() {
		return c.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获得今天在本月的第几天(获得当前日)
	 * 
	 * @return
	 */
	public static int getDayOfMonth() {
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得今天在本周的第几天
	 * 
	 * @return
	 */
	public static int getDayOfWeek() {
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获得今天是这个月的第几周
	 * 
	 * @return
	 */
	public static int getWeekOfMonth() {
		return c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}

	/**
	 * 得到二个日期间的间隔毫秒数
	 */
	public static long getTwoDayTimes(String sj1, String sj2, boolean second) {
		SimpleDateFormat myFormatter;
		if (second)
			myFormatter = new SimpleDateFormat(DATEFORMATHMS);
		else
			myFormatter = new SimpleDateFormat(DATEFORMATYMD);
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = date.getTime() - mydate.getTime();
		} catch (Exception e) {
		}
		return day;
	}

	/**
	 * 得到二个日期间的间隔毫秒数 sj1-sj2
	 */
	public static long getTwoDayTimes(Date sj1, Date sj2) {
		long day = 0;
		try {
			day = sj1.getTime() - sj2.getTime();
		} catch (Exception e) {
		}
		return day;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return date1-date2
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		long day = getTwoDayTimes(date1, date2, false) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return date1-date2
	 */
	public static long getDays(Date date1, Date date2) {
		if (date1 == null)
			return 0;
		if (date2 == null)
			return 0;
		// 转换为标准时间
		long day = getTwoDayTimes(date1, date2) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 计算当月最后一天,返回字符串
	 * */
	public static String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMATYMD);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得本年有多少天
	 * */
	public static int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	/**
	 * 获取星期几
	 * */
	public static String getWeek(Date date) {
		Calendar c1 = Calendar.getInstance();
		if (date != null) {
			c1.setTime(date);
		}
		return new SimpleDateFormat("EEEE").format(c1.getTime());
	}

	/**
	 * 获取星期几
	 * 
	 * @param strDate
	 *            字符串.
	 * @param marked
	 *            标示格式 （1:yyyy-MM-dd; 2:yyyy-MM-dd HH:mm:ss; 3:yyyy年MM月dd日;
	 *            4:yyyy年MM月dd日 HH时mm分ss秒 5:自定义格式） 当marked=5时，参数dateFormat为必填项
	 *            默认状态为2
	 * @param format
	 *            转换格式如:"yyyy-MM-dd HH:mm:ss"
	 * */
	public static String getWeek(String strDate, int marked, String format) {
		return getWeek(parseDate(strDate, marked, format));
	}

	// 获取下星期一
	public static String getNextMonday(String strDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(parseDate(strDate, 1, ""));
		int mondayPlus = c1.get(Calendar.DAY_OF_WEEK) - 1;
		if (mondayPlus == 0) {
			mondayPlus = 7;
		}
		c1.add(GregorianCalendar.DATE, 8 - mondayPlus);
		Date monday = c1.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取当前日期的上一个星期一日期
	 * @param strDate
	 * @return
	 */
	public static String getPreMonday() {
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_MONTH, -1);
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		Date firstWeek = calendar.getTime();
		String preMonday=DateUtil.getDate(firstWeek, 1, null);
		return preMonday;
	}

	/**
	 * 根据当前时间的毫秒数（相对于January 1, 1970 00:00:00），取当前时间的字符串
	 * 
	 * @param marked
	 *            标示格式 （1:yyyy-MM-dd; 2:yyyy-MM-dd HH:mm:ss; 3:yyyy年MM月dd日;
	 *            4:yyyy年MM月dd日 HH时mm分ss秒 5:自定义格式） 当marked=5时，参数dateFormat为必填项
	 *            默认状态为2
	 * @param format
	 *            转换格式如:"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String changTimeMillisToStr(Long longDate, int marked,
			String format) {
		switch (marked) {
		case 1:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATYMD);
			break;
		case 2:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATHMS);
			break;
		case 3:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATCH);
			break;
		case 4:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATMCH);
			break;
		case 5:
			FORMAT_TIME = new SimpleDateFormat(format);
			break;
		default:
			FORMAT_TIME = new SimpleDateFormat(DATEFORMATHMS);
			break;
		}
		return FORMAT_TIME.format(longDate);
	}

	/**
	 * 格式化字符串为日期的函数.
	 * 
	 * @param strDate
	 *            字符串.
	 * @param marked
	 *            标示格式 （1:yyyy-MM-dd; 2:yyyy-MM-dd HH:mm:ss; 3:yyyy年MM月dd日;
	 *            4:yyyy年MM月dd日 HH时mm分ss秒 5:自定义格式） 当marked=5时，参数dateFormat为必填项
	 *            默认状态为2
	 * @param format
	 *            转换格式如:"yyyy-MM-dd HH:mm:ss"
	 * @return 字符串包含的日期,失败则返回当前日期
	 */
	public static Date parseDate(String strDate, int marked, String format) {
		try {
			if (strDate == null || strDate.equals("") || strDate.equals("null")
					|| strDate.trim().length() == 0) {
				return new Date();
			}
			switch (marked) {
			case 1:
				FORMAT_TIME = new SimpleDateFormat(DATEFORMATYMD);
				break;
			case 2:
				FORMAT_TIME = new SimpleDateFormat(DATEFORMATHMS);
				break;
			case 3:
				FORMAT_TIME = new SimpleDateFormat(DATEFORMATCH);
				break;
			case 4:
				FORMAT_TIME = new SimpleDateFormat(DATEFORMATMCH);
				break;
			case 5:
				FORMAT_TIME = new SimpleDateFormat(format);
				break;
			default:
				FORMAT_TIME = new SimpleDateFormat(DATEFORMATHMS);
				break;
			}
			return FORMAT_TIME.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static int getYear(String strDate, int marked, String format) {
		Date d = parseDate(strDate, marked, format);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.YEAR);
	}

	public static int getMonth(String strDate, int marked, String format) {
		Date d = parseDate(strDate, marked, format);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 将字符串的 yyyy-mm-dd hh:mm:ss 翻译成数据库中的Long型
	 * 
	 * @param strDate
	 * @param marked
	 *            标示格式 （1:yyyy-MM-dd; 2:yyyy-MM-dd HH:mm:ss; 3:yyyy年MM月dd日;
	 *            4:yyyy年MM月dd日 HH时mm分ss秒 5:自定义格式） 当marked=5时，参数dateFormat为必填项
	 *            默认状态为2
	 * @param 自定义格式
	 * @return
	 */
	public static Long parseString2Long(String strDate, int marked,
			String format) {
		return new Long(parseDate(strDate, marked, format).getTime());
	}

	public static Long parseDate2Long(Date date) {
		return new Long(date.getTime());
	}

	/**
	 * 比较当前时间和给定的时间（只比较时、分、秒）
	 * */
	public static int compHMS(String hmsStart, String hmsEnd) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		Date date = c.getTime();
		hmsStart = Integer.toString(year) + "-" + Integer.toString(month) + "-"
				+ Integer.toString(day) + " " + hmsStart;
		hmsEnd = Integer.toString(year) + "-" + Integer.toString(month) + "-"
				+ Integer.toString(day) + " " + hmsEnd;
		Date dateStart = parseDate(hmsStart, 2, "");
		Date dateEnd = parseDate(hmsEnd, 2, "");
		if (date.getTime() >= dateStart.getTime()
				&& date.getTime() <= dateEnd.getTime()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 判断输入的字符串是否为日期格式(2011-04-04 10:36:00)
	 * 
	 * @param str需要判断的字符串
	 * @return 是返回true，否则返回false
	 * */
	public static boolean isDateTime(String str) {
		String regex = "^(((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(str);
		return m.matches();
	}

	/**
	 * 判断是否为日期(例如2011-04-04)
	 * 
	 * @param str需要判断的字符串
	 * @return 是返回true，否则返回false
	 * */
	public static boolean isDate(String str) {
		String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-9]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(str);
		return m.matches();
	}

	/**
	 * 判断是否为日期(例如10:41:12)
	 * 
	 * @param str需要判断的字符串
	 * @return 是返回true，否则返回false
	 * */
	public static boolean isTime(String str) {
		String regex = "^(20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(str);
		return m.matches();
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得本周一的日期
	public static String getMondayOFWeek() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得本周星期日的日期
	public static String getCurrentWeekday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 取得指定日期所在周的第一天
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 取得指定日期所在周的最后一天
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	public static Date getNowDate(int marked, String format) {
		return parseDate(getDate(new Date(), marked, format), marked, format);
	}

	public static Date formatDate(Date date, String yymm) {
		SimpleDateFormat sdf;
		if (yymm == null) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			sdf = new SimpleDateFormat(yymm);
		}
		String s_date = sdf.format(date);
		try {
			return sdf.parse(s_date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMATHMS);
		String s_date = sdf.format(date);
		try {
			return sdf.parse(s_date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	// 返回某间隔时间的日期
	public static List<String> returnDatestr(String begin, String end, int num) {
		List<String> datelist = new ArrayList<String>();
		Long begintemp = parseString2Long(begin, 1, "");
		Long endtemp = parseString2Long(end, 1, "");
		Long timetemp = (endtemp - begintemp) / num;
		while (begintemp <= endtemp) {
			begintemp = begintemp + timetemp;
			datelist.add(changTimeMillisToStr(begintemp, 1, ""));
		}
		return datelist;
	}

	// 返回当前日期的下一天的日期
	public static String returnNext(String datestr) {
		Long begintemp = parseString2Long(datestr, 1, "");
		begintemp = begintemp + (24 * 60 * 60 * 1000);
		return changTimeMillisToStr(begintemp, 1, "");
	}

	public static Date returnPre(Date datestr) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(datestr);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	public static Date returnPre(long datestr) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(datestr);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 指定返回当天的前几天
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date returnPre(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 得到本月的第一天
	 * 
	 * @return
	 */
	public static String getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(calendar.getTime());
	}
	/**
	 * 得到当前日期上个月的第一天日期
	 * 
	 * @return
	 */
	public static String getPreMonthFirstDay() {
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = calendar.getTime();
		String preMonthFirstDay=DateUtil.getDate(firstDate, 1, null);
		return preMonthFirstDay;
	}
	/**
	 * 得到本月的最后一天
	 * 
	 * @return
	 */
	public static String getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(calendar.getTime());
	}

	/**
	 * 得到第二天的日期
	 * 
	 * @return
	 */
	public static Date getNextDay() {
		// 获取当前日期的第二天的日期
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return date;
	}
	
	/**
	 * 获取当前时间的后几个小时
	 * @param ss 秒为单位 
	 * @return
	 */
	public static Date getNextHour(int ss){
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, ss);
		return c.getTime();
	}
	
	/**
	 * 获取当前时间的前几个小时
	 * @param hh 小时为单位
	 * @return
	 */
	public static Date getPreHour(int hh){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hh);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前时间的前几分钟
	 * @param min 分钟为单位
	 * @return
	 */
	public static Date getPreMinute(int min){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - min);
		return calendar.getTime();
	}
}
