package org.suxen.druid.equipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.script.MethodProvider;
import org.suxen.druid.Slave;
import org.suxen.druid.Timing;
import org.suxen.druid.item.RequiredItem;
import org.suxen.druid.item.SuxenItem;

public class SuxenEquipment {

	public static List<SuxenItem> EQUIP_LIST = new ArrayList<SuxenItem>();

	private MethodProvider methodProvider;
	private List<SuxenItem> gear;

	public SuxenEquipment(MethodProvider methodProvider, SuxenItem[] gear) {
		this.methodProvider = methodProvider;
		this.gear = Arrays.asList(gear);
	}

	public boolean hasValidGear() {
		return getNeededItems().isEmpty();
	}

	public List<SuxenItem> getNeededItems() {
		List<SuxenItem> neededItems = new ArrayList<SuxenItem>();
		for (SuxenItem item : gear) {
			if (!methodProvider.getEquipment().isWearingItem(item.getSlot(), item.getName())) {
				neededItems.add(item);
			}
		}
		return neededItems;
	}

	public boolean isWearingItem(SuxenItem item) {
		return this.methodProvider.getEquipment().isWearingItem(item.getSlot(), item.getID());
	}

	public static boolean isWearingItem(MethodProvider methodProvider, SuxenItem item) {
		return methodProvider.getEquipment().isWearingItem(item.getSlot(), item.getID());
	}

	public static void addItemsToEquipmentList(List<SuxenItem> neededItems) {
		neededItems.forEach(item -> {
			if (!EQUIP_LIST.contains(item)) {
				EQUIP_LIST.add(item);
			}
		});

	}

	public static void equipItems(MethodProvider methodProvider) {
		// initially check bank if it is open
		if (methodProvider.bank.isOpen()) {
			methodProvider.bank.close();
			Timing.waitCondition(() -> !methodProvider.bank.isOpen(), 200, 4000);
		} else {
			EQUIP_LIST.forEach(item -> {
				if (isWearingItem(methodProvider, item)) {
					EQUIP_LIST.remove(item);
				} else if (methodProvider.inventory.contains(item.getID())) {
					methodProvider.getEquipment().equip(item.getSlot(), item.getID());
					Timing.waitCondition(() -> isWearingItem(methodProvider, item), 600, 2500);
					if (isWearingItem(methodProvider, item)) {
						EQUIP_LIST.remove(item);
					}
				} else {
					Slave.WITHDRAW_LIST.add(new RequiredItem(1, item));
				}
			});
		}
	}
}
