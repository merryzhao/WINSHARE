package com.winxuan.ec.dao;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductAuthor;
import com.winxuan.ec.model.product.ProductBooking;
import com.winxuan.ec.model.product.ProductCategoryStatus;
import com.winxuan.ec.model.product.ProductDescription;
import com.winxuan.ec.model.product.ProductExtend;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.model.product.ProductImageZoomTask;
import com.winxuan.ec.model.product.ProductManageGrade;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleLog;
import com.winxuan.ec.model.product.ProductSaleStock;
import com.winxuan.ec.model.product.ProductSaleStockLog;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.framework.dynamicdao.annotation.Delete;
import com.winxuan.framework.dynamicdao.annotation.Evict;
import com.winxuan.framework.dynamicdao.annotation.Get;
import com.winxuan.framework.dynamicdao.annotation.Merge;
import com.winxuan.framework.dynamicdao.annotation.Save;
import com.winxuan.framework.dynamicdao.annotation.SaveOrUpdate;
import com.winxuan.framework.dynamicdao.annotation.Update;
import com.winxuan.framework.dynamicdao.annotation.query.Condition;
import com.winxuan.framework.dynamicdao.annotation.query.Conditions;
import com.winxuan.framework.dynamicdao.annotation.query.MaxResults;
import com.winxuan.framework.dynamicdao.annotation.query.Order;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBy;
import com.winxuan.framework.dynamicdao.annotation.query.OrderBys;
import com.winxuan.framework.dynamicdao.annotation.query.Page;
import com.winxuan.framework.dynamicdao.annotation.query.Parameter;
import com.winxuan.framework.dynamicdao.annotation.query.ParameterMap;
import com.winxuan.framework.dynamicdao.annotation.query.Query;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 * 
 * @author HideHai
 * @version 1.0,2011-7-14
 */
public interface ProductDao {

	@Get
	Product get(Long id);
	
	@Get
	ProductSale getProductSale(Long id);
	
	@Get
	ProductManageGrade getProductManageGrade(Long id);

	@Get
	ProductBooking getProductBooking(Long id);
	
	@Get
	ProductAuthor getProductAuthor(Long id);
	
	@Save
	void save(Product product);
	
	@Save
	void save(ProductImage productImage);

	@Save
	void save(ProductSale productSale);
	
	@Save
	void save(ProductExtend productExtend);
	
	@Save
	void save(ProductDescription productDescription);
	
	@SaveOrUpdate
	void saveOrUpdate(ProductBooking booking);
	
	@Save
	void save(ProductSaleLog log);
	
	@Save
	void save(ProductManageGrade grade);
	
	@Save
	void save(ProductCategoryStatus productCategoryStatus);

    @Save
    void save(ProductImageZoomTask task);

    @Update
    void update(ProductImageZoomTask task);

	@Update
	void update(ProductSale productSale);
	
	@Merge
	void merge(ProductSale productSale);

	@Update
	void update(Product product);
	
	@Update
	void update(ProductImage productImage);
	
	@Update
	void update(ProductManageGrade grade);
	
	@Delete
	void deleteExtend(ProductExtend extend);
	
	@Delete
	void deleteDescription(ProductDescription description);
	
	@Delete
	void deleteImage(ProductImage image);
	
	@Delete
	void deleteManageGrade(ProductManageGrade grade);
	
	
	@Query("from Product p")
	@Conditions({ 
		@Condition("p.id in:ids"),
		@Condition("p.barcode in:isbns")
		})
    List<Product> findProduct(@ParameterMap Map<String, Object> parameters,@Page Pagination pagination);
	
	@Query(sqlQuery=true,value="select p.barcode,count(p.id) num from product p where p.barcode in :barcode group by p.barcode")
	List<Object> isbnMate(@Parameter("barcode") Object[] barcodes);

	@Query("from ProductSale p")
	@Conditions({ 
		@Condition("p.shop=:shop"),
		@Condition("p.product=:product"),
		@Condition("p.outerId=:outerId") 
		})
	ProductSale get(@Parameter("shop") Shop shop,
			@Parameter("product") Product product,
			@Parameter("outerId") String outerId);
	
