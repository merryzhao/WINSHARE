package com.winxuan.ec.task.job.ec.topic;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.exception.ec.FileProcessException;
import com.winxuan.ec.task.model.ec.TopicFragment;
import com.winxuan.ec.task.service.ec.TopicService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

@Component("topicDecompressJob")
public class TopicDecompressJob implements TaskAware, Serializable {
	private static Log LOG = LogFactory.getLog(TopicDecompressJob.class);
	@Value("${ec.topic.src}")
	private String source;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3335527104527104675L;
	@Autowired
	TopicService topicService;
	
	@Override
	public String getName() {
		return "topicDecompressJob";
	}

	@Override
	public String getDescription() {
		return "将上传的FTP 主题.zip 包部署到WebApp并在数据库添加记录";
	}

	@Override
	public String getGroup() {
		
		return TaskAware.GROUP_EC_FRONT;
	}
	 
	@Override
	public void start() {
		
		
		File srcDir = new File(source);
		if (!srcDir.isDirectory()) {
			throw new IllegalArgumentException(String.format("src path not is directory : %s ! ", source));
		} 
		
		
		File[] files = srcDir.listFiles();
		
		List<TopicFragment> list = new ArrayList<TopicFragment>();//TODO : 删除
		File bak = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String suffix = format.format(new Date());
		File error = null;
		for (File file : files){
			if (file.getName().matches(".*.zip")) {
				try {
					if(null == bak){
						bak = new File(file.getParent() + File.separator + "bak" + File.separator);
						bak.mkdir();
						error = new File(file.getParent() + File.separator + "error" + File.separator);
						error.mkdir();
					}
					LOG.info(String.format("processing file == %s", file.getAbsolutePath()));
					TopicFragment fragmentPojo = topicService.process(file);
					if (fragmentPojo != null ){
						list.add(fragmentPojo);
					}
					File newFile = new File(bak.getAbsolutePath() + File.separator + file.getName() + "." + suffix);
					file.renameTo(newFile);
			 		
				} catch (IOException e) {
					
					File errorFile = new File(error.getAbsolutePath() + File.separator + file.getName() + "." + suffix);
					file.renameTo(errorFile);
					
					LOG.error(String.format("This file parse error ! %s", file.getAbsolutePath()));
					LOG.error(e.getMessage(), e);
					continue;
				} catch (FileProcessException e) {
					File errorFile = new File(error.getAbsolutePath() + File.separator + file.getName() + "." + suffix);
					file.renameTo(errorFile);
					LOG.error(e.getMessage(), e);
					continue;
				}
			}
		}
		

	}

}
