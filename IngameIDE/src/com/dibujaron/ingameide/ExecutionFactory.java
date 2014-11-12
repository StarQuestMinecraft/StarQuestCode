package com.dibujaron.ingameide;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ExecutionFactory {
	
	private static String[] before = {
			"import org.bukkit.*;",
			"import org.bukkit.block.*;",
			"import org.bukkit.enchantments.*;",
			"import org.bukkit.entity.*;",
			"import org.bukkit.entity.minecart.*;",
			"import org.bukkit.inventory.*;",
			"import org.bukkit.inventory.meta.*;",
			"import org.bukkit.material.*;",
			"import org.bukkit.potion.*;",
			"import org.bukkit.projectiles.*;",
			"import org.bukkit.scheduler.*;",
			"",
			"public class IngameIDEExecutable implements Runnable{",
			"",
			"public void run(){" };
			
	private static String[] after = {
			"}",
			"}"
	};
	
	public static void execute(String[] lines){
		String[] combination = combine(before, lines, after);
		File f = writeFile(combination);
		compile(f.getAbsolutePath());
		String newPath = javaFilePathToClassFilePath(f.getAbsolutePath());
		Runnable loaded = load(newPath);
		loaded.run();
	}
	
	private static String javaFilePathToClassFilePath(String javaPath){
		String shortString = javaPath.substring(0, javaPath.length() - 4);
		return shortString + "class";
	}
	
	private static Runnable load(String path){
		try {
			Class<?> cl = IngameIDE.getInstance().getClass().getClassLoader().loadClass(path);
			Runnable c = (Runnable) cl.newInstance();
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static void compile(String path){
		final Runtime rt = Runtime.getRuntime();
		System.out.println(path);
		try {
			rt.exec("C:\\Program Files (x86)\\Java\\jdk1.7.0_45\\bin\\javac.exe " + path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static File writeFile(String[] data){
		try{
			File f = new File(IngameIDE.getInstance().getDataFolder() + "/IngameIDEExecutable.java");
			f.getParentFile().mkdirs();
			PrintWriter out = new PrintWriter(f);
			for(String s : data){
				out.write(s + "\n");
			}
			out.close();
			return f;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private static String[] combine(String[] ar1, String[] ar2, String[] ar3){
		String[] combined = new String[ar1.length + ar2.length + ar3.length];
		int index = 0;
		for(int i = 0; i < ar1.length; i++){
			combined[index] = ar1[i];
			index++;
		}
		for(int i = 0; i < ar2.length; i++){
			combined[index] = ar2[i];
			index++;
		}
		for(int i = 0; i < ar3.length; i++){
			combined[index] = ar3[i];
			index++;
		}
		
		return combined;
	}
}
