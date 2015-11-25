package com.winxuan.ec.task.dao.search;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.winxuan.ec.util.AuthorStringTokenizer;
import com.winxuan.framework.util.StringUtils;
/**
 * 
 * @author Administrator
 *
 */
@Service("searchDao")
public class SearchDaoImpl implements SearchDao ,Serializable{
	
	private static  int  error = 0;
	
	private static final Log LOG = LogFactory.getLog(SearchDaoImpl.class);
	
	private static final long serialVersionUID = 8096998630734830325L;
	
	private static final String SEARCH_INDEX_COUNT = "select count(*) from search_index ";
	
	private static final String SEARCH_INDEX_STATUS = "select count(*) from interface_monitor im where im.procedure=? and im.lasttime>? ";
	
	private static final String SELECT_AUTHOR_STRING_IS_NULL = "select psid,author from search_index where authorstring is null limit 10000 ";
	
	private static final String UPDATE_AUTHOR_STRING = "update search_index set authorstring = ? where psid = ? ";
	
	private static final String SEARCH_INDEX_SQL = 
	//SAP编号,商品编号,商铺,商品名称,商品状态,商品销售名称,销售价
	"SELECT p.id AS pid, ps.id AS psid, s.id AS sid, s.shopname AS sname, sc. CODE AS scode,ps.sellname as name, p.listprice, "+
	//销售状态,供应类型,预售结束日期,条形码,制造商,出版日期,制造者,描述内容,商品种类,总销售
	"ps.salestatus, ps.supplytype, pb.enddate, p.barcode, p.manufacturer, p.productiondate, p.author, pd.content, p.sort, psp.totalsale, "+
	//评分,总点击数,总评论数,促销语,副标题,月销售,周销售,月点击数,月评论数,最后上架时间
	"psp.rank, psp.totalvisit, psp.totalcomment, ps.promvalue, ps.subheading,  psp.monthsale, psp.weeksale, psp.monthvisit, psp.monthcomment, psp.lastonshelftime, "+
	//常规供应商（仅文轩商品）,外部编号,创建时间,存储方式
	"p.vendor, ps.outerid, p.createtime, ps.storagetype, "+
	//商品链接 producturl
	"concat( 'http://www.winxuan.com/product/', ps.id )producturl, "+
	//(促销价格,销售价/定价 保留2位)discount
	"round( IF( ps.promotionprice IS NOT NULL and NOW() BETWEEN ps.promstarttime and ps.promendtime, ps.promotionprice, ps.saleprice )/ p.listprice, 2 )discount, "+
	//(促销价格,销售价/销售价)saleprice
	"IF( ps.promotionprice IS NOT NULL and NOW() BETWEEN ps.promstarttime and ps.promendtime, ps.promotionprice, ps.saleprice )saleprice, "+
	//(默认图片,商品图片/图片链接)imageurl
    "IF( pi.url IS NULL, 'http://static.winxuancdn.com/goods/mid_blank.jpg', pi.url )imageurl, "+
	//(是否有图片/是,否)hasimage
    "IF( pi.url IS NULL, 'false', 'true' )hasimage,"+
	//(库存数量>销售总量/是,否有库存)hasstock
    "IF( ps.stockquantity > ps.salequantity, 'true', 'false' )hasstock, "+
    //(库存数量<销售总量/0,可转换量)avaliblequantity
    "IF( ps.stockquantity < ps.salequantity, 0, ps.stockquantity - ps.salequantity )avaliblequantity, "+
    //true删除    false修改
    "IF( ps.salestatus in (13005,13003,13004) ,'true', 'false' )isdelete,"+
    //相对强度 strength
    "case when psw.strength is null then 1 when psw.strength = 0 then 1 else psw.strength end strength "+
    
