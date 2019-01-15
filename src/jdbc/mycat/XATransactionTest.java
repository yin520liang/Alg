/**
 * 
 */
package jdbc.mycat;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import jdbc.DBConfigReader;

/**
 * @Title XATransactionTest
 * XA transaction test
 */
public class XATransactionTest {
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
		
		String url1 = String.format(
				"jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=utf8&useSSL=false",
				address, db, user, password);
		String url2 = String.format(
				"jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=utf8&useSSL=false",
				address, db, user, password);

		Connection conn1 = null, conn2 = null;
		Statement stmt1 = null, stmt2 = null;
		try {
			conn1 = getConnection(url1);
			conn2 = getConnection(url2);

			stmt1 = conn1.createStatement();
			stmt2 = conn2.createStatement();

			UUID txId = UUID.randomUUID();
			try {
				// start
				stmt1.execute(String.format("XA start '%s'", txId));
				stmt2.execute(String.format("XA start '%s'", txId));

				int res1 = stmt1.executeUpdate("insert into t2(id, name) values(1, 'xa_1')");
				if (res1 < 1)
					throw new RuntimeException("update t1 in localhost failed!");
				
				int res2 = stmt2.executeUpdate("insert into t2(id, name) values(1, 'xa_2')");
				if (res2 < 1)
					throw new RuntimeException("update t1 in 3086 failed!");
				
				// end
				stmt1.execute(String.format("XA end '%s'", txId));
				stmt2.execute(String.format("XA end '%s'", txId));
				
				// prepare
				stmt1.execute(String.format("XA prepare '%s'", txId));
				stmt2.execute(String.format("XA prepare '%s'", txId));
				
				// commit
				stmt1.execute(String.format("XA commit '%s'", txId));
				stmt2.execute(String.format("XA commit '%s'", txId));
				
				System.out.println("committed!");
				
			} catch (SQLException | RuntimeException e) {
				System.err.println(e.getMessage());
				stmt1.execute(String.format("XA rollback '%s'", txId));
				stmt2.execute(String.format("XA rollback '%s'", txId));
				System.err.println("rollback");
			}
			
			stmt1.close();
			stmt2.close();
			conn1.close();
			conn2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static Connection getConnection(String url) throws SQLException {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl(url);
		return dataSource.getConnection();

	}
}
