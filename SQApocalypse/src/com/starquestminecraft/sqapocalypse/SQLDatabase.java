package com.starquestminecraft.sqapocalypse;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.countercraft.movecraft.bedspawns.Bedspawn;

public class SQLDatabase {
	
	private static final String update = "INSERT INTO apoc_data(uuid, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score=?";
	private static final String get = "SELECT score FROM apoc_data WHERE uuid = ?";
	private static final String top = "SELECT * FROM apoc_data ORDER BY score DESC LIMIT 10";
	
	private static Connection getConnection(){
		return Bedspawn.cntx;
	}
	
	public static int getScore(UUID plr){
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
	
	public static void displayTop(Player requester){
		try{
			PreparedStatement pstmt = getConnection().prepareStatement(top);
			ResultSet rs = pstmt.executeQuery();
			int num = 1;
			while(rs.next()){
				int score = rs.getInt("score");
				UUID u = UUID.fromString(rs.getString("uuid"));
				OfflinePlayer p = Bukkit.getOfflinePlayer(u);
				if(p != null){
					requester.sendMessage(num + "): " + p.getName() + ", " + score + " points");
					num++;
				}
			}
			rs.close();
			pstmt.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void addScore(UUID player, int addition){
		int score = getScore(player);
		updateScore(player, score + addition);
	}
	
	public static void updateScore(UUID player, int score){
		
		try{
		PreparedStatement pstmt = getConnection().prepareStatement(update);

		// set input parameters
		pstmt.setString(1, player.toString());
		pstmt.setInt(2, score);
		pstmt.setInt(3, score);
		pstmt.executeUpdate();
		pstmt.close();
		} catch (Exception e){
			e.printStackTrace();
		}
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
