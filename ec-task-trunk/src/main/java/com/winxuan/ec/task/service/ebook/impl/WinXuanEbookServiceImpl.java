package com.winxuan.ec.task.service.ebook.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winxuan.ec.task.dao.ebook.EbookChangeDao;
import com.winxuan.ec.task.model.ebook.EbookChange;
import com.winxuan.ec.task.service.ebook.OperateBookService;
import com.winxuan.ec.task.service.ebook.WinXuanEbookService;
/**
 * 将9月新商品匹配添加到文轩相关数据库实现类
 * @author guanxingjiang
 *
 */
@Service("winXuanEbookService")
public class WinXuanEbookServiceImpl implements WinXuanEbookService {
	private static final Logger LOG = Logger.getLogger(WinXuanEbookServiceImpl.class);
	
	@Autowired
    private EbookChangeDao ebookChangeDao; 
	@Autowired
	private OperateBookService operateBookService;
	
	@Override
	/**
	 * 获取中间表数据    数据导入入口方法
	 * 判断storeFlag，1：新增数据 2：仅修改封面表 上架判定无则新增 3：上架判定有则修改 无则新增 4：下架判定 有下架
	 */
	public void getBookChangeList(){
		
			List<EbookChange> ebookChanges = ebookChangeDao.getEbooks();	
			for(EbookChange ebook : ebookChanges){	
				try {
					operateBookService.operateBook(ebook);
			        }
		         catch (Exception e) {
        	         continue;
	             	}
		       	}
	}	 
	
    

}
