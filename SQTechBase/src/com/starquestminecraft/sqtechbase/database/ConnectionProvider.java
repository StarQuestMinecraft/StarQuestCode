package com.starquestminecraft.sqtechbase.database;

import java.sql.Connection;

public interface ConnectionProvider {
	
	public Connection getConnection();
	
}
