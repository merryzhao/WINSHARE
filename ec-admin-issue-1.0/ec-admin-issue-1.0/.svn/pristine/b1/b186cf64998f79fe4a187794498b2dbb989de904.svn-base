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
			<form action="/refund/save" method="post">
					   <input type="hidden" name="id" value="${refundMessage.id}">
				异常名称：<input type="text" name="name" value="${refundMessage.name }"><br/>
				支付方式：<select name="payment.id">
						   <c:forEach items="${paymentList }" var="payment">
								<option value="${payment.id }" <c:if test="${refundMessage.payment.id ==payment.id }">selected="selected"</c:if>>${payment.name}</option>
						   </c:forEach>
					   </select><br/>
				是否可用：<input type="radio" name="available" <c:if test="${refundMessage.available ==true}">checked="checked"</c:if> value="true">是
					   <input type="radio" name="available" <c:if test="${refundMessage.available ==false }">checked="checked"</c:if> value="false">否<br/>
				异常代码： <br/>
				 	   <textarea rows="2" cols="2" style="width: 180px;height: 120px;" name="message">${refundMessage.message}</textarea><br/>
					  <input type="submit" value="保存">
			</form>
		</div>
		<%@include file="../snippets/scripts.jsp"%>
</body>
</html>