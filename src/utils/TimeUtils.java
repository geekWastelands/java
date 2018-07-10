package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/**
 * 时间工具
 * @author admin
 *
 */
public class TimeUtils {

	public static String getTime() {
		Date data=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		return simpleDateFormat.format(data);
	}
	
	public static void main(String[] args) {

		System.out.println(getTime());
	}
}
