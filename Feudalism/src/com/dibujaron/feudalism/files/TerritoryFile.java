package com.dibujaron.feudalism.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.dibujaron.feudalism.kingdoms.Territory;
import com.dibujaron.feudalism.regions.Point;

public class TerritoryFile extends YamlConfiguration{
	
	String fileLocation;
	World w;
	
	public TerritoryFile(World w, JavaPlugin p){
		fileLocation = p.getDataFolder() + File.separator + w.getName() + "-territories.yml";
		try{
			load(fileLocation);
		} catch (Exception e){
			System.out.println("Failed to load territories file for Feudalism!");
			e.printStackTrace();
		}
		this.w = w;
	}
	
	public ArrayList<Territory> loadTerritoryArray(){
		Set<String> keys = getKeys(false);
		ArrayList<Territory> retval = new ArrayList<Territory>(keys.size());
		for(String name : keys){
			List<String> strPoints = this.getStringList(name);
			ArrayList<Point> points = new ArrayList<Point>(strPoints.size());
			for(String pt : strPoints){
				String[] split = pt.split(",");
				int ptx = Integer.parseInt(split[0]);
				int pty = Integer.parseInt(split[1]);
				points.add(new Point(ptx, pty));
			}
			retval.add(new Territory(points, name, null, w));
		}
		return retval;
	}
}
