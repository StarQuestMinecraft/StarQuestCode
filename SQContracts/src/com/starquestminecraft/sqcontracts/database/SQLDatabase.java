package com.starquestminecraft.sqcontracts.database;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class SQLDatabase implements Database {

	ConnectionProvider con;

	static final String HAS_KEY_SQL = "SELECT * from contract_data WHERE `uuid` = ?";
	static final String UPDATE_OBJECT_SQL = "UPDATE contract_data SET object_value = ? WHERE `uuid` = ?";
	static final String WRITE_OBJECT_SQL = "INSERT INTO contract_data(uuid, object_value) VALUES (?, ?)";
	static final String READ_OBJECT_SQL = "SELECT object_value FROM contract_data WHERE `uuid`= ?";
	static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS contract_data ( uuid varchar(32), object_value BLOB, primary key (uuid))";
	static final String READ_ALL_SQL = "SELECT object_value from contract_data";
	public SQLDatabase() {
		con = new BedspawnConnectionProvider();
		createTable(con.getConnection());
	}

	@Override
	public ContractPlayerData getDataOfPlayer(UUID u) {
		try {
			if (hasKey(con.getConnection(), u)) {
				return readData(con.getConnection(), u);
			} else {
				return ContractPlayerData.createDefault(u);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updatePlayerData(UUID u, ContractPlayerData d) {
		try {
			if (hasKey(con.getConnection(), u)) {
				updateData(con.getConnection(), d);
			} else {
				writeData(con.getConnection(), d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createTable(Connection con) {
		try {
			Statement s = con.createStatement();
			s.executeUpdate(CREATE_TABLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String stripDashes(UUID u){
		return u.toString().replaceAll("-", "");
	}

	private boolean hasKey(Connection con, UUID u) throws Exception {
		PreparedStatement s = con.prepareStatement(HAS_KEY_SQL);
		s.setString(1, stripDashes(u));
		ResultSet rs = s.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	private void updateData(Connection conn, ContractPlayerData object) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_OBJECT_SQL);

		// set input parameters
		pstmt.setObject(1, object);
		pstmt.setString(2, stripDashes(object.getPlayer()));
		pstmt.executeUpdate();
		pstmt.close();
	}

	private void writeData(Connection conn, ContractPlayerData object) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(WRITE_OBJECT_SQL);

		// set input parameters
		pstmt.setString(1, stripDashes(object.getPlayer()));
		pstmt.setObject(2, object);
		pstmt.executeUpdate();
		pstmt.close();
	}

	private ContractPlayerData readData(Connection conn, UUID plr) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(READ_OBJECT_SQL);
		pstmt.setString(1, stripDashes(plr));
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		byte[] buffer = rs.getBytes(1);
		
		ObjectInputStream objectIn = null;
		if (buffer != null)
			objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));

		Object deSerializedObject = objectIn.readObject();
		ContractPlayerData retval = (ContractPlayerData) deSerializedObject;
		rs.close();
		pstmt.close();
		return retval;
	}

	@Override
	public ContractPlayerData[] getAllPlayerData(){
		try{
			return readAllPlayerData();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public ContractPlayerData[] readAllPlayerData() throws Exception{
		ArrayList<ContractPlayerData> all = new ArrayList<ContractPlayerData>();
		int i = 0;
		PreparedStatement pstmt = con.getConnection().prepareStatement(READ_ALL_SQL);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()){
			byte[] buffer = rs.getBytes(1);
			
			ObjectInputStream objectIn = null;
			if (buffer != null)
				objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
	
			Object deSerializedObject = objectIn.readObject();
			i++;
			ContractPlayerData retval = (ContractPlayerData) deSerializedObject;
			all.add(retval);
		}
		rs.close();
		pstmt.close();
		return all.toArray(new ContractPlayerData[0]);
	}
}
