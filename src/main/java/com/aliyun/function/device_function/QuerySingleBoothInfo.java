package com.aliyun.function.device_function;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.common.model.Response;
import com.issmart.device.serviceImpl.DeviceServiceImpl;

/**
 * 查询单个展台指标信息
 * 
 * @param unitId
 * @param deviceMac
 * @return
 */
public class QuerySingleBoothInfo implements PojoRequestHandler<Map<String, Object>, Map<String, Object>> {

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> requestParam, Context context) {
		// 返回格式
		Map<String, Object> result = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		Response response = new Response();
		result.put("isBase64Encoded", false);
		result.put("statusCode", 200);
		result.put("headers", headers);
		context.getLogger().info("查询单个展台指标信息参数：" + requestParam);
		try {
			// 获取参数
			@SuppressWarnings("unchecked")
			Map<String, Object> queryParameters = (Map<String, Object>) requestParam.get("queryParameters");
			if (queryParameters.get("unitId") == null || queryParameters.get("boothId") == null) {
				result.put("body", "the param is empty");
				return result;
			}
			response.success(new DeviceServiceImpl().querySingleBoothInfo(queryParameters.get("unitId").toString(),
					Integer.parseInt(queryParameters.get("boothId").toString())));
			result.put("body", response);
			return result;
		} catch (Exception e) {
			context.getLogger().info("查询单个展台指标信息异常：" + e.getMessage());
		} 
		return result;
	}
}
