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

public class SanMenXia {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer>L = new HashMap<String, Integer>();
		L.put("http://www.smxrk.gov.cn/index.aspx?lanmuid=63&sublanmuid=547&page=", 2);
		L.put("http://www.smxrk.gov.cn/index.aspx?lanmuid=63&sublanmuid=539&page=", 6);
		L.put("http://www.smxrk.gov.cn/index.aspx?lanmuid=63&sublanmuid=540&page=", 8);
		//L.put("http://www.pyswjw.gov.cn/list.asp?classid=84&page=", 1);
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
					Lists=doc1.select("ul.article").last().select("li");
				} catch (Exception e) {
					System.out.println(e.getMessage());continue;
					// TODO: handle exception
				}
				if(Lists==null)continue;
				
				for (Element list : Lists) {
					//if(list==Lists.last())break;
					String titleurl=list.select("a").attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
					String date=list.select("span.date").text();
					System.out.println("date = "+date);
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
					String title=doc2.select("li.title h1").text();
					System.out.println("title = "+ title);
					String txt=doc2.select("div.content").text();
					String doc=doc2.select("div.content").html();
					
					System.out.println("txt = "+txt);
					//System.out.println("html = "+doc);
					Object parms[]={title,titleurl,date,txt,doc};
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
