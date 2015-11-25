package com.winxuan.ec.service.util;

import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.model.product.ProductImageZoomTask;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.employee.AttachService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.util.impl.DefaultParse;
import com.winxuan.ec.service.util.impl.SideSizeParse;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.util.image.ImageTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 图片service
 *
 * @author juqkai(juqkai@gmail.com)
 */
@Service("imageService")
@Transactional(rollbackFor = Exception.class)
public class ImageServiceImpl implements ImageService {
    private static final int MN_600 = 600;
    private static final int MN_200 = 200;
    private static final int MN_160 = 160;
    private static final int MN_130 = 130;
    private static final int MN_110 = 110;
    private static final int MN_55 = 55;
    private static final int MN_350 = 350;
    private static int fileCount = 0;
    private static Log log = LogFactory.getLog(ImageServiceImpl.class);
    private static Map<Short, ImageParse> sizes = new HashMap<Short, ImageParse>();

    private Boolean saveDB = true;

    //当当空白图的MD5
    private String dangdangEmptyImg = "745953a289a274d4f11ef5b0b4456e51";
    /**
     * 默认支持的
     */

    private FileWriter fw;

    @Value("${complex_image_address}")
    private String destDir;

    @Autowired
    private ProductService productService;

    @Autowired
    private AttachService attachService;

    public ImageServiceImpl() {
        sizes.put(ProductImage.TYPE_ORIGINAL, new DefaultParse());
        sizes.put(ProductImage.TYPE_SIZE_600, new SideSizeParse(MN_600, MN_600));
        sizes.put(ProductImage.TYPE_SIZE_200, new SideSizeParse(MN_200, MN_200));
        sizes.put(ProductImage.TYPE_SIZE_160, new SideSizeParse(MN_160, MN_160));
        sizes.put(ProductImage.TYPE_SIZE_130, new SideSizeParse(MN_130, MN_130));
        sizes.put(ProductImage.TYPE_SIZE_110, new SideSizeParse(MN_110, MN_110));
        sizes.put(ProductImage.TYPE_SIZE_55, new SideSizeParse(MN_55, MN_55));
        sizes.put(ProductImage.TYPE_SIZE_350, new SideSizeParse(MN_350, MN_350));
    }

    public ImageServiceImpl(String dataFilePath, String path) throws IOException {
        this();
        fw = new FileWriter(new File(dataFilePath));
        saveDB = false;
    }

