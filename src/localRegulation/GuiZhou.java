package localRegulation;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.GetConnect;

public class GuiZhou {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String , Integer> urls=new HashMap();
		urls.put("http://www.gzgov.gov.cn/xxgk/jbxxgk/fgwj/szfwj_8191/szfl_8192/index", 6);
		urls.put("http://www.gzgov.gov.cn/xxgk/jbxxgk/fgwj/szfwj_8191/qff_8193/index", 25);
		urls.put("http://www.gzgov.gov.cn/xxgk/jbxxgk/fgwj/szfwj_8191/qfh_8194/index", 42);
		urls.put("http://www.gzgov.gov.cn/xxgk/jbxxgk/fgwj/szfwj_8191/qfr_8195/index", 28);
		urls.put("http://www.gzgov.gov.cn/xxgk/jbxxgk/fgwj/szfwj_8191/qfbf_8196/index", 68);
		urls.put("http://www.gzgov.gov.cn/xxgk/jbxxgk/fgwj/szfwj_8191/qfbh_8197/index", 35);
		urls.put("http://www.gzgov.gov.cn/xxgk/jbxxgk/fgwj/szfwj_8191/wjxgfzqk_8198/xgfzqk/index", 2);
		urls.put("http://www.gzgov.gov.cn/xxgk/jbxxgk/fgwj/szfwj_8191/wjxgfzqk_8198/sxfzwj/index", 10);
		for(Entry<String, Integer>entry:urls.entrySet()) {
			String url=null;
			for(int i=0;i<entry.getValue();i++) {
				if(i==0)
					url=entry.getKey()+".html";
				else
					url=entry.getKey()+"_"+i+".html";
				Document doc1=GetConnect.connect(url);
				Elements lists=doc1.select(".right-list-box ul li");
				for (Element list : lists) {
					String titleurl=list.select("a").attr("abs:href");
					String date = list.select("span").text();
					System.out.println("titleurl = "+titleurl);
					System.out.println("date = "+date);
					Document doc2 = GetConnect.connect(titleurl);
					String title = doc2.select(".Article_bt h1").text();
					System.out.println("title = "+title);
					String source = doc2.select("#c > div.Article_ly > span:nth-child(1)").text();
					System.out.println("source = "+source);
					source = source.substring(source.indexOf("：")+1);
					
					String wenhao="";
					int begin=0;
					try {
						begin=title.lastIndexOf("黔");
						//System.out.println("begin = "+begin);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						if(begin==-1) {
							wenhao="";
						}else {
							wenhao = title.substring(title.lastIndexOf("黔"),title.length()-1);
						}
					}
					if(begin!=-1)
						wenhao = title.substring(title.lastIndexOf("黔"),title.length()-1);
					
					System.out.println("文号 = "+wenhao);
					String content=doc2.select("div.zw-con").text();
					String content_html=doc2.select("div.zw-con").html();
					System.out.println("content = "+content);
					Date dNow = new Date( );
				    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
					System.out.println("fetch_date = "+ft.format(dNow));
					Object[] parms= {titleurl,title,date,wenhao,content,content_html,ft.format(dNow)};
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
