<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.winxuan.com/tag/constant" prefix="winxuan-constant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>SPring MVC && Hibernate Vliadator</title>
<style type="text/css">
	.error{
		color: gray;
	}
</style>
</head>
<body>
	 <form:form action="/test/valid/post" method="post" commandName="testValid">
        <div>
            登 录 名
            : <input name="name" type="text" /><form:errors element="testValid" delimiter="," path="name" cssClass="error"/>
            <spring:hasBindErrors name="name"></spring:hasBindErrors>
        </div>
        <div>
            密  码
            :<input name="password" type="text" value="1"/><form:errors delimiter="," path="password" cssClass="error"/>
        </div>
        <div>
            密  码
            :<input name="passwordConfirm" type="text" /><form:errors delimiter="," path="passwordConfirm" cssClass="error"/>
        </div>
        <div>
        	  <form:hidden path="randomCode" />
            <input type="submit" value="  登 录  "/>
        </div>
    </form:form>
    <br/><br/><br/>international:<br/>
    NotEmpty.testValid.name:<spring:message code="NotEmpty.testValid.name" />
    <br/><br/><br/>request filederror:<br/>
    <c:if test="${result!=null && result.fieldErrors!=null }">
    		<c:forEach var="error" items="${result.fieldErrors}">
    		  ${error.code} - ${error.objectName} -  ${error.field} -    ${error.defaultMessage} </br>
    		</c:forEach>
    </c:if>
</body>
</html>