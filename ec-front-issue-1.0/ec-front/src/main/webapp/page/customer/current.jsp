<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.winxuan.com/tag/constant" prefix="winxuan-constant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>XXX</title>
</head>
<body>
<c:if test="${customer!=null}">
${customer.name }
</c:if>

${test }
<winxuan-constant:out name="orderStatusMap" parameter="5" />
<winxuan-constant:out name="orderStatusMap" parameter="${test}" />
</body>
</html>