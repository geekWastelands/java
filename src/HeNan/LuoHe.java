package HeNan;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LuoHe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//http://www.lhswsj.gov.cn/viewlist.aspx?id=6&__VIEWSTATE=%2FwEPDwULLTE2NjM5NDQxNTQPZBYCAgIPZBYEAgEPFgIeCWlubmVyaHRtbAVB5oKo546w5Zyo5omA5Zyo55qE5L2N572u77ya6aaW6aG1ID4%2BIOaWsOmXu%2BWKqOaAgSA%2BPiDpgJrnn6XlhazlkYpkAgUPZBYOAgEPDxYCHgRUZXh0BQMxODRkZAIDDw8WAh8BBQIxMGRkAgUPDxYCHwEFAjEwZGQCBw8PFgIeB0VuYWJsZWRnZGQCCQ8PFgIfAmdkZAILDw8WAh8CaGRkAg0PDxYCHwJoZGRkBCkQob4aUxyI1WrQYsfHqH7hjJ%2FY3ky%2FfC8mS%2FGzmHg%3D&__EVENTVALIDATION=%2FwEdAAeS7fFMrnC%2BHFs1zU21OV57KbAD7kDPkSOT98gzhXjshjeaFU0DzZvdszOWxvCOPayv7BqM4%2F4F2VBU1mM39KMPX2N3yaUGO0j6TvAm3oKUaxruMDl3cJG1iFSNFSZ46ZROLGuIiUqggTbyiF6HuzSizSUX6PLExDcak3%2BuenlOJsZ2sButja5UlUMY5UEYHUE%3D&PageNavigator1%24txtNewPageIndex=2&PageNavigator1%24LnkBtnGoto=%E8%BD%AC%E5%88%B0%E6%AD%A4%E9%A1%B5

		HashMap<Integer, Integer>L = new HashMap<Integer, Integer>();
		L.put(4, 124);
		//L.put(2, 48);//31
		//L.put(6, 10);
		//L.put(3, 8);
		for(Entry<Integer, Integer> entry : L.entrySet()){
			int cnt=81;
			if(entry.getKey()==2)cnt=31;
			while(cnt<=entry.getValue()){
				String url="http://www.lhswsj.gov.cn/viewlist.aspx?id=";
				url+=entry.getKey()+"&__VIEWSTATE=%2FwEPDwULLTE2NjM5NDQxNTQPZBYCAgIPZBYEAgEPFgIeCWlubmVyaHRtbAVB5oKo546w5Zyo5omA5Zyo55qE5L2N572u77ya6aaW6aG1ID4%2BIOaWsOmXu%2BWKqOaAgSA%2BPiDpgJrnn6XlhazlkYpkAgUPZBYOAgEPDxYCHgRUZXh0BQMxODRkZAIDDw8WAh8BBQIxMGRkAgUPDxYCHwEFAjEwZGQCBw8PFgIeB0VuYWJsZWRnZGQCCQ8PFgIfAmdkZAILDw8WAh8CaGRkAg0PDxYCHwJoZGRkBCkQob4aUxyI1WrQYsfHqH7hjJ%2FY3ky%2FfC8mS%2FGzmHg%3D&__EVENTVALIDATION=%2FwEdAAeS7fFMrnC%2BHFs1zU21OV57KbAD7kDPkSOT98gzhXjshjeaFU0DzZvdszOWxvCOPayv7BqM4%2F4F2VBU1mM39KMPX2N3yaUGO0j6TvAm3oKUaxruMDl3cJG1iFSNFSZ46ZROLGuIiUqggTbyiF6HuzSizSUX6PLExDcak3%2BuenlOJsZ2sButja5UlUMY5UEYHUE%3D&PageNavigator1%24txtNewPageIndex="+cnt+"&PageNavigator1%24LnkBtnGoto=%E8%BD%AC%E5%88%B0%E6%AD%A4%E9%A1%B5";
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
				if(doc1==null)continue;
				Elements Lists=null;
				try {
					Lists=doc1.select("ul.xwlb_list li");
				} catch (Exception e) {
					System.out.println(e.getMessage());continue;
					// TODO: handle exception
				}
				if(Lists==null)continue;
				
				for (Element list : Lists) {
					//if(list==Lists.last())break;
					String titleurl=list.select("a").attr("abs:href");
					System.out.println("titleurl = "+ titleurl);
					String date=list.select("span.fr").text();
					System.out.println("date = "+date);
					Document doc2=null;
					for (int j = 0; j < 10; j++) {
						try {
							doc2=Jsoup
									.connect(titleurl)
									.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
									.get();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(doc2!=null)break;
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					String title=doc2.select("div.newsTitle").text();
					System.out.println("title = "+ title);
					String txt=doc2.select("div.newsContent").text();
					String doc=doc2.select("div.newsContent").html();
					System.out.println("txt = "+txt);
					//System.out.println("html = "+doc);
					Object parms[]={title,titleurl,date,txt,doc};
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
