package us.higashiyama.george.SQShops;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if ((sender instanceof Player) && sender.isOp()) {
			if(cmd.getName().equalsIgnoreCase("ecoreload")) {
				itemIndex.clear();
				itemIndex = Database.loadData();
				sender.sendMessage(ChatColor.AQUA + "Economy Values Reloaded");
				return true;
			}
		
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e){
		if(e.getAction() == Action.LEFT_CLICK_BLOCK && 
				(((e.getClickedBlock().getType() == Material.SIGN) ||
				(e.getClickedBlock().getType() == Material.WALL_SIGN) ||
				(e.getClickedBlock().getType() == Material.SIGN_POST)))
				&& (((Sign) e.getClickedBlock().getState()).getLine(0).equals("[CashRegister]"))
				&& e.getPlayer().isOp()
				) {
			Sign s = (Sign) e.getClickedBlock().getState();
			s.setLine(0, ChatColor.AQUA + "Cash Register");
			s.update(true);
		}
		
		if(e.getAction() == Action.LEFT_CLICK_BLOCK && 
				(((e.getClickedBlock().getType() == Material.SIGN) ||
				(e.getClickedBlock().getType() == Material.WALL_SIGN) ||
				(e.getClickedBlock().getType() == Material.SIGN_POST)))
				&& (((Sign) e.getClickedBlock().getState()).getLine(0).equals(ChatColor.AQUA + "Cash Register"))
				) {
			Sign s = (Sign) e.getClickedBlock().getState();
			s.setLine(0, ChatColor.AQUA + "Cash Register");
			s.setLine(1, ChatColor.GREEN + "" + getWorth(s));
			s.setLine(2, ChatColor.AQUA + "Left Click to");
			s.setLine(3, ChatColor.AQUA + "Update Total");
			s.update(true);
		}
		
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && 
				(((e.getClickedBlock().getType() == Material.SIGN) ||
				(e.getClickedBlock().getType() == Material.WALL_SIGN) ||
				(e.getClickedBlock().getType() == Material.SIGN_POST)))
				&& (((Sign) e.getClickedBlock().getState()).getLine(0).equals(ChatColor.AQUA + "Cash Register"))) {
			
				sellItems(e.getPlayer(), (Sign) e.getClickedBlock().getState());
				Sign s = (Sign) e.getClickedBlock().getState();
				s.setLine(0, ChatColor.AQUA + "Cash Register");
				s.setLine(1, ChatColor.GREEN + "0.0");
				s.setLine(2, ChatColor.AQUA + "Left Click to");
				s.setLine(3, ChatColor.AQUA + "Update Total");
				s.update(true);
				
			
		}
		
		
		
		
	}

	private double getWorth(Sign s) {
		double total = 0;
		Location l = new Location(s.getWorld(), s.getLocation().getX(), (s.getLocation().getY()-1), s.getLocation().getZ());
		Chest c = (Chest) l.getBlock().getState();
		Inventory i = c.getInventory();
		
		for(ItemStack finalStack : i.getContents()) {
			if(finalStack == null) continue;
			double quantity = finalStack.getAmount();
			ItemStack checkStack = new ItemStack(finalStack);
			checkStack.setAmount(1);
			if(itemIndex.get(checkStack) == null) continue;
			double price = itemIndex.get(checkStack);
			total = total+(price * quantity);
		}
		return roundTwoDecimals(total);
		
	}

	private void sellItems(Player player, Sign s) {
		ArrayList<ItemStack> leftovers = new ArrayList<ItemStack>();
		double total = 0;
		Location l = new Location(player.getWorld(), s.getLocation().getX(), (s.getLocation().getY()-1), s.getLocation().getZ());
		Chest c = (Chest) l.getBlock().getState();
		Inventory i = c.getInventory();
		
		for(ItemStack finalStack : i.getContents()) {
			if(finalStack == null) continue;
			double quantity = finalStack.getAmount();
			ItemStack checkStack = new ItemStack(finalStack);
			checkStack.setAmount(1);
			if(itemIndex.get(checkStack) == null){
				leftovers.add(finalStack);
				continue;
			}
			double price = itemIndex.get(checkStack);
			Database.updateStats(finalStack, quantity);
			total = total+(price * quantity);
		}
		if(total == 0) return;
		
		economy.depositPlayer(player.getName(), total);
		player.sendMessage(ChatColor.AQUA + "You earned " + roundTwoDecimals(total) + " from selling items.");
		i.clear();
		for(ItemStack leftover : leftovers) {
			i.addItem(leftover);
			player.sendMessage(ChatColor.AQUA + "Some items could not be sold. Check your chest to recover un-sellable items.");
		}
		
		
		
	}


	
	  private boolean setupEconomy()
	  {
	    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
	    if (economyProvider != null) {
	      economy = (Economy)economyProvider.getProvider();
	    }
	    return economy != null;
	  }
	  
	  double roundTwoDecimals(double d) {
      	DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	  	}

	
	
	
}
