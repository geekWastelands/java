package GuangXi;

import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NanNing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cnt=0;
		String url=null;
		while(cnt<52){
			System.out.println("当前页面="+cnt);
			if(cnt==0)
				url="http://www.nnws.gov.cn/CL0022/index.html";
			else
				url="http://www.nnws.gov.cn/CL0022/index_"+cnt+".html";
			cnt++;
			Document doc1=null;
			for(int j=0;j<10;j++){
				try {
					doc1=Jsoup.connect(url).get();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(doc1!=null)break;
			}
			Elements Lists=doc1.select("td.ListColumnClassLB1");
			for(Element List:Lists){
				String titleurl=List.select("a").attr("abs:href");//新闻URL
				String title=List.select("a").text();//标题
				String date=List.select(".listtddateLB1").text();//日期
				date=date.substring(date.indexOf('(')+1, date.indexOf(')'));
				if(date.substring(0, 4).compareTo("2015")<=0)continue;
				System.out.println("titleurl = "+titleurl);
				System.out.println("title = "+title);
				System.out.println("date = "+date);
				
				//详情
				Document doc2=null;
				for(int j=0;j<10;j++){
					try {
						doc2=Jsoup.connect(titleurl).get();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(doc2!=null)break;
				}
				String doc=doc2.select("tbody").eq(12).html();
				String txt=doc2.select("tbody").eq(12).text();
				String source=doc2.select(".LawListSub1").text();
				System.out.println("doc = "+doc);
				System.out.println("txt = "+txt);
				System.out.println("source = "+source);
				Object parms[]={title,titleurl,date,doc,txt,source};
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
