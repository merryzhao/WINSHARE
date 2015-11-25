package com.winxuan.ec.task.service.ebook.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winxuan.ec.task.dao.ebook.BookDao;
import com.winxuan.ec.task.dao.ebook.ProductDao;
import com.winxuan.ec.task.model.ebook.Book;
import com.winxuan.ec.task.model.ebook.BookChapter;
import com.winxuan.ec.task.model.ebook.BookPreface;
import com.winxuan.ec.task.model.ebook.BusinessLog;
import com.winxuan.ec.task.model.ebook.Chapter;
import com.winxuan.ec.task.model.ebook.EProduct;
import com.winxuan.ec.task.model.ebook.Ebook;
import com.winxuan.ec.task.model.ebook.ExtendInfo;
import com.winxuan.ec.task.model.ebook.InFranceBookCategory;
import com.winxuan.ec.task.model.ebook.InFranceCategory;
import com.winxuan.ec.task.model.ebook.Publisher;
import com.winxuan.ec.task.model.ebook.Vendor;
import com.winxuan.ec.task.service.ebook.BookService;
import com.winxuan.ec.task.service.ebook.InFranceCategoryService;
import com.winxuan.ec.task.service.ebook.ProductService;
import com.winxuan.ec.task.service.ebook.PublisherService;
import com.winxuan.ec.task.support.utils.EbookConstants;
import com.winxuan.ec.task.support.utils.ISBNTool;

/**
 * 电子书图书信息管理
 * @author luosh
 *
 */
@Service("bookService")
@Transactional(rollbackFor=Exception.class)
public class BookServiceImpl implements BookService {
	
	private static final Log LOG = LogFactory.getLog(BookServiceImpl.class);
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private ProductDao eproductDao;
	
	@Autowired
	private ProductService eproductService;
	
	@Autowired
	private InFranceCategoryService inFranceCategoryService;
	
	@Autowired
	private PublisherService epublisherService;
	
	public Book uploadBook(Ebook ebook, Vendor vendor, BusinessLog businessLog) throws Exception {
		//生成book
			Book newbook = createBook(ebook,null);
			
			newbook.setVendorId(vendor.getVendorID());
			BigDecimal price =null;
			if(!StringUtils.isEmpty(ebook.getEbookListPrice())&&Double.valueOf(ebook.getEbookListPrice())!=0D){
				price = BigDecimal.valueOf(Double.valueOf(ebook.getEbookListPrice()));
			} else if(newbook.getPageCount()==null||newbook.getPageCount()==0){
				price = BigDecimal.valueOf(0.0D);
			} else if(newbook.getPageCount()<=200){
				price=BigDecimal.valueOf(3.0D);
			} else if(newbook.getPageCount()<=300&&newbook.getPageCount()>200){
				price=BigDecimal.valueOf(5.0D);
			} else if(newbook.getPageCount()>300){
				price=BigDecimal.valueOf(8.0D);
			}
			
//			if(price.compareTo(BigDecimal.valueOf(0.0D))>0)
//				newbook.setIsFree(0);
//			else newbook.setIsFree(1);
			newbook.setIsFree(0);
			bookDao.save(newbook);//存入BOOK表
			eproductService.createProduct(newbook, price);//存入ProductItem 和 product表 ProductPrice表  这个表PRODUCT_PROCESS_ITEM???
			//存入章节，又存一次newbook
			saveBookChapter(ebook, newbook);
			//加序
			saveBookPreface(ebook,newbook);
//			businessLog.setBusinessId(newbook.getId().toString());
//			businessLogService.logInfo(businessLog,file.getAbsolutePath()+";成功存入数据,还未上传文件",
//					"dbinsert",4,2,2);
			return newbook;
	}
	
