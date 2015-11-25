<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.winxuan.com/tag/page"  prefix="winxuan-page"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>快递对账</title>
<link rel="stylesheet" href="${contextPath}/css/slidingtabs-horizontal.css">
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
<div >
<div><b style="color:red" id="msg">${msg}</b></div>
<div>
注意:
<ul>
	<li>1. 文件只能使用2003兼容格式, 即xls文件</li>
	<li>2. 单个文件最多支持65535条记录, 因为xls文件有这个大小限制</li>
	<li>3. 生成后的文件是xls文件, 如果记录超过50000, 那么, 会自动的分成多个sheet, 查看时请注意</li>
	<li>4. 上传文件后, 将不再直接进行下载, 而是会转成后台处理, 请等待一段时间, 去"报表管理->附件管理"进行查询与下载</li>
	<li>5. 运单号在第几列, 以1开始算</li>
	<li>6. 时间范围不建议使用大范围, 否则会导致数据导出失败!</li>
</ul>
</div>
<form:form commandName="express" action="/order/expressToAccount" method="post" enctype="multipart/form-data" id="expressform">
<table>
	<tr>
		<td>快递公司:</td>
		<td>
			<select name="delivery">
				<c:forEach items="${deliveryTypes}" var="delivery">
				<option value="${delivery.id}">${delivery.company}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td>时间范围:</td>
		<td>
		<form:input path="startDate"  class="starttime" bind="datepicker" width="10px"  style="width:100px" onclick="clearRadio(this)"/>
		~<form:input path="endDate" class="endtime" bind="datepicker"  style="width:100px" onclick="clearRadio(this)"/>(最大两个月)
		</td>
	</tr>
	<tr>
		<td>运单号在第几列:</td>
		<td><form:input path="codeCol"/></td>
	</tr>
	<tr>
		<td>订单填充到第几列:</td>
		<td><form:input path="startCol"/></td>
	</tr>
	<tr>
		<td>文件</td>
		<td><input type="file" name="file" id="upfile"></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><input type="submit" value="上传" style="width:50px; height: 25px;" id="formbtn"></td>
	</tr>
</table>
</form:form>
</div>
</div>
</div>
</div>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="${contextPath}/js/area/areadata.js"></script>
<script type="text/javascript">
$(function(){
	$("#expressform").submit(function(){
		var re = /^\d+$/;
		if($("#startDate").val() == ""){
			alert("请选择开始时间!");
			return false;
		}
		if($("#endDate").val() == ""){
			alert("请选择线束时间!");
			return false;
		}
		if(!re.test($("#codeCol").val())){
			alert("运单号在第几列必需是数字!");
			return false;	
		}
		if(!re.test($("#startCol").val())){
			alert("订单填充到第几列必需是数字!并且大于运单号在第几列!");
			return false;	
		}
		$("#formbtn").attr("disabled", "disabled");
		$("#msg").html("正在进行处理, 请勿重复提交或关闭窗口! 处理成功后, 会提示下载! ");
	});
});
function clearRadio(object){
	var trClass = "."+$(object).parent().parent().attr("class");	
	$(trClass+" .day").attr("checked",false);
	$(trClass+" .week").attr("checked",false);
	$(trClass+" .month").attr("checked",false);
}
</script>
</body>
</html>
