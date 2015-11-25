package com.winxuan.ec.front.controller.customer;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.exception.CustomerCashApplyException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.customer.CustomerBankAccount;
import com.winxuan.ec.model.customer.CustomerCashApply;
import com.winxuan.ec.model.customer.CustomerPayee;
import com.winxuan.ec.model.customer.CustomerPostAccount;
import com.winxuan.ec.model.user.Customer;
import com.winxuan.ec.service.area.AreaService;

/** 
 * description
 * @author  zhoujun
 * @version 1.0,2011-10-26
 */
public class CashApplyForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 退款方式
	 */
	private Long type;
	
	/**
	 * 退款金额
	 */
	private BigDecimal refundMoney;
	
	/**
	 * 银行支付的银行
	 */
	private Long bank;
	
	/**
	 * 银行支付的市
	 */
	private Long bankCity;
	
	/**
	 * 银行支付的省
	 */
	private Long bankProvince;
	
	/**
	 * 银行支付的开户名
	 */
	private String bankAccountName;
	/**
	 * 银行支付的开户账号
	 */
	private String bankAccountCode;
	/**
	 * 银行支付的联系电话
	 */
	private String bankAccountContactPhone;
	
	/**
	 * 邮局汇款的收款人
	 */
	private String postAccountName;
	
	/**
	 * 邮局汇款的区域
	 */
	private Long postDistrict;
	
	/**
	 * 邮局汇款的市
	 */
	private Long postCity;
	/**
	 * 邮局汇款的省
	 */
	private Long postProvince;
	
	/**
	 * 邮局汇款的国家
	 */
	private Long postCountry;
	
	/**
	 * 邮局汇款的地址
	 */
	private String postAddress;
	
	/**
	 * 邮局汇款的邮编
	 */
	private String postCode;
	
	/**
	 * 邮局汇款的电话
	 */
	private String postContactPhone;
	/**
	 * 支付宝
	 */
	private String alipay;
	
	/**
	 * 支付宝退款时保存的姓名
	 */
	private String alipayname;
	
	/**
	 * 财付通
	 */
	private String tenpay;

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public BigDecimal getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(BigDecimal refundMoney) {
		this.refundMoney = refundMoney;
	}

	public Long getBank() {
		return bank;
	}

	public void setBank(Long bank) {
		this.bank = bank;
	}

	public Long getBankCity() {
		return bankCity;
	}

	public void setBankCity(Long bankCity) {
		this.bankCity = bankCity;
	}

	public Long getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(Long bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountCode() {
		return bankAccountCode;
	}

	public void setBankAccountCode(String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}

	public String getBankAccountContactPhone() {
		return bankAccountContactPhone;
	}

	public void setBankAccountContactPhone(String bankAccountContactPhone) {
		this.bankAccountContactPhone = bankAccountContactPhone;
	}

	public String getPostAccountName() {
		return postAccountName;
	}

	public void setPostAccountName(String postAccountName) {
		this.postAccountName = postAccountName;
	}

	public Long getPostDistrict() {
		return postDistrict;
	}

	public void setPostDistrict(Long postDistrict) {
		this.postDistrict = postDistrict;
	}
	
	

	public Long getPostCity() {
		return postCity;
	}

	public void setPostCity(Long postCity) {
		this.postCity = postCity;
	}

	public Long getPostProvince() {
		return postProvince;
	}

	public void setPostProvince(Long postProvince) {
		this.postProvince = postProvince;
	}

	public Long getPostCountry() {
		return postCountry;
	}

	public void setPostCountry(Long postCountry) {
		this.postCountry = postCountry;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostContactPhone() {
		return postContactPhone;
	}

	public void setPostContactPhone(String postContactPhone) {
		this.postContactPhone = postContactPhone;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}
	
	public String getAlipayname() {
		return alipayname;
	}

	public void setAlipayname(String alipayname) {
		this.alipayname = alipayname;
	}

	public String getTenpay() {
		return tenpay;
	}

	public void setTenpay(String tenpay) {
		this.tenpay = tenpay;
	}	
	
	public CustomerCashApply getCustomerCashApply(Customer customer,AreaService areaService,CustomerPayee customerPayee) throws CustomerCashApplyException {
		if(customerPayee == null){
			customerPayee = new CustomerPayee();
		}
		customerPayee.setCustomer(customer);	
		CustomerCashApply customerCashApply = new CustomerCashApply();
		if(refundMoney == null || refundMoney.compareTo(BigDecimal.ZERO) <= 0){
			throw new CustomerCashApplyException(customerCashApply,
					"申请退款金额必须大于0");
		}
		customerCashApply.setMoney(refundMoney);
		customerCashApply.setCustomer(customer);	
		generateCashApply(customerCashApply,customerPayee,areaService);
		customerCashApply.setType(new Code(type));
		return customerCashApply;
	}
	
	public void generateCashApply(CustomerCashApply customerCashApply,CustomerPayee customerPayee,AreaService areaService) throws CustomerCashApplyException{
		if (Code.CUSTOMER_CASH_TYPE_ALIPAY.equals(type)) {
			customerCashApply.setAlipay(alipay);
			customerCashApply.setAlipayName(alipayname);
			customerPayee.setAlipay(alipay);
			customerPayee.setAlipayName(alipayname);
		}else if (Code.CUSTOMER_CASH_TYPE_TENPAY.equals(type)) {
			customerCashApply.setTenpay(tenpay);
			customerPayee.setTenpay(tenpay);
		}else if (Code.CUSTOMER_CASH_TYPE_BANK.equals(type)) {	
			CustomerBankAccount bankAccount = getCustomerBankAccount(customerCashApply,areaService);
			customerCashApply.setBankAccount(bankAccount);
			customerPayee.setBankAccount(bankAccount);
		}else if(Code.CUSTOMER_CASH_TYPE_POST.equals(type)){
			CustomerPostAccount customerPostAccount = getCustomerPostAccount(customerCashApply,areaService);
			customerCashApply.setPostAccount(customerPostAccount);		
			customerPayee.setPostAccount(customerPostAccount);
		}else{
			 throw new CustomerCashApplyException(customerCashApply, "申请退款id为"
					+ customerCashApply.getId() + "没有对应支付类型");
		}		
	}
	public  CustomerBankAccount getCustomerBankAccount(CustomerCashApply customerCashApply,AreaService areaService)throws CustomerCashApplyException{
		if (StringUtils.isBlank(bankAccountName)
				|| StringUtils.isBlank(bankAccountCode)
				|| StringUtils.isBlank(bankAccountContactPhone)
				||bankCity == null||bankCity == -1
				||bankProvince == null||bankProvince==-1
				||bank==null) {
			throw new CustomerCashApplyException(customerCashApply,"缺 少 必填参数");
		}
		CustomerBankAccount customerBankAccount = new CustomerBankAccount();
		customerBankAccount.setBank(new Code(bank));
		customerBankAccount.setBankAccountCode(bankAccountCode);
		customerBankAccount.setBankAccountContactPhone(bankAccountContactPhone);
		customerBankAccount.setBankAccountName(bankAccountName);
		Area city = areaService.get(bankCity);
		Area province = areaService.get(bankProvince);
	    if(city == null || province == null){
	    	throw new CustomerCashApplyException(customerCashApply,"区域不正确");
	    }
		customerBankAccount.setBankCity(city);
		customerBankAccount.setBankProvince(province);
		return customerBankAccount;
	}
	public CustomerPostAccount getCustomerPostAccount(CustomerCashApply customerCashApply,AreaService areaService)throws CustomerCashApplyException{
		if(StringUtils.isBlank(postAccountName)||
				StringUtils.isBlank(postAddress)||
				StringUtils.isBlank(postCode)||
				postCity == null||postCity == -1||
				StringUtils.isBlank(postContactPhone)){
			throw new CustomerCashApplyException(customerCashApply,"缺少必 填参数");
		}
		CustomerPostAccount customerPostAccount = new CustomerPostAccount();
		customerPostAccount.setPostAccountName(postAccountName);
		customerPostAccount.setPostAddress(postAddress);
		customerPostAccount.setPostContactPhone(postContactPhone);
		customerPostAccount.setPostCode(postCode);
		if(postDistrict!=null&&postDistrict!=-1){
			 Area district = areaService.get(postDistrict);
			 if(district != null){ 
			   customerPostAccount.setPostDistrict(district);	
			 }
		}
			Area city =areaService.get(postCity);
			if(city == null){
				throw new CustomerCashApplyException(customerCashApply,"区域不正 确");
			}
			customerPostAccount.setPostCity(city);
			customerPostAccount.setPostProvince(city.getParent());
			customerPostAccount.setPostCountry(city.getParent().getParent().getParent());

		return customerPostAccount;
	}

}
