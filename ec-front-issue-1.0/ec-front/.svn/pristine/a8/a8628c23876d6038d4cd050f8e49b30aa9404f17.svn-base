package com.winxuan.ec.front.controller.cm;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.support.util.MagicNumber;

/**
 * 
 * @author cast911 组装百货导航
 */
public class MallNavAsse {
	private static final String ITEMS = "items";
	private static final String TITLE = "title";
	private static final String HREF = "href";
	private static final String NAME = "name";

	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
 
	public JSONObject asseDate() throws JSONException {
		Category mall = categoryService.get(Category.MALL);
		Set<Category> children = mall.getChildren();
		JSONObject items = new JSONObject();
		for (Category category : children) {
			if (!category.getLogicDisplay()) {
				continue;
			}
			JSONObject object = new JSONObject();
			object.put(TITLE, category.getName());
			Set<Category> childern2 = category.getChildren();
			JSONArray array =this.addItems(childern2);
			object.put(ITEMS, array);
			items.put(category.getId().toString(), object);
		}

		return items;
	}
	
	/**
	 * 这里危险吗?
	 * @param childern
	 * @return
	 * @throws JSONException
	 */
	private JSONArray addItems(Set<Category> categorys) throws JSONException{
		JSONArray array = new JSONArray();
		for (Category category : categorys) {
			if (!category.getLogicDisplay()) {
				continue;
			}
			JSONObject o = new JSONObject();
			o.put(TITLE, category.getAlias());
			o.put(NAME, category.getAlias());
			o.put(HREF, category.getCategoryHref());
			Set<Category> children = category.getChildren();
			if(children!=null&&category.getChildren().size()>MagicNumber.ZERO){
				o.put(ITEMS, this.addItems(category.getChildren()));
			}			
			array.put(o);
		}
		
		return array;
	}
	

}
