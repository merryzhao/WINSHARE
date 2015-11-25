package com.winxuan.ec.task.dao.union;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.model.union.ChannelComment;

@Component("mergeCommentDao")
public class MergeCommentDaoImpl implements MergeCommentDao,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5387994566328186650L;
	private static final String query_sql = "select * from product_comment where id>? and channel = ? limit ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplateChannel;

	@Override
	public List<ChannelComment> getChannelComments(Long id, Long channel,
			int mergeSize) {
		return jdbcTemplateChannel.query(query_sql,
				new Object[] { id, channel, mergeSize },
				new RowMapper<ChannelComment>() {
					@Override
					public ChannelComment mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						ChannelComment channelComment = new ChannelComment();
						channelComment.setChannelCommentId(rs.getLong("id"));
						channelComment.setChannel(rs.getLong("channel"));
						channelComment.setCommentTime(rs.getDate("createtime"));
						channelComment.setContent(rs.getString("content"));
						channelComment.setNickName(rs.getString("nick"));
						channelComment.setProductSaleId(rs.getLong("productsaleid"));
						channelComment.setRank(rs.getInt("rank"));
						channelComment.setReply(rs.getString("reply"));
						return channelComment;
					}
				});
	}

}
