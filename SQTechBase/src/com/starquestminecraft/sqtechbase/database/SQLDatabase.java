package com.starquestminecraft.sqtechbase.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import com.starquestminecraft.sqtechbase.database.objects.SerializableGUIBlock;
import com.starquestminecraft.sqtechbase.database.objects.SerializableMachine;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.PlayerOptions;

public class SQLDatabase {

	public static BedspawnConnectionProvider con;

	static final String WRITE_GUIBLOCKS = "INSERT INTO minecraft.guiblocks(server, object) VALUES (?, ?)";
	static final String READ_GUIBLOCKS = "SELECT * FROM minecraft.guiblocks WHERE server = ?";
	static final String CLEAR_GUIBLOCKS = "DELETE FROM minecraft.guiblocks WHERE server = ?";
	static final String CREATE_GUIBLOCKS = "CREATE TABLE IF NOT EXISTS minecraft.guiblocks (ID int NOT NULL AUTO_INCREMENT, server varchar(32), object BLOB, primary key (ID))";
	
	static final String WRITE_MACHINES = "INSERT INTO minecraft.machines(server, object) VALUES (?, ?)";
	static final String READ_MACHINES = "SELECT * FROM minecraft.machines WHERE server = ?";
	static final String CLEAR_MACHINES = "DELETE FROM minecraft.machines WHERE server = ?";
	static final String CREATE_MACHINES = "CREATE TABLE IF NOT EXISTS minecraft.machines (ID int NOT NULL AUTO_INCREMENT, server varchar(32), object BLOB, primary key (ID))";
	
	static final String WRITE_OPTION = "INSERT INTO minecraft.tech_options(uuid, object) VALUES (?, ?) ON DUPLICATE KEY UPDATE object = VALUES (object)";
	static final String READ_OPTIONS = "SELECT * FROM minecraft.tech_options WHERE uuid = ?";
	static final String CREATE_OPTIONS = "CREATE TABLE IF NOT EXISTS minecraft.tech_options (uuid varchar(36), object BLOB, primary key (uuid))";
	
	public SQLDatabase() {
		
		con = new BedspawnConnectionProvider();
		createTables(con.getConnection());
		
	}

	public void createTables(Connection conn) {
		
		try {
			
			Statement s = conn.createStatement();
			s.executeUpdate(CREATE_GUIBLOCKS);
			s.executeUpdate(CREATE_MACHINES);
			s.executeUpdate(CREATE_OPTIONS);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

	public static void writeGUIBlocks(Connection conn, String server, List<GUIBlock> guiBlocks) throws Exception {
		
		if (guiBlocks.size() > 0) {
			
			String query = WRITE_GUIBLOCKS;
			
			for (int i = 0; i < guiBlocks.size() - 1; i ++) {
				
				query = query + ", (?, ?)";
				
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);

			for (int i = 0; i < guiBlocks.size(); i ++) {
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos;
				
				try {
					
					oos = new ObjectOutputStream(baos);
					oos.writeObject(new SerializableGUIBlock(guiBlocks.get(i)));
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
				
				pstmt.setString(((i + 1) * 2) - 1, server);
				pstmt.setBinaryStream((i + 1) * 2, new ByteArrayInputStream(baos.toByteArray()), baos.toByteArray().length);
				
			}

			pstmt.executeUpdate();
			
		}

	}

	public static ResultSet readGUIBlocks(Connection conn, String server) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(READ_GUIBLOCKS);
		
		pstmt.setString(1, server);
		
		return pstmt.executeQuery();

	}
	
	public static void clearGUIBlocks(Connection conn, String server) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(CLEAR_GUIBLOCKS);

		pstmt.setString(1, server);
		
		pstmt.execute();
		
	}
	
	public static void writeMachines(Connection conn, String server, List<Machine> machines) throws Exception {
		
		if (machines.size() > 0) {
			
			String query = WRITE_MACHINES;
			
			for (int i = 0; i < machines.size() - 1; i ++) {
				
				query = query + ", (?, ?)";
				
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);

			for (int i = 0; i < machines.size(); i ++) {
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos;
				
				try {
					
					oos = new ObjectOutputStream(baos);
					oos.writeObject(new SerializableMachine(machines.get(i)));
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
				
				pstmt.setString(((i + 1) * 2) - 1, server);
				pstmt.setBinaryStream((i + 1) * 2, new ByteArrayInputStream(baos.toByteArray()), baos.toByteArray().length);
				
			}

			pstmt.executeUpdate();
			
		}

	}

	public static ResultSet readMachines(Connection conn, String server) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(READ_MACHINES);
		
		pstmt.setString(1, server);
		
		return pstmt.executeQuery();

	}
	
	public static void clearMachines(Connection conn, String server) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(CLEAR_MACHINES);
		
		pstmt.setString(1, server);
		
		pstmt.execute();
		
	}
	
	public static void updateOptions(Connection conn, UUID playerUUID, PlayerOptions options) throws Exception{
		
		PreparedStatement pstmt = conn.prepareStatement(WRITE_OPTION);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		
		try {
			
			oos = new ObjectOutputStream(baos);
			oos.writeObject(options);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		pstmt.setString(1, playerUUID.toString());
			
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		int length = baos.toByteArray().length;
		
		pstmt.setBinaryStream(2, bais, length);
			
		bais.close();
		oos.close();
		baos.close();
		
		pstmt.executeUpdate();

	}
	
	
	public static ResultSet readOption(Connection conn, UUID playerUUID) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(READ_OPTIONS);
		
		pstmt.setString(1, playerUUID.toString());
		
		return pstmt.executeQuery();

	}

}
