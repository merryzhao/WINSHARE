package com.winxuan.ec.task.service.ebook.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.ec.task.model.ebook.Book;
import com.winxuan.ec.task.model.ebook.BusinessLog;
import com.winxuan.ec.task.model.ebook.Ebook;
import com.winxuan.ec.task.model.ebook.Vendor;
import com.winxuan.ec.task.service.ebook.BookService;
import com.winxuan.ec.task.service.ebook.BusinessLogService;
import com.winxuan.ec.task.service.ebook.EbookUploadService;
import com.winxuan.ec.task.service.ebook.ProductService;
import com.winxuan.ec.task.service.ebook.VendorService;
import com.winxuan.ec.task.support.utils.EbookConstants;
import com.winxuan.ec.task.support.utils.FileUtil;
import com.winxuan.ec.task.support.utils.ToolsUtil;
import com.winxuan.ec.task.support.utils.XmlParser;

/**
 * 电子书上传实现类
 * @author luosh
 *
 */
@Service("ebookUploadService")
public class EbookUploadServiceImpl implements EbookUploadService{
	private static final Log LOG = LogFactory.getLog(EbookUploadServiceImpl.class);
	/**
	 * 图书上传的源文件目录
	 */
//	private static final String SOURCE_PATH = "/upload_ebook/";
	private static final String SOURCE_PATH = "F:/upload_tmp1/book/";
	
	/**
	 * 图书补的源文件目录
	 */
	  private static final String REPAIR_PATH = "F:/repair_ebook/book/";
	
	/**
	 * 图书上传成功备份文件目录 67和本机
	 */
	private static final String BOOK_BAKPATH = "H:/wenxuan_book_bak/";
	
	/**
	 * 图书上传的目的服务器目录
	 */

	private static final String DEST_PATH = "Z:/book/";
	private String instoreDate;
	
	@Autowired
	private BookService bookService;
	@Autowired
	private ProductService eproductService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private BusinessLogService businessLogService;
	
