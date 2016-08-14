package com.dibujaron.feudalism.terrcreator;

import java.awt.Color;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Territory {
	ArrayList<Point> points;
	String name;
	
	public Territory(ArrayList<Point> points, String name){
		this.points = new ArrayList<Point>();
		for(Point p : points){
			this.points.add(new Point(p.x, p.y));
		}
		this.name = name;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.PINK);
		for(int i = 0; i < points.size() - 1; i++){
			Point start = points.get(i);
			Point finish = points.get(i + 1);
			g.drawLine(start.x, start.y, finish.x, finish.y);
		}
		
		g.setColor(Color.BLACK);
		for(Point p : points){
			g.drawRect(p.x - 1, p.y -1, 3, 3);
		}
	}
	
	public void print(){
		System.out.println(name);
		for(Point p : points){
			System.out.println("      " + p);
		}
	}
	
	public void print(PrintWriter out, int tlx, int tly, int sx, int sy){
		out.println("  " + name + ":");
		out.println("    owner: None");
		out.println("    capture points:");
		out.println("    beachead: false");
		out.println("    points:");
		
		double rtlx = tlx / sx;
		double rtly = tlx / sy;
		
		for(Point p : points){
			double nx = (rtlx + p.x) * sx;
			double ny = (rtly + p.y) * sy;
			out.println("    - " + nx + "," + ny);
		}
	}
}
