package com.winxuan.ec.admin.controller.product;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.winxuan.ec.admin.controller.ControllerConstant;
import com.winxuan.ec.exception.DcRuleException;
import com.winxuan.ec.exception.ProductException;
import com.winxuan.ec.exception.ProductSaleStockException;
import com.winxuan.ec.model.category.Category;
import com.winxuan.ec.model.code.Code;
import com.winxuan.ec.model.product.Product;
import com.winxuan.ec.model.product.ProductDescription;
import com.winxuan.ec.model.product.ProductExtend;
import com.winxuan.ec.model.product.ProductImage;
import com.winxuan.ec.model.product.ProductMeta;
import com.winxuan.ec.model.product.ProductMetaComparator;
import com.winxuan.ec.model.product.ProductSale;
import com.winxuan.ec.model.user.Employee;
import com.winxuan.ec.service.category.CategoryService;
import com.winxuan.ec.service.code.CodeService;
import com.winxuan.ec.service.dc.DcService;
import com.winxuan.ec.service.product.ProductSaleStockService;
import com.winxuan.ec.service.product.ProductService;
import com.winxuan.ec.service.util.ImageService;
import com.winxuan.ec.support.interceptor.MyInject;
import com.winxuan.ec.support.util.MagicNumber;
import com.winxuan.framework.util.image.ImageCompress;
import com.winxuan.framework.util.image.ImageCompressImpl;
import com.winxuan.services.pcs.model.vo.CodeVo;
import com.winxuan.services.pss.model.vo.ProductSaleStockVo;

/**
 * ******************************
 * 
 * @author:新华文轩电子商务有限公司,cast911
 * @lastupdateTime:2013-9-9 下午5:40:06 --!
 * @description:套装书
 ******************************** 
 */
@Controller
@RequestMapping("/product")
public class ProductComplexController {
	private static final short VIRTUAL=1;//虚拟套装
	@Autowired
	private ProductService productService;

	@Autowired
	private CodeService codeService;

	@Autowired
	private CategoryService categoryService;

	@Value("${complex_image_address}")
	private String imageAddressPath;

	@Autowired
	private ImageService imageService;

	@Autowired
	private DcService dcService;
	
	@Autowired
	private ProductSaleStockService productSaleStockService;

	/**
	 * 跳转到套装书创建页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/complex", method = RequestMethod.GET)
	public ModelAndView complexProduct() {
		ModelAndView mav = new ModelAndView("/product/product_complex");
		mav.addObject("dcList", dcService.findByAvailableDc());
		return mav;
	}

	/**
	 * 创建套装书
	 * 
	 * @param ids
	 * @param mainId
	 * @param jsonData
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws ProductException
	 * @throws DcRuleException
	 */
	@RequestMapping(value = "/creatcomplex", method = RequestMethod.POST)
	public ModelAndView creatComplexProduct(
			@MyInject Employee operator,
			@RequestParam("ids") String ids,
			@RequestParam("mainId") Long mainId,
			@RequestParam("jsonData") String jsonData,
			@RequestParam("dc") Long dc,
			@RequestParam(required = false, value = "localfile") MultipartFile proPic,
			HttpServletRequest request) throws ParseException, IOException,
			ProductException, DcRuleException {
		ModelAndView mav = new ModelAndView("/product/result");
		ProductSale productSale = productService.getProductSale(mainId);
		Map<String, Object> parameters = new HashMap<String, Object>();
		String[] theIds = ids.split(ControllerConstant.SPLITPOINT);
		List<Long> proIds = new ArrayList<Long>();
		for (int i = MagicNumber.ZERO; i < theIds.length; i++) {
			if (!StringUtils.isBlank(theIds[i])) {
				try {
					proIds.add(Long.valueOf(theIds[i].trim()));
				} catch (Exception e) {
					continue;
				}
			}
		}
		parameters.put(ControllerConstant.PRODUCTSALEIDS, proIds);
		List<ProductSale> productSales = productService.findProductSale(
				parameters, null);
		ProductSale complexSale = getJsonData(jsonData, productSale, false);
		List<Code> dcList = new ArrayList<Code>();
		dcList.add(codeService.get(dc));
		productService.createComplexProduct(complexSale, productSales, dcList,operator);
		if (proPic != null && !proPic.isEmpty()) {
			upImg(complexSale, request, proPic);
		}
		mav.addObject("complexId", complexSale.getId());
		return mav;
	}

	
	
