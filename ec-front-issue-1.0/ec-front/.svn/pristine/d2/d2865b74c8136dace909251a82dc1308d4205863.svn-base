<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品提问</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="details" name="type"/>
</jsp:include>
</head> 
<body class="questionlist">
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
	<input type="hidden" value="${productSale.id}" class="productSaleId"/>
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  ${productSale.product.category.allCategoryName} > ${productSale.effectiveName}</span></div>
    <div class="left_box">
        <div class="left_container yellow_bg">
            <h4 class="fb box_title">咨询商品</h4>
            
            <p class="advisory txt_center"><a title="${productSale.effectiveName}" href="${productSale.product.url }"><img src="${product.imageUrlFor200px }" alt="${productSale.effectiveName}"></a><br><a class="link1" href="${productSale.product.url }">${productSale.effectiveName}</a><br>文轩价：<b class="red fb">￥${productSale.salePrice }</b><br><del>定价：￥${product.listPrice}</del></p>
            <div class="left_com"><span class="fl">评分：</span><div class="com_star fl"><b style="width:${avgRank*20}%;"></b></div>（<b class="link4" >${rankCount}</b>人）</div>
            <div class="left_com">评论数：（${productSale.performance.totalComment}人）</div>
            <p class="txt_center margin10">
            	
     		<a class="buy_but" href="javascript:;" bind="addToCart" data-id="${productSale.id}">购买</a> 	
     		<a class="mini_coll_but" href="#" bind="addToFavorite" data-id="${productSale.id}">收藏</a>
     		</p>          
            <div class="hei10"></div>
        </div>   
    </div>
    <div class="right_box">
        <div class="margin10">
            <h4 class="comment_tit">咨询商品 <a class="haveline" title="${productSale.effectiveName }" href="${productSale.product.url }">${productSale.effectiveName}</a></h4>
            <div class="comment_content">
            <div class="question_content">
            <c:choose>       
            <c:when test="${!empty customerQuestions }">           	
               <c:forEach var="customerQuestion" items="${customerQuestions}"
						varStatus="status">
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
										<c:otherwise><div>${reply.content}</div></c:otherwise>
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
					</c:forEach>  
            </c:when>
            <c:otherwise>
             		<p class="txt_center" style="padding:80px 0px 100px 0px;">暂无提问，请填写下面提问，我们会一个工作日内响应。</p>
            </c:otherwise>  
            </c:choose>
            </div>
            </div>
            <c:if test="${!empty customerQuestions && !empty pagination}">
    				<div class="margin10 fav_pages"><winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page></div>
            </c:if>
        </div>
        <div class="margin10">
           	<h4 class="comment_tit2">提交问答</h4> 
        	<div class="consulting">
        		<table width="100%" border="0" cellspacing="0" cellpadding="0">        
    				<tr>
        				<td width="18%" align="right">您的昵称：</td>
        				<td>
           					<input  type="text" readonly="readonly"  id="username" 
        					<c:choose >
        						<c:when test="${!empty customer}">
       								value=" ${customer.displayName}"
        						</c:when >
        						<c:otherwise>
        							value="游客" 
         						</c:otherwise>
        					</c:choose > /> 
      					<c:if test="${empty customer}"> <b bind="loginmessage">建议先<a class="link1" href="javascript:;" bind ="login">登录</a></b></c:if>       
    				</tr>
    				<tr> 
        				<td align="right">问答标题：</td>
        				<td><input class="unity_w" type="text" name="title" id="title" ></td>
    				</tr>
    				<tr>
        				<td align="right" style="vertical-align:text-top;">问答内容：</td>
        				<td><textarea class="unity_w" name="content" id="content" cols="45" rows="5" ></textarea></td>
    				</tr>
    				<tr>
        				<td align="right">验证码：</td>
        				<td><input type="text" size="10" name="verifyCode" id="verifyCode"/> <img data-lazy="false" src="http://www.winxuan.com/verifyCode?d=<%=System.currentTimeMillis() %>" class="verifyCodeImg"/> <a class="link1" href="javascript:;" bind="changeVerifyCode"> 换一张</a></td>
    				</tr>
    				<tr>
        				<td>&nbsp;</td>
        				<td align="right"><input id ="newquestion" class="consult_but" name="" type="button" value="提交咨询"></td>
    				</tr>
				</table>
        	</div>
		</div>
    </div>
    <div class="hei10"></div>
</div>
<jsp:include page="../../snippets/version2/footer.jsp"></jsp:include>
<script type="text/javascript" src="${serverPrefix}js/product.js?${version}"></script>
</body>
</html>
