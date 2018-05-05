package utils;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/*获取网页链接，取得页面返回的Document*/







import utils.ProxyHost.IP;

public class GetConnect {
	public static Document connect(String url) {
		Document document = null;
		int i;
		for(i = 1;i < 10;) { /* 请求链接5次，如果5此均失败返回NULL；如果成功一次则返回请求道链接的document*/
			try {
				document = Jsoup
						.connect(url)
						.header("Host", "www.xkb1.com")
//	       .header("Host", "www.sxws.gov.cn")
//	       .header("Referer", "http://www.sxws.gov.cn/col/col9863/index.html")
//	       .header("Content-Type", "text/javascript, application/javascript, */*")//text/javascript, application/javascript, */*
//	       .header("Accept", "text/plain, */*; q=0.01")
//	       .header("X-Requested-With", "XMLHttpRequest")
//	       .header("Accept-Encoding", "gzip, deflate")
//	       .header("Accept-Language", "zh-CN,zh;q=0.9")
//	       .header("Content-Length","215")
//	        .header("Connection", "keep-alive")
//	       .header("Cookie", "JSESSIONID=8689A94988C070D93CCC50724BE3313C; gs_b_tas=BOTQRH5QT48IDHBO; counter=3")
//						.userAgent(
//								"Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0")
						.ignoreContentType(true).timeout(3000).get();
			} catch (IOException e) {
				e.printStackTrace();
				i++;
			}
			if(document != null) {
				return document;
			}
				
		}
		System.out.println(url + "--》请求失败，放弃该链接！");
		return null;
	}
	public static Document connect(String url,String refer) {
		Document document = null;
		int i;
		for(i = 1;i < 10;) { /* 请求链接5次，如果5此均失败返回NULL；如果成功一次则返回请求道链接的document*/
			try {
				document = Jsoup
						.connect(url)
						.header("Host", "www.xkb1.com")
						.header("Referer", refer)
//	       .header("Host", "www.sxws.gov.cn")
//	       .header("Referer", "http://www.sxws.gov.cn/col/col9863/index.html")
//	       .header("Content-Type", "text/javascript, application/javascript, */*")//text/javascript, application/javascript, */*
//	       .header("Accept", "text/plain, */*; q=0.01")
//	       .header("X-Requested-With", "XMLHttpRequest")
//	       .header("Accept-Encoding", "gzip, deflate")
//	       .header("Accept-Language", "zh-CN,zh;q=0.9")
//	       .header("Content-Length","215")
//	        .header("Connection", "keep-alive")
//	       .header("Cookie", "JSESSIONID=8689A94988C070D93CCC50724BE3313C; gs_b_tas=BOTQRH5QT48IDHBO; counter=3")
//						.userAgent(
//								"Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0")
						.ignoreContentType(true).timeout(3000).get();
			} catch (IOException e) {
				e.printStackTrace();
				i++;
			}
			if(document != null) {
				return document;
			}
				
		}
		System.out.println(url + "--》请求失败，放弃该链接！");
		return null;
	}
	
