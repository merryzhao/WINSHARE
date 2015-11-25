<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--已使用index_510片段 --%>
<div class="right_box3 fr" fragment="${fragment.id}">
	<h4 class="hot_title">人气热销</h4>
	<ol class="hot_goods">
		<c:forEach items="${contentList}" var="content" varStatus="status">
			<li><a href="${content.url}" title="${content.sellName}"><img
					class="fl" src="${content.imageUrl}" alt="${content.sellName}">
			</a>
			<h3>
					${status.count}.<a href="${content.url}"
						title="${content.sellName}">${content.sellName}<b
						class="orange">${content.subheading}</b>
					</a>
				</h3>
				<b class="red fb">￥${content.salePrice}</b>
			</li>

		</c:forEach>
	</ol>
</div>