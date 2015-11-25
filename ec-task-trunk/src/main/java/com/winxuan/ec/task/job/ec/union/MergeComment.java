package com.winxuan.ec.task.job.ec.union;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.customer.CustomerComment;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleRank;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.customer.CustomerCommentService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.task.model.union.ChannelComment;
import com.winxuan.ec.task.service.union.MergeCommentService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.framework.util.hibernate.HibernateLazyResolver;

/**
 * 渠道评论融合
 * 
 * @author sunflower
 * 
 */
@Component("mergeComment")
public class MergeComment implements Serializable, TaskAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3135531187317877052L;
	private static final Log LOG = LogFactory.getLog(MergeComment.class);
	private static final int MAX_MERGE_SIZE = 100;

	@Autowired
	private HibernateLazyResolver hibernateLazyResolver;

	@Autowired
	private MergeCommentService mergeCommentService;

	@Autowired
	private ChannelService channelService;

	@Autowired
	private CustomerCommentService customerCommentService;

	@Autowired
	private ProductService productService;

	@Override
	public String getName() {
		return "mergeComment";
	}

	@Override
	public String getDescription() {
		return "渠道评论融合";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_FRONT;
	}

	@Override
	public void start() {
		hibernateLazyResolver.openSession();
		List<Channel> channels = getChannels();

		for (Channel channel : channels) {
			// 取得当前欲融合渠道的已经融合的最大id
			CustomerComment customerComment = customerCommentService
					.getMaxMergeByChannel(channel);
			Long maxId = customerComment.getChannelCommentId();
			LOG.info(String.format("Channel max comment id is %s ; Batch Size: %s", maxId,MAX_MERGE_SIZE));
			// 取得渠道评论信息
			List<ChannelComment> channelComments = mergeCommentService
					.getChannelComments(maxId,channel, MAX_MERGE_SIZE);
			transform(channelComments,channel);
		}
		hibernateLazyResolver.releaseSession();
	}

	private void transform(List<ChannelComment> channelComments,Channel channel) {
		if (channelComments == null || channelComments.size() == 0) {
			return;
		}
		for (ChannelComment channelComment : channelComments) {
			Long s = System.currentTimeMillis();
			ProductSale productSale = productService.getProductSale(channelComment.getProductSaleId());
			CustomerComment customerComment = new CustomerComment();
			customerComment.setChannel(channel);
			customerComment.setChannelCommentId(channelComment
					.getChannelCommentId());
			customerComment.setCommentTime(channelComment.getCommentTime());
			customerComment.setContent(channelComment.getContent());
			customerComment.setNickName(channelComment.getNickName());
			customerComment.setProductSale(productSale);

			ProductSaleRank productSaleRank = new ProductSaleRank();
			customerComment.setRank(productSaleRank);
			productSaleRank.setProductSale(productSale);
			mergeRank(productSaleRank,channelComment,channel);
			productSaleRank.setRankTime(channelComment.getCommentTime());
			customerComment.setRank(productSaleRank);
//			productSaleRank.setComment(customerComment);
			
			customerCommentService.save(customerComment);
			LOG.info(String.format("process 1 comment times: %s", System.currentTimeMillis() - s));
		}
	}

	/**
	 * 评分规则转化
	 * @param productSaleRank
	 * @param channelComment
	 */
	private void mergeRank(ProductSaleRank productSaleRank,
			ChannelComment channelComment,Channel channel) {
		if(channel.getId() == Channel.TAOBAO_WINSHARE){//淘宝评论
			if(channelComment.getRank() == ProductSaleRank.STAR_THREE){
				productSaleRank.setRank(ProductSaleRank.STAR_FIVE);
			}else if(channelComment.getRank() == ProductSaleRank.STAR_TWO){
				productSaleRank.setRank(ProductSaleRank.STAR_FOUR);
			}else{
				productSaleRank.setRank(ProductSaleRank.STAR_THREE);
			}
		}
	}

	private List<Channel> getChannels() {
		List<Channel> channels = new ArrayList<Channel>();
		channels.add(channelService.get(Channel.TAOBAO_WINSHARE));
		return channels;
	}

}
