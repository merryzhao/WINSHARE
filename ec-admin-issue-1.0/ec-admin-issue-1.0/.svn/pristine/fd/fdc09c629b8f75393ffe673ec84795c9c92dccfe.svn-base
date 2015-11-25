<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>包件录入成功</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/returnorder/returnorder.css"
	rel="stylesheet" />
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<!-- 退换货新建 -->
				<div>
					<!-- 图片 -->
					<div>
						<img class="img_width"
							src="${pageContext.request.contextPath}/imgs/returnorder/succeed.jpg">
					</div>
                    <div class="succeed">
	                    <c:if test="${msg==null}">
	                    <label>运单号：<a href="/returnorder/packagequery/${returnPackage.id }">${returnPackage.id } </a>包件，录入成功。</label>
	                    </c:if>
	                    <c:if test="${msg!=null}">
	                    <label>保存错误：${msg}</label>
	                    </c:if>
	                    <br>
	                    <label><a href="/returnorder/packagenew">继续录入退换货包件</a></label>
                    </div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/returnorder/returnorder.js"></script>
</body>
</html>
