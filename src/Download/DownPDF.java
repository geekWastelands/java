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
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import utils.ProxyHost;
import utils.ProxyHost.IP;
import utils.ReadConfig;
import utils.SQLHelper;
import utils.UserAgent;


public class DownPDF {
	private static String createPath = "";
	private static String tableName = ReadConfig.tablename;
	private static String fileDirHead = ReadConfig.filedir.endsWith("\\") ? ReadConfig.filedir : ReadConfig.filedir + "\\";
	private static ArrayList<HashMap<String, Object>> rows;

	/**
	 * 
	 *
	 * @return
	 */
	private synchronized static String manageFilePath() {
		if (createPath == null || "".endsWith(createPath)) {
			createPath = fileDirHead + UUID.randomUUID().toString().replace("-", "") + "\\";
			new File(createPath).mkdirs();  // 创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
		}
		File file = new File(createPath);//根据路径判断该文件夹下的文件是否超过2000
		int length = file.list().length;
		if (length >= 2000) {
			createPath = fileDirHead + UUID.randomUUID().toString().replace("-", "") + "\\";
			new File(createPath).mkdirs();
		}
		return createPath;//返回值是一个文件夹的路径
	}
	
	
	private static void downLoad(String pdf_url,int id) {
		String type = null;
		if(ReadConfig.substr_flag) //如果配置文件为true 说明要进行字符串截取根据下载链接下载相应类型的文档
		{
			type = pdf_url.substring(pdf_url.lastIndexOf("."));
		}
		else
			type = ReadConfig.downloadType;
		
		System.out.println(pdf_url);
		String filedir = manageFilePath() + UUID.randomUUID().toString().replace("-", "") + type;
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		IP ip = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			if (ReadConfig.isProxy) { //是否更换请求IP和端口号
				HttpHost proxy = null;
				if (ReadConfig.isuseGoAgent) {
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
			HttpGet httpGet = new HttpGet(pdf_url.trim());
			httpGet.setHeader("Cookie", ReadConfig.cookie); 
			httpGet.setHeader("User-Agent", UserAgent.getUserAgent());
			HttpResponse execute = httpClient.execute(httpGet);
			System.err.println(execute.getStatusLine());
			if (execute.getStatusLine().getStatusCode() != 200) {
				String sqlString = "update " + tableName + " set mark=mark+1 where id=" + id ; //自己增加，网页返回值不是200放弃该链接
//				String sqlString = "update " + tableName + " set mark=205 where id=" + id ; //自己增加，网页返回值不是200放弃该链接
				SQLHelper.updateBySQL(sqlString);
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
			System.out.println(pdf_url);
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
				System.out.println(sqlString);
			} catch (Exception e) {
				e.printStackTrace();
				new File(filedir).delete();
			}
		}System.out.println("完成！");
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
			String sqlString = "select download_url,id from " + tableName + " where download_url like 'http%' and mark<"+ReadConfig.mark1+" "+ReadConfig.orderBy+" limit 1000";
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
			System.out.println("id =  "+id);
//			if(tableRow.get("download_url") != null) {
//				if(!tableRow.get("download_url").toString().isEmpty())//判断取到的链接是否为空
			
			/* 上面这两层判断可以去掉，因为上面的sql语句已经限制了取出链接的条件下载链接一定不为空 */
			downLoad(tableRow.get("download_url").toString(),id);
			
//			}
		}
	}	

	public static void main(String[] asd) throws IOException {

		// ReadConfig.isProxy = false; //是否更换IP
		// ReadConfig.isuseGoAgent = false; //是否使用本地配置IP地址，false表示不适用配置文件的IP，而使用从数据库读取的IP
		// ReadConfig.isDebug = false; /* 是否开启调试 ，如果值为假调试期间出现的错误不会对数据库造成影响 */
		// ReadConfig.thread = 5;
		ReadConfig.showConfig();
		for (int i = 0; i < ReadConfig.thread; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}

