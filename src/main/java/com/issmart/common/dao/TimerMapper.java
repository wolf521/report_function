package com.issmart.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.issmart.beacon.model.BeaconActive;
import com.issmart.beacon.model.BeaconActiveInfo;
import com.issmart.device.model.DeviceBaseInfo;

public interface TimerMapper {
	
	
	/**
	 * 查询场地总体指标
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	Map<String,Object> queryBoothIndex(@Param("tableName") String tableName);

	/**
	 * 查询场地活跃指数
	 * 
	 * @param unitId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<DeviceBaseInfo> queryDeviceHot(@Param("tableName") String tableName,@Param("unitId") String unitId);

	/**
	 * 获取所有的展台Id
	 * 
	 * @param unitId
	 * @return
	 */
	List<Integer> queryBoothIds(@Param("unitId") String unitId);
	
	/**
	 * 查询场地累计访问人数
	 * 
	 * @param unitId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<DeviceBaseInfo> queryBemberTotalByBoothId(@Param("tableName") String tableName,@Param("unitId") String unitId);
	
	/**
	 * 查询场地访问记录
	 * 
	 * @param unitId
	 * @param boothId
	 * @return
	 */
	List<DeviceBaseInfo> queryVisitLogByBoothId(@Param("tableName") String tableName,@Param("unitId") String unitId,@Param("boothIdLists") List<Integer> boothIdLists);

	/**
	 * 新建场地报表数据
	 * 
	 * @param map
	 * @return
	 */
	int insertBoothReportResult(@Param("unitId") String unitId,@Param("indexData") Map<String, Object> map,@Param("list") List<DeviceBaseInfo> list);
	
	/**
	 * 修改场地指标数据
	 * 
	 * @param map
	 * @return
	 */
	int updateBoothReportResult(@Param("unitId") String unitId,@Param("indexData") Map<String, Object> map);
	
	/**
	 * 批量修改场地数据
	 * 
	 * @param map
	 * @return
	 */
	int updateBoothReportResultBatch(@Param("unitId") String unitId,@Param("list") List<DeviceBaseInfo> list);

	/***********************************************************************************/
	
	/**
	 * 查询所有使用报表功能的unitId
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	List<String> queryUnitIdList();

	/**
	 * 新建用户报表数据
	 * 
	 * @param map
	 * @return
	 */
	int insertReportResult(@Param("unitId") String unitId,@Param("activeScore") Integer activeScore,@Param("list") List<BeaconActiveInfo> list);
	
	/**
	 * 修改用户活跃综合评分
	 * 
	 * @param map
	 * @return
	 */
	int updateReportResult(@Param("unitId") String unitId,@Param("activeScore") Integer activeScore);
	
	/**
	 * 批量修改用户活跃综合评分
	 * 
	 * @param map
	 * @return
	 */
	int updateReportResultScoreBatch(@Param("unitId") String unitId,@Param("list") List<BeaconActiveInfo> list);

	/**
	 * 新建用户活跃指数
	 * 
	 * @param map
	 * @return
	 */
	int insertMmemberActivity(@Param("list") List<BeaconActiveInfo> list);
	
	/**
	 * 批量修改用户活跃指数
	 * 
	 * @param map
	 * @return
	 */
	int updateMmemberActivityBatch(@Param("list") List<BeaconActiveInfo> list);
	
	/**
	 * 查询用户活跃指数
	 * 
	 * @param params
	 * @return
	 */
	List<BeaconActiveInfo> queryMemberActives(@Param("tableName") String tableName,@Param("unitId") String unitId);
	
	/**
	 * 查询出某用户轨迹数据
	 * 
	 * @param unitId
	 * @return
	 */
	List<BeaconActive> queryActive(@Param("unitId") String unitId,@Param("beaconMac") String beaconMac);
	
	/**
	 * 查询出用于处理展台重复访问数的数据
	 * 
	 * @param unitId
	 * @return
	 */
	List<BeaconActiveInfo> queryActiveBoothRepeatTotal(@Param("tableName") String tableName,@Param("unitId") String unitId);
	
	/**
	 * 查询最感兴趣的展台
	 * 
	 * @param unitId
	 * @param beaconMac
	 * @return
	 */
	Map<String,String> queryInterestedBooth(@Param("unitId") String unitId,@Param("beaconMac") String beaconMac);
	
	/**
	 * 查询出所有人员
	 * 
	 * @param unitId
	 * @return
	 */
	List<BeaconActive> queryMemberNameByBeaconMac(@Param("unitId") String unitId);
	
	
	/**
	 * 查询数据条数
	 * 
	 * @param unitId
	 * @return
	 */
	Integer getTotal(@Param("unitId") String unitId);
	
	/**
	 * 查询当前beacon所处采集器Mac地址
	 * 
	 * @param unitId
	 * @return
	 */
	String getDeviceMac(@Param("unitId") String unitId,@Param("beaconId") String beaconId,@Param("minutes") Integer minutes);
	/**
	 * 查询当前采集器周围人数
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<Map<String,Object>> queryMemberByNearBy(@Param("unitId") String unitId,@Param("beaconId") String beaconId,@Param("deviceMac") String deviceMac,@Param("minutes") Integer minutes,@Param("rssi") Integer rssi);

	/**
	 * 查询用户总体指标数据
	 * 
	 * @param unitId
	 * @return
	 */
	Map<String,Object> queryOverallIndicators(String unitId);
	
	/**
	 * 查询签到用户
	 * 
	 * @param unitId
	 * @return
	 */
	Integer getBeaconTotal(String unitId);
	
	/**
	 * 查询智慧互动分析
	 * 
	 * @param unitId
	 * @return
	 */
	List<Map<String,Object>> queryInteractionAnalysis(String unitId);
}
