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

public class XinYang {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer>L = new HashMap<String, Integer>();
		L.put("http://www.hnxywjw.gov.cn/a/news/dongtai/list_14_", 38);
		L.put("http://www.hnxywjw.gov.cn/a/news/tongzhi/list_13_", 17);
		//http://www.hnxywjw.gov.cn/a/news/xyswjw/list_27_24.html
		L.put("http://www.hnxywjw.gov.cn/a/news/xyswjw/list_27_", 24);
		L.put("http://www.hnxywjw.gov.cn/a/news/xianqu/list_16_", 34);
		L.put("http://www.hnxywjw.gov.cn/a/gongkai/jiaoliu/list_20_", 7);
		for(Entry<String, Integer> entry : L.entrySet()){
			int cnt=1;
			while(cnt<=entry.getValue()){
				String url=entry.getKey();
				url+=cnt+".html";
				System.out.println("page = "+url);
				cnt++;
				Document doc1=null;
				for (int j = 0; j < 10; j++) {
					try {
						doc1=Jsoup
								.connect(url)
								.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
								.get();//.parse(new URL(url).openStream(), "GBK", url);//Jsoup.connect(url).timeout(500000).get();
						/*try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());//e.getMessage();//.printStackTrace();
						break;
					}
					if(doc1!=null)break;
				}
				Elements Lists=null;
				try {
					Lists=doc1.select("div.listbox li");
				} catch (Exception e) {
					System.out.println(e.getMessage());continue;
					// TODO: handle exception
				}
				if(Lists==null)continue;
				
				for (Element list : Lists) {
					//if(list==Lists.last())break;
					String titleurl=list.select("a").attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
					String date=list.select("span").text();
					System.out.println("date = "+date);
					String title=list.select("a").text();
					System.out.println("title = "+ title);
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
					
					String txt=doc2.select("div.content").text();
					String doc=doc2.select("div.content").html();
					
					System.out.println("txt = "+txt);
					//System.out.println("html = "+doc);
					/*String source=doc2.select("p.info span").eq(1).text();
					source=source.substring(source.indexOf("£º"));
					System.out.println("source = "+source);*/
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
