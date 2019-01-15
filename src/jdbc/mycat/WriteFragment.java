/**
 * 
 */
package jdbc.mycat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import jdbc.DBConfigReader;

/**
 * @Title WriteFragment
 * @Description 写分片测试
 */
public class WriteFragment {
	private static String user;
	private static String password;
	private static String address;
	private static String db;
	
	private static int ID_INC = 0;
	
	private static Connection conn = null;
	private static String instance = "local";
	
	public static void main(String[] args) {
		DBConfigReader prop = DBConfigReader.load();
		user = prop.getUser(instance);
		password = prop.getPassword(instance);
		address = prop.getUrl(instance);
		db = prop.getDb(instance);
		
		MysqlDataSource dataSource = new MysqlDataSource();
		String url = String.format(
				"jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=utf8&useSSL=false",
				address, db, user, password);
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
