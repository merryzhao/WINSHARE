<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>异常处理</title>
</head>
<body>
	<!-- 引入JS -->
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<form class="inline" action="/refund/findRefundMessage" method="post">
				<div class="row">
					<div>
						<span class="tablehead">支付方式名称:</span>
						<span class="tabletext">
							<select name="paymentId">
								<option value="0">--------------</option>
								<c:forEach items="${paymentList }" var="payment">
									<option value="${payment.id }"<c:if test="${payment.id==paymentId}">selected="selected" </c:if>>${payment.name }</option>
								</c:forEach>
							</select>
						</span>
						<span class="tablehead">是否可用:</span>
						<span class="tabletext">
							<select name="available">
								<option value="-1">--------------</option>
								<option value="1" <c:if test="${1==paymentId}">selected="selected" </c:if>>可用</option>
								<option value="0" <c:if test="${0==paymentId}">selected="selected" </c:if>>不可用</option>
							</select>
						</span>
					</div>
				</div>
				<input type="submit" value="查询">
				<a target="_blank" href="/refund/newMessage/-1"><input type="button" value="新增"></a>
			</form>
			<table class="list-table">
				<tr>
					<th>名称</th>
					<th>异常信息</th>
					<th>支付方式</th>
					<th>是否可用</th>
					<th>创建时间</th>
					<th>创建人</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${refundMessages }" var="message">
					<tr>
						<td>${message.name }</td>
						<td>${message.message }</td>
						<td>${message.payment.name }</td>
						<td>
							<c:if test="${message.available==true}">可用</c:if>
							<c:if test="${message.available==false}">不可用</c:if>
						</td>
						<td>${message.createTime }</td>
						<td>${message.employee.name }</td>
						<td>
							<a href="#" id="${message.id}" onclick="delMessage(${message.id});"><c:if test="${message.available==true}">停用</c:if>
							<c:if test="${message.available==false}">启用</c:if></a>
							<a target="_blank" href="/refund/newMessage/${message.id}">修改</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<c:if test="${pagination!=null&&pagination.pageCount!=0}">
				<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
			</c:if>
		</div>
		<%@include file="../snippets/scripts.jsp"%>
		<script type="text/javascript">
			function delMessage(id){
				$.ajax({
					type : 'POST',
					url : "/refund/delMessage/"+id+"?format=json",
					error : function() {			//请求失败处理函数
						alert('请求失败');
					},
					success : function(data) { //请求成功后回调函数。 
						if(data.message!=null){
							alert(data.message==true?'启用成功！':'停用成功！');
							$("#"+id).html(data.message==true?'停用':'启用');
						}else{
							alert(data.message);
						}
					}
				});
			}
		</script>
</body>
</html>