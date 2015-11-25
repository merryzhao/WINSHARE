<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta_product.jsp"%>
<%@include file="../snippets/meta.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
div.showContent a:link {
	color: #FF5500;
}

div.ui-widget-content {
	width: 1150px;
}

.message {
	margin: 0.5em 0;
	background-color: #FFFFFF;
	border: 1px solid #BBBBBB;
	color: #000000;
	font-family: inherit;
	font-size: inherit;
	font-weight: inherit;
}
</style>
<title>退款凭证详细信息</title>
</head>
<body>
	<!-- 引入JS -->
	<%@include file="../snippets/scripts.jsp"%>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner ui-widget-content" id="content">
				<div id="ui-widget-content">
					<div class="ui-widget-content">
						<table class="list-table">
							<tr class="hover">
								<td>凭证ID：</td>
								<td>${credential.id }</td>
								<td>支付方式：</td>
								<td>${credential.payment.name }</td>
								<td>用户ID：</td>
								<td>${credential.customer.id }</td>
							</tr>
							<tr class="hover">
								<td>外部ID：</td>
								<td>${credential.outerId }</td>
								<td>订单号：</td>
								<td>${credential.order.id }</td>
								<td>退款金额：</td>
								<td>${credential.money }</td>
							</tr>
							<tr class="hover">
								<td>创建时间：</td>
								<td>${credential.createTime }</td>
								<td>退款时间：</td>
								<td>${credential.refundTime }</td>
								<td>退款状态：</td>
								<td><select name="status" id="status">
										<option value="461013"
											<c:if test="${credential.status.id == 461013}">selected</c:if>
											>等待系统退款</option>
										<option value="461014"
											<c:if test="${credential.status.id == 461014}">selected</c:if>
											>等待第三方退款</option>
										<option value="461015"
											<c:if test="${credential.status.id == 461015}">selected</c:if>
											>退款成功</option>
										<option value="461016"
											<c:if test="${credential.status.id == 461016}">selected</c:if>
											>退款失败</option>
										<option value="461019"
											<c:if test="${credential.status.id == 461019}">selected</c:if>
											>退款作废</option>
								</select></td>
							</tr>
							<tr class="hover">
								<td>操作人：</td>
								<td>${credential.operator.id }</td>
								<td>版本：</td>
								<td>${credential.version }</td>
							</tr>
						</table>
					</div>
					<br />
					<div class="ui-widget-content">
						<table class="list-table">
							<tr class="hover" id="message">
								<td>备注信息</td>
								<td colspan="3">
									<textarea id="newMessage" style="width: 624px; height: 57px;">
										${credential.message }
									</textarea>
								</td>
							</tr>
						</table>
					</div>
					<button onclick="edit()">修改</button>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="id" value="${credential.id}">
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript">
		function edit() {
			if (!confirm("确认做此修改")) {
				return;
			}
			var id = $('#id').val();
			var newMessage = $('#newMessage').val();
			var status = $('#status').val();
			$.post('/refund/edit', {
				'id' : id,
				'status' : status,
				'message' : newMessage,
				'format' : 'json'
			}, function(data) {
				if (data.result == 1) {
					alert('操作成功');
					location.reload();
				} else {
					alert(data.message);
				}
			}, 'json');
		}
	</script>
</body>

</html>