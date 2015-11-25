<%@page contentType="text/javascript; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
String callback=request.getParameter("callback");
out.print(callback+"({'current':"+System.currentTimeMillis()+"});");
%>