<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="winxuan-string" %>
<div class="top_ten fl margin10" fragment="10">
	<h4>
		<a class="fr l_gray" href="javascript:;">近期新书&gt;&gt;</a><span class="fb">${fragment.name}</span>
	</h4>
	<ol>
		<c:forEach items="${contentList}" var="content" varStatus="status">
		<c:if test="${status.index==0}">
		<li class="first"><span class="no1 fl"></span><a
			href="${content.url}" target="_blank" title="${content.name}"><img alt="${content.name}"
				src="${content.imageUrl}" class="fl">
		</a>
			<p>
				<a href="${content.url}" target="_blank" title="${content.name}">
				<winxuan-string:substr length="10" content="${content.name}"/>
				</a><br> 作者：${content.author}<br>
				<b class="red fb">￥${content.listPrice}</b>
				<del>￥${content.salePrice}</del>
			</p></li>		
		</c:if>
		<c:if test="${status.index>0}">
		<li><span class="nu${status.count}"></span>
		<a href="${content.url}" target="_blank" title='${content.name}" />'>
			<winxuan-string:substr length="10" content="${content.name}"/>
			</a></li>
		</c:if>
		</c:forEach>
	</ol>
</div>