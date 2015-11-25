package com.winxuan.ec.task.service.ec;

import java.io.File;
import java.io.IOException;

import com.winxuan.ec.task.exception.ec.FileProcessException;
import com.winxuan.ec.task.model.ec.TopicFragment;

/**
 * 主题解压部署
 * @author Heyadong
 * @date 2011-11-30
 */
public interface TopicService {
	
	
	TopicFragment process(File zip) throws IOException, FileProcessException;
	
	void save(TopicFragment topicFragment);
}
