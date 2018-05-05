package ZheJiang;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import utils.GetConnect;
import utils.GetDocument;

public class WenZhou {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		int[][] id={{4200,1209919},//��һ��Ϊ������  �ڶ���Ϊuid
				{5146,1209920},
				{722,1209924}
				};
		for(int i=0;i<1;i++){
			int page=1;
			while(page<=id[i][0]/20){
						  //http://wjw.wenzhou.gov.cn/module/jpage/dataproxy.jsp?startrecord=60&endrecord=4196&perpage=20&col=1&appid=1&webid=1896&path=%2F&columnid=1209919&sourceContentType=1&unitid=3978136&webname=%E6%B8%A9%E5%B7%9E%E5%B8%82%E5%8D%AB%E8%AE%A1%E5%A7%94&permissiontype=0
				String url="http://wjw.wenzhou.gov.cn/module/jpage/dataproxy.jsp?startrecord="+page*60+"&endrecord="+id[i][0]+"&perpage=20&col=1&appid=1&webid=1896&path=%2F&columnid="+id[i][1]+"&sourceContentType=1&unitid=3978136&webname=%E6%B8%A9%E5%B7%9E%E5%B8%82%E5%8D%AB%E8%AE%A1%E5%A7%94&permissiontype=0";
				page++;
				String doc1 = null;
				System.out.println("url = "+url);
				doc1=GetConnect.getDocumentByUrl(url).toString();
				//System.out.println("doc1 = "+doc1);
				String[] lists=doc1.split("target=&quot;_blank&quot; href='");
				for (int j = 1; j < lists.length-1; j++) {
					String titleurl="http://wjw.wenzhou.gov.cn"+lists[j].substring(0,lists[j].indexOf(".html"))+".html";
					System.out.println("titleurl = "+titleurl);
					Document doc2=GetConnect.getDocumentByUrl(titleurl);
					//System.out.println(doc2);
					if(doc2==null)continue;
					String title=doc2.select("#c > tbody > tr:nth-child(1) > td").text();///art/2018/2/23/art_1209919_15582271.html
					System.out.println("title = "+title);
					String date=doc2.select("#c > tbody > tr:nth-child(2) > td > table > tbody > tr > td:nth-child(1)").text();
					date=date.substring(date.indexOf("：")+1);
					System.out.println("date = "+date);
					String txt=doc2.select("#c > tbody > tr:nth-child(3)").text();
					System.out.println("txt = "+txt);
					String html=doc2.select("#c > tbody > tr:nth-child(3)").html();
					Object parms[]= {title,titleurl,date,txt,html};
					try {
						SqlHelper.insertInfo(parms);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		//http://www.sxws.gov.cn/art/2018/3/6/art_9863_1214575.html
		
		
	}//http://www.sxws.gov.cn/module/jslib/jquery/jpage/dataproxy.jsp?startrecord=2611&endrecord=2678&perpage=30&appid=1&webid=34&path=%2F&columnid=9863&sourceContentType=1&unitid=15357&webname=%E7%BB%8D%E5%85%B4%E5%B8%82%E5%8D%AB%E7%94%9F%E5%92%8C%E8%AE%A1%E5%88%92%E7%94%9F%E8%82%B2%E5%A7%94%E5%91%98%E4%BC%9A&permissiontype=0
	
}/*
appid: 1
webid: 34
path: /
columnid: 9863
sourceContentType: 1
unitid: 15357
webname: �����������ͼƻ�����ίԱ��
permissiontype: 0

appid: 1
webid: 34
path: /
columnid: 9870
sourceContentType: 1
unitid: 15357
webname: �����������ͼƻ�����ίԱ��
permissiontype: 0*/