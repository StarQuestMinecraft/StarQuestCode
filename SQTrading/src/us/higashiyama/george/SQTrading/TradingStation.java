
package us.higashiyama.george.SQTrading;

import org.bukkit.Location;

public class TradingStation {

	private String name;
	private int x;
	private int y;
	private int z;
	private String world;
	private String server;

	public TradingStation(String name, int x, int y, int z, String world, String server) {

		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.server = server;
	}

	public Location getLocation() {

		return new Location(TradingCore.instance.getServer().getWorld(this.world), this.x, this.y, this.z);
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public int getX() {

		return x;
	}

	public void setX(int x) {

		this.x = x;
	}

	public int getY() {

		return y;
	}

	public void setY(int y) {

		this.y = y;
	}

	public int getZ() {

		return z;
	}

	public void setZ(int z) {

		this.z = z;
	}

	public String getWorld() {

		return world;
	}

	public void setWorld(String world) {

		this.world = world;
	}

	public String getServer() {

		return server;
	}

	public void setServer(String server) {

		this.server = server;
	}

}
