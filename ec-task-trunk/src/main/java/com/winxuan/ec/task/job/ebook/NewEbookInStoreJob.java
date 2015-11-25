package com.winxuan.ec.task.job.ebook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.ebook.EbookUploadService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 九月网电子书入库
 * @author luosh
 *
 */
@Component("newEbookInStoreJob")
public class NewEbookInStoreJob implements TaskAware {
	private static final Log LOG = LogFactory.getLog(NewEbookInStoreJob.class);
	@Autowired
	private EbookUploadService ebookUploadService;

	@Override
	public String getName() {
		return "newEbookInStoreJob";
	}

	@Override
	public String getDescription() {
		return "九月网 电子书新书入库";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		LOG.info("start uploadEbook()............");
		ebookUploadService.uploadEbook();
		LOG.info("end uploadEbook...........");
	}

}
