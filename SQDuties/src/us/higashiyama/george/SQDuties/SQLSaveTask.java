
package us.higashiyama.george.SQDuties;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class SQLSaveTask implements Runnable {

	public DataSource dataSource;
	public PreparedStatement query;

	public SQLSaveTask(String s, DataSource source) {

		this.dataSource = source;

		try {
			this.query = source.getConnection().prepareStatement(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void run() {

		try {
			this.query.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		;
	}

}
