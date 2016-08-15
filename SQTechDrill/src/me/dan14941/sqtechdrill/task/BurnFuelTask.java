package me.dan14941.sqtechdrill.task;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.starquestminecraft.sqtechbase.objects.Machine;

import me.dan14941.sqtechdrill.Drill;
import me.dan14941.sqtechdrill.SQTechDrill;
import me.dan14941.sqtechdrill.object.Fuel;

public class BurnFuelTask extends BukkitRunnable
{
	
	private final Machine drill;
	private final Fuel fuelItem;
	
	public BurnFuelTask(final Machine drill, final Fuel fuelItem)
	{
		this.drill = drill;
		this.fuelItem = fuelItem;
	}
	
	@SuppressWarnings("deprecation")
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
		
		if(fuel != null && fuelItem.isRegisteredFuel() == true)
		{
			if(fuelItem.getMaterial() == Material.COAL && fuel.getData().getData() != 0)
			{
				SQTechDrill.getMain().unregisterMachineFromBurningFuel(drill);
				furnace.setBurnTime((short) 0);
				cancel();
				return;
			}
			
			if(drill.getEnergy() >= maxEnergy)
			{
				SQTechDrill.getMain().unregisterMachineFromBurningFuel(drill);
				furnace.setBurnTime((short) 0);
				cancel();
				return;
			}
			
			final int fuelAmount = fuel.getAmount();
			final int burnTime = fuelItem.getBurnTime();
			
			furnace.setBurnTime((short) (burnTime + 1)); // set the furnace to burn
			furnace.setCookTime((short) 0);
			
			fuel.setAmount((fuelAmount)-1); // remove one
			
			new BukkitRunnable()
			{
				int count = 0;
				@Override
				public void run()
				{	
					if(count == burnTime)
					{
						cancel();
						count = 1;
						return;
					}
					
					drill.setEnergy(drill.getEnergy() + fuelItem.getEnergyPerTick()); // Adds the fuel
					
					count++;
				}
			}.runTaskTimer(SQTechDrill.getMain(), 0, 0); // runs every tick
			
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
		else if(fuelItem.isRegisteredFuel() == false)
		{
			SQTechDrill.getMain().unregisterMachineFromBurningFuel(drill);
			cancel();
			return;
		}
	}

}
