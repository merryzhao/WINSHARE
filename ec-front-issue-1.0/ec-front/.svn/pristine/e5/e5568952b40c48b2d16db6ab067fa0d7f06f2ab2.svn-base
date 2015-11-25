 <%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set var="des" value="" />
<c:forEach var="description" items="${product.descriptionList }"
	varStatus="status">
	<c:if test="${status.index == 0}">
		<c:set var="des" value="${description.content}" />
	</c:if>
</c:forEach>
<c:set var="subdes">
	<winxuan-string:substr length='30' content='${des}' />
</c:set>
<c:choose>
	<c:when test="${productSale.storageType.id==6004}">
		<title><c:out value="${product.name}" />-<c:out
				value="${product.author}"></c:out>-<c:out
				value="${product.category.parent.name}" />-电子书阅读下载-文轩九月网</title>
		<meta name="description"
			content="<c:out value="${product.name}"/>作者:<c:out value="${product.author}"/>,出版社:<c:out value="${product.manufacturer}" />,ISBN:<c:out value="${product.barcode}"/>文轩九月网电子书提供海量电子书在线免费试读、免费电子书、电子书下载、手机电脑同步阅读。" />
		<meta name="keywords"
			content="<c:out value="${product.name}"/>,<c:out value="${product.author}"/>,<c:out value="${product.manufacturer}"/>,<c:out value="${product.barcode}"/>,电子书阅读下载" />
	</c:when>
	<c:otherwise>
		<title><c:out value="${product.name}" />-<c:out
				value="${product.author}"></c:out>-<c:out
				value="${product.category.parent.name}" />-文轩网</title>
		<meta name="description"
			content="文轩网(winxuan.com)提供<c:out value="${product.name}"/>特卖,<c:out value="${product.name}"/>讲述:<c:out value="${subdes}" />,<c:out value="${product.author}"/>,<c:out value="${product.manufacturer}"/>,<c:out value="${product.barcode}"/>" />
		<meta name="keywords"
			content="<c:out value="${product.name}"/>,<c:out value="${product.author}"/>,<c:out value="${product.manufacturer}"/>,<c:out value="${product.barcode}"/>,图书" />
	</c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${productSale.storageType.id==6004}">
        <jsp:include page="/page/snippets/version2/meta.jsp" flush="true" >
            <jsp:param name="type" value="details" />
            <jsp:param value="true" name="ebook" />
        </jsp:include>
    </c:when>
    <c:otherwise>
        <jsp:include page="/page/snippets/version2/meta.jsp" flush="true" >
            <jsp:param name="type" value="details" />
            <jsp:param value="false" name="ebook" />
        </jsp:include>
    </c:otherwise>
