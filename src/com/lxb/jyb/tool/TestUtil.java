package com.lxb.jyb.tool;

import com.lxb.jyb.bean.OrderSelect;

public class TestUtil {
	public static StringBuffer getPC(String ticket) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("{");
		buffer.append("\"account\"").append(":").append("\"900171576\"")
				.append(",");
		buffer.append("\"broker\"").append(":").append("\"GMI\"").append(",");
		buffer.append("\"ticket\"").append(":").append("\"").append(ticket)
				.append("\"");
		buffer.append("}");
		return buffer;
	}

	public static StringBuffer getResult() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{").append("\"account\"").append(":\"");
		buffer.append("900171576").append("\",");

		buffer.append("\"broker\"").append(":\"").append("GMI").append("\",");
		buffer.append("\"symbol\"").append(":\"").append("EURUSD")
				.append("\",");
		buffer.append("\"lots\"").append(":\"").append("0.01").append("\",");
		buffer.append("\"ordertype\"").append(":\"").append("1").append("\",");
		buffer.append("\"sl\"").append(":\"").append("2.0123").append("\",");
		buffer.append("\"tp\"").append(":\"").append("0.5111").append("\",");
		buffer.append("\"comment\"").append(":\"").append("Android TEST")
				.append("\",");
		buffer.append("\"price\"").append(":\"").append("0").append("\",");
		buffer.append("\"expiretime\"").append(":\"").append("2016-4-22")
				.append("\"");
		buffer.append("}");
		// String url = "http://172.16.3.50:8080/business-trade/order";
		// CookieStore cookies=new CookieStore() {
		//
		// @Override
		// public List<Cookie> getCookies() {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public boolean clearExpired(Date date) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public void clear() {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void addCookie(Cookie cookie) {
		// // TODO Auto-generated method stub
		//
		// }
		// };
		// CloseableHttpClient httpClient =
		// HttpClients.custom().useSystemProperties()
		// .setDefaultCookieStore(cookies)
		// .build();
		// // HttpClient httpClient=new DefaultHttpClient();
		// // HttpClient client = HttpClientBuilder.create().build();
		// HttpPut put = new HttpPut(url);
		// put.setHeader("Content-type", "application/json");
		//
		// StringEntity params = null;
		// StringBuffer result = null;
		// try {
		// params = new StringEntity(buffer.toString());
		// put.setEntity(params);
		//
		// HttpResponse response = httpClient.execute(put);
		// System.out.println("Response Code:"
		// + response.getStatusLine().getStatusCode());
		// BufferedReader rd = new BufferedReader(new InputStreamReader(
		// response.getEntity().getContent()));
		//
		// result = new StringBuffer();
		// String line = "";
		// while ((line = rd.readLine()) != null) {
		// result.append(line);
		// }
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (ClientProtocolException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// System.out.println("result:" + result);
		return buffer;
	}
}
