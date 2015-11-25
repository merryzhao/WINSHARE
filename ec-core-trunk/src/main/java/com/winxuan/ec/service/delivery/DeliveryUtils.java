package com.winxuan.ec.service.delivery;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 快递公司工具类..
 * 代码写到这是因为. task工程.service用到的东西需要序列化.
 * XStream 没有实现序列化
 * @author Heyadong
 * @version 1.0 , 2012-1-13
 */
public class DeliveryUtils {
	private static XStream shentong  = new XStream();
	private static XStream yuantong = new XStream(new DomDriver());
	
	static {
		shentong.alias("root", com.winxuan.ec.service.delivery.ShenTongAPI.Root.class);
		shentong.alias("track", com.winxuan.ec.service.delivery.ShenTongAPI.Track.class);
		shentong.alias("detail", com.winxuan.ec.service.delivery.ShenTongAPI.Detail.class);
		shentong.addImplicitCollection(com.winxuan.ec.service.delivery.ShenTongAPI.Track.class, "details");
		
		
		yuantong.processAnnotations(com.winxuan.ec.service.delivery.YuanTongAPI.BatchQueryResponse.class);
		yuantong.processAnnotations(com.winxuan.ec.service.delivery.YuanTongAPI.OrderContent.class);
		yuantong.processAnnotations(com.winxuan.ec.service.delivery.YuanTongAPI.OrderStep.class);
	}
	public static XStream getShentong(){
		return shentong;
	}
	public static XStream getYuantong(){
		return yuantong;
	}
}
