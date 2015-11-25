<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<c:set var="messageString" value="全场满28免运费,未找到对应的商品,商品数量不满足,商品已下架,错误的数量,错误的参数"/>
<c:set var="messageArray" value='${fn:split(messageString,",")}'/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>购物车--文轩网</title>
<jsp:include page="${contextPath}/page/snippets/version2/meta.jsp">
	<jsp:param value="v1" name="type"/>
</jsp:include>
</head>
<body class="shopping-cart">
<div class="header other_header">
    <div class="logo_box"><a title="文轩网,新华书店" href="http://www.winxuan.com/"><img data-lazy="false" src="${serverPrefix}images/logo.gif" alt="文轩网" width="188" height="46"></a></div>
</div>
<div class="layout">
    <div class="shop_progress"> <span id="progress1"></span> </div>
    <c:choose>
    	<c:when test="${not empty shoppingcart.groupItems}">
 	<p class="promotions"></p>
    <div id="cart-goods-list">
	    <table class="cart_goods" width="100%" border="0" cellspacing="0" cellpadding="0"><thead><tr><th colspan="2">商品名称</th><th width="80">积分</th><th width="120">文轩价</th><th width="80">优惠</th><th width="100">数量</th><th width="80">操作</th></tr></thead><tbody>
<%-- 	    <c:set var="groupedShoppingCart" value="${shoppingcart.groupItems}"/> --%>
<%-- 	       	<c:forEach var="seller" items="${groupedShoppingCart}"> --%>
<%-- 	       		<tr><td class="suppliers" colspan="7"><p>商家：${seller.key.shop.shopName}${seller.key.supplyType.id==13102?"&nbsp;&nbsp;<b class='red'>新品预售</b>":""}</p></td></tr> --%>
<%-- 	       		<c:forEach var="item" items="${seller.value}"> --%>
<%-- 	       			<c:set var="tag" value=""/> --%>
<%-- 	       			<c:if test='${item.statusCode=="13001"}'> --%>
<%-- 	                	<c:set var="tag" value=" class='out-of-stock'"/> --%>
<%-- 	                </c:if> --%>
<%-- 	        		<tr${tag}> --%>
<%-- 		                <td width="60"><a href="${item.url}" class="thumbnail" title="${item.sellName}"><img src="${item.imageUrl}" alt="${item.sellName}" class="fl"/></a></td> --%>
<%-- 		                <td><p class="ac_goods_intro"><a href="${item.url}" title="${item.sellName}">${item.sellName}</a></p></td> --%>
<%-- 		                <td>${item.points}</td> --%>
<%-- 		                <td><b class="red fb">￥${item.salePrice}（${fn:substringBefore(item.discount*100,".")}折）</b></td> --%>
<%-- 		                <td>减${item.savePrice}</td> --%>
<%-- 		                <td><a href="javascript:;" bind="reduceQuantity"><img src="${contextPath}/images/reduce.gif" alt="reduce"></a><input class="quantity" name="quantity" type="text" size="4" value="${item.quantity}" default="${item.quantity}" id="${item.productSaleId}" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="ime-mode:Disabled"/><a href="javascript:;" bind="increaseQuantity"><img src="${contextPath}/images/increase.gif" alt="increase"></a></td> --%>
<%-- 		                <td><a class="link2" href="javascript:;" bind="addItemToFavorite" param="${item.productSaleId}">移入收藏</a><br/><a class="link2" href="javascript:;" bind="removeItemFromCart" param="${item.productSaleId}">删除</a></td> --%>
<%-- 		            </tr> --%>
<%-- 	            </c:forEach> --%>
<%-- 	       	</c:forEach> --%>
				<tr><td height="170" colspan="7"><p class="loading"><span>正在更新购物车</span></p></td></tr>
	        </tbody>
	    </table>
    </div>
    <div class="checkout" id="statistics"><p>商品价格总计：<b id="salePrice">￥${shoppingcart.salePrice}</b></p><p><a class="continue_Shopping" href="###" bind="shopping">&lt;&lt; 继续购物</a><button class="checkout_but" bind="GotoBalance">结&nbsp;&nbsp;算</button></p></div>
    	</c:when>
    	<c:otherwise>
    	<p class="promotions">您的购物车中还没有任何商品</p>
    	<div id="cart-goods-list">
	    <table class="cart_goods" width="100%" border="0" cellspacing="0" cellpadding="0">
	        <thead>
	            <tr>
	                <th width="45%" colspan="2">商品名称</th>
	                <th>积分</th>
	                <th>文轩价</th>
	                <th>优惠</th>
	                <th>数量</th>
	                <th>操作</th>
	            </tr>
	        </thead>
	        <tbody>
	            <tr><td height="170" colspan="7">你还没有挑选商品， <a class="link2" href="http://www.winxuan.com/">去促销看看 &gt;&gt;</a></td></tr>
	        </tbody>
	    </table>
	    </div>
	    <div class="checkout" id="statistics" style="display:none;"><p>商品价格总计：<b id="salePrice">￥${shoppingcart.salePrice}</b></p><p><a class="continue_Shopping" href="###">&lt;&lt; 继续购物</a><button class="checkout_but" bind="GotoBalance">结&nbsp;&nbsp;算</button></p></div>
    	</c:otherwise>
    </c:choose>
	
	<div id="favorite"></div>   
    <div class="recommend wider_rec unexp_rec" id="recommend" style="display:none;">
        <h3><a class="fr open_up" href="javascript:;"></a><a class="fr pack_up" href="javascript:;"></a> <a class="expansion" href="javascript:;"></a><a class="narrow" href="javascript:;"></a> 精品推荐 </h3>        
        <a id="arrow_pre" href="javascript:;"></a> <a id="arrow_next" href="javascript:;"></a>
        <div class="recom_goods_box">
            <ul id="recom_goods_scroll_ul">
                <li>
                    <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_1226030.jpg"></a></div>
                    <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                    <div class="comment"><span class="fr">20条评论</span>
                        <div class="comment_star"><span style="width:80%"></span></div>
                    </div>
                </li>
                <li>
                    <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002725.jpg"></a></div>
                    <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                    <div class="comment"><span class="fr">20条评论</span>
                        <div class="comment_star"><span style="width:80%"></span></div>
                    </div>
                </li>
                <li>
                    <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002850.jpg"></a></div>
                    <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                    <div class="comment"><span class="fr">20条评论</span>
                        <div class="comment_star"><span style="width:80%"></span></div>
                    </div>
                </li>
                <li>
                    <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002742.jpg"></a></div>
                    <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                    <div class="comment"><span class="fr">20条评论</span>
                        <div class="comment_star"><span style="width:80%"></span></div>
                    </div>
                </li>
                <li>
                    <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002724.jpg"></a></div>
                    <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                    <div class="comment"><span class="fr">20条评论</span>
                        <div class="comment_star"><span style="width:80%"></span></div>
                    </div>
                </li>
                <li>
                    <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002742.jpg"></a></div>
                    <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                    <div class="comment"><span class="fr">20条评论</span>
                        <div class="comment_star"><span style="width:80%"></span></div>
                    </div>
                </li>
                <li>
                    <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002724.jpg"></a></div>
                    <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                    <div class="comment"><span class="fr">20条评论</span>
                        <div class="comment_star"><span style="width:80%"></span></div>
                    </div>
                </li>
                <li>
                    <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002742.jpg"></a></div>
                    <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                    <div class="comment"><span class="fr">20条评论</span>
                        <div class="comment_star"><span style="width:80%"></span></div>
                    </div>
                </li>
                <li>
                    <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_1226030.jpg"></a></div>
                    <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                    <div class="comment"><span class="fr">20条评论</span>
                        <div class="comment_star"><span style="width:80%"></span></div>
                    </div>
                </li>
                <li>
                    <div class="goods_picture"><a href="javascript:;" title="与仓央嘉措一起修行"><img alt="与仓央嘉措一起修行" src="goods/sml_10002742.jpg"></a></div>
                    <strong><a href="javascript:;" title="与仓央嘉措一起修行">与仓央嘉措一起修行(强烈推荐)</a></strong> <span class="goods_price"><b class="red fb">￥23.00</b> <del>￥36.00</del></span>
                    <div class="comment"><span class="fr">20条评论</span>
                        <div class="comment_star"><span style="width:80%"></span></div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<%@include file="../snippets/footer.jsp"%>
<script src="${serverPrefix}js/shoppingcart.js?${version}"></script>
</body>
</html>
