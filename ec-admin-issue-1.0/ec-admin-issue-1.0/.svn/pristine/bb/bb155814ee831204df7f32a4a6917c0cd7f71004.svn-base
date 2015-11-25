<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../../snippets/meta.jsp"%>
<title>没有默认区域列表</title>
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-system.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content">
				<div id="roadmap-body">
					<h4>没有默认乡镇区域列表</h4>
					<table class="list-table">
						<tr>
							<th>国家</th>
							<th>省</th>
							<th>市</th>
							<th>区</th>
							<th>操作</th>
						</tr>
						<c:forEach var="default" items="${defaultArea }">
							<tr>
								<td>${default.country }</td>
								<td>${default.provincea }</td>
								<td>${default.citya }</td>
								<td>${default.districta }</td>
								<td>
									<a href="/default?directionId=${default.directionid }&provinceId=${default.provinceid }&cityId=${default.cityid }&districtId=${default.districtid }">指定默认区域</a>
								</td>
							</tr>
						</c:forEach>

					</table>
				</div>
				<div id="roadmap-show">
					<div id="show-div">
						<span id="show"></span>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>