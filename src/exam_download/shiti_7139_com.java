package exam_download;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.GetConnect;

public class shiti_7139_com {//求学网

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] subject = {3025,//语文
				3026,//数学
				3027,//英语
				3029,//物理
				3030,//化学
				3033,//生物
				3028,//政治
				3034,//科学
				3032,//地理
				3035,//综合
				3031//历史
				};
		for (int i = 0; i < subject.length; i++) {
			try {
				Document doc1 = GetConnect.getDocumentByUrl("https://shiti.7139.com/"+subject[i]+"/");
				Elements grades = doc1.select("div.list_more a");
				for (Element grade : grades) {
					Document doc2 = GetConnect.getDocumentByUrl(grade.attr("abs:href"));
					Elements papers = doc2.select("div.left");
					for (Element paper : papers) {
						String paper_url = paper.select("a").attr("abs:href");
						System.out.println("paper_url = "+paper_url);
						Document doc3 = GetConnect.getDocumentByUrl(paper_url);
						String paper_title = doc3.select("div.content div.content_title div.content_right").text();
						System.out.println("paper_title = "+paper_title);
						String begin_time = doc3.select("div.content_2_right > div:nth-child(3) > div.content_right").text();
						System.out.println("投稿时间 = "+begin_time);
						String end_time = doc3.select("#content > div.content_2.clear > div.content_2_right > div:nth-child(4) > div.content_right").text();
						System.out.println("最后更新 = "+end_time);
						String size = doc3.select("#content > div.content_1 > div.content_1_left > div:nth-child(6) > div.content_right").text();
						System.out.println("大小 = "+size);
						int num_begin = 31;
						int num_end = num_begin+6;
						//https://shiti.7139.com/showdown/index.php?classid=3102&id=613029&pathid=0
						String download_index = "https://shiti.7139.com/showdown/index.php?classid="+subject[i]+"&id="+paper_url.substring(num_begin, num_end)+"&pathid=0";
						System.out.println("download_index = "+download_index);
						Document doc4 = GetConnect.getDocumentByUrl(download_index);
						String download_url = doc4.select("body > table > tbody > tr:nth-child(3) > td > a").attr("abs:href");
						System.out.println("doenload_url = "+download_url);
						
						
						
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}

}
