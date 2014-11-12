package com.dibujaron.feudalism.terrcreator;

public class Point {
	int x, y;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String toString(){
		return x + "," + y;
	}
	
	public boolean isWithinRadiusOf(int radius, Point other){
		int dx = this.x - other.x;
		int dy = this.y - other.y;
		return radius * radius >= dx * dx + dy * dy;
	}
	
	public Point clone(){
		return new Point(x,y);
	}
}
