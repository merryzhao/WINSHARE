<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<html>
<head>
<title>新建订单</title>
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

#create input.error {
	border: 2px solid red;
}

td label.error {
	display: none !important;
}

#create select {
	border: 1px solid gray;
	padding: 0px;
}

#create select.error {
	border: 1px solid red;
	padding: 0px;
	margin: 0px;
}

#create input {
	border: 1px solid gray;
	padding: 2px;
}

#create input.error {
	border: 1px solid red;
	padding: 2px;
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
				<h4>订单添加</h4>
				<div>
					<label>库存查检</label><input type="checkbox" id="cbxCheckStock" checked="checked" onclick="return check_stock_change();" /><span style="color: red;">修改后会清空已经添加的商品信息</span>
					<br/>
					<label>下传选择</label>
					<select id="transferSelect" onchange="chooseDc()">
						<option value="35001" selected="selected">下传中启</option>
						<option value="35003">不下传中启</option>
						<option value="35004">下传SAP</option>
					</select>
				</div>
				<div>
					<label>物流中心</label>
					<span class="dc"></span>
					<label>物流系统收货人（客户代码）</label>
					<span><input type="text" id="dccustomer" name="dccustomer"></span>
				</div>
				<div><br />
					<form target="product" id="myform" action="/order/orderAddProduct"
						method="post" enctype="multipart/form-data"
						onsubmit="return checkFile();">
						<table>
							<tr>
								<td></td>
								<td><input multiple="multiple" type="file" name="file"
									id="productFile" />
								</td>
								<td><button type="submit">导入添加商品</button>
								</td>
								<td><a href="/excel/orderAddProductTemplate?format=xls"><font color="red">导入模板有变动，请下载新模板</font></a></td>
							</tr>
							<tr>
								<td colspan="3"></td>
								<td colspan="3"><span id="information"></span>
								</td>
							</tr>
						</table>
					</form>
					<form id="addProductForm" action="/product/orderAddProductSale"
						target="product" method="post" onsubmit="return checkAPF();">
						<span id="search_supplytype"></span>
						<table>
							<tr>
								<td>商品编码:</td>
								<td><input type="text" size="14"
									name="search_productSaleId" />
								</td>
								<td>商家自编码:</td>
								<td><input type="text" size="14" name="search_outerId" />
								</td>
								<td>ISBN:</td>
								<td><input type="text" size="14" name="search_ISBN" />
								</td>
								<td>商品名称:</td>
								<td><input type="text" size="14" name="search_productName" />
								<td>卖家</td>
								<td><select name="search_shop">
										<option value=''>所有</option>
										<c:forEach items="${listShop}" var="shop">
											<option value='${shop.id}'>${shop.shopName }</option>
										</c:forEach>
								</select></td>
								<td colspan="3"><button type="submit">添加商品</button>
								</td>
							</tr>
						</table>
					</form>
					<form id="create" onsubmit="return check_channel();" action="/order/create" method="post">
					<%-- <token:token></token:token> --%>
					<input type="hidden" name="checkStock" id="orderCheckStock" value="true" />
					<input type="hidden" name="transferResultId" id="transferResultId"/>
					<input type="hidden" name="dc" id="orderDc"/>
					<input type="hidden" name="dccustomer" id="orderDcCustomer"/>
						<div id="accordion" style="position: relative;">
							<h3>
								<a href="#">订单商品</a>
							</h3>
							<div>
								<table class="list-table" id="product" style="width: 100%">
									<tr>
										<th width="3%">序号</th>
										<th width="8%">EC商品编号</th>
										<th width="8%">商家自编码</th>
										<th width="14%">商品名称</th>
										<th width="10%">店铺名称</th>
										<th width="6%">状态</th>
										<th width="5%">可用量</th>
										<th width="6%">DC</th>
										<th width="7%">市场价</th>
										<th width="4%">折扣</th>
										<th width="5%">实际售价</th>
										<th width="5%">购买量</th>
										<th width="5%">小计</th>
										<th width="6%">删除</th>
									</tr>

									<tr id="total">
										<th colspan="4"><a style="color: yellow;" href="#"
											id="clear">清空商品</a></th>
										<th colspan="2">总数量:<span id="total_number"></span>
										</th>
										<th colspan="3">总码洋:￥<span id="total_money"></span>元</th>
										<th colspan="5">总实洋:￥<span id="total_sale_money"></span>元</th>
									</tr>
								</table>
								<span id="information"></span>
							</div>
						</div>
						<!-- 新建订单表单提交 -->

						<div>
							<!-- 收货人信息 -->
							<div class="entering_table">
								<table class="entering_table">
									<tbody>
									<colgroup>
										<col class="title" />
										<col class="property" />
										<col class="input" />
										<col class="input" />
										<col class="property" />
										<col class="input" />
										<col class="input" />
										<col class="property" />
										<col class="input" />
									</colgroup>
									<tr>
										<td colspan="9">发货信息</td>
									</tr>
									<tr>
										<td colspan="9"><HR width="100%" SIZE=1>
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">客户账号<label class="red">*</label>
										</td>
										<td><input type="text" name="customerName"
											id="customerName">
										</td>
										<td></td>
										<td class="property">收货人<label class="red">*</label>
										</td>
										<td><input type="text" name="consignee">
										</td>
										<td></td>
										<td class="property">电话<label class="red">*</label>
										</td>
										<td><input type="text" id="phone" name="phone">
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">手机<label class="red">*</label>
										</td>
										<td><input type="text" id="mobile" name="mobile">
										</td>
										<td></td>
										<td class="property">邮箱<label class="red">*</label></td>
										<td><input type="text" name="email">
										</td>
										<td></td>
										<td class="property">邮编<label class="red">*</label>
										</td>
										<td><input type="text" name="zipcode">
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">区域<label class="red">*</label>
										</td>
										<td colspan="7"><select areaLevel="country"
											name="country" id="country">
												<option value="-1">请选择国家</option>
										</select> <select areaLevel="province" name="province" id="province">
												<option value="-1">请选择省份</option>
										</select> <select areaLevel="city" name="city" id="city">
												<option value="-1">请选择城市</option>
										</select> <select areaLevel="district" name="district" id="district">
												<option value="-1">请选择区县</option>
										</select> <select areaLevel="town" name="town" id="town">
												<option value="-1">请选择乡镇</option>
										</select></td>
									</tr>
									<tr>
										<td></td>
										<td class="property">地址<label class="red">*</label>
										</td>
										<td colspan="2"><input type="text" name="address" id="address"
											class="long"></td>
										<td class="property">备注</td>
										<td colspan="4"><input type="text" name="remark"
											class="long"></td>
									</tr>
									</tbody>

								</table>
							</div>


							<!--  配送信息  -->
							<div class="entering_table">
								<table class="entering_table">
									<tbody>
									<colgroup>
										<col class="title" />
										<col class="property" />
										<col class="input" />
										<col class="input" />
										<col class="property" />
										<col class="input" />
										<col class="input" />
										<col class="property" />
										<col class="input" />
									</colgroup>
									<tr>
										<td colspan="9">配送信息</td>
									</tr>
									<tr>
										<td colspan="9"><HR width="100%" SIZE=1>
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">配送方式<label class="red">*</label>
										</td>
										<td><select name="deliveryTypeId" id="deliveryTypeId">
												<option value="-1">请选择</option>
										</select></td>
										<td></td>
										<td class="property">送货时间<label class="red">*</label>
										</td>
										<td><select name="deliverytime">
												<c:forEach var="deliveryTime"
													items="${codeDeliveryTime.children}">
													<option value="${deliveryTime.id}">${deliveryTime.name}</option>
												</c:forEach>
										</select></td>
										<td></td>
										<td class="property">运费<label class="red">*</label>
										</td>
										<td><input type="text" value="0.00" id="deliveryfee" name="deliveryfee">
										</td>
									</tr>
									</tbody>
								</table>
							</div>

							<!--  来源及支付信息   -->
							<div class="entering_table">
								<table class="entering_table">
									<tbody>
									<colgroup>
										<col class="title" />
										<col class="property" />
										<col class="input" />
										<col class="input" />
										<col class="property" />
										<col class="input" />
										<col class="input" />
										<col class="property" />
										<col class="input" />
									</colgroup>
									<tr>
										<td colspan="9">来源及支付信息</td>
									</tr>
									<tr>
										<td colspan="9"><HR width="100%" SIZE=1>
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">支付方式<label class="red">*</label>
										</td>
										<td><select name="paymentType" id="paymentType">
												<option value="-1">请选择</option>
										</select>
										</td>
										<td></td>
										<td class="property">订单来源<label class="red">*</label>
										</td>
										<td><button type="button" id="add_channel" >添加渠道</button><span id="channelChecked"></span></td>
										<td class="property" >
										</td>
										<td></td>
										<td id="channelContent">外部订单编号<input type="text"
											name="outerOrderId" id="outerOrderId"></td>
									</tr>
									</tbody>
								</table>
							</div>


							<!--  发票信息  -->
							<div class="entering_table">
								<table class="entering_table">
									<tbody>
									<colgroup>
										<col class="title" />
										<col class="property" />
										<col class="input" />
										<col class="input" />
										<col class="property" />
										<col class="input" />
										<col class="input" />
										<col class="property" />
										<col class="input" />
									</colgroup>
									<tr>
										<td colspan="9">配送信息</td>
									</tr>
									<tr>
										<td colspan="9"><HR width="100%" SIZE=1>
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">是否开发票<label class="red">*</label>
										</td>
										<td><select name="invoice" id="isInvoice">
												<option value="false">否</option>
												<option value="true">是</option>
										</select></td>
										<td></td>
										<td class="property" id="invoiceTitleTShow">抬头类型<label
											class="red">*</label>
										</td>
										<td id="invoiceTitleShow"><select name="invoiceTitle"
											id="invoiceTitle">
												<c:forEach var="invoiceTitleType"
													items="${codeInvoiceTitleType.children}">
													<option value="${invoiceTitleType.id}">${invoiceTitleType.name}</option>
												</c:forEach>
										</select>
										</td>
										<td></td>
										<td class="property" id="companyNameTitle">公司名称<label
											class="red">*</label>
										</td>
										<td id="companyName"><input type="text"
											name="companyName">
										</td>
									</tr>
									</tbody>
								</table>
							</div>
							
							<div>
								<span id="orderSaleType"></span>
							</div>
							<input type="submit" id="tjdd" value="提交订单" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="channelDiv">
		<ul id="channel_tree" class="tree"></ul>
		<br />
		<button type=button onclick="insertNodes()" id=getChecktree>确定</button>
	</div>
	<iframe name="product" style="display: none;"></iframe>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="${contextPath}/js/area/areadata.js"></script>
<script type="text/javascript" src="${contextPath}/js/area/areaevent.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.methods.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/order/_new.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/tree/channel_tree.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.bgiframe.min.js"></script>
</body>
</html>