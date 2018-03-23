package ZheJiang;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import utils.GetConnect;

public class ShaoXing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url="http://www.sxws.gov.cn/module/jslib/jquery/jpage/dataproxy.jsp?startrecord=2611&endrecord=2678&perpage=30&appid=1&webid=34&path=%2F&columnid=9863&sourceContentType=1&unitid=15357&webname=%E7%BB%8D%E5%85%B4%E5%B8%82%E5%8D%AB%E7%94%9F%E5%92%8C%E8%AE%A1%E5%88%92%E7%94%9F%E8%82%B2%E5%A7%94%E5%91%98%E4%BC%9A&permissiontype=0";
		try{
		Document doc = GetConnect.getDocumentByUrl(url);
		System.out.println(doc);
		}catch(Exception e){
			
		}
		//Document doc = null;
		//��ȡ��������
		Connection con = Jsoup.connect(url);
		//��Ӳ���
      con.header("Origin", "http://www.sxws.gov.cn");
      con.header("Host", "www.sxws.gov.cn");
      con.header("Referer", "http://www.sxws.gov.cn/col/col9863/index.html");
      con.data("aaa","ccc");
      con.header("Content-Type", "text/javascript, application/javascript, */*");//text/javascript, application/javascript, */*
      con.header("Accept", "text/plain, */*; q=0.01");
      con.header("X-Requested-With", "XMLHttpRequest");
      con.header("Accept-Encoding", "gzip, deflate");
      con.header("Accept-Language", "zh-CN,zh;q=0.9");
		//����cookie��ͷ�ļ���ʽ��
		con.header("Cookie", "JSESSIONID=8689A94988C070D93CCC50724BE3313C; gs_b_tas=BOTQRH5QT48IDHBO; counter=3");
		con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36");
        /*try {
			doc = con.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*doc = Jsoup.connect(url)
				.header("Origin", "http://www.sxws.gov.cn")
				.post();*/
		
		//System.out.println(doc);
	}//http://www.sxws.gov.cn/module/jslib/jquery/jpage/dataproxy.jsp?startrecord=2611&endrecord=2678&perpage=30&appid=1&webid=34&path=%2F&columnid=9863&sourceContentType=1&unitid=15357&webname=%E7%BB%8D%E5%85%B4%E5%B8%82%E5%8D%AB%E7%94%9F%E5%92%8C%E8%AE%A1%E5%88%92%E7%94%9F%E8%82%B2%E5%A7%94%E5%91%98%E4%BC%9A&permissiontype=0
	
}/*
appid: 1
webid: 34
path: /
columnid: 9863
sourceContentType: 1
unitid: 15357
webname: 绍兴市卫生和计划生育委员会
permissiontype: 0

appid: 1
webid: 34
path: /
columnid: 9870
sourceContentType: 1
unitid: 15357
webname: 绍兴市卫生和计划生育委员会
permissiontype: 0*/