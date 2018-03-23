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

public class HeChi {

	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Integer>num=new HashMap<String, Integer>();
		num.put("http://www.gxhcwsjsw.gov.cn/hc/zwgk/tzgg/index", 6);
		num.put("http://www.gxhcwsjsw.gov.cn/hc/gzdt/zyxw/index", 6);
		num.put("http://www.gxhcwsjsw.gov.cn/hc/gzdt/szdt/index", 5);
		num.put("http://www.gxhcwsjsw.gov.cn/hc/gzdt/gdgzdt/index", 10);
		num.put("http://www.gxhcwsjsw.gov.cn/hc/gzdt/mtjj/index", 4);
		for(Entry<String, Integer> entry : num.entrySet()){
			int cnt=1;
			System.out.println("当前页面 = "+cnt);
			//System.out.println("地址 = "+url);
			String url=null;
			while (cnt<=entry.getValue()) {
				if (cnt==1) {
					url=entry.getKey()+".html";
				} else {
					url=entry.getKey()+"_"+cnt+".html";
				}				
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
				Elements lists=doc1.select("div.column02 ul li");
				for (Element list : lists) {
					String titleurl=list.select("a").attr("abs:href");
					String title=list.select("a").text();
					String date=list.select("span").text();
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
							String TXT="D:\\GuangXi\\hechi.txt";
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
					String doc=doc2.select("div.main").html();
					String txt=doc2.select("div.main").text();
					txt=txt.replace(Jsoup.parse("&nbsp;").text(), " ");
					if(doc==null)continue;
					//System.out.println("doc = "+doc);
					System.out.println("txt = "+txt);
					//String source=doc2.select("div#source").text();//来源：梧州市卫生和计划生育委员会)
					//if(0==source.length())continue;
		//			source=source.substring(source.indexOf('：')+1);
					String author=doc2.select(".content-3 span").text();
					author=author.substring(author.lastIndexOf('：')+1);
					
					//System.out.println("source = "+source);
					System.out.println("author = "+author);
					Object parms[]={title,titleurl,date,doc,txt,author};
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
