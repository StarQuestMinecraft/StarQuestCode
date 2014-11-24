package com.starquestminecraft.sqcp;

import java.sql.Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Pool {
	
	private static final int POOL_SIZE = 3;
	public static String driver = "com.mysql.jdbc.Driver";
	public static String hostname = "play.starquestminecraft.com";
	public static String port = "3306";
	public static String db_name = "minecraft";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);
	
	private static ComboPooledDataSource source;
	
	
	/*
	 * Plugins that should use this API
	 * 
	 * - SQShops
	 * - SQRankup
	 * - SQDuties
	 * - SQBungeeMovecraft
	 * - Redeem
	 * 
	 * Pre-pool stats:
	 * 12 connections * (12 planets + 3 space + 1 limbo + 1 belt) = 204 cntx
	 * 12 connections * (10 planets + 3 space + 1 limbo) = 168 cntx
	 * 
	 * Post-pool stats (assuming max pool size of 3):
	 * 9 connections * (12 planets + 3 space + 1 limbo + 1 belt) = 153 cntx
	 * 9 connections * (10 planets + 3 space + 1 limbo) = 126 cntx
	 * 
	 * Maximum servers with this pool: 22
	 * 1 limbo + 1 belt + 4 space + 16 planets
	 * 
	 */
	
	public void onLoad() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(driver);
			cpds.setJdbcUrl(dsn);
			cpds.setUser(username);
			cpds.setPassword(password);
			cpds.setMaxPoolSize(POOL_SIZE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		source = cpds;
	}
	
	public static Connection getConnection(){
		try{
		return source.getConnection();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
