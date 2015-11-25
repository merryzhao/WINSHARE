package com.winxuan.ec.admin.controller.channel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.channel.ChannelAccountService;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.support.interceptor.MyInject;

/**
 * 渠道
 * 
 * @author Min-Huang
 * @version 1.0, 2011-7-20
 */
@Controller
@RequestMapping("/channel")
public class ChannelController {

	// 消息常量
	private static final String MESSAGE_SAVE_SUCCESS = "保存成功";
	private static final String MESSAGE_CHANNEL_NAME_IS_NULL = "渠道名称不能为空";
	private static final String MESSAGE_TYPE_ID_INVALID = "渠道分类参数不正确";
	private static final String MESSAGE_PARENT_ID_INVALID = "父级渠道参数不正确";
	private static final String MESSAGE_NOT_FOUND_CHANNEL = "没有找到对应的渠道";
	private static final String THREE_SPLIT_PATH = ".";
	private String rootChannelStr = "rootChannel";

	@Autowired
	private ChannelService channelService;
	@Autowired
	private CodeService codeService;

	@Autowired
	private ChannelAccountService channelAccountService;

	/**
	 * 默认页
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		Channel rootChannel = channelService.get(Channel.ROOT);
		Code channelType = codeService.get(Code.CHANNEL_TYPE);
		ModelAndView mav = new ModelAndView("/channel/index");
		if (null == rootChannel) {
			rootChannel = new Channel();
		}
		if (null == channelType) {
			channelType = new Code();
		}
		List<Channel> channelList = new ArrayList<Channel>();
		treeToList(rootChannel, channelList);
		/*
		 * List<Channel> secondList=rootChannel.getAvailableChildren();
		 * for(Channel channel:secondList){ treeToList(channel,channelList); }
		 */
		mav.addObject(rootChannelStr, rootChannel);
		mav.addObject("channelType", channelType);
		mav.addObject("channelList", channelList);
		return mav;
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public ModelAndView categorylist(@RequestParam(required = false, value = "id") Long id,
			@RequestParam(required = false, value = "type") Long type) {
		if (id == null) {
			id = Channel.ROOT;
		}
		Channel channel = channelService.get(id);
		ModelAndView mav = new ModelAndView("/channel/tree");
		mav.addObject("channel", channel);
		mav.addObject("type", type);
		return mav;
	}

	private void treeToList(Channel channel, List<Channel> list) {
		if (channel != null) {
			list.add(channel);
			Set<Channel> set = channel.getChildren();
			if (null != set && set.size() > 0) {
				for (Channel ch : set) {
					treeToList(ch, list);
				}
			}
		}
	}

	/**
	 * 保存表单
	 * 
	 * @param name
	 * @param parentId
	 * @param typeId
	 * @return
	 */

