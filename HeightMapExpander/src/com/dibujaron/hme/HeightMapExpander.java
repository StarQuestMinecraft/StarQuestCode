package com.dibujaron.hme;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import javax.imageio.ImageIO;

public class HeightMapExpander {
	
	public static void main(String[] args){
		UUID u = UUID.randomUUID();
		long min = u.getLeastSignificantBits();
		long max = u.getMostSignificantBits();
		String a = String.valueOf(min);
		String b = String.valueOf(max);
		System.out.println(a);
		System.out.println(b);
		/*long value = mostSig;
		String newValue = value + "";
		int base = 16;
		int MAX_LENGTH;
		while(newValue.length() > 9){
			newValue = Long.toString(value, base);
			System.out.println("========================");
			System.out.println("base:" + base);
			System.out.println("value: " + newValue);
			base++;
		}
		System.out.println(newValue);
		System.out.println(base);
		
		long value2 = leastSig;
		String newValue2 = value2 + "";
		int base2 = 16;
		while(newValue2.length() > 8){
			newValue2 = Long.toString(value2, base2);
			System.out.println("========================");
			System.out.println("base2:" + base2);
			System.out.println("value2: " + newValue2);
			base2++;
		}*/
		//System.out.println(newValue2);
		//System.out.println(base2);
	}
	
	public byte[] longToBytes(long x) {
		byte[] arr = String.valueOf(x).getBytes();
		return arr;
	}

	public long bytesToLong(byte[] bytes) {
		long longVar = Long.valueOf(new String(bytes)).longValue();
		return longVar;
	}
	
	
	public static void main2(String[] args){
		
		//read input
		Scanner s = new Scanner(System.in);
		System.out.println("Enter filename of heightmap of existing world:");
		String existingPlanet = s.nextLine();
		System.out.println("Enter radius of existing world:");
		int existingRadius = Integer.parseInt(s.nextLine());
		System.out.println("Enter filename of heightmap of expanded world (untrimmed):");
		String expandedHeightmap = s.nextLine();
		System.out.println("Enter radius of expanded world:");
		int expandedRadius = Integer.parseInt(s.nextLine());
		System.out.println("Enter the desired final radius:");
		int finalRadius = Integer.parseInt(s.nextLine());
		System.out.println("Processing!");
		s.close();
		
		
		//load images
		BufferedImage existing = null;
		BufferedImage expanded = null;
		try {
		    existing = ImageIO.read(new File(existingPlanet));
		    expanded = ImageIO.read(new File(expandedHeightmap));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		//copy the existing onto the expanded with the correct offsets
		int offset = (expandedRadius - existingRadius) / 2;
		
		System.out.println("Offset: " + offset);
		for (int y = 0; y < existing.getHeight(); y++) {
		    for (int x = 0; x < existing.getWidth(); x++) {
		          int  clr   = existing.getRGB(x, y); 
		          int  red   = (clr & 0x00ff0000) >> 16;
		          int  green = (clr & 0x0000ff00) >> 8;
		          int  blue  =  clr & 0x000000ff;
		         
		          //black is 0,0,0
		          if(red < 5 && green < 5 && blue < 5){
		        	  // do not copy black
		        	  continue;
		          } else {
		        	  expanded.setRGB(x + offset, y + offset, clr);
		          }
		    }
		}
		
		//now draw black everywhere except the center
		int centerX = expanded.getWidth() / 2;
		int centerY = expanded.getHeight() / 2;
		
		System.out.println("Center: " + centerX + ", " + centerY);
		for (int y = 0; y < expanded.getHeight(); y++) {
		    for (int x = 0; x < expanded.getWidth(); x++) {
		    	if(finalRadius < distance(centerX, centerY, x, y)){
		    		expanded.setRGB(x, y, Color.BLACK.getRGB());
		    	}
		    }
		}
		
		//now output to final file
		File output = new File(existingPlanet.substring(0, existingPlanet.length() - 4) + "-expanded.jpg");
		try {
			ImageIO.write(expanded, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//done!
		return;
	}
	
	private static double distance(int x1, int y1, int x2, int y2){
		int x = x2 - x1;
		int y = y2 - y1;
		return Math.sqrt(x * x + y * y);
	}
}
