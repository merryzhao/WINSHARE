package com.winxuan.ec.front.controller.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.order.OrderItem;
import com.winxuan.ec.model.returnorder.ReturnOrderItem;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.order.OrderService;

/**
 * description
 * @author  zhoujun
 * @version 1.0,2011-11-30
 */
public class ReturnOrderForms {

	private List<ReturnOrderForm> returnOrderForm;

	public List<ReturnOrderForm> getReturnOrderForm() {
		return returnOrderForm;
	}

	public void setReturnOrderForm(List<ReturnOrderForm> returnOrderForm) {
		this.returnOrderForm = returnOrderForm;
	}	
	public List<ReturnOrderItem> getReturnOrderItems(OrderService orderService,CodeService codeService){	
		if(returnOrderForm==null){
			return null;
		}	
		List<ReturnOrderItem> returnOrderItems = new ArrayList<ReturnOrderItem>();
			for(ReturnOrderForm form :returnOrderForm){
				ReturnOrderItem returnOrderItem = new ReturnOrderItem();
				OrderItem item = orderService.getOrderItem(form.getOrderItemId());
				returnOrderItem.setOrderItem(item);
				returnOrderItem.setAppQuantity(form.getQuantity());
				Long reasonId =form.getReason();
					if(reasonId!=null&&reasonId!=-1){
						Code reason =codeService.get(reasonId);
						if(reason!=null){
							returnOrderItem.setReason(reason);
						}
					}
				String remark =form.getRemark();
				if(remark!=null&&!StringUtils.isBlank(remark)){
					returnOrderItem.setRemark(remark);	
				}			
				returnOrderItems.add(returnOrderItem);	
			}
		return returnOrderItems;
	}
}
