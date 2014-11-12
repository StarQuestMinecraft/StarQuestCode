package com.dibujaron.DockingTube;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DockingTubeCore extends JavaPlugin implements Listener{
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (event.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) event.getClickedBlock().getState();
				if (sign.getLine(0).equalsIgnoreCase("[dockingtube]")) {
					sign.setLine(0, ChatColor.AQUA + "DOCKING TUBE");
					sign.setLine(1, "{" + ChatColor.RED + "RETRACTED" + ChatColor.BLACK + "}");
					sign.update();
					return;
				}
				if (sign.getLine(0).equalsIgnoreCase(ChatColor.AQUA + "DOCKING TUBE")){
					new DockingTube(sign, event.getPlayer());
				}
			}
		}
	}
}