	private Date date;
	private Long vendorId;
	private String vendorName;
	private String errorPath;
	private String successPath;
	@Override
	public void uploadEbook() {
		try {
			date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String start = df.format(new Date());
			instoreDate = format.format(date);
			
			LOG.info(start + "  开始扫描：" + SOURCE_PATH);
			initParam(SOURCE_PATH,DEST_PATH);
			repairBook(REPAIR_PATH,DEST_PATH);
			String end = df.format(new Date());
			LOG.info(end +"  "+SOURCE_PATH+ "扫描完成");
		} catch (Exception e) {
			LOG.info(e.getMessage());
		} 
	}
	/**
	 * 新书上传
	 * @param sourcePath
	 * @param destPath
	 */
	private void initParam(String sourcePath,String destPath) {
		File baseDir = new File(sourcePath);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			LOG.info("目录查找失败!");
			return;
		}
		Vendor vendor = null;
		String filePath = "";
		errorPath = BOOK_BAKPATH +  instoreDate + EbookConstants.ERROR_PATH;
		successPath = BOOK_BAKPATH +  instoreDate + EbookConstants.SUCCESS_PATH;
		for (File vendorFile : baseDir.listFiles()) {
			vendorName = vendorFile.getName();
			filePath = vendorFile.getAbsolutePath();
			//供应商处理
			vendor = this.checkVendor(vendorFile);
			if(vendor == null){//供应商不存在时备份该供应商目录
				try {
					FileUtil.copyFolder(filePath, errorPath, true);
				} catch (IOException e) {
					LOG.info(e.getCause());
				}
				continue;
			}
			vendorId = vendor.getVendorID();
			errorPath = BOOK_BAKPATH +  instoreDate + EbookConstants.ERROR_PATH + vendorName + EbookConstants.SPLIT ;
			successPath = BOOK_BAKPATH +  instoreDate + EbookConstants.SUCCESS_PATH + vendorName+ EbookConstants.SPLIT ;
			for (File bookfile : vendorFile.listFiles()) {// 进入 单本书
				if (!bookfile.isDirectory()) {
					continue;
				}
				filePath = bookfile.getAbsolutePath();
				LOG.info("进入："+filePath);
				try{
					parseFile(bookfile,destPath, vendor,filePath,null);// "9787507831498中国华侨在美洲//
				}catch (Exception e) {
					LOG.info(e.getMessage());
					continue;
				}
			}
		}
	}
	
	/**
	 * 补传
	 * @param repairPath
	 * @param bookPath
	 */
	public void repairBook(String repairPath,String bookPath) {
		File baseDir = new File(repairPath);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			LOG.info("目录查找失败!");
			return;
		}
		Vendor vendor = null;
		String filePath = "";
		errorPath = BOOK_BAKPATH +  instoreDate + EbookConstants.ERROR_PATH;
		successPath = BOOK_BAKPATH +  instoreDate + EbookConstants.SUCCESS_PATH;
		for (File vendorFile : baseDir.listFiles()) {
			filePath = vendorFile.getAbsolutePath();
			vendorName = vendorFile.getName();
			//供应商处理
			vendor = this.checkVendor(vendorFile);
			if(vendor == null){//供应商不存在时备份该供应商目录
				try {
					FileUtil.copyFolder(filePath, errorPath, true);
				} catch (IOException e) {
					LOG.info(e.getCause());
				}
				continue;
			}
			vendorId = vendor.getVendorID();
			errorPath = BOOK_BAKPATH +  instoreDate + EbookConstants.ERROR_PATH + vendorName + EbookConstants.SPLIT ;
			successPath = BOOK_BAKPATH +  instoreDate + EbookConstants.SUCCESS_PATH + vendorName+ EbookConstants.SPLIT ;
			for (File bookfile : vendorFile.listFiles()) {// 进入 单本书
				if (!bookfile.isDirectory()) {
					continue;
				}
				filePath = bookfile.getAbsolutePath();
				try{
					parseRepairFile(bookfile,bookPath, vendor,filePath);// "9787507831498中国华侨在美洲//
				}catch (Exception e) {
					LOG.info(e.getCause());
					continue;
				}
			}
		}
	}
	
	/**
	 * 解析供应商信息
	 * @param vendorFile
	 * @param filePath
	 * @return
	 */
	private Vendor checkVendor(File vendorFile){
		if (!vendorFile.isDirectory()) { // 单个供应商
			return null;
		}
		BusinessLog businessLog = new BusinessLog();
		businessLog.setKeyword(vendorFile.getName());
		businessLog.setProgramTag(EbookConstants.PROGRAM_TAG_TWO);
		businessLog.setProgramType(EbookConstants.PROGRAM_TYPE_TWO);
		Long vid;
		Vendor vendor = null;
		
		String discription = "";
		try {
			String vendorId = vendorName.substring(0, vendorName.indexOf("-"));
			vid = Long.valueOf(vendorId);
			vendor = vendorService.get(vid);
			if (vendor == null) {// 数据库中供应商不存在
				discription = "该供应商不存在";
				businessLogService.logInfo(businessLog, errorPath + vendorName + ";" + discription, 
						EbookConstants.CODE_PARSEVENDORID, EbookConstants.STEP_CODE_ONE, EbookConstants.RESULT_STATUS_TWO,EbookConstants.STATUS_ERROR_VENDOR);
				return null;
			} else if (vendor.getDeleteFlag() == 1) {// 数据库中供应商已删
				discription = "该供应商已经删除，请查证";
				businessLogService.logInfo(businessLog, errorPath + vendorName + ";" + discription, 
						EbookConstants.CODE_PARSEVENDORID, EbookConstants.STEP_CODE_ONE, EbookConstants.RESULT_STATUS_TWO,EbookConstants.STATUS_ERROR_VENDOR);
				return null;
			}
		} catch (NumberFormatException ex) {
			LOG.info(ex.getMessage());
			discription = "解析供应商ID失败";
			businessLogService.logInfo(businessLog, errorPath + vendorName + ";" + discription, 
					EbookConstants.CODE_PARSEVENDORID, EbookConstants.STEP_CODE_ONE, EbookConstants.RESULT_STATUS_TWO,EbookConstants.STATUS_ERROR_VENDOR);
			return null;// 二级目录供应商id不是数字
		} catch (IndexOutOfBoundsException ie) {
			LOG.info(ie.getMessage());
			discription = "解析供应商ID失败";
			businessLogService.logInfo(businessLog, errorPath + vendorName + ";" + discription, 
					EbookConstants.CODE_PARSEVENDORID, EbookConstants.STEP_CODE_ONE, EbookConstants.RESULT_STATUS_TWO,EbookConstants.STATUS_ERROR_VENDOR);
			return null;
		}catch(Exception e){
			return null;
		}
		return vendor;
	}

	/**
	 * 解析info.xml文件
	 * @param bookDir 目录文件
	 * @param filePath 目录地址
	 * @param businessLog
	 * @return Ebook
	 */
	private Ebook getXmlData(File bookDir,BusinessLog businessLog) {
		File xmlFile = new File(bookDir.getAbsoluteFile() + "/info.xml");
		Ebook ebook = null;
		if (!xmlFile.isFile()) {
			businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";info.xml文件不存在", 
					EbookConstants.CODE_XMLERROR, EbookConstants.STEP_CODE_THREE, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_NOT_INFO);
			return null;
		}

		try {
			ebook = (Ebook) XmlParser.parse(xmlFile);
		} catch (Exception e) {
			LOG.info(e.getMessage());
			businessLogService.logInfo(businessLog, errorPath + bookDir.getName()  + ";没有正确解析xml文件," + e.getMessage(), 
					EbookConstants.CODE_XMLERROR, EbookConstants.STEP_CODE_THREE, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_INFO);
			return null;
		}
		return ebook;
	}
	
	
	
	/**
	 * 新书上传
	 * @param bookDir
	 * @param destPath
	 * @param vendor
	 * @param filePath
	 * @throws IOException
	 */
	public void parseFile(File bookDir,String destPath, Vendor vendor,String filePath,BusinessLog businessLog) throws IOException {
		if(businessLog == null){
			businessLog = new BusinessLog();
		}
		businessLog.setKeyword(bookDir.getName());
		businessLog.setProgramTag(EbookConstants.PROGRAM_TAG_TWO);
		businessLog.setProgramType(EbookConstants.PROGRAM_TYPE_TWO);
		Book book = null;
		Ebook ebook = this.getXmlData(bookDir, businessLog);
		if(ebook == null){
			FileUtil.copyFolder(filePath, errorPath, true);
			return;
		}
		
		try {
			// 存入数据库
			boolean isvalidIsbn = Ebook.checkEbook(ebook);
			if(!isvalidIsbn){
				businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";ISBN号码有误", 
						EbookConstants.CODE_XMLERROR, EbookConstants.STEP_CODE_THREE, EbookConstants.RESULT_STATUS_TWO,EbookConstants.STATUS_ERROR_ISBN);
				FileUtil.copyFolder(filePath, errorPath, true);
				return;
			}
			book = bookService.uploadBook(ebook, vendor, businessLog);
		} catch (Exception e1) {
			LOG.info(e1.getCause()); 
			businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";" + e1.getMessage(), 
					EbookConstants.CODE_DBINSERT, EbookConstants.STEP_CODE_FOUR, EbookConstants.RESULT_STATUS_TWO,EbookConstants.STATUS_ERROR_INSTORE);
			FileUtil.copyFolder(filePath, errorPath, true);
			return;
		}
		if (book != null) {
			businessLog.setBusinessId(book.getId().toString());
			// 导文件
			String dirName = ToolsUtil.getRangDirName(book.getId().toString());
			File ddir = new File(destPath + dirName);// 服务器1-10000的目录
			if (!ddir.isDirectory()) {
				ddir.mkdirs();
			}
			// 开始上传
			try {
				String mark = "";
				if(!this.uploadCover(mark, bookDir, book, businessLog, ddir, filePath)){
					return;
				}
				if(!this.uploadReaderFile(mark, bookDir, book, businessLog, ddir, filePath)){
					return;
				}
			} catch (IOException e) {
				LOG.info(e.getCause());
				businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";读写源文件时出错;" + e.getMessage(), 
						EbookConstants.CODE_UPLOADFILEERROR, EbookConstants.STEP_CODE_FIVE, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_UPLOADFILE);
				FileUtil.copyFolder(filePath, errorPath, true);
				return;
			}
		}
		this.checkValidProduct(book, ebook, businessLog, bookDir, filePath, vendor);
		
		
	}
	/**
	 * 上架条件检查,并上架
	 * @param book
	 * @param ebook
	 * @param businessLog
	 * @param bookDir
	 * @param filePath
	 * @param vendor
	 * @throws IOException
	 */
	private void checkValidProduct(Book book,Ebook ebook,BusinessLog businessLog,File bookDir,String filePath,Vendor vendor) throws IOException{
		//检查为空字段
		String check = bookService.checkBookEmpltyInfo(book, ebook);
		if(!StringUtils.isBlank(check)){
			businessLogService.logInfo(businessLog,errorPath + bookDir.getName()+check, 
					EbookConstants.CODE_CHECKBOOKINFO,EbookConstants.STEP_CODE_FOUR,EbookConstants.RESULT_STATUS_TWO,EbookConstants.STATUS_ERROR_NOTEMPTY);
			FileUtil.copyFolder(filePath, errorPath, true);
			return;
		}
		if (!Ebook.checkEbook(ebook)) {
			businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";ISBN号码有误", 
					EbookConstants.CODE_XMLERROR, EbookConstants.STEP_CODE_THREE, EbookConstants.RESULT_STATUS_TWO,EbookConstants.STATUS_ERROR_ISBN);
			FileUtil.copyFolder(filePath, errorPath, true);
			return;
		}
		
		//检查重复
		List<BusinessLog> olist = businessLogService.find(bookDir.getName(), 1, vendorId);
		BigDecimal price  = BigDecimal.valueOf(Double.valueOf(ebook.getEbookListPrice()));
		String discription = "";
		if (olist != null && olist.size() != 0) {
			Book oldbook = bookService.get(Long.valueOf(businessLog.getBusinessId()));
			int repeatStatus = bookService.checkRepeatBook(book,oldbook,price);
			
			if(repeatStatus ==EbookConstants.REPEAT_NEWBOOK){
				//新书上架,老书下架
				discription = successPath + bookDir.getName() + ";上传成功,老书下架bookid:"+ oldbook.getId();
				eproductService.validProduct(book,oldbook,businessLog, discription);// 上架操作
				FileUtil.copyFolder(filePath, successPath, true);
				return;
			}else if(repeatStatus == EbookConstants.REPEAT_DOUBLE || repeatStatus == EbookConstants.REPEAT_NEWDOUBLE){//重复图书
				businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";该图书已经上传一次", 
						EbookConstants.CODE_CHECKBOOKINFO, EbookConstants.STEP_CODE_TWO, EbookConstants.RESULT_STATUS_TWO,EbookConstants.STATUS_ERROR_DOUBLE);
				FileUtil.copyFolder(filePath, errorPath, true);
				return;
			}
		}
		//不重复
		discription = successPath + bookDir.getName() + ";上传成功";
		eproductService.validProduct(book,null,businessLog, discription);// 上架操作
		FileUtil.copyFolder(filePath, successPath, true);
	}

	/**
	 * 是否已入库
	 * @param statusCode
	 * @return
	 */
	private boolean isValidStore(Integer statusCode){
		return statusCode.equals(EbookConstants.STATUS_ERROR_NOT_INFO) 
		|| statusCode.equals(EbookConstants.STATUS_ERROR_INFO) 
		|| statusCode.equals(EbookConstants.STATUS_ERROR_ISBN)
		|| statusCode.equals(EbookConstants.STATUS_ERROR_INSTORE);
	}
	/**
	 * 上传封面是否出错
	 * @param statusCode
	 * @return
	 */
	private boolean isValidCover(Integer statusCode){
		return statusCode.equals(EbookConstants.STATUS_ERROR_NOCOVER) 
		|| statusCode.equals(EbookConstants.STATUS_ERROR_EXCEED_COVER);
	}
	/**
	 * 上传阅读文件是否出错
	 * @param statusCode
	 * @return
	 */
	private boolean isValidReaderFile(Integer statusCode){
		return statusCode.equals(EbookConstants.STATUS_ERROR_NOPDF) 
		|| statusCode.equals(EbookConstants.STATUS_ERROR_NOPDFEPUB) 
		|| statusCode.equals(EbookConstants.STATUS_ERROR_EXCEED_FILE) 
		|| statusCode.equals(EbookConstants.STATUS_ERROR_DEFFER_FILE);
	}
	/**
	 * 上传封面结果处理
	 * @param mark
	 * @param bookDir
	 * @param book
	 * @param businessLog
	 * @param ddir
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private boolean uploadCover(String mark,File bookDir,Book book,BusinessLog businessLog,File ddir,String filePath) throws IOException{
		try{
			mark = uploadCoverFiles(bookDir,book.getId().toString(), ddir, book);
			if ("封面大于2M".equals(mark)) {
				businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";" + mark, 
						EbookConstants.CODE_CHECKESFILES, EbookConstants.STEP_CODE_FIVE, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_EXCEED_COVER);
				FileUtil.copyFolder(filePath, errorPath, true);
				return false;
			}
			if ("原图不存在".equals(mark)) {
				businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";" + mark, 
						EbookConstants.CODE_CHECKESFILES, EbookConstants.STEP_CODE_FIVE, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_NOCOVER);
				FileUtil.copyFolder(filePath, errorPath, true);
				return false;
			}
			
			if(Integer.valueOf(0).equals(book.getHasCover())){
				businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";无封面", 
						EbookConstants.CODE_CHECKESFILES, EbookConstants.STEP_CODE_FIVE, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_NOCOVER);
				FileUtil.copyFolder(filePath, errorPath, true);
				return false;
			}
		} catch (IOException e) {
			LOG.info(e.getMessage());
			businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";读写源文件时出错;" + e.getMessage(), 
					EbookConstants.CODE_UPLOADFILEERROR, EbookConstants.STEP_CODE_FIVE, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_UPLOADFILE);
			FileUtil.copyFolder(filePath, errorPath, true);
			return false;
		}
		return true;
	}
	/**
	 * 上传阅读文件及结果处理
	 * @param mark
	 * @param bookDir
	 * @param book
	 * @param businessLog
	 * @param ddir
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private boolean uploadReaderFile(String mark,File bookDir,Book book,BusinessLog businessLog,File ddir,String filePath) throws IOException{
		try{
			mark = handleLocalFiles(bookDir,book.getId().toString(), ddir, book);
			if ("noPdfEpub".equals(mark) || "".equals(mark) || "noPdf".equals(mark)) {
				businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";" + mark + "、源文件不存在", 
						EbookConstants.CODE_CHECKESFILES, EbookConstants.STEP_CODE_FIVE, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_NOPDFEPUB);
				FileUtil.copyFolder(filePath, errorPath, true);
				return false;
			}

			book.setHasEpub(1);
			if ("noEpub".equals(mark)){
				book.setHasEpub(0);
			}
			if (!"noPdf".equals(mark) || !"noPdfEpub".equals(mark)){
				book.setFileSize(Long.valueOf(FileUtil.getFileSize(filePath + "/cp.pdf")));
			}
			bookService.save(book);
			if (!compareFiles(filePath, ddir + EbookConstants.SPLIT + book.getId(), mark)) {
				businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";源文件与服务器文件不一致。", 
						EbookConstants.CODE_CHECKESFILES, EbookConstants.STEP_CODE_FIX, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_DEFFER_FILE);
				FileUtil.copyFolder(filePath, errorPath, true);
				return false;
			}
			
			if ("pdf大于500M".equals(mark) || "epub大于500M".equals(mark)) {
				businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";" + mark, 
						EbookConstants.CODE_CHECKESFILES, EbookConstants.STEP_CODE_FIVE, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_EXCEED_FILE);
				FileUtil.copyFolder(filePath, errorPath, true);
				return false;
			}
		} catch (IOException e) {
			LOG.info(e.getMessage());
			businessLogService.logInfo(businessLog, errorPath + bookDir.getName() + ";读写源文件时出错;" + e.getMessage(), 
					EbookConstants.CODE_UPLOADFILEERROR, EbookConstants.STEP_CODE_FIVE, EbookConstants.RESULT_STATUS_TWO, EbookConstants.STATUS_ERROR_UPLOADFILE);
			FileUtil.copyFolder(filePath, errorPath, true);
			return false;
		}
		return true;
	}
	/**
	 * 补传
	 * @param bookDir
	 * @param destPath
	 * @param vendor
	 * @param filePath
	 * @throws IOException
	 */
	public void parseRepairFile(File bookDir,String destPath, Vendor vendor,String filePath) throws IOException {
		BusinessLog businessLog = new BusinessLog();
		businessLog.setKeyword(bookDir.getName());
		businessLog.setProgramTag(EbookConstants.PROGRAM_TAG_TWO);
		businessLog.setProgramType(EbookConstants.PROGRAM_TYPE_TWO);
		
		List<BusinessLog> businessLogList = businessLogService.find(bookDir.getName(), 2);
		if (businessLogList != null && businessLogList.size() != 0){
			businessLog = businessLogList.get(0);
		}
		Integer statusCode = businessLog.getStatusCode();
		if(null == statusCode){
			return;
		}
		Book book = null;
		if(isValidStore(statusCode)){
			this.parseFile(bookDir, destPath, vendor, filePath,businessLog);
			return;
		}
		if (businessLog.getBusinessId() != null && statusCode != null) {
			book = bookService.get(Long.valueOf(businessLog.getBusinessId()));
			if(book == null){
				return;
			}
			String dirName = ToolsUtil.getRangDirName(book.getId().toString());
			File ddir = new File(destPath + dirName);// 服务器1-10000的目录
			if (!ddir.isDirectory()) {
				ddir.mkdirs();
			}
			String mark = "";
			Ebook ebook = this.getXmlData(bookDir, businessLog);
			if(isValidCover(statusCode) || statusCode.equals(EbookConstants.STATUS_ERROR_UPLOADFILE)){
				if(!this.uploadCover(mark, bookDir, book, businessLog, ddir, filePath)){
					return;
				}
				if(!this.uploadReaderFile(mark, bookDir, book, businessLog, ddir, filePath)){
					return;
				}
			}
			else if(isValidReaderFile(statusCode)){
				if(!this.uploadReaderFile(mark, bookDir, book, businessLog, ddir, filePath)){
					return;
				}
			}else if(statusCode.equals(EbookConstants.STATUS_ERROR_NOTEMPTY)){
				book = bookService.createBook(ebook,book);
			}
			bookService.save(book);
			this.checkValidProduct(book, ebook, businessLog, bookDir, filePath, vendor);
		}
	}
	
	/**
	 * 比较服务器文件与源文件是否一致
	 * @param oldPath
	 * @param bookPath
	 * @param mark
	 * @return
	 */
	private boolean compareFiles(String oldPath, String bookPath, String mark) {
		try {
			if("upBookSuccess".equals(mark)){
				return FileUtil.compareFileSize(oldPath + "/cp.pdf", bookPath + "/original.pdf")
						&& FileUtil.compareFileSize(oldPath + "/cp.epub", bookPath + "/original.epub");
			}else if("noPdf".equals(mark)){
				return FileUtil.compareFileSize(oldPath + "/cp.epub", bookPath + "/original.epub");
			}else if("noEpub".equals(mark)){
				return FileUtil.compareFileSize(oldPath + "/cp.pdf", bookPath + "/original.pdf");
			}else{
				return true;
			}
		} catch (Exception e){
			LOG.info(e.getMessage());
			return false;
		}
	}

	/**
	 * 上传封面图片
	 * @param file
	 * @param bookId
	 * @param ddir
	 * @param book
	 * @return
	 * @throws IOException
	 */
	private String uploadCoverFiles(File file, String bookId, File ddir,Book book) throws IOException{
		String mark = "";
		File coverFile = new File(file.getAbsolutePath() + "/cover");

		File bookFile = new File(ddir.getAbsolutePath() + EbookConstants.SPLIT + bookId);
		if (!bookFile.isDirectory() && !bookFile.mkdir()){
			return "";
		}
		File original = new File(coverFile.getAbsolutePath() + EbookConstants.SPLIT + "original.jpg");
		if(!original.exists()){
			return "原图不存在";
		}
		//封面文件导入
		if(!coverFile.exists()||coverFile.list().length==0){
			//生成封面
			book.setHasCover(0);
		}else if(coverFile.list().length==3){
			book.setHasCover(1);
			if(FileUtil.getFileSize(coverFile.getAbsolutePath() + EbookConstants.SPLIT + "big.jpg") > 1024*1024*2
					||FileUtil.getFileSize(coverFile.getAbsolutePath() + EbookConstants.SPLIT + "original.jpg") > 1024*1024*2
					||FileUtil.getFileSize(coverFile.getAbsolutePath() + EbookConstants.SPLIT + "middle.jpg") > 1024*1024*2){
				return "封面大于2M";
			}
			FileUtil.copyFolder(coverFile.getAbsolutePath(), bookFile.getAbsolutePath(),false);
		}else if(coverFile.list().length>=4){
			book.setHasCover(1);
			if(FileUtil.getFileSize(coverFile.getAbsolutePath() + EbookConstants.SPLIT + "big.jpg") > 1024*1024*2
					||FileUtil.getFileSize(coverFile.getAbsolutePath() + EbookConstants.SPLIT + "original.jpg") > 1024*1024*2
					||FileUtil.getFileSize(coverFile.getAbsolutePath() + EbookConstants.SPLIT + "middle.jpg") > 1024*1024*2
					||FileUtil.getFileSize(coverFile.getAbsolutePath() + EbookConstants.SPLIT + "small.jpg") > 1024*1024*2){
				return "封面大于2M";
			}
			FileUtil.copyFolder(coverFile.getAbsolutePath(), bookFile.getAbsolutePath(),false);
		}else{
			book.setHasCover(0);
		}
		bookService.save(book);
		return mark;
	}
	/**
	 * 上传pdf、epub、info.xml文件
	 * noEpub没有epub noPdf没有pdf noPdfEpub都没有 upBookSuccess都有
	 */ 
	private String handleLocalFiles(File file, String bookId, File ddir,Book book)
			throws IOException {
		String mark = "";
		boolean pdfrename = false;
		boolean epubrename = false;
		File filePdfTemp = new File(file.getAbsolutePath() + "/cp.pdf");
		File fileEpubTemp = new File(file.getAbsolutePath() + "/cp.epub");
		File xmlFile = new File(file.getAbsolutePath() + "/info.xml");

		File bookFile = new File(ddir.getAbsolutePath() + EbookConstants.SPLIT + bookId);
		if (!bookFile.isDirectory() && !bookFile.mkdir()){
			return "";
		}
		
		if (filePdfTemp.isFile()) {
			pdfrename = true;
			if(FileUtil.getFileSize(filePdfTemp.getAbsolutePath()) > 1024*1024*500){
				return "pdf大于500M";
			}
		}
		if (fileEpubTemp.isFile()) {
			epubrename = true;
			if(FileUtil.getFileSize(fileEpubTemp.getAbsolutePath()) > 1024*1024*500){
				return "epub大于500M";
			}
		}
		if (!pdfrename && !epubrename) {
			mark = "noPdfEpub";
			return mark;
		}else if (!pdfrename) {
			FileUtil.copyFile(fileEpubTemp.getAbsolutePath(), bookFile.getAbsoluteFile()+  "/original.epub", false);
			mark = "noPdf";
		} else if (!epubrename) {
			FileUtil.copyFile(filePdfTemp.getAbsolutePath(), bookFile.getAbsoluteFile() + "/original.pdf", false);
			LOG.info(filePdfTemp.getAbsolutePath());
			mark = "noEpub";
		} else {
			FileUtil.copyFile(filePdfTemp.getAbsolutePath(), bookFile.getAbsoluteFile() + "/original.pdf", false);
			FileUtil.copyFile(fileEpubTemp.getAbsolutePath(), bookFile.getAbsoluteFile() +  "/original.epub", false);
			mark = "upBookSuccess";
		}
		FileUtil.copyFile(xmlFile.getAbsolutePath(), bookFile.getAbsoluteFile() + "/info.xml", false);
		return mark;
	}
}
