<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div fragment="${fragment.id}">
<h3 class="pro_title margin10"><a href="javascript:;" title="">${fragment.name}</a></h3>
<jsp:include page="../null.jsp"/>
	<c:forEach var="content" items="${contentList}"  varStatus="status">
	    <dl class="goods_list">
	      <dt>
	      	<c:if test="${content.hasPromotion }">
		        <p><img src="http://static.winxuancdn.com/images/ads/pro_icon1.png"/></p>
	      	</c:if>
	        <a href="${content.url}" title="${content.name}"><img class="book_img" src="${content.imageUrl}" alt="${content.name}"></a></dt>
	      <dd class="goods_tit"><a href="${content.url}" title="${content.name}"><winxuan-string:substr length="20" content="${content.name}"/></a></dd>
	      <dd><del class="l_gray">定价：￥  ${content.listPrice}</del><br>
	        商城价：<b class="red fb">￥${content.effectivePrice}</b></dd>
	      
	    </dl>
	</c:forEach>
</div>