package net.homeip.hall.sqglobalinfo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import net.homeip.hall.sqglobalinfo.database.BedspawnConnectionProvider;

public class SQLDatabase {
	
	private ConnectionProvider con;
	
	static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS global_player_info (name varchar(16), uuid varchar(36), ip varchar(40), time DATETIME, online tinyint, world varchar(16), location varchar(32), primary key (name))";
	
	static final String UPSERT_SQL = "INSERT INTO global_player_info (name, uuid, ip, time, online, world, location) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE uuid = VALUES(uuid), ip = VALUES(ip), time = VALUES(time), online = VALUES(online), world = VALUES(world), location = VALUES(location)";
	
	static final String READ_ALL_SQL = "SELECT * FROM global_player_info WHERE `name` = ?";
	
	static final String READ_NAMES_SQL = "SELECT `name`, `time` FROM global_player_info WHERE `uuid` = (SELECT `uuid` FROM global_player_info WHERE `name` = ?) ORDER BY time DESC";
	static final String READ_ALTS_GIVEN_IP_SQL = "SELECT `name` FROM global_player_info WHERE `ip` = ?";
	static final String READ_ALTS_GIVEN_NAME_SQL = "SELECT `name` FROM global_player_info WHERE `ip` = (SELECT `ip` FROM global_player_info WHERE `name` = ?)";
	
	
	public SQLDatabase() {
		con = new BedspawnConnectionProvider();
		createTable(getConnection());
	}
	
	private void createTable(Connection connection) {
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(CREATE_TABLE_SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
 	public void updatePlayerData(String name, String uuid, String ip, Date time, boolean online, String world, String location) {
 		System.out.println("World: \"" + world + "\"");
 		System.out.println("Location: \"" + location + "\"");
 		System.out.println("IP: \"" + ip + "\"");
 		System.out.println("Name: \"" + name.toLowerCase() + "\"");
 		System.out.println("UUID: \"" + uuid + "\"");
 		
 		int onlineInt;
 		if(online == true) {
 			onlineInt = 1;
 		}
 		else {
 			onlineInt = 0;
 		}
 		try {
 			PreparedStatement s = getConnection().prepareStatement(UPSERT_SQL);
 			s.setString(1, name.toLowerCase());
 			s.setString(2, uuid);
 			s.setString(3, ip);
 			s.setTimestamp(4, new Timestamp(time.getTime()));
 			s.setInt(5, onlineInt);
 			s.setString(6, world);
 			s.setString(7, location);
 			s.executeUpdate();
 		}
 		catch(SQLException e) {
 			e.printStackTrace();
 		}
 	}

 	
 	public ArrayList<String> getNames(String name, Connection connection) {
 		ArrayList<String> names = new ArrayList<String>();
 		try {
 			PreparedStatement s = connection.prepareStatement(READ_NAMES_SQL);
 			s.setString(1, name);
 			ResultSet results = s.executeQuery();
 			//results.beforeFirst();
 			while (results.next()) {
 				names.add(results.getString(1));
 			}
 		}
 		catch(SQLException e) {
 			e.printStackTrace();
 		}
 		return names;
 	}
 	
 	public ArrayList<String> getAltsFromIP(String ip, Connection connection) {
 		ArrayList<String> names = new ArrayList<String>();
 		try {
 			PreparedStatement s = connection.prepareStatement(READ_ALTS_GIVEN_IP_SQL);
 			s.setString(1, ip);
 			ResultSet results = s.executeQuery();
 			while(results.next()) {
 				names.add(results.getString(1));
 			}
 		}
 		catch(SQLException e) {
 			e.printStackTrace();
 		}
 		return names;
 	}
 	public ArrayList<String> getAltsFromName(String name, Connection connection) {
 		ArrayList<String> names = new ArrayList<String>();
 		try {
 			PreparedStatement s = connection.prepareStatement(READ_ALTS_GIVEN_NAME_SQL);
 			s.setString(1, name);
 			ResultSet results = s.executeQuery();
 			while(results.next()) {
 				names.add(results.getString(1));
 			}
 		}
 		catch(SQLException e) {
 			e.printStackTrace();
 		}
 		return names;
 	}
 	
 	public HashMap<String, Object> getData(String name, Connection connection) {
 		HashMap<String, Object> data = new HashMap<String, Object>();
 		try {
 			PreparedStatement s = connection.prepareStatement(READ_ALL_SQL);
 			s.setString(1, name);
 			ResultSet results = s.executeQuery();
 			while(results.next()) {
 				data.put("name", results.getString(1));
 				data.put("uuid", results.getString(2));
 				data.put("ip", results.getString(3));
 				data.put("time", new Date(results.getTimestamp(4).getTime()));
 				data.put("online", new Boolean((results.getInt(5) != 0)));
 				data.put("world", results.getString(6));
 				data.put("location", results.getString(7));
 				return data;
 			}
 		}
 		catch(SQLException e) {
 			e.printStackTrace();
 		}
 		return null;
 	}

 	public Connection getConnection() {
 		return con.getConnection();
 	}
}
