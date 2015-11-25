package com.winxuan.ec.task.dao.booktop;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.winxuan.ec.task.model.booktop.BookTopCategory;
import com.winxuan.ec.task.model.booktop.BookTopProductSale;

/**
 * 
 * @author sunflower
 * 
 */
@Service("bookTopDao")
public class BookTopDaoImpl implements BookTopDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TAG_PRODUCTSALE = "productsale";

	private static final String DELETE_PRODUCT_SQL = "delete from book_top_product_sale";
	private static final String DELETE_CATEGORY_SQL = "delete from book_top_category";
	private static final String INSERT_SALE_CATEGORY_SQL = "insert into book_top_category(category,parent,num,url,top_type) values(?,?,?,?,?)";
	private static final String INSERT_SALE_PRODUCT_SQL = "insert into book_top_product_sale(category,productsale,sale,time_type) values(?,?,?,?)";
	private static final String QUERY_SALE_FIRST_CATEGORY_SQL = "select * from book_top_category where parent=? and top_type=?";
	private static final String QUERY_CATEGORY_SQL = "select * from book_top_category where category=? and top_type=?";
	private static final String QUERY_ALL_CATEGORY_SQL = "select * from book_top_category";
	private static final String QUERY_SALE_SECOND_CATEGORY_SQL = "select * from book_top_category where parent=? and top_type=?";
	private static final String SELECT_SALE_FIRST_CATEGORY_SQL = "select psac.ct2 as ct,COUNT(distinct pspon.productsale)AS num "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac "
			+ "WHERE psac.ct1 = 1 AND pspon.productsale = psac.productsale AND pspon.weeksale > 0 AND psac.ct2 IS NOT NULL GROUP BY psac.ct2 ORDER BY num DESC;";
	private static final String SELECT_NEW_FIRST_CATEGORY_SQL = "SELECT psac.ct2 AS ct,COUNT(DISTINCT pspon.productsale)AS num "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac,product p,product_sale ps "
			+ "WHERE psac.ct1 = 1 AND pspon.productsale = psac.productsale AND "
			+ "ps.id = psac.productsale AND ps.product = p.id AND p.productiondate > ? "
			+ "AND pspon.weeksale > 0 AND psac.ct2 IS NOT NULL GROUP BY psac.ct2 ORDER BY num DESC;";
	private static final String SELECT_SALE_SECOND_CATEGORY_SQL = "SELECT DISTINCT psac.ct3 as ct,COUNT(distinct pspon.productsale)AS num"
			+ " FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac"
			+ " WHERE psac.ct2 = ? AND pspon.productsale = psac.productsale AND pspon.weeksale > 0 AND psac.ct3 IS NOT NULL GROUP BY psac.ct3 ORDER BY num desc";
	private static final String SELECT_NEW_SECOND_CATEGORY_SQL = "SELECT psac.ct3 AS ct,COUNT(DISTINCT pspon.productsale)AS num "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac,product p,product_sale ps "
			+ "WHERE psac.ct2 = ? AND pspon.productsale = psac.productsale AND "
			+ "ps.id = psac.productsale AND ps.product = p.id AND p.productiondate > ? "
			+ "AND pspon.weeksale > 0 AND psac.ct3 IS NOT NULL GROUP BY psac.ct3 ORDER BY num DESC;";
	private static final String SELECT_SALE_A_WEEK_PRODUCT_SALE_OF_BOOK_SQL = "SELECT DISTINCT pspon.productsale,pspon.weeksale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac"
			+ " WHERE psac.ct1 = 1 AND pspon.productsale = psac.productsale ORDER BY pspon.weeksale DESC LIMIT 100";
	private static final String SELECT_SALE_A_WEEK_PRODUCT_NEW_OF_BOOK_SQL = "SELECT DISTINCT pspon.productsale,pspon.weeksale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac,product_sale ps,product p "
			+ "WHERE psac.ct1 = 1 AND pspon.productsale = psac.productsale AND ps.id = psac.productsale"
			+ " AND ps.product = p.id AND p.productiondate > ? ORDER BY pspon.weeksale DESC LIMIT 100";
	private static final String SELECT_SALE_A_MONTH_PRODUCT_SALE_OF_BOOK_SQL = "SELECT DISTINCT pspon.productsale,pspon.monthsale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac "
			+ "WHERE psac.ct1 = 1 AND pspon.productsale = psac.productsale ORDER BY pspon.monthsale DESC LIMIT 100";
	private static final String SELECT_SALE_A_MONTH_PRODUCT_NEW_OF_BOOK_SQL = "SELECT DISTINCT pspon.productsale,pspon.monthsale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac,product_sale ps,product p "
			+ "WHERE psac.ct1 = 1 AND pspon.productsale = psac.productsale AND ps.id = psac.productsale"
			+ " AND ps.product = p.id AND p.productiondate >? ORDER BY pspon.monthsale DESC LIMIT 100";
	private static final String SELECT_SALE_THREE_MONTH_PRODUCT_SALE_OF_BOOK_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct1=1) psac "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?)"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_THREE_MONTH_PRODUCT_NEW_OF_BOOK_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct1=1) psac,product_sale ps,product p "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?) AND ps.id = psac.productsale AND ps.product = p.id AND p.productiondate >?"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_LAST_YEAR_PRODUCT_SALE_OF_BOOK_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct1=1) psac "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?,?,?,?,?,?,?,?,?,?)"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_LAST_YEAR_PRODUCT_NEW_OF_BOOK_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct1=1) psac,product_sale ps,product p "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?,?,?,?,?,?,?,?,?,?) AND ps.id = psac.productsale AND ps.product = p.id AND p.productiondate >?"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_A_WEEK_PRODUCT_SALE_OF_FIRST_CATEGORY_SQL = "SELECT DISTINCT pspon.productsale,pspon.weeksale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac "
			+ "WHERE psac.ct2 = ? AND pspon.productsale = psac.productsale ORDER BY pspon.weeksale DESC LIMIT 100";
	private static final String SELECT_SALE_A_WEEK_PRODUCT_NEW_OF_FIRST_CATEGORY_SQL = "SELECT DISTINCT pspon.productsale,pspon.weeksale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac,product p,product_sale ps "
			+ "WHERE psac.ct2 = ? AND pspon.productsale = psac.productsale AND ps.id = psac.productsale "
			+ "AND ps.product = p.id AND p.productiondate > ? ORDER BY pspon.weeksale DESC LIMIT 100";
	private static final String SELECT_SALE_A_MONTH_PRODUCT_SALE_OF_FIRST_CATEGORY_SQL = "SELECT DISTINCT pspon.productsale,pspon.monthsale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac "
			+ "WHERE psac.ct2 = ? AND pspon.productsale = psac.productsale ORDER BY pspon.monthsale DESC LIMIT 100";
	private static final String SELECT_SALE_A_MONTH_PRODUCT_NEW_OF_FIRST_CATEGORY_SQL = "SELECT DISTINCT pspon.productsale,pspon.monthsale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac,product_sale ps,product p "
			+ "WHERE psac.ct2 = ? AND pspon.productsale = psac.productsale AND ps.id=psac.productsale AND ps.product=p.id AND p.productiondate>? ORDER BY pspon.monthsale DESC LIMIT 100";
	private static final String SELECT_SALE_THREE_MONTH_PRODUCT_SALE_OF_FIRST_CATEGORY_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct2=?) psac "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?)"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_THREE_MONTH_PRODUCT_NEW_OF_FIRST_CATEGORY_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct2=?) psac,product_sale ps,product p "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?) AND ps.id = psac.productsale AND ps.product = p.id AND p.productiondate >?"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_LAST_YEAR_PRODUCT_SALE_OF_FIRST_CATEGORY_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct2=?) psac "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?,?,?,?,?,?,?,?,?,?)"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_LAST_YEAR_PRODUCT_NEW_OF_FIRST_CATEGORY_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct2=?) psac,product_sale ps,product p "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?,?,?,?,?,?,?,?,?,?) AND ps.id = psac.productsale AND ps.product = p.id AND p.productiondate >?"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_A_WEEK_PRODUCT_SALE_OF_SECOND_CATEGORY_SQL = "SELECT DISTINCT pspon.productsale,pspon.weeksale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac "
			+ "WHERE psac.ct3 = ? AND pspon.productsale = psac.productsale ORDER BY pspon.weeksale DESC LIMIT 100";
	private static final String SELECT_SALE_A_WEEK_PRODUCT_NEW_OF_SECOND_CATEGORY_SQL = "SELECT DISTINCT pspon.productsale,pspon.weeksale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac,product_sale ps,product p "
			+ "WHERE psac.ct3 = ? AND pspon.productsale = psac.productsale AND ps.id = psac.productsale"
			+ " AND ps.product = p.id AND p.productiondate > ? ORDER BY pspon.weeksale DESC LIMIT 100";
	private static final String SELECT_SALE_A_MONTH_PRODUCT_SALE_OF_SECOND_CATEGORY_SQL = "SELECT DISTINCT pspon.productsale,pspon.monthsale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac "
			+ "WHERE psac.ct3 = ? AND pspon.productsale = psac.productsale ORDER BY pspon.monthsale DESC LIMIT 100";
	private static final String SELECT_SALE_A_MONTH_PRODUCT_NEW_OF_SECOND_CATEGORY_SQL = "SELECT DISTINCT pspon.productsale,pspon.monthsale "
			+ "FROM product_sale_performance_onshelf pspon,product_sale_amzn_ct psac,product_sale ps,product p "
			+ "WHERE psac.ct3 = ? AND pspon.productsale = psac.productsale AND ps.id = psac.productsale AND ps.product = p.id "
			+ "AND p.productiondate >? ORDER BY pspon.monthsale DESC LIMIT 100";
	private static final String SELECT_SALE_THREE_MONTH_PRODUCT_SALE_OF_SECOND_CATEGORY_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct3=?) psac "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?)"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_THREE_MONTH_PRODUCT_NEW_OF_SECOND_CATEGORY_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct3=?) psac,product_sale ps,product p"
			+ " WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?) AND ps.id = psac.productsale AND ps.product = p.id AND p.productiondate >?"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_LAST_YEAR_PRODUCT_SALE_OF_SECOND_CATEGORY_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct3=?) psac "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?,?,?,?,?,?,?,?,?,?)"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";
	private static final String SELECT_SALE_LAST_YEAR_PRODUCT_NEW_OF_SECOND_CATEGORY_SQL = "SELECT sm.productsale,"
			+ "SUM(sm.salequantity)AS salequantity FROM salebymonth sm,"
			+ "(select DISTINCT productsale from product_sale_amzn_ct where ct3=?) psac,product_sale ps,product p "
			+ "WHERE sm.productsale = psac.productsale AND sm.saledate IN(?,?,?,?,?,?,?,?,?,?,?,?) AND ps.id = psac.productsale AND ps.product = p.id AND p.productiondate >?"
			+ " GROUP BY sm.productsale ORDER BY salequantity DESC LIMIT 100";

	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;

	@Override
	public void deleteProducts() {
		jdbcTemplateEcCore.execute(DELETE_PRODUCT_SQL);
	}

	@Override
	public void deleteCategory() {
		jdbcTemplateEcCore.execute(DELETE_CATEGORY_SQL);
	}

	@Override
	public void addFirstCategory(int minNum, int saleNum) {
		List<Map<String, Object>> list = jdbcTemplateEcCore
				.queryForList(SELECT_SALE_FIRST_CATEGORY_SQL);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				if (minNum > 0) {
					addFirstCategory(map, BookTopCategory.TOP_TYPE_SALE);
					minNum--;
				} else if (Integer.parseInt(map.get("num").toString()) >= saleNum) {
					addFirstCategory(map, BookTopCategory.TOP_TYPE_SALE);
				}

			}
		}
	}

	private void addFirstCategory(Map<String, Object> map, int topType) {
		jdbcTemplateEcCore.update(
				INSERT_SALE_CATEGORY_SQL,
				new Object[] {
						Long.parseLong(map.get("ct").toString()),
						getBookTopCategory(BookTopCategory.CATEGORY_BOOK,
								topType).getId(),
						Long.parseLong(map.get("num").toString()),
						"http://www.winxuan.com/booktop/" + topType + "/"
								+ map.get("ct").toString() + "/1/1",
						new Long(topType) });
	}

	@Override
	public List<BookTopCategory> getFirstCategory(int topType) {
		return jdbcTemplateEcCore.query(
				QUERY_SALE_FIRST_CATEGORY_SQL,
				new Object[] {
						getBookTopCategory(BookTopCategory.CATEGORY_BOOK,
								topType).getId(), topType },
				new RowMapper<BookTopCategory>() {
					@Override
					public BookTopCategory mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						BookTopCategory btc = new BookTopCategory();
						btc.setId(rs.getLong("id"));
						btc.setCategory(rs.getLong("category"));
						btc.setParent(rs.getLong("parent"));
						btc.setNum(rs.getInt("num"));
						btc.setTopType(rs.getInt("top_type"));
						btc.setUrl(rs.getString("url"));
						return btc;
					}
				});
	}

	@Override
	public List<BookTopCategory> getSecondCategory(
			BookTopCategory firstCategory, int topType) {
		return jdbcTemplateEcCore
				.query(QUERY_SALE_SECOND_CATEGORY_SQL,
						new Object[] {
								getBookTopCategory(firstCategory.getCategory(),
										topType).getId(), topType },
						new RowMapper<BookTopCategory>() {
							@Override
							public BookTopCategory mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								BookTopCategory btc = new BookTopCategory();
								btc.setId(rs.getLong("id"));
								btc.setCategory(rs.getLong("category"));
								btc.setParent(rs.getLong("parent"));
								btc.setNum(rs.getInt("num"));
								btc.setTopType(rs.getInt("top_type"));
								btc.setUrl(rs.getString("url"));
								return btc;
							}
						});
	}

	@Override
	public BookTopCategory getBookTopCategory(long category, int topType) {
		return jdbcTemplateEcCore.queryForObject(QUERY_CATEGORY_SQL,
				new Object[] { category, topType },
				new RowMapper<BookTopCategory>() {
					@Override
					public BookTopCategory mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						BookTopCategory btc = new BookTopCategory();
						btc.setId(rs.getLong("id"));
						btc.setCategory(rs.getLong("category"));
						btc.setParent(rs.getLong("parent"));
						btc.setNum(rs.getInt("num"));
						btc.setTopType(rs.getInt("top_type"));
						btc.setUrl(rs.getString("url"));
						return btc;
					}
				});
	}

	@Override
	public void addSecondCategory(BookTopCategory firstCategory) {
		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(
				SELECT_SALE_SECOND_CATEGORY_SQL,
				new Object[] { firstCategory.getCategory() });
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				addSecondCategory(map, firstCategory,
						BookTopCategory.TOP_TYPE_SALE);
			}
		}
	}

	private void addSecondCategory(Map<String, Object> map,
			BookTopCategory firstCategory, int topType) {
		jdbcTemplateEcCore
				.update(INSERT_SALE_CATEGORY_SQL,
						new Object[] {
								Long.parseLong(map.get("ct").toString()),
								getBookTopCategory(firstCategory.getCategory(),
										topType).getId(),
								Long.parseLong(map.get("num").toString()),
								"http://www.winxuan.com/booktop/" + topType
										+ "/" + map.get("ct").toString()
										+ "/1/1", new Long(topType) });
	}

	@Override
	public void addAWeekProductSaleOfBook() {
		List<Map<String, Object>> list = jdbcTemplateEcCore
				.queryForList(SELECT_SALE_A_WEEK_PRODUCT_SALE_OF_BOOK_SQL);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE).toString()),
						Long.parseLong(map.get("weeksale").toString()),
						BookTopCategory.CATEGORY_BOOK,
						BookTopCategory.TOP_TYPE_SALE,
						BookTopProductSale.TIME_TYPE_WEEK);
			}
		}
	}

	private void addProduct(long productsale, long sale, long category,
			int topType, int timeType) {
		jdbcTemplateEcCore.update(INSERT_SALE_PRODUCT_SQL, new Object[] {
				new Long(getBookTopCategory(category, topType).getId()),
				new Long(productsale), new Long(sale), new Long(timeType) });
	}

	@Override
	public void addAWeekProductSaleOfFirstCategory() {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_SALE);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<Map<String, Object>> list = jdbcTemplateEcCore
						.queryForList(
								SELECT_SALE_A_WEEK_PRODUCT_SALE_OF_FIRST_CATEGORY_SQL,
								new Object[] { firstCategory.getCategory() });
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE)
								.toString()), Long.parseLong(map
								.get("weeksale").toString()),
								firstCategory.getCategory(),
								BookTopCategory.TOP_TYPE_SALE,
								BookTopProductSale.TIME_TYPE_WEEK);
					}
				}
			}
		}
	}

	@Override
	public void addAWeekProductSaleOfSecondCategory() {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_SALE);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<BookTopCategory> secondCategorys = getSecondCategory(
						firstCategory, BookTopCategory.TOP_TYPE_SALE);
				if (secondCategorys != null && secondCategorys.size() > 0) {
					for (BookTopCategory secondCategory : secondCategorys) {
						List<Map<String, Object>> list = jdbcTemplateEcCore
								.queryForList(
										SELECT_SALE_A_WEEK_PRODUCT_SALE_OF_SECOND_CATEGORY_SQL,
										new Object[] { secondCategory
												.getCategory() });
						if (list != null && list.size() > 0) {
							for (Map<String, Object> map : list) {
								addProduct(Long.parseLong(map.get(
										TAG_PRODUCTSALE).toString()),
										Long.parseLong(map.get("weeksale")
												.toString()),
										secondCategory.getCategory(),
										BookTopCategory.TOP_TYPE_SALE,
										BookTopProductSale.TIME_TYPE_WEEK);
							}
						}
					}

				}
			}
		}
	}

	@Override
	public void addAMonthProductSaleOfBook() {
		List<Map<String, Object>> list = jdbcTemplateEcCore
				.queryForList(SELECT_SALE_A_MONTH_PRODUCT_SALE_OF_BOOK_SQL);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE).toString()),
						Long.parseLong(map.get("monthsale").toString()),
						BookTopCategory.CATEGORY_BOOK,
						BookTopCategory.TOP_TYPE_SALE,
						BookTopProductSale.TIME_TYPE_ONE_MONTH);
			}
		}
	}

	@Override
	public void addAMonthProductSaleOfFirstCategory() {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_SALE);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<Map<String, Object>> list = jdbcTemplateEcCore
						.queryForList(
								SELECT_SALE_A_MONTH_PRODUCT_SALE_OF_FIRST_CATEGORY_SQL,
								new Object[] { firstCategory.getCategory() });
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE)
								.toString()), Long.parseLong(map.get(
								"monthsale").toString()),
								firstCategory.getCategory(),
								BookTopCategory.TOP_TYPE_SALE,
								BookTopProductSale.TIME_TYPE_ONE_MONTH);
					}
				}
			}
		}
	}

	@Override
	public void addAMonthProductSaleOfSecondCategory() {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_SALE);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<BookTopCategory> secondCategorys = getSecondCategory(
						firstCategory, BookTopCategory.TOP_TYPE_SALE);
				if (secondCategorys != null && secondCategorys.size() > 0) {
					for (BookTopCategory secondCategory : secondCategorys) {
						List<Map<String, Object>> list = jdbcTemplateEcCore
								.queryForList(
										SELECT_SALE_A_MONTH_PRODUCT_SALE_OF_SECOND_CATEGORY_SQL,
										new Object[] { secondCategory
												.getCategory() });
						if (list != null && list.size() > 0) {
							for (Map<String, Object> map : list) {
								addProduct(Long.parseLong(map.get(
										TAG_PRODUCTSALE).toString()),
										Long.parseLong(map.get("monthsale")
												.toString()),
										secondCategory.getCategory(),
										BookTopCategory.TOP_TYPE_SALE,
										BookTopProductSale.TIME_TYPE_ONE_MONTH);
							}
						}
					}

				}
			}
		}
	}

	@Override
	public void addThreeMonthProductSaleOfBook(List<String> months) {
		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(
				SELECT_SALE_THREE_MONTH_PRODUCT_SALE_OF_BOOK_SQL, new Object[] {
						months.get(0), months.get(1), months.get(2) });
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE).toString()),
						Long.parseLong(map.get("salequantity").toString()),
						BookTopCategory.CATEGORY_BOOK,
						BookTopCategory.TOP_TYPE_SALE,
						BookTopProductSale.TIME_TYPE_THREE_MONTH);
			}
		}
	}

	@Override
	public void addThreeMonthProductSaleOfFirstCategory(List<String> months) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_SALE);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<Map<String, Object>> list = jdbcTemplateEcCore
						.queryForList(
								SELECT_SALE_THREE_MONTH_PRODUCT_SALE_OF_FIRST_CATEGORY_SQL,
								new Object[] { firstCategory.getCategory(),
										months.get(0), months.get(1),
										months.get(2) });
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE)
								.toString()), Long.parseLong(map.get(
								"salequantity").toString()),
								firstCategory.getCategory(),
								BookTopCategory.TOP_TYPE_SALE,
								BookTopProductSale.TIME_TYPE_THREE_MONTH);
					}
				}
			}
		}
	}

	@Override
	public void addThreeMonthProductSaleOfSecondCategory(List<String> months) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_SALE);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<BookTopCategory> secondCategorys = getSecondCategory(
						firstCategory, BookTopCategory.TOP_TYPE_SALE);
				if (secondCategorys != null && secondCategorys.size() > 0) {
					for (BookTopCategory secondCategory : secondCategorys) {
						List<Map<String, Object>> list = jdbcTemplateEcCore
								.queryForList(
										SELECT_SALE_THREE_MONTH_PRODUCT_SALE_OF_SECOND_CATEGORY_SQL,
										new Object[] {
												secondCategory.getCategory(),
												months.get(0), months.get(1),
												months.get(2) });
						if (list != null && list.size() > 0) {
							for (Map<String, Object> map : list) {
								addProduct(
										Long.parseLong(map.get(TAG_PRODUCTSALE)
												.toString()),
										Long.parseLong(map.get("salequantity")
												.toString()),
										secondCategory.getCategory(),
										BookTopCategory.TOP_TYPE_SALE,
										BookTopProductSale.TIME_TYPE_THREE_MONTH);
							}
						}
					}

				}
			}
		}
	}

	@Override
	public void addLastYearProductSaleOfBook(List<String> months) {

		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(
				SELECT_SALE_LAST_YEAR_PRODUCT_SALE_OF_BOOK_SQL,
				getLastyearMonths(months));
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE).toString()),
						Long.parseLong(map.get("salequantity").toString()),
						BookTopCategory.CATEGORY_BOOK,
						BookTopCategory.TOP_TYPE_SALE,
						BookTopProductSale.TIME_TYPE_ONE_YEAR);
			}
		}
	}

	private Object[] getLastyearMonths(List<String> months) {
		Object[] objs = new Object[12];
		for (int i = 0; i < 12; i++) {
			objs[i] = months.get(i);
		}
		return objs;
	}

	@Override
	public void addLastYearProductSaleOfFirstCategory(List<String> months) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_SALE);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				Object[] objs = new Object[13];
				objs[0] = firstCategory.getCategory();
				for (int i = 0; i < 12; i++) {
					objs[i + 1] = months.get(i);
				}
				List<Map<String, Object>> list = jdbcTemplateEcCore
						.queryForList(
								SELECT_SALE_LAST_YEAR_PRODUCT_SALE_OF_FIRST_CATEGORY_SQL,
								objs);
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE)
								.toString()), Long.parseLong(map.get(
								"salequantity").toString()),
								firstCategory.getCategory(),
								BookTopCategory.TOP_TYPE_SALE,
								BookTopProductSale.TIME_TYPE_ONE_YEAR);
					}
				}
			}
		}
	}

	@Override
	public void addLastYearProductSaleOfSecondCategory(List<String> months) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_SALE);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<BookTopCategory> secondCategorys = getSecondCategory(
						firstCategory, BookTopCategory.TOP_TYPE_SALE);
				if (secondCategorys != null && secondCategorys.size() > 0) {
					for (BookTopCategory secondCategory : secondCategorys) {
						Object[] objs = new Object[13];
						objs[0] = secondCategory.getCategory();
						for (int i = 0; i < 12; i++) {
							objs[i + 1] = months.get(i);
						}
						List<Map<String, Object>> list = jdbcTemplateEcCore
								.queryForList(
										SELECT_SALE_LAST_YEAR_PRODUCT_SALE_OF_SECOND_CATEGORY_SQL,
										objs);
						if (list != null && list.size() > 0) {
							for (Map<String, Object> map : list) {
								addProduct(Long.parseLong(map.get(
										TAG_PRODUCTSALE).toString()),
										Long.parseLong(map.get("salequantity")
												.toString()),
										secondCategory.getCategory(),
										BookTopCategory.TOP_TYPE_SALE,
										BookTopProductSale.TIME_TYPE_ONE_YEAR);
							}
						}
					}

				}
			}
		}
	}

	@Override
	public void addBookCategory(int topType) {
		jdbcTemplateEcCore.update(INSERT_SALE_CATEGORY_SQL, new Object[] {
				new Long(1), new Long(0), new Long(0),
				"http://www.winxuan.com/booktop/" + topType + "/1/1/1",
				new Long(topType) });
	}

	@Override
	public void addNewFirstCategory(int minNum, int saleNum, Date date) {
		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(
				SELECT_NEW_FIRST_CATEGORY_SQL, new Object[] { date });
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				if (minNum > 0) {
					addFirstCategory(map, BookTopCategory.TOP_TYPE_NEW);
					minNum--;
				} else if (Integer.parseInt(map.get("num").toString()) >= saleNum) {
					addFirstCategory(map, BookTopCategory.TOP_TYPE_NEW);
				}

			}
		}
	}

	@Override
	public void addNewSecondCategory(BookTopCategory firstCategory, Date date) {
		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(
				SELECT_NEW_SECOND_CATEGORY_SQL,
				new Object[] { firstCategory.getCategory(), date });
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				addSecondCategory(map, firstCategory,
						BookTopCategory.TOP_TYPE_NEW);
			}
		}
	}

	@Override
	public void addAWeekProductNewOfBook(Date date) {

		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(
				SELECT_SALE_A_WEEK_PRODUCT_NEW_OF_BOOK_SQL,
				new Object[] { date });
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE).toString()),
						Long.parseLong(map.get("weeksale").toString()),
						BookTopCategory.CATEGORY_BOOK,
						BookTopCategory.TOP_TYPE_NEW,
						BookTopProductSale.TIME_TYPE_WEEK);
			}
		}
	}

	@Override
	public void addAWeekProductNewOfFirstCategory(Date date) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_NEW);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<Map<String, Object>> list = jdbcTemplateEcCore
						.queryForList(
								SELECT_SALE_A_WEEK_PRODUCT_NEW_OF_FIRST_CATEGORY_SQL,
								new Object[] { firstCategory.getCategory(),
										date });
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE)
								.toString()), Long.parseLong(map
								.get("weeksale").toString()),
								firstCategory.getCategory(),
								BookTopCategory.TOP_TYPE_NEW,
								BookTopProductSale.TIME_TYPE_WEEK);
					}
				}
			}
		}
	}

	@Override
	public void addAWeekProductNewOfSecondCategory(Date date) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_NEW);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<BookTopCategory> secondCategorys = getSecondCategory(
						firstCategory, BookTopCategory.TOP_TYPE_NEW);
				if (secondCategorys != null && secondCategorys.size() > 0) {
					for (BookTopCategory secondCategory : secondCategorys) {
						List<Map<String, Object>> list = jdbcTemplateEcCore
								.queryForList(
										SELECT_SALE_A_WEEK_PRODUCT_NEW_OF_SECOND_CATEGORY_SQL,
										new Object[] {
												secondCategory.getCategory(),
												date });
						if (list != null && list.size() > 0) {
							for (Map<String, Object> map : list) {
								addProduct(Long.parseLong(map.get(
										TAG_PRODUCTSALE).toString()),
										Long.parseLong(map.get("weeksale")
												.toString()),
										secondCategory.getCategory(),
										BookTopCategory.TOP_TYPE_NEW,
										BookTopProductSale.TIME_TYPE_WEEK);
							}
						}
					}

				}
			}
		}
	}

	@Override
	public void addAMonthProductNewOfBook(Date date) {
		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(
				SELECT_SALE_A_MONTH_PRODUCT_NEW_OF_BOOK_SQL,
				new Object[] { date });
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE).toString()),
						Long.parseLong(map.get("monthsale").toString()),
						BookTopCategory.CATEGORY_BOOK,
						BookTopCategory.TOP_TYPE_NEW,
						BookTopProductSale.TIME_TYPE_ONE_MONTH);
			}
		}
	}

	@Override
	public void addAMonthProductNewOfFirstCategory(Date date) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_NEW);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<Map<String, Object>> list = jdbcTemplateEcCore
						.queryForList(
								SELECT_SALE_A_MONTH_PRODUCT_NEW_OF_FIRST_CATEGORY_SQL,
								new Object[] { firstCategory.getCategory(),
										date });
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE)
								.toString()), Long.parseLong(map.get(
								"monthsale").toString()),
								firstCategory.getCategory(),
								BookTopCategory.TOP_TYPE_NEW,
								BookTopProductSale.TIME_TYPE_ONE_MONTH);
					}
				}
			}
		}
	}

	@Override
	public void addAMonthProductSaleOfSecondCategory(Date date) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_NEW);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<BookTopCategory> secondCategorys = getSecondCategory(
						firstCategory, BookTopCategory.TOP_TYPE_NEW);
				if (secondCategorys != null && secondCategorys.size() > 0) {
					for (BookTopCategory secondCategory : secondCategorys) {
						List<Map<String, Object>> list = jdbcTemplateEcCore
								.queryForList(
										SELECT_SALE_A_MONTH_PRODUCT_NEW_OF_SECOND_CATEGORY_SQL,
										new Object[] { secondCategory
												.getCategory(),date });
						if (list != null && list.size() > 0) {
							for (Map<String, Object> map : list) {
								addProduct(Long.parseLong(map.get(
										TAG_PRODUCTSALE).toString()),
										Long.parseLong(map.get("monthsale")
												.toString()),
										secondCategory.getCategory(),
										BookTopCategory.TOP_TYPE_NEW,
										BookTopProductSale.TIME_TYPE_ONE_MONTH);
							}
						}
					}

				}
			}
		}
	}

	@Override
	public void addThreeMonthProductNewOfBook(List<String> months, Date date) {
		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(
				SELECT_SALE_THREE_MONTH_PRODUCT_NEW_OF_BOOK_SQL, new Object[] {
						months.get(0), months.get(1), months.get(2), date });
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE).toString()),
						Long.parseLong(map.get("salequantity").toString()),
						BookTopCategory.CATEGORY_BOOK,
						BookTopCategory.TOP_TYPE_NEW,
						BookTopProductSale.TIME_TYPE_THREE_MONTH);
			}
		}
	}

	@Override
	public void addThreeMonthProductNewOfFirstCategory(List<String> months,
			Date date) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_NEW);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<Map<String, Object>> list = jdbcTemplateEcCore
						.queryForList(
								SELECT_SALE_THREE_MONTH_PRODUCT_NEW_OF_FIRST_CATEGORY_SQL,
								new Object[] { firstCategory.getCategory(),
										months.get(0), months.get(1),
										months.get(2), date });
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE)
								.toString()), Long.parseLong(map.get(
								"salequantity").toString()),
								firstCategory.getCategory(),
								BookTopCategory.TOP_TYPE_NEW,
								BookTopProductSale.TIME_TYPE_THREE_MONTH);
					}
				}
			}
		}
	}

	@Override
	public void addThreeMonthProductNewOfSecondCategory(List<String> months,
			Date date) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_NEW);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<BookTopCategory> secondCategorys = getSecondCategory(
						firstCategory, BookTopCategory.TOP_TYPE_NEW);
				if (secondCategorys != null && secondCategorys.size() > 0) {
					for (BookTopCategory secondCategory : secondCategorys) {
						List<Map<String, Object>> list = jdbcTemplateEcCore
								.queryForList(
										SELECT_SALE_THREE_MONTH_PRODUCT_NEW_OF_SECOND_CATEGORY_SQL,
										new Object[] {
												secondCategory.getCategory(),
												months.get(0), months.get(1),
												months.get(2), date });
						if (list != null && list.size() > 0) {
							for (Map<String, Object> map : list) {
								addProduct(
										Long.parseLong(map.get(TAG_PRODUCTSALE)
												.toString()),
										Long.parseLong(map.get("salequantity")
												.toString()),
										secondCategory.getCategory(),
										BookTopCategory.TOP_TYPE_NEW,
										BookTopProductSale.TIME_TYPE_THREE_MONTH);
							}
						}
					}

				}
			}
		}
	}

	@Override
	public void addLastYearProductNewOfBook(List<String> months, Date date) {

		Object[] objs = new Object[13];
		for (int i = 0; i < 12; i++) {
			objs[i] = months.get(i);
		}
		objs[12] = date;
		List<Map<String, Object>> list = jdbcTemplateEcCore.queryForList(
				SELECT_SALE_LAST_YEAR_PRODUCT_NEW_OF_BOOK_SQL, objs);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE).toString()),
						Long.parseLong(map.get("salequantity").toString()),
						BookTopCategory.CATEGORY_BOOK,
						BookTopCategory.TOP_TYPE_NEW,
						BookTopProductSale.TIME_TYPE_ONE_YEAR);
			}
		}
	}

	@Override
	public void addLastYearProductNewOfFirstCategory(List<String> months,
			Date date) {
		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_NEW);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				Object[] objs = new Object[14];
				objs[0] = firstCategory.getCategory();
				for (int i = 0; i < 12; i++) {
					objs[i + 1] = months.get(i);
				}
				objs[13] = date;
				List<Map<String, Object>> list = jdbcTemplateEcCore
						.queryForList(
								SELECT_SALE_LAST_YEAR_PRODUCT_NEW_OF_FIRST_CATEGORY_SQL,
								objs);
				if (list != null && list.size() > 0) {
					for (Map<String, Object> map : list) {
						addProduct(Long.parseLong(map.get(TAG_PRODUCTSALE)
								.toString()), Long.parseLong(map.get(
								"salequantity").toString()),
								firstCategory.getCategory(),
								BookTopCategory.TOP_TYPE_NEW,
								BookTopProductSale.TIME_TYPE_ONE_YEAR);
					}
				}
			}
		}
	}

	@Override
	public void addLastYearProductNewOfSecondCategory(List<String> months,
			Date date) {

		List<BookTopCategory> firstCategorys = getFirstCategory(BookTopCategory.TOP_TYPE_NEW);
		if (firstCategorys != null && firstCategorys.size() > 0) {
			for (BookTopCategory firstCategory : firstCategorys) {
				List<BookTopCategory> secondCategorys = getSecondCategory(
						firstCategory, BookTopCategory.TOP_TYPE_NEW);
				if (secondCategorys != null && secondCategorys.size() > 0) {
					for (BookTopCategory secondCategory : secondCategorys) {
						Object[] objs = new Object[14];
						objs[0] = secondCategory.getCategory();
						for (int i = 0; i < 12; i++) {
							objs[i + 1] = months.get(i);
						}
						objs[13] = date;
						List<Map<String, Object>> list = jdbcTemplateEcCore
								.queryForList(
										SELECT_SALE_LAST_YEAR_PRODUCT_NEW_OF_SECOND_CATEGORY_SQL,
										objs);
						if (list != null && list.size() > 0) {
							for (Map<String, Object> map : list) {
								addProduct(Long.parseLong(map.get(
										TAG_PRODUCTSALE).toString()),
										Long.parseLong(map.get("salequantity")
												.toString()),
										secondCategory.getCategory(),
										BookTopCategory.TOP_TYPE_NEW,
										BookTopProductSale.TIME_TYPE_ONE_YEAR);
							}
						}
					}

				}
			}
		}
	}

	@Override
	public List<BookTopCategory> getAllCategorys() {
		return jdbcTemplateEcCore.query(QUERY_ALL_CATEGORY_SQL,
				new RowMapper<BookTopCategory>() {
					@Override
					public BookTopCategory mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						BookTopCategory btc = new BookTopCategory();
						btc.setId(rs.getLong("id"));
						btc.setCategory(rs.getLong("category"));
						btc.setParent(rs.getLong("parent"));
						btc.setNum(rs.getInt("num"));
						btc.setTopType(rs.getInt("top_type"));
						btc.setUrl(rs.getString("url"));
						return btc;
					}
				});
	}

}
