package com.starquestminecraft.sqasteroidbays;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalConfiguration;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

public class SQAsteroidBayTask extends BukkitRunnable {

	private final SQAsteroidBays plugin;
	
	public SQAsteroidBayTask(SQAsteroidBays plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void run() {		
		
		BukkitTask asteroidBaysTask = new SQAsteroidBayTask(this.plugin).runTaskLater(plugin, 12000);
		
		
		WorldEditPlugin wep = ((WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit"));
		WorldEdit we = wep.getWorldEdit();
		
		LocalConfiguration config = we.getConfiguration();
		File dir = we.getWorkingDirectoryFile(config.saveDir);
		
		//File f = we.getSafeOpenFile(player, dir, "Asteroid1", "schematic", "schematic");
		
		Integer schematicNumber = 1;
		
		Double randomNumber = Math.random();
		
		List<Integer> availableNumbers = plugin.asteroidBaysInstance.availableNumbers;
		
		if(randomNumber <= 0.25) {
			
			schematicNumber = availableNumbers.get(0);
			availableNumbers.remove(0);
			availableNumbers.add(plugin.asteroidBaysInstance.lastSchematic);
			plugin.asteroidBaysInstance.lastSchematic = schematicNumber;
			
		}
		else if(randomNumber <= 0.5) {
			
			schematicNumber = availableNumbers.get(1);
			availableNumbers.remove(1);
			availableNumbers.add(plugin.asteroidBaysInstance.lastSchematic);
			plugin.asteroidBaysInstance.lastSchematic = schematicNumber;
			
		}
		else if(randomNumber <= 0.75) {
			
			schematicNumber = availableNumbers.get(2);
			availableNumbers.remove(2);
			availableNumbers.add(plugin.asteroidBaysInstance.lastSchematic);
			plugin.asteroidBaysInstance.lastSchematic = schematicNumber;
			
		}
		else {
			
			schematicNumber = availableNumbers.get(3);
			availableNumbers.remove(3);
			availableNumbers.add(plugin.asteroidBaysInstance.lastSchematic);
			plugin.asteroidBaysInstance.lastSchematic = schematicNumber;
		
		}
		
		File f = new File("plugins/WorldEdit/schematics/Asteroids" + schematicNumber + ".schematic");
		
		CuboidClipboard cc = null;
		try {
			cc = SchematicFormat.MCEDIT.load(f);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BukkitWorld world = new BukkitWorld(Bukkit.getServer().getWorld(plugin.asteroidBaysInstance.worldName));
		EditSession session = new EditSession(world, 1000);
		Vector mp = new Vector(plugin.asteroidBaysInstance.pasteX, plugin.asteroidBaysInstance.pasteY, plugin.asteroidBaysInstance.pasteZ);
		
		try {
			cc.paste(session, mp, true);
		} catch (MaxChangedBlocksException e) {
			e.printStackTrace();
		}
		
	}
	
}
