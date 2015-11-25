<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<body>
				<h4>退款凭证查询</h4>
				<div id="content-result">
					<form class="inline" action="/refund/getRefundCredential" method="post">
					<div class="row">
						<div style="float: left;">
							<span>订单号:</span>
							<span>
								<textarea cols="15" rows="2" name="orderIds" style="width: 224px; height: 114px;"></textarea>
							</span>
						</div>
							<table style="margin-left: 320px;">
								<tr>
									<td>支付方式名称:</td>
									<td>
										<select id="paymentId" name="paymentId">
										<option value="0">--------------</option>
										<c:forEach items="${paymentList }" var="payment">
											<option value="${payment.id }" <c:if test="${refundForm.paymentId==payment.id}">selected="selected"</c:if>>
												${payment.name }
											</option>
										</c:forEach>
										</select>
									</td>	
								</tr>
								<tr>
									<td >是否需要处理:</td>
									<td >
									<c:forEach items="${statusList }" var="status">
										<input type="checkbox" name="status" value="${status.id}"
										<c:forEach items="${refundForm.status }" var="refundStatus">
											<c:if test="${refundStatus == status.id }">
												checked="checked"
											</c:if>
										</c:forEach>
										 />${status.name }
									</c:forEach>
									</td>
								</tr>
								<tr>
									<td>退款时间</td>
									<td>
										<input type="text" size="18"  bind="datepicker" value="${refundForm.startTime}"  name="startTime">
										~
										<input type="text" size="18"  bind="datepicker" value="${refundForm.endTime}" name="endTime">
									</td>
								</tr>
								<tr>
									<td>排序</td>
									<td>
										<select id="sort" name="sort">
											<option value="0" <c:if test="${refundForm.sort==0}">selected="selected"</c:if>>创建时间</option>
											<option value="1" <c:if test="${refundForm.sort==1}">selected="selected"</c:if>>退款时间</option>
											<option value="2" <c:if test="${refundForm.sort==2}">selected="selected"</c:if>>支付方式</option>
											<option value="3" <c:if test="${refundForm.sort==3}">selected="selected"</c:if>>退款状态</option>
										</select>
									</td>
								</tr>
							</table>
							<button type="submit">查询</button>
					</div>
				</form>
				<div>
</body>
