
package us.higashiyama.george.SQDuties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class SQLTask {

	// Not implementing runnable because

	public final DataSource dataSource;
	public PreparedStatement query;

	public SQLTask(String s, DataSource source) {

		this.dataSource = source;
		try {
			this.query = source.getConnection().prepareStatement(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet execute() {

		try {
			return this.query.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
