package com.whirlwindgames.dibujaron.sqempire.database;

import java.sql.Connection;

import net.countercraft.movecraft.bedspawns.Bedspawn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class EmpireDB {
	
	//database has the following tables:
	//EmpireBoard(id,planetID,xcoord,zcoord,affiliation(0-3))
	//EmpirePlayer(uuid,name,empire(0-3),power_<0-4>int,lastseen_<0-4>int)
	//EmpireFaction?(empire(0-2),factionID)
	
	static{
		command("CREATE TABLE IF NOT EXISTS minecraft.empire_player"
				+ " (uuid VARCHAR(36),lname VARCHAR(16), empire INT(11), lastSeen INT(11), lastChanged datetime, PRIMARY KEY (uuid))");
		command("CREATE TABLE IF NOT EXISTS minecraft.empire_board(id INT(11),"
				+ "planet INT(11),cx INT(11),cz INT(11),empire INT(11), PRIMARY KEY(id))");
		command("CREATE TABLE IF NOT EXISTS minecraft.empire_faction(factionID VARCHAR(36),"
				+ "empire INT(11), PRIMARY KEY(factionID))");
		command("CREATE TABLE IF NOT EXISTS minecraft.empire_territories(planet VARCHAR(32),"
				+ "empire0 INT(11), empire1 INT(11), empire2 INT(11), empire3 INT(11), PRIMARY KEY(planet))");
	}
	
	public static ResultSet requestData(String query){
		Statement s = null;
		ResultSet rs = null;
		try{
			s = getConn().createStatement();
			rs = s.executeQuery(query);
		} catch(Exception e){
			System.out.println("Empire Database: SQL Exception!");
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void command(String query){
		Statement s = null;
		try{
			s = getConn().createStatement();
			s.executeUpdate(query);
		} catch(Exception e){
			System.out.println("Empire Database: SQL Exception!");
			e.printStackTrace();
		}
	}

	public static PreparedStatement prepareStatement(String statement) {
		try {
			return getConn().prepareStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Connection getConn() {
		return Bedspawn.cntx;
	}

}
