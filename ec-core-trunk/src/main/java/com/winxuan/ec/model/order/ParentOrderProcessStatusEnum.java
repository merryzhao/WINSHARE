/**
 * 
 */
package com.winxuan.ec.model.order;

import java.util.Set;

import com.winxuan.ec.model.code.Code;

/**
 * description
 * @author guanxingjiang
 *
 *  @version 1.0 2014-12-1
 */
public enum ParentOrderProcessStatusEnum {
 NEW(Code.ORDER_PROCESS_STATUS_NEW,1),WAITING(Code.ORDER_PROCESS_STATUS_WAITING_PICKING,2),PICKING(Code.ORDER_PROCESS_STATUS_PICKING,3),
 DELIVERIED_SEG(Code.ORDER_PROCESS_STATUS_DELIVERIED_SEG,4), DELIVERIED(Code.ORDER_PROCESS_STATUS_DELIVERIED,5),
 COMPLETED(Code.ORDER_PROCESS_STATUS_COMPLETED,6),STOCK_CANCEL(Code.ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL,7),
 REJECTION_CANCEL(Code.ORDER_PROCESS_STATUS_REJECTION_CANCEL,8), ERP_CANCEL(Code.ORDER_PROCESS_STATUS_ERP_CANCEL,9),
 STYSTEM_CANCEL(Code.ORDER_PROCESS_STATUS_SYSTEM_CANCEL,10),CUSTOMER_CANCEL(Code.ORDER_PROCESS_STATUS_CUSTOMER_CANCEL,11),
 INTERCEPT(Code.ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT,12), ARCHIVE(Code.ORDER_PROCESS_STATUS_ARCHIVE,13);
 
 private Long code;
 private int index;
 
 private ParentOrderProcessStatusEnum(Long code,int index){
	 this.code = code;
	 this.index = index;
 }

public Long getCode() {
	return code;
}

public void setCode(Long code) {
	this.code = code;
}

public int getIndex() {
	return index;
}
public void setIndex(int index) {
	this.index = index;
}
	
public static Long getProcessStatusCode(int index){
	 for(ParentOrderProcessStatusEnum p :ParentOrderProcessStatusEnum.values()){
		 if(p.getIndex() == index){
			 return p.getCode();
		 }
	 }
	 return null;
}

public static int getProcessStatusIndex(Long code){
	 for(ParentOrderProcessStatusEnum p :ParentOrderProcessStatusEnum.values()){
		 if(p.getCode() == code){
			 return p.getIndex();
		 }
	 }
	 return ParentOrderProcessStatusEnum.ARCHIVE.getIndex();
}

public static Long getProcessStatusCode(Set<Order> orders){
	int index = getProcessStatusIndex(orders.iterator().next().getProcessStatus().getId());
	for(ParentOrderProcessStatusEnum p :ParentOrderProcessStatusEnum.values()){
		for(Order order:orders){
			if(p.getCode().equals(order.getProcessStatus().getId())){
				if(p.getIndex() < index){
					index = p.getIndex();
				}
			}
		}
	}
	if( index == ParentOrderProcessStatusEnum.COMPLETED.getIndex() || index == ParentOrderProcessStatusEnum.DELIVERIED.getIndex() ){
		for(Order order :orders){
			if (order.isCanceled()) {
				index = ParentOrderProcessStatusEnum.DELIVERIED_SEG.getIndex();
				break;
			}
		}
	}
	return getProcessStatusCode(index);
}
}
