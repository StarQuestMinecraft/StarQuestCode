package com.dibujaron.feudalism.regions;

import org.bukkit.Location;

public class Point {
	private int x;
	private int z;
	
	public Point(int x, int z){
		this.x = x;
		this.z = z;
	}
	
	public Point(Location l){
		x = l.getBlockX();
		z = l.getBlockZ();
	}
	
	public int getX(){ return x;}
	public int getZ(){ return z;}
}
