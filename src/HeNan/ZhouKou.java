package HeNan;

import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ZhouKou {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//http://www.zkwsjd.com/NewsList.aspx?ParentId=2%20&p=1
		int cnt=1;
		String url="http://www.zkwsjd.com/NewsList.aspx?ParentId=2%20&p=";
		while (cnt<=7) {
			url+=cnt;
			Document doc1=null;
			for (int j = 0; j < 10; j++) {
				try {
					doc1=Jsoup
							.connect(url)
							.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
							//Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0				
							//Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36
							.get();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(doc1!=null)break;
			}
			if(doc1==null)continue;
			Elements List=doc1.select("ul#newslist li");
			for (Element list : List) {
				String titleurl=list.select("a").attr("abs:href");
				String date=list.select("span").text();
				System.out.println("titleurl = "+titleurl);
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
				String title=doc2.select("div.sec h3").text();
				System.out.println("title = "+title);
				String txt=doc2.select("div.newdiv").text();
				String doc=doc2.select("div.newdiv").html();
				System.out.println("txt = "+txt);
				Object parms[]={title,titleurl,date,txt,doc};
				/*try {
					SqlHelper.insertInfo(parms);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		}
	}

}
