package com.ginger_walnut.sqduties.database;

import java.sql.Connection;

public interface ConnectionProvider {
	
	public Connection getConnection();
	
}
