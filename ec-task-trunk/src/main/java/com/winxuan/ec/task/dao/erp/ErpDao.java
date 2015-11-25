/*
 * @(#)ErpDao.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.task.dao.erp;

import java.util.List;

import com.winxuan.ec.task.model.erp.ErpArea;
import com.winxuan.ec.task.model.erp.ErpDeliveryArea;
import com.winxuan.ec.task.model.erp.ErpDeliveryCompany;
import com.winxuan.ec.task.model.erp.ErpOrder;
import com.winxuan.ec.task.model.erp.ErpOrderInvoice;
import com.winxuan.ec.task.model.erp.ErpOrderItem;
import com.winxuan.ec.task.model.erp.ErpProductStock;
import com.winxuan.ec.task.model.erp.ErpReturnOrder;

/**
 * description
 * @author  HideHai
 * @version 1.0,2011-11-25
 */
public interface ErpDao {


	
	//////////////////////////////////////////////////SQL////////////////////////////////////


	/**
	 * 查询订单是否在ERP做过销退
	 */
	 String FIND_ERP_CANCEL_ORDER = "SELECT count(*) FROM DDGL_KHBGDD_STOP WHERE KHDDID = ?";
	/**
	 * 查询订单是否在ERP是否销退成功
	 */
	 String FIND_ERP_CANCEL_SUCCESS = "SELECT count(*) FROM WMS_C_WLXTD_V WHERE KHDDH = ?";
	/**
	 * 通过ERP 退货单号查询EC退换货订单编号
	 */
	 String FIND_EC_RETURNORDER = "SELECT KHHHDDID ecReturnOrder,SMTHHDDID erpReturnOrder,KHDDH ecOrder FROM jkxx_ddgl_khdd_ddsmthh WHERE SMTHHDDID= ?";
	/**
	 * 更新ERP接口处理状态
	 */
	 String UPDATE_ERP_FLAG ="UPDATE jkxx_zd_zq_ec main SET main.MSGTY='S' WHERE main.KHDDID=? AND main.LZZTFLAG = ?";
	/**
	 * 更新ERP明细接口处理状态
	 */
	 String UPDATE_ERP_MX_FLAG ="UPDATE JKXX_ZDMX_ZQ_EC item SET item.MSGTY='S' WHERE item.KHDDID=?";
	/**
	 * 更新ERP明细接口处理状态
	 */
	 String UPDATE_ERP_YSDH_FLAG ="UPDATE JKXX_ZD_ZQ_EC_YSDH d SET d.MSGTY='S' WHERE d.KHDDID=?";
	/**
	 * 得到ERP订单项
	 */
	 String FIND_ORDER_STATE_ITEM = "select SPXXID commodity,FHSL quantity,BQSL outofstockquantity,KHDDH orderid from JKXX_ZDMX_ZQ_EC where KHDDID=?";
	
	/**
	 * 销售订单 - 接口主表 - jkxx_ddgl_khdd
	 */
	 String INSERT_ORDER = "INSERT INTO jkxx_ddgl_khdd ( "
		+ "khddid,khddh,shfsid,gkbh," + "gkmc,shcs,yzbm,shdz,"
		+ "dhsl,dhmy,dhsy,dhpzs, " + "gkyqshsj,sfkfp,fptt,fpnr,fpje,"
		+ "ysje,gklxdh,sphhsxid,gkfksxid," + "qdbz,qd,pqflid,sfyfyf, "
		+ "sygwqje,shfy,yfkje,hkje," +"bz,"+
		"ddlxid,dhfsid,thhfsid,qhsfkf,EDIDH,EDIBZ,"
		+ // 常量 （订单类型，订货方式，退货方式,缺货是否可发,EDI单号,EDI备注）
		"ccdid"+
		//仓储ID
		") VALUES (" + "?,?,?,?," + "?,?,?,?," + "?,?,?,?," + "?,?,?,?,?,"
		+ "?,?,?,?," + "?,?,?,?," + "?,?,?,?,"+"?,"
		+ "?,'1','-1',?,?,?,"
		+"?)";
	
	/** 
	 *  销售订单明细 - 接口主表 - jkxx_ddgl_khddmx  mall: 2802
	 */
	 String INSERT_ORDER_INTEM = "INSERT INTO  jkxx_ddgl_khddmx( "
		+ "ztid,khddmxid,"
		+ "khddh,khddid,shfsid,spxxid,"
		+ "spzlid,dj,xj,xz,"
		+ "shcs,yzbm,shdz,dhsl,"
		+ "dhmy,dhsy,gkfksxid,spzpsxid,"
		+ "mjid,ddlxid,thhfsid"
		+ // 常量:订单类型，退货方式
		") VALUES ("
		+ "'1',?,"
		+ "?,?,?,?,"
		+ "?,?,?,?," + "?,?,?,?," + "?,?,?,?," + "?,?,'-1' ) ";
	
