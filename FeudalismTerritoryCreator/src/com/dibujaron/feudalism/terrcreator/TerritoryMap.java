package com.dibujaron.feudalism.terrcreator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class TerritoryMap extends ScreenObject{
	
	ArrayList<Point> enteredPoints = new ArrayList<Point>();
	ArrayList<Territory> territories = new ArrayList<Territory>();
	BufferedImage baseImage;
	BufferedImage workingImage;
	
	Point lastPoint = null;
	
	Color enteredPointsColor = Color.RED;
	
	int xdist, ydist;
	
	public TerritoryMap(BufferedImage image) {
		super(0, 0, 800, 800);
		workingImage = deepCopy(image);
		baseImage = deepCopy(image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMouseClick(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1){
			Point coords = new Point(e.getX() + xdist, e.getY() + ydist);
			
			for(Point p : enteredPoints){
				if(p.isWithinRadiusOf(10, coords)){
					if(lastPoint != p){
						enteredPoints.add(p);
						lastPoint = p;
						enteredPointsColor = Color.GREEN;
					} else {
						System.out.println("Last point is equal to current point, not placing point");
					}
					return;
				}
			}
			for(Territory t : territories){
				for(Point p : t.points){
					if(p.isWithinRadiusOf(10, coords)){
						enteredPoints.add(p);
						lastPoint = p;
						return;
					}
				}
			}
			enteredPoints.add(coords);
			lastPoint = coords;
		} else if(e.getButton() == MouseEvent.BUTTON3){
			if(enteredPoints.size() > 0){
			enteredPoints.remove(enteredPoints.get(enteredPoints.size() - 1));
			if(enteredPoints.size() > 0){
				lastPoint = enteredPoints.get(enteredPoints.size() - 1);
			} else {
				lastPoint = null;
			}
				if(enteredPointsColor == Color.GREEN){
					enteredPointsColor = Color.RED;
				}
			}
		}
	}
	
	public void removeTerritory(String name){
		for(int i = 0; i < territories.size(); i++){
			Territory t = territories.get(i);
			if(t.name.equals(name)){
				territories.remove(i);
				return;
			}
		}
	}

	@Override
	public void onFocusKeyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFocusGain() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFocusLost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics globalGraphics) {
		 
		Graphics g = workingImage.getGraphics();
		g.drawImage(baseImage, 0, 0, TerritoryCreator.getInstance());
		for(Territory t : territories){
			t.draw(g);
		}
		
		g.setColor(enteredPointsColor);
		for(int i = 0; i < enteredPoints.size() - 1; i++){
			Point start = enteredPoints.get(i);
			Point finish = enteredPoints.get(i + 1);
			g.drawLine(start.x, start.y, finish.x, finish.y);
		}
		
		g.setColor(Color.BLACK);
		for(Point p : enteredPoints){
			g.drawRect(p.x - 1, p.y -1, 3, 3);
		}
		globalGraphics.setColor(Color.LIGHT_GRAY);
		globalGraphics.drawRect(0, 0, 799, 799);
		if(workingImage.getWidth() > 800 && workingImage.getHeight() > 800){
			globalGraphics.drawImage(workingImage.getSubimage(xdist, ydist, 799, 799), 0, 0, TerritoryCreator.getInstance());
		} else if(workingImage.getWidth() > 800){
			globalGraphics.drawImage(workingImage.getSubimage(xdist, ydist, 799, workingImage.getHeight() - 1), 0, 0, TerritoryCreator.getInstance());
		} else if (workingImage.getHeight() > 800){
			globalGraphics.drawImage(workingImage.getSubimage(xdist, ydist, workingImage.getWidth(), 799), 0, 0, TerritoryCreator.getInstance());
		} else {
			globalGraphics.drawImage(workingImage, 0, 0, TerritoryCreator.getInstance());
		}
	}
	
	public void finishTerritory(String name){
		if(name == null || name.equals("") || name.equals("Enter Territory Name...")){
			System.out.println("Name was not entered! Please enter a name!");
			return;
		}
		if(enteredPointsColor == Color.GREEN){
			territories.add(new Territory(enteredPoints, name));
			enteredPointsColor = Color.RED;
			enteredPoints.clear();
		} else {
			System.out.println("Territory is not closed! Close it please!");
		}
	}
	
	public void changeXDist(int change){
		if(baseImage.getWidth() <= 800) return;
		xdist += change;
		if(xdist < 0) xdist = 0;
		if(xdist > (baseImage.getWidth() - 800)) xdist = baseImage.getWidth() - 800;
	}
	public void changeYDist(int change){
		if(baseImage.getHeight() <= 800) return;
		ydist += change;
		if(ydist < 0) ydist = 0;
		if(ydist > (baseImage.getHeight() - 800)) ydist = baseImage.getHeight() - 800;
	}
	
	private static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null).getSubimage(0, 0, bi.getWidth(), bi.getHeight());
	}
}
