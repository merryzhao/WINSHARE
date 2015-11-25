<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="title" fragment="${fragment.id }" cachekey="${param.cacheKey }">
	<h3 class="title-h"><i class="iconfont">&#xe64c;</i>
		<c:if test="${fragment.name==''}">暂无内容</c:if>
		${fragment.name}
	</h3>
</div>
