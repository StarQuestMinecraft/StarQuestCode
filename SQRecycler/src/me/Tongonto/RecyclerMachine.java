package me.Tongonto;

import org.bukkit.Location;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;

public class RecyclerMachine {

	Dropper inputDropper = null;
	Furnace processorFurnace = null;
	Dropper outputDropper = null;
	Sign recyclerSign = null;
	Location signLocation = null;
	
	public RecyclerMachine(Dropper topDropper, Furnace recyclerFurnace, Dropper bottomDropper, Sign machineSign, Location recyclerSignLocation){
		inputDropper = topDropper;
		processorFurnace = recyclerFurnace;
		outputDropper = bottomDropper;
		recyclerSign = machineSign;
	}
	
	public Dropper getInputDropper(){
		return inputDropper;
	}
	
	public Furnace getRecyclerFurnace(){
		return processorFurnace;
	}
	
	public Dropper getOutputDropper(){
		return outputDropper;
	}
	
	public Sign getSign(){
		return recyclerSign;
	}
	
	public Location getRecyclerSignLocation(){
		return signLocation;
	}
	
}
