<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${product.name}读后感,${product.name}书评-文轩网图书评论</title>
<meta name="description" content="${product.name}读后感及${product.name}书评由文轩网会员发布, ${product.name}名称${product.name}就在文轩网"/>
<meta name="keywords" content="${product.name},${product.name}读后感,${product.name}书评,${produc.author},${product.manufacturer},文轩网,新华文轩,新华书店"/>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="details" name="type"/>
</jsp:include>
</head> 
<body class="commentlist">
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  ${productSale.product.category.allCategoryName} &gt; ${productSale.effectiveName}</span></div>
    <input type="hidden" value="${productSale.id}" class="productSaleId"/>
    <div class="left_box">
        <div class="left_container yellow_bg">
            <h4 class="fb box_title" style="text-align:center;">商品信息</h4>
            <p class="advisory txt_center"><a title="${productSale.effectiveName}" href="${productSale.product.url }"><img src="${product.imageUrlFor200px }" alt="${productSale.effectiveName}"></a><br><a class="link1" href="${productSale.product.url }">${productSale.effectiveName}</a><br>文轩价：<b class="red fb">￥${productSale.salePrice }</b><br><del>定价：￥${product.listPrice}</del></p>
            <div class="left_com"><span class="fl">评分：</span><div class="com_star fl"><b style="width:${avgRank*20}%;"></b></div>（<b class="rating_link" id="allScoreCount">${rankCount}</b>人）</div>
            <div class="left_com">评论数：${productSale.performance.totalComment}人</div>
            <p class="txt_center margin10">	
     		<a class="buy_but" href="javascript:;" bind="addToCart" data-id="${productSale.id}">购买</a> 		
			<a class="mini_coll_but" href="javascript:;" bind="addToFavorite" data-id="${productSale.id}">收藏</a></p>
            <div class="hei10"></div>
        </div>
        	<div class="left_container">
        		<h4 class="fb">第一个评论者</h4>
        		<p class="margin10 txt_center commentators">
        		<b title="first commentators" >
        		<c:choose>
        		<c:when test="${!empty firstComment}">
        			<c:choose>
        			<c:when test="${!empty firstComment.customer }"> 
        				<img alt="commentators name" src="${firstComment.customer.avatar == null || firstComment.customer.avatar == '' ? 'http://static.winxuancdn.com/goods/account_photo.jpg' : firstComment.customer.avatar}" />	
        				<br>${firstComment.customer.protectionName}
        			</c:when> 
        			<c:otherwise>
        				<img alt="commentators name" src="http://static.winxuancdn.com/goods/account_photo.jpg" />
        				<br>游客
        			</c:otherwise>
        			</c:choose>
        		</c:when>
        		<c:otherwise>	
        			<img alt="commentators name" src="http://static.winxuancdn.com/goods/account_photo.jpg" />
        			<br>暂无
        		</c:otherwise>
        		</c:choose>
        		</b>
        		</p>
        	</div>
    </div>
    <div class="right_box">
        <div class="margin10">
           <ul class="blend_tab">
           <c:choose>
           	<c:when test='${showType=="tmall"}'>
           	<li><a href="list">文轩评论(${commentCount})</a></li>
           	<li class="selected">天猫评论(${tmallCommentCount})</li>
           	</c:when>
           	<c:otherwise>
           	 <li class="selected">文轩评论(${commentCount})</li>
           	 <li><a href="tmall">天猫评论(${tmallCommentCount})</a></li>
           	</c:otherwise>
           </c:choose>
           </ul>
		   <c:choose>
		   	<c:when test='${showType=="tmall"}'>
		   		<div id="comments-tmall">
		   		<div class="comment_content">
	           	<c:choose>
	           		<c:when test="${!empty tmallComments}">
	           		<p>以下评论来自新华文轩网络书店－天猫店</p>
	           			<c:forEach var="comment" items="${tmallComments}">
							<div class="reviewer"><span class="fr">${comment.nickName} 发表于<fmt:formatDate value="${comment.commentTime}" pattern="yyyy-MM-dd"/></span></div>
							<p>${comment.content}</p>           			
	           			</c:forEach>
	        		<c:if test="${not empty pagination}">
		    			<div class="margin10 fav_pages"><winxuan-page:page pagination="${pagination}" bodyStyle="javascript"></winxuan-page:page></div>
		            </c:if>
	           		</c:when>
	           		<c:otherwise>
	           		<p>暂时还没有评论哦~~</p>
	           		</c:otherwise>
	           	</c:choose>
	           	</div>
	           </div>
		   	</c:when>
		   	<c:otherwise>
		   		<div id="comments-winxuan">
           	<div class="commnet_score">
                <div class="rating_results">
                    <p>用户评分：<b id="avgStarScore">${avgRank}</b>星（共<b  id="allScoreCount">${rankCount}</b>人参与）</p>
                    <ul>
                        <li>
                            <div class="com_star"><b style="width:100%;"></b></div>
                            <div class="com_column"><b style="width:${rankRate.shareByFiveStar*100}%;" id="shareByFiveStar"></b></div>
                            <b id="commentCountByFiveStar">${rankRate.rankCountByFiveStar }</b>人</li>
                        <li>
                            <div class="com_star"><b style="width:80%;"></b></div>
                            <div class="com_column"><b style="width:${rankRate.shareByFourStar*100}%;" id="shareByFourStar"></b></div>
                            <b id="commentCountByFourStar">${rankRate.rankCountByFourStar }</b>人</li>
                        <li>
                            <div class="com_star"><b style="width:60%;"></b></div>
                            <div class="com_column"><b style="width:${rankRate.shareByThreeStar*100}%;" id="shareByThreeStar"></b></div>
                            <b id="commentCountByThreeStar"> ${rankRate.rankCountByThreeStar }</b>人</li>
                        <li>
                            <div class="com_star"><b style="width:40%;"></b></div>
                            <div class="com_column"><b style="width:${rankRate.shareByTwoStar*100}%;" id="shareByTwoStar"></b></div>
                             <b id="commentCountByTwoStar">${rankRate.rankCountByTwoStar }</b>人</li>
                        <li>
                            <div class="com_star"><b style="width:20%;"></b></div>
                            <div class="com_column"><b style="width:${rankRate.shareByOneStar*100}%;" id="shareByOneStar"></b></div>
                             <b id="commentCountByOneStar">${rankRate.rankCountByOneStar }</b>人</li>
                    </ul>
                </div>
                <div class="do_score">
                    <ul>
                    	<%-- <li>
							<c:if test="${not empty customer }">
								<p class="comm_account">用户：<span class="link1">${customer.name}</span>已登录<a href="#" target="_bland" class="link1">更换账户&gt;&gt;</a></p>
							</c:if>
						</li> --%>
                        <li><div class="do_score" id="do_score" param="1"><span class="fb f14">我来评分：</span><b class="star1"></b><b class="star2"></b><b class="star3"></b><b class="star4"></b><b class="star5"></b><label bind="scoring"'>请点击星星进行评分</label></div></li>
                        <li><a class="w_review" href="/product/${productSale.id }/comment/_new">我来写评论</a><a class="link4" href="http://www.winxuan.com/help/score.html">写点评 送积分 中奖券&gt;&gt;</a></li>
                    </ul>
                </div>
            </div>
            <div class="share_box">
            	<div id="ckepop" style="height: 20px">
            		<span class="jiathis_txt">分享到：</span> 
					<a class="jiathis_button_qzone"></a>
					<a class="jiathis_button_tsina"></a>
					<a class="jiathis_button_tqq"></a>
					<a class="jiathis_button_renren"></a>
					<a class="jiathis_button_kaixin001"></a>
					<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
					<a class="jiathis_counter_style"></a>
                  </div>
             </div>
             <c:choose>
             <c:when test="${not empty customerComments}">
             <div class="comment_content">  
                <c:forEach var="customerComment" items="${customerComments}" varStatus="status">
                	<div class="comment_list">
						<div class="user_info fl">
							<ul>
								<li>
									<span class="u_photo">
										<c:choose>
										<c:when test="${not empty customerComment[0].customer }">
											<c:choose>
												<c:when test="${not empty customerComment[0].customer.avatar }">
													<img src="${customerComment[0].customer.avatar }" alt="头像" />
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
								<li>
									<span class="u_name">
										<c:choose>
											<c:when test="${empty customerComment[0].customer }">游客</c:when>
											<c:otherwise><winxuan-string:substr length="8" content="${customerComment[0].customer.protectionName}"></winxuan-string:substr></c:otherwise>
										</c:choose>
									</span>
								</li>
								<li>
									<c:if test="${customerComment[0].customer.grade == 0 }"><span class="u_level gray">普通会员</span></c:if>
									<c:if test="${customerComment[0].customer.grade == 1 }"><span class="u_level orange">银牌会员</span></c:if>
									<c:if test="${customerComment[0].customer.grade == 2 }"><span class="u_level red">金牌会员</span></c:if>
								</li>
							</ul>
						</div>
						<div class="comment_info">
							<div class="reviewer" id="c${customerComment[0].id}">
								<span class="fr"><fmt:formatDate value="${customerComment[0].commentTime}" pattern="yyyy-MM-dd" /></span>
								<div class="com_star fl">
									<b style="width:${customerComment[0].rank.rank*20}%;"></b>
								</div>
								<a class="fb name_link"
									href="/product/comment/${customerComment[0].id}"
									title="${customerComment[0].title}" target="_blank">${customerComment[0].title}
								</a>
							</div>                     
						<c:if test="${not empty customerComment[1]}">
							<div class="quote">
                	     	<p class="qt_title">引用
                	     	<c:choose>
                	     		<c:when test="${not empty customerComment[1].customer }">
                	     			<span class="u_name link1">
                	     				<winxuan-string:substr length="8" content="${customerComment[1].customer.protectionName }"></winxuan-string:substr>
                	     			</span>
                	     		</c:when>
                	     		<c:otherwise>
                	     			<font color="grey">游客</font>
                	     		</c:otherwise>
                	     	</c:choose>
                	     	 的评论:${customerComment[1].title }</p><p>${customerComment[1].content }</p>
                	     	 </div>
                	     </c:if>
						<p>
						${customerComment[0].content}
						</p>
						<c:if test="${not empty  customerComment[0].replyList}">
							<c:forEach var="reply" items="${customerComment[0].replyList}">
								<div class="reply_item">
									<p class="reply">${reply.content}</p>
									<p class="response_time txt_right">
										<span class="red">文轩客服</span>
										回复于
										<fmt:formatDate value="${reply.replyTime}" pattern="yyyy-MM-dd" />
									</p>
								</div>
							</c:forEach>
						</c:if>
						<div class="com_btn">
						<p class="fl"><a class="useful_but" href="/product/${productSale.id }/comment/_new?quoteCommentId=${customerComment[0].id}">引用</a></p>
						<p class="txt_right">
							此评价对我：
							<c:if
								test="${!(not empty cookie.v.value && not empty customerComment[0].customer && cookie.v.value == customerComment[0].customer.protectionName)}">
								<a class="useful_but" href="javascript:;" bind="useful"
									param="${customerComment[0].id}">有用（<b class="useful">${customerComment[0].usefulCount}</b>）</a>
								<a class="useful_but" href="javascript:;" bind="useless"
									param="${customerComment[0].id}">没用（<b class="useless">${customerComment[0].uselessCount}</b>）</a>
							</c:if>
							<c:if
								test="${not empty cookie.v.value && not empty customerComment[0].customer && cookie.v.value == customerComment[0].customer.displayName}">
								<a class="useful_but" href="javascript:;">有用（<b
									class="useful">${customerComment[0].usefulCount}</b>）</a>
								<a class="useful_but" href="javascript:;">没用（<b
									class="useless">${customerComment[0].uselessCount}</b>）</a>
							</c:if>
						</p>
						</div>
					</div>
					</div>
                </c:forEach> 
            </div>
            <c:if test="${not empty pagination}">
    			<div class="margin10 fav_pages"><winxuan-page:page pagination="${pagination}" bodyStyle="javascript"></winxuan-page:page></div>
            </c:if>
            </c:when>
            <c:otherwise>
            	<p class="txt_center" style="padding:80px 0px 100px 0px;">暂无评论，想成为第一个评论员吗？有2倍积分哦，<a class="link1" href="/product/${productSale.id }/comment/_new" >立即发表评论>></a></p>
            </c:otherwise>
            </c:choose>
           </div>
		   	</c:otherwise>
		   </c:choose>
        </div>
    </div>
    <div class="hei10"></div>
</div>
<jsp:include page="../../snippets/version2/footer.jsp"></jsp:include>
<script type="text/javascript" src="http://v2.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
<script type="text/javascript" src="${serverPrefix}js/product.js?${version}"></script>
</body>
</html>