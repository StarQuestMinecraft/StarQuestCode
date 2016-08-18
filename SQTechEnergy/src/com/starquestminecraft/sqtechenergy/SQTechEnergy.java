package com.starquestminecraft.sqtechenergy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.sqtechenergy.objects.AdvancedGenerator;
import com.sqtechenergy.objects.BasicGenerator;
import com.sqtechenergy.objects.BioGenerator;
import com.sqtechenergy.objects.Charger;
import com.sqtechenergy.objects.Fuel;
import com.sqtechenergy.objects.PowerCauldron;
import com.sqtechenergy.objects.RedstoneGenerator;
import com.sqtechenergy.objects.SolarPanel;
import com.sqtechenergy.objects.SteamEngine;
import com.sqtechenergy.objects.WaterTurbine;
import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechenergy.tasks.ChargerTask;
import com.starquestminecraft.sqtechenergy.tasks.GeneratorTask;

public class SQTechEnergy extends JavaPlugin{

	public final Logger logger = Logger.getLogger("Minecraft");
	static SQTechEnergy plugin;

	public static FileConfiguration config = null;

	public static List<Fuel> fuels = new ArrayList<Fuel>();

	boolean enabled = false;

	@Override
	public void onEnable() {

		plugin = this;

		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");

		if (!new File(this.getDataFolder(), "config.yml").exists()) {

			saveDefaultConfig();
			saveConfig();

		}

		config = getConfig();

		//Basic generator fuels
		for (String fuel : config.getConfigurationSection("basic generator.fuel").getKeys(false)) {

			String path = "basic generator.fuel." + fuel;

			Fuel fuelObject = new Fuel();

			String[] split = config.getString(path + ".id").split(":");

			if (split.length == 1) {

				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = (short) 0;

			} else {

				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = Short.parseShort(split[1]);

			}

			fuelObject.energyPerTick = config.getInt(path + ".energy per tick");
			fuelObject.burnTime = config.getInt(path + ".burn time");
			fuelObject.generator = "Basic Generator";

			fuels.add(fuelObject);

		}

		//Advanced Generator fuels
		for (String fuel : config.getConfigurationSection("advanced generator.fuel").getKeys(false)) {

			String path = "advanced generator.fuel." + fuel;

			Fuel fuelObject = new Fuel();

			String[] split = config.getString(path + ".id").split(":");

			if (split.length == 1) {

				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = (short) 0;

			} else {

				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = Short.parseShort(split[1]);

			}

			fuelObject.energyPerTick = config.getInt(path + ".energy per tick");
			fuelObject.burnTime = config.getInt(path + ".burn time");
			fuelObject.generator = "Advanced Generator";

			fuels.add(fuelObject);

		}

		//Bio Generator fuels
		for (String fuel : config.getConfigurationSection("bio generator.fuel").getKeys(false)) {

			String path = "bio generator.fuel." + fuel;

			Fuel fuelObject = new Fuel();

			String[] split = config.getString(path + ".id").split(":");

			if (split.length == 1) {

				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = (short) 0;

			} else {

				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = Short.parseShort(split[1]);

			}

			fuelObject.energyPerTick = config.getInt(path + ".energy per tick");
			fuelObject.burnTime = config.getInt(path + ".burn time");
			fuelObject.generator = "Bio Generator";

			fuels.add(fuelObject);

		}

		//Redstone Generator fuels
		for (String fuel : config.getConfigurationSection("redstone generator.fuel").getKeys(false)) {

			String path = "redstone generator.fuel." + fuel;

			Fuel fuelObject = new Fuel();

			String[] split = config.getString(path + ".id").split(":");

			if (split.length == 1) {

				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = (short) 0;

			} else {

				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = Short.parseShort(split[1]);

			}

			fuelObject.energyPerTick = config.getInt(path + ".energy per tick");
			fuelObject.burnTime = config.getInt(path + ".burn time");
			fuelObject.generator = "Redstone Generator";

			fuels.add(fuelObject);

		}

		//Steam Engine fuels
		for (String fuel : config.getConfigurationSection("steam engine.fuel").getKeys(false)) {

			String path = "steam engine.fuel." + fuel;

			Fuel fuelObject = new Fuel();

			String[] split = config.getString(path + ".id").split(":");

			if (split.length == 1) {

				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = (short) 0;

			} else {

				fuelObject.id = Integer.parseInt(split[0]);
				fuelObject.data = Short.parseShort(split[1]);

			}

			fuelObject.energyPerTick = config.getInt(path + ".energy per tick");
			fuelObject.burnTime = config.getInt(path + ".burn time");
			fuelObject.generator = "Steam Engine";

			fuels.add(fuelObject);

		}

		SQTechBase.addMachineType(new BasicGenerator());
		SQTechBase.addMachineType(new AdvancedGenerator());
		SQTechBase.addMachineType(new BioGenerator());
		SQTechBase.addMachineType(new RedstoneGenerator());
		SQTechBase.addMachineType(new SolarPanel());
		SQTechBase.addMachineType(new WaterTurbine());
		SQTechBase.addMachineType(new SteamEngine());
		SQTechBase.addMachineType(new Charger());
		SQTechBase.addMachineType(new PowerCauldron());

		(new GeneratorTask()).run();
		(new ChargerTask()).run();

	}

	@Override
	public void onDisable() {

		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");

		saveDefaultConfig();

	}

	public static SQTechEnergy getPluginMain() {

		return plugin;

	}

	public static BlockFace getOpposite(BlockFace blockFace) {

		switch(blockFace) {

		case NORTH: return BlockFace.SOUTH;
		case WEST: return BlockFace.EAST;
		case SOUTH: return BlockFace.NORTH;
		case EAST: return BlockFace.WEST;
		default: return BlockFace.NORTH;

		}

	}

}
