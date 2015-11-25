package com.winxuan.ec.task.job.ebook;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.winxuan.ec.task.service.ebook.WinXuanEbookService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;


/**
 * 将9月新商品匹配添加到文轩相关数据库JOB
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
@Component("ebookTaskJob")
public class EbookTaskJob implements TaskAware, Serializable{
	private static final Log LOG = LogFactory.getLog(EbookTaskJob.class);
	private static final long serialVersionUID = 4891846672066104835L;
	@Autowired
	private WinXuanEbookService winXuanEbookService;
	@Override
	public String getName() {
		return "ebookTaskJob";
	}

	@Override
	public String getDescription() {
		return "将9月新加入的商品添加到文轩相关数据库表中。";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_EC_CORE;
	}

	@Override
	public void start() {
		LOG.info("start ebookTaskJob()............");
		winXuanEbookService.getBookChangeList();
		LOG.info("end ebookTaskJob()............");
	}
}
