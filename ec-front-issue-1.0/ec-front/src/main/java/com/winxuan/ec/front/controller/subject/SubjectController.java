package com.winxuan.ec.front.controller.subject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.cm.Fragment;
import com.winxuan.ec.model.subject.Subject;
import com.winxuan.ec.service.cm.CmService;
import com.winxuan.ec.service.subject.SubjectService;

/**
 * 
 * @author sunflower
 *
 */
@Controller
@RequestMapping(value = "/subject")
public class SubjectController {

	@Autowired
	SubjectService subjectService;
	
	@Autowired
	CmService cmService;

	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable("page") String page) {
		ModelAndView modelAndView = new ModelAndView("/subject/index");
		Subject subject = subjectService.get(Long.parseLong(page));
		if(subject == null){
			modelAndView.addObject("subject", new Subject());
		}else{
			modelAndView.addObject("page", page);
			modelAndView.addObject("subject", subject);
			
			Fragment fragment = new Fragment();
			fragment.setPage("subject/"+page);
			List<Fragment> fragments = cmService.getFragmentsByContext(fragment);
			modelAndView.addObject("fragments", fragments);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/fragment/{id}", method = RequestMethod.GET)
	public ModelAndView fragment(@PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("/subject/fragment");
		Fragment fragment = cmService.getFragment(id);
		Subject subject = subjectService.get(Long.parseLong(fragment.getPage().split("/")[1]));
		modelAndView.addObject("subject", subject);
		modelAndView.addObject("contentList", cmService.findContent(fragment));
		return modelAndView;
	}
}
