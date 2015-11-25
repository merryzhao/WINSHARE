/*
 * @(#)IP.java
 *
 */

package com.winxuan.ec.front.model.ip;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.support.util.MagicNumber;

/**
 * description
 * @author  huangyixiang
 * @version 2013-1-5
 */
public class IP implements Comparable<IP>{
	
	private int segment1;
	private int segment2;
	private int segment3;
	private int segment4;
	
	public IP(String ipString){
		if(StringUtils.isBlank(ipString)){
			throw new RuntimeException("ip地址为空");
		}
		if(!ipString.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
			throw new RuntimeException("错误的ip地址：" + ipString);
		}
		String[] ips = ipString.split("\\.");
		setSegment1(Integer.parseInt(ips[MagicNumber.ZERO]));
		setSegment2(Integer.parseInt(ips[MagicNumber.ONE]));
		setSegment3(Integer.parseInt(ips[MagicNumber.TWO]));
		setSegment4(Integer.parseInt(ips[MagicNumber.THREE]));
		
	}
	
	public boolean beIncluding(IP startIP, IP endIP){
		return this.compareTo(startIP) >= 0 
				&& this.compareTo(endIP) <= 0; 
	}
	
	
	
	public int getSegment1() {
		return segment1;
	}
	public void setSegment1(int segment1) {
		this.segment1 = segment1;
	}
	public int getSegment2() {
		return segment2;
	}
	public void setSegment2(int segment2) {
		this.segment2 = segment2;
	}
	public int getSegment3() {
		return segment3;
	}
	public void setSegment3(int segment3) {
		this.segment3 = segment3;
	}
	public int getSegment4() {
		return segment4;
	}
	public void setSegment4(int segment4) {
		this.segment4 = segment4;
	}

	@Override
	public String toString() {
		return "IP [segment1=" + segment1 + ", segment2=" + segment2
				+ ", segment3=" + segment3 + ", segment4=" + segment4 + "]";
	}

	@Override
	public int compareTo(IP ip) {
		if(this.segment1 != ip.segment1){
			return this.segment1 > ip.segment1 ? 1 : -1;
		}
		if(this.segment2 != ip.segment2){
			return this.segment2 > ip.segment2 ? 1 : -1;
		}
		if(this.segment3 != ip.segment3){
			return this.segment3 > ip.segment3 ? 1 : -1;
		}
		if(this.segment4 != ip.segment4){
			return this.segment4 > ip.segment4 ? 1 : -1;
		}
		return 0;
	}
}
