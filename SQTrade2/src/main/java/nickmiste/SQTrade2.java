package nickmiste;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.tools.interfaces.Loader;

import net.md_5.bungee.api.ChatColor;
import nickmiste.dropperTrade.DropperTrade;
import nickmiste.dropperTrade.Util;

public class SQTrade2 extends JavaPlugin implements Listener
{
	public static Common cc3;
	private static SQTrade2 instance;
	
	@Override
	public void onEnable()
	{
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Craftconomy3");
		if (plugin != null)
		{
			cc3 = (Common) ((Loader) plugin).getCommon();
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
//		if (event.getInventory().getName().startsWith(ChatColor.DARK_GREEN + "Trading with "))
//			DirectTrade.onTradeInventoryClick(event);
		if (event.getInventory().getName().equals(ChatColor.GREEN + "Browsing"))
			event.setCancelled(true);
	}
	
//	@EventHandler
//	public void onInventoryClose(InventoryCloseEvent event)
//	{
//		if (event.getInventory().getName().startsWith(ChatColor.DARK_GREEN + "Trading with ") && 
//				event.getInventory().getItem(0).getType().equals(Material.SKULL_ITEM))
//		{
//			Player partner = Bukkit.getPlayer(event.getInventory().getName().substring("**Trading with ".length()));
//			partner.sendMessage(ChatColor.AQUA + "Your trade partner has closed the window.");
//			partner.closeInventory();
//			
//			DirectTrade.refundItems(event.getInventory(), (Player) event.getPlayer(), partner);
//		}
//	}
	
//	@EventHandler
//	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
//	{
//		if (event.getRightClicked() instanceof Player && event.getPlayer().isSneaking())
//			DirectTrade.promptRequester(event.getPlayer(), (Player) event.getRightClicked()); 
//	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		DropperTrade.onPlayerInteract(event);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event)
	{
		DropperTrade.onBlockBreak(event);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
//		if (cmd.getName().equals("trade"))
//		{
//			if (sender instanceof Player)
//			{
//				if (DirectTrade.pending.containsKey((Player) sender))
//				{
//					DirectTrade.promptRequested((Player) sender, Bukkit.getPlayer(args[0]));
//					sender.sendMessage(ChatColor.AQUA + "Trade request sent!");
//					return true;
//				}
//			}
//		}
//		else if (cmd.getName().equals("tradeconfirm"))
//		{
//			if (sender instanceof Player)
//			{
//				if (DirectTrade.pending.containsKey(Bukkit.getPlayer(args[0])) 
//					&& DirectTrade.pending.get(Bukkit.getPlayer(args[0])).equals((Player) sender))
//				{
//					DirectTrade.startTrade(Bukkit.getPlayer(args[0]), (Player) sender);
//					return true;
//				}
//			}
//		}
		
		if (cmd.getName().equalsIgnoreCase("buy"))
		{
			if (sender instanceof Player && Util.pendingAmount.containsKey((Player) sender))
			{
				if (args.length == 1)
				{
					int amount = 0;
					try
					{
						amount = Integer.parseInt(args[0]);
					}
					catch (NumberFormatException e)
					{
						sender.sendMessage(ChatColor.DARK_RED + args[0] + " is not a number!");
						return false;
					}
					Util.completeTransaction((Player) sender, amount);
					return true;
				}
			}
		}
		return false;
	}
	
	public static SQTrade2 getInstance()
	{
		return instance;
	}
}