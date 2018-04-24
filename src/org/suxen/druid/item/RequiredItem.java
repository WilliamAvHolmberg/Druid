package org.suxen.druid.item;

import java.util.function.BooleanSupplier;

public class RequiredItem {
	
	private int amount;
	private SuxenItem item;
	private BooleanSupplier exception;

	
	public RequiredItem(long amount, SuxenItem item) {
		this.amount = (int) amount;
		this.item = item;
		this.exception = () -> false;
	}
	
	/**
	 * @param exception if there is any scenario when the item is not required
	 */
	public RequiredItem(int amount, SuxenItem item, BooleanSupplier exception){
		this.amount = amount;
		this.item = item;
		this.exception = exception;
	}
	

	public int getAmount(){
		return amount;
	}
	
	public SuxenItem getSuxenItem(){
		return item;
	}
	
	public BooleanSupplier getException(){
		return exception;
	}
	
	public String toString(){
		return amount + ":" + item.getName();
	}

}
