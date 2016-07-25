package com.ginger_walnut.sqsmoothcraft.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLDatabase {

	BedspawnConnectionProvider con;

	static final String WRITE_OBJECT_SETTINGS_SQL = "CALL set_smoothcraft_options(?, ?, ?);";
	static final String READ_OBJECT_SETTINGS_SQL = "SELECT * FROM minecraft.smoothcraft_settings";
	static final String CREATE_TABLE_SETTINGS = "CREATE TABLE IF NOT EXISTS minecraft.smoothcraft_settings(player varchar(40) NOT NULL, object BLOB)";
	
	public SQLDatabase() {
		
		con = new BedspawnConnectionProvider();
		createSettingsTable(con.getConnection());
		
	}

	public void createSettingsTable(Connection conn) {
		
		try {
			
			Statement s = conn.createStatement();
			s.executeUpdate(CREATE_TABLE_SETTINGS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}


	public void writeSettingsData(Connection conn, String uuid, short lockedDirection) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(WRITE_OBJECT_SETTINGS_SQL);
		
		pstmt.setString(1, uuid);
		
		pstmt.executeUpdate();

	}

	public ResultSet readSettingsData(Connection conn) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(READ_OBJECT_SETTINGS_SQL);
		
		return pstmt.executeQuery();

	}

}
