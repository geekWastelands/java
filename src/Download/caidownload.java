package Download;

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
import org.jsoup.nodes.Document;

import utils.ProxyHost;
import utils.ReadConfig;

public class caidownload {

	public static void main(String[] args) {
		String html = null;
		Document document = null;
        String url="http://cai.7cxk.net/gaozhong/softdown.asp?SoftID=273451";
		HttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
		HttpGet httpget = new HttpGet(url);// 以get方式请求该URL
		

		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 60 * 1000);// 鏉╃偞甯撮弮鍫曟？1閸掑棝鎸?
		httpClient.getParams().setParameter(
				CoreConnectionPNames.SO_TIMEOUT, 60 * 1000);// 閺佺増宓佹导鐘虹翻閺冨爼妫?鐏忓繑妞?
		httpClient.getParams().setParameter(
				ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
		httpget.setHeader("Cookie","	_ga=GA1.2.608097515.1449985522; CNZZDATA1727188=cnzz_eid%3D1514282972-1449983446-http%253A%252F%252Fbzko.com%252F%26ntime%3D1450411864; safedog-flow-item=FC7044C68272286D7E203E68B9BE98FC; ASP.NET_SessionId=m3vfik2y2tgrtt45ponbbrrl; _gat=1");
		httpget.setHeader("Host","	cai.7cxk.net");
		try {
			HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
			
			int resStatu = responce.getStatusLine().getStatusCode();// 返回码
			System.out.println("返回状态字# " + resStatu);
			if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不对
				// 获得相应实体
				HttpEntity entity = responce.getEntity();
				if (entity != null) {
					html = EntityUtils.toString(entity);// 获得html源代码
//					System.out.println(html);
					document = new Document(html);
//					System.out.println("document:" + document);
				}
			}
		} catch (Exception e) {
			System.out.println("访问【" + url + "】出现异常!");
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

	
	
	}

}
