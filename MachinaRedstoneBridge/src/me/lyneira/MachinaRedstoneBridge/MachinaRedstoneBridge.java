package me.lyneira.MachinaRedstoneBridge;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import me.lyneira.MachinaCore.BlockLocation;
import me.lyneira.MachinaCore.MachinaCore;
import net.countercraft.movecraft.utils.BlockUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.material.Diode;
import org.bukkit.material.Lever;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin that attempts to activate a machina if a repeater powers an activator
 * block, and the block in front of the activator is a lever.
 * 
 * @author Lyneira
 */
public class MachinaRedstoneBridge extends JavaPlugin implements Runnable{
    
	private final Set<Block> queuedBlocks = new LinkedHashSet<Block>();
    private boolean queueScheduled = false;
	
    private MachinaCore machinaCore;
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new RedstoneBridgeListener(this), this);
        machinaCore = (MachinaCore) Bukkit.getServer().getPluginManager().getPlugin("MachinaCore");
    }

    @Override
    public void onDisable() {
    }
    
    void queueDetect(Block block) {
        if (block.getType() != Material.LEVER)
            return;
        queuedBlocks.add(block);
        if (!queueScheduled) {
            queueScheduled = true;
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, this);
        }
    }
    
    @Override
    public void run() {
        queueScheduled = false;
        for (Iterator<Block> it = queuedBlocks.iterator(); it.hasNext();) {
            Block blockUpdated = it.next();
            it.remove();
            
            BlockFace direction = null;
            Block block = null;
            Block[] blocks = BlockUtils.getEdges(blockUpdated, false, false);
            for(Block b : blocks){
            	if(b.getType() == Material.DIODE_BLOCK_ON){
            		 direction = ((Diode) b.getState().getData()).getFacing();
            		if(direction == b.getFace(blockUpdated)){
            			block = b;
            			break;
            		}
            	}
            }
            if(block == null){
            	continue;
            }
            
            Block down = block.getRelative(BlockFace.DOWN);
            if (down.getType() != Material.BRICK) {
            	continue;
            }
  
        	Block[] edges = BlockUtils.getEdges(down, false, false);
            for(Block b : edges){
            	Material type = b.getType();
            	if(type == Material.WALL_SIGN || type == Material.SIGN_POST){
            		Sign s = (Sign) b.getState();
            		if(s.getLine(0).equals(RedstoneBridgeListener.SIGN_FIRSTLINE)){
            			UUID u = RedstoneBridgeListener.getUUIDfromSign(s);
            			if(u != null){
            				Player p = Bukkit.getPlayer(u);
            				if(p != null){
            					if(blockUpdated.getType() == Material.LEVER){
            						Lever lever = (Lever) blockUpdated.getState().getData();
            			            BlockFace attachedFace = lever.getAttachedFace();
            			            if (attachedFace == null) {
            			                Bukkit.getLogger().warning("MachinaRedstoneBridge: Lever at " + block.toString() + "seems to be attached to nothing?");
            			                break;
            			            }
            			            Block attachedTo = blockUpdated.getRelative(attachedFace);
            			            machinaCore.onLever(p, new BlockLocation(attachedTo), attachedFace.getOppositeFace(), p.getItemInHand());
            					}
            					break;
            				}
            			}
            		}
            	}
            }
        }
    }
}
