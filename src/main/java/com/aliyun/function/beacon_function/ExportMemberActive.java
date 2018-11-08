package com.aliyun.function.beacon_function;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.beacon.serviceImpl.BeaconServiceImpl;
import com.issmart.common.model.Response;

/**
 * 查询用户轨迹信息
 * 
 * @author CuiZhiXiang
 *
 */
public class ExportMemberActive implements PojoRequestHandler<Map<String, Object>, Map<String, Object>>{

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> requestParam, Context context) {
		// 返回格式
		Map<String, Object> result = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		Response response = new Response();
		result.put("isBase64Encoded", false);
		result.put("statusCode", 200);
		result.put("headers", headers);
		context.getLogger().info("查询用户轨迹信息参数"+requestParam);
		try {
			// 获取参数
			@SuppressWarnings("unchecked")
			Map<String, Object> queryParameters = (Map<String, Object>) requestParam.get("queryParameters");
			if(queryParameters.get("unitId") == null || queryParameters.get("beaconMac") == null) {
				context.getLogger().info("查询用户轨迹信息参数为空");
				return result;
			}
			response.success(new BeaconServiceImpl().queryActive(queryParameters.get("unitId").toString(),queryParameters.get("beaconMac").toString()));			
			result.put("body", response);
		} catch (Exception e) {
			context.getLogger().info("查询用户轨迹信息异常"+e.getMessage());
		}
		return result;
	}
}