</c:choose>
</head>
<body class="bookproduct">
   <c:if test="${productSale.storageType.id==6004}">
	<jsp:include page="../snippets/version2/header.jsp">
	<jsp:param value="ebook" name="label" />
	</jsp:include>
	</c:if>
   
    <c:if test="${productSale.storageType.id!=6004}">
	<jsp:include page="../snippets/version2/header.jsp">
	<jsp:param value="book" name="label" />
	</jsp:include>
	</c:if>
	
	<div class="layout">
		<div class="your_path cl">
			你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网${productBought.storageType.id}</a>
				&gt; ${ product.category.allCategoryName} > ${productSale.effectiveName}</span>
		</div>
		<input type="hidden" value="${productSale.id}" class="productSaleId" />
		<div class="left_box">
			<div class="left_container" style="display: none;">
				<h4 class="fb">相关图书系列</h4>
				<p class="related">
					<a title="book name" href="javascript:;"><img
						src="/goods/sml_1226030.jpg" alt="book name"></a><a
						href="javascript:;">二号首长</a>
				</p>
			</div>
			<c:if test="${!empty alsoBoughtList}">
				<div class="left_container">
					<h4 class="fb">购买此商品的顾客还购买了</h4>
					<dl class="also_viewed bought_list">
						<c:set var="productBought" value="${alsoBoughtList}"></c:set>
						<c:forEach var="productBought" items="${ alsoBoughtList}"
							varStatus="status">
							<c:if test="${status.first }">
								<dd <c:if test="${!status.first }">style="display: none"</c:if>
									class="expand">
									<a class="also" href="${productBought.url}"
										target="_blank"> <img
										src="${productBought.product.imageUrlFor55px }"
										alt="${productBought.effectiveName }">
									</a> <a href="${productBought.url}" class="link4"
										title="${productBought.effectiveName }" target="_blank">
								<c:if test="${productBought.storageType.id==6004}">
             										<div class="ebook_mini_ico"></div>
										</c:if>
												<winxuan-string:substr	length="16" content="${productBought.effectiveName }"></winxuan-string:substr>
									</a><br> <span>文轩价：</span><span class="fb red">￥${productBought.salePrice}</span>
								</dd>
							</c:if>
							<dd <c:if test="${status.first }">style="display: none"</c:if>>
								<a class="also" href="${productBought.url}"
									target="_blank"> <img
									src="${productBought.product.imageUrlFor55px }"
									alt="${productBought.effectiveName }">
								</a> <a href="${productBought.url}" class="link4"
									title="${productBought.effectiveName }" target="_blank">		
	                             	<c:if test="${productBought.storageType.id==6004}">
             										<div class="ebook_mini_ico"></div>
										</c:if><winxuan-string:substr
										length="16" content="${productBought.effectiveName}"></winxuan-string:substr>
								</a><br> <span>文轩价：</span><span class="fb red">￥${productBought.salePrice}</span>
							</dd>
						</c:forEach>
					</dl>
				</div>
			</c:if>
			<c:if test="${!empty alsoViewedList}">
				<div class="left_container">
					<h4 class="fb">浏览过此书的顾客还看过</h4>
					<dl class="also_viewed viewed_list">
						<c:set var="productRecommends" value="${alsoViewedList}"></c:set>
						<c:forEach var="productRecommend" items="${ productRecommends}"
							varStatus="status">
							<c:if test="${status.first }">
								<dd <c:if test="${!status.first }">style="display: none"</c:if>
									class="expand">
									<a class="also" href="${productRecommend.product.url}"
										target="_blank"><img
										src="${productRecommend.product.imageUrlFor55px }"
										alt="${productRecommend.effectiveName }"></a><a
										href="${productRecommend.url}" class="link4"
										title="${productRecommend.effectiveName }" target="_blank">
											<c:if test="${productRecommend.storageType.id==6004}">
             										<div class="ebook_mini_ico"></div>
										</c:if>
										<winxuan-string:substr length="16"
											content="${productRecommend.effectiveName }"></winxuan-string:substr>
									</a><br> <span>文轩价：</span><span class="fb red">￥${productRecommend.salePrice}</span>
								</dd>
							</c:if>
							<dd <c:if test="${status.first }">style="display: none"</c:if>>
								<a class="also" href="${productRecommend.product.url}"
									target="_blank"><img
									src="${productRecommend.product.imageUrlFor55px }"
									alt="${productRecommend.effectiveName }"></a><a
									href="${productRecommend.url}" class="link4"
									title="${productRecommend.effectiveName }" target="_blank">
									<c:if test="${productRecommend.storageType.id==6004}">
             										<div class="ebook_mini_ico"></div>
										</c:if>
									<winxuan-string:substr length="16"
										content="${productRecommend.effectiveName }"></winxuan-string:substr>
								</a><br> <span>文轩价：</span><span class="fb red">￥${productRecommend.salePrice}</span>
							</dd>
						</c:forEach>
					</dl>
				</div>
			</c:if>
			<script>
				seajs.use("jQuery", function($) {
					var contViewed = $("dl.viewed_list");
					contViewed.delegate("dd", "mouseover", function() {
						contViewed.find("dd.expand").removeClass("expand");
						$(this).addClass("expand");
					});
					var contBought = $("dl.bought_list");
					contBought.delegate("dd", "mouseover", function() {
						contBought.find("dd.expand").removeClass("expand");
						$(this).addClass("expand");
					});
				});
			</script>
       		<cache:fragmentPageCache idleSeconds="86400"
				key="PAGE_PRODUCT_CATEGORY_HOT_${product.category.code}">
				<c:import url="/product/hotsalelist?code=${product.category.code}">
					<c:param value="${cacheKey}" name="cacheKey" />
				</c:import>
			</cache:fragmentPageCache>

			<%-- 电子书推荐片段--%>
			<c:if test="${productSale.storageType.id==6004}">
					<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_3004">
						<c:import url="/fragment/3004">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
					</cache:fragmentPageCache>
					<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_3005">
						<c:import url="/fragment/3005">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
					</cache:fragmentPageCache>
				</c:if>
				
				<%-- 纸质书推荐片段--%>
				<c:if test="${productSale.storageType.id!=6004}">
					<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_3000">
						<c:import url="/fragment/3000">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
					</cache:fragmentPageCache>
					<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_3001">
						<c:import url="/fragment/3001">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
					</cache:fragmentPageCache>
				</c:if>
			
			

			<p class="left_ads" style="display: none">
				<a title="好书360" href="javascript:;"><img
					src="${serverPrefix}css2/images/ads/ads01.jpg" alt="好书360"
					width="200" /></a>
			</p>
			<div class="left_container" bind="authorEl">
				<h4 class="fb">
					<c:if test="${!empty product.authors }">
						<c:forEach var="author" items="${product.authors}"
							varStatus="status">
							<c:if test="${status.first }">
								<a class="fr link4"
									href="http://search.winxuan.com/search?author=<winxuan-string:encode content='${author}' type='utf-8'/>">更多</a>
							</c:if>
						</c:forEach>
					</c:if>
					该作者其他作品
				</h4>
				<dl class="also_viewed" bind="otherProduct"
					param="<winxuan-string:encode content='${product.author}' type='utf-8'/>"></dl>
			</div>
			<div class="left_container yourhistory">
				<h4 class="fb">你的浏览历史</h4>
				<dl class="also_viewed" id="also_viewed"></dl>
			</div>
			<div style="display: none;">
				<p class="left_ads">
					<a title="人气好书大盘点" href="javascript:;"><img
						src="${serverPrefix}css2/images/ads/ads02.jpg" width="200"
						alt="人气好书大盘点" /></a>
				</p>
				<p class="left_ads">
					<a title="企业团购" href="javascript:;"><img
						src="${serverPrefix}css2/images/ads/ads03.jpg" alt="企业团购"
						width="200" /></a>
				</p>
			</div>
		</div>
		<div class="right_box">
			<h1 class="goods_title">
				${productSale.effectiveName}
				<c:choose>       
		        	<c:when test="${!empty productSale.promValue && prodcutSale.hasPromotion} ">
		        		(${productSale.promValue})
		        	</c:when>
		        	<c:otherwise>
		        		<c:if test="${!empty productSale.subheading}">
		                    	(${ productSale.subheading})
		                </c:if> 
		        	</c:otherwise>
		        </c:choose>
				<c:if test="${productSale.needFreeFee }">
					<span class="promotion" href="javascript:;">免运费</span>
				</c:if>
			</h1>
			
				<c:if test="${productSale.storageType.id==6004}">
				<div class="goods_info">
					<jsp:include page="ebook/eBookShareInfo.jsp" />
					<jsp:include page="ebook/eBookDetailInfo.jsp" />
					<div class="hei35"></div>
					</div>
					<jsp:include page="ebook/productInfo-blend.jsp" />
				
				</c:if>
				<c:if test="${productSale.storageType.id!=6004}">
					<div class="goods_info">
					<c:set var="performance" value="${productSale.performance}"></c:set>
					<jsp:include page="paperBook/paperShareInfo.jsp" />
					<jsp:include page="paperBook/paperBookDetailInfo.jsp" />
					<div class="hei1"></div>
					</div>
					<jsp:include page="productInfo-blend.jsp" />
				</c:if>
			
			
			
		
		</div>
		<div class="hei10"></div>
	</div>
	<script type="text/javascript"
		src="${serverPrefix}js/product.js?${version}"></script>
	<script type="text/javascript"
		src="${serverPrefix}js/visit.js?${version}"></script>
	<script type="text/javascript"
		src="http://v2.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
	<jsp:include page="../snippets/version2/footer.jsp"></jsp:include>
	<script type="text/javascript">
		var _ozurltail = "#${product.secondLevelCategoryId}";
		var _ozprm = "cid99=${productSale.bookStorageType}${product.category.id}&press=${product.manufacturer}&author=${product.author}";
	</script>
</body>
</html>
