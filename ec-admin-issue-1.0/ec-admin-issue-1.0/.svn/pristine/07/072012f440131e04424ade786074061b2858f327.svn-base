package com.winxuan.ec.admin.controller.channel;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.ChannelSalesException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.channel.ChannelSalesProduct;
import com.winxuan.ec.model.channel.ChannelSalesRecord;
import com.winxuan.ec.model.channel.ChannelSalesUploadRecord;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.channel.ChannelSalesService;
import com.winxuan.ec.service.channel.ChannelService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.AcceptHashMap;

/**
 * 渠道真实销售
 * @author heyadong
 *
 */
@Controller
@RequestMapping("/channelsales")
public class ChannelSalesController {
	@Autowired
	ChannelService channelService;

	@Autowired
	ProductService productService;
	@Autowired
	CodeService codeService;
	
	@Autowired
	ChannelSalesService channelSalesService;
	
	//上传页面
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public ModelAndView upload(){
		ModelAndView mav = new ModelAndView("/channel/sales/upload");
		List<Channel> channels = channelService.get(Channel.CHANNEL_ORDER_SETTLE).getLeafChildren();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.DATE, -1);
		mav.addObject("endDate", cal.getTime());
		mav.addObject("channels", channels);
		return mav;
	}
	
	//上传
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public ModelAndView upload(Long channelId, String remark,@RequestParam(value = "file") MultipartFile file ,@MyInject Employee employee)
		throws ChannelSalesException, ParseException, IOException {
		ModelAndView mav = new ModelAndView("/channel/sales/result");
		@SuppressWarnings("unchecked")
		List<String> text = IOUtils.readLines(file.getInputStream());
		
        Channel channel = channelService.get(channelId);
        ChannelSalesUploadRecord uploadrecord = new ChannelSalesUploadRecord();
        uploadrecord.setChannel(channel);
        uploadrecord.setRemark(remark);
        uploadrecord.setUploader(employee);
        uploadrecord.setUploadtime(new Date());
        uploadrecord.appendStatusLog(employee, codeService.get(Code.CHANNELSALES_UPLOADED));
        Map<String,ChannelSalesRecord> records = new HashMap<String, ChannelSalesRecord>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 1; i < text.size(); i++){
        	String[] cells = text.get(i).split(",");
        	if (cells.length < 5){
        		throw new ChannelSalesException(i + "行上传格式不正确");
        	}
        	String startdate = cells[0];
        	String enddate = cells[1];
        	String channelProduct = cells[2];
        	String sales = cells[3];
        	String refund = cells[4];
        	
        	if (StringUtils.isBlank(startdate)
        			|| StringUtils.isBlank(enddate)
        			|| StringUtils.isBlank(channelProduct)
        			|| StringUtils.isBlank(sales)
        			|| StringUtils.isBlank(refund)){
         		mav.addObject(ControllerConstant.MESSAGE_KEY,"数据存在空行,行数:" + i);
                 return mav;
         	} else if(!startdate.matches("\\d{4}-\\d{2}-\\d{2}") || !enddate.matches("\\d{4}-\\d{2}-\\d{2}")) {
        		mav.addObject(ControllerConstant.MESSAGE_KEY,"日期格式 不正确,行数:" + i);
                return mav;
        	} else if (!sales.matches("[0-9]+") || !refund.matches("[0-9]+")){
        		mav.addObject(ControllerConstant.MESSAGE_KEY,"销售或退货数据格式不正确,行数:" + i);
                return mav;
        	} else if (!channelProduct.matches("[0-9a-zA-Z]+")){
        		mav.addObject(ControllerConstant.MESSAGE_KEY,"渠道商品ID格式 不正确,行数:" + i);
                return mav;
        	}
        	//由于上传销售记录有重复的情况 , 在此做合并处理.
        	String key = startdate + enddate + channelProduct;
        	if (!records.containsKey(key)){
        		ChannelSalesRecord record = new ChannelSalesRecord();
            	record.setUploadrecord(uploadrecord);
            	record.setChannelProduct(channelProduct);
            	record.setStartdate(format.parse(startdate));
            	record.setEnddate(format.parse(enddate));
            	record.setRefund(Integer.valueOf(refund));
            	record.setSales(Integer.valueOf(sales));
            	record.setStatus(codeService.get(Code.CHANNELSALES_UPLOADED));
            	records.put(key, record);
        	} else {
        		ChannelSalesRecord record = records.get(key);
        		record.setSales(record.getSales() + Integer.valueOf(sales));
        		record.setRefund(record.getRefund() + Integer.valueOf(refund));
        	}
        	
        	
        	
        	
        }
        
        channelSalesService.upload(uploadrecord, new ArrayList<ChannelSalesRecord>(records.values()));
        //上传成功，列表页
        return new ModelAndView("redirect:/channelsales/view");
        
	}
	
	//展示上传记录
	@RequestMapping("/view")
	public ModelAndView view(@MyInject Pagination pagination){
		ModelAndView mav = new ModelAndView("/channel/sales/view");
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("nstatus", new Long[]{Code.CHANNELSALES_DELETEED});
		mav.addObject("records", channelSalesService.find(params, pagination));
		mav.addObject("pagination",pagination);
		return mav;
	}
	
	//删除上传记录
	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable Long id, @MyInject Employee employee) {
		channelSalesService.delete(id, employee);
		ModelAndView mav = new ModelAndView("redirect:/channelsales/view");
		return mav;
	}
	
	//冲销
	@RequestMapping("/rollback/{id}")
	public ModelAndView rollback(@PathVariable Long id, @MyInject Employee employee) throws ChannelSalesException{
		channelSalesService.rollback(id, employee);
		ModelAndView mav = new ModelAndView("redirect:/channelsales/view");
		return mav;
	}
	
	
	private String[] split(String content){
		if (content != null && content.length() > 0){
			String[] s = content.trim().split("\r\n");
			for (int i = 0; i < s.length; i++){
				s[i] = s[i].trim();
			}
			return s;
		}
		return null;
	}
	private Long[] toLong(String[] s){
		List<Long> list = new ArrayList<Long>();
		if (s != null && s.length > 0){
			
			for (int i = 0; i < s.length; i++){
				if (s[i].matches("\\d+")) {
					list.add(Long.valueOf(s[i]));
				}
			}
			return list.toArray(new Long[list.size()]);
		}
		return null;
	}
	
	//渠道销售商品关维护
	@RequestMapping("/product/view")
	public ModelAndView productView(ChannelSalesQueryForm form, @MyInject Pagination pagination){
		ModelAndView mav = new ModelAndView("/channel/sales/productview");
		String[] ps = split(form.getProductSales());
		Long[] productSalesId = toLong(ps);
		
		String[] channelProductId = split(form.getChannelProducts());
		AcceptHashMap<String,Object> params = new AcceptHashMap<String,Object>();
		params.acceptIf("channelIds", form.getChannels(), form.getChannels() != null && form.getChannels().length > 0);
		params.acceptIf("channelProducts", channelProductId, channelProductId != null && channelProductId.length > 0);
		params.acceptIf("productSales", productSalesId, productSalesId != null && productSalesId.length > 0);
		
		List<ChannelSalesProduct> channelproducts = channelSalesService.findChannelSalesProduct(params, pagination, (short)0);
		mav.addObject("channelproducts", channelproducts);
		mav.addObject("channels", channelService.get(Channel.CHANNEL_ORDER_SETTLE).getLeafChildren());
		
		mav.addObject("pagination",pagination);
		
		return mav;
	}
	
	//渠道商品关系删除
	@RequestMapping("/product/delete")
	public ModelAndView productDelete(Long[] channelproducts, @MyInject Employee employee){
		channelSalesService.deleteChannelSalesProduct(channelproducts, employee);
		return new ModelAndView("redirect:/channelsales/product/view");
	}
	
	//渠道商品关系添加页面
	@RequestMapping(value="/product/upload", method=RequestMethod.GET)
	public ModelAndView productUpload(){
		ModelAndView mav = new ModelAndView("/channel/sales/productupload");
		List<Channel> channels = channelService.get(Channel.CHANNEL_ORDER_SETTLE).getLeafChildren();
		
		mav.addObject("channels", channels);
		
		return mav;
	}
	
	@RequestMapping(value="/product/upload", method=RequestMethod.POST)
	public ModelAndView productUpload(Long[] channels, String channelProducts, String productSales,@MyInject Employee employee) throws Exception{
		
		String[] ps = split(productSales);
		Long[] productSalesId = toLong(ps);
		String[] channelProductId = split(channelProducts);
		if (ArrayUtils.isEmpty(channels) || ArrayUtils.isEmpty(ps) || ArrayUtils.isEmpty(productSalesId)
				|| ArrayUtils.isEmpty(channelProductId)) {
			throw new ChannelSalesException("请完善参数,参数不能为空！");
		}
		
		if (productSalesId.length != channelProductId.length) {
			throw new ChannelSalesException("渠道商品ID,与EC商品ID数量不匹配!");
		}

		Map<String, Long> map = new HashMap<String,Long>();
		for (int i = 0; i < productSalesId.length; i++){
			Object old = map.put(channelProductId[i], productSalesId[i]);
			if (old != null) {
				throw new ChannelSalesException("上传渠道商品ID重复:" + channelProductId[i]);
			}
		}
		
		
		
		
		for (Long l : channels){
			Channel c = channelService.get(l);
			if (c == null) {
				throw new ChannelSalesException(l + ",渠道不存在");
			} 
		}
		
		
		channelSalesService.appendChannelSalesProduct(Arrays.asList(channels), map, employee);

		

		ModelAndView mav = new ModelAndView("redirect:/channelsales/product/upload");
		return mav;
	}
	
	@RequestMapping("/sendtosap")
	public ModelAndView sendToSap(BigDecimal money, @MyInject Employee employee) throws ChannelSalesException{
		channelSalesService.sendToSap(money, employee);
		return new ModelAndView("redirect:/channelsales/view");
	}
	
	/**
	 * 审核校验失败的记录
	 * @param recordId
	 * @param employee
	 * @return
	 */
	@RequestMapping("/audit/{id}")
	public ModelAndView audit(@PathVariable("id")Long uploadRecordId, @MyInject Employee employee) {
		ModelAndView view = new ModelAndView("redirect:/channelsales/view");
		channelSalesService.audit(uploadRecordId);
		return view;
	}
}
