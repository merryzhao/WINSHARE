package com.winxuan.ec.task.dao.ebook;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.winxuan.ec.task.model.ebook.EProduct;
import com.winxuan.ec.task.model.ebook.ProductItem;
import com.winxuan.ec.task.model.ebook.ProductProcessItem;
import com.winxuan.ec.task.service.ebook.impl.EProductRowMapper;
/**
 * 
 * @author luosh
 *
 */
@Repository("eproductDao")
public class ProductDaoImpl implements ProductDao{
	private static final Log LOG = LogFactory.getLog(PublisherDaoImpl.class);
	
	private static final String GET_PRODUCT_BY_BOOKID = " select p.* from PRODUCT p,PRODUCT_ITEM pi " +
		"where p.PRODUCT_TYPE <> 2 AND p.PRODUCT_ID=pi.PRODUCT_ID AND pi.ENTITY_TYPE=1 AND pi.ENTITY_ID=?";

	private static final String INSERT_PROCESSSITEM_SQL = "INSERT INTO PRODUCT_PROCESS_ITEM(USER_TYPE,PRODUCT_ID,BOOK_ID,FLAG,FLAG_DESCRIPTION," +
		"CREATE_DATETIME,CREATE_BY,UPDATE_DATETIME,UPDATE_BY,DELETE_FLAG,SYSTEM_CODE_ID) " +
		"VALUES(?,?,?,?,?,sysdate(),?,sysdate(),?,?,?)";
	private static final String INSERT_PRODUCTITEM_SQL = "INSERT INTO PRODUCT_ITEM(PRODUCT_ID,ENTITY_TYPE,ENTITY_ID,CREATE_DATETIME,CREATE_BY,UPDATE_DATETIME,UPDATE_BY,DELETE_FLAG)" +
		" VALUES(?,?,?,sysdate(),?,sysdate(),?,?)";
	private static final String UPDATE_PRODUCT_SQL = "UPDATE PRODUCT SET IS_VALID=?,INVALID_DATETIME=?,INVALID_REASON_DESCRIPTION=? WHERE PRODUCT_ID=?";
	private static final String INSERT_PRODUCT_SQL = "INSERT INTO PRODUCT(BOOK_COUNT,SELLCOUNT,PRICE,IS_VALID,DELETE_FLAG,PAYFOR_PRICE,PERCENT,PACKAGE_PERCENT," +
		"PRODUCT_NAME,PRODUCT_DESCRIPTION,PRODUCT_TYPE,IS_FROZEN,CREATE_BY,UPDATE_BY,INVALID_REASON_DESCRIPTION,INVALID_DATETIME,SALE_PRICE," +
		"COST_PRICE,CREATE_DATETIME,UPDATE_DATETIME) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate(),?,?,sysdate(),sysdate())";
	private static final String UPDATE_PRODUCT_PRICE_SQL = "UPDATE PRODUCT SET SALE_PRICE=?,COST_PRICE=? WHERE PRODUCT_ID=?";
	@Autowired
	private JdbcTemplate jdbcTemplateEbook;
	@Override
	public List<EProduct> findProduct(Long bookId) {
		List<EProduct> list =  jdbcTemplateEbook.query(GET_PRODUCT_BY_BOOKID,new Object[]{bookId},new EProductRowMapper());
		return list;
	}

	@Override
	public void save(ProductProcessItem productProcessItem) {
		Object[] param = new Object[10];
		param[1] = productProcessItem.getUserType();
		param[2] = productProcessItem.getProductId();
		param[3] = productProcessItem.getBookId();
		param[4] = productProcessItem.getFlag();
		param[5] = productProcessItem.getFlagDescription();
		param[6] = productProcessItem.getCreateBy();
		param[7] = productProcessItem.getUpdateBy();
		param[8] = productProcessItem.getDeleteFlag();
		param[9] = productProcessItem.getSystemCodeId();
		jdbcTemplateEbook.update(INSERT_PROCESSSITEM_SQL, param);
		
	}
	public PreparedStatement getProductSmt(Connection conn,
			EProduct product) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(INSERT_PRODUCT_SQL,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, product.getBookCount() == null ? 1: product.getBookCount());
			ps.setLong(2, product.getSellCount());
			ps.setBigDecimal(3, product.getPrice());
			ps.setInt(4, product.getIsValid() == null ? 0: product.getIsValid());
			ps.setInt(5, product.getDeleteFlag());
			ps.setBigDecimal(6, product.getPayforPrice());
			ps.setBigDecimal(7, product.getPercent());
			ps.setBigDecimal(8, product.getPackagePercent());
			ps.setString(9, product.getProductName());
			ps.setString(10, product.getProductDescription());
			ps.setInt(11, product.getProductType() == null ? 1:product.getProductType());
			ps.setInt(12, product.getIsFrozen() == null ? 0:product.getProductType());
			ps.setString(13, product.getCreateBy());
			ps.setString(14, product.getUpdateBy());
			ps.setString(15, product.getInvalidReasonDescription());
			ps.setBigDecimal(16, product.getSalePrice());
			ps.setBigDecimal(17, product.getCostPrice());
             return ps;
		} catch (Exception e) {
			LOG.info(e.getCause());
		}
		return ps;
	}
	@Override
	public void save(final EProduct product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateEbook.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) {
                return getProductSmt(conn,product);
            }
        }, keyHolder);
        product.setProductId(keyHolder.getKey().longValue());
	}

	@Override
	public void save(ProductItem productItem) {
		Object[] param = new Object[6];
		param[0] = productItem.getProductID();
		param[1] = productItem.getEntityType();
		param[2] = productItem.getEntityID();
		param[3] = productItem.getCreateBy();
		param[4] = productItem.getUpdateBy();
		param[5] = productItem.getDeleteFlag();
		jdbcTemplateEbook.update(INSERT_PRODUCTITEM_SQL, param);
		
	}

	@Override
	public void update(EProduct product) {
		jdbcTemplateEbook.update(UPDATE_PRODUCT_SQL, 
				product.getIsValid(),product.getInvalidDatetime(),product.getInvalidReasonDescription(),product.getProductId());
	}

	@Override
	public void updatePrice(BigDecimal price,EProduct product) {
		jdbcTemplateEbook.update(UPDATE_PRODUCT_PRICE_SQL, price,price,product.getProductId());
		
	}

}
