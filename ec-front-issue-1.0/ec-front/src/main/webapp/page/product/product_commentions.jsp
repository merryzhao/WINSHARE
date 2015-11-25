<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:choose>
	<c:when test="${product.sort.id == 11001}">
		<title>${product.name}读后感,${product.name}书评-文轩网图书评论</title>
		<meta name="description" content="${product.name}读后感及${product.name}书评由文轩网会员发布, 网购${product.name}就在文轩网"/>
		<meta name="keywords" content="${product.name},${product.name}读后感,${product.name}书评,${produc.author},${product.manufacturer},文轩网,新华文轩,新华书店"/>
	</c:when>
	<c:when test="${product.sort.id == 11002}">
		<title>${product.name}影评,${product.name}评论-文轩网音像评论</title>
		<meta name="description" content="${product.name}影评及${product.name}评论由文轩网会员发布, 网购${product.name}就在文轩网"/>
		<meta name="keywords" content="${product.name},${product.name}影评,${product.name}评论,${produc.author},${product.manufacturer},文轩网,新华文轩,新华书店"/>
	</c:when>
	<c:when test="${product.sort.id == 11003}">
		<title>${product.name}评论,${product.name}怎么样-文轩网百货评论</title>
		<meta name="description" content="${product.name}评论及${product.name}怎么样由文轩网会员发布, 网购${product.name}就在文轩网百货"/>
		<meta name="keywords" content="${product.name},${product.name}评论,${product.name}怎么样,文轩网,新华文轩,文轩网百货"/>
	</c:when>
</c:choose>
<jsp:include page="../snippets/version2/meta.jsp">
	<jsp:param value="details" name="type"/>
</jsp:include>
</head> 
<body>
  <c:if test="${productSale.product.sort.id == 11001}">
	<jsp:include page="../snippets/version2/header.jsp">
		<jsp:param value="book" name="label"/>
	</jsp:include>
</c:if>
<c:if test="${productSale.product.sort.id == 11002}">
	<jsp:include page="../snippets/version2/header.jsp">
		<jsp:param value="media" name="label"/>
	</jsp:include>
</c:if>
<c:if test="${productSale.product.sort.id == 11003}">
	<jsp:include page="../snippets/version2/header.jsp">
		<jsp:param value="mall" name="label"/>
	</jsp:include>
