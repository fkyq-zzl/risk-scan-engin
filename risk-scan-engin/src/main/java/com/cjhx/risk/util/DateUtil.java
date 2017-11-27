package com.cjhx.risk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期转换工具类
 *
 * @author lujinfu
 * @date 2017年4月18日
 */
public class DateUtil {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	public static String formatToMis(Date date){
		SimpleDateFormat spd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return spd.format(date);
	}
	
	/**
	 * 获取日期的前一天日期
	 * 
	 * @param intDate 8位数字格式：20171027
	 * @return 8位数字格式：20171026
	 * @throws ParseException
	 */
	public static int getTheDayBeforeOfIntDate(int intDate) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date date = df.parse(String.valueOf(intDate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return Integer.valueOf(df.format(cal.getTime()));
	}
	
	public static int getDaysOffsetDate(Date date,int offset){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, offset);
		return Integer.valueOf(df.format(cal.getTime()));
	}
	
	/**
	 * 日期转换为 yyyyMMdd 格式数字
	 * @param date
	 * @return
	 */
	public static Integer formatToDayInt(Date date){
		SimpleDateFormat spd = new SimpleDateFormat("yyyyMMdd");
		return Integer.valueOf(spd.format(date));
	}
	
	/**
	 * 日期转换为 HHmmss 格式数字
	 * @param date
	 * @return
	 */
	public static Integer formatToTimeInt(Date date){
		SimpleDateFormat spd = new SimpleDateFormat("HHmmss");
		return Integer.valueOf(spd.format(date));
	}
	
	/**
	 * 校验yyyyMMdd格式字符是否能转换,不能转换则是格式有误
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean validateIsDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		sdf.setLenient(false);//严格转换
		try {
			sdf.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 日期天数差
	 * @param startDate 减数时间
	 * @param endDate 被减数时间
	 * @return
	 */
	public static Integer getDateDiff(String startDate,String endDate){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	    
	    try {
	    	long start = df.parse(startDate).getTime();
		    long end = df.parse(endDate).getTime();
		    int  diff = (int) ((start - end) / (1000 * 60 * 60 * 24));
		    return diff;
		} catch (ParseException e) {
			logger.error("日期转换异常："+e.getMessage());
			e.printStackTrace();
		}
	    return null;
	}
	
	/**
	 * 获取这个日期偏移多少天后的yyyyMMdd数字格式日期
	 * @param date 日期
	 * @param offset 偏移天数
	 * @return yyyyMMdd 格式数字
	 */
	public static Integer getOffsetDate(Date date,int offset){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, offset);
		return DateUtil.formatToDayInt(cal.getTime());
	}
	
	//Test
	public static Long getTimeStamp(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//sdf.setLenient(false);//严格转换
		try {
			return sdf.parse(dateStr).getTime();
		} catch (ParseException e) {
			return null;
		}
	}
	
	
	
}
