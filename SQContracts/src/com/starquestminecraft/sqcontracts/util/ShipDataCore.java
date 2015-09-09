package com.starquestminecraft.sqcontracts.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
 


import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;

import net.countercraft.movecraft.Movecraft;
import net.countercraft.movecraft.database.StarshipData;
import net.countercraft.movecraft.utils.LocationUtils;

public class ShipDataCore {
	
	private static final String DATA_PENDING = ChatColor.RED + "Data Core Pending";
	private static final String LONG_OBFUSCATED = "dibujarondibujarondibujarondibujaron";
	public static void createShipDataCore(final Player p, final StarshipData d){
		ItemStack paper = new ItemStack(Material.PAPER, 1);
		ItemMeta meta = paper.getItemMeta();
		meta.setDisplayName(DATA_PENDING);
		paper.setItemMeta(meta);
		p.getInventory().addItem(paper);
		Bukkit.getServer().getScheduler().runTaskAsynchronously(SQContracts.get(), new Runnable(){
			public void run(){
				createShipDataCoreAsync(p, d);
			}
		});
	}
	public static void createShipDataCoreAsync(final Player p, final StarshipData d){
		System.out.println("Async call!");
		String type = d.getType();
		UUID pilot = d.getCaptain();
		String name = Bukkit.getOfflinePlayer(pilot).getName();
		int bnum = d.getBlockList().length;
		
		
		String displayName = (ChatColor.RESET + "" + ChatColor.AQUA + "Starship Data Core");
		ContractPlayerData data = SQContracts.get().getContractDatabase().getDataOfPlayer(pilot);
		boolean wanted = data.isWanted();
		final ArrayList<String> lore = new ArrayList<String>();
		if(!wanted && LocationUtils.getSystem().equals("Trinitos_Alpha")){
			displayName = (ChatColor.RESET + "" + ChatColor.RED + "Invalid Data Core");
			lore.add(ChatColor.RESET + "Cannot recieve non-wanted data cores in Alpha.");
		} else {
			lore.add(ChatColor.MAGIC + pilot.toString());
			lore.add(ChatColor.RESET + type);
			lore.add(ChatColor.RESET + "" + bnum + " blocks");
			lore.add(ChatColor.RESET + "Captain: " + name);
			
			if(data != null && wanted){
				lore.add(ChatColor.DARK_RED + "WANTED");
			}
		}
		
		lore.add(ChatColor.MAGIC + LONG_OBFUSCATED);
		final String displayName2 = displayName;
		System.out.println("Data Core: player " + p.getName() + " got a data core from " + name);
		Bukkit.getScheduler().runTask(Movecraft.getInstance(), new Runnable(){
			public void run(){
				createShipDataCoreCallback(p.getUniqueId(), displayName2, lore);
			}
		});
	}
	
	private static void createShipDataCoreCallback(UUID u, String name, ArrayList<String> lore){
		Player p = Bukkit.getPlayer(u);
		if(p == null) return;
		PlayerInventory i = p.getInventory();
		for(ItemStack item : i.getContents()){
			if(item == null) continue;
			ItemMeta m = item.getItemMeta();
			if(m == null) continue;
			String displayName = m.getDisplayName();
			if(displayName == null) continue;
			if(m.getDisplayName().equals(DATA_PENDING)){
				m.setDisplayName(name);
				m.setLore(lore);
				item.setItemMeta(m);
				p.updateInventory();
				return;
			}
		}
	}
	
	public static boolean isShipDataCore(ItemStack i, boolean isContractPiracy){
		//piracy contracts can only complete with NON WANTED contracts
		//privateering contracts can only complete with WANTED contracts
		if(i == null) return false;
		if(i.getType() != Material.PAPER) return false;
		ItemMeta m = i.getItemMeta();
		
		if(m.getDisplayName().equals(ChatColor.RESET + "" + ChatColor.AQUA + "Starship Data Core")){
			boolean wanted = isDataCoreWanted(m);
			if(isContractPiracy){
				//it's piracy
					//piracy contracts can only complete with non wanted contracts.
					return true;
				//if it's wanted and a piracy contract, return false.
			} else {
				//it's privateering
				if(wanted){
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	private static boolean isDataCoreWanted(ItemMeta meta){
		return meta.getLore().contains(ChatColor.DARK_RED + "WANTED");
	}

	private String type;
	private UUID pilot;
	private String pilotName;
	private int blocksLength;
	
	public ShipDataCore(ItemStack paper){
		ItemMeta meta = paper.getItemMeta();
		List<String> lore = meta.getLore();
		String uid = lore.get(0);
		pilot = UUID.fromString(uid.substring(2, uid.length()));
		String typeString = lore.get(1);
		type = typeString.substring(2, typeString.length());
		String blockString = lore.get(2);
		blocksLength = Integer.parseInt(blockString.substring(2, blockString.length() - 7));
		pilotName = lore.get(3);
	}

	public String getType() {
		return type;
	}

	public UUID getPilot() {
		return pilot;
	}

	public String getPilotName() {
		return pilotName;
	}

	public int getBlocksLength() {
		return blocksLength;
	}
}
