package HeNan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class XinXiang {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//http://www.xxswjw.gov.cn/index.php?_m=article_list&_a=get_page&article_category=4&layer_id=layerCEE6D4C159D28608431667DB6EAC768C&page=3&article_category_more=4
		//layer5944AD714608B582FAFBDF3CE36E8167

		// TODO Auto-generated method stub
		HashMap<Integer, Integer>L = new HashMap<Integer, Integer>();
		L.put(4, 8);
		L.put(5, 19);
		L.put(6, 36);
		L.put(72, 13);
		for(Entry<Integer, Integer> entry : L.entrySet()){
			int cnt=1;
			while(cnt<=entry.getValue()){
				String url="http://www.xxswjw.gov.cn/index.php?_m=article_list&_a=get_page&article_category=";
				/*if(cnt==1){
					url+=entry.getKey()+"&layer_id=layerCEE6D4C159D28608431667DB6EAC768C&page=3&article_category_more="+entry.getKey();
				}else{
					url+=cnt+".html";
				}*/
				url+=entry.getKey()+"&layer_id=layerCEE6D4C159D28608431667DB6EAC768C&page=3&article_category_more="+entry.getKey();
				System.out.println("page = "+url);
				cnt++;
				Document doc1=null;
				for (int j = 0; j < 10; j++) {
					try {
						doc1=Jsoup
								.connect(url)
								.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
								.get();//.parse(new URL(url).openStream(), "GBK", url);//Jsoup.connect(url).timeout(500000).get();
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());//e.getMessage();//.printStackTrace();
						break;
					}
					if(doc1!=null)break;
				}
				Elements Lists=null;
				try {
					Lists=doc1.select("p.link");
				} catch (Exception e) {
					System.out.println(e.getMessage());continue;
					// TODO: handle exception
				}
				if(Lists==null)continue;
				
				for (Element list : Lists) {
					//if(list==Lists.last())break;
					String titleurl=list.select("a").attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
//					String date=list.text();
//					System.out.println("date = "+date);
					Document doc2=null;
					for (int j = 0; j < 10; j++) {
						try {
							doc2=Jsoup.connect(titleurl).get();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(doc2!=null)break;
					}
					String title=doc2.select("div.artdetail_title").text();
					System.out.println("title = "+ title);
					String txt=doc2.select("div.artview_content").text();
					String doc=doc2.select("div.artview_content").html();
					String date=doc2.select("span.pub_txt_span").text();
					date=date.substring(date.indexOf('£º')+1);
					System.out.println("txt = "+txt);
					//System.out.println("html = "+doc);
					System.out.println("date = "+date);
					String source=doc2.select("span.aut_txt_span").text();
					source=source.substring(source.indexOf("£º")+1);
					System.out.println("source = "+source);
					Object parms[]={title,titleurl,date,txt,doc,source};
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
