/*
 * @(#)ProductService.java
 *
 * Copyright 2011 Xinhua Online, Inc. All rights reserved.
 */

package com.winxuan.ec.service.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winxuan.ec.exception.CategoryException;
import com.winxuan.ec.exception.ProductException;
import com.winxuan.ec.exception.ProductSaleManageGradeException;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductAuthor;
import com.winxuan.ec.model.product.ProductBooking;
import com.winxuan.ec.model.product.ProductChannelApply;
import com.winxuan.ec.model.product.ProductDescription;
import com.winxuan.ec.model.product.ProductExtend;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.model.product.ProductImageZoomTask;
import com.winxuan.ec.model.product.ProductManageGrade;
import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.model.product.ProductMetaEnum;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.product.ProductSaleLog;
import com.winxuan.ec.model.product.ProductSaleStockLog;
import com.winxuan.ec.model.shop.Shop;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.model.user.User;
import com.winxuan.ec.support.web.pojo.ProductSearch;
import com.winxuan.framework.pagination.Pagination;

/**
 * description
 *
 * @author liuan
 * @version 1.0, 2011-7-11
 */
public interface ProductService {

    /**
     * 获得商品
     *
     * @param id
     * @return
     */
    Product get(Long id);

    /**
     * 获取商品
     *
     * @param parameters
     * @return
     */
    List<Product> findProduct(Map<String, Object> parameters, Pagination pagination);

    /**
     * ISBN匹配结果
     *
     * @param barcodes
     * @return
     */
    List<Object> isbnMate(Object[] barcodes);

    /**
     * 对未匹配销售分类的商品设置销售分类
     *
     * @param product
     * @param category
     */
    void installCategory(Product product, Category category);

    /**
     * 对未匹配销售价格的商品设置销售价格
     *
     * @param productSale
     * @param salePrice
     */
    void installSalePrice(ProductSale productSale, BigDecimal salePrice, User operator);

    /**
     * 更新商品
     * 如果有更新商品上下架，请调用updateProductStatus方法,否则不能记录商品上下架操作日志
     *
     * @param product
     */
    void update(Product product);

    /**
     * 获得销售商品
     *
     * @param product 商品
     * @param shop    卖家店铺
     * @return
     */
    ProductSale get(Product product, Shop shop);

    /**
     * 根据卖家和外部id获得销售商品
     *
     * @param shop     卖家店铺
     * @param outterId 外部id
     * @return
     */
    ProductSale get(Shop shop, String outterId);
    

    ProductSale getProductSale(Long id);
    
    ProductBooking getProductBooking(Long id);
    
    List<ProductSale> getProductList(Shop shop, String outerId);

    /**
     * 更新销售商品
     *
     * @param productSale
     */
    void update(ProductSale productSale);

    /**
     * 删除商品的扩展属性
     *
     * @param extend
     */
    void removeProductExtend(ProductExtend extend);

    /**
     * 删除商品的描述信息
     *
     * @param description
     */
    void removeProductDescription(ProductDescription description);

    /**
     * 删除商品的图片
     *
     * @param description
     */
    void removeProductImage(ProductImage image);
    

    /**
     * 修改商品数量<br/>
     * 数量修改后，当productSale.stockQuantity-productSale.saleQuantity小于等于0时，
     * 设置productSale.status为0（下架）
     *
     * @param productSale         销售商品
     * @param updateStockQuantity 修改的库存数量，
     *                            增加库存数量传入为正数，减少库存数量传入为负数，不修改传入0
     * @param updateSaleQuantity  修改的占用数量，
     *                            增加占用数量传入为正数，减少占用数量传入为负数，不修改传入0
     */
    void updateProductQuantity(ProductSale productSale, int updateStockQuantity,
                               int updateSaleQuantity);

    /**
     * 订单缺货、部分发货时调用，传入缺货的商品，该接口根据规则判断商品库存数是否小于10，如果小于10，将商品立即作下架处理
     *
     * @param productSale
     */
    void updateProductSaleStatusByOutOfStock(ProductSale productSale);

