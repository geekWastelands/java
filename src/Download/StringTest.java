package Download;

public class StringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String str = "http://www.ajnr.org/content/7/6/1013.full.pdf+html?sid=1aff59ed-3086-48e7-bdf4-8ed9923c9c56";
//		int idex = 0;
//		idex = str.indexOf("+html?");
//		if(idex > 0)
//			str = str.substring(0, idex);
//		System.out.println(str);
		String s1 = "a";
		String s2 = "abb";
		
		System.out.println(s1 == s2);
		System.out.println(s1.compareTo(s2));
		System.out.println(s1.equals(s2));
		
	}
	
	static String formatDownUrl(String download_url) {
		int idex = 0;
		idex = download_url.indexOf("+html?");
		if(idex > 0)
			download_url = download_url.substring(0, idex);
		return download_url;
	}

}
