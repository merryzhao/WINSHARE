<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<div class="cont">
<!-- 总评论  -->
                 <!-- master-comment -->
                 <div class="master-comment comment-list">
					<c:choose>
					<c:when test="${not empty customerComments}">
					                 <!-- comment-item (loop) -->
					<c:forEach var="customerComment" items="${customerComments}" varStatus="status">
                     <div class="common-item">
                         <div class="common-user">
                         	<c:choose>
			<c:when test="${not empty customerComment[0].customer }">
				<c:choose>
					<c:when test="${not empty customerComment[0].customer.avatar }">
						<div class="img"><img src="${customerComment[0].customer.avatar }" alt="头像" /></div>
					</c:when>
					<c:otherwise>
					<c:if test="${customerComment[0].customer.grade == 0 }"> <div class="img img1"></div></c:if>
					<c:if test="${customerComment[0].customer.grade == 1 }"> <div class="img img2"></div></c:if>
					<c:if test="${customerComment[0].customer.grade == 2 }"> <div class="img img3"></div></c:if>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div class="img"><img src="../../goods/comment_true.jpg" alt="头像" /></div>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${empty customerComment[0].customer }"> <div class="name">游客</div></c:when>
			<c:otherwise><div class="name"><winxuan-string:substr length="8" content="${customerComment[0].customer.protectionName}"></div></winxuan-string:substr></c:otherwise>
		</c:choose>
		<c:if test="${customerComment[0].customer.grade == 0 }"> <div class="class class1">普通会员</div></c:if>
		<c:if test="${customerComment[0].customer.grade == 1 }"> <div class="class class2">银牌会员</div></c:if>
		<c:if test="${customerComment[0].customer.grade == 2 }"> <div class="class class3">金牌会员</div></c:if>
                            <!-- 根据用户等级class1~n -->
                         </div>
                         <div class="common-cont">
                             <dl class="comment-normal-list">
                                 <dt>
                                  <span class="comment-user-star comment-user-star${customerComment[0].rank.rank}"></span><b>${customerComment[0].title}</b>
                                  <span class="time"><fmt:formatDate value="${customerComment[0].commentTime}" pattern="yyyy-MM-dd" /></span>
                                 </dt>
                                 <c:if test="${not empty customerComment[1]}">
	                                 <dd class="comment-quote">
	                                 <p>回复<s>${customerComment[1].customer.protectionName}</s>的评论：<b>${customerComment[1].title}</b></p>${customerComment[1].content }</dd>
                                 </c:if>
                                 <dd>${customerComment[0].content }</dd>
                                 <!-- 引用变更为回复 -->
                                 <dd class="comment-avtive"><!-- <a class="comment-btn-2" href="/product/${productSale.id }/comment/_new?quoteCommentId=${customerComment[0].id}">引用</a> -->
                                     <div class="fr"><a class="comment-btn-3" href="javascript:void(0);" param="${customerComment[0].id}" bind="useful">有用（<b class="useful">${customerComment[0].usefulCount}</b>）</a>
                                     <a class="comment-btn-2" href="/product/${productSale.id }/comment/_new?quoteCommentId=${customerComment[0].id}" target="_blank">回复</a>
                                     </div>
                                 </dd>
                             </dl>
                             <div class="comment-cont-arrow"></div>
                         </div>
                     </div>
                 </c:forEach>
                 <div class="comment-master-avtive"><a class="comment-btn-3" href="/product/${productSale.id }/comment/_new" target="_blank" title="我要评论"></a><a href="/product/${productSale.id}/comment/list" class="common-all" target="_blank">查看所有评论 （${commentCount}）条 &gt;&gt;</a></div>
                 <!-- comment-item (loop) end -->
                 </c:when>
           <c:otherwise>
			<!-- comment-item-none -->
                 <div class="comment-item common-item-none">
                     <span class="text">暂无评论，想成为第一个评论员吗？有2倍积分哦~</span>
                 </div>
                 <!-- comment-item-none end -->
                 </c:otherwise>
                 </c:choose>
                 </div>
                 <!-- master-comment end -->
<!-- 总评论    end -->
                 
