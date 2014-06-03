
package us.higashiyama.george.RankupUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.gmail.filoghost.chestcommands.components.IconMenu;

public class Menu {

	public String name;
	public List<Perk> perks;
	public int size;

	public Menu(String name, int size) {

		this.name = name;
		this.perks = loadPerks(this.name);
		this.size = size;
	}

	public IconMenu build() {

		IconMenu menu = new IconMenu(this.name, this.name, this.size);
		for (Perk p : perks) {
			menu.setIcon(p.build(), p.x, p.y);
		}

		return menu;
	}

	public static List<Perk> loadPerks(String menuName) {

		// Load all the file names in the dir
		List<String> results = new ArrayList<String>();
		File[] files = new File("C:/StarQuest/GlobalConfigs/Rankup/Perks").listFiles();

		for (File file : files) {
			if (file.isFile()) {
				// Removing the file extension to just get the name
				System.out.println(file.getName());
				results.add(file.getName().replace(".yml", ""));
			}
		}

		List<Perk> loadedPerks = new ArrayList<Perk>();
		for (String fileName : results) {
			Perk p = new Perk(fileName);
			if (p.menuName.equalsIgnoreCase(menuName)) {
				loadedPerks.add(p);
			}
		}

		return loadedPerks;

	}
}
