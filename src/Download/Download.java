package Download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;

import utils.FileType;
import utils.ProxyHost;
import utils.ReadConfig;
import utils.UserAgent;
import utils.ProxyHost.IP;

public class Download {
/*
 * SZJG 2
 * 
 */
	public static void main(String[] args) throws IOException {
		String down_url= "http://www.tandfonline.com/doi/pdf/10.3109/00207459009000528";
		downLoad(down_url);
	}
	private static void downLoad(String down_url) {
		String type = "";
		String filedir =  "";
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		IP ip = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			if (ReadConfig.isProxy) {
				HttpHost proxy = null;
				if (ReadConfig.isuseGoAgent) {
					proxy = new HttpHost(ReadConfig.proxyIp, ReadConfig.proxyPort);
				} else {
					ip = ProxyHost.getIp(true);
					proxy = new HttpHost(ip.ip, ip.port);
				}
				httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60 * 1000);// 鏉╃偞甯撮弮鍫曟？1閸掑棝鎸?
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60 * 1000);// 閺佺増宓佹导鐘虹翻閺冨爼妫?鐏忓繑妞?
			httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
			HttpGet httpGet = new HttpGet(down_url.trim());
			httpGet.setHeader("User-Agent", UserAgent.getUserAgent());
//			httpGet.setHeader("Cookie", "	SERVER=WZ6myaEXBLGZpQFlLdJmhw==; JSESSIONID=aaa65Qo_l0M6sgqINUOgv; MAID=kxR9Ukd4rKLO5191f+/d3A==; AMCV_4D6368F454EC41940A4C98A6%40AdobeOrg=793872103%7CMCIDTS%7C16786%7CMCMID%7C15799978652579198605521579591839810613%7CMCAID%7CNONE%7CMCAAMLH-1450845275%7C11%7CMCAAMB-1450845275%7Chmk_Lq6TPIBMW925SPhw3Q; s_pers=%20v8%3D1450240475981%7C1544848475981%3B%20v8_s%3DFirst%2520Visit%7C1450242275981%3B%20c19%3Djb%253Aabstract%7C1450242275987%3B%20v68%3D1450240470671%7C1450242275990%3B; s_sess=%20e41%3D1%3B%20s_cpc%3D1%3B%20v31%3D1450240470671%3B; s_cc=true; _ga=GA1.3.673169456.1450240476; optimizelySegments=%7B%22536630985%22%3A%22false%22%2C%22537950427%22%3A%22direct%22%2C%22536570476%22%3A%22none%22%2C%22537380789%22%3A%22ff%22%7D; optimizelyEndUserId=oeu1450240477529r0.9389310884598037");
//			httpGet.setHeader("Referer", "	https://www.baidu.com/baidu?wd=Circular+redirect+to+%27http%3A%2F%2Fajp.amjpathol.org%2Faction%2FconsumeSharedSessionAction%3FJSESSIONID%3DaaadXxD4Pta8l5cJALOgv%26MAID%3DEJJNopBnWkaZ22jPuK7ukA%253D%253D%26SERVER%3DWZ6myaEXBLF%252FdY29RpN4fA%253D%253D%26ORIGIN%3D935622549%26RD%3DRD%27&tn=09060019_2_pg");
//			httpGet.setHeader("Host", "ajp.amjpathol.org");
//			httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
		
			HttpResponse execute = httpClient.execute(httpGet);
			
			type = FileType.getType(execute, down_url);
			if(type == null || "".endsWith(type)) {
				System.out.println("Type UnKnow");
				return;
			}
			System.out.println("====" + type);
		    filedir = "D:/" + getCurrentTime() + type;

			System.err.println(execute.getStatusLine());
			if (execute.getStatusLine().getStatusCode() != 200) {
				if (ip != null) {
					int code = execute.getStatusLine().getStatusCode();
					System.out.println(ip.ip + ":" + ip.port + "---" + code + "--");
					ProxyHost.removeIp(ip);
				}
				return;
			}
			HttpEntity entity = execute.getEntity();
			InputStream inputStream = entity.getContent();
			bufferedInputStream = new BufferedInputStream(inputStream);
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(filedir)));
			byte[] buf = new byte[1024];
			for (int len = 0; (len = bufferedInputStream.read(buf)) != -1;) {
				bufferedOutputStream.write(buf, 0, len);
				bufferedOutputStream.flush();
			}
		}
		catch (Exception e) {
			System.err.println("当前使用的IP为： " + ip);
			if (ip != null) {
				ProxyHost.removeIp(ip);
			}
			File file = new File(filedir);
			if (file.exists()) {
				file.delete();
			}
			e.printStackTrace();
			return;
		} finally {
			try {
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (bufferedOutputStream != null) {
					bufferedOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File file = new File(filedir);
		System.out.println("size: " + file.length() / 1024 + "K");
		if (file.length() < 1024 * 5) {
			file.delete();
			System.err.println("文件大小为：" + filedir.length() + "b，小于5b被删除。");
			return;
		}
	}
	
	public static String getCurrentTime() {
		//得到long类型当前时间
		long l = System.currentTimeMillis();
		//new日期对象
		Date date = new Date(l);
		//转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//		System.out.println(dateFormat.format(date));
		return dateFormat.format(date).toString();
	}
}
