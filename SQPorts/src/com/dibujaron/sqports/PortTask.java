package com.dibujaron.sqports;

import net.countercraft.movecraft.bungee.BungeePlayerHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PortTask extends BukkitRunnable{

	int iteration = 0;
	private static int MAX_ITERATION = 5;
	@Override
	public void run() {
		
		for(Player plr : Bukkit.getOnlinePlayers()){
			Location l = plr.getLocation();
			for(Port port : SQPorts.ports){
				if(port.region.contains(l.getBlockX(), l.getBlockY(),l.getBlockZ())){
					act(plr, port, iteration);
				}
			}
		}
		iteration++;
		if(iteration > MAX_ITERATION) iteration = 0;
	}
	
	private void act(Player plr, Port port, int itr) {
		if(itr == 0){
			plr.sendMessage("This ship leaves for " + port.target + " in 50 seconds!");
			plr.sendMessage("Please note that your inventory will " + ChatColor.RED + "NOT" + ChatColor.WHITE + " come with you!");
			return;
		}
		if(itr == 1){
			plr.sendMessage("This ship leaves for " + port.target + " in 40 seconds!");
			plr.sendMessage("Please note that your inventory will " + ChatColor.RED + "NOT" + ChatColor.WHITE + " come with you!");
		}
		if(itr == 2){
			plr.sendMessage("This ship leaves for " + port.target + " in 30 seconds!");
			plr.sendMessage("Please note that your inventory will " + ChatColor.RED + "NOT" + ChatColor.WHITE + " come with you!");
		}
		if(itr == 3){
			plr.sendMessage("This ship leaves for " + port.target + " in 20 seconds!");
			plr.sendMessage("Please note that your inventory will " + ChatColor.RED + "NOT" + ChatColor.WHITE + " come with you!");
		}
		if(itr == 4){
			plr.sendMessage("This ship leaves for " + port.target + " in 10 seconds!");
			plr.sendMessage("Please note that your inventory will " + ChatColor.RED + "NOT" + ChatColor.WHITE + " come with you!");
		}
		if(itr == 5){
			plr.sendMessage("This ship is departing!");
			Location l = plr.getLocation();
			int diffX = l.getBlockX() - port.x;
			int diffY = l.getBlockY() - port.y;
			int diffZ = l.getBlockZ() - port.z;
			int ptx /* pentatonix lol */ = port.tx + diffX;
			int pty = port.ty + diffY;
			int ptz = port.tz + diffZ;
			plr.getInventory().clear();
			BungeePlayerHandler.sendPlayer(plr, port.target, port.target, ptx, pty, ptz);
		}
	}

	public static int getIntervalShipLeavesEvery(int ticks){
		return ticks / (MAX_ITERATION + 1);
		//60 seconds divided by (5+1) is 10 seconds, good
	}
}
