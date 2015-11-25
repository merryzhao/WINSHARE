package com.winxuan.ec.front.controller.survey;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.survey.Answer;
import com.winxuan.ec.model.survey.Client;
import com.winxuan.ec.model.survey.Release;
import com.winxuan.ec.model.survey.Survey;
import com.winxuan.ec.service.survey.SurveyService;
import com.winxuan.framework.util.web.WebContext;

/**
 * 
 * @author sunflower
 * 
 */
@Controller
@RequestMapping(value = "/survey")
public class SurveyController {

	@Autowired
	SurveyService surveyService;

	@RequestMapping(value = "/{surveyId}", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable("surveyId") String surveyId) {
		ModelAndView modelAndView = new ModelAndView("/survey/index");
		Survey survey = surveyService.get(Long.parseLong(surveyId));
		modelAndView.addObject("survey", survey);
		modelAndView.addObject("start", new Date());
		modelAndView.addObject("referer", WebContext.currentRequest().getHeader("Referer"));
		return modelAndView;
	}
	
	@RequestMapping(value = "/{surveyId}/finish", method = RequestMethod.GET)
	public ModelAndView finish(@PathVariable("surveyId") String surveyId,
			@RequestParam(value = "referer") String referer) {
		ModelAndView modelAndView = new ModelAndView("/survey/finish");
		Survey survey = surveyService.get(Long.parseLong(surveyId));
		modelAndView.addObject("survey", survey);
		modelAndView.addObject("referer", referer);
		return modelAndView;
	}
	
	@RequestMapping(value = "/release", method = RequestMethod.GET)
	public ModelAndView finish(@RequestParam(value = "releaseUrl") String releaseUrl) {
		ModelAndView modelAndView = new ModelAndView("/survey/release");
		List<Release> releases = surveyService.findReleasesByUrl(releaseUrl);
		if(releases == null || releases.size()==0){
			return modelAndView;
		}
		
		modelAndView.addObject("survey", "http://www.winxuan.com/survey/"+releases.get(0).getSurvey().getId());
		return modelAndView;
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public ModelAndView submit(@RequestParam(value = "submit") String submit,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "surveyId") Long surveyId) {
		ModelAndView modelAndView = new ModelAndView("/survey/submit");
		Survey survey = surveyService.get(surveyId);
		if (survey == null) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			return modelAndView;
		}
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		try {
			start = sdf.parse(startTime);
		} catch (ParseException e) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			return modelAndView;
		}
		Client client = new Client();
		client.setEnd(new Date());
		client.setStart(start);
		client.setIp(WebContext.currentRequest().getRemoteAddr());
		surveyService.saveClient(client);
		
		String[] submits = submit.split("\\|");
		for(String asw : submits){
			Answer answer = new Answer();
			String[] asws = asw.split("=");
			answer.setSurvey(survey);
			answer.setItem(surveyService.getItem(Long.parseLong(asws[0])));
			answer.setClient(client);
			String[] values = asws[1].split(";");
			if(values.length == 2){
				if("0".equals(values[0])){
					answer.setSubmit(values[1]);
				}else{
					answer.setValue(surveyService.getSelectValue(Long.parseLong(values[0])));
					answer.setSubmit(values[1]);
				}
			}else{
				answer.setValue(surveyService.getSelectValue(Long.parseLong(values[0])));
			}
			surveyService.saveAnswer(answer);
		}
		
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}
}
