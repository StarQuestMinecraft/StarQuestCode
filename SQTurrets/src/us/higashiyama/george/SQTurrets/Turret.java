
package us.higashiyama.george.SQTurrets;

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

public abstract class Turret {

	// Name of the turret type (The name on the sign)
	private String name;
	// Permission the player has to have to use the turret
	private String permission;
	// Number of milliseconds between shots
	private long cooldown;
	// An ItemStack of required Ammo from a container defined
	private ItemStack ammo;
	// Acceptable materials for the base block
	private ArrayList<Material> baseMaterials = new ArrayList<Material>();
	// Acceptable materials for the top block
	private ArrayList<Material> topMaterials = new ArrayList<Material>();

	// Number of current ms. Don't touch this value. It should only get modified
	// from the fire method
	private long currentCooldown = 0;

	/*
	 * Shoot method will have to be overwritten for all new turret classes
	 */
	public void shoot(Player p) {

	}

	/*
	 * Advanced shoot method will have to be overwritten for all new turret
	 * classes that have an extended version of a turret that takes ammo
	 */
	public void shootAdvanced(Player p) {

	}

	/*
	 * Fire method is called from the SQTurret class, this should not be
	 * overwritten
	 */
	public void fire(Player p) {

		if (this.isInCooldown(p))
			return;

		if (this.isAdvanced(p)) {
			// Returning true here will take the ammo
			if (!this.hasAmmo(p)) {
				System.out.println("NO AMMOS");
				return;
			}
			this.shootAdvanced(p);
		} else {
			this.shoot(p);
		}
		this.setCurrentCooldown(System.currentTimeMillis() + this.getCooldown());

	}

	/*
	 * Don't override the enter and exit methods (for now)
	 */
	public void enter(Player p, Block turretSign) {

		// Update the turret sign
		((Sign) turretSign.getState()).setLine(1, ChatColor.RED + "OCCUPIED");
		((Sign) turretSign.getState()).setLine(2, p.getName());
		((Sign) turretSign.getState()).update();

		p.sendMessage(ChatColor.RED + "You entered a turret! Right click while holding a ship controller to exit the turret, left click it to fire.");
		BlockFace forward = DirectionUtils.getSignDirection(turretSign);
		Location l = turretSign.getRelative(forward).getRelative(BlockFace.UP).getLocation();
		Location target = new Location(l.getWorld(), l.getX() + 0.5D, l.getY(), l.getZ() + 0.5D);
		target.getBlock().setType(Material.GLOWSTONE);
		p.teleport(target);
		SQTurrets.userMap.put(p, this);
	}

	public void exit(Player p) {

		Block myBlock = p.getLocation().getBlock();
		Block signAttatchBlock = myBlock.getRelative(BlockFace.DOWN);
		for (Turret t : SQTurrets.turretTypes) {
			if (signAttatchBlock.getRelative(0, 0, 1).getType() == Material.WALL_SIGN) {
				Sign s = (Sign) signAttatchBlock.getRelative(0, 0, 1).getState();
				if (s.getLine(0).equals(ChatColor.BLUE + t.getName().toUpperCase())) {
					processTeleportLeave(p, s);
				}
			} else if (signAttatchBlock.getRelative(0, 0, -1).getType() == Material.WALL_SIGN) {
				Sign s = (Sign) signAttatchBlock.getRelative(0, 0, -1).getState();
				if (s.getLine(0).equals(ChatColor.BLUE + t.getName().toUpperCase())) {
					processTeleportLeave(p, s);
				}
			} else if (signAttatchBlock.getRelative(1, 0, 0).getType() == Material.WALL_SIGN) {
				Sign s = (Sign) signAttatchBlock.getRelative(1, 0, 0).getState();
				if (s.getLine(0).equals(ChatColor.BLUE + t.getName().toUpperCase())) {
					processTeleportLeave(p, s);
				}
			} else if (signAttatchBlock.getRelative(-1, 0, 0).getType() == Material.WALL_SIGN) {
				Sign s = (Sign) signAttatchBlock.getRelative(-1, 0, 0).getState();
				if (s.getLine(0).equals(ChatColor.BLUE + t.getName().toUpperCase())) {
					processTeleportLeave(p, s);
				}
			} else {
				processTeleportLeave(p, null);
			}
		}
		myBlock.setType(Material.SPONGE);
	}

