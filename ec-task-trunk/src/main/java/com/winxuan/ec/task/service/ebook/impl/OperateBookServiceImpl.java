package com.winxuan.ec.task.service.ebook.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.dao.ProductDao;
import com.winxuan.ec.dao.ProductJiuyueDao;
import com.winxuan.ec.dao.ProductMergeTaskDao;
import com.winxuan.ec.dao.ProductMetaDao;
import com.winxuan.ec.dao.ProductSalePerformanceDao;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductDescription;
import com.winxuan.ec.model.product.ProductExtend;
import com.winxuan.ec.model.product.ProductImageZoomTask;
import com.winxuan.ec.model.product.ProductJiuyue;
import com.winxuan.ec.model.product.ProductMergeTask;
import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSalePerformance;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.task.dao.ebook.EbookChangeDao;
import com.winxuan.ec.task.model.ebook.EbookChange;
import com.winxuan.ec.task.service.ebook.OperateBookService;
import com.winxuan.ec.task.support.utils.EbookConstants;
import com.winxuan.ec.task.support.utils.ToolsUtil;
import com.winxuan.framework.dynamicdao.InjectDao;

/**
 * 处理单本电子书
 * 
 * @author guanxingjiang
 * 
 */
@Service("operateBookService")
@Transactional(rollbackFor = Exception.class)
public class OperateBookServiceImpl implements OperateBookService {
	private static final Logger LOG = Logger
			.getLogger(OperateBookServiceImpl.class);
	private static final String BOOKPATH = "/data/ebook/9yuebook/";
	private static final int INDEX = 0;

	@Autowired
	private EbookChangeDao ebookChangeDao;

	@InjectDao
	private ProductDao productDao;

	@InjectDao
	private ProductMetaDao productMetaDao;

	@InjectDao
	private ProductSalePerformanceDao productSalePerformanceDao;

	@InjectDao
	private ProductJiuyueDao productJiuyueDao;

	@InjectDao
	private ProductMergeTaskDao productMergeTaskDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void operateBook(EbookChange ebook) throws Exception {
		// 判断流程
		Integer storeFlag = ebook.getStoreFlag();
		// 新增电子书
		if (storeFlag.equals(EbookConstants.STORE_FLAG_DRMFINISH)) {
			mergeProduct(ebook);
		} else {
			Long bookid = ebook.getBookID();
			ProductJiuyue product9yue = productJiuyueDao.selectMatchup(bookid);
			// 修改封面
			if (storeFlag.equals(EbookConstants.STORE_FLAG_ALTERIMAGE)) {
				if (null == product9yue) {
					mergeProduct(ebook);
				} else {
					Product product = product9yue.getProduct();
					updateImageTask(bookid, product);
				}
			}
			// 上架
			if (storeFlag.equals(EbookConstants.STORE_FLAG_ONSHELF)) {
				if (null == product9yue) {
					mergeProduct(ebook);
				} else {
					ProductSale ps = product9yue.getProductSale();
					ps.setUpdateTime(new Date());
					ps.setSaleStatus(new Code(Code.PRODUCT_SALE_STATUS_ONSHELF));
					productDao.merge(ps);
					LOG.info(ps.getId() + "商品上架成功............");
				}
			}
			// 下架
			if (storeFlag.equals(EbookConstants.STORE_FLAG_OFFSHELF)) {
				if (null == product9yue) {
					LOG.info(ebook.getBookID() + "没有对应商品需要下架");
				} else {
					ProductSale ps = product9yue.getProductSale();
					ps.setUpdateTime(new Date());
					ps.setSaleStatus(new Code(Code.PRODUCT_SALE_STATUS_EC_STOP));
					productDao.merge(ps);
					LOG.info(ps.getId() + "商品下架成功............");
				}
			}
			
			// 基础信息修改
			if (storeFlag.equals(EbookConstants.STORE_FLAG_BASE_INFO_CHANGE)) {
				if (null == product9yue) {
					mergeProduct(ebook);
				} else {
					
					ProductSale ps = product9yue.getProductSale();
					ps.setUpdateTime(new Date());
					ps.setVendor(ebook.getVendorId().toString());
					ps.setSalePrice(ebook.getSalePrice());
					ps.setBasicPrice(ebook.getSalePrice());
					ps.setSellName(ebook.getBookName());
					productDao.update(ps);
					
					product9yue.setIsbn(ebook.getIsbn());
					productJiuyueDao.update(product9yue);
					
					
					Long category = ebook.getCategory();
					if (category.compareTo(0L)== 0) {
						category = 1L;
					}
					
					Product product =product9yue.getProduct();
					Long pid = product.getId();
					updateCategory(pid, category);
					
					updateProduct(product, ebook);
					
					LOG.info(product.getId() + "基础信息修改成功............");
				}
			}
		}
		// 一本书所有环节处理结束，修改状态为文轩已导入：1
		ebookChangeDao.saveEbook(1, ebook.getId());
	}

