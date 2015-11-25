<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>退货包件信息查询</title>
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
				<h4>退货包件信息详情</h4>
				<!-- 包件信息 -->
				<div  class="entering_table">
					<table class="entering_table" style="border-collapse:separate; border-spacing:10px;">
						<tr>
							<td colspan="6">包件类型：
								<c:if test="${returnOrderPackage.type == '1'}">零售</c:if>
								<c:if test="${returnOrderPackage.type == '2'}">供货</c:if>
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
								${returnOrderPackage.expressid }
							</td>
							<td align="right">运单应收包数：</td>
							<td>
								${returnOrderPackage.shouldQuantity }
							</td>
							<td align="right">发件人电话：</td>
							<td>
								${returnOrderPackage.phone }
							</td>
						</tr>
						<tr>
							<td align="right">退货签收时间：<label class="red">*</label></td>
							<td>
								<fmt:formatDate value="${returnOrderPackage.sSignTime }"
												pattern="yyyy-MM-dd HH" />时
							</td>
							<td align="right">运单实收包数：</td>
							<td>
								${returnOrderPackage.realQuantity }
							</td>
							<td align="right">发件人地址：</td>
							<td colspan="3">
								${returnOrderPackage.address }
							</td>
						</tr>
						<tr>
							<td align="right">退货签收人：<label class="red">*</label></td>
							<td>
								${returnOrderPackage.sSignName }
							</td>
							<td align="right">暂存库位：</td>
							<td>
								${returnOrderPackage.storageLocation }
							</td>
							<td align="right">承运商：</td>
							<td>
								${returnOrderPackage.carrier }
							</td>
						</tr>
						<tr>
							<td align="right">退货类型：<label class="red">*</label></td>
							<td>
								${returnOrderPackage.returntype.name }
							</td>
							<td align="right">发件人：<label class="red">*</label></td>
							<td>
								${returnOrderPackage.customer }
							</td>
							<td align="right">包件揽收时间：</td>
							<td>
								<fmt:formatDate value="${returnOrderPackage.expressTime }"
												pattern="yyyy-MM-dd" />
							</td>
						</tr>
						<c:if test="${returnOrderPackage.type == '2' }">
							<tr>
								<td align="right" class="property">渠道退货单号：</td>
								<td colspan="5">
									<c:forEach var="returnOrderPackageRelate" items="${returnOrderPackage.returnOrderPackageRelateList }" varStatus="sta">
										<c:if test="${returnOrderPackageRelate.status == 2 }">
											<c:if test="${!sta.first}">,</c:if>${returnOrderPackageRelate.relateid }
										</c:if>
									</c:forEach>
								</td>
							</tr>
						</c:if>
						<tr>
							<td align="right" class="property">备注：</td>
							<td colspan="5">
								${returnOrderPackage.remark }
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
							<th>待处理数量</th>
						</tr>
						<tbody>
							<c:if test="${!empty packageitemlist }">
								<c:forEach var="packageItem" items="${packageitemlist }">
									<tr>
										<td>${packageItem.eccode }</td>
										<td>${packageItem.isbn }</td>
										<td>${packageItem.name }</td>
										<td>${packageItem.listprice }</td>
										<td>${packageItem.quantity }</td>
										<td>${packageItem.quantity-packageItem.dealQuantity }</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
					<div><button onclick="window.history.go(-1)">返回</button></div>
				</div>
			</div>
		</div>
	</div>
<%@include file="../snippets/scripts.jsp"%>
</body>
</html>
