<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<%-- <div class="prom_detail">
            <h3>文轩网优惠活动</h3>
            <ul>
                <c:forEach var="activityShow" items="${activityShows}" varStatus="status">
                 <li><a class="link4" href="${activityShow.url}" title="${activityShow.title}"> ${activityShow.title}</a></li>   
              </c:forEach>
            </ul>
            <div class="hei1"></div>
        </div> --%>


<ul class="infor_tab">
	<li class="current_info" target="tabcontent01" data-ga="0">商品详情</li>
	<li target="tabcontent02" data-ga="1">商品评论（${commentCount+tmallCommentCount}条）</li>
	<li target="tabcontent03" data-ga="2">商品咨询（<b class="questionCount"
		style="font-weight: 700;">${questionCount}</b>条）</li>
</ul>
<!-- 精品专区入口 -->
<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_ID_600234">
    <c:import url="/fragment/600234">
       <c:param value="${cacheKey}" name="cacheKey" />
    </c:import>
</cache:fragmentPageCache>
<!-- 精品专区入口 end -->
<div class="tabcontent01"> 
	<c:set var="descriptions" value="${product.descriptionList }"></c:set>
	<c:forEach var="description" items="${descriptions}">
		<c:if
			test="${!empty description.content && description.content!='null' && description.name!='摘要'}">
			<div class="moreinfo_box">
				<h2>
					<b class="info_title">${description.name}</b>
				</h2>
				<div class="morereader">
					<div class="content">
						<pre>${description.content}</pre>
					</div>
					<c:choose>
						<c:when test="${description.productMeta.id == 12}">
							<a class="read">显示全部目录&gt;&gt;</a>
							<a class="hide">隐藏全部目录&gt;&gt;</a>
						</c:when>
						<c:when test="${description.productMeta.id == 43 }">
							<a class="read">显示全部书摘&gt;&gt;</a>
							<a class="hide">隐藏全部书摘&gt;&gt;</a>
						</c:when>
						<c:otherwise>
							<a class="read">显示全部&gt;&gt;</a>
							<a class="hide">隐藏全部&gt;&gt;</a>
						</c:otherwise>
					</c:choose>
				</div>
				<c:if test="${description.productMeta.id == 9 }">
					<c:if test="${!empty newSaleList}">
						<dl class="hot_new">
							<dt>
								<a class="link4" href="#">热门新书</a>
							</dt>
							<c:forEach var="newSale" items="${newSaleList}"
								varStatus="status">
								<dd>
									<a href="${newSale.product.url }"
										title="${newSale.product.name }"><winxuan-string:substr
											length="25" content="${newSale.product.name }"></winxuan-string:substr>
									</a>
								</dd>
							</c:forEach>
							<dt class="hei1"></dt>
						</dl>
					</c:if>
				</c:if>
				<c:if test="${description.productMeta.id == 10 }">
					<p style="display: none;">
						此书可以试读，请<a class="link3" href="javascript:;">点击这里&gt;&gt;</a>
					</p>
				</c:if>
			</div>
		</c:if>
	</c:forEach>

	<!-- 书摘插图 -->
	<c:set var="metaimg" value="false"></c:set>
	<c:forEach items="${product.imageList}" var="img">
		<c:if test="${img.type == 5}">
			<c:set var="metaimg" value="true" />
		</c:if>
	</c:forEach>
	<c:if test="${metaimg}">
		<div class="moreinfo_box">
			<h2>
				<b class="info_title">书摘插图</b>
			</h2>
			<div class="morereader">
				<div class="content">
					<c:forEach items="${product.imageList}" var="img">
						<c:if test="${img.type == 5}">
							<img src="${img.url}">
							<br>
						</c:if>
					</c:forEach>
				</div>
				<a class="read">显示全部&gt;&gt;</a> <a class="hide">隐藏全部&gt;&gt;</a>
			</div>
		</div>
	</c:if>
	<!-- END书摘插图 -->
