package com.issmart.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface GetPressNumberMapper {
	
	/**
	 * 根据beaconMac查询互动数
	 * 1：按一按
	 * 2：贴一贴 
	 * 
	 * @param unitId
	 * @param behaviorId
	 * @return
	 */
	List<Map<String,Object>> getActiveNumberMapperByBeaconMac(@Param("unitId") String unitId,@Param("behaviorId") Integer behaviorId);
	
	/**
	 * 根据DeviceMac查询互动数
	 * 
	 * @param unitId
	 * @param behaviorId
	 * @return
	 */
	List<Map<String,Object>> getActiveNumberMapperByDeviceMac(@Param("unitId") String unitId,@Param("behaviorId") Integer behaviorId);
}
