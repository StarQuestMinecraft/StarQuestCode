package com.starquestminecraft.sqcontracts.randomizer.function;

import java.util.List;
import java.util.Random;

public class Weighable {

	String name;
	double weight;
	
	public Weighable(String name, double weight){
		this.name = name;
		this.weight = weight;
	}
	
	public String getName(){
		return name;
	}
	
	public double getWeight(){
		return weight;
	}
	
	public static Weighable weightedRandom(List<Weighable> list, Random generator){

		// Compute the total weight of all items together
		double totalWeight = 0.0d;
		for (Weighable w : list){
		    totalWeight += w.getWeight();
		}
		// Now choose a random item
		int randomIndex = -1;
		double random = Math.random() * totalWeight;
		for (int i = 0; i < list.size(); ++i)
		{
		    random -= list.get(i).getWeight();
		    if (random <= 0.0d)
		    {
		        randomIndex = i;
		        break;
		    }
		}
		return list.get(randomIndex);
	}
}
