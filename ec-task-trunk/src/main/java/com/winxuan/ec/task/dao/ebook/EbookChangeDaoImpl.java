package com.winxuan.ec.task.dao.ebook;



import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.model.ebook.EbookChange;
import com.winxuan.ec.task.service.ebook.impl.EbookChangeRowMapper;

/**
 * 九月网电子书变更Dao
 * @author luosh
 *
 */
@Repository("ebookChangeDao")
public class EbookChangeDaoImpl implements EbookChangeDao{

	private static final long serialVersionUID = -6963944289940036027L;
	/**
	 * 查询所有书
	 */
	private static final String FIND_EBOOKCHANGE_LIST = "SELECT * FROM T_BOOK_CHANGE WHERE WIN_FLAG = 0 AND BAMBOOK_ENC_FLAG = 2 AND EBOOK_ENC_FLAG = 2 AND STORE_FLAG in(1,2,3,4,5)";
	private static final String GET_EBOOKCHANGE_BYBOOKID = "SELECT * FROM T_BOOK_CHANGE WHERE BOOK_ID=?";
	/**
	 * 修改已经处理标识
	 */
	private static final String SAVE_EBOOKCHANGE = "UPDATE T_BOOK_CHANGE SET WIN_FLAG = ? WHERE ID = ?";
	
	private static final String INSERT_EBOOK_CHANGE_SQL = "INSERT INTO T_BOOK_CHANGE(BOOK_ID,PRODUCT_ID,AUTHOR,BOOK_NAME,ISBN,PUBLISHER_NAME,PUBLISH_DATE,PUBLISH_VERSION," +
			"PRINTED_COUNT,PRINTED_DATE,WORD_COUNT,PAGE_COUNT,PRINTED_QUANTITY,FOLIO,PAPER_MATERIAL,INTRODUCTION,AUTHOR_INTRODUCTION,EDITOR_COMMENT," +
			"TABLE_OF_CONTENTS,PAPER_PRICE,PREVIEW_PAGE_RANGE,HAS_EPUB,IS_FREE,VENDOR_ID,CREATE_DATETIME,UPDATE_DATETIME,UPDATE_BY,FILE_SIZE," +
			"LIBRARY_CATEGORY_CODE,PUBLISHER_CODE,HAS_COVER,STORE_FLAG,BAMBOOK_ENC_FLAG,EBOOK_ENC_FLAG,WIN_FLAG,ELIB_FLAG,SALE_PRICE) " +
			"VALUES (?,?,?,?,?,?,?,?,?,?," +
			"?,?,?,?,?,?,?,?,?,?," +
			"?,?,?,?,sysdate(),sysdate(),?,?,?,?," +
			"?,?,?,?,?,?,?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplateEbook;
	
	@Override
	public List<EbookChange> getEbooks() {
		List<EbookChange> listEbook = jdbcTemplateEbook.query(FIND_EBOOKCHANGE_LIST, new EbookChangeRowMapper());
			return listEbook;
	}

	@Override
	public void saveEbook(int winFlag, Long id) {
		jdbcTemplateEbook.update(SAVE_EBOOKCHANGE, new Object[]{winFlag,id});
	}

	@Override
	public void save(EbookChange ebookChange) {
		Object[] param = new Object[35];
		param[0] = ebookChange.getBookID();
		param[1] = ebookChange.getProductId();
		param[2] = ebookChange.getAuthor();
		param[3] = ebookChange.getBookName();
		param[4] = ebookChange.getIsbn();
		param[5] = ebookChange.getPublisherName();
		param[6] = ebookChange.getPublishDate();
		param[7] = ebookChange.getPublishVersion();
		param[8] = ebookChange.getPrintedCount();
		param[9] = ebookChange.getPrintedDate();
		param[10] = ebookChange.getWordCount();
		param[11] = ebookChange.getPageCount();
		param[12] = ebookChange.getPrintedQuantity();
		param[13] = ebookChange.getFolio();
		param[14] = ebookChange.getPaperMaterial();
		param[15] = ebookChange.getIntroduction();
		param[16] = ebookChange.getAuthorIntroduction();
		param[17] = ebookChange.getEditorComment();
		param[18] = ebookChange.getTableOfContents();
		param[19] = ebookChange.getPaperPrice();
		param[20] = ebookChange.getPreviewPageRange();
		param[21] = ebookChange.getHasEpub();
		param[22] = ebookChange.getIsFree();
		param[23] = ebookChange.getVendorId();
		param[24] = ebookChange.getUpdateBy();
		param[25] = ebookChange.getFileSize();
		param[26] = ebookChange.getLibraryCategoryCode();
		param[27] = ebookChange.getPublisherCode();
		param[28] = ebookChange.getHasCover();
		param[29] = ebookChange.getStoreFlag();
		param[30] = ebookChange.getBambookEncFlag();
		param[31] = ebookChange.getEbookEncFlag();
		param[32] = ebookChange.getWinFlag();
		param[33] = ebookChange.getElibFlag();
		param[34] = ebookChange.getSalePrice();
		jdbcTemplateEbook.update(INSERT_EBOOK_CHANGE_SQL, param);
		
	}

	@Override
	public EbookChange get(Long bookId) {
		List<EbookChange> listEbook = jdbcTemplateEbook.query(GET_EBOOKCHANGE_BYBOOKID, new Object[]{bookId}, new EbookChangeRowMapper());
		if(listEbook != null && !listEbook.isEmpty()){
			return listEbook.get(0);
		}
		return null;
	}

	


}
