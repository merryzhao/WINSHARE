package com.winxuan.ec.task.service.union.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.task.dao.union.MergeCommentDao;
import com.winxuan.ec.task.model.union.ChannelComment;
import com.winxuan.ec.task.service.union.MergeCommentService;

@Service("mergeCommentService")
public class MergeCommentServiceImpl implements MergeCommentService,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7809373331826383693L;
	@Autowired
	private MergeCommentDao mergeCommentDao;

	@Override
	public List<ChannelComment> getChannelComments(Long channelCommentId,
			Channel channel, int mergeSize) {

		return mergeCommentDao.getChannelComments(channelCommentId,
				channel.getId(), mergeSize);
	}

}
