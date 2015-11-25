/**
 * 
 */
package com.winxuan.ec.task.job.ec.product;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.ec.EcProductService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 商品责任人信息同步
 * @author monica
 *
 */
@Component("productResponsibilitySyncJob")
public class ProductResponsibilitySyncJob implements TaskAware, Serializable{

	private static final long serialVersionUID = -2893154656066796378L;
	private static final Logger LOG = Logger.getLogger(ProductRelationToAmazonJob.class);
    
    
    @Autowired
    private EcProductService ecProductService;
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "productResponsibilitySyncJob";
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return "同步商品责任人信息，更新product表的author字段";
    }

    @Override
    public String getGroup() {
        return TaskAware.GROUP_EC_CORE;
    }

    @Override
    public void start() {
        int limit = 300;
        int syncNum = 0;
        int sum = 0;
        do{
            syncNum = ecProductService.syncResponsibilityInfo(limit);
            sum += syncNum;
            LOG.info(String.format("当前已同步个数  total:%s", sum));
        } while(syncNum >= limit);
        LOG.info(String.format("商品责任人信息同步完成, 总共同步个数, %s", sum));
    }
}
