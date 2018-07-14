package TianJin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class tianjin {

	public static void main(String[] args) {
		HashMap<String, Integer>L = new HashMap<String, Integer>();
		L.put("http://wsjs.tj.gov.cn/html/wsjn/GZDT22913/List/list_", 657);
		L.put("http://wsjs.tj.gov.cn/html/wsjn/ZXXX23008/List/list_",1473);
		L.put("http://www.tjwsj.gov.cn/html/WSJn/QXWSXX22914/List/list_", 220);
		for(Entry<String, Integer> entry : L.entrySet()){//遍历网址
			int cnt=0;
			while(cnt<entry.getValue()){
				
				String url=entry.getKey()+cnt+".htm";//字符串拼接出具体的网址
				System.out.println("page = "+url);
				cnt++;
				Document doc1=null;
				for (int j = 0; j < 10; j++) {//尝试十次连接
					try {
						doc1=Jsoup//使用jsoup包抓取url的源代码
								.connect(url)
								.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
								.get();
						try {
							Thread.sleep(5000);//防止ip被封  休息一下
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (IOException e) {
						System.out.println(e.getMessage());
						break;
					}
					if(doc1!=null)break;//抓到内容即可跳出循环
				}
				Elements Lists=null;
				try {
					Lists=doc1.select("tbody > tr > td:nth-child(1) > a.list");//筛选出所需信息  比如看截图1
				} catch (Exception e) {
					System.out.println(e.getMessage());continue;
				}
				if(Lists==null)continue;
				
				for (Element list : Lists) {//遍历筛选出来的网址
					String titleurl=list.attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
					Document doc2=null;//doc2保存具体新闻页面的源码  基本同上
					for (int j = 0; j < 10; j++) {
						try {
							doc2=Jsoup.connect(titleurl).get();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if(doc2!=null)break;
					}
					String title=doc2.select("font#zoomtitl").text();//文章标题
					System.out.println("title = "+ title);
					String txt=doc2.select("table tbody tr td table tbody").eq(33).text();//文章内容
					String doc=doc2.select("tbody tbody").eq(33).html();//文章源码
					String date=doc2.select("div.time").text();//发布时间
					date=date.substring(date.indexOf('：')+1);
					System.out.println("txt = "+txt);
					System.out.println("date = "+date);
					String source=doc2.select("span.confont").text();//文章来源
					System.out.println("source = "+source);
					Object parms[]={title,titleurl,date,txt,doc,source};//将所有需要的内容放在一个数组中
					try {
						SqlHelper.insertInfo(parms);//将抓到的内容插入数据库
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
