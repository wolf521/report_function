package com.aliyun.function.device_function;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.common.model.Response;
import com.issmart.device.serviceImpl.DeviceServiceImpl;

/**
 * 查询场地最佳引流
 * 
 * @author Administrator
 *
 */ 
public class QueryBestDrainage implements PojoRequestHandler<Map<String, Object>, Map<String, Object>> {

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> requestParam, Context context) {
		// 返回格式
		Map<String, Object> result = new HashMap<>(); 
		Map<String, String> headers = new HashMap<>();
		Response response = new Response();
		result.put("isBase64Encoded", false);
		result.put("statusCode", 200);
		result.put("headers", headers);
		context.getLogger().info("查询场地最佳引流参数：" + requestParam);
		try {
			// 获取参数
			@SuppressWarnings("unchecked")
			Map<String, Object> queryParameters = (Map<String, Object>) requestParam.get("queryParameters");
			if (queryParameters.get("unitId") == null) {
				return result;
			}
			response.success(new DeviceServiceImpl().queryBestDrainage(queryParameters.get("unitId").toString()));
			result.put("body", response);
		} catch (Exception e) {
			context.getLogger().info("查询场地最佳引流异常：" + e.getMessage());
		}
		return result;
	}
}
