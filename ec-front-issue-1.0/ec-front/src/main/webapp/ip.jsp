<%@page contentType="text/html; charset=UTF-8"%>
<%

out.write(request.getHeader("X-Forwarded-For"));
out.write("<br/>");
out.write(request.getHeader("X-Real-IP"));
out.write("<br/>");
out.write(request.getRemoteAddr());
out.write("<br/>");
out.write(request.getHeader("x-forwarded-for"));
out.write("<br/>");
out.write(request.getHeader("Proxy-Client-IP"));
out.write("<br/>");
out.write(request.getHeader("WL-Proxy-Client-IP"));
%>