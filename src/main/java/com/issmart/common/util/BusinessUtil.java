package com.issmart.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.issmart.common.model.Response;
import com.issmart.device.model.DeviceConfigInfo;

public class BusinessUtil {

	// 字符串空校验
	public static boolean stringIsEmpty(String str) {

		if (str == null || str.equals("") || str.equals("null") || str.equals("NULL")) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> proParamAPIGatewayBodyToMap(String bodyBase64) {
		byte[] bodyByte = Base64.getDecoder().decode(bodyBase64);
		Map<String, Object> param = null;
		try {
			String body = new String(bodyByte, "utf-8");
			param = JSON.parseObject(body, Map.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}
	
	public static DeviceConfigInfo proParamAPIGatewayBodyToDeviceConfigInfo(String bodyBase64) {
		byte[] bodyByte = Base64.getDecoder().decode(bodyBase64);
		DeviceConfigInfo param = null;
		try {
			String body = new String(bodyByte, "utf-8");
			param = JSON.parseObject(body, DeviceConfigInfo.class);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param;
	}
	
	public static void main(String[] args) {
		System.out.println(proParamAPIGatewayBodyToMap("eyJkZXZpY2VNYWMiOiIwMDoxZjpjMjoyNjo1NDo3NCIsInF1YW50dW1UaW1lIjoiMjQiLCJlbmRUaW1lU3RhbXAiOiIxNTMzMzUxODQyMDAwIiwicXVhbnR1bVVuaXQiOiJoaCIsInVuaXRJZCI6IjkxOSJ9"));
	}

	public static void setPage(Response response, String pageNo, String pageSize, Integer count) {

		// 页码设置
		if (pageNo != null && !pageNo.equals("") && pageSize != null && !pageSize.equals("") 
				&& count != null) {

			Integer cPageNo = Integer.parseInt(pageNo);
			Integer cPageSize = Integer.parseInt(pageSize);

			Integer pageCount = count / Integer.parseInt(pageSize);

			if ((count % cPageSize) != 0) {
				pageCount += 1;
			}

			response.setPage(cPageNo, cPageSize, pageCount, count);
		}
	}

	/**
	 * 处理查询使用的pageNo
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static Integer processPageNo(Integer pageNo, Integer pageSize) {

		Integer processPageNo = pageNo;

		// 页码处理
		if (processPageNo != null && pageNo != 0 && pageSize != null && pageSize != 0) {
			processPageNo = (processPageNo - 1) * pageSize;
		}
		return processPageNo;
	}
}
