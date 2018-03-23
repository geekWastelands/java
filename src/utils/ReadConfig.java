/**  
 * @author yokoboy
 * @date 2013-9-25
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author yokoboy
 * @date 2013-9-25
 */
public class ReadConfig {

	public static String filedir;
	public static int thread;
	public static boolean isDebug;
	public static boolean isProxy;
	public static boolean isuseGoAgent;
	public static String proxyIp;
	public static int proxyPort;
	public static String cookie;
	public static String tablename;
	public static String orderBy;
	public static String downloadType;
	public static int mark1;  //截止mark
	public static int mark2;  //完成mark
	public static String tablename_an;
	public static boolean substr_flag;

	static {
		Properties properties = new Properties();
		File file = new File("./config.properties");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("读取配置文件失败！");
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		filedir = properties.getProperty("filedir");
		thread = Integer.parseInt(properties.getProperty("thread"));
		isDebug = properties.get("debug").equals("true") ? true : false;
		isProxy = properties.get("isProxy").equals("true") ? true : false;
		isuseGoAgent = properties.get("isuseGoAgent").equals("true") ? true : false;
		proxyIp = properties.get("proxyIp").toString();
		proxyPort = Integer.parseInt(properties.get("proxyPort").toString());
		cookie = properties.get("cookie").toString();
		tablename = properties.get("tablename").toString();
		tablename_an = properties.get("tablename_an").toString();
		orderBy = properties.get("orderBy").toString();
		downloadType = properties.get("downloadType").toString();
		mark1 = Integer.parseInt(properties.get("mark1").toString());
		mark2 = Integer.parseInt(properties.get("mark2").toString());
		substr_flag = properties.get("substr_flag").equals("true") ? true : false;
	}

	public static void showConfig() {
		System.out.println("============================================");
		System.out.println("文件地址                 : " + filedir);
		System.out.println("线程数                     ：                "+ thread);
		System.out.println("debug      : " + isDebug);
		System.out.println("isProxy    : " + isProxy);
		System.out.println("isuseGoAgent: " + isuseGoAgent);
		System.out.println("IP         : " + proxyIp);
		System.out.println("Port       : " + proxyPort);
		System.out.println("cookie     : " + cookie);
		System.out.println("tableName  : " + tablename);
		System.out.println("============================================");
	}

	public static void main(String[] args) {
		showConfig();
	}

}
