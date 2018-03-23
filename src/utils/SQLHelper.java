/**  
 * @author yokoboy
 * @date 2013-9-9
 */
package utils;

import java.sql.*;
import java.util.*;


/**
 * "`id`  int NOT NULL AUTO_INCREMENT ,"
+"`pageurl`  varchar(255) NULL ,"
+"`title`  varchar(255) NULL ,"
+"`qishu`  varchar(255) NULL ,"
+"`referencetext`  varchar(255) NULL ,"
+"`filetitle`  varchar(200) NULL ,"
+"`entitle`  varchar(255) NULL ,"
+"`sendtime`  varchar(100) NULL ,"
+"`lastmodifytime`  varchar(100) NULL ,"
+"`doi`  varchar(100) NULL ,"
+"`keyword_`  varchar(255) NULL ,"
+"`enkeyword`  varchar(255) NULL ,"
+"`fundprojectLabel`  varchar(255) NULL ,"
+"`clicknum`  varchar(20) NULL ,"
+"`author`  longtext NULL ,"
+"`author_depart`  longtext NULL ,"
+"`depart`  longtext NULL ,"
+"`pdfclicknum`  varchar(20) NULL ,"
+"`abstract_`  longtext NULL ,"
+"`enabstract`  longtext NULL ,"
+"`download_url`  varchar(100) NULL ,"
+"`mark`  int(5) NULL DEFAULT 0 ,"
+"PRIMARY KEY (`id`),"
+"UNIQUE INDEX (`pageurl`) "
+")";
 * @author yokoboy
 * @date 2013-9-9
 * 对数据库的增、删、查、改
 */

public class SQLHelper {

	public static int insertBySQL(String sqlString) {
		return updateBySQL(sqlString);
	}

	public static int insertBySQL(String sqlString, Object[] parms ) {
		return updateBySQL(sqlString, parms);
	}
	public static int insertBySQL(String sqlString, Object[] parms, int[] types) {
		return updateBySQL(sqlString, parms, types);
	}

	public static int insertBySQL(String sqlString, CompletePrepareStatementI cps) {
		return updateBySQL(sqlString, cps);
	}

	public static ArrayList<HashMap<String, Object>> selectBySQL(String sqlString) {
		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		Connection conn = DbUtil.getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			resultSet = prepareStatement.executeQuery();
			metaData = resultSet.getMetaData();    //获取此 ResultSet 对象的列的编号、类型和属性。 
			int columnCount = metaData.getColumnCount();
			for (; resultSet.next();) {
				HashMap<String, Object> row = new HashMap<String, Object>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = metaData.getColumnName(i + 1);
					row.put(columnName, resultSet.getObject(i + 1));
				}
				rows.add(row);
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				//System.out.println(rows);
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil.closeSource(resultSet, prepareStatement, conn);
		}
		return rows;
	}
   
	public static int selectColumnCount(String sqlString) {
		Connection conn = DbUtil.getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int columnCount = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			resultSet = prepareStatement.executeQuery();
			for (; resultSet.next();) {
				columnCount++;
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil.closeSource(resultSet, prepareStatement, conn);
		}
		return columnCount;
	}
	
	public static ArrayList<HashMap<String, Object>> selectBySQL(String sqlString, Object[] parms, int[] types) {
		if (parms == null || types == null || parms.length != types.length) {
			throw new RuntimeException("我也不知道这是什么异常！");
		}
		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		Connection conn = DbUtil.getConnection();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			for (int len = parms.length, i = 0; i < len; i++) {
				prepareStatement.setObject(i + 1, parms[i], types[i]);
			}
			resultSet = prepareStatement.executeQuery();
			metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (; resultSet.next();) {
				HashMap<String, Object> row = new HashMap<String, Object>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = metaData.getColumnName(i + 1);
					row.put(columnName, resultSet.getObject(i + 1));
				}
				rows.add(row);
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil.closeSource(resultSet, prepareStatement, conn);
		}
		return rows;
	}

	public static int updateBySQL(String sqlString) {
		Connection conn = DbUtil.getConnection();
		PreparedStatement prepareStatement = null;
		int result = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			result = prepareStatement.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil.closeSource(null, prepareStatement, conn);
		}
		return result;
	}
	public static int deleteBySQL(String sqlString) {
		Connection conn = DbUtil.getConnection();
		PreparedStatement prepareStatement = null;
		int result = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			result = prepareStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil.closeSource(null, prepareStatement, conn);
		}
		return result;
	}

	public static int updateBySQL(String sqlString, Object[] parms ) {
		Connection conn = DbUtil.getConnection();
		PreparedStatement prepareStatement = null;
		int result = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			for (int len = parms.length, i = 0; i < len; i++) {
				prepareStatement.setObject(i + 1, parms[i]);
			}
			result = prepareStatement.executeUpdate();
			conn.commit();//COMMIT命令用于把事务所做的修改保存到数据库
		} catch (SQLException e) {
			System.out.println(sqlString);
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil.closeSource(null, prepareStatement, conn);
		}
		return result;
	}
	
	public static int updateBySQL(String sqlString, Object[] parms, int[] types) {
		Connection conn = DbUtil.getConnection();
		PreparedStatement prepareStatement = null;
		int result = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			for (int len = parms.length, i = 0; i < len; i++) {
				prepareStatement.setObject(i + 1, parms[i], types[i]);
			}
			result = prepareStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil.closeSource(null, prepareStatement, conn);
		}
		return result;
	}

	public static int updateBySQL(String sqlString, CompletePrepareStatementI cps) {
		Connection conn = DbUtil.getConnection();
		PreparedStatement prepareStatement = null;
		int result = 0;
		try {
			conn.setAutoCommit(false);
			prepareStatement = conn.prepareStatement(sqlString);
			prepareStatement = cps.completeIt(prepareStatement);
			result = prepareStatement.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DbUtil.closeSource(null, prepareStatement, conn);
		}
		return result;
	}
	public static int createTable(String sql){
		Connection conn = DbUtil.getConnection();
		 try {  
			 conn.createStatement().execute(sql);  
			 return 1;
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }
		 DbUtil.closeSource(null,null, conn);
		return 0;
	}
}
