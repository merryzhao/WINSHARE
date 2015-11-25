<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>客户端版本管理</title>
<style type="text/css">
textarea {
	width: 350px;
	height: 50px;
	margin-bottom: -5px;
}
#content p{margin:10px 0px 10px 0px;padding:2px;}
select{margin:0px 10px 0px 10px;}
.replyitem{border-bottom: 1px black solid;width:360px;padding: 20px 10px;}
/*
#content div{margin:10px 0px 10px 200px;}
*/
.replayText{display: none;}
</style>
<!-- 引入JS -->
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content" align="center">
				<div>
					${message }
				</div>
				<form action="/version/add" method="post">
					<div align="left">
						版本号：<input type="text" name="version" value="${version }" /> 
					</div>	
					<p>	
					<div align="left">
						操作系统：
						<select name="system">
							<option value="1" <c:if test="${system==1 }">selected="selected""</c:if>>andriod</option>
							<option value="2" <c:if test="${system==2 }">selected="selected""</c:if>>ios</option>
						</select>
					</div>
					<p>
					<div align="left">
						更新地址：
						<textarea rows="4" cols="25" name="updateAddress" >${updateAddress }</textarea>
					</div>
					<p>
					<div align="left">
						更新内容：
						<textarea rows="4" cols="25" name="updateInfo">${updateInfo }</textarea>
					</div>
					<p>
					<!-- <div align="left">
						是否是最新版本：
						<select name="latest">
							<option value="1" selected="selected">是</option>
							<option value="0">否</option>
						</select>
					</div> -->
					<p>
					<div align="left">
						<input type="submit" value="提交" /> 
					</div>
				</form>
				
			</div>
		</div>
	</div>
</body>
</html>