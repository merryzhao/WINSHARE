package com.winxuan.ec.task.support.utils;

/**
 * 电子书上架常量类
 * @author luosh
 *
 */
public class EbookConstants {

	public static final Integer PROGRAM_TYPE_ONE = 1;
	public static final Integer PROGRAM_TYPE_TWO = 2;
	
	public static final Integer PROGRAM_TAG_ONE = 1;
	public static final Integer PROGRAM_TAG_TWO = 2;
	
	//入库步骤
	public static final Integer STEP_CODE_ONE = 1;
	public static final Integer STEP_CODE_TWO = 2;
	public static final Integer STEP_CODE_THREE = 3;
	public static final Integer STEP_CODE_FOUR = 4;
	public static final Integer STEP_CODE_FIVE = 5;
	public static final Integer STEP_CODE_FIX = 6;
	
	//日志记录结果状态
	public static final Integer RESULT_STATUS_ONE = 1;
	public static final Integer RESULT_STATUS_TWO = 2;
	
	//日志记录状态
	/**
	 * 供应商不存在、供应商已经删除、解析供应商ID失败:1
	 */
	public static final Integer STATUS_ERROR_VENDOR = 1;
	/**
	 * info.xml文件不存在:2
	 */
	public static final Integer STATUS_ERROR_NOT_INFO = 2;
	/**
	 * 没有正确解析info.xml:3
	 */
	public static final Integer STATUS_ERROR_INFO = 3;
	/**
	 * ISBN号码有误:4
	 */
	public static final Integer STATUS_ERROR_ISBN = 4;
	/**
	 * 入库出错:5
	 */
	public static final Integer STATUS_ERROR_INSTORE = 5;
	/**
	 * 无封面:6
	 */
	public static final Integer STATUS_ERROR_NOCOVER = 6;
	/**
	 * 封面不能超过2M:7
	 */
	public static final Integer STATUS_ERROR_EXCEED_COVER = 7;
	/**
	 * 无pdf:8
	 */
	public static final Integer STATUS_ERROR_NOPDF = 8;
	/**
	 * 无pdf和epub:9
	 */
	public static final Integer STATUS_ERROR_NOPDFEPUB = 9;
	/**
	 * 文件不能超过500M:10
	 */
	public static final Integer STATUS_ERROR_EXCEED_FILE = 10;
	/**
	 * 源文件与服务器文件不一致:11
	 */
	public static final Integer STATUS_ERROR_DEFFER_FILE = 11;
	/**
	 * 文件上传失败:12
	 */
	public static final Integer STATUS_ERROR_UPLOADFILE = 12;
	/**
	 * ***不能为空:13
	 */
	public static final Integer STATUS_ERROR_NOTEMPTY = 13;
	/**
	 * 该图书已经上传一次:14
	 */
	public static final Integer STATUS_ERROR_DOUBLE = 14;
	/**
	 * 信息上传成功，下架重复图书bookId:***:15
	 */
	public static final Integer STATUS_SUCCESS_DOWN = 15;
	
	//日志记录code
	public static final String CODE_SUCEESS = "success";
	public static final String CODE_PARSEVENDORID = "parseVendorId";
	public static final String CODE_XMLERROR = "xmlError";
	public static final String CODE_DBINSERT = "dbinsert";
	public static final String CODE_COVERERROR = "coverError";
	public static final String CODE_CHECKESFILES = "checkFiles";
	public static final String CODE_UPLOADFILEERROR = "upLoadFileError";
	public static final String CODE_CHECKBOOKINFO = "checkBookInfo";

	public static final String ERROR_PATH = "/error/";
	public static final String SUCCESS_PATH = "/success/";
	public static final String SPLIT = "/";
    
	//入库标识
	public static final Integer STORE_FLAG_DRMFINISH = 1;
	public static final Integer STORE_FLAG_ALTERIMAGE = 2;
	public static final Integer STORE_FLAG_ONSHELF = 3;
	public static final Integer STORE_FLAG_OFFSHELF = 4 ;
	public static final Integer STORE_FLAG_BASE_INFO_CHANGE = 5 ;
	

	
	/**
	 * 下架原因
	 */
	public static final Long	INVALID_CODE_ID = 100012L;
	/**
	 * 不重复:0
	 */
	public static final int REPEAT_NOT = 0;
	/**
	 * 新书上架:1
	 */
	public static final int REPEAT_NEWBOOK = 1;
	/**
	 * 必填字段重复:2
	 */
	public static final int REPEAT_OTHER = 2;
	/**
	 * 重复图书:3
	 */
	public static final int REPEAT_DOUBLE = 3;
	/**
	 * 老书上架，新书重复:4
	 */
	public static final int REPEAT_NEWDOUBLE = 4;
	

	//meta属性编号
	public static final Long PUBLISHVERSION = 5L;
	public static final Long PRINTEDCOUNT = 6L;
	public static final Long PRINTEDDATE = 8L;
	public static final Long WORDCOUNT = 3L;
	public static final Long FOLIO = 1L;
	public static final Long ISBN = 44L;
	public static final Long HASEPUB = 114L;
	public static final Long ISFREE = 115L;
	public static final Long PREVIEWPAGERANGE = 116L;
	public static final Long PAGECOUNT = 117L;
	public static final Long INTRODUCTION = 10L;
	public static final Long AUTHORINTRODUCTION = 14L;
	public static final Long EDITORCOMMENT = 18L;
	public static final Long TABLEOFCONTENTS = 12L;

}