	/**
	 * 查找商品
	 * 
	 * @param ids
	 * @param codingType
	 * @return
	 */
	@RequestMapping(value = "/productinfolist", method = RequestMethod.POST)
	public ModelAndView productInfoList(
			@RequestParam("codingValue") String ids,
			@RequestParam("codingType") String codingType) {
		ModelAndView mav = new ModelAndView("/product/complex");
		Map<String, Object> parameters = new HashMap<String, Object>();
		String errorMsg = "";
		String[] theIds = ids.split("\n");
		List<String> outerIds = new ArrayList<String>();
		List<Long> proIds = new ArrayList<Long>();
		if (ControllerConstant.OUTERID.equals(codingType)) {
			for (int i = MagicNumber.ZERO; i < theIds.length; i++) {
				if (!StringUtils.isBlank(theIds[i])) {
					outerIds.add(theIds[i].trim());
				}
			}
			if (!outerIds.isEmpty()) {
				parameters.put("outerIds", outerIds);
			}
		} else if (ControllerConstant.PRODUCTID.equals(codingType)) {
			proIds.add(Long.valueOf("-1"));
			for (int i = MagicNumber.ZERO; i < theIds.length; i++) {
				if (!StringUtils.isBlank(theIds[i])) {
					try {
						proIds.add(Long.valueOf(theIds[i].trim()));
					} catch (Exception e) {
						continue;
					}
				}
			}
			parameters.put(ControllerConstant.PRODUCTSALEIDS, proIds);
		}
		List<ProductSale> productSales = productService.findProductSale(
				parameters, null);
		List<ProductSale> cproductSales = new ArrayList<ProductSale>();
		for (ProductSale ps : productSales) {
			if (ps.getProduct().isComplex()) {
				/*errorMsg = errorMsg + "商品：" + ps.getId()
						+ "已经属于套装书<a href='/product/"
						+ ps.getComplexMaster().getId()
						+ "/editcomplex' target='_blank'>"
						+ ps.getComplexMaster().getName() + "</a><br/>";*/
				errorMsg  = errorMsg+"商品："+ps.getId()
						+"已经是一个套装书<a href='/product/"
						+ ps.getId()
						+ "/editcomplex' target='_blank'>"
						+ ps.getName() + "</a><br/>,套装书不能作为子商品";
			} else {
			try {
				productSaleStockService.initProductSaleStock(ps);
				for(ProductSaleStockVo stock:ps.getProductSaleStockVos()){
					Code codeinfo = codeService.get(stock.getDc());
					CodeVo vo = new CodeVo();
					vo.setId(codeinfo.getId());
					vo.setName(codeinfo.getName());
					vo.setDescription(codeinfo.getDescription());
					stock.setDcdetail(vo);
				}
			} catch (ProductSaleStockException e) {
				throw new RuntimeException();
			}
			cproductSales.add(ps);
			}
		}
		boolean nothas = true;
		if (ControllerConstant.OUTERID.equals(codingType)) {
			for (String o : outerIds) {
				for (ProductSale ps : productSales) {
					if (ps.getOuterId().equals(o)) {
						nothas = false;
						break;
					}
				}
				if (nothas) {
					errorMsg = errorMsg + "自编码为：" + o + "的商品不存在<br/>";
				}
				nothas = true;
			}

		} else if (ControllerConstant.PRODUCTID.equals(codingType)) {
			for (Long l : proIds) {
				for (ProductSale ps : productSales) {
					if (ps.getId().equals(l)) {
						nothas = false;
						break;
					}
				}
				if (nothas && l > 0) {
					errorMsg = errorMsg + "商品编码为：" + l + "的商品不存在<br/>";
				}
				nothas = true;
			}
		}
		List<ProductMeta> metas = null;
		if(cproductSales.size()>0) {
			metas = getMeta(cproductSales.get(0));
		}
		mav.addObject("ProductMetas", metas);
		mav.addObject(ControllerConstant.PRODUCTSALES, cproductSales);
		mav.addObject("error", errorMsg);
		return mav;
	}

