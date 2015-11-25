<%@ page contentType="text/html;charset=utf-8"%>
<%
	String unionName = (String)request.getParameter("cps");
	if("zswm".equals(unionName)){
		request.getRequestDispatcher("/query/14").forward(request, response);
	}
%>