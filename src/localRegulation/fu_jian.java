package localRegulation;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;

import utils.GetConnect;
import utils.GetDocument;

public class fu_jian {

	public static void main(String[] args) {
		HashMap<String, Integer> urls=new HashMap();
		urls.put("http://www.fujian.gov.cn/was5/web/search?channelid=229105&templet=advsch.jsp&sortfield=-docreltime&classsql=siteid%3D51*siteid%3D51*parentid%3D22046%2C22135%2C22121%2C22131%2C22117%2C22053%2C22054%2C22060*chnlid%3D22061%2C22062%2C22063%2C22064%2C22065%2C22066%2C22067%2C22068%2C22069%2C22070%2C22071%2C22072%2C22073%2C22074&prepage=5&page=",536 );
		urls.put("http://www.fujian.gov.cn/was5/web/search?channelid=229105&templet=advsch.jsp&sortfield=-docreltime&classsql=siteid%3D51*siteid%3D51*parentid%3D22046%2C22135%2C22121%2C22131%2C22117%2C22053%2C22054%2C22060*chnlid%3D22075&prepage=5&page=",22);
		
		for(Entry<String, Integer> entry:urls.entrySet()){
			for (int i = 1; i <= entry.getValue(); i++) {
				Document doc1=GetConnect.connect(entry.getKey()+i);
				//System.out.println(doc1.select("body"));
				String[] suburls=doc1.select("body").toString().split("&quot;, &quot;url&quot;:&quot");
				for (String suburl : suburls) {
					if(suburl.equals(suburls[0])||suburl.equals(suburls[suburls.length-1]))continue;
					String titleurl=suburl.substring(suburl.indexOf(";")+1, suburl.indexOf(".htm")+4);
					System.out.println(titleurl);
					Document doc2=GetConnect.connect(titleurl);
					String title=doc2.select("div h3").text();
					System.out.println("title = "+title);
					String index = doc2.select("body > div.xl_gb > div > div.gl-main.clearflx > div.xl-bk.clearflx > div.info_box > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td:nth-child(2)").text();
					System.out.println("索引号 = "+index);
					String wenhao = doc2.select("body > div.xl_gb > div > div.gl-main.clearflx > div.xl-bk.clearflx > div.info_box > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td:nth-child(4)").text();
					System.out.println("文号 = "+wenhao);
					String date = doc2.select("body > div.xl_gb > div > div.gl-main.clearflx > div.xl-bk.clearflx > div.info_box > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td:nth-child(4)").text();
					System.out.println("日期 = "+date);
					String txt=doc2.select("div.htmlcon").text();
					System.out.println("txt = "+txt);
					String html = doc2.select("div.htmlcon").html();
					Date dNow = new Date( );
				    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
					System.out.println("fetch_date = "+ft.format(dNow));
					Object[] prams={titleurl,title,index,wenhao,date,txt,html,ft.format(dNow)};
					try {
						SqlHelper.insertInfo(prams);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
	}

}
