package com.starquestminecraft.sqcp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExampleUser {
	
	public static void neverCalled(String name) throws SQLException{
		Connection cntx = Pool.grab();
		Statement s = null;
		try {
			s = cntx.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM BLAH WHERE blah = " + "\'" + name + "\'");

			if (rs.next()) {
				String blah = rs.getString("blah");
			}
		} catch (Exception e) {
			System.out.print("[SQBedSpawn] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			s.close();
			Pool.returnConnection(cntx);
		}
	}
}
