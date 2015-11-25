<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h2 class="sort_tit txt_center margin10">${fragment.name}</h2>
    <ul class="viewed_goods">
    	<c:forEach var="content" items="${contentList}"  varStatus="status">
		      <li><a href="${content.url}" title="${content.name}"><img class="fl" src="${content.imageUrl}" alt="${content.name}"></a><h3>${status.index}.<a href="${content.url}" title="${content.name}">${content.name}</a></h3><b class="red fb">￥${content.effectivePrice}</b></li>
    	</c:forEach>
    </ul>
