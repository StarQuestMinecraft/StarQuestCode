package com.dibujaron.feudalism.terrcreator;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class ScreenObject {
	
	int x,y,w,h;
	
	public ScreenObject(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getW(){
		return w;
	}
	public int getH(){
		return h;
	}
	
	public boolean pointWithin(Point p){
		if(x < p.x && p.x < x + w){
			if(y < p.y && p.y < y + h){
				return true;
			}
		}
		return false;
	}
	public abstract void onMouseClick(MouseEvent e);
	public abstract void onFocusKeyTyped(KeyEvent e);
	public abstract void onFocusGain();
	public abstract void onFocusLost();
	public abstract void draw(Graphics g);
}
