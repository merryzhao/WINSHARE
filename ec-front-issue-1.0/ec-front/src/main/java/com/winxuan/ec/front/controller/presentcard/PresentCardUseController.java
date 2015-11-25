package com.winxuan.ec.front.controller.presentcard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.util.Md5Encrypt;
import com.winxuan.ec.controller.ControllerConstant;
import com.winxuan.ec.exception.PresentCardException;
import com.winxuan.ec.front.controller.Constant;
import com.winxuan.ec.model.presentcard.PresentCard;
import com.winxuan.ec.model.presentcard.PresentCardDealLog;
import com.winxuan.ec.service.presentcard.PresentCardService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.presentcard.PresentCardUtils;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.web.CookieUtils;
import com.winxuan.framework.util.web.WebContext;

/**
 * 
 * @author cast911
 *
 */

@Controller
@RequestMapping(value="/presentcard")
public class PresentCardUseController {
	private static final String PRESENTCARD_INFO="pi";
	private static final String DEFAULT_DOMAIN = ".winxuan.com";
	private static final String DEFAULT_PATH = "/";
	
	@Autowired
	private PresentCardService presentCardService;
	
	

	
	/**
	 *  礼品卡登陆
	 * @param card
	 * @param password
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView useCard(@Valid PresentCardForm presentCardForm){ 
		ModelAndView modelAndView = new ModelAndView("redirect:/presentcard/info");
		PresentCard presentCard =null;
		try {
			presentCard =  presentCardService.login(presentCardForm.getCard(), presentCardForm.getPassword());
         	modelAndView.addObject("presentCard", presentCard);
		if(presentCard !=null){
			String md5Cardpwd = Md5Encrypt.md5(presentCardForm.getPassword());
			Cookie cookie = new Cookie(PRESENTCARD_INFO,presentCardForm.getCard()+"&"+md5Cardpwd);
			cookie.setMaxAge(Constant.COOKIE_CLIENT_AGE);
			cookie.setPath(Constant.COOKIE_PATH);
			cookie.setDomain(Constant.COOKIE_DOMAIN);
			CookieUtils.writeCookie(cookie);
		}
		} catch (PresentCardException e) {
			modelAndView.setViewName("presentcard/giftcard_index");
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,e.getMessage());
		}
		return modelAndView;
	}
	@RequestMapping(value="/info",method=RequestMethod.GET)
	public ModelAndView cardInfo(@MyInject Pagination pagination){
		ModelAndView modelAndView =new ModelAndView("/presentcard/detail");
		PresentCard presentCard = this.checkCard();
        if(presentCard!=null){
        	pagination.setPageSize(MagicNumber.THREE);
        	modelAndView.addObject("presentCard",presentCard);
        	modelAndView.addObject("canUse",presentCard.canUse());
        	Map<String,Object> map = new HashMap<String, Object>();
    		map.put("presentCardId", presentCard.getId());
    		List<PresentCardDealLog> presentCardDealLoglist = presentCardService.findDealLogList(map, pagination);
    		modelAndView.addObject("presentCardDealLoglist", presentCardDealLoglist);
    		modelAndView.addObject("pagination", pagination);
        }else{
        	modelAndView.setViewName("redirect:/giftcard");
        }
		
		return modelAndView;
		
	}
	
	/**
	 * 验证礼品卡
	 * @return
	 */
	private PresentCard checkCard(){
		PresentCard presentCard = null;
		Cookie cookie = CookieUtils.getCookie(PRESENTCARD_INFO);
		if(cookie==null){
			return null;
		}else{
			String[] value = cookie.getValue().split("&");
			presentCard =  presentCardService.get(value[0]);
			String pwd = presentCard.getProclaimPassword();
			if(Md5Encrypt.md5(pwd).equals(value[1])){
				return presentCard;
			}else{
				presentCard = null;
			}			
		}
		return presentCard;
		
	}
	
	@RequestMapping(value="/pwdchange",method=RequestMethod.GET)
	public ModelAndView pwdChange(){
		ModelAndView modelAndView = new ModelAndView("/presentcard/password_change");
		PresentCard presentCard = this.checkCard();
        if(presentCard!=null){
        	modelAndView.addObject("presentCard",presentCard);
        }else{
        	modelAndView.setViewName("redirect:/giftcard");
        }
		return modelAndView;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ModelAndView cardUpdate(PresentCardForm presentCardForm){
		ModelAndView modelAndView = new ModelAndView("/presentcard/password_change_succeeded");
		PresentCard presentCard;
		try {
			presentCard = presentCardService.login(presentCardForm.getCard(), presentCardForm.getPassword());
			byte[] newpwd = PresentCardUtils.generatePassword(presentCardForm.getNewPassWord());
			presentCard.setPassword(newpwd);
			presentCardService.update(presentCard);
		} catch (PresentCardException e) {
			modelAndView.setViewName("redirect:/presentcard/pwdchange");
			modelAndView.addObject(ControllerConstant.MESSAGE_KEY,e.getMessage());
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/quit",method=RequestMethod.GET)
	public ModelAndView quit(){
		ModelAndView modelAndView = new ModelAndView("redirect:/giftcard");
		Cookie cookie = CookieUtils.getCookie(PRESENTCARD_INFO);
		if(cookie!=null){
		CookieUtils.removeCookie (WebContext.currentRequest(), WebContext.currentResponse(),PRESENTCARD_INFO);	
		CookieUtils.removeCookie(PRESENTCARD_INFO, DEFAULT_PATH, DEFAULT_DOMAIN);
		}
		return modelAndView;
	}
	
	
	
}
