package com.starquestminecraft.sqrankup2.database;

import java.sql.Connection;

public interface ConnectionProvider {
	public Connection getConnection();
}
