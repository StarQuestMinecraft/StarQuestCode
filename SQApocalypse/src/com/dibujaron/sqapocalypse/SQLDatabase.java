package com.dibujaron.sqapocalypse;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import net.countercraft.movecraft.bedspawns.Bedspawn;

public class SQLDatabase {
	
	private static final String update = "INSERT INTO apoc_data(uuid, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score=?";
	private static final String get = "SELECT score FROM apoc_data WHERE uuid = ?";
	
	private static Connection getConnection(){
		return Bedspawn.cntx;
	}
	
	private static int getScore(UUID plr){
		try{
			PreparedStatement pstmt = getConnection().prepareStatement(get);
			pstmt.setString(1, plr.toString());
			ResultSet rs = pstmt.executeQuery();
			int score;
			if(rs.next()){
				score = rs.getInt("score");
			} else {
				score = 0;
			}
			rs.close();
			pstmt.close();
			return score;
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	private void updateScore(UUID player, int score){
		
		PreparedStatement pstmt = getConnection().prepareStatement(update);

		// set input parameters
		pstmt.setString(1, player);
		pstmt.setString(2, score);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public static void setUp() {
		String Database_table = "CREATE TABLE IF NOT EXISTS apoc_data (`uuid` VARCHAR(36),`score` INT(11), PRIMARY KEY(`uuid`))";
		
		Statement s = null;
		try {
			s = getConnection().createStatement();
			s.executeUpdate(Database_table);
			System.out.println("[SQApocalypse] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[SQApocalypse] Table Creation Error");
		}
	}
}
