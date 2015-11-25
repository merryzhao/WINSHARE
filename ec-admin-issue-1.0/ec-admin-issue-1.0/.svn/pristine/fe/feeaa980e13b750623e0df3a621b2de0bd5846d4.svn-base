<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
select[name=type] {
	margin-right: 20px;
}
</style>
<title>创建礼品卡</title>
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
				<h4>创建礼品卡</h4>
				<hr />
				<form action="/presentcard/new" method="post" onsubmit="return checkform();">
					卡类型：<select name="type">
						<option value="19002">实物卡</option>
						<option value="19001">电子卡</option>
					</select> 数量：<input type="text" name="quantity" />
					<button type="submit">确定</button>
				</form>
				<c:if test="${!empty list }">
					<table class="list-table">
						<tr>
							<th>序号</th>
							<th>卡号</th>
							<th>类型</th>
						</tr>
						<c:forEach items="${list }" var="presentcard"  varStatus="sta">
							<tr>
								<td>${sta.index+1}</td>
								<td><a href="/presentcard/cardinfo/${presentcard.id }">${presentcard.id }</a></td>
								<td><c:if test="${presentcard.type.id==19002}">实物卡</c:if><c:if test="${presentcard.type.id==19001}">电子卡</c:if></td>
							</tr>
						</c:forEach>
						<tr>
							<th>序号</th>
							<th>卡号</th>
							<th>类型</th>
						</tr>
					</table>
				</c:if>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script>
		function checkform(){
			if($("input[name=quantity]").val()==""||isNaN($("input[name=quantity]").val())){
				return false;
			}
			return true;
		}
	</script>
</body>
</html>