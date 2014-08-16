
package us.higashiyama.george.SQCylum;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Core extends JavaPlugin implements Listener {

	public static Core instance;
	public static HashMap<Player, Vector> velCache = new HashMap<Player, Vector>();
	public static Vector deadVel = new Vector(0, 0, 0);
	public static BlockFace[] blocks = { BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN };
	public static ArrayList<Player> sneakCache = new ArrayList<Player>();

	public void onEnable() {

		instance = this;
		new SneakCheckTask(instance).runTaskTimer(instance, 5, 3);
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void shift(PlayerToggleSneakEvent e) {

		if (e.isSneaking()) {
			sneakCache.add(e.getPlayer());
		} else {
			launchPlayer(e.getPlayer());
			sneakCache.remove(e.getPlayer());
			e.getPlayer().setLevel(0);
			e.getPlayer().setExp(0);
		}
	}

	private void launchPlayer(Player player) {

		Vector v = player.getLocation().getDirection();
		Vector newV = v.multiply(new Vector(player.getExp() * 3, player.getExp() * 3, player.getExp() * 3));
		player.setVelocity(newV);
	}

	@EventHandler
	public void playerMove(PlayerMoveEvent e) {

		Location from = e.getFrom();
		Location to = e.getTo();
		if ((to.getY() != from.getY()) || (to.getZ() != from.getZ()) || (to.getX() != from.getX())) {
			e.getPlayer().teleport(
					new Location(from.getWorld(), from.getX(), from.getY(), from.getZ(), e.getPlayer().getLocation().getYaw(), e.getPlayer().getLocation()
							.getPitch()));
			System.out.println("Just a peek");
			return;
		}

		// Don't use .contains() for efficiency sake
		if (velCache.get(e.getPlayer()) != null) {
			if (isTouchingWall(e.getPlayer())) {
				System.out.println("Touching Wall");
				velCache.put(e.getPlayer(), e.getPlayer().getVelocity());
			} else {
				System.out.println("Not touching Wall");
				e.getPlayer().setVelocity(velCache.get(e.getPlayer()));
			}
		} else {
			System.out.println("Not in cache");
			// If they aren't in the cache
			velCache.put(e.getPlayer(), e.getPlayer().getVelocity());
		}
	}

	private static boolean isTouchingWall(Player player) {

		if (player.getLocation().getX() - player.getLocation().getBlockX() != 0.3 && player.getLocation().getY() - ((int) player.getLocation().getY()) != 0.0
				&& player.getLocation().getZ() - ((int) player.getLocation().getZ()) != 0.3) {
			return false;
		}

		Location[] locs = { player.getLocation(), player.getEyeLocation() };
		for (BlockFace b : blocks) {
			for (Location l : locs) {
				if (l == player.getEyeLocation() && b == BlockFace.DOWN)
					continue;
				if (l == player.getLocation() && b == BlockFace.UP)
					continue;
				if (l.getBlock().getRelative(b).getType() != Material.AIR) {
					return true;
				}
			}
		}

		return false;
	}
}
