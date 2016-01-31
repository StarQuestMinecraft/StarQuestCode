package me.Tongonto;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class RecyclerListener implements Listener{
	
	int check = 0;
	
	public static Player player;
	
	private final SQRecycler plugin;
	
	public RecyclerListener(SQRecycler plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public static BlockFace getGateDirection(Block sign) {
		if(sign.getType().equals(Material.WALL_SIGN)){
			switch (sign.getData()){
				case 2:
					return BlockFace.SOUTH;
				case 3:
					return BlockFace.NORTH;
				case 4:
					return BlockFace.EAST;
				case 5:
					return BlockFace.WEST;
				default:
					return null;
			}
		}
		return null;
	}
	
	@EventHandler
	public void onBlockInteraction(PlayerInteractEvent e){
		player = e.getPlayer();
		Block clickedBlock = e.getClickedBlock();
	if(clickedBlock != null){
		if(clickedBlock.getType() == Material.WALL_SIGN){
			Block MiddleBlock = clickedBlock.getRelative(getGateDirection(clickedBlock));
			Block TopBlock = MiddleBlock.getRelative(BlockFace.UP);
			Block BottomBlock = MiddleBlock.getRelative(BlockFace.DOWN);
			Sign recyclerSign = (Sign) clickedBlock.getState();
			if(MiddleBlock.getType() == Material.FURNACE){
				if(TopBlock.getType() == Material.DROPPER){
					if(BottomBlock.getType() == Material.DROPPER){
						if(recyclerSign.getLine(0).equalsIgnoreCase("[recycler]")){
							recyclerSign.setLine(0, ChatColor.GREEN + "Recycler");
							recyclerSign.setLine(1, ChatColor.RED + "INACTIVE");
							recyclerSign.update(true);
						}
						else if(recyclerSign.getLine(0).equals(ChatColor.GREEN + "Recycler")){
							if(recyclerSign.getLine(1).equals(ChatColor.RED + "INACTIVE")){
								recyclerSign.setLine(1, ChatColor.GREEN + "ACTIVE");
								Furnace middleFurnace = (Furnace) MiddleBlock.getState();
								Dropper topDropper = (Dropper) TopBlock.getState();
								Dropper bottomDropper = (Dropper) BottomBlock.getState();
								RecyclerMachine recyclerOne = new RecyclerMachine(topDropper, middleFurnace, bottomDropper, recyclerSign, recyclerSign.getLocation());
								plugin.activeRecyclerList.put(recyclerSign.getLocation(), recyclerOne);
								plugin.recyclerLocationList.add(recyclerSign.getLocation());
							}
							else if(recyclerSign.getLine(1).equals(ChatColor.GREEN + "ACTIVE")){
								recyclerSign.setLine(1, ChatColor.RED + "INACTIVE");
								if(plugin.activeRecyclerList != null){
									if(plugin.activeRecyclerList.size() > 0){
										plugin.activeRecyclerList.remove(recyclerSign.getLocation());
										plugin.recyclerLocationList.remove(recyclerSign.getLocation());
									}
								}
								else{
								}
							}
							recyclerSign.update(true);
						}
					}
				}
			}
		}	
	}
	}

	
	@EventHandler
	public void onPluginEnable(PluginEnableEvent enableEvent){
		if(check == 0){
			BukkitTask task = new RecyclerTask(this.plugin).runTask(this.plugin);
			check = 1;
		}
	}
}
