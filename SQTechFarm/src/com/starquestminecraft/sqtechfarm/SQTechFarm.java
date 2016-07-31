package com.starquestminecraft.sqtechfarm;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechfarm.Object.Settings;
import com.starquestminecraft.sqtechfarm.harvester.Harvester;
import com.starquestminecraft.sqtechfarm.harvester.Object.HarvesterManagerTask;

public class SQTechFarm extends JavaPlugin
{
	private Settings settings;
	private Harvester harvesterType;
	private HarvesterManagerTask task;
	
	final public List<Material> fenceTypes = Arrays.asList(Material.FENCE, Material.ACACIA_FENCE, 
			Material.BIRCH_FENCE, Material.DARK_OAK_FENCE, Material.JUNGLE_FENCE, Material.SPRUCE_FENCE);
	//final private HashMap<Machine, MovingHarvester> movingHarvesters = new HashMap<Machine, MovingHarvester>();
	
	public void onEnable()
	{	
		this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        
        this.settings = new Settings(this);
		
		SQTechBase.addMachineType(harvesterType = new Harvester(settings.getHarvesterMaxEnergy(), this));
		
		this.getServer().getPluginManager().registerEvents(new Events(this), this);
	}
	
	public void onDisable()
	{
		for(Machine mach : SQTechBase.machines) // set all harvesters to inactive when the server stops
		{
			if(mach.getMachineType() instanceof Harvester)
			{
				this.setInactive(mach);
				mach.data.put("blocked", false); // set all harvesters to not blocked
			}
		}
	}
	
	public void startHarvester(Machine machine)
	{
		if(task == null)
		{
			task = new HarvesterManagerTask(this);
			task.runTaskTimer(this, settings.getHarvesterSpeed(), settings.getHarvesterSpeed());
		}
		
		task.addHarvester(machine);
		
		setActive(machine);
	}
	
	public void stopHarvester(Machine machine)
	{
		task.removeActiveHarvester(machine);
		if(task.activeHarvesters() < 1)
		{
			task.cancel();
			task = null;
		}
		machine.data.put("blocked", false);
		setInactive(machine);
	}
	
	public Settings getSettings()
	{
		return this.settings;
	}
	
	public Harvester getHarvesterType()
	{
		return this.harvesterType;
	}
	
	public boolean isActive(Machine machine)
	{
		if(machine.data.containsKey("isActive") == false)
		{
			machine.data.put("isActive", false);
			return false;
		}
		else
			return (Boolean) machine.data.get("isActive");
	}
	
	public void setActive(Machine machine)
	{
		machine.data.put("isActive", true);
	}
	
	public void setInactive(Machine machine)
	{
		machine.data.put("isActive", false);
	}
	
	/*
	public void registerMovingHarvester(Machine machine, MovingHarvester movingHarvester)
	{
		this.movingHarvesters.put(machine, movingHarvester);
	}
	
	public void unRegisterMovingHarvester(Machine machine)
	{
		this.movingHarvesters.remove(machine);
	}
	
	public MovingHarvester getMovingHarvester(Machine machine)
	{
		return this.movingHarvesters.get(machine);
	}
	*/
}
