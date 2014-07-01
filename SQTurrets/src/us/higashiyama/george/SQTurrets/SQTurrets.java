
package us.higashiyama.george.SQTurrets;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import us.higashiyama.george.SQTurrets.Utils.AntiGravityTask;
import us.higashiyama.george.SQTurrets.Utils.TurretManager;

public class SQTurrets extends JavaPlugin implements Listener {

	public static HashMap<Player, Turret> userMap = new HashMap<Player, Turret>();
	public static ArrayList<Turret> turretTypes = new ArrayList<Turret>();
	public static SQTurrets instance;
	public static ArrayList<Arrow> arrowList = new ArrayList<Arrow>();

	public void onEnable() {

		instance = this;
		instance.saveDefaultConfig();
		TurretManager.loadTurrets();
		for (String name : TurretManager.turrets.keySet()) {
			System.out.println("Welcome to the jungle");
			System.out.println(name);
			Turret turret = null;
			try {

				turret = (Turret) TurretManager.turrets.get(name).newInstance();
				turret.setName(name);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Turret t = loadTurret(turret);
			System.out.println(t.toString());
			turretTypes.add(t);

		}

		new AntiGravityTask();

		getServer().getPluginManager().registerEvents(this, this);
	}

	private static Turret loadTurret(Turret t) {

		String name = t.getName().toLowerCase();
		t.setPermission(instance.getConfig().getString(name + ".permission"));
		t.setCooldown(instance.getConfig().getLong(name + ".cooldown"));
		t.setAmmo(new ItemStack(instance.getConfig().getInt(name + ".ammo.id"), instance.getConfig().getInt(name + ".ammo.quantity")));
		List<Integer> base = instance.getConfig().getIntegerList(name + ".basematerials");
		ArrayList<Material> baseMaterials = new ArrayList<Material>();
		for (int sId : base) {
			baseMaterials.add(Material.getMaterial(sId));
		}

		List<Integer> top = instance.getConfig().getIntegerList(name + ".topmaterials");
		ArrayList<Material> topMaterials = new ArrayList<Material>();
		for (int sId : top) {
			topMaterials.add(Material.getMaterial(sId));
		}

		t.setBaseMaterials(baseMaterials);
		t.setTopMaterials(topMaterials);
		return t;
	}

	@EventHandler
	public void onPlayerInteractSign(PlayerInteractEvent event) {

		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType() == Material.WALL_SIGN)) {
			Sign s = (Sign) event.getClickedBlock().getState();

			// Begin checking if the turret is a registered type and is an
			// uncreated turret
			for (Turret t : turretTypes) {
				if (s.getLine(0).equalsIgnoreCase("[" + t.getName() + "]")) {
					s.setLine(0, ChatColor.BLUE + t.getName().toUpperCase());
					s.setLine(1, ChatColor.GREEN + "UNOCCUPIED");
					s.update();
					return;
				}
			}

			// Checking an existing turret construction so the player can enter
			// a turret
			for (Turret t : turretTypes) {
				System.out.println("Iterationz");
				if ((s.getLine(0).equalsIgnoreCase(ChatColor.BLUE + t.getName().toUpperCase())) && (s.getLine(1).equals(ChatColor.GREEN + "UNOCCUPIED"))) {
					// if the construction is a turret
					if (Turret.detectTurret(s, t)) {
						if (event.getPlayer().getItemInHand().getType() == Material.WATCH) {
							// Class object will the sub-class of the specific
							// turret
							Class<? extends Turret> c = t.getClass();
							// Get a constructor object from the pre-defined
							// turret sub class
							try {
								Constructor<? extends Turret> constructor = c.getConstructor();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							} catch (SecurityException e) {
								e.printStackTrace();
							}
							// Use the constructor object to create a new
							// instance of a specific turret sub class
							Turret turret = null;
							try {
								turret = (Turret) c.newInstance();
							} catch (InstantiationException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							turret.setName(t.getName());
							turret.setPermission(t.getPermission());
							turret.setCooldown(t.getCooldown());
							turret.setAmmo(t.getAmmo());
							turret.setBaseMaterials(t.getBaseMaterials());
							turret.setTopMaterials(t.getTopMaterials());

							turret.enter(event.getPlayer(), event.getClickedBlock());
							continue;
						}
						event.getPlayer().sendMessage("You must be holding a ship controller to enter the turret.");
						continue;
					}
					event.getPlayer().sendMessage("Improperly built turret");
					continue;
				}
			}
			return;

		}

		if ((event.getAction() == Action.LEFT_CLICK_BLOCK) && (event.getPlayer().getItemInHand().getType() == Material.WATCH)
				&& (userMap.containsKey(event.getPlayer()))) {
			userMap.get(event.getPlayer()).fire(event.getPlayer());
			return;
		}
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getPlayer().getItemInHand().getType() == Material.WATCH)
				&& (userMap.containsKey(event.getPlayer()))) {
			userMap.get(event.getPlayer()).exit(event.getPlayer());
			return;
		}
	}

	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event) {

		if (userMap.containsKey(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		if (!(event instanceof PlayerTeleportEvent)) {
			if (userMap.containsKey(event.getPlayer())) {
				if (event.getPlayer().getEyeLocation().getBlock().getType() != Material.GLASS) {
					userMap.get(event.getPlayer()).exit(event.getPlayer());
					event.getPlayer().sendMessage(ChatColor.RED + "Your cannon's glass block seems to be broken. You were removed from your turret.");
					return;
				}
				Location from = event.getFrom();
				Location to = event.getTo();
				if ((to.getY() != from.getY()) || (to.getZ() != from.getZ()) || (to.getX() != from.getX())) {
					event.getPlayer().teleport(
							new Location(from.getWorld(), from.getX(), from.getY(), from.getZ(), event.getPlayer().getLocation().getYaw(), event.getPlayer()
									.getLocation().getPitch()));
				}
			}
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {

		if ((event.getPlayer().getItemInHand().getType() == Material.WATCH) && (userMap.containsKey(event.getPlayer()))) {
			userMap.get(event.getPlayer()).exit(event.getPlayer());
			return;
		}
	}

	public static SQTurrets getInstance() {

		return instance;
	}

}
