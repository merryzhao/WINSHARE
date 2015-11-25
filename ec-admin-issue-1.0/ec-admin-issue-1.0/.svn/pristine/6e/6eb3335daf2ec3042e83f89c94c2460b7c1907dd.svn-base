<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>创建套装书</title>
<style type="text/css">
input.codingContent {
	margin-left: 50px;
}

input.sellerName {
	margin-left: 5px;
}

input.productAuthor {
	margin-left: 15px;
}

label.moretrem {
	margin-left: 25px;
	color: #5CACEE;
}
table.tr{
	margin-top: 5px;
}
div.moreterm {
	display: none;
}
.error {
    border: 0px;
    padding: 0px;
    margin: 0px;
}
textarea {
	width: 300px;
	height: 80px;
}
</style>
<!-- 引入JS -->
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor-4.1/themes/default/default.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor-4.1/plugins/code/prettify.css" />
	<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1/kindeditor.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1/plugins/code/prettify.js"></script>		
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
				<h4>创建套装书</h4>
				<!-- 查询表单div -->
				<div id="queryForm">
 						<table>
							<tr>
								<td>
			 						<select id="codingType" name="codingType" >
			 							<option  value="productId" >商品编号</option>
										<option value="outerId">自编码</option>
									</select>
								</td>
								<td>
									<textarea id="codingValue" name="codingValue" style="width: 150px;height: 60px;" rows="" cols=""></textarea>
								</td>
								<td>
									<span style="padding-left: 15px;"><input type="button" id="addProduct" value="添加商品"></span>
								</td>
							</tr>
						</table>
 				</div>
 				<div>
 				<span id="msg" style="color: red;">
 				</span>
 				</div>
				<!-- 查询结果展示div -->
				<div style="border:2px solid #DFDFDF;">
				<h4>商品信息查询</h4>
 						<table class="list-table" id="dataTable">
 								<tr>
									<th>序号</th>
									<th>商品编码</th>
									<th>商品名称</th>
									<th>商品自编码</th>
									<th>商品码洋</th>
									<th>商品实洋</th>
 									<th>库存</th>
 									<th>操作</th>
								</tr>
 							 
						</table>
 				</div>
			</div>
		
		<div  style="border:2px solid #DFDFDF;">
 			<br/>
   				<div>
						<h4>基本信息</h4>
						<table class="list-table1">
 							<tr>
								<td align="right">套装书名称：</td><td align="left" colspan="5"><input size="88" type="text" id="name" name="name"/></td>
								 
							</tr>
							<tr>
								<td align="right">套装书码洋：</td><td align="left"><span id="listPrice"></span></td>
								<td align="right">套装书实洋：</td><td align="left"><span id="salePrice"></span></td>
								<td align="right">套装书库存：</td><td align="left">
								<span id="complexQuantity">
                                   <select id="dc">
                                     <option value ="-1">请选择库位</option>
                                   <c:forEach items="${dcList}" var="dc">
                                     <option value ="${dc.location.id}">${dc.location.description}(${dc.location.name})</option>
                                   </c:forEach>
                                   </select>
								</span>	<span id="complexQuantityInfo"></span></td>
							</tr>
							<tr>
								<td align="right">ISBN号：</td><td align="left"><input type="text" id="barcode" name="barcode"/></td>
								<td align="right">出版社：</td><td align="left"><input type="text" id="manuFacturer" name="manuFacturer"/></td>
								<td align="right">作者：</td><td align="left"><input type="text"  id="author" name="author"/></td>
							</tr>
							<tr>
								<td align="right">销售分类：</td>
								<td align="left" colspan="5">
									<button type="button" id="chooseCategory">添加分类</button>
									<div id="categorier"></div>
								</td>
							</tr>
							<tr>
								<td align="right">mc分类：</td><td align="left"><span id="mcCategory"></span> </td>
								<td align="right">出版时间：</td><td align="left"  colspan="4"><input type="text" id="productionDate"  readonly="readonly"
														name="productionDate" bind="datepicker"/>
								</td>
							</tr>
							<tr>
								<td align="right">商品促销语：</td><td align="left"  colspan="5"><input type="text" size="88" id="promValue" name="promValue"/></td>
							</tr>
						</table>
					</div>
					<div style="border:2px solid #DFDFDF;">
						<h4>商品图片</h4>
 							<form action="/product/creatcomplex" method="post" id="complexForm" enctype="multipart/form-data">
								<table>
		 							<tr>
		 							<td rowspan="2">
										<input type="hidden" id="jsonData" name="jsonData"/>
										<input type="hidden" id="mainId" name="mainId"/>
										<input type="hidden" id="ids" name="ids"/>
										<input type="hidden" id="dc" name="dc" value="-1"/>
 		 							</td>
 		 							</tr>
	 							<tr>
	 							<td>
	 								<%@include file="./img.jsp"%>
	 							</td>
	 							</tr>
		  						</table>
 					    	</form>
					</div>
					
					<div style="border:2px solid #DFDFDF;">
						<h4>扩展信息</h4>
						<table  id="ex_table">
 
  						</table>
					</div>
					<div style="border:2px solid #DFDFDE;">
						<h4>商品详情描述</h4>
						<table id="exdec_table">
						</table>
					</div>
			</div>
			<br/>
					<center>
						<div>
							<input id="sbm" type="button" value="提交" style="width: 150px; height: 30px;">
						</div>
					</center>
		</div>
	</div>
		<div id="categoryDiv">
			<ul id="category_tree2" class="tree"></ul>
			<br />
			<button type=button onclick="insertNodes()" id=getChecktree>确定</button>
		</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
		<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/product/product_complex_base.js"></script>
 	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/product/product_complex.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/product_complex_dc.js"></script>
</body>
</html>