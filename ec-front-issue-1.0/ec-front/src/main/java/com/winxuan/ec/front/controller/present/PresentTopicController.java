package com.winxuan.ec.front.controller.present;

//import java.util.ArrayList;
//import java.util.List;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.present.PresentBatch;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.present.PresentService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.util.security.MD5Custom;

/**
 * @author zhousl
 *
 * 2013-4-12
 */
@Controller
@RequestMapping(value="/present")
public class PresentTopicController {

	@Autowired
	private PresentService presentService;
	
	/**
	 * 此方法注掉，以后领礼券可加一个标识以限制用户通过url猜批次号领取礼券
	 * luosh 2013-06-19 修改：
	 * 新增一个参数，存取加密码.
	 * 参数名为：token
	 * 参数值为：MD5Custom.encrypt(批次ID-礼券有效开始时间-礼券有效截止时间)
	 * 
	 * @param customer
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value="/send", method=RequestMethod.GET)
	public ModelAndView sendToCustomer(@MyInject Customer customer,
			@RequestParam("id")Long batchId,@RequestParam("token")String token){
		ModelAndView mav = new ModelAndView("/present/send");
		if(customer == null){
			//未登陆
			mav.addObject(ControllerConstant.RESULT_KEY, 0);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "请先登录！");
			return mav;
		}
		if(token == null){
			mav.addObject(ControllerConstant.RESULT_KEY, 2);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "地址验证不通过！");
			return mav;
		}
		if(customer.getChannel().getId().equals(Channel.CHANNEL_AGENT)){
			//代理商不领礼券
			mav.addObject(ControllerConstant.RESULT_KEY, 2);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "代理商不领礼券！");
			return mav;
		}
		PresentBatch presentBatch = presentService.getBatch(batchId);
		if(presentBatch == null){
			mav.addObject(ControllerConstant.RESULT_KEY, 2);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "礼券批次不存在！");
			return mav;
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer dateStr = new StringBuffer(batchId + "-");
		if(presentBatch.getPresentStartDate() != null){
			dateStr.append(df.format(presentBatch.getPresentStartDate()) + "-");
		}
		if(presentBatch.getPresentEndDate() != null){
			dateStr.append(df.format(presentBatch.getPresentEndDate()));
		}
		
		if(!token.equals(MD5Custom.encrypt(dateStr.toString()))){
			mav.addObject(ControllerConstant.RESULT_KEY, 2);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "地址验证不通过！");
			return mav;
		}
		if(presentBatch.getNum() <= presentBatch.getCreatedNum()){
			//礼券已领完
			mav.addObject(ControllerConstant.RESULT_KEY, 2);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "礼券已领完！");
			return mav;
		}
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		try {
			presentService.sendPresent4Customer(presentBatch, customers, null);
			mav.addObject(ControllerConstant.RESULT_KEY, 1);
		} catch (PresentException e) {
			//领取礼券失败
			mav.addObject(ControllerConstant.RESULT_KEY, 2);
			mav.addObject(ControllerConstant.MESSAGE_KEY, "领取礼券失败！");
		}
		return mav;
	}
	@RequestMapping(value="/vailable", method=RequestMethod.GET)
	public ModelAndView hasPresent(@RequestParam(value="b")Long[] batchIds){
		ModelAndView mav = new ModelAndView("/present/vailable");
		mav.addObject("t0", false);
		mav.addObject("t1", false);
		mav.addObject("t2", false);
		PresentBatch presentBatch = null;
		for(int i=0; i<batchIds.length; i++){
			presentBatch = presentService.getBatch(batchIds[i]);
			if(presentBatch.getCreatedNum() < presentBatch.getNum()){
				mav.addObject("t"+i, true);
			}
		}
		return mav;
	}
}
