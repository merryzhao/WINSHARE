<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>退货包件信息录入</title>
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

textarea.orderid {
	vertical-align:top;width: 400px;height: 60px;
}
</style>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
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
				<h4>退货包件信息录入</h4>
				<div>
					<!-- <form target="product" id="myform" action="/returnorder/returnPackageProductUpload"
						method="post" enctype="multipart/form-data"
						onsubmit="return checkFile();">
						<table>
							<tr>
								<td></td>
								<td>商品导入（.xls）<a href="/excel/exportReturnOrderPackageTemplate?format=xls"><font color="red">模板下载</font></a></td>
								<td><input multiple="multiple" type="file" name="file"
									id="productFile" />
								</td>
								<td><button type="submit">导入商品</button>
								</td>
							</tr>
							<tr>
								<td colspan="3"></td>
								<td colspan="3"><span id="information"></span>
								</td>
							</tr>
						</table>
					</form> -->
					
					<div id="tabs">
						<ul>
							<li><a href="#retail" onclick="clearSuppply()">零售</a></li>
							<li><a href="#supply" onclick="clearRetail()">供货</a></li>
						</ul>
						<div id="retail">
							<%@include file="../returnorder/returnorder_package_retail.jsp"%>
						</div>
						<div id="supply">
							<%@include file="../returnorder/returnorder_package_supply.jsp"%>
						</div>
					</div>
					
					<form id="addProductForm" action="/returnorder/addPackageProduct"
						target="product" method="post" onsubmit="return checkAPF();">
						<table>
							<tr>
								<!-- <td>商品编码:</td>
								<td><input type="text" size="14" name="search_productSaleId" />
								</td>
								<td>商家自编码:</td>
								<td><input type="text" size="14" name="search_outerId" />
								</td> -->
								<td>ISBN:</td>
								<td>
									<input id="search_ISBN" type="text" size="14" name="search_ISBN" tabindex="15"/>
								</td>
								<!-- <td>商品名称:</td>
								<td><input type="text" size="14" name="search_productName" />
								</td> -->
								<td><button type="submit" id="addProductButton">添加商品</button>
								</td>
							</tr>
						</table>
					</form>
				
					<form action="/returnorder/packageconfirm" method="post" id="returnorderPackageForm" onsubmit="return checkFormParam()">
						
						<!-- 商品信息  -->
						<div id="accordion">
							<h3>
								<a href="#">商品信息</a>
							</h3>
							<div>
								<table class="list-table" id="product" style="width: 100%">
									<tr>
										<!-- <th width="5%"><input type="checkbox" name="allCheck" id="allCheck"/>全选</th> -->
										<th width="5%">序号</th>
										<th width="15%">商品编号</th>
										<th width="15%">ISBN</th>
										<th width="25%">商品名称</th>
										<th width="10%">码洋</th>
										<th width="10%">实收数量</th>
										<th width="10%">暂存库位</th>
										<th width="10%">操作</th>
									</tr>
	
									<tr id="total">
										<th colspan="8"><a style="color: yellow;" href="#"
											id="clear">清空商品</a></th>
										<!-- <th colspan="2">总数量:<span id="total_number"></span>
										</th>
										<th colspan="3">总码洋:￥<span id="total_money"></span>元</th> -->
									</tr>
								</table>
								<span id="information"></span>
							</div>
						</div>
						<br/>
						<br/>
						
						<!-- 存放包件信息 -->
						<div hidden="hidden">
							<input type="hidden" id="type" name="type" value="1" />
							<input type="hidden" id="expressid_form" name="expressid" >
							<input type="hidden" id="signtime_form" name="signtime" >
							<input type="hidden" id="customer_form" name="customer" >
							<input type="hidden" id="carrier_form" name="carrier" >
							<input type="hidden" id="signname_form" name="signname" >
							<input type="hidden" id="phone_form" name="phone" >
							<input type="hidden" id="expresstime_form" name="expresstime" >
							<input type="hidden" id="returnType_form" name="returnType" >
							<input type="hidden" id="address_form" name="address" >
							<input type="hidden" id="remark_form" name="remark" >
							<input type="hidden" id="returnid_form" name="returnid" >
							<input type="hidden" id="storagelocation_form" name="storagelocation" >
							<input type="hidden" id="shouldquantity_form" name="shouldquantity" >
							<input type="hidden" id="realquantity_form" name="realquantity" >
						</div>
						
						<div>
							<button type="button" id="confirmtosubmit">下一步</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
<iframe name="product" style="display: none;"></iframe>
<%@include file="../snippets/scripts.jsp"%>
<script>
	$(function() {
		$( "#tabs" ).tabs({
			ajaxOptions: {
				error: function( xhr, status, index, anchor ) {
					$( anchor.hash ).html(
						"Couldn't load this tab. We'll try to fix this as soon as possible. " +
						"If this wouldn't be a demo." );
				},cash:true
			}
		});
	});
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.methods.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.bgiframe.min.js"></script>
<script src="${pageContext.request.contextPath}/js/returnorder/returnorderPackage.js"></script>
</body>
</html>
