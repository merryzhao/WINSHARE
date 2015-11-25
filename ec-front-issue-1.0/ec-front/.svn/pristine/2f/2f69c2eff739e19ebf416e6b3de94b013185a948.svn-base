<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-图书榜单</title>
<meta name="description"
	content="文轩网图书频道,新华文轩旗下新华书店网上书店,文轩网作为新华书店网上商城提供网络书店网上买书,快递送货上门" />
<meta name="keywords"
	content="文轩网,新华文轩,新华书店,网上书店,网上买书,网上书城,图书,小说,文学,科技,少儿,社科,管理,生活,经济,计算机,外语,教辅 ,图书销售榜" />
<jsp:include page="../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="bookshop" />
</jsp:include>
</head>
<body>
<jsp:include page="/page/snippets/version2/header.jsp">
	<jsp:param value="book" name="label" />
</jsp:include>
<div class="layout">

    <div class="your_path cl">你现在的位置： <span>${location }</span></div>
		<div class="left_box">
			<div class="leftbox_menu ranking_menu">
				<h3 class="box_title">图书类别</h3>
					<div class="book_filter_menu">
						
						<c:forEach items="${bookTopCategorys}" var="bookTopCategory" varStatus="status">
							<div class="filter_list">
								<h5 class="filter_list_h5"><a href="${bookTopCategory.url }">${bookTopCategory.category.name}</a></h5>
								<c:if test="${fn:length(bookTopCategory.children)>0}">
									<ul class="filter_list_ul">
									<c:forEach items="${bookTopCategory.children}" var="child" varStatus="status">
										<li><a href="${child.url}">${child.category.name}</a></li>
									</c:forEach>
									</ul>		
								</c:if>
							</div>						
						</c:forEach>
					</div>
    		</div>
         <cache:fragmentPageCache idleSeconds="86400"  key="FRAGMENT_3000"> 
        	<c:import url="/fragment/3000">
        		<c:param value="${cacheKey}" name="cacheKey"/> 
        	</c:import>
        </cache:fragmentPageCache>	
         <cache:fragmentPageCache idleSeconds="86400"  key="FRAGMENT_3001"> 
        	<c:import url="/fragment/3001">
        		<c:param value="${cacheKey}" name="cacheKey"/> 
        	</c:import>
        </cache:fragmentPageCache>	
    	</div>
		<div class="right_box">
    		<div class="book_list margin10">
        		<div class="top_title">
					文轩榜
				</div>
				<ul class="top_sort">
					<li><b class="fb">文轩图书榜：</b><span <c:if test="${topType==0}">class="active"</c:if>><a href="http://www.winxuan.com/booktop/0/1/1/1">畅销榜</a></span><span <c:if test="${topType==1}">class="active"</c:if>><a href="http://www.winxuan.com/booktop/1/1/1/1">新书榜</a></span></li>
					<li><b class="fb">近&nbsp;&nbsp;&nbsp;&nbsp;期&nbsp;&nbsp;&nbsp;&nbsp;榜：</b><span <c:if test="${timeType==1}">class="active"</c:if>><a href="http://www.winxuan.com/booktop/0/1/1/1" bind="week_top">近一周</a></span><span <c:if test="${timeType==2}">class="active"</c:if>><a href="http://www.winxuan.com/booktop/0/1/1/1" bind="a_month_top">近一个月</a></span><span <c:if test="${timeType==3}">class="active"</c:if>><a href="http://www.winxuan.com/booktop/0/1/1/1" bind="three_month_top">近三个月</a></span></li>
					<li class="last"><b class="fb">年&nbsp;&nbsp;&nbsp;&nbsp;度&nbsp;&nbsp;&nbsp;&nbsp;榜：</b><span <c:if test="${timeType==4}">class="active"</c:if>><a href="http://www.winxuan.com/booktop/0/1/4/1" bind="last_year_top">${lastYear}年</a></span></li>
				</ul>
				<div class="book_page">
				<c:if test="${pageNum>=1 }">
						<c:forEach begin="1" end="${pageNum}" step="1" var="i">
							<a href="http://www.winxuan.com/booktop/0/1/1/1"  bind="page_head_${i}" <c:if test="${page==i}">class="current"</c:if>>${i}</a>
						</c:forEach>
				</c:if>
				</div>
				<c:forEach items="${bookTopProductSales}" var="bookTopProductSale" varStatus="status">
	            	<dl class="store_list">
						<dt>
							<a target="_blank" href="${bookTopProductSale.productsale.url}" title=""><img alt=""
								src="${bookTopProductSale.productsale.product.imageUrlFor160px }"  ></a>
						</dt>
						<dd>
							<strong class="item_name"><span class="number_rank">${status.count+20*(page-1)}</span><a target="_blank" href="${bookTopProductSale.productsale.url}"  
								title="${bookTopProductSale.productsale.sellName}">${bookTopProductSale.productsale.sellName}</a> </strong>
						</dd>
						<dd>
							<div class="com_star fl">
								<b style="width: ${bookTopProductSale.avgRank*100.0/5}%;"></b>
							</div>
							 (${bookTopProductSale.commentCount}条评论)
						</dd>
						<dd>
							${empty bookTopProductSale.productsale.product.defaultAuthorsATag?'-':bookTopProductSale.productsale.product.defaultAuthorsATag }&nbsp;&nbsp;
							<c:choose>
								<c:when test="${!empty bookTopProductSale.productsale.product.productionDate }"><fmt:formatDate value="${bookTopProductSale.productsale.product.productionDate }" pattern="yyyy年MM月"/></c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
							&nbsp;
							<c:choose>
								<c:when test="${!empty bookTopProductSale.productsale.product.manufacturer }">
								<c:url var="commodityManufacturer" value="search"> 
								  <c:param name="manufacturer" value="${bookTopProductSale.productsale.product.manufacturer}"/> 
								</c:url> 
								<a class="link1" href="http://search.winxuan.com/${commodityManufacturer }" target="_blank">${bookTopProductSale.productsale.product.manufacturer}</a>
								</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>							
						</dd>
		
						<dd>
							<p class="item_intro">${bookTopProductSale.productsale.productIntroduce.content }</p>
						</dd>
						<dd>
							<span class="fr">
							<a class="buy_but" href="javascript:;" bind="addToCart" data-id="${bookTopProductSale.productsale.id}" >购买</a>
							 <a href="javascript:;" class="coll_but2" bind="addFavorite" data-id="${bookTopProductSale.productsale.id}">收藏</a> </span> 
								<span class="fl"><b	class="red fb"><fmt:formatNumber type="number" value="${bookTopProductSale.productsale.salePrice}" pattern="#.00"/>元</b>
								<c:choose>
                            		<c:when test="${bookTopProductSale.productsale.discount>=1||bookTopProductSale.productsale.discount==0}"></c:when>
                            		<c:otherwise>
                            			（<fmt:formatNumber value="${bookTopProductSale.productsale.discount*10}" pattern="#0.0"/>折）
                            		</c:otherwise>
                            	</c:choose>
								定价：<del>￥<fmt:formatNumber type="number" value="${bookTopProductSale.productsale.listPrice}" pattern="#.00"/></del>元</span>
						</dd>
		
						<dd class="hei1"></dd>
					</dl>
				</c:forEach>
				<div class="book_page">
				<c:if test="${pageNum>=1 }">
						<c:forEach begin="1" end="${pageNum}" step="1" var="i">
							<a href="http://www.winxuan.com/booktop/0/1/1/1"  bind="page_bottom_${i}" <c:if test="${page==i}">class="current"</c:if>>${i}</a>
						</c:forEach>
				</c:if>
				</div>
	        </div>
	     </div>
	  
	  <div class="hei10"></div>
	</div>
<div class="hei10"></div>

<%@include file="../snippets/version2/footer.jsp" %>
<script src="${serverPrefix}libs/core.js?${version}"></script>
<script type="text/javascript" src="${serverPrefix}js/list/common_list.js?${version}"></script>
<script src="${serverPrefix}js/booktop/booktop.js?${version}"></script>
</body>
</html>
