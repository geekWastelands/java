package TianJin;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
public class SqlHelper {
	public static void insertInfo(Object[] obj) throws SQLException{
			/*
			 * 提供一个参考链接  https://www.cnblogs.com/centor/p/6142775.html
			 * 原理就是用jdbc  大家都接触过  不明白的google一下都有
			 * JdbcUtils就是把创建连接池的方法封装了一下
			 */
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			conn= (Connection) JdbcUtils.getConnection();//创建连接
			//插入数据库
			String sql="insert into tianjin(title,titleurl,date,txt,doc,source) values(?,?,?,?,?,?)";
			
			try{
				ps=(PreparedStatement) conn.prepareStatement(sql);
				for(int t=0;t<obj.length;t++){
					ps.setString(t+1, obj[t].toString());
				}
				ps.execute();
			}finally{
				JdbcUtils.free(rs,ps,conn);
			}
		}
}

