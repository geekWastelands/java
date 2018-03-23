package pdfDownload;

import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ea_pdf {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int cnt=0;
		while(cnt<470){
			String url="http://ea.donntu.edu.ua:8080/simple-search?query=&filter_field_1=dateIssued&filter_type_1=equals&filter_value_1=%5B2016+TO+2017%5D&sort_by=score&order=desc&rpp=10&etal=0&start="+cnt;
			cnt+=10;
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
			Elements lists=doc1.select(".table tr");
			for(Element list:lists){
				if(list==lists.first())continue;
				String date=list.select("td").first().text();
				String title=list.select("td").eq(1).text();
				String titleurl=list.select("td").eq(1).select("a").attr("abs:href");
				String authors=list.select("td").eq(2).text();
				System.out.println("date = "+date);
				System.out.println("title = "+title);
				System.out.println("titleurl ="+titleurl);
				System.out.println("authors = "+authors);
				Document doc2=null;
				for(int j=0;j<10;j++){
					try {
						doc2=Jsoup.connect(titleurl).get();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(doc2!=null)break;
				}
				//if(doc2.select("")!=null){
					Elements pdfs=doc2.select(".container .panel tbody tr");
					System.out.println("pdfs   size  ="+pdfs.size());
					for(Element pdf:pdfs){
						if(pdf==pdfs.first())continue;
						String size=pdf.select("td").eq(2).text();
						//if(size==" ")continue;
						String download_url=pdf.select("td").eq(0).select("a").attr("abs:href");
						System.out.println("size = "+size);
						System.out.println("download_url = "+download_url);
						Object parms[]={title,titleurl,authors,date,size,download_url};
						try {
							SqlHelper.insertInfo(parms);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				//}
			}
		}
	}

}
