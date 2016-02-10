package me.thetimelord;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
//import org.bukkit.material.Sign;     - Using full name instead
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;

public class PlayerListener implements Listener{
	public PlayerListener(SQTractorBeam plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void clickSign(PlayerInteractEvent event)
	{
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			 //Block b = event.getClickedBlock();
			 if(event.getClickedBlock().getTypeId() == 68)
			 {
				 Location location = event.getClickedBlock().getLocation();
				 //int x = location.getBlockX();
				 //int y = location.getBlockY();
				 //int z = location.getBlockZ();
				 //World w = location.getWorld();
				 //Location locSign = new Location(w, x, y, z);
				 
				 boolean foundIron = false;
				 boolean foundSponge = false;
				 boolean foundGlass = false;
				 boolean foundGlass2 = false;
				 boolean foundGlowstone = false;
				 boolean alreadySwitched = false;
				 String stringJ = ("blank");
				 boolean stop = false;
				 byte glassType = 0;
				 Player player = event.getPlayer();
				 org.bukkit.material.Sign signMat = (org.bukkit.material.Sign)event.getClickedBlock().getState().getData();
				 Sign signBlock = (Sign)event.getClickedBlock().getState();
				 BlockFace signFace = signMat.getAttachedFace();
				 Block b = location.getBlock().getRelative(signFace, 1);
				 
				 if (b.getTypeId() == 19)
				 {
					 foundSponge = true;
				 }
				 b = location.getBlock().getRelative(signFace, 2);
				 if (b.getTypeId() == 42)
				 {
					 foundIron = true;
				 }
				 location.setY(location.getY()+1);
				 b = location.getBlock().getRelative(signFace, 2);
				 location.setY(location.getY()-1);
				 if (b.getTypeId() == 160)
				 {
					 foundGlass = true;
					 glassType = b.getData();
				 }
				 b = location.getBlock().getRelative(signFace, 3);
				 if (b.getTypeId() == 89)
				 {
					 foundGlowstone = true;
				 }
				 b = location.getBlock().getRelative(signFace, 2);
				 if (b.getTypeId() == 160)
				 {
					 foundGlass2 = true;
					 glassType = b.getData();
				 }
				 if (signBlock.getLine(0).equalsIgnoreCase("[tractorbeam]") && (foundGlass) && (foundIron) && (foundSponge))
				 {
					 signBlock.setLine(0,"{" + ChatColor.BLUE + "Tractor Beam" + ChatColor.BLACK + "}");
					 signBlock.setLine(1, "{" + ChatColor.RED + "OFFLINE" + ChatColor.BLACK + "}");
					 signBlock.setLine(0,"{" + ChatColor.BLUE + "Tractor Beam" + ChatColor.BLACK + "}");
					 signBlock.setLine(2, " ");
					 signBlock.update(); 
					 
					 alreadySwitched = true;
				 }
					 
				 if ((signBlock.getLine(0).equalsIgnoreCase("{" + ChatColor.BLUE + "Tractor Beam" + ChatColor.BLACK + "}")) && (signBlock.getLine(1).equalsIgnoreCase("{" + ChatColor.RED + "OFFLINE" + ChatColor.BLACK + "}")) && (foundGlass) && (foundIron) && (foundSponge) && (foundGlowstone) && (!alreadySwitched))
				 {
					 b = location.getBlock().getRelative(signFace, 4);
					 if (b.getTypeId() == 0)
					 {
						 for (int i = 0; i < 20; ++i)
						 {
							 b = location.getBlock().getRelative(signFace, 4 + i);
							 if ((b.getTypeId() != 0) && (!stop))
							 {
								 stop = true;
								 signBlock.setLine(1, "{" + ChatColor.GREEN + "ONLINE" + ChatColor.BLACK + "}");
								 signBlock.update(); 
								 
								 b = location.getBlock().getRelative(signFace, 2);
								 b.setTypeId(160);
								 if (player.hasPermission("dockingtube.color"))
								 {
									 b.setData(glassType);
								 }
								 location.setY(location.getY()+1);
								 b = location.getBlock().getRelative(signFace, 2);
								 b.setTypeId(0);
								 location.setY(location.getY()-1);
								 
								 for (int j = 0; j < i; ++j)
								 {
									 b = location.getBlock().getRelative(signFace, 4 + j);
									 b.setTypeId(160);
									 if (player.hasPermission("dockingtube.color"))
									 {
										 b.setData(glassType);
									 }
									 alreadySwitched = true;
									 stringJ = Integer.toString(j);
									 signBlock.setLine(2, stringJ);
									 signBlock.update();
								 }
							 }
						 }
					 }
				 }
				 
				 if ((signBlock.getLine(0).equalsIgnoreCase("{" + ChatColor.BLUE + "Tractor Beam" + ChatColor.BLACK + "}")) && (signBlock.getLine(1).equalsIgnoreCase("{" + ChatColor.GREEN + "ONLINE" + ChatColor.BLACK + "}")) && (foundGlass2) && (foundGlowstone) && (foundSponge) && (!alreadySwitched))
				 {
					 for (int i = 0; i < 20; ++i)
					 {
						 b = location.getBlock().getRelative(signFace, 4 + i);
						 if (b.getTypeId() ==160)
						 {
							 stringJ = signBlock.getLine(2);
							 int jToInt = Integer.parseInt(stringJ);
							 signBlock.setLine(1, "{" + ChatColor.RED + "OFFLINE" + ChatColor.BLACK + "}");
							 signBlock.setLine(2, " ");
							 signBlock.update(); 
							 
							 b = location.getBlock().getRelative(signFace, 2);
							 b.setTypeId(42);
							 location.setY(location.getY()+1);
							 b = location.getBlock().getRelative(signFace, 2);
							 b.setTypeId(160);
							 if (player.hasPermission("dockingtube.color"))
							 {
								 b.setData(glassType);
							 }
							 location.setY(location.getY()-1);
							 
							 for (int j = 0; j <= jToInt; ++j)
							 {
								 b = location.getBlock().getRelative(signFace, 4 + j);
								 if (b.getTypeId() == 160)
								 {
									 b.setTypeId(0);
									 alreadySwitched = true;
								 }
							 }
						 }
					 }
				 }
			 }
		}
	}
