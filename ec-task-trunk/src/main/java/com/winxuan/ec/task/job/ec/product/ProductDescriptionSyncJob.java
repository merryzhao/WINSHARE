package com.winxuan.ec.task.job.ec.product;

import java.io.Serializable;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winxuan.ec.task.service.ec.EcProductService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

/**
 * 商品描述信息同步 与过滤无效HTML
 * @author heyadong
 * @version 1.0, 2012-10-10 上午10:32:38
 */
@Component("productDescriptionSyncJob")
public class ProductDescriptionSyncJob implements TaskAware, Serializable{

    private static final Logger LOG = Logger.getLogger(ProductRelationToAmazonJob.class);
    private static final long serialVersionUID = -96494193482657428L;
    
    @Autowired
    private EcProductService ecProductService;
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "productDescriptionSyncJob";
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return "同步中启 更新商品描述信息的数据,过滤无用的HTML标签";
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
            syncNum = ecProductService.syncProductDescription(limit);
            
            sum += syncNum;
            LOG.info(String.format("当前已同步个数  total:%s", sum));
        } while(syncNum >= limit);
        LOG.info(String.format("商品描述信息同步完成, 总共同步个数, %s", sum));
    }

}
