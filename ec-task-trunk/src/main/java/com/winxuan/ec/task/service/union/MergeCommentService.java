package com.winxuan.ec.task.service.union;

import java.util.List;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.task.model.union.ChannelComment;

/**
 * 渠道评论融合
 * @author sunflower
 *
 */
public interface MergeCommentService {

	/**
	 * 获取待下发的评论数据<br/>不包括channelCommentId
	 * @param channelCommentId
	 * @param channel
	 * @param mergeSize 
	 * @return
	 */
	List<ChannelComment> getChannelComments(Long channelCommentId,
			Channel channel, int mergeSize);

}
