
package us.higashiyama.george.SQSpace;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class SQSpace extends JavaPlugin implements Listener {

	public static ArrayList<Player> Players = new ArrayList();

	public void onEnable() {

		getServer().getPluginManager().registerEvents(this, this);
		new TimeReset(this);
	}

	public void onDisable() {

	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		Player p = event.getPlayer();
		String planet = p.getLocation().getWorld().getName();
		if (hasSpaceHelmet(p)) {
			p.setRemainingAir(p.getMaximumAir());
		}
		if ((planet.equals("Defalos")) || (planet.equals("AsteroidBelt")) || (planet.equals("Digitalia")) || (planet.equals("Regalis"))) {

			if ((isInSpace(p)) && (!p.isFlying()) && (p.getGameMode().equals(GameMode.SURVIVAL))) {
				p.setAllowFlight(true);
				p.setFlying(true);
				p.setFlySpeed(0.05F);
			} else if ((!isInSpace(p)) && (p.isFlying()) && (p.getGameMode().equals(GameMode.SURVIVAL))) {
				p.setAllowFlight(false);
				p.setFlying(false);
				p.setFlySpeed(0.1F);
				p.setFallDistance(0.0F);
			}
			if ((isInSpace(p)) && (!hasSpaceHelmet(p)) && (!Players.contains(p)) && (p.getGameMode().equals(GameMode.SURVIVAL))) {
				Players.add(p);
				p.sendMessage(ChatColor.AQUA + "[Space] " + ChatColor.RED + "You are now Suffocating!");
				new SuffocationTask(this, p);
			}
		}
	}

	public boolean hasSpaceHelmet(Player p) {

		if ((p.getInventory().getHelmet() != null) && (p.getInventory().getHelmet().getType() == Material.PUMPKIN)) {
			return true;
		}
		return false;
	}

	public boolean isInSpace(Player p) {

		Location l = p.getLocation();
		boolean air1 = true;
		boolean air2 = true;
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		World w = l.getWorld();
		int height = 40;
		if (w.getName().equalsIgnoreCase("Regalis") && isInSpawn(p)) {
			height = 130;
		}
		for (int i = 0; i < height; i++) {
			int id1 = w.getBlockTypeIdAt(x, y + i + 1, z);
			int id2 = w.getBlockTypeIdAt(x, y - i, z);
			if (id1 != 0) {
				air1 = false;
			}
			if (id2 != 0) {
				air2 = false;
			}
		}
		if ((!air1) && (!air2)) {
			return false;
		}
		return true;
	}

	public boolean isInSpawn(Player p) {

		ApplicableRegionSet s = getWorldGuard().getRegionManager(Bukkit.getServer().getWorld("Regalis")).getApplicableRegions(p.getLocation());
		for (ProtectedRegion r : s) {
			if (r.getId().equals("spawn")) {
				return true;
			}
		}
		return false;
	}

	private WorldGuardPlugin getWorldGuard() {

		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
		if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
			return null;
		}
		return (WorldGuardPlugin) plugin;
	}
}