	/**
	 * 检查图书信息是否符合规范。
	 * 必填字段：书名、ISBN、页数、电子书价格、作者、出版社、出版时间、中图法分类（
	 * 原创需要在xml文件中标记出来，ISBN、出版社、中图法可以没有）、内容简介、目录；
	 * @param newbook
	 * @return
	 */
	public String checkBookEmpltyInfo(Book newbook,Ebook ebook){
		StringBuffer discription = new StringBuffer("");
		if(StringUtils.isBlank(newbook.getBookName())){
			discription.append(";没有书名");
		}
		
		if(null == newbook.getPageCount() || Integer.valueOf(0).equals(newbook.getPageCount())){
			discription.append(";没有页数");
		}
		if(StringUtils.isBlank(ebook.getEbookListPrice()) || Double.valueOf(ebook.getEbookListPrice()).equals(Double.valueOf("0"))){
			discription.append(";没有价格");
		}
		if(StringUtils.isBlank(newbook.getAuthor())){
			discription.append(";没有作者");
		}
		
		if(null==newbook.getPublishDate()){
			discription.append(";没有出版日期");
		}
		
		if(StringUtils.isBlank(newbook.getIntroduction())){
			discription.append(";没有内容简介");
		}
		if(StringUtils.isBlank(newbook.getTableOfContents())){
			discription.append(";没有目录");
		}
		if(Integer.valueOf(0).equals(newbook.getIsOriginal())){
			if(StringUtils.isBlank(newbook.getIsbn())){
				discription.append(";没有ISBN");
			}
			if(StringUtils.isBlank(newbook.getPublisherName())){
				discription.append(";没有出版社");
			}
			if(StringUtils.isBlank(newbook.getLibraryBookCateogry())){
				discription.append(";没有中图法分类");
			}
		}
		String re = discription.toString();
		return StringUtils.isBlank(re) ? "" : re;
	}
	
	private void saveBookPreface(Ebook ebook, Book newbook) {
		Date date = new Date();
		int type1 = 1;
		int type2 = 1;
		int type3 = 1;
		for(ExtendInfo extendInfo:ebook.getExtendInfos()){
			if(StringUtils.isEmpty(extendInfo.getType())||StringUtils.isEmpty(extendInfo.getContent())){
				continue;
			}
			BookPreface bookPreface = new BookPreface();
			bookPreface.setAuthor(extendInfo.getAuthor());
			bookPreface.setBookId(newbook.getId());
			bookPreface.setDeleteFlag(0);
			bookPreface.setContent(extendInfo.getContent());
			bookPreface.setCreateBy("task自动上传");
			bookPreface.setCreateDate(date);
			bookPreface.setRefName(extendInfo.getTitle());
			bookPreface.setUpdateBy("task自动上传");
			bookPreface.setUpdateDate(date);
			if("前言".equals(extendInfo.getType())){
				bookPreface.setQuence(type1);
				type1++;
				bookPreface.setRefType(1);
			}else if("序".equals(extendInfo.getType())||"序言".equals(extendInfo.getType())){
				bookPreface.setQuence(type2);
				type2++;
				bookPreface.setRefType(2);
			}else if("后记".equals(extendInfo.getType())){
				bookPreface.setQuence(type3);
				type3++;
				bookPreface.setRefType(3);
			}
			bookDao.save(bookPreface);
		}
	}

