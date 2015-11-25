/*
 * @(#)PaymentControl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.roadmap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.roadmap.Roadmap;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.roadmap.RoadmapService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

 

/**
 *  
 * 保存roadmap
 * @author rensy
 * @version 1.0,2011-8-23
 */

@Controller
@RequestMapping("/roadmap")
public class RoadmapController {
	@Autowired
	private RoadmapService roadmapService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request,
			@MyInject Employee operator) throws Exception {
		    Roadmap roadmap=new Roadmap();
		    String date =request.getParameter("createTime");
		    if(date==null||date.length()==0) {
		    	roadmap.setCreateTime(new Date());
		    }else{
		    	SimpleDateFormat sFormat= new SimpleDateFormat("yyyy-MM-dd");
		    	roadmap.setCreateTime(sFormat.parse(date));
		    }
		    roadmap.setEmployee(operator);
		    roadmap.setContent(request.getParameter("content"));
		    roadmapService.save(roadmap);
 		    return new ModelAndView("redirect:/roadmap/list");
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView findRoadmap(@MyInject Pagination pagination) throws Exception {
		final int size=15;
		ModelAndView modelAndView=new ModelAndView("/roadmap/roadmaplist");
		List<RoadmapBean> roadmaps=new ArrayList<RoadmapBean>();
		for(Roadmap roadmap:roadmapService.findRoadmaps(pagination, (short)0)){
			RoadmapBean roadmapBean=new RoadmapBean();
			roadmapBean.setContent(roadmap.getContent());
			roadmapBean.setCreateTime(roadmap.getCreateTime());
			roadmapBean.setEmployee(roadmap.getEmployee());
			roadmapBean.setId(roadmap.getId());
			String content=roadmap.getContent().replaceAll("<[^>]*>", "");
			if(content.length()>size){
				roadmapBean.setNoStylecontent(content.substring(0,size)+"...");
			}else{
				roadmapBean.setNoStylecontent(content);
			}
			roadmaps.add(roadmapBean);
		}
        modelAndView.addObject("list",roadmaps);
        modelAndView.addObject(pagination);
 		return modelAndView;
		
	}
	
	@RequestMapping(value = "/goeditor", method = RequestMethod.GET)
	public ModelAndView goEditor() throws Exception {
 		return new ModelAndView("/roadmap/editor");
		
	}
	 
}
