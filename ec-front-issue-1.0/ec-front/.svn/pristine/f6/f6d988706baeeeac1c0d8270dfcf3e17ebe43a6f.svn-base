<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>移动手机端帮助信息维护</title>
</head>

<body>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="index" name="type" />
</jsp:include>
<script>
;(function(m, o, d, u, l, a, r) {
	  if(m[d]) return;
	  function f(n, t) { return function() { r.push(n, arguments); return t; } }
	  m[d] = a = { args: (r = []), config: f(0, a), use: f(1, a) };
	  m.define = f(2);
	  u = o.createElement('script');
	  u.id = d + 'node';
	  u.src = 'http://static.winxuancdn.com/libs/sea.js';
	  l = o.getElementsByTagName('head')[0];
	  l.insertBefore(u, l.firstChild);
	})(window, document, 'seajs');
	seajs.config({
		"base":"http://static.winxuancdn.com/libs/",
		alias:{
			"jQuery":"jQuery/1.6.2/jQuery",
			"cart":"core/cart",
			"favorite":"core/favorite",
			"config":"module/config",
			"widgets":"module/portal-widgets",
			"winxuan-bar":"module/winxuan-bar",
			"widgets.css":"http://static.winxuancdn.com/css/widgets.css"
		}
	});
	seajs.use("widgets",function(widgets){
		widgets.init();
	});
</script>
<br/>
<br/>
<br/>

<div  style=""fragment="${fragment.id}">
	<c:if test="${fn:length(contentList) <= 0}">
            	暂无内容
	</c:if>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		${content.content}
	</c:forEach>
</div>
</body>
</html>