	// 较小的表单使用此种方式，但需注意post过来的参数名需和@requestParam后面的变量名一致否则会报400错误

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam String name, @RequestParam String parent, @RequestParam boolean available,
			@RequestParam boolean usingapi, @RequestParam boolean issettle, @RequestParam String type) {
		ModelAndView mav = new ModelAndView("redirect:/channel/list");
		Long parentId;
		Long typeId;
		if (!isValidated(name, parent, type, mav)) {
			return mav;
		} else {
			parentId = NumberUtils.createLong(parent);
			typeId = NumberUtils.createLong(type);
		}
		Channel parentChannl = channelService.get(parentId);
		Code channelType = new Code(typeId);
		Channel childChannel = new Channel();
		childChannel.setName(name);
		childChannel.setParent(parentChannl);
		childChannel.setType(channelType);
		childChannel.setCreateTime(new Date());
		childChannel.setUsingApi(usingapi);
		childChannel.setAvailable(available);
		childChannel.setIssettle(issettle);
		// 新增维护渠道的 树路径，先保存父路径
		childChannel.setPath(StringUtils.isNotBlank(parentChannl.getPath()) ? parentChannl.getPath() : "");
		channelService.create(parentChannl, childChannel);
		String childThreePath = StringUtils.isNotBlank(parentChannl.getPath()) ? childChannel.getPath()
				.concat(THREE_SPLIT_PATH).concat(String.valueOf(childChannel.getId())) : String.valueOf(childChannel
				.getId());
		childChannel.setPath(childThreePath);
		channelService.update(childChannel);
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@RequestParam Long parent, @RequestParam String name, @RequestParam boolean available,
			@RequestParam String type, @RequestParam boolean usingapi,@RequestParam boolean issettle) {

		ModelAndView mav = new ModelAndView("redirect:/channel/list");
		Channel childChannel = channelService.get(parent);
		childChannel.setName(name);
		childChannel.setType(codeService.get(NumberUtils.createLong(type)));
		childChannel.setCreateTime(new Date());
		childChannel.setAvailable(available);
		childChannel.setUsingApi(usingapi);
		childChannel.setIssettle(issettle);
		channelService.update(childChannel);
		mav.addObject(ControllerConstant.JSON_OBJECT_KEY, channelService.get(childChannel.getId()));
		mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
		mav.addObject(ControllerConstant.MESSAGE_KEY, MESSAGE_SAVE_SUCCESS);
		return mav;
	}

	/**
	 * @param name
	 * @param parentId
	 * @param typeId
	 * @param mav
	 * @return
	 */

	private boolean isValidated(String name, String parentId, String typeId, ModelAndView mav) {
		boolean validated = true;
		if (!NumberUtils.isNumber(parentId)) {
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			mav.addObject(ControllerConstant.MESSAGE_KEY, MESSAGE_PARENT_ID_INVALID);
			return false;
		}
		if (!NumberUtils.isNumber(typeId)) {
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			mav.addObject(ControllerConstant.MESSAGE_KEY, MESSAGE_TYPE_ID_INVALID);
			return false;
		}
		if (StringUtils.isBlank(name)) {
			mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			mav.addObject(ControllerConstant.MESSAGE_KEY, MESSAGE_CHANNEL_NAME_IS_NULL);
			return false;
		}
		return validated;
	}

	/**
	 * 子渠道列表
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/{id}/list", method = RequestMethod.GET)
	public ModelAndView listChannelByChannelId(@PathVariable("id") Long id) {
		Channel channel = channelService.get(id);
		ModelAndView mav = new ModelAndView("/channel/item_list");

		if (null == channel) {
			channel = new Channel();
		}
		List<Channel> channelList = new ArrayList<Channel>();
		treeToList(channel, channelList);
		mav.addObject("channel", channel);
		mav.addObject("channelList", channelList);
		return mav;
	}

	/**
	 * 新建渠道
	 * 
	 * @return
	 */

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newChannel() {

		Channel rootChannel = channelService.get(Channel.ROOT);
		Code channelType = codeService.get(Code.CHANNEL_TYPE);

		ModelAndView mav = new ModelAndView("/channel/_new");
		mav.addObject(rootChannelStr, rootChannel);
		mav.addObject("channelType", channelType);
		return mav;
	}

	/**
	 * 所有渠道列表
	 * 
	 * @return
	 */

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listChannel(@RequestParam(required = false, value = "needAvailableSort") Short needAvailableSort) {
		needAvailableSort = needAvailableSort == null ? 2 : needAvailableSort;
		Channel rootChannel = channelService.get(Channel.ROOT);
		ModelAndView mav = new ModelAndView("/channel/list");
		if (null != rootChannel) {
			mav.addObject(rootChannelStr, rootChannel);
		} else {
			mav.addObject(rootChannelStr, new Channel());
		}
		Code channelType = codeService.get(Code.CHANNEL_TYPE);
		List<Channel> channelList = new ArrayList<Channel>();
		treeToList(rootChannel, channelList);
		mav.addObject("channelList", channelList);
		mav.addObject("channelType", channelType.getChildren());
		mav.addObject("needAvailableSort", needAvailableSort);
		return mav;
	}

	/**
	 * 修改渠道状态
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{id}/change", method = RequestMethod.POST)
	public ModelAndView changeChannelState(@PathVariable Long id) {
		Channel channel = channelService.get(id);

		ModelAndView mav = new ModelAndView("/channel/success_result");
		ModelMap modelMap = new ModelMap();
		if (null == channel) {
			modelMap.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
			modelMap.put(ControllerConstant.MESSAGE_KEY, MESSAGE_NOT_FOUND_CHANNEL);
		} else {
			channel.setAvailable(!channel.isAvailable());
			channelService.update(channel);
			modelMap.put(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
			modelMap.put(ControllerConstant.MESSAGE_KEY, MESSAGE_SAVE_SUCCESS);
		}
		mav.addAllObjects(modelMap);
		return mav;
	}

	@RequestMapping(value = "/pay/gotoUpload", method = RequestMethod.GET)
	public ModelAndView uploadPage() {
		ModelAndView mav = new ModelAndView("/channel/channel_detail_upload");
		mav.addObject("histories", channelAccountService.getHistory());
		return mav;
	}

	@RequestMapping(value = "/pay/uploadDetail", method = RequestMethod.POST)
	public ModelAndView detailUpload(@RequestParam(value = "file") MultipartFile file, @MyInject Employee employee)
			throws Exception {
		// ModelAndView mav = new ModelAndView("/channel/result");
		ModelAndView mav = new ModelAndView("redirect:/channel/pay/gotoUpload");
		channelAccountService.uploadAccountDetail(Workbook.getWorkbook(file.getInputStream()), employee,
				file.getOriginalFilename());
		return mav;
	}

	@RequestMapping(value = "/confirmOrder/gotoUpload", method = RequestMethod.GET)
	public ModelAndView uploadConfirmOrderPage() {
		ModelAndView mav = new ModelAndView("/channel/channel_confrim_order_upload");
		return mav;
	}

	@RequestMapping(value = "/confrimOrder/upload", method = RequestMethod.POST)
	public ModelAndView confirmOrderUpload(@RequestParam(value = "file") MultipartFile file, @MyInject Employee employee) {
		ModelAndView mav = new ModelAndView("/channel/result");
		try {
			channelAccountService.uploadConfirmOrder(Workbook.getWorkbook(file.getInputStream()), employee);
			mav.addObject(ControllerConstant.MESSAGE_KEY, String.format("核对订单上传成功!", employee.getName()));
		} catch (Exception e) {
			mav.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
		}

		return mav;
	}

	/**
	 * 获取渠道的下级区域
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ModelAndView get(@PathVariable("id") Long id) {
		ModelAndView andView = new ModelAndView("/channel/lower_area");
		if (id != null) {
			List<Channel> channelList = channelService.findChildren(id);
			andView.addObject("channelList", channelList);
		}
		return andView;
	}
}
