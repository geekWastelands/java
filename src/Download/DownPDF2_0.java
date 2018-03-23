package Download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import com.mysql.jdbc.log.Log;

import utils.FileType;
import utils.ProxyHost;
import utils.ProxyHost.IP;
import utils.ReadConfig;
import utils.SQLHelper;
import utils.UserAgent;
/*
 * 程序更新日期：2015.12.7
 * 更新人员：fdd
 * */
public class DownPDF2_0 {
	private static String createPath = "";
	private static String tableName = ReadConfig.tablename;
	private static String fileDirHead = ReadConfig.filedir.endsWith("\\") ? ReadConfig.filedir : ReadConfig.filedir + "\\";
	private static ArrayList<HashMap<String, Object>> rows;
	private static ArrayList fileMax = new ArrayList();//创建新的文件夹下最多的文件个数集合

	/**
	 * @r
	 */
	private synchronized static String manageFilePath() {
		if (createPath == null || "".endsWith(createPath)) {
			createPath = CreatFile();
		}
		File file = new File(createPath);//根据路径判断该文件夹下的文件是否超过2000
		int length = file.list().length;
		if (length >= 2000) {
			createPath = CreatFile();
		}
		return createPath;//返回值是一个文件夹的路径
	}
	
	/*
	 * 此段代码是对download_url的规范处理，如果已经符合要求不必使用此代码
	 * */
	static String formatDownUrl(String download_url) {
		int idex = 0;
		idex = download_url.indexOf("+html?");
		if(idex > 0)
			download_url = download_url.substring(0, idex);
		return download_url;
	}
	
	private static void downLoad(String pdf_url,int id) {
		//此处因为网站原因对download_url做一些处理，在其他网站可以忽略
//		pdf_url = formatDownUrl(pdf_url);
//		pdf_url = "http://" + pdf_url;
		System.out.println("id=" + id + " --- is downloading... # " + pdf_url);
		String filedir = manageFilePath()
				+ UUID.randomUUID().toString().replace("-", "");
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		IP ip = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			if (ReadConfig.isProxy) { //是否更换请求IP和端口号 
				HttpHost proxy = null;
				if (ReadConfig.isuseGoAgent) { //isuseGoAgent 是false的时候更换IP
					proxy = new HttpHost(ReadConfig.proxyIp, ReadConfig.proxyPort);
				} else {
					ip = ProxyHost.getIp(true);
					proxy = new HttpHost(ip.ip, ip.port);
				}
				httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60 * 1000);// 鏉╃偞甯撮弮鍫曟？1閸掑棝鎸?
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60 * 1000);// 閺佺増宓佹导鐘虹翻閺冨爼妫?鐏忓繑妞?
			httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
			
			HttpGet httpGet = new HttpGet(pdf_url.trim());//该行代码是请求下载链接
			
			httpGet.setHeader("Cookie", ReadConfig.cookie); 
			httpGet.setHeader("User-Agent", UserAgent.getUserAgent());
//			httpGet.setHeader("Cookie", "AMCV_4D6368F454EC41940A4C98A6%40AdobeOrg=793872103%7CMCIDTS%7C16786%7CMCMID%7C15799978652579198605521579591839810613%7CMCAID%7CNONE%7CMCAAMLH-1450845275%7C11%7CMCAAMB-1450845275%7Chmk_Lq6TPIBMW925SPhw3Q; s_pers=%20v8%3D1450241585231%7C1544849585231%3B%20v8_s%3DFirst%2520Visit%7C1450243385231%3B%20c19%3Djb%253Afulltext%253Apdf%7C1450243385239%3B%20v68%3D1450241563609%7C1450243385260%3B; _ga=GA1.3.673169456.1450240476; optimizelySegments=%7B%22536630985%22%3A%22false%22%2C%22537950427%22%3A%22direct%22%2C%22536570476%22%3A%22none%22%2C%22537380789%22%3A%22ff%22%7D; optimizelyEndUserId=oeu1450240477529r0.9389310884598037; __atuvc=1%7C50; SERVER=WZ6myaEXBLGZpQFlLdJmhw==; JSESSIONID=aaa9pWz5kbxfif7V_WOgv; MAID=kxR9Ukd4rKLO5191f+/d3A==");
//			httpGet.setHeader("Referer", "http://www.osti.gov/scitech/biblio/" + idNum); //设置请求头信息，在必要的时候设置,页面跳转情况
//			httpGet.setHeader("Host", "ajp.amjpathol.org");
			
