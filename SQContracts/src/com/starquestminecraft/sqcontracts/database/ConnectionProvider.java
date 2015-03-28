package com.starquestminecraft.sqcontracts.database;

import java.sql.Connection;

public interface ConnectionProvider {
	public Connection getConnection();
}
