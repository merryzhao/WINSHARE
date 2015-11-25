<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../snippets2/meta.jsp">
	<jsp:param value="webmanage" name="type" />
</jsp:include>

<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="/js/proshop/proshop.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>专业店维护</title>
<style type="text/css">
div.letter {
	background: #D9D9D9;
}

div.proshopinfo {
	display: none;
	position: fixed;
	bottom: 5px;
	right: 10px;
	float: right;
	border: 0px;
	background: #D9D9D9;
}

#reports .span12 {
	border-bottom: 1px #D9D9D9 solid;
	margin-bottom: 20px;
}
</style>
</head>
<body>
	<!-- 引入top部分 -->
	<jsp:include page="../snippets2/frame-top.jsp">
		<jsp:param value="webmanage" name="type" />
	</jsp:include>
	<!-- 引入left菜单列表部分 -->
	<div class="container-fluid">
		<div class="row-fluid">

			<table class="list-table table table-striped">
				<thead>
					<tr>
						<td>编号</td>
						<td>名称</td>
						<td>url</td>
						<td>分类</td>
						<td>版型</td>
						<td>描述</td>
						<td>索引</td>
						<td>是否可用</td>
						<td>操作||<a href="javascript:;" class="add">添加</a>
						</td>
					</tr>
					<tr class="newdata" style="display: none">
						<td name="id">0</td>
						<td name="name" edit="true">请输入专业店名称</td>
						<td name="url" edit="true">填入url</td>
						<td name="category" edit=true>0</td>
						<td name="template" edit="true">请输入版型</td>
						<td name="description" edit="true">描述</td>
						<td name="index" edit="true">0</td>
						<td name="available"><input type="checkbox" checked="true" />
						</td>
						<td><a href="javascript:;" class="submit">确认</a>||<a
							href="javascript:;" class="del">取消</a>
						</td>
					</tr>
				</thead>

				<tbody>

					<c:forEach var="proShop" items="${proShopList}" varStatus="status">
						<tr>
							<td name="id">${proShop.id}</td>
							<td name="name" edit="true">${proShop.name}</td>
							<td name="url" edit="true">${proShop.url}</td>
							
							<c:if test="${fn:length(proShop.categories)>0}">
							<td name="category" edit = "true"><c:forEach var="category" items="${proShop.categories}" varStatus="status" >${category.id}<c:if test="${!status.last}">,</c:if>
                               </c:forEach>
                            </td>
							</c:if>
							<c:if test="${fn:length(proShop.categories)<=0}">
							<td name="category" edit = "true">0</td>
							</c:if>
							
							<td name="template" edit="true">${proShop.template}</td>
							<td name="description" edit="true">${proShop.description}</td>
							<td name="index" edit="true">${proShop.index}</td>
							<td name="available"><input type="checkbox"
								checked="${proShop.available}" /></td>
							<td><a href="javascript:;" class="del">删除</a>||<a
								href="javascript:;" class="update">修改</a>||<a
								href="javascript:;" class="findproshop">分类详情</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>


			</table>
			<div id="wrap">
				<c:if test="${pagination!=null}">
					<winxuan-page:page pagination="${pagination}"
						bodyStyle="javascript"></winxuan-page:page>
				</c:if>
			</div>
		</div>
	</div>
	<div class="proshopinfo">
	
	</div>

</body>
</html>