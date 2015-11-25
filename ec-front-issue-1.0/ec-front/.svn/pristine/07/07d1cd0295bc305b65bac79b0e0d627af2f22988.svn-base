<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="s" %>
<%--  --%>
<div content="media" fragment="${fragment.id }" >
<jsp:include page="../null.jsp"/>
 	<c:forEach items="${contentList}" var="content" varStatus="status" >
	 <dl class="goods_rush music_goods">
     	   <dt>
     	   	<c:if test="${content.hasPromotion }">
        		<p><img src="../../images/ads/pro_icon1.png"></p>
        	</c:if>
        		<a  href="${content.url }" title="<c:out value="${content.name }"/>"><img class="book_img" src="${content.imageUrl }" alt="${content.name }"></a>
       	 </dt>
       	 <dd class="goods_tit">
        	<a   href="${content.url}" title="<c:out value="${content.name }"/>"><s:substr length="15" content="${content.name}"></s:substr></a>
       	 </dd>
       	 <dd>
        	<del class="l_gray">定价：￥${content.listPrice}</del>
      	  <br/>
   			       文轩价：<b class="red fb">￥${content.effectivePrice}</b></dd>
    	</dl>
 	</c:forEach>
</div>