package com.ginger_walnut.sqsmoothcraft.database;

import java.sql.Connection;

public interface ConnectionProvider {
	
	public Connection getConnection();
	
}
