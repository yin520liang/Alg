/**
 * 
 */
package jdbc.mysqlrouter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import jdbc.DBConfigReader;

/**
 * @Title MySQLRouterTest
 */
public class MySQLRouterTest {
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

		String writeUrl = String.format(
				"jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=utf8&useSSL=false", address,
				db, user, password);
		String readUrl = String.format(
				"jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=utf8&useSSL=false",
				address, db, user, password);
		Connection writeCon = null, readCon = null;

		String insertSql = "insert into t2(id, name) values (1001, 'This is for insert');";
		String selectSql = "select * from t2;";
		try {
			writeCon = getConnection(writeUrl);
			readCon = getConnection(readUrl);
			try {
				Statement insertStmt = writeCon.createStatement();
				Statement selectStmt = readCon.createStatement();

				int res = insertStmt.executeUpdate(insertSql);
				ResultSet rs = selectStmt.executeQuery(selectSql);
				while (rs.next()) {
					System.out.println(rs.getString("name"));
				}
				rs.close();

				insertStmt.close();
				selectStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (writeCon != null) {
					writeCon.close();
				}
				if (readCon != null) {
					readCon.close();
				}
			}
		} catch (SQLException e) {
			System.err.println("Close connection failed: " + e.getMessage());
		}
	}

	private static Connection getConnection(String url) throws SQLException {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl(url);
		return dataSource.getConnection();

	}
}
