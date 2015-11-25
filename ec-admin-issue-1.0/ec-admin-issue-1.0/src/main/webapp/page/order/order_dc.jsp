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

<script type="text/javascript">
	function commit() {
		var $order=$("#orderId");
		if ($order.val()) {
			return true;
		} else {
			alert("订单号不能为空");
			return false;
		};
	}
</script>
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
					<table>
						<tr>
							<td>
								订单号：<textarea id="orderId" name="orderId" style="width: 150px;height:150px"></textarea>
							</td>
						</tr>
						<tr>
							<td>
								发货DC：<select name='dcId'>
									<option value="110003">8A17</option>
									<option value="110004">D818</option>
									<option value="110007">D819</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<input type="submit" onclick="return commit();" value="确定"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>