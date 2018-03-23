package GuangXi;

import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ChongZuo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cnt=0;
		String url=null;
		while(cnt<68){
			if (cnt==0) {
				url="http://www.chongzuo.gov.cn/govinfo/bm/czsrmzfgzbm/114514003308137175/new.html";
			} else {
				url="http://www.chongzuo.gov.cn/govinfo/bm/czsrmzfgzbm/114514003308137175/new_"+cnt+".html";
			}
			cnt++;
			Document doc1=null;
			for (int j = 0; j < 10; j++) {
				try {
					doc1=Jsoup.connect(url).get();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(doc1!=null)break;
			}
			Elements lists=doc1.select("ul#infomain li");
			for (Element list : lists) {
				String Num=list.select(".indexnum").text();
				String titleurl=list.select("a").attr("abs:href");
				String title=list.select("a").text();
				title=title.substring(title.indexOf("标　　题：")+5, title.indexOf("发布机构："));
				String date=list.select("i").text();
				if(date.substring(0,4).compareTo("2015")<=0)continue;
				String publication=list.select("a tbody tr td").eq(1).text();
				publication=publication.substring(publication.indexOf('：')+1);
				String wenhao=list.select("a tbody tr td").eq(2).text();
				wenhao=wenhao.substring(wenhao.indexOf('：')+1);
				System.out.println("num = "+Num);
				System.out.println("title = "+title);
				System.out.println("titleurl = "+titleurl);
				System.out.println("date = "+date);
				System.out.println("publication = "+publication);
				System.out.println("wenhao = "+wenhao);
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
				//System.out.println("doc2 = "+doc2);
				String doc=doc2.select("div.doccontent").html();//#mcontent > div > div
				String txt=doc2.select("div.doccontent").text();
				txt=txt.replace("?", "");
				System.out.println("txt = "+txt);
				Object parms[]={title,titleurl,Num,date,wenhao,publication,doc,txt};
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
