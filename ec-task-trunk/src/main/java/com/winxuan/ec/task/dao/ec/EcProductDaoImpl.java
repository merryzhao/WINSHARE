package com.winxuan.ec.task.dao.ec;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductAuthor;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.service.product.ProductSaleService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.task.model.ec.EcProductCategoryStatus;
import com.winxuan.ec.task.model.robot.RobotProductCategoryLog;
import com.winxuan.framework.util.html.HtmlFilter;

/**
 * 实现类
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
@Repository("ecProductDao")
public class EcProductDaoImpl implements EcProductDao {
	
	private static final long serialVersionUID = 2505245255998276581L;
	
	private static Log log = LogFactory.getLog(EcProductDaoImpl.class);
	
	private static  Map<String,Object[]> descriptionMetaMap = new HashMap<String,Object[]>();
	
    //允许使用的标签
    private static String[] tags = new String[] {"div","br","a","img","p","embed"};
    
    static {
       descriptionMetaMap.put("EditorRecommend", new Object[]{"主编推荐", 9, 1}); 
       descriptionMetaMap.put("ContentsAbstract", new Object[]{"内容简介", 10, 2});
       descriptionMetaMap.put("Summary", new Object[]{"摘要", 11, 3});
       descriptionMetaMap.put("AuthorIntroduction", new Object[]{"作者简介", 13, 4});
       descriptionMetaMap.put("Catalog", new Object[]{"目录", 12, 5});
       descriptionMetaMap.put("MediumCriticism", new Object[]{"媒体评论", 42, 6});
       descriptionMetaMap.put("DelicacyContents", new Object[]{"精彩内容", 43, 7});
       /**
        * 2014-12-11主数据增加两个大字段——名人推荐&&促销语，需同步到ec
        */
       descriptionMetaMap.put("CelebrityEndorsements", new Object[]{"名人推荐",145,8});
       descriptionMetaMap.put("PromotionalLanguage", new Object[]{"促销语",146,9});
    }
    /**
     * 实现类
     * @author Heyadong
     * @version 1.0, 2012-3-26
     */
    private static class MdmProductDescription{
        public Long productId;
        public Long merchclobextid;
        public Long merchId;
        public String clobname;
        public int indexs;
        public String value;
        public String creator;
        public String createtime;
        public int type;
    }
    /**
     * 实现类
     */
    private static class MdmProductResponsibility{
        public Long productId;
        public Long responsibilityId;
        public Long merchId;
        public String identity;
        public String fullName;
        public int sequenceNum;
        public String createtime;
    }
    String sapce4 = "&nbsp;&nbsp;&nbsp;&nbsp;";
    String sapce1 = "&nbsp;";
	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductSaleService productSaleService;
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<EcProductCategoryStatus> getNewProducts(int max) {
		//查询1.图书	 * 2.任务已经添加到robot的(isnew = 0). 3.当前关系为MC分类 status = 2;商品.
		String sqlformat = "SELECT pcs.product,p.barcode FROM product_category_status pcs INNER JOIN product p ON (p.id = pcs.product) WHERE pcs.isnew = 1 AND p.sort = 11001 LIMIT %s";
		return jdbcTemplateEcCore.query(String.format(sqlformat, max), new RowMapper<EcProductCategoryStatus>() {
			@Override
			public EcProductCategoryStatus mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				EcProductCategoryStatus status = new EcProductCategoryStatus();
				status.setProduct(Long.valueOf(rs.getString("product")));
				status.setIsbn(rs.getString("barcode"));
				status.setIsnew(1);
				return status;
			}
		});
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void updateIsNew(List<EcProductCategoryStatus> list) {
		//更新,新书状态. 为=0,表示已添加到robot
		String sqlformat = "UPDATE product_category_status set isnew = 0 WHERE product IN (%s)";
		StringBuilder productIds = new StringBuilder();
		for (EcProductCategoryStatus status : list){
			Assert.notNull(status.getProduct(),"productID IS NULL");
			productIds.append(status.getProduct()).append(',');
		}
		productIds.deleteCharAt(productIds.length() - 1);
		jdbcTemplateEcCore.update(String.format(sqlformat, productIds.toString()));
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<EcProductCategoryStatus> getMcProducts(Date mindate,
			int start, int limit) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlformat = "SELECT p.id,p.barcode FROM product_category_status pcs " +
				"INNER JOIN product p ON (p.id = pcs.product) " +
				"WHERE pcs.status IN (2,5) AND pcs.maxdate > ? " +
				"AND isnew = 0 AND p.sort = 11001 LIMIT ?,?";
		return jdbcTemplateEcCore.query(sqlformat, new Object[]{format.format(mindate), start, limit}, new RowMapper<EcProductCategoryStatus>(){
			@Override
			public EcProductCategoryStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
				EcProductCategoryStatus status = new EcProductCategoryStatus();
				status.setProduct(Long.valueOf(rs.getString("id")));
				status.setIsbn(rs.getString("barcode"));
				status.setStatus(EcProductCategoryStatus.CATEGORY_STATUS_MC);
				status.setIsnew(EcProductCategoryStatus.NEW_FALSE);
				return status;
			}
		});
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProductRelation(List<Long> products) {
		if (products != null && !products.isEmpty()){
			String sqlformat = "DELETE FROM product_category WHERE product IN (%s)";
			jdbcTemplateEcCore.update(String.format(sqlformat, StringUtils.join(products,",")));
		}
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void updateProductStatus(int status,List<Long> products) {
		String sqlformat = "UPDATE product_category_status SET status = %s WHERE product IN (%s)";
		if (products != null && !products.isEmpty()){
			jdbcTemplateEcCore.update(String.format(sqlformat, status, StringUtils.join(products,",")));
		}
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void syncProductCategory(long productId, List<Long> robotIds) {
		if (robotIds != null && !robotIds.isEmpty()){
			String sqlformat = "INSERT INTO product_category(category,product)  SELECT id, %s FROM category WHERE robot_id IN (%s)";
			jdbcTemplateEcCore.update(String.format(sqlformat, productId, StringUtils.join(robotIds,",") ));
		}
	}

    @Override
    public void resetEcProductStatus(List<RobotProductCategoryLog> logs) {
        StringBuilder sql = new StringBuilder();
        
        List<Object> pamars = new ArrayList<Object>();
        //修改为,  分类状态为MC, 修改最大时间为.当前时间 + 1个月 
        pamars.add(EcProductCategoryStatus.CATEGORY_STATUS_MC);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.getTime();
        pamars.add(calendar.getTime());
        
        sql.append("UPDATE product_category_status pcs,product p ")
           .append(" SET pcs.status = ?, pcs.maxdate = ?")
           .append(" WHERE p.id = pcs.product AND p.sort = 11001 AND p.barcode IN (");
             for (RobotProductCategoryLog log : logs) {
                 sql.append("?,");
                 pamars.add(log.getBarcode());
             }
             sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        
        jdbcTemplateEcCore.update(sql.toString(), pamars.toArray());
    }
    
    /**
     ** 商品作者的设计和展示规则需要变化，补充责任人规则
     */
    @Override
    public int syncResponsibilityInfo(int limit){
    	String sql = "SELECT p.id, mmr.responsibilityid, mmr.merchid, mmr.identity, mmr.fullname, mmr.sequencenum, mmr.createtime " +
    			"FROM mdm_merchresponsibilityinfo mmr INNER JOIN product p on (p.merchid = mmr.merchid) " +
    			"LIMIT " + limit;
    	List<Long> productList = new ArrayList<Long>(); 
    	List<MdmProductResponsibility> list = jdbcTemplateEcCore.query(sql,new RowMapper<MdmProductResponsibility>(){
			@Override
			public MdmProductResponsibility mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				MdmProductResponsibility mpr = new MdmProductResponsibility();
				mpr.productId = rs.getLong("id");
				mpr.responsibilityId = rs.getLong("responsibilityid");
				mpr.merchId = rs.getLong("merchid");
				mpr.identity = rs.getString("identity");
				mpr.fullName = rs.getString("fullname");
				mpr.sequenceNum = rs.getInt("sequencenum");
				mpr.createtime = rs.getString("createtime");
				return mpr;
			}
    	});
    	if(!list.isEmpty()){
    		for(MdmProductResponsibility mpr : list){
    			try{
    				if (mpr.identity == null || mpr.identity.isEmpty() || mpr.identity.trim().isEmpty()
							|| ("无".equals(mpr.identity))|| ("无".equals(mpr.identity.trim()))){
						mpr.identity = "";
					}
    				Date updatetime = new Date();
        			boolean isExists = jdbcTemplateEcCore.queryForInt("SELECT COUNT(1) FROM product_author WHERE productid=? AND responsibilityid=? ",mpr.productId, mpr.responsibilityId) > 0;
        			if(isExists){
        				jdbcTemplateEcCore.update("update product_author set identity = ?, fullname = ?, createtime = ?, updatetime = ?, sequencenum = ? " +
        						"where productid = ? and responsibilityid = ?", mpr.identity, mpr.fullName, mpr.createtime, updatetime,
        						mpr.sequenceNum, mpr.productId, mpr.responsibilityId);
        			} 
        			else{
            			jdbcTemplateEcCore.update("insert into product_author(productid,responsibilityid,identity," +
            					"fullname,sequencenum,createtime,updatetime) values(?,?,?,?,?,?,?)", mpr.productId, mpr.responsibilityId, mpr.identity, 
            					mpr.fullName, mpr.sequenceNum, mpr.createtime, updatetime);
        			}
        			if(!productList.contains(mpr.productId)){
        				productList.add(mpr.productId);
        			}
        			jdbcTemplateEcCore.update("delete from mdm_merchresponsibilityinfo where merchid = ? and responsibilityid = ?", 
        					new Object[]{mpr.merchId, mpr.responsibilityId});
    			}catch(Exception e){
    				log.error("productid:" + mpr.productId + "responsibilityid:" + mpr.responsibilityId + "同步责任人信息失败");
    			}
    		}
    	}
    	for (int i = 0; i < productList.size(); i++){
    		Product product = productService.get(productList.get(i));
    		String newAuthor = "";
    		//修改product表的author字段
    		updateProductAuthor(product, newAuthor);
    	}
    	return list.size();
    }
    
    //需要同一个商品的所有责任人都写进中间表之后才能更新product表的author字段
    private void updateProductAuthor(Product product, String newAuthor){
    	Map<Integer, String> mapping = new HashMap<Integer, String>();
    	List<Integer> nums = new ArrayList<Integer>();
    	List<ProductAuthor> productAuthors = productService.findProductAuthors(product);
    	for(ProductAuthor productAuthor : productAuthors){
    		if ((productAuthor.fullname != null && !(productAuthor.fullname.length() == 0) && !(productAuthor.fullname.trim().length() == 0)) &&
					(!("无".equals(productAuthor.fullname)) && !("无".equals(productAuthor.fullname.trim())))){
        		if("".equals(productAuthor.identity)){
        			mapping.put(productAuthor.sequencenum, (productAuthor.fullname + productAuthor.identity));
        		}
        		else{
            		mapping.put(productAuthor.sequencenum, (productAuthor.fullname + " " + productAuthor.identity));
        		}
        		nums.add(productAuthor.sequencenum);
    		}
    	}
    	Collections.sort(nums);
    	for(int j = 0; j < nums.size(); j++){
    		if (j != nums.size()-1){
    			newAuthor += mapping.get(nums.get(j))+"；";
    		}
    		else{
    			newAuthor += mapping.get(nums.get(j));
    		}
    	}
    	jdbcTemplateEcCore.update("update product set author = ? where id = ?", new Object[]{newAuthor, product.getId()});
    }
    
    @Override
    public int syncProductDescription(int limit) {
        String sql = "SELECT p.id, mm.merchclobextid, mm.merchId, mm.clobname, mm.indexs, mm.value, mm.creator, mm.createtime, mm.type " +
        		"FROM mdm_merchclobextinfo mm INNER JOIN product p ON (p.merchid = mm.merchid) " +
        		"WHERE mm.type = 0 LIMIT " + limit;
        
        List<MdmProductDescription> list = jdbcTemplateEcCore.query(sql,new RowMapper<MdmProductDescription>() {
            @Override
            public MdmProductDescription mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                MdmProductDescription mdm = new MdmProductDescription();
                mdm.productId = rs.getLong("id");
                mdm.merchclobextid = rs.getLong("merchclobextid");
                mdm.merchId = rs.getLong("merchId");
                mdm.clobname = rs.getString("clobname");
                mdm.indexs = rs.getInt("indexs");
                mdm.value = rs.getString("value");
                mdm.creator = rs.getString("creator");
                mdm.createtime = rs.getString("createtime");
                mdm.type = rs.getInt("type");
                return mdm;
            }
        });
        
        if (!list.isEmpty()) {
            Set<Long> mdmIdSet = new HashSet<Long>();
            int i=0;
            for (int len = list.size(); i< len; i++) {
               MdmProductDescription m = list.get(i);
               try {
                   //如果 m.value != null,才进行同步, 等于null 后面将其删除
            	   //利用正则表达式来替换同步数据中的对应字符，修改正则表达式来优化html的目录结构
                   if(m.value != null) {
                       Object[] metas = descriptionMetaMap.get(m.clobname);
                       if (metas != null) {
                           String metaName = metas[0].toString();
                           int metaId = (Integer) metas[1];
                           int index = (Integer)metas[2];
                           //保留每个章节的<div>标签,并将<div>标签替换为<br/>标签，保存到product_description表,针对除精彩内容之外的大字段
                           //这里的div标签无论大小写区分，都必须替换
                           String html = HtmlFilter.filter(m.value,tags);
                           html = html.replaceAll("　", "&nbsp;&nbsp;").replaceAll("^(?i)(?:(?:<br */?>)|(?:<p */?>))+","");
                           if ("目录".equals(metaName)
                        		   ||("主编推荐".equals(metaName)) || ("内容简介".equals(metaName))
                        		   ||("作者简介".equals(metaName)) || ("媒体评论".equals(metaName))
                        		   ||("摘要".equals(metaName))|| ("名人推荐").equals(metaName)
                        		   ||("促销语".equals(metaName))){
                               html = html.replaceAll("(?i)<div(.*?)>", "").replaceAll("</(?i)div>", "<br/>");
                           }
                           if ("精彩内容".equals(metaName)) {
                               html = html.replaceAll("(?i)<br */?>(?!&nbsp;&nbsp;&nbsp;&nbsp;)", "<br/>&nbsp;&nbsp;&nbsp;&nbsp;")
                               .replaceAll("(?i)<p */?>(?!&nbsp;&nbsp;&nbsp;&nbsp;)", "<p/>&nbsp;&nbsp;&nbsp;&nbsp;");
                               html = html.replaceAll("^(?:&nbsp;)+(.*)", "&nbsp;&nbsp;&nbsp;&nbsp;$1");
                                if (!html.startsWith("&nbsp;&nbsp;&nbsp;&nbsp;")) {
                                    html = "&nbsp;&nbsp;&nbsp;&nbsp;" + html;
                                }
                           } 
                           
                           /**
                            * 由于历史原因精彩内容文本不正确,
                            * 找到规律
                            * 如果文本存在换行, 或者小于200个字 则不管 认为内容正确
                            * 如果文本存在 页码如:   P1-01 并且没有换行符号 大于200 length 或者 大于1000 length
                            * 出现两种情况 4个空格为一个行换,  如果没有.  则一个空格为一个换行. 之前换行后,加两个缩进符号,则 4个 &nbsp; 
                            *
                            * ps:虽然不严谨,但尽可能让文本格式正确.总比.连续200字没有有换行来的好.
                            * 
                            * 由于之前将空格替换成了 &nbsp; 所以此时   匹配空格 ‘ ’  由'&nbsp;' 代替
                            */
                           if (("精彩内容".equals(metaName) && html.length() > 200)) {
                               //如果文本内没有找到,换行符号, 
                               Pattern pattern = Pattern.compile("[\\n\\r]|(<br */?>)|(<p */?>)");
                               if(!pattern.matcher(html).find()) {
                                   Pattern pageToken = Pattern.compile("P[0-9]+-[0-9]+");
                                   String endToken = html.substring(html.length() - 20);
                                  
                                   //200个字以上,找到 P1-0换行
                                   if (pageToken.matcher(endToken).find() || html.length() > 1000){
                                       
                                       //每段前,有4个nbsp;,这部分需要跳过
                                       int skipLength = sapce4.length();
                                       
                                       if (html.indexOf(sapce4, skipLength) != -1) {
                                           html = sapce4 + html.substring(skipLength).replaceAll(sapce4, "<br />" + sapce4);
                                       } else if (html.indexOf(sapce1, skipLength) != -1) {
                                           html = sapce4 + html.substring(skipLength).replaceAll(sapce1, "<br />" + sapce4); 
                                       }
                                      
                                   }
                               }
                           }
                           html = html.replaceAll("(?i)</p *>","").replaceAll("(?i)<p(?: *| .*?)/?>", "<br/><br/>")
                                   .replaceAll("(?i)</br *>", "").replaceAll("(?i)<br(?: *| .*?)/?>", "<br/>");
                           html = html.replaceAll("(&nbsp;){5,}", sapce4).replaceAll("(?i)(?:&nbsp;)+((?:<br */?>)|(?:<p */?>))", "$1");
                           /**
                            * 前主数据同步过来的促销语字数小于80,且对应的product_sale表中的promvalue字段为空，则写入product_sale表
                            */
                           if("促销语".equals(metaName)){
                        	   Product product = productService.get(m.productId);
                        	   ProductSale productSale = productSaleService.getPsByP(product);
                        	   if (html.length() < 80){
                        		   jdbcTemplateEcCore.update("update product_sale set promvalue = ? where id = ?", 
                        				   new Object[]{html, productSale.getId()});
                        	   }
                           }
                          //如果不为空白内容
                           if(!html.matches("(?i)^((?:<br */?>)|(?:<p */?>)|(?:&nbsp;))+$")){
                               //MD5
                               String digest = DigestUtils.md5Hex(html); 
                               boolean isExists = jdbcTemplateEcCore.queryForInt("SELECT COUNT(1) FROM product_description WHERE product=? AND meta=? ",
                            		   m.productId, metaId) > 0;
                               if (isExists) {
                            	   if("目录".equals(metaName)){
                            		   String oldhtml =  jdbcTemplateEcCore.queryForObject("select content from product_description where product=? and meta=12", 
                            				   String.class,new Object[]{m.productId});
                            		   jdbcTemplateEcCore.update("insert into product_description_log values(null,?,?,?,?,null)",m.merchId,metaId,oldhtml,m.value);
                            	   }
                                   jdbcTemplateEcCore.update("UPDATE product_description SET content=?, digest=?,index_= ? WHERE product=? AND meta=?", html, digest, index, m.productId, metaId);
                               } else {
                            	   if("目录".equals(metaName)){
                            		   jdbcTemplateEcCore.update("insert into product_description_log values(null,?,?,null,?,null)",m.merchId,metaId,m.value);
                            	   }
                                   jdbcTemplateEcCore.update("INSERT INTO product_description(merchid, product, name, meta, content, index_, digest) VALUES(?,?,?,?,?,?,?)"
                                           , m.merchId, m.productId, metaName, metaId, html, index, digest);
                               }
                           }
                       }
                   }
                   
                   jdbcTemplateEcCore.update("DELETE FROM mdm_merchclobextinfo WHERE merchclobextid=?", m.merchclobextid);
               } catch (Exception e) {
                   log.error(e.getMessage());
                   //出现异常记录id,最后会将.未处理的同步数据,type修改为2
                   mdmIdSet.add(m.merchclobextid);
                   continue;
               }
            }
            
            //将未删除(未同步)的 数据type修改为2
            if(!mdmIdSet.isEmpty()) {
                jdbcTemplateEcCore.update(String.format("UPDATE mdm_merchclobextinfo SET type=2 WHERE merchclobextid IN (%s)", StringUtils.join(mdmIdSet,",")));
            }
        }
        return list.size();
    }
    
}