			HttpResponse execute = httpClient.execute(httpGet); //客户端HttpClient执行HttpGet请求，返回执行结果
			
			String type = FileType.getType(execute, pdf_url);//获得下载文件类型
			System.out.println("#文件类型# " + type);
			
			if (type == null || "".endsWith(type)) {
				System.out.println(pdf_url + "  该文档无法下载");
				String sqlString = "update " + tableName
						+ " set mark=404 where id=" + id;
				try {
					SQLHelper.updateBySQL(sqlString);
					System.err.println("链接类型无法确定，标记 mark#404"); 
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				return;
			}
			filedir += type;
			
			System.err.println("下载结果：" + execute.getStatusLine());   //打印错误具体信息,查看是否下载成功
			if (execute.getStatusLine().getStatusCode() != 200) {//返回值不是200表示请求失败
				System.out.println("返回值错误!!!");
				String sqlString = "update " + tableName + " set mark=mark+1 where id=" + id ; //自己增加，网页返回值不是200放弃该链接
				SQLHelper.updateBySQL(sqlString);
				System.out.println("mark值更新...\n");
				if (ip != null) {
					int code = execute.getStatusLine().getStatusCode();
					System.out.println(ip.ip + ":" + ip.port + "---" + code + "--");
					ProxyHost.removeIp(ip);
				} 
				return;
			}
			HttpEntity entity = execute.getEntity();
			InputStream inputStream = entity.getContent();
			bufferedInputStream = new BufferedInputStream(inputStream);
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(filedir)));
			byte[] buf = new byte[1024];
			for (int len = 0; (len = bufferedInputStream.read(buf)) != -1;) {
				bufferedOutputStream.write(buf, 0, len);
				bufferedOutputStream.flush();
			}
		}
		
		catch (Exception e) {
			System.out.println(id + "..." + pdf_url + " 请求错误");
			if (!ReadConfig.isDebug) { //判断是否为调试模式
				String sqlString = "update " + tableName + " set mark=mark+1 where id=" + id ;
				try {
					SQLHelper.updateBySQL(sqlString);
					System.out.println(sqlString);
					
				} catch (Exception ee) {
					ee.printStackTrace();
					new File(filedir).delete();
				}
			}
			System.err.println("当前使用的IP为： " + ip);
			if (ip != null) {
				ProxyHost.removeIp(ip);
			}
			File file = new File(filedir);
			if (file.exists()) {
				file.delete();
			}
			e.printStackTrace();
			return;
		} finally {
			try {
				if (bufferedInputStream != null) {
					bufferedInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (bufferedOutputStream != null) {
					bufferedOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File file = new File(filedir);
		if (file.length() < 1024 * 5) {
			file.delete();
			System.err.println("文件大小为：" + filedir.length() + "b，小于5b被删除。");
			if (!ReadConfig.isDebug) {
				String sqlString = "update " + tableName + " set mark=mark+1 where id=" + id ;
				try {
					SQLHelper.updateBySQL(sqlString);
					System.out.println(sqlString);
				} catch (Exception ee) {
					ee.printStackTrace();
					new File(filedir).delete();
				}
			}
			return;
		}
		
		if (!ReadConfig.isDebug) {
			String sqlString = "update " + tableName + " set path='" + filedir.replace(fileDirHead, "").replace("\\", "/") + "',mark="+ReadConfig.mark2+" where id=" + id ;
			try {
				SQLHelper.updateBySQL(sqlString);
			} catch (Exception e) {
				System.out.println(id + " 数据库表下载路径更新失败！！！");
				e.printStackTrace();
				new File(filedir).delete();
			}
		}
		System.out.println("id=" + id + " Success！" + "    size:" + file.length() / 1024 + "K \n");
	}

	/**
	 *
	 * 从数据库取链接，保证取到的链接是存在值的，mark值在数据库信息补充完整时候为200，配置文件mark1设置为205
	 * 也就是说链接5次打不开就放弃，orderBy意思是取的时候按什么顺序，by id 就是顺序；by desc 倒序
	 * 注意：sql语句限制了从数据库取出的链接不为空
	 * @return
	 */
	private synchronized static HashMap<String, Object> getTableRow() {
		if (rows == null || rows.size() <= 0) {
			String sqlString = "select download_url,id from " + tableName 
					+ " where download_url like 'http%' and mark<" 
					+ ReadConfig.mark1 + " " + ReadConfig.orderBy + " limit 1000";
			rows = SQLHelper.selectBySQL(sqlString);
			if (rows.size() <= 0) {
				System.out.println("==========未取到链接=======");
				System.out.println("Sir " + tableName + "下载完成!");
				System.exit(0);
			}
		}
		HashMap<String, Object> row = rows.get(0);
		rows.remove(0);
		return row;
	}

	/*
	 * sring.isEmpty該函數的返回值如果字符串是空的话就为真，如果字符串不为空的话返回值是false
	 * 对于String str = null;这样不能通过该函数来判断是否为空，只有通过toString()函数专函后才能使用
	 * 因为对于null表示的是字符串内什么内容也没有，没有存储任何内容，""表示字符串指针指向不确定
	 * tableRow.get("download_url")相当于从数据库download_url这一列中把信息取出来，原封不动的得到
	 * */
	private static void start() {
		for (;;) {
			HashMap<String, Object> tableRow = getTableRow();
			int id = Integer.parseInt(tableRow.get("id").toString());
			downLoad(tableRow.get("download_url").toString(),id);
		}
	}	
	
	public static void ReadDiskfile() {//读取下载路径下不足2000个的文件夹名称
		File file = new File(fileDirHead);
		if(!file.exists()) {
			return;
		}
		File[] files = file.listFiles();
		  for(File a : files) {
			  if(a.isDirectory()) {
				  String filepath = a.getAbsolutePath();
				  File b = new File(filepath);
				  int lenth = b.list().length;
				  if(lenth < 2000) {
					  fileMax.add(filepath + "\\");
				  }
			  }
		  }
	}
	
	private synchronized static String CreatFile() { 
		String str = "";
		if(fileMax.size() <= 0) {
			String newfilepath  = fileDirHead + UUID.randomUUID().toString().replace("-", "") + "\\";
			new File(newfilepath).mkdirs();  // 创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
			fileMax.add(newfilepath);
		}
		if(!fileMax.isEmpty()) {
			str = fileMax.get(0).toString();
			fileMax.remove(0);
		}
		return str;
	}

	public static void main(String[] asd) throws IOException {

		// ReadConfig.isProxy = false; //是否更换IP
		// ReadConfig.isuseGoAgent = false; //是否使用本地配置IP地址，false表示不使用配置文件的IP，而使用从数据库读取的IP
		// ReadConfig.isDebug = false; /* 是否开启调试 ，如果值为假调试期间出现的错误不会对数据库造成影响 */
		// ReadConfig.thread = 5;
		ReadConfig.showConfig(); //读取配置文件
		ReadDiskfile();//读取本地存储文件夹
		for (int i = 0; i < ReadConfig.thread; i++) {
			new Thread(new Runnable() { //Runnable接口
				@Override
				public void run() {//覆写Runnable里面的run（）方法，该方法就是程序想要执行的代码块
					try {
						start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();//Thread对象开启并启动线程
		}
	}
}



