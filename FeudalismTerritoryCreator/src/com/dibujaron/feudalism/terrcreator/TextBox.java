package com.dibujaron.feudalism.terrcreator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TextBox extends ScreenObject{

	String text;
	String defaultText;
	boolean focused = false;
	
	public TextBox(String text, int x, int y, int w, int h) {
		super(x, y, w, h);
		defaultText = this.text = text;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}

	@Override
	public void onMouseClick(MouseEvent e) {
		
	}

	@Override
	public void onFocusKeyTyped(KeyEvent e) {
		
		if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
			if(text.length() > 0){
				System.out.println("Removing character!");
				System.out.println(text);
				text = text.substring(0, text.length() - 1);
				System.out.println(text);
			}
		} else if(text == defaultText){
			text = e.getKeyChar() + "";
		} else {
			text = text + e.getKeyChar();
		}
	}

	@Override
	public void onFocusGain() {
		focused = true;
	}

	@Override
	public void onFocusLost() {
		focused = false;
	}

	@Override
	public void draw(Graphics g) {
		if(focused) g.setColor(Color.WHITE);
		else g.setColor(Color.GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, w, h);
		g.drawString(text, x + 5, y + 15);
	}

}
