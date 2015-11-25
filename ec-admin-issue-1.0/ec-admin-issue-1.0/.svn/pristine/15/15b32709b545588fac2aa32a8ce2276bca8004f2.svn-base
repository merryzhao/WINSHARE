<%@page pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
#productInfo {
	width: 140px;
	height: 80px;
}
</style>
<%@include file="../snippets/meta.jsp"%>
<title>买商品免运费活动</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="promotion_error"></div>
				<form action="" >
					<table class="list-table" >
						<tr>
							<td align="right">活动编号:</td>
							<td> ${promotion.id }</td>
						</tr>
						<tr>
							<td align="right">活动标题:</td>
							<td> ${promotion.title }</td>
						</tr>
						<tr>
							<td align="right">活动类型:</td>
							<td> ${promotion.type.name }</td>
						</tr>
						<tr>
							<td align="right">活动有效期:</td>
							<td> <fmt:formatDate value="${promotion.startDate }" type="date"/>
								<fmt:formatDate value="${promotion.startDate }" type="time"/>
								~ <fmt:formatDate value="${promotion.endDate }" type="date"/>
								<fmt:formatDate value="${promotion.endDate }" type="time"/></td>
						</tr>
						<tr>
							<td align="right">订单支付时间:</td>
							<td> ${promotion.effectiveTime }小时</td>
						</tr>
						<tr>
							<td align="right">创建人:</td>
							<td> ${promotion.createUser.name }</td>
						</tr>
						<tr>
							<td align="right">创建时间:</td>
							<td> <fmt:formatDate value="${promotion.createTime}" type="both" /></td>
						</tr>
						<tr>
							<td align="right">最后更新者:</td>
							<td> ${promotion.assessor.name }</td>
						</tr>
						<tr>
							<td align="right">最后更新时间:</td>
							<td> <fmt:formatDate value="${promotion.assessTime}" type="both" /></td>
						</tr>
						<tr>
							<td align="right">活动描述:</td>
							<td> ${promotion.description }</td>
						</tr>
						<tr>
							<td align="right">促销语:</td>
							<td> ${promotion.advert }</td>
						</tr>
						</table>
						<hr />
						<table class="list-table" id="productlist">
							<caption>活动规则</caption>
							<tr>
								<th>商品编码</th>
								<th>商品名称</th>
								<th>商品类别</th>
								<th>卖家</th>
								<th>储配方式</th>
								<th>码洋</th>
								<th>实洋</th>
							</tr>
							<c:forEach items="${promotion.productRules}" var="rule">
								<tr>
									<td>${rule.productSale.id}</td>
									<td>${rule.productSale.product.name}</td>
									<td>${rule.productSale.product.category.name}</td>
									<td>${rule.productSale.shop.name}</td>
									<td>${rule.productSale.storageType.name}</td>
									<td>${rule.productSale.product.listPrice}</td>
									<td>${rule.productSale.salePrice}</td>
								</tr>
							</c:forEach>
							<tfoot>
								<tr>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>商品类别</th>
									<th>卖家</th>
									<th>储配方式</th>
									<th>码洋</th>
									<th>实洋</th>
								</tr>
							</tfoot>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/invoke_parent.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery-ui-timepicker-addon.js"></script>
</body>
</html>