	public Book createBook(Ebook ebook,Book newbook){
		if(newbook == null){
			newbook = new Book();
		}
		String isbn = ebook.getIsbn();	
		String printingTimes = ebook.getPrintingTimes();
		String factPageCount = ebook.getPageCount();
		String pageCount="";
		if(ebook.getFiles().size()>0){
			pageCount = ebook.getFiles().get(0).getPageCount();
		}
		String wordCount = ebook.getWordCount();
		String copyCount = ebook.getCopyCount();
		String publisher = ebook.getPublisher();
		String isoriginal = (null == ebook.getIsOriginal()|| "".equals(ebook.getIsOriginal()))?"false":ebook.getIsOriginal();
		boolean isOriginal = Boolean.getBoolean(isoriginal);
		String thisEditionPublishingDate = ebook.getThisEditionPublishingDate();
		String isscan = ebook.getIsscan();
		String libCategory ="";
		if(ebook.getCategorys().size()>0){
			libCategory= ebook.getCategorys().get(0).getTextVlaue();
		}
		String paperPrice = ebook.getListPrice();
		newbook.setBookName(ebook.getBookName());
		newbook.setIsbn(ebook.getIsbn());
		/**
		 * 版别代码，收藏调整值，收藏数,分类*/
		if(!StringUtils.isEmpty(isbn)&&"0".equals(ebook.getType())){//期刊没保存版别代码
			newbook.setPublisherCode("7"+ISBNTool.getPublisherCodeByISBN13(isbn));
		}
		newbook.setAdjustValue(0);
		newbook.setCollectionNum(0);
		newbook.setPaperMaterial(ebook.getPaperMaterial());/**纸张*/
		newbook.setEditorComment(ebook.getBookmakerRecommend());/**编辑推荐*/
		newbook.setPack(ebook.getPack());
		newbook.setMediaRecommend(ebook.getMediaRecommend());
		newbook.setFolio(ebook.getSizeFormat());
		newbook.setPublishVersion(ebook.getEdition());	
		newbook.setTags(ebook.getKeyWord());
		newbook.setFlag(4);
		newbook.setIsCanPayMonthly(1);
		newbook.setIsFree(0);
		newbook.setIsLimitHighestPrice(0);
		newbook.setIsSpecialLicence(0);
		newbook.setFileSize(0L);
		newbook.setBookSource(0);
		newbook.setIsHasChapterBook(0);
		newbook.setPrintedCount(0);
		newbook.setUpCount(0L);
		newbook.setDownCount(0L);
		newbook.setCommentCount(0L);
		//drm转换需求字段  标准应为0
		newbook.setDeleteFlag(0);
		newbook.setBookSeries(ebook.getSeriesName());
		newbook.setIntroduction(ebook.getContentsAbstract());
		if(isOriginal){
			newbook.setIsOriginal(1);
		}else {
			newbook.setIsOriginal(0);
		}
		this.getCategory(libCategory, newbook);
		try{
			if(isscan!=null) {
				newbook.setIsscan(Integer.parseInt(isscan));
			}
			if(!StringUtils.isEmpty(printingTimes)){
				newbook.setPrintedCount(Integer.valueOf(printingTimes));
			}
			if(!StringUtils.isEmpty(factPageCount)){
				newbook.setFactPageCount(Integer.valueOf(factPageCount));
			}
			if(!StringUtils.isEmpty(pageCount)){
				newbook.setPageCount(Integer.valueOf(pageCount));
			}
			if(!StringUtils.isEmpty(copyCount)){
				newbook.setPrintedQuantity(Integer.valueOf(copyCount));
			}
			if(!StringUtils.isEmpty(paperPrice)){
				newbook.setPaperPrice(BigDecimal.valueOf(Double.valueOf((paperPrice))));
			}
			Integer fontCount = Integer.valueOf(StringUtils.isEmpty(wordCount)?"0":wordCount+"000");
			newbook.setWordCount(fontCount.toString());	
			if(!StringUtils.isEmpty(thisEditionPublishingDate)){
				Date publishDate=new SimpleDateFormat("yyyy-MM-dd").parse(thisEditionPublishingDate);
				newbook.setPublishDate(publishDate);
			}
		}catch(NumberFormatException ex){
			LOG.info(ex.getCause());
		}catch(ParseException px){
			LOG.info(px.getCause());
		}
		this.getPublicher(newbook, publisher);
		newbook.setPublisherName(publisher);
		newbook.setScore(3f);
		newbook.setHasEpub(0);
		newbook.setAuthor(ebook.getAuthor());
		newbook.setAuthorIntroduction(ebook.getAuthorIntroduction());
		newbook.setLibraryBookCateogry(libCategory);
		newbook.setCreateDateTime(new Date(System.currentTimeMillis()));
		newbook.setUpdateDateTime(new Date(System.currentTimeMillis()));
		newbook.setCreateBy("task自动入库");
		newbook.setUpdateBy("task自动入库");
		getPreviewPageRange(newbook);
		this.updateProduct(newbook, ebook);
		return newbook;
	}
	private void getCategory(String libCategory,Book newbook){
		if(!StringUtils.isEmpty(libCategory)){
			for(int i=libCategory.length();i>=2;i--){
				String code = libCategory.substring(0,i);
				InFranceCategory inFranceCategory = inFranceCategoryService.findByCode(code);
				if(inFranceCategory!=null){
					List<InFranceBookCategory> inFranceBookCategoryList =
						inFranceCategoryService.findByInFranceCategory(inFranceCategory.getId());
					if(null != inFranceBookCategoryList && inFranceBookCategoryList.size()>0){
						newbook.setCategoryId(inFranceBookCategoryList.get(0).getCategoryId());
						break;
					}
				}
			}
		}
	}
	private void getPublicher(Book newbook,String publisher){
		Publisher pub = null;
		if(!StringUtils.isEmpty(newbook.getPublisherCode())){
			pub = epublisherService.getPublisherByCode(newbook.getPublisherCode());
		}
		if(pub!=null){
			newbook.setPublisherID(pub.getId());
		}else{
			if(!StringUtils.isEmpty(publisher)){//出版社
				pub=epublisherService.getPublisherByName(publisher);
				if(pub!=null){
					newbook.setPublisherID(pub.getId());
				}
			}
		}
	}
	private void getPreviewPageRange(Book newbook){
		if (newbook.getPageCount() != null && newbook.getPageCount() != 0) {
			String previewPageRange = "";
			float f = newbook.getPageCount() * (0.3f);
			if (f > 20.0f){
				previewPageRange = "1-20";
			} else {
				String fs = String.valueOf(f);
				fs = fs.substring(0, fs.indexOf("."));
				previewPageRange = "1-" + fs;
			}
			newbook.setPreviewPageRange(previewPageRange);
		}
	}
	private void updateProduct(Book newbook, Ebook ebook){
		if(newbook.getId() != null){
			BigDecimal price = null;
			if(!StringUtils.isEmpty(ebook.getEbookListPrice())&&Double.valueOf(ebook.getEbookListPrice())!=0D){
				price = BigDecimal.valueOf(Double.valueOf(ebook.getEbookListPrice()));
			} else if(newbook.getPageCount()==null||newbook.getPageCount()==0){
				price = BigDecimal.valueOf(0.0D);
			} else if(newbook.getPageCount()<=200){
				price=BigDecimal.valueOf(3.0D);
			} else if(newbook.getPageCount()<=300&&newbook.getPageCount()>200){
				price=BigDecimal.valueOf(5.0D);
			} else if(newbook.getPageCount()>300){
				price=BigDecimal.valueOf(8.0D);
			}
			eproductService.updatePrice(price,newbook.getId());
		}
	}
	@SuppressWarnings("unchecked")
	public void saveBookChapter(Ebook ebook,Book newbook){
		String tableOfContents = "";
		List<Chapter> chapterList = ebook.getChapters();
		Map pidMap = new LinkedMap();
		pidMap.put(0, null);
		for(int i=0;i<chapterList.size();i++){
			Chapter chapter = chapterList.get(i);
			String code=chapter.getCode();
			String page=chapter.getPage() != null ? chapter.getPage().trim(): null;
			//new CDATA("<xml> content");
			String chapterText=chapter.getTextValue() != null ? chapter.getTextValue().trim() : "";
			if(StringUtils.isEmpty(chapterText)){
				continue;
			}
			BookChapter ch=new BookChapter();
			ch.setBookId(newbook.getId());
			if(page!=null) {
				ch.setBeginPage(Integer.valueOf(page));
			}
			ch.setEndPage(0);
			ch.setCreateBy("task自动上传");
			ch.setUpdateBy("task自动上传");
			Date date=new Date();
			ch.setUpdateDatetime(date);
			ch.setDeleteFlag(0);
			ch.setChapterTitle(chapterText);
			tableOfContents += chapterText+"<br/>";
			ch.setAnchor(0);
			ch.setChapterIndex(String.valueOf(i));
			ch.setCreateDatetime(new Date(System.currentTimeMillis()));
			if(!StringUtils.isEmpty(code)){
				int level = code.trim().length()/3-1;
				ch.setLevel(level);						
				code = code.substring(level*3, code.length());	
				ch.setChapterSequence(Integer.valueOf(code));
				ch.setParentChapterId(pidMap.get(level)==null?null:Long.valueOf(pidMap.get(level).toString()));
				bookDao.save(ch);
				pidMap.put(level+1,ch.getId());
			}else{
				ch.setLevel(0);	
				bookDao.save(ch);
			}
		}
		newbook.setTableOfContents(tableOfContents);
		bookDao.save(newbook);
		pidMap.clear();
	}

