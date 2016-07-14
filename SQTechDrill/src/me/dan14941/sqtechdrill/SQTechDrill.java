package me.dan14941.sqtechdrill;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.starquestminecraft.sqtechbase.SQTechBase;

public class SQTechDrill extends JavaPlugin
{
	public MovingDrill movingDrill;
	public Drill drill;
	public EventSimulator eventSim;
	private static SQTechDrill plugin;
	
    private HashMap<Player, Integer> active = new HashMap<Player, Integer>();
	
	public void onEnable()
	{
		plugin = this;
		this.drill = new Drill(100, this);
		SQTechBase.addMachineType(drill);
		this.movingDrill = new MovingDrill(this);
		this.eventSim = new EventSimulator();
		
		this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        
        @SuppressWarnings("unused")
		UpgradeItem ui = new UpgradeItem(this);
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
	
	static SQTechDrill getMain()
	{
		return plugin;
	}
	
	public int getDefaultDrillSpeed()
	{
		return this.getConfig().getInt("default speed");
	}
}
