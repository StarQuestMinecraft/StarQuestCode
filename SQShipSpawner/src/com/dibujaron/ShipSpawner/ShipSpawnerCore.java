package com.dibujaron.ShipSpawner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.countercraft.movecraft.listener.InteractListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Ladder;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.WorldEdit;

public class ShipSpawnerCore extends JavaPlugin implements Listener {
	public static Economy economy = null;
	static int PRICE, LENGTH, WIDTH, HEIGHT;
	static String SCHEMATIC;
	static final int SPAWNER_TIMEOUT = 5 * 60 * 1000;
	public static HashMap<Location, Long> data;
	static long STARTUP_TIME;
	public void onEnable() {
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		setupEconomy();
		PRICE = getConfig().getInt("price");
		WIDTH = getConfig().getInt("width");
		LENGTH = getConfig().getInt("length");
		HEIGHT = getConfig().getInt("height");
		SCHEMATIC = getConfig().getString("schematic");
		data = new HashMap<Location, Long>();
		STARTUP_TIME = System.currentTimeMillis();
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign s = (Sign) event.getClickedBlock().getState();
				if (s.getLine(0).equals("[shipspawner]")) {
					String schematic = s.getLine(1);
					String priceStr = s.getLine(2);
					int price = Integer.parseInt(priceStr);
					s.setLine(0, ChatColor.AQUA + "Ship Spawner");
					s.setLine(1, "-*+*-");
					s.setLine(2, schematic);
					s.setLine(3, price + " " + economy.currencyNamePlural());
					s.update();
					return;
				}
				if (s.getLine(0).equals(ChatColor.AQUA + "Ship Spawner")) {
					BlockFace direction = DirectionUtils.getSignDirection(event.getClickedBlock());
					final Location startBlock = s.getBlock().getRelative(direction, 2).getLocation();
					BukkitWorld world = new BukkitWorld(startBlock.getWorld());
					EditSession session = new EditSession(world, 1000);
					WorldEditPlugin wep = ((WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit"));
					WorldEdit we = wep.getWorldEdit();
					LocalConfiguration config = we.getConfiguration();
					LocalPlayer p = wep.wrapPlayer(event.getPlayer());
					String fileName = getSchematicName(s);
					int price = getPrice(s);
					File dir = we.getWorkingDirectoryFile(config.saveDir);
					File f;
					Vector v = new Vector(startBlock.getX(), startBlock.getY(), startBlock.getZ());

					if (economy.getBalance(event.getPlayer()) < price) {
						event.getPlayer().sendMessage("You do not have " + price + " " + economy.currencyNamePlural());
						return;
					}

					try {
						f = we.getSafeOpenFile(p, dir, fileName, "schematic", "schematic");
						CuboidClipboard cc = SchematicFormat.MCEDIT.load(f);
						int originX = startBlock.getBlockX();
						int originY = startBlock.getBlockY();
						int originZ = startBlock.getBlockZ();
						
						Vector v2 = generateFarPointVector(cc, startBlock, direction);
						
						final CuboidSelection sr = new CuboidSelection(startBlock.getWorld(), v, v2);
						final Location minPoint = sr.getMinimumPoint();
						for (int X = 0; X < sr.getWidth(); X++) {
							for (int Y = 0; Y < sr.getHeight(); Y++) {
								for (int Z = 0; Z < sr.getLength(); Z++) {
									Location l = new Location(startBlock.getWorld(), minPoint.getX() + X, minPoint.getY() + Y, minPoint.getZ() + Z);
									Material type = l.getBlock().getType();
									if (!(type == Material.AIR || type == Material.SPONGE || type == Material.PISTON_MOVING_PIECE)) {
										event.getPlayer().sendMessage("Ship spawn area is obstructed. Try a different spawner, or hit the clear button.");
										return;
									}
									
								}
							}
						}
						
						Vector mp = new Vector(s.getBlock().getX(), s.getBlock().getY(), s.getBlock().getZ());
						cc.paste(session, mp, true);
						for (int X = 0; X < sr.getWidth(); X++) {
							for (int Y = 0; Y < sr.getHeight(); Y++) {
								for (int Z = 0; Z < sr.getLength(); Z++) {
									Location l = new Location(startBlock.getWorld(), minPoint.getX() + X, minPoint.getY() + Y, minPoint.getZ() + Z);
									Block b = l.getBlock();
									Material type = b.getType();
									if (type == Material.STONE && b.getData() != 0) {
										System.out.println("Found stone!");
										byte stoneData = b.getData();

										int toSet = s.getBlock().getData();
										if (stoneData == 1){
											int sd = toSet;
											if(sd == 4){
												toSet = 5;
											}
											else if(sd == 5){
												toSet = 4;
											}
											else if(sd == 2){
												toSet = 3;
											}
											else if(sd == 3){
												toSet = 2;
											}
										}
										b.setTypeIdAndData(68, (byte) toSet, false);
										Sign ss = (Sign) b.getState();
										if(stoneData == 1){
											ss.setLine(0, "Warship");
											String name = event.getPlayer().getName();
											if (name.length() > 15) name = name.substring(0, 15);
											ss.setLine(1, name);
											ss.update();
										} else if(stoneData == 3){
											ss.setLine( 0, "\\  ||  /" );
											ss.setLine( 1, "==      ==" );
											ss.setLine( 2, "/  ||  \\" );
											ss.update();
										} else if(stoneData == 5){
											ss.setLine(0, ChatColor.BLUE + "AUTOPILOT");
											ss.setLine(1, ChatColor.GREEN + "{DISABLED}");
											ss.update();
										}
									}
								}
							}
						}
						event.getPlayer().sendMessage("Enjoy your new ship!");
						economy.withdrawPlayer(event.getPlayer(), price);
						event.getPlayer().sendMessage(price + " " + economy.currencyNamePlural() + " have been withdrawn from your account.");
						event.getPlayer().getWorld().playSound(startBlock, Sound.BLOCK_PISTON_EXTEND, 2.0F, 1.0F);
						ShipSpawnerCore.data.put(s.getLocation(), System.currentTimeMillis());
						event.getPlayer().sendMessage("Be sure to move your ship away from the spawner quickly! After" + ChatColor.RED + " five minutes " + ChatColor.WHITE + "your ship can be deleted!");

					} catch (Exception e) {
						event.getPlayer().sendMessage("FAILED: try/catch error");
						e.printStackTrace();
					}
					return;
				}
				if (s.getLine(0).equals("[spawnerclear]") && event.getPlayer().hasPermission("ShipSpawner.create")) {
					s.setLine(0, ChatColor.RED + "Clear Spawner");
					s.setLine(1, "click to clear");
					s.setLine(2, "clear abandoned");
					s.setLine(3, "ships");
					s.update();
					return;
				}
				if (s.getLine(0).equals(ChatColor.RED + "Clear Spawner")) {
					Block left = s.getBlock().getRelative(BlockFace.EAST);
					Block right = s.getBlock().getRelative(BlockFace.WEST);
					Block forward = s.getBlock().getRelative(BlockFace.NORTH);
					Block backward = s.getBlock().getRelative(BlockFace.SOUTH);
					Sign spawner;
					if (right.getType() == Material.WALL_SIGN) {
						System.out.println("Found right side spawner");
						spawner = (Sign) right.getState();
					} else if (left.getType() == Material.WALL_SIGN) {
						System.out.println("Found left side spawner");
						spawner = (Sign) left.getState();
					} else if (forward.getType() == Material.WALL_SIGN) {
						System.out.println("Found forward side spawner");
						spawner = (Sign) forward.getState();
					} else if (backward.getType() == Material.WALL_SIGN) {
						System.out.println("Found backward side spawner");
						spawner = (Sign) backward.getState();
					} else {
						event.getPlayer().sendMessage("No spawner found to clear.");
						return;
					}
					
					clearSpawner(spawner, event.getPlayer());

				}
			}
		}
	}

