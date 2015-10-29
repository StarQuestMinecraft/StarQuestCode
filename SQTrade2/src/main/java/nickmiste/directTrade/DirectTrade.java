//package nickmiste.directTrade;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.inventory.meta.SkullMeta;
//
//import com.comphenix.protocol.PacketType;
//import com.comphenix.protocol.ProtocolLibrary;
//import com.comphenix.protocol.ProtocolManager;
//import com.comphenix.protocol.events.PacketContainer;
//import com.comphenix.protocol.wrappers.WrappedChatComponent;
//
//import net.md_5.bungee.api.ChatColor;
//import nickmiste.SQTrade2;
//
//public class DirectTrade 
//{
//	public static HashMap<Player, Player> pending = new HashMap<Player, Player>(); //requester, requested
//	
//	private static ArrayList<String> contrabandLore;
//	private static ArrayList<String> moneyLore;
//	static
//	{
//		contrabandLore = new ArrayList<String>();
//		contrabandLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
//		moneyLore = new ArrayList<String>();
//		moneyLore.add(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "Click here to add money to this trade" + ChatColor.RESET + "(Coming Soon)");
//		moneyLore.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");	
//	}
//	
//	public static void promptRequester(Player requester, Player requested)
//	{
//		pending.put(requester, requested);
//		Bukkit.getScheduler().scheduleSyncDelayedTask(SQTrade2.getInstance(), new RemoveRequesterTask(requester), 1200);
//		
//		String message = "{text:\"Click here to request to trade with " + requested.getName() + ".\",color:aqua,"
//				+ "clickEvent:{action:run_command,value:\"/trade " + requested.getName() + "\"},"
//				+ "hoverEvent:{action:show_text,value:{text:\"Request to trade with " + requested.getName() + "?\"}}}";
//		
//		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
//		PacketContainer container = new PacketContainer(PacketType.Play.Server.CHAT);
//		container.getChatComponents().write(0, WrappedChatComponent.fromJson(message));
//		try
//		{
//			manager.sendServerPacket(requester, container);
//		}
//		catch (InvocationTargetException e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	public static void promptRequested(Player requester, Player requested)
//	{
//		Bukkit.getScheduler().scheduleSyncDelayedTask(SQTrade2.getInstance(), new RemoveRequesterTask(requester), 1200);
//		
//		String message = "{text:\"" + requester.getName() + " has requested to trade with you. Click here to accept!\",color:aqua,"
//				+ "clickEvent:{action:run_command,value:\"/tradeconfirm " + requester.getName() + "\"},"
//				+ "hoverEvent:{action:show_text,value:{text:\"Trade with " + requester.getName() + "?\"}}}";
//		
//		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
//		PacketContainer container = new PacketContainer(PacketType.Play.Server.CHAT);
//		container.getChatComponents().write(0, WrappedChatComponent.fromJson(message));
//		try
//		{
//			manager.sendServerPacket(requested, container);
//		}
//		catch (InvocationTargetException e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	public static void startTrade(Player requester, Player requested)
//	{
//		Inventory requesterInv = getRequesterInv(requester, requested);
//		requester.openInventory(requesterInv);
//		requested.openInventory(getPartnerInv(requesterInv, requester, requested));
//	}
//	
//	private static Inventory getRequesterInv(Player requester, Player requested)
//	{
//		Inventory inv = Bukkit.createInventory(requester, 54, ChatColor.DARK_GREEN + "Trading with " + requested.getName());
//		
//		ItemStack dividerPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 9);
//		ItemMeta dividerPaneMeta = dividerPane.getItemMeta();
//		dividerPaneMeta.setLore(contrabandLore);
//		dividerPaneMeta.setDisplayName(" ");
//		dividerPane.setItemMeta(dividerPaneMeta);
//		
//		ItemStack requesterHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
//        SkullMeta requesterHeadMeta = (SkullMeta) requesterHead.getItemMeta();
//        requesterHeadMeta.setOwner(requester.getName());
//        requesterHeadMeta.setLore(contrabandLore);
//        requesterHeadMeta.setDisplayName(ChatColor.GOLD + requester.getName());
//        requesterHead.setItemMeta(requesterHeadMeta);
//        
//        ItemStack requestedHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
//        SkullMeta requestedHeadMeta = (SkullMeta) requestedHead.getItemMeta();
//        requestedHeadMeta.setOwner(requested.getName());
//        requestedHeadMeta.setLore(contrabandLore);
//        requestedHeadMeta.setDisplayName(ChatColor.GOLD + requested.getName());
//        requestedHead.setItemMeta(requestedHeadMeta);
//        
//        ItemStack requesterMoney = new ItemStack(Material.EMERALD, 0);
//        ItemMeta requesterMoneyMeta = requesterMoney.getItemMeta();
//        requesterMoneyMeta.setLore(moneyLore);
//        requesterMoneyMeta.setDisplayName(ChatColor.GREEN + "0 credits");
//        requesterMoney.setItemMeta(requesterMoneyMeta);
//        
//        ItemStack requestedMoney = new ItemStack(Material.EMERALD, 0);
//        ItemMeta requestedMoneyMeta = requestedMoney.getItemMeta();
//        requestedMoneyMeta.setLore(moneyLore);
//        requestedMoneyMeta.setDisplayName(ChatColor.GREEN + "0 credits");
//        requestedMoney.setItemMeta(requestedMoneyMeta);
//        
//        ItemStack confirm = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
//        ItemMeta confirmMeta = confirm.getItemMeta();
//        confirmMeta.setLore(contrabandLore);
//        confirmMeta.setDisplayName(ChatColor.DARK_GREEN + "CONFIRM");
//        confirm.setItemMeta(confirmMeta);
//        
//		inv.setItem(0, requesterHead);
//		inv.setItem(8, requestedHead);
//		for (int i = 1; i < 8; i++)
//			inv.setItem(i, dividerPane);
//		for (int i = 9; i < 45; i++)
//			if (i%9 == 0 || (i+1)%9 == 0 || (i+5)%9 == 0)
//				inv.setItem(i, dividerPane);
//		inv.setItem(45, requesterMoney);
//		inv.setItem(46, dividerPane);
//		inv.setItem(47, dividerPane);
//		inv.setItem(48, confirm);
//		inv.setItem(49, dividerPane);
//		inv.setItem(50, confirm);
//		inv.setItem(51, dividerPane);
//		inv.setItem(52, dividerPane);
//		inv.setItem(53, requestedMoney);
//		
//		return inv;
//	}
//	
//	public static Inventory getPartnerInv(Inventory mainInv, Player main, Player partner)
//	{
//		Inventory inv = Bukkit.createInventory(partner, 54, ChatColor.DARK_GREEN + "Trading with " + main.getName());
//		
//		for (int i = 0; i < 54; i++)
//		{
//			inv.setItem(i, mainInv.getItem(((9*(i/9)+8) -i) + (i/9)*9)); //Reflects the chest vertically
//			
//			if (inv.getItem(i) != null)
//			{
//				if (isAllowedSlot(i))
//				{
//					ItemMeta meta = inv.getItem(i).getItemMeta();
//					List<String> lore = meta.getLore();
//					lore.remove(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
//					meta.setLore(lore);
//					inv.getItem(i).setItemMeta(meta);
//				}
//				else
//				{
//					ItemMeta meta = inv.getItem(i).getItemMeta();
//					meta.setLore(contrabandLore);
//					inv.getItem(i).setItemMeta(meta);
//				}
//			}
//		}
//		SkullMeta requestedHeadMeta = (SkullMeta) inv.getItem(0).getItemMeta();
//		requestedHeadMeta.setOwner(partner.getName());
//		inv.getItem(0).setItemMeta(requestedHeadMeta);
//		
//		SkullMeta requesterHeadMeta = (SkullMeta) inv.getItem(0).getItemMeta();
//		requesterHeadMeta.setOwner(partner.getName());
//		inv.getItem(0).setItemMeta(requesterHeadMeta);
//		
//		return inv;
//	}
//	
//	public static void onTradeInventoryClick(InventoryClickEvent event)
//	{
//		if (event.getCurrentItem() != null)
//			if (event.getClickedInventory() != null)
//				if (event.getInventory().equals(event.getClickedInventory()))
//					if (event.getCurrentItem().hasItemMeta())
//						if (event.getCurrentItem().getItemMeta().hasLore())
//							if (event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"))
//								event.setCancelled(true);
//		
//
//		Bukkit.getScheduler().scheduleSyncDelayedTask(SQTrade2.getInstance(), new Runnable()
//				{
//					private InventoryClickEvent event;
//					public Runnable setEvent(InventoryClickEvent event)
//					{
//						this.event = event;
//						return this;
//					}
//					
//					@Override
//					public void run()
//					{
//						Player partner = Bukkit.getPlayer(event.getInventory().getName().substring("**Trading with ".length()));
//						if (partner.getOpenInventory() != null && partner.getOpenInventory().getTopInventory() != null &&
//								partner.getOpenInventory().getTopInventory().getName().startsWith(ChatColor.DARK_GREEN + "Trading with "))
//						{
//							partner.getOpenInventory().getTopInventory().setContents(DirectTrade.getPartnerInv(
//									event.getInventory(), (Player) event.getWhoClicked(), partner).getContents());
//							
//							if (event.getInventory().equals(event.getClickedInventory()))
//							{
//								if (isAllowedSlot(event.getSlot()) && !isInPhase2(event.getInventory()))
//								{
//									ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
//									ItemMeta meta = stack.getItemMeta();
//									meta.setDisplayName(ChatColor.DARK_GREEN + "CONFIRM");
//									meta.setLore(contrabandLore);
//									stack.setItemMeta(meta);
//									event.getInventory().setItem(48, stack);
//									event.getInventory().setItem(50, stack);
//								}
//							}
//						}
//					}
//				}.setEvent(event), 20);
//		
//		if (event.getInventory().equals(event.getClickedInventory()))
//		{
//			if (event.getSlot() == 48) //CONFIRM
//			{
//				//5 is LIME : 1 is ORANGE : 14 is RED : 10 is PURPLE
//				if (event.getCurrentItem().getDurability() == 5)
//				{
//					if (event.getInventory().getItem(50).getDurability() ==  1)
//					{
//						setupConfirm2(event.getInventory());
//					}
//					else
//					{
//						ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
//						ItemMeta meta = stack.getItemMeta();
//						meta.setDisplayName(ChatColor.GOLD + "READY");
//						meta.setLore(contrabandLore);
//						stack.setItemMeta(meta);
//						event.getInventory().setItem(48, stack);
//					}
//				}
//				else if (event.getCurrentItem().getDurability() == 1)
//				{
//					ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
//					ItemMeta meta = stack.getItemMeta();
//					meta.setDisplayName(ChatColor.DARK_GREEN + "CONFIRM");
//					meta.setLore(contrabandLore);
//					stack.setItemMeta(meta);
//					event.getInventory().setItem(48, stack);
//				}
//				else if (event.getCurrentItem().getDurability() == 14)
//				{
//					if (event.getInventory().getItem(50).getDurability() ==  10)
//					{
//						Player partner = Bukkit.getPlayer(event.getInventory().getName().substring("**Trading with ".length()));
//						event.getInventory().setItem(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10));
//						partner.getInventory().setItem(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10));
//						giveItems(event.getInventory(), (Player) event.getWhoClicked(), partner);
//					}
//					else
//					{
//						ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
//						ItemMeta meta = stack.getItemMeta();
//						meta.setDisplayName(ChatColor.DARK_PURPLE + "CONFIRMED");
//						meta.setLore(contrabandLore);
//						stack.setItemMeta(meta);
//						event.getInventory().setItem(48, stack);
//					}
//				}
//				else if (event.getCurrentItem().getDurability() == 10)
//				{
//					ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
//					ItemMeta meta = stack.getItemMeta();
//					meta.setDisplayName(ChatColor.RED + "CONFIRM AGAIN");
//					meta.setLore(contrabandLore);
//					stack.setItemMeta(meta);
//					event.getInventory().setItem(48, stack);
//				}
//			}
//			else
//				event.setCancelled(true);
//		}
//	}
//	
//	private static void giveItems(Inventory inv, Player player, Player partner)
//	{
//		for (int i = 0; i < 54; i++)
//		{
//			if (isAllowedSlot(i))
//			{
//				ItemStack stack = inv.getItem(i);
//				ItemMeta meta = stack.getItemMeta();
//				if (meta.hasDisplayName())
//					if (meta.getDisplayName().equals(ChatColor.MAGIC + ":"))
//						continue;
//				if (meta.hasLore())
//				{
//					List<String> lore = meta.getLore();
//					lore.remove(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
//					meta.setLore(lore);
//				}
//				partner.getInventory().addItem(stack);
//			}
//			else if (isPartnerAllowedSlot(i))
//			{
//				ItemStack stack = inv.getItem(i);
//				ItemMeta meta = stack.getItemMeta();
//				if (meta.hasDisplayName())
//					if (meta.getDisplayName().equals(ChatColor.MAGIC + ":"))
//						continue;
//				if (meta.hasLore())
//				{
//					List<String> lore = meta.getLore();
//					lore.remove(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
//					meta.setLore(lore);
//				}
//				player.getInventory().addItem(stack);
//			}
//		}
//		partner.closeInventory();
//		player.closeInventory();
//	}
//	
//	public static void refundItems(Inventory inv, Player player, Player partner)
//	{
//		for (int i = 0; i < 54; i++)
//		{
//			if (isAllowedSlot(i))
//			{
//				ItemStack stack = inv.getItem(i);
//				ItemMeta meta = stack.getItemMeta();
//				if (meta.hasDisplayName())
//					if (meta.getDisplayName().equals(ChatColor.MAGIC + ":"))
//						continue;
//				if (meta.hasLore())
//				{
//					List<String> lore = meta.getLore();
//					lore.remove(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
//					meta.setLore(lore);
//				}
//				player.getInventory().addItem(stack);
//			}
//			else if (isPartnerAllowedSlot(i))
//			{
//				ItemStack stack = inv.getItem(i);
//				ItemMeta meta = stack.getItemMeta();
//				if (meta.hasDisplayName())
//					if (meta.getDisplayName().equals(ChatColor.MAGIC + ":"))
//						continue;
//				if (meta.hasLore())
//				{
//					List<String> lore = meta.getLore();
//					lore.remove(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
//					meta.setLore(lore);
//				}
//				partner.getInventory().addItem(stack);
//			}
//		}
//	}
//	
//	private static void setupConfirm2(Inventory inv)
//	{
//		ItemStack dividerPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
//		ItemMeta dividerPaneMeta = dividerPane.getItemMeta();
//		dividerPaneMeta.setLore(contrabandLore);
//		dividerPaneMeta.setDisplayName(ChatColor.MAGIC + ":");
//		dividerPane.setItemMeta(dividerPaneMeta);
//		
//		ItemStack dividerPaneCenter = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 9);
//		ItemMeta dividerPaneCenterMeta = dividerPaneCenter.getItemMeta();
//		dividerPaneCenterMeta.setLore(contrabandLore);
//		dividerPaneCenterMeta.setDisplayName(" ");
//		dividerPaneCenter.setItemMeta(dividerPaneCenterMeta);
//		
//		ItemStack confirm = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
//        ItemMeta confirmMeta = confirm.getItemMeta();
//        confirmMeta.setLore(contrabandLore);
//        confirmMeta.setDisplayName(ChatColor.RED + "CONFIRM AGAIN");
//        confirm.setItemMeta(confirmMeta);
//		
//		dividerPane.setItemMeta(dividerPaneMeta);
//		for (int i = 1; i < 8; i++)
//			inv.setItem(i, dividerPane);
//		for (int i = 9; i < 45; i++)
//		{
//			if (i%9 == 0 || (i+1)%9 == 0)
//				inv.setItem(i, dividerPane);
//			if ((i+5)%9 == 0)
//				inv.setItem(i, dividerPaneCenter);
//		}
//		inv.setItem(45, dividerPane);
//		inv.setItem(46, dividerPane);
//		inv.setItem(47, dividerPane);
//		inv.setItem(48, confirm);
//		inv.setItem(49, dividerPane);
//		inv.setItem(50, confirm);
//		inv.setItem(51, dividerPane);
//		inv.setItem(52, dividerPane);
//		inv.setItem(53, dividerPane);
//		
//		for (int i = 0; i < 54; i++)
//		{
//			if (inv.getItem(i) != null)
//			{
//				ItemMeta meta = inv.getItem(i).getItemMeta();
//				meta.setLore(contrabandLore);
//				inv.getItem(i).setItemMeta(meta);
//			}
//			else
//				inv.setItem(i, dividerPane);
//		}
//	}
//	
//	private static boolean isAllowedSlot(int slot)
//	{
//		 int[] allowedSlots = {10, 11, 12, 19, 20, 21, 28, 29, 30, 37, 38, 39};
//		 for (int testSlot : allowedSlots)
//			if (testSlot == slot)
//				return true;
//		 return false;
//	}
//	
//	private static boolean isPartnerAllowedSlot(int slot)
//	{
//		 int[] allowedSlots = {14, 15, 16, 22, 23, 24, 31, 32, 33, 40, 41, 42};
//		 for (int testSlot : allowedSlots)
//			if (testSlot == slot)
//				return true;
//		 return false;
//	}
//	
//	public static boolean isInPhase2(Inventory inv)
//	{
//		return inv.getItem(1).getDurability() == 11;
//	}
//}
//
//class RemoveRequesterTask implements Runnable
//{
//	private Player player;
//	
//	public RemoveRequesterTask(Player player) 
//	{
//		this.player = player;
//	}
//	
//	@Override
//	public void run()
//	{
//		DirectTrade.pending.remove(player);
//	}
//}