	/**
	 * 修改套装书
	 * 
	 * @throws IOException
	 * @throws ProductException
	 * @throws DcRuleException
	 */
	@RequestMapping(value = "/updatecomplex", method = RequestMethod.POST)
	public ModelAndView updateComplexProduct(
			@RequestParam("ids") String ids,
			@RequestParam("mainId") Long mainId,
			@RequestParam("jsonData") String jsonData,
			@RequestParam("metaLength") Integer metaLength,
			@RequestParam(required = false, value = "localfile") MultipartFile proPic,
			HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("/product/editresult");
		ProductSale productSale = productService.getProductSale(mainId);
		List<Long> productSaleIds = new ArrayList<Long>();
		for (ProductSale ps : productSale.getComplexItemList()) {
			productSaleIds.add(ps.getId());
		}
		String[] theIds = ids.split(ControllerConstant.SPLITPOINT);
		List<Long> proIds = new ArrayList<Long>();
		for (int i = MagicNumber.ZERO; i < theIds.length; i++) {
			if (!StringUtils.isBlank(theIds[i])) {
				try {
					proIds.add(Long.valueOf(theIds[i].trim()));
				} catch (Exception e) {
					continue;
				}
			}
		}

		ProductSale complexSale = getJsonData(jsonData, productSale, true);
		//更新商品牌描述
		List<ProductMeta> metas = new ArrayList<ProductMeta>();
		for(int i=0;i<metaLength;i++) {
			ProductMeta productMeta = productService.getProductMeta(Long
					.valueOf(request.getParameter("productMetaId" + i)));
			productMeta.setValue(request.getParameter("productMeta" + i));
			metas.add(productMeta);
		}
		proDes(complexSale.getProduct(),metas);
		if (proPic != null && !proPic.isEmpty()) {
			if (!CollectionUtils.isEmpty(complexSale.getProduct()
					.getImageList())) {
				for (Iterator<ProductImage> iterator = complexSale.getProduct()
						.getImageList().iterator(); iterator.hasNext();) {
					ProductImage image = iterator.next();
					iterator.remove();
					productService.removeProductImage(image);
				}
			}
			upImg(complexSale, request, proPic);
			complexSale.getProduct().setHasImage(true);
		}
		productService.update(complexSale);
		mav.addObject("complexId", complexSale.getId());
		return mav;

	}

