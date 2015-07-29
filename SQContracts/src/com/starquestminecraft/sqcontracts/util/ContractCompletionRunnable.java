package com.starquestminecraft.sqcontracts.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.countercraft.movecraft.craft.Craft;
import net.countercraft.movecraft.craft.CraftManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.contracts.Contract;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;

public class ContractCompletionRunnable extends BukkitRunnable {

	private static List<Holder> toComplete = Collections.synchronizedList(new ArrayList<Holder>());

	public static void addToCompletionQueue(Contract c, ContractPlayerData d) {
		toComplete.add(new Holder(c, d));
	}

	@Override
	public void run() {
		for (Holder h : toComplete) {
			try {
				Contract c = h.c;
				Player p = Bukkit.getPlayer(c.getPlayer());
				if (p == null) {
					continue;
				}

				Craft craft = CraftManager.getInstance().getCraftByPlayer(p);
				if (craft == null) {
					continue;
				}

				CompletionStatus status = c.complete(craft);
				if (status == CompletionStatus.COMPLETE) {
					h.d.getContracts().remove(c);
					p.sendMessage("You have succesfully completed this contract!");
					c.giveRewards(h.d);
					queueSave(h.d);
				} else if (status == CompletionStatus.PARTIAL) {
					p.sendMessage("You have completed part of this contract. You will be rewarded when the contract is completed fully.");
					queueSave(h.d);
				} else {
					p.sendMessage("You do not have any of the required materials for this contract in your ship.");
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		toComplete.clear();
	}

	private void queueSave(final ContractPlayerData d) {
		Bukkit.getServer().getScheduler().runTaskAsynchronously(SQContracts.get(), new Runnable() {
			public void run() {
				SQContracts.get().getContractDatabase().updatePlayerData(d.getPlayer(), d);
			}
		});
	}

}

class Holder {
	Contract c;
	ContractPlayerData d;

	public Holder(Contract c, ContractPlayerData d) {
		this.c = c;
		this.d = d;
	}
}
