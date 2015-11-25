package com.winxuan.ec.task.job.feed.agent;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.feed.constants.TemplateConstants;
import com.winxuan.ec.feed.service.agent.AgentService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 
 * @author cast911
 *
 */
@Component("createInventoryIndex")
public class CreateInventoryIndex implements TaskAware,Serializable {

	
	@Autowired
	private AgentService agentService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4767940905204073552L;

	@Override
	public String getName() {
		return "createInventoryIndex";
	}

	@Override
	public String getDescription() {
		return "提供给代理商上架商品的库存信息";
	}

	@Override
	public String getGroup() {
		return TaskAware.GROUP_DEFAULT;
	}

	@Override
	public void start() {
		agentService.createDocument(TemplateConstants.MAIN_DIRECTORY);
		
	}

}
