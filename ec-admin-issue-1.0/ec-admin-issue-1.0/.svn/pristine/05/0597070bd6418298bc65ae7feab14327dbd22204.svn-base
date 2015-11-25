<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/scripts.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>监控任务.</title>

<link type="text/css" href="../../css/redmond/jquery-ui-1.8.14.custom.css" rel="stylesheet" />
<link type="text/css" href="../../css/global.css" rel="stylesheet" />
<script type="text/javascript" src="../../js/monitor/new.js"></script>
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
		<form action="/monitor/product/new" method="POST" enctype="multipart/form-data">
			<div align="left">
			监控日期:<input type="text" name="start" bind="datepicker" readonly="readonly" />至<input type="text" name="end" bind="datepicker" readonly="readonly"/>
			监控名称:<input name="name" id="name" type="text" maxlength="30" />
		</div>
		<div>
			<ul>
				<li style="display:inline;">监控频率:</li>
				<li  style="display:inline;padding-right: 20px;">30分钟<input name="frequency" type="radio" value="30"/></li>
				<li  style="display:inline;padding-right: 20px;">1小时<input name="frequency"  checked="checked" type="radio" value="60"/></li>
				<li  style="display:inline;padding-right: 20px;">3小时<input name="frequency"  type="radio" value="180"/></li>
				<li  style="display:inline;padding-right: 20px;">6小时<input name="frequency" type="radio" value="360"/></li>
				<li  style="display:inline;padding-right: 20px;">12小时<input name="frequency" type="radio" value="720"/></li>
				<li  style="display:inline;padding-right: 20px;">1天<input name="frequency" type="radio" value="1440"/></li>
				<li  style="display:inline;padding-right: 20px;">7天<input name="frequency" type="radio" value="10080"/></li>
				<li  style="display:inline;padding-right: 20px;">15天<input name="frequency" type="radio" value="21600"/></li>
				<li  style="display:inline;padding-right: 20px;">30天<input name="frequency" type="radio" value="43200"/></li>
			</ul>
	    　</div>
	    <div>
			    手&nbsp;机&nbsp;号:<input type="text" id="mobile" /><input type="button" onclick="monitor.appendMobile();" value="add" />
			 Email:<input type="text" id="email" /><input type="button" onclick="monitor.appendEmail();" value="add" />
			<br/>
			<div id="div_mobile" style="float:none;">mobile:</div>
			<div id="div_email" style="float:none;">email:</div>
	 	</div>
	 	<div>
	 		库存小于:<input name="stockLess" id="stockLess" value="20"/>
	 	</div>
		<div>
			消息提示:<textarea name="message" style="height:100px;width: 200px;resize:none;"></textarea>
			任务描述:<textarea name="description" style="height:100px;width: 200px;resize:none;"></textarea>		
		</div>
		<div>
			Excel上传<input type="file" name="file" id="file" readonly="readonly"/> (注: excel第一列为商品销售ID, 版本2003)
		</div>
		<input type="submit" value="提交" onclick="return monitor.submit()" />
		</form>
	</div>
</div>
</div>
</body>
</html>