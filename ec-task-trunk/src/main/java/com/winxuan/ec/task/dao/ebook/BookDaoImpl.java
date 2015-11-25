package com.winxuan.ec.task.dao.ebook;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.model.ebook.Book;
import com.winxuan.ec.task.model.ebook.BookChapter;
import com.winxuan.ec.task.model.ebook.BookPreface;

/**
 * 电子书DAO
 * @author luosh
 *
 */
@Repository("bookDao")
public class BookDaoImpl implements BookDao{
	private static final Log LOG = LogFactory.getLog(BookDaoImpl.class);
	//根据图书ID查询Book
	private static final String QUERY_SQL = "SELECT BOOK_ID,BOOK_NAME,AUTHOR,PUBLISHER_ID,PUBLISHER_NAME,PUBLISH_DATE,PUBLISH_VERSION,PRINTED_COUNT,PRINTED_DATE,ISBN," +
				"WORD_COUNT,FACT_PAGE_COUNT,PAGE_COUNT,PRINTED_QUANTITY,FOLIO,PAPER_MATERIAL,PACK,INTRODUCTION,AUTHOR_INTRODUCTION," +
				"EDITOR_COMMENT,TABLE_OF_CONTENTS,TAGS,PAPER_PRICE,PREVIEW_PAGE_RANGE,IS_FREE,VENDOR_ID,CREATE_DATETIME,CREATE_BY,UPDATE_DATETIME," +
				"UPDATE_BY,DELETE_FLAG,MCCODE,BOOK_SERIES,FILE_SIZE,PRODUCT_ID,LIBRARY_CATEGORY_CODE,HAS_EPUB,PUBLISHER_CODE," +
				"CATEGORY_ID,HAS_COVER,IS_ORIGINAL,IS_SCAN" +
				" FROM BOOK  WHERE BOOK_ID = ? ";
	//新增Book
	private static final String INSERT_BOOK_SQL = "INSERT INTO BOOK(BOOK_NAME,AUTHOR,PUBLISHER_ID,PUBLISHER_NAME,PUBLISH_DATE,PUBLISH_VERSION,PRINTED_COUNT," +
		"PRINTED_DATE,ISBN,WORD_COUNT,FACT_PAGE_COUNT,PAGE_COUNT,PRINTED_QUANTITY,FOLIO,PAPER_MATERIAL,PACK,INTRODUCTION,AUTHOR_INTRODUCTION," +
		"EDITOR_COMMENT,TABLE_OF_CONTENTS,TAGS,PAPER_PRICE,PREVIEW_PAGE_RANGE,IS_FREE,VENDOR_ID,CREATE_DATETIME,CREATE_BY,UPDATE_DATETIME," +
		"UPDATE_BY,DELETE_FLAG,MCCODE,BOOK_SERIES,FILE_SIZE,LIBRARY_CATEGORY_CODE,HAS_EPUB,PUBLISHER_CODE,CATEGORY_ID,HAS_COVER,IS_ORIGINAL,IS_SCAN,FLAG" +
		") VALUES(?,?,?,?,?,?,?,?,?,?," +
				 "?,?,?,?,?,?,?,?,?,?," +
				 "?,?,?,?,?,sysdate(),?,sysdate(),?,?," +
				 "?,?,?,?,?,?,?,?,?,?,0)";
	private static final String UPDATE_BOOK_SQL = "UPDATE BOOK SET BOOK_NAME=?,AUTHOR=?,PUBLISHER_ID=?,PUBLISHER_NAME=?,PUBLISH_DATE=?,PUBLISH_VERSION=?," +
			"PRINTED_COUNT=?,PRINTED_DATE=?,ISBN=?,WORD_COUNT=?,FACT_PAGE_COUNT=?,PAGE_COUNT=?,PRINTED_QUANTITY=?,FOLIO=?,PAPER_MATERIAL=?," +
			"PACK=?,INTRODUCTION=?,AUTHOR_INTRODUCTION=?,EDITOR_COMMENT=?,TABLE_OF_CONTENTS=?,TAGS=?,PAPER_PRICE=?,PREVIEW_PAGE_RANGE=?,IS_FREE=?," +
			"VENDOR_ID=?,UPDATE_DATETIME=?,UPDATE_BY=?,DELETE_FLAG=?,MCCODE=?,BOOK_SERIES=?,FILE_SIZE=?,LIBRARY_CATEGORY_CODE=?,HAS_EPUB=?," +
			"PUBLISHER_CODE=?,CATEGORY_ID=?,HAS_COVER=?,IS_ORIGINAL=?,IS_SCAN=? WHERE BOOK_ID=?";
	//新增BookPreface
	private static final String INSERT_PREFACE_SQL = "INSERT INTO T_BOOK_PREFACE(" +
			"BOOK_ID,REF_TYPE,REF_BOOK_ID,REF_NAME,AUTHOR,CONTENT,REMARK,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,DELETE_FLAG,QUENCE)" +
			" VALUES(?,?,?,?,?,?,?,sysdate(),?,sysdate(),?,?,?)";
	//新增BookChapter
	private static final String INSERT_CHAPTER_SQL = "INSERT INTO BOOK_CHAPTER(" +
			"CHAPTER_TITLE,BOOK_ID,BEGIN_PAGE,END_PAGE,LEVEL,CHAPTER_SEQUENCE,PARENT_CHAPTER_ID,CHAPTER_INDEX,ANCHOR,CREATE_BY,CREATE_DATETIME,UPDATE_BY,UPDATE_DATETIME,DELETE_FLAG)" +
			"VALUES (?,?,?,?,?,?,?,?,?,?,sysdate(),?,sysdate(),?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplateEbook;
	
