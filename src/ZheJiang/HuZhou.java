package ZheJiang;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.GetConnect;
import utils.GetDocument;

public class HuZhou {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> sub = new HashMap<String, Integer>();
		sub.put("http://www.hbhc.gov.cn/home.do?method=govChannelList&channelId=11&pageno=", 13);
		sub.put("http://www.hbhc.gov.cn/home.do?method=govChannelList&channelId=4&pageno=", 43);
		for (Entry<String, Integer> entry: sub.entrySet()) {
			int cnt=1;
			while(cnt<=entry.getValue()){
				String url=entry.getKey()+cnt;
				System.out.println("url = "+url);
				cnt++;
				Document doc1=null;
				doc1=GetConnect.getDocumentByUrl(url);
				//System.out.println(doc1);
				Elements lists=doc1.select("body > div.bodyAll > div.bodyM > div.bodyMBody > ul > li");
				for (Element list : lists) {
					String titleurl=list.toString();//titleurl.indexOf("<a href=")+1
					titleurl="http://www.hbhc.gov.cn/"+titleurl.substring(titleurl.indexOf("<a href=")+9, titleurl.indexOf("articleId")+14);
					System.out.println("titleurl = "+titleurl);
					String date=list.select("a span").text();
					if(date.substring(0,4).compareTo("2016")<0)continue;
					System.out.println("date = "+date);
					Document doc2=null;
					doc2=GetConnect.getDocumentByUrl(titleurl);
					System.out.println(doc2);
					String title=doc2.select("div.sl_articleTitle h3").text();
					System.out.println("title = "+title.toString());
					String from=doc2.select("div.sl_articleTitle li.sl_left").last().text();
					from=from.substring(from.indexOf("：")+1);
					System.out.println("from ="+from);
					String txt=doc2.select("div.sl_articleBody").text();
					String html=doc2.select("div.sl_articleBody").html();
					System.out.println("txt = "+txt);
					Object parms[]={title,titleurl,date,from,txt,html};
				}
			}
			
		}
	}

}
