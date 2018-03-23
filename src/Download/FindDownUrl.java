package Download;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.GetDocument;
import utils.ProxyHost;
import utils.ReadConfig;
import utils.SQLHelper;
import utils.ProxyHost.IP;
  
   /*
    * 该类完成从数据库把没有补充信息的链接取出，使用多线程将该页面下的信息补充完整。
    */
public class FindDownUrl {
	
  private static ArrayList<HashMap<String, Object>> rows;   //用来封装从数据库取到的信息
	
  private static String tableName = ReadConfig.tablename;  //读取配置文件中的表名
	
	private static synchronized HashMap<String, Object> getType() {
		if (rows == null || rows.size() <= 0) {
		    String str = "";
		        str = "select id,download from " + tableName + " where download is not null and mark<110 limit 1000";  //每次取出500个没有补充详细的链接
		    	rows = SQLHelper.selectBySQL(str);
				if (rows.size() <= 0) {
					System.out.println("==========未取到链接=======");
					try {
						Thread.sleep(10*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
				}
		}
			HashMap<String, Object> row = rows.get(0);
			rows.remove(0);
			return row;
	}
	public static void main(String[] args) {
		for (int i = 0; i < ReadConfig.thread; i++) {   
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (;;) {
                       Fetch();
                    }
                }
            }).start();
        }
		
	}
	public static Document Jsoup(String url){
		
			Document document = null;
		
			int i = 1;
			while (i <= 30) {   //请求超时多次请求
				try {
					//GetDocument.changeIp(url);
					document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0").ignoreContentType(true).timeout(3000).get();
					return document;   //请求成功跳出循环
				} catch (Exception e1) {
					i++;
				}
			}
		return null;
	}
	public static  void Fetch(){
		 HashMap<String, Object> row = getType();
		String downurl = row.get("download").toString();
		int id = Integer.parseInt(row.get("id").toString()); //将id转化为int类型
		
		try {
			Document html = Jsoup(downurl);
			/*int b1 = html.indexOf("location.href='");
			int b2 = html.indexOf("';", b1);
			String download_url = "http://a.paperopen.com/OA/" + html.substring(b1+"location.href='".length(),b2);*/
			String download_url = html.select("#content a").attr("href");
			Object[] oo = {download_url};
			
			String str = "Update "+tableName+" set download_url=?,mark=200 where id="+id;//出现异常将标记+1
			 SQLHelper.updateBySQL(str,oo);
			 System.out.println("更新成功！");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		}
	
}
