package com.winxuan.ec.front.controller.customer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.CustomerPointsException;
import com.winxuan.ec.exception.PresentExchangeException;
import com.winxuan.ec.model.customer.CustomerPoints;
import com.winxuan.ec.model.present.PresentExchange;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 用户的积分
 * @author sunflower
 *
 */
@Controller
@RequestMapping(value="/customer/points")
public class CustomerPointsController {
	
/*	private static final String LAST_THREE_MONTH_TYPE = "3";
	private static final String LAST_YEAR_TYPE = "12";*/
	private static final boolean CAN_EXCHANGE = true;
	private static final boolean CANNOT_EXCHANGE = false;
	
	private static final Log LOG = LogFactory.getLog(CustomerPointsController.class);
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	PresentService presentService;
	

	/**
	 * 用户查询积分记录
	 * @param customer
	 * @param type
	 * @param pagination
	 * @return
	 */
	@RequestMapping(value="", method = RequestMethod.GET)
	public ModelAndView list(@MyInject Customer customer ,
			@RequestParam(value ="type",defaultValue = "3") String type,
			@MyInject Pagination pagination){
		ModelAndView modelAndView = new ModelAndView("/customer/points/list");
		
		List<CustomerPoints> customerPointsList = new ArrayList<CustomerPoints>();
		ScoreDate scoreDate = setScoreDate(type);
		customerPointsList = customerService.findPoints(customer, scoreDate.startDate, scoreDate.endDate, pagination);

		modelAndView.addObject("customerPointsList", customerPointsList);
		modelAndView.addObject("points", customer.getAccount().getPoints());
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	/**
	 * 查询积分记录开始时间和结束时间封装类
	 * @author sunflower
	 *
	 */
	class ScoreDate{
		
		Date startDate;
		Date endDate;
		
		ScoreDate(Date startDate,Date endDate){
			
			this.startDate = startDate;
			this.endDate = endDate;
		}
	}
	
	/**
	 * 设定开始时间和结束时间
	 * @param last
	 * @return
	 */
	private ScoreDate setScoreDate(String last){
		
		Calendar calendar = Calendar.getInstance();
		Date startDate = null;
		Date endDate = new Date();

		calendar.add(Calendar.MONTH, 0-Integer.parseInt(last));
		startDate = calendar.getTime();
		return new ScoreDate(startDate,endDate);
	}
	

	/**
	 * 查询所有可兑换类型
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="/pointsExchange", method = RequestMethod.GET)
	public ModelAndView pointsExchange(@MyInject Customer customer){
		ModelAndView modelAndView = new ModelAndView("/customer/points/pointsExchange");
		List<PresentExchange> presentExchanges = new ArrayList<PresentExchange>();
		presentExchanges = presentService.findExchange();
		modelAndView.addObject("points", customer.getAccount().getPoints());
		modelAndView.addObject("presentExchanges", presentExchanges);
		return modelAndView;
	}
	
	/**
	 * 查询指定present_exchange Id的可兑换礼券
	 * @param customer
	 * @param presentExchangeId
	 * @return
	 */
	@RequestMapping(value="/presentExchange/{presentExchangeId}", method = RequestMethod.GET)
	public ModelAndView presentExchange(@MyInject Customer customer,@PathVariable Long presentExchangeId){
		ModelAndView modelAndView = new ModelAndView("/customer/points/presentExchange");
		PresentExchange presentExchange = new PresentExchange();
		presentExchange = presentService.findExchange(presentExchangeId);
		int points = customer.getAccount().getPoints();
		if(points < presentExchange.getPoints()){
			modelAndView.addObject("canExchange", CANNOT_EXCHANGE);
		}else{
			modelAndView.addObject("canExchange", CAN_EXCHANGE);
		}
		modelAndView.addObject("presentExchange", presentExchange);
		modelAndView.addObject("points", customer.getAccount().getPoints());
		return modelAndView;
	}
	
	@RequestMapping(value="/exchange", method = RequestMethod.GET)
	public ModelAndView exchange(@MyInject Customer customer,
			@RequestParam(value="id",required=true) Long id){
		ModelAndView modelAndView = new ModelAndView("/customer/points/exchange");
		PresentExchange presentExchange = new PresentExchange();
		presentExchange = presentService.findExchange(id);
		if(presentExchange == null){
			LOG.warn("没有查询到指定id的present exchange对应关系");
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		try {
			presentService.exchange(customer, presentExchange.getValue(),presentExchange.getPoints());
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		} catch (CustomerPointsException e) {
			LOG.error(e.getMessage(),e);
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_WARNING);
		} catch (PresentExchangeException e) {
			LOG.error(e.getMessage(),e);
			modelAndView.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
		}
		return modelAndView;
	}
}
