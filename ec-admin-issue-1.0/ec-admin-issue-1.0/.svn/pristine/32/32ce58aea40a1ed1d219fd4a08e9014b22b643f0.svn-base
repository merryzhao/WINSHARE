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
<title>商品图片批量转换</title>
<link rel="stylesheet" href="${contextPath}/css/slidingtabs-horizontal.css">
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
<div >
<div><b style="color:red" id="msg">${msg}</b></div>
<div>
注意:
<ul>
	<li>1. 文件只能使用2003兼容格式, 即xls文件</li>
	<li>2. 单个文件最多支持65535条记录, 因为xls文件有这个大小限制</li>
	<li>3. 生成后的文件是xls文件, 如果记录超过50000, 那么, 会自动的分成多个sheet, 查看时请注意</li>
	<li>4. 上传文件后, 将不再直接进行下载, 而是会转成后台处理, 请等待一段时间, 去"报表管理->附件管理"进行查询与下载</li>
	<li>5. 第一列为商品ID(PID), 第二列为原始图片地址</li>
	<li>6. 地址必须是服务器能访问的地址!</li>
</ul>
</div>
<form:form commandName="express" action="/product/zoomImage" method="post" enctype="multipart/form-data" id="expressform">
<table>
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
</body>
</html>
