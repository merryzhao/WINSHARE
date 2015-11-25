<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索规则</title>
<%@include file="../../snippets/meta.jsp"%>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-search.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content">
				<table width="80%">
					<tr>
						<th width="5%">id</th>
						<th width="30%">描述</th>
						<th width="5%">父類</th>
						<th width="20%">类别</th>
						<th width="15%">权重</th>
						<th width="10%">是否可用</th>
						<th width="10%">操作</th>

					</tr>
					<c:forEach items="${rules}" var="rules" varStatus="status">
						<c:if test="${rules.ct==3 }">
							<form action="/search/saveOrUpdate?id=${rules.id }" method="post">
								<tr>
									<td>${rules.id }</td>
									<td>${rules.description}</td>
									<td>${rules.parent.id }</td>
									<td>
										<c:if test="${rules.ct==3}">百货</c:if>
									</td>
									<td>
									
										<c:if test="${rules.boost != null }">
											<input type="text" value="${rules.boost}" name="boost">										
										</c:if>

									</td>
									
									<td>
									<c:if test="${rules.available!=null}">
									<input type="checkbox" name="available"
										class="available" 
										<c:if test="${rules.available==1}">checked="checked"</c:if>
										
										/>
									</c:if>	
									</td>
									
									<td><input type="submit" value="修改"></td>
								</tr>
							</form>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="rule"></div>
	<%@include file="../../snippets/scripts.jsp"%>
	<!-- <script>
 	function update(id,obj){
		
		var url = '/search/saveorupdate?format=json';
		$.ajax({
			type : 'POST',
			url : url,
			data: "id="+id,
			error : function() {	
			},
			success : function(data) { 
				
			}
		});
		
	}
			
	
 	</script> -->

</body>
</html>