
package com.regalphoenixmc.SQRankup;

public class RankupPlayer {

	private String name;
	private long lastKillTime;
	private String lastKillName;
	private int kills;

	public RankupPlayer(String name, long lastKillTime, String lastKillName, int kills) {

		this.name = name;
		this.lastKillTime = lastKillTime;
		this.lastKillName = lastKillName;
		this.kills = kills;
	}

	public String getName() {

		return name;
	}

	public double getLastKillTime() {

		return lastKillTime;
	}

	public String getLastKillName() {

		return lastKillName;
	}

	public void setName(String name) {

		this.name = name;
	}

	public void setLastKillTime(long lastKillTime) {

		this.lastKillTime = lastKillTime;
	}

	public void setLastKillName(String lastKillName) {

		this.lastKillName = lastKillName;
	}

	public void setKills(int kills) {

		this.kills = kills;
	}

	public int getKills() {

		return kills;
	}

	public void saveData() {

		Database.updateEntry(this.name, this.lastKillTime, this.lastKillName, this.kills);
	}

	public void saveNew() {

		Database.newEntry(this.name, this.lastKillTime, this.lastKillName, this.kills);

	}

}
