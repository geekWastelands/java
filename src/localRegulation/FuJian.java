package localRegulation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.processors.JsonValueProcessorMatcher;
import utils.GetConnect;

public class FuJian {
	public static void main(String[] args) {
		HashMap<Integer, Integer> urls=new HashMap();
		urls.put(132, 202);
		urls.put(31463, 120);
		urls.put(134, 709);
		urls.put(633,107);
		urls.put(634,171);
		urls.put(635,90);
		urls.put(636,69);
		urls.put(638,75);
		urls.put(637,128);
		urls.put(639,101);
		urls.put(640,89);
		urls.put(641,113);
		urls.put(738,9);
		urls.put(739,7);
		for(Entry<Integer, Integer> entry : urls.entrySet()){
			Document doc2 = null;
			for (int j = 1; j < entry.getValue(); j++) {
				String url = "http://www.fujian.gov.cn/was5/web/search?channelid=200043&templet=docs.jsp&classsql=chnlid%3D"+j+"&prepage=15";
				doc2 = GetConnect.connect(url);
				//System.out.println("doc2 = "+doc2.select("body").text());
				ArrayList<String[]> arr=Json.JsonString(doc2.select("body").text());
				for (String[] objects : arr) {
//					if(objects[4].compareTo("2016")<0 || objects[0].equals("文章标题"))
//						continue;
					for (String string : objects) {
						System.out.println(string);
					}
					try {
						SqlHelper.insertInfo(objects);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
}


