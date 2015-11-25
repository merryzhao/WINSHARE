package com.winxuan.ec.task.service.ebook;

import java.math.BigDecimal;

import com.winxuan.ec.task.model.ebook.Book;
import com.winxuan.ec.task.model.ebook.BusinessLog;
import com.winxuan.ec.task.model.ebook.Ebook;
import com.winxuan.ec.task.model.ebook.Vendor;
/**
 * 
 * @author luosh
 *
 */
public interface BookService {


	Book uploadBook(Ebook ebook, Vendor vendor, BusinessLog businessLog)throws Exception ;
	/**
	 * 检查图书信息是否符合规范。
	 * 必填字段：书名、ISBN、页数、电子书价格、作者、出版社、出版时间、中图法分类
	 * （原创需要在xml文件中标记出来，ISBN、出版社、中图法可以没有）、内容简介、目录；
	 * @param newbook
	 * @return
	 */
	String checkBookEmpltyInfo(Book newbook,Ebook ebook);
	/**
	 * 基本条件：书名、ISBN、作者、供应商和出版社是否一致
	 * 附加条件：基本条件都一致时：系统匹配上架必备字段中除基本条件的字段外的字段：页数、出版时间、中图法分类
	 * （原创需要在xml文件中标记出来，ISBN、出版社、中图法可以没有）、内容简介、目录；
	 * 保留信息最全的那条数据，另一条为上传失败并写明原因
	 * 其它条件：如以上两个条件都一致时，取价格最高的那条；另一条为上传失败并写明原因<p>
	 * flag:0:不重复；1：新书上架;3：新书上架；4：重复图书；5：老书上架，新书重复
	 */
	int checkRepeatBook(Book book, Book oldbook,BigDecimal price);
	
	Book createBook(Ebook ebook,Book book);
	void save(Book book);
	Book get(Long bookId);

}
