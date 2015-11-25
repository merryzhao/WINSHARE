	<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网_百货_卖家店铺首页</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="bookshop" name="type"/>
</jsp:include>
</head>

<body class="shopIndex">
<jsp:include page="/page/snippets/version2/header.jsp">
		<jsp:param value="mall" name="label" />
</jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a class="link4" title="文轩网" href="javascript:;">${shop.name }</a></span> </div>
  <input type="hidden" value="0" class="productSaleId"/>
  <input type="hidden" value="${shop.id}" class="shopId"/>
  <div class="left_box">
   <c:import url="shop_left_info.jsp"></c:import> 
  </div>
  <div class="right_box">
  <c:if test="${shoptopadvert !=null }">
    <div class="seller_ads_box margin10">
     <ul class="seller_ads">
       <c:forEach items="${shoptopadvert}" var="shopColumn" varStatus="status">
          <li><a href="${shopColumn.href}" target="_blank" title=""><img src="${serverPrefix}upload/shopColumn/${shop.id}/${shopColumn.img}"></a></li>
       </c:forEach>
      </ul>
      <ul class="ads_no">
        <c:forEach items="${shoptopadvert}" var="shopPromotion" varStatus="status">
           <li>${status.count}</li>
       </c:forEach>
      </ul>
    </div>
    </c:if>
   
   <c:if test="${shopHots!=null}">
    <div class="hot_goods margin10">
      <dl class="hot_top">
        <dt>卖家热销</dt>
        <dd><a class="current_no" href="javascript:;">1</a><a href="javascript:;">2</a><a href="javascript:;">3</a>
        </dd>
      </dl>
      <a class="pre_page" href="javascript:;"></a> <a class="next_page" href="javascript:;"></a>
      <div class="goods_box">
      	<div class="goods_list">
	      <c:forEach items="${shopHots}" var="shopHot" varStatus="status">
	        <c:set var="productsale" value="${shopHot}"/>
	          	<dl>
	          	<dt>
	          	<c:if test="${productsale.hasPromotion }"><p>
	          	<img src="../../images/ads/pro_icon1.png"/></p>
	          	</c:if> 
	            <a href="${productsale.url }" title="<c:out value="${productsale.product.name }"/>"><img class="book_img" src="${productsale.product.imageUrlFor110px}" alt="${productsale.product.name }"></a></dt>
	          	<dd class="goods_tit"><a href="${productsale.url }" title="<c:out value="${productsale.product.name }"/>">${productsale.product.name }<b class="orange">${product.subheading }</b></a></dd>
	          <dd><del class="l_gray">定价：￥${productsale.product.listPrice }</del><br>
	            商城价：<b class="red fb">￥${productsale.salePrice}</b></dd>
	           </dl>
	       </c:forEach>
       	</div>
      </div>
    </div>
   </c:if>
   
   <c:if test="${shopPromotion!=null}">
    <div class="pro_goods margin10">
      <dl class="buy_top">
        <dt>卖家促销</dt>
        <dd><a class="current_no" href="javascript:;">1</a><a href="javascript:;">2</a><a href="javascript:;">3</a>
        </dd>
      </dl>
      <a class="pre_page" href="javascript:;"></a> <a class="next_page" href="javascript:;"></a>
      <div class="goods_box">
      	<div class="goods_list">
	      <c:forEach items="${shopPromotion}" var="shopPromotion" varStatus="status">
	        <c:set var="productsale" value="${shopPromotion}"/>
	          	<dl>
	          	<dt>
	          	<c:if test="${productsale.hasPromotion }"><p>
	          	<img src="../../images/ads/pro_icon1.png"/></p>
	          	</c:if> 
	            <a href="${productsale.url }" title="<c:out value="${productsale.product.name }"/>"><img class="book_img" src="${productsale.product.imageUrlFor110px}" alt="${productsale.product.name }"></a></dt>
	          	<dd class="goods_tit"><a href="${productsale.url }" title="<c:out value="${productsale.product.name }"/>">${productsale.product.name }<b class="orange">${product.subheading }</b></a></dd>
	          <dd><del class="l_gray">定价：￥${productsale.product.listPrice }</del><br>
	            商城价：<b class="red fb">￥${productsale.salePrice}</b></dd>
	           </dl>
	       </c:forEach>
       	</div>
      </div>
    </div>
   </c:if>
   
  <c:forEach items="${shopColumns}" var="columns" varStatus="status">
  <c:if test="${columns.type.id==47003}">
  <c:if test="${fn:length(columns.shopColumnItems)>0}">
       <h3 class="pro_title margin10">
	    <a href="http://www.winxuan.com/shop/${shop.id}/category/${columns.shopcategory.id}" title="${columns.name}"><span> ${columns.name}(${fn:length(columns.shopColumnItems)})</span></a>
	    </h3>
	 <c:forEach items="${columns.shopColumnItems}" var="columnsItems" varStatus="status"> 
	 <c:if test="${status.index<columns.productnum}">
	    <dl class="goods_list">
		      <dt>
		        <c:if test="${columnsItems.productSale.hasPromotion }"><p><img src="../../images/ads/pro_icon1.png"/></p></c:if> 
		        <a href="${columnsItems.productSale.url }" title="${columnsItems.productSale.product.name }"><img class="book_img" src="${columnsItems.productSale.product.imageUrlFor160px}" alt="${columnsItems.productSale.product.name }"></a></dt>
		      <dd class="goods_tit"><a href="${columnsItems.productSale.url }" title="<c:out value="${columnsItems.productSale.product.name }"/>">${columnsItems.productSale.product.name } <b class="orange">${product.subheading }</b></a></dd>
		      <dd><del class="l_gray">定价：￥${columnsItems.productSale.product.listPrice }</del><br>
		        商城价：<b class="red fb">￥${columnsItems.productSale.salePrice }</b></dd>
		  </dl>
	 <c:if test="${status.index==(columns.productnum-1)||status.last}"><p class="cl txt_right"><a class="view_but" href="http://www.winxuan.com/shop/${shop.id}/category/${columns.shopcategory.id}">所有宝贝</a></p></c:if> 
	 </c:if>
	 </c:forEach>