    /**
     * 修改商品销售状态,并记录修改日志
     *
     * @param productSale
     * @param status      更新后的商品状态，productSale中存的是更新前的状态
     * @param operator
     */
    void updateProductStatus(ProductSale productSale, Code status, User operator);

    /**
     * 保存新商品
     *
     * @param product
     */
    void save(Product product);

    /**
     * 保存新的销售商品
     *
     * @param productSale
     */
    void save(ProductSale productSale);

    /**
     * 保存商品图片
     *
     * @param
     */
    void save(ProductImage productImage);

    /**
     * 修改商品图片
     *
     * @param productImage
     */
    void update(ProductImage productImage);

    /**
     * 查找productMeta
     *
     * @param onlyAvailable 是否仅返回有效的
     * @return
     */
    List<ProductMeta> findProductMeta(boolean onlyAvailable);

    /**
     * 根据查询条件查找productMeta
     *
     * @param parameters 查询条件
     * @return
     */
    List<ProductMeta> findProductMeta(Map<String, Object> parameters, Pagination pagination, short orderIndex);

    /**
     * 根据查询条件查找productMeta
     * @param name 属性名称
     * @param category 分类ID
     * @return
     */
    ProductMeta findProductMeta(String name,Integer category); 
    
    /**
     * 得到productMeta
     *
     * @param id
     * @return
     */
    ProductMeta getProductMeta(Long id);

    /**
     * 创建productMeta
     *
     * @param productMeta
     */
    void createProductMeta(ProductMeta productMeta);

    /**
     * 更新productMeta
     *
     * @param productMeta
     */
    void updateProductMeta(ProductMeta productMeta);

    /**
     * 得到ProductMetaEnum
     *
     * @param id
     * @return
     */
    ProductMetaEnum getProductMetaEnum(Long id);

    /**
     * 创建productMetaEnum
     *
     * @param productMeta
     * @param productMetaEnum
     */
    void addProductMetaEnum(ProductMeta productMeta,
                            ProductMetaEnum productMetaEnum);

    /**
     * 更新productMetaEnum
     *
     * @param productMetaEnum
     */
    void updateProductMetaEnum(ProductMetaEnum productMetaEnum);

    /**
     * 删除productMetaEnum
     *
     * @param productMeta
     * @param productMetaEnum
     */
    void deleteProductMetaEnum(ProductMeta productMeta,
                               ProductMetaEnum productMetaEnum);


    /**
     * 查找ProductSale，商品信息查询
     *
     * @param
     * @return
     */
    List<ProductSale> findProductSale(Map<String, Object> parameters,
                                      Pagination pagination);

    /**
     * 根据商品ID查询商品所在套装书
     *
     * @param parameters
     * @param pagination
     * @return
     */
    List<ProductSale> findProductSaleComplex(Map<String, Object> parameters, Pagination pagination);

    /**
     * 查询商品渠道销售申请
     *
     * @param params
     * @param pagination
     * @return
     */
    List<ProductChannelApply> findProductChannelApply(Map<String, Object> params, Pagination pagination);

    /**
     * 审核商品渠道销售申请
     *
     * @param channelApply
     * @param state
     */
    void auditProductChannelApply(ProductChannelApply channelApply, Code state);

    /**
     * @return
     */
    int getTotalLikeByProductSaleId(ProductSale productSale);


    /**
     * 判断isbn是否存在
     *
     * @param isbn
     * @return
     */
    boolean isIsbnExist(String isbn);


    /**
     * 设置新品预售
     *
     * @param productSale 销售商品
     * @param booking     预售信息，必须设置stockQuantity,startDate,endDate
     * @param forSale     是否上架
     * @throws ProductException 
     */
    void setupBooing(ProductSale productSale, ProductBooking booking,
                     boolean forSale) throws ProductException;

    /**
     * 保存商品信息
     *
     * @param productSale
     */
    void saveProduct(ProductSale productSale);


