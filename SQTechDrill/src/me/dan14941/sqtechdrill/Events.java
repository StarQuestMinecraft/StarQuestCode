package me.dan14941.sqtechdrill;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Machine;

public class Events implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChunkUnload(ChunkUnloadEvent event)
	{
		if(!event.isCancelled())
		{
			Chunk chunk = event.getChunk();
			
			for(Machine machine : SQTechBase.machines)
			{
				if(machine.getMachineType().name == "Drill")
				{
					if(machine.getGUIBlock().getLocation().getChunk() == chunk)
					{
						machine.data.put("isActive", false);
						machine.data.put("blocked", false);
					}
				}
			}
		}
	}
}