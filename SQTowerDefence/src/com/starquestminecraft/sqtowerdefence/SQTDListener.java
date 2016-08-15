package com.starquestminecraft.sqtowerdefence;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.events.MachinesLoadedEvent;
import com.starquestminecraft.sqtechbase.listeners.MovecraftEvents;
import com.starquestminecraft.sqtechbase.objects.Machine;

import net.md_5.bungee.api.ChatColor;

public class SQTDListener implements Listener {
	
	private final SQTowerDefence plugin;
	public static ArrayList<String> stringList = new ArrayList<String>();
	
	public SQTDListener(SQTowerDefence plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		ArrayList<Turret> turretList = com.starquestminecraft.sqtowerdefence.SQTowerDefence.getTurretList();
		for(int i=0; i<turretList.size(); i++) {
			stringList.add(ChatColor.GRAY + turretList.get(i).getName());
			stringList.add(ChatColor.GRAY + "Tower");
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onItemPickup(InventoryClickEvent e) {		
		
		if(stringList.contains(e.getInventory().getName())) {
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onArrowHit(EntityDamageByEntityEvent e) {
		
		Entity arrow = e.getDamager();
		
		if(arrow.getType().equals(EntityType.ARROW)) {
			
			if(arrow.getCustomName() != null) {	
				if(arrow.getCustomName().equals("SQTDProjectile")) {
					
					e.setDamage(arrow.getMetadata("damage").get(0).asDouble());
					e.getDamager().remove();
				
				}
			}
			
		}
		
	}
	
	@EventHandler
	public void onMachinesLoaded(MachinesLoadedEvent e) {
		
		for(Machine m : SQTechBase.machines) {
			
			if(m.data.containsKey("turretData")) {
				
				if(m.check()) {
					
					TowerMachine towerMachine = new TowerMachine(m.getEnergy(), m.getGUIBlock(), m.getMachineType());
					towerMachine.enabled = m.enabled;
					towerMachine.exportsEnergy = m.exportsEnergy;
					towerMachine.importsEnergy = m.importsEnergy;
					SQTechBase.machines.remove(m);
					SQTechBase.machines.add(towerMachine);
					Turret turret = towerMachine.turret;
					turret.runTurret();
					
				}
			}
			
		}
		
	}
	
	
}