	/**
	 * 新增合并商品
	 * 
	 * @throws Exception
	 */
	public void mergeProduct(EbookChange ebook) throws Exception {
		try {
			// 验证有没有转换过
			ProductJiuyue product9yue = productJiuyueDao.selectMatchup(ebook
					.getBookID());
			if (null == product9yue) {
				Product product = insertItem(ebook);
				Long bid = ebook.getBookID();
				insertImageTask(bid, product);
				ProductSale productSale = insertProductSale(product, ebook);
				insertProductSalePerformance(productSale);
				String isbn = ebook.getIsbn();
				Long pid9y = ebook.getProductId();
				insertMatchup(bid, pid9y, isbn, product, productSale);
				// 是否存在纸质书
				boolean isPaperBook = productDao.getCountByIsbn(isbn) > 1 ? true
						: false;
				if (isPaperBook) {
					insertTask(product, productSale, isbn);
				}
				LOG.info(ebook.getBookID() + "合并商品成功............");
			} else {
				LOG.error(ebook.getBookID() + "已经被上传过");
			}
		} catch (Exception e) {
			LOG.error("ebookTaskJob:" + ebook.getBookID() + "新增合并商品失败!", e);
			throw e;
		}
	}

	/**
	 * 写入商品及相关表 返回一个Product
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public Product insertItem(EbookChange ebook) throws SQLException {
		Product product = insertProduct(ebook);
		insertProductExtend(product, ebook);
		insertEbookExtend(product, ebook);
		insertDscription(product, ebook);

		Long category = ebook.getCategory();
		if (category.compareTo(0L)== 0) {
			category = 1L;
		}
		Long pid = product.getId();
		insertCategory(pid, category);
		return product;
	}
	/**
	 * 根据参数 ebook  更新 product
	 * @param product
	 * @param ebook
	 */
	public void updateProduct(Product product, EbookChange ebook) {
		product.setName(ebook.getBookName());
		product.setBarcode(ebook.getIsbn());
		product.setManufacturer(ebook.getPublisherName());
		product.setProductionDate(ebook.getPublishDate());
		product.setSort(new Code(Code.PRODUCT_SORT_BOOK));
		product.setAuthor(ebook.getAuthor());
		product.setListPrice(ebook.getSalePrice());
		product.setVendor(ebook.getVendorId().toString());
		if (null != ebook.getCreateDatetime()) {
			product.setCreateTime(ebook.getCreateDatetime());
		}
		product.setHasImage(ebook.getHasCover().equals(new Integer(1)));
		product.setMerchId(500000000 + ebook.getBookID());
		product.setUpdateTime(new Date());
		
//		修改描述信息
		for(ProductDescription pd : product.getDescriptionList()){
			// "内容简介", 10
			if (EbookConstants.INTRODUCTION.equals(pd.getProductMeta().getId())) {
				pd.setContent(ebook.getIntroduction());
				ebook.setIntroduction("");
			}
			// "作者简介", 14
			if (EbookConstants.AUTHORINTRODUCTION.equals(pd.getProductMeta().getId())) {
				pd.setContent(ebook.getAuthorIntroduction());
				ebook.setAuthorIntroduction("");
			}
			
			// "媒体评论", 18
			if (EbookConstants.EDITORCOMMENT.equals(pd.getProductMeta().getId())) {
				pd.setContent(ebook.getEditorComment());
				ebook.setEditorComment("");
			}
			// "目录", 12
			if (EbookConstants.TABLEOFCONTENTS.equals(pd.getProductMeta().getId())) {
				pd.setContent(ebook.getTableOfContents());
				ebook.setTableOfContents("");
			}
		}
//		修改扩展信息
		for(ProductExtend pe : product.getExtendList()){
			
			// "版次",id:5 index:3
			if (EbookConstants.PUBLISHVERSION.equals(pe.getProductMeta().getId())) {
				pe.setValue(ebook.getPublishVersion());
				ebook.setPublishVersion("");
			}
			// "印次",id:6 index:4
			if (EbookConstants.PRINTEDCOUNT.equals(pe.getProductMeta().getId())) {
				pe.setValue(ebook.getPrintedCount().toString());
				ebook.setPrintedCount(null);
			}
			// "印刷时间", id:8 index:10
			if (EbookConstants.PRINTEDDATE.equals(pe.getProductMeta().getId())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String printedDate = sdf.format(ebook.getPrintedDate());
				pe.setValue(printedDate);
				ebook.setPrintedDate(null);
			}
			// "字数", id:3 index:9
			if (EbookConstants.WORDCOUNT.equals(pe.getProductMeta().getId())) {
				pe.setValue(ebook.getWordCount());
				ebook.setWordCount("");
			}
			// "开本", id:1 index:7
			if (EbookConstants.FOLIO.equals(pe.getProductMeta().getId())) {
				pe.setValue(ebook.getFolio());
				ebook.setFolio("");
			}
			// "ISBN", id:44 index:1
			if (EbookConstants.ISBN.equals(pe.getProductMeta().getId())) {
				pe.setValue(ebook.getIsbn());
				ebook.setIsbn("");
			}
			
			
			// "是否有epub", id:114
			if (EbookConstants.HASEPUB.equals(pe.getProductMeta().getId())) {
				pe.setValue(ebook.getHasEpub().toString());
				ebook.setHasEpub(null);
			}
			// "是否免费", 115
			if (EbookConstants.ISFREE.equals(pe.getProductMeta().getId())) {
				pe.setValue(ebook.getIsFree().toString());
				ebook.setIsFree(null);
			}
			// "读取范围", 116
			if (EbookConstants.PREVIEWPAGERANGE.equals(pe.getProductMeta().getId())) {
				pe.setValue(ebook.getPreviewPageRange());
				ebook.setPreviewPageRange("");
			}
			
			// "电子书页数", 117
			if (EbookConstants.PAGECOUNT.equals(pe.getProductMeta().getId())) {
				pe.setValue(ebook.getPageCount().toString());
				ebook.setPageCount(null);
			}	
		}
		productDao.update(product);
//		新增产品描述和扩展
		insertProductExtend(product, ebook);
		insertEbookExtend(product, ebook);
		insertDscription(product, ebook);
		
	}

