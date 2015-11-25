<%@page pageEncoding="UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>
<c:choose>
	<c:when test="${page=='management'}">管理</c:when>
	<c:when test="${page=='novel'}">小说</c:when>
	<c:when test="${page=='literature'}">文学</c:when>
	<c:when test="${page=='art'}">艺术</c:when>
	<c:when test="${page=='biography'}">传记</c:when>
	<c:when test="${page=='law'}">法律</c:when>
	<c:when test="${page=='travel'}">旅游</c:when>
	<c:when test="${page=='success'}">励志</c:when>
	<c:when test="${page=='history'}">历史</c:when>
	<c:when test="${page=='philosophy'}">哲学</c:when>
	<c:when test="${page=='economy'}">经济</c:when>
	<c:when test="${page=='finance'}">财会</c:when>
	<c:when test="${page=='marketing'}">营销</c:when>
	<c:when test="${page=='investment'}">投资</c:when>
	<c:when test="${page=='food'}">美食</c:when>
	<c:when test="${page=='health'}">保健</c:when>
	<c:when test="${page=='beauty'}">美妆</c:when>
	<c:when test="${page=='baby'}">育儿</c:when>
	<c:when test="${page=='life'}">生活</c:when>
	<c:when test="${page=='children'}">少儿</c:when>
	<c:when test="${page=='computer'}">计算机</c:when>
	<c:when test="${page=='medicine'}">医学</c:when>
	<c:when test="${page=='architecture'}">建筑</c:when>
	<c:when test="${page=='education'}">教育</c:when>
	<c:when test="${page=='language'}">外语</c:when>
	<c:when test="${page=='exam'}">考试</c:when>
</c:choose>图书首页-文轩网</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="bookshop" name="type"/>
</jsp:include>
</head>

<body class="bookshop">
<jsp:include page="/page/snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置：<a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  <c:import url="/fragment/${page}/99"/> </div>
  <div class="left_box">
    <div class="leftbox_menu">
       <c:import url="/fragment/${page}/11"/>
    </div>
    <div class="leftbox_menu">
      <c:import url="/fragment/${page}/5"/>
    </div>
     <c:import url="/fragment/${page}/6"/>
     <c:import url="/fragment/${page}/10"/>
  </div>
  <div class="right_box">
    <div class="right_box2 fl">
       <c:import url="/fragment/${page}/1"/>
        <c:import url="/fragment/${page}/3"/>
    </div>
    <div class="right_box3 fr">
      <c:import url="/fragment/${page}/2"/>
      <c:import url="/fragment/${page}/4"/>
    </div>
    <div class="hei10"></div>
    <div class="editors_re">
      <h3 class="pro_title"><a href="javascript:void(0);" title="">主编推荐</a></h3>
      <c:import url="/fragment/${page}/21"/>
      <c:import url="/fragment/${page}/22"/>
      <c:import url="/fragment/${page}/23"/>
      <c:import url="/fragment/${page}/24"/>
      <c:import url="/fragment/${page}/25"/>
      <c:import url="/fragment/${page}/26"/>
      <c:import url="/fragment/${page}/27"/>
      <c:import url="/fragment/${page}/28"/>
      <div class="hei10"></div>
    </div>
      <c:import url="/fragment/${page}/7"/>
    <h3 class="pro_title margin10"><a href="http://www.winxuan.com/booktop/0/1/1/1" class="categoryname" title="">排行榜</a></h3>
    <c:import url="/fragment/${page}/31"/>
    <c:import url="/fragment/${page}/32"/>
     <c:import url="/fragment/${page}/33"/>
    <div class="hei10"></div>
    <c:import url="/fragment/${page}/8"/>
     <c:import url="/fragment/${page}/9"/>
  </div>
  <div class="hei10"></div>
</div>

<script type="text/javascript" src="${serverPrefix}/js/book/common.js?${version}"></script>

<%@include file="/page/snippets/version2/footer.jsp" %>
</body>
</html>
