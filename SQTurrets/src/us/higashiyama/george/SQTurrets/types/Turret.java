
package us.higashiyama.george.SQTurrets.types;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import us.higashiyama.george.SQTurrets.Ammo;
import us.higashiyama.george.SQTurrets.DirectionUtils;
import us.higashiyama.george.SQTurrets.SQTurrets;

public abstract class Turret {

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
	// velocity to shoot the projectile
	private double velocity;
	// Arraylist of acceptable ammos
	private ArrayList<Ammo> ammos = new ArrayList<Ammo>();

	public Turret() {}
	
	// Override this for firing
	public abstract void shoot(final Player p);
	
	// Override this for firing with ammo
	public abstract void shoot(final Player p, Ammo loaded_ammo);

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

		Ammo loaded = getLoadedAmmo(p);
		if (hasAmmo(p)) {
			this.shoot(p, loaded);
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


	// Returns whether this turret has an ammo taking version
	 
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
	protected boolean hasAmmo(Player p) {

		// This method is called after we already know that it is an advanced
		// turret
		Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		if (b.getType() != Material.DISPENSER)
			return false;
		Dispenser d = (Dispenser) b.getState();
		Inventory i = d.getInventory();
		for (Ammo ammo : ammos) {
			if (i.contains(Material.SPLASH_POTION) && ammo.getName().equalsIgnoreCase("potion")){
				i.removeItem(i.getItem(i.first(Material.SPLASH_POTION)));
				return true;
			} else if (i.contains(Material.LINGERING_POTION) && ammo.getName().equalsIgnoreCase("potion")){
				i.removeItem(i.getItem(i.first(Material.LINGERING_POTION)));
				return true;
			} else if (i.contains(ammo.getItem().getType(), ammo.getItem().getAmount())) {
				i.removeItem(ammo.getItem());
				return true;
			}
		}
		return false;

	}

	protected Ammo getLoadedAmmo(Player p) {

		// This method returns the ammo type we are using
		Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		Dispenser d = (Dispenser) b.getState();
		Inventory i = d.getInventory();
		for (Ammo ammo : this.getAmmos()) {
			if (i.contains(Material.SPLASH_POTION) && ammo.getName().equalsIgnoreCase("potion")){
				ammo.setItem(i.getItem(i.first(Material.SPLASH_POTION)));
				return ammo;
			} else if (i.contains(Material.LINGERING_POTION) && ammo.getName().equalsIgnoreCase("potion")){
				ammo.setItem(i.getItem(i.first(Material.LINGERING_POTION)));
				return ammo;
			} else if (i.contains(ammo.getItem().getType())) {
				ItemStack stack = new ItemStack(i.getItem(i.first(ammo.getItem().getType())));
				stack.setAmount(ammo.getItem().getAmount());
				ammo.setItem(stack);
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

}
