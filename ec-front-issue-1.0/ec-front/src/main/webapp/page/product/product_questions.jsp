<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品提问</title>
<jsp:include page="../snippets/version2/meta.jsp">
	<jsp:param value="details" name="type"/>
</jsp:include>
</head> 
<body>
<jsp:include page="../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
	<input type="hidden" value="${productSale.id}" class="productSaleId"/>
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  ${productSale.product.category.allCategoryName} > ${productSale.effectiveName}</span></div>
    <div class="left_box">
        <div class="left_container yellow_bg">
            <h4 class="fb box_title">咨询商品</h4>
            <p class="advisory txt_center"><a title="${productSale.effectiveName}" href="${productSale.product.url }"><img src="${product.smallImageUrl }" alt="${productSale.effectiveName}"></a><br><a class="link1" href="${productSale.product.url }">${productSale.effectiveName }</a><br>文轩价：<b class="red fb">￥${productSale.salePrice }</b><br><del>定价：￥${product.listPrice}</del></p>
            <div class="left_com"><span class="fl">评分：</span><div class="com_star fl"><b style="width:${productSale.avgStarScore*20}%;"></b></div>（<a class="link4" href="javascript:;">${rankCount}</a>）</div>
            <div class="left_com">评论数：（${commentCount}）</div>
            <p class="txt_center margin10"><a class="buy_but" href="javascript:;">购买</a> <a class="coll_but" href="javascript:;">收藏</a></p>
            <div class="hei10"></div>
        </div>
        <p class="left_ads"><a title="人气好书大盘点" href="javascript:;"><img src="/images/ads/ads02.jpg" width="200" alt="人气好书大盘点" /></a></p>
        <p class="left_ads"><a title="企业团购" href="javascript:;"><img src="/images/ads/ads03.jpg" alt="企业团购" width="200" /></a></p>
    </div>
    <div class="right_box">
        <div class="margin10">
            <h4 class="comment_tit">咨询商品 <a class="haveline" title="${productSale.product.name }" href="${productSale.product.url }">${productSale.product.name }</a></h4>
            <c:choose>
            <c:when test="${!empty customerQuestions }">
            <div class="comment_content">
            	<div class="question_content">
               <c:forEach var="customerQuestion" items="${customerQuestions }" varStatus="status">
                		 <div class="reviewer"><span class="fr">${customerQuestion.customer.name}  发表于 <fmt:formatDate value="${customerQuestion.askTime}" pattern="yyyy-MM-dd"/> </span> <a class="fb name_link"><c:out value="${customerQuestion.title}"/></a></div>
                		  <p><c:out value="${customerQuestion.content}"/></p>
                		  <c:choose>
                		  		<c:when test="${!empty customerQuestion.replyList}">
                		  			  <p class="reply"><c:out value="${customerQuestion.defaultReply.content}"/></p>
               		 				  <p class="response_time txt_right"><c:choose><c:when test="${!empty customerQuestion.defaultReply.replier}">${customerQuestion.defaultReply.replier.name}</c:when><c:otherwise>游客</c:otherwise></c:choose> 发表于<fmt:formatDate value="${customerQuestion.defaultReply.replyTime}" pattern="yyyy-MM-dd"/></p>
                		  		      <p class="gray"><a class="review_link" href="javascript:;">展开全部回复（${fn:length(customerQuestion.replyList)}）</a>| <a class="review_link" href="javascript:;">我来回复</a></p>
                		  		</c:when>
                		  		<c:otherwise><p class="txt_center"><b class="link3">暂无回复！</b> <a class="link2" href="javascript:;">我来回复</a></p></c:otherwise>
                		  </c:choose>
                </c:forEach>
                </div>
            </div>
            <c:if test="${!empty pagination}">
    				<div class="margin10 fav_pages"><winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page></div>
            </c:if>
            </c:when>
            <c:otherwise>
             		  还没有人进行过提问。。。。
            </c:otherwise>
            </c:choose>
        </div>
        
        <h4 class="comment_tit">提交咨询</h4> 
        <div class="solid1"></div>
        <div class="margin10">
            <h4 class="comment_tit2">提交咨询</h4>
				<div class="consulting">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="18%" align="right">您的昵称：</td>
							<td><input type="text" readonly="readonly" id="username"
								<c:choose >
        							<c:when test="${!empty customer}"> value=" ${customer.name}"</c:when >
        							<c:otherwise>value="游客"</c:otherwise>
    						    </c:choose > />
								<c:if test="${empty customer}">
									<b bind="loginmessage">建议先<a class="link1"href="javascript:;" bind='login'>登录</a>
									</b>
								</c:if>
						</tr>
						<tr>
							<td align="right">咨询标题：</td>
							<td><input class="unity_w" type="text" name="title" id="title">
							</td>
						</tr>
						<tr>
							<td align="right" style="vertical-align: text-top;">咨询内容：</td>
							<td><textarea class="unity_w" name="content" id="content" cols="45" rows="5"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">验证码：</td>
							<td><input type="text" size="10" name="verifyCode"
								id="verifyCode" /> <img data-lazy="false" src="http://www.winxuan.com/verifyCode?d=<%=System.currentTimeMillis()%>" class="verifyCodeImg"/> 看不清
								<a class="link1" href="javascript:;" bind="changeVerifyCode"> 换一张</a>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="right"><input id ="newquestion" class="consult_but" name="" type="button" value="提交你的咨询">
							</td>
						</tr>
					</table>
				</div>
			</div>
    </div>
    <div class="hei10"></div>
</div>
<jsp:include page="../snippets/version2/footer.jsp"></jsp:include>
<script type="text/javascript" src="${serverPrefix}/js/product.js?${version}"></script>
</body>
</html>
