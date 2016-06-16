package com.starquestminecraft.sqtechbase.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.starquestminecraft.sqtechbase.GUIBlock;
import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.database.objects.SerializableGUIBlock;
import com.starquestminecraft.sqtechbase.database.objects.SerializableMachine;

public class SQLDatabase {

	public static BedspawnConnectionProvider con;

	static final String WRITE_GUIBLOCK = "INSERT INTO minecraft.guiblocks(object) VALUES (?)";
	static final String READ_GUIBLOCKS = "SELECT * FROM minecraft.guiblocks";
	static final String CLEAR_GUIBLOCKS = "TRUNCATE TABLE minecraft.guiblocks";
	static final String CREATE_GUIBLOCK = "CREATE TABLE IF NOT EXISTS minecraft.guiblocks (ID int NOT NULL AUTO_INCREMENT, object BLOB, primary key (ID))";
	
	static final String WRITE_MACHINE = "INSERT INTO minecraft.machines(object) VALUES (?)";
	static final String READ_MACHINES = "SELECT * FROM minecraft.machines";
	static final String CLEAR_MACHINES = "TRUNCATE TABLE minecraft.machines";
	static final String CREATE_MACHINE = "CREATE TABLE IF NOT EXISTS minecraft.machines (ID int NOT NULL AUTO_INCREMENT, object BLOB, primary key (ID))";
	
	public SQLDatabase() {
		
		con = new BedspawnConnectionProvider();
		createTables(con.getConnection());
		
	}


	public void createTables(Connection conn) {
		
		try {
			
			Statement s = conn.createStatement();
			s.executeUpdate(CREATE_GUIBLOCK);
			s.executeUpdate(CREATE_MACHINE);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}

	public static void writeGUIBlock(Connection conn, GUIBlock guiBlock) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(WRITE_GUIBLOCK);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		
		try {
			
			oos = new ObjectOutputStream(baos);
			oos.writeObject(new SerializableGUIBlock(guiBlock));
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		pstmt.setBinaryStream(1, new ByteArrayInputStream(baos.toByteArray()), baos.toByteArray().length);
		
		pstmt.executeUpdate();

	}

	public static ResultSet readGUIBlocks(Connection conn) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(READ_GUIBLOCKS);
		
		return pstmt.executeQuery();

	}
	
	public static void clearGUIBlocks(Connection conn) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(CLEAR_GUIBLOCKS);
		
		pstmt.execute();
		
	}
	
	public static void writeMachine(Connection conn, Machine machine) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(WRITE_MACHINE);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		
		try {
			
			oos = new ObjectOutputStream(baos);
			oos.writeObject(new SerializableMachine(machine));
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		pstmt.setBinaryStream(1, new ByteArrayInputStream(baos.toByteArray()), baos.toByteArray().length);
		
		pstmt.executeUpdate();

	}

	public static ResultSet readMachines(Connection conn) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(READ_MACHINES);
		
		return pstmt.executeQuery();

	}
	
	public static void clearMachines(Connection conn) throws Exception {
		
		PreparedStatement pstmt = conn.prepareStatement(CLEAR_MACHINES);
		
		pstmt.execute();
		
	}

}
