package com.starquestminecraft.sqcontracts.util;

public class Weighable<T> {

	T obj;
	double weight;
	
	public Weighable(T obj, double weight){
		this.obj = obj;
		this.weight = weight;
	}
	
	public T getObject(){
		return obj;
	}
	
	public double getWeight(){
		return weight;
	}
}
