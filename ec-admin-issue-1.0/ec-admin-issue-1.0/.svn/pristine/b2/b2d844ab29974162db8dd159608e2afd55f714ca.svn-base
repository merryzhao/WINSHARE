<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<html>
<head>
<title>添加商品属性</title>

<style type="text/css">
.selectStyle{
	width: 60;
}
.buttonstyle {
	width: 85px;
	height: 25px;
  }
.textareaStyle{width: 350px; height: 60px; padding:0px;}

#add-body {
	margin-top: 30px;
	margin-left:30px;
	width: 500px;
	height: 350px;
	border:2px solid #DFDFDF;
	
 }
label.error {
	padding:0em;
}
#productMetaForm input.error {
	padding:0px;
	margin:0px;
	border: 2px solid red;
}
</style>
<%@include file="../snippets/meta.jsp"%>
</head>
<body>
<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
	 
					<div id="add-body">
					<h4>创建属性</h4>
						<form:form commandName="productMetaForm"
							action="${contextPath}/productmeta/add" method="post">
							 <token:token></token:token>
							<table class="list-table1">
							<tr>
							<td align="right">属性名称<span style="color: red;">*</span>：</td>
							<td align="left"><form:input path="name" maxlength="20" /> <label ><form:errors path="name"></form:errors></label></td>
							</tr>	 
							<tr>
							<td align="right">属性类型：</td>
							<td align="left"> <form:select  
												path="type" cssClass="selectStyle"
												items="${typeList}" id="type" multiple="" itemLabel="name"
												itemValue="id" />  <span id="enumadd"></span> </td>
							</tr>	 
							<tr>
							<td align="right">属性长度<span style="color: red;">*</span>：</td>
							<td align="left"><form:input path="length"/><label ><form:errors path="length"></form:errors></label>  </td>
							</tr>
							<tr>
							<td align="right">属性分类<span style="color: red;">*</span>：</td>
							<td align="left"><form:select path="category" ><form:option value="1">图书</form:option><form:option value="7786">音像</form:option><form:option value="7787">百货</form:option> </form:select><label ></label>  </td>
							</tr>
							<tr>
							<td align="right">是否可为空：</td>
							<td align="left">
							<form:select path="allowNull" cssClass="selectStyle"> 
							<form:option value="1" label="是"></form:option>
							<form:option value="0" label="否"></form:option>
							</form:select>
							 </td>
							</tr>
							<tr>
							<td align="right">是否前台展示：</td>
							<td align="left">
							<form:select path="show" cssClass="selectStyle"> 
							<form:option value="1" label="是"></form:option>
							<form:option value="0" label="否"></form:option>
							</form:select>
							 </td>
							</tr>
							<tr>
							<td align="right">默认值：</td>
							<td align="left"><form:input path="defaultValue" maxlength="200" />  </td>
							</tr>
							<tr>
							<td align="right">状态：</td>
							<td align="left">
							<form:select path="available" cssClass="selectStyle"> 
							<form:option value="1" label="有效"></form:option>
							<form:option value="0" label="无效"></form:option>
							</form:select>
							 </td>
							</tr>
 							<tr>
							<td align="right">描述：</td>
							<td align="left"><form:textarea path="description" cssClass="textareaStyle"/> </td>
							</tr>
		     				<tr>
							<td align="center" colspan="2"><input type="submit" class="buttonstyle" value="提交"/>  <input type="reset" class="buttonstyle" value="重置"/> </td>
							</tr>
							</table>	
  						</form:form>
					</div>
					</div>
					</div>
					</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/order/jquery.mousewheel.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/order/jquery.easing.1.3.js"></script>
    <script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript" 
		src="${contextPath}/js/productmeta/productmeta_validate.js"></script>
 
</body>
</html>