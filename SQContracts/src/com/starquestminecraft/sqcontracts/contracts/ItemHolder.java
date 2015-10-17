package com.starquestminecraft.sqcontracts.contracts;

import java.io.Serializable;

import org.bukkit.Material;

public class ItemHolder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Material type;
	int amount;
	short data;
	
	public ItemHolder(Material type, int amount, short data){
		this.type = type;
		this.amount = amount;
		this.data = data;
	}
	
	public Material getType(){
		return type;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public int getMaxStackSize(){
		return type.getMaxStackSize();
	}
	
	public short getData(){
		return data;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
}
