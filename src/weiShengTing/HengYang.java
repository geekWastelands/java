package weiShengTing;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.GetConnect;

public class HengYang {

	public static void main(String[] args) {
		HashMap<String, Integer> urls = new HashMap<String, Integer>();
		urls.put("http://wjw.hengyang.gov.cn/xxgk/gzdt/wjyw/index", 83);
		urls.put("http://wjw.hengyang.gov.cn/xxgk/gzdt/xsqdt/index", 53);
		urls.put("http://wjw.hengyang.gov.cn/xxgk/gzdt/zsdw/index", 25);
		urls.put("http://wjw.hengyang.gov.cn/xxgk/gzdt/tzgg/index", 24);
		for(Entry<String, Integer> entry:urls.entrySet()){
			for (int i = 0; i <= entry.getValue(); i++) {
				String url=null;
				if(i==0)
					url=entry.getKey()+".html";
				else {
					url=entry.getKey()+"_"+i+".html";
					
				}
				try {
					Document doc1 = GetConnect.connect(url);
					Elements titleurls = doc1.select("div.erji_y ul li a");
					for (Element element : titleurls) {
						String titleurl = element.attr("abs:href");
						System.out.println("titleurl = "+titleurl);
						Document doc2=GetConnect.connect(titleurl);
						String title = doc2.select("div.xilan_l h2").text();
						System.out.println("title = "+title);
						String date = doc2.select(".laiyuan").text();
						date = date.substring(date.indexOf("时间")+3);
						System.out.println("date = "+date);
						String txt = doc2.select("div.xilan_l div").last().text();
						System.out.println("txt = "+txt);
						String html = doc2.select("div.xilan_l div").last().html();
						Date fetch_time = new Date();
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						System.out.println("fetch_time = "+df.format(fetch_time));
						Object[] parms={title,titleurl,date,txt,html,df.format(fetch_time)};
						SqlHelper.insertInfo(parms);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

}

