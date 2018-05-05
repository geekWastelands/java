package TianJin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class tianjin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer>L = new HashMap<String, Integer>();
		L.put("http://wsjs.tj.gov.cn/html/wsjn/GZDT22913/List/list_", 657);
		L.put("http://wsjs.tj.gov.cn/html/wsjn/ZXXX23008/List/list_",1473);
		L.put("http://www.tjwsj.gov.cn/html/WSJn/QXWSXX22914/List/list_", 220);
		for(Entry<String, Integer> entry : L.entrySet()){
			int cnt=0;
			while(cnt<entry.getValue()){
				
				String url=entry.getKey()+cnt+".htm";
				System.out.println("page = "+url);
				/*if(cnt==37){
					cnt++;
					continue;
				}*/
				cnt++;
				Document doc1=null;
				for (int j = 0; j < 10; j++) {
					try {
						doc1=Jsoup
								.connect(url)
								.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
								.get();//.parse(new URL(url).openStream(), "GBK", url);//Jsoup.connect(url).timeout(500000).get();
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());//e.getMessage();//.printStackTrace();
						break;
					}
					if(doc1!=null)break;
				}
				//body > table:nth-child(12) > tbody > tr > td:nth-child(3) > table > tbody > tr > td > table:nth-child(2) > tbody > tr > td:nth-child(2) > table:nth-child(7) > tbody > tr > td:nth-child(1)
				Elements Lists=null;
				try {
					Lists=doc1.select("tbody > tr > td:nth-child(1) > a.list");
				} catch (Exception e) {
					System.out.println(e.getMessage());continue;
					// TODO: handle exception
				}
				if(Lists==null)continue;
				
				for (Element list : Lists) {
					String titleurl=list.attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
					
					Document doc2=null;
					for (int j = 0; j < 10; j++) {
						try {
							doc2=Jsoup.connect(titleurl).get();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(doc2!=null)break;
					}
					//http://wsjs.tj.gov.cn/html/wsjn/GZDT22913/2018-02-11/Detail_647973.htm
					//System.out.println("doc2 = "+doc2);
					//body > table:nth-child(14) > tbody > tr > td > table > tbody
					String title=doc2.select("font#zoomtitl").text();
					//title=title.substring(2);
					System.out.println("title = "+ title);
					String txt=doc2.select("table tbody tr td table tbody").eq(33).text();
					String doc=doc2.select("tbody tbody").eq(33).html();
					String date=doc2.select("div.time").text();
					date=date.substring(date.indexOf('：')+1);
					System.out.println("txt = "+txt);
					//System.out.println("html = "+doc);
					System.out.println("date = "+date);
					//if(date.substring(0, 4)==null)continue;
					//if(date.substring(0, 4).compareTo("2016")<0)continue;
					String source=doc2.select("span.confont").text();
					System.out.println("source = "+source);
					Object parms[]={title,titleurl,date,txt,doc,source};
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
