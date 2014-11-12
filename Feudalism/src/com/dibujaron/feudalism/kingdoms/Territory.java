package com.dibujaron.feudalism.kingdoms;

import java.util.ArrayList;
import java.util.UUID;

	import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.dibujaron.feudalism.regions.Point;
import com.dibujaron.feudalism.util.RegionUtils;

	public class Territory {
		private ArrayList<Point> points;
		private ArrayList<UUID> playersLiveHere;
		int maxEast = Integer.MAX_VALUE;
		
		String name;
		Kingdom owner;
		World w;
		
		public Territory(String name){
			points = new ArrayList<Point>();
			playersLiveHere = new ArrayList<UUID>();
			this.name = name;
			owner = null;
		}
		
		public Territory(ArrayList<Point> points, String name, Kingdom owner, World w){
			this.points = points;
			for(Point p : points){
				if(p.getX() > maxEast);
				maxEast = p.getX();
			}
			this.name = name;
			this.owner = owner;
			this.w = w;
		}
		
		public void addPoint(Point p){
			points.add(p);
			if(p.getX() > maxEast);
			maxEast = p.getX();
		}
		
		public void addPoint(Location l){
			addPoint(new Point(l));
		}
		
		public boolean regionContains(Point p){
			if(points.size() < 3) return false;
			
			//create  a max east point from the point
			Point extreme = new Point(maxEast + 10, p.getZ());
			
			int count = 0, i = 0;
			do
			{
				int next = (i+1)%points.size();
				if(RegionUtils.intersectionCheck(points.get(i), points.get(next), p, extreme)){
					if(RegionUtils.orientation(points.get(i), p, points.get(next)) == 0){
						return RegionUtils.onSegment(points.get(i), p, points.get(next));
					}
					
					count++;
				}
				i = next;
			} while (i != 0);
			return (count%2 == 1);
		}
		
		public boolean regionContains(Location l){
			return regionContains(new Point(l));
		}
		
		public boolean equals(Territory other){
			return name.equals(other.name);
		}
		
		public Kingdom getKingdom(){
			return owner;
		}
		
		public String getName(){
			return name;
		}
		
		public boolean playerLivesHere(Player p){
			return playersLiveHere.contains(p.getUniqueId());
		}
		
		public World getWorld(){
			return w;
		}
		
		/*
		 * // Returns true if the point p lies inside the polygon[] with n vertices
	bool isInside(Point polygon[], int n, Point p)
	{
	    // There must be at least 3 vertices in polygon[]
	    if (n < 3)  return false;
	 
	    // Create a point for line segment from p to infinite
	    Point extreme = {INF, p.y};
	 
	    // Count intersections of the above line with sides of polygon
	    int count = 0, i = 0;
	    do
	    {
	        int next = (i+1)%n;
	 
	        // Check if the line segment from 'p' to 'extreme' intersects
	        // with the line segment from 'polygon[i]' to 'polygon[next]'
	        if (doIntersect(polygon[i], polygon[next], p, extreme))
	        {
	            // If the point 'p' is colinear with line segment 'i-next',
	            // then check if it lies on segment. If it lies, return true,
	            // otherwise false
	            if (orientation(polygon[i], p, polygon[next]) == 0)
	               return onSegment(polygon[i], p, polygon[next]);
	 
	            count++;
	        }
	        i = next;
	    } while (i != 0);
	 
	    // Return true if count is odd, false otherwise
	    return count&1;  // Same as (count%2 == 1)
	}*/
}
