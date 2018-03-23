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

public class YuLin {

	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Integer>num=new HashMap<String, Integer>();
		num.put("http://www.yulin.gov.cn:8088/channel/030005/content?pageNo=", 22);
		num.put("http://www.yulin.gov.cn:8088/channel/030003/content?pageNo=", 13);
		num.put("http://www.yulin.gov.cn:8088/channel/030006/content?pageNo=", 3);
		
		for(Entry<String, Integer> entry : num.entrySet()){
			int cnt=1;
			System.out.println("当前页面 = "+cnt);
			//System.out.println("地址 = "+url);
			String url=null;
			while (cnt<=entry.getValue()) {
				/*if (cnt==0) {
					url=entry.getKey()+".html";
				} else {
					url=entry.getKey()+"_"+cnt+".html";
				}*/
				url=entry.getKey()+cnt;
				
				System.out.println("地址 = "+url);
				cnt++;
				Document doc1=null;
				for(int j=0;j<10;j++){
					try {
						doc1 = Jsoup.parse(new URL(url).openStream(), "GBK", url);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(doc1!=null)break;
				}
				Elements lists=doc1.select("table#infolist tr td");
				for (Element list : lists) {
					String titleurl=list.select("a").attr("abs:href");
					String title=list.select("a").text();
					String date=list.select("div#time").text();
					//date=date.substring(date.indexOf('[')+1, date.indexOf(']'));
					if(date.substring(0, 4).compareTo("2015")<=0)continue;
					System.out.println("date = "+date);
					System.out.println("title = "+title);
					System.out.println("titleurl = "+titleurl);
					Document doc2=null;
					for (int j = 0; j < 10; j++) {
						try {
							//doc2=Jsoup.connect(titleurl).get();
							doc2 = Jsoup.parse(new URL(titleurl).openStream(), "GBK", titleurl);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							String TXT="D:\\GuangXi\\yulin.txt";
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
					String doc=doc2.select("div#content").html();
					String doc3=" ",doc4=" ",txt1=" ",txt2=" ";
					/*if(doc.length()>10485760){
						doc3=doc.substring(doc.length()/3,doc.length()/3*2);
						doc4=doc.substring(doc.length()/3*2);
						doc=doc.substring(0,doc.length()/3);
					}*/
					String txt=doc2.select("div#content").text();
					txt=txt.replace(Jsoup.parse("&nbsp;").text(), " ");
					/*if(txt.length()>10485760){
						txt1=txt.substring(txt.length()/3,txt.length()/3*2);
						txt2=txt.substring(txt.length()/3*2);
						txt=txt.substring(0,txt.length()/3);
					}*/
					if(doc==null)continue;
					//System.out.println("doc = "+doc);
					System.out.println("txt = "+txt);
					String source=doc2.select("div#source").text();//来源：梧州市卫生和计划生育委员会)
					if(0==source.length())continue;
		
					source=source.substring(source.indexOf('：')+1);
					//String author=doc2.select(".article .page_date span").eq(1).text();
					//author=author.substring(author.indexOf('：')+1);
					
					//System.out.println("source = "+source);
					//System.out.println("author = "+author);
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

}
