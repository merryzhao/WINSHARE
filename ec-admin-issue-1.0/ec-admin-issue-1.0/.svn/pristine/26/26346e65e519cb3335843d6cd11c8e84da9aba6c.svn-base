<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/scripts.jsp"%>
<%@include file="../snippets/meta.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>新建版本历史</title>
	<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
	<link type="text/css" rel="stylesheet" href="${contextPath}/ckeditor/_samples/sample.css" />
	<style type="text/css">
	#editor-body{
	margin-top:10px;
	margin-left:10px;
	width: 600px;
	height:500px;
	border:2px solid #DFDFDF;
 	}
 	#formcontent{
 	margin-left: 50px;
 	}
	</style>
</head>
<body>
<!-- 引入JS -->
 	<div class="frame" >
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-system.jsp"%>
		<div class="frame-main">
    <div id="editor-body">
   	<h4>添加版本更新信息</h4>
   	<div id="formcontent">
	<form action="/roadmap/save" method="post">
	   	<token:token></token:token>
	<table>
	<tr>
	<td>时间：</td>
	<td>
	 <input type="text" name="createTime"  id="createTime" readonly="true"  bind="datepicker" />
	</td>
	</tr>
	<tr><td> <br/></td></tr>
	<tr>
	<td>描述：</td>
	<td width="400">
	<textarea cols="100" id="content" name="content" rows="10"></textarea>
	</td>
	</tr>
	<tr>
	<td colspan="2" align="center"><input type="submit" value="提交"> 
	 <input type="reset" value="重置" >
	</td>
	</tr>
	</table>	
</form>
 </div>
</div>
</div>
</div>
	<script type="text/javascript">//<![CDATA[
window.CKEDITOR_BASEPATH='../ckeditor/';
//]]></script>
<script type="text/javascript" src="${contextPath}/ckeditor/ckeditor.js?t=B37D54V"></script>

<script type="text/javascript">//<![CDATA[
CKEDITOR.replace('content',{toolbar : 'MyToolbar'});
//]]></script>
<script type="text/javascript">
 
</script>

</body>
</html>
