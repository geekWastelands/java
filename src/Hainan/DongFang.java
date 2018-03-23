package Hainan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DongFang {

	public static void main(String[] args) {
		ArrayList<String> num=new ArrayList<String>();
		num.add("24883");
		num.add("24886");
		num.add("24884");
		num.add("24885");
		num.add("24891");
		num.add("25181");
		num.add("25118");
		num.add("24892");
		num.add("24985");
		int sum=0;
		for(int i=0;i<num.size();i++){
			int page=0;
			while(page<=20){
				String url=null;
				//http://xxgk.hainan.gov.cn/wnxxgk/wsjxxgk/24880/24883/list.htm
				if(page==0)
					url="http://xxgk.hainan.gov.cn/dfxxgk/wsj/24880/"+num.get(i)+"/list.htm";
				else
					url="http://xxgk.hainan.gov.cn/dfxxgk/wsj/24880/"+num.get(i)+"/list_"+page+".htm";
				page++;
				Document doc1=null;
				for(int j=0;j<10;j++){
					try {
						doc1=Jsoup.connect(url).get();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
					if(doc1!=null)break;
				}
				if(doc1.select("body").text().startsWith("±§Ç¸"))
					break;
				System.out.println(url);
				//System.out.println(doc1);
				Elements lists=doc1.select(".bor1 tr a");
				for(Element list :lists){
					ArrayList<String> con=new ArrayList<String>();
					String titleurl=list.attr("abs:href");
					System.out.println("titleurl = "+titleurl);
					con.add(titleurl);
					String title=list.text();
					System.out.println("title = "+title);
					sum++;
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
					Elements contents=doc2.select("tbody tr .a3D");
					for(Element content:contents){
						if(content==contents.get(5))continue;
						String temp=content.text().substring(content.text().indexOf('£º')+1);
						System.out.println(temp);
						con.add(content.text().substring(content.text().indexOf('£º')+1));
					}
					Object parms[]=new Object[con.size()+1];
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
		System.out.println(sum);
	}

}
