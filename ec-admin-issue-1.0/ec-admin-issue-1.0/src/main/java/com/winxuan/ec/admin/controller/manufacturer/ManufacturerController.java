package com.winxuan.ec.admin.controller.manufacturer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.ManufacturerException;
import com.winxuan.ec.model.manufacturer.Manufacturer;
import com.winxuan.ec.service.manufacturer.ManufacturerService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 出版社
 * @author heyadong
 * @version 1.0, 2012-9-17 下午03:44:03
 */
@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController {
    
    private static final String TOKEN = "%";
    
    @Autowired
    ManufacturerService manufacturerService;
    
    @RequestMapping("/list")
    public ModelAndView list(
            @RequestParam(value="name",required=false) String name,
            @RequestParam(value="nickname",required=false) String nickname,
            @MyInject Pagination pagination) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(name)) {
            map.put("name",TOKEN + name + TOKEN);
        }
        if (StringUtils.isNotBlank(nickname)) {
            map.put("nickname", TOKEN + nickname + TOKEN);
        }
        ModelAndView mav = new ModelAndView("/manufacturer/list");
        
        
        mav.addObject("manufacturers", manufacturerService.query(map, pagination));
        mav.addObject("pagination",pagination);
        return mav;
    }
    
    @RequestMapping("/updateName")
    public ModelAndView updateName(@RequestParam Integer id, @RequestParam String name) {
        ModelAndView mav = new ModelAndView("/manufacturer/result");
        Manufacturer manufacturer = manufacturerService.get(id);
        try {
            manufacturerService.updateName(manufacturer, name);
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
            mav.addObject(ControllerConstant.MESSAGE_KEY, "修改成功");
        } catch (ManufacturerException e) {
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
            mav.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
        }
        return mav;
    }
    
    @RequestMapping("/append")
    public ModelAndView append(@RequestParam Integer id, @RequestParam String name) {
        ModelAndView mav = new ModelAndView("/manufacturer/result");
        Manufacturer manufacturer = manufacturerService.get(id);
        try {
            manufacturerService.appendItem(manufacturer, name);
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
            mav.addObject(ControllerConstant.MESSAGE_KEY, "添加成功");
        } catch (ManufacturerException e) {
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
            mav.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
        }
        return mav;
    }
    @RequestMapping("/save")
    public ModelAndView save(@RequestParam String name) {
        ModelAndView mav = new ModelAndView("/manufacturer/result");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        try {
            manufacturerService.save(manufacturer);
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
            mav.addObject(ControllerConstant.MESSAGE_KEY, "添加成功");
        } catch (ManufacturerException e) {
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_PARAMETER_ERROR);
            mav.addObject(ControllerConstant.MESSAGE_KEY, e.getMessage());
        }
        
        return mav;
    }
}
