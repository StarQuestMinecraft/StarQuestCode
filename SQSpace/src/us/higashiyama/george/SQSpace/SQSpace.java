
package us.higashiyama.george.SQSpace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SQSpace extends JavaPlugin implements Listener {

	public static ArrayList<Player> Players = new ArrayList<Player>();	
	public static List<Player> noSuffacatePlayers = new ArrayList<Player>();
	
	public static List<String> spaceWorlds = new ArrayList<String>();
	
	public static SQSpace instance;

	@Override
	public void onDisable() {
		saveDefaultConfig();	
	}

	@Override
	public void onEnable() {
		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
			saveConfig();
		}	
		
		spaceWorlds = getConfig().getStringList("systems");
		instance = this;
		boolean enabled = false;
		for (World world : Bukkit.getServer().getWorlds()) {
			if (spaceWorlds.contains(world.getName().toLowerCase())) {
				enabled = true;				
			}
		}

		if (enabled) {
			System.out.println("Events registered!");
			this.getServer().getPluginManager().registerEvents(this, this);
		}
	}

	// Adding headshot support
	@EventHandler
	public void arrowDamager(EntityDamageByEntityEvent e) {

		// Making sure the entity that did the damage was an arrow
		if ((e.getDamager() instanceof Arrow) && (e.getEntity() instanceof Player)) {
			final Entity arrow = e.getDamager();
			final Player p = (Player) e.getEntity();
			// Sneaking changes height of the player's head
			final double headHeight = (p.isSneaking()) ? 1.46 : 1.62;

			if (arrow.getLocation().getY() > (p.getLocation().getY() + headHeight)) {
				// Replacing their pumpkin with air
				if (p.getInventory().getHelmet().getType() == Material.PUMPKIN) {
					p.getInventory().setHelmet(new ItemStack(Material.AIR));
					// Refunding them the jack o lantern
					if (p.getInventory().firstEmpty() > -1) {
						p.getInventory().addItem(new ItemStack(Material.JACK_O_LANTERN, 1));
					} else {
						p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.JACK_O_LANTERN, 1));
					}
					((Player) ((Arrow) arrow).getShooter()).sendMessage(ChatColor.RED + "Headshot!");
				}
			}
		}
	}

	boolean checkIfSuffocating(Player p) {

		// WARNING: This check assumes you have already checked that the player
		// is in a "Space" area.
		// It is only a Respiration / Perm check
		boolean suffocating = false;
		if (!p.hasPermission("SQSpace.nosuffocate") && !(p.getGameMode().equals(GameMode.CREATIVE)) && (isInSpace(p)) && (!this.hasSpaceArmor(p) && !noSuffacatePlayers.contains(p))
				&& (!Players.contains(p))) {
			Players.add(p);
			p.sendMessage(ChatColor.AQUA + "[Space] " + ChatColor.RED + "You are now Suffocating!");
			new SuffocationTask(this, p);
			suffocating = true;
		}
		return suffocating;
	}

	public boolean hasSpaceArmor(Player p) {

		/*final ItemStack helmet = p.getInventory().getHelmet();
		if (helmet != null) {
			// Can breathe in space wearing a Pumpkin (Space Helmet)
			// Also can breathe in space if helmet has Respiration III
			if ((helmet.getType() == Material.PUMPKIN))
				return true;
		}*/
		ItemStack[] armor = p.getInventory().getArmorContents();
		if(armor == null) return false;
		for(ItemStack i : armor){
			if(i == null) return false;
			if(!isArmor(i)){
				return false;
			}
		}
		return true;
	}
	
	private boolean isArmor(ItemStack item){
		Material m = item.getType();
		boolean armor = false;
		if (m == Material.CHAINMAIL_HELMET ||  m == Material.CHAINMAIL_CHESTPLATE ||  m == Material.CHAINMAIL_LEGGINGS || m ==  Material.CHAINMAIL_BOOTS) {
			armor = true;
		}
		if (item.hasItemMeta()) {
			if (item.getItemMeta().hasLore()) {
				if (item.getItemMeta().getLore().contains(ChatColor.DARK_PURPLE + "Power Tool")) {
					armor = true;
				}
			}
		}
		return armor;
	}

	public static boolean isInSpace(Entity e) {

		final Location l = e.getLocation();
		boolean air1 = true;
		boolean air2 = true;
		final int x = l.getBlockX();
		final int y = l.getBlockY();
		final int z = l.getBlockZ();
		final World w = l.getWorld();
		int height = 40;
		for (int i = 0; i < height; i++) {
			final int id1 = w.getBlockTypeIdAt(x, y + i + 1, z);
			final int id2 = w.getBlockTypeIdAt(x, y - i, z);
			if (id1 != 0) {
				air1 = false;
			}
			if (id2 != 0) {
				air2 = false;
			}
		}
		if ((!air1) && (!air2))
			return false;
		return true;
	}

	@EventHandler
	public void onHelmetChange(final InventoryClickEvent event) {

		if (event.getSlotType().equals(SlotType.ARMOR) && (103 == event.getSlot())) {
			this.checkIfSuffocating((Player) event.getWhoClicked());
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		final Player p = event.getPlayer();
		final String planet = p.getLocation().getWorld().getName().toLowerCase();
		if (this.hasSpaceArmor(p)) {
			p.setRemainingAir(p.getMaximumAir());
		}
		if (spaceWorlds.contains(planet)){
			
			if(p.getGameMode().equals(GameMode.SURVIVAL)){
				if ((isInSpace(p)) && (!p.isFlying()) && (p.getLocation().getY() < 256)) {
					p.setAllowFlight(true);
					p.setFlying(true);
					p.setFlySpeed(0.02F);
				} else if ((!isInSpace(p)) && (p.isFlying())) {
					p.setAllowFlight(false);
					p.setFlying(false);
					p.setFlySpeed(0.1F);
					p.setFallDistance(0.0F);
				}
				if(p.isFlying() && p.isSprinting()){
						p.setSprinting(false);
				}
				if(p.getLocation().getY() > 256)
				{
					Location new_Y = p.getLocation().add(0, -1, 0);
					p.teleport(new_Y);
				}
				else if(p.getLocation().getY() < 0)
				{
					Location new_Y = p.getLocation().add(0, 1, 0);
					p.teleport(new_Y);
				}
			}
		
			this.checkIfSuffocating(p);
		}
	}
	
	@EventHandler
	public void onItemDrop(ItemSpawnEvent event) {
	
		String planet = event.getEntity().getWorld().getName().toLowerCase();
		
		if (spaceWorlds.contains(planet)) {
			
			event.getEntity().setGravity(false);
			
		}

	}
	
}
