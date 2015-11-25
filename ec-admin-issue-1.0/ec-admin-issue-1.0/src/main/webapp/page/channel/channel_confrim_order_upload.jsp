<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>上传网店确认核对订单</title>
</head>
<script>
	var uploadPayDetail = function(btn){
		if(/\.xls$/.test(document.getElementById("file").value)){
			btn.style.display = "none";
			document.form.submit();
		} else {
			alert("请选择一个2003版本的Excel,格式需正确");
		}
	}
</script>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
		<form id="exForm" name="exForm" method="POST" target="_blank" action="/excel/channelPayConfirmOrderTemplate">
			<input type="hidden" value="xls" name="format">
			<a onclick="document.exForm.submit()">已核对订单模板下载</a>
		</form>
			
			<form name="form" action="/channel/confrimOrder/upload" target="upload" method="post" enctype="multipart/form-data">
				<label>文件:</label>
				<input name="file" id="file" type="file" onchange="javascript:document.getElementById('upload').style.display = '';" />
				<input type="button" style="display:none" id="upload" value="核对订单明细上传" onclick="uploadPayDetail(this);"/>
			</form>
			<iframe name="upload" style="border:0px;width: 100%;height: 500px;"></iframe>
		</div>
		
	</div>
</body>
</html>