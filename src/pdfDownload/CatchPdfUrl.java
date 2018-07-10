package pdfDownload;

import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//public class CatchPdfUrl {
//
//	public static void main(String[] args) throws SQLException {
//		// TODO Auto-generated method stub
//		int cnt=1;
//		while(cnt<=238){
//			String url="https://www.springeropen.com/search?searchType=publisherSearch&sort=Relevance&query=Published+2017&page="+cnt;
//		    System.out.println("当前页面："+cnt);
//			cnt++;
//		    Document doc=null;
//			for(int j=0;j<10;j++){
//		    	try {
//					doc=Jsoup.connect(url).get();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		    	if(doc!=null)break;
//		    }
//			Elements eles=doc.select(".ResultsList_item");
//			int flag=0;
//			for(Element ele:eles){
//				String title=ele.select("h3").text();
//				String date=ele.select(".ResultsList_published").text();
//				date=date.substring(date.indexOf(':')+1);
//				String download_url=ele.select("li").last().select("a").attr("abs:href");
//				System.out.println("title="+title+"\ndate="+date+"\ndownload_url="+download_url);
//				Object[] parms={title,date,download_url};
//				SqlHelper.insertInfo(parms);
//			}
//		}
//
//		cnt=1;
//		while(cnt<=284){
//			String url="https://www.springeropen.com/search?searchType=publisherSearch&sort=Relevance&query=Published+2016&page="+cnt;
//		    System.out.println("当前页面："+cnt);
//			cnt++;
//		    Document doc=null;
//			for(int j=0;j<10;j++){
//		    	try {
//					doc=Jsoup.connect(url).get();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		    	if(doc!=null)break;
//		    }
//			Elements eles=doc.select(".ResultsList_item");
//			int flag=0;
//			for(Element ele:eles){
//				String title=ele.select("h3").text();
//				String date=ele.select(".ResultsList_published").text();
//				date=date.substring(date.indexOf(':')+1);
//				String download_url=ele.select("li").last().select("a").attr("abs:href");
//				System.out.println("title="+title+"\ndate="+date+"\ndownload_url="+download_url);
//				Object[] parms={title,date,download_url};
//				SqlHelper.insertInfo(parms);
//			}
//		}
//	
//	}
//
//}


/*public class CatchPdfUrl {
	public static void main(String[] args) throws SQLException {
	int cnt=0;
	//2016年
	while(cnt<=8720){
		String url="http://orbi.ulg.ac.be/browse?type=datepublished&sort_by=1&order=DESC&rpp=20&etal=3&value=2016&offset="+cnt;
		cnt+=20;
		Document doc1=null;
		for(int j=0;j<10;j++){
			try {
				doc1=Jsoup.connect(url).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Elements titleconent=doc1.select(".info");
		for(Element ele:titleconent){
			String title=ele.select(".title").text();
			String titleurl=ele.select(".title").attr("abs:href");
			String author=ele.select(".author").text();
			System.out.println("title = "+title+"\nauthor = "+author+"\ntitleurl = "+titleurl);
			Document doc2=null;
			for(int j=0;j<10;j++){
				try {
					doc2=Jsoup.connect(titleurl).get();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Elements dates=doc2.select(".misc").eq(1).select("tr");
			String date = null;
			for(Element ele0:dates){
				if(ele0.select("td").eq(0).text().startsWith("Publication")){
					date=ele0.select("td").eq(1).text();
					break;
				}
			}
			System.out.println("date = "+date);
			Elements download_urls=null;
			if(doc2.select(".inner").eq(1).hasText()){
				download_urls=doc2.select(".inner tr");
				for(Element ele1:download_urls){
					
					if(ele1.select("td").eq(0).select("a").text().endsWith("Open access")){
						String download_url=ele1.select("td").eq(5).select("a").attr("abs:href");
						System.out.println("download_url = "+download_url);
						String size=ele1.select("td").eq(4).text();
						System.out.println("size = "+size);
						Object[] prams={title,titleurl,author,date,download_url,size};
					}
				}
			}
		}
	}
	//2017年
	cnt=0;
	while(cnt<=5400){
		String url="http://orbi.ulg.ac.be/browse?type=datepublished&sort_by=1&order=DESC&rpp=20&etal=3&value=2017&offset="+cnt;
		cnt+=20;
		Document doc1=null;
		for(int j=0;j<10;j++){
			try {
				doc1=Jsoup.connect(url).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Elements titleconent=doc1.select(".info");
		for(Element ele:titleconent){
			String title=ele.select(".title").text();
			String titleurl=ele.select(".title").attr("abs:href");
			String author=ele.select(".author").text();
			System.out.println("title = "+title+"\nauthor = "+author+"\ntitleurl = "+titleurl);
			Document doc2=null;
			for(int j=0;j<10;j++){
				try {
					doc2=Jsoup.connect(titleurl).get();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Elements dates=doc2.select(".misc").eq(1).select("tr");
			String date = null;
			for(Element ele0:dates){
				if(ele0.select("td").eq(0).text().startsWith("Publication")){
					date=ele0.select("td").eq(1).text();
					break;
				}
			}
			System.out.println("date = "+date);
			Elements download_urls=null;
			if(doc2.select(".inner").eq(1).hasText()){
				download_urls=doc2.select(".inner tr");
				for(Element ele1:download_urls){
					
					if(ele1.select("td").eq(0).select("a").text().endsWith("Open access")){
						String download_url=ele1.select("td").eq(5).select("a").attr("abs:href");
						System.out.println("download_url = "+download_url);
						String size=ele1.select("td").eq(4).text();
						System.out.println("size = "+size);
						Object[] prams={title,titleurl,author,date,download_url,size};
					}
				}
			}
		}
	}

}
}*/


public class CatchPdfUrl {
	public static void main(String[] args) throws SQLException {
		//http://aisel.aisnet.org/do/search/advanced/?q=&start=0&start_date=01%2F01%2F2016&end_date=11%2F21%2F2017&peer_reviewed=true&context=509156&sort=score&facet=#query-results
		//http://aisel.aisnet.org/do/search/advanced/?q=&start=25&start_date=01%2F01%2F2016&end_date=11%2F21%2F2017&peer_reviewed=true&context=509156&sort=score&facet=#query-results
		//http://aisel.aisnet.org/do/search/advanced/?q=&start=569&start_date=01%2F01%2F2016&end_date=11%2F21%2F2017&peer_reviewed=true&context=509156&sort=score&facet=#query-results
	int cnt=0;
	while(cnt<=569){
		String url="http://aisel.aisnet.org/do/search/advanced/?q=&start="+cnt+"&start_date=01%2F01%2F2016&end_date=11%2F21%2F2017&peer_reviewed=true&context=509156&sort=score&facet=#query-results";
		System.out.println("当前页面："+cnt/25);
		cnt+=25;
		Document doc1=null;
		for(int i=0;i<10;i++){
			try {
				doc1=Jsoup.connect(url).timeout(20000).get();
				if(doc1!=null)break;	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		}
		//System.out.println(doc1);
		Elements lists=doc1.select("div .result");
		
		for(Element list:lists){
			System.out.println(list);
			String title=list.select(".title").text();
			String titleurl=list.select(".title a").attr("abs:href");
			String date=list.select(".year strong").text();
			String author=list.select(".author strong").text();
			String download_url=list.select(".download a").attr("abs:href");
			System.out.println("title = "+title+"\ntitleurl = "+titleurl+"\ndate = "
			+date+"\nauthor = "+author+"\ndownload_url = "+download_url);
		}
	}
	}
}