	/**
	 * 根据URL获得所有的html信息
	 * 
	 * @param url
	 * @return
	 */
	public static Document getDocumentByUrl(String url) {//更换IP地址
		IP ip = null;
		String html = null;
		Document document = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
			HttpGet httpget = new HttpGet(url);// 以get方式请求该URL			
			if (ReadConfig.isProxy) { // 是否更换请求IP和端口号
				HttpHost proxy = null;
				if (ReadConfig.isuseGoAgent) { // isuseGoAgent 是false的时候更换IP
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
			httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
			//							 Hm_lvt_929c0ab204c30f9dd1605aa47b476299=1523442759; bdshare_ty=0x18; dede_pid=1523451294; Hm_lpvt_929c0ab204c30f9dd1605aa47b476299=1523451299		
			httpget.setHeader("Host", "www.xkb1.com");
			httpget.setHeader("Referer", "https://www.xkb1.com/plus/download.php?open=0&aid=191649&cid=3");
			//		     httpget.setHeader("Content-Length","215");//httpget.setHeader("Cookie", "JSESSIONID=8689A94988C070D93CCC50724BE3313C; gs_b_tas=BOTQRH5QT48IDHBO; counter=3");
			try {
				HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
				//String cookie = responsce.getHeaders("Cookie").toString();
				//System.out.println("cookie = "+cookie);
				//httpget.setHeader("Cookie", cookie);
				
				int resStatu = responce.getStatusLine().getStatusCode();// 返回码
				System.out.println("返回状态字# " + resStatu);
				if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不对
					// 获得相应实体
					HttpEntity entity = responce.getEntity();
					if (entity != null) {
						
						html = EntityUtils.toString(entity);// 获得html源代码
//						System.out.println(html);
//	 				    document = new Document(html);
						document = Jsoup.parse(new URL(url).openStream(), "GBK", url);
//						System.out.println("document:" + document);
					}
				}
			} catch (Exception e) {
				System.out.println("访问【" + url + "】出现异常!");
				System.err.println("当前使用的IP为： " + ip);
				if (ip != null) { //如果该IP地址不为空并且发生异常，就会从集合移除该IP地址
					ProxyHost.removeIp(ip);
				}
				e.printStackTrace();
			} finally {
				httpClient.getConnectionManager().shutdown();
			}
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return document;

	}
	
	/**
	 * 根据URL获得所有的html信息
	 * 
	 * @param url
	 * @return
	 */
	public static Document getDocumentByUrl(String url,Map map) {//更换IP地址
		System.out.println("更换IP获取Document...");
		IP ip = null;
		String html = null;
		Document document = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
			HttpGet httpget = new HttpGet(url);// 以get方式请求该URL			
			if (ReadConfig.isProxy) { // 是否更换请求IP和端口号
				HttpHost proxy = null;
				if (ReadConfig.isuseGoAgent) { // isuseGoAgent 是false的时候更换IP
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
			httpget.setHeader("User-Agent", UserAgent.getUserAgent());
			//							 Hm_lvt_929c0ab204c30f9dd1605aa47b476299=1523442759; bdshare_ty=0x18; dede_pid=1523451294; Hm_lpvt_929c0ab204c30f9dd1605aa47b476299=1523451299		
			if(map!=null) {
				
				Iterator iterator=map.entrySet().iterator();
				while(iterator.hasNext()) {
					Map.Entry e=(Entry) iterator.next();
					
					httpget.setHeader(e.getKey().toString(), e.getValue().toString());
				}
			}
//			httpget.setHeader("Host", "www.xkb1.com");
//			httpget.setHeader("Referer", "https://www.xkb1.com/plus/download.php?open=0&aid=191649&cid=3");
			//		     httpget.setHeader("Content-Length","215");//httpget.setHeader("Cookie", "JSESSIONID=8689A94988C070D93CCC50724BE3313C; gs_b_tas=BOTQRH5QT48IDHBO; counter=3");
			try {
				HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
				//String cookie = responsce.getHeaders("Cookie").toString();
				//System.out.println("cookie = "+cookie);
				//httpget.setHeader("Cookie", cookie);
				
				int resStatu = responce.getStatusLine().getStatusCode();// 返回码
				System.out.println("返回状态字# " + resStatu);
				if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不对
					// 获得相应实体
					HttpEntity entity = responce.getEntity();
					if (entity != null) {
						
						html = EntityUtils.toString(entity);// 获得html源代码
//						System.out.println(html);
//	 				    document = new Document(html);
						document = Jsoup.parse(new URL(url).openStream(), "GBK", url);
//						System.out.println("document:" + document);
					}
				}
			} catch (Exception e) {
				System.out.println("访问【" + url + "】出现异常!");
				System.err.println("当前使用的IP为： " + ip);
				if (ip != null) { //如果该IP地址不为空并且发生异常，就会从集合移除该IP地址
					ProxyHost.removeIp(ip);
				}
				e.printStackTrace();
			} finally {
				httpClient.getConnectionManager().shutdown();
			}
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return document;

	}
	
}