	/**
	 * 跳转到套装书查询
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/querycomplex", method = RequestMethod.GET)
	public ModelAndView goFindComplexProduct() {
		ModelAndView mav = new ModelAndView("/product/complex_product_list");
		mav.addObject(ControllerConstant.SALESTATUS,
				codeService.get(ControllerConstant.PRODUCTSALESTATUS)
						.getChildren());
		mav.addObject("complexQueryForm", new ComplexQueryForm());
		return mav;
	}

	/**
	 * 跳转到套装书修改
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/{id}/editcomplex", method = RequestMethod.GET)
	public ModelAndView goEditComplexProduct(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("/product/product_complex_edit");
		ProductSale productSale = productService.getProductSale(id);
		String ids = "";
		for (ProductSale ps : productSale.getComplexItemList()) {
			ids = ids + ps.getId() + ControllerConstant.SPLITPOINT;
		}
		List<ProductMeta> metas = getMeta(productSale);
		mav.addObject("ids", ids);
		mav.addObject("productSaleId", productSale.getId());
		mav.addObject("productSale", productSale);
		mav.addObject("ProductMetas", metas);
		return mav;
	}

	/**
	 * 读取json数据 返回套装书的productsale
	 * 
	 * @param jsonString
	 * @param productSale
	 * @return
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 */
	public ProductSale getJsonData(String jsonString, ProductSale productSale,
			boolean flag) throws ParseException, UnsupportedEncodingException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		jsonString = jsonString.replace(ControllerConstant.SPLITCHAR, "<br/>")
				.replace("\r\n", "<br/>").replace("\r", "<br/>");
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jarray = null;
		ProductSale newProductSale = null;
		Product newProduct = null;
		if (flag) {
			newProductSale = productSale;
			newProduct = productSale.getProduct();
		} else {
			newProduct = new Product();
			newProductSale = new ProductSale();
		}
		jarray = JSONArray.fromObject(jsonObject.get("exList"));
		String value = null;
		String name = null;
		// 获取扩展属性
		for (int i = MagicNumber.ZERO; i < jarray.size(); i++) {
			ProductMeta meta = new ProductMeta();
			if (jarray.get(i) == null) {
				continue;
			}
			meta.setId(Long.valueOf((String) JSONObject.fromObject(
					jarray.get(i)).get(ControllerConstant.METAID)));
			value = (String) JSONObject.fromObject(jarray.get(i)).get(
					ControllerConstant.VALUE);
			name = (String) JSONObject.fromObject(jarray.get(i)).get("name");
			ProductExtend productExtend = newProduct.getProductExtend(meta);
			if (productExtend != null) {
				if (StringUtils.isBlank(value)) {
					newProduct.removeProductExtend(productExtend);
					productService.removeProductExtend(productExtend);
				} else {
					productExtend.setValue(value);
				}
			} else if (!StringUtils.isBlank(value)) {
				productExtend = new ProductExtend();
				productExtend.setProduct(newProduct);
				productExtend.setShow(true);
				productExtend.setName(name);
				productExtend.setValue(value);
				productExtend.setProductMeta(meta);
				newProduct.addExtend(productExtend);
			}
		}
		// 获取扩展描述
		jarray = JSONArray.fromObject(jsonObject.get("descriptionList"));
		if(!"[null]".equals(jarray.toString())) {
			for (int i = MagicNumber.ZERO; i < jarray.size(); i++) {
				ProductMeta meta = new ProductMeta();
				meta.setId(Long.valueOf((String) JSONObject.fromObject(
						jarray.get(i)).get(ControllerConstant.METAID)));
				value = (String) JSONObject.fromObject(jarray.get(i)).get(
						ControllerConstant.VALUE);
				value = value.replaceAll("<br />", "");
				name = (String) JSONObject.fromObject(jarray.get(i)).get("name");
				ProductDescription productDescription = newProduct
						.getProductDescription(meta);
				if (productDescription != null) {
					if (StringUtils.isBlank(value)) {
						newProduct.removeProductDescription(productDescription);
						productService.removeProductDescription(productDescription);
					} else {
						productDescription.setContent(value);
					}
				} else if (!StringUtils.isBlank(value)) {
					productDescription = new ProductDescription();
					productDescription.setProduct(newProduct);
					productDescription.setDigest("");
					productDescription.setName(name);
					productDescription.setContent(value);
					productDescription.setProductMeta(meta);
					newProduct.addDescription(productDescription);
				}
			}
		}
		newProduct.setSort(productSale.getProduct().getSort());// 种类
		newProduct.setName((String) jsonObject.get("name"));// 商品名
		newProduct.setManufacturer((String) jsonObject.get("manuFacturer"));// 出版社
		newProduct.setBarcode((String) jsonObject.get("barcode"));// 条形码
		newProduct.setAuthor((String) jsonObject.get("author"));// 作者
		newProduct.setProductionDate(format.parse((String) jsonObject
				.get("productionDate")));// 出版日期

