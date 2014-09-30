
package us.higashiyama.george.SQTurrets;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Fish;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.Inventory;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class Turret {

	// Name of the turret type (The name on the sign)
	private String name;
	// Permission the player has to have to use the turret
	private String permission;
	// Number of milliseconds between shots
	private long cooldown;
	// Acceptable materials for the base block
	private ArrayList<Material> baseMaterials = new ArrayList<Material>();
	// Acceptable materials for the top block
	private ArrayList<Material> topMaterials = new ArrayList<Material>();
	/*
	 * Projectile to shoot ( Arrow, Egg, EnderPearl, Fireball, Fish,
	 * LargeFireball, SmallFireball, Snowball, ThrownExpBottle, ThrownPotion,
	 * WitherSkull)
	 */
	private Class<? extends Projectile> projectileClass;

	private String projectileString;
	// velocity to shoot the projectile
	private double velocity;
	// Arraylist of acceptable ammos
	private ArrayList<Ammo> ammos = new ArrayList<Ammo>();

	public Turret() {

	}

	public void shoot(final Player p) {

		// First create a vector
		Vector v = new Vector(velocity, velocity, velocity);
		Vector pVec = p.getLocation().getDirection();
		Vector finalVec = new Vector(v.getX() * pVec.getX(), v.getY() * pVec.getY(), v.getZ() * pVec.getZ());

		Location eye = p.getEyeLocation();
		Location oneUp = eye.add(0.0D, 1.0D, 0.0D);
		Location loc = oneUp.toVector().add(p.getLocation().getDirection().multiply(2))
				.toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());

		// then launch the projectile
		Projectile proj = p.getWorld().spawn(loc, this.projectileClass);
		// launchProjectile(this.projectileClass, finalVec);
		// finally, set the shooter
		proj.setVelocity(finalVec);
		if (!(proj instanceof Egg) || !(proj instanceof Snowball)) {
			proj.setShooter((ProjectileSource) p);
		}

		// SQTurrets.liveProjectiles.add(proj);
		if (proj instanceof Egg) {
			SQTurrets.eggs.add(proj);
		}

		if (proj instanceof Snowball) {
			SQTurrets.liveEMP.add(proj);
		}

		// play sounds
		p.getWorld().playSound(p.getLocation(), Sound.SHOOT_ARROW, 2.0F, 1.0F);
	}

	/*
	 * Advanced shoot method will have to be overwritten for all new turret
	 * classes that have an extended version of a turret that takes ammo
	 */
	public void shootAdvanced(final Player p) {

		// Very first, we have to see what ammo the player is using
		Ammo a = getLoadedAmmo(p);
		if (a == null) {
			return;
		}

		// create a vector for the initial projectile
		Vector v = new Vector(this.velocity, this.velocity, this.velocity);

		// Now create a vector for the advanced version modifier
		Vector vMod = new Vector(a.getVelocity(), a.getVelocity(), a.getVelocity());
		Vector pVec = p.getLocation().getDirection();
		Vector finalV = pVec.multiply(v.multiply(vMod));

		// then launch the projectile
		Location eye = p.getEyeLocation();
		Location oneUp = eye.add(0.0D, 1.0D, 0.0D);
		Location loc = oneUp.toVector().add(p.getLocation().getDirection().multiply(2))
				.toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());

		// then launch the projectile
		Projectile proj = p.getWorld().spawn(loc, this.projectileClass);

		proj.setVelocity(finalV);

		if (proj instanceof Egg) {
			SQTurrets.eggs.add(proj);
		}

		if (proj instanceof Snowball) {
			SQTurrets.liveEMP.add(proj);
		}

		if (!(proj instanceof Egg) || !(proj instanceof Snowball)) {
			proj.setShooter((ProjectileSource) p);
		}
		// SQTurrets.liveProjectiles.add(proj);

		// Ammo specific settings

		if (a.isFire()) {
			proj.setFireTicks(Integer.MAX_VALUE);
		}

		// play sounds
		p.getWorld().playSound(p.getLocation(), Sound.SHOOT_ARROW, 0.5F, 1.0F);
	}

	/*
	 * Fire method is called from the SQTurret class, this should not be
	 * overwritten
	 */
	public void fire(Player p) {

		if (!p.hasPermission(this.permission)) {

			p.sendMessage(ChatColor.RED + "You don't have permission to use this turret type!");
			return;

		}

		if (this.isInCooldown(p))
			return;

		if (this.isAdvanced(p)) {
			// Returning true here will take the ammo
			if (!this.hasAmmo(p)) {
				p.sendMessage(ChatColor.RED + "Out of recognized Ammo!");
				return;
			}
			this.shootAdvanced(p);
		} else {
			this.shoot(p);
		}

		if (SQTurrets.cooldownMap.get(p) != null) {
			SQTurrets.cooldownMap.remove(p);
			SQTurrets.cooldownMap.put(p, (System.currentTimeMillis()));
		}

	}

	/*
	 * Returns whether a shooting player is in cooldown
	 */
	public boolean isInCooldown(Player p) {

		if (SQTurrets.cooldownMap.get(p) == null) {

			SQTurrets.cooldownMap.put(p, (System.currentTimeMillis()));
			return false;
		}

		if ((SQTurrets.cooldownMap.get(p) + this.getCooldown()) > System.currentTimeMillis()) {
			p.sendMessage(ChatColor.RED + "Turret is in cooldown");
			return true;
		}
		return false;
	}

	/*
	 * Don't override the enter and exit methods (for now)
	 */
	public static void enter(Player p, Block turretSign) {

		// Update the turret sign
		p.sendMessage(ChatColor.RED + "You entered a turret! Right click while holding a ship controller to exit the turret, left click it to fire.");
		BlockFace forward = DirectionUtils.getSignDirection(turretSign);
		Location l = turretSign.getRelative(forward).getRelative(BlockFace.UP).getLocation();
		Location target = new Location(l.getWorld(), l.getX() + 0.5D, l.getY(), l.getZ() + 0.5D, p.getLocation().getPitch(), p.getLocation().getYaw());
		target.getBlock().setType(Material.GLOWSTONE);
		p.teleport(target);
	}

	public void exit(Player p) {

		Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		BlockFace blockFace = BlockFace.WEST;
		BlockFace[] signFaces = { BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH };
		for (BlockFace bf : signFaces) {
			for (Turret t : SQTurrets.turretTypes) {
				if ((b.getRelative(bf).getType() == Material.WALL_SIGN)
						&& ((Sign) b.getRelative(bf).getState()).getLine(0).equalsIgnoreCase(ChatColor.BLUE + t.getName())) {
					blockFace = bf;

				}
			}
		}
		Sign s = (Sign) b.getRelative(blockFace).getState();

		int yMod;
		try {
			yMod = Integer.parseInt(s.getLine(3));
		} catch (NumberFormatException e) {
			System.out.println(e);
			yMod = 0;
		}
		Block baseBlock = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		Location base = p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(blockFace).getLocation();
		double[] xz = DirectionUtils.playerOffsetTeleport(p, baseBlock);
		if (p.getLocation().getBlock().getType() == Material.GLOWSTONE) {
			p.getLocation().getBlock().setType(Material.SPONGE);
		}
		p.teleport(new Location(p.getLocation().getWorld(), p.getLocation().getX() + xz[0], base.getY() - yMod, p.getLocation().getZ() + xz[1]));
		if (s != null) {
			s.setLine(1, ChatColor.GREEN + "UNOCCUPIED");
			s.setLine(2, "");
			s.setLine(3, "");
			s.update(true);
		}
	}

	public static boolean detectTurret(Sign turretSign, Turret t) {

		BlockFace dir = DirectionUtils.getSignDirection(turretSign.getBlock());
		Block oneInFront = turretSign.getBlock().getRelative(dir);
		Block aboveThat = oneInFront.getRelative(BlockFace.UP);
		Block oneMoreUp = aboveThat.getRelative(BlockFace.UP);
		if (((aboveThat.getType() == Material.SPONGE) || (aboveThat.getType() == Material.GLOWSTONE))
				&& (t.getTopMaterials().contains(oneMoreUp.getType()) && (t.getBaseMaterials().contains(oneInFront.getType())))) {
			return true;
		}
		return false;
	}

	/*
	 * Returns whether this turret has an ammo taking version
	 */
	public boolean isAdvanced(Player p) {

		Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		if (b.getType() == Material.DISPENSER && this.getBaseMaterials().contains(Material.DISPENSER)) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * Returns whether the turret has ammo. It will also take the ammo it finds.
	 */
	private boolean hasAmmo(Player p) {

		// This method is called after we already know that it is an advanced
		// turret
		Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		if (b.getType() != Material.DISPENSER)
			return false;
		Dispenser d = (Dispenser) b.getState();
		Inventory i = d.getInventory();
		for (Ammo ammo : this.getAmmos()) {
			if (i.containsAtLeast(ammo.getItem(), ammo.getItem().getAmount())) {
				i.removeItem(ammo.getItem());

				return true;

			}
		}
		return false;

	}

	private Ammo getLoadedAmmo(Player p) {

		// This method returns the ammo type we are using
		Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		Dispenser d = (Dispenser) b.getState();
		Inventory i = d.getInventory();
		for (Ammo ammo : this.getAmmos()) {
			if (i.containsAtLeast(ammo.getItem(), 1)) {
				return ammo;

			}
		}
		return null;

	}

	@Override
	public String toString() {

		String a = this.name;
		String b = this.permission;
		String c = "" + this.cooldown;
		String d = this.ammos.toString();
		String e = "";
		String f = "";
		for (Material p : this.baseMaterials)
			e += p.toString() + ", ";
		for (Material p : this.topMaterials)
			f += p.toString() + ", ";
		return a + " " + b + " " + c + " " + d + " " + e + " " + f;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getPermission() {

		return permission;
	}

	public void setPermission(String permission) {

		this.permission = permission;
	}

	public long getCooldown() {

		return cooldown;
	}

	public void setCooldown(long cooldown) {

		this.cooldown = cooldown;
	}

	public ArrayList<Material> getBaseMaterials() {

		return baseMaterials;
	}

	public void setBaseMaterials(ArrayList<Material> baseMaterials) {

		this.baseMaterials = baseMaterials;
	}

	public ArrayList<Material> getTopMaterials() {

		return topMaterials;
	}

	public void setTopMaterials(ArrayList<Material> topMaterials) {

		this.topMaterials = topMaterials;
	}

	public double getVelocity() {

		return velocity;
	}

	public void setVelocity(double velocity) {

		this.velocity = velocity;
	}

	public ArrayList<Ammo> getAmmos() {

		return ammos;
	}

	public void setAmmos(ArrayList<Ammo> ammos) {

		this.ammos = ammos;
	}

	public String getProjectileString() {

		return projectileString;
	}

	public void setProjectileString(String projectileString) {

		this.projectileString = projectileString;
		switch (this.projectileString.toUpperCase()) {
			case "ARROW":
				this.projectileClass = Arrow.class;
				break;
			case "EGG":
				this.projectileClass = Egg.class;
				break;
			case "ENDERPEARL":
				this.projectileClass = EnderPearl.class;
				break;
			case "FIREBALL":
				this.projectileClass = Fireball.class;
				break;
			case "FISH":
				this.projectileClass = Fish.class;
				break;
			case "LARGEFIREBALL":
				this.projectileClass = LargeFireball.class;
				break;
			case "SMALLFIREBALL":
				this.projectileClass = SmallFireball.class;
				break;
			case "SNOWBALL":
				this.projectileClass = Snowball.class;
				break;
			case "THROWNEXPBOTTLE":
				this.projectileClass = ThrownExpBottle.class;
				break;
			case "THROWNPOTION":
				this.projectileClass = ThrownPotion.class;
				break;
			case "WITHERSKULL":
				this.projectileClass = WitherSkull.class;
				break;
		}

	}

}
