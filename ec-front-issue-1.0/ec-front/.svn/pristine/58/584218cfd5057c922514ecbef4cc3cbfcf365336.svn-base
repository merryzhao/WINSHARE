<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="winxuan-string" %>
<div class="top_ten fl margin10" fragment="${fragment.id }">
	<h4>
		<a class="fr l_gray" href="javascript:;" style="display:none;">近期新书&gt;&gt;</a><span class="fb">${fragment.name}</span>
	</h4>
	<ol>
		<c:forEach items="${contentList}" var="content" varStatus="status">
		<c:choose>
			<c:when test="${status.index==0}">
			<li class="expand">
				<span class="no1 fl"></span>
				<a href="${content.url}" target="_blank" title="<c:out value="${content.name}" />" class="img">
					<img alt='<c:out value="${content.name}"/>' src="${content.imageUrl}" class="fl">
				</a>
				<p>
				<a href="${content.url}" target="_blank" title='<c:out value="${content.name}" />'>
					<winxuan-string:substr length="14" content="${content.name}"/>
				</a>
				<span>${content.author}</span>
				<b class="red fb">￥${content.effectivePrice}</b>
				<del>￥${content.listPrice}</del>
				</p>
			</li>		
			</c:when>
			<c:otherwise>
			<li>
				<span class="nu${status.count} fl"></span>
				<a href="${content.url}" target="_blank" title="<c:out value="${content.name}" />" class="img">
					<img alt='<c:out value="${content.name}"/>' src="${content.imageUrl}" class="fl">
				</a>
				<p>
				<a class='fk' href="${content.url}" target="_blank" title='<c:out value="${content.name}" />'>
					<winxuan-string:substr length="14" content="${content.name}"/>
				</a>
				<span>${content.author}</span>
				<b class="red fb">￥${content.effectivePrice}</b>
				<del>￥${content.listPrice}</del>
				</p>
			</li>
			</c:otherwise>
		</c:choose>
		</c:forEach>
	</ol>
</div>
<script>
seajs.use("jQuery",function($){
	var context=$("div[fragment='128']");
	context.delegate("ol>li","mouseover",function(){
		context.find("ol>li.expand").removeClass("expand");
		$(this).addClass("expand");
	});
});
</script>