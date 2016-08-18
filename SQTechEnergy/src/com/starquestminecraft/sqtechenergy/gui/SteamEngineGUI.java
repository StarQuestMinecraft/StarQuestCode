package com.starquestminecraft.sqtechenergy.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.Fluid;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.util.EnergyUtils;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;
import com.starquestminecraft.sqtechbase.util.ObjectUtils;
import com.starquestminecraft.sqtechenergy.SQTechEnergy;

import net.md_5.bungee.api.ChatColor;

public class SteamEngineGUI extends GUI {
	
	public SteamEngineGUI(Player player, int id) {
		
		super(player, id);
		
	}
	
	@Override
	public void open() {

		Inventory gui = Bukkit.createInventory(owner, 27, ChatColor.BLUE + "SQTech - Steam Engine");

		Machine machine = ObjectUtils.getMachineFromMachineGUI(this);
		
		Fluid f = null;
		//Get the water Fluid object
		for (Fluid f2 : SQTechBase.fluids) {
			if (f2.name.equals("Water")) {
				f = f2;
			}
		}

		//Make sure the machine has a liquid maximum set.
		machine.maxLiquid.put(f, SQTechEnergy.config.getInt("steam engine.max liquid"));
		machine.liquidImports.add(f);
		
		Fluid fluid = null;
		int amount = 0;
		String name = "none";

		for (Fluid f3 : SQTechBase.fluids) {
			if (machine.getLiquid(f3) > 0) {
				fluid = f3;
			}
		}
		
		if (fluid != null) {
			amount = machine.getLiquid(fluid);
			name = fluid.name;
		}
		
		gui.setItem(8, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, "Energy", new String[] {EnergyUtils.formatEnergy(machine.getEnergy()) + "/" + EnergyUtils.formatEnergy(machine.getMachineType().getMaxEnergy()), ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(16, InventoryUtils.createSpecialItem(Material.BUCKET, (short) 0, "Liquid", new String[] {
				ChatColor.BLUE + "Liquid Type: " + name,
				ChatColor.BLUE + "Amount in millibuckets: " + EnergyUtils.formatEnergy(amount) + "/" + EnergyUtils.formatEnergy(SQTechEnergy.config.getInt("steam engine.max liquid")),
				ChatColor.BLUE + "Amount in buckets: " + EnergyUtils.formatEnergy(amount/1000) + "/" + EnergyUtils.formatEnergy(SQTechEnergy.config.getInt("steam engine.max liquid")/1000),
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		}));
		gui.setItem(17, InventoryUtils.createSpecialItem(Material.CHEST, (short) 0, "Remaining fuel", new String[] {
				"Fuel Type: None",
				"Amount left: 0",
				ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"
		}));
		gui.setItem(26, InventoryUtils.createSpecialItem(Material.WOOD_DOOR, (short) 0, "Back", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		
		gui.setItem(0, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(2, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(9, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(11, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(18, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(19, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(20, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		
		gui.setItem(3, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(5, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(12, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(14, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(21, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(22, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		gui.setItem(23, InventoryUtils.createSpecialItem(Material.IRON_FENCE, (short) 0, " ", new String[] {ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));

		gui.setItem(1, InventoryUtils.createSpecialItem(Material.PAPER, (short) 0, "Info", new String[] {"Fuel input", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
		
		gui.setItem(4, InventoryUtils.createSpecialItem(Material.WATER_BUCKET, (short) 0, "Info", new String[] {"Liquid input", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));

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

				if (event.getClickedInventory().getTitle().equals(ChatColor.BLUE + "SQTech - Steam Engine")) {

					if (event.getSlot() == 26) {

						GUIBlock guiBlock = ObjectUtils.getMachineFromMachineGUI(this).getGUIBlock();

						guiBlock.getGUI(owner).open();

					} else if (event.getSlot() == 10) {

						event.setCancelled(false);

					} else if (event.getSlot() == 13) {
						
						event.setCancelled(false);
						
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
