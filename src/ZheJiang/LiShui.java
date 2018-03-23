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

public class LiShui {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer>L = new HashMap<String, Integer>();
		//			http://wjw.jiyuan.gov.cn/gzdt/index.html
		//			 http://www.tzswjw.gov.cn/gzdt/class/index.php?69.html&page=2&showtj=&showhot=&author=&key=
		L.put("http://wsjsw.lishui.gov.cn/xwzx/bjdt/index", 24);
		L.put("http://wsjsw.lishui.gov.cn/xwzx/xsdt/index", 24);
		L.put("http://wsjsw.lishui.gov.cn/xwzx/wddt/index", 4);
		L.put("http://wsjsw.lishui.gov.cn/xwzx/zxfw/index", 2);
		L.put("http://wsjsw.lishui.gov.cn/gsgg/tztg/index", 23);
		for(Entry<String, Integer> entry : L.entrySet()){
			int cnt=0;
			while(cnt<=entry.getValue()){
				String url=entry.getKey();
				if(cnt==0){
					url+=".htm";
				}else{
					url+="_"+cnt+".htm";
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
					Lists=doc1.select("table.bk > tbody > tr > td > table:nth-child(2) tr");
					//System.out.println(Lists);//body > table:nth-child(5) > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(3) > td > table > tbody > tr > td > table > tbody > tr:nth-child(4) > td.line_buttom:nth-child(2) > a
				} catch (Exception e) {
					//System.out.println(e.getMessage());continue;
					e.printStackTrace();
					// TODO: handle exception
				}
				if(Lists==null)continue;
				for (Element list : Lists) {
					String titleurl=list.select("td").eq(1).select("a").attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
					String title=list.select("td").eq(1).select("a").text();
					System.out.println("title = "+ title);
					String date=list.select("td").eq(2).text();
					date=date.substring(date.indexOf("[")+1,date.indexOf(']'));
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
					if(doc2==null)continue;
					String txt=doc2.select("table.top8.bk > tbody > tr > td > table:nth-child(3) > tbody").text();
					String doc=doc2.select("table.top8.bk > tbody > tr > td > table:nth-child(3) > tbody").html();
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




