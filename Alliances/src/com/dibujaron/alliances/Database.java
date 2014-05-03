package com.dibujaron.alliances;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class Database {
	public String player;
	public String server;
	public String world;
	public int x, y, z;
	public static String driverString = "com.mysql.jdbc.Driver";
	public static String hostname = "localhost";
	public static String port = "3306";
	public static String db_name = "towny";
	public static String username = "minecraft";
	public static String password = "R3b!rth!ng";
	public static Connection cntx = null;
	public static String dsn = ("jdbc:mysql://" + hostname + ":" + port + "/" + db_name);

	public static void setUp(){
		
		String Database_table = "CREATE TABLE IF NOT EXISTS Alliances_Alliances (" 
				+ "`name` VARCHAR(32),"
				+ "`lead-org` VARCHAR(32),"
				+ "`orgs` LONGTEXT," 
				+ "PRIMARY KEY (`name`)" 
				+ ")";
		
		String Database_table2 = "CREATE TABLE IF NOT EXISTS Alliances_Organizations (" 
				+ "`name` VARCHAR(32),"
				+ "`alliance` VARCHAR(32)"
				+ "`invite` VARCHAR(32),"
				+ "PRIMARY KEY (`name`)" 
				+ ")";
		
		String Database_table3 = "CREATE TABLE IF NOT EXISTS Alliances_Residents (" 
				+ "`name` VARCHAR(32),"
				+ "`organization` VARCHAR(32)," 
				+ "PRIMARY KEY (`name`)" 
				+ ")";
		
		getContext();
		try {
			Driver driver = (Driver) Class.forName(driverString).newInstance();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			System.out.println("[CCDBs] Driver error: " + e);
		}
		Statement s = null;
		try {
			s = cntx.createStatement();
			s.executeUpdate(Database_table);
			s.executeUpdate(Database_table2);
			s.executeUpdate(Database_table3);
			System.out.println("[CCDB] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[CCDB] Table Creation Error");
		} finally {
			close(s);
		}
	}
	public static void saveNewAlliance(String name, String capital){
		if(!getContext()) System.out.println("something is wrong!");
		PreparedStatement s = null;
		try {
			 s = cntx.prepareStatement("INSERT INTO Alliances_Alliances (`name`, `lead-org`, `orgs`) values (?,?)");
			s.setString(1, name);
			s.setString(2, capital);
			s.execute();
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
	}
	public static void deleteAlliance(String name){
		if(!getContext()) System.out.println("something is wrong!");
		PreparedStatement s = null;
		try {
			 s = cntx.prepareStatement("DELETE FROM Alliances_Alliance WHERE (`name`) = ?");
			s.setString(1, name);
			s.execute();
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
	}
	public static ArrayList<String> getOrganizations(String alliance){
		if(!getContext()) System.out.println("something is wrong!");
		ArrayList<String> returnArray = new ArrayList<String>();
		String unparsed = "";
		PreparedStatement s = null;
		try {
			
			 s = cntx.prepareStatement("SELECT `orgs` FROM Alliances_Alliance WHERE (`name`) = (?)");
			s.setString(1, alliance);
			ResultSet rs = s.execute();
			while(rs.next()) {
				unparsed = rs.getString("orgs");
			}
			returnArray = Arrays.asList(unparsed.split("\\s*,\\s*"));
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}

		return returnArray;
	}
	public static String getAllianceOfOrganization(String organization){
		if(!getContext()) System.out.println("something is wrong!");
		String org = "";
		PreparedStatement s = null;
		try {
			
			 s = cntx.prepareStatement("SELECT `orgs` FROM Alliances_Alliance WHERE (`name`) = (?)");
			s.setString(1, alliance);
			ResultSet rs = s.execute();
			while(rs.next()) {
				org = rs.getString("orgs");
			}
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		return org;
	}
	public static String getAllianceOfPlayer(String player){
		if(!getContext()) System.out.println("something is wrong!");
		String org = "";
		PreparedStatement s = null;
		try {
			
			 s = cntx.prepareStatement("SELECT `alliance` FROM Alliances_Residents WHERE (`name`) = (?)");
			s.setString(1, player);
			ResultSet rs = s.execute();
			while(rs.next()) {
				org = rs.getString("organization");
			}
			returnArray = Arrays.asList(unparsed.split("\\s*,\\s*"));
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		return org;
	}
	public static String getCapitolOfAlliance(String alliance){
		if(!getContext()) System.out.println("something is wrong!");
		String org = "";
		PreparedStatement s = null;
		try {
			
			 s = cntx.prepareStatement("SELECT `lead-org` FROM Alliances_Alliances WHERE (`name`) = (?)");
			s.setString(1, alliance);
			ResultSet rs = s.execute();
			while(rs.next()) {
				org = rs.getString("lead-org");
			}
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		return org;
	}
	public static void addAllianceInvite(String alliance, String invitedOrganization){
		if(!getContext()) System.out.println("something is wrong!");
		PreparedStatement s = null;
		try {
			 s = cntx.prepareStatement("UPDATE Alliances_Organization SET (`invite`) = (?) WHERE (name) = ?");
			s.setString(1, alliance);
			s.setString(2, invitedOrganization);
			s.execute();
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
	}
	public static void removeAllianceInvite(String invitedOrganization){
		if(!getContext()) System.out.println("something is wrong!");
		PreparedStatement s = null;
		try {
			 s = cntx.prepareStatement("UPDATE Alliances_Organization SET (`invite`) = ("") WHERE (name) = ?");
			s.setString(1, invitedOrganization);
			s.execute();
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
	}
	public static String getAllianceInvitedTo(String organization){
		if(!getContext()) System.out.println("something is wrong!");
		String org = "";
		PreparedStatement s = null;
		try {
			
			 s = cntx.prepareStatement("SELECT `invite` FROM Alliances_Organization WHERE (`name`) = (?)");
			s.setString(1, organization);
			ResultSet rs = s.execute();
			while(rs.next()) {
				org = rs.getString("invite");
			}
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		return org;
	}
	public static ArrayList<String> getAllAlliances(){
		if(!getContext()) System.out.println("something is wrong!");
		ArrayList<String> returnArray = new ArrayList<String>();
		String unparsed = "";
		PreparedStatement s = null;
		try {
			
			 s = cntx.prepareStatement("SELECT `name` FROM Alliances_Alliances");
			ResultSet rs = s.execute();
			while(rs.next()) {
				unparsed = rs.getString("name");
				returnArray.add(unparsed);
			}
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}

		return returnArray;
	}
	public static void addOrganization(String alliance, String organization){
		ArrayList<String> preEdits = getOrganizations(alliance);
		preEdits.add(organization);
		String csv = preEdits.toString().replace("[", "").replace("]", "")
	            .replace(", ", ",");
		if(!getContext()) System.out.println("something is wrong!");
		PreparedStatement s = null;
		try {
			 s = cntx.prepareStatement("UPDATE Alliances_Alliances SET (`orgs`) = (?)");
			s.setString(1, csv);
			s.execute();
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		
		try {
			 s = cntx.prepareStatement("INSERT INTO Alliances_Organizations VALUES (name, alliance)");
			s.setString(1, organization);
			s.setString(2, alliance);
			s.execute();
			s.close();
		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		} finally {
			close(s);
		}
		
		
	}
	public static void removeOrganization(String alliance, String organization){
		
	}
	public static void setPlayerAlliance(String alliance, String player){
		
	}
	public static void removePlayerAlliance(String alliance, String player){
		
	}

	public static int getNumOrganizationsInAlliance(String alliance){
		return getOrganizations().size();
	}
	
	public static boolean getContext() {
		
		try {
			if (cntx == null || cntx.isClosed() || !cntx.isValid(1)) {
				if (cntx != null && !cntx.isClosed()) {
					try {
						cntx.close();
					} catch (SQLException e) {
						System.out.print("Exception caught");
					}
					cntx = null;
				}
				if ((username.equalsIgnoreCase("")) && (password.equalsIgnoreCase(""))) {
					cntx = DriverManager.getConnection(dsn);
				} else
					cntx = DriverManager.getConnection(dsn, username, password);

				if (cntx == null || cntx.isClosed())
					return false;
			}

			return true;
		} catch (SQLException e) {
			System.out.print("Error could not Connect to db " + dsn + ": " + e.getMessage());
		}
		return false;
	}
	
	
	  private static void close(Statement s)
	  {
	    if (s == null) {
	      return;
	    }
	    try
	    {
	      s.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
}