	/**
	 * 基本条件：书名、ISBN、作者、供应商和出版社是否一致
	 * 附加条件：基本条件都一致时：系统匹配上架必备字段中除基本条件的字段外的字段：页数、出版时间、中图法分类
	 * （原创需要在xml文件中标记出来，ISBN、出版社、中图法可以没有）、内容简介、目录；
	 * 保留信息最全的那条数据，另一条为上传失败并写明原因
	 * 其它条件：如以上两个条件都一致时，取价格最高的那条；另一条为上传失败并写明原因
	 * flag:0:不重复；1：新书上架；3：重复图书；4：老书上架，新书重复
	 */
	@Override
	public int checkRepeatBook(Book book, Book oldbook,BigDecimal price) {
		int flag = EbookConstants.REPEAT_NOT;
		if (book.getBookName().equals(oldbook.getBookName())
				&& book.getIsbn().equals(oldbook.getIsbn())
				&& book.getAuthor().equals(oldbook.getAuthor())
				&& book.getVendorId().equals(oldbook.getVendorId())
				&& book.getPublisherName().equals(oldbook.getPublisherName())) {
			flag = EbookConstants.REPEAT_NEWBOOK;
			
			if (!this.otherMessageOrEquals(oldbook) && this.otherMessageEquals(book, oldbook)) {
				flag = EbookConstants.REPEAT_OTHER;
			}
			if (flag == EbookConstants.REPEAT_OTHER) {
				EProduct product = null;
				List<EProduct> list = eproductDao.findProduct(oldbook.getId());
				product = list == null || list.size() == 0 ? null : list.get(0);
				if (price.compareTo(product.getSalePrice()) == 1) {
					flag = EbookConstants.REPEAT_NEWBOOK;
				} else if (price.compareTo(product.getSalePrice()) == 0) {
					flag = EbookConstants.REPEAT_DOUBLE;
				} else if (price.compareTo(product.getSalePrice()) == -1) {
					flag = EbookConstants.REPEAT_NEWDOUBLE;
				}
			}
		}
		return flag;
	}

