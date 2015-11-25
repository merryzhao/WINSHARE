/*
 * @(#)NotifyServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.service.task.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.winxuan.ec.service.mail.MailService;
import com.winxuan.ec.task.service.task.NotifyService;
import com.winxuan.ec.task.support.quartz.job.AutoTaskProcessor;
import com.winxuan.ec.task.support.quartz.job.TaskAware;
import com.winxuan.ec.task.support.quartz.job.TaskConfigure;
import com.winxuan.framework.util.StringUtils;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-12-14
 */
@Service("notifyService")
public class NotifyServiceImpl implements NotifyService,Serializable{

	private static final long serialVersionUID = 149407614161402851L;
	private static final Log LOG = LogFactory.getLog(AutoTaskProcessor.class);

	@Autowired
	private MailService mailService;

	@Value("${task.noticemail}")
	private String noticeMail;

	@Value("${task.noticemail.template}")
	private String noticeMaiTlemplate;

	@Value("${task.noticemsg}")
	private String noticeMessage;

	@Override
	public void notify(Trigger trigger,String msg){
		sendMail(trigger.getName(),trigger.getGroup(),trigger.getDescription(), msg);
	}

	@Override
	public void notify(TaskAware aware,String msg) {
		if(aware != null){
			if(aware instanceof TaskConfigure){
				TaskConfigure configure = (TaskConfigure) aware;
				int p = configure.getNotifyLevel();
				if(p == TaskConfigure.LEVEL_ALL){
					mailNotice(aware, msg);
					msgNotice(aware, msg);
				}else if(p == TaskConfigure.LEVEL_MAIL){
					mailNotice(aware, msg);
				}else if(p == TaskConfigure.LEVEL_MESSAGE){
					msgNotice(aware, msg);
				}
			}
		}
		LOG.info(String.format("%s notify msg: %s", aware.getName(),msg));
	}

	private void mailNotice(TaskAware aware,String msg){
		sendMail(aware.getName(),aware.getGroup(),aware.getDescription(), msg);
	}

	private void msgNotice(TaskAware aware ,String msg){
		sendMsg(aware.getName(),aware.getGroup(),aware.getDescription(), msg);
	}

	public void sendMail(String name,String group,String desc,String msg){
		if(!StringUtils.isNullOrEmpty(noticeMail) && !StringUtils.isNullOrEmpty(noticeMaiTlemplate)){
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("trigger_name",name);
			model.put("trigger_group",name);
			model.put("trigger_desc",desc);
			String[] mails = null;
			if(noticeMail.indexOf(',')!=-1){
				mails = noticeMail.split(",");
			}else{
				mails = new String[1];
				mails[0]=noticeMail;
			}
			if(mails != null){
				for(String mail : mails){
					mailService.sendMail(mail,msg,noticeMaiTlemplate, model);
				}
			}
		}else{
			LOG.warn("not mail template!");
		}
	}

	public void sendMsg(String name,String group,String desc,String msg){
		if(!StringUtils.isNullOrEmpty(noticeMessage) && !StringUtils.isNullOrEmpty(noticeMaiTlemplate)){
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("trigger_name",name);
			model.put("trigger_group",name);
			model.put("trigger_desc",desc);
			String[] mails = null;
			if(noticeMessage.indexOf(',')!=-1){
				mails = noticeMessage.split(",");
			}else{
				mails = new String[1];
				mails[0]=noticeMessage;
			}
			if(mails != null){
				for(String mail : mails){
					mailService.sendMail(mail,msg,noticeMaiTlemplate, model);
				}
			}
		}else{
			LOG.error("not mail template!");
		}
	}

}

