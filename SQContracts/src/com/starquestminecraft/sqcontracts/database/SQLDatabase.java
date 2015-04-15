package com.starquestminecraft.sqcontracts.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class SQLDatabase implements Database {

	ConnectionProvider con;

	static final String HAS_KEY_SQL = "SELECT * from contract_data WHERE `uuid` = ?";
	static final String UPDATE_OBJECT_SQL = "UPDATE contract_data SET object_value = ? WHERE `uuid` = ?";
	static final String WRITE_OBJECT_SQL = "INSERT INTO contract_data(uuid, object_value) VALUES (?, ?)";
	static final String READ_OBJECT_SQL = "SELECT object_value FROM contract_data WHERE `uuid`= ?";
	static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS java_objects ( uuid varchar(32), object_value BLOB, primary key (uuid))";

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

	private boolean hasKey(Connection con, UUID u) throws Exception {
		PreparedStatement s = con.prepareStatement(HAS_KEY_SQL);
		s.setString(1, u.toString());
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
		pstmt.setString(2, object.getPlayer().toString());
		pstmt.executeUpdate();
		pstmt.close();
	}

	private void writeData(Connection conn, ContractPlayerData object) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(WRITE_OBJECT_SQL);

		// set input parameters
		pstmt.setString(1, object.getPlayer().toString());
		pstmt.setObject(2, object);
		pstmt.executeUpdate();
		pstmt.close();
	}

	private ContractPlayerData readData(Connection conn, UUID plr) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(READ_OBJECT_SQL);
		pstmt.setString(1, plr.toString());
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Object object = rs.getObject(1);
		ContractPlayerData retval = (ContractPlayerData) object;
		rs.close();
		pstmt.close();
		return retval;
	}
}
