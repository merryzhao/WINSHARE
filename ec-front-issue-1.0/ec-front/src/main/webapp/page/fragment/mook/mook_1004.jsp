<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
 <div class="editors_re" fragment="${fragment.id}">
	<h3 class="pro_title">
		<a href="javascript:;" title="">${fragment.name}</a>
	</h3>
	<dl class="artists_recom">
	<jsp:include page="../null.jsp"/>
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<c:choose>
				<c:when test="${status.index<2}">
					<dd class="dt">
						<a class="pro_img"   target="_blank"  href="${content.url }"><img
							src="${content.imageUrl}" alt="<c:out value="${content.name}"></c:out>"> </a>
						<h3>
							<a  target="_blank"  href="http://search.winxuan.com/search?author=<winxuan-string:encode content="${content.author}"></winxuan-string:encode>" class="khaki fb "> ${content.author}</a> 
							推荐： 
							<a	class="link1 fb"   target="_blank"  href="${content.url}" title="<c:out value="${content.name }"/>">${content.name}</a>
						</h3>
						<p>
							作者:<a  target="_blank"  href="http://search.winxuan.com/search?author=<winxuan-string:encode content="${content.author}"></winxuan-string:encode>" class="khaki fb ">${content.author}</a><br/>出版社:<a  target="_blank"  href="http://search.winxuan.com/search?author=<winxuan-string:encode content="	${content.product.manufacturer}"></winxuan-string:encode>" class="khaki fb ">${content.product.manufacturer}</a>
						
						</p>
						<p>
						 <b class="fb red f14">￥${content.effectivePrice}</b>
						 <b class="l_gray"><fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>折</b>	
						</p>
					</dd>
				</c:when>
				<c:otherwise>
					<dd>
						<div><a class="pro_img"   target="_blank"  href="${content.url }"><img
							src="${content.imageUrl }" alt="<c:out value="${content.product.name }"></c:out>">
							</a>
							<h3>
								<a  target="_blank"  href="http://search.winxuan.com/search?author=<winxuan-string:encode content="${content.author}"></winxuan-string:encode>" class="khaki fb "> ${content.author }</a>
								 推荐：
							</h3>
							<p class="book_name">
								<a class="link4"   target="_blank"  href="${content.url }"	title="<c:out value="${content.name}"></c:out>">${content.name }</a>
							</p>
							<p>
							<b class="fb red f14">￥${content.effectivePrice}</b>
									<b class="l_gray"> <fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>折</b>
	                       	</p>
                      	</div>
					</dd>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</dl>
	<div class="hei10"></div>
</div>