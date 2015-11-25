<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div   content="hotsale" fragment="471">
<jsp:include page="../null.jsp"/>
 	<c:forEach items="${contentList}" var="content" varStatus="status"
			begin="0" end="4">
	 	  <dl class="goods_rush music_goods">
     	   <dt>
     	   	<c:if test="${content.hasPromotion }">
        		<p><img src="../../images/ads/pro_icon1.png"></p>
        	</c:if>
        		<a     href="${content.url }" title="<c:out value="${content.product.name }"/>"><img class="book_img" src="${content.imageUrl }" alt="<c:out value="${content.product.name}"/>"></a>
       	 </dt>
       	 <dd class="goods_tit">
        	<a     href="${content.url}" title="<c:out value="${content.product.name}"/>">${content.product.name}</a>
       	 </dd>
       	 <dd>
        	<del class="l_gray">定价：￥${content.listPrice}</del>
      	  <br/>
   			       文轩价：<b class="red fb">￥${content.effectivePrice}</b></dd>
    	  </dl>
 	</c:forEach>
</div>