package GuangXi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GuiLin {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HashMap<String, Integer>num=new HashMap<String, Integer>();
		num.put("http://www.glwsjs.gov.cn/rsf/site/glwjw/benweigongzuodongtai/index", 40);
		num.put("http://www.glwsjs.gov.cn/rsf/site/glwjw/zizhiqugongzuodongtai/index", 4);
		num.put("http://www.glwsjs.gov.cn/rsf/site/glwjw/zhishu1/index", 40);
		num.put("http://www.glwsjs.gov.cn/rsf/site/glwjw/xianqu/index", 40);
		num.put("http://www.glwsjs.gov.cn/rsf/site/glwjw/gongshigonggao/index", 34);
		for(Entry<String, Integer> entry : num.entrySet()){
			int cnt=0;
			System.out.println("��ǰҳ�� = "+cnt);
			//System.out.println("��ַ = "+url);
			String url=null;
			while (cnt<entry.getValue()) {
				if (cnt==0) {
					url=entry.getKey()+".html";
				} else {
					url=entry.getKey()+"_"+cnt+".html";
				}
				System.out.println("��ַ = "+url);
				cnt++;
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
				Elements lists=doc1.select(".list01 li");
				for (Element list : lists) {
					String titleurl=list.select("a").attr("abs:href");
					String title=list.select("a").text();
					String date=list.select("span").text();
					if(date.substring(0, 4).compareTo("2015")<=0)continue;
					System.out.println("title = "+title);
					System.out.println("titleurl = "+titleurl);
					System.out.println("date = "+date);
					Document doc2=null;
					for (int j = 0; j < 10; j++) {
						try {
							doc2=Jsoup.connect(titleurl).get();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							String TXT="D:\\GuangXi\\guilin.txt";
							File file=new File(TXT);
							if(!file.exists()){
								file.mkdir();
							}
							RandomAccessFile random=new RandomAccessFile(TXT, "rw");
							long fileLength = random.length();  
				            // ��д�ļ�ָ���Ƶ��ļ�β��  
				            random.seek(fileLength);  
				            random.writeUTF(titleurl+'\t'+e.getMessage()+'\n');  
				            random.close();  
							e.printStackTrace();
							
						}
						if (doc2!=null) {
							break;
						}
					}
					String doc=doc2.select(".article").html();
					String txt=doc2.select(".article").text();
					String source=doc2.select(".article .page_date span").eq(0).text();
					source=source.substring(source.indexOf('：')+1);
					String author=doc2.select(".article .page_date span").eq(1).text();
					author=author.substring(author.indexOf('：')+1);
					//System.out.println("doc = "+doc);
					//System.out.println("txt = "+txt);
					System.out.println("source = "+source);
					System.out.println("author = "+author);
					Object parms[]={title,titleurl,date,doc,txt,author,source};
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
