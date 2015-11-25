/*
 * @(#)Code.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.winxuan.ec.model.tree.Tree;

/**  
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-15
 */
@Entity
@Table(name = "code")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Code extends Tree<Code> {
	/**
	 * 订单跟踪状态
	 */
	public static final Long ORDER_TRACKING_STATUS = 1358L;
	/**
	 * 很好
	 */
	public static final Long ORDER_RECEIVE_VERY_GOOD = 1381L;
	/**
	 *基本满意
	 */
	public static final Long ORDER_RECEIVE_GOOD = 1382L;
	/**
	 * 一般
	 */
	public static final Long ORDER_RECEIVE_GENERAL = 1383L;
	/**
	 * 很差
	 */
	public static final Long ORDER_RECEIVE_BAD = 1384L;
	/**
	 * 订单送货时间选项
	 */
	public static final Long DELIVERY_OPTION = 3433L;
	/**
	 * 工作日
	 */
	public static final Long DELIVERY_OPTION_WORK_DAY = 3434L;
	/**
	 * 周末及节假日
	 */
	public static final Long DELIVERY_OPTION_WEEKEND = 3435L;
	/**
	 * 不限
	 */
	public static final Long DELIVERY_OPTION_ANYTIME = 3436L;
	/**
	 * 缺货处理方式
	 */
	public static final Long OUT_OF_STOCK_OPTION = 3437L;
	/**
	 * 缺货取消
	 */
	public static final Long OUT_OF_STOCK_OPTION_CANCEL = 3438L;
	/**
	 * 发送有的商品
	 */
	public static final Long OUT_OF_STOCK_OPTION_SEND = 3439L;
	/**
	 * 渠道类型
	 */
	public static final Long CHANNEL_TYPE = 3440L;
	/**
	 * 综合有购物功能
	 */
	public static final Long CHANNEL_TYPE_NORMAL_SALE = 3441L;
	/**
	 * 综合无购物功能
	 */
	public static final Long CHANNEL_TYPE_NORMAL = 3442L;
	/**
	 * 专业有购物功能
	 */
	public static final Long CHANNEL_TYPE_PROFESSION_SALE = 3443L;
	/**
	 * 专业无购物功能
	 */
	public static final Long CHANNEL_TYPE_PROFESSION = 3444L;
	/**
	 * 联盟
	 */
	public static final Long CHANNEL_TYPE_UNION = 3445L;
	/**
	 * 其它
	 */
	public static final Long CHANNEL_TYPE_OTHER = 3446L;
	/**
	 * 订单未自动审核通过的原因
	 */
	public static final Long UN_AUDIT_REASON = 3447L;
	/**
	 * 发票类型
	 */
	public static final Long INVOICE_TYPE = 3452L;
	/**
	 * 发票类型-普通发票
	 */
	public static final Long INVOICE_TYPE_GENERAL = 3453L;
	/**
	 * 发票抬头类型
	 */
	public static final Long INVOICE_TITLE_TYPE = 3459L;
	/**
	 * 发票类型-个人
	 */
	public static final Long INVOICE_TITLE_TYPE_PERSONAL = 3460L;
	/**
	 * 发票类型-公司
	 */
	public static final Long INVOICE_TITLE_TYPE_COMPANY = 3461L;
	/**
	 * 发票状态-正常
	 */
	public static final Long INVOICE_STATUS_NORMAL = 3463L;
	/**
	 * 发票状态-作废
	 */
	public static final Long INVOICE_STATUS_DUE = 3464L;
	/**
	 * 订单支付状态
	 */
	public static final Long ORDER_PAY_STATUS = 4000L;
	/**
	 * 订单支付状态-完全支付
	 */
	public static final Long ORDER_PAY_STATUS_COMPLETED = 4001L;
	/**
	 * 订单支付状态-部分支付
	 */
	public static final Long ORDER_PAY_STATUS_PART = 4003L;
	/**
	 * 订单支付状态-未支付
	 */
	public static final Long ORDER_PAY_STATUS_NONE = 4002L;
	/**
	 * 订单支付类型
	 */
	public static final Long ORDER_PAY_TYPE = 5000L;
	/**
	 * 订单支付类型-先款后货
	 */
	public static final Long ORDER_PAY_TYPE_FIRST_PAY = 5001L;
	/**
	 * 订单支付类型-先货后款
	 */
	public static final Long ORDER_PAY_TYPE_FIRST_DELIVERY = 5002L;
	/**
	 * 储配方式
	 */
	public static final Long STORAGE_AND_DELIVERY_TYPE = 6000L;
	/**
	 * 储配方式-代储代发
	 */
	public static final Long STORAGE_AND_DELIVERY_TYPE_STORAGE_PLATFORM_DELIVERY_PLATFORM = 6001L;
	/**
	 * 储配方式-自储代发
	 */
	public static final Long STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_PLATFORM = 6002L;
	/**
	 * 储配方式-自储自发
	 */
	public static final Long STORAGE_AND_DELIVERY_TYPE_STORAGE_SELLER_DELIVERY_SELLER = 6003L;
	 /**
     * 电子书
     */
	public static final Long STORAGE_AND_DELIVERY_TYPE_EBOOK = 6004L;
	/**
	 * 订单销售方式
	 */
	public static final Long ORDER_SALE_TYPE = 7000L;
	/**
	 * 订单销售方式-普通
	 */
	public static final Long ORDER_SALE_TYPE_NORMAL = 7001L;
	/**
	 * 订单销售方式-预售
	 */
	public static final Long ORDER_SALE_TYPE_BOOKING = 7002L;
	/**
	 * 订单销售方式-快速分拨
	 */
	public static final Long ORDER_SALE_TYPE_RAPID = 7003L;
	/**
	 * 订单处理状态
	 */
	public static final Long ORDER_PROCESS_STATUS = 8000L;
	/**
	 * 订单处理状态-已提交
	 */
	public static final Long ORDER_PROCESS_STATUS_NEW = 8001L;
	/**
	 * 订单处理状态-等待配货
	 */
	public static final Long ORDER_PROCESS_STATUS_WAITING_PICKING = 8002L;
	/**
	 * 订单处理状态-正在配货
	 */
	public static final Long ORDER_PROCESS_STATUS_PICKING = 8003L;
	/**
	 * 订单处理状态-已发货
	 */
	public static final Long ORDER_PROCESS_STATUS_DELIVERIED = 8004L;
	/**
	 * 订单处理状态-交易完成
	 */
	public static final Long ORDER_PROCESS_STATUS_COMPLETED = 8005L;
	/**
	 * 订单处理状态-客户取消
	 */
	public static final Long ORDER_PROCESS_STATUS_CUSTOMER_CANCEL = 8006L;
	/**
	 * 订单处理状态-缺货取消
	 */
	public static final Long ORDER_PROCESS_STATUS_OUT_OF_STOCK_CANCEL = 8007L;
	/**
	 * 订单处理状态-系统取消
	 */
	public static final Long ORDER_PROCESS_STATUS_SYSTEM_CANCEL = 8008L;
	/**
	 * 订单处理状态-退货取消
	 */
	public static final Long ORDER_PROCESS_STATUS_REJECTION_CANCEL = 8009L;
	/**
	 * 订单处理状态-拦截取消(即infor系统没有上线前的中启取消)
	 */
	public static final Long ORDER_PROCESS_STATUS_ERP_CANCEL = 8010L;
	/**
	 * 订单处理状态-部分发货
	 */
	public static final Long ORDER_PROCESS_STATUS_DELIVERIED_SEG = 8011L;
	/**
	 * 订单处理状态-归档
	 */
	public static final Long ORDER_PROCESS_STATUS_ARCHIVE = 8012L;
	/**
	 * 订单处理状态-等待拦截
	 */
	public static final Long ORDER_PROCESS_STATUS_WAIT_ERP_INTERCEPT = 8013L;
	/**
	 * 订单处理状态-父订单取消
	 */
	public static final Long PARENT_ORDER_PROCESS_STATUS_CANCEL = 8014L;
	/**
	 * ERP订单状态
	 */
	public static final Long ERP_PROCESS_STATUS = 8500L;
	/**
	 * ERP订单状态-未下传
	 */
	public static final Long ERP_PROCESS_STATUS_INIT = 8501L;
	/**
	 * ERP订单状态-集货
	 */
	public static final Long ERP_PROCESS_STATUS_WAIT = 8502L;
	/**
	 * ERP订单状态-质检包装
	 */
	public static final Long ERP_PROCESS_STATUS_PACKING = 8503L;
	/**
	 * ERP订单状态-等待发运
	 */
	public static final Long ERP_PROCESS_STATUS_WAIT_DELIVERY = 8504L;
	/**
	 * ERP订单状态-全部已发
	 */
	public static final Long ERP_PROCESS_STATUS_DELIVERY_ALL = 8505L;
	/**
	 * ERP订单状态-妥投
	 */
	public static final Long ERP_PROCESS_STATUS_CONFIRM = 8506L;
	/**
	 * ERP订单状态-缺货取消
	 */
	public static final Long ERP_PROCESS_STATUS_OUT_OF_STOCK = 8507L;
	/**
	 * ERP订单状态-拒收取消
	 */
	public static final Long ERP_PROCESS_STATUS_REJECT = 8508L;
	/**
	 * ERP订单状态-客户取消
	 */
	public static final Long ERP_PROCESS_STATUS_CANCEL = 8509L;
	/**
	 * ERP订单状态-逾期支付取消
	 */
	public static final Long ERP_PROCESS_STATUS_OVERDUE = 8510L;
	/**
	 * ERP订单状态-无法确认取消
	 */
	public static final Long ERP_PROCESS_STATUS_UNRECIVE = 8511L;
	/**
	 * ERP订单状态-拦截订单
	 */
	public static final Long ERP_PROCESS_STATUS_CATCH = 8512L;
	/**
	 * 暂存款使用类型
	 */
	public static final Long CUSTOMER_ACCOUNT_USE_TYPE = 9000L;
	/**
	 * 暂存款使用类型-充值
	 */
	public static final Long CUSTOMER_ACCOUNT_USE_TYPE_CHARGE = 9001L;
	/**
	 * 暂存款使用类型-支付
	 */
	public static final Long CUSTOMER_ACCOUNT_USE_TYPE_PAY = 9002L;
	/**
	 * 暂存款使用类型-退回暂存款
	 */
	public static final Long CUSTOMER_ACCOUNT_USE_TYPE_REFUND = 9003L;
	/**
	 * 暂存款使用类型-申请提现
	 */
	public static final Long CUSTOMER_ACCOUNT_USE_TYPE_APPLY_TO_CASH = 9004L;
	/**
	 * 暂存款使用类型-取消申请提现
	 */
	public static final Long CUSTOMER_ACCOUNT_USE_TYPE_CANCEL_TO_CASH = 9005L;
	/**
	 * 暂存款使用类型-提现
	 */
	public static final Long CUSTOMER_ACCOUNT_USE_TYPE_CASH = 9006L;
	/**
	 * 用户登陆类型
	 */
	public static final Long USER_LOGIN_TYPE= 100000L;
	/**
	 * 网站登陆
	 */
	public static final Long USER_LOGIN_WEBSITE = 100001L;
	/**
	 * 物流中心
	 */
	public static final Long DELIVERY_CENTER = 110000L;
	/**
	 * SAP系统管理的北京物流中心
	 */
	public static final Long DC_D803 = 110001L;
	/**
	 * SAP系统管理的成都五块石物流中心
	 */
	public static final Long DC_D801 = 110002L; 	
	/**
	 * 成都青白江物流中心
	 */
	public static final Long DC_8A17 = 110003L;
	/**
	 * 北京物流中心
	 */
	public static final Long DC_D818 = 110004L;
	/**
	 * 北京分拨
	 */
	public static final Long DC_8A19 = 110005L;	
	/**
	 * 百货仓位.
	 */
	public static final Long DC_MALL = 110006L;
	
	/**	华东仓 **/
	public static final Long DC_D819 = 110007L;
	/**
	 * 支付方式类型
	 */
	public static final Long PAYMENT_TYPE = 10000L;
	/**
	 * 支付方式类型-线下支付
	 */
	public static final Long PAYMENT_TYPE_OFFLINE = 10001L;
	/**
	 * 支付方式类型-线上支付
	 */
	public static final Long PAYMENT_TYPE_ONLINE = 10002L;
	/**
	 * 支付方式类型-暂存款
	 */
	public static final Long PAYMENT_TYPE_ACCOUNT = 10003L;
	/**
	 * 支付方式类型-礼券礼品卡
	 */
	public static final Long PAYMENT_TYPE_OTHER = 10004L;
	/**
	 * 支付方式类型-渠道
	 */
	public static final Long PAYMENT_TYPE_CHANNEL = 10005L;
	/**
	 * 支付方式类型-先货后款
	 */
	public static final Long PAYMENT_TYPE_COD = 10006L;
	/**
	 * 商品种类
	 */
	public static final Long PRODUCT_SORT = 11000L;
	/**
	 * 商品种类 - 图书
	 */
	public static final Long PRODUCT_SORT_BOOK = 11001L;
	/**
	 * 商品种类 - 音像
	 */
	public static final Long PRODUCT_SORT_VIDEO = 11002L;
	/**
	 * 商品种类 -百货
	 */
	public static final Long PRODUCT_SORT_MERCHANDISE = 11003L;
	/**
	 * 字段类型
	 */
	public static final Long FIELD_TYPE = 12000L;
	/**
	 * 字段类型-数字
	 */
	public static final Long FIELD_TYPE_NUMBER = 12001L;
	/**
	 * 字段类型-字符
	 */
	public static final Long FIELD_TYPE_STRING = 12002L;
	/**
	 * 字段类型-日期
	 */
	public static final Long FIELD_TYPE_DATE = 12003L;
	/**
	 * 字段类型-枚举
	 */
	public static final Long FIELD_TYPE_ENUM = 12004L;
	
	/**
	 * 字段类型-文本
	 */
	public static final Long FIELD_TYPE_TEXT = 12005L;

	/**
	 * 商品销售状态
	 */
	public static final Long PRODUCT_SALE_STATUS = 13000L;
	
	/**
	 * 商品销售状态-下架
	 */
	public static final Long PRODUCT_SALE_STATUS_OFFSHELF = 13001L;
	
	/**
	 * 商品销售状态-上架
	 */
	public static final Long PRODUCT_SALE_STATUS_ONSHELF = 13002L;
	
	/**
	 * 商品销售状态-EC停用
	 */
	public static final Long PRODUCT_SALE_STATUS_EC_STOP = 13003L;
	
	/**
	 * 商品销售状态-SAP停用
	 */
	public static final Long PRODUCT_SALE_STATUS_SAP_STOP = 13004L;
	
	/**
	 * 商品销售状态-已删除
	 */
	public static final Long PRODUCT_SALE_STATUS_DELETED = 13005L;
	
	/**
	 * 商品供应类型
	 */
	public static final Long PRODUCT_SALE_SUPPLY_TYPE = 13100L;
	
	/**
	 * 商品供应类型-正常销售
	 */
	public static final Long PRODUCT_SALE_SUPPLY_TYPE_USUAL = 13101L;
	
	/**
	 * 商品供应类型-新品预售
	 */
	public static final Long PRODUCT_SALE_SUPPLY_TYPE_BOOKING = 13102L;
	
	/**
	 * 商品供应类型-订购
	 */
	public static final Long PRODUCT_SALE_SUPPLY_TYPE_ORDER = 13103L;
	
	/**
	 * 商品库存类型
	 */
	public static final Long PRODUCT_STOCK_TYPE = 13200L;
	
	/**
	 * 商品库存类型-实物库存
	 */
	public static final Long PRODUCT_STOCK_TYPE_ACTUAL = 13201L;
	
	/**
	 * 商品库存类型-虚拟库存
	 */
	public static final Long PRODUCT_STOCK_TYPE_VIRTUAL = 13202L;
	
	/**
	 * 运费收取方式
	 */
	public static final Long DELIVERY_FEE_TYPE = 14000L;
	
	
	/**
	 * 运费收取方式-固定
	 */
	public static final Long DELIVERY_FEE_FIXED = 14001L;
	
	/**
	 * 运费收取方式-比例
	 */
	public static final Long DELIVERY_FEE_PERCENT = 14002L;

	/**
	 * 发票开票方式
	 */
	public static final Long ORDER_INVOICE_MODE = 15000L;
	
	/**
	 * 发票开票方式-正常
	 */
	public static final Long ORDER_INVOICE_MODE_NORMAL = 15001L;

	/**
	 * 礼券批次状态
	 */
	public static final Long PRESENT_BATCH_STATUS = 16000L;
	
	/**
	 * 礼券批次状态-待审核
	 */
	public static final Long PRESENT_BATCH_STATUS_VERIFY  = 16001L;
	
	/**
	 * 礼券批次状态-审核通过
	 */
	public static final Long PRESENT_BATCH_STATUS_PASS = 16002L;
	
	/**
	 * 礼券批次状态-审核不通过
	 */
	public static final Long PRESENT_BATCH_STATUS_FAIL = 16003L;
	
	/**
	 * 礼劵状态
	 */
	public static final Long PRESENT_STATUS = 17000L;
	/**
	 * 礼劵状态：已生成
	 */
	public static final Long PRESENT_STATUS_GENERATE= 17001L;
	
	/**
	 * 礼劵状态：已分发
	 */
	public static final Long PRESENT_STATUS_EXPORT= 17002L;
	
	/**
	 * 礼劵状态：已激活
	 */
	public static final Long PRESENT_STATUS_ACTIVE = 17003L;
	
	/**
	 * 礼劵状态：已使用
	 */
	public static final Long PRESENT_STATUS_USED = 17004L;
	
	/**
	 * 礼劵状态：已支付
	 */
	public static final Long PRESENT_STATUS_PAY = 17005L;
	/**
	 * 礼劵状态：已作废
	 */
	public static final Long PRESENT_STATUS_CANCEL = 17006L;
	
	/**
	 * 礼券来源
	 */
	public static final Long PRESENT_ORIGIN = 17100L;
	
	/**
	 * 礼券来源-文轩网赠送
	 */
	public static final Long PRESENT_ORIGIN_WXSEND = 17101L;
	
	/**
	 * 礼券来源-积分兑换
	 */
	public static final Long PRESENT_ORIGIN_POINTS = 17102L;
	
	/**
	 * 礼券来源-满赠活动
	 */
	public static final Long PRESENT_ORIGIN_PROMOTION = 17103L;
	
	/**
	 * 礼品卡状态
	 */
	public static final Long PRESENT_CARD_STATUS = 18000L;
	
	/**
	 * 礼品卡状态-新建
	 */
	public static final Long PRESENT_CARD_STATUS_NEW = 18001L;
	
	/**
	 * 礼品卡状态-印刷
	 */
	public static final Long PRESENT_CARD_STATUS_PRINT = 18002L;
	
	/**
	 * 礼品卡状态-入库
	 */
	public static final Long PRESENT_CARD_STATUS_STORAGE = 18003L;
	
	/**
	 * 礼品卡状态-发送
	 */
	public static final Long PRESENT_CARD_STATUS_SENT = 18004L;
	
	/**
	 * 礼品卡状态-激活
	 */
	public static final Long PRESENT_CARD_STATUS_ACTIVATE = 18005L;
	
	/**
	 * 礼品卡状态-使用
	 */
	public static final Long PRESENT_CARD_STATUS_USE = 18006L;
	
	/**
	 * 礼品卡状态-注销
	 */
	public static final Long PRESENT_CARD_STATUS_OFF = 18007L;
	
	/**
	 * 礼品卡类型
	 */
	public static final Long PRESENT_CARD_TYPE = 19000L;
	
	/**
	 * 礼品卡类型-电子卡
	 */
	public static final Long PRESENT_CARD_TYPE_ELECTRONIC = 19001L;
	
	/**
	 * 礼品卡类型-实物卡
	 */
	public static final Long PRESENT_CARD_TYPE_PHYSICAL = 19002L;
	
	/**
	 * 促销类型
	 */
	public static final Long PROMOTION_TYPE = 20000L;
	
	/**
	 * 单品价格优惠活动
	 */
	public static final Long PROMOTION_TYPE_PRODUCT_AMOUNT = 20001L;
	
	/**
	 * 类别价格优惠活动
	 */
	public static final Long PROMOTION_TYPE_CATEGORY_AMOUNT = 20002L;
	
	/**
	 * 买商品赠商品活动
	 */
	public static final Long PROMOTION_TYPE_PRODUCT_SEND_PRODUCT = 20003L;
	
	/**
	 * 订单满省活动
	 */
	public static final Long PROMOTION_TYPE_ORDER_SAVE_AMOUNT = 20004L;
	
	/**
	 * 订单满送券活动
	 */
	public static final Long PROMOTION_TYPE_ORDER_SEND_PRESENT = 20005L;
	
	/**
	 * 订单减运费活动
	 */
	public static final Long PROMOTION_TYPE_ORDER_SAVE_DELIVERYFEE = 20006L;
	
	/**
	 * 买商品免运费
	 */
	public static final Long PROMOTION_TYPE_PRODUCT_SAVE_DELIVERYFEE = 20007L;
	
	/**
	 * 买部分商品满省
	 */
	public static final Long PROMOTION_TYPE_PRODUCT_SAVE_AMOUNT = 20008L;
	
	/**
	 * 注册送礼券
	 */
	public static final Long PROMOTION_TYPE_REGISTER_SEND_PRESENT = 20009L;
	
	/**
	 * 促销-订单满省、满赠促销方式
	 */
	public static final Long PROMOTION_ORDER_SAVEORSEND_TYPE = 21000L;
	
	/**
	 * 促销-订单满省、满赠促销方式-普通优惠
	 */
	public static final Long PROMOTION_ORDER_SAVEORSEND_TYPE_NORMAL = 21001L;
	
	/**
	 * 促销-订单满省、满赠促销方式-梯度优惠
	 */
	public static final Long PROMOTION_ORDER_SAVEORSEND_TYPE_GRADIENT = 21002L;
	
	/**
	 * 促销-商品类型
	 */
	public static final Long PROMOTION_PRODUCT_TYPE = 23000L;
	
	/**
	 * 促销-商品类型-主商品
	 */
	public static final Long PROMOTION_PRODUCT_TYPE_MAIN = 23001L;
	
	/**
	 * 促销-商品类型-赠品
	 */
	public static final Long PROMOTION_PRODUCT_TYPE_GIFT = 23002L;
	
	/**
	 * 订单退换货类型
	 */
	public static final Long RETURN_ORDER_TYPE = 24000L;

	/**
	 * 订单退换货类型 - 退货
	 */
	public static final Long RETURN_ORDER_TYPE_RETURN = 24001L;

	/**
	 * 订单退换货类型 - 换货
	 */
	public static final Long RETURN_ORDER_TYPE_REPLACE = 24002L;

	/**
	 * 订单退换货类型 - 补偿
	 */
	public static final Long RETURN_ORDER_TYPE_COMPENSATE = 24003L;
	
	/**
	 * 订单退换货类型 - 书款补偿 
	 */
	public static final Long RETURN_ORDER_TYPE_COMPENSATE_BOOK = 24004L;

	/**
	 * 订单退换货状态
	 */
	public static final Long RETURN_ORDER_STATUS = 25000L;

	/**
	 * 订单退换货状态 - 提交
	 */
	public static final Long RETURN_ORDER_STATUS_NEW = 25001L;

	/**
	 * 订单退换货状态 - 已审核
	 */
	public static final Long RETURN_ORDER_STATUS_AUDITED = 25002L;

	/**
	 * 订单退换货状态 - 正在退货
	 */
	public static final Long RETURN_ORDER_STATUS_RETURNING = 25003L;

	/**
	 * 订单退换货状态 - 实物入库
	 */
	public static final Long RETURN_ORDER_STATUS_RECEIVED = 25004L;

	/**
	 * 订单退换货状态 - 已退款
	 */
	public static final Long RETURN_ORDER_STATUS_REFUNDED = 25005L;
	
	/**
	 * 订单退换货状态 - 审核未通过
	 */
	public static final Long RETURN_ORDER_STATUS_CANCEL = 25006L;
	
	/**
	 * 订单退换货状态 - 已换货
	 */
	public static final Long RETURN_ORDER_STATUS_CHANGED = 25007L;

	/**
	 * 促销-分类折扣调整方式
	 */
	public static final Long PROMOTION_CATEGORY_DIS_TYPE = 26000L;
	
	/**
	 * 促销-分类折扣调整方式-统一折扣
	 */
	public static final Long PROMOTION_CATEGORY_DIS_TYPE_UNIFIED = 26001L;
	
	/**
	 * 促销-分类折扣调整方式-只下调折扣
	 */
	public static final Long PROMOTION_CATEGORY_DIS_TYPE_DOWN = 26002L;
	
	/**
	 * 促销-分类折扣调整方式-只上调折扣
	 */
	public static final Long PROMOTION_CATEGORY_DIS_TYPE_UP = 26003L;
	
	/**
	 * 促销-促销活动状态
	 */
	public static final Long PROMOTION_STATUS = 29000L;
	
	/**
	 * EC到ERP订单传输状态
	 */
	public static final Long EC2ERP_STATUS = 35000L;
	
	/**
	 * EC到ERP订单传输状态 - 未下传
	 */
	public static final Long EC2ERP_STATUS_NONE = 35001L;
	
	/**
	 * EC到ERP订单传输状态 - 新建订单下传
	 */
	public static final Long EC2ERP_STATUS_NEW = 35002L;
	
	/**
	 * EC到ERP订单传输状态 - 取消订单下传
	 */
	public static final Long EC2ERP_STATUS_CANCEL = 35003L;
	
	/**
	 * EC到SAP订单传输状态 - 未下传SAP
	 */
	public static final Long EC2SAP_STATUS_NONE = 35004L;
	
	/**
	 * EC到SAP订单传输状态 - 新建订单已下传SAP
	 */
	public static final Long EC2SAP_STATUS_NEW = 35005L;
	
	/**
	 * 促销-促销活动状态-已生成
	 */
	public static final Long PROMOTION_STATUS_CREATE = 29001L;
	
	/**
	 * 促销-促销活动状态-审核通过
	 */
	public static final Long PROMOTION_STATUS_PASS = 29002L;
	
	/**
	 * 促销-促销活动状态-审核未通过
	 */
	public static final Long PROMOTION_STATUS_FAIL = 29003L;
	
	/**
	 * 促销-促销活动状态-已停用
	 */
	public static final Long PROMOTION_STATUS_STOP = 29004L;
	
	/**
	 * 促销-促销活动状态-已过期
	 */
	public static final Long PROMOTION_STATUS_OVER = 29005L;
	
	/**
	 * 退换货方式
	 */
	public static final Long RETURN_ORDER_STYLE = 28000L;
	
	/**
	 * 退换货方式 - 个人邮寄
	 */
	public static final Long RETURN_ORDER_STYLE_PERSON = 28001L;
	
	/**
	 * 退换货方式 - 上门取货
	 */
	public static final Long RETURN_ORDER_STYLE_DOOR = 28002L;
	/**
	 * 退换货承担方
	 */
	public static final Long RETURN_ORDER_HOLDER = 27000L;
	
	/**
	 * 退换货承担方-客户
	 */
	public static final Long RETURN_ORDER_HOLDER_CUSTOMER = 27000L;
	
	/**
	 * 退换货承担方-文轩
	 */
	public static final Long RETURN_ORDER_HOLDER_WINXUAN = 27000L;
	
	/**
	 * 退换货原因
	 */
	public static final Long RETURN_ORDER_REASON = 30000L;
	
	/**
	 * 退换货原因-个人喜好
	 */
	public static final Long RETURN_ORDER_REASON_PERSONAL = 30001L;
	
	/**
	 * 退换货跟踪类型
	 */
	public static final Long RETURN_ORDER_TRACK_TYPE=31000L;
	
	/**
	 * 用户-居住状态
	 */
	public static final Long CUSTOMER_LIVING_STATUS = 32000L;

	/**
	 * 用户-职业/身份
	 */
	public static final Long CUSTOMER_CAREER = 33000L;

	/**
	 * 用户-收入水平
	 */
	public static final Long CUSTOMER_SALARY = 34000L;
	
	/**
	 * 卖家店铺状态
	 */
	public static final Long SHOP_STATE = 36000L;
	
	/**
	 * 卖家店铺状态-已生成
	 */
	public static final Long SHOP_STATE_CREATE = 36001L;
	/**
	 * 卖家店铺状态-已激活
	 */
	public static final Long SHOP_STATE_PASS = 36002L;
	/**
	 * 卖家店铺状态-搜索引擎屏蔽
	 */
	public static final Long SHOP_STATE_SEARCH_FAIL = 36003L;
	/**
	 * 卖家店铺状态-完全屏蔽
	 */
	public static final Long SHOP_STATE_FAIL = 36004L;
	/**
	 * 卖家店铺状态-已注销
	 */
	public static final Long SHOP_STATE_CANCEL = 36005L;
	/**
	 * 卖家商品审核状态
	 */
	public static final Long PRODUCT_AUDIT_STATUS = 37000L;
	/**
	 * 卖家商品审核状态-未审核
	 */
	public static final Long PRODUCT_AUDIT_STATUS_CREATE = 37001L;
	/**
	 * 卖家商品审核状态-审核通过
	 */
	public static final Long PRODUCT_AUDIT_STATUS_PASS = 37002L;
	/**
	 * 卖家商品审核状态-审核不通过
	 */
	public static final Long PRODUCT_AUDIT_STATUS_FAIL = 37003L;
	/**
	 * 卖家商品渠道销售申请类型
	 */
	public static final Long PRODUCT_CHANNEL_APP_STATUS = 38000L;
	/**
	 * 卖家商品渠道销售申请类型-加入渠道
	 */
	public static final Long PRODUCT_CHANNEL_APP_STATUS_ADD = 38001L;
	/**
	 * 卖家商品渠道销售申请类型-退出渠道
	 */
	public static final Long PRODUCT_CHANNEL_APP_STATUS_OUT = 38002L;
	
	/**
	 * 用户-已购商品排序
	 */
	public static final Long CUSTOMER_BOUGHT_COMPOSITOR = 38003L;
	/**
	 * 用户-按购买时间排序（从新到旧）
	 */
	public static final Long CUSTOMER_BOUGHT_BUY_TIME_DESC = 38004L;
	/**
	 * 用户-按购买时间排序（从旧到新）
	 */
	public static final Long CUSTOMER_BOUGHT_BUY_TIME_ASC = 38005L;
	/**
	 * 用户-按价格排序（从高到底）
	 */
	public static final Long CUSTOMER_BOUGHT_PRICE_DESC = 38006L;
	/**
	 * 用户-按价格排序（从底到高）
	 */
	public static final Long CUSTOMER_BOUGHT_PRICE_ASC = 38007L;
	/**
	 * 用户-按折扣排序（从多到少）
	 */
	public static final Long CUSTOMER_BOUGHT_DISCOUNT_DESC = 38008L;
	/**
	 * 用户-按折扣排序（从少到多）
	 */
	public static final Long CUSTOMER_BOUGHT_DISCOUNT_ASC = 38009L;
	/**
	 * 订单取消申请状态
	 */
	public static final Long ORDER_CANCEL_APPLAY_STATE = 39000L;
	/**
	 * 订单取消申请状态-提交
	 */
	public static final Long ORDER_CANCEL_APPLAY_STATE_NEW = 39001L;
	/**
	 * 订单取消申请状态-审核通过
	 */
	public static final Long ORDER_CANCEL_APPLAY_STATE_PASS = 39002L;
	/**
	 * 订单取消申请状态-审核不通过
	 */
	public static final Long ORDER_CANCEL_APPLAY_STATE_FALL = 39003L;
	
	/**
	 * 订单取消申请状态-已删除
	 */
	public static final Long ORDER_CANCEL_APPLAY_STATE_DELETE = 39004L;
	
	/**
	 * 用户来源
	 */
	public static final Long USER_SOURCE = 40000L;
	
	/**
	 * 用户来源-EC前台
	 */
	public static final Long USER_SOURCE_EC_FRONT = 40001L;
	/**
	 * 用户来源-9月网前台
	 */
	public static final Long USER_SOURCE_9YUE_FRONT = 40021L;
	/**
	 * 用户来源-EC后台
	 */
	public static final Long USER_SOURCE_EC_CONSOLE = 40002L;
	
	/**
	 * 用户来源-EC卖家
	 */
	public static final Long USER_SOURCE_EC_SELLER = 40003L;
	
	/**
	 * 用户来源-支付宝
	 */
	public static final Long USER_SOURCE_ALIPAY = 40004L;
	
	/**
	 * 用户来源-新浪
	 */
	public static final Long USER_SOURCE_SINA = 40005L;
	
	/**
	 * 用户来源-QQ
	 */
	public static final Long USER_SOURCE_QQ = 40006L;
	
	/**
	 * 用户来源-163
	 */
	public static final Long USER_SOURCE_163 = 40007L;
	
	/**
	 * 用户来源-开心网
	 */
	public static final Long USER_SOURCE_KAIXIN = 40008L;
	
	/**
	 * 用户来源-人人网
	 */
	public static final Long USER_SOURCE_RENREN = 40009L;
	
	/**
	 * 用户来源-淘宝
	 */
	public static final Long USER_SOURCE_TAOBAO = 40010L;
	
	/**
	 * 用户来源-拍拍
	 */
	public static final Long USER_SOURCE_PAIPAI = 40011L;
	
	/**
	 * 用户来源-红孩子
	 */
	public static final Long USER_SOURCE_REDBABY = 40012L;

	/**
	 * 用户来源-豆瓣
	 */
	public static final Long USER_SOURCE_DOUBAN = 40013L;
	
	/**
	 * 用户来源-qq彩贝
	 */
	public static final Long USER_SOURCE_QQ_CAIBEI = 40014L;

	/**
	 * 用户来源-卓越供货
	 */
	public static final Long USER_SOURCE_ZHUOYUE = 40015L;
	
	/**
	 * 用户来源-移动端
	 */
	public static final Long USER_SOURCE_MOBILE = 40016L;
	
	/**
	 * 用户来源-卓越
	 */
	public static final Long USER_SOURCE_AMAZON = 40017L;
	
	/**
	 * 用户来源-匿名用户
	 */
	public static final Long USER_SOURCE_ANONYMITY = 40100L;
	
	/**
	 * 用户来源—1号店
	 */
	public static final Long USER_SOURCE_YIHAODIAN = 40019L;
	
	/**
	 * 用户来源—苏宁
	 */
	public static final Long USER_SOURCE_SUNING = 40020L;
	
	/**
	 * 用户来源－国美库巴
	 */
	public static final Long USER_SOURCE_COO8 = 40021L;
	
	/**
	 * 用户来源－京东店铺
	 */
	public static final Long USER_SOURCE_360BUY = 40023L;
	
	/**
	 * 用户来源－当当店铺
	 */
	public static final Long USER_SOURCE_DANGDANG = 40024L;
	
	/**
	 * 用户来源－易讯
	 */
	public static final Long USER_SOURCE_YIXUN = 40025L;
	
	public static final Long USER_SOURCE_DANGDANG_JIT=40027L;
	
	//用户来源－孔夫子
	public static final Long USER_SOURCE_KONGFUZI = 40026L;
	
	// 用户来源 - 微购
	public static final Long USER_SOURCE_WEIGOU=40028L;
	
	//用户来源 - 苏宁易购
	public static final Long USER_SOURCE_SUNING_YIGOU=40029L;
	
	// 用户来源-微信小店
	public static final Long USER_SOURCE_WEIXIN=40030L;
	/**
	 *  用户来源-京东供货
	 */
	public static final Long USER_SOURCE_JINGDDONG=40031L;
	// 用户来源-专业渠道-当当教育店
	public static final Long USER_SOURCE_DANGDANG_JYZ=40032L;
	
	/**
	 *  用户来源-彰伟科技
	 */
	public static final Long USER_SOURCE_ZHANGWEI=40033L;
	/**
	 * 店铺等级
	 */
	public static final Long SHOP_GRADE = 41000L;
	/**
	 * 普通店铺
	 */
	public static final Long SHOP_GRADE_USUAL = 41001L;
	/**
	 * 高级店铺
	 */
	public static final Long SHOP_GRADE_SUPER = 41002L;
	
	/**
	 * 银行
	 */
	public static final Long BANK = 42000L;
	
	/**
	 * 客户暂存款提现类型
	 */
	public static final Long CUSTOMER_CASH_TYPE = 43000L;

	/**
	 * 客户暂存款提现类型-支付宝
	 */
	public static final Long CUSTOMER_CASH_TYPE_ALIPAY = 43001L;

	/**
	 * 客户暂存款提现类型-财付通
	 */
	public static final Long CUSTOMER_CASH_TYPE_TENPAY = 43002L;

	/**
	 * 客户暂存款提现类型-银行转账
	 */
	public static final Long CUSTOMER_CASH_TYPE_BANK = 43003L;

	/**
	 * 客户暂存款提现类型-邮局汇款
	 */
	public static final Long CUSTOMER_CASH_TYPE_POST = 43004L;

	/**
	 * 客户暂存款提现状态
	 */
	public static final Long CUSTOMER_CASH_STATUS = 44000L;

	/**
	 * 客户暂存款提现状态-待处理
	 */
	public static final Long CUSTOMER_CASH_STATUS_NEW = 44001L;
	
	/**
	 * 客户暂存款提现状态-处理中
	 */
	public static final Long CUSTOMER_CASH_STATUS_PROCESSING = 44002L;
	/**
	 * 客户暂存款提现状态-已退款
	 */
	public static final Long CUSTOMER_CASH_STATUS_SUCCESS = 44003L;

	/**
	 * 客户暂存款提现状态-已撤销
	 */
	public static final Long CUSTOMER_CASH_STATUS_CANCEL = 44004L;
	
	/**
	 * 客户积分类型
	 */
	public static final Long CUSTOMER_POINTS_TYPE = 45000L;

	/**
	 * 客户积分类型-订单奖励
	 */
	public static final Long CUSTOMER_POINTS_TYPE_ORDER_AWARD = 45001L;

	/**
	 * 客户积分类型-兑换礼券
	 */
	public static final Long CUSTOMER_POINTS_TYPE_PRESENT_EXCHANGE = 45002L;

	/**
	 * 客户积分类型-活动奖励
	 */
	public static final Long CUSTOMER_POINTS_TYPE_ACTIVITY_AWARD = 45003L;
	
	/**
	 * 客户积分类型-确认收货奖励
	 */
	public static final Long CUSTOMER_POINTS_TYPE_CONFIRM_ORDER_AWARD = 45004L;
	
	/**
	 * 客户积分类型-提交评论奖励
	 */
	public static final Long CUSTOMER_POINTS_TYPE_COMMENT_AWARD = 45005L;
	
	/**
	 * 客户积分类型-添加喜欢奖励
	 */
	public static final Long CUSTOMER_POINTS_TYPE_FAVORITE_AWARD = 45006L;
	
	/**
	 * 客户积分类型-添加收藏奖励
	 */
	public static final Long CUSTOMER_POINTS_TYPE_COLLECT_AWARD = 45007L;
	
	/**
	 * 客户积分类型-退货扣减积分
	 */
	public static final Long CUSTOMER_POINTS_TYPE_RETURN_ORDER = 45008L;
	
	/**
	 * 商品到货通知
	 */
	public static final Long ARRIVAL = 461003L;
	
	/**
	 * 商品降价通知
	 */
	public static final Long PRICE_REDUCE = 461004L;
	
	/**
	 * 店铺栏目类型
	 */
	public static final Long SHOP_COLUMN_TYPE=47000L;
	
	/**
	 * 店铺栏目类型-热销商品栏目
	 */
	public static final Long SHOP_COLUMN_TYPE_HOTSALE=47001L;
	
	/**
	 * 店铺栏目类型-促销商品栏目
	 */
	public static final Long SHOP_COLUMN_TYPE_PROMOTION=47002L;
	
	/**
	 * 店铺栏目类型-类别商品展示栏目
	 */
	public static final Long SHOP_COLUMN_TYPE_CATEGORY=47003L;
	
	/**
	 * 店铺栏目类型-专题展示栏目
	 */
	public static final Long SHOP_COLUMN_TYPE_IMG=47004L;
	
	
	/**
	 * 店铺首页-广告切换
	 */
	public static final Long SHOP_TOP_ADVERT = 47005L;
	
	/**
	 * 店铺客服类型
	 */
	public static final Long SHOP_SERVICE_TYPE=48000L;
	
	/**
	 * 店铺客服类型-qq
	 */
	public static final Long SHOP_SERVICE_TYPE_QQ=48001L;
	
	/**
	 * 店铺客服类型-msn
	 */
	public static final Long SHOP_SERVICE_TYPE_MSN=48002L;
	
	/**
	 * 店铺客服类型-旺旺号
	 */
	public static final Long SHOP_SERVICE_TYPE_WANGWANG=48003L;
	
	/**
	 * 店铺客服类型-电话
	 */
	public static final Long SHOP_SERVICE_TYPE_PHONE=48004L;
	
	/**
	 * 店铺客服类型-手机
	 */
	public static final Long SHOP_SERVICE_TYPE_MOBILE_PHONE=48005L;
	
	/**
	 * 订单确认类型
	 */
	public static final Long ORDER_CONFIRM_TYPE=49000L;
	
	/**
	 * 订单确认类型-系统自动确定
	 */
	public static final Long ORDER_CONFIRM_TYPE_SYSTEM_AUTO=49001L;
	
	/**
	 * 订单确认类型-手工确定
	 */
	public static final Long ORDER_CONFIRM_TYPE_PERSON=49002L;
	
	/**
	 * 操作类型
	 */
	public static final Long OPERATE_TYPE=50000L;
	
	/**
	 * 操作类型-添加
	 */
	public static final Long OPERATE_SAVE = 50001L;
	/**
     * 操作类型-修改
     */
	public static final Long OPERATE_UPDATE = 50002L;
	/**
     * 操作类型-删除
     */
	public static final Long OPERATE_DELETE = 50003L;
	
	/**
	 * 团购申请状态
	 */
	public static final Long GROUP_SHOPPING_STATUS = 60000L;
	/**
	 * 团购申请状态-审核中
	 */
	public static final Long GROUP_SHOPPING_STATUS_EXAMINE= 60001L;
	/**
	 * 团购申请状态-通过
	 */
	public static final Long GROUP_SHOPPING_STATUS_PASS = 60002L;
	/**
	 * 团购申请状态-作废
	 */
	public static final Long GROUP_SHOPPING_STATUS_NULLIFY = 60003L;
	
	/**
	 * 投诉与咨询处理状态
	 */
	public static final Long COMPLAIN_INFO_STATUS = 70000L;
	/**
	 * 投诉与咨询处理状态-处理中
	 */
	public static final Long COMPLAIN_INFO_STATUS_DISPOSE= 70001L;
	/**
	 * 投诉与咨询处理状态-处理完成
	 */
	public static final Long COMPLAIN_INFO_STATUS_FINISH = 70002L;
	
	/**
	 * 商品管理分级
	 */
	public static final Long PRODUCT_SALE_MANAGEGRADE = 71000L;
	/**
	 * 商品管理分级-Z重点商品
	 */
	public static final Long PRODUCT_SALE_MANAGEGRADE_Z = 71001L;
	/**
	 * 商品管理分级-R热点商品
	 */
	public static final Long PRODUCT_SALE_MANAGEGRADE_R = 71002L;
	/**
	 * 商品管理分级-J分时段热销商品
	 */
	public static final Long PRODUCT_SALE_MANAGEGRADE_J = 71003L;
	
	/**
	 * 渠道排行榜
	 */
	public static final Long CHANNEL_CATEGORY_TOP = 72000L;
	
	/**
	 * 渠道新品榜
	 */
	public static final Long CHANNEL_CATEGORY_TOP_NEW = 72001L;
	
	/**
	 * 渠道热销榜
	 */
	public static final Long CHANNEL_CATEGORY_TOP_HOTSALE = 72002L;
	
	/**
	 * 订单短信类型
	 */
	public static final Long SMS_ORDER_TYPE = 80000L;
	/**
	 * 订单短信整单缺货
	 */
	public static final Long SMS_ORDER_STOCKOUT = 80001L;
	/**
	 * 订单发货
	 */
	public static final Long SMS_ORDER_DELIVERY = 80002L;
	/**
	 * 订单退货
	 */
	public static final Long SMS_ORDER_RETURN = 80003L;
	/**
	 * 订单换货
	 */
	public static final Long SMS_ORDER_REPLACE = 80004L;
	/**
	 * 改派快递公司
	 */
	public static final Long SMS_ORDER_CHANGE_DELIVERYCOMPANY = 80005L;
	/**
	 * 订单部分发货
	 */
	public static final Long SMS_ORDER_PART_DELIVERY = 80006L;
	/**
	 * 订单是否分包发货
	 */
	public static final Long SMS_ORDER_SPLIT_DELIVERY = 80007L;
	/**
	 * 订单是否分仓发货
	 */
	public static final Long SMS_ORDER_MULTI_BIN = 80008L;
	/**
	 * 订单拆分通知短信
	 */
	public static final Long SMS_SPLIT_ORDER_MESSAGE = 80009L;
	/**
	 * 帐单处理状态
	 */
	public static final Long BILL_STATUS = 90000L;
	/**
	 * 未确认状态
	 */
	public static final Long BILL_UNCONFIRMED = 90001L;
	/**
	 * 已确认
	 */
	public static final Long BILL_CONFIRMED = 90002L;
	/**
	 * 已处理,下传SAP或其他
	 */
	public static final Long BILL_PROCESSED = 90003L;
	
	/**
	 * 帐单未分配完成 ,该状态只用bill
	 */
	public static final Long BILL_ALLOC_UNFINISHED = 90004L;
	
	/**
	 * 分配帐单锁定,该状态只用bill
	 */
	public static final Long BILL_LOCK= 90005L;
	
	/**
	 * 不下传SAP,起初数据
	 */
	public static final Long BILL_ORD_DATA = 90009L;
	
	/**
	 * 渠道销售
	 */
	public static final Long CHANNELSALES_STATUS = 91000L;
	/**
	 * 渠道销售-已上传
	 */
	public static final Long CHANNELSALES_UPLOADED = 91001L;
	
	/**
	 * 渠道销售-校验成功
	 */
	public static final Long CHANNELSALES_SUCESSFUL = 91002L;
	
	/**
	 * 渠道销售-校验失败
	 */
	public static final Long CHANNELSALES_FAILD = 91003L;
	
	/**
	 * 渠道销售- 准备下传到SAP - 商品销售记录
	 */
	public static final Long CHANNELSALES_SAP_PREPARE = 91004L;
	
	/**
	 * 渠道上传销售部分下传SAP
	 */
	public static final Long CHANNELSALES_UPLOAD_SAP_SUB_DONE = 91006L;
	
	/**
	 * 渠道上传销售完全下传SAP
	 */
	public static final Long CHANNELSALES_UPLOAD_SAP_DONE = 91007L;
	/**
	 * 渠道销售- 删除
	 */
	public static final Long CHANNELSALES_DELETEED = 91008L;
	/**
	 * 渠道销售-准备冲销
	 */
	public static final Long CHANNELSALES_ROLLBACK_PREPARE = 91009L;
	/**
	 * 渠道销售-冲销完成, 未下传SAP的冲销
	 */
	public static final Long CHANNELSALES_ROLLBACK_DOWE = 91010L;
	/**
	 * 渠道销售-冲销完成, 下传到SAP的冲销
	 */
	public static final Long CHANNELSALES_ROLLBACK_DOWE_SAP = 91011L;
	/**
	 * 渠道商品关系导入方式
	 */
	public static final Long CHANNELPRODUCT_REL_TYPE = 92000L;
	/**
	 * 渠道商品关系导入方式-系统导入
	 */
	public static final Long CHANNELPRODUCT_REL_SYSTEM = 92001L;
	/**
	 * 渠道商品关系导入方式-人工导入
	 */
	public static final Long CHANNELPRODUCT_REL_HAND = 92002L;
	/**
	 * 词典来源
	 */
	public static final Long DICTIONARY_SOURCE = 461005L;
	/**
	 * 词典来源_人工录入
	 */
	public static final Long DICTIONARY_SOURCE_PERSON = 461006L;
	/**
	 * 词典来源_sina抓取
	 */
	public static final Long DICTIONARY_SOURCE_SINA = 461007L;
	
	/**
	 * 词典来源_baidu抓取
	 */
	public static final Long DICTIONARY_SOURCE_BAIDU = 461008L;
	/**
	 * 词典来源_当当抓取
	 */
	public static final Long DICTIONARY_SOURCE_DANGDANG = 461009L;
	/**
	 * 词典来源_文轩网抓取
	 */
	public static final Long DICTIONARY_SOURCE_WINXUAN = 461010L;
	/**
	 * 退货数据未上架
	 */
	public static final Long RETURN_ONSHELF_NO = 25101L;
	/**
	 * 退货数据已上架
	 */
	public static final Long RETURN_ONSHELF_YES = 25102L;

    /**
     * META信息开放性
     */
    public static final Long PRODUCT_META_OPEN = 82000L;
    /**
     * 通用META
     */
    public static final Long PRODUCT_META_OPEN_GENERAL = 82001L;
    /**
     * 电子书私有
     */
    public static final Long PRODUCT_META_OPEN_EBOOK = 82002L;

    /**
     * 商品合并任务状态
     */
    public static final Long PRODUCT_MERTE_STATUS = 83000L;
    /**
     * 未合并
     */
    public static final Long PRODUCT_MERTE_STATUS_NONE = 83001L;
    /**
     * 已处理未合并
     */
    public static final Long PRODUCT_MERTE_STATUS_NOT_MERGE = 83002L;

    /**
     * 已合并
     */
    public static final Long PRODUCT_MERTE_STATUS_MERGE = 83003L;
    
    /**
     * 商品合并任务状态
     */
    public static final Long PRODUCT_IMAGE_ZOOM = 84000L;
    /**
     * 未合并
     */
    public static final Long PRODUCT_IMAGE_ZOOM_NONE = 84001L;
    /**
     * 已合并
     */
    public static final Long PRODUCT_IMAGE_ZOOM_OK = 84002L;
    /**
     * 出错了
     */
    public static final Long PRODUCT_IMAGE_ZOOM_ERR = 84003L;    
    /**
     * 订单广播项
     */
    public static final Long ORDER_BROADCAST = 79000L;    
    /**
     * 广播项-ec状态修改
     */
    public static final Long ORDER_BROADCAST_PROCESS = 79001L;    
    /**
     * 广播项-收货地址修改
     */
    public static final Long ORDER_BROADCAST_ADDRESS = 79002L;
    
    /**
     * 广播项-未知的修改
     */
    public static final Long ORDER_BROADCAST_UNKNOW = 79009L;
    /**
     * 发货类型（补货模型）
     */
    public static final Long MR_DELIVERY = 470000L;    
    /**
     * 发货类型（补货模型）-团购
     */
    public static final Long MR_TUANGOU = 470001L;    
    /**
     * 发货类型（补货模型）-零售
     */
    public static final Long MR_LINGSHOW = 470002L;    
    /**
     * 补货模型算法
     */
    public static final Long MR_MODE = 480000L;    
    /**
     * 定位表
     */
    public static final Long MR_FIX = 480001L;    
    /**
     * 老模型
     */
    public static final Long MR_MODE_OLD = 480002L;    
    /**
     * 新模型
     */
    public static final Long MR_MODE_NEW = 480003L;    
    /**
     * MC类型
     */
    public static final Long MR_MC_TYPE = 490000L;
    /**
     * 非特定MC类型
     */
    public static final Long MR_MC_NON_SPECIFIC = 490001L;
    /**
     * 特定MC类1
     */
    public static final Long MR_MC_SPECIFIC_1 = 490002L;
    /**
     * 特定MC类2
     */
    public static final Long MR_MC_SPECIFIC_2 = 490003L;
    /**
     * 冻结类型
     */
    public static final Long MR_FREEZE_TYPE = 510000L;
    /**
     * 冻结类型--系统冻结510001
     */
    public static final Long MR_FREEZE_SYSTEM = 510001L;
    /**
     * 冻结类型--人工限制510002
     */
    public static final Long MR_FREEZE_RESTRICT = 510002L;
    /**
     * 冻结类型--人工冻结510003
     */
    public static final Long MR_FREEZE_MANUAL = 510003L;
    /**
     * 冻结类型--人工申请补货造成冻结510004
     */
    public static final Long MR_FREEZE_ARTIFICIAL = 510004L;    
    /**
     * 具有特定配送方式的权限（自提）
     */
    public static final Long DELIVERY_DEMAND_PRIVILEGE = 521000L;    
    /**
     * 商品批量停用状态
     */
    public static final Long PRODUCT_STOP_STATUS_TYPE = 120000L;
    //已上传
    public static final Long PRODUCT_STOP_STATUS_UPLOAD = 120001L;
    //已停用
    public static final Long PRODUCT_STOP_STATUS_STOP = 120002L;
    /**
     * 部分释放
     */
    public static final Long PRODUCT_STOP_STATUS_PART_RELEASE = 120003L;
    /**
     * 完全释放
     */
    public static final Long PRODUCT_STOP_STATUS_ALL_RELEASE = 120004L;    
    /*** 库存占用明细状态  */    
    public static final Long STOCK_SALES_STSTUS = 13300L;    
    /*** 库存占用明细状态-占用  */
    public static final Long STOCK_SALES_STSTUS_TAKE_UP = 133001L;    
    /** * 库存占用明细状态-释放 */
    public static final Long STOCK_SALES_STSTUS_RELEASE = 133002L;    
    /*** 库存占用 类型  */    
    public static final Long STOCK_SALES_TYPE = 13400L;    
    /*** 库存占用类型-集货占用 */    
    public static final Long STOCK_SALES_TYPE_COLLECT = 134001L;    
    /*** 库存占用类型-订单占用  */    
    public static final Long STOCK_SALES_TYPE_ORDER = 134002L;
    
    // 补货类型
    public static final Long MR_REPLENISH_TYPE = 531000L;
    // 补货类型--系统自动补货531001
    public static final Long MR_REPLENISH_TYPE_SYSTEM = 531001L;
    // 补货类型--人工申请补货531002
    public static final Long MR_REPLENISH_TYPE_MANUAL = 531002L;
    
    // 退货类型
    public static final Long RETURN_ORDER_TRANSFER_TYPE = 93000L; 
    // 退货类型--正常退货
    public static final Long RETURN_ORDER_TRANSFER_NORMAL = 93001L;
    // 退货类型--退货不下传（如：苏宁批量退货2014-5）
    public static final Long RETURN_ORDER_TRANSFER_NONE = 93002L; 
    //订单集货类型
    public static final Long ORDER_COLLECT_TYPE = 540000L;
    //订单等待集货
    public static final Long ORDER_COLLECT_WAITING = 540001L;
    //订单正在集货
    public static final Long ORDER_COLLECT_PROCESSING = 540002L;
    //订单集货发货
    public static final Long ORDER_COLLECT_SEND = 540003L;  
    //订单集货收货
    public static final Long ORDER_COLLECT_RECEIVE = 540004L; 
    //订单初始化状态类型
    public static final Long ORDER_INIT_TYPE = 550000L;
    //订单等待初始化
    public static final Long ORDER_INIT_WAITING = 550001L;
    //订单初始化完成
    public static final Long ORDER_INIT_COMPLETE =550002L;
    //订单集货、DC策略类型
    public static final Long ORDER_STRATEGY_TYPE = 560000L;
    //订单集货、DC外部指定
    public static final Long ORDER_STRATEGY_OUTER_APPOINT =560001L;
    //订单集货、DC系统计算
    public static final Long ORDER_STRATEGY_SYSTEM_COMPUTE =560002L;
    //订单集货、DC系统配置
    public static final Long ORDER_STRATEGY_SYSTEM_CONFIGURE =560003L;
    //退货订单、标签、普通退货单
    public static final Long RETURN_ORDER_TAG_570001 = 570001L;
    //退货订单、标签、原包原退
    public static final Long RETURN_ORDER_TAG_570002 = 570002L;
    
	/**
	 * 根节点
	 */
	public static final Long ROOT = 1L;

	private static final long serialVersionUID = -5416499110172680085L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private Code parent;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", targetEntity = Code.class)
	@OrderBy("index")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Code> children;
	@Column
	private boolean available;
	@Column(name = "index_")
	private int index;
	@Column
	private boolean editable;

	public Code() {
	}

	public Code(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Code getParent() {
		return parent;
	}
	public void setParent(Code parent) {
		this.parent = parent;
	}
	public Set<Code> getChildren() {
		return children;
	}
	public void setChildren(Set<Code> children) {
		this.children = children;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public List<Code> getEditableChildren() {
		if (children == null || children.isEmpty()) {
			return null;
		}
		List<Code> editableChildren = null;
		for (Code code : children) {
			if (code.isEditable()) {
				if (editableChildren == null) {
					editableChildren = new ArrayList<Code>();
				}
				editableChildren.add(code);
			}
		}
		return editableChildren;
	}
	public List<Code> getAvailableChildren(){
		if (children == null || children.isEmpty()) {
			return null;
		}
		List<Code> availableChildren = null;
		for (Code code : children) {
			if (code.isAvailable()) {
				if (availableChildren == null) {
					availableChildren = new ArrayList<Code>();
				}
				availableChildren.add(code);
			}
		}
		return availableChildren;
	}
	@Override
	public String toString() {
		return "Code [getId()=" + getId() + ", getName()=" + getName() + "]";
	}
}