<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>支付方式列表</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="content-result">
					<button name="addPayment">添加支付方式</button>
					<table class="list-table">
						<colgroup>
							<col class="id" />
							<col class="name" />
							<col class="number" />
							<col class="number" />
							<col class="type" />
							<col class="type" />
							<col class="type" />
							<col class="type" />
							<col class="type" />
							<col class="number" />
							<col class="number" />
							<col class="number" />
							<col class="type" />
							<col class="operate" />
						</colgroup>
						<tr>
							<th>编号</th>
							<th>名称</th>
							<th>收款手续费率</th>
							<th>收款手续费最小收取金额</th>
							<th>收款手续费最小收取方式</th>
							<th>支付类型</th>
							<th>是否有效</th>
							<th>是否前台显示</th>
							<th>是否可退款</th>
							<th>退款最大期限</th>
							<th>退款手续费率</th>
							<th>退款手续费最小金额</th>
							<th>退款手续费最小收取方式</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${payments}" var="payment" varStatus="status">
							<c:set var="type" value="${payment.type}"></c:set>
							<c:if test="${status.index%2==0}"><tr name="paymentAvailable${status.index}"></c:if>
							<c:if test="${status.index%2==1}"><tr class="trs" name="paymentAvailable${status.index}"></c:if>
								<td align="center">${payment.id}</td>
								<td align="center">${payment.name}</td>
								<td align="center">${payment.payFee}<c:if test="${!empty payment.payFee}">%</c:if>
								</td>
								<td align="center">${payment.payFeeMin}<c:if
										test="${!empty payment.payFeeMin}">元</c:if>
								</td>
								<td align="center">${payment.payFeeType.name}</td>
								<td align="center">${type.name}</td>
								<td align="center"><c:if test="${payment.available}">
										<span id="availablestr${status.index}">有效</span>
									</c:if> <c:if test="${!payment.available}">
										<span id="availablestr${status.index}">无效</span>
									</c:if></td>
								<td align="center"><c:if test="${payment.show}">是</c:if> <c:if
										test="${!payment.show}">否</c:if></td>
								<td align="center"><c:if test="${payment.allowRefund}">可退款</c:if> <c:if
										test="${!payment.allowRefund}">不可退款</c:if></td>
								<td align="center"><c:if test="${0!=payment.refundTerm}">${payment.refundTerm}个月</c:if>
									<c:if test="${0==payment.refundTerm}">无限制</c:if></td>
								<td align="center">${payment.refundFee}<c:if
										test="${!empty payment.refundFee}">%</c:if>
								</td>
								<td align="center">${payment.refundFeeMin}<c:if
										test="${!empty payment.refundFeeMin}">元</c:if>
								</td>
								<td align="center">${payment.refundFeeType.name}</td>
								<td><c:if test="${type.id==10001||type.id==10002}">
										<a class="operate-link"
											href="${contextPath}/payment/${payment.id}/edit">修改</a>
										<a class="operate-link" href="#paymentAvailable${status.index}" 
											onclick="changeAvailable('${payment.id}','${status.index}');"><c:if
												test="${payment.available}">
												<span id="availablespan${status.index}">禁用</span>
											</c:if> <c:if test="${!payment.available}">
												<span id="availablespan${status.index}">启用</span>
											</c:if> </a>
									</c:if></td>
							</tr>
							<c:if test="${!empty payment.description}">
								<c:if test="${status.index%2==0}"><tr></c:if>
								<c:if test="${status.index%2==1}"><tr class="trs"></c:if>
									<td></td>
									<td style="color:#ff0000;">描述:</td>
									<td colspan="12" align="left">${payment.description}</td>
								</tr>
							</c:if>
						</c:forEach>
						<tr>
							<th>编号</th>
							<th>名称</th>
							<th>收款手续费率</th>
							<th>收款手续费最小收取金额</th>
							<th>收款手续费最小收取方式</th>
							<th>支付类型</th>
							<th>是否有效</th>
							<th>是否前台显示</th>
							<th>是否可退款</th>
							<th>退款最大期限</th>
							<th>退款手续费率</th>
							<th>退款手续费最小金额</th>
							<th>退款手续费最小收取方式</th>
							<th>操作</th>
						</tr>
					</table>
					<button name="addPayment">添加支付方式</button>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="path" value="${contextPath}"></input>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/payment/payment.js"></script>
</body>
</html>