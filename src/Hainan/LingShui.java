package Hainan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LingShui {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int page=0;
		while(page<=6){
			String url=null;
			if(page==0)
				url="http://www.lingshui.gov.cn/Government/PublicInfoList.aspx?DepartmentId=29";
			else
				url="http://www.lingshui.gov.cn/Government/PublicInfoList.aspx?DepartmentId=29&page="+page;
			page++;
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
			Elements lists=doc1.select("div.show-tips");
			for(Element list:lists){
				
				ArrayList<String>con=new ArrayList<String>();
				String titleurl=list.select("a").attr("abs:href");
				System.out.println("titleurl = "+titleurl);
				con.add(titleurl);
				String title=list.select("a").text();
				System.out.println("title = "+title);
				//con.add(title);
				//String date=list.select("td").eq(3).text();
				//System.out.println("date = "+date);
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
				Elements details=doc2.select(".imf-public-read-table td");
				for(int i=1;i<details.size();i+=2){
					con.add(details.eq(i).text());
					System.out.println(details.eq(i).text());
				}
				String doc=doc2.select(".imf-public-read-text").html();
				con.add(doc);
				System.out.println("doc = "+doc);
				String txt=doc2.select(".imf-public-read-text").text();
				con.add(txt);
				System.out.println("txt = "+txt);
				Object parms[]=new Object[con.size()];
				//parms[0]=doc2.toString();
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
