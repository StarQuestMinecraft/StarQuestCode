package com.starquestminecraft.sqtechbase.database;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.database.objects.SerializableGUIBlock;
import com.starquestminecraft.sqtechbase.database.objects.SerializableMachine;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.Network;
import com.starquestminecraft.sqtechbase.objects.PlayerOptions;

public class DatabaseInterface {
	
	public static void saveObjects() {
		
		SQTechBase.getPluginMain().reloadConfig();
		SQTechBase.config = SQTechBase.getPluginMain().getConfig();
		
		List<GUIBlock> guiBlocks = new ArrayList<GUIBlock>();
		
		for (Network network : SQTechBase.networks) {
			
			for (GUIBlock guiBlock : network.getGUIBlocks()) {
				
				guiBlocks.add(guiBlock);
				
			}
			
		}
		
		try {
			
			SQLDatabase.writeGUIBlocks(SQLDatabase.con.getConnection(), SQTechBase.config.getString("server name"), guiBlocks);
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
		try {
			
			SQLDatabase.writeMachines(SQLDatabase.con.getConnection(), SQTechBase.config.getString("server name"), SQTechBase.machines);
			
		} catch (Exception e) {

			e.printStackTrace();
			
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

						Network network = new Network(guiBlock.getLocation().getBlock(), false);
						
						for (int i = 0; i < network.getGUIBlocks().size(); i ++) {
							
							if (guiBlock.getLocation().equals(network.getGUIBlocks().get(i).getLocation())) {
								
								network.getGUIBlocks().set(i, guiBlock);
								
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
	
	public static void updateOptions(Player player) {
		
		PlayerOptions options;
		
		if (SQTechBase.currentOptions.containsKey(player.getUniqueId())) {
			
			options = SQTechBase.currentOptions.get(player.getUniqueId());
			
		} else {
			
			options = new PlayerOptions();
			SQTechBase.currentOptions.put(player.getUniqueId(), options);
			
		}

		try {
			
			SQLDatabase.updateOptions(SQLDatabase.con.getConnection(), player.getUniqueId(), options);
		
		} catch (Exception e) {
			
			e.printStackTrace();
		
		}
		
	}
	
	public static void readOptions(Player player) {
		
		try {
		
			ResultSet rs = SQLDatabase.readOption(SQLDatabase.con.getConnection(), player.getUniqueId());
			
			while (rs.next()) {
				
				try {
				
					byte[] bytes = (byte[]) rs.getObject("object");
					
					ByteArrayInputStream baip = new ByteArrayInputStream(bytes);
					
					ObjectInputStream ois = new ObjectInputStream(baip);
					
					PlayerOptions options = (PlayerOptions) ois.readObject();
					
					if (options != null) {
	
						if (SQTechBase.currentOptions.containsKey(player.getUniqueId())) {
							
							SQTechBase.currentOptions.replace(player.getUniqueId(), options);
							
						} else {
							
							SQTechBase.currentOptions.put(player.getUniqueId(), options);
							
						}
						
					}
					
					ois.close();
					baip.close();
					
				} catch (Exception e) {

					e.printStackTrace();
					
				}
	
			}
			
			rs.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
