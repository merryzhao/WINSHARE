package com.winxuan.ec.task.dao.recommend;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.winxuan.ec.support.web.enumerator.RecommendMode;
import com.winxuan.ec.task.support.utils.ThreadLocalMode;
/**
 * 
 * @author Administrator
 *
 */
@Service("recommendDao")
public class RecommendDaoImpl implements RecommendDao ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3766958548117479803L;
	private static Log log = LogFactory.getLog(RecommendDaoImpl.class);

	private static final String SAVE_BUY_BASE_DATA_BY_DATE = "INSERT INTO product_recommend_base_data (logicid,commodityid,mode,userid) " +
			"SELECT  o.id , ps.id , "+ RecommendMode.BUY.getMode() + ",0 " +
			" FROM  _order o inner join  order_item i on o.id=i._order inner join product_sale ps on i.productsale=ps.id " +
			"WHERE o.createtime <= ? AND  o.id > ? and o.processstatus in (8002,8003,8004,8005)" +
			"and o.channel in (6,104,110,111,701,702)";
	
	private static final String SAVE_VISIT_BASE_DATA_BY_DATE = "insert into product_recommend_base_data (logicid,commodityid,mode,userid,tag)  " +
			"(select session,productsale,"+ RecommendMode.VISIT.getMode() +",0,MIN(id) from customer_visited group by session,productsale " +
			"having MIN(id) > (SELECT if(MAX(tag) is null ,0,MAX(tag)) FROM product_recommend_base_data where mode = ?))";

	private static final String SAVE_SEARCH_BASE_DATA_BY_DATE = "insert into search_recommend_base_data (logicid,itemid,mode,userid,tag,item)  " +
			"(select sessioncookieid,keyword,"+ RecommendMode.SEARCH.getMode() +",0,MAX(id),0 from search_history where LENGTH(keyword) < 50 and hits>0 group by sessioncookieid,keyword " +
			"having MAX(id) > (SELECT if(MAX(tag) is null ,0,MAX(tag)) FROM search_recommend_base_data where mode = ?))";

	private static final String DELETE_ONLY_ONE_USERID = "delete from `product_recommend_base_data`  where id in (:ids)";
	
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	
	private String getSelectOnlyOneUserIdSql(){
		return "select pr1.id from product_recommend_base_data pr1 " 
		+ " where pr1.userid=0 and pr1.mode =" + ThreadLocalMode.getMode() + " "
		+ " group by pr1.logicid having count(pr1.logicid) = 1" ;
		
	}

	public String getGenerateUserId() {
		return "update `"+ ThreadLocalMode.getBaseTable() +"` p," +
		"(select id,logicid from `"+ ThreadLocalMode.getBaseTable() +"` group by logicid) pr " +
		"set p.userid=pr.id where p.logicid=pr.logicid and p.userid=0 and mode=?";
	}

	private String getMaxUserIdSql() {
		return "SELECT if(MAX(logicid) is null ,0,MAX(logicid)) FROM "+ ThreadLocalMode.getBaseTable() +" where mode =?";
	}

	private String getMaxTagIdSql(){
		return "SELECT if(MAX(tag) is null ,0,MAX(tag)) FROM "+ ThreadLocalMode.getBaseTable() +" where mode = ?";
	}

	private String getMaxIdSql(){
		return "select max(id) from "+ ThreadLocalMode.getBaseTable() +" where mode = ?";
	}

	private String getIdAfterIdSql(){
		return "select id from "+ ThreadLocalMode.getBaseTable() + " where id > ? and mode = ? limit 1 ";
	}

	private String getItemByIdSql(long id){
		return "select min(s1.id) from "+ ThreadLocalMode.getBaseTable() +" s1," +
		"(select * from "+ ThreadLocalMode.getBaseTable() +" where id = " + id +" limit 1) s2 " +
		"where s1.itemid = s2.itemid and s1.id <= "+ id + " and s1.mode = ?";
	}

	private String updateItemSql(){
		return "update "+ ThreadLocalMode.getBaseTable() +" set item= ?  where id = ? and mode = ?";
	}

	private String updateRecommendidSql(){
		return "update "+ ThreadLocalMode.getRecommendTable() +" s ,"+ ThreadLocalMode.getBaseTable() +" sd set s.recommendid=sd.itemid " +
		"where s.recommendid=sd.id and s.mode=?";
	}

	private String updateRecommendationSql(){
		return "update "+ ThreadLocalMode.getRecommendTable() +" s ,"+ ThreadLocalMode.getBaseTable() +" sd set s.recommendation=sd.itemid " +
		"where s.recommendation=sd.id and s.mode=?";
	}

	private String updateModeSql(){
		return "update  "+ ThreadLocalMode.getRecommendTable() +" set mode = ? where mode =?";
	}

	private String getFindDateBase() {
		return "SELECT id,userid,"+ ThreadLocalMode.getItemIdName() +" FROM "+ ThreadLocalMode.getBaseTable() + " where "+ 
		ThreadLocalMode.getItemIdName() +" !=0 and mode=? and id>? order by id";
	}

	private String getFindNeedCaculateCommodityIdList() {
		return "SELECT DISTINCT(r."+ ThreadLocalMode.getItemIdName() +") FROM "+ ThreadLocalMode.getBaseTable() +"  r  where mode=?";
	}

	private String getEmptyResule() {
		return "DELETE FROM "+ ThreadLocalMode.getRecommendTable() +" WHERE mode=?";
	}

	private String getFindRecommendListByRecommoendId() {
		return "SELECT recommendation, preference from "+ ThreadLocalMode.getRecommendTable() +"  where recommendid = ? and mode=?";
	}

	private String getSaveResuleByFile() {
		return "LOAD DATA LOCAL INFILE ? INTO TABLE "+ ThreadLocalMode.getRecommendTable() +" FIELDS TERMINATED BY '\t' " +
		"LINES TERMINATED BY '\r\n' (recommendid,recommendation,preference,mode) ";
	}

	@Override
	public SqlRowSet findBaseData(int lastid, int count) {
		short mode = ThreadLocalMode.getMode();
		StringBuffer sql = new StringBuffer(getFindDateBase());
//		sql.append(" LIMIT " + start + "," + count + "");
		sql.append(" LIMIT " + count + "");
		return jdbcTemplateEcCore.queryForRowSet(sql.toString(),new Object[] {mode,lastid});
	}

	@Override
	public long getMaxOrderId() {
		short mode = ThreadLocalMode.getMode();
		try{
			return jdbcTemplateEcCore.queryForLong(getMaxUserIdSql(), new Object[] {mode});
		}
		catch(EmptyResultDataAccessException e){
			return 0;
		}
	}

	@Override
	public long getMaxTagId() {
		short mode = ThreadLocalMode.getMode();
		try{
			return jdbcTemplateEcCore.queryForLong(getMaxTagIdSql(), new Object[] {mode});
		}
		catch(EmptyResultDataAccessException e){
			return 0;
		}
	}

	@Override
	public long getMaxId() {
		short mode = ThreadLocalMode.getMode();
		try{
			return jdbcTemplateEcCore.queryForLong(getMaxIdSql(), new Object[] {mode});
		}
		catch(EmptyResultDataAccessException e){
			return 0;
		}
	}

	@Override
	public long getIdAfterId(long id) {
		short mode = ThreadLocalMode.getMode();
		try{
			return jdbcTemplateEcCore.queryForLong(getIdAfterIdSql(), new Object[] {id , mode});
		}
		catch(EmptyResultDataAccessException e){
			return 0;
		}
	}

	@Override
	public long getItemById(long id){
		short mode = ThreadLocalMode.getMode();
		try{
			return jdbcTemplateEcCore.queryForLong(getItemByIdSql(id), new Object[] {mode});
		}
		catch(EmptyResultDataAccessException e){
			return 0;
		}
	}

	@Override
	public void updateRecommendid(){
		jdbcTemplateEcCore.update(updateRecommendidSql(), new Object[] {RecommendMode.TEMP_MODE});
	}

	@Override
	public void updateRecommendation(){
		jdbcTemplateEcCore.update(updateRecommendationSql(), new Object[] {RecommendMode.TEMP_MODE});
	}

	@Override
	public void updateMode(){
		short mode = ThreadLocalMode.getMode();
		jdbcTemplateEcCore.update(updateModeSql(), new Object[] {mode,RecommendMode.TEMP_MODE});
	}

	@Override
	public void saveBaseDataByDate() {
		RecommendMode recommendMode = ThreadLocalMode.get();
		short mode = recommendMode.getMode();
		String sql = null;
		Object tag = null;
		
		if(recommendMode.equals(RecommendMode.BUY)){
			//
			//只去7天以前的交易成功的订单
			//
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			int finalDay = -7;
			calendar.add(Calendar.DATE, finalDay);
			String startDate = sdf.format(calendar.getTime());
			
			sql = SAVE_BUY_BASE_DATA_BY_DATE;
			tag = getMaxOrderId();
			log.info("上次导入数据标识： MaxOrderId = " + tag);
			jdbcTemplateEcCore.update(sql, new Object[] { startDate , tag });
			deleteOnlyOneUserId();
			jdbcTemplateEcCore.update(getGenerateUserId() , new Object[] { mode });
		}
		else if(recommendMode.equals(RecommendMode.VISIT)){
			sql = SAVE_VISIT_BASE_DATA_BY_DATE;
			tag = mode;
			log.info("上次导入数据标识： customer_visited id = " + tag);
			jdbcTemplateEcCore.update(sql, new Object[] { tag });
			deleteOnlyOneUserId();
			jdbcTemplateEcCore.update(getGenerateUserId() , new Object[] { mode });
		}
		else if(recommendMode.equals(RecommendMode.SEARCH)){
			sql = SAVE_SEARCH_BASE_DATA_BY_DATE;
			long maxId = getMaxId();
			jdbcTemplateEcCore.update(sql, new Object[] { mode });
			jdbcTemplateEcCore.update(getGenerateUserId() , new Object[] { mode });
			long afterId = getIdAfterId(maxId);
			//单条更新item
			while(afterId != 0){
				long item = jdbcTemplateEcCore.queryForLong(getItemByIdSql(afterId), new Object[] {mode});
				jdbcTemplateEcCore.update(updateItemSql(), new Object[] {item , afterId ,mode});
				afterId = getIdAfterId(afterId);
			}
		}
		else{
			log.warn("错误：没有对应的recommendMode");
		}
	}

	@Override
	public List<Long> findNeedCalculateCommodityIdList(int start, int count) {
		short mode = ThreadLocalMode.getMode();
		StringBuffer sql = new StringBuffer(
				getFindNeedCaculateCommodityIdList());
		sql.append(" LIMIT " + start + "," + count + "");
		return jdbcTemplateEcCore.queryForList(sql.toString(), Long.class , new Object[] {mode} );
	}

	@Override
	public void emptyResult() {
		short mode = ThreadLocalMode.getMode();
		jdbcTemplateEcCore.update(getEmptyResule(), new Object[] {mode});

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RecommendedItem> findRecommendListByCommodityId(long commodityId,short mode) {

		return (List<RecommendedItem>) jdbcTemplateEcCore.query(
				getFindRecommendListByRecommoendId(),
				new Object[] { commodityId ,mode}, new ResultSetExtractor() {
					public Object extractData(ResultSet rs)
					throws SQLException, DataAccessException {
						List<RecommendedItem> recommendedItems = new ArrayList<RecommendedItem>();
						while (rs.next()) {
							RecommendedItem item = new GenericRecommendedItem(
									rs.getLong(1), rs.getFloat(2));

							recommendedItems.add(item);
						}
						return recommendedItems;
					}
				});
	}

	@Override
	public void saveResultByFile(String filePath) {
		jdbcTemplateEcCore.update(getSaveResuleByFile(), new Object[] { filePath });

	}

	@Override
	public void deleteOnlyOneUserId() {
		List<String> onlyOneUserIds = jdbcTemplateEcCore.queryForList(getSelectOnlyOneUserIdSql(), String.class);
		if(onlyOneUserIds == null || onlyOneUserIds.isEmpty()){
			log.info("no only one product for order OR visit .........." + " MODE = " + ThreadLocalMode.getMode());
			return;
		}
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplateEcCore);
		Map<String,List<String>> ids = new HashMap<String, List<String>>();
		ids.put("ids", onlyOneUserIds);
		namedParameterJdbcTemplate.update(DELETE_ONLY_ONE_USERID, ids);
		log.info("deleted only one product for order OR visit .........." + " MODE = " + ThreadLocalMode.getMode());
	}

}
