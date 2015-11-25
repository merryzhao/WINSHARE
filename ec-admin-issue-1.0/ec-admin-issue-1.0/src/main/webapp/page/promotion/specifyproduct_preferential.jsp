<%@page pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/promotion/specifyproduct_preferential.js"></script>
<div class="frame-main-inner" id="content">
	<div class="promotion_type">
		<div> 
			<label>促销方式：</label> <input type="radio" checked="checked"
				name="orderPricemanner" value="0"><label>普通优惠</label> <input
				type="radio" name="orderPricemanner" value="1"><label>梯度优惠</label>
		</div>
		<div class="promotion_type_price">
			<div id="general_price_promotion" class="general" style="height:auto"></div>
			<div id="grads_price_promotion" class="general" style="height:auto"></div>
		</div>
	</div>
	<div id="singlePrice">
		<p>
			<b>应用商品</b>
			<a href="javascript:void();" id="single_product_addProduct">添加商品</a>
			<a href="javascript:void();" id="importExcel">导入Excel</a>
			<a href="/excel/template/promotion_up_products.xls">下载模板</a> 
		</p>
		<iframe id="iframe" name="iframe" style="display: none"></iframe>
		<table class="list-table" id="productlist">
			<tr>
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
			<tfoot>
				<tr>
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
		<div id="addProductDiv"></div>
	</div>
</div>
