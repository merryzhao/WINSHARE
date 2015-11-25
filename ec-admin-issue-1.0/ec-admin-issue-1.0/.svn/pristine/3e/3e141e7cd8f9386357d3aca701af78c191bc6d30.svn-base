<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<title>促销-买商品送商品</title>
<style type="text/css">
.idTextArea {
	width: 100px;
	height: 60px;
}
</style>
</head>
<body>
	<div id="content-result">
		<table>
			<tr>
				<td colspan="2">单订单可重复享受赠品：</td>
				<td><input type="radio" name="isreplication"  value="true" />是</td>
				<td><input type="radio" name="isreplication"  checked="checked" value="false" />否</td>
			</tr>
		</table>
		<div>
			应用商品：<a href="javascript:void(0);" id="addProduct">添加商品</a>
		</div>
		<hr />
		<div id="productTable"></div>
		<br />
		<div>
			选择赠品： <a href="javascript:void(0);" id="addGift">添加赠品</a>
		</div>
		<hr />
		<div id="giftTable"></div>
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
	<script type="text/javascript"
		src="${contextPath }/js/promotion/product_promotion.js"></script>
</body>
</html>