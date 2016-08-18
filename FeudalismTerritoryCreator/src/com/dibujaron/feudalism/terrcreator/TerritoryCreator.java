package com.dibujaron.feudalism.terrcreator;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class TerritoryCreator extends JFrame implements Runnable, MouseListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String fileName;
	private static String executionPath;
	private static TerritoryCreator instance;
	int mapScaleX, mapScaleY;
	BufferedImage image;
	ArrayList<ScreenObject> objects = new ArrayList<ScreenObject>();
	ScreenObject focus;

	TextBox nameBox;
	TextBox scaleXBox;
	TextBox scaleYBox;
	TextBox tlcornerX;
	TextBox tlcornerY;
	TextBox moveSpeed;
	TerritoryMap tm;

	Button finishTerritory;
	Button finishMap;
	Button focusMap;
	Button removeTerritory;

	boolean leftPressed, rightPressed, upPressed, downPressed;

	public TerritoryCreator(BufferedImage image) {
		super("Feudalism Territory Creator v1.2.0");
		instance = this;
		this.image = image;
		this.setSize(1030, 800);

		tm = new TerritoryMap(image);

		objects.add(tm);

		nameBox = new TextBox("Enter Territory Name...", 810, 40, 200, 50);
		scaleXBox = new TextBox("Enter Map Scale X...", 810, 100, 200, 50);
		scaleYBox = new TextBox("Enter Map Scale Y...", 810, 160, 200, 50);
		tlcornerX = new TextBox("Enter Top Left Corner X...", 810, 220, 200, 50);
		tlcornerY = new TextBox("Enter Top Left Corner Y...", 810, 280, 200, 50);
		moveSpeed = new TextBox("Enter Move Speed (default 5)...", 810, 340, 200, 50);
		objects.add(nameBox);
		objects.add(scaleXBox);
		objects.add(scaleYBox);
		objects.add(tlcornerX);
		objects.add(tlcornerY);
		objects.add(moveSpeed);

		finishTerritory = new Button("Finish Territory", new Runnable() {
			public void run() {
				tm.finishTerritory(nameBox.getText());
				nameBox.setText("Enter Territory Name...");
			}
		}, 810, 400, 200, 50);

		objects.add(finishTerritory);

		finishMap = new Button("Export File and Map", new Runnable() {
			public void run() {
				if (tm.enteredPoints.size() > 0) {
					tm.finishTerritory(nameBox.getText());
					nameBox.setText("Enter Territory Name...");
				}
				finish();
			}
		}, 810, 460, 200, 50);

		objects.add(finishMap);

		focusMap = new Button("Focus on map without adding point", new Runnable() {
			public void run() {
				focus = tm;
			}
		}, 810, 520, 200, 50);

		objects.add(focusMap);
		
		removeTerritory = new Button("Remove Named Territory", new Runnable() {
			public void run() {
				String name = nameBox.getText();
				if(name.equals("Enter Territory Name...")){
					System.out.println("No territory given to delete!");
				} else {
					tm.removeTerritory(name);
				}
			}
		}, 810, 580, 200, 50);

		objects.add(removeTerritory);

		addMouseListener(this);
		addKeyListener(this);
		this.setVisible(true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void finish() {
		if (scaleXBox.getText().equals("Enter Map Scale X...")) {
			System.out.println("Enter a map scale X!");
			return;
		}
		if (scaleYBox.getText().equals("Enter Map Scale Y...")) {
			System.out.println("Enter a map scale Y!");
			return;
		}
		if (tlcornerX.getText().equals("Enter Top Left Corner X...")) {
			System.out.println("Enter an X coordinate for the top left corner!");
			return;
		}
		if (tlcornerY.getText().equals("Enter Top Left Corner Y...")) {
			System.out.println("Enter a Y coordinate for the top left corner!");
			return;
		}

		int tlx, tly, sx, sy;
		try {
			tlx = Integer.parseInt(tlcornerX.getText());
			tly = Integer.parseInt(tlcornerY.getText());
			sx = Integer.parseInt(scaleXBox.getText());
			sy = Integer.parseInt(scaleYBox.getText());
		} catch (Exception e) {
			System.out.println("One of the numbers entered was not a number. Please try again!");
			return;
		}

		FileWriter fout = null;
		PrintWriter out = null;
		try {
			File f = new File(executionPath + File.separator + fileName.substring(0, fileName.length() - 4) + ".yml");
			if (!f.exists()) {
				f.createNewFile();
			}
			fout = new FileWriter(f);
			out = new PrintWriter(fout);

			out.println("regions:");
			
			for (Territory t : tm.territories) {
				t.print(out, tlx, tly, sx, sy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}

		System.out.println("0, 0 is located at pixels: ");
		double rtlx = tlx / sx;
		double rtly = tlx / sy;
		double nx = (rtlx + 0) * sx;
		double ny = (rtly + 0) * sy;
		System.out.println(nx + "," + ny);

		System.out.println("1000, 1000 is located at pixels: ");
		double rtlx2 = tlx / sx;
		double rtly2 = tlx / sy;
		double nx2 = (rtlx2 + 1000) * sx;
		double ny2 = (rtly2 + 1000) * sy;
		System.out.println(nx2 + "," + ny2);

		try {
			File f2 = new File(executionPath + File.separator + fileName.substring(0, fileName.length() - 4) + "-map.png");
			if (!f2.exists()) {
				f2.createNewFile();
			}
			ImageIO.write(tm.workingImage, "png", f2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String[] values;

		if (args.length > 0) {
			values = args;
		} else {
			values = readInfoFromUser();
		}

		fileName = values[0];
		String ymlName = values[1];
		executionPath = System.getProperty("user.dir");
		String pathToImage = executionPath + File.separator + fileName;
		System.out.println(pathToImage);
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(pathToImage));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<Territory> terrs = readExistingYML(ymlName);
		if (image != null) {
			System.out.println("image not null!");
			TerritoryCreator t = new TerritoryCreator(image);
			if(terrs != null){
				t.addTerritories(terrs);
			}
			t.run();
		}
	}

	private void addTerritories(ArrayList<Territory> terrs) {
		System.out.println("add territories called!");
		for(Territory t : terrs){
			tm.territories.add(t);
		}
		System.out.println("Size: " + terrs.size());
	}

	private static ArrayList<Territory> readExistingYML(String fileName) {
		try {
			if (!fileName.endsWith(".yml")){
				System.out.println("doesn't end with yml!");
				return null;
			}
			String pathToFile = executionPath + File.separator + fileName;
			File f = new File(pathToFile);
			if (!f.exists())
				return null;
			FileInputStream in = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			ArrayList<Point> currentPoints = new ArrayList<Point>();
			ArrayList<Territory> retval = new ArrayList<Territory>();
			br.readLine();
			String terrName = br.readLine();
			terrName = terrName.substring(2, terrName.length() - 1);
			String strLine;
			boolean readingPoints = false;
			
			while ((strLine = br.readLine()) != null) {
				System.out.println(strLine);
				if (strLine.startsWith("    points:")) {
					
					readingPoints = true;
					
				}
				
				if (strLine.startsWith("    -") && readingPoints) {
					
					String[] split = strLine.split(",");
					double x = Double.parseDouble(split[1]);
					double z = Double.parseDouble(split[0].substring(6, split[0].length()));
					Point p = new Point((int) Math.round(x), (int) Math.round(z));
					System.out.println("adding point: " + x + ", " + z);
					currentPoints.add(p);
				}
				
				if (!strLine.startsWith("    -") && !strLine.startsWith("    points:") && readingPoints) {

					readingPoints = false;
					
					Territory t = new Territory(currentPoints, terrName);
					retval.add(t);
					System.out.println("added territory with name " + terrName + " and points size " + currentPoints.size());

					currentPoints.clear();
					terrName = strLine.substring(2, strLine.length() - 1);
				
				}
				
			}
			
			ArrayList<Point> p2 = new ArrayList<Point>();
			for(Point p : currentPoints){
				p2.add(p.clone());
			}
			Territory t = new Territory(p2, terrName);
			retval.add(t);
			System.out.println("added territory with name " + terrName + " and points size " + p2.size());
			
			br.close();
			return retval;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String[] readInfoFromUser() {
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter image filename with extension:");
		String name = s.nextLine();
		System.out.println("Please enter .yml Territories file to load or skip to skip");
		String terrfile = s.nextLine();
		s.close();
		return new String[] { name, terrfile };
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 / 30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();

			int speed;
			String text = moveSpeed.getText();
			if (moveSpeed.getText() == moveSpeed.defaultText) {
				speed = 5;
			} else {
				try {
					speed = Integer.parseInt(text);
				} catch (Exception e) {
					System.out.println("Speed value is not a number, please enter a number!");
					speed = 5;
				}
			}
			if (upPressed)
				tm.changeYDist(-1 * speed);
			if (downPressed)
				tm.changeYDist(speed);
			if (leftPressed)
				tm.changeXDist(-1 * speed);
			if (rightPressed)
				tm.changeXDist(speed);
		}
	}

	@Override
	public void paint(Graphics g) {
		BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (ScreenObject o : objects) {
			o.draw(image.getGraphics());
		}
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (focus == tm) {
			switch (arg0.getKeyChar()) {
			case 'w':
				upPressed = true;
				return;
			case 'a':
				leftPressed = true;
				return;
			case 'd':
				rightPressed = true;
				return;
			case 's':
				downPressed = true;
				return;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (focus == tm) {
			switch (arg0.getKeyChar()) {
			case 'w':
				upPressed = false;
				return;
			case 'a':
				leftPressed = false;
				return;
			case 'd':
				rightPressed = false;
				return;
			case 's':
				downPressed = false;
				return;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		if (focus != null) {
			focus.onFocusKeyTyped(arg0);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Point p = new Point(arg0.getX(), arg0.getY());
		if (focus != null && focus.pointWithin(p)) {
			focus.onMouseClick(arg0);
		} else {
			if (focus != null)
				focus.onFocusLost();
			for (ScreenObject o : objects) {
				if (o.pointWithin(p)) {
					debug("giving focus to object!");
					focus = o;
					focus.onFocusGain();
					focus.onMouseClick(arg0);
					return;
				}
			}
			focus = null;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void debug(String value) {
		System.out.println(value);
	}

	public static TerritoryCreator getInstance() {
		return instance;
	}
}
