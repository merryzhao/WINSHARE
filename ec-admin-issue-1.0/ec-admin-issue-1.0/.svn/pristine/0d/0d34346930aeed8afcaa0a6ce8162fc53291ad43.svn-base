<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>修改促销活动-运费减免</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/promotion/_new.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
<style type="text/css">
#oldAreasContent {
	width: 600px;
	max-width: 600px;
}

.oldAreaDiv {
	margin-top: 5px;
	margin-right: 5px;
	background-color: #EEEEDD;
}
</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div id="content">
				<iframe style="display: none;" name="myiframe"></iframe>
				<div id="promotion_error"></div>
				<form target="myiframe" enctype="multipart/form-data"
					action="${contextPath}/promotion/deliveryFeeUpdate" method="post"
					name="promotionform" id="promotionform">
					<input type="hidden" name="id" value="${promotion.id }" />
					<table>
						<tr>
							<td>活动编号：</td>
							<td>${promotion.id }</td>
							<td>活动标题：</td>
							<td><input name="promotionTitle" id="promotionTitle"
								value="${promotion.title }" type="text">
							</td>
						</tr>
						<tr>
							<td>活动类型：</td>
							<td>${promotion.type.name }</td>
							<td>活动有效期：</td>
							<td>活动有效期:<input type="text" id="promotionStartDate"
								readonly="readonly" name="promotionStartDate"
								value="<fmt:formatDate value="${promotion.startDate }" type="date"/>">时间<input
								value=<fmt:formatDate value="${promotion.startDate }" pattern="HH:mm" type="time"/>
								type="text" id="promotionStartTime" readonly="readonly"
								name="promotionStartTime">~ <input readonly="readonly"
								type="text" name="promotionEndDate" id="promotionEndDate"
								value="<fmt:formatDate value="${promotion.endDate }" type="date"/>">时间<input
								value="<fmt:formatDate value="${promotion.endDate }" pattern="HH:mm" type="time"/>"
								type="text" id="promotionEndTime" readonly="readonly"
								name="promotionEndTime">
							</td>
						</tr>
						<tr>
							<td>创建人：</td>
							<td>${promotion.createUser.name }</td>
							<td>创建时间：</td>
							<td><fmt:formatDate value="${promotion.createTime }"
									type="both" />
							</td>
						</tr>
						<tr>
							<td>最后更新者：</td>
							<td>${promotion.assessor.name }</td>
							<td>最后更新时间：</td>
							<td><fmt:formatDate value="${promotion.assessTime }"
									type="both" />
							</td>
						</tr>
						<tr>
							<td>订单支付时间：</td>
							<td><input type="text" name="effectivetime"
								value="${promotion.effectiveTime }" id="effectivetime"
								onkeyup="this.value=this.value.replace(/\D/g,'')" />小时</td>
						</tr>
						<tr>
							<td>活动描述：</td>
							<td><textarea id="promotiondescribe"
									name="promotionDescription">${promotion.description }</textarea>
							</td>
						</tr>
						<tr>
							<td>活动促销语：</td>
							<td><input name="advert" type="text"
								value="${promotion.advert }" />
							</td>
							<td>宣传图标：</td>
							<td colspan="3"><%@include file="./img.jsp"%>
							</td>
						</tr>
						<tr>
							<td>活动专题链接：</td>
							<td colspan="3"><input name="advertUrl" type="text"
								value="${promotion.advertUrl }" />
							</td>
						</tr>
					</table>
					<div>
						<h4>活动规则</h4>
					</div>
					<hr />
					<!-- 选择卖家 -->
					<div class="general">
						<label>选择卖家</label> <select id="seller_select">
							<c:forEach items="${sellerList }" var="seller">
								<option value="${seller.id }">${seller.name }</option>
							</c:forEach>
						</select>
						<button type="button" onclick="addSellers();">添加</button>
					</div>
					<!-- 添加的卖家 -->
					<div id="seller_list" class="sellers">
						<c:forEach items="${sellers }" var="seller2">
							<div class='seller'>
								<label class='text'> ${seller2.name } </label> <label><a
									id='delete' class='delete' href='javascript:void(0);'>删除</a> </label> <input
									type='hidden' name='sellers' value='${seller2.id }'>
							</div>
						</c:forEach>
					</div>
					<div>
						<label>订单基准金额：</label><input type="text" name="minAmount"
							id="minAmount" value="${deliveryfeeOrderRule.minAmount }" />元
					</div>
					<div>
						<label>减免金额：</label><input type="text" name="deliveryfee"
							value="${deliveryfeeOrderRule.deliveryFee }" />元
						<c:if test="${deliveryfeeOrderRule.remitDeliveryFee }">
							<input type="checkbox" id="" checked="checked"
								name="remitdeliveryfee" />减免所有运费
							</c:if>
						<c:if test="${!deliveryfeeOrderRule.remitDeliveryFee }">
							<input type="checkbox" id="" name="remitdeliveryfee" />减免所有运费
							</c:if>
					</div>
					<div>
						<ul id=area_tree class=tree></ul>
					</div>
					<div id="areaInput"></div>
					<br />
					<div>之前所选择的区域：</div>
					<hr />
					<div id="oldAreasContent">
						<c:forEach items="${oldAreas}" var="oldArea" varStatus="status">
							<span class="oldAreaDiv" id="oldAreaDiv${status.index }">
								<label>${oldArea.name}</label> <input type="hidden" name="areas"
								value="${oldArea.id }" /> <a href="javascript:void(0);"
								onclick="deleteDiv(${status.index});">删除</a> </span>
						</c:forEach>
					</div>
					<table>
						<tr>
							<td><button type="button" onclick="submitEdit();">保存</button>
							</td>
							<td><button type="button">取消</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/promotion/new_validate.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/invoke_parent.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/edit_deliveryfee.js"></script>
</body>
</html>