package com.winxuan.ec.model.code;


/*******************************************
 * @ClassName: CodeLogistics
 * @Description: 订单包裹信息流转
 * @author:cast911
 * @date:2014年9月11日 下午2:13:41
 *********************************************/
public class CodeOrderLogistics {

	/**
	 * 包裹流转
	 */
	public static final Long PACKGE_CIRCULATION = 600000L;

	/**
	 * 收货
	 */
	public static final Long TAKE_CARGO = 600001L;

	/**
	 * 打包
	 */
	public static final Long PACKAGING = 600002L;

	/**
	 * 中转
	 */
	public static final Long TRANSSHIPMENT = 600003L;

	/**
	 * 货物在途
	 */
	public static final Long ON_THE_ROAD = 600004L;

	/**
	 * 正在派件
	 */
	public static final Long DISPATCH_PACKGE = 600005L;

	/**
	 * 收货
	 */
	public static final Long TAKE_DELIVERY = 600006L;

	/**
	 * 自提
	 */
	public static final Long SINCE_THE_QUESTION = 600007L;
	/**
	 * 处理物流运单状态
	 */
	public static final Long ORDER_WAYBILL_STATUS = 600100L;
	/**
	 * 未抓取
	 */
	public static final Long ORDER_WAYNILL_STATUS_WAIT = 600101L;
	/**
	 * 已效验
	 */
	public static final Long ORDER_WAYNILL_STATUS_CHECK = 600102L;
	/**
	 * 废弃
	 */
	public static final Long ORDER_WAYNILL_STATU_DISCARD = 	600103L;
	/**
	 * 自定义状态
	 */
	public static final Long ORDER_WAYNILL_STATUS_CUSTOM = 600104L;
	
	

}
