package net.countercraft.movecraft.sail;


import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Driver extends JavaPlugin implements Listener{
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getPlayer().getItemInHand().getType() == Material.POISONOUS_POTATO){
				Block b = event.getClickedBlock();
				BlockFace facing = getFacing(event.getPlayer());
				System.out.println(facing);
				System.out.println(b.getType());
				Block main = b.getRelative(facing);
				System.out.println(main.getType());
				if(main.getType() != Material.AIR){
					long time = System.currentTimeMillis();
					SailGenerator g = new SailGenerator();
					ArrayList<Block> sailWool = g.toggleSail(main, facing, true);
					long time2 = System.currentTimeMillis();
					System.out.println("Sail generation took " + (time - time2) + " ms.");
					System.out.println("Created sail of size " + sailWool.size());
				}
			}
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()){
			if(cmd.getName().equalsIgnoreCase("spawnsail")){
				Player plr = (Player) sender;
				BlockFace facing = getFacing(plr);
				Block b = plr.getEyeLocation().getBlock().getRelative(facing);
				Block main = b.getRelative(facing);
				sender.sendMessage("Deploying sail!");
				long time = System.currentTimeMillis();
				SailGenerator g = new SailGenerator();
				ArrayList<Block> sailWool = g.toggleSail(main, facing, true);
				long time2 = System.currentTimeMillis();
				sender.sendMessage("Sail generation took " + (time - time2) + " ms.");
				sender.sendMessage("Created sail of size " + sailWool.size());
				
				return true;
			}
		}
		return false;
	}
	
	private BlockFace getFacing(Player p){
		int yaw = (int) p.getLocation().getYaw();
		switch (yaw) {
        case 0 : return BlockFace.SOUTH;
        case 90 : return BlockFace.WEST;
        case 180 : return BlockFace.NORTH;
        case 270 : return BlockFace.EAST;
        }
        //Let's apply angle differences
        if (yaw >= -45 && yaw < 45) {
            return BlockFace.SOUTH;
        } else if (yaw >= 45 && yaw < 135) {
            return BlockFace.WEST;
        } else if (yaw >= -135 && yaw < -45) {
            return BlockFace.EAST;
        } else {
            return BlockFace.NORTH;
        }
	}
}
