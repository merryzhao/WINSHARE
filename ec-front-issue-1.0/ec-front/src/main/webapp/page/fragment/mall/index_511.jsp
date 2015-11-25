<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="left_box12 fl" fragment="${fragment.id}">
<jsp:include page="../null.jsp"/>

    <c:forEach items="${contentList}" var="content" varStatus="status">
    
    
	 <dl class="goods_rush">
      <dt>
       <p><c:if test="${content.hasPromotion }"><img src="images/ads/pro_icon1.png"></c:if></p>
        <a href="${content.url}" title="${content.sellName}"><img class="book_img" src="${content.imageUrl}" alt="${content.sellName}"></a></dt>
      <dd class="goods_tit"><a href="${content.url}" title="">${content.sellName} <b class="orange">${content.subheading}</b></a></dd>
      <dd><del class="l_gray">定价：￥${content.listPrice}</del><br>
                                                      商城价：<b class="red fb">￥${content.salePrice}</b></dd>
    </dl>
    </c:forEach>
 </div>