    /**
     * 查询指定分类的商品促销
     *
     * @param category 分类，不指定为查询所有商品
     * @param size     获取的数量
     * @param effect   促销是否生效:true 已经开始 false 即将开始
     * @return
     */
    List<ProductSale> findPromotion(Category category, int size, boolean effect);

    /**
     * 创建套装商品<br/>
     * 创建时以组成商品的码洋合计和实洋合计分别设置套装的码洋和实洋，以组成商品的最低库存可用量-5设置为套装的库存数量<br/>
     * 拷贝第一个商品的信息至套装商品,该接口,设计的时候没有考虑多个dc指定其中一个dc的场景<br/>
     * 如果执意使用该接口,请 productSale.getProductTransient().setComplexDc(complexDc)
     *
     * @param complex 套装商品（传入时是未持久化的，方法执行完后会保存）
     * @param items   组成的商品列表
     * @return
     * @throws ProductException 
     * @see com.winxuan.ec.model.product.ProductSale#copy(ProductSale)
     */
    @Deprecated
    void createComplexProduct(ProductSale complex, List<ProductSale> items) throws ProductException;
    /**
     * 创建套装商品<br/>
     * 创建时以组成商品的码洋合计和实洋合计分别设置套装的码洋和实洋，以组成商品的最低库存可用量-5设置为套装的库存数量<br/>
     * 拷贝第一个商品的信息至套装商品,创建套装书,指定dc.
     *
     * @param complex 套装商品（传入时是未持久化的，方法执行完后会保存）
     * @param items   组成的商品列表
     * @return
     * @throws ProductException 
     * @see com.winxuan.ec.model.product.ProductSale#copy(ProductSale)
     */
    void createComplexProduct(ProductSale complex, List<ProductSale> items,List<Code> dc,Employee employee) throws ProductException;

    /**
     * 添加商品至套装商品，重新计算价格和库存
     *
     * @param complex
     * @param item
     * @throws ProductException 
     */
    void addToComplexProduct(ProductSale complex, ProductSale item) throws ProductException;

    /**
     * 从套装商品中移除单个商品，重新计算价格和库存
     *
     * @param complex
     * @param item
     * @throws ProductException 
     */
    void removeFromComplexProduct(ProductSale complex, ProductSale item) throws ProductException;

    
    void updateComplexProduct(ProductSale complex) throws ProductException;
    
    /**
     * 修改销售商品价格
     *
     * @param productSale 销售商品
     * @param operator    处理人
     * @param price       修改的价格
     */
    @Deprecated
    void updatePrice(ProductSale productSale, User operator, BigDecimal price);

    /**
     * 查询商品修改日志
     *
     * @param productSale
     * @param pagination
     * @return
     */
    List<ProductSaleLog> findProductSaleLog(ProductSale productSale, Pagination pagination);

    /**
     * 判断是否可以上架
     *
     * @param productSale
     * @return
     */
    boolean canBeOnShelf(ProductSale productSale);
    
    /**
     * 商品上下架
     * @param productSale
     */
    void onShelfOrOffShelf(ProductSale productSale);
    
    /**
     * 根据套装书的子商品，获取套装书的供应类型
     * @param complexItemList
     */
    Code getComplexSupplyType(Set<ProductSale> complexItemList);

    /**
     * 同类热销商品
     * 筛选规则：
     * 1、库存大于3，上架状态，有封面
     * 2、属于同类别
     * 3、近30天点击量排序，取前6
     *
     * @param category
     * @return
     */
    List<ProductSale> findProductSaleByCategoryAndSell(Category category);
    
    
    /**
     * 同类热销商品
     * 筛选规则：
     * 1、库存大于3，上架状态，有封面
     * 2、属于同类别
     * 3、近30天点击量排序，取前12
     *
     * @param category
     * @return
     */
    List<ProductSale> findProductSaleByCategoryAndSell(Category category,ProductSale productSale,int size);
    

