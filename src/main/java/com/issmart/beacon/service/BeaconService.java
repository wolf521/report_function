package com.issmart.beacon.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.issmart.beacon.model.BeaconActive;
import com.issmart.beacon.model.BeaconActiveInfo;

public interface BeaconService {
	
	/**
	 * 查询用户总体指标数据
	 * 
	 * @param unitId
	 * @return
	 */
	Map<String,Object> queryOverallIndicators(String unitId);
	
	/**
	 * 查询用户活跃指数
	 * 
	 * @param unitId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<BeaconActiveInfo> queryMemberActives(String unitId,int pageNo, int pageSize);
	
	/**
	 * 查询用户访问轨迹
	 * 
	 * @param unitId
	 * @param beaconMac
	 * @return
	 */
	LinkedList<BeaconActive> queryActive(String unitId,String beaconMac);
	
	Map<String,String> queryInterestedBooth(String unitId, String beaconMac);
	
	List<BeaconActive> queryMemberNameByBeaconMac(String unitId);
	
	List<BeaconActive>  queryActiveList(String unitId,String beaconMac);
	
	Integer getTotal(String unitId);
	
	
	/**
	 * 查询当前beacon扫一扫数据
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<Map<String,Object>> queryMemberByNearBy (String unitId,String beaconId,Integer minutes,Integer rssi);


	
	/**
	 * 查询智慧互动分析
	 * 
	 * @param unitId
	 * @return
	 */
	List<Map<String,Object>> queryInteractionAnalysis(String unitId);
}
