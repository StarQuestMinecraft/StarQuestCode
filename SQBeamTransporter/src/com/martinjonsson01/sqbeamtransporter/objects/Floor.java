package com.martinjonsson01.sqbeamtransporter.objects;

import org.bukkit.World;
import org.bukkit.block.Block;

public class Floor {
	private String name = "";
	private int y;
	private int floor;
	private World world;
	private Block stainedGlass;

	public Floor(Block b) {
		this.stainedGlass = b;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		if (name == null) {
			this.name = "";
		}

	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getFloor() {
		return this.floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public World getWorld() {
		return this.world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Block getStainedGlass() {
		return this.stainedGlass;
	}

	public void setstainedGlass(Block stainedGlass) {
		this.stainedGlass = stainedGlass;
	}
}
