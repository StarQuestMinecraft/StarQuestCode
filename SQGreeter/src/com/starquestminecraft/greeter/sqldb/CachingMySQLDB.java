package com.starquestminecraft.greeter.sqldb;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;

import com.starquestminecraft.greeter.Settings;
import com.starquestminecraft.greeter.Greeter;

public class CachingMySQLDB{

	private static final String set_data = "INSERT INTO minecraft.premium_data(uuid, purchaseTime, hoursPurchased, permanent, codes) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE purchaseTime = ?,hoursPurchased=?,permanent=?,codes=?";
	private static final String get_data = "SELECT * from minecraft.premium_data WHERE uuid = ?";
	private static final String get_all = "SELECT * from minecraft.premium_data";
	
	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname; /*starquest.c1odbljhmyum.us-east-1.rds.amazonaws.com*/;
	public static String port = "3306";
	public static String db_name;
	public static String username;
	public static String password;
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);
	
	private HashMap<String, String> allPlayerData = new HashMap<String, String>();
	
	public CachingMySQLDB(){
		hostname = Settings.dbhost;
		db_name = Settings.dbdb;
		username = Settings.dbuser;
		password = Settings.dbpass;
		dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);
		String Database_table = "CREATE TABLE IF NOT EXISTS minecraft.greeter_data (`ip` VARCHAR(36),`username` VARCHAR(32),PRIMARY KEY(`ip`))";
		getContext();
		try {
			Driver driver = (Driver) Class.forName(driverString).newInstance();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			System.out.println("[SQGreeter] Driver error: " + e);
		}
		Statement s = null;
		try {
			s = cntx.createStatement();
			s.executeUpdate(Database_table);
			System.out.println("[SQGreeter] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[SQGreeter] Table Creation Error");
		}
	}
	

	public void initialize() {
		System.out.println("[Greeter] Database initializing.");
		allPlayerData = loadAll();
		System.out.println("[Greeter] Done: " + allPlayerData.size() + " known players found!");
	}
	public void shutDown() {
		if(cntx != null){
			try{
			cntx.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public HashMap<String,String> loadAll(){
		HashMap<String,String> retval = new HashMap<String,String>();
		getContext();
		try {
			Statement s = cntx.createStatement();
			ResultSet rs = s.executeQuery("SELECT * from minecraft.greeter_data");
			while(rs.next()){
				String ip = rs.getString("ip");
				String username = rs.getString("username");
				retval.put(ip, username);
			}
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retval;
	}
	
	public String getUsername(String ip){
		return allPlayerData.get(ip);
	}
	
	public void updateIP(final String ip, final String username){
		allPlayerData.put(ip, username);
		ProxyServer.getInstance().getScheduler().runAsync(Greeter.getInstance(),
            new Runnable() {
                public void run() {
            		getContext();
            		try {
						PreparedStatement ps = cntx.prepareStatement("INSERT INTO minecraft.greeter_data(ip,username) VALUES (?,?) "+
								"ON DUPLICATE KEY UPDATE username=?");
						ps.setString(1,ip);
						ps.setString(2, username);
						ps.setString(3,username);
						ps.execute();
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        );
 
	}

	
	public static boolean getContext() {

		try {
			if ((cntx == null) || (cntx.isClosed()) || (!cntx.isValid(1))) {
				cntx = DriverManager.getConnection(dsn, username, password);
				if ((cntx == null) || (cntx.isClosed())) {
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			System.out.print("Error could not Connect to db " + dsn + ": " + e.getMessage());
		}
		return false;
	}
	
	private ArrayList<String> fromCSV(String s){
		if(s == null) return new ArrayList<String>();
		String[] split = s.split(",");
		ArrayList<String> retval = new ArrayList<String>(split.length);
		for(String spl : split){
			retval.add(spl);
		}
		return retval;
	}
	
	private String toCSV(ArrayList<String> a){
		String r = "";
		if(a.size() == 0) return r;
		for(int i = 0; i < a.size() - 1; i++){
			r = r + a.get(i) + ",";
		}
		r = r + a.get(a.size() - 1);
		return r;
	}
	
	private int millisToHours(long millis) {
		return (int) (millis / 1000L / 60L / 60L);
	}
}
