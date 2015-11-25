package com.winxuan.ec.task.service.ebook.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.winxuan.ec.task.model.ebook.EbookChange;
/**
 *  使用EbookChangeRowMapper简化查询封装
 * @author guanxingjiang
 *
 */
public class EbookChangeRowMapper implements RowMapper<EbookChange> {

	@Override
	public EbookChange mapRow(ResultSet rs, int rowNum) throws SQLException {
		EbookChange ebookChange = new EbookChange();
		ebookChange.setId(rs.getLong("id"));
		ebookChange.setBookID(rs.getLong("book_ID"));
		ebookChange.setProductId(rs.getLong("product_Id"));
		ebookChange.setAuthor(rs.getString("author"));
		ebookChange.setBookName(rs.getString("book_Name"));
		ebookChange.setIsbn(rs.getString("isbn"));
		ebookChange.setPublisherName(rs.getString("publisher_Name"));
		ebookChange.setPublishDate(rs.getDate("publish_Date"));
		ebookChange.setPublishVersion(rs.getString("publish_Version"));
		ebookChange.setPrintedCount(rs.getInt("printed_Count"));
		ebookChange.setPrintedDate(rs.getDate("printed_Date"));
		ebookChange.setWordCount(rs.getString("word_Count"));
		ebookChange.setPageCount(rs.getInt("page_Count"));
		ebookChange.setPrintedQuantity(rs.getInt("printed_Quantity"));
		ebookChange.setFolio(rs.getString("folio"));
		ebookChange.setPaperMaterial(rs.getString("paper_Material"));
		ebookChange.setIntroduction(rs.getString("introduction"));
		ebookChange.setAuthorIntroduction(rs.getString("author_Introduction"));
		ebookChange.setEditorComment(rs.getString("editor_Comment"));
        ebookChange.setTableOfContents(rs.getString("table_Of_Contents"));
        ebookChange.setPaperPrice(rs.getBigDecimal("paper_price"));
        ebookChange.setPreviewPageRange(rs.getString("preview_Page_Range"));
        ebookChange.setHasEpub(rs.getInt("has_Epub"));
        ebookChange.setIsFree(rs.getInt("is_Free"));
        ebookChange.setVendorId(rs.getLong("vendor_Id"));
        ebookChange.setCreateDatetime(rs.getDate("create_Datetime"));
        ebookChange.setUpdateDatetime(rs.getDate("update_Datetime"));
        ebookChange.setUpdateBy(rs.getString("update_By"));
        ebookChange.setFileSize( rs.getLong("file_Size"));
        ebookChange.setLibraryCategoryCode(rs.getString("library_Category_Code"));
        ebookChange.setPublisherCode(rs.getString("publisher_Code"));
        ebookChange.setHasCover(rs.getInt("has_Cover"));
        ebookChange.setStoreFlag(rs.getInt("store_Flag"));
        ebookChange.setBambookEncFlag(rs.getInt("bambook_Enc_Flag"));
        ebookChange.setEbookEncFlag(rs.getInt("ebook_Enc_Flag"));
        ebookChange.setWinFlag(rs.getInt("win_Flag"));
        ebookChange.setElibFlag(rs.getInt("elib_Flag"));
        ebookChange.setSalePrice(rs.getBigDecimal("sale_Price"));
        ebookChange.setCategory(rs.getLong("category"));
		return ebookChange;
	}

}
