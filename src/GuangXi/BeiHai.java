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

public class BeiHai {

	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Integer>num=new HashMap<String, Integer>();
		num.put("http://www.bhhb.gov.cn/list.asp?page=&id=3", 24);
		num.put("http://www.bhhb.gov.cn/list.asp?page=&id=68", 2);
		num.put("http://www.bhhb.gov.cn/list.asp?page=&id=73", 1);
		num.put("http://www.bhhb.gov.cn/list.asp?page=&id=9", 5);
		num.put("http://www.bhhb.gov.cn/list.asp?page=&id=11", 3);
		num.put("http://www.bhhb.gov.cn/list.asp?page=&id=20", 2);
		num.put("http://www.bhhb.gov.cn/list.asp?page=&id=21", 3);
		for(Entry<String, Integer> entry : num.entrySet()){
			int cnt=1;
			
			//System.out.println("地址 = "+url);
			String url=null;
			while (cnt<=entry.getValue()) {
				System.out.println("当前页面 = "+cnt);
				url=(String) entry.getKey().subSequence(0, entry.getKey().indexOf('&'))+cnt+entry.getKey().substring(entry.getKey().indexOf('&'));
				System.out.println("地址 = "+url);
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
				Elements lists=doc1.select("tbody").eq(18).select("tbody tr tbody td a");
				for (Element list : lists) {
					String titleurl=list.attr("abs:href");
					String title=list.text();
					System.out.println("title = "+title);
					System.out.println("titleurl = "+titleurl);
					Document doc2=null;
					for (int j = 0; j < 10; j++) {
						try {
							doc2=Jsoup.connect(titleurl).get();
							//doc2 = Jsoup.parse(new URL(titleurl).openStream(), "GBK", titleurl);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							String TXT="D:\\GuangXi\\beihai.txt";
							RandomAccessFile random=new RandomAccessFile(TXT, "rw");
							long fileLength = random.length();  
				            // 将写文件指针移到文件尾。  
				            random.seek(fileLength);  
				            random.writeUTF(titleurl+'\t'+e.getMessage()+'\n');  
				            random.close();  
							e.printStackTrace();
						}
						if (doc2!=null) {
							break;
						}
					}
					String doc=null;
					try{
						doc=doc2.select("tbody").eq(10).html();
					}catch(Exception e){
						String TXT="D:\\GuangXi\\beihai.txt";
						RandomAccessFile random=new RandomAccessFile(TXT, "rw");
						long fileLength = random.length();  
			            // 将写文件指针移到文件尾。  
			            random.seek(fileLength);  
			            random.writeUTF(title+'\t'+titleurl+'\t'+e.getMessage()+'\n');  
			            random.close();  
						e.printStackTrace();
						continue;
					}
					String txt=doc2.select("tbody").eq(10).text();
					txt=txt.replace(Jsoup.parse("&nbsp;").text(), " ");
					if(doc==null)continue;
					//System.out.println("doc = "+doc);
					//System.out.println("txt = "+txt);
					String date=doc2.select("tbody").eq(10).select("tr").eq(1).text();
					date=date.substring(date.indexOf("间：")+1);
					System.out.println("date = "+date);
					if(4<date.length()&&date.substring(0, 4).compareTo("2015")<=0)continue;
					String source=doc2.select("tbody").eq(10).select("tr").eq(1).text();
					source=(String) source.subSequence(source.indexOf("源：")+1, source.indexOf('：'));//来源：梧州市卫生和计划生育委员会)
					if(0==source.length())continue;
		
					source=source.substring(source.indexOf('：')+1,source.indexOf(')'));
					//String author=doc2.select(".article .page_date span").eq(1).text();
					//author=author.substring(author.indexOf('：')+1);
					
					System.out.println("source = "+source);
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
