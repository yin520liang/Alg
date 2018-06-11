/**
 * 
 */
package jdbc.mysqlrouter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @Title MySQLRouterTest
 * @Description
 * @Author lvzhaoyang
 * @Date 2018年4月24日
 */
public class MySQLRouterTest {

	/**
	 * @Description
	 * @Author lvzhaoyang
	 * @Date 2018年4月24日
	 */
	public static void main(String[] args) {
		String writeUrl = "jdbc:mysql://127.0.0.1:7001/test?user=root&password=root&useUnicode=true&characterEncoding=utf8&useSSL=false";
		String readUrl = "jdbc:mysql://127.0.0.1:7002/test?user=root&password=MZtest2018!&useUnicode=true&characterEncoding=utf8&useSSL=false";
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
				while(rs.next()) {
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
