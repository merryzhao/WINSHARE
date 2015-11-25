<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="../tags.jsp" %>
<c:set var="serverPrefix" value="http://static.winxuancdn.com/" scope="page"/>
<c:choose>
	<c:when test='${param.type=="v1"}'>
		<%--第1版 的头部引用 --%>
		<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="${serverPrefix}images/ie-fix.css" />
		<![endif]-->
		<link href="${serverPrefix}images/general.css?${version}" rel="stylesheet" type="text/css">
		<link href="${serverPrefix}css/layout.css?${version}" rel="stylesheet" type="text/css">
	</c:when>
	<c:otherwise>
		<%--第2版的头部引用 --%>
		<!--[if lte IE 6]>
			<link rel="stylesheet" href="${serverPrefix}images/ie-fix.css"/>
			<link rel="stylesheet" type="text/css" href="${serverPrefix}images/png_fix.css" />
		<![endif]-->
		<link href="${serverPrefix}css2/images/general.css?${version}" rel="stylesheet" type="text/css">
		<c:if test='${param.type=="details"}'>
			<link href="${serverPrefix}css2/images/detail.css?${version}" rel="stylesheet" type="text/css">
		</c:if>
		<c:if test='${param.type=="acc_order"}'>
			<link href="${serverPrefix}css2/images/acc_order.css?${version}" rel="stylesheet" type="text/css">
			<link href="${serverPrefix}css2/images/bookshop.css?${version}" rel="stylesheet" type="text/css">
		</c:if>
		<c:if test='${param.type=="my_acc_order"}'>
			<link href="${serverPrefix}css2/images/my/acc_order.css?${version}" rel="stylesheet" type="text/css">
		</c:if>
		<c:if test='${param.type=="index"}'>
			<link href="${serverPrefix}css2/images/index.css?${version}" rel="stylesheet" type="text/css">
		</c:if>
		<c:if test='${param.type=="bookshop"}'>
			<link href="${serverPrefix}css2/images/bookshop.css?${version}" rel="stylesheet" type="text/css">
		</c:if>
		<c:if test='${param.type=="mall"}'>
			<link href="${serverPrefix}css2/images/mall.css?${version}" rel="stylesheet" type="text/css">
		</c:if>
		<c:if test='${param.type=="media"}'>
			<link href="${serverPrefix}css2/images/media.css?${version}" rel="stylesheet" type="text/css">
		</c:if>
		<c:if test='${param.type=="help"}'>
			<link href="${serverPrefix}help/css/css_help.css?${version}" rel="stylesheet" type="text/css">
		</c:if>
		<c:if test='${param.type=="company"}'>
			<link href="${serverPrefix}help/css/general_2.css?${version}" rel="stylesheet" type="text/css">
		</c:if>
		<link href="${serverPrefix}css2/images/fix.css?${version}" rel="stylesheet" type="text/css">
	</c:otherwise>
</c:choose>
<link rel="icon" href="http://www.winxuan.com/favicon.ico" type="image/x-icon" />  
<link rel="shortcut icon" href="http://www.winxuan.com/favicon.ico" type="image/x-icon" />  
<script src="${serverPrefix}libs/core.js?${version}"></script>
<c:choose>
    <c:when test='${param.ebook=="true"}'>
        <script src="${serverPrefix}js/ethirdparty.js?${version}"></script>
        <script src="${serverPrefix}js/thirdparty.js?${version}"></script>
    </c:when>
    <c:otherwise>
        <script src="${serverPrefix}js/thirdparty.js?${version}"></script>
    </c:otherwise>
</c:choose>