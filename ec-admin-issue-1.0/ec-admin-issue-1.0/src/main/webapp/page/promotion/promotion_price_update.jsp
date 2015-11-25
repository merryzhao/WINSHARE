<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>修改促销活动-满省</title>
<!-- 引入CSS -->
<link type="text/css"
	href="${pageContext.request.contextPath}/css/promotion/_new.css"
	rel="stylesheet" />
<!-- 引入JS -->
</head>
<c:set var="minamount" value=""></c:set>
<c:set var="amount" value=""></c:set>
<c:forEach var="orderRule" items="${promotion.orderRules}">
	<c:set var="minamount" value="${minamount}${orderRule.minAmount}_"></c:set>
	<c:set var="amount" value="${amount}${orderRule.amount}_"></c:set>
</c:forEach>
<body
	onload="loadingSelect('${promotion.manner.id}','${minamount}','${amount}');">
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<iframe style="display: none;" name="myiframe"></iframe>
				<div id="promotion_error"></div>
				<!-- 修改内容 -->
				<div>
					<form target="myiframe" action="/promotion/orderPriceUpdate"
						method="post" id="promotionOPUpdateform"
						enctype="multipart/form-data">
						<!-- 上半部分 -->
						<div>
							<h4>修改促销活动-满省</h4>
							<hr>
							<input type="hidden" name="promotionId" value="${promotion.id }">
							<table>
								<tr>
									<td>活动标题：</td>
									<td><input name="promotionTitle" id="promotionTitle"
										type="text" value="${promotion.title }"></td>
								</tr>
								<tr>
									<td>活动描述：</td>
									<td><textarea id="promotiondescribe"
											name="promotionDescription">${promotion.description }</textarea>
									</td>
								</tr>
								<tr>
									<td>活动类型：</td>
									<td>${promotion.type.name }</td>
								</tr>
								<tr>
									<td>活动有效期：</td>
									<td><input type="text" id="promotionStartDate"
										readonly="readonly"
										value="<fmt:formatDate value="${promotion.startDate }" type="date"/>"
										name="promotionStartDate"><input
										value="<fmt:formatDate value="${promotion.startDate }" pattern="HH:mm" type="time"/>"
										type="text" id="promotionStartTime" readonly="readonly"
										name="promotionStartTime">~<input readonly="readonly"
										type="text" name="promotionEndDate" id="promotionEndDate"
										value="<fmt:formatDate value="${promotion.endDate }" type="date"/>"><input
										value="<fmt:formatDate value="${promotion.endDate }" pattern="HH:mm" type="time"/>"
										type="text" id="promotionEndTime" readonly="readonly"
										name="promotionEndTime"></td>
								</tr>
								<tr>
									<td>订单支付时间：</td>
									<td><input type="text" name="effectivetime"
										id="effectivetime" value="${promotion.effectiveTime }"
										onkeyup="this.value=this.value.replace(/\D/g,'')" />小时</td>
								</tr>
								<tr>
									<td>活动促销语：</td>
									<td><input name="advert" type="text"
										value="${promotion.advert }" /></td>

									<td>宣传图标：</td>
									<td>
										<div id="preview"
											style="display: inline; filter: progid : DXImageTransform.Microsoft.AlphaImageLoader ( sizingMethod = scale );">
											<img id="detailimg"
												<c:if test="${empty  promotion.advertImage }">src=/imgs/promotion/default.jpg </c:if>
												<c:if test="${!empty  promotion.advertImage }">src=/imgs/promotion/${promotion.advertImage } </c:if>
												width="100" height="100">
										</div>
										<input type="file" id="picPath" name="localfile"
										onChange="previewImage('preview',this,100,100);" />
									</td>
								</tr>
								<tr>
									<td>活动专题链接：</td>
									<td colspan="3"><input name="advertUrl" type="text"
										value="${promotion.advertUrl }" /></td>
								</tr>
								<tr>
									<td></td>
								</tr>
							</table>
						</div>
						<!-- 下半部分 -->
						<div>
							<!-- 选择卖家 -->
							<div class="general">
								<label>选择卖家</label> <select id="seller_select">
									<c:forEach items="${sellerList }" var="seller">
										<option value="${seller.id }">${seller.name }</option>
									</c:forEach>
								</select>
								<button type="button" onclick="orderPriceAddSeller();">添加</button>
							</div>
							<!-- 添加的卖家 -->
							<div id="seller_list" class="sellers">
								<c:forEach items="${sellers }" var="seller2">
									<div class='seller'>
										<label class='text'> ${seller2.name } </label> <label><a
											id='delete' class='deleteseller' href='javascript:void(0);'>删除</a>
										</label> <input type='hidden' name='orderPricesellers'
											value='${seller2.id }'>
									</div>
								</c:forEach>
							</div>
						</div>
						<!-- 促销方式 -->
						<div class="promotion_type">
							<div>
								<label>促销方式：</label> <input type="radio" name="orderPricemanner"
									value="0" checked="checked"><label>普通优惠</label> <input
									type="radio" name="orderPricemanner" value="1"><label>梯度优惠</label>
							</div>
							<div class="promotion_type_price">
								<div id="general_price_promotion" class="general"></div>
								<div id="grads_price_promotion" class="general"></div>
							</div>
						</div>
				</div>
				<div>
					<button type="button" onclick="orderPriceUpdateSumbit();">保存</button>
				</div>
				</form>
			</div>
		</div>
	</div>
	<div class="loading">数据处理中... 请稍候</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/promotion/_new.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/promotion/new_validate.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/invoke_parent.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery-ui-timepicker-addon.js"></script>
</body>
</html>