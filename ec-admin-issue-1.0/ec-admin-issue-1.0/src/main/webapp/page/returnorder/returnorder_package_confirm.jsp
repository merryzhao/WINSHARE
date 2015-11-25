<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>退货包件信息确认</title>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
div.entering_table {
	background: #ecf5ff;
	margin-top: 25px;
	margin-bottom: 25px;
	border: 1px solid #6293bb;
}

table.entering_table {
	width: 90%;
	margin-left: 5%;
	margin-right: 5%;
	margin-top: 10px;
	margin-bottom: 10px;
}

table.entering_table td.property {
	text-align: right;
}

table.entering_table .title {
	width: 5%;
}

table.entering_table .property {
	width: 10%;
}

table.entering_table .input {
	width: 10%;
}

table.entering_table .longinput {
	width: 20%;
}
label.red {
	color: red;
}

input.long {
	width: 98%;
}

label.error {
	border: none;
	padding: 0.1em;
	color: red;
	font-weight: normal;
	background: none;
	padding-left: 16px;
}

td label.error {
	display: none !important;
}
</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<!-- 图片 -->
				<div>
					<img class="img_width"
						src="${pageContext.request.contextPath}/imgs/returnorder/confirm.jpg">
				</div>
				<h4>退货包件信息确认</h4>
				<!-- 包件信息 -->
				<div  class="entering_table">
					<table class="entering_table" style="border-collapse:separate; border-spacing:10px;">
						<tr>
							<td colspan="6">包件类型：
								<span>${type }</span>
							</td>
						</tr>
						<tr>
							<td colspan="6"><HR width="100%" SIZE=1>
							</td>
						</tr>
						<!-- 表单布局 -->
						<colgroup>
							<col class="property" />
							<col class="input" />
							<col class="property" />
							<col class="input" />
							<col class="property" />
							<col class="input" />
						</colgroup>
						<!--数据输入 -->
						<tr>
							<td align="right">包件运单号：<label class="red">*</label></td>
							<td>
								${packageform.expressid }
							</td>
							<td align="right">运单应收包数：</td>
							<td>
								${packageform.shouldquantity }
							</td>
							<td align="right">发件人电话：</td>
							<td>
								${packageform.phone }
							</td>
						</tr>
						<tr>
							<td align="right">退货签收时间：<label class="red">*</label></td>
							<td>
								${packageform.signtime }
							</td>
							<td align="right">运单实收包数：</td>
							<td>
								${packageform.realquantity }
							</td>
							<td align="right">发件人地址：</td>
							<td colspan="3">
								${packageform.address }
							</td>
						</tr>
						<tr>
							<td align="right">退货签收人：<label class="red">*</label></td>
							<td>
								${packageform.signname }
							</td>
							<td align="right">暂存库位：</td>
							<td>
								${packageform.storagelocation }
							</td>
							<td align="right">承运商：</td>
							<td>
								${packageform.carrier }
							</td>
						</tr>
						<tr>
							<td align="right">退货类型：<label class="red">*</label></td>
							<td>
								${packageform.returnType }
							</td>
							<td align="right">发件人：<label class="red">*</label></td>
							<td>
								${packageform.customer }
							</td>
							<td align="right">包件揽收时间：</td>
							<td>
								${packageform.expresstime }
							</td>
						</tr>
						<c:if test="${packageform.type == '2' }">
							<tr>
								<td align="right" class="property">渠道退货单号：</td>
								<td colspan="5">
									${packageform.returnid }
								</td>
							</tr>
						</c:if>
						<tr>
							<td align="right" class="property">备注：</td>
							<td colspan="5">
								${packageform.remark }
							</td>
						</tr>
					</table>
				</div>
				<!-- 商品信息 -->
				<div>
					<table class="list-table" style="">
						<tr>
							<th>商品编号</th>
							<th>ISBN</th>
							<th>商品名称</th>
							<th>码洋</th>
							<th>实收数量</th>
							<th>暂存库位</th>
						</tr>
						<tbody>
							<c:if test="${!empty packageitemlist }">
								<c:forEach var="packageItem" items="${packageitemlist }">
									<tr>
										<td>${packageItem.productsale.id }</td>
										<td>${packageItem.productsale.product.barcode }</td>
										<td>${packageItem.productsale.sellName }</td>
										<td>${packageItem.productsale.product.listPrice }</td>
										<td>${packageItem.quantity }</td>
										<td>${packageItem.location }</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
					<div>
						<form action="/returnorder/packagenew" method="post">
							<input type="hidden" name="type" value="${packageform.type }" />
							<input type="hidden" name="expressid" value="${packageform.expressid }" />
							<input type="hidden" name="shouldquantity" value="${packageform.shouldquantity }" />
							<input type="hidden" name="phone" value="${packageform.phone }" />
							<input type="hidden" name="signtime" value="${packageform.signtime }" />
							<input type="hidden" name="realquantity" value="${packageform.realquantity }" />
							<input type="hidden" name="address" value="${packageform.address }" />
							<input type="hidden" name="signname" value="${packageform.signname }" />
							<input type="hidden" name="storagelocation" value="${packageform.storagelocation }" />
							<input type="hidden" name="carrier" value="${packageform.carrier }" />
							<input type="hidden" name="returnType" value="${packageform.returnType }" />
							<input type="hidden" name="customer" value="${packageform.customer }" />
							<input type="hidden" name="expresstime" value="${packageform.expresstime }" />
							<input type="hidden" name="returnid" value="${packageform.returnid }" />
							<input type="hidden" name="remark" value="${packageform.remark }" />
							
							<c:if test="${!empty packageitemlist }">
								<c:forEach var="packageItem" items="${packageitemlist }">
									<input type="hidden" name="productSale" value="${packageItem.productsale.id }" />
									<input type="hidden" name="quantity" value="${packageItem.quantity }" />
									<input type="hidden" name="locations" value="${packageItem.location }" />
								</c:forEach>
							</c:if>
							
							<button type="button" onclick="history.go(-1);">上一步</button><button type="submit">提交</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
<%@include file="../snippets/scripts.jsp"%>
</body>
</html>
