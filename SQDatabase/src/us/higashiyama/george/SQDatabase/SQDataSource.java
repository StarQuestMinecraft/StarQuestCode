
package us.higashiyama.george.SQDatabase;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class SQDataSource {

	private HikariDataSource connectionPool = null;
	private String connectionString;
	private String username;
	private String password;

	public SQDataSource(String conn, String user, String pass) {

		this.connectionString = conn;
		this.username = user;
		this.password = pass;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(this.connectionString);
		config.setUsername(this.username);
		config.setPassword(this.password);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.addDataSourceProperty("useServerPrepStmts", "true");

		connectionPool = new HikariDataSource(config);
	}

	public Connection getConnection() {

		if (connectionPool != null) {
			try {
				return connectionPool.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public void setPool(HikariDataSource ds) {

		this.connectionPool = ds;
	}

	public HikariDataSource getPool() {

		return this.connectionPool;
	}

	public String getConnectionString() {

		return connectionString;
	}

	public void setConnectionString(String connectionString) {

		this.connectionString = connectionString;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}
}
