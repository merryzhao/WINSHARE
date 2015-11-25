<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
<html>
<head>
<%@include file="../../snippets/meta.jsp"%>
<title>银联手机付款查询</title>
<link rel="stylesheet"
	href="${contextPath}/css/slidingtabs-horizontal.css">
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div>
				<form action="/order/unionPayQuery" method="post">
					订单号：<input type="text" name="orderId" value="${orderId}"><input type="submit" value="查询">
				</form>
					<table class="list-table">
						<tr>
							<th>支付类型</th>
							<th>支付号</th>
							<th>签名信息</th>
							<th>付款金额</th>
							<th>交易时间</th>
							<th>文轩状态</th>
							<th>付款状态</th>
							<th>付款描述</th>
							<th>请求参数</th>
						</tr>
						<tr style="cursor: pointer;">
							<th>${ query.transType}</th>
							<th>${ batchPay.id}</th>
							<th title="${ query.signature}">${ query.signature}</th>
							<th>${ batchPay.totalMoney}</th>
							<th>${ batchPay.createTime}</th>
							<th>${ batchPay.success}</th>
							<th>${ query.respCode}</th>
							<th>${ query.respMsg}</th>
							<th title="${ batchPay.params}">${fn:substring(batchPay.params, 0, 10)}...</th>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../snippets/scripts.jsp"%>
</body>
</html>