	@Query("from ProductSale p")
	@Conditions({
		@Condition("p.shop=:shop"),
		@Condition("p.outerId=:outerId")
	})
	List<ProductSale> get(@Parameter("shop") Shop shop,
			@Parameter("outerId") String outerId);
	
	
	@Query("Select distinct p from ProductSale p left join p.shopCategoryList s left join p.product pp left join pp.categories pc")
	@Conditions({
		  @Condition("p.product.id = :productId"),
		  @Condition("p.product.id in :productIds"),
		  @Condition("p.id = :productSaleId"),
		  @Condition("p.id in :productSaleIds"),
		  @Condition("p.product.name like :productName"),
		  @Condition("p.sellName like :sellName"),
		  @Condition("p.product.complex = :complex"),
		  @Condition("p.product.manufacturer like :manufacturer"),
		  @Condition("p.product.hasImage = :hasPicture"),
		  @Condition("p.product.barcode = :productBarcode"),
		  @Condition("p.product.barcode in :productBarcodes"),
		  @Condition("p.product.merchId = :prodcutMerchId"),
		  @Condition("p.product.merchId in :prodcutMerchIds"),
		  @Condition("p.product.author = :productAuthor"),
		  @Condition("p.hasShopCategory = :hasShopCategory"),
		  @Condition("p.product.mcCategory = :productMcCategory"),
		  @Condition(" ( pc.code = :categoryCode or pc.code like CONCAT( :categoryCode  ,'.%'  ) )  "),
		  @Condition("p.shop.shopName = :shopName"),
 		  @Condition("p.shop in :shops"),
		  @Condition("p.shop.id = :shopId"),
		  @Condition("p.shop.businessScope = :businessScope"),
		  @Condition("p.outerId = :outerId"),
		
		  @Condition("p.outerId in :outerIds"),
		  @Condition("p.saleStatus.id = :saleStatus"),
		  @Condition("p.saleStatus.id in :saleStatuses"),
		  @Condition("p.auditStatus.id = :auditStatus"),
		  @Condition("p.auditStatus.id in :auditStatuses"),
		  @Condition("s.id=:categoryid"),
		  @Condition("p.supplyType.id in :supplyTypes"),
		  @Condition("pc.id = :categoryId"),
		  @Condition("p.hasShopCategory=:hasShopCategory"),
		  @Condition("p.storageType.id=:storageType"),
		  @Condition("p.storageType.id<>:notStorageType"),
		  @Condition("p.updateTime>= :startTime"),
		  @Condition("p.updateTime<= :endTime"), 
		  @Condition("p.product.productionDate>= :productionStartDate"),
		  @Condition("p.product.productionDate<= :productionEndDate"), 
		  @Condition("p.product.virtual=:virtual"),
		  @Condition("p.stockQuantity=:stockQuantity"),
		  @Condition("p.product.listPrice > :listpriceMin"),
		  @Condition("p.product.listPrice < :listpriceMax"),
		  @Condition("(p.salePrice / p.product.listPrice)*100 > :discountMin"),
		  @Condition("(p.salePrice / p.product.listPrice)*100 < :discountMax"),
		  @Condition("p.stockQuantity>=:stockQuantityMin"),
		  @Condition("p.stockQuantity<=:stockQuantityMax"),
		  @Condition("p.product.complex =:complex"),
		  @Condition("p.hasBundle = :bundle")
	})  
	List<ProductSale> find(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
	@Query("Select distinct p from ProductSale p left join p.shopCategoryList s left join p.product pp left join pp.categories pc")
	@Conditions({
		@Condition("s.id=:categoryid OR s.parent=:categoryid"),
		  @Condition("p.saleStatus.id = :saleStatus"),
		  @Condition("pc.id = :categoryId")
	})  
	List<ProductSale> findByCategory(@ParameterMap Map<String, Object> parameters,
			@Page Pagination pagination);
	
	@Query("select distinct p from ProductSale p left join  p.complexItemList c")
	@Conditions({
		@Condition("c.id in :items"),
		@Condition("p.updateTime>= :startTime"),
		@Condition("p.id in :productSaleIds"),
		@Condition("p.product.name like :productName"),
		@Condition("p.saleStatus.id = :saleStatus"),
		@Condition("p.product.complex = :complex"),
		@Condition("p.updateTime<= :endTime") 
	})
	List<ProductSale> findComplex(@ParameterMap Map<String, Object> parameters, @Page Pagination pagination);
	
	@Query("select count(p.id) from Product p where p.barcode=?")
	Long getCountByIsbn(String barcode);
	
	@Query("select distinct p from ProductSalePerformanceOnShelf pr inner join pr.productSale p inner join pr.productSaleCTs psct ")
	@Conditions({ 
		@Condition("p.storageType.id = :storageType"),
		@Condition("p.promotionPrice is not null AND true=:promotionPrice"),
		@Condition("p.promotionStartTime < :promotionStartTime"),
		@Condition("p.promotionEndTime >= :promotionEndTime"),
		@Condition("p.promotionStartTime > :promotionStartBegin"),
		@Condition("p.stockQuantity - p.saleQuantity > :availableQuantity"),
		@Condition("p.product.hasImage = :hasPicture"),
		@Condition(" ( TO_DAYS( now() ) - TO_DAYS( pr.lastOnShelfTime ) <= :onShelfDate ) "),
		@Condition(" ( TO_DAYS( now() ) - TO_DAYS( p.product.productionDate ) <= :publishDate ) "),
		@Condition("p.product.complex = :complex"),
		@Condition("pr.discount <= :discount "),
		@Condition("psct.ct1 = :category1"),
		@Condition("psct.ct2 = :category2"),
		@Condition("psct.ct3 = :category3"),
		@Condition("psct.ct4 = :category4"),
		@Condition("psct.ct5 = :category5"),
		@Condition("psct.ct6 = :category6"),
		@Condition("psct.ct7 = :category7"),
		@Condition("psct.ct8 = :category8")
	})
	@OrderBys({ 
		@OrderBy("pr.monthSale desc"), 
		@OrderBy("pr.lastOnShelfTime desc"),
		@OrderBy("pr.monthVisit desc"),
		@OrderBy("pr.monthFavorite desc"),
		@OrderBy("pr.monthDigging desc"),
		@OrderBy("pr.monthSale desc") //pp.weekVisit*0.1+pp.monthSale*5+pp.monthFavorite*2+pp.monthComment*3 desc
	})
	List<ProductSale> findProductSaleByPerformerce(@ParameterMap Map<String, Object> parameters,@Order short orderBy, @MaxResults int size);
	
	@Query("select p from ProductSalePerformanceOnShelf pr inner join pr.productSale p inner join pr.productSaleCTs psct ")
	@Conditions({ 
		@Condition("p.storageType.id = :storageType"),
		@Condition("p.promotionPrice is not null AND true=:promotionPrice"),
		@Condition("p.promotionStartTime < :promotionStartTime"),
		@Condition("p.promotionEndTime >= :promotionEndTime"),
		@Condition("p.promotionStartTime > :promotionStartBegin"),
		@Condition("p.stockQuantity - p.saleQuantity > :availableQuantity"),
		@Condition("p.product.hasImage = :hasPicture"),
		@Condition(" ( TO_DAYS( now() ) - TO_DAYS( pr.lastOnShelfTime ) <= :onShelfDate ) "),
		@Condition(" ( TO_DAYS( now() ) - TO_DAYS( p.product.productionDate ) <= :publishDate ) "),
		@Condition("p.product.complex = :complex"),
		@Condition("pr.discount <= :discount "),
		@Condition("psct.ct1 = :category1"),
		@Condition("psct.ct2 = :category2"),
		@Condition("psct.ct3 = :category3"),
		@Condition("psct.ct4 = :category4"),
		@Condition("psct.ct5 = :category5"),
		@Condition("psct.ct6 = :category6"),
		@Condition("psct.ct7 = :category7"),
		@Condition("psct.ct8 = :category8")
	})
	@OrderBys({ 
		@OrderBy("pr.monthSale desc"), 
		@OrderBy("pr.lastOnShelfTime desc"),
		@OrderBy("pr.monthVisit desc"),
		@OrderBy("pr.monthFavorite desc"),
		@OrderBy("pr.monthDigging desc"),
		@OrderBy("pr.monthSale desc") //pp.weekVisit*0.1+pp.monthSale*5+pp.monthFavorite*2+pp.monthComment*3 desc
	})
	List<ProductSale> findProductSaleByPerformerceWithNoDistinct(@ParameterMap Map<String, Object> parameters,@Order short orderBy, @MaxResults int size);
	
	@Query("from ProductSale p left join p.product pp left join pp.categories pc where p.saleStatus.id = 13002")
	@Conditions({ 
		@Condition(" ( pc.code = :categoryCode or pc.code like CONCAT( :categoryCode  ,'.%'  ) )  "),
		@Condition("p.promotionPrice is not null AND true=:promotionPrice"),
		@Condition("p.promotionStartTime < :promotionStartTime"),
		@Condition("p.promotionEndTime >= :promotionEndTime"),
		@Condition("p.promotionStartTime > :promotionStartBegin"),
		@Condition("p.stockQuantity - p.saleQuantity > :availableQuantity"),
		@Condition("p.product.hasImage = :hasPicture"),
		@Condition(" ( TO_DAYS( now() ) - TO_DAYS( pp.lastOnShelfTime ) <= :onShelfDate ) "),
		@Condition(" ( TO_DAYS( now() ) - TO_DAYS( p.product.productionDate ) <= :publishDate ) ")
	})
	@OrderBys({ 
		@OrderBy("p.promotionStartTime"), 
		@OrderBy("p.promotionEndTime")
	})
	List<ProductSale> findProductSale(@ParameterMap Map<String, Object> parameters,@Order short orderBy, @MaxResults int size);
	
	@Query(sqlQuery=true,value="select max(id) from product")
	Long getMaxProductId();
	
	@Query("FROM ProductSaleLog log WHERE log.productSale = ? and log.operator<>6 ORDER BY log.id DESC")
	List<ProductSaleLog> find(ProductSale productSale,
			@Page Pagination pagination);
	
	@Evict
	void evict(ProductSale productSale);
	
	@Query(value="UPDATE product_category_status SET status = 3 WHERE product = :productId",sqlQuery=true,autoInfer=false,executeUpdate=true)
	int updateCategoryStatus(@Parameter("productId") Long productId);
	
	@Query(value="UPDATE product_category SET category =:targetCategoryId WHERE category =:categoryId",sqlQuery=true,autoInfer=false,executeUpdate=true)
	int transferCategory4Product(@Parameter("targetCategoryId") Long targetCategoryId,@Parameter("categoryId") Long categoryId);
	
	@Query(value="UPDATE product_category SET category =:targetCategoryId WHERE category =:categoryId AND product in :productIds",sqlQuery=true,autoInfer=false,executeUpdate=true)
	int transferCategoryWithProduct(@Parameter("targetCategoryId") Long targetCategoryId,@Parameter("categoryId") Long categoryId,
			@Parameter("productIds")List<String> productIds);
	
	@Query(value="SELECT pc1.product as product FROM product_category pc1,product_category pc2 " +
			"WHERE pc1.product= pc2.product AND pc1.category =:targetCategoryId AND pc2.category=:categoryId",
			sqlQuery=true)
	List<Map<String, Integer>> findConflictCategory(@Parameter("targetCategoryId") Long targetCategoryId,@Parameter("categoryId") Long categoryId);
	
	@Query(value="DELETE FROM product_category WHERE category = :categoryId AND product = :productId",sqlQuery=true,autoInfer=false,executeUpdate=true)
	int removeProductCategory(@Parameter("categoryId") Long categoryId,@Parameter("productId") int productId);
	
	
	/**
	 * 忽略短信发送的缺货商品
	 * @return true,可忽略
	 */
	@Query(value="SELECT productSale IS NOT NULL FROM message_ignore_productsale WHERE productSale IN :productSales", sqlQuery=true)
	boolean existsMessageIgnoreProduct(@Parameter("productSales") List<Long> productSales);
	
	@Delete
	void removeProductBooking(ProductBooking productBooking);


    @Query(value = "from ProductImageZoomTask WHERE status=84001")
    List<ProductImageZoomTask> findProductImageZoomTask(@Page Pagination pagination);
    
    @Save
    void save(ProductSaleStockLog productSaleStockLog);
    
    @Query("select p from ProductImageZoomTask p where p.product = ?")
    ProductImageZoomTask findProductImageZoomTask(Product product);

    @Query(value="UPDATE product_sale_stock SET stock = if(:quantity > 0 or stock > abs(:quantity), stock + :quantity,0) WHERE id = :productSaleStockId",
    		sqlQuery=true,autoInfer=false,executeUpdate=true)
	int updateStock(@Parameter("productSaleStockId") Long productSaleStockId, @Parameter("quantity") int quantity);
    
    @Query(value="UPDATE product_sale_stock SET sales = if(:quantity > 0 or sales > abs(:quantity), sales + :quantity,0) WHERE id = :productSaleStockId",
    		sqlQuery=true,autoInfer=false,executeUpdate=true)
	int updateSales(@Parameter("productSaleStockId") Long productSaleStockId, @Parameter("quantity") int quantity);
    
    @Save
    void save(ProductSaleStock productSaleStock);
    
//	@Query("from ProductAuthor pa")
//	@Conditions({
//		@Condition("pa.productId=:product")
//	})
	@Query("from ProductAuthor pa where pa.productId = ?")
	List<ProductAuthor> findProductAuthors(Product product);

}