</div>
<div class="tabcontent02">
	<h4 class="comment_tit">商品评论（${commentCount+tmallCommentCount}条）</h4>
	<ul class="inner-blend-tab">
		<li class="selected" index="0" data-ga="3">文轩评论(${commentCount})</li>
		<li index="1" data-ga="4">淘宝电子书评论(${tmallCommentCount})</li>
	</ul>
	<script>
		seajs
				.use(
						"jQuery",
						function($) {
							$(function() {
								var items = $("#comments-winxuan,#comments-tmall"), menus = $("ul.inner-blend-tab li");
								menus.click(function() {
									var el = $(this);
									if (!el.hasClass("selected")) {
										menus.removeClass("selected");
										el.addClass("selected");
										items.hide();
										$(items[el.attr("index")]).show();
									}
								});
							});
						});
	</script>
	<div id="comments-winxuan">
		<div class="commnet_score">
			<div class="rating_results">
				<p>
					用户评分：<a class="rating_link" href="#" id="avgStarScore">${avgRank}</a>星（共<a
						class="rating_link" href="#" id="allScoreCount">${rankCount}</a>人参与）
				</p>
				<ul>
					<li>
						<div class="com_star">
							<b style="width: 100%;"></b>
						</div>
						<div class="com_column">
							<b style="width:${rankRate.shareByFiveStar*100}%;"
								id="shareByFiveStar"></b>
						</div> <b id="commentCountByFiveStar">${rankRate.rankCountByFiveStar
							}</b>人</li>
					<li>
						<div class="com_star">
							<b style="width: 80%;"></b>
						</div>
						<div class="com_column">
							<b style="width:${rankRate.shareByFourStar*100}%;"
								id="shareByFourStar"></b>
						</div> <b id="commentCountByFourStar">${rankRate.rankCountByFourStar
							}</b>人</li>
					<li>
						<div class="com_star">
							<b style="width: 60%;"></b>
						</div>
						<div class="com_column">
							<b style="width:${rankRate.shareByThreeStar*100}%;"
								id="shareByThreeStar"></b>
						</div> <b id="commentCountByThreeStar">
							${rankRate.rankCountByThreeStar }</b>人</li>
					<li>
						<div class="com_star">
							<b style="width: 40%;"></b>
						</div>
						<div class="com_column">
							<b style="width:${rankRate.shareByTwoStar*100}%;"
								id="shareByTwoStar"></b>
						</div> <b id="commentCountByTwoStar">${rankRate.rankCountByTwoStar
							}</b>人</li>
					<li>
						<div class="com_star">
							<b style="width: 20%;"></b>
						</div>
						<div class="com_column">
							<b style="width:${rankRate.shareByOneStar*100}%;"
								id="shareByOneStar"></b>
						</div> <b id="commentCountByOneStar">${rankRate.rankCountByOneStar
							}</b>人</li>
				</ul>
			</div>
			<div class="do_score">
				<ul>
					<li id="login_info">
						<c:if test="${not empty customer }">
							<p class="comm_account">用户：<span class="link1" id="current_user">${customer.protectionName}</span>已登录 
							<a class="link1" href="javascript:;" bind="login">更换账户&gt;&gt;</a></p>
						</c:if> 
					</li>
					<li><div class="do_score" id="do_score" param="1">
							<span class="fb">我来评分：</span><b class="star1"></b><b
								class="star2"></b><b class="star3"></b><b class="star4"></b><b
								class="star5"></b><label bind="scoring"></label>
						</div>
					</li>
					<li><a class="w_review f14"
						href="/product/${productSale.id }/comment/_new">我来写评论</a>写点评 <a
						target="_bland" class="link1"
						href="http://www.winxuan.com/help/score.html">送积分&gt;&gt;</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="comment_content">
			<c:choose>
				<c:when test="${not empty customerComments }">
					<p class="txt_center">
						以下仅展示部分评论，查看所有<span class="red fb">${commentCount}</span>评论请<a class="link1"
							href="/product/${productSale.id}/comment/list">点击这里&gt;&gt;</a>
					</p>
					<c:forEach var="customerComment" items="${customerComments}"
						varStatus="status">
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
													<img src="../../goods/comment_true.jpg" alt="头像" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<img src="../../goods/comment_false.jpg" alt="头像" />
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
							<span class="fr"><fmt:formatDate value="${customerComment[0].commentTime}"
									pattern="yyyy-MM-dd" />
							</span>
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
                	     			<span class="u_name link1"><winxuan-string:substr length="8" content="${customerComment[1].customer.protectionName }"></winxuan-string:substr></span>
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
						<c:if test="${!empty  customerComment[0].replyList}">
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
					<h5 class="view_all">
						<a href="/product/${productSale.id}/comment/list">查看所有<span class="red fb">${commentCount}</span>条评论&gt;&gt;</a>
					</h5>
				</c:when>
				<c:otherwise>
					<p class="txt_center" tyle="height:100px;">
						暂无评论，想成为第一个评论员吗？有2倍积分哦，<a class="link1"
							href="/product/${productSale.id }/comment/_new">立即发表评论>></a>
					</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div id="comments-tmall" style="display: none;">
		<div class="comment_content">
			<c:choose>
				<c:when test="${not empty tmallComments}">

					<p>以下评论来自新华文轩网络书店－天猫店</p>
					<c:forEach var="comment" items="${tmallComments}">
						<div class="reviewer">
							<span class="fr">${comment.nickName} 发表于<fmt:formatDate
									value="${comment.commentTime}" pattern="yyyy-MM-dd" />
							</span>
						</div>
						<p>${comment.content}</p>
					</c:forEach>

					<h5 class="view_all">
						<a href="/product/${productSale.id}/comment/tmall">查看所有天猫${tmallCommentCount}条评论&gt;&gt;</a>
					</h5>
				</c:when>
				<c:otherwise>
					<p>暂时还没有评论哦~~</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<div class="tabcontent03">
	<h4 class="comment_tit">
		<a class="fr" href="/product/${productSale.id}/question/list">查看所有<b
			class="questionCount">${questionCount}</b>条咨询&gt;&gt;</a>商品咨询（<b
			class="questionCount">${questionCount}</b>条）
	</h4>
	<div class="comment_content">
		<div class="question_content">
			<c:choose>
				<c:when test="${not empty customerQuestions}">
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
													<img src="../../goods/comment_true.jpg" alt="头像" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<img src="../../goods/comment_false.jpg" alt="头像" />
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
										<%-- <span class="u_name link1"><winxuan-string:substr length="8" content="${reply.replier.protectionName }"></winxuan-string:substr></span> --%>
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
					<p class="txt_center" style="padding: 80px 0px 100px 0px;">暂无提问，请填写下面提问，我们会一个工作日内响应。</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<%-- <div class="tabcontent04" style="display:none"> --%>
	<h4 class="comment_tit2">提交咨询</h4>
	<div class="consulting">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="12%" align="right">您的昵称：</td>
				<td><input type="text" readonly="readonly" id="username" class="reply_txt"
					<c:choose >
        						<c:when test="${!empty customer}">
       								value=" ${customer.displayName}"
        						</c:when >
        						<c:otherwise>
        							value="游客" 
         						</c:otherwise>
        					</c:choose > />
					<c:if test="${empty customer}">
						<b bind="loginmessage">建议先<a class="link1" href="javascript:;"
							bind="login">登录</a>
						</b>
					</c:if>
			</tr>
			<tr>
				<td align="right">咨询标题：</td>
				<td><input class="unity_w reply_txt" type="text" name="title" id="title">
				</td>
			</tr>
			<tr>
				<td align="right" style="vertical-align: text-top;">咨询内容：</td>
				<td><textarea class="unity_w reply_txt" name="content" id="content"
						cols="45" rows="5"></textarea>
				</td>
			</tr>
			<tr>
				<td align="right">验证码：</td>
				<td><input type="text" size="10" name="verifyCode" class="reply_txt"
					id="verifyCode" /> <img data-lazy="false"
					src="http://www.winxuan.com/verifyCode?d=<%=System.currentTimeMillis()%>"
					class="verifyCodeImg" /> <a class="link1" href="javascript:;"
					bind="changeVerifyCode"> 换一张</a>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input id="newquestion" class="consult_but"
					name="" type="button" value="提交咨询">
				</td>
			</tr>
		</table>
	</div>
</div>