    /**
     * 最新上架
     * 1、属于本栏目，如管理、计算机、少儿
     * 2、近30天上架的近期出版新书(出版年月为近3个月)
     * 3、上架状态，库存大于50，有封面图片
     * 4、可人工干预(每本书可设置成手动更新，则系统job不更新)
     * 5、最多连续展示7天，连续7天不再显示
     *
     * @param category
     * @return
     */
    List<ProductSale> findProductSaleByLastest(Category category);

    /**
     * 列表页：畅销榜
     * 1、一级大类，近15天总销量，按从高到低排序
     * 2、上架状态，库存大于10，有封面图片
     * 3、取前15条
     * <p/>
     * 更新规则：
     * 1、24小时更新一次
     *
     * @param category
     * @return
     */
    List<ProductSale> findProductSaleByBestSellers(Category category,Long storageType, int size);

    /**
     * 通过不同hql查询商品，解决慢查询
     *
     * @param productSearch
     * @param orderBy
     * @param size
     * @return
     */
    List<ProductSale> findProductSaleByPerformerce(ProductSearch productSearch, short orderBy, int size);

    /**
     * 修改销售商品信息, 同时会检测套装书
     *
     * @param productSale
     * @throws ProductException 
     */
    void updateProductSaleInfo(ProductSale productSale, User operator) throws ProductException;

    /**
     * 停用套装书
     *
     * @param ps
     */
    void complexStop(User operator, ProductSale... ps);

    /**
     * 更新商品分类
     * 将商品分类状态设置为手工设置
     *
     * @param producc
     */
    void updateCategory(Product product);

    void saveManageGrade(ProductManageGrade grade) throws ProductSaleManageGradeException;

    void updateManageGrade(ProductManageGrade grade);

    ProductManageGrade getManageGrade(Long id);

    void deleteManageGrade(ProductManageGrade grade);

    /**
     * 分类下商品批量移动
     * 如果原始分类有子分类，只移动原始分类下的商品，子分类不作处理.
     *
     * @param category       原始分类
     * @param targetCategory 目标分类
     * @throws CategoryException 目标分类末级分类时，抛出此异常
     */
    int transferProductByCategory(Category category, Category targetCategory) throws CategoryException;

    /**
     * 批量移动商品分类
     *
     * @param productIds     需要转移分类的产品编号
     * @param category       原始分类
     * @param targetCategory 转移的目标分类
     * @throws CategoryException 目标分类非末级分类是抛出此异常
     */
    int transferPeoductByCategory(String[] productIds, Category category, Category targetCategory) throws CategoryException;


    /**
     * 查询这批商品是否存在忽略短信
     *
     * @param productsales 销售商品ID
     * @return true:存在 ,   false:不存在
     */
    boolean existsMessageIgnoreProduct(List<Long> productsales);

    List<ProductSale> findByCategory(Map<String, Object> parameters,
                                     Pagination pagination);

    /**
     * 取消商品预售
     * 将商品修改为正常销售,
     * 把正在配货并且不包含预售商品的 的订单 供应类型 修改为.正常销售
     *
     * @param productSale
     * @param employee
     */
    void cancelPresaleProduct(ProductSale productSale, Employee employee) throws ProductException;

    /**
     * 获取购买组合
     *
     * @param id
     * @return
     */
    List<ProductSale> getshoppingCombinations(Long id);


    /**
     * 更新任务
     *
     * @param task
     */
    void update(ProductImageZoomTask task);

    /**
     * 保存任务
     *
     * @param task
     */
    void save(ProductImageZoomTask task);

    /**
     * 查询任务
     *
     * @param page
     * @return
     */
    List<ProductImageZoomTask> findProductImageZoomTask(Pagination page);
    
    void save(ProductSaleStockLog productSaleStockLog);

    /**
     * 商品从停用恢复，更新销售状态
     * @param productSale
     * @param operator
     */
	void recoverProductSaleStatus(ProductSale productSale, User operator);
	
	/**
	 * 从product_author中根据指定的productid找到所有的责任人信息
	 */
	List<ProductAuthor> findProductAuthors(Product product);
}