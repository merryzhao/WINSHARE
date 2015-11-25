package com.winxuan.ec.task.service.ebook;

import com.winxuan.ec.task.model.ebook.EbookChange;
/**
 * 处理单本电子书service接口
 * @author guanxingjiang
 *
 */
public interface OperateBookService {
   void operateBook(EbookChange ebook) throws Exception;
}
