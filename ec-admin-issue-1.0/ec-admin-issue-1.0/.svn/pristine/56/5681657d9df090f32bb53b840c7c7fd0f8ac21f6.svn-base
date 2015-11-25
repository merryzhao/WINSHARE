<%@page pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<link type="text/css" href="${pageContext.request.contextPath}/css/promotion/_new.css" rel="stylesheet" />
<title>购买指定商品满省活动</title>
<style type="text/css">
#productInfo {
	width: 140px;
	height: 80px;
}

#singlePrice p {
	margin: 10px 0 5px 0;
}

#singlePrice p input,select,a {
	margin: 0 0 0 5px;
}

#saleprice {
	margin-left: 200px;
}
</style>
</head>
<c:set var="minamount" value=""></c:set>
<c:set var="amount" value=""></c:set>
<c:forEach var="orderRule" items="${promotion.orderRules}">
	<c:set var="minamount" value="${minamount}${orderRule.minAmount}_"></c:set>
	<c:set var="amount" value="${amount}${orderRule.amount}_"></c:set>
</c:forEach>
<body onload="loadingSelect('${promotion.manner.id}','${minamount}','${amount}');">
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
					<form target="myiframe" action="/promotion/updateSpecifyProductPreferential"
						method="post"  onsubmit="return specifyProductPerferentialUpdate();" enctype="multipart/form-data">
						<!-- 上半部分 -->
						<div>
							<h4>修改促销活动-购买指定商品满省</h4>
							<hr>
							<input type="hidden" name="id" value="${promotion.id }">
							<table>
								<tr>
									<td>活动标题：</td>
									<td><input name="promotionTitle" id="promotionTitle"
										type="text" value="${promotion.title }"></td>
								</tr>
								<tr>
									<td>活动描述：</td>
									<td><textarea id="promotiondescribe"
											name="promotionDescription" style="height:30px;">${promotion.description }</textarea>
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
						<!-- 促销方式 -->
						<div class="promotion_type">
							<div>
								<label>促销方式：</label> <input type="radio" name="orderPricemanner"
									value="0" checked="checked"><label>普通优惠</label> <input
									type="radio" name="orderPricemanner" value="1"><label>梯度优惠</label>
							</div>
							<div class="promotion_type_price">
								<div id="general_price_promotion" class="general" style="height:auto;"></div>
								<div id="grads_price_promotion" class="general" style="height:auto;"></div>
							</div>
						</div><br/>
						<!-- 促销商品-->
						<p>
							<b>应用商品</b>
							<a href="javascript:void();" id="single_product_addProduct">添加商品</a>
							<a href="javascript:void();" id="importExcel">导入Excel</a>
							<a href="/excel/template/promotion_up_products.xls">下载模板</a> 
						</p>
						<iframe id="iframe" name="iframe" style="display: none"></iframe>
						<div id="singlePrice">
						<table class="list-table" id="productlist">
							<tr>
								<th>&nbsp;</th>
								<th><input type=checkbox id=quanxuan style="margin: 0px;" />全选<input
									type=checkbox id=fanxuan style="margin: 0px;" />反选</th>
								<th>商品编码</th>
								<th>商品名称</th>
								<th>商品类别</th>
								<th>卖家</th>
								<th>储配方式</th>
								<th>码洋</th>
								<th>实洋</th>
								<th>操作</th>
							</tr>
							<c:set var="index" value="0"></c:set>
							<c:forEach items="${promotion.productRules}" var="rule" varStatus="status">
							<c:set var="index" value="${status.index + 1}"/>
								<tr>
									<td>${status.index + 1}</td>
									<td><input type=checkbox name=chk /><input type=hidden
										name=productSaleIds value=${rule.productSale.id } /></td>
									<td>${rule.productSale.id}</td>
									<td>${rule.productSale.product.name}</td>
									<td>${rule.productSale.product.category.name}</td>
									<td>${rule.productSale.shop.name}</td>
									<td>${rule.productSale.storageType.name}</td>
									<td><label class=price>${rule.productSale.product.listPrice}</label></td>
									<td>${rule.productSale.salePrice}</td>
									<td>
										<a href=javascript:void(0); class=del>删除</a>
									</td>
								</tr> 
							</c:forEach>
							
							<tfoot>
								<tr>
									<th>&nbsp;</th>
									<th></th>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>商品类别</th>
									<th>卖家</th>
									<th>储配方式</th>
									<th>码洋</th>
									<th>实洋</th>
									<th>操作</th>
								</tr>
							</tfoot>
						</table>
						</div>
						<input type="hidden" id="index" value="${index}">
						<button type="submit">修改</button>
						<div id="addProductDiv"></div>
					</form>
				</div>
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
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/promotion/specifyproduct_preferential_update.js"></script>
</body>
</html>