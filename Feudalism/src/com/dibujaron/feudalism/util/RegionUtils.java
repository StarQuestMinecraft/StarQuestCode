package com.dibujaron.feudalism.util;

import com.dibujaron.feudalism.regions.Point;

public class RegionUtils {
	
	public static boolean intersectionCheck(Point p1, Point q1, Point p2, Point q2){
		//Adapted from:
		//http://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);
		
		if(o1 != o2 && o3 != o4) return true;
		
		if(o1 == 0 && onSegment(p1, p2, q1))return true;
		
		if(o2 == 0 && onSegment(p1, q2, q1)) return true;
		
		if(o3 == 0 && onSegment(p2, p1, q2)) return true;
		
		if(o4 == 0 && onSegment(p2, q1, q2)) return true;
		
		return false;
	}
	
	public static int orientation(Point p, Point q, Point r){
		int val = (q.getZ() - p.getZ()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getZ() - q.getZ());
		
		if(val == 0) return 0;
		return (val > 0)? 1 : 2;
	}
	
	public static boolean onSegment(Point p, Point q, Point r){
		if (q.getX() <= max(p.getX(), r.getX()) && q.getX() >= min(p.getX(), r.getX()) &&
		        q.getZ() <= max(p.getZ(), r.getZ()) && q.getZ() >= min(p.getZ(), r.getZ()))
		       return true;
		 
		    return false;
	}
	
	private static int max(int a, int b){
		return (b > a)? b : a;
	}
	private static int min(int a, int b){
		return (b < a)? b : a;
	}
}
