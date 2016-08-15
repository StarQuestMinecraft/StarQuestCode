package com.starquestminecraft.sqtechfarm.harvester.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;
import com.starquestminecraft.sqtechfarm.SQTechFarm;
import com.starquestminecraft.sqtechfarm.harvester.Harvester;
import com.starquestminecraft.sqtechfarm.harvester.Harvester.HarvesterSize;

public class HarvesterGUI extends GUI
{

	private Machine machine;
	private SQTechFarm plugin;
	
	public HarvesterGUI(Player player, int id, SQTechFarm plugin)
	{
		super(player, id);
		this.plugin = plugin;
	}

	/**
	 * Called when the GUI is to be opened.
	 */
	@Override	
	public void open()
	{
		Inventory gui = Bukkit.createInventory(owner, 9, ChatColor.BLUE + "Harvester");
		
		machine = ObjectUtils.getMachineFromMachineGUI(this);
		
		gui.setItem(8, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, ChatColor.GOLD + "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));	
		gui.setItem(7, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.GREEN + "Energy: " + machine.getEnergy(), new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));	
		
		if(machine.data.get("blocked") != null && (Boolean) machine.data.get("blocked") == true)
		{
			gui.setItem(4, InventoryUtils.createSpecialItem(Material.BEDROCK, (short) 0, ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Blocked", new String[] {ChatColor.GOLD + "This happens when a harvester is doing something that can't be interrupted.",ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));	
		}
		else
		{
			if(plugin.isActive(machine) == true)
				gui.setItem(4, InventoryUtils.createSpecialItem(Material.REDSTONE_TORCH_ON, (short) 0, ChatColor.RED + "Stop", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));	
			else
				gui.setItem(4, InventoryUtils.createSpecialItem(Material.REDSTONE_LAMP_OFF, (short) 0, ChatColor.RED + "Start", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));	
		}
		//TODO replace when upgrades are implemented
		machine.data.put("maxSize", plugin.getSettings().getDefaultSize()); // Sets the default size to minimum
		
		owner.openInventory(gui);
		
		machine.data.put("size", plugin.getHarvesterType().getHarvesterSize(machine.getGUIBlock().getLocation().getBlock(), Harvester.getHarvesterForward(machine)));
		
		if (SQTechBase.currentGui.containsKey(owner)) {
			SQTechBase.currentGui.remove(owner);
			SQTechBase.currentGui.put(owner,  this);
		} else {
			SQTechBase.currentGui.put(owner,  this);
		}
	}
	
	/**
	 * Called when a click happens in an inventory.
	 */
	@Override
	public void click(InventoryClickEvent event)
	{
		if(event.getClickedInventory() != null && event.getClickedInventory().getTitle().startsWith(ChatColor.BLUE + "Harvester"))
		{
			event.setCancelled(true);
			
			ItemStack clickedItem = event.getInventory().getItem(event.getSlot());
			
			if(clickedItem == null)
				return;
			else if(event.getSlot() == 8) // door
			{
				GUIBlock guiBlock = null;

				for (Machine machine : SQTechBase.machines)
					if (machine.getGUIBlock() == this.machine.getGUIBlock())
						guiBlock = machine.getGUIBlock();

				guiBlock.getGUI(owner).open(); // Opens the GUIBlock's gui
				return;	
			}
			else if(event.getSlot() == 4) // start/stop button
			{
				boolean isActive = true;
				boolean isBlocked = false;
				
				if(clickedItem.getType() == Material.REDSTONE_LAMP_OFF)
					isActive = false;
				else if(clickedItem.getType() == Material.BEDROCK) // blocked
					isBlocked = true;
				
				if(isBlocked)
				{
					event.getWhoClicked().closeInventory();
					return;
				}
				
				if(isActive == false) // turn on
				{
					if(event.getWhoClicked().hasPermission("SQTechFarm.harvester.activate"))
					{
						// Check if the machine is too big.
						if((Integer) machine.data.get("size") > plugin.getHarvesterType().getSizeForHarvesterSize((HarvesterSize) machine.data.get("maxSize")))
							event.getWhoClicked().sendMessage(ChatColor.RED + "Your harvester is too big!");
						else
						{
							plugin.startHarvester(machine);
							//MovingHarvester harvester = new MovingHarvester(plugin, machine, (Player) event.getWhoClicked());
							//harvester.startHarvester(); // Harvester is registered
						}
						event.getWhoClicked().closeInventory();
						return;
					}
					else
					{
						event.getWhoClicked().sendMessage(ChatColor.RED + "Sorry you don't have permission to activate this harvester!");
						return;
					}
				}
				else // turn off
				{
					//main.movingDrill.deactivateDrill(machine, (Player) event.getWhoClicked());
					machine.data.put("isActive", false);
					//plugin.unRegisterMovingHarvester(machine);
					plugin.stopHarvester(machine);
					event.getWhoClicked().closeInventory();
					return;
				}
			}
		}
	}
	
}
