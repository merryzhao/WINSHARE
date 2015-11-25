<%@page pageEncoding="UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文轩网_百货_百货分类页</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="bookshop" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="/page/snippets/version2/header.jsp">
	<jsp:param value="mall" name="label"/>
</jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置：<a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  <c:import url="/fragment/${page}/711"/></div>
  <div class="left_box">
  	<c:import url="/fragment/${page}/710"/>
    
    
    
  	<c:import url="/fragment/${page}/701"/>
    <jsp:include page="../fragment/mall/702.jsp"/>
  </div>
  <div class="right_box">
  	<c:import url="/fragment/${page}/703"/>
  	<jsp:include page="/page/fragment/mall/type1.jsp"></jsp:include>
  	<jsp:include page="/page/fragment/mall/type2.jsp"></jsp:include>
  	<jsp:include page="/page/fragment/mall/type3.jsp"></jsp:include>
    
    
    <br class="cl"/>
  </div>
  <div class="hei10"></div>
</div>

<script type="text/javascript" src="${serverPrefix}/js/book/common.js"></script>
<script type="text/javascript" src="${serverPrefix}/js/ajaxpage/mallvisit.js"></script>
<%@include file="/page/snippets/version2/footer.jsp" %>
</body>
</html>