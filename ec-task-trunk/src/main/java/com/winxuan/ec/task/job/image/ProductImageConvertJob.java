package com.winxuan.ec.task.job.image;

import com.winxuan.ec.task.service.image.ProductImageConvertService;
import com.winxuan.ec.task.support.quartz.job.TaskAware;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: juqkai(juqkai@gmail.com)
 * Date: 13-5-9
 * Time: 下午3:20
 */
@Component("productImageZoomJob")
public class ProductImageConvertJob implements TaskAware {
	private static final Log LOG = LogFactory.getLog(ProductImageConvertJob.class);
    @Autowired
    private ProductImageConvertService productImageConvertService;

    @Override
    public String getName() {
        return "productImageZoomJob";
    }

    @Override
    public String getDescription() {
        return "定时根据product_image_zoom_task表转换商品图片!";
    }

    @Override
    public String getGroup() {
        return TaskAware.GROUP_EC_CORE;
    }

    @Override
    public void start() {
    	LOG.info("start ProductImageConvertJob()............");
        productImageConvertService.convert();
        LOG.info("end ProductImageConvertJob()............");
    }
}
