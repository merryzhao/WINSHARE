package com.winxuan.ec.task.service.ec;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.winxuan.ec.task.model.ec.EcProductCategoryStatus;
import com.winxuan.ec.task.model.ec.convert.ChannelSalesInfo;

/**
 * Ec商品分类同步.
 * 
 * 主要实现功能:
 * 将EC-Core新商品,作为抓取任务,添加到robot系统.
 * 添加后,将新商品标识修改为否,避免添加到重复任务.
 * 
 * @author Heyadong
 * @version 1.0, 2012-3-26
 */
public interface EcProductService extends Serializable{
	/**
	 * 获取新商品
	 * 返回只包含参数
	 * {@link EcProductCategoryStatus#getProduct()}
	 * {@link EcProductCategoryStatus#getIsbn()}
	 * @param max 最大个数
	 * @return list
	 */
	List<EcProductCategoryStatus> getNewProducts(int max);
	
	/**
	 * 同步更新 新商品到 Robot任务里
	 * 将调这批商品设置为已同步
	 * @param list
	 */
	void syncProductToTask(List<EcProductCategoryStatus> list);
	
	/**
	 * 获取MC关系的商品
	 * 主要用于: 将MC分类图书商品 的关系, 替换成 卓越分类商品.
	 * 
	 * 
	 * @param minDate 时间限制  (EcProductCategoryStatus.maxdate < minDate)
	 * @param start 开始位置. 
	 * @param limit 大小
	 * @return list
	 */
	List<EcProductCategoryStatus> getMcProducts(Date minDate, int start, int limit);
	
	/**
	 * 同步商品关系
	 * 
	 * 如果list有商品 能在robot系统中找到对应的卓越分类关系.
	 * 则替换
	 * @param list
	 * @return 真实mc商品转换到 amazon商品的数量
	 */
	int syncProductRelation(List<EcProductCategoryStatus> list);
	
	/**
     * 同步EC商品新分类
     * @param limit 同步 RobotProductCategoryLog 条数
     * @return 实际同步的Log条数,如果小于 limit,所有日志都处理完成.
     */
    int syncProductNewCategory(int limit);
    
    /**
     * 同步商品描述信息
     * @param limit 同步个数限制
     * @return 0 或 小于limit则,所有数据同步完成 
     */
    int syncProductDescription(int limit);
    
    /**
     * 同步商品责任人信息
     */
    int syncResponsibilityInfo(int limit);
    
    /**
     * 统计商品销售数据. - 发货
     * 
     * @param limit 同步个数限制
     * @return 小于limit，则统计完所有数据,大于等于则.未同步完成
     */
    int sumSales(int limit);
    
    /**
     * 统计商品退货数据 
     * @param limit 同步个数限制
     * @return 小于limit，则统计完所有数据,大于等于则.未同步完成
     */
    int sumRefund(int limit);
    
    /**
     * 统计商品拒收数据. - 退货取消,
     * 
     * @param limit 同步个数限制
     * @return 小于limit，则统计完所有数据,大于等于则.未同步完成
     */
    int sumReject(int limit);
    
    /**
     * 合并苏宁销售数据,
     * 文轩苏宁分为： 先采后销40，  先销后采8090（以前是综合,现在算供货渠道）。
     * 由于苏宁提供的销售数据分不出40,8090。
     * 在销售历史统计表里.将8090(先销后采)销售 合并 到40
     * 方便做超卖验证, (8090,40)的发货 是否小于上传销售
     * 
     */
    void mergeSuning();
    
    /**
     * 统计下传SAP的销售商品
     * @param limit
     * @return
     */
    List<ChannelSalesInfo> sumChannelSales(List<ChannelSalesInfo> list, int limit);
    
    /**
     * 统计下传SAP的退货商品
     * @param limit
     * @return
     */
    List<ChannelSalesInfo> sumChannelReturun(List<ChannelSalesInfo> list, int limit);
    
    /**
     * 更新下传SAP商品记录的状态
     * @param limit
     * @return
     */
    int updateSalesRecord(int limit);
    
    /**
     * 统计冲销发货
     * @param limit
     * @return
     */
    List<ChannelSalesInfo> sumRollbackSales(List<ChannelSalesInfo> list, int limit);
    /**
     * 统计冲销退货
     * @param limit
     * @return
     */
    List<ChannelSalesInfo> sumRollbackReturn(List<ChannelSalesInfo> list, int limit);
    
    /**
     * 更新冲销状态
     * @param limit
     * @return
     */
    int updateRollbackRecord(int limit);
}