</c:if>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  ${productSale.product.category.allCategoryName} > ${productSale.effectiveName}</span></div>
    <input type="hidden" value="${productSale.id}" class="productSaleId"/>
    <div class="left_box">
        <div class="left_container yellow_bg">
            <h4 class="fb box_title">咨询商品</h4>
            <p class="advisory txt_center"><a title="${productSale.effectiveName}" href="${productSale.product.url }"><img src="${product.smallImageUrl }" alt="book name"></a><br><a class="link1" href="${productSale.product.url }">${productSale.effectiveName}</a><br>文轩价：<b class="red fb">￥${productSale.salePrice }</b><br><del>定价：￥${product.listPrice}</del></p>
            <div class="left_com"><span class="fl">评分：</span><div class="com_star fl"><b style="width:${productSale.avgStarScore*20}%;"></b></div>（<a class="rating_link" href="javascript:;" id="allScoreCount">${rankCount}</a>）</div>
            <div class="left_com">评论数：${commentCount}</div>
            <p class="txt_center margin10"><a class="buy_but" href="javascript:;">购买</a> <a class="coll_but" href="javascript:;">收藏</a></p>
            <div class="hei10"></div>
        </div>
        <div class="left_container">
        <h4 class="fb">第一个评论者</h4>
        <p class="margin10 txt_center commentators"><a title="first commentators" href="javascript:;""><img alt="commentators name" src="images/ads/reviewer.png" /><br>第一个评论者</a></p>
        </div>
    </div>
    <div class="right_box">
        <div class="margin10">
            <h4 class="comment_tit">评论（${commentCount}条）</h4>
           <div class="commnet_score">
                <div class="rating_results">
                    <p>用户评分：<a class="rating_link" href="#" id="avgStarScore">${productSale.avgStarScore}</a>星（共<a class="rating_link" href="javascript:;" id="allScoreCount">${fn:length(productSale.rankList)}</a>人参与）</p>
                    <ul>
                        <li>
                            <div class="com_star"><b style="width:100%;"></b></div>
                            <div class="com_column"><b style="width:${productSale.shareByFiveStar*100}%;" id="shareByFiveStar"></b></div>
                            <b id="commentCountByFiveStar">${productSale.rankCountByFiveStar }</b>人</li>
                        <li>
                            <div class="com_star"><b style="width:80%;"></b></div>
                            <div class="com_column"><b style="width:${productSale.shareByFourStar*100}%;" id="shareByFourStar"></b></div>
                            <b id="commentCountByFourStar">${productSale.rankCountByFourStar }</b>人</li>
                        <li>
                            <div class="com_star"><b style="width:60%;"></b></div>
                            <div class="com_column"><b style="width:${productSale.shareByThreeStar*100}%;" id="shareByThreeStar"></b></div>
                            <b id="commentCountByThreeStar"> ${productSale.rankCountByThreeStar }</b>人</li>
                        <li>
                            <div class="com_star"><b style="width:40%;"></b></div>
                            <div class="com_column"><b style="width:${productSale.shareByTwoStar*100}%;" id="shareByTwoStar"></b></div>
                             <b id="commentCountByTwoStar">${productSale.rankCountByTwoStar }</b>人</li>
                        <li>
                            <div class="com_star"><b style="width:20%;"></b></div>
                            <div class="com_column"><b style="width:${productSale.shareByOneStar*100}%;" id="shareByOneStar"></b></div>
                             <b id="commentCountByOneStar">${productSale.rankCountByOneStar }</b>人</li>
                    </ul>
                </div>
                <div class="do_score">
                    <ul>
                        <li><div class="do_score"><span class="fb f14">我来评分：</span><b class="star1"></b><b class="star2"></b><b class="star3"></b><b class="star4"></b><b class="star5"></b><label bind="scoring"'>请点击星星进行评分</label></div></li>
                        <li><a class="w_review" href="/product/${productSale.id }/newcomment">我来写评论</a><a class="link4" href="http://www.winxuan.com/help/score.html">写点评 送积分 中奖券&gt;&gt;</a></li>
                    </ul>
                </div>
            </div>
            <div class="share_box">分享到：<a href="javascript:void(0)" id="sina" title="推荐到新浪微博">sina</a>新浪微波 <a href="javascript:void(0)" id="qzone" title="推荐到腾讯微博">qzone</a>腾讯微博 <a href="javascript:void(0)" id="renren" title="推荐到人人">renren</a>人人网 <a href="javascript:void(0)" id="kaixing" title="推荐到开心网">kaixing</a>开心网 <a href="javascript:void(0)" id="douban" title="推荐到豆瓣">douban</a>豆瓣网 </div>
             <c:choose>
             <c:when test="${!empty customerComments}">
            <div class="comment_content">  
                <c:forEach var="customerComment" items="${customerComments}" varStatus="status">
                	 <div class="reviewer"><span class="fr"><c:choose><c:when test="${empty customerComment.customer }">游客</c:when><c:otherwise>${customerComment.customer.name}</c:otherwise></c:choose> 发表于<fmt:formatDate value="${customerComment.commentTime}" pattern="yyyy-MM-dd"/></span>
                	   <div class="com_star fl"><b style="width:${customerComment.rank.rank*20}%;"></b></div>
                	   <a class="fb name_link">${customerComment.title}</a></div>
                	     <p>
                	     	${customerComment.content}
                	     </p>
                	      <p class="txt_right">此评价对我：<a class="useful_but" href="javascript:;" bind="useful" param="${customerComment.id}">有用（<b class="useful">${customerComment.usefulCount}</b>）</a><a class="useful_but" href="javascript:;" bind="useless" param="${customerComment.id}">没用（<b class="useless">${customerComment.uselessCount}</b>）</a></p>
                </c:forEach> 
                
            </div>
            <c:if test="${!empty pagination}">
    				<div class="margin10 fav_pages"><winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page></div>
            </c:if>
            </c:when>
            <c:otherwise>
            		还没有人进行过评论。
            </c:otherwise>
            </c:choose>
        </div>
       
    </div>
    <div class="hei10"></div>
</div>
<jsp:include page="../snippets/version2/footer.jsp"></jsp:include>
<script type="text/javascript" src="${serverPrefix}/js/product.js${version}"></script>
</body>
</html>
