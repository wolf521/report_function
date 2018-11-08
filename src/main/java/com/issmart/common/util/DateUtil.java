package com.issmart.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;;

public class DateUtil {

	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private static SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
	
	/**
	 * 按照格式转换日期，默认yyyy-MM-dd HH:mm:ss
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date strToDate(String dateStr,String pattern){
		Date date =  null; 
		if(StringUtil.isNotEmpty(dateStr)){
			if(StringUtil.isNotEmpty(pattern)){
				sdf = new SimpleDateFormat(pattern);
			}
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 时间差（天数）
	 *
	 * @param maxDate
	 * @param minDate
	 * @return
	 * @throws Exception
	 */
	public static Integer getDaySpace(String maxDate, String minDate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar maxC = Calendar.getInstance();
		;
		Calendar minC = Calendar.getInstance();

		maxC.setTime(sdf.parse(maxDate));
		minC.setTime(sdf.parse(minDate));

		Long mm = ((maxC.getTime().getTime() / 1000) - (minC.getTime().getTime() / 1000)) / 3600 / 24;

		return mm.intValue();
	}

	public static String getDayAdd(String date, Integer num) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(sdf.parse(date));
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + num);

		return sdf.format(calendar.getTime());
	}

	public static String getDaySubtract(String date, Integer num) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(sdf.parse(date));
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - num);

		return sdf.format(calendar.getTime());
	}

	// 分钟加法
	public static String processMinuteAdd(Date date, Integer minute) {

		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date returnDate = new Date(date.getTime() + (1000 * 60 * minute));

		return sdfTime.format(returnDate);
	}

	// 分钟加法
	public static String processMinuteSubtract(Date date, Integer minute) {

		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date returnDate = new Date(date.getTime() - (1000 * 60 * minute));

		return sdfTime.format(returnDate);
	}

	// 分钟加法
	public static Long processMsSubtract(Date date, Integer ms) {

		return date.getTime() - ms;
	}

	// 秒加法
	public static String processSecondAdd(Date date, Integer second) {

		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date returnDate = new Date(date.getTime() + (1000 * second));

		return sdfTime.format(returnDate);
	}

	// 秒减法
	public static String processSecondSubtract(Date date, Integer second) {

		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date returnDate = new Date(date.getTime() - (1000 * second));

		return sdfTime.format(returnDate);
	}

	public static long dateDiff(Date minuend, Date subtrahend){
		Calendar calMinuend = Calendar.getInstance();
		calMinuend.setTime(minuend);
		Calendar calSubtrahend = Calendar.getInstance();
		calSubtrahend.setTime(subtrahend);
		long minuendDate = calMinuend.getTime().getTime(); //Date.getTime() 获得毫秒型日期
		long subtrahendDate = calSubtrahend.getTime().getTime(); //Date.getTime() 获得毫秒型日期
		long rslt = (minuendDate - subtrahendDate)/(1000 * 60);
		return rslt;
	}
}
