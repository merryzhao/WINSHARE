package com.winxuan.ec.admin.controller.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.exception.SmsMessageException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.message.SmsOrderMessage;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.delivery.DeliveryService;
import com.winxuan.ec.service.message.SmsMessageService;
import com.winxuan.ec.service.order.OrderMessageService;
import com.winxuan.ec.service.order.OrderMessageServiceImpl;
import com.winxuan.ec.service.order.OrderService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 短信Controller
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    
    @Autowired
    CodeService codeService;
    @Autowired
    ChannelService channelService;
    @Autowired
    DeliveryService deliveryService;
    @Autowired
    SmsMessageService smsMessageService;
    
    @Autowired
    OrderService orderService;
    @Autowired
    OrderMessageService orderMessageService;
    @RequestMapping
    public ModelAndView index(@MyInject Pagination pagination){
        ModelAndView mav = new ModelAndView("/message/index");
        List<SmsOrderMessage> list =smsMessageService.query(new HashMap<String, Object>(), pagination);
        mav.addObject("list",list);
        mav.addObject("pagination",pagination);
        return mav;
    }
    
    @RequestMapping(value="/new",method=RequestMethod.GET)
    public ModelAndView gonew(){
       ModelAndView mav = new ModelAndView("/message/new");
       
       HashMap<String, Object> parameters = new HashMap<String, Object>();
       mav.addObject("messagetype",codeService.get(Code.SMS_ORDER_TYPE).getChildren());
       mav.addObject("channels", channelService.find(parameters));
       mav.addObject("paytype",codeService.get(Code.ORDER_PAY_TYPE).getChildren());
       
       mav.addObject("deliverytype", deliveryService.findDeliveryType(true));
       mav.addObject("tags", OrderMessageServiceImpl.TAGS.entrySet());
       return mav;
    }
    
    private String join(Object[] emls){
        if (emls != null && emls.length > 0) {
            return ";" + StringUtils.join(emls,';') + ";";
        }
        return "";
    }
    @RequestMapping(value="/new", method=RequestMethod.POST)
    public ModelAndView create(OrderMessageForm form, @MyInject Employee employee){
        SmsOrderMessage msg = new SmsOrderMessage();
        msg.setChannels(join(form.getChannels()));
        msg.setDeliveryType(join(form.getDeliverytype()));
        msg.setEmployee(employee);
        msg.setKindgreat(form.getKindgreat());
        msg.setKindless(form.getKindless());
        msg.setMessage(form.getMessage());
        msg.setPaytype(join(form.getPaytype()));
        msg.setRemark(form.getRemark());
        msg.setType(codeService.get(form.getType()));
        try {
            smsMessageService.save(msg);
        } catch (SmsMessageException e) {
            ModelAndView mav = new ModelAndView("/message/error");
            mav.addObject("message",e.getMessage());
            return mav;
            
        }
        return new ModelAndView("redirect:/message");
    }
    
    @RequestMapping(value="/edit/{id}",method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id){
        ModelAndView mav = new ModelAndView("/message/edit");
        SmsOrderMessage message = smsMessageService.get(id);
        mav.addObject("message", message);
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        mav.addObject("messagetype",codeService.get(Code.SMS_ORDER_TYPE).getChildren());
        mav.addObject("channels", channelService.find(parameters));
        mav.addObject("paytype",codeService.get(Code.ORDER_PAY_TYPE).getChildren());
        
        mav.addObject("deliverytype", deliveryService.findDeliveryType(true));
        mav.addObject("tags", OrderMessageServiceImpl.TAGS.entrySet());
        
        
        return mav;
    }
    @RequestMapping(value="/edit/{id}",method=RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id,OrderMessageForm form, @MyInject Employee employee){

        SmsOrderMessage msg = smsMessageService.get(id);
        msg.setChannels(join(form.getChannels()));
        msg.setDeliveryType(join(form.getDeliverytype()));
        msg.setEmployee(employee);
        msg.setKindgreat(form.getKindgreat());
        msg.setKindless(form.getKindless());
        msg.setMessage(form.getMessage());
        msg.setPaytype(join(form.getPaytype()));
        msg.setRemark(form.getRemark());
        msg.setType(codeService.get(form.getType()));
        try {
            smsMessageService.update(msg);
        } catch (SmsMessageException e) {
            ModelAndView mav = new ModelAndView("/message/error");
            mav.addObject("message",e.getMessage());
            return mav;
        }
        
        return new ModelAndView("redirect:/message");
    }
    
    @ResponseBody
    @RequestMapping(value="/enable/{id}")
    public String enable(@PathVariable("id") Long id, @MyInject Employee employee) throws SmsMessageException {
        SmsOrderMessage msg = smsMessageService.get(id);
        msg.setEmployee(employee);
        msg.setEnable(true);
        
        smsMessageService.update(msg);
       
        return "successful!";
    }
    @ResponseBody
    @RequestMapping(value="/unable/{id}")
    public String unable(@PathVariable("id") Long id, @MyInject Employee employee) throws SmsMessageException {
        SmsOrderMessage msg = smsMessageService.get(id);
        msg.setEmployee(employee);
        msg.setEnable(false);
        
        smsMessageService.update(msg);
        
        return "successful!";
    }
    
    @RequestMapping(value="/retry", method=RequestMethod.GET)
    public ModelAndView retry(){
    	
    	return new ModelAndView("/message/retry");
    }
    
    @RequestMapping(value="/retry", method=RequestMethod.POST)
    public ModelAndView retry(String orders) throws Exception{
    	
    	if (orders != null){
    		List<Order> list = new ArrayList<Order>();
    		String[] os = orders.split("[\r\n]+");
    		for (int i = 0; i < os.length; i++){
    			os[i] = os[i].trim();
    			if(!os[i].matches("\\d+")){
    				throw new IllegalArgumentException(String.format("%s,订单号格式错误", os[i]));
    			}
    			Order o = orderService.get(os[i]);
    			if (o == null) {
    				throw new IllegalArgumentException(String.format("%s, 订单不存在", os[i]));
    			} else {
    				list.add(o);
    			}
    		}
    		
    		orderMessageService.retry(list);
    		
    	}
    	ModelAndView mav = new ModelAndView("/message/retry");
    	mav.addObject("message","补发成功");
    	return mav;
    }
}
