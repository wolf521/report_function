package com.issmart.device.service;

import java.util.List;
import java.util.Map;

import com.issmart.device.model.DeviceBaseInfo;
import com.issmart.device.model.RequestParamOfBoothDetails;

/**
 * 每场展会都会创建一个单独的表，表根据展会时间按天进行分区
 * 
 * @author Administrator
 *
 */
public interface DeviceService {
	
	/**
	 * 查询场地总体指标
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	Map<String,Integer> queryBoothIndex(String unitId);
	
	/**
	 * 查询当前实时访问人数
	 * 
	 * @param unitId
	 * @param minutes
	 * @param rssi
	 * @return
	 */
	Integer queryRealMemberTotal(String unitId,Integer boothId,Integer minutes);
	
	/**
	 * 查询场地最佳引流
	 * 
	 * @param unitId
	 * @return
	 */
	List<Map<String,Object>> queryBestDrainage(String unitId);
	
	/**
	 * 查询最佳互动数据
	 * 
	 * @param unitId
	 * @return
	 */
	List<Map<String, Object>> queryBestActive(String unitId);
	
	/**
	 * 查询最佳吸引数据
	 * 
	 * @param unitId
	 * @return
	 */
	List<Map<String, Object>> queryBestAttract(String unitId);
	
	/**
	 * 查询场地活跃指数
	 * 
	 * @param unitId	
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<DeviceBaseInfo> queryDeviceHot(String unitId,int pageNo, int pageSize);
	
	Integer getTotal(String unitId);
	
	/**
	 * 查询单个展台指标信息
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	Map<String,Object> querySingleBoothInfo(String unitId,Integer boothId);

	/**
	 * 查询单展台折线图
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	Map<String, List<Object>> queryTrendChart (RequestParamOfBoothDetails param);
	
	/**
	 * 查询饼图
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	Map<String,Map<String,List<Object>>> queryDistributeChart (RequestParamOfBoothDetails param);
	
	/**
	 * 查询单展台活跃客户排名
	 * 
	 * @param unitId
	 * @param deviceMac
	 * @return
	 */
	List<Map<String,Object>> queryActiveList (RequestParamOfBoothDetails param);
}
