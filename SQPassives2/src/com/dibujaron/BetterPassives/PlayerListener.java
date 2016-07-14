package com.dibujaron.BetterPassives;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
	BetterPassives p;
	ArrayList<Player> editingPlayers = new ArrayList<Player>();

	PlayerListener(BetterPassives plugin) {
		this.p = plugin;
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (!Settings.getAllPassives().contains(event.getRightClicked().getType())) {
			return;
		}

		if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.IRON_HOE) {
			if (!this.editingPlayers.contains(event.getPlayer())) {
				LivingEntity entity = (LivingEntity) event.getRightClicked();
				if (entity.getCustomName() == null)
					return;

				event.getPlayer().sendMessage(ChatColor.GREEN + "**********************************");
				event.getPlayer().sendMessage(ChatColor.YELLOW + "Type the entity's new name");
				event.getPlayer().sendMessage(ChatColor.GREEN + "**********************************");
				this.editingPlayers.add(event.getPlayer());
				this.p.setMetadata(event.getPlayer(), "target", event.getRightClicked());
			}
		}

		List<Material> mat = this.p.getTameItems(event.getRightClicked());
		if (mat.size() != 0 && mat.contains(event.getPlayer().getInventory().getItemInMainHand().getType())) {
			LivingEntity entity = (LivingEntity) event.getRightClicked();
			if (entity.getCustomName() != null)
				return;

			entity.getWorld().playEffect(entity.getLocation(), Effect.SMOKE, 20);
			event.getPlayer().sendMessage(ChatColor.YELLOW + "Tamed!");
			String name = this.p.getRandomName(entity);
			entity.setCustomName(name);
			entity.setCustomNameVisible(true);
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (!this.editingPlayers.contains(event.getPlayer()))
			return;

		LivingEntity entity = (LivingEntity) this.p.getMetadata(event.getPlayer(), "target");

		entity.setCustomName(event.getMessage());
		entity.setCustomNameVisible(true);

		event.getPlayer().sendMessage(ChatColor.YELLOW + "Entity's name changed");
		this.editingPlayers.remove(event.getPlayer());
		event.setCancelled(true);
	}
}

/*
 * Location: C:\Users\Drew\Desktop\SQPassives.jar Qualified Name:
 * com.dibujaron.BetterPassives.PlayerListener JD-Core Version: 0.6.2
 */
