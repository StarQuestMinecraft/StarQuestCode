package me.dan14941.sqtechdrill.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

import me.dan14941.sqtechdrill.Drill;
import me.dan14941.sqtechdrill.SQTechDrill;
import me.dan14941.sqtechdrill.object.Fuel;
import me.dan14941.sqtechdrill.task.BurnFuelTask;

public class DrillGUI extends GUI
{
	private Machine machine;
	HashMap<String, Object> machineData;
	private final SQTechDrill main;

	public DrillGUI(SQTechDrill mainPlugin, Player player, int id)
	{
		super(player, id);
		main = mainPlugin;
	}

	@Override
	public void open() 
	{	
		Inventory gui = Bukkit.createInventory(owner, 9, ChatColor.BLUE + "Drill");

		machine = ObjectUtils.getMachineFromMachineGUI(this); // gets the machine for this gui

		if(machine == null)
		{
			owner.sendMessage(ChatColor.RED + "Something went wrong!");
			Bukkit.getLogger().warning("Player " + owner.getName() + " attempted to open a Drill GUI and something went wrong!");
			close = true;
			return;
		}

		Block fuelInventory = SQTechDrill.getMain().drill.detectFuelInventory(machine.getGUIBlock().getLocation().getBlock(), Drill.getDrillForward(machine.getGUIBlock().getLocation().getBlock()));
		
		if(fuelInventory.getState() instanceof Furnace)
			machine.data.put("fuelInventory", "FURNACE");
		else if(fuelInventory.getState() instanceof Dropper)
			machine.data.put("fuelInventory", "DROPPER");
		
		ItemStack onButton = null;

		if(machine.data == null)
		{
			machine.data = new HashMap<String, Object>();
		}

		machineData = machine.data;

		boolean isActive = false;

		if(machineData.get("isActive") == null)
		{
			isActive = false;
			machineData.put("isActive", isActive);
		}

		if(machineData.get("isActive") instanceof Boolean)
			isActive = (Boolean) machineData.get("isActive");

		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");

		if(isActive == true)
		{
			onButton = new ItemStack(Material.REDSTONE_TORCH_ON);
			ItemMeta buttonMeta = onButton.getItemMeta();
			buttonMeta.setDisplayName(ChatColor.RED + "Stop");
			buttonMeta.setLore(lore);
			onButton.setItemMeta(buttonMeta);
		}
		else
		{
			onButton = new ItemStack(Material.REDSTONE_LAMP_OFF);
			ItemMeta buttonMeta = onButton.getItemMeta();
			buttonMeta.setDisplayName(ChatColor.RED + "Start");
			buttonMeta.setLore(lore);
			onButton.setItemMeta(buttonMeta);
		}
		
		if(machineData.get("fuelInventory") instanceof String)
			if(machineData.get("fuelInventory") == "FURNACE")
			{
				BukkitTask BFRun;
				BFRun = main.getBurnFuelRunnable(machine);
				if(BFRun == null) // The machine isn't burning fuel
				{
					if(((Furnace) fuelInventory.getState()).getInventory().getFuel() != null)
					{
						Fuel fuel = new Fuel(((Furnace) fuelInventory.getState()).getInventory().getFuel().getType());
						BFRun = new BurnFuelTask(machine, fuel).runTaskTimer(main, 0, fuel.getBurnTime()); // repeats every number of ticks set in config
						main.registerMachineBurningFuel(machine, BFRun);
					}
				}
			}

		gui.setItem(8, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, ChatColor.GOLD + "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));	
		gui.setItem(7, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.GREEN + "Energy: " + machine.getEnergy(), new String[] {ChatColor.GOLD + "Click to refresh", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));	
		gui.setItem(4, onButton);

		owner.openInventory(gui);

		if (SQTechBase.currentGui.containsKey(owner)) {
			SQTechBase.currentGui.remove(owner);
			SQTechBase.currentGui.put(owner,  this);
		} else {
			SQTechBase.currentGui.put(owner,  this);
		}
	}

	@Override
	public void click(InventoryClickEvent event)
	{
		if(event.getClickedInventory() != null && event.getClickedInventory().getTitle().startsWith(ChatColor.BLUE + "Drill"))
		{
			event.setCancelled(true);

			ItemStack clickedItem = event.getInventory().getItem(event.getSlot());

			if(clickedItem == null)
				return;
			else if(event.getSlot() == 8) // if the door was clicked
			{

				GUIBlock guiBlock = null;

				for (Machine machine : SQTechBase.machines)
					if (machine.getGUIBlock() == this.machine.getGUIBlock())
						guiBlock = machine.getGUIBlock();

				guiBlock.getGUI(owner).open(); // Opens the GUIBlock's gui
				return;

			}
			else if(event.getSlot() == 7)
			{
				event.getClickedInventory().setItem(7, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.GREEN + "Energy: " + machine.getEnergy(), new String[] {ChatColor.GOLD + "Click to refresh", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
			}
			else if(event.getSlot() == 4) // or if the start/stop button was clicked
			{
				boolean clickedState = false; // false meaning player clicked stop
				// true meaning player clicked start
				if(clickedItem.getType() == Material.REDSTONE_LAMP_OFF)
					clickedState = true;
				else if(clickedItem.getType() == Material.REDSTONE_TORCH_ON)
					clickedState = false;

				if(clickedState == true)
				{
					if(event.getWhoClicked().hasPermission("SQTechDrill.activate"))
					{
						if(machine.getEnergy() < 10)
						{
							event.getWhoClicked().sendMessage(ChatColor.RED + "The drill does not have enough energy to start!");
							machine.data.put("isActive", false); // turn the drill off
							event.getWhoClicked().closeInventory();
							return;
						}
						event.getWhoClicked().sendMessage(ChatColor.RED + "Activating drill!");
						//main.movingDrill.activateDrill(machine, (Player) event.getWhoClicked());
						main.activateDrill(machine, (Player) event.getWhoClicked());
						event.getWhoClicked().closeInventory();
						return;
					}
					else
					{
						event.getWhoClicked().sendMessage(ChatColor.RED + "You don't have permission to active drills.");
						event.setCancelled(true);
						event.getWhoClicked().closeInventory();
						return;
					}
				}
				else // turn off drill
				{
					event.getWhoClicked().sendMessage(ChatColor.RED + "Deactivating drill!");
					//main.movingDrill.deactivateDrill(machine, (Player) event.getWhoClicked());
					main.deActivateDrill(machine);
					event.setCancelled(true);
					event.getWhoClicked().closeInventory();
					return;
				}
			}
		}
	}
}
