package com.winxuan.ec.admin.controller.replenishment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.order.OrderController;
import com.winxuan.ec.exception.ReplenishmentArtificialLimitException;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.replenishment.MrCycle;
import com.winxuan.ec.model.replenishment.MrExaminer;
import com.winxuan.ec.model.replenishment.MrModelChoose;
import com.winxuan.ec.model.replenishment.MrProductFreeze;
import com.winxuan.ec.model.replenishment.MrProductItem;
import com.winxuan.ec.model.replenishment.MrSubMcCategory;
import com.winxuan.ec.model.replenishment.MrSupply;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.replenishment.MrCycleService;
import com.winxuan.ec.service.replenishment.MrExaminerService;
import com.winxuan.ec.service.replenishment.MrMcTypeService;
import com.winxuan.ec.service.replenishment.MrModelChooseService;
import com.winxuan.ec.service.replenishment.MrProductFreezeService;
import com.winxuan.ec.service.replenishment.MrProductItemService;
import com.winxuan.ec.service.replenishment.MrSubMcCategoryService;
import com.winxuan.ec.service.replenishment.MrSupplyService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.util.DateUtils;

/**
 * 智能补货后台
 * 
 * @author yangxinyi
 * 
 */
@Controller
@RequestMapping("/replenishment")
public class ReplenishmentController {

	private static final Log LOG = LogFactory.getLog(OrderController.class);

	private static final String MSG = "msg";

	private static final String ERR_MSG = "errMsg";

	private static final String ERROR = "error";

	private static final String SUCCESS = "success";

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final String RESTRICT_REASON = "永久限制补货";

	private static final String MANUAL_REASON = "人工冻结补货";

	@Autowired
	private MrMcTypeService mrMcTypeService;
	
	@Autowired
	private MrSubMcCategoryService mrSubMcCategoryService;

	@Autowired
	private MrSupplyService mrSupplyService;

	@Autowired
	private MrModelChooseService mrModelChooseService;

	@Autowired
	private MrCycleService mrCycleService;

	@Autowired
	private MrProductItemService itemService;

	@Autowired
	private MrProductFreezeService freezeService;

	@Autowired
	private CodeService codeService;

	@Autowired
	private MrExaminerService mrExaminerService;
	
	
	/****************************** 上传MC类别 -start **********************************/
	/**
	 * 查看MC分类数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/mrMcTypeShow")
	public ModelAndView showMrMcType(
			@RequestParam(value = MSG, required = false, defaultValue = "") String msg,
			@MyInject Pagination pagination) {
		pagination.setPageSize(50);
		ModelAndView mav = new ModelAndView(
				"/replenishment/replenishment_mc_type");
		mav.addObject("mrMcTypes", mrMcTypeService.getByPage(pagination));
		mav.addObject("pagination", pagination);
		mav.addObject(MSG, msg);
		return mav;
	}

	/**
	 * 上传MC分类数据，包括定位表，特定MC分类
	 * 
	 * @param employee
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/mcTypeExcelImport", method = RequestMethod.POST)
	public ModelAndView mcTypeExcelImport(@MyInject Employee employee,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		String msg = SUCCESS;
		try {
			mrMcTypeService.saveData(file.getInputStream());
		} catch (Exception e) {
			msg = ERROR;
		}
		return new ModelAndView("redirect:/replenishment/mrMcTypeShow?msg="
				+ msg);
	}
	/****************************** 上传MC类别 - end  **********************************/
	
	/****************************** MC二次分类 -start **********************************/
	/**
	 * 查看MC二次分类
	 */
	@RequestMapping(value = "/mrSubMcCategory")
	public ModelAndView showSubMcCategory(
			@RequestParam(value = MSG, required = false, defaultValue = "") String msg,
			@RequestParam(value = "mcCategory", required = false, defaultValue = "") String mcCategory,
			@MyInject Pagination pagination) {
		pagination.setPageSize(50);
		ModelAndView mav = new ModelAndView(
				"/replenishment/replenishment_submccategory");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("mcCategory", mcCategory);
		
		mav.addObject("mrSubMcCategorys", this.mrSubMcCategoryService.findMrSubMcCategorys(parameters, pagination));
		mav.addObject("pagination", pagination);
		mav.addObject("mcCategory",mcCategory);
		mav.addObject(MSG, msg);
		return mav;
	}
	
	/**
	 * 上传MC二次分类
	 */
	@RequestMapping(value = "/subMcCategoryExcelImport", method = RequestMethod.POST)
	public ModelAndView subMcCategoryExcelImport(@MyInject Employee employee,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		String msg = SUCCESS;
		try {
			mrSubMcCategoryService.saveSubMcCategoryData(file.getInputStream());
		} catch (Exception e) {
			msg = ERROR;
		}
		return new ModelAndView("redirect:/replenishment/mrSubMcCategory?msg="
				+ msg);
	}
	
