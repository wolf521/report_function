package com.issmart.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsUtil {

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	String _url = "";
	Map<String, String> _params;

	public HttpsUtil(String url, Map<String, String> keyValueParams) {
		this._url = url;
		this._params = keyValueParams;
	}

	public static String httpsRequest(String url) {

		String result = "";
		BufferedReader in = null;

		try {
			URL realUrl = new URL(url);
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			// 鎵撳紑鍜孶RL涔嬮棿鐨勮繛鎺�
			HttpsURLConnection connection = (HttpsURLConnection) realUrl.openConnection();

			// 璁剧疆https鐩稿叧灞炴��
			connection.setSSLSocketFactory(sc.getSocketFactory());
			connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
			connection.setDoOutput(true);

			// 璁剧疆閫氱敤鐨勮姹傚睘鎬�
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 寤虹珛瀹為檯鐨勮繛鎺�
			connection.connect();

			// 瀹氫箟 BufferedReader杈撳叆娴佹潵璇诲彇URL鐨勫搷搴�
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;

			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("鍙戦�丟ET璇锋眰鍑虹幇寮傚父锛�");
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 鍙戣捣https璇锋眰骞惰幏鍙栫粨鏋�
	 * 
	 * @param requestUrl
	 *            璇锋眰鍦板潃
	 * @param requestMethod
	 *            璇锋眰鏂瑰紡锛圙ET銆丳OST锛�
	 * @param outputStr
	 *            鎻愪氦鐨勬暟鎹�
	 * @return JSONObject(閫氳繃JSONObject.get(key)鐨勬柟寮忚幏鍙杍son瀵硅薄鐨勫睘鎬у��)
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {

		StringBuffer buffer = new StringBuffer();
		try {
			// 鍒涘缓SSLContext瀵硅薄锛屽苟浣跨敤鎴戜滑鎸囧畾鐨勪俊浠荤鐞嗗櫒鍒濆鍖�
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 浠庝笂杩癝SLContext瀵硅薄涓緱鍒癝SLSocketFactory瀵硅薄
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 璁剧疆璇锋眰鏂瑰紡锛圙ET/POST锛�
			httpUrlConn.setRequestMethod(requestMethod);

			// if ("GET".equalsIgnoreCase(requestMethod))
			httpUrlConn.connect();

			// 褰撴湁鏁版嵁闇�瑕佹彁浜ゆ椂
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 娉ㄦ剰缂栫爜鏍煎紡锛岄槻姝腑鏂囦贡鐮�
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 灏嗚繑鍥炵殑杈撳叆娴佽浆鎹㈡垚瀛楃涓�
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 閲婃斁璧勬簮
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			System.out.println("https request error:{}");
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static String httpRequest(String requestUrl, String outputStr) {

		String result = "";

		try {
			// 寤虹珛杩炴帴
			URL url = new URL(requestUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			// 璁剧疆鍙傛暟
			httpConn.setDoOutput(true); // 闇�瑕佽緭鍑�
			httpConn.setDoInput(true); // 闇�瑕佽緭鍏�
			httpConn.setUseCaches(false); // 涓嶅厑璁哥紦瀛�
			httpConn.setRequestMethod("POST"); // 璁剧疆POST鏂瑰紡杩炴帴
			// 璁剧疆璇锋眰灞炴��
			httpConn.setRequestProperty("Content-Type", "application/json");
			httpConn.setRequestProperty("Charset", "UTF-8");
			// 杩炴帴,涔熷彲浠ヤ笉鐢ㄦ槑鏂嘽onnect锛屼娇鐢ㄤ笅闈㈢殑httpConn.getOutputStream()浼氳嚜鍔╟onnect
			httpConn.connect();
			// 寤虹珛杈撳叆娴侊紝鍚戞寚鍚戠殑URL浼犲叆鍙傛暟
			DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
			dos.write(outputStr.getBytes());
			;
			dos.flush();
			dos.close();
			// 鑾峰緱鍝嶅簲鐘舵��
			int resultCode = httpConn.getResponseCode();
			System.out.println("code:"+resultCode);
			if (HttpURLConnection.HTTP_OK == resultCode) {
				StringBuffer sb = new StringBuffer();
				String readLine = new String();
				BufferedReader responseReader = new BufferedReader(
						new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				while ((readLine = responseReader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
				responseReader.close();
				result = sb.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public static String processUrlParams(String url, Map<String, Object> params) {

		StringBuilder proUrl = new StringBuilder(url);

		Set<String> keySet = params.keySet();
		int i = 0;
		for (String key : keySet) {
			if (i != 0) {
				proUrl.append("&" + key + "=" + params.get(key));
			} else {
				proUrl.append("?");
				proUrl.append(key + "=" + params.get(key));
			}
			i++;
		}
		return proUrl.toString();
	}

}
