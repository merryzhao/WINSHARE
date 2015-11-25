<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>库存添加</title>
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
							<th>销售类型</th>
							<th>库存类型</th>
							<th>操作</th>
						</tr>
						<c:forEach var="stock" items="${stockRules }">
							<tr>
								<td>${stock.channel.name }</td>
								<td>${stock.supplyType.name }</td>
								<td><c:if test="${stock.stockType==1 }">实物库存</c:if>
								<c:if test="${stock.stockType==2 }">虚拟库存</c:if></td>
								<td><a href="/stockrule/${stock.id }?type=edit" >编辑</a>|<a href="/stockrule/${stock.id }?type=browse" >浏览</a></td>
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