	private int getPrice(Sign s) {
		String priceline = s.getLine(3);
		String cname = economy.currencyNamePlural();
		String price = priceline.substring(0, priceline.length() - cname.length());
		return Integer.parseInt(price.trim());
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
	
	private void clearSpawner(Sign spawner, Player clicker){
		Long timestamp = data.get(spawner.getLocation());
		System.out.println("timestamp: " + timestamp);
		if(timestamp == null){
			timestamp = STARTUP_TIME + 180000;
		}
		if((System.currentTimeMillis() - timestamp) > SPAWNER_TIMEOUT){
			BlockFace direction = DirectionUtils.getSignDirection(spawner.getBlock());
			Location startBlock = spawner.getBlock().getRelative(direction, 2).getLocation();
			BukkitWorld world = new BukkitWorld(startBlock.getWorld());
			EditSession session = new EditSession(world, 1000);
			WorldEditPlugin wep = ((WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit"));
			WorldEdit we = wep.getWorldEdit();
			LocalConfiguration config = we.getConfiguration();
			LocalPlayer p = wep.wrapPlayer(clicker);
			String fileName = getSchematicName(spawner);
			//int price = getPrice(spawner);
			File dir = we.getWorkingDirectoryFile(config.saveDir);
			File f;
			Vector v = new Vector(startBlock.getX(), startBlock.getY(), startBlock.getZ());

			try {
				f = we.getSafeOpenFile(p, dir, fileName, "schematic", "schematic");
				CuboidClipboard cc = SchematicFormat.MCEDIT.load(f);
				int originX = startBlock.getBlockX();
				int originY = startBlock.getBlockY();
				int originZ = startBlock.getBlockZ();
				
				Vector v2 = generateFarPointVector(cc, startBlock, direction);
				
				CuboidSelection sr = new CuboidSelection(startBlock.getWorld(), v, v2);
				Location minPoint = sr.getMinimumPoint();
				for (int X = 0; X < sr.getWidth(); X++) {
					for (int Y = 0; Y < sr.getHeight(); Y++) {
						for (int Z = 0; Z < sr.getLength(); Z++) {
							Location l = new Location(startBlock.getWorld(), minPoint.getX() + X, minPoint.getY() + Y, minPoint.getZ() + Z);
							l.getBlock().setType(Material.AIR);
						}
					}
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			clicker.sendMessage("Spawner Cleared!");
		} else {
			clicker.sendMessage("This spawner cannot be cleared yet. It has been used in the last two minutes.");
			long time = (SPAWNER_TIMEOUT - (System.currentTimeMillis() - timestamp)) / 1000;
			clicker.sendMessage("It can be cleared in " + time + " seconds.");
		}
	}

	private String getSchematicName(Sign s) {
		if(s.getLine(2).equals("Price:")){
			return SCHEMATIC;
		} else {
			return s.getLine(2);
		}
	}
	
	private Vector generateFarPointVector(CuboidClipboard cc, Location o, BlockFace direction){
		if (direction.equals(BlockFace.NORTH)) {
			double x = cc.getWidth() - 1;
			double z = (cc.getLength() - 1) * -1;
			double y = cc.getHeight() - 1;
			return new Vector(x+o.getX(),y+o.getY(),z+o.getZ());
		} else if (direction.equals(BlockFace.SOUTH)) {
			double x = (cc.getWidth() - 1) * -1;
			double z = cc.getLength() - 1;
			double y = cc.getHeight() - 1;
			return new Vector(x+o.getX(),y+o.getY(),z+o.getZ());
		} else if (direction.equals(BlockFace.WEST)) {
			double x = (cc.getWidth() - 1) * -1;
			double z = (cc.getLength() - 1) * -1;
			double y = cc.getHeight() - 1;
			return new Vector(x+o.getX(),y+o.getY(),z+o.getZ());		
		} else {
			double x = cc.getWidth() - 1;
			double z = cc.getLength() - 1;
			double y = cc.getHeight() - 1;
			return new Vector(x+o.getX(),y+o.getY(),z+o.getZ());		
		}
	}	
}