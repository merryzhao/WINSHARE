<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>

<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>商品信息查询</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/product/productList.css"
	rel="stylesheet" />
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
<style>
button.move {
	background: url("/css/zTreeStyle/img/move.png") no-repeat scroll 1px 1px
		transparent;
}
</style>
</head>
<body
	<c:if test="${productQueryForm.ismore==true}">
onload="inorvisible('moreterm');"
</c:if>>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>
					商品信息查询<span style="color: red">带*号的条件至少要填写一项!</span>
				</h4>
				<!-- 查询表单div -->
				<div id="queryForm">
					<form:form class="inline" action="/product/productList"
						method="post" commandName="productQueryForm" id="productform">
						<form:hidden value="0" path="ismore" />
						<form:select cssStyle="vertical-align:top;" id="coding"
							path="coding">
							<form:option value="productBarcodes" label="ISBN编码*"></form:option>
							<form:option value="outerIds" label="SAP编码*"></form:option>
							<form:option value="productSaleIds" label="商品编码*"></form:option>
							<form:option value="prodcutMerchIds" label="主数据商品编码*"></form:option>
						</form:select>
						<form:textarea path="codingContent" />
						<label>商品名称*：</label>
						<form:input path="productName" />
						<input type="submit" value="检索" />
						<label class="moretrem" id="viewmore"
							onclick="inorvisible('moreterm');"><a
							href="javascript:void(0);">显示更多检索条件</a> </label>
						<br />
						<div id="moreterm" class="moreterm">
							<label> 作者： </label>
							<form:input path="productAuthor" />
							<label> MC分类*：</label>
							<form:input path="productMcCategory" />
							<label> 出版社名称*： </label>
							<form:input path="manufacturer" />
							<label> EC库存量： </label>
							<form:input size="7" path="stockNumberMin" />
							~
							<form:input size="7" path="stockNumberMax" />
							<label> <br /> 状态：
							</label>
							<form:select id="status" path="status" multiple="">
								<form:option value="0" label="请选择"></form:option>
								<c:forEach items="${saleStatus}" var="s">
									<form:option value="${s.id}" label="${s.name}"></form:option>
								</c:forEach>
							</form:select>
							<label>储配方式：</label>
							<form:select id="storageType" path="storageType" multiple="">
								<form:option value="0" label="请选择"></form:option>
								<c:forEach items="${store.children}" var="s">
									<form:option value="${s.id}" label="${s.name}"></form:option>
								</c:forEach>
							</form:select>
							<label>卖家名称：</label>
							<form:select id="shopId" path="shopId" multiple="">
								<form:option value="0" label="请选择"></form:option>
								<c:forEach items="${shops}" var="s">
									<form:option value="${s.id}" label="${s.name}"></form:option>
								</c:forEach>
							</form:select>
							<label>出版日期*：</label>
							<form:input id="productionStartDate" path="productionStartDate" />
							~
							<form:input id="productionEndDate" path="productionEndDate" />
							<div>
								<label>是否有图片：</label>
								<form:select id="picture" path="picture" multiple="">
									<form:option value="-1" label="请选择"></form:option>
									<form:option value="0" label="否"></form:option>
									<form:option value="1" label="是"></form:option>
								</form:select>
								<label>是否套装*：</label>
								<form:select id="complex" path="complex" multiple="">
									<form:option value="-1" label="请选择"></form:option>
									<form:option value="0" label="否"></form:option>
									<form:option value="1" label="虚拟套装"></form:option>
									<form:option value="2" label="实物套装"></form:option>
								</form:select>
								<label>商品供应类型*：</label>
								<form:checkbox path="supplyTypes" label="正常销售" value="13101" />
								<form:checkbox path="supplyTypes" label="新品预售" value="13102" />
								<form:checkbox path="supplyTypes" label="订购" value="13103" />

								<button type="button" id="chooseCategory"
									style="line-height: 1.5; padding: 0px;">选择平台商品主分类*</button>
								<label id="categoryName"></label>
								<form:hidden id="category" path="category" />
							</div>
							<div>
								折扣区间:
								<form:input size="7" path="discountMin" />
								~
								<form:input size="7" path="discountMax" />
								码洋区间:
								<form:input size="7" path="listpriceMin" />
								~
								<form:input size="7" path="listpriceMax" />
							</div>
						</div>
					</form:form>
				</div>
				<!-- 查询结果展示div -->
				<div>
					<c:if
						test="${pagination!=null && pagination.count>0 && pagination.count<=10000}">
						<button href="javascript:void(0);" onclick="submitExcel();">导出搜索结果</button>
						<button href="javascript:void(0);" onclick="changeCategory();">移动分类</button>
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<form action="">
						<table class="list-table" style="width: 1180;">
							<c:if test="${pagination!=null}">
								<tr>
									<th width="2%"><input type="checkbox" id="selectAll"
										name="selectAll"></th>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>ISBN编码/条形码</th>
									<th>码洋/市场价格</th>
									<th>实洋/销售价格</th>
									<th>SAP编码</th>
									<th>MC分类</th>
									<th>网站可用量</th>
									<th>商品主数据编码</th>
									<th>状态</th>
									<th>作者</th>
									<th>出版社</th>
									<th>出版时间</th>
									<th>商品供应类型</th>
									<th>商品类型</th>
									<th>操作</th>
								</tr>
							</c:if>
							<c:forEach var="productSale" items="${productSales}"
								varStatus="i">
								<tr>
									<td><input type="checkbox" name="export"
										value="${productSale.id}"></td>
									<td><a href="/product/${productSale.id}/detail">${productSale.id}</a>
									</td>
									<td width="150px;">${productSale.sellName}</td>
									<td>${productSale.product.barcode}</td>
									<td>${productSale.product.listPrice}</td>
									<td>${productSale.salePrice}</td>
									<td>${productSale.outerId}</td>
									<td>${productSale.product.mcCategory}</td>
									<td class="stockQuantityCss"><a href="javascript:void(0);"
										title="<c:if test="${productSale.supplyType.id==13101 }">实物库存(<c:forEach var="productSaleStock" items="${productSale.productSaleStockVos}">${productSaleStock.dcdetail.name}:${productSaleStock.stock}；&nbsp;</c:forEach>)</c:if><c:if test="${productSale.supplyType.id==13102 }">预售库存</c:if>">${productSale.stockQuantity-productSale.saleQuantity}</a>
									</td>
									<td>${productSale.product.merchId }</td>
									<td class="saleStatuscss">${productSale.saleStatus.name}</td>
									<td>${productSale.product.author}</td>
									<td>${productSale.product.manufacturer }</td>
									<td><fmt:formatDate
											value="${productSale.product.productionDate }" type="date" />
									</td>
									<td class="statuscss">${productSale.supplyType.name}</td>
									<td>
									<c:if test="${productSale.product.complex == 1}">虚拟套装</c:if>
									<c:if test="${productSale.product.complex == 2}">实物套装</c:if>
									</td>
									<td id="operateTd${i.index }"><a
										href="/product/${productSale.id}/detail">查看</a> &nbsp;&nbsp;<a
										target="_blank" href="/product/${productSale.id}/edit">编辑</a>
										&nbsp;&nbsp;<c:choose>
											<c:when
												test="${productSale.shop.id==1&&productSale.storageType.id==6001}">
												纸质书请到价格系统修改
											</c:when>
											<c:otherwise>
										&nbsp;&nbsp;<a href="/product/${productSale.id }/newprice">修改销售价格</a>
											</c:otherwise>
										</c:choose> <c:if test="${productSale.supplyType.id!=13102 }">
											<%-- 											<c:if test="${productSale.stockQuantity==0}"> --%>
											&nbsp;&nbsp;<a href="javascript:void(0);"
												onclick="setBook(${productSale.id},${i.index },${productSale.stockQuantity-productSale.saleQuantity},this);">设置新品预售</a>
											<%-- 											</c:if> --%>
										</c:if> &nbsp;&nbsp;<a href="/product/${productSale.id}/manageGrade">管理分级</a>

										<c:if test="${productSale.supplyType.id == 13102}">
											<input type="button"
												onclick="cancelPresaleProduct(${productSale.id},this);"
												value="取消预售" />
										</c:if></td>
								</tr>
							</c:forEach>
							<c:if test="${pagination!=null&&pagination.pageCount!=0}">
								<tr>
									<th width="2%"><input type="checkbox" name="selectAll2">
									</th>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>ISBN编码/条形码</th>
									<th>码洋/市场价格</th>
									<th>实洋/销售价格</th>
									<th>SAP编码</th>
									<th>MC分类</th>
									<th>网站可用量</th>
									<th>商品主数据编码</th>
									<th>状态</th>
									<th>作者</th>
									<th>出版社</th>
									<th>出版时间</th>
									<th>商品供应类型</th>
									<th>商品类型</th>
									<th>操作</th>
								</tr>
							</c:if>
						</table>
					</form>
					<c:if test="${pagination!=null&&pagination.pageCount!=0}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</div>

			</div>
		</div>
		<div class="loading">数据处理中... 请稍候</div>
	</div>
	<input type="hidden" id="currentPsId" />
	<input type="hidden" id="currentIndex" />
	<div id="success" align="center">
		设置成功<br /> <br />
		<button type="button" id="successButton">确定</button>
	</div>
	<div id="failure">
		<label id="returnMessage"></label>
	</div>
	<div id="productBookingDialog">
		<div>
			<label>预售商品库存量*：</label> <input type="text" name="stockQuantity"
				id="stockQuantity" onchange="checkBooking();" /><label
				id="quantityError" class="error"></label>
		</div>
		<div>
			<label class="predc">预售DC：</label> <input type="radio" name="dc"
				value="110004" /><label>D818</label> <input type="radio" name="dc"
				checked="checked" value="110003" /><label>8A17</label> <input
				type="radio" name="dc" checked="checked" value="110007" /><label>D819</label>
		</div>
		<div>
			<label>预售起止时间*：</label> <input style="margin-left: 10px;" type="text"
				name="bookStartDate" id="bookStartDate" readonly="readonly"
				bind="datepicker" onchange="checkBooking();" />-<input type="text"
				onchange="checkBooking();" name="bookEndDate" readonly="readonly"
				id="bookEndDate" bind="datepicker" /><label id="dateError"
				class="error"></label>
		</div>
		<br />
		<div style="display: none">
			<label>预售商品忽略库存量*：</label> <input type="text" name="ignore" value="0"
				id="ignore" onchange="checkBooking();" /><label id="ignoreError"
				class="error"></label>
		</div>
		<div>
			<label class="preDescr">预售描述 ：</label>
			<textarea name="bookDescription"></textarea>
		</div>
	</div>
	<div id="categoryDiv">
		<ul id="category_tree3" class="tree"></ul>
		<br />
		<button type=button id=getChecktree>确定</button>
	</div>
	<div id="categoryMoveDiv">
		<ul id="categoryMoveDiv_tree" class="tree"></ul>
		<br /> <input name="selectTargetId" type="hidden" />
	</div>
	<form action="/excel/productList" target="_blank" id="xlsform"
		method="post" style="display: none;">
		<input type='hidden' name='format' value='xls' /> <span
			id="otherinfo"></span>
	</form>
</body>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/tree/category_tree.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/product/productList.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/category/category_move.js"></script>
</html>