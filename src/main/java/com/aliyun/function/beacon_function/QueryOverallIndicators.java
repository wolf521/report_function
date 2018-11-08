package com.aliyun.function.beacon_function;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.beacon.serviceImpl.BeaconServiceImpl;
import com.issmart.common.model.Response;

/**
 * 查询用户总体指标数据
 * 
 * @param unitId
 * @param deviceMac
 * @return
 */
public class QueryOverallIndicators implements PojoRequestHandler<Map<String, Object>, Map<String, Object>> {

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> requestParam, Context context) {
		// 返回格式
		Map<String, Object> result = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		Response response = new Response();
		result.put("isBase64Encoded", false);
		result.put("statusCode", 200);
		result.put("headers", headers);
		try {
			// 获取参数
			@SuppressWarnings("unchecked")
			Map<String, Object> queryParameters = (Map<String, Object>) requestParam.get("queryParameters");
			if(queryParameters.get("unitId") == null) {
				result.put("body", "the param is empty");
				return result;
			}
			response.success(new BeaconServiceImpl().queryOverallIndicators(queryParameters.get("unitId").toString()));
			result.put("body", response);
		} catch (Exception e) {
			context.getLogger().info("查询用户总体指标数据异常："+e.getMessage());
		}
		return result;
	}
}
