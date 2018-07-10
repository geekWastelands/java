package weiShengTing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.GetConnect;

public class XianNing {

	public static void main(String[] args) {
		HashMap<String, Integer> urls = new HashMap<String, Integer>();
		urls.put("http://wjw.xianning.gov.cn/xxgk/gsgg/index", 24);
		urls.put("http://wjw.xianning.gov.cn/dtlm/tzgg/index", 4);
		urls.put("http://wjw.xianning.gov.cn/dtlm/gzdt/index", 4);
		urls.put("http://wjw.xianning.gov.cn/dtlm/jcsm/index", 7);
		for(Entry<String, Integer> entry:urls.entrySet()){
			for (int i = 0; i <= entry.getValue(); i++) {
				String url=null;
				if(i==0)
					url=entry.getKey()+".shtml";
				else {
					url=entry.getKey()+"_"+i+".shtml";
					
				}
				try {
					Document doc1 = GetConnect.connect(url);
					Elements titleurls = doc1.select("ul.lm_newslist li a");
					for (Element element : titleurls) {
						String titleurl = element.attr("abs:href");
						System.out.println("titleurl = "+titleurl);
						Document doc2=GetConnect.connect(titleurl);
						String title = doc2.select("h1").text();
						System.out.println("title = "+title);
						String date = doc2.select("div.twriter.fl > span:nth-child(2)").text();
						date = date.substring(date.indexOf("时间")+3);
						System.out.println("date = "+date);
						String txt = doc2.select("div.TRS_Editor").text();
						System.out.println("txt = "+txt);
						String html = doc2.select("div.TRS_Editor").html();
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
