package com.starquestminecraft.sqtechbase.database;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;

import com.starquestminecraft.sqtechbase.GUIBlock;
import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.Network;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.database.objects.SerializableGUIBlock;
import com.starquestminecraft.sqtechbase.database.objects.SerializableMachine;

public class DatabaseInterface {
	
	public static void saveObjects() {
		
		for (Network network : SQTechBase.networks) {
			
			for (GUIBlock guiBlock : network.getGUIBlocks()) {
				
				try {
					
					SQLDatabase.writeGUIBlock(SQLDatabase.con.getConnection(), SQTechBase.config.getString("server name"), guiBlock);
					
				} catch (Exception e) {

					e.printStackTrace();
					
				}
				
			}
			
		}
		
		for (Machine machine : SQTechBase.machines) {
			
			try {
				
				SQLDatabase.writeMachine(SQLDatabase.con.getConnection(), SQTechBase.config.getString("server name"), machine);
				
			} catch (Exception e) {

				e.printStackTrace();
				
			}
			
		}
		
	}
	
	public static void readObjects() {
		
		try {
			
			ResultSet rs = SQLDatabase.readGUIBlocks(SQLDatabase.con.getConnection(), SQTechBase.config.getString("server name"));
			
			while (rs.next()) {
				
				try {
				
					byte[] bytes = (byte[]) rs.getObject("object");
					
					ByteArrayInputStream baip = new ByteArrayInputStream(bytes);
					ObjectInputStream ois = new ObjectInputStream(baip);
					
					GUIBlock guiBlock = ((SerializableGUIBlock) ois.readObject()).getGUIBlock();
					
					if (guiBlock != null) {

						Network network = new Network(guiBlock.getLocation().getBlock());
						
						for (GUIBlock networkGUIBlock : network.getGUIBlocks()) {
							
							if (guiBlock.getLocation().equals(networkGUIBlock.getLocation())) {
								
								networkGUIBlock.setExports(guiBlock.getExports());
								networkGUIBlock.setImports(guiBlock.getImports());
								
							}
							
						}
						
					}
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}

			}
			
			SQLDatabase.clearGUIBlocks(SQLDatabase.con.getConnection(), SQTechBase.config.getString("server name"));
			
			rs.close();
			
			ResultSet rs2 = SQLDatabase.readMachines(SQLDatabase.con.getConnection(), SQTechBase.config.getString("server name"));
			
			while (rs2.next()) {
				
				try {
					
					byte[] bytes = (byte[]) rs2.getObject("object");
					
					ByteArrayInputStream baip = new ByteArrayInputStream(bytes);
					ObjectInputStream ois = new ObjectInputStream(baip);
					
					Machine machine = ((SerializableMachine) ois.readObject()).getMachine();
					
					if (machine != null)  {
						
						SQTechBase.machines.add(machine);
						
					}
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}

			}
			
			SQLDatabase.clearMachines(SQLDatabase.con.getConnection(), SQTechBase.config.getString("server name"));
			
			rs2.close();
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
	}
	
}
