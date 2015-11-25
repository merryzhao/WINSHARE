<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>注销礼品卡</title>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
.fontcl {
	font-size: 15px;
	color: #6699cc;
	font-weight: bold;
	font-family: Microsoft YaHei;
	margin-right:20px;
}

.errorstyle {
	font-size: 12px;
	color: #ff0000;
	font-weight: bold;
	font-family: Microsoft YaHei;
	margin-right:20px;
}
</style>
</head>
<body>
<div class="frame">
		<%@include file="../snippets/frame-top.jsp"%>
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<div id="content">
			   <h4>礼品卡注销</h4><hr>
			   <label class="fontcl">礼品卡号 : </label><input id="id" /><label id="error" class="errorstyle"></label>
			   <div id="canceldiv" style="display: none">
			      <table class="list-table">
			         <tr>
			            <td>礼品卡号：<label id="presentCardId"></label></td>
			            <td>礼品卡状态:<label id="status"></label></td>
			            <td>礼品卡类型:<label id="type"></label></td>
			         </tr>
			         <tr>
			            <td>创建日期：<label id="createdate"></label></td>
			            <td>有效期：<label id="enddate"></label></td>
			            <td>余额:<label id="balance"></label></td>
			         </tr>
			          <tr>
			            <td>面额:<label id="denomination"></label></td>
			            <td>是否有效：<label id="expired"></label></td>
			         </tr>
			       </table> 
			       <button id="cancel">注销</button>
			   </div>
			</div>
			</div>
			</div>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/presentcard/cancel_presentcard.js"></script>
</body>
</html>