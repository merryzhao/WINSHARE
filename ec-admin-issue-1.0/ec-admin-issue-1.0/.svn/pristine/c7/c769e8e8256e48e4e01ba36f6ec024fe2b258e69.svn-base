<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>印刷卡</title>
<style type="text/css">
 .showinfo{
   border:2px solid #DFDFDF;
   margin-top: 5px;
}
 
#ifr{
  width: 770px;
  height: 700px;
  }
  
  label.error {
	padding:0.1em;
}
#printForm input.error {
	padding:0px;
	border: 2px solid red;
}
 </style>
</head>
<body>
<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
<div id="content-result">
    <div id="the-count"  >
	<h4>印刷礼品卡 </h4>
    <form action="/presentcard/printlist" target="list-card" method="post" id="printForm">
                     印刷数量：<input type="text" size="5" maxlength="5" id="count" name="count" >
        <input type="submit" value="提交"><label for="count" generated="true" class="error" style="display: none;"></label>
        </form>
    </div>
	 <hr/>
     <div id="ifr">
   	 <iframe id="list-card" name="list-card" width="100%" height="80%" frameborder="0"></iframe>
     </div>
</div>
</div>
</div>
</div>
	<%@include file="../snippets/scripts.jsp"%>
    <script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.methods.min.js"></script>
		<script type="text/javascript">
 $().ready(function() {
		// 表单验证
		$("#printForm").validate({
			rules : {
				count : {
					required : true,
					number : true,
					range : [1,99999]
				} 
			},
			messages : {
				count : {
					required : "请输入数量",
					number : "输入错误",
					range : "范围错误"
				} 
			}
		});

	});
 </script>
</body>
</html>
