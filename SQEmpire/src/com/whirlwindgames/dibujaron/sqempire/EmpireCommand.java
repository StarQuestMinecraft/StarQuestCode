package com.whirlwindgames.dibujaron.sqempire;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;

public class EmpireCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {

		if(!(sender instanceof Player)){
			sender.sendMessage("The empire command can only be run ingame.");
		}
		final Player p = (Player) sender;
		AsyncUtil.runAsync(new Runnable(){
			public void run(){
				processEmpireCommand(p, args);
			}
		});
		return true;
	}
	
	private void processEmpireCommand(Player p, String[] args){
		if(args.length == 0){
			cmdHelp(p,args);
		} else {
			String argKey = args[0];
			switch(argKey){
			case "stats":
			case "statistics":
			case "power":
			case "s":
			case "p":
				cmdStats(p, args);
			case "join":
			case "j":
				cmdJoin(sender, args);
			case "claim":
			case "c":
				cmdClaim(sender, args);
			case "leave":
			case "l":
				cmdLeave(sender, args);
			default:
				cmdHelp(sender);
			}
		}
	}
	
	private void cmdHelp(Player p, String[] args){
		SQEmpire.echo(p, "SQEmpire commands:",
				"==================================",
				"/empire stats <empire/player>: displays statistics about the named empire or player",
				"/empire join <empire>: join the named empire.",
				"/empire lore <empire>: displays lore about the named empire.",
				"/empire leave: leave your empire.",
				"/empire claim: claims the chunk you are standing in for your empire.",
				"/empire map: displays a link to the interative galaxy map.",
				"/empire help: displays this dialogue.");
	}
	
	private void cmdClaim(Player p, String[] args){
		p.sendMessage("Attempting to claim, please wait...");
		
		boolean canClaim - EmpirePower.
	}
}
