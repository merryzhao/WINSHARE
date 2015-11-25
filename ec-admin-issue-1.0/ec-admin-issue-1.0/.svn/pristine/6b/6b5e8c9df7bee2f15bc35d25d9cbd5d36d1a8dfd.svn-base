/*
 * @(#)Test.java
 *
 * Copyright 2011 Winxuan.Com, Inc. All rights reserved.
 */
package com.winxuan.ec.admin.controller.present;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.present.PresentBatch;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.RandomCodeUtils;
import com.winxuan.framework.util.StringUtils;

/**
 * 
 * 
 * @author
 * @version 1.0,2011-8-31
 */
@Controller
@RequestMapping("/presentbatch")
public class PresentBatchController {
	private static final Long UNVERIFY = 16001L;
	private static final String PRESENTBATCHIDSTR = "presentBatchId";
	private static final String UNVERIFYPRESENTBATCHESSTR = "unVerifyPresentBatches";
	private static final String PASSVERIFYCOUNT = "passVerifyCount";
	private static final String AJAXVERIFYRESULTURL = "/present/verify_result";
	private static final String DATEFORMAT = "yyyy-MM-dd";
	private static final String NEEDVERIFYCOUNT = "needVerifyBatchCount";
	private static final String TYPESTR = "type";
	private static final String CHECKVALUE = "Y";
	private static final int GENERALCODELENGTH = 5;
	private static final Long CODEID = 16001L;
	@Autowired
	PresentService presentService;
	@Autowired
	CodeService codeService;

