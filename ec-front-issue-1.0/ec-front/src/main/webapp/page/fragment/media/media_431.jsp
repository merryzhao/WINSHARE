<%@page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="right_box3 fr" >

<dl class="book_news" fragment="${fragment.id}">
	<dt>
		<a style="display:none;" class="fr more_news" href="javascript:;">更多&gt;&gt;</a><b>热门音讯</b>
	</dt>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		<dd>
			<a   target="_blank"  href="${content.url }" title="${content.title }">${content.name}</a>
		</dd>
	</c:forEach>
</dl>
<c:import url="/fragment/445" />
</div>
