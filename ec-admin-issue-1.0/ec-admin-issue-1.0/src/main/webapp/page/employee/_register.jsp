<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户登录</title>
</head>
<body>
 <form:form action="/employee/login" method="post" commandName="loginForm">
        <div>
            Name:
            <form:input path="name"/>
            <form:errors path="name" cssStyle="" />
        </div>
        <div>
            Password:
            <form:input path="password" />
            <form:errors path="password" cssStyle="" />
        </div>
        <div>
            VerifyCode:
            <form:input path="verifyCode" />
            <form:errors path="verifyCode" cssStyle="" />
        </div>
        <div>
            <input type="submit" value="  OK  "/>
        </div>
    </form:form>


</body>
</html>