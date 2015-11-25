<%@page pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
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
	src="${pageContext.request.contextPath}/js/promotion/single_product.js"></script>
<div class="frame-main-inner" id="content">
	<div id="singlePrice">
		<p>
			<b>应用商品</b>
			<a href="javascript:void();" id="single_product_addProduct">添加商品</a>
			<a href="javascript:void();" id="importExcel">导入Excel</a>
			<a href="/excel/template/promotion_up_products.xls">下载模板</a> 
			<span style="color: red; font-size: 10px">(目前仅支持电子书修改,非电子书不允许修改)</span>
			<span id="saleprice">
<!-- 				实洋：<input type="text" size=10 id=SetSalePrice /> 
				折扣：<input type="text" size=10 id=SetDiscount />  -->
				单用户每天限购数量：<input type="text" size=10 id=SetNum />
				每天限购总数量：<input type="text" size=10 id=SetNums />
				<button type=button id=change>修改</button> <a href="#" id=batchreset>批量重置</a>
			</span>
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
				<th>实洋(原实洋)</th>
				<th>折扣(如70)</th>
				<th>单用户每天限购数量<br/>(0:不限制)</th>
				<th>每天限购总数量<br/>(0:不限制)</th>
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
					<th>实洋(原实洋)</th>
					<th>折扣(如70)</th>
					<th>单用户每天限购数量<br/>(0:不限制)</th>
				    <th>每天限购总数量<br/>(0:不限制)</th>
					<th>操作</th>
				</tr>
			</tfoot>
		</table>
		<div id="addProductDiv"></div>
	</div>
</div>
