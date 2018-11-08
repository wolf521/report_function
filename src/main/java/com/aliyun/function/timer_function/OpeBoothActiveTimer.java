package com.aliyun.function.timer_function;

import java.util.HashMap;
import java.util.Map;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.issmart.common.model.Response;
import com.issmart.common.serviceImpl.TimerServiceImpl;

/**
 * 定时处理场地报表数据
 * 
 * @param unitId
 * @param deviceMac
 * @return
 */
public class OpeBoothActiveTimer implements PojoRequestHandler<Map<String, Object>, Map<String, Object>> {

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
			new TimerServiceImpl().opeBoothActiveTimer();
			context.getLogger().info("开始处理场地报表数据");
			response.success(null);
			result.put("body", response);
		} catch (Exception e) {
			context.getLogger().info("定时处理用户报表数据异常："+e.getMessage());
		}
		return result;
	}
}
