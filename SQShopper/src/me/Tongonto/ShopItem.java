package me.Tongonto;

import org.bukkit.Material;

public class ShopItem {
	Material itemMaterial = null;
	Double currentPrice = null;
	Double baseNumber = 100.0;
	Integer totalBought = null;
	Integer totalSold = null;
	Integer dailyBought = 1;
	Integer dailySold = 1;
	Integer data = 0;

	public ShopItem(Integer bought, Integer sold, Double base, Material material) {
		itemMaterial = material;
		totalBought = bought;
		totalSold = sold;
		baseNumber = base;
		updatePrice();
	}
	
	public Double getCurrentPrice() {
		updatePrice();
		return currentPrice;
	}
	
	public Integer getTotalBought() {
		return totalBought;
	}
	
	public Integer getTotalSold() {
		return totalSold;
	}
	
	public Integer getDailyBought() {
		return dailyBought;
	}
	
	public Integer getDailySold() {
		return dailySold;
	}
	
	public void setDailyBought(Integer number) {
		dailyBought = number;
	}
	
	public void setDailySold(Integer number) {
		dailySold = number;
	}
	
	public void setData(Integer number) {
		data = number;
	}
	
	public void addBoughtItems(Integer itemsBought) {
		totalBought = totalBought + itemsBought;
		dailyBought = dailyBought + itemsBought;
		updatePrice();
	}
	
	public void addSoldItems(Integer itemsSold) {
		totalSold = totalSold + itemsSold;
		dailySold = dailySold + itemsSold;
		updatePrice();
	}
	
	public void updatePrice() {
		Double modifier = (totalBought + 0.0) / (totalSold + 0.0);
		currentPrice = modifier * baseNumber;
		currentPrice = (double) Math.round(currentPrice * 100) / 100;
	}
	
}
