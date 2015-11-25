package com.winxuan.ec.service.util;

import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductImageZoomTask;
import com.winxuan.ec.model.user.Employee;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 图片服务
 * @author juqkai(juqkai@gmail.com)
 */
public interface ImageService {
    /**
     * 按额定的几种缩放率批量缩放图片
     * @param srcDir
     * @param destDir
     * @return 返回出错的图片路径与提示信息, 默认情况下会把出错的信息记入日志warn级别 
     */
    Map<String, String> batchZoomImage(String srcDir);
    /**
     * 按额定的几种缩放率缩放指定图片
     * @param file 源文件
     * @param destDir 保存地址目录, 会根据保存规则生成相应的子目录, 即商品编号后4位
     * @throws IOException
     */
    void zoomFile(BufferedImage srcImg, Product product) throws IOException;
    /**
     * 缩放指定规格的图片
     * @param destPath 图片保存路径
     * @param srcImg 源图片
     * @param width 宽度
     * @param height 高度
     * @return
     */
    boolean zoomImage(String destPath, BufferedImage srcImg, int width, int height);

    /**
     * 根据EXCEL来转换图片
     * @param employee
     * @param data
     */
    void zoomImage(Employee employee, List<List<String>> data);

    /**
     * 根据任务来处理图片
     * @param task
     */
    void zoomFile(ProductImageZoomTask task);
    /**
     * 生成URL, 目录的相对路径
     * @param task
     */
    String relativePath(String productId, String fileName) ;
    /**
     * 取得目的地路径
     * @param task
     */
    String getDestDir();
}
