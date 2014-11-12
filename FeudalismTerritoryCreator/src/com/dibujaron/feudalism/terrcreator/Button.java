package com.dibujaron.feudalism.terrcreator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Button extends ScreenObject{

	String text;
	Runnable execute;
	
	public Button(String text, Runnable execute, int x, int y, int w, int h){
		super(x,y,w,h);
		this.text = text;
		this.execute = execute;
	}

	@Override
	public void onMouseClick(MouseEvent e) {
		execute.run();
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
	public void draw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, w, h);
		g.drawString(text, x + 5, y + 15);
	}
	
}