		jarray = JSONArray.fromObject(jsonObject.get("categories"));
		if (jarray == null || jarray.size() <= 0) {
			newProduct.setCategories(productSale.getProduct().getCategories());
		} else {
			Map<String, Category> tt = new HashMap<String, Category>();
			for (int i = 0; i < jarray.size(); i++) {
				if (jarray.get(i) == null) {
					continue;
				}
				if (!tt.containsKey(jarray.getString(i))) {
					tt.put(jarray.getString(i), categoryService.get(Long
							.valueOf(jarray.getString(i))));
				}
			}
			List<Category> cas = new ArrayList<Category>();
			cas.addAll(tt.values());
			newProduct.setCategories(cas);
		}
		newProduct.setSort(productSale.getProduct().getSort());
		newProduct.setComplex(VIRTUAL);
		Date now = new Date();
		newProduct.setCreateTime(now);
		newProduct.setUpdateTime(now);
		newProductSale.setUpdateTime(now);
		// productSale
		newProductSale.setPromValue((String) jsonObject.get("promValue"));
		newProductSale.setAuditStatus(new Code(Code.PRODUCT_AUDIT_STATUS_PASS));// 审核状态
		newProductSale.setStorageType(productSale.getStorageType());// 储配方式
		newProductSale.setSupplyType(productSale.getSupplyType());// 销售类型
		newProductSale.setOuterId(null);// 外部编号
		newProductSale.setSellName(newProduct.getName());// 商品销售名称
		newProductSale.setProduct(newProduct);
		return newProductSale;
	}

	/**
	 * 上传图片
	 * 
	 * @param promotion
	 * @param request
	 * @param img
	 * @throws IOException
	 */
	private void upImg(ProductSale productSale, HttpServletRequest request,
			MultipartFile img) throws IOException {
		if (img != null && productSale != null) {
			ImageCompress ic = new ImageCompressImpl();
			String[] compressUrl = new String[ControllerConstant.THREE];
			String[] digests = new String[ControllerConstant.THREE];
			ic.setSavePath(imageAddressPath);

			boolean hasImageBefore = productSale.getProduct().isHasImage();
			// 保存600, 200等通用大小
			BufferedImage srcImg = ImageIO.read(img.getInputStream());
			imageService.zoomFile(srcImg, productSale.getProduct());

			if (ic.compress(productSale.getProduct().getId(),
					img.getInputStream(), compressUrl, digests)) {
				String timezone = "?" + System.currentTimeMillis();
				ProductImage piBig = new ProductImage();
				piBig.setProduct(productSale.getProduct());
				piBig.setType((short) ControllerConstant.THREE);
				piBig.setUrl(hasImageBefore ? compressUrl[0] + timezone
						: compressUrl[0]);
				piBig.setDigest(digests[0]);

				ProductImage piMid = new ProductImage();
				piMid.setProduct(productSale.getProduct());
				piMid.setType((short) 2);
				piMid.setUrl(hasImageBefore ? compressUrl[1] + timezone
						: compressUrl[1]);
				piMid.setDigest(digests[1]);

				ProductImage piMin = new ProductImage();
				piMin.setProduct(productSale.getProduct());
				piMin.setType((short) 1);
				piMin.setUrl(hasImageBefore ? compressUrl[2] + timezone
						: compressUrl[2]);
				piMin.setDigest(digests[2]);
				if (productSale.getProduct().getImageList() != null) {
					productSale.getProduct().getImageList().add(piBig);
					productSale.getProduct().getImageList().add(piMid);
					productSale.getProduct().getImageList().add(piMin);
				} else {
					Set<ProductImage> images = new HashSet<ProductImage>();
					images.add(piBig);
					images.add(piMid);
					images.add(piMin);
					productSale.getProduct().setImageList(images);
				}
				productSale.getProduct().setHasImage(true);
				productService.update(productSale);
			}
		}
	}
	
	/**
	 * 获取商品分类下的信息
	 * @param productSale
	 * @return
	 */
	private List<ProductMeta> getMeta(ProductSale productSale) {
		Product product = productSale.getProduct();
		List<Category> categories = product.getCategories();
		List<ProductMeta> metas2  = new ArrayList<ProductMeta>();
		Set<ProductDescription> descriptions = product.getDescriptionList();
		for(Category c : categories){
			List<ProductMeta> metas = c.getOrderedProductMetaList();
			if (!CollectionUtils.isEmpty(metas)) {
				for(ProductMeta pm : metas){
					if(Code.FIELD_TYPE_TEXT.equals(pm.getType().getId())) {
						if(!metas2.contains(pm)) {
							metas2.add(pm);
						}
					}
				}
			}
		}
		if(metas2.size()>0) {
			for(ProductMeta productMeta : metas2) {
				for(ProductDescription productDescription : descriptions) {
					if (productDescription.getProductMeta().getId()
							.compareTo(productMeta.getId()) == 0
							&& productDescription.getProduct().getId()
							.compareTo(product.getId()) == 0) {
						productMeta.setValue(productDescription.getContent());
					}
				}
			}
			Collections.sort(metas2, new ProductMetaComparator());
		}
		return metas2;
	}
	
	/**
	 * 设置商品描述信息
	 */
	public void proDes(Product complex, List<ProductMeta> metas) {
		// 获取扩展描述
		for (int i = MagicNumber.ZERO; i < metas.size(); i++) {
			ProductDescription productDescription = complex
					.getProductDescription(metas.get(i));
			if (productDescription != null) {
				if (StringUtils.isBlank(metas.get(i).getValue())) {
					complex.removeProductDescription(productDescription);
					productService.removeProductDescription(productDescription);
				} else {
					productDescription.setContent(metas.get(i).getValue());
				}
			} else if (!StringUtils.isBlank(metas.get(i).getValue())) {
				productDescription = new ProductDescription();
				productDescription.setProduct(complex);
				productDescription.setDigest("");
				productDescription.setName(metas.get(i).getName());
				productDescription.setContent(metas.get(i).getValue());
				productDescription.setProductMeta(metas.get(i));
				complex.addDescription(productDescription);
			}
		}
	}
}
