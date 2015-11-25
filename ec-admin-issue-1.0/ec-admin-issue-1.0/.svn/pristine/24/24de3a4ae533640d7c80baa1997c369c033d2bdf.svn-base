/**
 * 
 */
package com.winxuan.ec.admin.controller.heartbeat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.model.heartbeat.Beat;
import com.winxuan.ec.service.heartbeat.HeartBeatMonitorService;
import com.winxuan.framework.pagination.Pagination;

/**
 * @author monica
 *
 */
@Controller
@RequestMapping("/heartbeat")
public class HeartBeatController {
	
	private static final String MSG = "msg";

	private static final String ERR_MSG = "errMsg";
	
	@Autowired
	private HeartBeatMonitorService heartBeatMonitorService;
	
	/**
	 * description 查看应用配置信息，比如app、appkey、hostname、timeout、threshold以及phone等
	 * 设置编辑、启用、禁用按钮
	 * @param pagination
	 * @return
	 */
	
	@RequestMapping(value="/app",method=RequestMethod.GET)
	public ModelAndView showAppInfo(Pagination pagination,
			@RequestParam(value = MSG, required = false, defaultValue = "") String msg,
			@RequestParam(value = ERR_MSG, required = false, defaultValue = "") String errMsg){
		ModelAndView modelAndView = new ModelAndView("heartbeat/showBeatInfo");
		//按照应用主键的首字母升序排列
		Short sort = 0;
		List<Beat> beats = this.heartBeatMonitorService.getAll(sort);
		for(Beat beat : beats){
			if(beat.getAvailable()){
				int messageSend = this.heartBeatMonitorService.getMessageSend(beat);
				beat.setMessagesend(messageSend);
			}
			else{
				beat.setMessagesend(0);
			}
		}	
		modelAndView.addObject("beats", beats);
		modelAndView.addObject("pagination", pagination);
		return modelAndView;
	}
	
	/**
	 * 设置应用是启用还是禁用
	 * @param appkey
	 * @param hostname
	 * @return
	 */
	@RequestMapping(value = "/app/modify", method = RequestMethod.PUT)
	public ModelAndView audit(@RequestParam("appkey") String appkey,
			@RequestParam("hostname") String hostname){
		ModelAndView modelAndView = new ModelAndView("heartbeat/showBeatInfo");
		Beat beat = heartBeatMonitorService.get(appkey, hostname);
		if (beat != null){
			this.heartBeatMonitorService.audit(beat);
		}
		modelAndView.addObject("result", 1);
		modelAndView.addObject("beat", beat);
		return modelAndView;
	}
	
	/**
	 * 对应用的名称进行修改
	 * @param appkey
	 * @param hostname
	 * @param app
	 * @return
	 */
	@RequestMapping(value="/updateBeatApp", method = RequestMethod.POST)
	public ModelAndView updateBeatAppInfo(
			@RequestParam(value = "appkey") String appkey,
			@RequestParam(value = "hostname") String hostname,
			@RequestParam(value = "app") String app
			) {
		ModelAndView modelAndView = new ModelAndView("heartbeat/showBeatInfo");
		Beat beat = this.heartBeatMonitorService.get(appkey, hostname);
		beat.setApp(app);
		this.heartBeatMonitorService.updateBeatInfo(beat);
		modelAndView.addObject("beat", beat);
		return modelAndView;
	}

	/**
	 * 对应用的发送短信的次数进行修改
	 * @param appkey
	 * @param hostname
	 * @param threshold
	 * @return
	 */
	@RequestMapping(value="/updateBeatThreshold", method = RequestMethod.POST)
	public ModelAndView updateBeatThresholdInfo(
			@RequestParam(value = "appkey") String appkey,
			@RequestParam(value = "hostname") String hostname,
			@RequestParam(value = "threshold") int threshold
			) {
		ModelAndView modelAndView = new ModelAndView("heartbeat/showBeatInfo");
		Beat beat = this.heartBeatMonitorService.get(appkey, hostname);
		beat.setThreshold(threshold);
		this.heartBeatMonitorService.updateBeatInfo(beat);
		modelAndView.addObject("beat", beat);
		return modelAndView;
	}
	
	/**
	 * 对应用的发送短信的次数进行修改
	 * @param appkey
	 * @param hostname
	 * @param threshold
	 * @return
	 */
	@RequestMapping(value="/updateBeatTimeout", method = RequestMethod.POST)
	public ModelAndView updateBeatTimeoutInfo(
			@RequestParam(value = "appkey") String appkey,
			@RequestParam(value = "hostname") String hostname,
			@RequestParam(value = "timeout") int timeout
			) {
		ModelAndView modelAndView = new ModelAndView("heartbeat/showBeatInfo");
		Beat beat = this.heartBeatMonitorService.get(appkey, hostname);
		beat.setTimeout(timeout);
		this.heartBeatMonitorService.updateBeatInfo(beat);
		modelAndView.addObject("beat", beat);
		return modelAndView;
	}
	
	/**
	 * 对应用的短信发送对象进行修改
	 * @param appkey
	 * @param hostname
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/updateBeatPhone", method = RequestMethod.POST)
	public ModelAndView updateBeatPhoneInfo(
			@RequestParam(value = "appkey") String appkey,
			@RequestParam(value = "hostname") String hostname,
			@RequestParam(value = "phone") String phone
			) {
		ModelAndView modelAndView = new ModelAndView("heartbeat/showBeatInfo");
		Beat beat = this.heartBeatMonitorService.get(appkey, hostname);
		beat.setPhone(phone);
		this.heartBeatMonitorService.updateBeatInfo(beat);
		modelAndView.addObject("beat", beat);
		return modelAndView;
	}
}
