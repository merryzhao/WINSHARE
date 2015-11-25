<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>批量停用商品</title>
<style type="text/css">
input.codingContent {
	margin-left: 50px;
}

input.sellerName {
	margin-left: 50px;
}

input.productAuthor {
	margin-left: 25px;
}

label.moretrem {
	margin-left: 25px;
	color: #5CACEE;
}

div.moreterm {
	display: none;
}
</style>
<!-- 引入JS -->
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

				<h4>批量停用商品:</h4>
				<!-- 查询表单div -->
				<div id="queryForm">
					<form class="inline" action="/product/findproduct" method="post" id="productform">
					<table>
					<tr>
					<td>查询条件:
                     <select name="codingType" id="codingType">
							<option value="productBarcode">ISBN编码</option>
							<option value="outerId">SAP编码</option>
							<option value="productSaleId">商品编码</option>
							<option value="prodcutMerchId"> 主数据商品编码</option>
						</select>
					</td>
					<td>
						<textarea id="codingValue" name="codingValue" style="width: 150px;height: 60px;" rows="" cols=""></textarea>
					</td>
  					<td align="center"> 
					<span style="padding-left: 10px;">店铺名称 :<input type="text" id="shopName" name="shopName"/></span>
					</td>
					<td align="center">
						<span style="padding-left: 10px;"><input type="button"  id="cx" value="查询" style="width: 60px;height: 30px;"></span>
					</td>
					</tr>
					</table>
					   <input type="hidden" name="actionFlag" value=""/>
					</form>
					<form action="/product/queryconfig" onsubmit="return checkfile();" method="post" id="excelForm" enctype="multipart/form-data">
 					<table>
					<tr>
					<td>导入查询条件:<a href="javascript:void(0);" onclick="$('#exForm').submit();">导出查询条件模板</a>
						<input type="file" name="fileName" id="fileName">
						<input type="button" value="上传"  id="sbm">
   					</td>
					</tr>
					</table>
					</form>
				</div>
				<form action="/excel/exmodel" target="_blank" method="post" name="exForm" id="exForm">
   												    <input type='hidden' name='format' value='xls' />
  			    </form>
				<!-- 查询结果展示div -->
				<div>
				<br/>
 
 					<input type="checkbox"/><input type="button" id="stopButton" value="批量停用"/>
                    <br>
                    备注:
                    <select name="remark" id="remark">
                        <option value="">选择</option>
                        <option value="清退供应商">清退供应商</option>
                        <option value="商品质量问题">商品质量问题</option>
                        <option value="商品版权问题">商品版权问题</option>
                        <option value="商品改版停采">商品改版停采</option>
                        <option value="禁止销售">禁止销售</option>
                        <option value="库存不足,出现缺货系统停用">库存不足,出现缺货系统停用</option>
                    </select>


  					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<form action="">
						<table class="list-table" id="datatable">
 								<tr>
									<th width="2%"><input type="checkbox" id="selectAll" class="selectAll"
										name="selectAll"></th>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>ISBN编码</th>
									<th>码洋</th>
 									<th>SAP编码</th>
 									<th>EC库存可用量</th>
									<th>销售占用数量</th>
 									<th>店铺名称</th>
									<th>存储方式</th>
									<th>商品供应类型</th>
									<th>操作</th>
								</tr>
							<c:if test="${fn:length(productSales)!=0}">
 						 	<c:forEach var="productSale" items="${productSales}">
								<tr>
									<td>
									<c:if test="${productSale.saleStatus.id==13003||productSale.saleStatus.id==13004||productSale.saleStatus.id==13005}"><input disabled="disabled" type="checkbox" name="stop" canstop="cannot" class="stop" 
										value="${productSale.id}">
										</c:if> 
										<c:if test="${productSale.saleStatus.id!=13003&&productSale.saleStatus.id!=13004&&productSale.saleStatus.id!=13005}"><input  type="checkbox" name="stop" class="stop" canstop="can"
										value="${productSale.id}">
										</c:if> </td>
									<td><a
										href="/product/${productSale.id}/detail">${productSale.id}</a>
									</td>
									<td>${productSale.product.name}</td>
									<td>${productSale.product.barcode}</td>
 									<td>${productSale.salePrice}</td>
									<td>${productSale.outerId}</td>
 									<td>${productSale.stockQuantity}</td>
									<td>${productSale.saleQuantity}</td>
 									<td>${productSale.shop.shopName}</td>
									<td>${productSale.storageType.name}</td>
									<td><span class="status">${productSale.saleStatus.name}</span></td>
									<td><a href="javascript:void(0)" class="delectpro">删除该条记录</a></td>
								</tr>
							</c:forEach> 
							</c:if>
						  <c:if test="${fn:length(productSales)==0}">
						  <tr>
						  	<td align="center" colspan="12">暂无数据</td>
						  </tr>
						  </c:if>
							
 								<tr>
									<th width="2%"><input type="checkbox" id="selectAll" class="selectAll"
										name="selectAll"></th>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>ISBN编码</th>
									<th>码洋</th>
 									<th>SAP编码</th>
 									<th>EC库存可用量</th>
									<th>销售占用数量</th>
 									<th>店铺名称</th>
									<th>存储方式</th>
									<th>商品供应类型</th>
									<th>操作</th>
								</tr>
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
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="${contextPath}/js/product/productStop.js"></script>
 </body>
</html>