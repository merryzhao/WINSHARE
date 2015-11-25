<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<html>
<head>
<%@include file="../../snippets/meta.jsp"%>
<title>没有默认区域统计</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-system.jsp"%>
		<div class="frame-main">
			<div>
				<h4>没有默认区域总数<b style="color: red;">*(点击条数进行查看，0条时不可点击)</b></h4>
				<table class="list-table">
					<tr><td>
					没有指定默认区域的共有：<a href="<c:if test="${districtcount!=0 }">/default/nodeftownlist</c:if><c:if test="${districtcount==0 }">#</c:if>">${districtcount }</a>条
					</td></tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>