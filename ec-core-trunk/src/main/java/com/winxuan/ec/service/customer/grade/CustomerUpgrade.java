package com.winxuan.ec.service.customer.grade;

import java.io.Serializable;

import com.winxuan.ec.model.user.Customer;

/**
 * 用户等级处理模板类（模板模式）
 * @author sunflower
 *
 */
public abstract class CustomerUpgrade implements Serializable{


	private static final long serialVersionUID = 4411923224534554310L;

	
	/**
	 * 用户升级
	 * @param customer
	 */
	public final void upgrade(Customer customer){
		
		if(canUpgradeToGoldByOrderPoints(customer)){
			 upgrade(Customer.GRADE_TYPE_GOLD,customer);
			 return;
		 }else if(canUpgradeToGoldByAccount(customer)){
			 upgrade(Customer.GRADE_TYPE_GOLD,customer);
			 return;
		 }else if(canUpgradeToSilverByOrderPoints(customer)){
			 upgrade(Customer.GRADE_TYPE_SILVER,customer);
			 return;
		 }else if(canUpgradeToSilverByAccount(customer)){
			 upgrade(Customer.GRADE_TYPE_SILVER,customer);
			 return;
		 }
	}
	
	/**
	 * 晋级
	 * @param grade
	 * @param customer
	 */
	protected abstract void upgrade(short grade,Customer customer);
	
	/**
	 * 单张订单满**升级为金卡
	 * @param customer
	 * @return
	 */
	protected abstract boolean canUpgradeToGoldByOrderPoints(Customer customer);

	/**
	 * 单张订单满**升级为银卡
	 * @param customer
	 * @return
	 */
	protected abstract boolean canUpgradeToSilverByOrderPoints(Customer customer);

	/**
	 * 积分满**升级为金卡
	 * @param customer
	 * @return
	 */
	protected abstract boolean canUpgradeToGoldByAccount(Customer customer);

	/**
	 * 积分满**升级为银卡
	 * @param customer
	 * @return
	 */
	protected abstract boolean canUpgradeToSilverByAccount(Customer customer);
}