	/**
	 * 删除MC二次分类
	 */
	@RequestMapping(value = "/deleteSubMcCategory", method = RequestMethod.POST)
	public ModelAndView deleteSubMcCategory(@RequestParam("id") Integer id) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/mrSubMcCategory");
		try {
			MrSubMcCategory mrSubMcCategory = this.mrSubMcCategoryService.getMrSubMcCategory(id);
			this.mrSubMcCategoryService.delete(mrSubMcCategory);
			model.addObject(MSG, SUCCESS);
			return model;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, "传入的参数有误！");
			return model;
		}
	}
	
	/****************************** MC二次分类 -  end **********************************/

	/****************************** 团购权重 -start **********************************/

	/**
	 * description 查看团购权重配置
	 * 
	 * @author wangbiao date: 2013-8-23 下午3:27:59
	 * 
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/showMrSupply")
	public ModelAndView showMrSupply(
			@RequestParam(value = MSG, required = false, defaultValue = "") String msg,
			@RequestParam(value = ERR_MSG, required = false, defaultValue = "") String errMsg) {
		ModelAndView mav = new ModelAndView(
				"/replenishment/replenishment_supply");
		mav.addObject("mrSupplys", this.mrSupplyService.getAll());
		mav.addObject(MSG, msg);
		mav.addObject(ERR_MSG, errMsg);
		mav.addObject("codes",
				this.codeService.findByParent(Code.DELIVERY_CENTER));
		return mav;
	}

	@RequestMapping(value = "/saveOrUpdateSupply", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateSupply(@Valid MrSupply mrSupply,
			BindingResult result) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/showMrSupply");
		try {
			// 验证传入对象属性是否有误
			if (result.hasErrors()) {
				List<FieldError> list = result.getFieldErrors();
				StringBuilder builder = new StringBuilder();
				for (FieldError fieldError : list) {
					builder.append(fieldError.getDefaultMessage());
					builder.append("\n");
				}
				model.addObject(MSG, ERROR);
				model.addObject(ERR_MSG, builder.toString());
				return model;
			}
			mrSupply.setGrade(mrSupply.getGrade().toUpperCase());

			long count = this.mrSupplyService.isExistedByGrade(
					mrSupply.getGrade(), mrSupply.getDc().getId());
			if (null == mrSupply.getId()) {
				if (count > 0) {
					model.addObject(MSG, ERROR);
					model.addObject(ERR_MSG, "销售级别已经存在，不能再添加！");
					return model;
				}
				this.mrSupplyService.save(mrSupply);
			} else {
				if (count == 1) {
					String grade = this.mrSupplyService.findGradeById(mrSupply
							.getId());
					Long dcId = this.mrSupplyService.findDCById(mrSupply
							.getId());
					if (null != grade && grade.equals(mrSupply.getGrade())
							&& null != dcId
							&& dcId.equals(mrSupply.getDc().getId())) {
						this.mrSupplyService.update(mrSupply);
						model.addObject(MSG, SUCCESS);
						return model;
					} else {
						model.addObject(MSG, ERROR);
						model.addObject(ERR_MSG, "销售级别已经存在，不能修改！");
						return model;
					}
				} else {
					this.mrSupplyService.update(mrSupply);
					model.addObject(MSG, SUCCESS);
					return model;
				}
			}
			model.addObject(MSG, SUCCESS);
			return model;

		} catch (Exception e) {
			LOG.error(e.getMessage());
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, "修改失败！");
			return model;
		}
	}

	@RequestMapping(value = "/deleteSupply", method = RequestMethod.POST)
	public ModelAndView deleteSupply(@RequestParam("id") Long id) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/showMrSupply");
		try {
			MrSupply mrSupply = this.mrSupplyService.get(id);
			this.mrSupplyService.delete(mrSupply);
			model.addObject(MSG, SUCCESS);
			return model;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, "传入的参数有误！");
			return model;
		}
	}

	/****************************** 团购权重 -end **********************************/

	/****************************** 补货审核-start **********************************/

	@RequestMapping(value = "/productItemList")
	public ModelAndView listProductItem(
			@Valid ProductItemQueryForm queryForm,
			@MyInject Pagination pagination,
			@RequestParam(value = MSG, required = false, defaultValue = "") String msg) {

		ModelAndView view = new ModelAndView(
				"/replenishment/replenishment_examine");
		view.addObject("queryForm", queryForm);

		queryForm.setType(new Code(Code.MR_REPLENISH_TYPE_SYSTEM));
		Map<String, Object> parameters = handleParameters(queryForm);

		// 设置分页为200
		pagination.setPageSize(200);
		// 判断设置分页条件,默认为按补货数量的多少降序排列
		Short sort = 0;
		
		List<MrProductItem> items = new ArrayList<MrProductItem>();
		List<String> subMcCategorys = this.mrSubMcCategoryService.getAllSubMcCategory();
		if(parameters.get("subMcCategory") != null){
			String subMcCategory = parameters.get("subMcCategory").toString().replace("%", "");
			List<String> mcCategorys = this.mrSubMcCategoryService.getMcCategorys(subMcCategory);
			if(mcCategorys.size() != 0 && !mcCategorys.isEmpty()){
				parameters.put("mcCategorys", mcCategorys);
				items = this.itemService.findProductItems(parameters, sort, pagination);
			}
		}
		else{
			items = this.itemService.findProductItems(parameters, sort, pagination);
		}
		view.addObject("submccategorys", subMcCategorys);
		view.addObject("items", items);
		view.addObject("pagination", pagination);
		view.addObject(MSG, msg);
		return view;
	}

	@RequestMapping(value = "/updateProductItem")
	public ModelAndView updateProductItem(@RequestParam("id") Long id,
			@RequestParam("replenishmentQuantity") Integer replenishmentQuantity) {
		ModelAndView view = new ModelAndView(
				"/replenishment/replenishment_examine");
		try {
			if (null != replenishmentQuantity) {
				MrProductItem mrProductItem = this.itemService.get(id);
				mrProductItem.setReplenishmentQuantity(replenishmentQuantity);
				/**
				 * 在页面上修改补货数量后，可以直接保存
				 */
				this.itemService.update(mrProductItem);
				view.addObject(MSG, "succeed");
				return view;
			} else {
				view.addObject(MSG, ERROR);
				return view;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			view.addObject(MSG, ERROR);
			view.addObject(ERR_MSG, "传入的需要审核的商品条目id参数有误！");
			return view;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updateCheckStatus")
	public String updateCheckStatus(@RequestParam("ids") String ids,
			@Valid ProductItemQueryForm queryForm,
			@MyInject Pagination pagination, @MyInject Employee employee,
			@MyInject HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			String[] idStrings = StringUtils.split(ids, "\\|");
			/**
			 * num用于记录成功通过审核的补货数据记录的数量
			 */
			int num = 0;
			/**
			 * noAccess用于记录因为当前用户不具备审核权限所不通过审核的补货数据记录的数量
			 */
			int noAccess = 0;
			
			for (String string : idStrings) {
				long id = Long.parseLong(string);
				MrProductItem mrProductItem = this.itemService.get(id);	
				
				/**
				 * 考察当前页面用户的权限
				 */
				Long userId = employee.getId();
				MrExaminer mrExaminer = this.mrExaminerService.findExaminer(userId);
				
				/**
				 * 补货数量不大于0的商品不能通过审核
				 */
				if(mrProductItem.getReplenishmentQuantity() > 0){
					/**
					 * 根据单条记录的补货数量和总码洋判断用户是否能够成功审核
					 */
					if(checkAuthority(mrProductItem, mrExaminer)){ 
						this.itemService.updateCheckStatus(id, employee);
						num++;
					}else{
						noAccess++;
						continue;
					}
				}
			}
			response.getWriter().print("成功审核的商品数量为："+num
					+"，未成功审核的商品数量为："+(idStrings.length-num)+"！其中不具备审核权限的商品数量为："
					+noAccess);
			return null;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.getWriter().print("传入的需要审核的商品条目id参数有误！");
			return null;
		}
	}

	@RequestMapping(value = "/deleteProductItem", method = RequestMethod.POST)
	public ModelAndView deleteProductItem(@RequestParam("ids") String ids) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/productItemList");
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idStrings = StringUtils.split(ids, "\\|");
				for (String string : idStrings) {
					long id = Long.parseLong(string);
					MrProductItem mrProductItem = this.itemService.get(id);
					this.itemService.delete(mrProductItem);
				}

			} else {
				model.addObject(MSG, ERROR);
				model.addObject(ERR_MSG, "传入的参数有误！");
				return model;
			}
			model.addObject(MSG, SUCCESS);
			return model;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, "传入的参数有误！");
			return model;
		}
	}

	/**
	 * description 合并模糊查询的条件
	 * 
	 * @author wangbiao
	 * @date 2013-9-3 上午11:13:43
	 * 
	 * @param str
	 * @return
	 */
	private String mergeDimSearchCondtion(String condition) {
		StringBuilder sb = new StringBuilder();
		return sb.append("%").append(condition).append("%").toString();
	}

	/**
	 * description 处理查询参数
	 * 
	 * @param parameters
	 * @param key
	 * @param value
	 */
	private void handleMapParameters(Map<String, Object> parameters,
			String key, Object value) {
		// 处理字符串类型
		if (value instanceof String) {
			if (StringUtils.isNotBlank((String) value)) {
				parameters.put(key, mergeDimSearchCondtion((String) value));
			}
		} else {
			if (null != value) {
				parameters.put(key, value);
			}
		}
	}

	/**
	 * 
	 * 处理前获取前端传过来的销售分级的字符串，按照','和'，'处理
	 * 
	 * @param parameters
	 * @param key
	 * @param gradeString
	 */
	private void handleGradeParameters(Map<String, Object> parameters,
			String key, String gradeString) {
		if (StringUtils.isNotBlank(gradeString)) {
			if ((StringUtils.indexOf(gradeString, ',')) != -1) {
				String[] gradeArr = StringUtils.split(gradeString, ',');
				parameters.put(key, gradeArr);
			} else if ((StringUtils.indexOf(gradeString, "，")) != -1) {
				String[] gradeArr = StringUtils.split(gradeString, "，");
				parameters.put(key, gradeArr);
			} else {
				parameters.put(key, gradeString);
			}
		}
	}

	@RequestMapping(value = "/exportProductItem")
	public void exportProductItem(HttpServletRequest request,
			HttpServletResponse response,
			@Valid ProductItemQueryForm queryForm,
			@MyInject Pagination pagination) throws UnsupportedEncodingException{

		queryForm.setType(new Code(Code.MR_REPLENISH_TYPE_SYSTEM));
		Map<String, Object> parameters = handleParameters(queryForm);
		// 不需要分页信息
		this.itemService.exportProductItem(request, response, parameters, null);
	}
	
	/**
	 * 批量导入修改补货数量
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/productItemExcelImport", method = RequestMethod.POST)
	public ModelAndView productItemExcelImport(
			@RequestParam(value = "file", required = false) MultipartFile file) {
		String msg = SUCCESS;
		try {
			List<MrProductItem> datas = this.itemService.fetchData(file
					.getInputStream());
			LOG.info("datas total number :" + datas.size());
			this.itemService.updateAll(datas);
		} catch (Exception e) {
			msg = ERROR;
			LOG.info(e.getMessage());
		}
		return new ModelAndView(
				"redirect:/replenishment/productItemList?msg=" + msg);
	}

	// 处理补货审核查询的参数
	private Map<String, Object> handleParameters(ProductItemQueryForm queryForm) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		// 商品SAPid，商品名称，MC二次分类，供应商名称,DC模糊查询
		handleMapParameters(parameters, "outerId", queryForm.getOuterId());
		handleMapParameters(parameters, "productName",
				queryForm.getProductName());
		handleMapParameters(parameters, "subMcCategory",
				queryForm.getSubMcCategory());
		handleMapParameters(parameters, "productVendor",
				queryForm.getProductVendor());
		handleMapParameters(parameters, "dc", queryForm.getDc());

		// 码洋区间，库存区间，补货区间
		handleMapParameters(parameters, "basicPriceMin",
				queryForm.getBasicPriceMin());
		handleMapParameters(parameters, "basicPriceMax",
				queryForm.getBasicPriceMax());
		handleMapParameters(parameters, "availableQuantityMin",
				queryForm.getAvailableQuantityMin());
		handleMapParameters(parameters, "availableQuantityMax",
				queryForm.getAvailableQuantityMax());
		handleMapParameters(parameters, "repQuantityMin",
				queryForm.getRepQuantityMin());
		handleMapParameters(parameters, "repQuantityMax",
				queryForm.getRepQuantityMax());
		handleMapParameters(parameters, "repQuantityMax",
				queryForm.getRepQuantityMax());
		handleMapParameters(parameters, "type",
				queryForm.getType());
		handleMapParameters(parameters, "check", queryForm.getCheck());

		// 销售分级
		handleGradeParameters(parameters, "grades",
				StringUtils.trim(queryForm.getGrades()));
		// 处理创建时间区间
		try {
			if (StringUtils.isNotBlank(queryForm.getCreateStartTime())) {
				Date createStartTime = DateUtils.parserStringToDate(
						queryForm.getCreateStartTime(), DATE_FORMAT);
				parameters.put("createStartTime", createStartTime);
			}
			if (StringUtils.isNotBlank(queryForm.getCreateEndTime())) {
				Date createEndTime = DateUtils.parserStringToDate(
						queryForm.getCreateEndTime(), DATE_FORMAT);
				Date delayOneDayDate = DateUtils.addSecond(createEndTime,
						24 * 60 * 60);
				parameters.put("createEndTime", delayOneDayDate);
			}
		} catch (ParseException e) {
			LOG.error("传入的日期格式有误，不支持这种格式的解析！");
		}
		
		return parameters;
	}

	/****************************** 补货审核-end **********************************/

	/****************************** 周期参数 -start **********************************/

	/**
	 * 查询所有数据 周期参数页面
	 */
	@RequestMapping(value = "/showMrCycle")
	public ModelAndView showMrCycle(
			@MyInject Pagination pagination,
			@RequestParam(value = MSG, required = false, defaultValue = "") String msg,
			@RequestParam(value = ERR_MSG, required = false, defaultValue = "") String errMsg) {
		ModelAndView mav = new ModelAndView(
				"/replenishment/replenishment_mrcycle");
		mav.addObject("MrCycle", mrCycleService.getAll(pagination));// 获取所有列表
		mav.addObject("pagination", pagination);
		mav.addObject(MSG, msg);
		mav.addObject(ERR_MSG, errMsg);
		return mav;
	}

	@RequestMapping(value = "/mrCycleExcelImport", method = RequestMethod.POST)
	public ModelAndView mrCycleExcelImport(@MyInject Employee employee,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		ModelAndView mav = new ModelAndView(
				"redirect:/replenishment/showMrCycle");
		String msg = "success";
		String errMsg = "";
		try {
			mrCycleService.saveData(file.getInputStream());
		} catch (Exception e) {
			LOG.error(e.getMessage());
			errMsg = e.getMessage();
			msg = ERROR;
		}
		mav.addObject(MSG, msg);
		mav.addObject(ERR_MSG, errMsg);
		return mav;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteMrCycle", method = RequestMethod.POST)
	public ModelAndView deleteMrCycle(@RequestParam("cycleId") Long id) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/showMrCycle");
		MrCycle rCycle = this.mrCycleService.get(id);
		this.mrCycleService.delete(rCycle);
		model.addObject(MSG, SUCCESS);
		return model;
	}

	/**
	 * 修改保存
	 */
	@RequestMapping(value = "/saveMrCycle", method = RequestMethod.POST)
	public ModelAndView updateMrCycle(@Valid MrCycle mrCycle,
			BindingResult result) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/showMrCycle");
		MrCycle cycleTmp = this.mrCycleService.get(mrCycle.getId());
		cycleTmp.setReplenishmentCycle(mrCycle.getReplenishmentCycle());
		cycleTmp.setSaleCycle(mrCycle.getSaleCycle());
		cycleTmp.setTransitCycle(mrCycle.getTransitCycle());
		try {
			this.mrCycleService.update(cycleTmp);
			model.addObject(MSG, SUCCESS);
			return model;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, "修改后的参数有误！周期时间只能为合理的正整数！");
			return model;
		}
	}

	/**
	 * 按条件查找
	 */

	@RequestMapping(value = "/findMrCycle")
	public ModelAndView showMrCycleByMc(
			@MyInject String mc,
			@RequestParam(value = MSG, required = false, defaultValue = "") String msg) {
		ModelAndView mav = new ModelAndView(
				"/replenishment/replenishment_mrcycle");
		mav.addObject("MrCycle", mrCycleService.findByMC(mc));// 获取所有列表
		mav.addObject(MSG, msg);
		return mav;
	}

	/****************************** 周期参数 -end **********************************/
	/****************************** 模型选择 -start **********************************/

	/**
	 * 
	 * @param msg
	 * @param errMsg
	 * @return
	 */
	@RequestMapping(value = "/showMrModelChoose")
	public ModelAndView showMrModelChoose(
			@RequestParam(value = MSG, required = false, defaultValue = "") String msg,
			@RequestParam(value = ERR_MSG, required = false, defaultValue = "") String errMsg) {
		ModelAndView mav = new ModelAndView(
				"/replenishment/replenishment_modelchoose");
		mav.addObject("models", this.mrModelChooseService.getAll());
		mav.addObject(MSG, msg);
		mav.addObject(ERR_MSG, errMsg);
		return mav;
	}

	@RequestMapping(value = "/saveOrUpdateModel", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateModel(@Valid MrModelChoose mrModelChoose,
			BindingResult result) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/showMrModelChoose");
		try {
			// 验证传入对象属性是否有误
			if (result.hasErrors()) {
				List<FieldError> list = result.getFieldErrors();
				StringBuilder builder = new StringBuilder();
				for (FieldError fieldError : list) {
					builder.append(fieldError.getDefaultMessage());
					builder.append("\n");
				}
				model.addObject(MSG, ERROR);
				model.addObject(ERR_MSG, builder.toString());
				return model;
			}
			mrModelChoose.setGrade(mrModelChoose.getGrade().toUpperCase());
			long count = this.mrModelChooseService
					.isExistedByGrade(mrModelChoose);
			if (null == mrModelChoose.getId()) {
				if (count > 0) {
					model.addObject(MSG, ERROR);
					model.addObject(ERR_MSG, "销售级别已经存在，不能再添加！");
					return model;
				}
				this.mrModelChooseService.save(mrModelChoose);
			} else {
				if (count >= 1) {
					model.addObject(MSG, ERROR);
					model.addObject(ERR_MSG, "销售级别已经存在，不能修改！");
					return model;
				}
				this.mrModelChooseService.update(mrModelChoose);
			}
			model.addObject(MSG, SUCCESS);
			return model;

		} catch (Exception e) {
			LOG.error(e.getMessage());
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, e.getMessage());
			return model;
		}
	}

	@RequestMapping(value = "/deleteModel", method = RequestMethod.POST)
	public ModelAndView deleteModel(@RequestParam("id") Long id) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/showMrModelChoose");
		try {
			MrModelChoose mrModelChoose = this.mrModelChooseService.get(id);
			this.mrModelChooseService.delete(mrModelChoose);
			model.addObject(MSG, SUCCESS);
			return model;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, "传入的参数有误！");
			return model;
		}
	}

	/****************************** 模型选择 -end **********************************/

	/****************************** 限制补货-start **********************************/

	@RequestMapping(value = "/productFreezeExcelImport", method = RequestMethod.POST)
	public ModelAndView productFreezeExcelImport(
			@RequestParam(value = "file", required = false) MultipartFile file) {
		String msg = SUCCESS;
		try {
			List<MrProductFreeze> datas = this.freezeService.fetchData(file
					.getInputStream());
			LOG.info("datas total number :" + datas.size());
			this.freezeService.saveAll(datas);
		} catch (Exception e) {
			msg = ERROR;
			LOG.info(e.getMessage());
		}
		return new ModelAndView(
				"redirect:/replenishment/showMrProductFreeze?msg=" + msg);
	}

	@RequestMapping(value = "/showMrProductFreeze")
	public ModelAndView showMrProductFreeze(
			@MyInject Pagination pagination,
			@RequestParam(value = "searchProductSaleId", required = false, defaultValue = "") Long searchProductSaleId,
			@RequestParam(value = ERR_MSG, required = false, defaultValue = "") String errMsg,
			@RequestParam(value = MSG, required = false, defaultValue = "") String msg) {
		ModelAndView mav = new ModelAndView(
				"/replenishment/replenishment_limit");

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productSaleId", searchProductSaleId);
		// 人工限制补货
		parameters.put("type", Code.MR_FREEZE_RESTRICT);
		mav.addObject("freezes",
				this.freezeService.findFreezedProducts(parameters, pagination));// 获取所有列表
		mav.addObject("pagination", pagination);
		mav.addObject("searchProductSaleId", searchProductSaleId);
		mav.addObject(ERR_MSG, errMsg);
		mav.addObject(MSG, msg);
		return mav;
	}

	/**
	 * 
	 * @param productSaleId
	 *            :EC编码
	 * @param id
	 *            :冻结表的id
	 * @return
	 * @throws Exception  
	 */
	@RequestMapping(value = "/saveOrUpdateFreeze", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateFreeze(
			@RequestParam("productSaleId") Long productSaleId,
			@RequestParam("id") Long id) throws Exception {
			ModelAndView model = new ModelAndView(
				"redirect:/replenishment/showMrProductFreeze");
			try{
				MrProductFreeze mrProductFreeze = new MrProductFreeze();
//				mrProductFreeze.setId(id);
				ProductSale sale = new ProductSale(productSaleId);
				mrProductFreeze.setProductSale(sale);
				mrProductFreeze.setType(new Code(Code.MR_FREEZE_RESTRICT));
				mrProductFreeze.setReason(RESTRICT_REASON);
//				mrProductFreeze.setDc(new Code(Code.DC_8A17)); //限制补货没有分仓区分，默认8A17
				this.freezeService.saveOrUpdateRestrict(mrProductFreeze);
				model.addObject(MSG, SUCCESS);
				return model;
			}catch (Exception e) {
				LOG.error(e.getMessage());
				LOG.error(e);
				model.addObject(MSG, ERROR);
				model.addObject(ERR_MSG, "操作失败！");
				return model;
			}
	}

	@RequestMapping(value = "/deleteFreeze", method = RequestMethod.POST)
	public ModelAndView deleteFreeze(@RequestParam("ids") String ids) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/showMrProductFreeze");
		try {
			String[] idArr = StringUtils.split(ids, "\\|");
			for (String string : idArr) {		
				long id = Long.parseLong(string);
				MrProductFreeze mrProductFreeze = this.freezeService.get(id);		
				this.freezeService.delete(mrProductFreeze);
			}
			model.addObject(MSG, SUCCESS);
			return model;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, "传入的参数有误！");
			return model;
		}
	}

	/****************************** 限制补货 -end **********************************/
	/****************************** 人工冻结补货 -start **********************************/

	@RequestMapping(value = "/artificialProductFreezeExcelImport", method = RequestMethod.POST)
	// 批量上传人工冻结补货的商品信息，并返回给showMrArtificialProductFreeze页面
	public ModelAndView artificialProductFreezeExcelImport(
			@RequestParam(value = "file", required = false) MultipartFile file)
			throws ReplenishmentArtificialLimitException {
		String msg = SUCCESS;
		try {
			List<MrProductFreeze> datas = this.freezeService
					.fetchArtificialFreezeData(file.getInputStream());
			LOG.info("datas total number :" + datas.size());
			this.freezeService.saveAll(datas);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			msg = ERROR;
		}
		return new ModelAndView(
				"redirect:/replenishment/showMrArtificialProductFreeze?msg="
						+ msg);
	}

	// showMrArtificialProductFreeze页面展示
	@RequestMapping(value = "/showMrArtificialProductFreeze")
	public ModelAndView showMrArtificialProductFreeze(
			@MyInject Pagination pagination,

			@RequestParam(value = "selectProductSaleId", required = false, defaultValue = "") Long selectProductSaleId,

			@RequestParam(value = MSG, required = false, defaultValue = "") String msg) {
		ModelAndView mav = new ModelAndView(
				"/replenishment/replenishment_artificiallimit");

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("productSaleId", selectProductSaleId);
		parameters.put("type", Code.MR_FREEZE_MANUAL);
		mav.addObject("freezes",
				this.freezeService.findFreezedProducts(parameters, pagination));// 获取所有列表
		mav.addObject("pagination", pagination);

		mav.addObject("selectProductSaleId", selectProductSaleId);

		mav.addObject(MSG, msg);
		return mav;
	}

	@RequestMapping(value = "/deleteArtificialFreeze", method = RequestMethod.POST)
	public ModelAndView deleteArtificialFreeze(@RequestParam("ids") String ids) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/showMrArtificialProductFreeze");
		try {
			String[] idArr = StringUtils.split(ids, "\\|");
			for (String string : idArr) {
				long id = Long.parseLong(string);
				MrProductFreeze mrProductFreeze = this.freezeService.get(id);
				this.freezeService.delete(mrProductFreeze);
			}
			model.addObject(MSG, SUCCESS);
			return model;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, "传入的参数有误！");
			return model;
		}
	}

	@RequestMapping(value = "/saveOrUpdateFreezeV2", method = RequestMethod.POST)
	public ModelAndView saveOrUpdateFreeze(
			@RequestParam("productSaleId") Long productSaleId,
			@RequestParam("id") Long id,
			@RequestParam("endTime") String endTime, @RequestParam("dc") Long dc)
			throws ReplenishmentArtificialLimitException {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/showMrArtificialProductFreeze");
		try {
			MrProductFreeze mrProductFreeze = new MrProductFreeze();
			ProductSale sale = new ProductSale(productSaleId);
			mrProductFreeze.setProductSale(sale);

			if (StringUtils.isBlank(endTime)) {
				mrProductFreeze.setEndTime(null);
			} else {
				Date time = DateUtils.parserStringToDate(endTime, DATE_FORMAT);
				mrProductFreeze.setEndTime(time);
			}

			mrProductFreeze.setDc(new Code(dc));
			mrProductFreeze.setType(new Code(Code.MR_FREEZE_MANUAL));
			mrProductFreeze.setReason(MANUAL_REASON);
			this.freezeService.saveOrUpdateArtificial(mrProductFreeze);
			model.addObject(MSG, SUCCESS);
			return model;

		} catch (Exception e) {
			LOG.error(e.getMessage());
			LOG.error(e);
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, "操作失败！");
			return model;
		}

		/****************************** 人工冻结补货 -end **********************************/
	}
	    /****************************** 人工批量上传补货申请 -start ************************/
	
	@RequestMapping(value = "/artificialApplicationExcelImport", method = RequestMethod.POST)
	
	// 批量上传人工补货申请，并且返回到artificialApplicationList页面
	public ModelAndView artificialApplicationExcelImport(
			@RequestParam(value = "file", required = false) MultipartFile file){
		String msg = SUCCESS;
		try {
			List<MrProductItem> datas = this.itemService
					.fetchArtificialApplication(file.getInputStream());
			LOG.info("datas total number :" + datas.size());
			this.itemService.saveAll(datas);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			msg = ERROR;
		}
		return new ModelAndView(
				"redirect:/replenishment/artificialApplicationList?msg=" + msg);
	}
	
	@RequestMapping(value = "/artificialApplicationList")
	public ModelAndView listArtificialApplication(
			@Valid ProductItemQueryForm queryForm,
			@MyInject Pagination pagination,
			@RequestParam(value = MSG, required = false, defaultValue = "") String msg) {

		ModelAndView view = new ModelAndView(
				"/replenishment/replenishment_artificialapplication");
		view.addObject("queryForm", queryForm);
		queryForm.setType(new Code(Code.MR_REPLENISH_TYPE_MANUAL));
		Map<String, Object> parameters = handleArtificialParameters(queryForm);

		// 设置分页为200
		pagination.setPageSize(200);
		// 判断设置分页条件,默认为按补货数量的多少降序排列
		Short sort = 0;
		
		List<MrProductItem> items = new ArrayList<MrProductItem>();
		List<String> subMcCategorys = this.mrSubMcCategoryService.getAllSubMcCategory();
		
		if(parameters.get("subMcCategory") != null){
			String subMcCategory = parameters.get("subMcCategory").toString().replace("%", "");
			List<String> mcCategorys = this.mrSubMcCategoryService.getMcCategorys(subMcCategory);
			if (mcCategorys.size() != 0 && !mcCategorys.isEmpty()){
				parameters.put("mcCategorys", mcCategorys);
				items = this.itemService.findProductItems(parameters, sort, pagination);
			}
		}
		else{
			items = this.itemService.findProductItems(parameters, sort, pagination);
		}
		view.addObject("submccategorys", subMcCategorys);
		view.addObject("items", items);
		view.addObject("pagination", pagination);
		view.addObject(MSG, msg);
		return view;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateArtificialCheckStatus")
	public String updateArtificialCheckStatus(@RequestParam("ids") String ids,
			@Valid ProductItemQueryForm queryForm,
			@MyInject Pagination pagination, @MyInject Employee employee,
			@MyInject HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			String[] idStrings = StringUtils.split(ids, "\\|");
			/**
			 * num用于记录成功通过审核的记录的数量
			 */
			int num = 0;
			/**
			 * noAccess用于记录因为当前用户不具备审核权限所不通过审核的补货数据记录的数量
			 */
			int noAccess = 0;
			for (String string : idStrings) {
				long id = Long.parseLong(string);
				MrProductItem mrProductItem = this.itemService.get(id);			
				/**
				 * 考察当前页面用户的权限
				 */
				Long userId = employee.getId();
				MrExaminer mrExaminer = this.mrExaminerService.findExaminer(userId);

				/**
				 * 补货数量不大于0的商品不能通过审核
				 */
				if(mrProductItem.getReplenishmentQuantity() > 0){
					/**
					 * 根据单条记录的补货数量和总码洋判断用户是否能够成功审核
					 */
					if(checkAuthority(mrProductItem, mrExaminer)){ 
							this.itemService.updateCheckStatus(id, employee);
							num++;
					}else{
						noAccess++;
						continue;
					}
				}
			}
			response.getWriter().print("成功审核的商品数量为："+num
					+"，未成功审核的商品数量为："+(idStrings.length-num)+"！其中不具备审核权限的商品数量为："
					+noAccess);
			return null;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.getWriter().print("传入的需要审核的商品条目id参数有误！");
			return null;
		}
	}

	@RequestMapping(value = "/deleteArtificialApplication", method = RequestMethod.POST)
	public ModelAndView deleteArtificialApplication(@RequestParam("ids") String ids) {
		ModelAndView model = new ModelAndView(
				"redirect:/replenishment/artificialApplicationList");
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idStrings = StringUtils.split(ids, "\\|");
				for (String string : idStrings) {
					long id = Long.parseLong(string);
					MrProductItem mrProductItem = this.itemService.get(id);
					this.itemService.delete(mrProductItem);
				}

			} else {
				model.addObject(MSG, ERROR);
				model.addObject(ERR_MSG, "传入的参数有误！");
				return model;
			}
			model.addObject(MSG, SUCCESS);
			return model;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			model.addObject(MSG, ERROR);
			model.addObject(ERR_MSG, "传入的参数有误！");
			return model;
		}
	}
	
	private String mergeArtificialDimSearchCondtion(String condition) {
		StringBuilder sb = new StringBuilder();
		return sb.append("%").append(condition).append("%").toString();
	}
	
	/**
	 * description 处理查询参数
	 * 
	 * @param parameters
	 * @param key
	 * @param value
	 */
	private void handleArtificialMapParameters(Map<String, Object> parameters,
			String key, Object value) {
		// 处理字符串类型
		if (value instanceof String) {
			if (StringUtils.isNotBlank((String) value)) {
				parameters.put(key, mergeArtificialDimSearchCondtion((String) value));
			}
		} else {
			if (null != value) {
				parameters.put(key, value);
			}
		}
	}
	
	// 处理补货审核查询的参数
	private Map<String, Object> handleArtificialParameters(ProductItemQueryForm queryForm) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		// 商品SAPid，MC二次分类，DC模糊查询
		handleArtificialMapParameters(parameters, "outerId", queryForm.getOuterId());
		handleArtificialMapParameters(parameters, "subMcCategory", queryForm.getSubMcCategory()); 
		handleArtificialMapParameters(parameters, "dc", queryForm.getDc());

		// 补货区间
		handleArtificialMapParameters(parameters, "repQuantityMin",
				queryForm.getRepQuantityMin());
		handleArtificialMapParameters(parameters, "repQuantityMax",
				queryForm.getRepQuantityMax());
		handleArtificialMapParameters(parameters, "type",
				queryForm.getType());
		
		return parameters;
	}
	     /****************************** 人工批量上传补货申请 - end  ************************/
	
	/**
	 * 检查当前用户是否具备对单条补货记录的审核权限
	 */
	private boolean checkAuthority(MrProductItem mrProductItem, MrExaminer mrExaminer){
		
		boolean haveCheckAuthority = false;
		/**
		 * 对单条补货记录，计算其总码洋以考察当前员工是否能够成功审核
		 * 总码洋 = 补货数量 * 商品码洋
		 */
		ProductSale productSale = mrProductItem.getProductSale();
		Product product = productSale.getProduct();
		BigDecimal totalListPrice = new BigDecimal(
				(product.getListPrice().doubleValue()) 
				* mrProductItem.getReplenishmentQuantity());
		/**
		 * 对中心经理级别用户而言，只需满足一个条件即可
		 * 对部门经理级别和员工级别用户则需要同时满足两个条件
		 */
		if(mrExaminer.getBottomQuantity() >= 3000){
			haveCheckAuthority = true;
		}
		else if (mrExaminer.getBottomQuantity() >= 1000 && mrExaminer.getTopQuantity() <= 3000){
			if(mrProductItem.getReplenishmentQuantity() < mrExaminer.getTopQuantity()
					&& totalListPrice.doubleValue() < mrExaminer.getTopAmount().doubleValue()){
				haveCheckAuthority = true;
			}
		}
		else if(mrExaminer.getTopQuantity() <= 1000 && mrExaminer.getTopQuantity() <= 50000){
			if(mrProductItem.getReplenishmentQuantity() < mrExaminer.getTopQuantity()
					&& totalListPrice.doubleValue() < mrExaminer.getTopAmount().doubleValue()){
				haveCheckAuthority = true;
			}
		}
		return haveCheckAuthority;
	}
}