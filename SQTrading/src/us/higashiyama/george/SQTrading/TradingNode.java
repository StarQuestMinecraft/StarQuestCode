
package us.higashiyama.george.SQTrading;

import java.io.Serializable;

public class TradingNode implements Serializable {

	private static final long serialVersionUID = 2489133053882919732L;
	private int x;
	private int y;
	private int z;
	private String name;
	private String world;
	private String server;

	public TradingNode(int x, int y, int z, String name, String world, String server) {

		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
		this.world = world;
		this.server = server;
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

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
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
