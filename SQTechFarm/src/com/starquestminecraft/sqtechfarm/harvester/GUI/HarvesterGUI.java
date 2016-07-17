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

public class HarvesterGUI extends GUI
{

	private Machine machine;
	
	public HarvesterGUI(Player player, int id)
	{
		super(player, id);
	}

	/**
	 * Called when the GUI is to be opened.
	 */
	@Override	
	public void open()
	{
		Inventory gui = Bukkit.createInventory(owner, 18, ChatColor.BLUE + "Harvester");
		
		machine = ObjectUtils.getMachineFromMachineGUI(this);
		
		System.out.println(machine.getGUIBlock().id);
		
		gui.setItem(17, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, ChatColor.GOLD + "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));	

		owner.openInventory(gui);
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
			else if(event.getSlot() == 17) // door
			{
				
				GUIBlock guiBlock = null;

				for (Machine machine : SQTechBase.machines)
					if (machine.getGUIBlock() == this.machine.getGUIBlock())
						guiBlock = machine.getGUIBlock();

				guiBlock.getGUI(owner).open(); // Opens the GUIBlock's gui
				return;
				
			}
		}
	}
	
}
