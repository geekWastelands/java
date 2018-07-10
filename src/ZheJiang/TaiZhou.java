package ZheJiang;

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

public class TaiZhou {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer>L = new HashMap<String, Integer>();
		//			http://wjw.jiyuan.gov.cn/gzdt/index.html
		//			 http://www.tzswjw.gov.cn/gzdt/class/index.php?69.html&page=2&showtj=&showhot=&author=&key=
		L.put("http://www.tzswjw.gov.cn/gzdt/class/index.php?0.html&page=", 290);
		//L.put("http://www.jhwsj.gov.cn/xwzx/gzdt/index", 5);
		//L.put("http://wjw.wenzhou.gov.cn/col/col1209924/index.html", 37);
		for(Entry<String, Integer> entry : L.entrySet()){
			int cnt=1;
			while(cnt<=entry.getValue()){
				String url=entry.getKey();
				url+=cnt+"&showtj=&showhot=&author=&key=";
				System.out.println("page = "+url);
				cnt++;
				Document doc1=null;
				for (int j = 0; j < 10; j++) {
					try {
						doc1=Jsoup
								.connect(url)
								.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
								.get();//.parse(new URL(url).openStream(), "GBK", url);//Jsoup.connect(url).timeout(500000).get();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());//e.getMessage();//.printStackTrace();
						break;
					}
					if(doc1!=null)break;
				}
				//System.out.println(doc1);
				Elements Lists=null;
				try {
					Lists=doc1.select("#container > div > table > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(2) > td > table:nth-child(1) > tbody > tr");
					//System.out.println(Lists);//body > table:nth-child(5) > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(3) > td > table > tbody > tr > td > table > tbody > tr:nth-child(4) > td.line_buttom:nth-child(2) > a
				} catch (Exception e) {
					//System.out.println(e.getMessage());continue;
					e.printStackTrace();
					// TODO: handle exception
				}
				if(Lists==null)continue;
				for (Element list : Lists) {
					String titleurl=list.select("a").attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
					String date=list.select(".index_time").text();
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
					//System.out.println("doc2 = "+doc2);
					if(doc2==null)continue;
					String title=doc2.select("td.newstitle span").eq(1).text();
					System.out.println("title = "+ title);
					String txt=doc2.select("td.right_c > table:nth-child(3)").text();
					String doc=doc2.select("td.right_c > table:nth-child(3)").html();
					System.out.println("txt = "+txt);
					/*String authors=doc2.select("span#authors").text();
					System.out.println("authors = "+authors);
					String source=doc2.select("span#sources").first().text();
					System.out.println("source = "+source);
					String date=doc2.select("span#filldate").text();*/
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



