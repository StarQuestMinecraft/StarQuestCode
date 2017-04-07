package com.starquestminecraft.sqtechenergy.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.sqtechenergy.objects.SolarPanel;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.EnergyUtils;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;

import net.md_5.bungee.api.ChatColor;

public class SolarPanelGUI extends GUI {
	
	public SolarPanelGUI(Player player, int id) {
		
		super(player, id);
		
	}
	
	@Override
	public void open() {
		
		Inventory gui = Bukkit.createInventory(owner, 9, ChatColor.BLUE + "SQTech - Solar Panel");
		
		Machine machine = ObjectUtils.getMachineFromMachineGUI(this);
		
		String sunlight = ChatColor.RED + "false";
		
		Block middlePanel = machine.getGUIBlock().getLocation().getBlock().getRelative(BlockFace.UP).getRelative(SolarPanel.getFace(machine.getGUIBlock()));
		Block panel1 = middlePanel.getRelative(BlockFace.NORTH);
		Block panel2 = middlePanel.getRelative(BlockFace.SOUTH);
		Block panel3 = middlePanel.getRelative(BlockFace.EAST);
		Block panel4 = middlePanel.getRelative(BlockFace.WEST);
		Block panel5 = middlePanel.getRelative(BlockFace.NORTH_EAST);
		Block panel6 = middlePanel.getRelative(BlockFace.NORTH_WEST);
		Block panel7 = middlePanel.getRelative(BlockFace.SOUTH_EAST);
		Block panel8 = middlePanel.getRelative(BlockFace.SOUTH_WEST);
		
		if (middlePanel.getLightFromSky() == 15 &&
				panel1.getLightFromSky() == 15 &&
				panel2.getLightFromSky() == 15 &&
				panel3.getLightFromSky() == 15 &&
				panel4.getLightFromSky() == 15 &&
				panel5.getLightFromSky() == 15 &&
				panel6.getLightFromSky() == 15 &&
				panel7.getLightFromSky() == 15 &&
				panel8.getLightFromSky() == 15) {
			
			if (middlePanel.getWorld().getTime() <= 12000 || middlePanel.getWorld().getTime() >= 23500) {
				
				sunlight = ChatColor.GREEN + "true";
				gui.setItem(4, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 5, "Energy", new String[] {EnergyUtils.formatEnergy(machine.getEnergy()) + "/" + EnergyUtils.formatEnergy(machine.getMachineType().getMaxEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
				
			}
			
		} else {

			gui.setItem(4, InventoryUtils.createSpecialItem(Material.STAINED_GLASS_PANE, (short) 14, "Energy", new String[] {EnergyUtils.formatEnergy(machine.getEnergy()) + "/" + EnergyUtils.formatEnergy(machine.getMachineType().getMaxEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));

		}
		
		gui.setItem(8, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(0, InventoryUtils.createSpecialItem(Material.PAPER, (short) 0, "Info", new String[] {
				"The solar panel needs direct",
				"access to sunlight to produce energy.",
				"Sunlight: " + sunlight,
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		}));
		
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

				if (event.getClickedInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Solar Panel")) {

					if (event.getSlot() == 8) {

						GUIBlock guiBlock = ObjectUtils.getMachineFromMachineGUI(this).getGUIBlock();

						guiBlock.getGUI(owner).open();

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
