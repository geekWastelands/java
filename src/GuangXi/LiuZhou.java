package GuangXi;

import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LiuZhou {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cnt=0;
		String url=null;
		//新闻
		while(cnt<77){
			if(cnt==0)
				url="http://wjw.liuzhou.gov.cn/xwzx/index.html";
			else
				url="http://wjw.liuzhou.gov.cn/xwzx/index_"+cnt+".html";
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
			Elements Lists=doc1.select("div.sublist li");
			for(Element List:Lists){
				String titleurl=List.select("a").attr("abs:href");
				String title=List.select("a").text();
				String date=List.select("span").text();
				System.out.println("title = "+title);
				System.out.println("titleurl = "+titleurl);
				System.out.println("date = "+date);
				
				//新闻详情
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
				String doc=doc2.select(".open-smss").html();
				System.out.println("doc = "+doc);
				String txt=doc2.select(".open-smss").text();
				System.out.println("txt = "+txt);
				Object parms[]={title,titleurl,date,doc,txt};
				try {
					SqlHelper.insertInfo(parms);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//公告
		cnt=0;
		while(cnt<14){

			if(cnt==0)
				url="http://wjw.liuzhou.gov.cn/gsgg/index.html";
			else
				url="http://wjw.liuzhou.gov.cn/gsgg/index_"+cnt+".html";
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
			Elements Lists=doc1.select("div.sublist li");
			for(Element List:Lists){
				String titleurl=List.select("a").attr("abs:href");
				String title=List.select("a").text();
				String date=List.select("span").text();
				System.out.println("title = "+title);
				System.out.println("titleurl = "+titleurl);
				System.out.println("date = "+date);
				
				//新闻详情
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
				String doc=doc2.select(".open-smss").html();
				System.out.println("doc = "+doc);
				String txt=doc2.select(".open-smss").text();
				System.out.println("txt = "+txt);
				Object parms[]={title,titleurl,date,doc,txt};
				try {
					SqlHelper.insertInfo(parms);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		}
		//信息公开
		cnt=0;
		while(cnt<11){
			if(cnt==0)
				url="http://wjw.liuzhou.gov.cn/xxgk/index.html";
			else
				url="http://wjw.liuzhou.gov.cn/xxgk/index_"+cnt+".html";
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
			Elements Lists=doc1.select("div.sublist li");
			for(Element List:Lists){
				String titleurl=List.select("a").attr("abs:href");
				String title=List.select("a").text();
				String date=List.select("span").text();
				System.out.println("title = "+title);
				System.out.println("titleurl = "+titleurl);
				System.out.println("date = "+date);
				
				//新闻详情
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
				String doc=doc2.select(".open-smss").html();
				System.out.println("doc = "+doc);
				String txt=doc2.select(".open-smss").text();
				System.out.println("txt = "+txt);
				Object parms[]={title,titleurl,date,doc,txt};
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
