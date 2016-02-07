package com.lawrencethefrog.nanfixer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jnbt.CompoundTag;
import org.jnbt.DoubleTag;
import org.jnbt.ListTag;
import org.jnbt.NBTInputStream;
import org.jnbt.NBTOutputStream;
import org.jnbt.Tag;


public class NaNFixer extends JavaPlugin implements Listener {
	private boolean autofix;
	private Vector teleportVector;
	private FixMode fixMode;	
	private int fixDelayTicks;
	
	@Override
	public void onEnable(){
		saveDefaultConfig();
		FileConfiguration config = getConfig();
		Boolean autofixConfig = config.getBoolean("Autofix");
		if(autofixConfig == null) {
			getLogger().info("Missing boolean value in config field 'Autofix', disabling...");
			setEnabled(false);
			return;
		}	//else
		autofix = autofixConfig;
		if(autofix){
			Integer configFixDelayTicks = config.getInt("AutofixDelayTicks");
			if (configFixDelayTicks==null){
				getLogger().info("Missing Integer value in config field 'AutofixDelayTicks', disabling...");
				setEnabled(false);
				return;
			}	//else
			fixDelayTicks = configFixDelayTicks;
		}
		
		String modeConfigStr = config.getString("Mode");
		if (modeConfigStr == null){
			getLogger().info("Missing String value in config field 'Mode', disabling...");
			setEnabled(false);
			return;
		}
		fixMode = FixMode.valueOf(modeConfigStr.trim().toUpperCase());
		if (fixMode == null){
			getLogger().info("Incorrect fix mode in config field 'Mode', valid modes are DELETE, and TELEPORT_COORDS. Disabling...");
			setEnabled(false);
			return;
		}
		if(fixMode != FixMode.DELETE){
			String configWorldStr = config.getString("TeleportWorld");
			if(configWorldStr == null){
				getLogger().info("Missing String value in config field 'TeleportWorld', disabling...");
				setEnabled(false);
				return;
			}
			World configWorld = Bukkit.getWorld(configWorldStr);
			if(configWorld== null){
				getLogger().info("There is no world with name '"+configWorldStr+"', disabling...");
				setEnabled(false);
				return;
			}
			
			Vector configTpVector = config.getVector("TeleportTo");
			if (configTpVector == null){
				getLogger().info("Missing vector data in config field 'TeleportTo', disabling...");
				setEnabled(false);
				return;
			}
			teleportVector = configTpVector;
		}		
		
		if(isEnabled()){
			getLogger().info("Registering events...");
			getServer().getPluginManager().registerEvents(this, this);
			getLogger().info("Events successfully registered.");
		}
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("nan")){
			if(args.length==0){
				sender.sendMessage("Please specify a sub-command");
				return true;
			}
			if(args[0].equalsIgnoreCase("fixUUID")){
				if(args.length<2){
					sender.sendMessage("Please specify a UUID to fix.");
					return true;
				}
				fixPlayer(args[1], sender);
			} else {
				sender.sendMessage("Please specify a valid sub-command.");
				return true;
			}
		}
		return true;
	}
	
	private enum FixMode {
		DELETE, TELEPORT_COORDS
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onKick(PlayerKickEvent event){		
		if(!autofix) return;
		if(!event.getReason().startsWith("NaN in position")) return;
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		//fixes file after delay to make sure player is fully kicked
		CommandSender consoleSender = Bukkit.getConsoleSender();
		consoleSender.sendMessage("Fixing player " +player.getDisplayName()+"'s NaN errors in " +fixDelayTicks +" ticks.");
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new FixTask(this, uuid, consoleSender), fixDelayTicks);
		fixPlayer(uuid, consoleSender);
	}
	
	//is unchecked playername
	protected void fixPlayer(String playerUUID, CommandSender sender){
		sender.sendMessage("Attempting to fix NaN position errors of player with UUID: " + playerUUID);
		for(World world: Bukkit.getWorlds()){
			String worldname = world.getName();
			File datFolder = new File(worldname+File.separator+"playerdata");
			File playerFile;
			try {
				playerFile = new File(datFolder, playerUUID+".dat");
			} catch (java.lang.NullPointerException ex){
				sender.sendMessage("Error finding player.dat file with UUID " + playerUUID +". Are you sure the UUUID is correct?");
				return;
			}
			fixFile(playerFile, sender);
		}		
	}
	private void fixFile(File playerFile, CommandSender sender) {
		sender.sendMessage("Attempting to fix player file " + playerFile + " for NaN errors in " + fixMode+ " mode.");
		if(fixMode == FixMode.DELETE){
			playerFile.delete();
			sender.sendMessage("File deleted.");
		}
		if(fixMode == FixMode.TELEPORT_COORDS){		
			try {				
				NBTInputStream nbtInputStream = new NBTInputStream(new FileInputStream(playerFile));
				CompoundTag oldRootTag = (CompoundTag)nbtInputStream.readTag();
				nbtInputStream.close();
				Map<String, Tag> oldRootMap = oldRootTag.getValue();
				DoubleTag xTag = new DoubleTag(null, teleportVector.getX());
				DoubleTag yTag = new DoubleTag(null, teleportVector.getY());
				DoubleTag zTag = new DoubleTag(null, teleportVector.getZ());
				List<Tag> posTagsList = new ArrayList<Tag>();
				posTagsList.add(xTag);
				posTagsList.add(yTag);
				posTagsList.add(zTag);
				ListTag posTag = new ListTag("Pos", DoubleTag.class, posTagsList);
				Map<String, Tag> newRootMap = new HashMap<String, Tag>();
				//allows changing of values (old map is UnmodifiableMap so throws erros on .put())
				newRootMap.putAll(oldRootMap);
				newRootMap.put("Pos", posTag);
				CompoundTag newRootTag = new CompoundTag("", newRootMap);
				NBTOutputStream nbtOutputStream = new NBTOutputStream(new FileOutputStream(playerFile));
				nbtOutputStream.writeTag(newRootTag);
				nbtOutputStream.close();
				sender.sendMessage("Done fixing player file " + playerFile + " in TELEPORT_COORDS mode.");
			} catch (IOException e) {
				sender.sendMessage("An IOException occurred while fixing file "+playerFile.getAbsolutePath()+"in TELEPORT_COORDS mode. Does it exist?.");
				return;
			}			
			
		}
				
	}
}
