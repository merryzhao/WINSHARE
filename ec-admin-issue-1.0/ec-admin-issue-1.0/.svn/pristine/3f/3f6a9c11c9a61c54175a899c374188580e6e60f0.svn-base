package com.winxuan.ec.admin.controller.monitor;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.model.monitor.MonitorTask;
import com.winxuan.ec.model.monitor.ProductSaleMonitor;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.monitor.MonitorService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;

/**
 * 监控
 * @author heyadong
 * @version 1.0, 2012-10-18 下午02:43:51
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController {
    
    @Autowired
    private MonitorService monitorService;
    
    @RequestMapping(value="/create",method=RequestMethod.GET)
    public ModelAndView view(@MyInject Employee employee) {
        
        ModelAndView mav = new ModelAndView("/monitor/new");
        return mav;
    }
    
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public ModelAndView list(@MyInject Pagination pagination) {
        ModelAndView mav = new ModelAndView("/monitor/list");
        List<MonitorTask> list = monitorService.query(new HashMap<String, Object>(), pagination);
        mav.addObject("list", list);
        mav.addObject("pagination",pagination);
        return mav;
    }
    
    @RequestMapping(value="/status") 
    public ModelAndView status(@MyInject Employee employee,
                               @RequestParam(value="status") int status,
                               @RequestParam(value="taskId") Long taskId) {
        ModelAndView mav = new ModelAndView("/monitor/result");
        if (MonitorTask.TASK_STATUS_ENABLE == status) {
            monitorService.enable(taskId, employee);
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
        } else if (MonitorTask.TASK_STATUS_UNABLE == status) {
            monitorService.unable(taskId, employee);
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_SUCCESS);
        } else {
            mav.addObject(ControllerConstant.RESULT_KEY, ControllerConstant.RESULT_WARNING);
        }
        return mav;
    }
    
    @RequestMapping(value="/product/new",method=RequestMethod.POST)
    public ModelAndView newProductSaleTask(@MyInject Employee employee
            ,@RequestParam(value="file",required=false) MultipartFile file
            ,MonitorProductSaleForm form) throws BiffException, IOException {
        MonitorTask task = new MonitorTask();
        task.setCreatetime(new Date());
        task.setCreator(employee);
        task.setName(form.getName());
        task.setDescription(form.getDescription());
        task.setMessage(form.getMessage());
        task.setEmails(form.getEmails() == null ? null : StringUtils.join(form.getEmails(), ";"));
        task.setMobiles(form.getMobiles() == null ? null : StringUtils.join(form.getMobiles(), ";"));
        task.setStart(form.getStart());
        task.setEnd(form.getEnd());
        task.setFrequency(form.getFrequency());
        ProductSaleMonitor params = new ProductSaleMonitor();
        params.setStockLess(form.getStockLess());
        Workbook workbook = Workbook.getWorkbook(file.getInputStream());
        monitorService.addProductSaleMonitor(task, params, workbook, employee);
        return new ModelAndView("redirect:/monitor/list");
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
}
