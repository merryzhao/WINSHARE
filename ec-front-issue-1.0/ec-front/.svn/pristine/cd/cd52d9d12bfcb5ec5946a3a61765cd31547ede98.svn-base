<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="com.winxuan.ec.support.util.WebApplicationContextUtils"%>
<%@page import="com.winxuan.ec.service.product.ProductService"%>
<%@page import="com.winxuan.ec.service.shop.ShopService"%>
<%@page import="com.winxuan.ec.model.product.Product"%>
<%@page import="com.winxuan.ec.model.shop.Shop"%>
<%@page import="com.winxuan.ec.model.product.ProductSale"%>
<%
	String productIdStr  = request.getParameter("goodsNo");
    String shopIdStr = request.getParameter("shopNo");
    if(productIdStr == null || "".equals(productIdStr) || shopIdStr == null|| "".equals(shopIdStr)){
    	response.sendRedirect("/");
    	return ;
    }
    Long productId = Long.parseLong(productIdStr);
    if(productId.longValue() < 10000000){
        productId = 10000000 + productId.longValue();
    }

    Long shopId =Long.parseLong(shopIdStr);
    ProductService prorductService =(ProductService)WebApplicationContextUtils.getService("productService",application);
    ShopService shopService = (ShopService)WebApplicationContextUtils.getService("shopService",application);
	Product product  = prorductService.get(productId);
    Shop shop = shopService.get(shopId);
    
    if(product == null ||shop == null){
    	response.sendRedirect("/");
    	return ;
    }
    ProductSale productSale = prorductService.get(product,shop);  
    if(productSale == null){
    	response.sendRedirect("/");
    	return ;
    }
    Long productSaleId = productSale.getId();
    response.addHeader("Location","http://www.winxuan.com/product/"+productSaleId);
    response.sendError(301);
%>
