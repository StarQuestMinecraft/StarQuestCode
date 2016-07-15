package me.dan14941.sqtechdrill;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.SQTechBase;

import me.dan14941.sqtechdrill.movement.MovingDrill;

public class SQTechDrill extends JavaPlugin
{
	public MovingDrill movingDrill;
	public Drill drill;
	public EventSimulator eventSim;
	private static SQTechDrill plugin;
	
    private HashMap<Player, Integer> active = new HashMap<Player, Integer>();
    private HashMap<Machine, BukkitTask> machinesBurningFuel = new HashMap<Machine, BukkitTask>();
	
	public void onEnable()
	{
		plugin = this;
		
		this.drill = new Drill(10000, this);
		SQTechBase.addMachineType(drill);
		this.movingDrill = new MovingDrill(this);
		this.eventSim = new EventSimulator();
		
		this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        
        if(getConfig().getBoolean("testing version") == true)
        {
        	@SuppressWarnings("unused")
        	UpgradeItem ui = new UpgradeItem(this);
        }
	}
	
	public void onDisable()
	{
		for(Machine machine : SQTechBase.machines)
        {
        	if(machine.getMachineType().name == "Drill")
        		machine.data.put("isActive", false); // Turn all drills off
        }
	}
	
	/**
	 * Gets the amount of active drills a player currently has
	 * @param player the Player with active drills
	 * @return the amount of active drills a player currently has
	 */
	public Integer getActiveDrillsForPlayer(Player player)
	{
		return this.active.get(player);
	}
	
	/**
	 * Sets the current active amount of drills for a Player.
	 * @param player the Player who has the active drills
	 * @param integer amount of drills active
	 * @return Current active drills for a Player after changing it
	 */
	public Integer setActiveDrillsForPlayer(Player player, Integer integer)
	{
		this.active.put(player, integer);
		return this.active.get(player);
	}
	
	public static SQTechDrill getMain()
	{
		return plugin;
	}
	
	public int getDefaultDrillSpeed()
	{
		return this.getConfig().getInt("default speed");
	}
	
	public int getEnergyPerBlockMined()
	{
		return this.getConfig().getInt("energy consumption per block");
	}
	
	public void registerMachineBurningFuel(Machine machine, BukkitTask task)
	{
		this.machinesBurningFuel.put(machine, task);
	}
	
	public BukkitTask getBurnFuelRunnable(Machine machine)
	{
		return this.machinesBurningFuel.get(machine);
	}
	
	public void unregisterMachineFromBurningFuel(Machine machine)
	{
		this.machinesBurningFuel.remove(machine);
	}
	
	public int getCoalFuelPerTick()
	{
		return this.getConfig().getInt("fuel.coal.energy per tick");
	}
	
	public int getCoalBurnTime()
	{
		return this.getConfig().getInt("fuel.coal.burn time");
	}
	
}
