package com.starquestminecraft.sqcontracts.randomizer.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.bukkit.Material;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;
import com.starquestminecraft.sqcontracts.util.Weighable;
import com.starquestminecraft.sqcontracts.util.WeightedRandom;

public class ItemData {

	//private static HashMap<Material, ItemData> dataMap = new HashMap<Material, ItemData>();
	private static List<Weighable<ItemData>> dataList = new ArrayList<Weighable<ItemData>>();
	public static void loadFromFile() {
		System.out.println("Begin loading item data.");
		try {
			File f = new File(SQContracts.get().getDataFolder() + File.separator + "itemdata.txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			while(line != null){
				ItemData d = loadFromLine(line);
				Weighable<ItemData> w = new Weighable<ItemData>(d, d.getRarity());
				dataList.add(w);
				line = br.readLine();
			}
			br.close();
			System.out.println("Finished loading item data: " + dataList.size() + " entries.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ItemData pickRandomItem(ContractPlayerData d, Random generator){
		WeightedRandom<ItemData> r = new WeightedRandom<ItemData>(dataList,generator);
		ItemData data = r.generate().getObject();
		while(!checkCurrencies(d, data)){
			data = r.generate().getObject();
		}
		return data;
	}

	private static boolean checkCurrencies(ContractPlayerData d, ItemData data) {
		return d.getBalanceInCurrency("smuggling") >= data.getMinLevel() || d.getBalanceInCurrency("trading") >= data.getMinLevel();
	}

	private static ItemData loadFromLine(String line) {
		//WOOD,2,0,5.0,0,1.0
		StringTokenizer tok = new StringTokenizer(line, ",");
		Material type = Material.valueOf(tok.nextToken());
		byte data = (byte) Integer.parseInt(tok.nextToken());
		double price = Double.parseDouble(tok.nextToken());
		int minLevel = Integer.parseInt(tok.nextToken());
		double rarity = Double.parseDouble(tok.nextToken());
		return new ItemData(type, data, price, minLevel, rarity);
	}

	Material myType;
	double myPrice;
	int minLevel;
	double rarity;
	byte data;

	public ItemData(Material type, byte data, double unitPrice, int minLevel, double rarityMod) {
		myType = type;
		this.data = data;
		myPrice = unitPrice;
		this.minLevel = minLevel;
		rarity = rarityMod;
	}

	public Material getType() {
		return myType;
	}

	public byte getData() {
		return data;
	}

	public double getUnitPrice() {
		return myPrice;
	}

	public double getRarity() {
		return rarity;
	}

	public int getMinLevel() {
		return minLevel;
	}
}