	/**
	 * 跳转到添加页面
	 * 
	 * @return
	 * @throws ParseException
	 * 
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView goNewAddPresentBatch() throws ParseException {
		ModelAndView mav = new ModelAndView("/present/present_batch_add");
		PresentBatchForm presentBatchForm = new PresentBatchForm();
		presentBatchForm.setNum(0);
		presentBatchForm.setMaxQuantity(0);
		presentBatchForm.setIsPloy(CHECKVALUE);
		presentBatchForm.setIsRebate(CHECKVALUE);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
		presentBatchForm.setPresentStartDateString(simpleDateFormat
				.format(new Date()));
		mav.addObject("presentBatchForm", presentBatchForm);
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 * @throws ParseException
	 * 
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView goEditPresentBatch(@PathVariable("id") Long id)
	throws ParseException {
		ModelAndView mav = new ModelAndView("/present/present_batch_edit");
		PresentBatch presentBatch = presentService.getBatch(id);
		PresentBatchForm presentBatchForm = new PresentBatchForm();
		presentBatchForm.setBatchTitle(presentBatch.getBatchTitle());
		presentBatchForm.setCreateNum(presentBatch.getCreatedNum());
		presentBatchForm.setDescription(presentBatch.getDescription());
		presentBatchForm.setGeneralCode(presentBatch.getGeneralCode());
		presentBatchForm.setId(presentBatch.getId());
		presentBatchForm.setIsGeneral(presentBatch.isGeneral());
		presentBatchForm.setMaxQuantity(presentBatch.getMaxQuantity());
		presentBatchForm.setNum(presentBatch.getNum());
		presentBatchForm.setOrderBaseAmount(presentBatch.getOrderBaseAmount());
		if (presentBatch.getPresentEndDate() != null) {
			presentBatchForm.setPresentEndDateString(new SimpleDateFormat(
					DATEFORMAT).format(presentBatch.getPresentEndDate()));
		} else {
			presentBatchForm.setPresentEffectiveDay(presentBatch
					.getPresentEffectiveDay());
		}
		presentBatchForm.setPresentStartDateString(new SimpleDateFormat(
				DATEFORMAT).format(presentBatch.getPresentStartDate()));
		List<String> types = new ArrayList<String>();
		String productType = presentBatch.getProductType();
		for (int i = 0; i < productType.length(); i++) {
			types.add(productType.substring(i, i + 1));
		}
		presentBatchForm.setProductType(types);
		if(presentBatch.isPloy()){
			presentBatchForm.setIsPloy(CHECKVALUE);
		}
		if(presentBatch.isRebate()){
			presentBatchForm.setIsRebate(CHECKVALUE);
		}
		presentBatchForm.setValue(presentBatch.getValue());
		mav.addObject("presentBatchForm", presentBatchForm);
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		if(Code.PRESENT_BATCH_STATUS_PASS.equals(presentBatch.getState().getId())){
			mav.setViewName("/present/present_batch_show");
		}
		return mav;
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/createorupdate", method = RequestMethod.POST)
	public ModelAndView newAddOrEditPresentBatch(
			@Valid PresentBatchForm presentBatchForm, BindingResult result,
			@MyInject Employee operator, HttpServletRequest request)
	throws ParseException {
		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("presentBatchForm", presentBatchForm);
			if ("save".equals(request.getParameter("flag"))) {
				mav.setViewName("/present/present_batch_add");
				return mav;
			} else {
				mav.setViewName("/present/present_batch_edit");
				return mav;
			}

		}
		PresentBatch presentBatch = new PresentBatch();
		presentBatch.setCreatedNum(0);
		presentBatch.setCreateTime(new Date());
		presentBatch.setDescription(presentBatchForm.getDescription());
		presentBatch.setBatchTitle(presentBatchForm.getBatchTitle());
		presentBatch.setGeneral(presentBatchForm.getIsGeneral());
		presentBatch.setMaxQuantity(presentBatchForm.getMaxQuantity());
		presentBatch.setNum(presentBatchForm.getNum());
		presentBatch.setOrderBaseAmount(presentBatchForm.getOrderBaseAmount());
		presentBatch.setPloy(CHECKVALUE.equals(presentBatchForm.getIsPloy()));
		presentBatch.setRebate(CHECKVALUE.equals(presentBatchForm.getIsRebate()));
		String presentEndDateString = request
		.getParameter("presentEndDateString");
		if (presentEndDateString != null) {
			if (presentEndDateString.length() != 0) {
				presentBatch.setPresentEndDate(new SimpleDateFormat(DATEFORMAT)
				.parse(presentEndDateString));
			}
		}
		String presentEffectiveDay = request
		.getParameter("presentEffectiveDay");
		if (presentEffectiveDay != null) {
			if (presentEffectiveDay.length() != 0) {
				presentBatch.setPresentEffectiveDay(Integer
						.valueOf(presentEffectiveDay));
			}
		}
		presentBatch.setPresentStartDate(new SimpleDateFormat(DATEFORMAT)
		.parse(presentBatchForm.getPresentStartDateString()));
		presentBatch.setState(new Code(CODEID));
		String productType = "";
		if (presentBatchForm.getProductType() != null) {
			for (String type : presentBatchForm.getProductType()) {
				productType = productType + type;
			}
		}
		if (presentBatchForm.getIsGeneral()) {
			presentBatch.setGeneralCode(generateCode());
		}
		presentBatch.setProductType(productType);
		presentBatch.setValue(presentBatchForm.getValue());
		try {
			if ("save".equals(request.getParameter("flag"))) {
				presentService.createBatch(presentBatch, operator);
			} else if ("edit".equals(request.getParameter("flag"))) {
				presentBatch.setId(presentBatchForm.getId());
				presentService.updateBatch(presentBatch);
			}
		} catch (Exception e) {
			ModelAndView mav = new ModelAndView("/present/result");
			mav.addObject("message", e.getMessage());
			return mav;
		}
 		ModelAndView mav = new ModelAndView("redirect:/presentbatch/select");
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}
	
	private String generateCode(){
		String resultCode = RandomCodeUtils.create(RandomCodeUtils.MODE_LETTER_UPPERCASE_NUMBER, GENERALCODELENGTH);
		return presentService.isExistedByCode(resultCode) > 0 ? generateCode() : resultCode;
	}

	/**
	 * description
	 * 
	 * @author liyang
	 * @version 1.0,2011-8-31
	 */

