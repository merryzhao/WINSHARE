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

#categoryPriceEdit p span {
	margin: 15px 20px 5px 0;
}

#categoryPriceEdit p {
	margin: 5px 0 0 5px;
}

#saleprice {
	margin-left: 200px;
}

#categoryPriceEdit textarea {
	width: 150px;
	height: 50px;
}
</style>
<title>修改促销活动-类别调价</title>
<%@include file="../snippets/meta.jsp"%>
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
					action="/promotion/updateCategoryPrice?id=${promotion.id }"
					enctype="multipart/form-data"
					onsubmit="return validateCategoryPrice();" method=post>
					<div id="categoryPriceEdit">
						<p>
							<span>活动编号:${promotion.id }</span><span>活动标题:<input
								type=text name=promotionTitle value=${promotion.title } /> </span>
						</p>
						<p>
							<span>活动类型:${promotion.type.name }</span><span>活动有效期:<input
								type="text" id="promotionStartDate" readonly="readonly"
								name="promotionStartDate"
								value="<fmt:formatDate value="${promotion.startDate }" type="date"/>">时间<input
								value="<fmt:formatDate value="${promotion.startDate }" pattern="HH:mm" type="time"/>"
								type="text" id="promotionStartTime" readonly="readonly"
								name="promotionStartTime">~ <input readonly="readonly"
								type="text" name="promotionEndDate" id="promotionEndDate"
								value="<fmt:formatDate value="${promotion.endDate }" type="date"/>">时间<input
								value="<fmt:formatDate value="${promotion.endDate }" pattern="HH:mm" type="time"/>"
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
							<span>促销语:<input name="advert" type="text"
								value="${promotion.advert }" /> </span><span><%@include
									file="./img.jsp"%> </span>
						</p>
						<p>
							<b>活动规则</b>
						</p>
						<hr />
						<p>
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
										id='delete' class='deleteseller' href='javascript:void(0);'>删除</a>
									</label> <input type='hidden' name='sellers' value='${seller2.id }'>
								</div>
							</c:forEach>
						</div>
						</p>
						<p>
							选择分类：
							<button type=button id=show_tree>添加</button>
							<select id=discount_methods><option>--请选择--</option>
								<option value=26001>统一折扣</option>
								<option value=26002>只下调折扣</option>
								<option value=26003>只上调折扣</option>
							</select> 折扣:<input type=text id=discount size=5 />
							<button type=button id=change_discount>修改</button>
						</p>
						<table class="list-table" id="categorylist">
							<tr>
								<th><input type=checkbox id=quanxuan style="margin: 0px;" />全选<input
									type=checkbox id=fanxuan style="margin: 0px;" />反选</th>
								<th>分类</th>
								<th>折扣方式</th>
								<th>折扣(如70)</th>
								<th>操作</th>
							</tr>

							<c:forEach items="${promotion.productRules }" var="rule">
								<tr class="selectData">
									<td><input type=checkbox name=chk /><input type=hidden
										name=categorys value=${rule.category.id } /></td>
									<td>${rule.category.allName }</td>
									<td><select name=categoryDisTypes class=discount_method><option
												value=26001
												<c:if test="${rule.categoryDisType.id==26001}">selected</c:if>>统一折扣</option>
											<option value=26002
												<c:if test="${rule.categoryDisType.id==26002}">selected</c:if>>只下调折扣</option>
											<option value=26003
												<c:if test="${rule.categoryDisType.id==26003}">selected</c:if>>只上调折扣</option>
									</select></td>
									<td><input name=categoryDiscounts type=text class=discount
										size=5 value=${rule.categoryDiscount } /></td>
									<td><a href=javascript:void(0); class=del>删除</a></td>
								</tr>
							</c:forEach>

							<tfoot>
								<tr>
									<th></th>
									<th>分类</th>
									<th>折扣方式</th>
									<th>折扣(如70)</th>
									<th>操作</th>
								</tr>
							</tfoot>
						</table>
						<div id="categoryDiv">
							<ul id="category_tree" class="tree"></ul>
							<br />
							<button type=button onclick="insertNodes()" id=getChecktree>确定</button>
						</div>
						<button type="submit">修改</button>
						<button type="reset">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/_new.js"></script>
	<link type="text/css"
		href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
		rel="stylesheet" />
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/tree/category_tree.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/invoke_parent.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/category_priceedit.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery-ui-timepicker-addon.js"></script>
</body>
</html>