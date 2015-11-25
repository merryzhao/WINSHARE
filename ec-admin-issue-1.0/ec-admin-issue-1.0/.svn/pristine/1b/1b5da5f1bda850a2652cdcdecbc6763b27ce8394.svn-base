<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.cl-table{width:100%;border:1px solid #DFDFDF}
table.cl-table tr{height:30px;line-height:30px;border:1px solid #DFDFDF;}
table.cl-table tr.hover{background:#ffffe1}
table.cl-table .odd{background:#FCFCFC}
table.cl-table .even{background:#F9F9F9}
</style>

<%@include file="../snippets/meta.jsp"%>
<title>订单批量更新DC</title>
</head>
<body>
	<!-- 引入JS -->
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<form action="/order/batchUpdateDc" method="post">
					<div id="content-result">
						<div align="center">
							<h4>
								<c:if test="${!empty message}">
									${message }
								</c:if>
								
								<c:if test="${empty messageMap && empty message}">
									更新订单DC成功，共 ${total } 条 <br/>
								</c:if> 
								<c:if test="${!empty messageMap}">
									<c:forEach items="${messageMap }" var="item">
										${item.key } : ${item.value }<br/>
									</c:forEach>
								</c:if> 
							</h4>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>