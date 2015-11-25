package com.winxuan.ec.task.service.mdm;



public interface MdmService {
	
	public static final String BOOK = "图书";
	
	public static final String BIG_IMG = "封面大图";
	
	public static final String SMALL_IMG = "封面小图";
	
	public static final String ILLUSTRATION_IMG = "书摘插图";
	
	public static final Integer BIG = 3;
	
	public static final Integer SMALL = 1;
	
	public static final Integer ILLUSTRATION = 4;
	
	
	
	
	
	public static final String SELECT_ADD_PRODUCT_COUNT = "select  count(*) from MERCHBASEINFO a WHERE  " +
	"trunc(a.CREATETIME) >trunc(sysdate-1) " +
	" and a.ISCOMPLETE =1";
	
	public static final String SELECT_UPDATE_PRODUCT_COUNT = "select  count(*) from MERCHBASEINFO a WHERE  " +
			"trunc(a.CREATETIME) >trunc(sysdate-1) " +
			" and a.ISCOMPLETE =1";
	
	public static final String SELECT_UPDATE_PRODUCT = "SELECT  t2.* FROM (" +
	" SELECT t1.*, rownum as ROW_NUM from ( " +
	"select  a.MERCHID,a.SAPCODE,a.merchtype,a.BOOKNAME,a.ISBN_ISRC,a.PUBLISHER," +
	"a.AUTHOR,a.PRICE,a.NORMALVENDORID,a.CREATETIME,a.LASTMODIFYTIME, a.SIZEFORMAT ," +
	"a.PAGENUM,a.WORDNUMBER,a.BINDINGFORMAT,a.THISEDITION,a.PRINTINGTIMES," +
	"a.THISEDITIONYEARMONTH,a.THISPRINTINGYEARMONTH ,a.TRANSLATOR ,a.SUBHEADING," +
	"a.SERIESNAME ,a.UNUSABLE " +
	"from MERCHBASEINFO a WHERE  " +
	"a.CREATETIME >to_date('2011-07-08 00:00:00','yyyy-MM-dd hh24:mi:ss') "+
//	"trunc(a.LASTMODIFYTIME) >trunc(sysdate-1) " +
	" and a.ISCOMPLETE =1" +
	") t1 where rownum <= ? " +
	") t2 where t2.row_num >= ?";

	public static final String SELECT_ADD_PRODUCT = "SELECT  t2.* FROM (" +
	" SELECT t1.*, rownum as ROW_NUM from ( " +
	"select  a.MERCHID,a.SAPCODE,a.merchtype,a.BOOKNAME,a.ISBN_ISRC,a.PUBLISHER," +
	"a.AUTHOR,a.PRICE,a.NORMALVENDORID,a.CREATETIME,a.LASTMODIFYTIME, a.SIZEFORMAT ," +
	"a.PAGENUM,a.WORDNUMBER,a.BINDINGFORMAT,a.THISEDITION,a.PRINTINGTIMES," +
	"a.THISEDITIONYEARMONTH,a.THISPRINTINGYEARMONTH ,a.TRANSLATOR ,a.SUBHEADING," +
	"a.SERIESNAME ,a.UNUSABLE ,a.LANGUAGE,a.STARRING " +
	"from MERCHBASEINFO a WHERE  " +
	"a.CREATETIME >to_date('2011-07-08 00:00:00','yyyy-MM-dd hh24:mi:ss')" +
//	"trunc(a.LASTMODIFYTIME) >trunc(sysdate-1) " +
	" and a.ISCOMPLETE =1 " +
	") t1 where rownum <= ? " +
	") t2 where t2.row_num >= ?";

	public static final String SELECT_DESC_PRODUCT = "SELECT clobname,VALUE FROM MerchClobExtInfo WHERE MERCHID =?";

	public static final String FIND_MDM_COMMODITY_MACOTORY = "select SORTTREEID,SORTCODE from MERCHSORTMAP a,SORTCODETREEDEF b "
		+ "where  a.SORTCODEID=b.SORTCODEID and a.MERCHID = ?";
	
	public static final String FIND_MDM_COMMODITY_CD ="select PLAYTIME,DISCQUANTITY from MERCHBASEEXTINFO where MERCHID =? ";

	public static final String INSERT_EC_COMMODITY ="insert into product(name, barcode, manufacturer, productiondate, sort, printdate, author, category," 
		+" listprice, workcategory, managecategory, mccategory, vendor,islock,createtime, updatetime)"
		+" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())";
	
	public static final String UPDATE_EC_COMMODITY = "update product set name=?, barcode=?, manufacturer=?,productiondate=?, sort=?, " +
			"printdate=?, author=?,category=?,listprice=?, workcategory=?, managecategory=?, mccategory=?, vendor=?,updatetime=now() " +
			"WHERE `id`=?";

	public static final String FIND_MAX_ID ="select max(id) from product";

	public static final String GET_EC_CATEGORY ="select category from mccategory where id =? ";

	public static final String GET_EC_COMMODITY_DISCOUNT ="select discount from rule_price where vendor= ? and mccategory= ?";

	public static final String INSERT_EC_COMMODITY_SALE ="insert into product_sale(product,seller,saleprice,status,storagetype,location,stockquantity,salequantity,outerid,_index) values (?,?,?,?,?,?,?,?,?,?)";

	public static final String INSERT_EC_COMMODITY_NOTMATCH_PRICE ="insert into product_notmatch_price  values(?,now())";

	public static final String INSERT_EC_COMMODITY_NOTMATCH_CATEGORY ="insert into product_notmatch_category  values(?,now())";
	
	public static final String INSERT_EC_COMMODITY_EXTEND = "insert into product_extend(product,meta,name,_value,_index,isshow) values (?,?,?,?,?,?)";
	
	public static final String INSERT_EC_COMMODITY_DESC = "insert into product_description (product,meta,name,content,_index,digest) values(?,?,?,?,?,?)";

	public static final String SELECT_EC_PRODUCT_ID = "SELECT product FROM product_sale WHERE outerid=?";
	
	public static final String SELECT_EC_PRODUCT_LOCK = "select `islock` from `product` where id = ?";
	
	public static final String UPDATE_EC_COMMODITY_EXTEND = "update product_extend set _value = ? where product = ? and meta = ?";
	
	public static final String SELECT_MDM_COMMODITY_IMG = "select filename,attachmenttype,content from attachmentfile where MERCHID =?";

	public static final String SELECT_EC_COMMODITY_DESC = "select meta,digest from product_description where product = ?";
	
	public static final String UPDATE_EC_COMMODITY_DESC = "UPDATE product_description SET content = ? WHERE product = ? AND meta = ?";

    public static final String INSERT_EC_COMMODITY_IMG = "INSERT INTO product_image(product,url,type,_index,digest) VALUES (?,?,?,?,?)";
    
    public static final String UPDATE_EC_COMMODITY_SALE = "UPDATE product_sale SET saleprice = ? WHERE product = ? AND outerid=?";
    
    public static final String SELECT_EC_COMMODITY_IMG = "SELECT digest FROM product_image WHERE product=? AND type=?";
    
    public static final String UPDATE_EC_COMMODITY_IMG = "UPDATE product_image SET url=?,digest=? WHERE product=? AND type=?";
	/**
	 * 同步新增商品
	 */
	public void synchNewProduct();

	/**
	 * 同步更新的商品
	 */
	public void synchChangedProduct();

}
