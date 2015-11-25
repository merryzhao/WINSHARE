package com.winxuan.ec.task.service.ebook.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.task.dao.ebook.BusinessLogDao;
import com.winxuan.ec.task.dao.ebook.EbookChangeDao;
import com.winxuan.ec.task.dao.ebook.ProductDao;
import com.winxuan.ec.task.model.ebook.Book;
import com.winxuan.ec.task.model.ebook.BusinessLog;
import com.winxuan.ec.task.model.ebook.EProduct;
import com.winxuan.ec.task.model.ebook.EbookChange;
import com.winxuan.ec.task.model.ebook.ProductItem;
import com.winxuan.ec.task.model.ebook.ProductProcessItem;
import com.winxuan.ec.task.service.ebook.ProductService;
import com.winxuan.ec.task.support.utils.EbookConstants;

/**
 * 电子书商品接口
 * @author luosh
 *
 */
@Service("eproductService")
@Transactional(rollbackFor=Exception.class)
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDao eproductDao;
	@Autowired
	private EbookChangeDao ebookChangeDao;
	@Autowired
	private BusinessLogDao businessLogDao;
	
	/**
	 * 上架操作
	 */
	public void validProduct(Book book,Book oldbook,BusinessLog businessLog, String description){
		EProduct product =null;
		List<EProduct> list = eproductDao.findProduct(book.getId());
		product = list==null||list.size()==0?null:list.get(0);
		Date date = new Date();
		ProductProcessItem productProcessItem = null;
//		product.setIsValid(1);
//		product.setValidDatetime(date);
//		productProcessItem = new ProductProcessItem();
//		productProcessItem.setBookId(book.getId());
//		productProcessItem.setCreateBy("task自动入库");
//		productProcessItem.setCreateDatetime(date);
//		productProcessItem.setDeleteFlag(0);
//		productProcessItem.setFlag(5);
//		productProcessItem.setFlagDescription("task自动入库");
//		productProcessItem.setProductId(product.getProductId());
//		productProcessItem.setUpdateBy("task自动入库");
//		productProcessItem.setUpdateDatetime(date);
//		productProcessItem.setUserType(1);
//		productDao.save(productProcessItem);
//		productDao.update(product);
		EbookChange ebookChange = ebookChangeDao.get(book.getId());
		if(ebookChange == null){
			ebookChange = new EbookChange();
			ebookChange.setAuthor(book.getAuthor());
			ebookChange.setAuthorIntroduction(book.getAuthorIntroduction());
			ebookChange.setBambookEncFlag(0);
			ebookChange.setBookID(book.getId());
			ebookChange.setBookName(book.getBookName());
			ebookChange.setCreateDatetime(date);
			ebookChange.setEbookEncFlag(0);
			ebookChange.setEditorComment(book.getEditorComment());
			ebookChange.setElibFlag(0);
			ebookChange.setFileSize(book.getFileSize());
			ebookChange.setFolio(book.getFolio());
			ebookChange.setHasCover(book.getHasCover());
			ebookChange.setHasEpub(book.getHasEpub());
			ebookChange.setIntroduction(book.getIntroduction());
			ebookChange.setIsbn(book.getIsbn());
			ebookChange.setIsFree(book.getIsFree());
			ebookChange.setLibraryCategoryCode(book.getLibraryBookCateogry());
			ebookChange.setPageCount(book.getPageCount());
			ebookChange.setPaperMaterial(book.getPaperMaterial());
			ebookChange.setPaperPrice(product.getSalePrice());
			ebookChange.setPreviewPageRange(book.getPreviewPageRange());
			ebookChange.setPrintedCount(book.getPrintedCount());
			ebookChange.setPrintedDate(book.getPrintedDate());
			ebookChange.setPrintedQuantity(book.getPrintedQuantity());
			ebookChange.setProductId(product.getProductId());
			ebookChange.setPublishDate(book.getPublishDate());
			ebookChange.setPublisherCode(book.getPublisherCode());
			ebookChange.setPublisherName(book.getPublisherName());
			ebookChange.setPublishVersion(book.getPublishVersion());
			ebookChange.setStoreFlag(0);
			ebookChange.setTableOfContents(book.getTableOfContents());
			ebookChange.setUpdateBy(book.getUpdateBy());
			ebookChange.setUpdateDatetime(date);
			ebookChange.setVendorId(book.getVendorId());
			ebookChange.setWinFlag(0);
			ebookChange.setWordCount(book.getWordCount());
			ebookChange.setSalePrice(product.getSalePrice());
			ebookChangeDao.save(ebookChange);
		}
		
		
		if(oldbook != null){
			list = eproductDao.findProduct(oldbook.getId());
			product = list==null||list.size()==0?null:list.get(0);
			product.setIsValid(0);
			product.setInvalidDatetime(date);
			product.setInvalidReasonDescription("task入库检查重复图书下架");
			productProcessItem = new ProductProcessItem();
			productProcessItem.setBookId(book.getId());
			productProcessItem.setCreateBy("task自动入库");
			productProcessItem.setCreateDatetime(date);
			productProcessItem.setDeleteFlag(0);
			productProcessItem.setFlag(7);
			productProcessItem.setFlagDescription("task入库检查重复图书下架");
			productProcessItem.setProductId(product.getProductId());
			productProcessItem.setUpdateBy("task自动入库");
			productProcessItem.setUpdateDatetime(date);
			productProcessItem.setUserType(1);
			productProcessItem.setSystemCodeId(EbookConstants.INVALID_CODE_ID);
			eproductDao.save(productProcessItem);
			eproductDao.update(product);
		}
		businessLog.setDiscription(description);
		businessLog.setErrorCode("success");
		businessLog.setStepCode(EbookConstants.STEP_CODE_TWO);
		businessLog.setResultStatus(EbookConstants.RESULT_STATUS_ONE);
		businessLog.setStatusCode(0);
		businessLog.setCreateDate(new Date(System.currentTimeMillis()));
		businessLogDao.save(businessLog);
	}
	@Override
	public void createProduct(Book newbook, BigDecimal price) {
		Date date = new Date();
		ProductItem productItem = new ProductItem();
		productItem.setEntityType(1);
		productItem.setEntityID(newbook.getId());
		productItem.setCreateBy("task自动入库");
		productItem.setDeleteFlag(0);
		productItem.setCreateDatetime(date);
		productItem.setUpdateBy("task自动入库");
		productItem.setUpdateDatetime(date);

		EProduct product = new EProduct();
		/** 创建一个商品 * */
		product.setCreateDatetime(date);
		product.setBookCount(1);
		product.setSellCount(0L);
		product.setPrice(BigDecimal.valueOf(0.0D));
		product.setIsValid(0);
		product.setDeleteFlag(0);
		product.setPayforPrice(BigDecimal.valueOf(0.0D));
		product.setPercent(BigDecimal.valueOf(0.0D));
		product.setPackagePercent(BigDecimal.valueOf(0.0D));
		product.setProductName(newbook.getBookName());
		product.setProductDescription(newbook.getIntroduction());
		product.setProductType(1);
		product.setIsFrozen(0);
		product.setCreateBy("task自动入库");
		product.setUpdateBy("task自动入库");
		product.setUpdateDatetime(date);
		product.setInvalidReasonDescription("task自动入库");
		product.setInvalidDatetime(date);
		product.setSalePrice(price);
		product.setCostPrice(price);
		eproductDao.save(product);
		newbook.setProductId(product.getProductId());
		productItem.setProductID(product.getProductId());
		eproductDao.save(productItem);
	}
	@Override
	public void updatePrice(BigDecimal price,Long bookId) {
		List<EProduct> productList = eproductDao.findProduct(bookId);
		EProduct product = null;
		if(productList != null && !productList.isEmpty()){
			product = productList.get(0);
			if(product.getSalePrice().compareTo(price) == 0){
				return;
			}
			eproductDao.updatePrice(price,product);
		}
	}

}
