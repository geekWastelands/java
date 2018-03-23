package KeJian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Download {

	@SuppressWarnings("null")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="http://xuexi.12371.cn/kjjm/index.shtml";
		Document doc1=null;
		for (int j = 0; j < 10; j++) {
			try {
				doc1=Jsoup
						.connect(url)
						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36")
						.get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(doc1!=null)break;
		}
		Elements sub=doc1.select("div.menu_body ul.sub li a");
		//System.out.println(sub);
		for (Element element : sub) {
			//System.out.println("url = "+element.attr("abs:href"));
			Document doc2=null;//p.title1_r a
			for (int j = 0; j < 10; j++) {
				try {
					doc2=Jsoup
							.connect(url)
							.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36")
							.get();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(doc2!=null)break;
			}
			Elements titleurls=doc2.select("p.title1_r a");
			for (Element element2 : titleurls) {
				String titleurl=element2.attr("abs:href");
				System.out.println("titleurl = "+titleurl);
				Document doc3=null;
				for (int j = 0; j < 1; j++) {
					try {
						doc3=Jsoup
								.connect(titleurl)
								.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36")
								.get();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(doc3!=null)break;
				}
				Elements contents=doc3.select("div.xuanji_list > div.lunbo_dyw407 > ul > li > p > a");
				for (Element element3 : contents) {
					String contenturl=element3.attr("abs:href");
					System.out.println("contenturl = "+contenturl);
					Document doc4=null;//div#myFlashContainer
					for (int j = 0; j < 10; j++) {
						try {
							doc4=Jsoup
									.connect(contenturl)
									.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36")
									.get();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(doc4!=null)break;
					}
					String source=doc4.select(".back_ad").html();
					source=source.substring(source.indexOf("videoCenterId")+16, source.indexOf(");//视频生产中心guid")-1);
					System.out.println("source = "+source);
					String jsonurl="http://vdn.apps.cntv.cn/api/getHttpVideoInfo.do?pid="+source+"&tz=-8&from=000dangyuan&url=http://xuexi.12371.cn/2016/07/13/VIDE1468401757461866.shtml&idl=32&idlr=32&modifyed=false";
					System.out.println("jsonurl = "+jsonurl );
					//http://vdn.apps.cntv.cn/api/getHttpVideoInfo.do?pid=00fa9158c366d5c346813230fc5604e2&tz=-8&from=000dangyuan&url=http://xuexi.12371.cn/2016/07/13/VIDE1468401757461866.shtml&idl=32&idlr=32&modifyed=false
					String jsondata=null;
					for (int j = 0; j < 10; j++) {
						try {
							jsondata=Jsoup.connect(jsonurl).get().text();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(jsondata!=null)break;
					}
					JsonParser parser=new JsonParser();
					JsonObject object=(JsonObject) parser.parse(jsondata);
					JsonObject total=object.get("video").getAsJsonObject();
					for (int k = 1; k <= 2; k++) {
						String label="chapters";
						if(k>1)label+=k;
						if(total.get(label).getAsJsonArray()==null)break;
						JsonArray subob=total.get(label).getAsJsonArray();
						System.out.println("subob = "+subob);
						for (int j = 0; j < subob.size(); j++) {
							//JsonObject url_s = ;
							System.out.println("subob.get(j).getAsJsonObject() = "+subob.get(j).getAsJsonObject());
							//JsonObject absurls=JsonArray.fromObject()
							JsonObject absurl=(JsonObject) subob.get(j);
							String downloadurl=absurl.get("url").getAsString();
							System.out.println("absurl = "+downloadurl);
						}
					}
					System.out.println("total = "+total);
				}
			}
		}
		
	}

}
