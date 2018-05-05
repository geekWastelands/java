package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import lishui.JdbcUtils;
import utils.GetConnect;

public class test {

	//得到url
	public static ArrayList getUrl() throws SQLException{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn= (Connection) JdbcUtils.getConnection();
		String sql="select * from 福建";
		Statement st=conn.createStatement();
		
		ArrayList<String> arr=new ArrayList();
		try{
			
			rs=st.executeQuery(sql);
			
			while (rs.next()) {
				System.out.println(rs.getString(3));
				arr.add(rs.getString(3));
			}
		}finally{
			JdbcUtils.free(rs,ps,conn);
		}
	return arr;
	}
	
	//更新content_html
	public static void update(String content_html,String titleurl) throws SQLException {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn= (Connection) JdbcUtils.getConnection();
		String sql="update 福建 set content_html=? where titleurl=?";
		
		try{
			
			ps=(PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, content_html);
			ps.setString(2, titleurl);
			ps.execute();
		}finally{
			JdbcUtils.free(rs,ps,conn);
		}
	}
	
	public static void main(String[] args) {
		try {
			ArrayList<String> arr=getUrl();
			for (String url : arr) {
				Document doc=GetConnect.connect(url);
				if(doc==null)continue;
				String content_html = doc.select(".xl-bk").html();
				//System.out.println("con = "+content_html);
				update(content_html, url);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
