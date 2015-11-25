/*
 * @(#)PresentServiceImpl.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.present;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.PresentBatchDao;
import com.winxuan.ec.dao.PresentDao;
import com.winxuan.ec.dao.PresentExchangeDao;
import com.winxuan.ec.dao.PresentLogDao;
import com.winxuan.ec.exception.AttachException;
import com.winxuan.ec.exception.CustomerPointsException;
import com.winxuan.ec.exception.PresentException;
import com.winxuan.ec.exception.PresentExchangeException;
import com.winxuan.ec.model.channel.Channel;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.employee.EmployeeAttach;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.present.Present;
import com.winxuan.ec.model.present.PresentBatch;
import com.winxuan.ec.model.present.PresentExchange;
import com.winxuan.ec.model.present.PresentLog;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.shoppingcart.ShoppingcartItem;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.customer.CustomerService;
import com.winxuan.ec.service.employee.AttachService;
import com.winxuan.ec.service.employee.EmployeeService;
import com.winxuan.ec.service.mail.MailService;
import com.winxuan.ec.support.util.FileType;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.ec.util.Constant;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.pagination.Pagination;
import com.winxuan.framework.readwritesplitting.Read;
import com.winxuan.framework.util.RandomCodeUtils;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-8-30
 */
@Service("presentService")
@Transactional(rollbackFor = Exception.class)
public class PresentServiceImpl implements PresentService,Serializable {


	public static final Long PASS = 16002L;
	public static final Long UNPASS = 16003L;
	public static final Integer LENGTH = 6;

	private static final long serialVersionUID = 2076754853167677998L;
	private static final Log LOG = LogFactory.getLog(PresentServiceImpl.class);
	private static final String PRESENTEXPORTNAMEPRE = "present_batch_";
	//private static final String EMAILCODE = "{present.generalCode}";
	//private static final String EMAILUSER = "{user}";


	private static final String SUBJECT = "文轩网提醒：礼券已经发放";

	@InjectDao
	private PresentDao presentDao;
	@InjectDao
	private PresentLogDao presentLogDao;
	@InjectDao
	private PresentBatchDao presentBatchDao;
	@InjectDao
	private PresentExchangeDao presentExchangeDao;

	@Autowired
	private CodeService codeService;
	@Autowired
	private AttachService attachService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private MailService mailService;


	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Present get(Long id) {
		return presentDao.get(id);
	}

	public void update(Present present) {
		presentDao.update(present);
	}

	public PresentBatch getBatch(Long batchId) {
		return presentBatchDao.get(batchId);
	}

	public void createBatch(PresentBatch presentBatch, Employee operator) {
		presentBatch.setCreateUser(operator);
		checkBatch(presentBatch);
		presentBatchDao.save(presentBatch);
	}

	public void updateBatch(PresentBatch presentBatch) {
		checkBatch(presentBatch);
		presentBatchDao.update(presentBatch);
	}

	private void checkBatch(PresentBatch presentBatch){
		if(!presentBatch.isGeneral() && presentBatch.getNum() <=0){
			throw new RuntimeException("非通用券的数量必须大于0");
		}
	}

