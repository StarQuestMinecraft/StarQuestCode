package com.ginger_walnut.sqsmoothcraft.gui;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ginger_walnut.sqsmoothcraft.SQSmoothCraft;
import com.ginger_walnut.sqsmoothcraft.enums.BlockType;
import com.ginger_walnut.sqsmoothcraft.gui.options.OptionGui;
import com.ginger_walnut.sqsmoothcraft.objects.Ship;
import com.ginger_walnut.sqsmoothcraft.objects.ShipBlock;
import com.ginger_walnut.sqsmoothcraft.objects.ShipLocation;
import com.ginger_walnut.sqsmoothcraft.tasks.ShipDetection;
import com.ginger_walnut.sqsmoothcraft.utils.ShipUtils;
import com.martinjonsson01.sqsmoothcraft.missile.MissileGUI;

public class MainGui extends Gui{
	
	public MainGui(Player player) {
		
		super(player);
		
	}
	
	@Override
	public void open() {
		
		Inventory inventory = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQSmoothCraft - Ship");
		
		ItemStack exit = new ItemStack(Material.WOOD_DOOR);
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Click this to exit the ship");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		exit = ShipUtils.createSpecialItem(exit, lore, "Exit Ship");
		
		ItemStack spawn = new ItemStack(Material.MONSTER_EGG);
		
		lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Click this to spawn a ship");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		spawn = ShipUtils.createSpecialItem(spawn, lore, "Spawn Ship");
		
		ItemStack detect = new ItemStack(Material.PISTON_BASE);
		
		lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Click this to detect a ship");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		detect = ShipUtils.createSpecialItem(detect, lore, "Detect Ship");
		
		ItemStack undetect = new ItemStack(Material.PISTON_STICKY_BASE);
		
		lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Click this to undetect a ship");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		undetect = ShipUtils.createSpecialItem(undetect, lore, "Undetect Ship");
		
		ItemStack options = new ItemStack(Material.REDSTONE);
		
		lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Click this to optimize your SmoothCraft");
		lore.add(ChatColor.DARK_PURPLE + "experience - currently disabled");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		options = ShipUtils.createSpecialItem(options, lore, "Options");
		
		inventory.setItem(8, options);
		
		ItemStack hsMissileList = new ItemStack(Material.FIREBALL);
		
		lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Click this to show a list of heat seeking missiles");
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		
		hsMissileList = ShipUtils.createSpecialItem(hsMissileList, lore, "Heat Seeking Missile List");
		
		inventory.setItem(26, hsMissileList);
		
		if (SQSmoothCraft.shipMap.containsKey(owner.getUniqueId())) {
			
			inventory.setItem(1, undetect);
			inventory.setItem(26, exit);
			
		} else {
			
			inventory.setItem(0, detect);
			
			if (owner.hasPermission("SQSmoothCraft.spawnShip")) {
				
				inventory.setItem(18, spawn);
				
			}
			
		}
		
		owner.openInventory(inventory);
		
		if (SQSmoothCraft.currentGui.containsKey(owner)) {
			
			SQSmoothCraft.currentGui.remove(owner);
			SQSmoothCraft.currentGui.put(owner,  this);
			
		} else {
			
			SQSmoothCraft.currentGui.put(owner,  this);
			
		}
		
	}
	
