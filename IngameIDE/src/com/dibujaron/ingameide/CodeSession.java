package com.dibujaron.ingameide;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CodeSession {
	
	private static final String OPEN_IDE_MESSAGE = ChatColor.GREEN + "========" + ChatColor.GOLD + "[" + ChatColor.BLUE + "IGIDE v1.0.0" + ChatColor.GOLD + "]" + ChatColor.GREEN + "========";
	private static final String EXECUTE_MESSAGE = ChatColor.GREEN + "========" + ChatColor.GOLD + "[" + ChatColor.BLUE + "Executing" + ChatColor.GOLD + "]" + ChatColor.GREEN + "========";
	private Player coder;
	
	ArrayList<String> lines;
	
	public CodeSession(Player sender){
		coder = sender;
		lines = new ArrayList<String>();
		coder.sendMessage(OPEN_IDE_MESSAGE);
	}
	
	public void execute(){
		coder.sendMessage(EXECUTE_MESSAGE);
		ExecutionFactory.execute(lines.toArray(new String[lines.size()]));
	}
	
	public String[] getLines(){
		return lines.toArray(new String[lines.size()]);
	}
	
	public void addLine(String line){
		lines.add(line);
		coder.sendMessage(ChatColor.DARK_AQUA + line);
	}
}
