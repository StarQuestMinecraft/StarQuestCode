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

public class FileDataHandler {

	// private static HashMap<Material, ItemData> dataMap = new
	// HashMap<Material, ItemData>();
	private static List<Weighable<ItemData>> itemDataList = new ArrayList<Weighable<ItemData>>();
	private static List<Weighable<ShipClassData>> shipDataList = new ArrayList<Weighable<ShipClassData>>();

	public static void loadFromFile() {
		try {
			File f = new File(SQContracts.get().getDataFolder() + File.separator + "itemdata.txt");
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			while (line != null) {
				if (!line.startsWith("#")) {
					ItemData d = loadItemFromLine(line);
					if(d != null){
						Weighable<ItemData> w = new Weighable<ItemData>(d, d.getRarity());
						itemDataList.add(w);
					}
				}
				line = br.readLine();
			}
			br.close();

			f = new File(SQContracts.get().getDataFolder() + File.separator + "shipclassdata.txt");
			br = new BufferedReader(new FileReader(f));
			line = br.readLine();
			while (line != null) {
				if (!line.startsWith("#")) {
					ShipClassData d = loadShipFromLine(line);
					if(d != null){
						Weighable<ShipClassData> w = new Weighable<ShipClassData>(d, d.getRarity());
						shipDataList.add(w);
					}
				}
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ItemData pickRandomItem(ContractPlayerData d, Random generator, boolean blackMarket, ItemData[] exclude) {
		WeightedRandom<ItemData> r = new WeightedRandom<ItemData>(itemDataList, generator);
		ItemData data = r.generate().getObject();
		// while (if the currencies don't match or the data does match
		while (!checkCurrenciesItem(d, data, blackMarket) || itemArrayContains(exclude, data)) {
			data = r.generate().getObject();
		}
		return data;
	}

	private static boolean itemArrayContains(ItemData[] array, ItemData check) {
		for (ItemData d : array) {
			if (d != null && d.getType() == check.getType() && d.getData() == check.getData()) {
				return true;
			}
		}
		return false;
	}

	public static ShipClassData pickRandomShipClass(ContractPlayerData d, Random generator, boolean blackMarket, ShipClassData[] exclude) {
		WeightedRandom<ShipClassData> r = new WeightedRandom<ShipClassData>(shipDataList, generator);
		ShipClassData data = r.generate().getObject();
		while (!checkCurrenciesShip(d, data, blackMarket) || shipArrayContains(exclude, data.getType())) {
			data = r.generate().getObject();
		}
		return data;
	}

	private static boolean shipArrayContains(ShipClassData[] array, String check) {
		for (ShipClassData d : array) {
			if (d != null && d.getType().equals(check)) {
				return true;
			}
		}
		return false;
	}

	private static boolean checkCurrenciesItem(ContractPlayerData d, ItemData data, boolean blackMarket) {
		if (blackMarket) {
			return d.getBalanceInCurrency("smuggling") >= data.getMinLevel();
		} else {
			return d.getBalanceInCurrency("trading") >= data.getMinLevel();
		}
	}

	private static boolean checkCurrenciesShip(ContractPlayerData d, ShipClassData data, boolean blackMarket) {
		if (blackMarket) {
			return d.getBalanceInCurrency("infamy") >= data.getMinLevel();
		} else {
			return d.getBalanceInCurrency("reputation") >= data.getMinLevel();
		}
	}

	private static ItemData loadItemFromLine(String line) {
		// WOOD,0,5.0,0,1.0
		try {
			StringTokenizer tok = new StringTokenizer(line, ",");
			Material type = Material.valueOf(tok.nextToken());
			byte data = (byte) Integer.parseInt(tok.nextToken());
			double price = Double.parseDouble(tok.nextToken());
			int minLevel = Integer.parseInt(tok.nextToken());
			double rarity = Double.parseDouble(tok.nextToken());
			return new ItemData(type, data, price, minLevel, rarity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static ShipClassData loadShipFromLine(String line) {
		// Starfighter,1000,5,1.0
		try {
			StringTokenizer tok = new StringTokenizer(line, ",");
			String className = tok.nextToken();
			double price = Double.parseDouble(tok.nextToken());
			int minLevel = Integer.parseInt(tok.nextToken());
			double rarity = Double.parseDouble(tok.nextToken());
			return new ShipClassData(className, price, minLevel, rarity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
