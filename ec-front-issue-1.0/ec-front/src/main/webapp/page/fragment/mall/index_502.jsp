<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- 
            <li>
                <div class="have_pro">
                    <p><c:if test=""><img src="images/ads/pro_icon1.png"></c:if></p>				
                    <a title="<c:out value="${content.name}" /> " href="${content.url}"><img class="book_img" alt="<c:out value="${content.name}" /> " src="${content.imageUrl}" ></a></div>
                <h4><a title="<c:out value="${content.name}" /> " href="${content.url}"><c:out value="${content.name}" /> </a></h4>
                <b class="red fb">￥${content.salePrice}</b> <del>￥${content.product.listPrice}</del> </li>
      --%>  
    <div class="tab_content" fragment="${fragment.id}">
    <jsp:include page="../null.jsp"/>
    <c:forEach items="${contentList}" var="content" varStatus="status">
	 <dl class="goods_rush">
      <dt>
    <p><c:if test="${content.hasPromotion }"><img src="images/ads/pro_icon1.png"></c:if></p>
        <a href="${content.url}" title="${content.sellName} ${content.subheading}"><img class="book_img" src="${content.imageUrl}" alt="${content.sellName} ${content.subheading}"></a></dt>
      <dd class="goods_tit"><a href="${content.url}" title="">${content.sellName} <b class="orange">${content.subheading}</b></a></dd>
      <dd><del class="l_gray">定价：￥${content.listPrice}</del><br>
         商城价：<b class="red fb">￥${content.effectivePrice}</b></dd>
    </dl>
    </c:forEach>
    </div>

    
    