package me.dan14941.sqtechdrill;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

public class DrillGUI extends GUI
{
	private Machine machine;
	HashMap<String, Object> machineData;
	private static SQTechDrill main;
	
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
		
		for (Machine listMachine : SQTechBase.machines)
		{
			if (listMachine.getGUIBlock().id == id)
				machine = listMachine;
		}
		
		if(machine == null)
		{
			owner.sendMessage(ChatColor.RED + "Something went wrong!");
			Bukkit.getLogger().warning("Player " + owner.getName() + " attempted to open a Drill GUI and something went wrong!");
			close = true;
			return;
		}
		
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
		if(event.getClickedInventory().getTitle().startsWith(ChatColor.BLUE + "Drill"))
		{
			ItemStack clickedItem = event.getInventory().getItem(event.getSlot());
			
			if(clickedItem == null)
				return;
			
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
					event.getWhoClicked().sendMessage(ChatColor.RED + "Activating drill!");
					main.movingDrill.activateDrill(machine, (Player) event.getWhoClicked());
					event.setCancelled(true);
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
				main.movingDrill.deactivateDrill(machine, (Player) event.getWhoClicked());
				event.setCancelled(true);
				event.getWhoClicked().closeInventory();
				return;
			}
		}
	}
}