	/**
	 * 数据写入Product
	 */
	public Product insertProduct(EbookChange ebook) {
		Product product = new Product();
		product.setName(ebook.getBookName());
		product.setBarcode(ebook.getIsbn());
		product.setManufacturer(ebook.getPublisherName());
		product.setProductionDate(ebook.getPublishDate());
		product.setSort(new Code(Code.PRODUCT_SORT_BOOK));
		product.setAuthor(ebook.getAuthor());
		product.setListPrice(ebook.getSalePrice());
		product.setVendor(ebook.getVendorId().toString());
		if (null != ebook.getCreateDatetime()) {
			product.setCreateTime(ebook.getCreateDatetime());
		}
		product.setHasImage(ebook.getHasCover().equals(new Integer(1)));
		product.setMerchId(500000000 + ebook.getBookID());
		product.setUpdateTime(new Date());

		productDao.save(product);
		return product;
	}

	/**
	 *插入书的分类
	 */
	public void insertCategory(Long pid, Long cid) throws SQLException {
     String sql = "insert into product_category(category, product) values (?,?)";
     jdbcTemplate.update(sql,cid,pid);
	}
	/**
	 *更新书的分类
	 */
	public void updateCategory(Long pid, Long cid) throws SQLException {
     String sql = "update product_category set category = ? where product = ?";
     jdbcTemplate.update(sql,cid,pid);
	}
	/**
	 * Extend数据写入ProductExtend 按照要求index全部写0
	 */
	public void insertProductExtend(Product product, EbookChange ebook) {
		// "版次",id:5 index:3
		if (!StringUtils.isEmpty(ebook.getPublishVersion())) {
			extendInsertsDefault(product, EbookConstants.PUBLISHVERSION,
					ebook.getPublishVersion());
		}
		// "印次",id:6 index:4
		if (null != ebook.getPrintedCount()) {
			extendInsertsDefault(product, EbookConstants.PRINTEDCOUNT, ebook
					.getPrintedCount().toString());
		}
		// "印刷时间", id:8 index:10
		if (null != ebook.getPrintedDate()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String printedDate = sdf.format(ebook.getPrintedDate());
			extendInsertsDefault(product, EbookConstants.PRINTEDDATE,
					printedDate);
		}
		// "字数", id:3 index:9
		if (!StringUtils.isEmpty(ebook.getWordCount())) {
			extendInsertsDefault(product, EbookConstants.WORDCOUNT,
					ebook.getWordCount());
		}
		// "开本", id:1 index:7
		if (!StringUtils.isEmpty(ebook.getFolio())) {
			extendInsertsDefault(product, EbookConstants.FOLIO,
					ebook.getFolio());
		}
		// "ISBN", id:44 index:1
		if (!StringUtils.isEmpty(ebook.getIsbn())) {
			extendInsertsDefault(product, EbookConstants.ISBN, ebook.getIsbn());
		}
	}

