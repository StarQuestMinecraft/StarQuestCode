package com.ginger_walnut.sqdonation.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLDatabase {

	BedspawnConnectionProvider con;

	static final String WRITE_OBJECT_SQL = "CALL set_donater_amount(?, ?);";
	static final String READ_OBJECT_SQL = "SELECT * FROM minecraft.donaters";
	static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS minecraft.donaters(donater varchar(40) NOT NULL, amount int NOT NULL)";
	
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


	public void writeData(Connection conn, String uuid, int amount) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(WRITE_OBJECT_SQL);
		
		pstmt.setString(1, uuid);
		pstmt.setInt(2, amount);
		
		pstmt.executeUpdate();

	}

	public ResultSet readData(Connection conn) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(READ_OBJECT_SQL);
		
		return pstmt.executeQuery();

	}

}
