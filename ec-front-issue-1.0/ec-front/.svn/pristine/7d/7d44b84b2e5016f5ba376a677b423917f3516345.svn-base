<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@include file="../snippets/tags.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文轩网-店铺-所有商品</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="bookshop" name="type"/>
</jsp:include>
</head>
<body class="shopIndex">
<jsp:include page="/page/snippets/version2/header.jsp">
		<jsp:param value="mall" name="label" />
</jsp:include>
<div class="layout">
	<c:if test="${pagination.count == 0}"><div class="tips">暂无数据</div></c:if>
  	 <div class="com_pages"><winxuan-page:page pagination="${pagination}" bodyStyle="front-default"/></div>
     	<c:forEach items="${list}" var="productSale">
				<dl class="goods_list" productSaleId="${productSale.id}">
					<dt>
						<a href="${productSale.product.url}" target="_blank" title="">
						<img class="book_img"
							src="${productSale.product.imageUrlFor160px}" alt="${productSale.name }">
						</a>
					</dt>
					<dd class="goods_tit">
						<c:set var="subheading">
							<c:if test="${!empty productSale.subheading}">(${productSale.subheading})</c:if>
						</c:set>
						<a target="_blank" href="${productSale.product.url}" title="${productSale.sellName }${subheading}">${productSale.sellName}${subheading} 
						<b class="red">${productSale.promValue}<c:if test="${empty productSale.promValue}">${productSale.subheading}</c:if> </b>
						</a>
					</dd>
					
					<dd>
						<del class="l_gray">定价：￥<fmt:formatNumber type="number" value="${productSale.listPrice}" pattern="#.00"/></del>
						<br> 商城价：<b class="red fb">￥<fmt:formatNumber type="number" value="${productSale.salePrice}" pattern="#.00"/></b>
					</dd>
					
					<dd>
						<a class="goods_but" href="javascript:;" bind="addToCart"  data-id="${productSale.id }">购买</a><a class="goods_but"
							target="_blank" href="${productSale.product.url}">咨询</a>
					</dd>
					<%--
					 --%>
				</dl>
			</c:forEach>
 </div>
<script type="text/javascript" src="${serverPrefix}js/list/common_list.js?v=20111221"></script>
<script type="text/javascript" src="${serverPrefix}js/list/mall_list.js"></script>
<script type="text/javascript" src="http://v2.jiathis.com/code_mini/jia.js" charset="utf-8"></script>    
<%@include file="/page/snippets/version2/footer.jsp" %>
</body>
</html>