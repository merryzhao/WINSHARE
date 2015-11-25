package com.winxuan.ec.task.service.image.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.winxuan.ec.dao.ProductDao;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.service.util.ImageService;
import com.winxuan.ec.task.dao.image.CompressImageDao;
import com.winxuan.ec.task.model.mdm.MDMImage;
import com.winxuan.ec.task.service.image.CompressImageService;
import com.winxuan.framework.dynamicdao.InjectDao;
import com.winxuan.framework.util.image.ImageCompress;
import com.winxuan.framework.util.image.ImageCompressImpl;
import com.winxuan.framework.util.image.ResourceRelease;
import com.winxuan.framework.util.security.MD5Custom;

/**
 * description
 * 
 * @author YangJun
 * @version 1.0,2011-11-14
 */
@Service("compressImageService")
public class CompressImageServiceImpl implements CompressImageService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5936125548092503993L;
	private static final Log LOG = LogFactory.getLog(CompressImageServiceImpl.class);
	private static final short MAGIC_3 = 3;
	private static final short MAGIC_2= 2;
	private static final short MAGIC_1 = 1;
	private static final int PAGE_SIZE = 100;
	private static final String[][] PARAMETERS = {{"插图", "4"},{"精彩图画", "8"},{"版权页图片", "7"},{"目录图片", "6"},{"书摘插图", "5"},{"封底图片", "15"},
		{"条形码图片", "17"}};
	 private static final String BIG_MID_SML_SQL = "select attachmentid,merchid,attachmenttype,content,description,creator from mdm_attachment where " +
			"(attachmenttype='封面大图' or attachmenttype='封面小图' or attachmenttype='立体封面大图' or attachmenttype='立体封面小图') " +
			"and (description is null or (description!='111' and description!='222' and description!='333' " +
			"and description!='444' and description!='555')) order by merchid desc limit ?";
	private static final String OTHER_SQL = "select attachmentid,merchid,attachmenttype,content from mdm_attachment where attachmenttype=? and (description is null " +
			"or (description!='111' and description!='222' and description!='333' and description!='444' and description!='555')) order by merchid desc limit ?";
	 
	@InjectDao
	private CompressImageDao compressImageDao;

	@InjectDao
	private ProductDao productDao;

	@Autowired
	private JdbcTemplate jdbcTemplateEcCore;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${complex_image_address}")
	private String path;

	@Override
	public Product getProduct(Long merchId) {
		return compressImageDao.getProduct(merchId);
	}

	@Override
	public ProductImage getProductImage(Map<String, Object> parameters) {
		return compressImageDao.getProductImage(parameters);
	}

	@Override	
	public List<MDMImage> queryListData(String sql, int size) {
		List<MDMImage> list = null;
		try{
			list = jdbcTemplateEcCore.query(sql, new Object[] {size}, ParameterizedBeanPropertyRowMapper.newInstance(MDMImage.class));
		}catch(Exception e){
			LOG.error("(" + sql + ") sql select error : " + e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<MDMImage> queryListData(String sql, String imageType, int size) {
		List<MDMImage> list = null;
		try{
			list = jdbcTemplateEcCore.query(sql, new Object[] {imageType, size}, ParameterizedBeanPropertyRowMapper.newInstance(MDMImage.class));
		}catch(Exception e){
			LOG.error("(" + sql + ") sql select error : " + e.getMessage(), e);
		}
		return list;
	}

	@Override
	public long getProductId(long merchid) {
		long productId = 0;
		try{
			Product product = compressImageDao.getProduct(merchid);
			if(product!=null){
				productId = product.getId();
			}
		}catch(Exception e){
			LOG.error("(select id from product where merchid=" + merchid + ") sql select error : " + e.getMessage(), e);
		}
		return productId;
	}

	@Override
	public void deleteProductImage(long productId, short type) {
		compressImageDao.deleteProductImage(productId, type);
	}

	@Override
	public void updateMDMImage(String flag1, String flag2, long id) {
		compressImageDao.updateMDMImage(flag1, id);
	}

	private BufferedImage blobToBufferedImage(byte[] blob){
		if(blob == null){
			return null;
		}
		BufferedImage bufImage = null;
		File file = new File(path + "temp.jpg");
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(blob);
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(is);
			bufImage = decoder.decodeAsBufferedImage();
			ImageIO.write(bufImage, "JPG", file);
			
			bufImage = ImageIO.read(file);
			file.delete();
			ResourceRelease.releaseByteArrayInputStream(is);
			return bufImage;
		} catch (Exception e) {
			LOG.error("byte[] convert bufferedimage error : " + e.getMessage(), e);
			return null;
		}
	}
	
	@Override
	public boolean save(ImageCompress ic, Product product, byte[] blob, short type, short index) {
		if(product == null){
			return false;
		}
	
		BufferedImage bufImage = blobToBufferedImage(blob);
		if(bufImage == null){
			LOG.warn("product(" + product.getId() + ") byte[] convert bufferedimage error");
			return false;
		}
	
		if(!isUpdate(product,blob)){
			return true;
		}

		String[] url = new String[1];
		String[] digest = new String[1];
		if(ic.save(product.getId(), bufImage, url, digest, type, index)){
			ProductImage productImage = new ProductImage();
			productImage.setProduct(product);
			productImage.setUrl(url[0]);
			productImage.setType(type);
			productImage.setIndex(index);
			productImage.setDigest(digest[0]);
			productDao.save(productImage);

			compressImageDao.updateProductImage((short)1, product.getId());
			return true;
		}
		return false;
	}

	@Override
	public boolean save(ImageCompress ic, Product product, byte[] blob) {
		if(product == null){
			return false;
		}

		BufferedImage bufImage = blobToBufferedImage(blob);
		if(bufImage == null){
			LOG.warn("product(" + product.getId() + ") byte[] convert bufferedimage error");
			return false;
		}
		if(!isUpdate(product,blob)){
			return true;
		}

		String[] compressUrl = new String[MAGIC_3];
		String[] digests = new String[MAGIC_3];
		if(ic.compress(product.getId(), bufImage, compressUrl, digests)){
			boolean hasImageBefore = product.isHasImage();
			
		    //处理600*600, 200*200等几种固定格式
		    try {
                imageService.zoomFile(bufImage, product);
            }
            catch (IOException e) {
                return false;
            }
            String timezone = "?"+ System.currentTimeMillis();
			deleteProductImage(product.getId(), MAGIC_3);
			deleteProductImage(product.getId(), MAGIC_2);
			deleteProductImage(product.getId(), MAGIC_1);
			ProductImage productImage = new ProductImage();
			productImage.setProduct(product);
			productImage.setUrl(hasImageBefore?compressUrl[0]+timezone:compressUrl[0]);
			productImage.setType(MAGIC_3);
			productImage.setDigest(digests[0]);
			productDao.save(productImage);

			productImage = new ProductImage();
			productImage.setProduct(product);
			productImage.setUrl(hasImageBefore?compressUrl[1]+timezone:compressUrl[1]);
			productImage.setType(MAGIC_2);
			productImage.setDigest(digests[1]);
			productDao.save(productImage);

			productImage = new ProductImage();
			productImage.setProduct(product);
			productImage.setUrl(hasImageBefore?compressUrl[2]+timezone:compressUrl[2]);
			productImage.setType(MAGIC_1);
			productImage.setDigest(digests[2]);
			productDao.save(productImage);
			
			compressImageDao.updateProductImage((short)1, product.getId());
			return true;
		}
		return false;
	}

	@Override
	public void compress() {
		LOG.info("-----------------------------------big,middle and small image compress start-----------------------------------");
		List<MDMImage> list = null;
		ImageCompress ic = new ImageCompressImpl();
		ic.setSavePath(path);
		byte[] stereoMax = null;  //立体封面大图
		byte[] stereoMin = null;  //立体封面小图
		byte[] max = null;
		byte[] min = null;
		Long stereoMaxId = null;
		Long stereoMinId = null;
		Long maxId = null;
		Long minId = null;
		long merchId = -1;
		Product product = null;
		list = queryListData(BIG_MID_SML_SQL, PAGE_SIZE);
		boolean flag = false;
		int errorNum=0;
		int total=0;
		int same=0;
		while(list != null && !list.isEmpty()){
			int listSize = list.size();
			total+=listSize;
			if(listSize < PAGE_SIZE){
				listSize++;
				flag = true;
			}
			for(int i = 0; i < listSize; i++){
				long id = 0;
				long merid;
				String type;
				byte[] b = null;
				try{
					if(flag && (i + 1) == listSize){
						id = 0;
						merid = 0;
						type = "";
						b = null;
					}else{
						id = list.get(i).getAttachmentid();
						merid = list.get(i).getMerchid();
						type = list.get(i).getAttachmenttype();
						b = list.get(i).getContent();
					}
				}catch(Exception e){
					LOG.error("get mdm image data error : " + e.getMessage(), e);
					compressImageDao.updateMDMImage("111", id);
					errorNum++;
					continue;
				}

				if(merchId == -1 && product == null){
					merchId = merid;
					product = getProduct(merid);
					if(product == null){
						LOG.warn("merchid(" + merid + ") in product not found (select id from product where merchid=" + merid + ")");
						compressImageDao.updateMDMImage("222", id);
						errorNum++;
						continue;
					}
				}

				if(merchId != merid){
					/**
					 * 立体封面大图(stereo:立体的)的优先级最高
					 * 1)只有封面大图和(或)封面小图，则取封面大图
					 * 2)当立体封面大图存在时，则取立体封面大图
					 * 3)优先级：立体封面大图>大图>立体封面小图>小图
					 */
					if (stereoMax != null){
						max = stereoMax;
					}
					else{
						max = (max == null)?((stereoMin == null ? min : stereoMin)): max;
					}
					try{
						 
						if(save(ic, product, max)){
							if(stereoMaxId != null){
								compressImageDao.deleteMDMImage(stereoMaxId);
							}
							if(stereoMinId != null){
								compressImageDao.deleteMDMImage(stereoMinId);
							}
							if(maxId != null){
								compressImageDao.deleteMDMImage(maxId);
							}
							if(minId != null){
								compressImageDao.deleteMDMImage(minId);
							}
						}else{
							if(stereoMaxId != null){
								compressImageDao.updateMDMImage("444",stereoMaxId);
								errorNum++;
							}
							if(stereoMinId != null){
								compressImageDao.updateMDMImage("444", stereoMinId);
							}
							if(maxId != null){
								compressImageDao.updateMDMImage("444", maxId);
								errorNum++;
							}
							if(minId != null){
								compressImageDao.updateMDMImage("444", minId);
								errorNum++;
							}
						}
					}catch(Exception e){
						LOG.error("product(" + product.getId() + ") image compress image error : " + e.getMessage(), e);
						if(stereoMaxId != null){
							compressImageDao.updateMDMImage("333", stereoMaxId);
						}
						if(stereoMinId != null){
							compressImageDao.updateMDMImage("333", stereoMinId);
						}
						if(maxId != null){
							compressImageDao.updateMDMImage("333", maxId);
						}
						if(minId != null){
							compressImageDao.updateMDMImage("333", minId);
						}
						errorNum++;
						continue;
					}

					merchId = merid;
					product = getProduct(merid);
					if(product == null){
						LOG.warn("merchid(" + merid + ") in product not found (select id from product where merchid=" + merid + ")");
						compressImageDao.updateMDMImage("222", id);
						errorNum++;
						continue;
					}
					stereoMax = null;
					stereoMin = null;
					max = null;
					min = null;
					stereoMaxId = null;
					stereoMinId = null;
					maxId = null;
					minId = null;
				}
				if(product == null){
					compressImageDao.updateMDMImage("222", id);
					errorNum++;
					continue;
				}
				if("立体封面大图".equalsIgnoreCase(type)){
					stereoMax = b;
					stereoMaxId = id;
				}
				if("立体封面小图".equalsIgnoreCase(type)){
					stereoMin = b;
					stereoMinId = id;
				}
				if("封面大图".equalsIgnoreCase(type)){
					max = b;
					maxId = id;
				}
				if("封面小图".equalsIgnoreCase(type)){
					min = b;
					minId = id;
				}
			}
			list = queryListData(BIG_MID_SML_SQL, PAGE_SIZE);
		}
		LOG.info("-----------------------------------big,middle and small image compress end-----------------------------------");
		LOG.info("-----------------------------------total:"+total+"--errorNum:"+(errorNum-1)+"-相同图片:"+same+"--------------------------------");
	}

	@Override
	public void compressOther() {
		LOG.info("-----------------------------------other image compress start-----------------------------------");
		List<MDMImage> list = null;
		ImageCompress ic = new ImageCompressImpl();
		ic.setSavePath(path);
		long merchId = -1;
		Product product = null;
		short index = 1;
		int errorNum=0;
		int total=0;
		int same = 0;
		for(int f = 0; f < PARAMETERS.length; f++){
			String imageType = PARAMETERS[f][0];
			short type = Short.parseShort(PARAMETERS[f][1]);
			do {
				list = queryListData(OTHER_SQL, imageType, PAGE_SIZE);
				total=total+list.size();
				for(int i = 0; i < list.size(); i++){
					long id = 0;
					long merid;
					byte[] b = null;
					try{
						id = list.get(i).getAttachmentid();
						merid = list.get(i).getMerchid();
						b = list.get(i).getContent();
					}catch(Exception e){
						LOG.error("get mdm image data error : " + e.getMessage(), e);
						compressImageDao.updateMDMImage("111", id);
						errorNum++;
						continue;
					}
	
					if(merchId == -1 && product == null){
						merchId = merid;
						product = getProduct(merid);
						if(product == null){
							LOG.warn("merchid(" + merid + ") in product not found (select id from product where merchid=" + merid + ")");
							compressImageDao.updateMDMImage("222", id);
							errorNum++;
							continue;
						}
						compressImageDao.deleteProductImage(product.getId(), type);
					}
					
					if(merchId != merid){
						index = 1;
						merchId = merid;
						product = getProduct(merid);
						if(product == null){
							LOG.warn("merchid(" + merid + ") in product not found (select id from product where merchid=" + merid + ")");
							compressImageDao.updateMDMImage("222", id);
							errorNum++;
							continue;
						}
						compressImageDao.deleteProductImage(product.getId(), type);
					}
					if(product == null){
						compressImageDao.updateMDMImage("222", id);
						errorNum++;
						continue;
					}
					try{
						if(save(ic, product, b, type, index)){
							compressImageDao.deleteMDMImage(id);
						}else{
							compressImageDao.updateMDMImage("444", id);
							errorNum++;
						}
						index++;
					}catch(Exception e){
						LOG.error("product(" + product.getId() + ") image compress image error : " + e.getMessage(), e);
						compressImageDao.updateMDMImage("333", id);
						errorNum++;
						continue;
					}
				}
			} while (list != null && !list.isEmpty());
		}
		compressImageDao.updateMDMImage("goon");
		LOG.info("-----------------------------------other image compress end-----------------------------------");
		LOG.info("-----------------------------------total:"+total+"--errorNum:"+errorNum+"-相同图片:"+same+"--------------------------------");
	}
	/**
	 * 是否更新 true 更新 
	 * @param product
	 * @return
	 */
	public boolean isUpdate(Product product,byte[] blob){
		boolean isUpdate=true;
		try {
			 String digest = MD5Custom.encrypt(blob);
			 for(ProductImage tempImage:product.getImageList()){
				 if(digest.equals(tempImage.getDigest())){
					isUpdate=false;
					break;
				 }
			 }
		} catch (Exception e) {
			LOG.error("byte[] convert bufferedimage error : " + e.getMessage(), e);
			return true;
		}
		return isUpdate;
	}
}
