
package us.higashiyama.george.SQTurrets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
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

public class SQTurrets extends JavaPlugin implements Listener {

	public static ArrayList<Turret> turretTypes = new ArrayList<Turret>();
	public static ArrayList<Ammo> ammoTypes = new ArrayList<Ammo>();
	public static SQTurrets instance;
	public static ArrayList<Player> liveProjectiles = new ArrayList<Player>();

	public void onEnable() {

		instance = this;
		instance.saveDefaultConfig();
		loadAmmos();
		loadTurrets();
		new AntiGravityTask();
		getServer().getPluginManager().registerEvents(this, this);

	}

	private static void loadAmmos() {

		for (String name : instance.getConfig().getConfigurationSection("ammos").getKeys(false)) {
			Ammo a = new Ammo();
			a.setName(name);
			a.setItem(new ItemStack(instance.getConfig().getInt("ammos." + name + ".id"), 1));
			a.setFire(instance.getConfig().getBoolean("ammos." + name + ".fire"));
			a.setVelocity(instance.getConfig().getDouble("ammos." + name + ".velocity"));
			ammoTypes.add(a);
		}
	}

	private static void loadTurrets() {

		for (String name : instance.getConfig().getConfigurationSection("turrets").getKeys(false)) {
			Turret t = new Turret();
			t.setName(name);
			t.setPermission(instance.getConfig().getString("turrets." + name + ".permission"));
			t.setCooldown(instance.getConfig().getLong("turrets." + name + ".cooldown"));
			List<Integer> base = instance.getConfig().getIntegerList("turrets." + name + ".basematerials");
			ArrayList<Material> baseMaterials = new ArrayList<Material>();
			for (int sId : base) {
				baseMaterials.add(Material.getMaterial(sId));
			}
			List<Integer> top = instance.getConfig().getIntegerList("turrets." + name + ".topmaterials");
			ArrayList<Material> topMaterials = new ArrayList<Material>();
			for (int sId : top) {
				topMaterials.add(Material.getMaterial(sId));
			}
			t.setBaseMaterials(baseMaterials);
			t.setTopMaterials(topMaterials);
			ArrayList<Ammo> turretAmmo = new ArrayList<Ammo>();
			List<String> turretAmmoStrings = instance.getConfig().getStringList("turrets." + name + ".ammo");
			for (Ammo a : ammoTypes) {
				for (String s : turretAmmoStrings) {
					if (a.getName().equalsIgnoreCase(s)) {
						turretAmmo.add(a);
					}
				}
			}
			t.setProjectileString(instance.getConfig().getString("turrets." + name + ".projectile"));
			t.setAmmos(turretAmmo);
			t.setVelocity(instance.getConfig().getDouble("turrets." + name + ".velocity"));
			turretTypes.add(t);
		}
	}

	@EventHandler
	public void onPlayerInteractSign(final PlayerInteractEvent event) {

		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType() == Material.WALL_SIGN)) {
			Sign s = (Sign) event.getClickedBlock().getState();

			// Begin checking if the turret is a registered type and is an
			// uncreated turret
			for (Turret t : turretTypes) {
				if (s.getLine(0).equalsIgnoreCase("[" + t.getName() + "]")) {
					s.setLine(0, ChatColor.BLUE + t.getName().toUpperCase());
					s.setLine(1, ChatColor.GREEN + "UNOCCUPIED");
					s.update(true);
					return;
				}
			}

			// Checking an existing turret construction so the player can enter
			// a turret
			for (Turret t : turretTypes) {
				if ((s.getLine(0).equalsIgnoreCase(ChatColor.BLUE + t.getName().toUpperCase())) && (s.getLine(1).equals(ChatColor.GREEN + "UNOCCUPIED"))) {
					// if the construction is a turret
					if (Turret.detectTurret(s, t)) {
						if (event.getPlayer().getItemInHand().getType() == Material.WATCH) {
							if (!DirectionUtils.playerOffsetLocation(event.getPlayer(), s)) {
								event.getPlayer().sendMessage(ChatColor.RED + "You must be standing next to the turret sign to use it!");
								return;
							}

							s.setLine(1, ChatColor.RED + "OCCUPIED");
							s.setLine(2, event.getPlayer().getName());
							int pX = (int) Math.floor(event.getPlayer().getLocation().getY());
							int sX = (int) Math.floor(s.getLocation().getY());
							s.setLine(3, Integer.toString(sX - pX));
							s.update(true);
							Turret.enter(event.getPlayer(), event.getClickedBlock());
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
				&& (isInTurret(event.getPlayer()))) {

			Turret t = detectTurretType((Player) event.getPlayer());
			if (t == null) {
				return;
			} else {
				t.fire(event.getPlayer());
			}
			return;
		}

		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getPlayer().getItemInHand().getType() == Material.WATCH)
				&& (isInTurret(event.getPlayer()))) {

			Turret t = detectTurretType((Player) event.getPlayer());
			if (t == null) {

			} else {
				t.exit(event.getPlayer());
			}

			return;
		}

	}

	@EventHandler
	public void playerMove(PlayerMoveEvent event) {

		if (isInTurret(event.getPlayer())) {
			if (!(event instanceof PlayerTeleportEvent)) {
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
	public void onPlayerBreakBlock(BlockBreakEvent event) {

		if (isInTurret(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {

		if ((event.getPlayer().getItemInHand().getType() == Material.WATCH) && (isInTurret(event.getPlayer()))) {
			// detectTurretType(event.getPlayer()).exit(event.getPlayer());
			// TODO:
			return;
		}
	}

	public static Turret detectTurretType(Player p) {

		// First find the sign that is a turret sign??
		Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		// now check block faces for turret sign
		BlockFace[] signFaces = { BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH };
		for (BlockFace bf : signFaces) {
			for (Turret t : turretTypes) {
				if ((b.getRelative(bf).getType() == Material.WALL_SIGN)
						&& ((Sign) b.getRelative(bf).getState()).getLine(0).equalsIgnoreCase(ChatColor.BLUE + t.getName())) {
					return t;
				}
			}
		}
		return null;

	}

	public static boolean isInTurret(Player p) {

		if (p.getLocation().getBlock().getType() != Material.GLOWSTONE && p.getLocation().getBlock().getType() != Material.SPONGE) {
			return false;
		}

		if (detectTurretType(p) != null && detectTurretType(p).getTopMaterials().contains(p.getLocation().getBlock().getRelative(BlockFace.UP).getType())) {
			return true;

		}

		BlockFace[] signFaces = { BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH };
		BlockFace face = null;
		for (BlockFace bf : signFaces) {
			if ((p.getLocation().getBlock().getRelative(bf).getType() == Material.WALL_SIGN)) {
				face = bf;
			}
		}

		if (face == null) {
			return false;
		}
		for (Turret t : turretTypes) {
			if (((Sign) p.getLocation().getBlock().getRelative(face).getState()).getLine(0).equalsIgnoreCase(ChatColor.BLUE + t.getName())) {
				return true;

			}
		}
		return false;
	}

	public static boolean isInShootingTurret(Player p) {

		if (p.getLocation().getBlock().getType() != Material.GLOWSTONE && p.getLocation().getBlock().getType() != Material.SPONGE) {
			return false;
		}

		return true;
	}

	public static SQTurrets getInstance() {

		return instance;
	}

}
