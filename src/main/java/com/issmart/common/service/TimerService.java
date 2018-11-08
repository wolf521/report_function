package com.issmart.common.service;

public interface TimerService {
	
	/**
	 * 定时处理场地报表数据
	 * 
	 * @param unitId
	 * @return
	 */
	void opeBoothActiveTimer();
	
	/**
	 * 定时处理用户报表数据
	 * 
	 * @param unitId
	 * @return
	 */
	void opeMemberActiveTimer();
}
