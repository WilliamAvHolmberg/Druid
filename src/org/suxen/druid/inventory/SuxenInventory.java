package org.suxen.druid.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

import org.osbot.rs07.script.MethodProvider;
import org.suxen.druid.Slave;
import org.suxen.druid.item.RequiredItem;
import org.suxen.druid.item.SuxenItem;

public class SuxenInventory {
	public static List<RequiredItem> INVENTORY_LIST = new ArrayList<RequiredItem>();

	private MethodProvider methodProvider;
	private List<RequiredItem> items;

	public SuxenInventory(MethodProvider methodProvider, RequiredItem[] items) {
		this.methodProvider = methodProvider;
		this.items = Arrays.asList(items);
	}
	public boolean hasValidInventory() {
		//if player in fight area && health is not below Critical
		//return true
		return getNeededItems().isEmpty();
	}

	public List<RequiredItem> getNeededItems() {
		List<RequiredItem> neededItems = new ArrayList<RequiredItem>();
		for (RequiredItem item : items) {
			if (!(methodProvider.getInventory().getAmount(item.getSuxenItem().getID()) == item.getAmount()) && !item.getException().getAsBoolean()) {
				//Remove the amount that inventory already contain (if contain)
				neededItems.add(new RequiredItem(item.getAmount() - methodProvider.getInventory().getAmount(item.getSuxenItem().getID()), item.getSuxenItem()));
			}
		}
		return neededItems;
	}

	public static void addItemsToWithdrawList(List<RequiredItem> neededItems) {
		neededItems.forEach(item -> {
			if (!Slave.WITHDRAW_LIST.contains(item)) {
				Slave.WITHDRAW_LIST.add(item);
			}
		});

	}
}
