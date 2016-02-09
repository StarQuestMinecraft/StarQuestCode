package com.ginger_walnut.sqboosters.database;

import java.sql.Connection;

public interface ConnectionProvider {
	
	public Connection getConnection();
	
}
