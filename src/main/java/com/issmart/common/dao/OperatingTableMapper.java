package com.issmart.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OperatingTableMapper {

	/**
	 * 创建分区表
	 * 
	 * @param tableName
	 * @param partitionList
	 * @return
	 */
	int createTable(@Param("tableName") String tableName,@Param("partitionList") List<Map<String,Object>> partitionList);

	/**
	 * 查询分区名称
	 */
	List<String> queryPartitionName(@Param("tableName") String tableName);
}
