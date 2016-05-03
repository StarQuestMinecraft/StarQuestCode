package com.whirlwindgames.sqorbits.core;

public class PlanetData {
	/**
	 * A representation of the planet object stored in the config.
	 */
	double sunX;
	double sunZ;
	
	String systemName;
	String worldName;
	int orbitY;
	double speed;
	double blocksFromSun;
	
	int relativeRegionMinCornerX;
	int relativeRegionMinCornerY;
	int relativeRegionMinCornerZ;
	
	int relativeRegionMaxCornerX;
	int relativeRegionMaxCornerY;
	int relativeRegionMaxCornerZ;
	
	String schematicPath;	
	//initially set to the value from the config, but is later changed if found in DB
	double rotation;	
	
	
	
}
