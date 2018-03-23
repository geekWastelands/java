package pdfDownload;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
public class SqlHelper {
	public static void insertInfo(Object[] obj) throws SQLException{
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			conn= (Connection) JdbcUtils.getConnection();
			String sql="insert into ea(title,titleurl,authors,date,size,download_url) values(?,?,?,?,?,?)";
			try{
				
				ps=(PreparedStatement) conn.prepareStatement(sql);
				ps.setString(1,obj[0].toString());
				ps.setString(2,obj[1].toString());
				ps.setString(3,obj[2].toString());
				ps.setString(4,obj[3].toString());
				ps.setString(5,obj[4].toString());
				ps.setString(6,obj[5].toString());
				ps.execute();
			}finally{
				JdbcUtils.free(rs,ps,conn);
			}
		}
}
