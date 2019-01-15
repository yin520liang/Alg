/**
 * 
 */
package jdbc.mycat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import jdbc.DBConfigReader;

/**
 * @Title ReadWriteSeperation
 * test mycat's read/write seperation
 */
public class ReadWriteSeperation {
	private static String user;
	private static String password;
	private static String address;
	private static String db;
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
		Connection conn = null;
		try {		
			conn = dataSource.getConnection();
			read(conn);
			write(conn);
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
	
	private static void insert(Connection conn, String val) throws SQLException {
		String sql = String.format("insert into t2(id, name) values(2, '%s');", val);
		Statement stmt = conn.prepareStatement(sql);
		stmt.execute(sql);
		stmt.close();
	}
	
	private static void read(Connection conn) throws SQLException {
		String sql = "select name from t1 where id = ?;";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, 1);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString("name"));
		}
		rs.close();
		stmt.close();
	}
	
	private static void write(Connection conn) throws SQLException {
		String sql = "insert into t1(id, name,age) values(2, 'jim', 23);";
		Statement stmt = conn.prepareStatement(sql);
		stmt.execute(sql);
		stmt.close();
	}
	
}
