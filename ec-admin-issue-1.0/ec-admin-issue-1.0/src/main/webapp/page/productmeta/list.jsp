<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.table{width:70%;}
</style>
<%@include file="../snippets/meta.jsp"%>
<title>商品分类属性维护</title>
<link type="text/css" href="${pageContext.request.contextPath}/css/productmeta/productMetaEnum.css" rel="stylesheet"/>
</head>
<body>
    <!-- 引入JS部分 -->
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<!-- 查询表单div -->
				<div id="queryForm">
				<h4>商品分类属性维护</h4><hr/>
					<form class="inline" action="/productmeta/list" method="get"
						id="searchform">
						<table class="table">
							<tr>
								<td>属性名:<input name="name" type="text" />前台展示:<input type="checkbox" name="show" value="1" /> 是  <input type="checkbox" name="show" value="0"/> 否 </td>
								<td> 状态: <input type="checkbox" name="available" value="1"> 有效 <input type="checkbox" name="available" value="0" /> 无效 </td>
								<td><button>查询</button></td>
							</tr>
							<tr><td><button type="button" onclick="gotoAdd();">添加属性</button></td></tr>
						</table>
					</form>
				</div>
				<div>
					<c:if test="${list!=null}">
					<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
						<form action="" id="form" method="get">
							<table class="list-table">
								<tr>
									<th>属性名</th>
									<th>属性类型</th>
									<th>属性长度</th>
									<th>是否可为空</th>
									<th>默认值</th>
									<th>是否前台显示</th>
									<th>属性分类</th>
									<th>状态</th>
									<th>描述</th>
									<th>操作</th>
								</tr>
								<c:forEach items="${list}" var="arr">
									<tr>
										<td>${arr.name}</td>
										<td><c:if test="${arr.type.id==12004 }"><a href="javascript:void(0)" onclick="gotoproductMetaEnum(${arr.id});">${arr.type.name }</a></c:if><c:if test="${arr.type.id!=12004 }">${arr.type.name }</c:if></td>
										<td>${arr.length }</td>
										<td><c:if test="${arr.allowNull }">是</c:if><c:if test="${!arr.allowNull }">否</c:if></td>
										<td>${arr.defaultValue}</td>
										<td><c:if test="${arr.show }">是</c:if><c:if test="${!arr.show }">否</c:if></td>
										<td><c:if test="${arr.category==1 }">图书</c:if><c:if test="${arr.category==7786 }">音像</c:if><c:if test="${arr.category==7787 }">百货</c:if> </td>
										<td><c:if test="${arr.available }">有效</c:if><c:if test="${!arr.available }">无效</c:if></td>
										<td>${arr.description}</td>
										<td><a href="/productmeta/${arr.id}/goedit">修改</a></td>
									</tr>
								</c:forEach>
								<tr>
									<th>属性名</th>
									<th>属性类型</th>
									<th>属性长度</th>
									<th>是否可为空</th>
									<th>默认值</th>
									<th>是否前台显示</th>
									<th>属性分类</th>
									<th>状态</th>
									<th>描述</th>
									<th>操作</th>
								</tr>
							</table>
						</form>
					</c:if>
					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
					</c:if>
				</div>

			</div>
		</div>
	</div>
	<div id="productMetaEnumdiv" >
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/productmeta/productMetaEnum.js"></script>
<script type="text/javascript">
	function gotoAdd(){
		location.href = "/productmeta/goadd";
	}
</script>
</body>
</html>