    public Map<String, String> batchZoomImage(String srcDir) {
        List<File> files = fetchFile(new File(srcDir), "jpg");
        Map<String, String> errorFile = new HashMap<String, String>();
        try {
            int i = 0;
            for (File file : files) {
                String srcDigest = ImageTool.digest(file);
                if (dangdangEmptyImg.equals(srcDigest)) {
                    log.warn(file.getAbsolutePath() + ":" + "dang dang empty Image");
                    errorFile.put(file.getAbsolutePath(), "dang dang empty Image");
                    continue;
                }
                try {
                    String productId = file.getName().substring(0, file.getName().indexOf('.'));

                    Product product = new Product();
                    product.setId(Long.parseLong(productId));
                    zoomFile(ImageIO.read(file), product);
                    log.info(file.getAbsoluteFile() + ": zoom done!");
                    log.debug("已处理:" + i++);
                } catch (Exception e) {
                    log.warn(file.getAbsolutePath() + " : save error : " + e.getMessage());
                    errorFile.put(file.getAbsolutePath(), "save error : " + e.getMessage());
                }
            }
        } finally {
            if (fw != null) {
                try {
                    fw.flush();
                    fw.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return errorFile;
    }

    public void zoomFile(BufferedImage srcImg, Product product) throws IOException {
        if (srcImg == null) {
            throw new NullPointerException("source File is Null!");
        }

        String productId = product.getId().toString();
        String fileName;
        String relative;
        String destPath;
        for (Entry<Short, ImageParse> entry : sizes.entrySet()) {
            if (entry.getKey().equals(ProductImage.TYPE_ORIGINAL)) {
                fileName = productId + ".jpg";
            } else {
                fileName = String.format(productId + "_%s.jpg", entry.getKey());
            }
            relative = relativePath(productId, fileName);
            destPath = getDestDir() + relative;
            File file = new File(destPath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }

            ImageParse parse = sizes.get(entry.getKey());
            if (parse.parseAndSave(destPath, srcImg)) {
                String url = createUrl(Long.parseLong(productId)) + relative;
                String digest = ImageTool.digest(new File(destPath));
                save(productId, entry.getKey(), url, digest);
            } else {
                throw new FileNotFoundException(destPath);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void zoomFile(ProductImageZoomTask task) {
        File file = new File(task.getSrc());
        try {
            zoomFile(ImageIO.read(file), task.getProduct());
            task.setStatus(new Code(Code.PRODUCT_IMAGE_ZOOM_OK));
        } catch (IOException e) {
            task.setStatus(new Code(Code.PRODUCT_IMAGE_ZOOM_ERR));
        }
        productService.update(task);
    }

    /**
     * 保存
     *
     * @param fileName
     * @param destImg
     * @param type
     * @param productId
     * @throws IOException
     */
    private void save(String productId, short type, String url, String digest) throws IOException {
        url += "?" + System.currentTimeMillis();
        if (saveDB) {
            saveProductImage(productId, type, url, digest);
        } else {
            saveProductImageToFile(productId, type, url, digest);
        }
    }

    /**
     * 缩放默认规格的图片
     *
     * @param productId 商品编号
     * @param file      源文件
     * @param type      规格类别 {@link ProductImage}
     * @param destDir   目标目录
     * @return
     */
    public boolean zoomImage(String destPath, BufferedImage srcImg, int width, int height) {
        if (srcImg == null) {
            return false;
        }
        BufferedImage destImg = ImageTool.zoom(srcImg, width, height);

        Boolean isSave = ImageTool.save(destPath, destImg);
        if (!isSave) {
            return false;
        }
        return true;
    }

    @Override
    @Async
    public void zoomImage(Employee employee, List<List<String>> data) {
        List<List<String>> out = new ArrayList<List<String>>();
        for (List<String> items : data) {
            List<String> col = new ArrayList<String>();
            String id = items.get(0);
            String src = items.get(1);
            col.add(id);
            col.add(src);

            ProductImageZoomTask task = new ProductImageZoomTask();
            task.setStatus(new Code(Code.PRODUCT_IMAGE_ZOOM_NONE));
            Product product = productService.get(Long.parseLong(id));
            task.setProduct(product);
            task.setSrc(src);
            productService.save(task);
        }
    }

    /**
     * 生成URL, 目录的相对路径
     *
     * @param productId
     * @param fileName
     * @return
     */
    public String relativePath(String productId, String fileName) {
        return fetchPath(productId) + '/' + fileName;
    }

    /**
     * 创建URL前缀
     *
     * @param productId
     * @return
     */
    private String createUrl(Long productId) {
        return "http://img" + (productId % MagicNumber.FOUR) + ".winxuancdn.com/";
    }

    /**
     * 取得目的地路径
     *
     * @return
     */
    public String getDestDir() {
        if (!(destDir.endsWith("/") || destDir.endsWith("\\"))) {
            destDir = destDir + '/';
        }
        return destDir;
    }

    /**
     * 取到中间目录
     *
     * @param sProductId
     * @return
     */
    private String fetchPath(String sProductId) {
        if (sProductId.length() > MagicNumber.FOUR) {
            sProductId = sProductId.substring(sProductId.length() - MagicNumber.FOUR);
        }
        return sProductId;
    }

    /**
     * 保存商品图片到商品中去
     *
     * @param pid
     * @param type
     * @param url
     * @param digest
     */
    private void saveProductImage(String pid, short type, String url, String digest) {
        Product product = productService.get(Long.parseLong(pid));
        if (product.getImageList() == null) {
            product.setImageList(new HashSet<ProductImage>());
        }
        boolean t = false;
        for (ProductImage pi : product.getImageList()) {
            if (pi.getType() == type) {
                pi.setUrl(url);
                pi.setDigest(digest);
                t = true;
            }
        }
        if (!t) {
            ProductImage piBig = new ProductImage();
            piBig.setProduct(product);
            piBig.setType(type);
            piBig.setUrl(url);
            piBig.setDigest(digest);
            product.getImageList().add(piBig);
        }
        product.setHasImage(true);
        productService.update(product);
    }

    /**
     * 将记录保存到文件中去
     *
     * @param pid
     * @param type
     * @param url
     * @param digest
     * @throws IOException
     */
    private void saveProductImageToFile(String pid, short type, String url, String digest) throws IOException {
        fw.append(pid);
        fw.append(',');
        fw.append(url);
        fw.append(',');
        fw.append(String.valueOf(type));
        fw.append(',');
        fw.append("0");
        fw.append(',');
        fw.append(digest);
        fw.append('\n');
        fw.flush();
    }


    /**
     * 根据文件类型提取文件
     *
     * @param file
     * @param type
     * @return
     */
    private static List<File> fetchFile(File file, String type) {
        List<File> files = new ArrayList<File>();
        if (file.isFile()) {
            if (file.getName().toLowerCase().endsWith(type)) {
                files.add(file);
                log.debug("找到第" + (fileCount++) + "个文件:" + file.getAbsolutePath());
            }
            return files;
        }
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                files.addAll(fetchFile(f, type));
                continue;
            } else {
                if (f.getName().toLowerCase().endsWith(type)) {
                    files.add(f);
                    log.debug("找到第" + (fileCount++) + "个文件:" + f.getAbsolutePath());
                }
            }
        }
        return files;
    }
}
