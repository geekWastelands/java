package Hainan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HaiKou_LongHua {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//http://www.haikou.gov.cn/govsearch/searPage.jsp?page=1&pubURL=http://www.haikou.gov.cn/pub/root9/&sinfo=216&surl=http://www.haikou.gov.cn/pub/root9/xyqzf/0108/216/index_1.htm#
		//http://www.haikou.gov.cn/HaiKou/f/search?title=%E9%BE%99%E5%8D%8E%E5%8C%BA&type=search&order_time_asc=&order_time_desc=&startTime=&endTime=&content=&pageNum=2&organization=%E5%8C%BA%E5%8D%AB%E7%94%9F%E5%B1%80&radio_pace=
		int cnt=78;
		while(cnt<=127){
			System.out.println("当前页面 = "+cnt);
			String url="http://www.haikou.gov.cn/HaiKou/f/search?title=%E9%BE%99%E5%8D%8E%E5%8C%BA&type=search&order_time_asc=&order_time_desc=&startTime=&endTime=&content=&pageNum="+cnt+"&organization=%E5%8C%BA%E5%8D%AB%E7%94%9F%E5%B1%80&radio_pace=";
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
				System.out.println("标题 = "+title);
				con.add(title);
				Document doc2=null;
				for(int j=0;j<10;j++){
					try {
						doc2=Jsoup.connect(articleurl).get();
						if(doc2!=null)break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
				}
				if(doc2==null)continue;
				Elements items=doc2.select("div.headInfo tbody tr tbody td");
				for(Element item:items){
					
					if(item==items.first()){
						String item1=item.select("b").text();//�����
						String item2=item.text().substring(item.text().indexOf(':')+1);
						System.out.println(item1+item2);
						con.add(item2);
					}else{
						String item1=item.select("b").text();//����  ��������  ��������  �ĺ�  �����
						String item2=" ";
						item2=item.select("span").text();
//						if(item1.startsWith("����"))
//							item2=item2.substring(0, item2.lastIndexOf("??"));
						con.add(item2);
						System.out.println(item1+item2);
					}
					
				}
				System.out.println(con.size());
				Object[] parms=new Object[con.size()+1];
				parms[0]=doc2.toString();
				for(int t=0;t<con.size();t++){
					parms[t+1]=con.get(t);
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
