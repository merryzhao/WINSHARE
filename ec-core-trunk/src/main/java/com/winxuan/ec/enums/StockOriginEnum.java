package com.winxuan.ec.enums;

/**
 * 
 * @author YangJun
 * @version 1.0, 2014
 *
 */
public enum StockOriginEnum {
	DEFAULT((short) 0), STOCK_INIT((short) 1), RECEIVING((short) 2), ORDER_CREATED((short) 3), ORDER_CANCEL((short) 4), ORDER_DELIVERY(
			(short) 5), INCREMENT((short) 6);

	private short origin;

	private StockOriginEnum(short origin) {
		this.origin = origin;
	}

	public short getOrigin() {
		return origin;
	}

	@Override
	public String toString() {
		return String.valueOf(this.origin);
	}

}
