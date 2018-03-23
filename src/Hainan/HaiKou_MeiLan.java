package Hainan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HaiKou_MeiLan {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//http://www.haikou.gov.cn/HaiKou/f/search?title=%E7%BE%8E%E5%85%B0%E5%8C%BA&type=search&order_time_asc=&order_time_desc=&startTime=&endTime=&content=&pageNum=2&organization=%E5%8C%BA%E5%8D%AB%E7%94%9F%E5%B1%80&radio_pace=

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		//http://www.haikou.gov.cn/HaiKou/f/search?title=%E7%BE%8E%E5%85%B0%E5%8C%BA&type=search&order_time_asc=&order_time_desc=&startTime=&endTime=&content=&pageNum=9&organization=%E5%8C%BA%E5%8D%AB%E7%94%9F%E5%B1%80&radio_pace=
		int cnt=0;
		while(cnt<=28){
			System.out.println("当前页面= "+cnt);
			String url="http://www.haikou.gov.cn/HaiKou/f/search?title=%E7%BE%8E%E5%85%B0%E5%8C%BA&type=search&order_time_asc=&order_time_desc=&startTime=&endTime=&content=&pageNum="+cnt+"&organization=%E5%8C%BA%E5%8D%AB%E7%94%9F%E5%B1%80&radio_pace=";
			cnt++;
			Document doc1=null;
			for(int j=0;j<10;j++){
				try {
					doc1=Jsoup.connect(url).get();
					if(doc1!=null)break;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Elements articles=doc1.select("div.resultOne");
			for(Element article:articles){
				ArrayList<String> con=new ArrayList<String>();
				String articleurl=article.select("h3 a").attr("href");
				System.out.println("articleurl = "+articleurl);
				con.add(articleurl);
				String title=article.select("h3").text();
				title=title.substring(title.indexOf(' ')+4);
				System.out.println("title = "+title);
				con.add(title);
				con.add(articleurl);
				con.add(title);
				Document doc2=null;
				for(int j=0;j<10;j++){
					try {
						doc2=Jsoup.connect(articleurl).get();
						if(doc2!=null)break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Elements items=doc2.select("div.headInfo tbody tr tbody td");
				for(Element item:items){
					
					if(item==items.first()){
						String item1=item.select("b").text();//�����
						String item2=item.text().substring(item.text().indexOf(':')+1);
						System.out.println(item1+item2);
						con.add(item2);
					}else{
						String item1=item.select("b").text();//����  ��������  ��������  �ĺ�  �����
						String item2=item.select("span").text();
						con.add(item2);
						System.out.println(item1+item2);
					}
					Object parms[]={doc2};
					for(int t=0;t<con.size();t++){
						parms[t]=con.get(t);
					}
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
