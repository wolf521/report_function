package com.issmart.common.model;

import java.util.Map;

public class Request {
	 private String path;
     private String httpMethod;
     private Map<String,Object> headers;
     private Map<String,Object> queryParameters;
     private Map<String,Object> pathParameters;
     private String body;
     private boolean isBase64Encoded;
     @Override
     public String toString() {
         return "Request{" +
                 "path='" + path + '\'' +
                 ", httpMethod='" + httpMethod + '\'' +
                 ", headers=" + headers +
                 ", queryParameters=" + queryParameters +
                 ", pathParameters=" + pathParameters +
                 ", body='" + body + '\'' +
                 ", isBase64Encoded=" + isBase64Encoded +
                 '}';
     }
     public String getBody() {
         return body;
     }
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public Map<String, Object> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}
	public Map<String, Object> getQueryParameters() {
		return queryParameters;
	}
	public void setQueryParameters(Map<String, Object> queryParameters) {
		this.queryParameters = queryParameters;
	}
	public Map<String, Object> getPathParameters() {
		return pathParameters;
	}
	public void setPathParameters(Map<String, Object> pathParameters) {
		this.pathParameters = pathParameters;
	}
	public boolean isBase64Encoded() {
		return isBase64Encoded;
	}
	public void setBase64Encoded(boolean isBase64Encoded) {
		this.isBase64Encoded = isBase64Encoded;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
