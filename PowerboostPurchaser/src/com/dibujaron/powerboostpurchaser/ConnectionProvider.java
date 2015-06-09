package com.dibujaron.powerboostpurchaser;

import java.sql.Connection;

public interface ConnectionProvider {
	public Connection getConnection();
}
