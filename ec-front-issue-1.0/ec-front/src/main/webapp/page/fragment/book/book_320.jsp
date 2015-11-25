<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div content="hotpro" style="display:none" >
<ul class="more_sort">
	<li > 
	  <a   target="_blank"  href="" class="now_sort"></a>
	</li>
</ul>
<ul class="hotpro_list" fragment="320">
<jsp:include page="../null.jsp"/>
<c:forEach items="${contentList}" var="content" varStatus="status">
	<li>
		<p>
			<a title="<c:out value="${content.name }"/>"    target="_blank"  href="${content.url }"><img data-lazy="false" src="${content.imageUrl }" alt="<c:out value="${content.name}" /> ">
			</a>
		</p> <strong><a title="<c:out value="${content.name }"/>"   target="_blank"  href="${content.url }"><winxuan-string:substr length="16" content="${content.name}"/></a>
	</strong> <b class="red fb">￥${content.effectivePrice }</b> <del>￥${content.listPrice}</del>
	</li>
</c:forEach>
</ul>
</div>