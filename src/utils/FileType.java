package utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

/*
 * author@fdd
 * date:2015-12-09
 * 功能：根据url和网页响应头信息，判断下载文件类型
 * */
public class FileType {
	static final String strType = "swf,wmv,,db,rm,gif,dwg,max,mpg,pdf,doc,rar,zip,pps，xls，ppt"; //文档类型集合
	static final String[] ArrayType = {"pdf","doc","rar","ppt","xls"};
	public static String getType(HttpResponse execute, String pdf_url) {
		String type = "";
		//*根据情况添加此处代码
		String str1 = pdf_url.substring(pdf_url.lastIndexOf(".") + 1);
		str1 = str1.toLowerCase();//可能存在大写
		if (strType.contains(str1)) {
			type = "." + str1;
			return type;
		}
		
		for(String str2 : ArrayType) {
			if(pdf_url.contains(str2)) {
				return "." + str2;
			}
		}
		
		
		//头信息确定文件类型
		Header[] map = execute.getAllHeaders();
		System.out.println("显示响应Header信息\n");
		try {
			for (Header entry : map) {
				if (entry.getName().contains("Content")) {
					System.out.println(entry.getName() + " : "+ entry.getValue());
					String value = entry.getValue();
					
					if (value.contains("/pdf")) {
						type = ".pdf";
						return type;
						
					} else if(value.contains("/msword")) {
						type = ".doc";
						return type;
						
					} else if(value.contains("powerpoint")) {
						type = ".ppt";
						return type;
						
					} else if(value.contains("/richtext")) {
						type = ".rtf";
						return type;
						
					} else if(value.contains("/jpeg")) {
						type = ".jpg";
						return type;
						
					} else if (value.contains("filename=")) {
						int begin = value.lastIndexOf(".");
						if(begin!=-1){
							type = value.substring(begin);
							if(type.contains("\""))
								type = type.replace("\"", "");
							return type;
						}
						
					} else {
						if (value.lastIndexOf(".") > -1) {
							String ss = value.substring(
									value.lastIndexOf(".") + 1);
							ss = ss.toLowerCase();
							if (strType.contains(ss)) {
								type = "." + ss;
								return type;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(pdf_url);
			e.printStackTrace();
		}
		
		/*考虑到如果首先根据网页的url，可能存在跳转的情况，未解决*/
		return type;
	}
	
}
