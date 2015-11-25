<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">

		<div class="container-fluid">
			<a class="brand" href="http://www.winxuan.com" class="logo">平台运营管理系统
				v0.1</a>
			<ul class="nav">
				<li><a href="${contextPath}/roadmap/list">系统管理</a></li>
				<li><a href="${contextPath}/order/search">订单管理</a></li>
				<li><a href="${contextPath}/channel">销售渠道</a></li>
				<li><a href="${contextPath}/comment/goList">网站管理</a></li>
				<li><a href="${contextPath}/product/prepare">商品管理</a></li>
				<li><a href="${contextPath}/seller/query">卖家管理</a></li>
				<li><a href="${contextPath}/customer/advanceaccountQuery">财务管理</a>
				</li>

				<c:if test='${param.type=="report"}'>
					<li class="active"><a href="${contextPath}/report/grid/list">报表管理</a>
					</li>
				</c:if>
				<c:if test='${param.type!="report"}'>
					<li><a href="${contextPath}/report/grid/list">报表管理</a>
					</li>
				</c:if>


				<li><a href="${contextPath}/dic">搜索管理</a>
				</li>
			</ul>
			<ul class="nav pull-right">
				<li><a href="javascript:;">${requestScope["com.winxuan.ec.model.user.User"].name}</a>
				</li>
				<li><a href="/employee/logout">退出系统</a>
				</li>
				<li></li>
			</ul>
		</div>
	</div>
</div>