	 /**
	 * 销售订单 - 发票信息表 - jkxx_fp
	 */
	 String INSERT_INVOICE = "INSERT INTO jkxx_fp  ( "
		+ "invoice_id,"
		+ "source_id,category_type,invoice_title,invoice_content,"
		+ "invoice_amount,invoice_number,invoice_total_daxie,invoice_total_xiaoxie,"
		+ "source_type,original_invoice_source,warehouse_id,zt,"
		+ // 常量: 类型,补开发票id，库存id，0：写入erp 1：erp取走
		"creation_date,last_changed_date,invoice_unit_price,is_recomputed,"
		+ // 常量:创建时间，更新时间，发票单价，0原始发票1修改发票
		"company_type"
		+ // 常量:公司ID
		") VALUES ("+"?,?,?,?,?," + "?,?,?,?,"
		+ "?,0,1,0," + "sysdate,sysdate,0,0," + "1) ";
	 
	 /**
	  * COD电子面单 - jkxx_khdd_dzmd
	  */
	 String INSERT_DANGDANG_COD = "INSERT INTO jkxx_khdd_dzmd ( "
		+ "KHDDH, OUTID, TYPE, MBC, FHC, LYC, CYS, SHSMSJ, FHSS, YSJE, SJBH, DGRQ, THC, CYSDQ,WAYBILLNUMBER,REMARK"
		+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TO_DATE(?,'yyyy-mm-dd hh24:mi:ss'), ?, ?,?,?)";
	
	 /**
	 * 插入退换货单
	 * 
	 */
	 String INSERT_RETURNORDER=
		"insert into jkxx_ddgl_khdd_ddsmthh(SMTHHDDID, KHDDID, KHDDH, KHHHDDID, KHHHDDH, GKTHFSID,THSL, THMY, THSY, JSZTFLAG" +
		") " +
		"values(?, ?,?,?,?, ?, ?, ?, ?, ?)";
	
	/**
	 * 插入退换货订单项
	 * 
	 */
	 String INSERT_RETURNORDER_ITEM=
		"insert into jkxx_ddgl_khddmx_ddsmthh(" +
		"SMTHHDDMXID, KHDDMXID, KHDDID, KHDDH, KHHHDDID, KHHHDDH, KHHHDDMXID, GKTHFSID, GKTHYYID, " +
		"THSL, THMY, THSY, THBZ, SPXXID, HHSL, HHMY, HHSY) " +
		"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'退货', ?, '0', '0', '0')"; 
	
	 String INSERT_CHARGEOFF_ORDER=
		"insert into jkxx_ddgl_khdd ( "	+ 
		"khddid, khddh, shfsid, gkbh, gkmc, shcs, yzbm, shdz, dhsl, dhmy, dhsy, dhpzs, "+ 
		"ysje, gklxdh, sphhsxid, qdbz, qd, pqflid, hkje, bz, ddlxid, dhfsid, thhfsid, GKYQSHSJ, SHFY, SYGWQJE, YFKJE, SFKFP, GKFKSXID, SFYFYF," +
		"ccdid) " + 
		"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '3', '1', ?, ?, ?, ?, '0', 'false', ?, ?," +
		"?) ";
	
	 String INSERT_CHARGEOFF_ORDER_ITEM=
		"insert into jkxx_ddgl_khddmx(" +
		"ztid, khddmxid, khddh, khddid, shfsid, spxxid, spzlid, dj, xj, xz, shcs, yzbm, shdz, dhsl, dhmy, dhsy, gkfksxid, spzpsxid, mjid, ddlxid, thhfsid" +
		") " +
		"values ('1',?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '3', ?)";
	/**
	 * 需要回传EC状态的订单
	 */
	 String FIND_ORDER_STATE = "select KHDDH orderId,LZZTFLAG state,YSDH code,CYSID deliveryCompany, DDLXID ddlxid,fhrq,gkthfsid,CCDID dc "
		+ "from JKXX_ZD_ZQ_EC where MSGTY is null and (" +
				"(LZZTFLAG in ('4','06','08','09','10','13','091') and ddlxid=1) " +
				"or (LZZTFLAG in ('4','091') and ddlxid=3)" +
				"or (LZZTFLAG in ('09','091') and ddlxid=7)) order by CDATE";
	
	 String FIND_ORDER_STATE_ONE = "select KHDDH orderId,LZZTFLAG state,YSDH code,CYSID deliveryCompany, DDLXID ddlxid,fhrq,gkthfsid "
		+ "from JKXX_ZD_ZQ_EC where MSGTY is null and KHDDID = ?";
	
	/**
	 * 根据订单号查询运单信息
	 */
	 String FIND_ORDER_DELIVERY = "SELECT KHDDH orderId,LZZTFLAG state,YSDH code,CYSID deliveryCompany, DDLXID ddlxid,fhrq,CCDID dc " +
			" FROM JKXX_ZD_ZQ_EC_YSDH WHERE KHDDID = ? ORDER BY FHRQ DESC";
	
	 String FIND_ORDER_DELIVERY_LIST = "SELECT DISTINCT(KHDDH) orderId FROM JKXX_ZD_ZQ_EC_YSDH WHERE MSGTY IS NULL AND ROWNUM  < 2000";
	
	/**
	 * 查询中启中该订单是否已经重量回填
	 */
	 String FIND_ERP_ZT_ORDER = "SELECT count(*) FROM ddgl_khbgdd_tz WHERE khddid = ? and fhrq<sysdate-2/24";
	
	/**
	 * 查询中启12小时内的拦截订单
	 */
	 String FIND_ERP_ORDER_REJECT ="SELECT * FROM DDGL_KHBGDD_STOP WHERE CDATE > \"TO_DATE\"(to_char(sysdate-1,'yyyy-mm-dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')";
	 
	
	 /**
	  * 统计需要进行增量更新的库存数据
	  */
	 String COUNT_ERP_PRODUCT_STOCK = "SELECT count(*) from JKXX_KCXX where ZT = 1";
     
	 /**
	  * 更新商品库存抓取状态
	  */
	 String UPDATE_ERP_PRODUCT_STATE = "update JKXX_KCXX set ZT =2,UDATE=TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') where SPXXID = ?";
	 
	 String NEED_EPRODUCT_STOCK = "select JK.SPXXID merchid,JK.KYKC stock,JK.CCDID dc,JK.CDATE cdate,JK.UDATE udate from (select JK.*,rownum rc from JKXX_KCXX JK where rownum<=?) JK where" +
	 		" JK.rc>? and JK.ZT=1 order by cdate asc";
	 
	 String FIND_ORDER_INVOICE_SQL = "select * from JKXX_BKFP_EC jbe where (jbe.MSGTY is null or jbe.MSGTY!='S') and jbe.KHDDH is not null and rownum<2000";
	 
	 String UPDATE_ORDER_INVOICE_STATE_SQL = "update JKXX_BKFP_EC jbe set jbe.MSGTY='S' where jbe.KHDDH=?";
	 
	 String FIND_AREA_SQL = "select * from (select jzp.*,rownum r from jkxx_zq2xhbs_pqwh jzp where rownum<=?) a where a.r>=?";
	 
	 String FIND_DELIVERY_AREA_SQL = "select * from (select jzcp.*,rownum r from jkxx_zq2xhbs_cys_pq jzcp where rownum<=?) a where a.r>=?";
	 
	 String FIND_DELIVERY_COMPANY_SQL = "select * from (select jzc.*,rownum r from jkxx_zq2xhbs_cysxx jzc where rownum<=?) a where a.r>=?";
	 
	 String UPDATE_AREA_STATE_SQL = "update jkxx_zq2xhbs_pqwh jzp set jzp.MSGTY='S' where jzp.id=? and jzp.type=? and jzp.zt=?";
	 
	 String UPDATE_DELIVERY_AREA_STATE_SQL = "update jkxx_zq2xhbs_cys_pq jzcp set jzcp.MSGTY='S' where jzcp.pqid=? and jzcp.cysid=? and jzcp.type=? and jzcp.mark=?";
	 
	 String UPDATE_DELIVERY_COMPANY_STATE_SQL = "update jkxx_zq2xhbs_cysxx jzc set jzc.MSGTY='S' where jzc.id=? and jzc.type=? and jzc.zt=?";
	 
	 String DELETE_AREA_SQL = "delete jkxx_zq2xhbs_pqwh jzp where jzp.MSGTY='S'";
	 
	 String DELETE_DELIVERY_AREA_SQL = "delete jkxx_zq2xhbs_cys_pq jzcp where jzcp.MSGTY='S'";
	 
	 String DELETE_DELIVERY_COMPANY_SQL = "delete jkxx_zq2xhbs_cysxx jzc where jzc.MSGTY='S'";
	 
	 /**
	  * 统计需要进行增量更新的库存数据
	  * @return
	  */
	 int countErpProductStock();
	 
	 /**
	  * 需要进行增量更新的数据
	  * @param pageSize 页尺寸
	  * @param pagination 页码 
	  * @return
	  */
	 List<ErpProductStock> needUpdateProductStock(int pageSize,int pagination);
	 
	 /**
	  * 需要进行更新状态的库存主数据
	  * @param merchid
	  */
	 void updateErpProductStockState(long merchid);
	 
	 
	 
	/**
	 * 判断销售订单是否在ERP做了销退
	 * 当中启变成发货/部分发货状态后
	 * 24小时过后未销退成功就更新为发货或者部分发货状态
	 */
	boolean erpDoCancel(ErpOrder erpOrder);
	
	/**
	 * 判断Erp订单是否销退成功
	 * @param erpOrder
	 * @return
	 */
	boolean erpCanceled(ErpOrder erpOrder);
	/**
	 *  获取ERP退换单
	 * @param erpReturnId
	 * @return
	 */
	ErpReturnOrder getByErpReturnOrderId(ErpOrder erpOrder);
	/**
	 *  ERP处理状态更新
	 *  作用为通知接口本条消息已经接受到
	 * @param id
	 */
	void updateErpProcessStatus(String id,String status);
	
	/**
	 * ERP运单号状态更新
	 * @param id
	 * @param status
	 */
	void updateErpDeliverytatus(String id,String status);
	/**
	 * 根据订单号查询订单状态详情
	 * 
	 * @param orderId
	 * @return
	 */
	List<ErpOrderItem> fetchOrderStateItem(String orderId);
	
	/**
	 * EC销售订单- 主订单
	 * @param order
	 */
	void sendOrder(Object[] params);
	
	/**
	 *  EC销售订单 - 主订单明细
	 * @param params
	 */
	void sendOrderItems(Object[] params);
	
	/**
	 *  EC销售订单 - 发票
	 * @param params
	 */
	void sendUserInovice(Object[] params);
	
	/**
	 *  EC补开发票 - 虚拟订单
	 * @param params
	 */
	void sendInoviceOrder(Object[] params);
	
	/**
	 *  EC补开发票 - 虚拟订单项
	 * @param params
	 */
	void sendInoviceOrderItem(Object[] params);
	
	/**
	 * COD - 电子面单
	 * 不止当当COD
	 * 所有渠道COD都可以
	 * @param params
	 */
	void sendDangDangCod(Object[] params);
	
	/**
	 * EC退货单 - ERP退换货单主表
	 * @param returnOrder
	 */
	void sendReturnOrder(Object[] params);
	
	/**
	 * EC退货单项 - ERP退换货单明细表
	 * @param returnOrder
	 */
	void sendReturnOrderItem(Object[] params);
	/**
	 * EC退货单 - ERP订单接口主表
	 * @param returnOrder
	 */
	void sendReturnOrder4Main(Object[] params);
	/**
	 * EC退货单 - ERP订单接口主表明细
	 * @param returnOrder
	 */
	void sendReturnOrderItem4Main(Object[] params);
	
	
	/**
	 *  获取ERP接口表的订单
	 * @return ErpOrder
	 */
	List<ErpOrder> fetchOrderState();
	
	/**
	 * 查找12小时内拦截订单
	 * @return
	 */
	List<ErpOrder> fetchOrderReject();
	
	List<ErpOrder> fetchOrderState(String orderId);
	
	/**
	 *  从运输单号表获取订单的运单信息
	 * @return
	 */
	List<ErpOrder> fetchOrderDelivery(String orderid); 
	
	/**
	 * 查询需要更新运单的订单号
	 * @return
	 */
	List<String> fetchDistinctOrderDelivery();
	
	boolean checkDelivery(String orderid);
	
	/**
	 * 查询需要回传的发票信息
	 */
	List<ErpOrderInvoice> fetchOrderInvoice();
	
	/**
	 * 更新已经回传的状态为“S”
	 */
	void updateOrderInvoiceStatus(String id);
	
	/**
	 * 查询需要同步到EC的区域信息
	 * 区域信息上传ec接口表
	 * @param pageNum 页码
	 */
	List<ErpArea> fetchArea(int pageNum);
	
	/**
	 * 更新已经回传的状态为“S”
	 */
	void updateAreaStatus(String id, String type, String zt);
	
	/**
	 * 删除已经回传的区域
	 */
	void deleteArea();
	
	/**
	 * 查询需要同步到EC的区域和配送公司关系
	 * 配送公司和区域关系上传ec接口表
	 * @param pageNum 页码
	 */
	List<ErpDeliveryArea> fetchDeliveryArea(int pageNum);
	
	/**
	 * 更新已经回传的状态为“S”
	 */
	void updateDeliveryAreaStatus(String pqid, String cysid, String type, String mark);
	
	/**
	 * 删除已经回传的配送公司和区域关系
	 */
	void deleteDeliveryArea();
	
	/**
	 * 查询需要同步到EC的配送公司
	 * 配送公司上传ec接口表
	 * @param pageNum 页码
	 */
	List<ErpDeliveryCompany> fetchDeliveryCompany(int pageNum);
	
	/**
	 * 更新已经回传的状态为“S”
	 */
	void updateDeliveryCompanyStatus(String id, String type, String zt);
	
	/**
	 * 删除已经回传的配送公司
	 */
	void deleteDeliveryCompany();
}

