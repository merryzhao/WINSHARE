package com.winxuan.ec.task.support.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author luosh
 *
 */
public class ClassMethodUtil {
	private static final Log LOG = LogFactory.getLog(ClassMethodUtil.class);
	private static final  Map<String,String> METHOD_MAP = new HashMap<String,String>();
	private static String classpath="com.winxuan.ec.task.model.ebook.";
	public static final void setMethodMap() { 
		METHOD_MAP.put("/start/ebooks", "Ebook");
		METHOD_MAP.put("/start/ebooks/baseInfo/categorys", "Ebook/getCategorys");
		METHOD_MAP.put("/start/ebooks/baseInfo/categorys/category", "Category");
		METHOD_MAP.put("/start/ebooks/extendInfos", "Ebook/getExtendInfos");
		METHOD_MAP.put("/start/ebooks/extendInfos/extendInfo", "ExtendInfo");
		METHOD_MAP.put("/start/ebooks/files", "Ebook/getFiles");
		METHOD_MAP.put("/start/ebooks/files/file", "OriginalFile");
		METHOD_MAP.put("/start/ebooks/catalog", "Ebook/getChapters");
		METHOD_MAP.put("/start/ebooks/catalog/chapter", "Chapter");
		
		METHOD_MAP.put("/end/ebooks/baseInfo/bookName", "Ebook/setBookName");
		METHOD_MAP.put("/end/ebooks/baseInfo/seriesName", "Ebook/setSeriesName");
		METHOD_MAP.put("/end/ebooks/baseInfo/ISBN", "Ebook/setIsbn");
		METHOD_MAP.put("/end/ebooks/baseInfo/sizeFormat", "Ebook/setSizeFormat");
		METHOD_MAP.put("/end/ebooks/baseInfo/Edition", "Ebook/setEdition");
		METHOD_MAP.put("/end/ebooks/baseInfo/printingTimes", "Ebook/setPrintingTimes");
		METHOD_MAP.put("/end/ebooks/baseInfo/pageCount", "Ebook/setPageCount");
		METHOD_MAP.put("/end/ebooks/baseInfo/wordCount", "Ebook/setWordCount");
		METHOD_MAP.put("/end/ebooks/baseInfo/copyCount", "Ebook/setCopyCount");
		METHOD_MAP.put("/end/ebooks/baseInfo/publisher", "Ebook/setPublisher");
		METHOD_MAP.put("/end/ebooks/baseInfo/copyRightOwner", "Ebook/setCopyRightOwner");
		METHOD_MAP.put("/end/ebooks/baseInfo/thisEditionPublishingDate", "Ebook/setThisEditionPublishingDate");
		METHOD_MAP.put("/end/ebooks/baseInfo/author", "Ebook/setAuthor");
		METHOD_MAP.put("/end/ebooks/baseInfo/authorIntroduction", "Ebook/setAuthorIntroduction");
		METHOD_MAP.put("/end/ebooks/baseInfo/translator", "Ebook/setTranslator");
		METHOD_MAP.put("/end/ebooks/baseInfo/language", "Ebook/setLanguage");
		METHOD_MAP.put("/end/ebooks/baseInfo/listPrice", "Ebook/setListPrice");
		METHOD_MAP.put("/end/ebooks/baseInfo/ebookListPrice", "Ebook/setEbookListPrice");
		METHOD_MAP.put("/end/ebooks/baseInfo/paperMaterial", "Ebook/setPaperMaterial");
		METHOD_MAP.put("/end/ebooks/baseInfo/pack", "Ebook/setPack");
		METHOD_MAP.put("/end/ebooks/baseInfo/bookmakerRecommend", "Ebook/setBookmakerRecommend");
		METHOD_MAP.put("/end/ebooks/baseInfo/mediaRecommend", "Ebook/setMediaRecommend");
		METHOD_MAP.put("/end/ebooks/baseInfo/keyWord", "Ebook/setKeyWord");
		METHOD_MAP.put("/end/ebooks/baseInfo/contentsAbstract", "Ebook/setContentsAbstract");
		METHOD_MAP.put("/end/ebooks/baseInfo/isOriginal", "Ebook/setIsOriginal");//原创标记
		METHOD_MAP.put("/end/ebooks/baseInfo/type", "Ebook/setType");//期刊标记
		
		METHOD_MAP.put("/end/ebooks/baseInfo/categorys", "Ebook/setCategorys");
		METHOD_MAP.put("/end/ebooks/baseInfo/categorys/category/type", "Category/setType");
		METHOD_MAP.put("/end/ebooks/baseInfo/categorys/category", "Category/setTextVlaue");
		METHOD_MAP.put("/add/ebooks/baseInfo/categorys/category", "List/add");//多一个添加方法
	
		METHOD_MAP.put("/end/ebooks/files", "Ebook/setFiles");
		METHOD_MAP.put("/end/ebooks/files/file/type", "OriginalFile/setType");
		METHOD_MAP.put("/end/ebooks/files/file/fileName", "OriginalFile/setFileName");
		METHOD_MAP.put("/end/ebooks/files/file/pageCount", "OriginalFile/setPageCount");
		METHOD_MAP.put("/add/ebooks/files/file", "List/add");
		
		METHOD_MAP.put("/end/ebooks/catalog", "Ebook/setChapters");
		METHOD_MAP.put("/end/ebooks/catalog/chapter/code", "Chapter/setCode");
		METHOD_MAP.put("/end/ebooks/catalog/chapter/page", "Chapter/setPage");
		METHOD_MAP.put("/end/ebooks/catalog/chapter", "Chapter/setTextValue");
		METHOD_MAP.put("/add/ebooks/catalog/chapter", "List/add");//多一个添加方法
		
		METHOD_MAP.put("/end/ebooks/extendInfos", "Ebook/setExtendInfos");
		METHOD_MAP.put("/end/ebooks/extendInfos/extendInfo/type", "ExtendInfo/setType");
		METHOD_MAP.put("/end/ebooks/extendInfos/extendInfo/title", "ExtendInfo/setTitle");
		METHOD_MAP.put("/end/ebooks/extendInfos/extendInfo/author", "ExtendInfo/setAuthor");
		METHOD_MAP.put("/end/ebooks/extendInfos/extendInfo/content", "ExtendInfo/setContent");
		METHOD_MAP.put("/add/ebooks/extendInfos/extendInfo", "List/add");//多一个添加方法
		
		
	}
	public static Map<String, String> getMethodMap() {
		return METHOD_MAP;
	}
	public static String getString(String key){
		return "".equals(METHOD_MAP.get(key)) ? null:METHOD_MAP.get(key);
	}
	
	public static String getClassName(String key){
		if(getString(key)==null){
			return null;
		}
		return getString(key).split("/")[0];
	}
	public static String getMethodName(String key){
		if (getString(key) == null) {
			return null;
		}
		String methodName = null;
		if (getString(key).split("/").length > 1) {
			methodName = getString(key).split("/")[1];
		}
		return methodName;
	}
	public static void main(String[] str){
		setMethodMap();
	}
	public static Class<?> getClass(String key){
		String name = getClassName(key);
		if(name==null){
			return null;
		}
		//ClassLoader  cl = Loader.getSystemClassLoader();
		Class clas;
		try {
			//clas = cl.loadClass(classpath+name);
			clas =Class.forName(classpath+name);
			return clas;
		} catch (ClassNotFoundException e) {
			LOG.info(e.getCause());
			return null;
		}
		
	}
	
	public static Method getMethod(String key,Class<?>... parameterTypes){
		if(getMethodName(key)==null){
			return null;
		}
		try {
			return getClass(key).getMethod(getMethodName(key), parameterTypes);
		} catch (Exception e) {
			LOG.info(e.getMessage());
			return null;
		}
	}
}
