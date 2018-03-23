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

import com.lowagie.tools.concat_pdf;

public class JiYuan {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer>L = new HashMap<String, Integer>();
		//			http://wjw.jiyuan.gov.cn/gzdt/index.html
		//			http://wjw.jiyuan.gov.cn/gzdt/index_1.html
		L.put("http://wjw.jiyuan.gov.cn/gzdt/index", 24);
		L.put("http://wjw.jiyuan.gov.cn/tzgg/index", 9);
		L.put("http://wjw.jiyuan.gov.cn/zhgl/index", 6);
		L.put("http://wjw.jiyuan.gov.cn/szyy/index", 8);
		L.put("http://wjw.jiyuan.gov.cn/szyy/index", 8);

		for(Entry<String, Integer> entry : L.entrySet()){
			int cnt=0;
			while(cnt<=entry.getValue()){
				String url=entry.getKey();
				if(cnt==0){
					url+=".html";
				}else{
					url+="_"+cnt+".html";
				}
				
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
					Lists=doc1.select("ul.list-text li");
				} catch (Exception e) {
					System.out.println(e.getMessage());continue;
					// TODO: handle exception
				}
				if(Lists==null)continue;
				
				for (Element list : Lists) {
					if(!list.hasText())continue;
					String titleurl=list.select("a").attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
					String date=list.select("span").text();
					System.out.println("date = "+date);
					if(!titleurl.endsWith(".html"))continue;
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
					System.out.println("doc2 = "+doc2);
					if(doc2==null)continue;
					String title=doc2.select("div.panel-body h3").text();
					
					System.out.println("title = "+ title);
					String txt=doc2.select("div.panel-body").text();
					String doc=doc2.select("div.panel-body").html();
					System.out.println("txt = "+txt);
					//System.out.println("html = "+doc);
					/*String source=doc2.select(".text").first().text();
					System.out.println("source = "+source);
					source=source.substring(source.indexOf("£º")+1, source.indexOf("·¢"));*/
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
