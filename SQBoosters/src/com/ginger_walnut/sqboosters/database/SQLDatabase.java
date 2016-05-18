package com.ginger_walnut.sqboosters.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLDatabase {

	BedspawnConnectionProvider con;

	static final String WRITE_OBJECT_SQL = "INSERT INTO minecraft.boosters2(booster, multiplier, purchaser, expirationdate) VALUES (?, ?, ?, DATE_ADD(NOW(), INTERVAL ? MINUTE))";
	static final String READ_OBJECT_SQL = "SELECT * FROM minecraft.boosters2 WHERE `expirationdate` > NOW()";
	static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS minecraft.boosters2 (ID int NOT NULL AUTO_INCREMENT, booster varchar(32) NOT NULL, multiplier tinyint NOT NULL, purchaser varchar(32) NULL, expirationdate datetime NOT NULL, primary key (ID))";
	
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


	public void writeData(Connection conn, String booster, int multiplier, String purchaser, int minutes) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(WRITE_OBJECT_SQL);

		pstmt.setString(1, booster);
		pstmt.setInt(2, multiplier);
		pstmt.setString(3, purchaser);
		pstmt.setInt(4, minutes);
		
		pstmt.executeUpdate();

	}

	public ResultSet readData(Connection conn) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(READ_OBJECT_SQL);
		
		return pstmt.executeQuery();

	}

}
