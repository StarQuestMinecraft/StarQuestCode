package com.dibujaron.alliances;

import org.bukkit.Bukkit;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.palmergames.bukkit.towny.Towny;

public class OrganizationUtils {
	
	public static TownyUniverse tVerse = ((Towny) Bukkit.getServer().getPluginManager().getPlugin("Towny")).getTownyUniverse();
	
	
	@SuppressWarnings("deprecation")
	public static String getOrganizationOfPlayer(String player){
		try{
			UPlayer p = UPlayer.get(player);
			if(p != null){
				Faction f = p.getFaction();
				if(f != null) return f.getName();
			}
			Resident r = tVerse.getResident(player);
			if(r != null){
			Town t = r.getTown();
				if(t != null){
					return t.getName();
				}
			}
		}catch(Exception e){
			return null;
		}
		return null;
	}
	public static String getOrganizationPlayerOwn(String player){
		try{
			UPlayer p = UPlayer.get(player);
			if(p != null){
				Faction f = p.getFaction();
				if(f != null){
					if(f.getLeader().equals(p)){
						return f.getName();
					}
				}
			}
			Resident r = tVerse.getResident(player);
			if(r != null){
			Town t = r.getTown();
				if(t != null){
					if(r.isMayor()){
						return t.getName();
					}
				}
			}
		}catch(Exception e){
			return null;
		}
		return null;
	}
	public static boolean isOwnerOfAlliance(String player, String alliance){
		String capital = Database.getCapitolOfAlliance(alliance);
		String org = getOrganizationPlayerOwn(player);
		if(org.equals(capital)){
			return true;
		}
		return false;
	}
}
