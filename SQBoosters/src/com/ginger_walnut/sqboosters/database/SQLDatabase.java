package com.ginger_walnut.sqboosters.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLDatabase {

	BedspawnConnectionProvider con;

	static final String CHECK_RECORD_EXISTS_SQL = "SELECT count(1) FROM boosters WHERE booster = ?";
	static final String UPDATE_OBJECT_SQL = "UPDATE boosters SET multiplier = ? WHERE `booster` = ?";
	static final String WRITE_OBJECT_SQL = "INSERT INTO boosters(booster, multiplier) VALUES (?, ?)";
	static final String READ_OBJECT_SQL = "SELECT multiplier FROM boosters WHERE `booster`= ?";
	static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS boosters ( booster varchar(32), multiplier tinyint, primary key (booster))";
	
	public SQLDatabase() {
		
		con = new BedspawnConnectionProvider();
		createTable(con.getConnection());
		
	}


	public void createTable(Connection conn) {
		
		try {
			
			Statement s = conn.createStatement();
			s.executeUpdate(CREATE_TABLE);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

	public void updateData(Connection conn, String booster, int multiplier) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_OBJECT_SQL);

		// set input parameters
		pstmt.setInt(1, multiplier);
		pstmt.setString(2, booster);
		
		pstmt.executeUpdate();
		
	}

	public void writeData(Connection conn, String booster, int multiplier) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(WRITE_OBJECT_SQL);

		// set input parameters
		pstmt.setString(1, booster);
		pstmt.setInt(2, multiplier);
		
		pstmt.executeUpdate();

	}

	public int readData(Connection conn, String booster) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(READ_OBJECT_SQL);
		
		pstmt.setString(1, booster);
		
		ResultSet rs = pstmt.executeQuery();
		rs.next();
	
		int data = rs.getInt(1);
		
		return data;
		

		
	}
	
	public boolean checkRecordExists(Connection conn, String booster) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(CHECK_RECORD_EXISTS_SQL);
		
		pstmt.setString(1, booster);
		
		ResultSet rs = pstmt.executeQuery();
		rs.next();
	
		int exists = rs.getInt(1);
		
		if (exists == 0) {
			
			return false;
			
		} else {
			
			return true;
			
		}
		
	}

}
