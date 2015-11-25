<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE div PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://static.winxuancdn.com/libs/core.js?${version}"></script>
</head>
<body>
	<div class="right_box2 fl" fragment="${fragment.id}">
		<jsp:include page="../null.jsp" />
		<div class="shopads_box">
			<ul class="rotation2">
				<c:forEach items="${contentList}" var="content" varStatus="status">
					<c:if test="${status.first }">
						<li class="current_ad">${status.index+1 }</li>
					</c:if>
					<c:if test="${!status.first }">
						<li>${status.index+1}</li>
					</c:if>
				</c:forEach>
			</ul>
			
			<ul class="shop_ads">
				<c:forEach items="${contentList}" var="content" varStatus="status">
					<li><a href="${content.url }"
						title="<c:out value="${content.title }"/>" target="_blank"><img
							src="${content.src }" alt="${content.title }" data-lazy="false"></a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="ads_sort">
			<dl>
				<c:forEach items="${contentList}" var="content" varStatus="status">
					<c:if test="${status.first }">
						<dd class="now_theme">${content.name}</dd>
					</c:if>
					<c:if test="${!status.first }">
						<dd>${content.name}</dd>
					</c:if>
				</c:forEach>
			</dl>
			<a href="javascript:;" class="left_arrow"></a> <a href="javascript:;"
				class="right_arrow"></a>
		</div>
	</div>
</body>
<script>
	seajs.use("slider", function(slider) {
		slider()
	});
</script>
</html>
