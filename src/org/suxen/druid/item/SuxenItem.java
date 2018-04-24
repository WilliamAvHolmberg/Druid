package org.suxen.druid.item;

import org.osbot.rs07.api.ui.EquipmentSlot;

public enum SuxenItem {
	
	AMULET_OF_STRENGTH("Amulet of Strength", 1725, EquipmentSlot.AMULET);
	
	private String name;
	private int id;
	private EquipmentSlot slot;
	
	SuxenItem(String name, int id, EquipmentSlot slot){
		this.name = name;
		this.id = id;
		this.slot = slot;
	}
	
	public String getName(){
		return name;
	}
	public int getID(){
		return id;
	}
	public EquipmentSlot getSlot() {
		return slot;
	}

}
