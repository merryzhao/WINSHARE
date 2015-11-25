package com.winxuan.ec.task.model.union;

import java.util.Date;

/**
 * 渠道评论
 * @author sunflower
 *
 */
public class ChannelComment {
	
	/**
	 * 渠道评论主键id
	 */
	private Long channelCommentId;
	
	/**
	 * 商品id
	 */
	private Long productSaleId;
	
	/**
	 * 渠道id
	 */
	private Long channel;
	
	/**
	 * 评分
	 */
	private int rank;
	
	/**
	 * 评论
	 */
	private String content;
	
	/**
	 * 别名
	 */
	private String nickName;
	
	/**
	 * 回复
	 */
	private String reply;
	
	/**
	 * 评论时间
	 */
	private Date commentTime;

	public Long getChannelCommentId() {
		return channelCommentId;
	}

	public void setChannelCommentId(Long channelCommentId) {
		this.channelCommentId = channelCommentId;
	}

	public Long getProductSaleId() {
		return productSaleId;
	}

	public void setProductSaleId(Long productSaleId) {
		this.productSaleId = productSaleId;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
}
