package com.winxuan.ec.front.controller.cm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 
 * @author cast911 组装导航弹出数据
 */
public class NavAsse {
	private static final Log LOG =LogFactory.getLog(NavAsse.class);
	private static final String EBOOK_PATH = "ebookjson.txt";
	private static final String WINXUAN_URL = "http://www.winxuan.com/";
	private static final String ITEMS = "items";
	private static final String TITLE = "title";
	private static final String HREF = "href";
	private static final String NAME = "name";

	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public JSONArray asseDate() throws JSONException {
		JSONArray jsonArray = new JSONArray();
		this.bookjson(jsonArray);
		this.bookmorejson(jsonArray);
		this.mediajson(jsonArray);
		this.malljson(jsonArray);
		this.ebookjson(jsonArray);
		return jsonArray;
	}
	/**
	 * 电子书json
	 * 
	 * @param ctx
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private JSONArray ebookjson(JSONArray jsonArray) throws JSONException {
		// {title:"",href:"",item:[{title:"",name:"",href:""}]}
		String fileName = NavAsse.class.getClassLoader()
		.getResource(EBOOK_PATH).getFile();
		String ss=readFile(fileName);
		net.sf.json.JSONArray bookarray =  net.sf.json.JSONArray.fromObject(ss);
		jsonArray.put(bookarray);
		return jsonArray;
	}
	
	/**
	 * 影像导航json
	 * 
	 * @param ctx
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private JSONArray mediajson(JSONArray jsonArray) throws JSONException {
		// {title:"",href:"",item:[{title:"",name:"",href:""}]}
		Category media = categoryService.get(Category.MEDIA);
		Set<Category> children = media.getChildren();
		JSONArray bookarray = new JSONArray();
		JSONObject jsonobject = new JSONObject();
		jsonobject.put(TITLE, media.getName());
		jsonobject.put(HREF, WINXUAN_URL + "media/");
		JSONArray item = new JSONArray();
		for (Category category : children) {
			Set<Category> three = category.getChildren();
			for (Category category2 : three) {
				if (category2.getLogicDisplay()) {
					JSONObject mediajson = this.asseJson(category2);
					item.put(mediajson);
				}
			}
		}
		jsonobject.put(ITEMS, item);
		bookarray.put(jsonobject);
		jsonArray.put(bookarray);
		return jsonArray;
	}

	/**
	 * 封装百货导航json
	 * 
	 * @param ctx
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private JSONArray malljson(JSONArray jsonArray) throws JSONException {
		Category mall = categoryService.get(Category.MALL);
		JSONArray bookarray = new JSONArray();
		JSONObject jsonobject = new JSONObject();
		jsonobject.put(TITLE, mall.getName());
		jsonobject.put(HREF, WINXUAN_URL + "book/");
		JSONArray item = new JSONArray();
		Set<Category> children = mall.getChildren();
		for (Category category : children) {
			if (category.getLogicDisplay()) {
				JSONObject malljson = this.asseJson(category);
				item.put(malljson);
			}
		}
		jsonobject.put(ITEMS, item);
		bookarray.put(jsonobject);
		jsonArray.put(bookarray);
		return jsonArray;
	}

	/**
	 * 更多图书
	 * 
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private JSONArray bookmorejson(JSONArray jsonArray) throws JSONException {
		Category book = categoryService.get(Category.BOOK);
		JSONArray bookarray = new JSONArray();
		JSONObject jsonobject = new JSONObject();
		jsonobject.put(TITLE, book.getName());
		jsonobject.put(HREF, WINXUAN_URL + "book/");
		JSONArray item = new JSONArray();
		Set<Category> children = book.getChildren();
		for (Category category : children) {
			if (category.getLogicDisplay()
					&& category.getIndex() > MagicNumber.NINE) {
				JSONObject bookjson = this.asseJson(category);
				item.put(bookjson);
			}
		}
		jsonobject.put(ITEMS, item);
		bookarray.put(jsonobject);
		jsonArray.put(bookarray);
		return jsonArray;
	}

	/**
	 * 封装图书导航array
	 * 
	 * @param ctx
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private JSONArray bookjson(JSONArray jsonArray) throws JSONException {
		Category book = categoryService.get(Category.BOOK);
		Set<Category> children = book.getChildren();
		for (Category category : children) {
			if (category.getLogicDisplay()
					&& category.getIndex() < MagicNumber.NINE) {
				JSONArray bookarray = new JSONArray();
				JSONObject jsonobject = new JSONObject();
				jsonobject.put(TITLE, category.getName());
				jsonobject.put(HREF, category.getCategoryHref());
				JSONArray item = new JSONArray();
				Set<Category> three = category.getChildren();
				for (Category category2 : three) {
					if (category2.getLogicDisplay()) {
						JSONObject itemsObject = this.asseJson(category2);
						item.put(itemsObject);
					}
				}
				jsonobject.put(ITEMS, item);
				bookarray.put(jsonobject);
				jsonArray.put(bookarray);
			}
		}
		return jsonArray;

	}
	
	private JSONObject asseJson(Category category) throws JSONException{
		JSONObject itemsObject = new JSONObject();
		itemsObject.put(TITLE, category.getName());
		itemsObject.put(NAME, category.getName());
		itemsObject.put(HREF, this.getHref(category));
		return itemsObject;
		
	}
	
	
	/**
	 * 获取路径转化
	 * @param category
	 * @return
	 */
	private String getHref(Category category){
		return category.getCategoryHref();
	}
	public  String readFile(String path){
	    File file = new File(path);
	    BufferedReader reader = null;
	    String laststr = "";
	    try {
	     reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
	     String tempString = null;
	     int line = 1;
	     while ((tempString = reader.readLine()) != null) {
	      laststr = laststr+tempString;
	      line++;
	     }
	      reader.close();
	    } catch (IOException e) {
	    	 LOG.error(e);
	    } finally {
	     if (reader != null) {
	      try {
	       reader.close();
	      } 
	      catch (Exception e) {
	    	  LOG.error(e);
		}
	     }
	    }
	    return laststr;
	}
}
