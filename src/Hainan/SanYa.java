//#secmn > div > ul > script:nth-child(1)
package Hainan;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.Reader;  
import java.net.URL;  
import java.nio.charset.Charset;  
  







import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;  
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SanYa {


	  private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	 
	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      jsonText=jsonText.substring(jsonText.indexOf("ztzpCallBack(")+13,jsonText.lastIndexOf(')'));
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	     // System.out.println("ͬʱ ������Ҳ�ܿ��� ����return�ˣ���Ȼ��ִ��finally�ģ�");
	    }
	  }
	 
	  public static void main(String[] args) throws IOException, JSONException {
	      //������ðٶȵ�ip��λapi���� ��� http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
	    int cnt=0;
	    while(cnt<=98){
	    	System.out.println("page = "+cnt);
	    	JSONObject json = readJsonFromUrl("http://61.186.46.34:81/u/search/topxxgk/wjwsite?PageSize=15&curPage="+cnt+"&title=&fwzh=&fwjg=&channelCode=&classId=&time=1512462274125&JsonpCallBack=ztzpCallBack&_=1512462260916.html");
	 	    //System.out.println(json.toString());
	 	    cnt++;
	 	    JSONArray arr=json.getJSONArray("list");
	 	    //System.out.println(arr);
	 	    for(int i=0;i<arr.length();i++){
	 	    	JSONObject obj=arr.getJSONObject(i);
	 	    	System.out.println(obj);
	 	    	for(int j=0;j<obj.length();j++){
	 	    		String type=obj.getString("channel_name");
	 	    		String date=obj.getString("published_time");
	 	    		String syh=obj.getString("syh");
	 	    		String title=obj.getString("title");
	 	    		String url=obj.getString("url");
	 	    		url="http://61.186.46.34:81"+url;
	 	    		String wh=obj.getString("wh");
	 	    		String website_name=obj.getString("website_name");
	 	    		System.out.println("type = "+type+"\ndate = "+date+"\n������ = "+syh+"\n��Ŀ = "+title+"\n����= "+url+"\n�������� = "+website_name+"\n���� = "+wh);
	 	    		Document doc=null;
	 	    		for(int t=0;t<10;t++){
	 	    			doc=Jsoup.connect(url).timeout(12000).get();
	 	    			if(doc!=null){
	 	    				//System.out.println(doc);
	 	    				break;
	 	    			}
	 	    		}
	 	    		Object parms[]={doc.toString(),title,date,syh,type,url,wh,website_name};
	 	    		try {
						SqlHelper.insertInfo(parms);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 	    	}
	 	    }
	 	    //System.out.println(((JSONObject) json.get("content")).get("address"));
	    }
	  }
	}
	
	
	