    "FROM product p LEFT JOIN product_sale ps ON ps.product = p.id LEFT JOIN product_booking pb ON pb.productsale = ps.id LEFT JOIN product_image pi ON pi.product = p.id "+
    "AND pi.type = 10 LEFT JOIN product_description pd ON pd.product = p.id AND pd. NAME = '内容简介' LEFT JOIN product_sale_performance psp ON psp.productsale = ps.id "+
    "LEFT JOIN shop s ON s.id = ps.shop LEFT JOIN product_sale_shopcategory psc ON psc.productsale = ps.id LEFT JOIN shopcategory sc ON psc.shopcategory = sc.id "+
    "LEFT JOIN product_sale_week_statistics psw ON psw.productsale = ps.id "+
    "WHERE ";

	
	private static final String SEARCH_UPDATE_SQL = 
	"SELECT ps.id AS psid,IF( ps.salestatus in (13005,13003,13004) ,'true', 'false' )isdelete,'true' as available,now() as createtime "+
	"FROM product p LEFT JOIN product_sale ps ON ps.product = p.id LEFT JOIN shop s ON s.id = ps.shop " +
	"LEFT JOIN salebyday oi ON ps.id = oi.productsale "+
	//WHERE 商铺已激活,上架销售,出版日期小于365天,商品种类：图书
    "WHERE s.state = 36002 AND( ps.salestatus = 13002 OR( TO_DAYS(NOW())- TO_DAYS(p.productiondate)<= 365 AND p.sort = 11001 ) "+
	//出版日期小于183天,商品种类：音像
    "OR( TO_DAYS(NOW())- TO_DAYS(p.productiondate)<= 183 AND p.sort = 11002 ) "+
    "OR ps.supplytype = 13102 OR TO_DAYS(NOW())- TO_DAYS(oi.saledate)<= 365) AND ps.salestatus NOT IN(13003, 13004, 13005) GROUP BY ps.id";
	
	private static final String CHANGE_PROMOTIONPRICE = "UPDATE search_index s,(SELECT ps.id,ps.promotionprice FROM search_index s,product_sale ps "+
	"WHERE s.psid = ps.id AND ps.promotionprice IS NOT NULL AND LEFT(ps.promstarttime, 10)>= LEFT(now(), 10) AND LEFT(ps.promendtime, 10) "+
	"<= LEFT(now(), 10))a SET s.saleprice = a.promotionprice, s.discount = round( a.promotionprice / s.listprice, 2 ) WHERE s.psid = a.id;";
			
	private static final String CHANGE_EBOOK = "UPDATE search_index s,(	SELECT pid, psid, storagetype, saleprice "+
	"FROM search_index WHERE storagetype = 6004 GROUP BY pid )a SET s.epsid = a.psid, s.esaleprice = a.saleprice "+
	"WHERE a.pid = s.pid AND s.storagetype <> 6004;";
	
	private static final String CHANGE_HASBOOK_0 = "update search_index set hasbook=0; ";
	private static final String CHANGE_HASBOOK_1 ="update search_index s1,search_index s2  set s1.hasbook=1 " +
	"where s1.storagetype=6004 and s2.epsid<>0 and s1.psid=s2.epsid;";
	
	private static final String SEARCH_UPDATE_COUNT_SQL = "select psid from search_update";
	private static final String SEARCH_UPDATE_CLEAR_SQL = "delete from search_update;";
	private static final String SEARCH_INDEX_CLEAR_SQL = " delete from search_index;";
	private static final String CLEAN_SEARCH_UPDATE = "delete from search_update";
	private static final String AVAILABLE = "available";
	private static final String PRODUCTURL = "producturl";

	
	//需要转换时间字段
	private static final List<String> SQL_DATE = Arrays.asList("createtime","productiondate","lastonshelftime","enddate");
	//不需要字段
	private static final List<String> SQL_FILED = Arrays.asList("available","isdelete");
	private static final String INCREMENT_PRODUCT = "select  ps.id AS psid,IF( ps.salestatus in (13005,13003,13004) ," +
				"'true', 'false' )isdelete,'true' as available,now() as createtime " +
				" from product_sale ps where updatetime > ";
	private static final String LAST_TIME = "select if(createtime is null,now(),createtime) as createtime from search_update limit 1";
	
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	
	
	@Override
	public long searchIndexCount() {
		return jdbcTemplateEcCore.queryForLong(SEARCH_INDEX_COUNT);
	}
	
