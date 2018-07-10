package weiShengTing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.GetConnect;

public class EZhou {

	public static void main(String[] args) {
		HashMap<String, Integer> urls = new HashMap<String, Integer>();
		urls.put("http://wjw.ezhou.gov.cn/Ilist.aspx?itid=3&page=", 14);
		urls.put("http://wjw.ezhou.gov.cn/Ilist.aspx?itid=2&page=", 22);
		for(Entry<String, Integer> entry:urls.entrySet()){
			for (int i = 1; i <= entry.getValue(); i++) {
				String url = entry.getKey()+i;
				try{
					Document doc1 = GetConnect.connect(url);
					Elements titleurls = doc1.select("#DataList1 > tbody > tr > td > table > tbody > tr > td> a");
					for (Element element : titleurls) {
						String titleurl= element.attr("abs:href");
						System.out.println("titleurl = "+titleurl);
						try {
							Document doc2 = GetConnect.connect(titleurl);
							String title = doc2.select("span#LabelTitle").text();
							System.out.println("title = "+title);
							String date = doc2.select("td.LabelDte").text();
							date = date.substring(date.indexOf("20"));
							System.out.println("date = "+date);
							String txt = doc2.select("#f_bg_in > div:nth-child(2) > table:nth-child(2) > tbody > tr > td > table > tbody > tr:nth-child(4) > td > table > tbody:nth-child(2) > tr > td > table > tbody").text();
							System.out.println("txt = "+txt);
							String html = doc2.select("#f_bg_in > div:nth-child(2) > table:nth-child(2) > tbody > tr > td > table > tbody > tr:nth-child(4) > td > table > tbody:nth-child(2) > tr > td > table > tbody").html();
							Date fetch_time = new Date();
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							System.out.println("fetch_time = "+df.format(fetch_time));
							Object[] parms={title,titleurl,date,txt,html,df.format(fetch_time)};
							SqlHelper.insertInfo(parms);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				 
			}
		}
	}

}
