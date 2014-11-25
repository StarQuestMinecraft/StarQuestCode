package com.starquestminecraft.sqcp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExampleUser {
	
	public static void neverCalled(String name){
		SharedConnection c = Pool.checkOutConnection();
		Connection cntx = c.getConnection();
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
			Pool.returnConnection(c);
		}
	}
}
