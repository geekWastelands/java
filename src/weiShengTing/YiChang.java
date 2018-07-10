package weiShengTing;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.GetConnect;

public class YiChang {

	public static void main(String[] args) {
		HashMap<String, Integer> urls = new HashMap<String, Integer>();
		urls.put("http://wjw.yichang.gov.cn/list-41523-", 40);
		urls.put("http://wjw.yichang.gov.cn/list-41524", 49);
		urls.put("http://wjw.yichang.gov.cn/list-41525-", 70);
		urls.put("http://wjw.yichang.gov.cn/list-41531-", 22);
		for(Entry<String, Integer> entry:urls.entrySet()){
			for (int i = 1; i <= entry.getValue(); i++) {
				String url = entry.getKey()+i+".html";
				Document doc1 = GetConnect.connect(url);
				Elements titleurls = doc1.select("li.new_liebiao a");
				for (Element element : titleurls) {
					String titleurl = element.attr("abs:href");
					if(!titleurl.endsWith(".html"))continue;
					System.out.println("titleurl = "+titleurl);
					Document doc2 = GetConnect.connect(titleurl);
					String title = doc2.select("li.h1").text();
					System.out.println("title = "+title);
					String shuoming = doc2.select("li.shuoming").text();
					String date = shuoming.substring(shuoming.indexOf("20"), shuoming.indexOf("阅读"));
					System.out.println("date = "+date);
					String txt = doc2.select("div#neir li").last().text();
					System.out.println("txt = "+txt);
					String html = doc2.select("div#neir li").last().html();
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
