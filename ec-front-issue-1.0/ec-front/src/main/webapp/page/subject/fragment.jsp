<%@page pageEncoding="UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="winxuan"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-${subject.title}-专题-更多</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="bookshop" name="type"/>
</jsp:include>
<link href="${serverPrefix}css2/images/detail.css?${version}" rel="stylesheet" type="text/css">
</head>
<body class="bookshop">
<c:if test="${subject.sort.id==Code.PRODUCT_SORT_BOOK}">
	<jsp:include page="/page/snippets/version2/header.jsp">
		<jsp:param value="book" name="label"/>
	</jsp:include>
</c:if>
<c:if test="${subject.sort.id==Code.PRODUCT_SORT_VIDEO}">
	<jsp:include page="/page/snippets/version2/header.jsp">
		<jsp:param value="media" name="label"/>
	</jsp:include>
</c:if>
<c:if test="${subject.sort.id==Code.PRODUCT_SORT_MERCHANDISE}">
	<jsp:include page="/page/snippets/version2/header.jsp">
		<jsp:param value="mall" name="label"/>
	</jsp:include>
</c:if>

<div class="layout">
	<div class="subject_books">
		<ul>
			<c:forEach items="${contentList}" var="content" varStatus="status">
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
		    </c:forEach>
		</ul>
	</div>
</div>

<script type="text/javascript" src="${serverPrefix}/js/book/common.js?${version}"></script>

<%@include file="/page/snippets/version2/footer.jsp" %>
<script>
			seajs.use(["jQuery","toolkit"],function($,ToolKit){new ToolKit();});
			seajs.use(["jQuery","lazyimg"],function($,lazyimg){lazyimg.run();});
</script>
</body>
</html>
