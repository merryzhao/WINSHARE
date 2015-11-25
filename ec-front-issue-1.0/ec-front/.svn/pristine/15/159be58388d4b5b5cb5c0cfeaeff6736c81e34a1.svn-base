<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="top_ten fl margin10 mleft" fragment="${fragment.id}">
	<h4>收藏最多</h4>
	<ol>
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<c:if test="${status.first }">
				<li class="first"><span class="no1 fl"></span>
				<a	title="<c:out value="${content.name }"/>"	  target="_blank"  href="${content.url }"><img class="fl" src="${content.imageUrl }" alt="${content.name }">
				</a>
					<p>
						<a title="<c:out value="${content.name }"/>"  target="_blank"  href="${content.url }"><winxuan-string:substr length="10" content="${content.name}"/></a><br/>
						<del>定价：￥${content.listPrice}</del>
						<br/> 文轩价：<b class="red fb">￥${content.effectivePrice}</b>
					</p></li>
			</c:if>
			<c:if test="${!status.first }">
				<li><span class="nu${status.index+1 }"></span>
				<a title="<c:out value="${content.name }"/>"  target="_blank"  href="${content.url }"><winxuan-string:substr length="14" content="${content.product.name }"/></a></li>
			</c:if>
		</c:forEach>
		<li class="txt_right">
		<%-- 
		<a class="link1"   target="_blank"  href="javascript:;">更多>></a>
		 --%>
		</li>
	</ol>
</div>