package com.starquestminecraft.sqtechfarm;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechfarm.harvester.Harvester;

public class Events implements Listener
{
	private final SQTechFarm main;
	
	public Events(SQTechFarm plugin)
	{
		this.main = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChunkUnload(ChunkUnloadEvent event)
	{
		if(!event.isCancelled())
		{
			Chunk chunk = event.getChunk();
			
			for(Machine machine : SQTechBase.machines)
			{
				if(machine.getMachineType() instanceof Harvester)
				{
					if(machine.getGUIBlock().getLocation().getChunk() == chunk)
					{
						main.setInactive(machine);
						machine.data.put("blocked", false);
					}
				}
			}
		}
	}
	
}
