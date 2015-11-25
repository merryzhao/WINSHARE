<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>商品详情</title>
</head>
<body>
Hello World!</br>
<c:if test="${product!=null}">
	图书名称: 《 ${product.name}》 </br>
	<c:if test="${product!=null}">
	作者:  ${product.author} </br>
	</c:if>
	ISBN号：${product.barcode} </br>
	图书定价: ${product.listPrice}</br>
	
	图书描述信息：
	
	<c:forEach items="${product.descriptionList}" var="description" varStatus="#line">
	   </br> ----------------${description.name}----------------${line}
	   ${description.content}
	</c:forEach>
</c:if>
</body>
</html>