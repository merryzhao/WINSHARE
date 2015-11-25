package com.winxuan.ec.service.order;

import java.util.List;

/**
 ******************************* 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-7-30 下午2:01:39 --!
 * @description:广播订单状态,后续可扩展为broadcastService.用作高级抽象.所有的发生的事件都可以广播给收听者
 ******************************** 
 */
public interface OrderStatusService {

    /**
     * 订单执行状态发生变更的时候广播出去,仅此用作订单状态改变
     * 
     * @param msg
     *            订单号
     * @param item
     *            广播项
     */
    void broadcastOrderStatus(String orderId, List<Long> item);

}
