package me.dan14941.sqtechdrill.task;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.starquestminecraft.sqtechbase.Machine;

import me.dan14941.sqtechdrill.Drill;
import me.dan14941.sqtechdrill.SQTechDrill;

public class BurnFuelRunnable extends BukkitRunnable
{

	private final Machine drill;
	public BurnFuelRunnable(Machine drill)
	{
		this.drill = drill;
	}
	
	@Override
	public void run()
	{
		Block guiBlock = drill.getGUIBlock().getLocation().getBlock();
		Block fuelInv = SQTechDrill.getMain().drill.detectFuelInventory(guiBlock, Drill.getDrillForward(guiBlock));
		Furnace furnace = null;
		if(fuelInv.getState() instanceof Furnace)
			furnace = (Furnace) fuelInv.getState();
		
		if(furnace == null)
		{
			SQTechDrill.getMain().unregisterMachineFromBurningFuel(drill);
			cancel();
			return;
		}
		
		FurnaceInventory furnaceInv = furnace.getInventory();
		int maxEnergy = SQTechDrill.getMain().drill.getMaxEnergy();
		
		ItemStack fuel = furnaceInv.getFuel();
		if(fuel != null && fuel.getType() == Material.COAL)
		{
			if(drill.getEnergy() >= maxEnergy)
			{
				SQTechDrill.getMain().unregisterMachineFromBurningFuel(drill);
				cancel();
				return;
			}
			
			int fuelAmount = fuel.getAmount();
			furnace.setBurnTime((short) 31); // set the furnace to burn
			fuel.setAmount((fuelAmount)-1); // remove one
			drill.setEnergy(drill.getEnergy() + 1000);
			if(fuel.getAmount() == 0)
			{
				furnaceInv.setFuel(new ItemStack(Material.AIR));
				SQTechDrill.getMain().unregisterMachineFromBurningFuel(drill);
				cancel();
				return;
			}
		}
		else if(fuel == null)
		{
			SQTechDrill.getMain().unregisterMachineFromBurningFuel(drill);
			cancel();
			return;
		}
	}

}
