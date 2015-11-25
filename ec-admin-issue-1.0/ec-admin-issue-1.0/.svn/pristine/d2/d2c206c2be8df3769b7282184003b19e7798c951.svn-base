<%@page pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@include file="../snippets/tags.jsp"%>
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

#discount_method {
	margin-left: 300px;
}
</style>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/tree/category_tree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/promotion/category_price.js"></script>

<div class="frame-main-inner" id="content">
	<div id="category_price">
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
		<div id="seller_list" class="sellers"></div>
		<p>
			选择分类：
			<button type=button id=show_tree onclick=>添加</button>
			<!-- <select id=discount_method><option>--请选择--</option>
				<option value=26001>统一折扣</option>
				<option value=26002>只下调折扣</option>
				<option value=26003>只上调折扣</option>
			</select> 折扣:<input type=text id=discount size=5 /> -->
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
	</div>
</div>
