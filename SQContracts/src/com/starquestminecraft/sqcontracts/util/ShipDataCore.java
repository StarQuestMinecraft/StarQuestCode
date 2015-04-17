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

import net.countercraft.movecraft.Movecraft;
import net.countercraft.movecraft.database.StarshipData;

public class ShipDataCore {
	
	private static final String LONG_OBFUSCATED = "dibujarondibujarondibujarondibujarondi";
	private static final String DATA_PENDING = ChatColor.RED + "Data Core Pending";
	public static void createShipDataCore(Player p, final StarshipData d){
		ItemStack paper = new ItemStack(Material.PAPER, 1);
		ItemMeta meta = paper.getItemMeta();
		meta.setDisplayName(DATA_PENDING);
		paper.setItemMeta(meta);
		p.getInventory().addItem(paper);
		Bukkit.getServer().getScheduler().runTaskAsynchronously(SQContracts.get(), new Runnable(){
			public void run(){
				createShipDataCoreAsync(d);
			}
		});
	}
	public static void createShipDataCoreAsync(final StarshipData d){
		System.out.println("Async call!");
		String type = d.getType();
		UUID pilot = d.getCaptain();
		String name = Bukkit.getOfflinePlayer(pilot).getName();
		int bnum = d.getBlockList().length;
		
		
		final String displayName = (ChatColor.RESET + "" + ChatColor.AQUA + "Starship Data Core");
		final ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.MAGIC + pilot.toString());
		lore.add(ChatColor.RESET + type);
		lore.add(ChatColor.RESET + "" + bnum + " blocks");
		lore.add(ChatColor.RESET + "Captain: " + name);
		
		boolean wanted = SQContracts.get().getContractDatabase().getDataOfPlayer(pilot).isWanted();
		if(wanted){
			lore.add(ChatColor.DARK_RED + "WANTED");
		}
		
		lore.add(ChatColor.MAGIC + LONG_OBFUSCATED);
		Bukkit.getScheduler().runTask(Movecraft.getInstance(), new Runnable(){
			public void run(){
				createShipDataCoreCallback(d.getCaptain(), displayName, lore);
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
			}
		}
	}
	
	public static boolean isShipDataCore(ItemStack i){
		if(i.getType() != Material.PAPER) return false;
		ItemMeta m = i.getItemMeta();
		return m.getDisplayName().equals(ChatColor.RESET + "" + ChatColor.AQUA + "Starship Data Core");
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
		type = lore.get(1);
		blocksLength = Integer.parseInt(lore.get(2));
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