</c:if>
	 </c:if>
</c:forEach>

  
    <h3 class="pro_title margin10"><a href="http://www.winxuan.com/shop/${shop.id}/category/0" title="掌柜所有宝贝">掌柜所有宝贝(<c:out value="${productListSize}"></c:out>)</a></h3>
    <c:forEach items="${productList}" var="productsale" varStatus="status" end="3">
     <dl class="goods_list">
		      <dt>
		        <c:if test="${productsale.hasPromotion }"><p><img src="../../images/ads/pro_icon1.png"/></p></c:if> 
		        <a href="${productsale.url}" title="${productsale.product.name }"><img class="book_img" src="${productsale.product.imageUrlFor110px}" alt="${productsale.product.name} ${product.subheading}"></a></dt>
		      <dd class="goods_tit"><a href="${productsale.url }" title="<c:out value="${productsale.product.name }"/>">${productsale.product.name } <b class="orange">${product.subheading }</b></a></dd>
		      <dd><del class="l_gray">定价：￥${productsale.product.listPrice }</del><br>
		        商城价：<b class="red fb">￥${productsale.salePrice }</b></dd>
		</dl>
	   <c:if test="${status.index==3||status.last }"><p class="cl txt_right"><a class="view_but" href="http://www.winxuan.com/shop/${shop.id}/category/0">所有宝贝</a></p></c:if> 
    </c:forEach>
    
    
    <h4 class="comment_tit margin10">店铺评分<span class="commentCount">(${commentCount}条)</span></h4>
            <div class="commnet_score">
                <div class="rating_results">
                     <p>用户评分：<a class="rating_link" href="javascript:;" id="avgStarScore"><fmt:formatNumber value="${shopRankAvgrank}" pattern="0.0" type="number"></fmt:formatNumber></a>星(共<a class="rating_link" href="javascript:;" id="allScoreCount">${shopRankCount}</a>人参与)</p>
                    <ul>
                        <li>
                            <div class="com_star"><b style="width:100%;"></b></div>
                            <div class="com_column"><b style="width:${rankCountStar5*100}%;" id="shareByFiveStar"></b></div>
                            <b id="commentCountByFiveStar">${shareStar5}</b>人</li>
                        <li>
                            <div class="com_star"><b style="width:80%;"></b></div>
                            <div class="com_column"><b style="width:${rankCountStar4*100}%;" id="shareByFourStar"></b></div>
                            <b id="commentCountByFourStar">${shareStar4}</b>人</li>
                        <li> 
                            <div class="com_star"><b style="width:60%;"></b></div>
                            <div class="com_column"><b style="width:${rankCountStar3*100}%;" id="shareByThreeStar"></b></div>
                            <b id="commentCountByThreeStar"> ${shareStar3}</b>人</li>
                       <li>
                            <div class="com_star"><b style="width:40%;"></b></div>
                            <div class="com_column"><b style="width:${rankCountStar2*100}%;" id="shareByTwoStar"></b></div>
                             <b id="commentCountByTwoStar">${shareStar2}</b>人</li>
                         <li>
                            <div class="com_star"><b style="width:20%;"></b></div>
                            <div class="com_column"><b style="width:${rankCountStar1*100}%;" id="shareByOneStar"></b></div>
                             <b id="commentCountByOneStar">${shareStar1}</b>人</li>
                   
                    </ul>
                </div>
                <div class="do_score">
                    
                </div>
            </div>
        <p class="hei10"></p>
       <div class="comment_content"> </div>

<div class="tabcontent02" type="question" action ="http://www.winxuan.com/shop/commentlist/${shop.id}?format=json">
            <div class="ajaxpage_html"  >
                   <div class="page_list" >
                
                   </div>
                <h5 class="view_all"></h5>
            </div>
         </div>
           
      
        <%-- action=  target="tabcontent03"--%>
        <h4 class="comment_tit margin10" >商品咨询</h4>
        <div class="tabcontent03" type="question" action ="http://www.winxuan.com/shop/questlist/${shop.id}?format=json">
            <div class="ajaxpage_html"  >
                   <div class="page_list" >
                
                   </div>
                <h5 class="view_all"></h5>
            </div>
         </div>
  </div>
  
 
  <div class="hei10"></div>
</div>


<script type="text/javascript">
seajs.use(['jQuery','roller','shopslide'],function($,Roller,ShopSlide){
	Roller({
		context:$("div.right_box"),
		paging:true,
		page:3,
		selector:{
			container:"div.pro_goods",
			page:"dl.buy_top a",
			next:"a.next_page",
			prev:"a.pre_page",
			box:"div.goods_list",
			items:"dl"
		},
		className:{pageSelected:"current_no"}
	});
	Roller({
		context:$("div.right_box"),
		paging:true,
		page:3,
		selector:{
			container:"div.hot_goods",
			page:"dl.hot_top a",
			next:"a.next_page",
			prev:"a.pre_page",
			box:"div.goods_list",
			items:"dl"
		},
		className:{pageSelected:"current_no"}
	});
	
   new ShopSlide({
		speed:3000
		});
   
  
});    

</script>
<script src="${serverPrefix}js/common.js?${version}"></script>
<script type="text/javascript" src="${serverPrefix}js/ajaxpage/shopajax.js"></script>
<script type="text/javascript" src="http://v2.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
<%@include file="/page/snippets/version2/footer.jsp" %>
</body>
</html>
