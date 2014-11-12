package com.starquestminecraft.dynamicwhitelist.flatfiledb;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.mini.Arguments;
import com.mini.Mini;
import com.starquestmineraft.dynamicwhitelist.Database;
import com.starquestmineraft.dynamicwhitelist.Whitelister;

public class DatabaseFlatfile extends Database{

	Mini database = new Mini(Whitelister.getInstance().getDataFolder() + "", "premiumdata.mini");
	
	private ArrayList<Runnable> runSyncQueue = new ArrayList<Runnable>();
	public DatabaseFlatfile(){
		Whitelister.getInstance().getProxy().getScheduler().schedule(Whitelister.getInstance(), new Runnable(){

			@Override
			public void run() {
				for(Runnable r : runSyncQueue){
					r.run();
				}
				runSyncQueue.clear();
			}
			
		}, 1000, 1000, TimeUnit.SECONDS);
	}
	@Override
	public void addPremiumTime(final String playername, final int hours) {
		Whitelister.getInstance().getProxy().getScheduler().runAsync(Whitelister.getInstance(), new Runnable(){
			public void run(){
				addPremiumTimeAsync(playername, hours);
			}
		});
	}
	
	@Override
	public int getRemainingTime(UUID u){
		Arguments entry = database.getArguments(u.toString());
		if(entry == null) return 0;
		return getHoursRemaining(entry);
	}
	
	private void addPremiumTimeAsync(String playername, final int hours){
		final UUID u = super.uuidFromUsername(playername);
		runSyncQueue.add(new Runnable(){
			public void run(){
				addPremiumTime(u, hours);
			}
		});
	}
	
	@Override
	public void addPremiumTime(final UUID u, final int hours){
		//first load their arguments from the database
			Arguments entry = database.getArguments(u.toString());
			
			//if they aren't in the database they must be buying premium time before having logged into the server. Wierd.
			if(entry == null){
				entry = new Arguments(u.toString());
				entry.setValue("purchaseTime", 0 + "");
				entry.setValue("hoursPurchased", 0 + "");
				entry.setValue("permanent", false + "");
				//TODO initialize entry values
			}
			
			int hoursRemaining = getHoursRemaining(entry);
			int newHours = hoursRemaining + hours;
			entry.setValue("purchaseTime", System.currentTimeMillis() + "");
			entry.setValue("hoursPurchased", newHours + "");
			String key = u.toString();
			if(database.hasIndex(key)){
				database.removeIndex(key);
			}
			database.addIndex(u.toString(), entry);
			database.update();

	}
	
	private int getHoursRemaining(Arguments entry){
		//get the timestamp of the last time they paid, and how many hours they had purchased at that point
		long previousPurchaseTime = entry.getLong("purchaseTime");
		int hoursPurchased = entry.getInteger("hoursPurchased");
		
		long currentTime = System.currentTimeMillis();
		
		int hoursDifference = millisToHours(currentTime - previousPurchaseTime);
		
		int retval = hoursPurchased - hoursDifference;
		if(retval < 0) return 0;
		return retval;
	}
	
	private int millisToHours(long millis){
		return (int) (((millis / 1000) / 60) / 60);
	}

	@Override
	public boolean hasPlayedBefore(UUID u) {
		return database.hasIndex(u.toString());
	}

	@Override
	public void registerNewPlayer(UUID u) {
		Arguments entry = new Arguments(u.toString());
		entry.setValue("purchaseTime", 0 + "");
		entry.setValue("hoursPurchased", 0 + "");
		entry.setValue("permanent", false + "");
		database.addIndex(entry.getKey(), entry);
		database.update();
	}

	@Override
	public void setPermanent(UUID u, boolean permanent) {
		Arguments entry = database.getArguments(u.toString());
		
		//if they aren't in the database they must be buying premium time before having logged into the server. Wierd.
		if(entry == null){
			entry = new Arguments(u.toString());
			entry.setValue("purchaseTime", 0 + "");
			entry.setValue("hoursPurchased", 0 + "");
			entry.setValue("permanent", false + "");
			//TODO initialize entry values
		}
				
		entry.setValue("permanent", permanent + "");
		if(database.hasIndex(u.toString())){
			database.removeIndex(u.toString());
		}
		database.addIndex(entry.getKey(), entry);
		database.update();
	}

	@Override
	public boolean isPermanent(UUID u) {
		Arguments entry = database.getArguments(u.toString());
		if(entry == null) return false;
		boolean permanent = entry.getBoolean("permanent");
		return permanent;
	}
}
