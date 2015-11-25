<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="s" %>
<%--已使用media_462片段 --%>
<div content="media" fragment="464" style="display: none;">
<jsp:include page="../null.jsp"/>
 	<c:forEach items="${contentList}" var="content" varStatus="status"
			begin="0" end="4">
	 	  <dl class="goods_rush music_goods">
     	   <dt>
     	   	<c:if test="${content.hasPromotion }">
        		<p><img src="../../images/ads/pro_icon1.png"></p>
        	</c:if>
        		<a   href="${content.url }" title="<c:out value="${content.product.name }"/>"><img class="book_img" src="${content.imageUrl }" alt="${content.product.name }"></a>
       	 </dt>
       	 <dd class="goods_tit">
        	<a   href="${content.url}" title="<c:out value="${content.product.name }"/>"><s:substr length="15" content="${content.product.name}"></s:substr></a>
       	 </dd>
       	 <dd>
        	<del class="l_gray">定价：￥${content.listPrice}</del>
      	  <br/>
   			       文轩价：<b class="red fb">￥${content.effectivePrice}</b></dd>
    	  </dl>
 	</c:forEach>
</div>