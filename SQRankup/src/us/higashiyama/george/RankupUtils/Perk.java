
package us.higashiyama.george.RankupUtils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.filoghost.chestcommands.components.Command;
import com.gmail.filoghost.chestcommands.components.Icon;
import com.regalphoenixmc.SQRankup.SQRankup;

public class Perk {

	public String name;
	public String menuName;
	public int x, y;
	public List<String> permissions;
	public double moneyCost;
	public double killCost;
	// TODO: Future currencies here.
	public ConfigAccessor configAccessor;
	public FileConfiguration config;
	public List<String> preReqs;
	public int iconId;
	public int iconQuantity;
	public List<String> iconLore;
	// Commands should only be used for pseudo pinging servers
	public String commands;

	public Perk(String name) {

		this.configAccessor = new ConfigAccessor(SQRankup.instance(), name, "C:/StarQuest/GlobalConfigs/Rankup/Perks/");
		this.name = name;
		this.config = this.configAccessor.getConfig();
		this.moneyCost = this.config.getDouble("Money");
		this.killCost = this.config.getInt("Kills");
		this.permissions = this.config.getStringList("Permissions");
		this.preReqs = this.config.getStringList("PreReqs");
		this.iconId = this.config.getInt("ItemID");
		this.iconQuantity = this.config.getInt("ItemQuantity");
		this.iconLore = this.config.getStringList("Lore");
		this.menuName = this.config.getString("MenuName");
		this.x = this.config.getInt("X");
		this.y = this.config.getInt("Y");
		this.commands = this.config.getString("Commands");
		/*
		 * We get everything we need to build the Icon item, but we don't create
		 * the Icon object until we need it. We might modify the values of Icon
		 */

	}

	public void save() {

		this.config.set("Permissions", this.permissions);
		this.config.set("PreReqs", this.preReqs);
		this.config.set("Kills", this.killCost);
		this.config.set("Money", this.moneyCost);
		this.configAccessor.saveConfig();
	}

	public void reload() {

		this.configAccessor.reloadConfig();
	}

	public Icon build() {

		Icon i = new Icon(Material.getMaterial(this.iconId));
		i.setAmount(this.iconQuantity);
		i.setNameAndLore(this.name, this.iconLore);
		i.setCommands(Command.arrayFromString(this.commands));
		return i;
	}

}
