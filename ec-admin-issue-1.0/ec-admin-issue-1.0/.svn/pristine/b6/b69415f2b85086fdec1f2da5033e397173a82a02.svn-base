<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/promotion/_new.css"
	rel="stylesheet" />
<%@include file="../snippets/meta.jsp"%>
<title>修改促销活动-买商品赠商品</title>
<style type="text/css">
.idTextArea {
	width: 100px;
	height: 60px;
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
				<c:set var="oldIndex" value="${fn:length(promotion.productRules) }"></c:set>
				<form target="myiframe" enctype="multipart/form-data"
					action="${contextPath}/promotion/productUpdate" method="post"
					id="promotionform" name="promotionform">
					<input type="hidden" name="id" value="${promotion.id }" />
					<table>
						<tr>
							<td>活动编号：</td>
							<td>${promotion.id }</td>
							<td>活动标题：</td>
							<td><input name="promotionTitle" id="promotionTitle"
								value="${promotion.title }" type="text"></td>
						</tr>
						<tr>
							<td>活动类型：</td>
							<td>${promotion.type.name }</td>
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
							<td>创建人：</td>
							<td>${promotion.createUser.name }</td>
							<td>创建时间：</td>
							<td><fmt:formatDate value="${promotion.createTime }"
									type="both" /></td>
						</tr>
						<tr>
							<td>最后更新者：</td>
							<td>${promotion.assessor.name }</td>
							<td>最后更新时间：</td>
							<td><fmt:formatDate value="${promotion.assessTime }"
									type="both" /></td>
						</tr>
						<tr>
							<td>订单支付时间：</td>
							<td><input type="text" name="effectivetime"
								value="${promotion.effectiveTime }" id="effectivetime"
								onkeyup="this.value=this.value.replace(/\D/g,'')" />小时</td>
						</tr>
						<tr>
							<td>单订单可重复享受赠品：</td>
							<c:if test="${promotion.replication }">
								<td><input type="radio" name="isreplication"
									checked="checked" value="true" />是</td>
								<td><input type="radio" name="isreplication" value="false" />否</td>
							</c:if>
							<c:if test="${!promotion.replication }">
								<td><input type="radio" name="isreplication" value="true" />是</td>
								<td><input type="radio" name="isreplication"
									checked="checked" value="false" />否</td>
							</c:if>

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
								value="${promotion.advert }" /></td>
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
					<div>
						应用商品：<a href="javascript:void(0);" id="addProduct">添加商品</a>
					</div>
					<hr />
					<div id="productTable">
						<table class="list-table" id="product_table">
							<colgroup>
								<col class="id" />
								<col class="product_name" />
								<col class="type" />
								<col class="type" />
								<col class="type" />
								<col class="number" />
								<col class="number" />
								<col class="address" />
								<col class="operate" />
							</colgroup>
							<tr>
								<th>商品编号</th>
								<th>商品名称</th>
								<th>商品类别</th>
								<th>卖家</th>
								<th>储配方式</th>
								<th>码洋</th>
								<th>实洋</th>
								<th>数量限制(最小)</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${promotion.productRules }" var="productrRule"
								varStatus="status">
								<c:if test="${productrRule.productType.id==23001 }">
									<tr id="productTr${status.index }">
										<td>${productrRule.productSale.id }</td>
										<td>${productrRule.productSale.product.name }</td>
										<td>${productrRule.productSale.product.sort.name }</td>
										<td>${productrRule.productSale.shop.name }</td>
										<td>${productrRule.productSale.storageType.name }</td>
										<td>${productrRule.productSale.product.listPrice }</td>
										<td>${productrRule.productSale.salePrice }</td>
										<td><input type="text" name="productQuantity"
											value="${productrRule.productNum }" id="productQuantity" />
										</td>
										<td><a href="javascript:void(0);"
											onclick="deleteTr(${status.index},0);">删除</a>
										</td>
										<input type="hidden" name="productSaleIds"
											value="${productrRule.productSale.id}" />
									</tr>
								</c:if>
							</c:forEach>
						</table>

					</div>
					<br />
					<div>
						选择赠品： <a href="javascript:void(0);" id="addGift">添加赠品</a>
					</div>
					<hr />
					<div id="giftTable">
						<table class="list-table" id="gift_table">
							<colgroup>
								<col class="id" />
								<col class="product_name" />
								<col class="number" />
								<col class="operate" />
							</colgroup>
							<tr>
								<th>赠品编号</th>
								<th>赠品名称</th>
								<th>赠送数量</th>
								<th>操作</th>
							</tr>

							<c:forEach items="${promotion.productRules }" var="promotionGift"
								varStatus="status">
								<c:if test="${promotionGift.productType.id==23002 }">
									<tr id="giftTr${status.index}">
										<td>${promotionGift.productSale.id}</td>
										<td>${promotionGift.productSale.product.name}</td>
										<td><input type="text" name="giftQuantity"
											value="${promotionGift.productNum }" id="giftQuantity" />
										</td>
										<td><a href="javascript:void(0);"
											onclick="deleteTr(${status.index},1);">删除</a>
										</td>
										<input type="hidden" name="giftIds"
											value="${promotionGift.productSale.id}" />
									</tr>
								</c:if>
							</c:forEach>

						</table>
					</div>
					<table>
						<tr>
							<td><button type="button" onclick="submitEdit();">保存</button>
							</td>
							<td><button type="button">取消</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="frame-main-inner" id="content"></div>
		</div>
	</div>
	<div id="addProductDialog">
		<table class="addDialog">
			<tr>
				<td><select id="productSelectType">
						<option value="0" selected="selected">商品编号</option>
						<option value="1">ISBN号</option>
						<option value="2">自编码</option>
				</select>
				</td>
				<td><textarea rows="" cols="" id="productTextArea"
						class="idTextArea"></textarea></td>
				<td><font color="red" id="productMessage"></font>
				</td>
			</tr>
		</table>
	</div>
	<div id="addGiftDialog">
		<table class="addDialog">
			<tr>
				<td><select id="giftSelectType">
						<option value="0" selected="selected">商品编号</option>
						<option value="1">ISBN号</option>
						<option value="2">自编码</option>
				</select>
				</td>
				<td><textarea rows="" cols="" id="giftTextArea"
						class="idTextArea"></textarea></td>
				<td><font color="red" id="giftMessage"></font>
				</td>
			</tr>
		</table>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/promotion/new_validate.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/invoke_parent.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript"
		src="${contextPath }/js/promotion/edit_product.js"></script>
</body>
</html>