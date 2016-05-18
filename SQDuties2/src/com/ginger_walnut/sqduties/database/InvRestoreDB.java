package com.ginger_walnut.sqduties.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dibujaron.cardboardbox.Knapsack;

public class InvRestoreDB {
	
	public static BedspawnConnectionProvider con;
	
	public InvRestoreDB() {
		con = new BedspawnConnectionProvider();	
		String Database_table = "CREATE TABLE IF NOT EXISTS InventoryRestore ("
				+ "`name` VARCHAR(32) NOT NULL," + "`data` BLOB," 
				+ "`time` DATETIME,"
				+ ")";
		Statement s = null;

		try {
			s = con.getConnection().createStatement();
			s.executeUpdate(Database_table);
			System.out.println("[SQDatabase] Table check/creation sucessful");
		} catch (SQLException ee) {
			System.out.println("[SQDatabase] Table Creation Error");
		}

	}

	public Knapsack getKnapsack(String name, String datetime) {
		PreparedStatement s = null;
		try {
			s = con.getConnection().prepareStatement("SELECT `data` FROM InventoryRestore WHERE `name` = ? && `time` = ?");
			s.setString(1, name);
			s.setString(2, datetime);
			ResultSet rs = s.executeQuery();
			byte[] unparsedPerk = null;
			while (rs.next()) {
				unparsedPerk = (byte[]) rs.getObject("data");
				
			}
			
			ByteArrayInputStream baip = new ByteArrayInputStream(unparsedPerk);
			ObjectInputStream ois = new ObjectInputStream(baip);
			s.close();
			
			return (Knapsack) ois.readObject();

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		}
		return null;

	}
	
	public void trimRow(final String datetime) {
        Runnable task = new Runnable() {

            public void run() {
                try {
            		PreparedStatement s = null;
            		try {
            			s = con.getConnection().prepareStatement("DELETE FROM InventoryRestore WHERE `time` = ?");
            			s.setString(1, datetime);
            			s.execute();
            			s.close();

            		} catch (SQLException e) {
            			System.out.print("[CCDB] SQL Error" + e.getMessage());
            		} catch (Exception e) {
            			System.out.print("[CCDB] SQL Error (Unknown)");
            			e.printStackTrace();
            		}
                } catch (Exception ex) {
                    //handle error which cannot be thrown back
                }
            }
        };
        new Thread(task, "ServiceThread").start(); 



	}
	
	public ArrayList<String> getDeaths(String name) {
		System.out.println("GETTING DEATHS");
		ArrayList<String> returnArray = new ArrayList<String>();
		PreparedStatement s = null;
		try {
			System.out.println("TRYING");
			s = con.getConnection().prepareStatement("SELECT * FROM InventoryRestore WHERE `name` = ? ORDER BY InventoryRestore.time DESC");
			s.setString(1, name);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				System.out.println("IM THE NEXT");
				String entry = "Name: " + rs.getString("name") + " Time: " + rs.getString("time");
				returnArray.add(entry);
			}
			s.close();
			return returnArray;

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		}
		System.out.println("RETUNING NULL");
		return null;

	}
	
	public static String getDateIndex(String name, int index) {
		int counter = 1;
		PreparedStatement s = null;
		try {
			s = con.getConnection().prepareStatement("SELECT `time` FROM InventoryRestore WHERE `name` = ? ORDER BY InventoryRestore.time DESC");
			s.setString(1, name);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				if (counter == index) {
					System.out.println(counter + ": RETURNING");
					return rs.getString("time");
				} else {
					System.out.println("Incrementing: " + counter);
					counter++;
				}
			}
			s.close();

		} catch (SQLException e) {
			System.out.print("[CCDB] SQL Error" + e.getMessage());
		} catch (Exception e) {
			System.out.print("[CCDB] SQL Error (Unknown)");
			e.printStackTrace();
		}
		return null;

	}

	public void newKey(final String name, final Knapsack knapsack) {
   		PreparedStatement s = null;
   		
        try {
            			
          byte[] knapsackBytes = convertToBytes(knapsack);
            			
          s = con.getConnection().prepareStatement("INSERT INTO InventoryRestore VALUES (?,?,NOW())");
          s.setString(1, name);
          s.setBinaryStream(2, convertToBinary(knapsackBytes), knapsackBytes.length);
          s.executeUpdate();
          s.close();

        } catch (Exception ex) {
        	
        	ex.printStackTrace();
        	
        }

	}

	public void trimKey(final String name) {
		  Runnable task = new Runnable() {
	            public void run() {
	                try {
	                	System.out.println("Trimming");
	            		int counter = 1;
	            		PreparedStatement s = null;
	            		try {
	            			s = con.getConnection().prepareStatement("SELECT * FROM InventoryRestore WHERE `name` = ? ORDER BY InventoryRestore.time DESC");
	            			s.setString(1, name);
	            			ResultSet rs = s.executeQuery();
	            			while (rs.next()) {
	            				if (counter <= 5) {
	            					System.out.println(counter);
	            				} else {
	            					System.out.println("deleting row");
	            					trimRow(rs.getString("time"));
	            					
	            				}
	            				counter++;
	            			}
	            			s.close();

	            		} catch (SQLException e) {
	            			System.out.print("[CCDB] SQL Error" + e.getMessage());
	            		} catch (Exception e) {
	            			System.out.print("[CCDB] SQL Error (Unknown)");
	            			e.printStackTrace();
	            		}
	                } catch (Exception ex) {
	                    //handle error which cannot be thrown back
	                }
	            }
	        };
	        new Thread(task, "ServiceThread").start(); 
		
	}
	
	private byte[] convertToBytes(Knapsack k) {

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

	private ByteArrayInputStream convertToBinary(byte[] bytes) {

		return new ByteArrayInputStream(bytes);
		
	}
	
}
