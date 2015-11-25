<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="winxuan-string" %>
<div class="top_ten fl margin10" fragment="${fragment.id}">
		<h4>
			<a style="display:none;" class="fr l_gray" href="javascript:;">详细&gt;&gt;</a><span class="fb">每月销售榜</span>
		</h4>
		<ol>
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<li <c:if test="${status.index == 0}">class="expand"</c:if>>
			<span class="nu${status.count} fl"></span>
				<a href="${content.url}" target="_blank" title="<c:out value="${content.name}" />" class="img">
					<img alt='<c:out value="${content.name}"/>' src="${content.imageUrl}" class="fl">
				</a>
				<p>
				<a class='fk' href="${content.url}" target="_blank" title='<c:out value="${content.name}" />'>
					<winxuan-string:substr length="14" content="${content.name}"/>
				</a>
				<b class="red fb">￥${content.effectivePrice}</b>
				<del>￥${content.listPrice}</del>
				</p>
			</li>
				</c:forEach>
		</ol>
	</div>
<script>
seajs.use("jQuery",function($){
	var context=$("div[fragment='478']");
	context.delegate("ol>li","mouseover",function(){
		context.find("ol>li.expand").removeClass("expand");
		$(this).addClass("expand");
	});
});
</script>