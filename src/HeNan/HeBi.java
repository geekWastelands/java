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

public class HeBi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer>L = new HashMap<String, Integer>();
		L.put("http://wsjsw.hebi.gov.cn/wsjsw/1102809/17522/index", 16);
		L.put("http://wsjsw.hebi.gov.cn/wsjsw/1102777/17520/index", 39);
		L.put("http://wsjsw.hebi.gov.cn/wsjsw/1102776/18225/index", 12);
		/*L.put("http://wsjsw.hebi.gov.cn/wsjsw/1102784/17808/index", 21);
		L.put("http://www.lyws.gov.cn/sort/sort_7", 5);
		L.put("http://www.lyws.gov.cn/sort/sort_17", 9);
		L.put("http://wsjs.zhengzhou.gov.cn/hydt/index", 26);
		L.put("http://wsjs.zhengzhou.gov.cn/hydt/index", 26);*/

		for(Entry<String, Integer> entry : L.entrySet()){
			int cnt=1;
			while(cnt<=entry.getValue()){
				String url=entry.getKey();
				if(cnt==1){
					url+=".html";
				}else{
					url+=cnt+".html";
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
					Lists=doc1.select("div.pro02_area div.portlet ul li");
				} catch (Exception e) {
					System.out.println(e.getMessage());continue;
					// TODO: handle exception
				}
				if(Lists==null)continue;
				
				for (Element list : Lists) {
					if(list==Lists.last())break;
					String titleurl=list.select("a").attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
//					String date=list.text();
//					System.out.println("date = "+date);
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
					String title=doc2.select("h3#articletitle").text();
					System.out.println("title = "+ title);
					String txt=doc2.select("div#maincontent").text();
					String doc=doc2.select("div#maincontent").html();
					String date=doc2.select("span#adddate").text();
					date=date.substring(date.indexOf('£º')+1);
					System.out.println("txt = "+txt);
					//System.out.println("html = "+doc);
					System.out.println("date = "+date);
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
