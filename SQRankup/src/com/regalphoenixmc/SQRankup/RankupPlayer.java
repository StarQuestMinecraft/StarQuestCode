
package com.regalphoenixmc.SQRankup;

public class RankupPlayer {

	private String name;
	private long lastKillTime;
	private String lastKillName;
	private int kills;
	private int asyncKills;

	public RankupPlayer(String name, long lastKillTime, String lastKillName, int kills) {

		this.name = name;
		this.lastKillTime = lastKillTime;
		this.lastKillName = lastKillName;
		this.kills = kills;
		this.asyncKills = 0;
	}

	public String getName() {

		return this.name;
	}

	public double getLastKillTime() {

		return this.lastKillTime;
	}

	public String getLastKillName() {

		return this.lastKillName;
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

		return this.kills;
	}

	public void saveData() {

		System.out.println("Saving data");
		if (this.asyncKills != 0) {
			System.out.println("Async kills ain't 0");
			Database.addKills(this.name, this.asyncKills);
		}
		Database.updateEntry(this.name, Long.valueOf(this.lastKillTime), this.lastKillName);

	}

	public void saveNew() {

		Database.newEntry(this.name, Long.valueOf(this.lastKillTime), this.lastKillName, this.kills);
	}

	public int getAsyncKills() {

		return asyncKills;
	}

	public void setAsyncKills(int asyncKills) {

		this.asyncKills = asyncKills;
	}
}
