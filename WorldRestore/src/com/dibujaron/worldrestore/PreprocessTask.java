package com.dibujaron.worldrestore;

import org.bukkit.World;

public abstract class PreprocessTask {
	public abstract void process(World from, World to);
}