	private Book getBook(ResultSet rs) throws NumberFormatException, SQLException{
		if(rs.next()){
			Book book = new Book();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				book.setId(Long.valueOf(rs.getString("BOOK_ID")));
				book.setBookName(rs.getString("BOOK_NAME"));
				book.setAuthor(rs.getString("AUTHOR"));
				if(StringUtils.isNotEmpty(rs.getString("PUBLISHER_ID"))){
					book.setPublisherID(Long.valueOf(rs.getString("PUBLISHER_ID")));
				}
				book.setPublisherName(rs.getString("PUBLISHER_NAME"));
				if(StringUtils.isNotEmpty(rs.getString("PUBLISH_DATE"))){
					book.setPublishDate(format.parse(rs.getString("PUBLISH_DATE")));
				}
				
				book.setPublishVersion(rs.getString("PUBLISH_VERSION"));
				if(StringUtils.isNotEmpty(rs.getString("PRINTED_COUNT"))){
					book.setPrintedCount(Integer.valueOf(rs.getString("PRINTED_COUNT")));
				}
				if(StringUtils.isNotEmpty(rs.getString("PRINTED_DATE"))){
					book.setPrintedDate(format.parse(rs.getString("PRINTED_DATE")));
				}
				book.setIsbn(rs.getString("ISBN"));
				book.setWordCount(rs.getString("WORD_COUNT"));
				if(StringUtils.isNotEmpty(rs.getString("FACT_PAGE_COUNT"))){
					book.setFactPageCount(Integer.valueOf(rs.getString("FACT_PAGE_COUNT")));
				}
				if(StringUtils.isNotEmpty(rs.getString("PAGE_COUNT"))){
					book.setPageCount(Integer.valueOf(rs.getString("PAGE_COUNT")));
				}
				if(StringUtils.isNotEmpty(rs.getString("PRINTED_QUANTITY"))){
					book.setPrintedQuantity(Integer.valueOf(rs.getString("PRINTED_QUANTITY")));
				}
				book.setFolio(rs.getString("FOLIO"));
				book.setPaperMaterial(rs.getString("PAPER_MATERIAL"));
				book.setPack(rs.getString("PACK"));
				book.setIntroduction(rs.getString("INTRODUCTION"));
				book.setAuthorIntroduction(rs.getString("AUTHOR_INTRODUCTION"));
				book.setEditorComment(rs.getString("EDITOR_COMMENT"));
				book.setTableOfContents(rs.getString("TABLE_OF_CONTENTS"));
				book.setTags(rs.getString("TAGS"));
				if(StringUtils.isNotEmpty(rs.getString("PAPER_PRICE"))){
					book.setPaperPrice(BigDecimal.valueOf(Double.valueOf(rs.getString("PAPER_PRICE"))));
				}
				book.setPreviewPageRange(rs.getString("PREVIEW_PAGE_RANGE"));
				if(StringUtils.isNotEmpty(rs.getString("IS_FREE"))){
					book.setIsFree(Integer.valueOf(rs.getString("IS_FREE")));
				}
				if(StringUtils.isNotEmpty(rs.getString("VENDOR_ID"))){
					book.setVendorId(Long.valueOf(rs.getString("VENDOR_ID")));
				}
				book.setCreateDateTime(rs.getDate("CREATE_DATETIME"));
				book.setCreateBy(rs.getString("CREATE_BY"));
				book.setUpdateDateTime(rs.getDate("UPDATE_DATETIME"));
				book.setUpdateBy(rs.getString("UPDATE_BY"));
				if(StringUtils.isNotEmpty(rs.getString("DELETE_FLAG"))){
					book.setDeleteFlag(Integer.valueOf(rs.getString("DELETE_FLAG")));
				}
				book.setMcCode(rs.getString("MCCODE"));
				book.setBookSeries(rs.getString("BOOK_SERIES"));
				if(StringUtils.isNotEmpty(rs.getString("FILE_SIZE"))){
					book.setFileSize(Long.valueOf(rs.getString("FILE_SIZE")));
				}
				if(StringUtils.isNotEmpty(rs.getString("PRODUCT_ID"))){
					book.setProductId(Long.valueOf(rs.getString("PRODUCT_ID")));
				}
				book.setLibraryBookCateogry(rs.getString("LIBRARY_CATEGORY_CODE"));
				
				if(StringUtils.isNotEmpty(rs.getString("HAS_EPUB"))){
					book.setHasEpub(Integer.valueOf(rs.getString("HAS_EPUB")));
				}
				book.setPublisherCode(rs.getString("PUBLISHER_CODE"));
				if(StringUtils.isNotEmpty(rs.getString("CATEGORY_ID"))){
					book.setCategoryId(Long.valueOf(rs.getString("CATEGORY_ID")));
				}
				if(StringUtils.isNotEmpty(rs.getString("HAS_COVER"))){
					book.setHasCover(Integer.valueOf(rs.getString("HAS_COVER")));
				}
				if(StringUtils.isNotEmpty(rs.getString("IS_ORIGINAL"))){
					book.setIsOriginal(Integer.valueOf(rs.getString("IS_ORIGINAL")));
				}
				if(StringUtils.isNotEmpty(rs.getString("IS_SCAN"))){
					book.setIsscan(Integer.valueOf(rs.getString("IS_SCAN")));
				}
				return book;
			} catch (ParseException e) {
				LOG.info(e.getCause());
			}
		}
		return null;
	}
	@Override
	public Book get(Long bookId) {
		return (Book) jdbcTemplateEbook.query(QUERY_SQL, new Object[]{bookId}, new ResultSetExtractor<Book>(){
			@Override
			public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
				return getBook(rs);
			}
		});
	}
	private PreparedStatement getBookSmt(Connection conn, Book book) {
		PreparedStatement ps = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			ps = conn.prepareStatement(INSERT_BOOK_SQL,Statement.RETURN_GENERATED_KEYS);
			 ps.setString(1, book.getBookName());
             ps.setString(2, book.getAuthor());
             if( book.getPublisherID() == null){
				 ps.setNull(3,java.sql.Types.BIGINT);
			 }else{
				 ps.setLong(3, book.getPublisherID());
			 }
			 ps.setString(4, book.getPublisherName());
			 ps.setString(5, book.getPublishDate() != null? format.format(book.getPublishDate()): null);
			 ps.setString(6, book.getPublishVersion());
			 if( book.getPrintedCount() == null){
				 ps.setNull(7,java.sql.Types.INTEGER);
			 }else{
				 ps.setInt(7, book.getPrintedCount());
			 }
			 ps.setString(8, book.getPrintedDate() != null? format.format(book.getPrintedDate()): null);
			 ps.setString(9, book.getIsbn());
			 ps.setString(10, book.getWordCount());
			 if( book.getFactPageCount() == null){
				 ps.setNull(11,java.sql.Types.INTEGER);
			 }else{
				 ps.setInt(11, book.getFactPageCount());
			 }
			 if( book.getPageCount() == null){
				 ps.setNull(12,java.sql.Types.INTEGER);
			 }else{
				 ps.setInt(12, book.getPageCount());
			 }
			 if( book.getPrintedQuantity() == null){
				 ps.setNull(13,java.sql.Types.INTEGER);
			 }else{
				 ps.setInt(13, book.getPrintedQuantity());
			 }
			 ps.setString(14, book.getFolio());
			 ps.setString(15, book.getPaperMaterial());
			 ps.setString(16, book.getPack());
			 ps.setString(17, book.getIntroduction());
			 ps.setString(18, book.getAuthorIntroduction());
			 ps.setString(19, book.getEditorComment());
			 ps.setString(20, book.getTableOfContents());
			 ps.setString(21, book.getTags());
			 ps.setBigDecimal(22, book.getPaperPrice());
			 ps.setString(23, book.getPreviewPageRange());
			 ps.setInt(24, book.getIsFree());
			 ps.setLong(25, book.getVendorId());
			 ps.setString(26, book.getCreateBy());
			 ps.setString(27, book.getUpdateBy());
			 ps.setInt(28, book.getDeleteFlag());
			 ps.setString(29, book.getMcCode());
			 ps.setString(30, book.getBookSeries());
			 if( book.getFileSize() == null){
				 ps.setNull(31,java.sql.Types.BIGINT);
			 }else{
				 ps.setLong(31, book.getFileSize());
			 }
			 ps.setString(32, book.getLibraryBookCateogry());
			 ps.setInt(33, book.getHasEpub());
			 ps.setString(34, book.getPublisherCode());
			 if( book.getCategoryId() == null){
				 ps.setNull(35,java.sql.Types.BIGINT);
			 }else{
				 ps.setLong(35, book.getCategoryId());
			 }
			 ps.setInt(36, book.getHasCover() == null ? 0 : book.getHasCover());
			 ps.setInt(37, book.getIsOriginal() == null ? 0 : book.getIsOriginal());
			 ps.setInt(38, book.getIsscan() == null ? 0 : book.getIsscan());
             return ps;
		} catch (SQLException e) {
			LOG.info(e.getCause());
		}
		return ps;
	}
	@Override
	public void save(final Book book) {
		if(book.getId() == null){
	        KeyHolder keyHolder = new GeneratedKeyHolder();
	        jdbcTemplateEbook.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection conn) {
	            	return getBookSmt(conn,book);
	            }
	        }, keyHolder);
	        book.setId(keyHolder.getKey().longValue());
		}
		else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Object[] param = new Object[39];
			 param[0] = book.getBookName();
             param[1] = book.getAuthor();
             param[2] =  book.getPublisherID();
			 param[3] =  book.getPublisherName();
			 param[4] =  book.getPublishDate() != null? format.format(book.getPublishDate()): null;
			 param[5] =  book.getPublishVersion();
			 param[6] =  book.getPrintedCount() == null ? 0 : book.getPrintedCount();
			 param[7] = book.getPrintedDate() != null? format.format(book.getPrintedDate()): null;
			 param[8] =  book.getIsbn();
			 param[9] =  book.getWordCount();
			 param[10] =  book.getFactPageCount() == null?0:book.getFactPageCount();
			 param[11] =  book.getPageCount() == null ? 0 : book.getPageCount();
			 param[12] =  book.getPrintedQuantity()== null?0 : book.getPrintedQuantity();
			 param[13] =  book.getFolio();
			 param[14] =  book.getPaperMaterial();
			 param[15] =  book.getPack();
			 param[16] =  book.getIntroduction();
			 param[17] =  book.getAuthorIntroduction();
			 param[18] =  book.getEditorComment();
			 param[19] =  book.getTableOfContents();
			 param[20] =  book.getTags();
			 param[21] =  book.getPaperPrice();
			 param[22] =  book.getPreviewPageRange();
			 param[23] =  book.getIsFree();
			 param[24] =  book.getVendorId();
			 param[25] =  book.getUpdateDateTime();
			 param[26] =  book.getUpdateBy();
			 param[27] =  book.getDeleteFlag();
			 param[28] =  book.getMcCode();
			 param[29] =  book.getBookSeries();
			 param[30] =  book.getFileSize();
			 param[31] =  book.getLibraryBookCateogry();
			 param[32] =  book.getHasEpub();
			 param[33] =  book.getPublisherCode();
			 param[34] =  book.getCategoryId();
			 param[35] =  book.getHasCover() == null ? 0 : book.getHasCover();
			 param[36] =  book.getIsOriginal() == null ? 0 : book.getIsOriginal();
			 param[37] =  book.getIsscan() == null ? 0 : book.getIsscan();
			 param[38] =  book.getId();
			jdbcTemplateEbook.update(UPDATE_BOOK_SQL, param);
		}
	}

	@Override
	public void save(BookPreface bookPreface) {
		Object[] param = new Object[11];
		param[0] = bookPreface.getBookId();
		param[1] = bookPreface.getRefType();
		param[2] = bookPreface.getRefBookId();
		param[3] = bookPreface.getRefName();
		param[4] = bookPreface.getAuthor();
		param[5] = bookPreface.getContent();
		param[6] = bookPreface.getRemark();
		param[7] = bookPreface.getCreateBy();
		param[8] = bookPreface.getUpdateBy();
		param[9] = bookPreface.getDeleteFlag();
		param[10] = bookPreface.getQuence();
		jdbcTemplateEbook.update(INSERT_PREFACE_SQL, param);
	}

	@Override
	public void save(BookChapter bookChapter) {
		Object[] param = new Object[12];
		param[0] = bookChapter.getChapterTitle();
		param[1] = bookChapter.getBookId();
		param[2] = bookChapter.getBeginPage();
		param[3] = bookChapter.getEndPage();
		param[4] = bookChapter.getLevel();
		param[5] = bookChapter.getChapterSequence();
		param[6] = bookChapter.getParentChapterId();
		param[7] = bookChapter.getChapterIndex();
		param[8] = bookChapter.getAnchor();
		param[9] = bookChapter.getCreateBy();
		param[10] = bookChapter.getUpdateBy();
		param[11] = bookChapter.getDeleteFlag();
		jdbcTemplateEbook.update(INSERT_CHAPTER_SQL, param);
	}
}
