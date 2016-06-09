package com.whirlwindgames.dibujaron.sqempire;

import java.util.ArrayList;
import java.util.List;

import com.sk89q.worldedit.BlockVector2D;

public class Territory {

	public String name;
	public Empire owner;
	public int age;
	
	public List<CapturePoint> capturePoints = new ArrayList<CapturePoint>();
	
	public List<BlockVector2D> points = new ArrayList<BlockVector2D>();
	
	public Territory() {
		
	}
	
}
