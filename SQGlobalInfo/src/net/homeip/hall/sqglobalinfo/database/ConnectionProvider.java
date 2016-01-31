package net.homeip.hall.sqglobalinfo.database;

import java.sql.Connection;

public interface ConnectionProvider {
	public Connection getConnection();
}
