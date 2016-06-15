package com.gmail.igotburnt.ChestFix;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.lyneira.MachinaCore.ArtificialPlayerInteractEvent;

public class ContainerListener implements Listener

{
	private ChestFix plugin;
	private Checker checker;

	public ContainerListener(ChestFix plugin)
	{
		this.plugin = plugin;
		this.checker = new Checker(plugin);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onBlockInteract(PlayerInteractEvent e)
	{
		if(e instanceof ArtificialPlayerInteractEvent)
		{
			return;
		}
		
		if ((e.isCancelled())
				|| ((e.getAction() != Action.RIGHT_CLICK_BLOCK) && (e.getAction() != Action.LEFT_CLICK_BLOCK)))
			return;
		if (e.getPlayer().hasPermission("chestfix.bypass"))
		{
			return;
		}

		Block b = e.getClickedBlock();

		if ((e.getAction() == Action.LEFT_CLICK_BLOCK) && (this.plugin.getRightClickOnly().contains(b.getType())))
		{
			return;
		}
		
		if ((this.plugin.getInteractBlocks().contains(b.getType()))
				&& (!this.checker.canSee(e.getPlayer(), e.getClickedBlock())))
		{
			sendError(e.getPlayer());
			e.setCancelled(true);
		}
	}

	private void sendError(Player p)
	{
		if (this.plugin.getConfig().getBoolean("message")) {
			p.sendMessage(ChatColor.RED + "[ChestFix] " + ChatColor.YELLOW + "You tried to use something you can't see.");
		}
		if (this.plugin.getHawkEye() != null) {
			uk.co.oliwali.HawkEye.util.HawkEyeAPI.addCustomEntry(this.plugin, "Freecammed through something. ", p, p.getLocation(), "FREECAM");
		}
		if (this.plugin.getConfig().getBoolean("log.server-log")) {
			this.plugin.log.info(p.getName() + " freecammed through something.");
		}
		if (this.plugin.getConfig().getBoolean("notify-mods")) 
		{
			Collection<? extends Player> arrayOfPlayer;
			int j = (arrayOfPlayer = org.bukkit.Bukkit.getOnlinePlayers()).size();
			Object[] playerArray = arrayOfPlayer.toArray();
			for (int i = 0; i < j; i++)
			{
				if(playerArray[i] instanceof Player)
				{
					Player player = (Player) playerArray[i];
					if ((player != p) && (player.hasPermission("chestfix.notify")))
					{
						player.sendMessage(ChatColor.RED + "[ChestFix] " + ChatColor.YELLOW + p.getName() + " used something they couldn't see.  This might be lag or a hack.");
					}
				}
			}
		}
	}
}