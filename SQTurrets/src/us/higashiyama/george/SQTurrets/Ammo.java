
package us.higashiyama.george.SQTurrets;

import org.bukkit.inventory.ItemStack;

public class Ammo {

	// name of ammo
	private String name;
	// Itemstack of the ammo
	private ItemStack item;
	// knockback
	private boolean fire;
	// velocity modifier (MULTIPLICATION)
	private double velocity;
	private double yield;
	private double yield2 = 0;

	public ItemStack getItem() {

		return item;
	}

	public void setItem(ItemStack item) {

		this.item = item;
	}

	public boolean isFire() {

		return fire;
	}

	public void setFire(boolean fire) {

		this.fire = fire;
	}

	public double getVelocity() {

		return velocity;
	}

	public void setVelocity(double velocity) {

		this.velocity = velocity;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}
	
	public double getYield(){
		return yield;
	}
	
	public void setYield(double yield){
		this.yield = yield;
	}
	
	public double getYield2(){
		return yield2;
	}
	
	public void setYield2(double yield2){
		this.yield2 = yield2;
	}

}
