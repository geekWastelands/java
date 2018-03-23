package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import utils.ProxyHost.IP;

public class GetDocument {
	public static Document changeIp(String url) {
		StringBuilder sBuilder = null;
		InputStream inputStream = null;
		IP ip = null;
		Document document = null;
		try {

			HttpClient httpClient = new DefaultHttpClient();
			if (ReadConfig.isProxy) {

				HttpHost proxy = null;
				if (ReadConfig.isuseGoAgent) {
					proxy = new HttpHost(ReadConfig.proxyIp,
							ReadConfig.proxyPort);
				} else {
					ip = ProxyHost.getIp(true);
					proxy = new HttpHost(ip.ip, ip.port);
				}
				httpClient.getParams().setParameter(
						ConnRoutePNames.DEFAULT_PROXY, proxy);
			}

			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 60 * 1000);// 鏉╃偞甯撮弮鍫曟？1閸掑棝鎸?

			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 60 * 1000);// 閺佺増宓佹导鐘虹翻閺冨爼妫?鐏忓繑妞?

			httpClient.getParams().setParameter(
					ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
			HttpGet httpGet = new HttpGet(url.trim());

			httpGet.setHeader("User-Agent", UserAgent.getUserAgent());
			//httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			/*httpGet.setHeader("Accept-Encoding", "gzip, deflate");
			httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			httpGet.setHeader("Connection", "keep-alive");
			httpGet.setHeader("Host", "222.173.194.17");
			httpGet.setHeader("Referer", "http://down.foodmate.net/standard/sort/9/10988.html");
			httpGet.setHeader("Cookie","__gads=ID=6109395a762bbfb3:T=1431574048:S=ALNI_Ma9CuLH7X1k9-oxG8YHdyjgz1F3Lw; AJSTAT_ok_times=5; Hm_lvt_2aeaa32e7cee3cfa6e2848083235da9f=1431574280,1431597039,1431654769,1431859568; Hm_lpvt_2aeaa32e7cee3cfa6e2848083235da9f=1431869162; AJSTAT_ok_pages=11");
			
            
			
		//	execute.get
		Header[] map = execute.getAllHeaders();
	           
            System.out.println("显示响应Header信息\n");
           
            for (Header entry : map)
            {
                System.out.println("Key : " + entry.getName() + " ,Value : " + entry.getValue());
            }
            
*/HttpResponse execute = httpClient.execute(httpGet);

			System.err.println(execute.getStatusLine());
			if (execute.getStatusLine().getStatusCode() != 200) {

				if (ip != null) {
					int code = execute.getStatusLine().getStatusCode();
					System.out.println(ip.ip + ":" + ip.port + "---" + code
							+ "--");
					ProxyHost.removeIp(ip);
				}
				return null;
			}
			BufferedReader bReader = null;
			sBuilder = new StringBuilder();
			HttpEntity entity = execute.getEntity();
			inputStream = entity.getContent();
			bReader = new BufferedReader(new InputStreamReader(inputStream,
					"utf-8"));
			for (String line = bReader.readLine(); line != null; line = bReader
					.readLine()) {
				sBuilder.append(line).append("\r\n");
			}
			if (sBuilder != null) {
				document = Jsoup.parse(sBuilder.toString());
			}
		} catch (Exception ex) {
			if (ip != null) {
				ProxyHost.removeIp(ip);
			}
			ex.printStackTrace();

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// System.out.println("---"+sBuilder.toString());
		
		

		return document;

	}
	public static Document changeIpGBK(String url) {
		StringBuilder sBuilder = null;
		InputStream inputStream = null;
		IP ip = null;
		try {

			HttpClient httpClient = new DefaultHttpClient();
			if (ReadConfig.isProxy) {

				HttpHost proxy = null;
				if (ReadConfig.isuseGoAgent) {
					proxy = new HttpHost(ReadConfig.proxyIp,
							ReadConfig.proxyPort);
				} else {
					ip = ProxyHost.getIp(true);
					proxy = new HttpHost(ip.ip, ip.port);
				}
				httpClient.getParams().setParameter(
						ConnRoutePNames.DEFAULT_PROXY, proxy);
			}

			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 60 * 1000);// 鏉╃偞甯撮弮鍫曟？1閸掑棝鎸?

			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 60 * 1000);// 閺佺増宓佹导鐘虹翻閺冨爼妫?鐏忓繑妞?

			httpClient.getParams().setParameter(
					ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
			HttpGet httpGet = new HttpGet(url.trim());

			httpGet.setHeader("User-Agent", UserAgent.getUserAgent());
			
            
   HttpResponse execute = httpClient.execute(httpGet);

			System.err.println(execute.getStatusLine());
			if (execute.getStatusLine().getStatusCode() != 200) {

				if (ip != null) {
					int code = execute.getStatusLine().getStatusCode();
					System.out.println(ip.ip + ":" + ip.port + "---" + code
							+ "--");
					ProxyHost.removeIp(ip);
				}
				return null;
			}
			BufferedReader bReader = null;
			sBuilder = new StringBuilder();
			HttpEntity entity = execute.getEntity();
			inputStream = entity.getContent();
			bReader = new BufferedReader(new InputStreamReader(inputStream,
					"gbk"));
			for (String line = new String(bReader.readLine().getBytes("gbk"),"utf-8"); line != null; line = bReader.readLine()) {
				sBuilder.append(line).append("\r\n");
			}

		} catch (Exception ex) {
			if (ip != null) {
				ProxyHost.removeIp(ip);
			}
			ex.printStackTrace();

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Document document = null;
		if (sBuilder != null) {
			document = Jsoup.parse(sBuilder.toString());
		}

		return document;

	}
	public static Document connect(String url) {
		// TODO Auto-generated method stub
		return null;
	}
}
