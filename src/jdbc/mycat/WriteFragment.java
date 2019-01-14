/**
 * 
 */
package jdbc.mycat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @Title WriteFragment
 * @Description 写分片测试
 */
public class WriteFragment {
	
	private static int ID_INC = 0;
	
	private static Connection conn = null;
	
	public static void main(String[] args) {
		MysqlDataSource dataSource = new MysqlDataSource();
		String url = "jdbc:mysql://127.0.0.1:8066/TEST?useUnicode=true&characterEncoding=utf8&useSSL=false";
		dataSource.setUrl(url);
		try {		
			conn = dataSource.getConnection();
			write("kate");
//			write("taylor swift");
			read();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.err.println("Close connection failed.");
				}
			}
		}
	}

	private static void read() throws SQLException {
		String sql = "select * from t1 order by id;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString("name"));
		}
		rs.close();
		stmt.close();
	}
	
	private static void write(String name) throws SQLException {
		String sql = "insert into t1(id, name, age) values(next value for MYCATSEQ_GLOBAL, ?, ?);";
		PreparedStatement stmt = conn.prepareStatement(sql);
//		stmt.setLong(1, ++ ID_INC);
		stmt.setString(1, name);
		stmt.setInt(2, 23);
		stmt.executeUpdate();
		stmt.close();
	}

}
