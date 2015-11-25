<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan" %>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>暂存款查询</title>
</head>
<body>
    <!-- 引入JS -->
	<div class="frame">
	     <!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		 <!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
		    <!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>暂存款查询</h4>
                <!-- 查询表单div -->
				<div id="queryForm">
					<form class="inline" action="/customer/advanceaccountList" method="post">
					订单号：<input type="text" name="orderId" size="18" value="${advanceAccountFrom.orderId}" >
					注册名：<input type="text" name="customerName" size="18" value="${advanceAccountFrom.customerName}">
					<button type="submit">查询</button><br>
					开始时间：<input type="text" name="startDate" size="18" value="${advanceAccountFrom.startDateString}"  bind="datepicker">
					截止时间：<input type="text" name="endDate" size="18" value="${advanceAccountFrom.endDateString}"  bind="datepicker">
					
					
					</form>
				</div>
                <!-- 查询结果展示div -->
                <div>
                 <c:if test="${pagination!=null}">
                 <winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
                 </c:if>
                 <table class="list-table">
                 <c:if test="${pagination!=null}">
                 <tr>
                 <th>订单号</th><th>注册名</th><th>交易时间</th><th>收入</th><th>支出</th><th>余额</th><th>创建人</th><th>原因</th>
                 </tr>
                 </c:if>
                 <c:forEach var="customerAccountDetail" items="${customerAccountDetails}" varStatus="i">
                 <tr>
                 <td><a href="${contextPath}/order/${customerAccountDetail.order.id}?view=detail">${customerAccountDetail.order.id}</a></td>
                 <td>${customerAccountDetail.account.customer.name}</td>
                 <td>${customerAccountDetail.useTime }</td>
                 <c:if test="${customerAccountDetail.amount>=0}">
                 <td>${customerAccountDetail.amount }</td>
                 <td></td>
                 </c:if>
                 <c:if test="${customerAccountDetail.amount<=0}">
                 <td></td>
                 <td>${-(customerAccountDetail.amount)}</td>
                 </c:if>
                 <td>${customerAccountDetail.balance }</td>
                 <td>${customerAccountDetail.operator.realName }</td>
                 <td>${customerAccountDetail.type.name}</td>             
                 
                 </tr>
                 </c:forEach>
                  <c:if test="${pagination!=null&&pagination.pageCount!=0}">
                 <tr>
                 <th>订单号</th><th>注册名</th><th>交易时间</th><th>收入</th><th>支出</th><th>余额</th><th>创建人</th><th>原因</th>
                 </tr>
                 </c:if>
                 </table>
                 <c:if test="${pagination!=null&&pagination.pageCount!=0}">
                 <winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
                 </c:if>
                </div>

			</div>
		</div>
		<div class="loading">数据处理中... 请稍候</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>