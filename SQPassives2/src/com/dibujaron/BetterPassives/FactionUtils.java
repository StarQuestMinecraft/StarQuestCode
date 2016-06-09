package com.dibujaron.BetterPassives;

import org.bukkit.Location;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;

public class FactionUtils {
	public static boolean isInClaimedLand(Location l) {
		Faction f = BoardColl.get().getFactionAt(PS.valueOf(l));
		if (f == null)
			return false;
		String id = f.getId();
		if (id.equals(Factions.ID_NONE) || id.equals(Factions.ID_SAFEZONE) || id.equals(Factions.ID_WARZONE)) {
			return false;
		}
		return true;
	}
}