	/**
	 * 封装meta重复代码
	 */
	public void extendInsertsDefault(Product product, Long metaId, String value) {
		extendInserts(product, metaId, value, INDEX);
	}

	public void extendInserts(Product product, Long metaId, String value,
			int index) {
		ProductExtend pe = new ProductExtend();
		ProductMeta pm = productMetaDao.getProductMeta(metaId);
		pe.setProductMeta(pm);
		pe.setProduct(product);
		pe.setName(pm.getName());
		pe.setValue(value);
		pe.setIndex(index);	
		pe.setShow(true);

		productDao.save(pe);
	}

	/**
	 * EbookExtend数据写入ProductExtend index默认为0
	 */
	public void insertEbookExtend(Product product, EbookChange ebook) {
		// "是否有epub", id:114
		if (null !=ebook.getHasEpub()) {
			extendInsertsDefault(product, EbookConstants.HASEPUB, ebook
					.getHasEpub().toString());
		}
		// "是否免费", 115
		if (null !=ebook.getIsFree()) {
			extendInsertsDefault(product, EbookConstants.ISFREE, ebook
					.getIsFree().toString());
		}
		// "读取范围", 116
		if (!StringUtils.isEmpty(ebook.getPreviewPageRange())) {
			extendInsertsDefault(product, EbookConstants.PREVIEWPAGERANGE,
					ebook.getPreviewPageRange());
		}
		// "电子书页数", 117
		if (null !=ebook.getPageCount()) {
			extendInsertsDefault(product, EbookConstants.PAGECOUNT, ebook
					.getPageCount().toString());
		}
	}

	/**
	 * EbookExtend数据写入ProductDscription
	 */
	public void insertDscription(Product product, EbookChange ebook) {
		// "内容简介", 10
		if (!StringUtils.isEmpty(ebook.getIntroduction())) {
			descriptionInsertsDefult(product, EbookConstants.INTRODUCTION,
					ebook.getIntroduction());
		}
		// "作者简介", 14
		if (!StringUtils.isEmpty(ebook.getAuthorIntroduction())) {
			descriptionInsertsDefult(product,
					EbookConstants.AUTHORINTRODUCTION,
					ebook.getAuthorIntroduction());
		}
		// "媒体评论", 18
		if (!StringUtils.isEmpty(ebook.getEditorComment())) {
			descriptionInsertsDefult(product, EbookConstants.EDITORCOMMENT,
					ebook.getEditorComment());
		}
		// "目录", 12
		if (!StringUtils.isEmpty(ebook.getTableOfContents())) {
			descriptionInsertsDefult(product, EbookConstants.TABLEOFCONTENTS,
					ebook.getTableOfContents());
		}
	}

	/**
	 * 封装Description重复代码
	 */
	public void descriptionInsertsDefult(Product product, Long metaId,
			String content) {
		descriptionInserts(product, metaId, content, INDEX);
	}

	public void descriptionInserts(Product product, Long metaId,
			String content, int index) {
		ProductDescription pd = new ProductDescription();
		ProductMeta pm = productMetaDao.getProductMeta(metaId);
		pd.setProductMeta(pm);
		pd.setProduct(product);
		pd.setName(pm.getName());
		pd.setContent(content);
		pd.setIndex(index);
		pd.setDigest("");

		productDao.save(pd);
	}

