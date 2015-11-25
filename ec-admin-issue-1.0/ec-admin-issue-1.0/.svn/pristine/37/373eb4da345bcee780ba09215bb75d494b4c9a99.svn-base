<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<style>
center {
	font-size: 12px;
	color: red;
	font-weight: bold;
}

select {
	font-size: 12px;
	color: green;
}
</style>
<%@include file="../snippets/meta.jsp"%>
<title>数据描述</title>
</head>
<body>
	<center>
		<H4>${robotcategory.name }的数据描述</H4>
		<form action="${contextPath}/category/${category.id }/productMeta"
			method="post" onsubmit="$('select[name=item] option').attr('selected','selected');return true;">
			<table border="0" width="400">
				<tr>
					<td><CENTER>可选择数据描述</CENTER>
					</td>
					<td></td>
					<td><CENTER>已选择数据描述</CENTER>
					</td>
				</tr>
				<tr>
					<td width="40%"><select
						size="${fn:length(productMetaAvailable)>fn:length(productMetaList)?fn:length(productMetaAvailable):fn:length(productMetaList)}"
						multiple id="left" size="8" style='width: 200;'
						ondblclick="moveOption(document.getElementById('left'), document.getElementById('item'))">
							<c:forEach var="productMeta" items="${productMetaAvailable}"
								varStatus="status">
								<option value="${productMeta.id}">${productMeta.name}
							</c:forEach>
					</select></td>
					<td width="20%" align="center"><input type="button"
						value=" >> "
						onclick="moveOption(document.getElementById('left'),document.getElementById('item'))"><br>
					<br> <input type="button" value=" << "
						onclick="moveOption(document.getElementById('item'), document.getElementById('left'))">
					</td>
					<td width="40%"><select
						size="${fn:length(productMetaAvailable)>fn:length(productMetaList)?fn:length(productMetaAvailable):fn:length(productMetaList)}"
						multiple name="item" id="item" size="8" style='width: 200;'
						ondblclick="moveOption(document.getElementById('item'), document.getElementById('left'))">
							<c:forEach var="productMeta" items="${productMetaList}"
								varStatus="status">
								<option selected="selected" value="${productMeta.id}">${productMeta.name}
								
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td colspan="3">
						<div>
							<input type="button" value="MC分类" onclick="$('#div_mccategory').append('<input type=text name=mccategories /> ');" />
							<input type="button" value="X" onclick="$('input[name=mccategories]').last().remove()" />
							<div id="div_mccategory">
								<c:forEach var="mc" items="${category.mcCategory }">
								<input type=text name=mccategories value="${mc.id }" /> 
								</c:forEach>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<button type="submit">提交</button>
		</form>

	</center>
	<%@include file="../snippets/scripts.jsp"%>
</body>
<script src="${contextPath}/js/category/productmeta_list.js"></script>
</html>