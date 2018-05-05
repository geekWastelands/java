package lishui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class JDBC {
	public static void main(String[] args) throws SQLException {
		
		String url = "jdbc:mysql://localhost/testdb";//mysql建立连接格式
		String user = "root";
		String password = "root";
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
		//ResultSet rs=st.executeQuery("select * from user");
		
		/*
		 * 
		 （1）Statement对象用于执行不带参数的简单的SQL语句；Statement接口提供了执行语句和获取结果的基本方法。

		（2）PerparedStatement对象用于执行带或不带IN参数的预编译SQL语句；PeraredStatement接口添加处理IN参数的方法；
		 */
		
		ps=(PreparedStatement) conn.prepareStatement(sql);
		ps.setString(1, "waa");
		ps.setString(2, "111");
		ps.execute();
		
		
		//5.处理结果
		/*while (rs.next()) {
			System.out.println(rs.getObject(1));
		}*/
		
		//6.关闭资源
		//rs.close();
		
		//ps.close();
		st.close();
		
		conn.close();
		
	}
}
