package ZheJiang;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import utils.GetConnect;
import utils.GetDocument;

public class ShaoXing {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		int[][] id={{2678,9863},
				{666,9870}
				};
		for(int i=0;i<2;i++){
			int page=0;
			while(page<=id[i][0]/90){
				String url="http://www.sxws.gov.cn/module/jslib/jquery/jpage/dataproxy.jsp?startrecord="+page*90+"&endrecord="+id[i][0]+"&perpage=30&appid=1&webid=34&path=%2F&columnid="+id[i][1]+"&sourceContentType=1&unitid=15357&webname=%E7%BB%8D%E5%85%B4%E5%B8%82%E5%8D%AB%E7%94%9F%E5%92%8C%E8%AE%A1%E5%88%92%E7%94%9F%E8%82%B2%E5%A7%94%E5%91%98%E4%BC%9A&permissiontype=0";
				page++;
				Document doc1 = null;
				System.out.println("url = "+url);
				doc1=GetConnect.getDocumentByUrl(url);
//				Connection con = Jsoup.connect(url);
//				con.header("Host", "www.sxws.gov.cn");
//				con.header("Upgrade-Insecure-Requests", "1");
//				con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//		        con.header("Accept-Encoding", "gzip, deflate");
//		        con.header("Accept-Language", "zh-CN,zh;q=0.9");
//				con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
//		        con.header("Cache-Control", "max-age=0");
//		        con.header("Connection", "keep-alive");
//		        con.header("Cookie", "JSESSIONID=E39088CCD268B0510E484B34A2015BF3; gs_b_tas=BOTQRH5QT48IDHBO; counter=3");
//				for (int j = 0; j < 10; j++) {
//					try {
//						doc1 = con.timeout(3000).get();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					if(doc1!=null)break;
//					Thread.sleep(300);
//				}
				Elements lists=doc1.select("body > table > tbody > tr > td:nth-child(2) > a");
				for (Element list : lists) {
					String betitleurl = list.attr("href");
					String titleurl="http://www.sxws.gov.cn"+betitleurl.substring(betitleurl.indexOf('\'')+1,betitleurl.lastIndexOf('\'')-1);
					System.out.println("href="+titleurl);
					Document doc2 = null;
					/*for (int j = 0; j < 10; j++) {
						try {
							doc2=Jsoup.connect(titleurl).get();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Thread.sleep(300);
					}*/
					doc2=GetDocument.changeIp(titleurl);
					String title=doc2.select("td.title").text();
					String txt=doc2.select("div#zoom").text();
					String html=doc2.select("div#zoom").html();
					String date=doc2.select("#article > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(1) > td:nth-child(1) > span").text();
					date=date.substring(date.indexOf('：')+1);
					if(date.substring(0,4).compareTo("2016")<0)continue;
					System.out.println("title = "+title);
					System.out.println("txt = "+txt);
					System.out.println("date = "+date);
					Object parms[]={title,titleurl,date,txt,html};
					try {
						SqlHelper.insertInfo(parms);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}//http://www.sxws.gov.cn/module/jslib/jquery/jpage/dataproxy.jsp?startrecord=2611&endrecord=2678&perpage=30&appid=1&webid=34&path=%2F&columnid=9863&sourceContentType=1&unitid=15357&webname=%E7%BB%8D%E5%85%B4%E5%B8%82%E5%8D%AB%E7%94%9F%E5%92%8C%E8%AE%A1%E5%88%92%E7%94%9F%E8%82%B2%E5%A7%94%E5%91%98%E4%BC%9A&permissiontype=0
	
}/*
col: 1
appid: 1
webid: 1896
path: /
columnid: 1209919
sourceContentType: 1
unitid: 3978136
webname: 温州市卫计委
permissiontype: 0

appid: 1
webid: 34
path: /
columnid: 9863
sourceContentType: 1
unitid: 15357
webname: 绍兴市卫生和计划生育委员会
permissiontype: 0

appid: 1
webid: 34
path: /
columnid: 9870
sourceContentType: 1
unitid: 15357
webname: 绍兴市卫生和计划生育委员会
permissiontype: 0*/