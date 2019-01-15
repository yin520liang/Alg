/**
 * 
 */
package jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @Title JdbcTest
 * 
 * <p>Two ways to get a connection to a database:
 * 	<li>1. DriverManager
 *  <li>2. DataSource 
 *  <li>3. Spring Managed
 */
public class JdbcTest {
	
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
	}

	private static void connectByDriverManager() {
		// log setting
		PrintWriter logWriter = new PrintWriter(System.out);
		DriverManager.setLogWriter(logWriter);

//		Class.forName("com.mysql.jdbc.Driver");
		// Driver类在加载时会将自己注册到DriverManager
		String url = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=utf8&useSSL=false"
				,address, db, user, password);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			if (conn != null) {
				System.out.println("Connected !");
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.err.println("Close connection failed.");
				}
			}
			logWriter.close();
		}
	}
	
	
	private static void connectByDataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		String url = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=utf8&useSSL=false"
						, address, db, user, password);
		dataSource.setUrl(url);
		Connection conn = null;
		try {
			conn = dataSource.getConnection("root", "root");
			if(conn != null) {
				System.out.println("Connected !");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.err.println("Close connection failed.");
				}
			}
		}
	}
	
	private static void connectBySpringWithoutTransaction() {	
		String url = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=utf8&useSSL=false",
				address, db, user, password);
		Driver driver = null;
		try {
			driver = DriverManager.getDriver(url);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SimpleDriverDataSource ds = new SimpleDriverDataSource(driver, url, "root", "root");
		Connection conn = null;
		try {
			conn = ds.getConnection();
			if(conn != null) {
				System.out.println("Connected !");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.err.println("Close connection failed.");
				}
			}
		}
	}
	
	private static void connectBySpringWithTransaction() {

	}
	

}
