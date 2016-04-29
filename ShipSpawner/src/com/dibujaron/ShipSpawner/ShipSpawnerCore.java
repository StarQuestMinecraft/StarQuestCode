package com.dibujaron.ShipSpawner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.countercraft.movecraft.listener.InteractListener;
import net.milkbowl.vault.economy.Economy;

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
				if (s.getLine(0).equals("[shipspawner]") && event.getPlayer().hasPermission("ShipSpawner.create")) {
					String schematic = s.getLine(1);
					String priceStr = s.getLine(2);
					int price = Integer.parseInt(priceStr);
					s.setLine(0, ChatColor.AQUA + "Ship Spawner");
					s.setLine(1, "Color: " + ChatColor.DARK_RED + "Red");
					s.setLine(2, schematic);
					s.setLine(3, price + " " + economy.currencyNamePlural());
					s.update();
					return;
				}
				if (s.getLine(0).equals(ChatColor.AQUA + "Ship Spawner")) {
					BlockFace direction = DirectionUtils.getSignDirection(event.getClickedBlock());
					Location startBlock = s.getBlock().getRelative(direction, 2).getLocation();
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

					if (economy.getBalance(event.getPlayer().getName()) < price) {
						event.getPlayer().sendMessage("You do not have " + price + " " + economy.currencyNamePlural());
						return;
					}

					try {
						f = we.getSafeOpenFile(p, dir, fileName, "schematic", "schematic");
						CuboidClipboard cc = SchematicFormat.MCEDIT.load(f);

						int originX = startBlock.getBlockX();
						int originY = startBlock.getBlockY();
						int originZ = startBlock.getBlockZ();
						if(direction.equals(BlockFace.SOUTH)) {
							for (int X = 0; X < cc.getWidth(); X++) {
								for (int Y = 0; Y < cc.getHeight(); Y++) {
									for (int Z = 0; Z < cc.getLength(); Z++) {
										Location l = new Location(startBlock.getWorld(), originX + X, originY + Y, originZ + Z);
										Material type = l.getBlock().getType();
										if (!(type == Material.AIR || type == Material.SPONGE || type == Material.PISTON_MOVING_PIECE)) {
											event.getPlayer().sendMessage("Ship spawn area is obstructed. Try a different spawner, or hit the clear button.");
											return;
										}
									}
								}
							}
						}
						
						if(direction.equals(BlockFace.NORTH)) {
							for (int X = 0; X < cc.getWidth(); X++) {
								for (int Y = 0; Y < cc.getHeight(); Y++) {
									for (int Z = 0; Z < cc.getLength(); Z++) {
										Location l = new Location(startBlock.getWorld(), originX + X, originY + Y, originZ - Z);
										Material type = l.getBlock().getType();
										if (!(type == Material.AIR || type == Material.SPONGE || type == Material.PISTON_MOVING_PIECE)) {
											event.getPlayer().sendMessage("Ship spawn area is obstructed. Try a different spawner, or hit the clear button.");
											return;
										}
									}
								}
							}
						}
						
						if(direction.equals(BlockFace.EAST)) {
							for (int X = 0; X < cc.getWidth(); X++) {
								for (int Y = 0; Y < cc.getHeight(); Y++) {
									for (int Z = 0; Z < cc.getLength(); Z++) {
										Location l = new Location(startBlock.getWorld(), originX + X, originY + Y, originZ - Z);
										Material type = l.getBlock().getType();
										if (!(type == Material.AIR || type == Material.SPONGE || type == Material.PISTON_MOVING_PIECE)) {
											event.getPlayer().sendMessage("Ship spawn area is obstructed. Try a different spawner, or hit the clear button.");
											return;
										}
									}
								}
							}
						}
						
						if(direction.equals(BlockFace.WEST)) {
							for (int X = 0; X < cc.getWidth(); X++) {
								for (int Y = 0; Y < cc.getHeight(); Y++) {
									for (int Z = 0; Z < cc.getLength(); Z++) {
										Location l = new Location(startBlock.getWorld(), originX - X, originY + Y, originZ - Z);
										Material type = l.getBlock().getType();
										if (!(type == Material.AIR || type == Material.SPONGE || type == Material.PISTON_MOVING_PIECE)) {
											event.getPlayer().sendMessage("Ship spawn area is obstructed. Try a different spawner, or hit the clear button.");
											return;
										}
									}
								}
							}
						}
						
						cc.paste(session, v, true);
						byte data = parseWoolDataColor(s);
						for (int X = 0; X < cc.getWidth(); X++) {
							for (int Y = 0; Y < cc.getHeight(); Y++) {
								for (int Z = 0; Z < cc.getLength(); Z++) {
									Location l = new Location(startBlock.getWorld(), originX + X, originY + Y, originZ + Z);
									Material type = l.getBlock().getType();
									byte dataval = l.getBlock().getData();
									if (type == Material.WOOL && dataval == 14) {
										l.getBlock().setTypeIdAndData(l.getBlock().getTypeId(), data, false);
									} else if (type == Material.STAINED_CLAY) {
										l.getBlock().setType(Material.LADDER);
									} else if (type == Material.WALL_SIGN) {
										Sign sign = (Sign) l.getBlock().getState();
										System.out.println("Found sign: " + s.getLine(0));
										if (InteractListener.getCraftTypeFromString(s.getLine(0)) != null) {
											System.out.println("Found craft sign.");
											String name = event.getPlayer().getName();
											if (name.length() > 15) name = name.substring(0, 15);
											sign.setLine(1, name);
											sign.update();
										}
									}
								}
							}
						}
						event.getPlayer().sendMessage("Enjoy your new ship!");
						economy.withdrawPlayer(event.getPlayer().getName(), price);
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
					Sign spawner;
					if (right.getType() == Material.WALL_SIGN) {
						System.out.println("Found right side spawner");
						spawner = (Sign) right.getState();
					} else if (left.getType() == Material.WALL_SIGN) {
						System.out.println("Found left side spawner");
						spawner = (Sign) left.getState();
					} else {
						event.getPlayer().sendMessage("No spawner found to clear.");
						return;
					}
					
					clearSpawner(spawner, event.getPlayer());

				}
			}
		} else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign s = (Sign) event.getClickedBlock().getState();
				if (s.getLine(0).equals(ChatColor.AQUA + "Ship Spawner")) {
					if (s.getLine(1).equals("Color: " + ChatColor.DARK_RED + "Red")) {
						s.setLine(1, "Color: " + ChatColor.GOLD + "Orange");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.GOLD + "Orange")) {
						s.setLine(1, "Color: " + ChatColor.YELLOW + "Yellow");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.YELLOW + "Yellow")) {
						s.setLine(1, "Color: " + ChatColor.GREEN + "Lime");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.GREEN + "Lime")) {
						s.setLine(1, "Color: " + ChatColor.DARK_GREEN + "Green");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_GREEN + "Green")) {
						s.setLine(1, "Color: " + ChatColor.DARK_AQUA + "Cyan");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_AQUA + "Cyan")) {
						s.setLine(1, "Color: " + ChatColor.BLUE + "L. Blu");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.BLUE + "L. Blu")) {
						s.setLine(1, "Color: " + ChatColor.DARK_BLUE + "D. Blu");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_BLUE + "D. Blu")) {
						s.setLine(1, "Color: " + ChatColor.DARK_PURPLE + "Purple");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_PURPLE + "Purple")) {
						s.setLine(1, "Color: " + ChatColor.LIGHT_PURPLE + "Magent");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.LIGHT_PURPLE + "Magent")) {
						s.setLine(1, "Color: " + ChatColor.LIGHT_PURPLE + "Pink");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.LIGHT_PURPLE + "Pink")) {
						s.setLine(1, "Color: " + ChatColor.DARK_GRAY + "Brown");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_GRAY + "Brown")) {
						s.setLine(1, "Color: " + ChatColor.GRAY + "L. Gra");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.GRAY + "L. Gra")) {
						s.setLine(1, "Color: " + ChatColor.DARK_GRAY + "D. Gra");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_GRAY + "D. Gra")) {
						s.setLine(1, "Color: " + ChatColor.BLACK + "Black");
						s.update();
						event.setCancelled(true);
					} else if (s.getLine(1).equals("Color: " + ChatColor.BLACK + "Black")) {
						s.setLine(1, "Color: " + ChatColor.DARK_RED + "Red");
						s.update();
						event.setCancelled(true);
					}
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
			int originX = startBlock.getBlockX();
			int originY = startBlock.getBlockY();
			int originZ = startBlock.getBlockZ();
			for (int X = 0; X < LENGTH; X++) {
				for (int Y = 0; Y < HEIGHT; Y++) {
					for (int Z = 0; Z < WIDTH; Z++) {
						Location l = new Location(startBlock.getWorld(), originX + X, originY + Y, originZ + Z);
						l.getBlock().setType(Material.AIR);
					}
				}
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


	public byte parseWoolDataColor(Sign s) {
		if (s.getLine(1).equals("Color: " + ChatColor.DARK_RED + "Red")) {
			return 14;
		} else if (s.getLine(1).equals("Color: " + ChatColor.GOLD + "Orange")) {
			return 1;
		} else if (s.getLine(1).equals("Color: " + ChatColor.YELLOW + "Yellow")) {
			return 4;
		} else if (s.getLine(1).equals("Color: " + ChatColor.GREEN + "Lime")) {
			return 5;
		} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_GREEN + "Green")) {
			return 13;
		} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_AQUA + "Cyan")) {
			return 9;
		} else if (s.getLine(1).equals("Color: " + ChatColor.BLUE + "L. Blu")) {
			return 3;
		} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_BLUE + "D. Blu")) {
			return 11;
		} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_PURPLE + "Purple")) {
			return 10;
		} else if (s.getLine(1).equals("Color: " + ChatColor.LIGHT_PURPLE + "Magent")) {
			return 2;
		} else if (s.getLine(1).equals("Color: " + ChatColor.LIGHT_PURPLE + "Pink")) {
			return 6;
		} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_GRAY + "Brown")) {
			return 12;
		} else if (s.getLine(1).equals("Color: " + ChatColor.GRAY + "L. Gra")) {
			return 8;
		} else if (s.getLine(1).equals("Color: " + ChatColor.DARK_GRAY + "D. Gra")) {
			return 7;
		} else if (s.getLine(1).equals("Color: " + ChatColor.BLACK + "Black")) {
			return 15;
		}
		return 0;
	}
}
