package nickmiste.dropperTrade;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Dropper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import nickmiste.SQTrade2;

public class DropperTrade
{
	public static void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{	
			if (event.getClickedBlock().getType().equals(Material.DROPPER))
			{
				Sign sign = Util.getSign(event.getClickedBlock().getLocation());
				if (sign != null)
				{
					if ((sign.getLine(0).equals(ChatColor.DARK_GREEN + "Dropper Shop") &&
							!sign.getLine(3).substring(2).equals(event.getPlayer().getName())) ||
							(sign.getLine(0).equals(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "Dropper Shop") &&
							sign.getLine(2).equals(ChatColor.DARK_RED + "Dropper Locked")))
					{
						event.getPlayer().sendMessage(ChatColor.DARK_RED + "You must break the shop sign and wait 45 seconds to get to this dropper!");
						event.setCancelled(true);
					}
				}
			}
			else if (event.getClickedBlock().getType().equals(Material.WALL_SIGN))
			{
				Sign sign = (Sign) (event.getClickedBlock().getState());
				
				if (sign.getLine(0).equals("[shop]"))	
				{
					if (Util.isValid(event.getClickedBlock().getLocation()))
					{
						int price = 0;
						try
						{
							price = Integer.parseInt(sign.getLine(1));
						}
						catch (NumberFormatException e)
						{
							event.getPlayer().sendMessage(ChatColor.DARK_RED + "Please enter a valid price on the second line.");
							return;
						}
						if (price > 50000)
						{
							event.getPlayer().sendMessage(ChatColor.DARK_RED + "The price per item may not exceed 50000 credits.");
							return;
						}
						if (price < 0)
						{
							event.getPlayer().sendMessage(ChatColor.DARK_RED + "The price per item must not be negative.");
							return;
						}
						
						Dropper dropper = Util.getDropper(event.getClickedBlock().getLocation());
						ArrayList<ItemStack> inv = new ArrayList<ItemStack>(Arrays.asList(dropper.getInventory().getContents()));		
						while (inv.remove(null));
						ItemStack stack = inv.get(0);
						
						sign.setLine(0, ChatColor.DARK_GREEN + "Dropper Shop");
						sign.setLine(2, ChatColor.DARK_RED + sign.getLine(1) + "c each");
						sign.setLine(1, ChatColor.DARK_RED + "" + Util.getAmount(sign.getLocation()) + " " + stack.getType().toString());
						sign.setLine(3, ChatColor.DARK_GREEN + event.getPlayer().getName());
						sign.update();
					}
					else
						event.getPlayer().sendMessage(ChatColor.DARK_RED + "Improperly built shop! Please ensure the sign is on the side of a dropper with a single type of item to sell in it.");
				}
				else if (sign.getLine(0).equals(ChatColor.DARK_GREEN + "Dropper Shop"))
				{
					if (Util.isValid(event.getClickedBlock().getLocation()))
					{
						if (event.getPlayer().isSneaking())
							Util.viewContents(event.getPlayer(), event.getClickedBlock().getLocation());
						
						int amount = Integer.parseInt(sign.getLine(1).split(" ")[0].substring(2));
						
						if (Util.getAmount(sign.getLocation()) == amount)
						{
							Util.pendingAmount.remove(event.getPlayer());
							event.getPlayer().sendMessage(ChatColor.AQUA + "Type \"/buy <amount>\" to complete your transaction.");
							Util.pendingAmount.put(event.getPlayer(), event.getClickedBlock().getLocation());
							Bukkit.getScheduler().scheduleSyncDelayedTask(SQTrade2.getInstance(), new Runnable()
									{
										private Player player;
										
										@Override
										public void run()
										{
											Util.pendingAmount.remove(player);
										}
										
										public Runnable setPlayer(Player player)
										{
											this.player = player;
											return this;
										}
									}.setPlayer(event.getPlayer()), 1200);
						}
						else event.getPlayer().sendMessage(ChatColor.DARK_RED + "Illegally modified shop!");
					}
					else event.getPlayer().sendMessage(ChatColor.DARK_RED + "Improperly built shop!");
				}
				else if (sign.getLine(0).equals(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "Dropper Shop"))
				{
					if (sign.getLine(3).substring(4).equals(event.getPlayer().getName()))
					{
						int amount = Integer.parseInt(sign.getLine(1).split("G")[0].substring(4));
						int price = Integer.parseInt(sign.getLine(1).split("G")[1]);
						
						if (Util.getType(event.getClickedBlock().getLocation()) == null)
						{
							sign.setLine(0, ChatColor.DARK_GREEN + "Dropper Shop");
							sign.setLine(1, ChatColor.RED + "OUT");
							sign.setLine(2, ChatColor.RED + "OF");
							sign.setLine(3, ChatColor.RED + "STOCK");
							sign.update();
						}
						else
						{
							sign.setLine(0, ChatColor.DARK_GREEN + "Dropper Shop");
							sign.setLine(1, ChatColor.DARK_RED + "" + amount + " " + Util.getType(event.getClickedBlock().getLocation()));
							sign.setLine(2, ChatColor.DARK_RED + "" + price + "c each");
							sign.setLine(3, ChatColor.DARK_GREEN + event.getPlayer().getName());
							sign.update();
						}
					}
				}
			}
		}
	}
	
	public static void onBlockBreak(BlockBreakEvent event)
	{	
		if (!event.getPlayer().hasPermission("sqtrade.breakshops"))
		{
			if (event.getBlock().getType().equals(Material.DROPPER))
			{
				Sign sign = Util.getSign(event.getBlock().getLocation());
				
				if (sign != null)
				{
					if (!sign.getLine(3).substring(2).equals(event.getPlayer().getName()))
					{
						if (!Util.isValid(sign.getLocation()))
						{
							event.getPlayer().sendMessage(ChatColor.AQUA + "You were able to break this shop because it was invalid.");
							return;
						}
						if (sign != null)
						{
							if (sign.getLine(0).equals(ChatColor.DARK_GREEN + "Dropper Shop") &&
									!sign.getLine(3).substring(2).equals(event.getPlayer().getName()))
							{
								if (Bukkit.getPlayer(sign.getLine(3).substring(2)).isOnline())	
								{
									Bukkit.getScheduler().scheduleSyncDelayedTask(SQTrade2.getInstance(), new SignCooldownTask(sign), 900);
									event.setCancelled(true);
								}
								else event.getPlayer().sendMessage(ChatColor.AQUA + "You were able to break this shop because its owner is offline.");
							}
							else if (sign.getLine(0).equals(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "Dropper Shop") &&
									sign.getLine(2).equals(ChatColor.DARK_RED + "Dropper Locked"))
							{
								event.getPlayer().sendMessage(ChatColor.DARK_RED + "This dropper is still locked! Please wait.");
								event.setCancelled(true);
							}
						}
					}
				}
			}
			else if (event.getBlock().getType().equals(Material.WALL_SIGN))
			{
				if (!((Sign) event.getBlock().getState()).getLine(3).substring(2).equals(event.getPlayer().getName()))
				{
					if (Util.isDropperShopSign((Sign) event.getBlock().getState()))
					{
						if (Util.hasDropper(event.getBlock().getLocation()))
						{
							if (((Sign) event.getBlock().getState()).getLine(0).equals(ChatColor.DARK_GREEN + "Dropper Shop"))
							{
								if (Util.isValid(event.getBlock().getLocation()))
								{
									if (Bukkit.getPlayer(((Sign) event.getBlock().getState()).getLine(3).substring(2)).isOnline())
									{
										Bukkit.getScheduler().scheduleSyncDelayedTask(SQTrade2.getInstance(), 
												new SignCooldownTask((Sign) event.getBlock().getState()), 900);
										event.setCancelled(true);
									}
									else event.getPlayer().sendMessage(ChatColor.AQUA + "You were able to break this shop because its owner is offline.");
								}
								else event.getPlayer().sendMessage(ChatColor.AQUA + "You were able to break this shop because it was invalid.");
							}
							else if (((Sign) event.getBlock().getState()).getLine(0).equals(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "Dropper Shop") &&
									((Sign) event.getBlock().getState()).getLine(2).equals(ChatColor.DARK_RED + "Dropper Locked"))
								event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}