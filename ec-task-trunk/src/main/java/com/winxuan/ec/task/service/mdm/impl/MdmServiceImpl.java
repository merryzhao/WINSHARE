package com.winxuan.ec.task.service.mdm.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.winxuan.ec.task.model.mdm.MdmProduct;
import com.winxuan.ec.task.service.mdm.MdmService;
import com.winxuan.ec.task.support.utils.MD5Utils;
import com.winxuan.framework.util.security.MD5NORMAL;


@Service("mdmService")
public class MdmServiceImpl implements MdmService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5066917254795693493L;

	private Logger log = LoggerFactory.getLogger(MdmServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	@Autowired
	private JdbcTemplate jdbcTemplateMdm;
	/**
	 * 库位
	 */
	private static final List<String> LOCATIONS = new ArrayList<String>();
	static {
		LOCATIONS.add("0001");
		LOCATIONS.add("0025");
	}
	/**
	 * 附加属性
	 */
	private static final Map<String,String> PRODUCT_EXTEND_NAME = new HashMap<String,String>();
	static {
		PRODUCT_EXTEND_NAME.put("SIZEFORMAT", "开本");
		PRODUCT_EXTEND_NAME.put("PAGENUM", "页数");
		PRODUCT_EXTEND_NAME.put("WORDNUMBER", "字数");
		PRODUCT_EXTEND_NAME.put("BINDINGFORMAT", "装帧");
		PRODUCT_EXTEND_NAME.put("THISEDITION", "版次");
		PRODUCT_EXTEND_NAME.put("PRINTINGTIMES", "印次");
		PRODUCT_EXTEND_NAME.put("THISEDITIONYEARMONTH", "出版时间");
		PRODUCT_EXTEND_NAME.put("THISPRINTINGYEARMONTH", "印刷时间");	
		PRODUCT_EXTEND_NAME.put("DISCQUANTITY", "碟片数");
		PRODUCT_EXTEND_NAME.put("PLAYTIME", "片长");
		PRODUCT_EXTEND_NAME.put("LANGUAGE", "语种");
		PRODUCT_EXTEND_NAME.put("STARRING", "主演");
	}

	/**
	 * 描述属性
	 */
	private static final Map<String,String> PRODUCT_DESC_NAME =new HashMap<String,String>();
	static {
		PRODUCT_DESC_NAME.put("EditorRecommend","主编推荐");
		PRODUCT_DESC_NAME.put("ContentsAbstract","内容简介");
		PRODUCT_DESC_NAME.put("Catalog","目录");
		PRODUCT_DESC_NAME.put("AuthorIntroduction","作者介绍");
		PRODUCT_DESC_NAME.put("DelicacyContents","书摘");
	}

	/**
	 * 描述属性装入临时对象
	 * @param product
	 * @return
	 */
	private MdmProduct getMdmProductClobInfo(MdmProduct product){
		List<Map<String, Object>> maps = findCommodityDesc(product.getMerchId());
		if(maps!=null && maps.size()>0){
			for(Map<String, Object> m : maps){
				String clobname=(String) m.get("CLOBNAME");
				String value = (String) m.get("VALUE");	
				if("Catalog".equals(clobname)){
					product.setCatalog(value);
				}else if("ContentsAbstract".equals(clobname)){	
					product.setContentsAbstract(value);
				}else if("DelicacyContents".equals(clobname)){
					product.setDelicacyContents(value);
				}else if("EditorRecommend".equals(clobname)){
					product.setEditorRecommend(value);
				}
			}
		}
		return product;
	}
    
	
	/**
	 * 添加到商品扩展属性
	 * @param product
	 * @param meta
	 */
	private  void addEcProductExtends(MdmProduct product){		
		int index = 0 ;
		if(product.getSizeFormat() != null){
			addEcProductExtend(product.getId(),1,index,PRODUCT_EXTEND_NAME.get("SIZEFORMAT"),product.getSizeFormat());			
			index++;
		}if(product.getPageNum() != null){
			addEcProductExtend(product.getId(),2,index,PRODUCT_EXTEND_NAME.get("PAGENUM"),product.getPageNum());
			index++;
		}if(product.getWordNumber() != null){
			addEcProductExtend(product.getId(),3,index,PRODUCT_EXTEND_NAME.get("WORDNUMBER"),product.getWordNumber());
			index++;
		}if(product.getBindingFormat() != null){
			addEcProductExtend(product.getId(),4,index,PRODUCT_EXTEND_NAME.get("BINDINGFORMAT"),product.getBindingFormat());
			index++;
		}if(product.getBindingFormat() != null){
			addEcProductExtend(product.getId(),5,index,PRODUCT_EXTEND_NAME.get("BINDINGFORMAT"),product.getBindingFormat());
			index++;
		}if(product.getThisEdition() != null){
			addEcProductExtend(product.getId(),6,index,PRODUCT_EXTEND_NAME.get("THISEDITION"),product.getThisEdition());
			index++;
		}if(product.getThisEditionYearMonth() != null){
			addEcProductExtend(product.getId(),7,index,PRODUCT_EXTEND_NAME.get("THISEDITIONYEARMONTH"),product.getThisEditionYearMonth());
			index++;
		}if(product.getThisPrintingYearMonth() != null){
			addEcProductExtend(product.getId(),8,index,PRODUCT_EXTEND_NAME.get("THISPRINTINGYEARMONTH"),product.getThisPrintingYearMonth());
			index++;
		}if(product.getDiscQuantity() != null){
			addEcProductExtend(product.getId(),14,index,PRODUCT_EXTEND_NAME.get("DISCQUANTITY"),product.getDiscQuantity());
			index++;
		}if(product.getPlaytime() != null){
			addEcProductExtend(product.getId(),15,index,PRODUCT_EXTEND_NAME.get("PLAYTIME"),product.getPlaytime());
			index++;
		}if(product.getLanguage() != null){
			addEcProductExtend(product.getId(),16,index,PRODUCT_EXTEND_NAME.get("LANGUAGE"),product.getLanguage());
			index++;
		}if(product.getStarring() != null){
			addEcProductExtend(product.getId(),17,index,PRODUCT_EXTEND_NAME.get("STARRING"),product.getStarring());
			index++;
		}
		
	}
	/**
	 * 添加到描述属性
	 * @param product
	 * @param meta
	 */
	private  void addEcProductDescs(MdmProduct product){
		int index = 0 ;
		if(product.getEditorRecommend() != null){
			addEcProductDesc(product.getId(),9,PRODUCT_DESC_NAME.get("EditorRecommend"),product.getEditorRecommend(),index,MD5NORMAL.getMD5(product.getEditorRecommend()));
			index++;
		}if(product.getContentsAbstract() != null){
			addEcProductDesc(product.getId(),10,PRODUCT_DESC_NAME.get("ContentsAbstract"),product.getContentsAbstract(),index,MD5NORMAL.getMD5(product.getContentsAbstract()));
			index++;
		}if(product.getCatalog() != null){
			addEcProductDesc(product.getId(),11,PRODUCT_DESC_NAME.get("Catalog"),product.getCatalog(),index,MD5NORMAL.getMD5(product.getCatalog()));
			index++;
		}if(product.getAuthorIntroduction() != null){
			addEcProductDesc(product.getId(),12,PRODUCT_DESC_NAME.get("AuthorIntroduction"),product.getAuthorIntroduction(),index,MD5NORMAL.getMD5(product.getAuthorIntroduction()));
			index++;
		}if(product.getDelicacyContents() != null){
			addEcProductDesc(product.getId(),13,PRODUCT_DESC_NAME.get("DelicacyContents"),product.getDelicacyContents(),index,MD5NORMAL.getMD5(product.getDelicacyContents()));
			index++;
		}
	}
	/**
	 * 获取分类信息
	 * @param merchId
	 * @return
	 */
	private MdmProduct getMdmProductCategory(MdmProduct product){
		List<Map<String, Object>> maps = jdbcTemplateMdm.queryForList(FIND_MDM_COMMODITY_MACOTORY,new Object[]{product.getMerchId()});
		for(Map<String, Object> m : maps){
			String sorttreeid=(String) m.get("SORTTREEID");
			String sortcode = (String) m.get("SORTCODE");
			if(sorttreeid.equals("Category")){
				product.setMccategory(sortcode);
			}else if(sorttreeid.equals("ManageSort")){
				product.setManagecategory(sortcode);
			}else if(sorttreeid.equals("MartSort")){
				product.setWorkcategory(sortcode);
			}
		}
		return product;
	}
	/**
	 * 获取影像属性
	 * @param product
	 * @return
	 */
	private MdmProduct getMdmProductCdInfo(MdmProduct product){
		List<Map<String, Object>> maps = jdbcTemplateMdm.queryForList(FIND_MDM_COMMODITY_CD,new Object[]{product.getMerchId()});
		for(Map<String, Object> m : maps){
			Integer discQuantity = null;
			String playtime=(String) m.get("PLAYTIME");
			if(m.get("DISCQUANTITY")!=null){
				discQuantity = ((BigDecimal) m.get("DISCQUANTITY")).intValue();
				product.setDiscQuantity(discQuantity);
			}
			product.setPlaytime(playtime);
		}
		return product;
	}
	private void addEcProductImgs(List<Map<String,Object>> list,MdmProduct product){
		int imgIndex =0;
		for(Map<String,Object> map : list){						
			Integer type = (Integer)map.get("type");
			String digest = (String)map.get("digest");
			String url = (String)map.get("url");
			InputStream inputStream = (InputStream) map.get("content");
			if(saveProductImg(inputStream,url)){
				addEcProductImg(product.getId(),url,type,imgIndex,digest);
				imgIndex++;							
			}
		}
	}
	/**
	 * 同步新加商品
	 */
	@Override
	public void synchNewProduct() {
		int firstResult = 0;
		int buffer = 100;
		List<MdmProduct> mdmProducts = null;
		int count = jdbcTemplateMdm.queryForInt(SELECT_ADD_PRODUCT_COUNT);
		log.warn(" ADD total:" + count + " firstReust:" + firstResult
				+ " buffer: " + buffer);
		while ((mdmProducts = getCommodtiy(SELECT_ADD_PRODUCT, firstResult,
				buffer)) != null && mdmProducts.size() > 0) {
			for (MdmProduct product : mdmProducts) {
				log.info(product.getMerchId() + " " + product.getBookName());
				getMdmProductCdInfo(product);
				getMdmProductClobInfo(product);
				getMdmProductCategory(product);
				if (getEcProductIdBySapCode(product.getSapCode()).size() > 0) {
					log.warn("SAPCode exist..." + product.getSapCode());
					continue;
				}
				Integer mcCategory = getEcCategory(product.getMccategory());
				BigDecimal discount = getEcProductDiscount(product);
				System.out.println(discount);
				int id = addEcProduct(product, mcCategory);
				product.setId(id);
				if (processDiscountAndCategory(discount, mcCategory, id)) {
					continue;
				} else {
					List<Map<String, Object>> list = findMdmProductImg(
							product.getMerchId(), product.getId());
					addEcProductImgs(list, product);
					for (String location : LOCATIONS) {
						addEcProductSale(location, discount, product);		
					}
					addEcProductExtends(product);
					addEcProductDescs(product);
				}
			}
			firstResult += buffer;
		}
	}

	@Override
	public void synchChangedProduct() {
		//TODO
		log.warn("start synch product!");
		BigDecimal discount  =null;
		List<Integer> ecProductIds =null;
		Integer mcCategory = null;
		List<MdmProduct> mdmProducts = null;
		int firstResult=0;
		int buffer=100;
		int count = jdbcTemplateMdm.queryForInt(SELECT_UPDATE_PRODUCT_COUNT);
		log.warn("total:"+count+" firstReustl:"+firstResult+" buffer: "+buffer);
		while((mdmProducts = getCommodtiy(SELECT_UPDATE_PRODUCT,firstResult, buffer)) != null
				&& mdmProducts.size() >0){
			for(MdmProduct product : mdmProducts){
				getMdmProductClobInfo(product);
				getMdmProductCategory(product);
				ecProductIds = getEcProductIdBySapCode(product.getSapCode());	
				if(ecProductIds!= null && ecProductIds.size()>0){
					for(Integer id : ecProductIds){
						log.info(product.getMerchId()+" "+id);
						product.setId(id);
						discount = getEcProductDiscount(product);	
						mcCategory = getEcCategory(product.getMccategory());
						if(processDiscountAndCategory(discount, mcCategory,id)){
							continue;
						}
						product.setDiscount(discount);
						product.setCategory(mcCategory);
						if(isLockedProduct(id)){
							log.warn("product is locked! "+id);
							continue;
						}
						updateEcProductBaseInfo(product);
						updateProductSaleInfo(product);
						updateEcProductMeta(product);
						updateEcProductDesc(product);
						updateEcProductImg(product);
					}
				}
			}
			firstResult += buffer;
		}	
	}

	private void updateEcProductImg(MdmProduct product){
		List<Map<String,Object>> list = findMdmProductImg(product.getMerchId(),product.getId());
		for(Map<String,Object> map :list){						
			Integer type = (Integer)map.get("type");
			String digest = (String)map.get("digest");
			String url = (String)map.get("url");
			InputStream inputStream = (InputStream) map.get("content");
			String oldDigest = getEcProductImgDegist(product.getId(), type);
			if(!digest.equals(oldDigest)){
				if(saveProductImg(inputStream,url))
					updateProductImg(product,url,type,digest);
			}
		}
	}

	private void updateProductImg(MdmProduct product,String url ,Integer type,String digest){
		jdbcTemplateEcCore.update(UPDATE_EC_COMMODITY_IMG, new Object[]{url,digest,product.getId(),type});

	}

	private void updateProductSaleInfo(MdmProduct product){
		BigDecimal salePrice = product.getPrice().multiply(product.getDiscount());
		jdbcTemplateEcCore.update(UPDATE_EC_COMMODITY_SALE, new Object[]{salePrice,product.getId(),product.getSapCode()});
	}

	private String getEcProductImgDegist(Integer id,Integer type){	
		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(SELECT_EC_COMMODITY_IMG, new Object[]{id,type});
		if(list != null && list.size()>0){
			return (String) list.get(0).get("digest");
		}
		return null;
	}

	/**
	 * 没有折扣和分类对应的商品
	 * @param discount
	 * @param mcCategory
	 * @param id
	 * @return
	 */
	private boolean processDiscountAndCategory(BigDecimal discount,Integer mcCategory,Integer id){
		if(discount == null ){
			log.warn("product discount not found! "+id);
			addNotMatchPriceCommodity(id);
			return true;
		}
		if(mcCategory ==null){
			log.warn("product category not found! "+id);
			addNotMatchCategoryCommodity(id);
			return true;
		}
		return false;
	}
	/**
	 * 得到图片信息
	 * @param merchId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> findMdmProductImg(Integer merchId,final Integer id){
		return  jdbcTemplateMdm.query(SELECT_MDM_COMMODITY_IMG, new RowMapper(){
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				Map<String,Object> results = new HashMap<String,Object>();				
				String filetype = null;
				StringBuffer fileName = new StringBuffer("F:/");
				try {	
					filetype =rs.getString("attachmenttype");
					if(BIG_IMG.equals(filetype)){
						results.put("type", MdmService.BIG);
						fileName.append(id).append("_b.jpg");
					}else if(SMALL_IMG.equals(filetype)){
						results.put("type", MdmService.SMALL);
						fileName.append(id).append("_s.jpg");
					}else if(ILLUSTRATION_IMG.equals(filetype)){
						results.put("type", MdmService.ILLUSTRATION);
						fileName.append(rs.getString("filename")).append(".jpg");
					}
					results.put("url",fileName.toString());					
					InputStream inputStream = rs.getBinaryStream("content");
					InputStream inputStream1 = rs.getBinaryStream("content");
					results.put("content", inputStream);
					results.put("digest", MD5Utils.getMD5(inputStream1));
				}catch(Exception e){
					log.warn("query img exception! "+e.getMessage());
				}
				return results;
			}
		},new Object[]{merchId});
	}

    /**
     * 保存图片
     * @param inputStream
     * @param path
     * @return
     */
	private boolean saveProductImg(InputStream inputStream,String path){
		File fileOutput = new File(path.toString());
		FileOutputStream fo = null;
		try {
			if(inputStream==null || path ==null){
				return false;
			}
			fo = new FileOutputStream(fileOutput);
			int c;
			while ((c = inputStream.read()) != -1) {
				fo.write(c);
			}
			fo.flush();
			fo.close();
		} catch (FileNotFoundException e) {
			log.warn("path not found! "+path);
		} catch (IOException e) {
			log.warn("saveProductImg error! "+e.getMessage());
		}
		return true;
	}

	/**
	 * 更新商品基础信息
	 * @param owncode
	 * @return
	 */
	private void updateEcProductBaseInfo(MdmProduct m){
		jdbcTemplateEcCore.update(UPDATE_EC_COMMODITY,new Object[]{
				m.getBookName(),
				m.getIsbn(),
				m.getPublisher(),
				m.getThisEditionYearMonth(),
				BOOK.equals(m.getMerchType())? 1 : 2,
						m.getThisPrintingYearMonth(),	
						m.getAuthor(),
						m.getCategory(),
						m.getPrice(),
						m.getWorkcategory(),
						m.getManagecategory(),
						m.getMccategory(),
						m.getNormalVendorId(),
						m.getId()
		});
	}

	/**
	 * 更新商品基础属性 
	 * @param product
	 */
	private void updateEcProductMeta(MdmProduct product){
		if(product.getSizeFormat() != null){
			updateProductExtend(product, 1, product.getSizeFormat());
		}if(product.getPageNum() != null){
			updateProductExtend(product, 2, product.getPageNum());
		}if(product.getWordNumber() != null){
			updateProductExtend(product, 3, product.getWordNumber());		
		}if(product.getBindingFormat() != null){
			updateProductExtend(product, 4, product.getBindingFormat());	
		}if(product.getBindingFormat() != null){
			updateProductExtend(product, 5, product.getBindingFormat());	
		}if(product.getThisEdition() != null){
			updateProductExtend(product, 6, product.getThisEdition());	
		}if(product.getThisEditionYearMonth() != null){
			updateProductExtend(product, 7, product.getThisEditionYearMonth());	
		}if(product.getThisPrintingYearMonth() != null){
			updateProductExtend(product, 8, product.getThisPrintingYearMonth());	
		}
	}

	/**
	 * 更新商品扩展属性
	 * @param product
	 */
	private void updateEcProductDesc(MdmProduct product){
		Map<Integer, String> digestMap = getEcProductDesc(product);
		if(product.getEditorRecommend() != null){
			updateProductDesc(product, 9, product.getEditorRecommend(), digestMap.get(9));
		}if(product.getContentsAbstract() != null){
			updateProductDesc(product, 10, product.getContentsAbstract(), digestMap.get(10));
		}if(product.getCatalog() != null){
			updateProductDesc(product, 11, product.getCatalog(), digestMap.get(11));
		}if(product.getAuthorIntroduction() != null){
			updateProductDesc(product, 12, product.getAuthorIntroduction(), digestMap.get(12));
		}if(product.getDelicacyContents() != null){
			updateProductDesc(product, 13, product.getDelicacyContents(), digestMap.get(13));
		}
	}

	private Map<Integer, String> getEcProductDesc(MdmProduct product){
		Map<Integer, String> resultMap = new HashMap<Integer, String>();
		List<Map<String,Object>> list =  jdbcTemplateEcCore.queryForList(SELECT_EC_COMMODITY_DESC, new Object[]{product.getId()});
		for(Map<String, Object> map : list){
			String meta =String.valueOf((Long) map.get("meta"));
			String content=(String)map.get("digest");
			resultMap.put(Integer.valueOf(meta), content);
		}
		return resultMap;
	}

	private void updateProductDesc(MdmProduct product,Integer meta,String content,String digest){
		String newDigest = MD5NORMAL.getMD5(content);
		if(digest!=null && newDigest.equals(digest)){
			return;
		}
		jdbcTemplateEcCore.update(UPDATE_EC_COMMODITY_DESC, new Object[]{content,product.getId(),meta});
	}

	private void updateProductExtend(MdmProduct product,Integer meta,Object value){
		jdbcTemplateEcCore.update(UPDATE_EC_COMMODITY_EXTEND, new Object[]{
				value,product.getId(),meta
		});
	}

	/**
	 * 判断是否为不更新商品
	 * @param productId
	 * @return
	 */
	private boolean isLockedProduct(Integer productId){
		List<Map<String, Object>> result =jdbcTemplateEcCore.queryForList(SELECT_EC_PRODUCT_LOCK,
				new Object[]{productId});
		if(result != null && result.size()>0){
			return (Boolean) result.get(0).get("islock");
		}
		return false;
	}

	/**根据自编码获取EC系统的商品编号
	 * @param owncode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Integer> getEcProductIdBySapCode(String owncode){
		return jdbcTemplateEcCore.query(SELECT_EC_PRODUCT_ID, new Object[]{owncode}, new RowMapper() {
			@Override
			public Integer mapRow(ResultSet rs, int i) throws SQLException {
				Integer ecProductId =rs.getInt("product");
				return ecProductId;
			}
		});
	}


	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/config/applicationContext.xml");
		MdmService mdmService =(MdmService)applicationContext.getBean("mdmService");
		mdmService.synchNewProduct();
	}



	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> findCommodityDesc(Integer merchId){		  
		return  jdbcTemplateMdm.query(SELECT_DESC_PRODUCT, new RowMapper(){
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				Map results = new HashMap();
				CLOB clob = null;
				String str = null;
				String clobname = null;
				try {
					clob = (CLOB) rs.getClob("VALUE");
					str = clobToString(clob);
					clobname =rs.getString("CLOBNAME");
					results.put("VALUE", str);
					results.put("CLOBNAME", clobname);
				}catch(Exception e){
					e.printStackTrace();
				}
				return results;
			}
		},new Object[]{merchId});
	}

	private Integer getEcCategory(String mcCategory){
		List<Map<String,Object>> list = jdbcTemplateEcCore.queryForList(GET_EC_CATEGORY,new Object[]{mcCategory});
		if(list.size() <= 0)
			return null;
		return (Integer)list.get(0).get("category");
	}


	/**
	 * 添加ec商品
	 * @param m
	 * @param category
	 * @return
	 */
	private int  addEcProduct(MdmProduct m,Integer category){
		jdbcTemplateEcCore.update(INSERT_EC_COMMODITY,new Object[]{
				m.getBookName(),
				m.getIsbn(),
				m.getPublisher(),
				m.getThisEditionYearMonth(),
				BOOK.equals(m.getMerchType())? 1 : 2,
						m.getThisPrintingYearMonth(),	
						m.getAuthor(),
						category,
						m.getPrice(),
						m.getWorkcategory(),
						m.getManagecategory(),
						m.getMccategory(),
						m.getNormalVendorId(),
						0		   				  
		});
		return jdbcTemplateEcCore.queryForObject(FIND_MAX_ID, Integer.class);
	}
	/**
	 * 得到商品的折扣
	 * @param product
	 * @return
	 */
	private BigDecimal getEcProductDiscount(MdmProduct product){  
		List<Map<String,Object>> list = jdbcTemplateEcCore.queryForList(GET_EC_COMMODITY_DISCOUNT, new Object[]{product.getNormalVendorId(),product.getMccategory()});
		if(list == null ||list.size()<=0)
			return null;
		return (BigDecimal)list.get(0).get("discount");
	}
	/**
	 * 添加商品销售
	 * @param location
	 * @param id
	 * @param discount
	 * @param product
	 * @param index
	 */
	private void addEcProductSale(String location,BigDecimal discount ,MdmProduct product){		
		jdbcTemplateEcCore.update(INSERT_EC_COMMODITY_SALE, new Object[]{	
				product.getId(),
				1,
				product.getPrice().multiply(discount),
				0,
				1,
				location,
				0,
				0,
				product.getSapCode(),
				0
		});
	}
	/**
	 * 
	 */
	private void addEcProductImg(int id,String url,int type,int imgIndex,String digest){		
		jdbcTemplateEcCore.update(INSERT_EC_COMMODITY_IMG, new Object[]{	
				id,
				url,
				type,
				imgIndex,
				digest
		});
	}
	/**
	 * 插入到不匹配商品价格
	 * @param id
	 */
	private void addNotMatchPriceCommodity(int id){
		jdbcTemplateEcCore.update(INSERT_EC_COMMODITY_NOTMATCH_PRICE,new Object[]{id});
	}

	/**
	 * 插入到不匹配商品价格
	 * @param id
	 */
	private void addNotMatchCategoryCommodity(int id){
		jdbcTemplateEcCore.update(INSERT_EC_COMMODITY_NOTMATCH_CATEGORY,new Object[]{id});
	}

	/**
	 * 添加附加属性
	 * @param id
	 * @param meta
	 * @param index
	 * @param name
	 * @param value
	 */
	private void addEcProductExtend(int id,int meta,int index,String name,Object value){
		jdbcTemplateEcCore.update(INSERT_EC_COMMODITY_EXTEND, new Object[]{
				id,
				meta,
				name,
				value,
				index,
				1
		});
	}
	/**
	 * 添加商品描述
	 * @param product
	 * @param meta
	 * @param name
	 * @param content
	 * @param index
	 * @param digest
	 */
	private void addEcProductDesc(int product,int meta,String name,String content,int index,String digest){
		jdbcTemplateEcCore.update(INSERT_EC_COMMODITY_DESC,new Object[]{product,meta,name,content,index,digest});
	}

	/**
	 *得到主数据商品
	 * @param firstResult
	 * @param buffer
	 * @return
	 */
	private List<MdmProduct> getCommodtiy(String sql,int firstResult,int buffer){
		return jdbcTemplateMdm.query(sql, 
				new Object[]{firstResult+buffer,firstResult}, new MdmProductRowMapper());
	}

	/**
	 * clob 转化为String
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private String clobToString(CLOB clob) throws SQLException, IOException {
		String reString = "";
		if( clob == null || clob.getCharacterOutputStream() == null )
			return "";
		Reader is = clob.getCharacterStream();
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}


}
