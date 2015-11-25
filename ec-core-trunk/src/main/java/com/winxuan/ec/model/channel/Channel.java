/*
 * @(#)Channel.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.model.channel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.tree.Tree;

/**
 * description
 * 
 * @author liuan
 * @version 1.0,2011-7-10
 */
@Entity
@Table(name = "channel")
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Channel extends Tree<Channel>{
	
	/**
	 * 渠道根节点
	 */
	public static final Long ROOT = 1000L;
	/**
	 * 直销-普通用户
	 */
	public static final Long CHANNEL_EC  = 104L;
	
	
	public static final Long CHANNEL_MOBILE = 8092L;
	
	/**
	 * 供货渠道—传统团购
	 */
	public static final Long CHANNEL_GROUP_BUY = 105L;
	
	/**
	 * 供货渠道—电商合作
	 */
	public static final Long CHANNEL_GROUP_BUY_EC = 1007L;
	
	/**
	 * 联盟
	 */
	public static final Long CHANNEL_UNION = 700L;
	
	/**
	 * 自建广告联盟
	 */
	public static final Long CHANNEL_UNION_AD = 702L;
	
	/**
	 * 好事者广告联盟
	 */
	public static final Long CHANNEL_UNION_DOT = 701L;
	
	/**
	 * 代理
	 */
	public static final Long CHANNEL_AGENT = 6L;
	
	/**
	 * 好事者
	 */
	public static final Long CHANNEL_REBATE = 1004L;
	
	/**
	 * 淘宝-综合
	 */
	public static final Long TAOBAO_WINSHARE = 110L;
	
	/**
	 * 淘宝-管理
	 */
	public static final Long TAOBAO_MANAGER = 122L;
	
	/**
	 * 淘宝-医学
	 */
	public static final Long TAOBAO_MEDICINE = 123L;
	
	/**
	 * QQ网购
	 */
	public static final Long QQ_CHANNEL = 126L;
	
	/**
	 * 卓越-综合
	 */
	public static final Long AMAZON_WINSHARE = 8081L;
	
	/**
	 * 拍拍-综合
	 */
	public static final Long PAIPAI_WINSHARE = 111L;
	/**
	 * 专业渠道
	 */
	public static final Long CHANNEL_PROFESSIONAL=1003L;
	
	/**
	 * 综合渠道
	 */
	public static final Long CHANNEL_SYNTHETICAL=1002L; 
	
	
	/**
	 * 一号店综合
	 */
	public static final Long YIHAODIAN_WINSHARE = 8086L;
	
	/**
	 * 用户来源－国美库巴
	 */
	public static final Long COO8_WINSHARE = 8094L;
	
	/**
	 * 卓越edi
	 */
	public static final Long ZHUOYUE_EDI = 8085L;
	
	/**
	 * 京东edi
	 */
	public static final Long JINGDONG_EDI = 8087L;
	
	/**
	 * 苏宁edi
	 */
	public static final Long SUNING_EDI = 8090L;
	
	/**
	 * 加盟店
	 */
	public static final Long FRANCHISEE = 8089L;
	/**
	 * 唐宁DC直达
	 */
	public static final Long CHANNEL_TANGNING_DC=45L;
	/**
	 * 供货渠道
	 */
	public static final Long CHANNEL_ORDER_SETTLE = 1005L;
	
	public static final Channel DIRECT_CHANNEL = new Channel(CHANNEL_EC);
	
	public static final Channel UNION_CHANNEL = new Channel(CHANNEL_UNION);
	
	/**
	 * 移动端
	 */
	public static final Channel MOBILE_CHANNEL = new Channel(CHANNEL_MOBILE);
	/**
	 * 易迅-综合
	 */
	public static final Long YIXUN_WINSHARE = 8098L;
	/**
	 * 综合渠道当当
	 */
	public static final Long CHANNEL_ID_DANGDANG=8097L;
	/**
	 * 综合渠道京东
	 */
	public static final Long CHANNEL_ID_360BUY=8096L;
	
	/**
	 * 专业渠道-当当教育专营店
	 */
	public static final Long CHANNEL_DANGDANG_JYZ = 8104L;
	
	public static final Map<Long, String> CHANNEL_MAP = new HashMap<Long, String>(); 
	
	
	public static final String CHANNEL_TAOBAO_NAME = "淘宝";
	public static final String CHANNEL_PAIPAI_NAME = "拍拍";
	public static final String CHANNEL_AMAZON_NAME = "亚马逊";
	public static final String CHANNEL_YIHAODIAN_NAME = "一号店";
	public static final String CHANNEL_QQ_NAME = "QQ网购";
	/**
	 * 渠道COD订单
	 */
	public static final String CHANNEL_COD="CHANNEL_COD_ORDER";
	/**
	 * 当当JIT
	 */
	public static final Long CHANNEL_ID_DANGDANG_JIT = 8099L;
	/**
	 * 京东（华东）JIT
	 */
	public static final Long CHANNEL_ID_JINGDONG_JIT = 8088L;	
	
	/**
	 * 亚马逊Dropship
	 */
	public static final Long CHANNEL_ID_ZHUOYUE_DS = 8108L;

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8382426179607907232L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private Code type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private Channel parent;

	@Column(name = "createtime")
	private Date createTime;
	
	@Column(name = "usingapi")
	private boolean usingApi;

	@Column
	private boolean available;

	@Column(name = "index_")
	private int index;
	
	@Column(name="outofstockcancel")
	private boolean outOfStockCancel;
	
	@Column(name="path")
	private String path;
	/**
	 * 是否结算
	 */
	@Column
	private boolean issettle;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", targetEntity = Channel.class)
	@OrderBy("index")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Channel> children;
	@Column
	private String alias;
	
	static{
		CHANNEL_MAP.put(CHANNEL_ID_360BUY, CHANNEL_COD);
		CHANNEL_MAP.put(CHANNEL_ID_DANGDANG, CHANNEL_COD);
		CHANNEL_MAP.put(CHANNEL_DANGDANG_JYZ, CHANNEL_COD);
	}
    public Channel() {
		super();
	}

	public Channel(Long id) {
		super();
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

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public Channel getParent() {
		return parent;
	}

	public void setParent(Channel parent) {
		this.parent = parent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Set<Channel> getChildren() {
		return children;
	}

	public void setChildren(Set<Channel> children) {
		this.children = children;
	}

	public boolean isUsingApi() {
		return usingApi;
	}

	public void setUsingApi(boolean usingApi) {
		this.usingApi = usingApi;
	}
	
	public boolean isOutOfStockCancel() {
		return outOfStockCancel;
	}

	public void setOutOfStockCancel(boolean outOfStockCancel) {
		this.outOfStockCancel = outOfStockCancel;
	}

	/**
	 * 是否属于直销渠道
	 * @return
	 */
	public boolean isChildOfDirectChannel() {
		return equals(DIRECT_CHANNEL) || isGrandChild(DIRECT_CHANNEL);
	}
	/**
	 * 是否是最后一级渠道
	 */
	public boolean getHasAvailableChildren(){
		return hasAvailableChildren();
	}

	/**
	 * 是否属于外部渠道
	 * @return
	 */
	public boolean isOuterChannel() {
		return isGrandChild(new Channel(CHANNEL_PROFESSIONAL))
				|| isGrandChild(new Channel(CHANNEL_SYNTHETICAL))
				|| this.id.equals(FRANCHISEE);
	}
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
    public boolean equals(Object obj) {
    	if(obj instanceof Channel) {
    		Channel channel = (Channel) obj;
    		return channel.getId() != null && channel.getId().equals(this.getId());
    	}
    	return false;
    }

	public boolean isIssettle() {
		return issettle;
	}

	public void setIssettle(boolean issettle) {
		this.issettle = issettle;
	}
    
	
}
