package localRegulation;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Json {
	public class Context {
		
		public String count;
		public String chnlname;
		public String pagenum;
		public String searchtime;
		public List docs;
		public List getDocs() {
			return docs;
		}
	}
	
	public class Content{
		public String title;
		public String url;
		public String time;
		public String docsourcename;
		public String content;
		public String getTitle() {
			return title;
		}
		public String getUrl() {
			return url;
		}
		public String getTime() {
			return time;
		}
		public String getDocsourcename() {
			return docsourcename;
		}
		public String getContent() {
			return content;
		}
	}
	
	public static ArrayList JsonString(String doc) {
		Gson gson=new Gson();
		ArrayList<String[]> list = new ArrayList();
		Context p1=gson.fromJson(doc, Context.class);

	    java.lang.reflect.Type listType=new TypeToken<ArrayList<Content>>(){}.getType();//TypeToken内的泛型就是Json数据中的类型
	    ArrayList<Content>books=gson.fromJson(gson.toJson(p1.getDocs()), listType);
	    for(Content b:books){
	    	if(b.getTitle().compareTo(books.get(books.size()-1).getTitle())==0)continue;
	    	//System.out.println(b.getTitle());
	    	String[] parm= {b.getTitle(),b.getUrl(),b.getContent(),b.getDocsourcename(),b.getTime()};
	    	list.add(parm);
	    }
	    return list;
	}
}

