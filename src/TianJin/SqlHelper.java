package TianJin;


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
			String sql="insert into tianjin(title,titleurl,date,txt,doc,source) values(?,?,?,?,?,?)";
			//String sql="insert into yulin(title,titleurl,date,doc,txt,source) values(?,?,?,?,?,?,?,?,?,?,?)";

			try{
				
				ps=(PreparedStatement) conn.prepareStatement(sql);
				for(int t=0;t<obj.length;t++){
					ps.setString(t+1, obj[t].toString());
				}
/*				ps.setString(1,obj[0].toString());
				ps.setString(2,obj[1].toString());
				ps.setString(3,obj[2].toString());
				ps.setString(4,obj[3].toString());
				ps.setString(5,obj[4].toString());
				ps.setString(6,obj[5].toString());
				ps.setString(7,obj[6].toString());
				ps.setString(8,obj[7].toString());
				ps.setString(9,obj[8].toString());*/
				ps.execute();
			}finally{
				JdbcUtils.free(rs,ps,conn);
			}
		}
}