<!-- 好评  -->
                 <!-- master-comment -->
                 <div class="master-comment comment-list" style="display: none;">
					<c:choose>
					<c:when test="${not empty praiseCustomerComment}">
					                 <!-- comment-item (loop) -->
			<c:forEach var="customerComment" items="${praiseCustomerComment}" varStatus="status">
          	<div class="common-item">
              <div class="common-user">
              	<c:choose>
				<c:when test="${not empty customerComment[0].customer }">
				<c:choose>
					<c:when test="${not empty customerComment[0].customer.avatar }">
						<div class="img"><img src="${customerComment[0].customer.avatar }" alt="头像" /></div>
					</c:when>
					<c:otherwise>
					<c:if test="${customerComment[0].customer.grade == 0 }"> <div class="img img1"></div></c:if>
					<c:if test="${customerComment[0].customer.grade == 1 }"> <div class="img img2"></div></c:if>
					<c:if test="${customerComment[0].customer.grade == 2 }"> <div class="img img3"></div></c:if>
						
					</c:otherwise>
				</c:choose>
				</c:when>
					<c:otherwise>
						<div class="img"><img src="../../goods/comment_true.jpg" alt="头像" /></div>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${empty customerComment[0].customer }"> <div class="name">游客</div></c:when>
					<c:otherwise><div class="name"><winxuan-string:substr length="8" content="${customerComment[0].customer.protectionName}"></div></winxuan-string:substr></c:otherwise>
				</c:choose>
				<c:if test="${customerComment[0].customer.grade == 0 }"> <div class="class class1">普通会员</div></c:if>
				<c:if test="${customerComment[0].customer.grade == 1 }"> <div class="class class2">银牌会员</div></c:if>
				<c:if test="${customerComment[0].customer.grade == 2 }"> <div class="class class3">金牌会员</div></c:if>
                            <!-- 根据用户等级class1~n -->
                         </div>
                         <div class="common-cont">
                             <dl class="comment-normal-list">
                                 <dt>
                                  <span class="comment-user-star comment-user-star${customerComment[0].rank.rank}"></span><b>${customerComment[0].title}</b>
                                  <span class="time"><fmt:formatDate value="${customerComment[0].commentTime}" pattern="yyyy-MM-dd" /></span>
                                 </dt>
                                 <c:if test="${not empty customerComment[1]}">
	                                 <dd class="comment-quote">
	                                 <p>回复<s>${customerComment[1].customer.protectionName}</s>的评论：<b>${customerComment[1].title}</b></p>${customerComment[1].content }</dd>
                                 </c:if>
                                 <dd>${customerComment[0].content }</dd>
                                 <!-- 引用变更为回复 -->
                               	 <dd class="comment-avtive">
                                 <div class="fr"><a class="comment-btn-3" href="#">有用（${customerComment[0].usefulCount}）</a>
                                 <a class="comment-btn-2" href="/product/${productSale.id }/comment/_new?quoteCommentId=${customerComment[0].id}" target="_blank">回复</a>
                                 </div>
                                 </dd>
                             </dl>
                             <div class="comment-cont-arrow"></div>
                         </div>
                     </div>
                 </c:forEach>
                 <div class="comment-master-avtive"><a class="comment-btn-3" href="/product/${productSale.id }/comment/_new" target="_blank" title="我要评论"></a><a href="/product/${productSale.id}/comment/list" class="common-all" target="_blank">查看所有评论 （${commentCount}）条 &gt;&gt;</a></div>
                 <!-- comment-item (loop) end -->
                 </c:when>
           <c:otherwise>
			<!-- comment-item-none -->
                 <div class="comment-item common-item-none">
                     <span class="text">暂无评论</span>
                 </div>
                 <!-- comment-item-none end -->
                 </c:otherwise>
                 </c:choose>
                 </div>
                 <!-- master-comment end -->
                 <!-- 好评    end -->
                 
                 <!-- 中评  -->
                 <!-- master-comment -->
                 <div class="master-comment comment-list" style="display: none;">
					<c:choose>
					<c:when test="${not empty middlerCustomerComment}">
					                 <!-- comment-item (loop) -->
			<c:forEach var="customerComment" items="${middlerCustomerComment}" varStatus="status">
             <div class="common-item">
               <div class="common-user">
               	<c:choose>
				<c:when test="${not empty customerComment[0].customer }">
				<c:choose>
					<c:when test="${not empty customerComment[0].customer.avatar }">
						<div class="img"><img src="${customerComment[0].customer.avatar }" alt="头像" /></div>
					</c:when>
					<c:otherwise>
					<c:if test="${customerComment[0].customer.grade == 0 }"> <div class="img img1"></div></c:if>
					<c:if test="${customerComment[0].customer.grade == 1 }"> <div class="img img2"></div></c:if>
					<c:if test="${customerComment[0].customer.grade == 2 }"> <div class="img img3"></div></c:if>
						
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div class="img"><img src="../../goods/comment_true.jpg" alt="头像" /></div>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${empty customerComment[0].customer }"> <div class="name">游客</div></c:when>
			<c:otherwise><div class="name"><winxuan-string:substr length="8" content="${customerComment[0].customer.protectionName}"></div></winxuan-string:substr></c:otherwise>
		</c:choose>
		<c:if test="${customerComment[0].customer.grade == 0 }"> <div class="class class1">普通会员</div></c:if>
		<c:if test="${customerComment[0].customer.grade == 1 }"> <div class="class class2">银牌会员</div></c:if>
		<c:if test="${customerComment[0].customer.grade == 2 }"> <div class="class class3">金牌会员</div></c:if>
                            <!-- 根据用户等级class1~n -->
                         </div>
                         <div class="common-cont">
                             <dl class="comment-normal-list">
                                 <dt>
                                  <span class="comment-user-star comment-user-star${customerComment[0].rank.rank}"></span><b>${customerComment[0].title}</b>
                                  <span class="time"><fmt:formatDate value="${customerComment[0].commentTime}" pattern="yyyy-MM-dd" /></span>
                                 </dt>
                                 <c:if test="${not empty customerComment[1]}">
	                                 <dd class="comment-quote">
	                                 <p>回复<s>${customerComment[1].customer.protectionName}</s>的评论：<b>${customerComment[1].title}</b></p>${customerComment[1].content }</dd>
                                 </c:if>
                                 <dd>${customerComment[0].content }</dd>
                                 <!-- 引用变更为回复 -->
                              	  <dd class="comment-avtive">
                                  <div class="fr"><a class="comment-btn-3" href="#">有用（${customerComment[0].usefulCount}）</a>
                                  <a class="comment-btn-2" href="/product/${productSale.id }/comment/_new?quoteCommentId=${customerComment[0].id}" target="_blank">回复</a>
                                  </div>
                                  </dd>
                             </dl>
                             <div class="comment-cont-arrow"></div>
                         </div>
                     </div>
                 </c:forEach>
                 <div class="comment-master-avtive"><a class="comment-btn-3" href="/product/${productSale.id }/comment/_new" target="_blank" title="我要评论"></a><a href="/product/${productSale.id}/comment/list" class="common-all" target="_blank">查看所有评论 （${commentCount}）条 &gt;&gt;</a></div>
                 <!-- comment-item (loop) end -->
                 </c:when>
           <c:otherwise>
			<!-- comment-item-none -->
                 <div class="comment-item common-item-none">
                     <span class="text">暂无评论</span>
                 </div>
                 <!-- comment-item-none end -->
                 </c:otherwise>
                 </c:choose>
                 </div>
                 <!-- master-comment end -->
                 <!-- 中评    end -->
                 
                 
                 <!-- 差评  -->
                 <!-- master-comment -->
                 <div class="master-comment comment-list" style="display: none;">
				<c:choose>
				<c:when test="${not empty badCustomerComment}">
				                 <!-- comment-item (loop) -->
				<c:forEach var="customerComment" items="${badCustomerComment}" varStatus="status">
                     <div class="common-item">
                         <div class="common-user">
        <c:choose>
			<c:when test="${not empty customerComment[0].customer }">
				<c:choose>
					<c:when test="${not empty customerComment[0].customer.avatar }">
						<div class="img"><img src="${customerComment[0].customer.avatar }" alt="头像" /></div>
					</c:when>
					<c:otherwise>
					<c:if test="${customerComment[0].customer.grade == 0 }"> <div class="img img1"></div></c:if>
					<c:if test="${customerComment[0].customer.grade == 1 }"> <div class="img img2"></div></c:if>
					<c:if test="${customerComment[0].customer.grade == 2 }"> <div class="img img3"></div></c:if>
						
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div class="img"><img src="../../goods/comment_true.jpg" alt="头像" /></div>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${empty customerComment[0].customer }"> <div class="name">游客</div></c:when>
			<c:otherwise><div class="name"><winxuan-string:substr length="8" content="${customerComment[0].customer.protectionName}"></div></winxuan-string:substr></c:otherwise>
		</c:choose>
		<c:if test="${customerComment[0].customer.grade == 0 }"> <div class="class class1">普通会员</div></c:if>
		<c:if test="${customerComment[0].customer.grade == 1 }"> <div class="class class2">银牌会员</div></c:if>
		<c:if test="${customerComment[0].customer.grade == 2 }"> <div class="class class3">金牌会员</div></c:if>
                            <!-- 根据用户等级class1~n -->
                         </div>
                         <div class="common-cont">
                             <dl class="comment-normal-list">
                                 <dt>
                                  <span class="comment-user-star comment-user-star${customerComment[0].rank.rank}"></span><b>${customerComment[0].title}</b>
                                  <span class="time"><fmt:formatDate value="${customerComment[0].commentTime}" pattern="yyyy-MM-dd" /></span>
                                 </dt>
                                 <c:if test="${not empty customerComment[1]}">
	                                 <dd class="comment-quote">
	                                 <p>回复<s>${customerComment[1].customer.protectionName}</s>的评论：<b>${customerComment[1].title}</b></p>${customerComment[1].content }</dd>
                                 </c:if>
                                 <dd>${customerComment[0].content }</dd>
                                 <!-- 引用变更为回复 -->
                              	 <dd class="comment-avtive">
                                  <div class="fr"><a class="comment-btn-3" href="#">有用（${customerComment[0].usefulCount}）</a>
                                  <a class="comment-btn-2" href="/product/${productSale.id }/comment/_new?quoteCommentId=${customerComment[0].id}" target="_blank">回复</a>
                                  </div>
                                  </dd>
                             </dl>
                             <div class="comment-cont-arrow"></div>
                         </div>
                     </div>
                 </c:forEach>
                 <div class="comment-master-avtive"><a class="comment-btn-3" href="/product/${productSale.id }/comment/_new" target="_blank" title="我要评论"></a><a href="/product/${productSale.id}/comment/list" class="common-all" target="_blank">查看所有评论 （${commentCount}）条 &gt;&gt;</a></div>
                 <!-- comment-item (loop) end -->
                 </c:when>
           <c:otherwise>
			<!-- comment-item-none -->
                 <div class="comment-item common-item-none">
                     <span class="text">暂无评论</span>
                 </div>
                 <!-- comment-item-none end -->
                 </c:otherwise>
                 </c:choose>
                 </div>
                 <!-- master-comment end -->
                 <!-- 差评    end -->
                 
                 
                 <!-- master-comment -->
                 <div class="slave-comment comment-list" style="display: none;">
                 <c:choose>
                 	<c:when test="${not empty tmallComments}">
                      <!-- comment-item-other (loop) -->
                     <div class="slave-comment-title">以下评论来自新华文轩网络书店－天猫店</div>
                  	<c:forEach var="comment" items="${tmallComments}">
                      <div class="comment-item-other">
                          <div class="comment-user">
                              <div class="name">${comment.nickName}</div>
                              <div class="time">发表于<fmt:formatDate value="${comment.commentTime}" pattern="yyyy-MM-dd" /></div>
                          </div>
                          <div class="common-cont">
                              <div class="comment-cont-text">${comment.content}</div>
                              <div class="comment-cont-arrow"></div>
                          </div>
                      </div>
                      <!-- comment-item-other (loop) end -->
                    </c:forEach>
                 	<div class="comment-slave-avtive"><a href="/product/${productSale.id}/comment/tmall" target="_blank">查看天猫所有评论（${tmallCommentCount}） 条 &gt;&gt;</a></div>
				</c:when>
				<c:otherwise>
					<p>暂时还没有评论哦~~</p>
				</c:otherwise>
                 </c:choose>
                 </div>
                 <!-- master-comment end -->
             </div>
         </div>
     

