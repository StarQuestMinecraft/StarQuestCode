
package us.higashiyama.george.SQTurrets;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import us.higashiyama.george.SQTurrets.types.*;

public class SQTurrets extends JavaPlugin implements Listener {

	public static HashMap<Player, Long> cooldownMap = new HashMap<Player, Long>();
	public static ArrayList<Turret> turretTypes = new ArrayList<Turret>();
	public static List<Ammo> ammoTypes = new ArrayList<Ammo>();
	public static SQTurrets instance;
	public static ArrayList<Projectile> eggs = new ArrayList<Projectile>();
	public static FileConfiguration config;

	public void onEnable() {

		instance = this;
		instance.saveDefaultConfig();
		config = instance.getConfig();
		loadAmmos();
		loadTurrets();
		getServer().getPluginManager().registerEvents(this, this);

	}

	private static void loadAmmos() {

		for (String name : config.getConfigurationSection("ammos").getKeys(false)) {
			Ammo a = new Ammo();
			a.setName(name);
			a.setItem(new ItemStack(Material.matchMaterial(config.getString("ammos." + name + ".id")), config.getInt("ammos." + name + ".quantity")));
			a.setFire(config.getBoolean("ammos." + name + ".fire"));
			a.setVelocity(config.getDouble("ammos." + name + ".velocity"));
			a.setYield(config.getDouble("ammos." + name + ".yield"));
			a.setYield2(config.getDouble("ammos." + name + ".yield2"));
			ammoTypes.add(a);
		}
	}

	private static void loadTurrets(){
		turretTypes.add(new ArrowTurret());
		turretTypes.add(new FireballTurret());
		turretTypes.add(new BeamTurret());
		turretTypes.add(new PotionTurret());
		Iterator<Turret> it = turretTypes.iterator();
		while(it.hasNext()){
			Turret turret = it.next();
			turret.setPermission(config.getString("turrets." + turret.getName() + ".permission"));
			turret.setCooldown(config.getLong("turrets." + turret.getName() + ".cooldown"));
			List<String> base = config.getStringList("turrets." + turret.getName() + ".basematerials");
			ArrayList<Material> baseMaterials = new ArrayList<Material>();
			for(String material : base){
				baseMaterials.add(Material.matchMaterial(material));
			}
			turret.setBaseMaterials(baseMaterials);
			List<String> top = config.getStringList("turrets." + turret.getName() + ".topmaterials");
			ArrayList<Material> topMaterials = new ArrayList<Material>();
			for (String material : top) {
				topMaterials.add(Material.matchMaterial(material));
			}
			turret.setTopMaterials(topMaterials);
			ArrayList<Ammo> turretAmmo = new ArrayList<Ammo>();
			List<String> turretAmmoStrings = config.getStringList("turrets." + turret.getName() + ".ammo");
			for (Ammo a : ammoTypes) {
				for (String s : turretAmmoStrings) {
					if (a.getName().equalsIgnoreCase(s)) {
						System.out.print(a.getName() + ", ");
						turretAmmo.add(a);
					}
				}
			}
			turret.setAmmos(turretAmmo);
			turret.setVelocity(config.getDouble("turrets." + turret.getName() + ".velocity"));
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("turretsreload") && sender.hasPermission("SQTurrets.reload")) {
			config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
			turretTypes.clear();
			ammoTypes.clear();
			loadAmmos();
			loadTurrets();
			sender.sendMessage("Turrets Reloaded");

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("turretlist")) {
			String turrets = "";
			for (Turret t : turretTypes) {
				if (sender.hasPermission(t.getPermission())) {
					turrets += t.getName() + " ";
				}
			}
			sender.sendMessage("Turrets you have permission for: " + turrets);
			return true;
		}

		return false;

	}

	@EventHandler
	public void eggBreak(final ProjectileHitEvent e) {

		if (e.getEntity() instanceof Egg && eggs.contains(e.getEntity())) {

			final Location l = e.getEntity().getLocation();
			int radius = 3;
			final int[][] newLocs = { { radius, 0 }, { -radius, 0 }, { 0, radius }, { 0, -radius }

			};
			for (int i = 0; i < 4; i++) {
				final int count = i;
				Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {

					public void run() {

						e.getEntity().getWorld().createExplosion(l.getX() + newLocs[count][0], l.getY(), l.getZ() + newLocs[count][1], 2.3F, false, true);
					}
				}, 4 * i);
			}
			eggs.remove(e.getEntity());
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
						if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WATCH) {
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

		if ((event.getAction() == Action.LEFT_CLICK_BLOCK) && (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WATCH)
				&& (isInTurret(event.getPlayer()))) {

			Turret t = detectTurretType((Player) event.getPlayer());
			if (t == null) {
				return;
			} else {
				t.fire(event.getPlayer());
			}
			return;
		}

		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WATCH)
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

	@SuppressWarnings("unused")
	private static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {

		BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
		BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST,
				BlockFace.WEST, BlockFace.NORTH_WEST };

		if (useSubCardinalDirections) {
			return radial[Math.round(yaw / 45f) & 0x7];
		} else {
			return axis[Math.round(yaw / 90f) & 0x3];
		}
	}

	public static SQTurrets getInstance() {

		return instance;
	}

}
