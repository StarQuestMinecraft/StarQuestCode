
package us.higashiyama.george.SQTrading;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CompletedTransaction {

	private UUID playerUUID;
	private String materialName;
	private short data;
	private int amount;
	private String tradeStation;
	private int id;

	public CompletedTransaction(UUID p, String itype, short data, int amount, String ts) {

		this.playerUUID = p;
		this.materialName = itype;
		this.data = data;
		this.amount = amount;
		this.tradeStation = ts;
	}

	public CompletedTransaction(UUID p, String itype, short data, int amount, String ts, int id) {

		this.playerUUID = p;
		this.materialName = itype;
		this.data = data;
		this.amount = amount;
		this.tradeStation = ts;
		this.id = id;
	}

	public ItemStack getItemStack() {

		return new ItemStack(Material.getMaterial(this.getMaterialName()), this.getAmount(), this.getData());
	}

	public CompletedTransaction copy() {

		return new CompletedTransaction(this.playerUUID, this.materialName, this.data, this.amount, this.tradeStation, this.id);
	}

	public UUID getPlayerUUID() {

		return playerUUID;
	}

	public void setPlayerUUID(UUID playerUUID) {

		this.playerUUID = playerUUID;
	}

	public String getMaterialName() {

		return materialName;
	}

	public void setMaterialName(String materialName) {

		this.materialName = materialName;
	}

	public short getData() {

		return data;
	}

	public void setData(short data) {

		this.data = data;
	}

	public int getAmount() {

		return amount;
	}

	public void setAmount(int amount) {

		this.amount = amount;
	}

	public String getTradeStation() {

		return tradeStation;
	}

	public void setTradeStation(String tradeStation) {

		this.tradeStation = tradeStation;
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

}
