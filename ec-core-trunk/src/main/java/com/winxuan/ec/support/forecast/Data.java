/*
 * @(#)Data.java
 *
 * Copyright 2013 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.support.forecast;



/**
 * description
 * @author  qiaoyao
 * @version 1.0,2013年8月19日
 */
public class Data {
	
	private double cycle;
	private double value;
	
	public Data(){
		
	}
	
	public Data(double cycle, double value){
		this.cycle = cycle;
		this.value = value;
	}
	
 

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getCycle() {
		return cycle;
	}

	public void setCycle(double cycle) {
		this.cycle = cycle;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getCycle() + ":" + this.getValue();
	}
	

}