	@Override
	public void updateAuthorString(){
		while(true){
			List<Map<String, Object>> list =  jdbcTemplateEcCore.queryForList(SELECT_AUTHOR_STRING_IS_NULL);
			if(CollectionUtils.isEmpty(list)){
				return;
			}
			for(Map<String, Object> map : list){
				Long psid = Long.valueOf(map.get("psid").toString());
				String author = map.get("author") == null ? "" : map.get("author").toString();
					
					String authorString = AuthorStringTokenizer.getAuthorNameString(author);
				if(StringUtils.isNullOrEmpty(authorString)){
					authorString = "";
				}
				jdbcTemplateEcCore.update(UPDATE_AUTHOR_STRING, authorString , psid);
				LOG.info("psid:" + psid + ",author:" + author + "  ======>> authorstring:" + authorString);
			}
		}
	}

	@Override
	public boolean isSearchIndexFinished(Date dateStart) {
		return jdbcTemplateEcCore.queryForLong(SEARCH_INDEX_STATUS, new Object[]{
				"up_search_index",dateStart
		})>0?true:false;
	}
//------------------------------ 全量	
	
	@Override
	public void updateQuery() throws ParseException, SQLException{
		SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LOG.info("开始执行索引更新\n清空search_update,search_index......");
		clearQuery();
		LOG.info("开始抓取商品id");
		List<Map<String, Object>> update = jdbcTemplateEcCore.queryForList(SEARCH_UPDATE_SQL);
		addSearchUpdate(update,formart);
		List<Map<String, Object>> updateAll = jdbcTemplateEcCore.queryForList(SEARCH_UPDATE_COUNT_SQL);
		addSearchIndex(updateAll,formart);
		LOG.info("更新促销价格");
		jdbcTemplateEcCore.execute(CHANGE_PROMOTIONPRICE);
		LOG.info("更新电子书");
		jdbcTemplateEcCore.execute(CHANGE_EBOOK);
		LOG.info("更新未有图书");
		jdbcTemplateEcCore.execute(CHANGE_HASBOOK_0);
		LOG.info("更新更新有书");
		jdbcTemplateEcCore.execute(CHANGE_HASBOOK_1);
		LOG.info("完成");
	}
	
//------------------------------- 增量
	
