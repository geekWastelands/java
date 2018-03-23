package Hainan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HaiKou {

	public static void main(String[] args) {
		int cnt=0;
		while(cnt<3){
			System.out.println("page="+cnt);
			String url=null;
			if(cnt==0)
				url="http://wsj.haikou.gov.cn/zwgk/xzsp/index.htm";
			else
				url="http://wsj.haikou.gov.cn/zwgk/xzsp/index_"+cnt+".htm";
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
			Elements lists=doc1.select(".zmenu1 tr");
			for(Element list:lists){
				if(list==lists.last())break;
				String articleurl=list.select("a").attr("abs:href");
				String title=list.select("a").text();
				String date=list.select("td").last().text();
				System.out.println("articleurl="+articleurl);
				System.out.println("title="+title);
				System.out.println("date="+date);
				Document doc2=null;
				for(int j=0;j<10;j++){
					try {
						doc2=Jsoup.connect(articleurl).get();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(doc2!=null)break;
				}
				String doc=doc2.toString();
				String detail=doc2.select(".artlist tr td div").eq(1).text();
				
				System.out.println("detail="+detail);
				if(detail.length()==0)continue;
				String author=detail.substring(detail.indexOf("作者")+3, detail.indexOf("文章来源"));
				String _from=detail.substring(detail.indexOf("源")+2, detail.indexOf("更新"));
				System.out.println("from="+_from);
				System.out.println("author="+author);
				Object[] parms={doc,articleurl,title,date,author,_from};
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
