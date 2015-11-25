<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<body>
	<c:if test="${ empty list }">
		<script>
			parent.noProduct();
		</script>
	</c:if>
	<c:if test="${! empty list }">
	<c:forEach items="${list}" var="attr" varStatus="status">
		<script>
			var jsonobject=
			{
			        eccode:'${attr.eccode}',
			        isbn:'${attr.isbn}',
			        name:'${attr.name}',
			        listprice:'${attr.listprice}',
			        quantity:'${attr.quantity}'
			};
			parent.addProduct(jsonobject);
		</script>
	</c:forEach>
	</c:if>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>