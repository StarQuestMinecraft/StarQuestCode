package com.ginger_walnut.sqpowertools.objects;

public class Attribute {

	public String attribute;
	public float amount;
	public String slot;
	public int operation;
	public int uuid;
	
	public Attribute() {
		
	}
	
	public boolean equalsExceptAmount(Attribute attribute) {
		
		if (attribute.attribute.equals(attribute) && attribute.slot.equals(slot) && attribute.operation == operation && attribute.uuid == uuid) {
			
			return true;
			
		}
		
		return false;
		
	}
	
}
