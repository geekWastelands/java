package HeNan;


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

public class PuYang {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer>L = new HashMap<String, Integer>();
		L.put("http://www.pyswjw.gov.cn/list.asp?classid=13&page=", 3);
		L.put("http://www.pyswjw.gov.cn/list.asp?classid=14&page=", 1);
		L.put("http://www.pyswjw.gov.cn/list.asp?classid=12&page=", 4);
		L.put("http://www.pyswjw.gov.cn/list.asp?classid=84&page=", 1);
		/*L.put("http://www.lyws.gov.cn/sort/sort_7", 5);
		L.put("http://www.lyws.gov.cn/sort/sort_17", 9);
		L.put("http://wsjs.zhengzhou.gov.cn/hydt/index", 26);
		L.put("http://wsjs.zhengzhou.gov.cn/hydt/index", 26);*/

		for(Entry<String, Integer> entry : L.entrySet()){
			int cnt=1;
			while(cnt<=entry.getValue()){
				String url=entry.getKey();
				/*if(cnt==1){
					url+=".html";
				}else{
					url+="_"+cnt+".html";
				}*/
				url+=cnt;
				System.out.println("page = "+url);
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
				Elements Lists=null;
				try {
					Lists=doc1.select("ul.newslist a");
				} catch (Exception e) {
					System.out.println(e.getMessage());continue;
					// TODO: handle exception
				}
				if(Lists==null)continue;
				
				for (Element list : Lists) {
					//if(list==Lists.last())break;
					String titleurl=list.attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
//					String date=list.select(".date").text();
//					System.out.println("date = "+date);
					Document doc2=null;
					for (int j = 0; j < 10; j++) {
						try {
							doc2=Jsoup
									.connect(titleurl)
									.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
									.get();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(doc2!=null)break;
					}
					String title=doc2.select("div.title h1").text();
					System.out.println("title = "+ title);
					String txt=doc2.select("div#sdcms_content").text();
					String doc=doc2.select("div#sdcms_content").html();
					String date=doc2.select("div.item").text();
					date=date.substring(date.indexOf("日期：")+3, date.indexOf("人气")-1);
					System.out.println("txt = "+txt);
					//System.out.println("html = "+doc);
					System.out.println("date = "+date);
					String source=doc2.select("div.item").text();
					source=source.substring(source.indexOf("来源：")+1, source.indexOf("日期")-3);
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
