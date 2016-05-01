package com.whirlwindgames.dibujaron.sqempire;

import java.util.Arrays;

import net.countercraft.movecraft.bungee.BungeePlayerHandler;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePercentage;
import com.whirlwindgames.dibujaron.sqempire.database.object.EmpirePlayer;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;

public class EmpireCommand implements CommandExecutor{
	
	public static int STARTING_CASH = 10000;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if(cmd.getName().equalsIgnoreCase("playerSendEmpire") && (sender instanceof ConsoleCommandSender || sender instanceof BlockCommandSender)){
			Player p = Bukkit.getPlayer(args[0]);
			EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
			Empire e = ep.getEmpire();
			if(e == Empire.ARATOR){
				//SQEmpire.permission.playerAddGroup(p,"Arator0");
				BungeePlayerHandler.sendPlayer(p, "AratorSystem", "AratorSystem", 2598, 100, 1500);
				//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
						//"eb janesudo Aratorians, please welcome your newest member " + p.getName() + "!");
			} else if(e == Empire.REQUIEM){
				//SQEmpire.permission.playerAddGroup(p,"Requiem0");
				BungeePlayerHandler.sendPlayer(p, "QuillonSystem", "QuillonSystem", 1375, 100, -2381);
				//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
						//"eb janesudo Requiem, please welcome your newest member " + p.getName() + "!");
			} else if(e == Empire.YAVARI){
				//SQEmpire.permission.playerAddGroup(p,"Yavari0");
				BungeePlayerHandler.sendPlayer(p, "YavarSystem", "YavarSystem", 0, 231, 2500);
				//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
				//		"eb janesudo Yavari, please welcome your newest member " + p.getName() + "!");
			}
			return true;
		}
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
			EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
			p.sendMessage("Your empire is " + ep.getEmpire().getName() + ".");
		} else {
			if(args[0].equalsIgnoreCase("join")){
				cmdJoin(p, args);
			} else {
				p.sendMessage("Do /empire join");
			}
		}
		return;
		/*if(args.length == 0){
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
		}*/
	}
	
	private void cmdJoin(Player p, String[] args){
		EmpirePlayer ep = EmpirePlayer.getOnlinePlayer(p);
		if(ep.getEmpire() != Empire.NONE){
			p.sendMessage("You are already in an Empire, you cannot join one.");
			return;
		}
		if(args.length < 2){
			p.sendMessage("You must specify an empire to join, please choose Arator, Requiem, or Yavari.");
		}
		if(args.length == 2){
			String empire = args[1];
			Empire e = Empire.fromString(empire);
			if(e == Empire.NONE){
				p.sendMessage("No empire found with name " + empire + ", please choose Arator, Requiem, or Yavari.");
			}
			int[] pop = EmpirePercentage.getEmpirePopulationDistribution();
			System.out.println("Populations are " + pop[0] + "," + pop[1] + "," + pop[2]);
			double total = pop[0] + pop[1] + pop[2];
			int theirEmpire = e.getID();
			double theirPercentage = pop[theirEmpire-1] / total;
			System.out.println("percentage for empire " + theirEmpire + " is " + theirPercentage);
			if(theirPercentage > 0.36){
				p.sendMessage("This empire cannot be joined at this time, it has more than 36% of the player population. Please pick again.");
				return;
			}
			if(theirPercentage > 0.35){
				p.sendMessage("This empire currently has 35% of the playerbase and may soon not allow new members.");
				p.sendMessage(ChatColor.RED + "If you are joining with friends, it is possible that only some of you will get into this empire.");
				p.sendMessage("To avoid being split up, we recommend that you join another empire.");
			} else if (theirPercentage > 0.345){
				p.sendMessage("This empire is overpopulated and as such you will recieve 25% less starting cash if you join it.");
			} else if (theirPercentage > 0.30){
				p.sendMessage("This empire has normal population and so you will recieve normal starting cash if you join it.");
			} else {
				p.sendMessage("This empire is underpopulated and you will recieve 25% additional starting cash if you join it.");
			}
			p.sendMessage("Are you sure you want to join " + e.getName() + "? Type / empire join " + e.getName() + " confirm to confirm.");
		} else if(args.length == 3){
			if(args[2].equalsIgnoreCase("confirm")){
				String empire = args[1];
				Empire e = Empire.fromString(empire);
				if(e == Empire.NONE){
					p.sendMessage("No empire found with name " + empire + ", please choose Arator, Requiem, or Yavari.");
				}
				int[] pop = EmpirePercentage.getEmpirePopulationDistribution();
				double total = pop[0] + pop[1] + pop[2];
				int theirEmpire = e.getID();
				double theirPercentage = pop[theirEmpire-1] / total;
				if(theirPercentage > 0.36){
					p.sendMessage("This empire cannot be joined at this time, it has more than 36% of the player population. Please pick again.");
					return;
				} else {
					ep.setEmpire(e);
					if(theirPercentage > 0.345)
						SQEmpire.economy.depositPlayer(p, 7500);
					else if(theirPercentage > 0.30){
						SQEmpire.economy.depositPlayer(p, 10000);
					} else {
						SQEmpire.economy.depositPlayer(p, 12500);
					}
					p.sendMessage("You have succesfully joined empire " + e + "!");
					if(e == Empire.ARATOR){
						SQEmpire.permission.playerAddGroup(p,"Arator0");
						BungeePlayerHandler.sendPlayer(p, "AratorSystem", "AratorSystem", 2598, 100, 1500);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
								"eb janesudo Aratorians, please welcome your newest member " + p.getName() + "!");
					} else if(e == Empire.REQUIEM){
						SQEmpire.permission.playerAddGroup(p,"Requiem0");
						BungeePlayerHandler.sendPlayer(p, "QuillonSystem", "QuillonSystem", 1375, 100, -2381);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
								"eb janesudo Requiem, please welcome your newest member " + p.getName() + "!");
					} else if(e == Empire.YAVARI){
						SQEmpire.permission.playerAddGroup(p,"Yavari0");
						BungeePlayerHandler.sendPlayer(p, "YavarSystem", "YavarSystem", 0, 231, 2500);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
								"eb janesudo Yavari, please welcome your newest member " + p.getName() + "!");
					}
					SQEmpire.permission.playerRemoveGroup(p,"Guest");

				}
			} else {
				p.sendMessage("Incorrect usage, type /empire join <name>");
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
	
	/*private void cmdClaim(Player p, String[] args){
		p.sendMessage("Attempting to claim, please wait...");
		
		boolean canClaim - EmpirePower.
	}*/
}