	@Override
	public void clicked(String itemName, int slot) {
		
		if (itemName.equals("Spawn Ship")) {
			
			List<ShipBlock> blockList = new ArrayList<ShipBlock>();
			
			Location location = owner.getLocation();
			location.setY(location.getY() - 1);
			
			ItemStack blueWool = new ItemStack (Material.WOOL);
			ItemStack whiteWool = new ItemStack (Material.WOOL);
			
			ItemStack dispenser = new ItemStack(Material.DISPENSER);
			
			ItemStack glass = new ItemStack (Material.GLASS);
			ItemStack seaLamp = new ItemStack(Material.SEA_LANTERN);
			
			ItemStack coal = new ItemStack(Material.PISTON_BASE);
			
			blueWool.setDurability((short) 11);
			whiteWool.setDurability((short) 0);
			
			SQSmoothCraft.nextShipLocation = location;
			SQSmoothCraft.nextShipYawCos = Math.cos(Math.toRadians(owner.getLocation().getYaw()));
			SQSmoothCraft.nextShipYawSin = Math.cos(Math.toRadians(owner.getLocation().getYaw()));
			SQSmoothCraft.nextShipPitchCos = Math.cos(Math.toRadians(owner.getLocation().getYaw()));
			SQSmoothCraft.nextShipPitchSin = Math.cos(Math.toRadians(owner.getLocation().getYaw()));
			

			blockList.add(new ShipBlock(location, new ShipLocation(0, 0, 0, null), whiteWool, BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 0, 1, blockList.get(0)), dispenser, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 0, -1, blockList.get(0)), whiteWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 0,-2, blockList.get(0)), whiteWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 0, 0, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 0, 1, blockList.get(0)), coal, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 0, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 0, 0, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 0, 1, blockList.get(0)), coal, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 0, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 1, 2, blockList.get(0)), glass, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 1, 1, blockList.get(0)), glass, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 1, 1, blockList.get(0)), glass, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 1, 0, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 1, 0, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 1, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 1, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-2, 1, 0, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(2, 1, 0, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 1, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 1, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-2, 1, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(2, 1, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(3, 1, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-3, 1, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 1, -2, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 1, -2, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 1, -1, blockList.get(0)), new ItemStack(Material.DROPPER), blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 1, -2, blockList.get(0)), seaLamp, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 2, 1, blockList.get(0)), glass, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 2, 0, blockList.get(0)), glass, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 2, 0, blockList.get(0)), glass, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 2, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 2, -1, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 2, -2, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 2, -2, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(1, 2, -3, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(-1, 2, -3, blockList.get(0)), blueWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 2, -2, blockList.get(0)), seaLamp, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 3, 0, blockList.get(0)), glass, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 3, -1, blockList.get(0)), whiteWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 3, -2, blockList.get(0)), whiteWool, blockList.get(0), BlockType.NORMAL));
			blockList.add(new ShipBlock(new ShipLocation(0, 3, -3, blockList.get(0)), whiteWool, blockList.get(0), BlockType.NORMAL));

			new Ship(blockList, blockList.get(0), owner, 1f, 6.0f, 0.05f, 100000, 0);
			
			this.close();
			
		} else if (itemName.equals("Detect Ship")) {
			
			Location location = owner.getLocation();
			
			location.add(0, -1, 0);
			
			ShipDetection.detectShip(location.getWorld().getBlockAt(location), owner);
			
			this.close();
			
		} else if (itemName.equals("Undetect Ship")) {
			
			if (SQSmoothCraft.shipMap.containsKey(owner.getUniqueId())) {
				
				Ship ship = SQSmoothCraft.shipMap.get(owner.getUniqueId());
				
				boolean succesful = ship.blockify(true);
				
				if (succesful) {
					
					ship.exit(true);
					
				}
				
			} else {
				
				owner.sendMessage(ChatColor.RED + "You must be in a ship to undetect");
				
			}
			
			this.close();
			
		} else if (itemName.equals("Exit Ship")) {
			
			if (SQSmoothCraft.shipMap.containsKey(owner.getUniqueId())) {
				
				Ship ship = SQSmoothCraft.shipMap.get(owner.getUniqueId());
				
				ship.exit(true);
				
			} else {
				
				owner.sendMessage(ChatColor.RED + "You must be in a ship to exit one");
				
			}
			
			this.close();
			
		} else if (itemName.equals("Options")) {
			
			OptionGui gui = new OptionGui(owner);
			gui.open();
			
		} else if (itemName.equals("Heat Seeking Missile List")){
			
			MissileGUI gui = new MissileGUI((Player) owner);
			gui.open();
			
		}else if (itemName.equals("EM Field Info")) {
			
			owner.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[EM Field] " + "Field strength currently at: " + SQSmoothCraft.shipMap.get(owner.getUniqueId()).shieldHealth);
			
		}
		
	}
	
}