	/**
	 * 查询礼劵批次
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView view(@Valid PresentBatchSearchForm resentBatchSearchForm,
			@MyInject Pagination pagination) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		if (!StringUtils.isNullOrEmpty(resentBatchSearchForm.getId())) {
			parameters.put("id", Long.valueOf(resentBatchSearchForm.getId()));
		}
		parameters.put("value", resentBatchSearchForm.getValue());
		parameters.put("batchTitle", resentBatchSearchForm.getBatchTitle());
		parameters.put("createUserName", resentBatchSearchForm.getCreateUser());
		parameters.put("stateId", resentBatchSearchForm.getBatchState());

		List<PresentBatch> presentBatchList = presentService.findBatch(parameters, pagination);
		ModelAndView mav = new ModelAndView("/present/query_presentbatch");
		//礼券批次状态
		Code batchStatus = codeService.get(Code.PRESENT_BATCH_STATUS);
		mav.addObject("batchStatus", batchStatus);
		mav.addObject("presentBatchList", presentBatchList);
		mav.addObject("pagination", pagination);
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}

	/**
	 * 跳转到礼劵批次查询画面
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public ModelAndView presentBatchPage() {
		// 返回
		ModelAndView mav = new ModelAndView("/present/query_presentbatch");
		//礼券批次状态
		Code batchStatus = codeService.get(Code.PRESENT_BATCH_STATUS);
		mav.addObject("batchStatus", batchStatus);
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}

	/**
	 * 跳转到批量审核页面
	 * 
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value = "/unVerifyPresentBatch", method = RequestMethod.GET)
	public ModelAndView findNeedVerify(@MyInject Pagination pagination) {
		ModelAndView mav = new ModelAndView("/present/verify_batch");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("stateId", new Long[] { UNVERIFY });
		List<PresentBatch> unVerifyPresentBatches = presentService.findBatch(
				parameters, pagination);
		mav.addObject(UNVERIFYPRESENTBATCHESSTR, unVerifyPresentBatches);
		mav.addObject(pagination);
		mav.addObject(NEEDVERIFYCOUNT, presentService.findNeedVerifyCount());
		return mav;
	}

	/**
	 * 审核单个批次
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public ModelAndView verify(
			@RequestParam(PRESENTBATCHIDSTR) Long presentBatchId,
			@RequestParam(TYPESTR) int type, @MyInject Employee operator) {
		ModelAndView mav = new ModelAndView(AJAXVERIFYRESULTURL);
		PresentBatch presentBatch = presentService.getBatch(presentBatchId);
		if (type == 1) {
			presentBatch.setState(new Code(Code.PRESENT_BATCH_STATUS_PASS));
			mav.addObject(ControllerConstant.RESULT_KEY, NumberUtils.LONG_ONE);
			presentService.verifyBatch(presentBatch, operator);
		}
		if (type == 0) {
			presentBatch.setState(new Code(Code.PRESENT_BATCH_STATUS_FAIL));
			mav.addObject(ControllerConstant.RESULT_KEY, NumberUtils.LONG_ZERO);
			presentService.updateBatch(presentBatch);
		}
		mav.addObject(PASSVERIFYCOUNT, NumberUtils.LONG_ONE);
		return mav;
	}

	/**
	 * 批量审核批次
	 */
	@RequestMapping(value = "/verifyBatch", method = RequestMethod.POST)
	public ModelAndView verify(@RequestParam(required=true,value="item") String presentBatchIds,
			@RequestParam(TYPESTR) int type, @MyInject Employee operator) {
		ModelAndView mav = new ModelAndView(AJAXVERIFYRESULTURL);
		String[] ids = presentBatchIds.trim().split(",");
		// 循环审核
		if (null != presentBatchIds) {
			for (int i = 0; i < ids.length; i++) {
				PresentBatch presentBatch = presentService.getBatch(Long
						.valueOf(ids[i]));
				if (type == 1) {
					presentBatch.setState(new Code(
							Code.PRESENT_BATCH_STATUS_PASS));
					presentService.verifyBatch(presentBatch, operator);
				}
				if (type == 0) {
					presentBatch.setState(new Code(
							Code.PRESENT_BATCH_STATUS_FAIL));
					presentService.updateBatch(presentBatch);
				}
			}
		}
		mav.addObject(PASSVERIFYCOUNT, ids.length);
		mav.addObject(ControllerConstant.RESULT_KEY, NumberUtils.LONG_ONE);
		return mav;
	}


	/**
	 * 查询礼券批次
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/findbatchlist", method = RequestMethod.POST)
	public ModelAndView findPresentBathcList(
			@Parameter("paramType") String paramType,
			@Parameter("paramValue") String paramValue,
			@Parameter("nomey") String nomey) {
		ModelAndView mav = new ModelAndView("/presentbatch/presentbatch");
		List<PresentBatch> presentBatchs = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if ("0".equals(paramType) && paramValue.length() > 0) {
			params.put("id", Long.valueOf(paramValue));
		} else if ("1".equals(paramType) && paramValue.length() > 0) {
			params.put("batchTitle", paramValue);
		}
		if (nomey.length() > 0) {
			params.put("value", Integer.valueOf(nomey));
		}
		presentBatchs = presentService.findBatch(params);
		ModelMap map = new ModelMap();
		map.put(ControllerConstant.RESULT_KEY,
				ControllerConstant.RESULT_SUCCESS);
		map.put(ControllerConstant.MESSAGE_KEY, "success");
		map.put("presentBatchs", presentBatchs);
		mav.addAllObjects(map);
		return mav;
	}

}
