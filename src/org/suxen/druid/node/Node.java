package org.suxen.druid.node;

import org.osbot.rs07.script.MethodProvider;

public abstract class Node {
	
	private MethodProvider methodProvider;

	public Node init(MethodProvider methodProvider){
		this.methodProvider = methodProvider;
		return this;
	}
	
	public abstract boolean active();
	public abstract void execute();

}
