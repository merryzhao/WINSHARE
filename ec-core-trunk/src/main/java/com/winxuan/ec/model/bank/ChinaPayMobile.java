package com.winxuan.ec.model.bank;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ****************************** 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-6-27 下午2:27:25  --!
 * @description:没有实现,只是规范一下
 ********************************
 */
public class ChinaPayMobile extends Bank {
	
	private static final Log LOG = LogFactory.getLog(ChinaPayMobile.class);

	@Override
	protected void initTrade(String tradeId, BigDecimal amount) {
		LOG.warn("this's a not implements ChinaPayMobile");
	}

}
