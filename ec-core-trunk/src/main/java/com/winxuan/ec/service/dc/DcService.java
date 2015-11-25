package com.winxuan.ec.service.dc;

import java.util.List;
import java.util.Map;

import com.winxuan.ec.exception.BusinessException;
import com.winxuan.ec.model.area.Area;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.dc.DcPriority;
import com.winxuan.ec.model.dc.DcRule;
import com.winxuan.ec.model.dc.DcRuleArea;
import com.winxuan.ec.model.order.Order;
import com.winxuan.ec.model.user.User;
import com.winxuan.framework.pagination.Pagination;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-30 上午11:30:07  --!
 * @description:
 ********************************
 */
public interface DcService {

	DcRule getDc(Long id);

	DcRuleArea getDcArea(Long id);

	void saveDcArea(DcRuleArea area);
	
	void saveDcRule(DcRule dcRule);

	List<DcRule> find(Map<String, Object> parameters, Pagination pagination,Short sort);

	/**
	 * 获取可用的Dc信息
	 * @return
	 */
	List<DcRule> findByAvailableDc();
	
	/**
	 * 获取可用的Dc location
	 * @return
	 */
	List<Long> findAvailableDc();
	
	/**
	 * 获取可用的dc信息
	 * @param dc 名称
	 * @return
	 */
	DcRule findByAvailableDc(String dc);
	
	/**
	 * 根据区域通过优先级排序
	 * @param area
	 * @return
	 */
	List<DcPriority> findDcPriorityByArea(Area area);
	
	/**
	 * 
	 * @param dc code表的dc码
	 * @return
	 */
	DcRule findByAvailableDc(Code dc);
	
	/**
	 * 传入区域信息,获取覆盖改区域的DC信息
	 * @param area
	 * @param dcs
	 * @return
	 */
	List<DcRule> findDcArea(Area area,List<Long> dcs);
	
	/**
	 * 传入区域信息,获取覆盖改区域的DC信息,且根据优先级降序排列
	 * @param area
	 * @param dcs 
	 * @return
	 */
	List<DcRule> findDcAreaOrderByPriority(Area area,List<Long> dcs);
	
	
	/**
	 * 修改订单实际发货的dc信息,并且记录日志
	 * 实际发货由业务人员根据,dc实际情况进行手动分配.在未分配的情况下.实际发货dc和应发货dc一致.
	 * 
	 * orderDcService.updateOrderDcOriginal
	 * @param order
	 * @param target
	 * @return 修改是否成功
	 * @throws BusinessException
	 */
	@Deprecated
	Boolean updateOrderDcOriginal(Order order,Code dc,User user) throws BusinessException;
	
	/**
	 * 更新DC规则
	 * @param dcRule
	 */
	void updateDcRule(DcRule dcRule);
	
	/**
	 * 删除DC配送区域
	 * @param dcRuleArea
	 */
	void removeDcRuleArea(DcRuleArea dcRuleArea);
	

}
