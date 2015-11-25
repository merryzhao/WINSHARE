<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/scripts.jsp"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>商品信息查询</title>
<script type="text/javascript" src="../../js/manufacturer/list.js"></script>

</head>
<body
	<c:if test="${productQueryForm.ismore==true}">
onload="inorvisible('moreterm');"
</c:if>>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<div>
				<form action="/manufacturer/list" method="post">
					<ul>
						<li>出 版 商:<input type="text" name="name" /></li>
						<li>子出版商:<input type="text" name="nickname" /></li>
						<li><input type="submit" value="查询"/><input type="button" value="添加" onclick="manufacturer.add()"/></li>
						
					</ul>
				</form>
			</div>
			<hr />
			<!-- 核心内容部分div -->
			
			<div class="frame-main-inner" id="content">				
				<table  class="list-table">
					<tr>
						<th>出版社名称</th>
						<th>编辑</th>
						<th>子出版社</th>
					</tr>
				<c:forEach var="m" items="${manufacturers }">
					<tr>
						<td>${m.name }</td>
						<td>
							<input type="button" value="编辑名称" onclick="manufacturer.edit(${m.id},'${m.name}');" />
							<input type="button" value="追加" onclick="manufacturer.append(${m.id});" />
						</td>
						<td>

							<c:forEach var="mi" items="${m.items }" varStatus="s" >
								${mi.name }
								<c:if test="${!s.last}">,</c:if>
								<c:if test="${s.count%3== 0}"> <br/> </c:if>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
				</table>
			</div>
			
			<c:if test="${not empty pagination}">	
				<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
			</c:if>
		</div>
	</div>
</body>

</html>