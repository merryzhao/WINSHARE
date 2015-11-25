<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品提问</title>
<c:choose>
	<c:when test="${product.sort.id == 11001}">
		<title>商品名称读后感,商品名称书评-文轩网图书评论</title>
		<meta name="description" content="${product.name}读后感及${product.name}书评由文轩网会员发布, 网购商品名称${product.name}就在文轩网"/>
		<meta name="keywords" content="${product.name},${product.name}读后感,${product.name}书评,${produc.author},${product.manufacturer},文轩网,新华文轩,新华书店"/>
	</c:when>
	<c:when test="${product.sort.id == 11002}">
		<title>${product.name}影评,商品名称评论-文轩网音像评论</title>
		<meta name="description" content="${product.name}影评及${product.name}评论由文轩网会员发布, 网购${product.name}就在文轩网"/>
		<meta name="keywords" content="${product.name},${product.name}影评,${product.name}评论,${produc.author},${product.manufacturer},文轩网,新华文轩,新华书店"/>
	</c:when>
	<c:when test="${product.sort.id == 11003}">
		<title>商品名称评论,商品名称怎么样-文轩网百货评论</title>
		<meta name="description" content="${product.name}评论及${product.name}怎么样由文轩网会员发布, 网购${product.name}就在文轩网百货"/>
		<meta name="keywords" content="${product.name},${product.name}评论,${product.name}怎么样,文轩网,新华文轩,文轩网百货"/>
	</c:when>
</c:choose>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="details" name="type"/>
</jsp:include>
</head> 
<body class="questionview">
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
            <h4 class="fb box_title">咨询商品</h4>
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
        <div class="margin10">
            <h4 class="comment_tit2">咨询商品 <a class="haveline" title="${productSale.effectiveName}" href="${productSale.product.url}">${productSale.effectiveName}</a></h4>
            <div class="question_content">
               	<div class="comment_list">
						<div class="user_info fl">
							<ul>
								<li>
									<span class="u_photo">
										<c:choose>
										<c:when test="${not empty customerQuestion.customer }">
											<c:choose>
												<c:when test="${not empty customerQuestion.customer.avatar }">
													<img src="${customerQuestion.customer.avatar }" alt="头像" />
												</c:when>
												<c:otherwise>
													<img src="../../../goods/comment_true.jpg" alt="头像" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<img src="../../../goods/comment_false.jpg" alt="头像" />
										</c:otherwise>
										</c:choose>
									</span>
								</li>
								<li><span class="u_name"><winxuan-string:substr length="8" content="${customerQuestion.customer.protectionName }"></winxuan-string:substr></span></li>
								<li>
									<c:if test="${customerQuestion.customer.grade == 0 }"><span class="u_level gray">普通会员</span></c:if>
									<c:if test="${customerQuestion.customer.grade == 1 }"><span class="u_level orange">银牌会员</span></c:if>
									<c:if test="${customerQuestion.customer.grade == 2 }"><span class="u_level red">金牌会员</span></c:if>
								</li>
							</ul>
						</div>
						<div class="comment_info">
						<div class="reviewer" id="q${customerQuestion.id}">
							<span class="fr"><fmt:formatDate value="${customerQuestion.askTime}"
									pattern="yyyy-MM-dd" /> </span> <a class="fb name_link"
								href="/product/question/${customerQuestion.id}">${customerQuestion.title}</a>
						</div>
						<p>${customerQuestion.content}</p>
						
						<div id="questionReply_${status.count}" class="reply">
						<c:if test="${not empty  customerQuestion.replyList}">
							<c:forEach var="reply" items="${customerQuestion.replyList}">
								<div class="reply_item"><h4>
								<c:choose>
									<c:when test="${not empty reply.replier}">
										<c:choose>
											<c:when test="${reply.replier.protectionName == '文轩客服'}">
												<span class="u_name link1 red">文轩客服</span>
											</c:when>
											<c:otherwise>
												<span class="u_name link1"><winxuan-string:substr length="8" content="${reply.replier.protectionName }"></winxuan-string:substr></span>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>游客</c:otherwise>
									</c:choose>
									 回复说：
									<b style='float:right;'><fmt:formatDate value="${reply.replyTime}" pattern="yyyy-MM-dd" /></b>
									</h4>
									<c:choose>
											<c:when test="${reply.replier.protectionName == '文轩客服'}">
												<div class="red">${reply.content}</div>
											</c:when>
											<c:otherwise>
												<div>${reply.content}</div>
											</c:otherwise>
									</c:choose>
									
								</div>
							</c:forEach>
							
						</c:if>
						</div>
						<p align="right"><button bind="reply" data-field="${status.count }" class="reply_btn">回复</button></p>
								<div id="replyTable_${status.count }" style="display:none" class="replyTable">
								<input type="hidden" value="${customerQuestion.id }" id="customerQuestionId_${status.count }"/>
								<table>
									<tr>
										<td><textarea rows="6" cols="40" bind="replyContent_${status.count }" class="reply_txt"></textarea></td>
									</tr>
									<tr>
										<td><button bind="replySubmit" data-field="${status.count }" class="useful_but">回复</button></td>
									</tr>
								</table>
								</div>
						</div>
					</div>
            </div>
        </div>
    	<div class="hei10"></div>
	</div>
</div>
<jsp:include page="../../snippets/version2/footer.jsp"></jsp:include>
<script type="text/javascript" src="http://v2.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
<script type="text/javascript" src="${serverPrefix}js/product.js?${version}"></script>
</body>
</html>
