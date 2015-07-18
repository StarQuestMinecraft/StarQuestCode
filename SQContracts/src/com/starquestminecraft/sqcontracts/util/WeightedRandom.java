package com.starquestminecraft.sqcontracts.util;

import java.util.List;
import java.util.Random;

public class WeightedRandom<T> {
	
	List<Weighable<T>> list;
	Random generator;
	
	public WeightedRandom(List<Weighable<T>> list, Random generator){
		this.list = list;
		this.generator = generator;
	}
	
	public Weighable<T> generate(){
		double totalWeight = 0.0d;
		for (Weighable<T> w : list){
		    totalWeight += w.getWeight();
		}
		// Now choose a random item
		int randomIndex = -1;
		double random = generator.nextDouble() * totalWeight;
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
