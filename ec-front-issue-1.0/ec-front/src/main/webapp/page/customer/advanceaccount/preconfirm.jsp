<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的暂存款- 文轩网</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>

<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
  <jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="3_7" />
</jsp:include>
<div class="order_border">
<h4 class="check_title f14 fb">暂存款提现：<span class="red">&yen;${cashApplyForm.refundMoney }</span></h4>
				<div class="check_orders">
				<c:choose>
					<c:when test="${empty customerExtend.payPassword }">
					<p>您还没有设置暂存款支付密码，为了保障您的帐户安全，请先<a href="javascript:;" bind="set-checkpass">设置支付密码</a></p>
					</c:when>
					<c:otherwise>
					<p class="password_tip tip"></p>
					<div class="pass_field"><label>支付密码：</label><input type="password" id="password" name="payPassword"/><a href="javascript:;" bind="set-checkpass">忘记密码？</a></div>
					<c:if test="${payEmailStatus!=0}">
					<div class="type_field"><label>校验码获取方式：</label><input type="radio" name="checkType" bind="checkType" value="mobile" /><span>手机获取</span><input type="radio" name="checkType" bind="checkType" value="email" /><span>邮箱获取</span></div>
					</c:if>
					<p class="code_tip tip"></p>
					<div class="code_field"><label>校验码：</label>
					<input type="text" class="code" name="payCheckCode" />
					<button class="get_code" bind="getCode">点此免费获取</button><span class="gray">校验码是6位数字</span></div>
					<div class="btn_field"><button class="check_btn" bind="withdraw">确认提现</button><p class="message tip"></p></div>
					<input name="userType" type="hidden" value="${payEmailStatus}" /><!-- 用户类型 -->
		            <input name="hasMobile" type="hidden" value="${payMobileStatus}" /><!-- 绑定手机 -->
		            <input name="hasEmail" type="hidden" value="${payEmailStatus}" /><!-- 绑定邮箱 -->
					</c:otherwise>
				</c:choose>
				</div>
			</div>
<%@include file="../../snippets/footer.jsp"%>
<script type="text/javascript" src="${serverPrefix}js/checkpassword.js?20120110"></script>	
</body>
</html>
