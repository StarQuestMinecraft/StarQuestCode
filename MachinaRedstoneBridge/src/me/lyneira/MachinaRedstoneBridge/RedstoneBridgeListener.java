package me.lyneira.MachinaRedstoneBridge;

import java.util.UUID;

import me.lyneira.MachinaCore.BlockLocation;
import me.lyneira.MachinaCore.MachinaCore;
import net.countercraft.movecraft.utils.BlockUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Diode;
import org.bukkit.material.Lever;

class RedstoneBridgeListener implements Listener {
    private static final int repeaterOn = Material.DIODE_BLOCK_ON.getId();    
    public static final String SIGN_KEYWORD = "[Activator]";
    public static final String SIGN_FIRSTLINE = "[" + ChatColor.RED + "Activator" + ChatColor.BLACK + "]";
    private static final ItemStack AIR = new ItemStack(Material.AIR);
    

    private final MachinaRedstoneBridge plugin;

    RedstoneBridgeListener(MachinaRedstoneBridge plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onRedstone(BlockPhysicsEvent event) {
        if (event.getChangedType() != Material.DIODE_BLOCK_ON)
            return;
        Block blockUpdated = event.getBlock();
        if(blockUpdated.getType() != Material.LEVER){
        	return;
        }

    	plugin.queueDetect(blockUpdated);
    }
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event){
    	if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
    		Block b = event.getClickedBlock();
    		Material type = b.getType();
    		if(type == Material.WALL_SIGN || type == Material.SIGN_POST){
    	    	Sign s = (Sign) b.getState();
    			String line = s.getLine(0);
    	    	if(line.equalsIgnoreCase(SIGN_KEYWORD)){
    	    		s.setLine(0, SIGN_FIRSTLINE);
    	    		putUUIDonSign(event.getPlayer().getUniqueId(), s);
    	    		s.update();
    	    	}
    		}
    	}
    }
    
    public static void putUUIDonSign(UUID u, Sign s){
    	String ustring = u.toString();
    	String[] retval = new String[3];
    	s.setLine(1, ChatColor.MAGIC + ustring.substring(0, 12));
    	s.setLine(2,ChatColor.MAGIC + ustring.substring(12, 24));
    	s.setLine(3,ChatColor.MAGIC + ustring.substring(24, ustring.length()));
    }
    
    public static UUID getUUIDfromSign(Sign s){
    	String part1 = stripMagic(s.getLine(1));
    	String part2 = stripMagic(s.getLine(2));
    	String part3 = stripMagic(s.getLine(3));
    	String uuidString = part1 + part2 + part3;
    	return UUID.fromString(uuidString);
    }
    
    private static String stripMagic(String s){
    	return s.substring(2, s.length());
    }
}
