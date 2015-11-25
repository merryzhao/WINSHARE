<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>XXX</title>
</head>
<body>

<form action="/customeraddress" method="post">
	consignee<input name="consignee" type="text"><br/>
	address<input name="address" type="text"><br/>
	district.id<input name="district.id" type="text"><br/>
	<input type="submit" value=" Submit"><br/>
</form>



<form action="/customeraddress" method="put">
	<input name="_method" type="hidden" value="put"/><br/>
	consignee<input name="consignee" type="text"><br/>
	address<input name="address" type="text"><br/>
	district.id<input name="district.id" type="text"><br/>
	<input type="submit" value=" Submit"><br/>
</form>

</body>
</html>