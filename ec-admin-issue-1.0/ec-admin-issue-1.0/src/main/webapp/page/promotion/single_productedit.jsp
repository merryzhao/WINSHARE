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

#singlePriceEdit p span {
	margin: 15px 20px 5px 0;
}

#singlePriceEdit p {
	margin: 5px 0 0 5px;
}

#saleprice {
	margin-left: 200px;
}

#singlePriceEdit textarea {
	width: 150px;
	height: 50px;
}
</style>
<%@include file="../snippets/meta.jsp"%>
<title>修改单品调价活动</title>
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
				<iframe style="display: none;" name="myiframe"></iframe>
				<div id="promotion_error"></div>
				<form target="myiframe"
					action="/promotion/updateSingleProduct?id=${promotion.id }"
					enctype="multipart/form-data"
					onsubmit="return validateSingleProduct();" method=post>
					<div id="singlePriceEdit">
						<p>
							<span>活动编号:${promotion.id }</span><span>活动标题:<input
								type=text name=promotionTitle value=${promotion.title } /> </span>
						</p>
						<p>
							<span>活动类型:${promotion.type.name }</span><span>活动有效期:<input
								type="text" id="promotionStartDate" readonly="readonly"
								name="promotionStartDate"
								value="<fmt:formatDate value="${promotion.startDate }" type="date"/>"><input
								pattern="HH:mm"
								value=<fmt:formatDate value="${promotion.startDate }" type="time"/>
								type="text" id="promotionStartTime" readonly="readonly"
								name="promotionStartTime">~ <input readonly="readonly"
								type="text" name="promotionEndDate" id="promotionEndDate"
								value="<fmt:formatDate value="${promotion.endDate }" type="date"/>"><input
								pattern="HH:mm"
								value="<fmt:formatDate value="${promotion.endDate }" type="time"/>"
								type="text" id="promotionEndTime" readonly="readonly"
								name="promotionEndTime"> </span>
						</p>
						<p>
							订单支付时间： <input type="text" name="effectivetime"
								id="effectivetime" value="${promotion.effectiveTime }"
								onkeyup="this.value=this.value.replace(/\D/g,'')" />小时
						</p>
						<p>
							<span>创建人:${promotion.createUser.name }</span><span>创建时间:<fmt:formatDate
									value="${promotion.createTime}" type="both" /> </span>
						</p>
						<p>
							<span>最后更新者:${promotion.assessor.name }</span><span>最后更新时间:<fmt:formatDate
									value="${promotion.assessTime}" type="both" /> </span>
						</p>
						<p>
							<label style="vertical-align: top;">活动描述:</label>
							<textarea id="promotiondescribe" name="promotionDescription">${promotion.description }</textarea>
						</p>
						<p>
							<c:if test="${empty promotion.advertImage }">
								<c:set value="default.jpg" var="p"></c:set>
							</c:if>
							<span>促销语:<input name="advert" type="text"
								value="${promotion.advert }" /> </span><span><%@include
									file="./img.jsp"%></span>
						</p>
						<p>
							<span>活动专题链接:<input name="advertUrl" type="text" value="${promotion.advertUrl }" />
							</span>
						</p>
						<p>
							<b>活动规则</b>
						</p>
						<hr />
						<p>
							<b>应用商品</b>
							<a href="javascript:void(0);" id="single_product_addProduct">添加商品</a>
							<a href="javascript:void(0);" id="importExcel">导入Excel</a>
							<a href="/excel/template/promotion_up_products.xls">下载模板</a> 
							<span id="saleprice">
								实洋:<input type="text" size=10 id=SetSalePrice /> 
								折扣:<input type="text" size=10 id=SetDiscount />
								单用户每天限购数量：<input type="text" size=10 id=SetNum />
								每天限购总数量：<input type="text" size=10 id=SetNums />
								<button type=button id=change>修改</button> 
								<a href="javascript:void(0);" id=batchreset>批量重置</a> 
							</span>
						</p>
						<iframe id="iframe" name="iframe" style="display: none"></iframe>
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
								<th>实洋(原实洋)</th>
								<th>折扣(如70)</th>
								<th>单用户每天限购数量<br/>(0:不限制)</th>
								<th>每天限购总数量<br/>(0:不限制)</th>
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
									<td><label class=price>${rule.productSale.product.listPrice}</label>
									</td>
									<td><input type=text name=salePrice class=saleprice type=text onblur=setDiscount(this) size=4
										value=${rule.salePrice } />(<label class="dbsaleprice">${rule.productSale.salePrice}</label>)</td>
									<td><input class=discount type=text onblur=setSalePrice(this) size=3
										value="<fmt:formatNumber pattern="#" value="${rule.salePrice/rule.productSale.product.listPrice*100 } " />" />
									</td>
									<td><input name=num class=num type=text size=3 value="${rule.productNum} " />
									<td>
									  <c:if test="${! empty rule.productNums }">
									   <input name=nums class=nums type=text size=3 value="${rule.productNums} " />
									  </c:if>
									   <c:if test="${empty rule.productNums }">
									   <input name=nums class=nums type=text size=3 value="0" />
									  </c:if> 
									</td>
									<td>
										<a href=javascript:void(0); class=reset>重置</a> 
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
									<th>实洋(原实洋)</th>
									<th>折扣(如70)</th>
									<th>单用户每天限购数量<br/>(0:不限制)</th>
								    <th>每天限购总数量<br/>(0:不限制)</th>
									<th>操作</th>
								</tr>
							</tfoot>
						</table>
						<input type="hidden" id="index" value="${index}">
						<div id="addProductDiv"></div>
						<button type="submit">修改</button>
						<button type="reset">取消</button>
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
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/single_productedit.js"></script>
</body>
</html>