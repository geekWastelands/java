package GuangXi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LaiBin {

	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Integer>num=new HashMap<String, Integer>();
		num.put("http://www.lbswsj.gov.cn/list.aspx?cabh=12&page=", 11);
		num.put("http://www.lbswsj.gov.cn/list.aspx?cabh=03&page=", 5);
		num.put("http://www.lbswsj.gov.cn/list.aspx?cabh=13&page=", 2);
		num.put("http://www.lbswsj.gov.cn/list.aspx?cabh=05&page=", 3);
		num.put("http://www.lbswsj.gov.cn/list.aspx?cabh=18&page=", 2);
		for(Entry<String, Integer> entry : num.entrySet()){
			int cnt=1;
			System.out.println("当前页面 = "+cnt);
			//System.out.println("地址 = "+url);
			String url=null;
			while (cnt<=entry.getValue()) {
				/*if (cnt==1) {
					url=entry.getKey()+".html";
				} else {
					url=entry.getKey()+"_"+cnt+".html";
				}	*/			
				url=entry.getKey()+cnt;
				System.out.println("地址 = "+url);
				cnt++;
				Document doc1=null;
				for(int j=0;j<10;j++){
					try {
						doc1 = Jsoup.parse(new URL(url).openStream(), "utf-8", url);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(doc1!=null)break;
				}
				Elements lists=doc1.select("ul.list_news li");
				for (Element list : lists) {
					String titleurl=list.select("a").attr("abs:href");
					String title=list.select("a").text();
					
					System.out.println("title = "+title);
					System.out.println("titleurl = "+titleurl);
					Document doc2=null;
					for (int j = 0; j < 10; j++) {
						try {
							//doc2=Jsoup.connect(titleurl).get();
							doc2 = Jsoup.parse(new URL(titleurl).openStream(), "utf-8", titleurl);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							String TXT="D:\\GuangXi\\laibin.txt";
							RandomAccessFile random=new RandomAccessFile(TXT, "rw");
							long fileLength = random.length();  
				            // 将写文件指针移到文件尾。  
				            random.seek(fileLength);  
				            random.writeChars(title+'\t'+titleurl+'\t'+e.getMessage()+'\n');  
				            random.close();  
							e.printStackTrace();
						}
						if (doc2!=null) {
							break;
						}
					}
					String doc=doc2.select(".bg_map").html();
					String txt=doc2.select(".bg_map").text();
					txt=txt.replace(Jsoup.parse("&nbsp;").text(), " ");
					if(doc==null)continue;
					//System.out.println("doc = "+doc);
					System.out.println("txt = "+txt);
					String date=txt.substring(txt.indexOf("间：")+2, txt.indexOf("间：")+12);
					//String date=list.select("#form1 > div.nbox > div.rightbar > div.bg_map > div.cen4").text();//#form1 > div.nbox > div.rightbar > div.bg_map > div.cen4 > br
					
					//date=date.substring(date.indexOf("间："));
					
					if(date.substring(0, 4).compareTo("2015")<=0)continue;
					System.out.println("date = "+date);
					//String source=doc2.select("div#source").text();//来源：梧州市卫生和计划生育委员会)
					//if(0==source.length())continue;
		//			source=source.substring(source.indexOf('：')+1);
					/*String author=doc2.select(".content-3 span").text();
					author=author.substring(author.lastIndexOf('：')+1);
					
					//System.out.println("source = "+source);
					System.out.println("author = "+author);*/
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

}
