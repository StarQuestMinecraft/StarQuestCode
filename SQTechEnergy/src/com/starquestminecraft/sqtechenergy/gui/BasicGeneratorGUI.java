package com.starquestminecraft.sqtechenergy.gui;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.GUIBlock;
import com.starquestminecraft.sqtechbase.Machine;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;
import com.starquestminecraft.sqtechenergy.Fuel;
import com.starquestminecraft.sqtechenergy.SQTechEnergy;

import net.md_5.bungee.api.ChatColor;

public class BasicGeneratorGUI extends GUI{

	public BasicGeneratorGUI(Player player, int id) {
		
		super(player, id);
		
	}
	
	@Override	
	public void open() {

		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Basic Generator");

		Machine machine = ObjectUtils.getMachineFromMachineGUI(this);
				
		gui.setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, "Energy: " + machine.getEnergy(), new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(26, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		
		gui.setItem(0, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(2, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(9, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(11, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(18, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(19, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(20, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		
		gui.setItem(1, InventoryUtils.createSpecialItem(Material.PAPER, (short) 0, "Info", new String[] {"Fuel input", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		
		owner.openInventory(gui);
		
		if (SQTechBase.currentGui.containsKey(owner)) {
			
			SQTechBase.currentGui.remove(owner);
			SQTechBase.currentGui.put(owner,  this);
			
		} else {
			
			SQTechBase.currentGui.put(owner,  this);
			
		}
		
	}
	
	@Override
	public void click(final InventoryClickEvent event) {
		
		if (event.getClickedInventory() != null) {
			
			if (event.getClickedInventory().getTitle().startsWith(ChatColor.BLUE + "SQTech")) {
				
				event.setCancelled(true);
				
				ItemStack clickedItem = event.getInventory().getItem(event.getSlot());
				
				boolean normalItem = true;
				
				if (clickedItem == null) {
					
					normalItem = false;
					
				} else {
					
					if (clickedItem.hasItemMeta()) {
						
						if (clickedItem.getItemMeta().hasLore()) {
							
							if (clickedItem.getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband")) {
								
								normalItem = false;
								
							}
							
						}
						
					}
					
				}
				
				if (normalItem) {
					
					event.setCancelled(false);
					
				}
				
				if (event.getClickedInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Basic Generator")) {

					if (event.getSlot() == 26) {
				
						GUIBlock guiBlock = ObjectUtils.getMachineFromMachineGUI(this).getGUIBlock();
						
						guiBlock.getGUI(owner).open();
						
					} else if (event.getSlot() == 10) {
						
						event.setCancelled(false);
						
						final Machine machine = ObjectUtils.getMachineFromMachineGUI(this);
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(SQTechEnergy.getPluginMain(), new Runnable() {
							
							public void run() {
								
								if (!event.getClickedInventory().getItem(event.getSlot()).getType().equals(Material.AIR)) {

									for (Fuel fuel : SQTechEnergy.fuels) {
										
										if (fuel.generator.equals(machine.getMachineType().name)) {
											
											if (event.getClickedInventory().getItem(event.getSlot()).getTypeId() == fuel.id) {
												
												if (machine.data.containsKey("fuel")) {
													
													if (machine.data.get("fuel") instanceof HashMap<?,?>) {
														
														HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
														
														if (currentFuels.containsKey(fuel)) {
															
															currentFuels.replace(fuel, currentFuels.get(fuel) + (event.getClickedInventory().getItem(event.getSlot()).getAmount() * fuel.burnTime));
															
														} else {
															
															currentFuels.put(fuel, event.getClickedInventory().getItem(event.getSlot()).getAmount() * fuel.burnTime);
															
														}
														
														event.getClickedInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
														
													}
													
												} else {
													
													machine.data.put("fuel", new HashMap<Fuel, Integer>());
													
													HashMap<Fuel, Integer> currentFuels = (HashMap<Fuel, Integer>) machine.data.get("fuel");
													currentFuels.put(fuel, event.getClickedInventory().getItem(event.getSlot()).getAmount() * fuel.burnTime);
													
													event.getClickedInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
													
												}
												
											}
											
										}
										
									}
									
								}
								
							}
							
						});
		
					}
					
				}
				
			} else {
				
				if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
					
					event.setCancelled(true);
					
				}
				
			}
			
		}	
		
	}
	
}
