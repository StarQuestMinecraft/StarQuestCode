package com.ginger_walnut.sqduties.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import net.countercraft.movecraft.bungee.BungeePlayerHandler;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.dibujaron.cardboardbox.Knapsack;
import com.ginger_walnut.sqduties.SQDuties;

public class SQLDatabase {

	public static BedspawnConnectionProvider con;

	static final String CHECK_RECORD_EXISTS_SQL = "SELECT count(1) FROM duties2 WHERE uuid = ?";
	static final String ADD_PLAYER_SQL = "INSERT INTO duties2 (`uuid`, `data`, `location`) VALUES (?,?,?)";
	static final String DELETE_PLAYER_SQL = "DELETE FROM duties2 WHERE `uuid` = ?";
	static final String READ_PLAYER_SQL = "SELECT * FROM duties2 WHERE `uuid` = ?";
	static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS duties2 (uuid varchar(64), data BLOB, location varchar(64), primary key (uuid))";
	
	public SQLDatabase() {
		
		con = new BedspawnConnectionProvider();
		createTable()	;	
		
	}
	
	public void createTable() {
		
		try {
			
			Statement s = con.getConnection().createStatement();
			s.executeUpdate(CREATE_TABLE);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

	public void addPlayer(Connection conn, Player player, Knapsack knapsack) {

		try {
			
			if (checkRecordExists(conn, player)) {
				
				PreparedStatement pstmt2 = con.getConnection().prepareStatement(DELETE_PLAYER_SQL);
				
				pstmt2.setString(1, player.getUniqueId().toString());
				
				pstmt2.execute();
				
			}
			
			PreparedStatement pstmt = conn.prepareStatement(ADD_PLAYER_SQL);

			pstmt.setString(1, player.getUniqueId().toString());
			
			byte[] knapsackBytes = convertToBytes(knapsack);
			pstmt.setBinaryStream(2, convertToBinary(knapsackBytes), knapsackBytes.length);
			
			pstmt.setString(3, player.getServer().getServerName() + "," + player.getLocation().getWorld().getName() + "," + player.getLocation().getBlockX() + "," + player.getLocation().getBlockY() + "," + player.getLocation().getBlockZ());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

	}
	
	public void loadPlayer(Connection conn, Player player) {
		
		try {
		
			PreparedStatement pstmt = conn.prepareStatement(READ_PLAYER_SQL);
			
			pstmt.setString(1, player.getUniqueId().toString());
			
			ResultSet rs = pstmt.executeQuery();
			
			String location = "Trinitos_Alpha, Trinitos_Alpha, 0, 100, 0";
			byte[] unparsedPerk = null;
			
			while(rs.next()) {
		
				location = rs.getString("location");
				unparsedPerk = (byte[]) rs.getObject("data");
			
			}
			
			ByteArrayInputStream baip = new ByteArrayInputStream(unparsedPerk);
			ObjectInputStream ois = new ObjectInputStream(baip);
			
			Knapsack knapsack = (Knapsack) ois.readObject();
			knapsack.unpack(player);
			
			String[] parts = location.split(",");
			
			Plugin plugin = SQDuties.getPluginMain();

			World world = plugin.getServer().getWorld(parts[1]);
			double x = Double.parseDouble(parts[2]);
			double y = Double.parseDouble(parts[3]);
			double z = Double.parseDouble(parts[4]);
				
			if (parts[0].equalsIgnoreCase(player.getServer().getServerName())) {
				
				player.teleport(new Location(world, x, y, z));
					
			} else {
				
				BungeePlayerHandler.sendPlayer(player, parts[0], parts[1], (int) x, (int) y, (int) z);
					
			}
			
			PreparedStatement pstmt2 = con.getConnection().prepareStatement(DELETE_PLAYER_SQL);
			
			pstmt2.setString(1, player.getUniqueId().toString());
			
			pstmt2.execute();
			
			pstmt2.close();
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public boolean checkRecordExists(Connection conn, Player player) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(CHECK_RECORD_EXISTS_SQL);
		
		pstmt.setString(1, player.getUniqueId().toString());
		
		ResultSet rs = pstmt.executeQuery();
		rs.next();
	
		int exists = rs.getInt(1);
		
		if (exists == 0) {
			
			return false;
			
		} else {
			
			return true;
			
		}
		
	}

	private static byte[] convertToBytes(Knapsack k) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		
		try {
			
			oos = new ObjectOutputStream(baos);
			oos.writeObject(k);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		byte[] wantBytes = baos.toByteArray();
		return wantBytes;
		
	}

	private static ByteArrayInputStream convertToBinary(byte[] bytes) {

		return new ByteArrayInputStream(bytes);
		
	}

}