	/**
	 * 判断其它信息是否都相同
	 * @param book
	 * @param oldbook
	 * @return
	 */
	private boolean otherMessageEquals(Book book, Book oldbook) {
		return book.getPageCount().equals(oldbook.getPageCount())
				&& book.getPublishDate().equals(oldbook.getPublishDate())
				&& book.getLibraryBookCateogry().equals(
						oldbook.getLibraryBookCateogry())
				&& book.getIntroduction().equals(oldbook.getIntroduction())
				&& book.getTableOfContents().equals(
						oldbook.getTableOfContents());

	}

	/**
	 * 检查其它信息是否有相同
	 * @param oldbook
	 * @return
	 */
	private boolean otherMessageOrEquals(Book oldbook) {
		return oldbook.getPageCount() == null
				|| Integer.valueOf(0).equals(oldbook.getPageCount())
				|| oldbook.getPublishDate() == null
				|| StringUtils.isBlank(oldbook.getLibraryBookCateogry())
				|| StringUtils.isBlank(oldbook.getIntroduction())
				|| StringUtils.isBlank(oldbook.getTableOfContents());
	}
	@Override
	public void save(Book book) {
		bookDao.save(book);
	}

	@Override
	public Book get(Long bookId) {
		return bookDao.get(bookId);
	}

}
