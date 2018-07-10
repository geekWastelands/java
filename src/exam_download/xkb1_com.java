package exam_download;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.GetConnect;
import utils.SQLHelper;

public class xkb1_com {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Document doc1=GetConnect.connect("https://www.xkb1.com/");
			Elements subjects = doc1.select(".main_title_282 td:nth-child(1) a");
			for (int k=12;k<subjects.size();k++) {
				Element subject=subjects.get(k);
				Document doc2 = GetConnect.connect(subject.attr("abs:href"));
				System.out.println("subject = "+subject.attr("abs:href"));//初高中学科分类
				Elements grades = doc2.select(".main_announce tr td:nth-child(1) a");
				for (Element grade : grades) {
					if(grade.text().endsWith("试卷")) {
						String grade_first_url = grade.attr("abs:href");
						Document doc3 = GetConnect.connect(grade_first_url);
						//https://www.xkb1.com/shengwu/gaozhongshengwu/gaoyishengwushiti/list_330_1.html
						//https://www.xkb1.com/shengwu/gaozhongshengwu/gaoyishengwushiti/
						/*拼接每一页的url*/
						String add = doc3.select("ul.pagelist > li:nth-child(3) > a").attr("href");
						add = add.substring(0,add.lastIndexOf('_'));
						int pagenum = Integer.parseInt(doc3.select(".pageinfo strong").first().text());
						for (int i = 1; i <= pagenum; i++) {
							String pageurl = grade_first_url+add+"_"+i+".html";
							System.out.println("pageurl = "+pageurl);
							Document doc4 = GetConnect.connect(pageurl);
							Elements lists = doc4.select("table.center_tdbgall > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(4) > td > table > tbody > tr > td a");
							for (Element list : lists) {
								String content_url = list.attr("abs:href");//试卷详情
								System.out.println("content_url = "+content_url);
								Document doc5=null;
								try {
									for (int j = 0; j < 10; j++) {
										doc5 = GetConnect.connect(content_url, pageurl);
									}
								} catch (Exception e) {
									// TODO: handle exception
									if(e.getMessage().startsWith("peer not authenticated")) {
										doc5 = GetConnect.connect(content_url, pageurl);
									}
								}
								
								String title = doc5.select("body > table:nth-child(4) > tbody > tr > td:nth-child(3) > table > tbody > tr > td > table:nth-child(3) > tbody > tr:nth-child(1) > td:nth-child(2)").text();
								System.out.println("title = "+title);
								String size = doc5.select("body > table:nth-child(4) > tbody > tr > td:nth-child(3) > table > tbody > tr > td > table:nth-child(3) > tbody > tr:nth-child(4) > td:nth-child(2)").text();
								System.out.println("size = "+size);
								String date = doc5.select("body > table:nth-child(4) > tbody > tr > td:nth-child(3) > table > tbody > tr > td > table:nth-child(3) > tbody > tr:nth-child(8) > td:nth-child(2)").text();
								System.out.println("date = "+date);
								//										body > table:nth-child(4) > tbody > tr > td:nth-child(3) > table > tbody > tr > td > table:nth-child(3) > tbody > tr:nth-child(14) > td:nth-child(2) > a
								String download_list_url = doc5.select("body > table:nth-child(4) > tbody > tr > td:nth-child(3) > table > tbody > tr > td > table:nth-child(3) > tbody > tr").last().select("td:nth-child(2) > a").attr("abs:href");
								System.out.println("download_list_url = "+download_list_url);//下载列表
								
//								Map map=new HashMap();
//								map.put("Host", "www.xkb1.com");
//								map.put("Referer",content_url );
								
								Document doc6 = GetConnect.connect(download_list_url, content_url);
								//								 //body > table:nth-child(4) > tbody > tr:nth-child(2) > td > li
								Elements download_urls = doc6.select("body > table:nth-child(4) > tbody > tr:nth-child(2) > td > li > a");
								ArrayList<String> urls=new ArrayList();
								for (Element download_url : download_urls) {
									System.out.println("download_url = "+download_url.attr("abs:href"));//下载地址
									urls.add(download_url.attr("abs:href"));
								}
								System.out.println("urls = "+urls.toString());
								Object[] parms={title,size,date,content_url,urls.toString()};
								try {
									SqlHelper.insertInfo(parms);
								} catch (Exception e) {
									System.out.println(e.getMessage());
									// TODO: handle exception
								}
								
							}
						}
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

}
