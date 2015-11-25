<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan-page" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.winxuan.com/tag/winxuan-string" prefix="winxuan-string" %>
<%@ taglib uri="http://www.winxuan.com/cache" prefix="cache" %>
<%@page import="java.util.Random"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%--
 
	新版的 tags.jsp 不再在jsp中编写直接量
	同时兼容老版的引用
	version 不再由程序员维护，将由POM的自动化过程中生成version版本标识
		
 --%>
<c:set var="serverPrefix" value="${applicationScope.miscConfig.server}/" />
<c:set var="miscServer" value="${applicationScope.miscConfig.server}" />
<c:set var="version" value="${applicationScope.miscConfig.version}" />

<%--
	新版前端资源部署结构调整所需常量声明
--%>

<c:set var="isRelease" value="${applicationScope.miscConfig.release}" />
<c:set var="jsPrefix" value="${applicationScope.miscConfig.jsPrefix}" />
<c:set var="cssPrefix" value="${applicationScope.miscConfig.cssPrefix}" />