	@Override
	public void incrementQuery() {
		LOG.info("增量开始");
		SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = jdbcTemplateEcCore.queryForObject(LAST_TIME, Date.class);
		List<Map<String, Object>> increment = jdbcTemplateEcCore.queryForList(INCREMENT_PRODUCT+"'"+formart.format(date)+"'");
		LOG.info("增量更新："+increment.size());
		//清空增量数据
		jdbcTemplateEcCore.execute(CLEAN_SEARCH_UPDATE);
		addSearchUpdate(increment,formart);
		List<Map<String, Object>> updateAll = jdbcTemplateEcCore.queryForList(SEARCH_UPDATE_COUNT_SQL);
		addSearchIndex(updateAll, formart);
	}
	/**
	 *  批量插入 search_update（记录增量，全量变更数据 psid）
	 * @param update 保存对象
	 * @param formart 时间格式
	 */
	public void addSearchUpdate(List<Map<String, Object>> update,SimpleDateFormat formart){
		Date select = new Date();
		int countall = update.size();
		List<Map<String, Object>> pag = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < countall; i++) {
			pag.add(update.get(i));
			if(i%5000==0||i==(countall-1)){
				Date pt = new Date();
				insertUpdate(pag,formart);
				LOG.info(String.format("当前处理：", i,(new Date().getTime()-pt.getTime()))+i);
				pag.clear();
			}
		}
		LOG.info(String.format("程序开始执行时间:%s;\n 抓取完成： 总共用时：%s 秒;\n总共：%s 条;",
				formart.format(select),(new Date().getTime()-select.getTime())/1000,countall));
	}
	
	/**
	 * 批量插入 search_index（记录商品数据）
	 * @param updateAll 保存对象
	 * @param formart  时间格式
	 * @param type  true 全量 ,false 增量
	 */
	public void addSearchIndex(List<Map<String, Object>> updateAll,SimpleDateFormat formart){
		Date select = new Date();
		LOG.info(String.format("程序开始执行时间:%s;\n 获取所有search_update ID耗时： 总共用时：%s 秒;",
				formart.format(select), (new Date().getTime()-select.getTime())/1000,error));
		int countall = updateAll.size();
		int inscount = 0;
		StringBuilder psid = new StringBuilder();
		for (Map<String, Object> map : updateAll) {
			psid.append(map.get("psid").toString()+",");
			if(inscount%500==0||inscount==countall-1){
				Date ins = new Date();
				List<Map<String, Object>> index = jdbcTemplateEcCore.queryForList(SEARCH_INDEX_SQL + "ps.id in ("+psid.substring(0,psid.length()-1)+")");
				insertIndex(index,formart,psid);
				psid.delete(0,psid.length());
				LOG.info(String.format("已插入%s条：%s 秒;", inscount,(new Date().getTime()-ins.getTime())/1000));
			}
			inscount++;
		}
		LOG.info(String.format("程序开始执行时间:%s;\n search_index数据更新完成： 总共用时：%s 秒;\n执行插入：%s 次;\n插入sql错误：%s 条;",
				formart.format(select), (new Date().getTime()-select.getTime())/1000,inscount,error));
	}
	
	/**
	 * 插入search_update 
	 * @param list
	 * @param formart
	 */
	public void insertUpdate(List<Map<String, Object>> list,SimpleDateFormat formart){
		Date ins = new Date();
		String insert = "insert into search_update";
		StringBuilder keys = new StringBuilder();
		StringBuilder value = new StringBuilder();
		Set<String> key = list.get(0).keySet();
			for (String string : key) {
					keys.append(string+",");
			}
			keys = new StringBuilder(keys.substring(0,keys.length()-1));
			for (Map<String, Object> map : list){
				value.append("(");
				//需要删除的数据
				for (Map.Entry<String, Object> val : map.entrySet()) {
					if(SQL_DATE.contains(val.getKey())){
						value.append( "'"+formart.format(val.getValue())+"',");
					}else{
						value.append( val.getValue()+",");
					}
				}
				value = new StringBuilder(value.substring(0,value.length()-1));
				value.append("),");
			}
		insert = insert +"("+ keys +") VALUES "+ value.substring(0,value.length()-1);
		try {
			jdbcTemplateEcCore.execute(insert);
		} catch (Exception e) {
			LOG.info("search_update执行sql异常"+e);
		}	
		LOG.info("执行sql："+(new Date().getTime()-ins.getTime())/1000+"秒");
	}
	
	/**
	 * 插入search_index
	 * @param list
	 * @param formart
	 * @param psid
	 */
	public void insertIndex(List<Map<String, Object>> list,SimpleDateFormat formart,StringBuilder psid){
		String insert = "replace into search_index";
		StringBuilder keys = new StringBuilder();
		StringBuilder value = new StringBuilder();
		Set<String> key = list.get(0).keySet();
			for (String string : key) {
				if(!AVAILABLE.equals(string)){
					keys.append(string+",");
				}
			}
			keys.append("authorstring").toString();
			for (Map<String, Object> map : list){
				value.append("(");
				for (Map.Entry<String, Object> val : map.entrySet()) {
					if(val.getValue()==null||StringUtils.isNullOrEmpty(val.getValue().toString())){
						value.append( null + ",");
					}else if(SQL_DATE.contains(val.getKey())){
						value.append( "'"+formart.format(val.getValue())+"',");
					}else if(PRODUCTURL.equals(val.getKey())){
						value.append( "'http://www.winxuan.com/product/"+map.get("psid")+"',");
					}else if(SQL_FILED.contains(val.getKey().toString())){
						value.append( val.getValue()+",");
					}else{
						value.append( "'"+val.getValue().toString().replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "\\\\'")+" ',");
					}
				}
				try{
					String author = map.get("author") == null ? null : map.get("author").toString();
					value = value.append("'" +AuthorStringTokenizer.getAuthorNameString(author)
							.replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "\\\\'")+"'");
				}catch (Exception e) {
					value = value.append("' '");
				}
					value.append("),");
			}
		insert = insert +"("+ keys +") VALUES "+ value.substring(0,value.length()-1);
		try {
			jdbcTemplateEcCore.execute(insert);
		} catch (DataAccessException e) {
			error++;
			LOG.error("有数据插入失败 \n"+e);
			LOG.info("未插入商品:"+psid);
		}	
	}
	
	@Override
	public void clearQuery() {
		jdbcTemplateEcCore.execute(SEARCH_UPDATE_CLEAR_SQL);
		jdbcTemplateEcCore.execute(SEARCH_INDEX_CLEAR_SQL);
	} 
}
