<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>锁定库存查询</title>
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content">
			<div id="roadmap-body">
			 <table class="list-table">
						<tr>
							<th>渠道</th>
							<th>用户</th>
							<th>商品编码</th>
							<th>实际锁定库存</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
						<c:forEach var="stocklock" items="${stockLockRules }">
							<tr>
								<td>${stocklock.channel.name }</td>
								<td><c:if test="${stocklock.customer!=null }">${stocklock.customer.name}</c:if></td>
								<td>${stocklock.productSale }</td>
								<td>${stocklock.realLock }</td>
								<td><fmt:formatDate value='${stocklock.beginTime }' pattern='yyyy-MM-dd HH:mm'/></td>
								<td><fmt:formatDate value='${stocklock.endTime }' pattern='yyyy-MM-dd HH:mm'/></td>
								<td><fmt:formatDate value='${stocklock.createTime }' pattern='yyyy-MM-dd HH:mm'/></td>
								<td><c:if test="${stocklock.status}">是</c:if><c:if test="${stocklock.status==false}">否</c:if></td>
								<td><a href="/stockrule/lock/${stocklock.id }?type=edit" >编辑</a>|<a href="/stockrule/lock/${stocklock.id }?type=browse" >浏览</a></td>
							</tr>
						</c:forEach>
					</table>
					${pagination }
				</div>
			</div>
		</div>
	</div>
</body>
</html>