	/**
	 * 插入图片任务
	 */
	public void insertImageTask(Long bookid, Product product) {
		ProductImageZoomTask pizt = new ProductImageZoomTask();
		String url = ToolsUtil.getRangDirName(bookid + "") + "/" + bookid
				+ "/cover/original.jpg";
		url = BOOKPATH + url;
		pizt.setProduct(product);
		pizt.setSrc(url);
		pizt.setStatus(new Code(Code.PRODUCT_IMAGE_ZOOM_NONE));

		productDao.save(pizt);
	}

	/**
	 * 修改图片任务
	 */
	public void updateImageTask(Long bookid, Product product) {
		ProductImageZoomTask pizt = productDao
				.findProductImageZoomTask(product);
		String url = ToolsUtil.getRangDirName(bookid + "") + "/" + bookid
				+ "/cover/original.jpg";
		url = BOOKPATH + url;
		pizt.setProduct(product);
		pizt.setSrc(url);
		pizt.setStatus(new Code(Code.PRODUCT_IMAGE_ZOOM_NONE));

		productDao.save(pizt);
	}

	/**
	 * 写入ProductSale
	 * 
	 * @throws ParseException
	 */
	public ProductSale insertProductSale(Product product, EbookChange ebook)
			throws ParseException {
		ProductSale ps = new ProductSale();
		ps.setProduct(product);
		ps.setShop(new Shop(Shop.WINXUAN_SHOP));
		ps.setSalePrice(ebook.getSalePrice());
		ps.setBasicPrice(ebook.getSalePrice());
		ps.setSellName(product.getName());
		ps.setSaleStatus(new Code(Code.PRODUCT_SALE_STATUS_ONSHELF));
		ps.setStorageType(new Code(Code.STORAGE_AND_DELIVERY_TYPE_EBOOK));
		ps.setLocation(new Area(Area.CHENDU));
		ps.setAuditStatus(new Code(Code.PRODUCT_AUDIT_STATUS_PASS));
		ps.setSupplyType(new Code(Code.PRODUCT_SALE_SUPPLY_TYPE_USUAL));
		ps.setVendor(ebook.getVendorId().toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ps.setUpdateTime(sdf.parse(sdf.format(new Date())));

		productDao.save(ps);
		return ps;
	}

	/**
	 * 插入商品统计
	 * 
	 * @throws ParseException
	 */
	public void insertProductSalePerformance(ProductSale productSale)
			throws ParseException {
		ProductSalePerformance psp = new ProductSalePerformance();
		psp.setProductSale(productSale);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		Date now = sdf.parse(dateStr);
		psp.setLastOnShelfTime(now);
		psp.setFirstOnShelfTime(now);
		psp.setCt1("B");
		psp.setCt2("B");
		psp.setCt3("B");
		psp.setCt4("B");
		psp.setCt5("B");
		psp.setDiscount(new BigDecimal(0));
		psp.setSaleStatus(new Code(0L));
		
		productSalePerformanceDao.saveOrupdate(psp);
	}

	/**
	 * 保存对应关系
	 */
	public void insertMatchup(Long bookid9yue, Long product9yue, String isbn,
			Product product, ProductSale productSale) {
		ProductJiuyue pj = new ProductJiuyue();
		pj.setJiuyueBookid(bookid9yue);
		pj.setJiuyueProductid(product9yue);
		pj.setProduct(product);
		pj.setProductSale(productSale);
		pj.setIsbn(isbn);
		pj.setHasFangzheng(false);

		productJiuyueDao.save(pj);
	}

	/**
	 * 创建任务
	 */
	public void insertTask(Product product, ProductSale productSale, String isbn) {
		List<Product> products = productJiuyueDao.queryProductForISBN(isbn);
		if (null == products || products.isEmpty() || products.size() == 0) {
			return;
		}
		ProductMergeTask task = new ProductMergeTask();
		task.setProduct(product);
		task.setProductSale(productSale);
		task.setIsbn(isbn);
		Date now = new Date();
		task.setCreateDate(now);
		task.setLastTime(now);
		task.setStatus(new Code(Code.PRODUCT_MERTE_STATUS_NONE));
		task.setCount(products.size());
		task.setSaleStatus(productSale.getSaleStatus());

		productMergeTaskDao.save(task);
	}
}
