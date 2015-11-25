/*
 * @(#)ShouJiZhiFuCallBackController.java
 *
 */

package com.winxuan.ec.front.controller.order.callback;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisun.iposm.HiiposmUtil;
import com.winxuan.ec.model.bank.BankConfig;
import com.winxuan.ec.model.bank.ShouJiZhiFu;
import com.winxuan.ec.model.order.CallBackForm;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * description
 * @author  huangyixiang
 * @version 2012-7-5
 */
@Controller
@RequestMapping(value="/order/onlinepay/shouJiZhiFu")
public class ShouJiZhiFuCallBackController extends OnlinePayCallBackController{
	
	private static final Log LOG = LogFactory.getLog(ShouJiZhiFuCallBackController.class);
	
	@RequestMapping(value = "/return")
	public ModelAndView returnCallBack(HttpServletRequest request){
		return payByReturn(request);
	}
	
	@RequestMapping(value = "/notify")
	@ResponseBody
	public String notifyCallBack(HttpServletRequest request) {
		if (payByNotify(request)) {
			return "SUCCESS";
		} else {
			return "FAIL";
		}
	}

	@Override
	protected boolean verify(HttpServletRequest request) throws Exception {
		//HiNotifyMessage hm = HiNotifyMessage.getNotifyMessage(request,signKey);
        // if use CA,use  HiNotifyMessage.getNotifyMessage(request);
        String merchantId = request.getParameter("merchantId");
        String payNo = request.getParameter("payNo");
        String returnCode = request.getParameter("returnCode");
        String message = request.getParameter("message");
        String signType = request.getParameter("signType");
        String type = request.getParameter("type");
        String version = request.getParameter("version");
        String amount = request.getParameter("amount");
        String amtItem = request.getParameter("amtItem");
        String bankAbbr = request.getParameter("bankAbbr");
        String mobile = request.getParameter("mobile");
        String orderId = request.getParameter("orderId");
        String payDate = request.getParameter("payDate");
        String reserved1 = request.getParameter("reserved1");
        String reserved2 = request.getParameter("reserved2");
        String status = request.getParameter("status");
        String orderDate = request.getParameter("orderDate");
        String fee = request.getParameter("fee");
        String hmac = request.getParameter("hmac");
        String accountDate = request.getParameter("accountDate");
        
        ShouJiZhiFu shouJiZhiFu = getBank(request);
        
        //必输字段 非空验证
        if (merchantId == null) {
           merchantId = "";
        }
        if (payNo == null) {
           payNo = "";
        }
        if (returnCode == null) {
           returnCode = "";
        }
        if (message == null) {
           message = "";
        }
        if (signType == null ) {
           signType = "MD5";
        }
        if (type == null) {
           type = "";
        }
        if (version == null) {
           version = "";
        }
        if (amount == null) {
           amount = "";
        }
        if (amtItem == null) {
           amtItem = "";
        }
        if (bankAbbr == null) {
           bankAbbr = "";
        }
        if (mobile == null) {
           mobile = "";
        }
        if (orderId == null) {
           orderId = "";
        }
        if (payDate == null) {
           payDate = "";
        }
        if (reserved1 == null) {
           reserved1 = "";
        }
        if (reserved2 == null) {
           reserved2 = "";
        }
        if (status == null) {
           status = "";
        }
        if (orderDate == null) {
           orderDate = "";
        }
        if (fee == null) {
           fee = "";
        }
        if (hmac == null) {
           hmac = "";
        }
        if (accountDate == null){
           accountDate = "";
        }
        //验签报文
        String signData = merchantId + payNo + returnCode + message + signType
              + type + version + amount + amtItem + bankAbbr + mobile 
              + orderId + payDate + accountDate + reserved1 + reserved2 + status
              + orderDate + fee;
        
        HiiposmUtil util = new HiiposmUtil();
        LOG.debug("验签报文："+signData+"<br/>");
        //验签
        boolean signFlag = util.MD5Verify(signData,hmac,shouJiZhiFu.getSignKey());


        if (signFlag) {//验签成功
           /*LOG.debug("商户编号:" + orderId);
           LOG.debug("支付金额:" + amount);
           LOG.debug("支付银行:" + bankAbbr);
           LOG.debug("支付人：" +  mobile);
           LOG.debug("支付时间：" + payDate);
           LOG.debug("保留字段1：" + URLDecoder.decode(reserved1, "UTF-8"));
           LOG.debug("保留字段2：" + URLDecoder.decode(reserved2, "UTF-8"));*/
           return true;
        } else {
           LOG.info("验签失败！");
           return false;
        }
		
	}

	@Override
	protected CallBackForm getCallBackForm(HttpServletRequest request) {
		CallBackForm callBackForm = new CallBackForm();
		ShouJiZhiFu shouJiZhiFu = getBank(request);
		callBackForm.setBatchId(request.getParameter("orderId"));
		callBackForm.setMoney(new BigDecimal(request.getParameter("amount")).divide(new BigDecimal(MagicNumber.HUNDRED)));
		callBackForm.setOuterTradeNo(request.getParameter("payNo"));
		callBackForm.setPaymentId(shouJiZhiFu.getId());
		return callBackForm;
	}
	
	private ShouJiZhiFu getBank(HttpServletRequest request){
		ShouJiZhiFu shouJiZhiFu = BankConfig.getSimpleBank(ShouJiZhiFu.class);
		return shouJiZhiFu;
	}

}
