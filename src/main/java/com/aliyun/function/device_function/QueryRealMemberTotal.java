package com.aliyun.function.device_function;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.common.model.Response;
import com.issmart.device.serviceImpl.DeviceServiceImpl;

/**
 * 查询当前实时访客人数
 * 
 * @author Administrator
 * 
 */
public class QueryRealMemberTotal implements PojoRequestHandler<Map<String, Object>, Map<String, Object>> {

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> requestParam, Context context) {
		// 返回格式
		Map<String, Object> result = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		Response response = new Response();
		result.put("isBase64Encoded", false);
		result.put("statusCode", 200);
		result.put("headers", headers);
		context.getLogger().info("查询当前实时访客人数参数：" + requestParam);
		try {
			// 获取参数
			@SuppressWarnings("unchecked")
			Map<String, Object> queryParameters = (Map<String, Object>) requestParam.get("queryParameters");
			if (queryParameters.get("unitId") == null || queryParameters.get("minutes") == null) {
				result.put("body", "The param is empty");
				return result;
			}
			Integer boothId = null;
			if (queryParameters.get("boothId") != null) {
				boothId = Integer.parseInt(queryParameters.get("boothId").toString());
			}
			response.success(new DeviceServiceImpl().queryRealMemberTotal(queryParameters.get("unitId").toString(),
					boothId, Integer.parseInt(queryParameters.get("unitId").toString())));
			result.put("body", response);
		} catch (Exception e) {
			context.getLogger().info("查询当前实时访客人数异常：" + e.getMessage());
		}
		return result;
	}
}
