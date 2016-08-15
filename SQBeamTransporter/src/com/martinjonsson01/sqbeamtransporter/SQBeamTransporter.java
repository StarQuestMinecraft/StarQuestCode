package com.martinjonsson01.sqbeamtransporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.martinjonsson01.sqbeamtransporter.objects.Beam;
import com.martinjonsson01.sqbeamtransporter.objects.BeamTransporter;

public class SQBeamTransporter extends JavaPlugin{

	public static List<Entity> beamEntities = new ArrayList<Entity>();
	public static HashMap<Entity, BeamTransporter> transporterMap = new HashMap<Entity, BeamTransporter>();
	public static HashMap<BeamTransporter, Long> timeoutMap = new HashMap<BeamTransporter, Long>();
	public static ArrayList<BeamTransporter> currentlyBeaming = new ArrayList<BeamTransporter>();
	public static SQBeamTransporter plugin;
	public static FileConfiguration config;
	public static List<BeamTransporter> beamTransporterList = new ArrayList<BeamTransporter>();

	@Override
	public void onEnable() {

		plugin = this;

		config = this.getConfig();

		this.saveDefaultConfig();

		getServer().getPluginManager().registerEvents(new Events(), this);

		if (config == null) {
			Bukkit.getLogger().log(Level.WARNING, "[SQBeamTransporters] Could not find configuration file, try restarting the server.");
			Bukkit.getLogger().log(Level.WARNING, "[SQBeamTransporters] If this message still appears after restart, something is wrong.");
			return;
		}

		if (config.getConfigurationSection("BeamTransporters") == null) {
			Bukkit.getLogger().log(Level.WARNING, "[SQBeamTransporters] Could not find any players in config, moving on.");
			return;
		}

		for (String uuid : config.getConfigurationSection("BeamTransporters").getKeys(false)) {

			for (String xz : config.getConfigurationSection("BeamTransporters." + uuid).getKeys(false)) {

				if (uuid == null) return;

				int x = config.getInt("BeamTransporters." + uuid + "." + xz + ".x");
				int z = config.getInt("BeamTransporters." + uuid + "." + xz + ".z");
				String worldName =  config.getString("BeamTransporters." + uuid + "." + xz + ".world");
				@SuppressWarnings("deprecation")
				OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
				if (p == null) Bukkit.getLogger().log(Level.WARNING, "[SQBeamTransporter] !!!!Player is null!!!!");
				BeamTransporter.createBeamTransporterFromXZ(x, z, worldName, p);
				Bukkit.getLogger().log(Level.INFO, "[SQBeamTransporter] Successfully created beam transporter at X: " + x + " Z: " + z + " In world: " + worldName + ". Owned by player: " + p.getName(), false);

			}

		}

	}

	@Override
	public void onDisable() {

		for (BeamTransporter bt : SQBeamTransporter.beamTransporterList) {

			Block stainedGlass = bt.floorMap.firstEntry().getValue().getStainedGlass();
			OfflinePlayer owner = bt.owner;
			if (!owner.hasPlayedBefore()) {
				Bukkit.getLogger().log(Level.WARNING, "[SQBeamTransporter] !!!!Player is null!!!!", false);
				return;
			}

			SQBeamTransporter.config.set("BeamTransporters." + owner.getUniqueId() + "." + stainedGlass.getLocation().getBlockX() + stainedGlass.getLocation().getBlockZ() + ".x", stainedGlass.getLocation().getBlockX());
			SQBeamTransporter.config.set("BeamTransporters." + owner.getUniqueId() + "." + stainedGlass.getLocation().getBlockX() + stainedGlass.getLocation().getBlockZ() + ".z", stainedGlass.getLocation().getBlockZ());
			SQBeamTransporter.config.set("BeamTransporters." + owner.getUniqueId() + "." + stainedGlass.getLocation().getBlockX() + stainedGlass.getLocation().getBlockZ() + ".world", stainedGlass.getLocation().getWorld().getName());
			SQBeamTransporter.getPluginMain().saveConfig();

		}

	}

	public static SQBeamTransporter getPluginMain() {

		return plugin;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (commandLabel.equalsIgnoreCase("newbeam")) {

			if (args[0] == null) return false;

			if (args[0].equalsIgnoreCase("up")) {

				Player p = (Player) sender;

				//Prepare variables for beam
				Block bottomBlock = p.getLocation().getBlock();
				Block middleBlock = bottomBlock.getRelative(BlockFace.UP);
				Block topBlock = middleBlock.getRelative(BlockFace.UP);
				BlockFace direction = BlockFace.UP;
				//Create new beam and start it
				Beam beam = new Beam(bottomBlock, middleBlock, topBlock, direction, Material.STAINED_GLASS, (byte) 3);
				beam.myTask.runTaskTimer(SQBeamTransporter.getPluginMain(), 3, 3);
				return true;

			} else if (args[0].equalsIgnoreCase("down")) {

				Player p = (Player) sender;

				//Prepare variables for beam
				Block bottomBlock = p.getLocation().getBlock();
				Block middleBlock = bottomBlock.getRelative(BlockFace.UP);
				Block topBlock = middleBlock.getRelative(BlockFace.UP);
				BlockFace direction = BlockFace.DOWN;
				//Create new beam and start it
				Beam beam = new Beam(bottomBlock, middleBlock, topBlock, direction, Material.STAINED_GLASS, (byte) 2);
				beam.myTask.runTaskTimer(SQBeamTransporter.getPluginMain(), 3, 3);
				return true;

			}

		} else if (commandLabel.equalsIgnoreCase("beamblocks")) {

			sender.sendMessage("BeamBlocks: " + Beam.beamBlocks);
			return true;

		} else if (commandLabel.equalsIgnoreCase("groundblocks")) {

			sender.sendMessage("GroundBlocks: " + Beam.groundBlocks);
			return true;

		} else if (commandLabel.equalsIgnoreCase("helix")) {

			new BukkitRunnable(){
				Player p = (Player) sender;
				Location loc = p.getLocation();
				double t = 0;
				double r = 1;
				public void run(){
					t = t + Math.PI/16;
					double x = r*Math.cos(t);
					double y = 0.1*t; //Vertical speed
					double z = r*Math.sin(t);
					loc.add(x, y, z);
					loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 0, 0, 0, 100);
					loc.subtract(x, y, z);
					if (t > Math.PI*20){
						this.cancel();
					}
				}
			}.runTaskTimer(SQBeamTransporter.getPluginMain(), 0, 1);

		} else if (commandLabel.equalsIgnoreCase("helix2")) {
			Player p = (Player) sender;
			new BukkitRunnable(){

				@Override
				public void run() {
					
					for (double y = 0; y <= 8; y+= 0.01) {
						double adjustedX = 1 * Math.cos(y);
						double adjustedZ = 1 * Math.sin(y);
						Location loc = p.getLocation();
						loc.add(adjustedX, y, adjustedZ);
						loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 0, -10, 0, 1);
						loc.subtract(adjustedX, y, adjustedZ);
					}
					
				}
				
			}.runTaskTimer(getPluginMain(), 0, 10);
			
		}
		return false;
	}

}
