package weiShengTing;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lishui.news;
import utils.GetConnect;

public class HuangGangShi {
	/*
	 * @author = wastelands
	 * @date = 2018/5/30
	 */

	public static void main(String[] args) throws IOException {
		//			http://www.hg.gov.cn/module/jslib/jquery/jpage/dataproxy.jsp?startrecord=61&endrecord=120&perpage=20
		//		  col=1&appid=1&webid=1&path=%2F&columnid=30&sourceContentType=1&unitid=8181&webname=%E9%BB%84%E5%86%88%E5%B8%82%E6%94%BF%E5%BA%9C%E9%97%A8%E6%88%B7%E7%BD%91%E7%AB%99&permissiontype=0
//		String url="http://wjw.hg.gov.cn/module/jslib/jquery/jpage/dataproxy.jsp?startrecord=121&endrecord=180&perpage=20&"
//				+"col=1&appid=1&webid=7&path=%2F&columnid=4662&sourceContentType=1&unitid=6375&webname=%E9%BB%84%E5%86%88%E5%B8%82%E5%8D%AB%E7%94%9F%E5%92%8C%E8%AE%A1%E5%88%92%E7%94%9F%E8%82%B2%E5%A7%94%E5%91%98%E4%BC%9A&permissiontype=0";
		HashMap<String, Integer> urls = new HashMap<String, Integer>();
		//新闻
		urls.put("col=1&appid=1&webid=1&path=%2F&columnid=30&sourceContentType=1&unitid=8181&webname=%E9%BB%84%E5%86%88%E5%B8%82%E6%94%BF%E5%BA%9C%E9%97%A8%E6%88%B7%E7%BD%91%E7%AB%99&permissiontype=0", 16115);
		//工作动态
		urls.put("col=1&appid=1&webid=7&path=%2F&columnid=4662&sourceContentType=1&unitid=6375&webname=%E9%BB%84%E5%86%88%E5%B8%82%E5%8D%AB%E7%94%9F%E5%92%8C%E8%AE%A1%E5%88%92%E7%94%9F%E8%82%B2%E5%A7%94%E5%91%98%E4%BC%9A&permissiontype=0", 451);
		//通知公告
		urls.put("col=1&appid=1&webid=7&path=%2F&columnid=4668&sourceContentType=1&unitid=6375&webname=%E9%BB%84%E5%86%88%E5%B8%82%E5%8D%AB%E7%94%9F%E5%92%8C%E8%AE%A1%E5%88%92%E7%94%9F%E8%82%B2%E5%A7%94%E5%91%98%E4%BC%9A&permissiontype=0", 103);
		for(Entry<String, Integer> entry:urls.entrySet()){
			String url=null;
			for(int num=1;num<entry.getValue();num+=19){
				url="http://www.hg.gov.cn/module/jslib/jquery/jpage/dataproxy.jsp?startrecord="+num+"&endrecord="+Integer.toString(num+19)+"&perpage=20&"+entry.getKey();
				Document doc1=GetConnect.connect(url);
				String[] unsplit_urls=doc1.toString().split("A href=&quot;");//切割document获取url
				for (int i=1;i<unsplit_urls.length;i++) {
					//System.out.println("unsplit_urls[i]"+unsplit_urls[i]);
					String unsplit_url=unsplit_urls[i].substring(0, unsplit_urls[i].indexOf("&quot; target"));
					//新闻网址
					String titleurl="http://wjw.hg.gov.cn"+unsplit_url;
					System.out.println("titleurl = "+titleurl);
					Document doc2=GetConnect.connect(titleurl);
					//标题
					String title=doc2.select("h3").text();
					System.out.println("title = "+title);
					//日期
					String date=doc2.select("span.tys-main-zt-aa").last().text();
					date = date.substring(date.indexOf("时间：")+3);
					System.out.println("date = "+date);
					//正文
					String txt = doc2.select("div.Custom_UnionStyle").text();
					//正文源码
					String html = doc2.select("div.Custom_UnionStyle").html();
					System.out.println("txt = "+txt);
					Date fetch_time = new Date();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					System.out.println("fetch_time = "+df.format(fetch_time));
					Object[] parms={title,titleurl,date,txt,html,df.format(fetch_time)};
					try {
						SqlHelper.insertInfo(parms);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}

}
