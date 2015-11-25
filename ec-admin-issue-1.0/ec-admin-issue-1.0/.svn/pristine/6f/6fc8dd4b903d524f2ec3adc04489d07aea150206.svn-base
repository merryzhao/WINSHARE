<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>查看默认乡镇</title>
<%@include file="../../snippets/meta.jsp"%>
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
</head>
<body>
<div class="frame" >
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-system.jsp"%>
	<div class="frame-main">
		<form action="/default/saveupdate" method="post">
		<div>
			<h4>查看当前默认乡镇</h4>
				<table class="">
				<tr>
					<td class="property">区域<label class="red">*</label>
					</td>
					<td colspan="10">
						<select areaLevel="country" name="country" id="country" >
								<option value="-1">请选择国家</option>
						</select> 
						<select areaLevel="province" name="province" id="province">
								<option value="-1">请选择省份</option>
						</select>
						<select areaLevel="city" name="city" id="city">
								<option value="-1">请选择城市</option>
						</select>
						<select areaLevel="district1" name="id" id="district">
								<option value="-1">请选择区县</option>
						</select> 
						<b></b>
					</td>
				</tr>
			</table>
		</div>
		</form>
	</div>
</div>
 	<%@include file="../../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath}/js/area/areadata.js"></script>
	<script type="text/javascript"src="${contextPath}/js/area/areaevent.js"></script>
	<script type="text/javascript" src="${contextPath}/js/area/areadefault.js"></script>
</body>
</html>