<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="cell cell-img-left-intro" fragment="${fragment.id}" cachekey="${param.cacheKey }">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="content" varStatus="i">
			${content.content}
	</c:forEach>
</div>

<%-- 样式模版 --%>
<%-- <div class="img"><a href="#" target="_blank"><img src="" alt=""></a></div>
<div class="name"><a href="#" target="_blank" title="">标题标题标题</a></div>
<div class="intro">商品介绍商品介绍商品介绍</div>	 --%>