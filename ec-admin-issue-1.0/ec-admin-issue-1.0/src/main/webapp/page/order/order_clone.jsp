<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<html>
<head>
<title>复制订单</title>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
div.entering_table {
	background: #ecf5ff;
	margin-top: 25px;
	margin-bottom: 25px;
	border: 1px solid #6293bb;
}

table.entering_table {
	width: 90%;
	margin-left: 5%;
	margin-right: 5%;
	margin-top: 10px;
	margin-bottom: 10px;
}

table.entering_table td.property {
	text-align: right;
}

table.entering_table .title {
	width: 5%;
}

table.entering_table .property {
	width: 10%;
}

table.entering_table .input {
	width: 10%;
}

table.entering_table .longinput {
	width: 20%;
}

label.red {
	color: red;
}

input.long {
	width: 98%;
}

label.error {
	border: none;
	padding: 0.1em;
	color: red;
	font-weight: normal;
	background: none;
	padding-left: 16px;
}

td label.error {
	display: none !important;
}
#uploadPanel{
	margin:20px 0;
}
#uploadbtn{
	padding: 2px 10px;
}
</style>
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
				<h4>复制订单</h4>
				<div>
					<div>
						<p>说明:</p>
						<ul>
							<li>1. 格式只能使用excel2003兼容格式, 即后缀是xml的excel文件,每次最多上传300个订单</li>
							<li>2. 上传时填写订单编号在第几列, 从1开始算,excel第一行默认为表头</li>
							<li>3. 上传成功后, 请到"报表管理->订单复制日志"查询复制成功的订单</li>
							<li>4. 只有网站订单在取消状态下才可复制</li>
							<li></li>
							<li></li>
						</ul>
					</div>
					<div id="uploadPanel">
						<form action="/order/batchCopyOrder" method="post" enctype="multipart/form-data" id="uploadForm">
							<table>
								<tr>
									<td>文件:</td>
									<td><input type="file" name="file"></td>
								</tr>
								<tr>
									<td>单号所以列:</td>
									<td><input type="text" name="col" id="col" value="1"></td>
								</tr>
								<tr>
									<td><input type="submit" value="提交" id="uploadbtn"/></td>
									<td></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<div style="color: red">
					<c:if test="${not empty success}">
						<c:if test="${success == true}">
							订单复制完成, 请到"报表管理->订单复制日志"查询复制成功的订单!<br />
							复制失败订单：<br />${msg}
							</c:if>
						<c:if test="${success != true}">
							操作失败!${msg}
							</c:if>
					</c:if>
				</div>
			</div>
		</div>
	</div>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.validate.methods.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/order/order_clone.js"></script>
</body>
</html>