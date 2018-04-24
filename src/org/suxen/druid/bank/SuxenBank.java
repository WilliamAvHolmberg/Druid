package org.suxen.druid.bank;

import java.util.List;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.event.webwalk.PathPreferenceProfile;
import org.osbot.rs07.script.MethodProvider;
import org.suxen.druid.Slave;
import org.suxen.druid.Timing;
import org.suxen.druid.item.RequiredItem;
import org.suxen.druid.item.SuxenItem;

public class SuxenBank {
	 public static Area[] BANKS = {
	
	            Banks.AL_KHARID,
	            Banks.ARCEUUS_HOUSE,
	            Banks.ARDOUGNE_NORTH,
	            Banks.ARDOUGNE_SOUTH,
	            Banks.CAMELOT,
	            Banks.CANIFIS,
	            Banks.CASTLE_WARS,
	            Banks.CATHERBY,
	            Banks.DRAYNOR,
	            Banks.DUEL_ARENA,
	            Banks.EDGEVILLE,
	            Banks.FALADOR_EAST,
	            Banks.FALADOR_WEST,
	            Banks.GNOME_STRONGHOLD,
	            Banks.GRAND_EXCHANGE,
	            Banks.HOSIDIUS_HOUSE,
	            Banks.LOVAKENGJ_HOUSE,
	            Banks.LOVAKITE_MINE,
	            Banks.LUMBRIDGE_LOWER,
	            Banks.LUMBRIDGE_UPPER,
	            Banks.PEST_CONTROL,
	            Banks.PISCARILIUS_HOUSE,
	            Banks.SHAYZIEN_HOUSE,
	            Banks.TZHAAR,
	            Banks.VARROCK_EAST,
	            Banks.VARROCK_WEST,
	            Banks.YANILLE,
	            new Area(2534, 3576, 2537, 3572), //Barbarian Assault Bank
	            new Area(3496, 3213, 3499, 3210), //Burgh de Rott Bank
	            new Area(2933, 3284, 2936, 3281), //Crafting Guild Bank
	            new Area(2618, 3896, 2620, 3893), //Etceteria Bank
	            new Area(2661, 3162, 2665, 3160), //Fishing Trawler Bank
	            new Area(2584, 3422, 2588, 3418), //Fishing Guild Bank
	            new Area(2440, 3489, 2442, 3487).setPlane(1), //Grand Tree West
	            new Area(2448, 3482, 2450, 3479).setPlane(1), //Grand Tree South
	            new Area(2415, 3803, 2418, 3801), //Jatiszo Bank
	            new Area(1610, 3683, 1613, 3680).setPlane(2), //Kingdom of Great Kourend Bank
	            new Area(2350, 3163, 2354, 3162), //Lletya Bank
	            new Area(2097, 3919, 2102, 3917), //Lunar Isle Bank
	            new Area(1508, 3423, 1511, 3419), //Lands End Bank
	            new Area(3424, 2892, 3430, 2889), //Nardah Bank
	            new Area(2335, 3808, 2337, 3805), //Neitiznot Bank
	            new Area(3686, 3471, 3691, 3463), //Port Phasmatys Bank
	            new Area(2327, 3690, 2332, 3687), //Piscatoris Bank
	            new Area(2849, 2955, 2855, 2953), //Shilo Village Bank
	            new Area(1717, 3466, 1722, 3463), //Sandcrabs Bank
	            new Area(3305, 3123, 3308, 3119), //Shantay Pass Bank
	            new Area(1453, 3859, 1458, 3856), //Sulphur Mine
	            new Area(3120, 3124, 3123, 3120), //Tutorial Island Bank
	            new Area(2444, 3427, 2446, 3422).setPlane(1), //Tree Gnome Stronghold Bank
	            new Area(1802, 3571, 1808, 3571), //Vinery Bank
	            new Area(2843, 3544, 2846, 3539), //Warriors Guild Bank
	            new Area(1589, 3480, 1593, 3476), //Woodcutting Guild Bank
	            new Area(1653, 3613, 1658, 3607), //Zeah Cooking Bank
	    };
	 
	 public static boolean inBank(MethodProvider methodProvider){
		 for(Area bank : BANKS){
			 if(bank.contains(methodProvider.myPlayer())){
				 return true;
			 }
		 }
		return false;
	 }
	 
	 public static void withdraw(MethodProvider methodProvider, RequiredItem item){
		 if(methodProvider.bank.isOpen()){
			 //withdraw the item
			 methodProvider.bank.withdraw(item.getSuxenItem().getID(), item.getAmount());
			 //sleep untill the item is withdrawn
			 Timing.waitCondition(() -> methodProvider.inventory.contains(item.getAmount(), item.getSuxenItem().getID()), 200, 4000);
			 //remove from withdraw_list if successful
			 if(methodProvider.inventory.contains(item.getSuxenItem().getID())){
				 Slave.WITHDRAW_LIST.remove(item);
			 }
		 }else{
			 try {
				methodProvider.bank.open();
				Timing.waitCondition(() -> methodProvider.bank.isOpen(), 200, 4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }

	public static void withdraw(Slave methodProvider, List<RequiredItem> items) {
		items.forEach(item -> withdraw(methodProvider,item));
	}
	
	public static void startBankWalk(MethodProvider methodProvider) {
		Slave.WEBWALKING = true;
		WebWalkEvent webEvent = new WebWalkEvent(BANKS);
		PathPreferenceProfile ppp = new PathPreferenceProfile();
		ppp.setAllowObstacles(true);
		webEvent.setPathPreferenceProfile(ppp);
		methodProvider.execute(webEvent);
		return;
	}
}

