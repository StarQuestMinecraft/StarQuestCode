
package us.higashiyama.george.SQTurrets.Utils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import us.higashiyama.george.SQTurrets.SQTurrets;
import us.higashiyama.george.SQTurrets.Turret;

public class TurretManager {

	public static Map<String, Class<? extends Turret>> turrets;

	public static void loadTurrets() {

		File dir = new File(SQTurrets.getInstance().getDataFolder(), "turrets");
		if (turrets == null)
			turrets = new HashMap<String, Class<? extends Turret>>();
		if (!dir.exists())
			dir.mkdir();

		int i = 0;
		while (i < 50) {
			i++;
			System.out.println(" ");
		}
		System.out.println("Starting turret loading");
		loadCustomTurrets(dir);
	}

	public static void loadCustomTurrets(File classDir) {

		if (turrets == null)
			turrets = new HashMap<String, Class<? extends Turret>>();

		// Load all the custom abilities.
		System.out.println("Calling load method");
		loadClasses(classDir);
	}

	private static void register(Class<? extends Turret> cls) {

		System.out.println(cls.getName());
		String name = cls.getName();
		if (name.contains("$"))
			return;
		System.out.println(name + " redistered! ");
		turrets.put(name, cls);

	}

	private static void loadClasses(File classDir) {

		// Grab the class loader
		ClassLoader loader = getLoader(classDir);
		System.out.println(loader.toString());
		if (loader == null) {
			return;
		}

		for (File file : classDir.listFiles()) {
			String filename = file.getName();
			System.out.println(filename);

			// Only load .class files.
			int dot = filename.lastIndexOf(".class");
			if (dot < 0)
				continue;

			// Trim off the .class extension
			String name = filename.substring(0, file.getName().lastIndexOf("."));

			try {
				// Load the class
				Class<?> cls = loader.loadClass(name);
				System.out.println(cls.getName());

				// Verify that it's an Ability, then register it
				if (Turret.class.isAssignableFrom(cls)) {
					System.out.println("Registering");
					register(cls.asSubclass(Turret.class));
				}
			} catch (Exception e) {
			}
		}
	}

	private static ClassLoader getLoader(File dir) {

		try {
			ClassLoader loader = new URLClassLoader(new URL[] { dir.toURI().toURL() }, Turret.class.getClassLoader());
			return loader;
		} catch (Exception e) {
		}

		return null;
	}

}
