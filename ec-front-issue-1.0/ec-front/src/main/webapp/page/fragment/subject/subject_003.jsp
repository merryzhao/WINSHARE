<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="winxuan"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="subject_books"  fragment="${fragment.id}" cachekey="${cacheKey}">
<jsp:include page="../null.jsp">
	<jsp:param value="产品列表-商品" name="tip"/>
</jsp:include>
<ul>
	<c:forEach items="${contentList}" var="content" varStatus="status">
	<c:if test="${status.count<=20}">
           <li>
               <div class="pic">			
                   <a title="${content.name}<c:if test="${!empty content.subheading }"> (${content.subheading })</c:if>"   target="_blank"  href="${content.url}"><img class="book_img" alt="${content.name}" src="${content.imageUrl}" ></a></div>
               <h4><a title="${content.name}<c:if test="${!empty content.subheading }"> (${content.subheading })</c:if>"   target="_blank"  href="${content.url}"><winxuan:substr length="24" content="${content.name}"></winxuan:substr></a></h4>
               <b class="red fb">￥${content.effectivePrice}</b> <del>￥${content.product.listPrice}</del> 
               <c:choose>
                <c:when test="${content.supplyType.id == 13102}">        
                    <c:choose>
                     	<c:when test="${content.preSaleCanBuy}">
						<p class="action"><button class="order_butb order_butb_subject" bind="presell" data-id="${content.id}" data-region="成都" data-date=" <fmt:formatDate value="${content.booking.endDate }" pattern="yyyy年MM月dd日"/>"></button></p>
                     	</c:when>
                     	<c:otherwise>
						<p class="action"><button class="notice_butb notice_butb_subject" bind="arrivalNotify" data-id="${content.id}"></button></p>
                     	</c:otherwise>
                     </c:choose>       		      		
               </c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${content.canSale}">
						<p class="action"><button class="addtocart addtocart_subject" bind="addToCart" data-id="${content.id}"></button></p>
					</c:when>
					<c:when test="${content.storageType.id==6004}">
						<p class="action"><a class="addtocart addtocart_subject"  href="http://ebook.winxuan.com/shoppingcart?opt=add&p=${content.id}" target="_blank"></a></p>
					</c:when>
					<c:otherwise>
						<p class="action"><button class="notice_butb notice_butb_subject" bind="arrivalNotify" data-id="${content.id}"></button></p>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		</li>	
	</c:if>
    </c:forEach>
</ul>
<c:if test="${fn:length(contentList) > 20}">
<div>
	<span class="subject_more">
		<a target="_blank" href="http://www.winxuan.com/subject/fragment/${fragment.id}">
			<img data-lazy="false" src="${serverPrefix}css2/images/more.png" alt="查看更多"/>
		</a>
	</span>
</div>
</c:if>
</div>