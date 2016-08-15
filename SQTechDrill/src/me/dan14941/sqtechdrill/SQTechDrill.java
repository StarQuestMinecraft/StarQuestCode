package me.dan14941.sqtechdrill;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.objects.Machine;

import me.dan14941.sqtechdrill.movement.MovingDrill;
import me.dan14941.sqtechdrill.task.DrillManager;

public class SQTechDrill extends JavaPlugin
{
	public MovingDrill movingDrill;
	public Drill drill;
	public EventSimulator eventSim;
	private static SQTechDrill plugin;
	private DrillManager task;
	
    private HashMap<Machine, BukkitTask> machinesBurningFuel = new HashMap<Machine, BukkitTask>();
	
	public void onEnable()
	{
		plugin = this;
		
		task = null;
		
		this.drill = new Drill(this.getDrillMaxEnergyFromConfig(), this);
		SQTechBase.addMachineType(drill);
		this.movingDrill = new MovingDrill(this);
		this.eventSim = new EventSimulator();
		
		this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        
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
	
	public void activateDrill(Machine drill, Player player)
	{
		drill.data.put("isActive", true);
		
		List<Block> frontBlocks = MovingDrill.getBlocksInFront(drill);
		if(frontBlocks == null)
		{
			player.sendMessage(ChatColor.RED + "The drill could not start!");
			return;
		}

		for (Block block : frontBlocks)
		{
			if(movingDrill.attemptDrill(block, player) == false)
			{
				drill.data.put("isActive", false);
				return;
			}
		} // Checks front blocks

		if((boolean) drill.data.get("isActive") == false)
			return;
		
		if(this.task == null)
		{
			this.getLogger().info("Starting a new drill manager task.");
			task = new DrillManager(this);
			task.runTaskTimer(this, this.getFastDrillSpeed()+1, this.getFastDrillSpeed()+1);
		}
		
		task.addDrill(drill);
	}
	
	public void deActivateDrill(Machine drill)
	{
		if(task == null)
			return;
		task.removeActiveMachine(drill);
		if(task.activeDrills() < 1)
		{
			this.getLogger().info("Stopping the drill manager task.");
			task.cancel();
			task = null;
		}
		
		drill.data.put("isActive", false);
	}
	
	public static SQTechDrill getMain()
	{
		return plugin;
	}
	
	public int getNormalDrillSpeed() //iron block
	{
		return this.getConfig().getInt("normal speed");
	}
	
	public int getFastDrillSpeed()
	{
		return this.getConfig().getInt("fast speed");
	}
	
	public int getEnergyPerBlockMined()
	{
		return this.getConfig().getInt("energy consumption per block");
	}
	
	public int getDrillMaxEnergyFromConfig()
	{
		return this.getConfig().getInt("max energy");
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
	
}
