package com.starquestminecraft.sqrankup2.database;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import com.starquestminecraft.sqrankup2.SQRankup2;

public class SQLDatabase implements Database {

	ConnectionProvider con;

	static final String HAS_KEY_SQL = "SELECT * from rankup_data WHERE `uuid` = ?";
	static final String UPDATE_CERTS_SQL = "UPDATE rankup_data SET certs = ? WHERE `uuid` = ?";
	static final String WRITE_CERTS_SQL = "INSERT INTO rankup_data(uuid, certs) VALUES (?, ?)";
	static final String READ_CERTS_SQL = "SELECT certs FROM rankup_data WHERE `uuid`= ?";
	static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS rankup_data ( uuid VARCHAR(32), certs TEXT, primary key (uuid))";

	public SQLDatabase() {
		con = new BedspawnConnectionProvider();
		createTable(con.getConnection());
	}

	@Override
	public ArrayList<String> getCertsOfPlayer(UUID u) {
		ArrayList<String> retvals = new ArrayList<String>();
		retvals.addAll(SQRankup2.get().defaultCerts.keySet());
		try {
			if (hasKey(con.getConnection(), u)) {
				retvals.addAll(readData(con.getConnection(), u));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return retvals;
	}

	@Override
	public void updateCertsOfPlayer(UUID u, ArrayList<String> newCerts) {
		ListIterator<String> i = newCerts.listIterator();
		while(i.hasNext()){
			String s = i.next();
			if(SQRankup2.get().defaultCerts.get(s) != null){
				//it's a default
				i.remove();
			}
		}
		try {
			if (hasKey(con.getConnection(), u)) {
				updateData(con.getConnection(), u, newCerts);
			} else {
				writeData(con.getConnection(), u, newCerts);
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

	private void updateData(Connection conn, UUID player, ArrayList<String> certs) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_CERTS_SQL);

		// set input parameters
		pstmt.setString(1, toCSV(certs));
		pstmt.setString(2, stripDashes(player));
		pstmt.executeUpdate();
		pstmt.close();
	}

	private void writeData(Connection conn, UUID player, ArrayList<String> certs) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(WRITE_CERTS_SQL);

		// set input parameters
		pstmt.setString(1, stripDashes(player));
		pstmt.setString(2, toCSV(certs));
		pstmt.executeUpdate();
		pstmt.close();
	}

	private ArrayList<String> readData(Connection conn, UUID plr) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement(READ_CERTS_SQL);
		pstmt.setString(1, stripDashes(plr));
		ResultSet rs = pstmt.executeQuery();
		String s;
		if(rs.next()){
			s = rs.getString("certs");
		} else {
			s = null;
		}
		rs.close();
		pstmt.close();
		return fromCSV(s);
	}
	
	private ArrayList<String> fromCSV(String s){
		if(s == null) return new ArrayList<String>();
		String[] split = s.split(",");
		ArrayList<String> retval = new ArrayList<String>(split.length);
		for(String spl : split){
			retval.add(spl);
		}
		return retval;
	}
	
	private String toCSV(ArrayList<String> a){
		String r = "";
		if(a.size() == 0) return r;
		for(int i = 0; i < a.size() - 1; i++){
			r = r + a.get(i) + ",";
		}
		r = r + a.get(a.size() - 1);
		return r;
	}
}
