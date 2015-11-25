<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:choose>
	<c:when test="${product.sort.id == 11001}">
		<title>${product.name}读后感,${product.name}书评-文轩网图书评论</title>
		<meta name="description" content="${product.name}读后感及${product.name}书评由文轩网<c:choose><c:when test="${empty comment.customer }">游客</c:when><c:otherwise>会员${comment.customer.displayName}</c:otherwise></c:choose>发布, 网购${product.name}就在文轩网"/>
		<meta name="keywords" content="${product.name},${product.name}读后感,${product.name}书评,${produc.author},${product.manufacturer},文轩网,新华文轩,新华书店"/>
	</c:when>
	<c:when test="${product.sort.id == 11002}">
		<title>${product.name}影评,${product.name}评论-文轩网音像评论</title>
		<meta name="description" content="${product.name}影评及${product.name}评论由文轩网<c:choose><c:when test="${empty comment.customer }">游客</c:when><c:otherwise>会员${comment.customer.displayName}</c:otherwise></c:choose>发布, 网购${product.name}就在文轩网"/>
		<meta name="keywords" content="${product.name},${product.name}影评,${product.name}评论,${produc.author},${product.manufacturer},文轩网,新华文轩,新华书店"/>
	</c:when>
	<c:when test="${product.sort.id == 11003}">
		<title>${product.name}评论,${product.name}怎么样-文轩网百货评论</title>
		<meta name="description" content="${product.name}评论及${product.name}怎么样由文轩网<c:choose><c:when test="${empty comment.customer }">游客</c:when><c:otherwise>会员${comment.customer.displayName}</c:otherwise></c:choose>发布, 网购${product.name}就在文轩网百货"/>
		<meta name="keywords" content="${product.name},${product.name}评论,${product.name}怎么样,文轩网,新华文轩,文轩网百货"/>
	</c:when>
</c:choose>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="details" name="type"/>
</jsp:include>
</head> 
<body class="commentview">

  <c:if test="${productSale.product.sort.id == 11001}">
	<jsp:include page="../../snippets/version2/header.jsp">
		<jsp:param value="book" name="label"/>
	</jsp:include>
</c:if>
<c:if test="${productSale.product.sort.id == 11002}">
	<jsp:include page="../../snippets/version2/header.jsp">
		<jsp:param value="media" name="label"/>
	</jsp:include>
</c:if>
<c:if test="${productSale.product.sort.id == 11003}">
	<jsp:include page="../../snippets/version2/header.jsp">
		<jsp:param value="mall" name="label"/>
	</jsp:include>
</c:if>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  ${productSale.product.category.allCategoryName} > ${productSale.effectiveName}</span></div>
    <input type="hidden" value="${productSale.id}" class="productSaleId"/>
    <div class="left_box">
        <div class="left_container yellow_bg">
            <h4 class="fb box_title">评论商品</h4>
            <p class="advisory txt_center"><a title="${productSale.effectiveName}" href="${productSale.product.url }" class="goodspic"><img src="${product.imageUrlFor200px }" alt="${productSale.effectiveName}"></a><br><a class="link1" href="${productSale.product.url }">${productSale.effectiveName}</a><br>文轩价：<b class="red fb">￥${productSale.salePrice }</b><br><del>定价：￥${product.listPrice}</del></p>
            <div class="left_com"><span class="fl">评分：</span><div class="com_star fl"><b style="width:${avgRank*20}%;"></b></div>（<b class="rating_link" id="allScoreCount">${rankCount}</b>人）</div>
            <div class="left_com">评论数：${productSale.performance.totalComment}人</div>
			<p class="txt_center margin10">
     			<a class="buy_but" href="javascript:;" bind="addToCart" data-id="${productSale.id}">购买</a> 		
				<a class="mini_coll_but" href="javascript:;" bind="addToFavorite" data-id="${productSale.id}">收藏</a>
			</p> 
			<div class="hei10"></div>
        </div>    
    </div>   
	 <div class="right_box">
    <table class="signal_com margin10" width="100%" border="0">
      <tr>
        <td class="reviewer_pic"><a title="first commentators" >
        <img alt="commentators name" src="${comment.customer.avatar == null || comment.customer.avatar == '' ? 'http://static.winxuancdn.com/goods/account_photo.jpg' : comment.customer.avatar}" />
        	<br><c:choose><c:when test="${empty comment.customer }">游客</c:when><c:otherwise><winxuan-string:substr length="8" content="${comment.customer.displayName}"></winxuan-string:substr></c:otherwise></c:choose></a></td>    
        <td>
        <div class="re_title">
        	<h1><winxuan-string:substr length="26" content="${comment.title}"></winxuan-string:substr></h1>
        	<span class="fr"><c:choose><c:when test="${empty comment.customer }">游客</c:when><c:otherwise>${comment.customer.protectionName}</c:otherwise></c:choose> 发表于 <fmt:formatDate value="${comment.commentTime}" pattern="yyyy-MM-dd"/></span><div class="com_star fl"><b style="width:${comment.rank.rank * 20}%;"></b></div>
         </div>
 		 <p>${comment.content}</p>
         <c:if test="${!empty  comment.replyList}">  
               <c:forEach var="reply" items="${comment.replyList}">        		  	
               		 <div class="reply_item">
               		 	<p class="reply">${reply.content}</p>
               		 	<p class="response_time txt_right">
               		 		<span class="red">文轩客服</span>
					  		回复于
					  		<fmt:formatDate value="${reply.replyTime}" pattern="yyyy-MM-dd"/>
					  	</p>
               		 </div>
               </c:forEach>	
        </c:if>
         <p class="txt_right">这条评论对你有用吗？
				<c:if test="${!(not empty cookie.v.value && not empty comment.customer && cookie.v.value == comment.customer.displayName)}">
	            	<a class="useful_but" href="javascript:;" bind="useful" param="${comment.id}">有用（<b class="useful">${comment.usefulCount}</b>）</a>
	           		 <a class="useful_but" href="javascript:;" bind="useless" param="${comment.id}">没用（<b class="useless">${comment.uselessCount}</b>）</a>
                </c:if>
                <c:if test="${not empty cookie.v.value && not empty comment.customer && cookie.v.value == comment.customer.displayName}">
	                <a class="useful_but" href="javascript:;">有用（<b class="useful">${comment.usefulCount}</b>）</a>
	                <a class="useful_but" href="javascript:;">没用（<b class="useless">${comment.uselessCount}</b>）</a>
                </c:if>		 
         </p>
        </td>
      </tr>
    </table>
    
</div>
    <div class="hei10"></div>
</div>
<jsp:include page="../../snippets/version2/footer.jsp"></jsp:include>
<script type="text/javascript" src="http://v2.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
<script type="text/javascript" src="${serverPrefix}js/product.js?${version}"></script>
</body>
</html>
