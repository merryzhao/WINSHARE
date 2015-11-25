<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="right_box3 fr" >
    <dl class="book_news" fragment="${fragment.id }">
      <dt><b>促销公告</b></dt>
	<c:forEach items="${contentList}" var="content" varStatus="status">
	
	<dd>
		<a href="${content.href}" title="${content.title}">${content.name}</a>
	</dd>
</c:forEach>
    </dl>
  <c:import url="/fragment/509" />
  </div>
