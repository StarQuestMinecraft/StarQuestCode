package me.ginger_walnut.mcmmointegration;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.nossr50.mcMMO;

import net.countercraft.movecraft.event.CraftReleaseEvent;
import net.countercraft.movecraft.utils.MovecraftLocation;

public class McMMOIntegration extends JavaPlugin implements Listener{

	@Override
	public void onEnable(){
	
		getServer().getPluginManager().registerEvents(this, this );
		
	}
	
	@EventHandler
	public void onCraftRelease(CraftReleaseEvent event) {
		
		for (MovecraftLocation movecraftLocation : event.getCraft().getBlockList()) {
			
			Block block =  (new Location (event.getCraft().getW(), movecraftLocation.getX(), movecraftLocation.getY(), movecraftLocation.getZ())).getBlock();
			
			if (com.gmail.nossr50.util.BlockUtils.shouldBeWatched(block.getState()) && block.getState().getType() != Material.CHORUS_FLOWER) {
				
				mcMMO.getPlaceStore().setTrue(block.getState());
				
			}
			
		}
		
	}
	
}
