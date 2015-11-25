<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
	<div class="detail_info">

<ul class="price_info">
	<li>电子书价：<b class="fb">￥${productSale.effectivePrice}</b></li>
	<c:if test="${!empty productSale.paperBook}">
	 <li>纸质书价：&nbsp;&nbsp;￥${productSale.paperBook.effectivePrice}</li>
	</c:if>
	<dl class="ebook_ohter_info">
		<dd>
			作&nbsp;&nbsp;&nbsp;&nbsp;者：
			<c:if test="${!empty product.authorUrl}">
                			${product.authorUrl}
                    	</c:if>
		</dd>
		<dd>
			出&nbsp;版&nbsp;社：<a target="_bland" class="link1"
				href="http://search.winxuan.com/search?manufacturer=<winxuan-string:encode content='${product.manufacturer }' type='utf-8'/>"
				title='${product.manufacturer }'>${product.manufacturer}</a>
		</dd>
		<dd>
			出版时间：
			<fmt:formatDate value="${product.productionDate }"
				pattern="yyyy-MM-dd" />
		</dd>
		<c:if test="${!empty promotions }">
			<dd>
				<label>促销信息：</label>
				<c:forEach items="${promotions}" var="promotion">
					<c:if test="${!empty promotion.advertUrl }">
						<p class="promotion_info">
							<a href="${promotion.advertUrl}" title="${promotion.description}"
								style="color: red;">该商品参加了${promotion.type.name},<c:out
									value="${promotion.title}"></c:out></a>,${promotion.advert}
						</p>
					</c:if>
					<c:if test="${empty promotion.advertUrl }">
						<span class="promotion_info_noUrl" style="color: red;">
							该商品参加了${promotion.type.name},${promotion.title} </span>
						<c:if test="${!empty promotion.advert}">,${promotion.advert}</c:if>
					</c:if>
				</c:forEach>
			</dd>
		</c:if>
		<dt>
			<a class="ilike" href="javascript:;"><span class="red"><b
					dig="dig" id="totalDigging"></b></span></a>
		</dt>
	</dl>

</ul>

<dl class="ohter_info">
	<dd>页数：${productSale.ebookPageCount.value}</dd>
	<%-- <dd>文件大小：${productSale.ebookPageCount.value}M</dd> --%>
	<dd>
		支持的阅读设备：
		<c:if test="${productSale.vendor != '87' }">
		<a class="ebook_ico_online" href="http://www.9yue.com/drm/read.html?bookId=${productSale.productSeptember.septemberBookId}" target="_blank">浏览器</a><a class="ebook_ico_pc" href="http://www.9yue.com/reader/9yueReader0.9.2.msi" target="_blank">PC客户端</a><a class="ebook_ico_phone" href="http://www.9yue.com/readerapp/android2/9yue_reader.apk" target="_blank">安卓</a><c:if test="${supportIphone == true }"><a class="ebook_ico_iphone" href="https://itunes.apple.com/app/jiu-yue-du-shu-for-iphone/id670670008" target="_blank">iPhone</a></c:if><a class="ebook_ico_pad" href="https://itunes.apple.com/app/jiu-yue-du-shu/id566826904" target="_blank">iPad</a>
       </c:if>
       <c:if test="${productSale.vendor == '87' }">
       <a class="ebook_ico_online" href="http://www.9yue.com/reader/tryreadfunder.html?id=${productSale.productSeptember.septemberBookId}#tryread" target="_blank">浏览器</a>
       	&nbsp;&nbsp;【仅支持在线阅读】
       </c:if>
		
	</dd>
</dl>

<table class="addbuttons">
	<tr>
		<td class="hei100">
		<a	href="http://ebook.winxuan.com/shoppingcart?opt=add&p=${productSale.id}" target="_blank" class="ebook_buy">
		</a></td>
		<td>
	
     		<c:if test="${productSale.paperBook.saleStatus.id==13003}">
				<a class="coll_but_big" href="javascript:;" id="addToFavorite"
					bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
				</c:if>
	
				 <c:if test="${empty productSale.paperBook}">
				   <a class="coll_but_big" href="javascript:;" id="addToFavorite"
								bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
				 </c:if>
				
			
			     <c:if test="${productSale.paperBook.saleStatus.id!=13003&&!empty productSale.paperBook}">
					<a class="coll_but" href="javascript:;" id="addToFavorite"
					bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
				</c:if>
		
		
		
		<c:if test="${productSale.paperBook.supplyType.id == 13102}">
		       <a href="http://www.winxuan.com/product/${productSale.paperBook.id}"  target="_blank" class="ebook_order_butb order_butb">
			   </a>
		</c:if>
		
		
		<c:if test="${productSale.paperBook.supplyType.id != 13102}">
		
			
				
	        
		          <c:if test="${productSale.paperBook.saleStatus.id==13002}">
					     <a href="http://www.winxuan.com/product/${productSale.paperBook.id}"  target="_blank" class="addtocart ebook_addtocart">
					     </a>
			     </c:if>
			
			     <c:if test="${productSale.paperBook.saleStatus.id==13001}">
			         <a href="http://www.winxuan.com/product/${productSale.paperBook.id}"  target="_blank" class="ebook_notify_medi">
					 </a>
			     </c:if>
		    
		
		</c:if>
		
		
		
	
		    
		     
		</td>
	</tr>
</table>

</div>