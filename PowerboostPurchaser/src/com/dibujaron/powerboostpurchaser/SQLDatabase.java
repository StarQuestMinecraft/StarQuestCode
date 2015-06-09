package com.dibujaron.powerboostpurchaser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.Faction;

public class SQLDatabase implements Database{

	ConnectionProvider con;
	
	static final String HAS_KEY_SQL = "SELECT * from powerboost_data WHERE id = ?";
	static final String UPDATE_BOOST_SQL = "UPDATE powerboost_data SET boost = ? WHERE id = ?";
	static final String INSERT_BOOST_SQL = "INSERT INTO powerboost_data(id, boost, taxes) VALUES (?, ?, ?)";
	static final String READ_BOOST_SQL = "SELECT `boost` FROM powerboost_data WHERE id = ?";
	static final String READ_TAXES_SQL = "SELECT `taxes` FROM powerboost_data WHERE id = ?";
	static final String UPDATE_TAXES_SQL = "UPDATE powerboost_data SET taxes = ? WHERE id = ?";
	static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS powerboost_data (id VARCHAR(32), boost INT(11), taxes INT(11), primary key (id))";
	
	public SQLDatabase(){
		con = new BedspawnConnectionProvider();
		createTable(con.getConnection());
	}
	

	@Override
	public FactionPowerboost getBoostOfFaction(Faction f) {
		return new FactionPowerboost(f, getBoost(f.getId()));
	}

	@Override
	public PersonalPowerboost getBoostOfPlayer(Player p) {
		return new PersonalPowerboost(p.getUniqueId(), getBoost(stripDashes(p.getUniqueId())));
	}

	@Override
	public void setBoostOfFaction(FactionPowerboost b) {
		setBoost(b.getFaction().getId(), b.getBoost());
	}

	@Override
	public void setBoostOfPlayer(PersonalPowerboost b) {
		setBoost(stripDashes(b.getPlayer()), b.getBoost());
	}
	
	@Override
	public int getTaxesOfFaction(Faction f) {
		String id = f.getId();
		try {
			if (hasKey(con.getConnection(), id)) {
				return readTaxes(con.getConnection(), id);
			} else {
				return 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


	@Override
	public void setTaxesOfFaction(Faction f, int taxes) {
		String id = f.getId();
		try {
			if (hasKey(con.getConnection(), id)) {
				updateTaxes(con.getConnection(), id, taxes);
			} else {
				writeNewData(con.getConnection(), id, 0, taxes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private void updateTaxes(Connection conn, String id, int taxes) throws Exception{
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_BOOST_SQL);

		// set input parameters
		pstmt.setInt(1, taxes);
		pstmt.setString(2, stripDashes(id));
		pstmt.executeUpdate();
		pstmt.close();
	}


	private int readTaxes(Connection conn, String id) throws Exception{
		PreparedStatement pstmt = conn.prepareStatement(READ_TAXES_SQL);
		pstmt.setString(1, stripDashes(id));
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		int retval = rs.getInt("taxes");
		rs.close();
		pstmt.close();
		return retval;
	}
	
	private void createTable(Connection con) {
		try {
			Statement s = con.createStatement();
			s.executeUpdate(CREATE_TABLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int getBoost(String id){
		try {
			if (hasKey(con.getConnection(), id)) {
				return readBoost(con.getConnection(), id);
			} else {
				return 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private int readBoost(Connection conn, String id) throws Exception{
		PreparedStatement pstmt = conn.prepareStatement(READ_BOOST_SQL);
		pstmt.setString(1, stripDashes(id));
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		int retval = rs.getInt("boost");
		rs.close();
		pstmt.close();
		return retval;
	}

	private boolean hasKey(Connection con, String id) throws Exception{
		PreparedStatement s = con.prepareStatement(HAS_KEY_SQL);
		s.setString(1, stripDashes(id));
		ResultSet rs = s.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}
	
	private void setBoost(String id, int boost) {
		try {
			if (hasKey(con.getConnection(), id)) {
				updateBoost(con.getConnection(), id, boost);
			} else {
				writeNewData(con.getConnection(), id, boost, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeNewData(Connection conn, String id, int boost, int taxes) throws Exception{
		PreparedStatement pstmt = conn.prepareStatement(INSERT_BOOST_SQL);

		// set input parameters
		pstmt.setString(1, stripDashes(id));
		pstmt.setInt(2, boost);
		pstmt.setInt(3,  taxes);
		pstmt.executeUpdate();
		pstmt.close();
	}

	private void updateBoost(Connection conn, String id, int boost) throws Exception{
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_BOOST_SQL);
		// set input parameters
		pstmt.setInt(1, boost);
		pstmt.setString(2, stripDashes(id));
		pstmt.executeUpdate();
		pstmt.close();
	}



	private String stripDashes(UUID u){
		return stripDashes(u.toString());
	}
	
	private String stripDashes(String s){
		return s.replaceAll("-", "");
	}
}
