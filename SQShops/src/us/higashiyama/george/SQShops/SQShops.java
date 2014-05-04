package us.higashiyama.george.SQShops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SQShops extends JavaPlugin implements Listener {

	public static HashMap<ItemStack, Double> itemIndex = new HashMap<ItemStack, Double>();
	public static Economy economy = null;

	
	public void onEnable() {
		setupEconomy();
		Database.setUp();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		itemIndex = Database.loadData();
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e){
	/*
		if(e.getAction() == Action.LEFT_CLICK_BLOCK && 
				(((e.getClickedBlock().getType() == Material.SIGN) ||
				(e.getClickedBlock().getType() == Material.WALL_SIGN) ||
				(e.getClickedBlock().getType() == Material.SIGN_POST)))
				&& (((Sign) e.getClickedBlock().getState()).getLine(0).equals(ChatColor.AQUA + "Cash Register"))) {
		}
		*/
		if(e.getAction() == Action.LEFT_CLICK_BLOCK && 
				(((e.getClickedBlock().getType() == Material.SIGN) ||
				(e.getClickedBlock().getType() == Material.WALL_SIGN) ||
				(e.getClickedBlock().getType() == Material.SIGN_POST)))
				) {
			Sign s = (Sign) e.getClickedBlock().getState();
			s.setLine(0, ChatColor.AQUA + "Cash Register");
		}
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && 
				(((e.getClickedBlock().getType() == Material.SIGN) ||
				(e.getClickedBlock().getType() == Material.WALL_SIGN) ||
				(e.getClickedBlock().getType() == Material.SIGN_POST)))
				&& (((Sign) e.getClickedBlock().getState()).getLine(0).equals(ChatColor.AQUA + "Cash Register"))) {
			
				sellItems(e.getPlayer(), (Sign) e.getClickedBlock().getState());
				
				
			
		}
		
		
		
		
	}

	private void sellItems(Player player, Sign s) {
		double total = 0;
		Location l = new Location(player.getWorld(), s.getLocation().getX(), (s.getLocation().getY()-1), s.getLocation().getZ());
		Chest c = (Chest) l.getBlock().getState();
		Inventory i = c.getInventory();
		
		for(ItemStack finalStack : i.getContents()) {
			double quantity = finalStack.getAmount();
			ItemStack checkStack = new ItemStack(finalStack);
			checkStack.setAmount(1);
			double price = itemIndex.get(checkStack);
			Database.updateStats(finalStack, quantity);
			total = total+price;
		}
		economy.depositPlayer(player.getName(), total);
		
		i.clear();
		player.sendMessage("You sold stuff.");
		
	}


	
	  private boolean setupEconomy()
	  {
	    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
	    if (economyProvider != null) {
	      economy = (Economy)economyProvider.getProvider();
	    }
	    return economy != null;
	  }


	
	
	
}