	private void processTeleportLeave(Player p, Sign s) {

		Location tbl;
		// If the sign is null, then we assume the turret is broken
		if ((s == null)
				|| ((s.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.AIR) && (s.getBlock()
						.getRelative(BlockFace.DOWN).getType() == Material.AIR))) {
			tbl = p.getLocation().getBlock().getRelative(0, 2, 0).getLocation();
			p.sendMessage(ChatColor.RED + "Your turret seems to be broken, so we put you out on top of it.");
		} else {
			// We assume the turret is not broken
			if ((s.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() != Material.AIR)
					&& (s.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR)) {
				tbl = s.getBlock().getRelative(BlockFace.DOWN).getLocation();
			} else {
				tbl = s.getBlock().getLocation();
			}
		}
		p.teleport(new Location(tbl.getWorld(), tbl.getX() + 0.5D, tbl.getY(), tbl.getZ() + 0.5D));
		SQTurrets.userMap.remove(p);
		if (s != null) {
			s.setLine(1, ChatColor.GREEN + "UNOCCUPIED");
			s.setLine(2, "");
			s.update();
		}
	}

	public static boolean detectTurret(Sign turretSign, Turret t) {

		System.out.println("Iteration");

		BlockFace dir = DirectionUtils.getSignDirection(turretSign.getBlock());
		Block oneInFront = turretSign.getBlock().getRelative(dir);
		Block aboveThat = oneInFront.getRelative(BlockFace.UP);
		Block oneMoreUp = aboveThat.getRelative(BlockFace.UP);
		System.out.println(aboveThat.getType());
		System.out.println(t.getTopMaterials().toString());
		System.out.println(oneMoreUp.getType());
		if ((aboveThat.getType() == Material.SPONGE) && (t.getTopMaterials().contains(oneMoreUp.getType()))) {
			return true;
		}
		return false;
	}

	/*
	 * Returns whether this turret has an ammo taking version
	 */
	public boolean isAdvanced(Player p) {

		Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		System.out.println(b.getType());
		if (b.getType() == Material.DISPENSER && this.getBaseMaterials().contains(Material.DISPENSER)) {
			System.out.println("True");
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
		if (i.containsAtLeast(this.getAmmo(), 1)) {
			i.removeItem(this.getAmmo());
			return true;
		} else {
			p.sendMessage(ChatColor.RED + "Out of Ammo!");
			return false;
		}

	}

	/*
	 * Returns whether a shooting player is in cooldown
	 */
	public boolean isInCooldown(Player p) {

		if (this.getCurrentCooldown() == 0) {
			System.out.println("In 0 check");
			this.setCurrentCooldown(System.currentTimeMillis());
			return false;
		}
		System.out.println(this.getCurrentCooldown());
		System.out.println(System.currentTimeMillis());
		if ((this.getCurrentCooldown()) > System.currentTimeMillis()) {
			p.sendMessage(ChatColor.RED + "Turret is in cooldown");
			return true;
		}
		return false;
	}

	@Override
	public String toString() {

		String a = this.name;
		String b = this.permission;
		String c = "" + this.cooldown;
		String d = this.ammo.toString();
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

	public long getCurrentCooldown() {

		return currentCooldown;
	}

	public void setCurrentCooldown(long currentCooldown) {

		this.currentCooldown = currentCooldown;
	}

	public ItemStack getAmmo() {

		return ammo;
	}

	public void setAmmo(ItemStack ammo) {

		this.ammo = ammo;
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

}
