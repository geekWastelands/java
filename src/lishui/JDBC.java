package lishui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

public class JDBC {
	public String getDownloadUrl() throws SQLException {
		
		String url = "jdbc:mysql://localhost/paper_download";//mysql建立连接格式
		String user = "root";
		String password = "root";
		String[] urls=null;
		//1.注册驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//2.建立连接
		Connection conn=DriverManager.getConnection(url,user,password);
		
		//3.创建语句
		Statement st=conn.createStatement();//
		PreparedStatement ps=null;
		
		//4.执行语句
		String sql="insert into user(username,password)values(?,?)";
		ResultSet rs=st.executeQuery("select download_url from xkb1");
		
//		ps=(PreparedStatement) conn.prepareStatement(sql);
//		ps.setString(1, "waa");
//		ps.setString(2, "111");
//		ps.execute();
		
		
		//5.处理结果
		while (rs.next()) {
			urls=rs.getObject(1).toString().split(", ");
			
			return urls[1];
		}
		//6.关闭资源
		rs.close();
		
		//ps.close();
		st.close();
		
		conn.close();
		return null;
	}
}
