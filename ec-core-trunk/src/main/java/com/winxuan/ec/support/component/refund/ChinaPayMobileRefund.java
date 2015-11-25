package com.winxuan.ec.support.component.refund;

import javax.servlet.http.HttpServletRequest;

import com.winxuan.ec.exception.ChinaPayMobileRefundException;
import com.winxuan.ec.model.bank.ChinaPayMobile;
import com.winxuan.ec.model.refund.RefundCallBackForm;
import com.winxuan.ec.model.refund.RefundCredential;

/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-6-27 下午2:27:25  --!
 * @description:没有实现,只是规范一下
 ********************************
 */
public class ChinaPayMobileRefund extends Refunder<ChinaPayMobile>{

	@Override
	public String refund(RefundCredential refundCredential) throws ChinaPayMobileRefundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getRefundProcessStatus(String res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkCallBackUrl(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RefundCallBackForm getCallBackForm(
			HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RefundCredential refundFailed(RefundCredential refundCredential) {
		// TODO Auto-generated method stub
		return null;
	}


}
