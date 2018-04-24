package org.suxen.druid;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.util.ItemContainer;
import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;
import org.suxen.druid.bank.SuxenBank;
import org.suxen.druid.equipment.SuxenEquipment;
import org.suxen.druid.inventory.SuxenInventory;
import org.suxen.druid.item.RequiredItem;
import org.suxen.druid.item.SuxenItem;
import org.suxen.druid.node.Node;


@ScriptManifest(name = "Druid", author = "Suxen", version = 1.0, info = "", logo = "") 

public class Slave extends Script {
	

	public static List<Node> NODE_HANDLER;
	public static List<RequiredItem> WITHDRAW_LIST;
	public static long CURRENT_SLEEP;
	public static boolean WEBWALKING;
	SuxenEquipment requiredEquipment;
	SuxenInventory requiredInventory;
	
    @Override
    public void onStart() {
    	requiredEquipment = new SuxenEquipment(this, new SuxenItem[] {SuxenItem.AMULET_OF_STRENGTH});
    	//exception for when the player is in fight area and health is not below CRITICAL_HEALTH
    	requiredInventory = new SuxenInventory(this, new RequiredItem[] {new RequiredItem(3, SuxenItem.AMULET_OF_STRENGTH, () -> SuxenBank.inBank(this))});
    	WITHDRAW_LIST = new ArrayList<RequiredItem>();
    }

    

    @Override

    public void onExit() {
        //Code here will execute after the script ends
    }

    @Override

    public int onLoop() {
    	//since webwalking is its own event, we can set webwalking to false as soon as the loop is ran again
    	WEBWALKING = false;
    	
    	if(!WITHDRAW_LIST.isEmpty()){
    		log("lets withdraw needed items");
    		//if inventory right contains right amount the item
    		//remove from list
    		WITHDRAW_LIST.forEach(item -> {
    			if(inventory.getAmount(item.getSuxenItem().getID()) == item.getAmount()){
    				WITHDRAW_LIST.remove(item);
    			}
    		});
    		if(SuxenBank.inBank(this)){
    			log("we are in bank, lets bank");
    			SuxenBank.withdraw(this, WITHDRAW_LIST);
    		}else{
    			SuxenBank.startBankWalk(this);
    		}
    	}else if(!SuxenEquipment.EQUIP_LIST.isEmpty()){
    		SuxenEquipment.equipItems(this);
    	}else if(!requiredEquipment.hasValidGear()){
    		log("lets add item to equip list");
    		SuxenEquipment.addItemsToEquipmentList(requiredEquipment.getNeededItems());
    	}else if(!requiredInventory.hasValidInventory()){
    		log("lets add item to withdraw list");
    		SuxenInventory.addItemsToWithdrawList(requiredInventory.getNeededItems());
    	}else{
    		log("valid gear and inventory");
    	}
    	
    	log("looping");
        return 200; //The amount of time in milliseconds before the loop starts over
    }
    
    public boolean hasRequiredGear(){
    	//should be replaced with something similar to forEach - isWearing - Item(Slot, Item) 
    	if(	getEquipment().isWearingItemThatContains(EquipmentSlot.AMULET, "Amulet of Strength") 	&&
    		getEquipment().isWearingItemThatContains(EquipmentSlot.CHEST, "Monk's robe top")	 	&&
    		getEquipment().isWearingItemThatContains(EquipmentSlot.LEGS, "Monk's robe")	 			&&
			getEquipment().isWearingItemThatContains(EquipmentSlot.FEET, "Leather boots")	 		&&	
			getEquipment().isWearingItemThatContains(EquipmentSlot.WEAPON, "Rune scimitar"))	 
    	{
    		return true;
    	}
    	return false;
    }
    
    

    @Override

    public void onPaint(Graphics2D g) {

        g.drawString("Equip List: " + SuxenEquipment.EQUIP_LIST, 50, 50);
        g.drawString("Withdraw List: " + Slave.WITHDRAW_LIST, 50, 75);

        if(CURRENT_SLEEP > System.currentTimeMillis()){
        	g.drawString("sleeping for: " + (CURRENT_SLEEP - System.currentTimeMillis()) , 50,100);
        }
        if(WEBWALKING){
        	g.drawString("Currently webwalking", 50,100);
        }

    }
    


}