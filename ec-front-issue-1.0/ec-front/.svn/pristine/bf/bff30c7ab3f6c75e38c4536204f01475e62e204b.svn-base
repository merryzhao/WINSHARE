package com.winxuan.ec.front.controller.cps;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.front.controller.cps.query.Query;
import com.winxuan.ec.front.controller.cps.query.QueryFactory;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-8
 */
@Controller
@RequestMapping(value = "/query")
public class QueryController {
	
	@Autowired
	QueryFactory queryFactory;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView track(@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//不同对象
		//得到不同参数
		//通过参数查询出结果
		//将结果转化成字符串
		Query query = queryFactory.get(id);
		if(query == null){
			ModelAndView modelAndView = new ModelAndView("/cps/error");
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,"不存在该联盟");
			return modelAndView;
		}
		String result ="";
		try {
			 result = query.query(request);
		} catch (CpsException e) {
			ModelAndView modelAndView = new ModelAndView("/cps/error");
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,e.getMessage());
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("/cps/result");
		String message = StringUtils.isBlank(result)?"没有满足条件的数据":result;
		modelAndView.addObject(ControllerConstant.MESSAGE_KEY, message);
		return modelAndView;
	}

}
