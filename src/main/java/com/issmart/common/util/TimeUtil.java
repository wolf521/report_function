package com.issmart.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.issmart.device.model.RequestParamOfBoothDetails;

public class TimeUtil {

	/**
	 * 处理时间
	 * 
	 * @param param
	 */
	public static void handleTime(RequestParamOfBoothDetails param){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(param.getEndTimeStamp()));
		if(param.getQuantumTime() < 24) {
			cal.add(Calendar.HOUR, -param.getQuantumTime());
			param.setStartTimeStamp(cal.getTimeInMillis() / 1000);
			param.setEndTimeStamp(param.getEndTimeStamp() / 1000);
		} else {
			cal.add(Calendar.HOUR, -cal.get(Calendar.HOUR_OF_DAY));
			cal.add(Calendar.MINUTE, -cal.get(Calendar.MINUTE));
			cal.set(Calendar.SECOND, 0);
			//param.setStartTimeStamp(cal.getTimeInMillis() / 1000);
			param.setStartTimeStamp(cal.getTimeInMillis() / 1000 - 8 * 60 * 60);
			cal.add(Calendar.HOUR, 24-cal.get(Calendar.HOUR_OF_DAY));
			//param.setEndTimeStamp(cal.getTimeInMillis() / 1000);
			param.setEndTimeStamp(cal.getTimeInMillis() / 1000 - 8 * 60 * 60);
		}
	}
	
	/**
	 * 处理时间
	 * 
	 * @param param
	 */
	public static List<Map<String, Object>> getDivisionTime(RequestParamOfBoothDetails param){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		long timeStamp = param.getStartTimeStamp()*1000;
		cal.setTime(new Date(timeStamp));
		long count = (param.getEndTimeStamp() - param.getStartTimeStamp())/60 - 1;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)+480);
		for(int i = 0;i <= count;i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("time", format.format(cal.getTime()));
			cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)+1);		
			list.add(map);
		}
		return list;
	}
}
