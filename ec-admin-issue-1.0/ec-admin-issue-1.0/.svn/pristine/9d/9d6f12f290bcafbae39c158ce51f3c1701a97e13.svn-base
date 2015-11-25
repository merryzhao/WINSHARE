package com.winxuan.ec.admin.controller.search;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.search.SearchListRules;
import com.winxuan.ec.service.search.SearchListRulesService;

/**
 * 搜索规则
 * 
 * @author 赵芮
 * 
 *         上午11:51:28
 */
@Controller
@RequestMapping("/search")
public class SearchListRulesController {

	@Autowired
	SearchListRulesService searchListRulesService;

	@RequestMapping(value = "/rules", method = RequestMethod.GET)
	public ModelAndView rules() {
		List<SearchListRules> rules = searchListRulesService.findAll();
		ModelAndView mav = new ModelAndView("/search/rule/rules");
		mav.addObject("rules", rules);
		return mav;
	}



	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public ModelAndView update(@RequestParam(value="boost",required=false) String boost,
			@RequestParam(value="available",required=false) String available,
			@RequestParam("id") int id) {
		ModelAndView mav = new ModelAndView("/search/rule/rules");
		SearchListRules rule = searchListRulesService.get(id);
		if (rule != null) {
			if(boost!=null){
				rule.setBoost(new BigDecimal(boost));
			}
			if("on".equals(available)){
				 rule.setAvailable((short)1);
			}else{
				if(rule.getParent()==null){
				 rule.setAvailable((short)0);
				}
			}
		}
		searchListRulesService.update(rule);
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		mav.addObject(ControllerConstant.MESSAGE_KEY, "修改成功!");
		mav.addObject("rules", searchListRulesService.findAll());

		return mav;
	}
}
