package com.ginger_walnut.sqdonation.database;

import java.sql.Connection;

public interface ConnectionProvider {
	
	public Connection getConnection();
	
}
