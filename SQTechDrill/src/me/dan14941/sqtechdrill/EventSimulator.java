package me.dan14941.sqtechdrill;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.nossr50.events.fake.FakeBlockBreakEvent;

public class EventSimulator
{
	public static Event pretendEvent;
	public static boolean pretendEventCancelled;
	
	/**
     * Pretends a block break event to determine whether it would be allowed to
     * happen. It will collect the cancelled result at the highest possible
     * priority, then cancel its own event to prevent it from being logged by
     * any monitoring plugins.
     * 
     * @param target
     *            The target location to break at
     * @param player
     *            The player to simulate for
     * @return True if the player may break a block at the location
     */
    public static boolean blockBreakPretend(Location target, Player player) {
        Block block = target.getBlock();
        pretendEvent = new FakeBlockBreakEvent(block, player);
        pretendEventCancelled = true;
        SQTechDrill.getMain().getServer().getPluginManager().callEvent(pretendEvent);

        return !pretendEventCancelled;
    }
    
    public static boolean blockBreak(Location target, Player player)
    {
    	Block block = target.getBlock();
    	BlockBreakEvent breakEvent = new FakeBlockBreakEvent(block, player);
    	
    	SQTechDrill.getMain().getServer().getPluginManager().callEvent(breakEvent);
    	if(breakEvent.isCancelled())
    		return false;
    	
    	return true;
    }
}
