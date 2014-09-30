
package us.higashiyama.george.SQTrading;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import us.higashiyama.george.SQTrading.Utils.TransactionType;

public class TradingOffer {

	private String tradingStation;
	private TransactionType tradingType;
	private UUID playerUUID;
	private String material;
	private int quantity;
	private short data;
	private double price;

	public TradingOffer(String tradingStation, TransactionType tradingType, UUID playerUUID, String material, int quantity, short data, double price) {

		this.tradingStation = tradingStation;
		this.tradingType = tradingType;
		this.playerUUID = playerUUID;
		this.material = material;
		this.quantity = quantity;
		this.data = data;
		this.price = price;
	}

	public ItemStack getItemStack() {

		return new ItemStack(Material.getMaterial(this.getMaterial()), 1, this.getData());
	}

	public String getTradingStation() {

		return tradingStation;
	}

	public void setTradingStation(String tradingStation) {

		this.tradingStation = tradingStation;
	}

	public TransactionType getTradingType() {

		return tradingType;
	}

	public void setTradingType(TransactionType tradingType) {

		this.tradingType = tradingType;
	}

	public UUID getPlayerUUID() {

		return playerUUID;
	}

	public void setPlayerUUID(UUID playerUUID) {

		this.playerUUID = playerUUID;
	}

	public String getMaterial() {

		return material;
	}

	public void setMaterial(String material) {

		this.material = material;
	}

	public int getQuantity() {

		return quantity;
	}

	public void setQuantity(int quantity) {

		this.quantity = quantity;
	}

	public short getData() {

		return data;
	}

	public void setData(short data) {

		this.data = data;
	}

	public double getPrice() {

		return price;
	}

	public void setPrice(double price) {

		this.price = price;
	}

}
