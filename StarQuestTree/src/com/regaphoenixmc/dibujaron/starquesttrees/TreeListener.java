package com.regaphoenixmc.dibujaron.starquesttrees;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class TreeListener implements Listener{
	StarQuestTreesAndMobs plugin;
	TreeListener(StarQuestTreesAndMobs plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerBreak(BlockBreakEvent event){
		if(event.getPlayer().getWorld().getName().equals("Regalis")){
			if(event.getBlock().getType() == Material.LOG){
				RegionManager rm = WorldGuardPlugin.inst().getRegionManager(event.getBlock().getWorld());
				ApplicableRegionSet set = rm.getApplicableRegions(event.getBlock().getLocation());
				for(ProtectedRegion r : set){
					if(r.getId().startsWith("treefarm")){
						event.setCancelled(false);
						
					}
				}
				if(!event.isCancelled()){
					final Block b = event.getBlock().getRelative(BlockFace.DOWN);
					final Block sapling = event.getBlock();
					if(b.getType() == Material.DIRT){
						event.setCancelled(true);
						event.getPlayer().getInventory().addItem(event.getBlock().getDrops().toArray(new ItemStack[1]));
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
							public void run(){
								sapling.setType(Material.SAPLING);
								sapling.setData((byte) 2);
							}
						}, 1L);
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onTreeGrow(StructureGrowEvent event){
	
		//kelakarian flower trees
		if (event.getWorld().getName().equalsIgnoreCase("Rakuria")){
			if (Math.random() > 0.5){
				if (event.getSpecies() == TreeType.BIRCH){
					event.setCancelled(true);
					spawnTree(event.getLocation(), "Kelakaria-Birch", new Vector(-3, -1, -3));
					BlockSetTask t = new BlockSetTask(event.getLocation().getBlock(), 17, (byte) 2);
					t.runTaskLater(plugin, 5);
					return;
				}
				if (event.getSpecies() == TreeType.TREE){
					event.setCancelled(true);
					spawnTree(event.getLocation(), "Kelakaria-Oak", new Vector(-4, 0, -6));
					BlockSetTask t = new BlockSetTask(event.getLocation().getBlock(), 17, (byte) 0);
					t.runTaskLater(plugin, 5);
					return;
				}
				if (event.getSpecies() == TreeType.SMALL_JUNGLE){
					event.setCancelled(true);
					spawnTree(event.getLocation(), "Kelakaria-Jungle", new Vector(-2, 0, -2));
					BlockSetTask t = new BlockSetTask(event.getLocation().getBlock(), 17, (byte) 3);
					t.runTaskLater(plugin, 5);
					return;
				}
				if (event.getSpecies() == TreeType.REDWOOD){
					event.setCancelled(true);
					spawnTree(event.getLocation(), "Kelakaria-Spruce", new Vector(-3, -1, -3));
					BlockSetTask t = new BlockSetTask(event.getLocation().getBlock(), 17, (byte) 1);
					t.runTaskLater(plugin, 5);
					return;
				}
			}
			return;
		}
		if (event.getWorld().getName().equalsIgnoreCase("Grun")){
			if (event.getSpecies() == TreeType.TREE || event.getSpecies() == TreeType.BIG_TREE){
				event.setCancelled(true);
				spawnTree(event.getLocation(), "Quavara-Oak", new Vector(-4, 0, -4));
				BlockSetTask t = new BlockSetTask(event.getLocation().getBlock(), 17, (byte) 0);
				t.runTaskLater(plugin, 5);
			}
			event.setCancelled(true);
			return;
		}
		if (event.getWorld().getName().equalsIgnoreCase("Otavo")){
			if (event.getSpecies() == TreeType.TREE || event.getSpecies() == TreeType.BIG_TREE){
				if (Math.random() > 0.5){
					event.setCancelled(true);
					spawnTree(event.getLocation(), "Valadro-Oak", new Vector(-7, -3, -7));
					BlockSetTask t = new BlockSetTask(event.getLocation().getBlock(), 17, (byte) 0);
					t.runTaskLater(plugin, 5);
				}
			}
		}
		if (event.getWorld().getName().equalsIgnoreCase("Xylos")){
			if (event.getSpecies() == TreeType.REDWOOD){
					event.setCancelled(true);
					spawnTree(event.getLocation(), "Ceharram-Spruce", new Vector(-2, 0, -2));
					BlockSetTask t = new BlockSetTask(event.getLocation().getBlock(), 17, (byte) 1);
					t.runTaskLater(plugin, 5);
			}
		}
	}
	private void spawnTree(Location startBlock, String fileName, Vector offset){
		BukkitWorld world = new BukkitWorld(startBlock.getWorld());
		EditSession session = new EditSession(world, 1000);
		WorldEditPlugin wep = ((WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit"));
		WorldEdit we = wep.getWorldEdit();
		LocalConfiguration config = we.getConfiguration();
		BukkitPlayer p = wep.wrapPlayer(startBlock.getWorld().getPlayers().get(0));
		File dir = we.getWorkingDirectoryFile(config.saveDir);
        File f;
        Vector v = new Vector(startBlock.getX(), startBlock.getY(), startBlock.getZ());
        
		try {
			f = we.getSafeOpenFile(p, dir, fileName, "schematic", "schematic");
			CuboidClipboard cc = SchematicFormat.MCEDIT.load(f);
			cc.setOffset(offset);
			
			cc.paste(session, v, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(event.isCancelled()) return;
		if (event.getPlayer().getWorld().getName().equalsIgnoreCase("Rakuria")){
			if (event.getBlock().getType() == Material.WOOL){
				Block wool = event.getBlock();
				double chance = Math.random() * 100;
				if (chance < 10){
					if (wool.getData() == 14){
						wool.getWorld().dropItem(wool.getLocation(), new ItemStack(Material.SAPLING, 1, (short) 3));
						return;
					}
					if (wool.getData() == 5){
						wool.getWorld().dropItem(wool.getLocation(), new ItemStack(Material.SAPLING, 1, (short)  1));
						return;
					}
					if (wool.getData() == 11){
						wool.getWorld().dropItem(wool.getLocation(), new ItemStack(Material.SAPLING, 1, (short)  0));
						return;
					}
					if (wool.getData() == 4){
						wool.getWorld().dropItem(wool.getLocation(), new ItemStack(Material.SAPLING, 1, (short)  2));
						return;
					}
				}
			}
		}
		if (event.getPlayer().getWorld().getName().equalsIgnoreCase("Xylos")){
			if (event.getBlock().getType() == Material.GLOWSTONE){
				double chance = Math.random() * 100;
				if (chance < 5){
					event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.SAPLING, 1, (short) 1));
				}
			}
		}
		if (event.getPlayer().getWorld().getName().equalsIgnoreCase("Grun")){
			if (event.getBlock().getType() == Material.CACTUS){
				double chance = Math.random() * 100;
				if (chance < 20){
					event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.WOOL, 1, (short) 13));
				}
			}
		}
		if (event.getPlayer().getWorld().getName().equalsIgnoreCase("Loyavas")){
			if (event.getBlock().getType() == Material.LEAVES && (event.getBlock().getData() == 5 || event.getBlock().getData() == 13)) {
				double chance = Math.random() * 100;
				if (chance < 20){
					event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.WOOL, 1, (short) 7));
				}
			}
		}
		if (event.getPlayer().getWorld().getName().equalsIgnoreCase("Eratoss")){
			if (event.getBlock().getType() == Material.DIRT) {
				double chance = Math.random() * 100;
				if (chance < 5){
					event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.GRASS, 1));
				}
			}
		}
		if (event.getPlayer().getWorld().getName().equalsIgnoreCase("Cetallion")){
			if (event.getBlock().getTypeId() == 100){
				double chance = Math.random() * 100;
				if (chance < 25){
					event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.WOOL, 1, (short) 14));
				}
			}
			if (event.getBlock().getTypeId() == 99){
				double chance = Math.random() * 100;
				if (chance < 25){
					event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.WOOL, 1, (short) 12));
				}
			}
		}
	}
}
