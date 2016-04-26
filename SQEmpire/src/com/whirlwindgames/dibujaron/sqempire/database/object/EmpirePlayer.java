package com.whirlwindgames.dibujaron.sqempire.database.object;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.whirlwindgames.dibujaron.sqempire.Empire;
import com.whirlwindgames.dibujaron.sqempire.Planet;
import com.whirlwindgames.dibujaron.sqempire.Settings;
import com.whirlwindgames.dibujaron.sqempire.database.EmpireDB;
import com.whirlwindgames.dibujaron.sqempire.util.AsyncUtil;
import com.whirlwindgames.dibujaron.sqempire.util.RSReader;
import com.whirlwindgames.dibujaron.sqempire.util.SuperPS;

public class EmpirePlayer {
	private UUID id;
	private String name;
	private int empire;
	private int[] powerRatings;
	private long[] lastSeenTimes;
	
	private static HashMap<UUID, EmpirePlayer> cache = new HashMap<UUID, EmpirePlayer>();
	
	public static EmpirePlayer getOnlinePlayer(Player p){
		EmpirePlayer ep = cache.get(p.getUniqueId());
		if(ep == null){
			throw new NullPointerException("Requested online EmpirePlayer but none exists!");
		}
		return ep;
	}
	
	public static void loadPlayerData(final Player player) {
		// TODO Auto-generated method stub
		AsyncUtil.runAsync(new Runnable(){
			public void run(){
				final EmpirePlayer p = new EmpirePlayer(player.getUniqueId(), true);
				AsyncUtil.runSync(new Runnable(){
					public void run(){
						cache.put(player.getUniqueId(), p);
					}
				});
			}
		});
	}
	public static void unloadPlayerData(Player player) {
		cache.remove(player.getUniqueId());
	}
	public EmpirePlayer(UUID u){
		this(u, true);
	}
	
	String updateCommand = "INSERT INTO minecraft.empire_player(uuid,lname,empire,"
			+ "power_0,power_1,power_2,power_3,power_4,"
			+ "lastSeen_0,lastSeen_1,lastSeen_2,lastSeen_3,lastSeen_4)"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE"
			+ "uuid=?,name=?,empire=?,"
			+ "power_0=?,power_1=?,power_2=?,power_3=?,power_4=?,"
	        + "lastSeen_0=?,lastSeen_1=?,lastSeen_2=?,lastSeen_3=?,lastSeen_4=?)";

	private EmpirePlayer(UUID u, boolean requestData){
		id = u;
		powerRatings = new int[5];
		lastSeenTimes = new long[5];
		if(requestData){
			requestData();
		}
	}
	
	private static LinkedList<EmpirePlayer> getPlayersOfflineMoreThan(Planet p, long ticks){
		int pid = p.getID();
		long earliestAcceptableLogoff = System.currentTimeMillis() - ticks;
		String query = "SELECT * from minecraft.empire_player WHERE lastSeen_" + pid + " < " + earliestAcceptableLogoff;
		RSReader rs = new RSReader(EmpireDB.requestData(query));
		LinkedList<EmpirePlayer> retval = new LinkedList<EmpirePlayer>();
		while(rs.next()){
			UUID u = UUID.fromString(rs.getString("uuid"));
			EmpirePlayer player = new EmpirePlayer(u, false);
			player.readData(rs);
			retval.add(player);
		}
		return retval;
	}
	
	public int getPowerOnPlanet(Planet p){
		return powerRatings[p.getID()];
	}
	
	public void updatePowerOnPlanet(Planet p, int power){
		powerRatings[p.getID()] = power;
	}
	
	public long getLastSeenOnPlanet(Planet p){
		return lastSeenTimes[p.getID()];
	}
	
	public void updateLastSeenOnPlanet(Planet p, long time){
		lastSeenTimes[p.getID()] = time;
	}
	
	public Empire getEmpire(){
		return Empire.fromID(empire);
	}
	
	private void requestData(){
		AsyncUtil.crashIfNotAsync();
		String query = "SELECT * from minecraft.empire_player WHERE uuid=" + id.toString();
		RSReader rs = new RSReader(EmpireDB.requestData(query));

		if(rs.next()){
			readData(rs);
		}
	}
	
	private void readData(RSReader rs){
		name = rs.getString("lname");
		empire = rs.getInt("empire");
		for(int i = 0; i < 5; i++){
			powerRatings[i] = rs.getInt("power_" + i);
			lastSeenTimes[i] = rs.getLong("lastSeen_" + i);
		}
	}
	
	public void publishData(){
		AsyncUtil.crashIfNotAsync();
		SuperPS ps = new SuperPS(EmpireDB.prepareStatement(updateCommand),13);
		ps.setDuplicate(0,id.toString());
		ps.setDuplicate(1,name);
		for(int i = 0; i < 5; i++){
			ps.setDuplicate(i+2,powerRatings[i]);
			ps.setDuplicate(i+7,lastSeenTimes[i]);
		}
		ps.executeAndClose();
	}
}
