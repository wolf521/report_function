package com.issmart.device.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.issmart.device.model.DeviceBaseInfo;
import com.issmart.device.model.RequestParamOfBoothDetails;

public interface DeviceMapper {
	
	/**
	 * 查询表分区信息
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<String> queryTablePartitionInfo (@Param("tableName") String tableName);
	
	/**
	 * 查询此展会报表功能等级
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	String queryPermissionLevel(String unitId);
	
	/**
	 * 查询场地总体指标
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	Map<String,Integer> queryBoothIndex(@Param("unitId") String unitId);

	/**
	 * 获得实时访问数量
	 * 
	 * @param unitId
	 * @return
	 */
	Integer getRealMemberNumber(@Param("unitId") String unitId,@Param("boothId") Integer boothId,@Param("startTimeStamp") long startTimeStamp,@Param("endTimeStamp") long endTimeStamp);
	
	/**
	 * 查询最佳引流
	 * 
	 * @param unitId
	 * @return
	 */
	List<Map<String,Object>> queryBestDrainage(@Param("unitId") String unitId);
	
	/**
	 * 查询最佳吸引数据
	 * 
	 * @param unitId
	 * @return
	 */
	List<Map<String,Object>> queryBestAttract(@Param("unitId") String unitId);
	
	/**
	 * 查询最佳互动数据
	 * 
	 * @param unitId
	 * @return
	 */
	List<Map<String,Object>> queryBestActive(@Param("unitId") String unitId);

	/**
	 * 查询场地活跃指数
	 * 
	 * @param unitId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<DeviceBaseInfo> queryDeviceHot(@Param("unitId") String unitId,@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);

	/**
	 * 查询场地累计访问人数
	 * 
	 * @param unitId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<DeviceBaseInfo> queryBemberTotalByBoothId(@Param("tableName") String tableName,@Param("unitId") String unitId,@Param("boothId") Integer boothId);
	
	/**
	 * 查询场地访问记录
	 * 
	 * @param unitId
	 * @param boothId
	 * @return
	 */
	List<DeviceBaseInfo> queryVisitLogByBoothId(@Param("tableName") String tableName,@Param("unitId") String unitId,@Param("boothIdLists") List<Integer> boothIdLists);

	/**
	 * 获取所有的展台Id
	 * 
	 * @param unitId
	 * @return
	 */
	List<Integer> queryBoothIds(@Param("unitId") String unitId);

	/**
	 * 查询数据条数
	 * 
	 * @param unitId
	 * @return
	 */
	Integer getTotal(@Param("unitId") String unitId);
	
	/**
	 * 查询单个展台指标信息
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	Map<String,Object> querySingleBoothInfo(@Param("unitId") String unitId,@Param("boothId") Integer boothId);
	
	/**
	 * 查询单展台实时访客数量
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	Integer querySingleBoothRealTimeVisotor(@Param("unitId") String unitId,@Param("deviceMac") String deviceMac,@Param("startTimeStamp") long startTimeStamp);

	/**
	 * 查询展台关联采集器数量
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<String> queryDeviceMacs (@Param("boothId") Integer boothId);
	
	/**
	 * 查询单展台折线图
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<Map<String,Object>> queryTrendChart (RequestParamOfBoothDetails param);
	

	/**
	 * 查询职位分布饼图
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<Map<String,Object>> queryDistributeChartByPosition (RequestParamOfBoothDetails param);
	
	/**
	 * 查询地域分布饼图
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<Map<String,Object>> queryDistributeChartByCity (RequestParamOfBoothDetails param);
	
	/**
	 * 查询行业分布饼图
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<Map<String,Object>> queryDistributeChartByIndustry (RequestParamOfBoothDetails param);
	
	/**
	 * 查询单展台活跃客户排名
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<Map<String,Object>> queryActiveList (RequestParamOfBoothDetails param);
}
