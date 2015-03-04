
package us.higashiyama.george.SQDatabase;

public class SQLConnectionData {

	private String user;
	private String pass;
	private String connectionString;

	public SQLConnectionData(String username, String password, String connStr) {

		this.user = username;
		this.pass = password;
		this.connectionString = connStr;
	}

	public String getUsername() {

		return user;
	}

	public void setUsername(String user) {

		this.user = user;
	}

	public String getPassword() {

		return pass;
	}

	public void setPassword(String pass) {

		this.pass = pass;
	}

	public String getConnectionString() {

		return connectionString;
	}

	public void setConnectionString(String connectionString) {

		this.connectionString = connectionString;
	}

}
