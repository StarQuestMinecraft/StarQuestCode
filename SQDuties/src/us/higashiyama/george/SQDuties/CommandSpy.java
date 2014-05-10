package us.higashiyama.george.SQDuties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class CommandSpy {

	public static HashMap<Player, File> playerFiles = new HashMap<Player, File>();
	public Player player;
	public File file;
	public static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd h:mm:ss a");
	
	
	CommandSpy(Player p) {
		
		this.player = p;
		this.file = new File("C:/StarQuest/CommandSpy/" + p.getName() + ".txt");
		if(!this.file.exists()) {
				try {
					this.file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
			playerFiles.put(this.player, this.file);
	}
	
	
	public static void writeString(final String message, final Player p){
		Runnable task = new Runnable() {

		    public void run() {
		    	
		        try {    
		        	Date date = new Date();
		        	String formattedDate = sdf.format(date);
		    		if(playerFiles.get(p) == null) return;
		    		try {
		    			FileWriter filewriter = new FileWriter(playerFiles.get(p), true);
		    			BufferedWriter bw = new BufferedWriter(filewriter);  
		    			bw.newLine();
		    			bw.write("[" + formattedDate + "]" + message);
		    			bw.close();
		    		} catch (IOException e1) {
		    			e1.printStackTrace();
		    		}

		        } catch (Exception ex) {
		        	System.out.println("Thread Exception: " + ex);
		        }
		        
		        
		    }
		};
		new Thread(task, "ServiceThread").start(); 

		
	}
	
	
	
}


