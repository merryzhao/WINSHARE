package com.winxuan.ec.admin.controller.survey;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.model.survey.Answer;
import com.winxuan.ec.model.survey.Item;
import com.winxuan.ec.model.survey.Select;
import com.winxuan.ec.model.survey.SelectValue;
import com.winxuan.ec.model.survey.Separator;
import com.winxuan.ec.model.survey.Survey;
import com.winxuan.ec.model.survey.SurveyText;
import com.winxuan.ec.service.survey.SurveyService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 
 * @author sunflower
 * 
 */
@Controller
@RequestMapping("/survey")
public class SurveyController {

	@Autowired
	SurveyService surveyService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView surveyList(@MyInject Pagination pagination) {

		ModelAndView mav = new ModelAndView("/survey/list");
		mav.addObject("surveys", surveyService.querySurveys(pagination));
		mav.addObject("pagination", pagination);
		return mav;
	}

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public ModelAndView clients(@MyInject Pagination pagination) {

		ModelAndView mav = new ModelAndView("/survey/client");
		mav.addObject("clients", surveyService.queryClients(pagination));
		mav.addObject("pagination", pagination);
		return mav;
	}

	@RequestMapping(value = "/client/detail/{clientId}", method = RequestMethod.GET)
	public ModelAndView answers(@PathVariable("clientId") Long clientId) {

		ModelAndView mav = new ModelAndView("/survey/detail");
		Set<Answer> answers = surveyService.queryClient(clientId).getAnswers();
		Iterator<Answer> it = answers.iterator();
		while (it.hasNext()) {
			Answer answer = it.next();
			mav.addObject("survey", answer.getSurvey());
			break;
		}
		mav.addObject("answers", answers);
		return mav;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addSurvey(
			@RequestParam(value = "title", required = true) String title) {

		ModelAndView mav = new ModelAndView("/survey/add");
		Survey survey = new Survey();
		survey.setTitle(title);
		surveyService.addSurvey(survey);
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/{surveyId}", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable("surveyId") String surveyId) {
		ModelAndView modelAndView = new ModelAndView("/survey/index");
		Survey survey = surveyService.get(Long.parseLong(surveyId));
		if (survey == null) {
			modelAndView.addObject("survey", new Survey());
		} else {
			modelAndView.addObject("survey", survey);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/separatorManage", method = RequestMethod.POST)
	public ModelAndView separatorManage(
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "separatorId") Long separatorId,
			@RequestParam(value = "surveyId", required = true) Long surveyId,
			@RequestParam(value = "type", required = true) int type) {

		ModelAndView mav = new ModelAndView("/survey/separatorManage");
		Survey survey = surveyService.get(surveyId);
		if (survey == null) {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			return mav;
		}
		Separator separator = surveyService.getSeparator(separatorId);
		if (separator == null) {
			separator = new Separator();
			separator.setTitle(title);
			separator.setType(type);
			Item item = new Item();
			item.setContent(separator);
			item.setSurvey(survey);
			item.setIndex(survey.getLastItemIndex() + 1);
			survey.addItem(item);
		} else {
			separator.setTitle(title);
			separator.setType(type);
		}
		surveyService.saveOrUpdateSeparator(separator);
		surveyService.updateSurvey(survey);
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/textManage", method = RequestMethod.POST)
	public ModelAndView textManage(
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "minInput") int minInput,
			@RequestParam(value = "maxInput") int maxInput,
			@RequestParam(value = "textId") Long textId,
			@RequestParam(value = "surveyId", required = true) Long surveyId) {

		ModelAndView mav = new ModelAndView("/survey/textManage");
		Survey survey = surveyService.get(surveyId);
		if (survey == null) {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			return mav;
		}
		SurveyText text = surveyService.getText(textId);
		if (text == null) {
			text = new SurveyText();
			text.setTitle(title);
			text.setMaxLength(maxInput);
			text.setMinLength(minInput);
			text.setType(type);
			Item item = new Item();
			item.setContent(text);
			item.setSurvey(survey);
			item.setIndex(survey.getLastItemIndex() + 1);
			survey.addItem(item);
		} else {
			text.setTitle(title);
			text.setMaxLength(maxInput);
			text.setMinLength(minInput);
		}
		surveyService.saveOrUpdateText(text);
		surveyService.updateSurvey(survey);
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/selectManage", method = RequestMethod.POST)
	public ModelAndView selectManage(
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "direction", required = true) int direction,
			@RequestParam(value = "label", required = true) String[] labels,
			@RequestParam(value = "allowInput", required = true) int[] types,
			@RequestParam(value = "minSelect", required = false) Integer minSelect,
			@RequestParam(value = "maxSelect", required = false) Integer maxSelect,
			@RequestParam(value = "selectId") Long selectId,
			@RequestParam(value = "surveyId", required = true) Long surveyId) {

		ModelAndView mav = new ModelAndView("/survey/selectManage");
		Survey survey = surveyService.get(surveyId);
		if (survey == null) {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			return mav;
		}
		if (labels.length != types.length) {
			mav.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			return mav;
		}
		Select select = surveyService.getSelect(selectId);
		if (select == null) {
			select = new Select();
			if (minSelect != null) {
				select.setMinSelect(minSelect.intValue());
			}
			if (maxSelect != null) {
				select.setMaxSelect(maxSelect.intValue());
			}
			select.setTitle(title);
			select.setType(type);
			select.setDirection(direction);
			for (int i = 0; i < labels.length; i++) {
				SelectValue sv = new SelectValue();
				sv.setIndex(i + 1);
				sv.setSelect(select);
				sv.setType(types[i]);
				sv.setValue(labels[i]);
				select.add(sv);
			}
			Item item = new Item();
			item.setContent(select);
			item.setSurvey(survey);
			item.setIndex(survey.getLastItemIndex() + 1);
			survey.addItem(item);
			surveyService.saveOrUpdateSelect(select);
			surveyService.updateSurvey(survey);
		} else {
			if (minSelect != null) {
				select.setMinSelect(minSelect.intValue());
			}
			if (maxSelect != null) {
				select.setMaxSelect(maxSelect.intValue());
			}
			select.setTitle(title);
			select.setType(type);
			select.setDirection(direction);
			Set<SelectValue> svs = new HashSet<SelectValue>(select.getValues());
			for (SelectValue sv : svs) {
				select.getValues().remove(sv);
				sv.setSelect(null);
				surveyService.removeSelectValue(sv);
			}
			for (int i = 0; i < labels.length; i++) {
				SelectValue sv = new SelectValue();
				sv.setIndex(i + 1);
				sv.setSelect(select);
				sv.setType(types[i]);
				sv.setValue(labels[i]);
				select.add(sv);
			}
			surveyService.saveOrUpdateSelect(select);
		}
		mav.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return mav;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public ModelAndView remove(
			@RequestParam(value = "removeId", required = true) Long removeId) {
		ModelAndView modelAndView = new ModelAndView("/survey/remove");
		Item item = surveyService.getItem(removeId);
		if (item.getType().equals(Item.TYPE_SELECT)) {
			surveyService.removeSelect(item.getContent());
		} else if (item.getType().equals(Item.TYPE_SEPARATOR)) {
			surveyService.removeSeparator(item.getContent());
		} else if (item.getType().equals(Item.TYPE_TEXT)) {
			surveyService.removeText(item.getContent());
		}
		surveyService.deleteItem(item);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}

	@RequestMapping(value = "/moveup", method = RequestMethod.POST)
	public ModelAndView moveup(
			@RequestParam(value = "moveupId", required = true) Long moveupId) {
		ModelAndView modelAndView = new ModelAndView("/survey/moveup");
		Item item = surveyService.getItem(moveupId);
		surveyService.moveup(item);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}

	@RequestMapping(value = "/movedown", method = RequestMethod.POST)
	public ModelAndView movedown(
			@RequestParam(value = "movedownId", required = true) Long movedownId) {
		ModelAndView modelAndView = new ModelAndView("/survey/moveup");
		Item item = surveyService.getItem(movedownId);
		surveyService.movedown(item);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public ModelAndView delete(
			@RequestParam(value = "deleteId", required = true) Long deleteId) {
		ModelAndView modelAndView = new ModelAndView("/survey/delete");

		Survey survey = surveyService.get(deleteId);
		/*
		 * Set<Item> items = survey.getItems(); for(Item item : items){
		 * if(item.getType().equals(Item.TYPE_SELECT)){
		 * surveyService.removeSelect(item.getContent()); }else
		 * if(item.getType().equals(Item.TYPE_SEPARATOR)){
		 * surveyService.removeSeparator(item.getContent()); }else
		 * if(item.getType().equals(Item.TYPE_TEXT)){
		 * surveyService.removeText(item.getContent()); }
		 * surveyService.deleteItem(item); }
		 */
		surveyService.delete(survey);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}

	@RequestMapping(value = "/surveyEdit", method = RequestMethod.POST)
	public ModelAndView surveyEdit(
			@RequestParam(value = "surveyId", required = true) Long surveyId,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "description", required = false) String description) {
		ModelAndView modelAndView = new ModelAndView("/survey/surveyEdit");

		Survey survey = surveyService.get(surveyId);
		if (survey == null) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			return modelAndView;
		}
		survey.setDescription(description);
		survey.setTitle(title);
		surveyService.updateSurvey(survey);
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}
	
	@RequestMapping(value = "/release", method = RequestMethod.POST)
	public ModelAndView release(
			@RequestParam(value = "surveyId", required = true) Long surveyId,
			@RequestParam(value = "release", required = true) String release) {
		ModelAndView modelAndView = new ModelAndView("/survey/release");
		
		Survey survey = surveyService.get(surveyId);
		if (survey == null) {
			modelAndView.addObject(ControllerConstant.RESULT_KEY,
					ControllerConstant.RESULT_PARAMETER_ERROR);
			return modelAndView;
		}
		surveyService.deleteRelease(survey);
		if(release != null && !"".equals(release.trim())){
			String[] urls = release.split(";");
			for(String url : urls){
				surveyService.addRelease(url,survey);
			}
		}
		modelAndView.addObject(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		return modelAndView;
	}
}
