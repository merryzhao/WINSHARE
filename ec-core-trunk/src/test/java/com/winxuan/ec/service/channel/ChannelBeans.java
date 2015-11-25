package com.winxuan.ec.service.channel;

import com.winxuan.ec.model.channel.Channel;

/**
 * 销售通道工具
 * @author Heyadong
 *
 */
public class ChannelBeans {
	/**
	 * 直销用户 104L
	 * @return
	 */
	public static Channel createChannelEC(){
		return new Channel(Channel.CHANNEL_EC);
	}
	
	/**
	 * 联盟 117L
	 * @return
	 */
	public static Channel createChannelUnion(){
		return new Channel(Channel.CHANNEL_UNION);
	}
	
	/**
	 * 代理 6L
	 * @return
	 */
	public static Channel createChannelAgent(){
		return new Channel(Channel.CHANNEL_AGENT);
	}
	
	
}
