<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="album_box rush_details" fragment="434">
<jsp:include page="../null.jsp"/>
	<c:forEach items="${contentList}" var="contentHref" step="3" varStatus="status">
		
				<dl class="goods_rush">
				 <dd>
					 <p class="other_mnews">
				     <c:forEach items="${contentList}" var="contentProduct" varStatus="statucProduct" begin="${status.index}" end="${status.index+2}" >
		      			 <a href="${contentProduct.url}"  title="${contentProduct.product.name}">${contentProduct.product.name}</a>
					 </c:forEach>
					 </p>
				 </dd>
				</dl>
		
	</c:forEach>
	</div>
