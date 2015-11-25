package com.winxuan.ec.task.dao.union;

import java.util.List;

import com.winxuan.ec.task.model.union.ChannelComment;

public interface MergeCommentDao {

	List<ChannelComment> getChannelComments(Long id, Long channel,
			int mergeSize);

}