	@Override
	public void verifyBatch(PresentBatch presentBatch, Employee operator) {
		presentBatch.setAssessor(operator);
		presentBatch.setAssessTime(new Date());
		presentBatchDao.update(presentBatch);
		create(presentBatch);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Present> find(Map<String, Object> parameters,
			Pagination pagination) {
		return presentDao.find(parameters, pagination);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PresentBatch> findBatch(Map<String, Object> parameters,
			Pagination pagination) {
		return presentBatchDao.find(parameters, pagination,(short)2);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public long findNeedVerifyCount() {
		return presentBatchDao.getNeedVerifyCount();
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public long findPresentCount(PresentBatch presentBatch, Long state) {
		return presentDao.getCountByBatchAndState(presentBatch.getId(), state);
	}

	@Override
	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<BigDecimal> findValue() {
		return presentBatchDao.findValue();
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PresentBatch> findBatch(Map<String, Object> parameters) {
		return presentBatchDao.find(parameters);
	}

	/**
	 * 生成礼券的激活码
	 * @return
	 */
	private String generateCode(){
		String resultCode = RandomCodeUtils.create(RandomCodeUtils.MODE_LETTER_UPPERCASE_NUMBER, LENGTH);
		return presentDao.isExistedByCode(resultCode) > 0 ? generateCode() : resultCode;
	}

	/**
	 * 生成礼券
	 * 1.非通用券数量不能为0
	 * @return
	 */
	private void create(PresentBatch presentBatch) {
		if(presentBatch.getNum() >= 0){
			Present present = null;
			int forSize = presentBatch.isGeneral() ? MagicNumber.ONE : presentBatch.getNum();
			for(int i=0; i < forSize; i++){
				try {
					present = presentBatch.convertPresent();
					present.setCode(presentBatch.isGeneral() ? presentBatch.getGeneralCode() : generateCode());
					presentDao.save(present);
				} catch (ParseException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 更改礼券为分发状态
	 * @param present
	 * @param customer
	 */
	private void exportPresent(Present present , User operator){
		present.changeStatus(codeService.get(Code.PRESENT_STATUS_EXPORT),null,operator);
		presentDao.update(present);
	}

	/**
	 * 激活礼券
	 * @param present
	 * @param customer
	 * @param origin 来源
	 * @param originOrder 来源订单
	 * @throws PresentException
	 */
	private Present activePresent(Present present,Customer customer,Code origin,Order originOrder) throws PresentException{
		PresentBatch presentBatch = present.getBatch();
		if(presentBatch.getMaxQuantity()!=0){
			long currentQuantity = presentDao.getCount(presentBatch.getId(),customer.getId());
			if(currentQuantity>=presentBatch.getMaxQuantity()){
				throw new PresentException("当前批次礼券，最多领取"+presentBatch.getMaxQuantity()+"张");
			}
		}
		if(presentBatch.isGeneral()){
			if(presentBatch.getNum() > 0){
				long currentNum = presentDao.getCount(presentBatch.getId());
				currentNum = presentBatch.isGeneral() ? currentNum - 1 : currentNum;
				if(currentNum>=presentBatch.getNum()){
					throw new PresentException("生成礼券数量已超过限制!");
				}
			}
			try {
				present = presentBatch.convertPresent();
			} catch (ParseException e) {
				throw new PresentException(e);
			}
			present.setCode(presentBatch.getGeneralCode());
		}else{
			present.setEndDate(presentBatch.getEndDate());
		}
		present.setCustomer(customer);
		present.setOrigin(origin);
		present.setOriginOrder(originOrder);
		present.changeStatus(codeService.get(Code.PRESENT_STATUS_ACTIVE), null, customer);
		presentDao.saveOrUpdate(present);
		updateBatchActiveNum(presentBatch);
		return present;
	}


	public Present activePresent(String code,Customer customer) throws PresentException {
		Present present = presentDao.getGeneratePresentByCode(code);
		if(present == null){
			throw new PresentException("没有找到此礼券");
		}
		if (customer!= null&&customer.getChannel()!=null&&Channel.CHANNEL_AGENT.equals(customer.getChannel().getId())){
			throw new PresentException("代理用户不能激活礼券");
		}
		present = activePresent(present, customer,new Code(Code.PRESENT_ORIGIN_WXSEND),null);
		return present;
	}

	/**
	 * 更新礼券统计数量
	 * @param presentBatch
	 */
	private void updateBatchCreatedNum(PresentBatch presentBatch,int createdNum){
		presentBatch.setCreatedNum(createdNum);
		updateBatch(presentBatch);
	}

	private void updateBatchActiveNum(PresentBatch presentBatch){
		presentBatch.setActiveNum(presentBatch.getActiveNum()+MagicNumber.ONE);
		updateBatch(presentBatch);
	}

	private void updateBatchUsedNum(PresentBatch presentBatch){
		presentBatch.setUsedNum(presentBatch.getUsedNum()+MagicNumber.ONE);
		updateBatch(presentBatch);
	}

	private void updateBatchPayNum(PresentBatch presentBatch){
		presentBatch.setPayNum(presentBatch.getPayNum()+MagicNumber.ONE);
		updateBatch(presentBatch);
	}


	@Async
	public Future<String> generatePresent(PresentBatch presentBatch,Employee operator) throws PresentException {
		presentBatch = getBatch(presentBatch.getId());		//Async
		int currentPage = 0;
		List<String> presentList = null;
		List<Present> presentDataList = null;
		List<List<String>> presentExcelList = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", new Long[]{Code.PRESENT_STATUS_GENERATE});
		parameters.put("batch", presentBatch.getId());
		Pagination pagination = new Pagination(MagicNumber.FIVE_HUNDRED, currentPage);
		presentExcelList = new ArrayList<List<String>>();
		while((presentDataList = presentDao.find(parameters, pagination)) != null && !presentDataList.isEmpty()){	
			for (Present present : presentDataList) {
				presentList = new ArrayList<String>();
				presentList.add(presentBatch.getId().toString());
				presentList.add(presentBatch.getDescription());
				presentList.add(present.getCode());
				presentList.add(present.getValue().toString());
				presentList.add(present.getState().getName());
				presentList.add(dateFormat.format(present.getStartDate()));
				presentList.add(dateFormat.format(present.getEndDate()));
				presentList.add(presentBatch.getPresentEffectiveDay()==null ? "0":presentBatch.getPresentEffectiveDay().toString());
				presentList.add(presentBatch.getProductTypeName());
				presentList.add(presentBatch.getOrderBaseAmount().toString());
				presentExcelList.add(presentList);
			}
			pagination.setCurrentPage(++currentPage);
		}
		if(presentExcelList == null || presentExcelList.isEmpty()){
			throw new PresentException("没有礼券!");
		}
		EmployeeAttach employeeAttach = new EmployeeAttach();
		employeeAttach.setName(PRESENTEXPORTNAMEPRE+presentBatch.getId());
		employeeAttach.setType(FileType.XLS.toString());
		employeeAttach.setSort(codeService.get(Code.PRESENT_STATUS));
		employeeAttach.setEmployee(operator);
		try {
			List<String> presentExportHead = new ArrayList<String>();
			presentExportHead.add("批次编号");
			presentExportHead.add("批次描述");
			presentExportHead.add("激活码");
			presentExportHead.add("面值");
			presentExportHead.add("状态");
			presentExportHead.add("有效期（起）");
			presentExportHead.add("有效期（止）");
			presentExportHead.add("有效天数");
			presentExportHead.add("使用范围");
			presentExportHead.add("基准金额");
			LOG.info("导出礼券数量: "+presentExcelList.size());
			String path = attachService.addAttach(employeeAttach, presentExportHead, presentExcelList);
			return new AsyncResult<String>(path);
		} catch (AttachException e) {
			throw new PresentException("礼券导出错误" + e);
		}
	}

	public void sendPresent4Customer(PresentBatch presentBatch,List<Customer> customers, String emailTemplate) throws PresentException {
		sendPresent4Customer(presentBatch,customers,new Code(Code.PRESENT_ORIGIN_WXSEND),null);
	}

	@Override
	public void sendPresent4Customer(PresentBatch presentBatch,List<Customer> customers, Code origin,Order originOrder) throws PresentException{
		int needSize = customers.size();
		List<Present> presentDataList = null;
		if(presentBatch.getNum()>0 && presentBatch.getNum()-presentBatch.getCreatedNum() < needSize){
			if(originOrder == null && Code.PRESENT_ORIGIN_PROMOTION.equals(origin.getId())){
				throw new PresentException("请求数量超过可用数量");
			}else{
				return;
			}
		}
		int send = 0; 
		int currentPage = 0;
		int failCount = 0;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", new Long[]{Code.PRESENT_STATUS_GENERATE});
		parameters.put("batch", presentBatch.getId());
		Pagination pagination = new Pagination(MagicNumber.FIVE_HUNDRED, currentPage);
		readySend:
			while((presentDataList = presentDao.find(parameters, pagination)) != null && !presentDataList.isEmpty()){
				for (Present present : presentDataList) {
					if(send>=needSize){
						break readySend;
					}
					Customer customer = customers.get(send);
					send++;
					activePresent(present, customer,origin,originOrder);
					customer.getDisplayName();
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("present", present);
					model.put("mail", null);
					mailService.sendMail(customer.getEmail(), SUBJECT, Constant.MAIL_PRESENT_SENDED, model);
				}
				pagination.setCurrentPage(++currentPage);
			}
		updateBatchCreatedNum(presentBatch, presentBatch.getCreatedNum() + needSize-failCount);
	}

	public void sendPresent4Email(PresentBatch presentBatch,List<String> emails, String emailTemplate) throws PresentException {
		presentBatch = getBatch(presentBatch.getId());		//Async
		int needSize = emails.size();
		List<Present> presentDataList = null;
		if(presentBatch.getNum()>0 && presentBatch.getNum()-presentBatch.getCreatedNum() < needSize){
			throw new PresentException("请求数量超过可用数量");
		}
		int send = 0;
		int currentPage = 0;
		int failCount = 0;
		String mailAddress = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("state", new Long[]{Code.PRESENT_STATUS_GENERATE});
		parameters.put("batch", presentBatch.getId());
		Pagination pagination = new Pagination(MagicNumber.FIVE_HUNDRED, currentPage);
		readySend:
			while((presentDataList = presentDao.find(parameters, pagination)) != null && !presentDataList.isEmpty()){
				for (Present present : presentDataList) {
					if(send>=needSize){
						break readySend;
					}
					if(!presentBatch.isGeneral()){
						exportPresent(present,employeeService.get(Employee.SYSTEM));
					}
					mailAddress = emails.get(send);
					send++;
					Map<String, Object> model = new HashMap<String, Object>();
					present.getCustomer().getDisplayName();
					model.put("present", present);
					model.put("mail", mailAddress);
					mailService.sendMail(mailAddress, SUBJECT, Constant.MAIL_PRESENT_SENDED, model);
				}
				pagination.setCurrentPage(++currentPage);
			}
		updateBatchCreatedNum(presentBatch, presentBatch.getCreatedNum()+needSize-failCount);
	}

	public void cancelPresent(Present present, Employee operator) throws PresentException {
		Long stateId = present.getState().getId();
		if (stateId.equals(Code.PRESENT_STATUS_EXPORT) || stateId.equals(Code.PRESENT_STATUS_GENERATE)) {
			present.changeStatus(codeService.get(Code.PRESENT_STATUS_CANCEL), null, operator);
			presentDao.update(present);
		} else {
			throw new PresentException("礼券状态错误");
		}
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PresentLog> findPresentLogByOrderId(String orderId) {
		return presentLogDao.findByOrder(orderId);
	}


	public void usePresent(Present present, Order order, BigDecimal realPay)
	throws PresentException {
		Customer customer = order.getCustomer();
		if(!present.getState().getId().equals(Code.PRESENT_STATUS_ACTIVE)){
			throw new PresentException("礼券状态不正确!");
		}
		if(!customer.equals(present.getCustomer())){
			throw new PresentException("礼券不属于当前用户!");
		}
		Date now = new Date();
		if(!present.getStartDate().before(now) || present.getEndDate().before(now)){
			throw new PresentException("礼券已经超过有效期!");
		}
		if(realPay.compareTo(present.getValue()) > 0){
			throw new PresentException("支付金额不能超过礼券的有效金额!");
		}
		present.setRealPay(realPay);
		present.changeStatus(new Code(Code.PRESENT_STATUS_USED),order,employeeService.get(Employee.SYSTEM));
		presentDao.update(present);
		updateBatchUsedNum(present.getBatch());
	}


	public void reissuePresent(Present present,Order order) throws PresentException{
		Date now = new Date();
		Date endDate = present.getEndDate();
		Date nagDate = DateUtils.addDays(endDate, MagicNumber.TEN*MagicNumber.NEGATIVE_ONE);
		Date hopeDate = DateUtils.addDays(now, MagicNumber.TEN);
		if(nagDate.before(now)){
			present.setEndDate(hopeDate);
		}
		present.changeStatus(codeService.get(Code.PRESENT_STATUS_ACTIVE),order,employeeService.get(Employee.SYSTEM));
		presentDao.update(present);
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Present> findEffectivePresentByCustomer(Customer customer) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("customer", customer.getId());
		parameters.put("state", codeService.get(Code.PRESENT_STATUS_ACTIVE));
		parameters.put("startDate", new Date());
		parameters.put("endDate", new Date());
		List<Present> presents = presentDao.findPresnet(parameters);
		return presents;
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Present> findPresentByCustomer(Customer customer) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("customer", customer.getId());
		List<Present> presents = presentDao.findPresnet(parameters);
		return presents;
	}


	public void payPresent(Present present, Order order, BigDecimal realPay)
	throws PresentException {
		if(!present.getState().getId().equals(Code.PRESENT_STATUS_USED)){
			throw new PresentException("礼券状态不正确!");
		}
		PresentBatch presentBatch = present.getBatch();
		present.setRealPay(realPay);
		present.changeStatus(codeService.get(Code.PRESENT_STATUS_PAY),null,employeeService.get(Employee.SYSTEM));
		presentDao.update(present);
		updateBatchPayNum(presentBatch);
	}


	public void reissuePresent(Customer customer, BigDecimal value)
	throws PresentException {
		sendPresent(customer,value,PresentBatch.REUSSUE_PRESENT_ID);
	}

	/**
	 *  系统礼券批次分发礼券
	 * @param customer	分发用户
	 * @param value		分发金额
	 * @param presentbatch 特殊批次编号
	 * @return
	 * @throws PresentException
	 */
	private Present sendPresent(Customer customer, BigDecimal value,long presentbatch)
	throws PresentException{
		PresentBatch presentBatch = getBatch(presentbatch);
		Present present = null;
		try {
			present = presentBatch.convertPresent();
		} catch (ParseException e) {
			throw new PresentException(e);
		}
		present.setValue(value);
		present.setCode(generateCode());
		present.setCustomer(customer);
		present.changeStatus(codeService.get(Code.PRESENT_STATUS_ACTIVE), null, customer);
		present.setOrigin(new Code(Code.PRESENT_ORIGIN_POINTS));
		presentDao.saveOrUpdate(present);
		updateBatchCreatedNum(presentBatch,presentBatch.getCreatedNum()+1);
		return present;
	}

	@Override
	public List<Present> findByOrder(Order order) {
		if(order==null || order.getId() == null){
			return null;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("order", order.getId());
		return presentDao.findPresnet(params);
	}

	@Override
	public Present exchange(Customer customer, BigDecimal value, int points)
	throws PresentExchangeException, CustomerPointsException {
		if (!presentExchangeDao.isExisted(value, points)) {
			throw new PresentExchangeException();
		}
		customerService.subPointsByExchangePresent(customer, points);
		try {
			return sendPresent(customer, value, PresentBatch.EXCHANGE_PRESENT_ID);
		} catch (PresentException e) {
			throw new PresentExchangeException(e.getMessage());
		}
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PresentExchange> findExchange() {
		return presentExchangeDao.find();
	}

	@Read
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public PresentExchange findExchange(Long id) {
		return presentExchangeDao.find(id);
	}

	@Override
	public List<Present> findEffectivePresentByCustomerAndShoppingCart(
			Customer customer, List<ShoppingcartItem> shoppingcartItems) {
		List<Present> presents = findEffectivePresentByCustomer(customer);	//查询用户有效的礼券列表
		if(presents ==null || presents.isEmpty()){
			return null;
		}
		List<Present> effectivies = new ArrayList<Present>(presents.size());
		PresentBatch presentBatch = null;
		BigDecimal baseaMount = BigDecimal.ZERO;

		BigDecimal salePrice = BigDecimal.ZERO;
		BigDecimal bookPrice = BigDecimal.ZERO;
		BigDecimal videoPrice = BigDecimal.ZERO;
		BigDecimal mallPrice = BigDecimal.ZERO;
		List<Character> items = new ArrayList<Character>(MagicNumber.THREE);

		for(ShoppingcartItem shoppingcartItem : shoppingcartItems){
			Product product = shoppingcartItem.getProductSale().getProduct();
			BigDecimal effectivePrice = shoppingcartItem.getSalePrice();
			int quantity = shoppingcartItem.getQuantity();
			BigDecimal productPrice = effectivePrice.multiply(new BigDecimal(quantity));
			salePrice = salePrice.add(shoppingcartItem.getTotalSalePrice());
			if(product.getSort().getId().equals(Code.PRODUCT_SORT_BOOK)){
				bookPrice = bookPrice.add(productPrice);
				items.add('B');
			}else if(product.getSort().getId().equals(Code.PRODUCT_SORT_VIDEO)){
				videoPrice = videoPrice.add(productPrice);
				items.add('V');
			}else if(product.getSort().getId().equals(Code.PRODUCT_SORT_MERCHANDISE)){
				mallPrice = mallPrice.add(productPrice);
				items.add('G');
			}
		}

		for(Present p : presents){
			presentBatch = p.getBatch();
			baseaMount = presentBatch.getOrderBaseAmount();
			String currentSupportKind = presentBatch.getProductType();		//B：图书  V：音像  G：百货
			BigDecimal amount = BigDecimal.ZERO;
			boolean hasItem = false;
			for(char c : currentSupportKind.toCharArray()){
				if(c == 'B'){
					amount = amount.add(bookPrice);
				}else if(c == 'V'){
					amount =amount.add(videoPrice);
				}else if(c == 'G'){
					amount = amount.add(mallPrice);
				}
				if(!hasItem && items.contains(c)){		//商品项里包含了礼券支持的商品种类
					hasItem = true;
				}
			}
			if(amount.compareTo(baseaMount)>=0 && hasItem){
				effectivies.add(p);
			}
		}
		return effectivies;
	}

	@Override
	public long isExistedByCode(String code) {
		return presentDao.isExistedByCode(code);
	}


}
