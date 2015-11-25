<%@ page contentType="text/html;charset=UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/cache" prefix="cache"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>退换货流程-文轩网</title>
		<jsp:include page="/page/snippets/v2/meta.jsp"><jsp:param value="help" name="type"/></jsp:include>
	</head>
	<body>
	<div class="wrap">
		<jsp:include page="/page/snippets/v2/header.jsp"></jsp:include>
		<jsp:include page="/page/snippets/v2/navigation.jsp">
	 		<jsp:param value="false" name="index" />
		</jsp:include>
			<div class="layout">
			<jsp:include page="help_menu.jsp">
			  	<jsp:param value="help_17" name="label"/>
			  </jsp:include>
			  <div class="right_box">
			  
  <cache:fragmentPageCache idleSeconds="86400"	key="FRAGMENT_ID_901">
			 <c:import url="/fragment/901" />
		</cache:fragmentPageCache>
  </div>
			  <div class="hei10"></div>
			</div>
		<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
		</div>
	</body>
</html>