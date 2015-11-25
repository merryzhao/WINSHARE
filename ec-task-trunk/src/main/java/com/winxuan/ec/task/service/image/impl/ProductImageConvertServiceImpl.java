package com.winxuan.ec.task.service.image.impl;

import com.winxuan.ec.model.product.ProductImageZoomTask;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.util.ImageServiceImpl;
import com.winxuan.ec.task.service.image.ProductImageConvertService;
import com.winxuan.framework.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: juqkai(juqkai@gmail.com)
 * Date: 13-5-9
 * Time: 下午3:26
 */
@Service
public class ProductImageConvertServiceImpl implements ProductImageConvertService {
    @Autowired
    private ProductService productService;

    @Autowired
    private ImageServiceImpl imageService;

    public void convert() {
        Pagination page = new Pagination();
        page.setPageSize(20);
        while(true){
            List<ProductImageZoomTask> tasks = productService.findProductImageZoomTask(page);
            if (null == tasks || tasks.size() <= 0) {
                return;
            }

            for (ProductImageZoomTask item : tasks) {
                imageService.zoomFile(item);
            }
        }
    }
}
