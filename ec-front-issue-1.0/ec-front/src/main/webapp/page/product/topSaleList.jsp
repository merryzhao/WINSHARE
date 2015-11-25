<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!-- tab1 list-base-1 -->
<c:if test="${not empty weekSaleBookTop && not empty newSaleBookTop }">
 <div class="col col-base-1 hot-list" cache="${cacheKey}">
     <div class="title">
         <h2>同类热销商品</h2>
     </div>
     <div class="tab">
             <div class="tab-item current">近7日热销榜</div>
             <div class="tab-item">新书热销榜</div>
         </div>
         <div class="cont">
             <ul class="list list-base-1">
                 <!-- list-base-1 li (loop) -->
				<c:forEach var="weekSale" items="${weekSaleBookTop}" varStatus="i">
				<c:if test="${i.index<10}">
                 <li>
                     <div class="cell cell-style-1">
                         <div class="img"><a href="${weekSale.productsale.url}" title="${weekSale.productsale.effectiveName}" target="_blank">
                         <img src="${weekSale.productsale.imageUrl }" alt="${weekSale.productsale.effectiveName}"></a></div>
                         <div class="name"><a href="${weekSale.productsale.url}" target="_blank">
                         <winxuan-string:substr	length="17" content="${weekSale.productsale.sellName }">
                         </winxuan-string:substr></a></div>
                         <div class="price-n"><b>￥${weekSale.productsale.salePrice}</b><s>￥${weekSale.productsale.product.listPrice}</s></div>
                         <c:choose>
	                         <c:when test="${ i.index < 3}">
	                         	<div class="tag topx">${ i.index + 1}</div>
	                         </c:when>
	                         <c:otherwise>
	                         	<div class="tag">${ i.index + 1}</div>
	                         </c:otherwise>
                         </c:choose>
                     </div>
                 </li>
                 </c:if>
                 </c:forEach>
                 <!-- list-base-1 li (loop) end -->
             </ul>
             <!-- list-base-1 end -->

             <!-- tab2 list-base-1 -->
             <ul class="list list-base-1" style="display: none;">
                 <!-- list-base-1 li (loop) -->
				<c:forEach var="newBookSale" items="${newSaleBookTop}" varStatus="i">
				<c:if test="${i.index<10}">
                 <li>
                     <div class="cell cell-style-1">
                         <div class="img"><a href="${newBookSale.productsale.url}" title="${newBookSale.productsale.effectiveName}" target="_blank">
                         <img src="${newBookSale.productsale.imageUrl}" alt="${newBookSale.productsale.effectiveName}"></a></div>
                         <div class="name"><a href="${newBookSale.productsale.url}" target="_blank">
                         <winxuan-string:substr	length="17" content="${newBookSale.productsale.sellName }">
                         </winxuan-string:substr></a></div>
                         <div class="price-n"><b>￥${newBookSale.productsale.salePrice}</b><s>￥${newBookSale.productsale.product.listPrice }</s></div>
                          <c:choose>
	                         <c:when test="${ i.index < 3}">
	                         	<div class="tag topx">${ i.index + 1}</div>
	                         </c:when>
	                         <c:otherwise>
	                         	<div class="tag">${ i.index + 1}</div>
	                         </c:otherwise>
                         </c:choose>
                     </div>
                 </li>
                 </c:if>
                 </c:forEach>
                 <!-- list-base-1 li (loop) end -->
                 
             </ul>
          </div>
          	<!-- list-base-1 end -->
		 </div>
</c:if>