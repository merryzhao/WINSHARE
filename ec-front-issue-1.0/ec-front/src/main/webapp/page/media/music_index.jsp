<%@page pageEncoding="UTF-8" %><%@include file="../../page/snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><c:choose>
<c:when test="${page == 'music'}">音乐</c:when>
<c:when test="${page == 'movie'}">影视</c:when>
<c:when test="${page == 'soft'}">软件</c:when>
<c:when test="${page == 'teach'}">教学</c:when>
</c:choose>店首页-文轩网</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="media" name="type"/>
</jsp:include>
</head>

<body class="musicIndex">
<jsp:include page="/page/snippets/version2/header.jsp">
	<jsp:param value="media" name="label"/>
</jsp:include>
<div class="layout">
	<div class="your_path cl">你现在的位置：<a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  <c:import url="/fragment/${page}/809"/></div>
  <div class="left_box">
    <c:import url="/fragment/${page}/808"/>
    
    
    <c:import url="/fragment/${page}/801"/>
  </div>
  <div class="right_box">
    <c:import url="/fragment/${page}/802"/>
    <jsp:include page="/page/fragment/media/803.jsp"/>
    <div class="hei10"></div>
    <c:import url="/fragment/${page}/804"/>
    <jsp:include page="/page/fragment/media/852.jsp"/>
    <jsp:include page="/page/fragment/media/862.jsp"/>
    <div class="hei10"></div>
    <c:import url="/fragment/${page}/807"/>
  </div>
  <div class="hei10"></div>
</div>

<script type="text/javascript" src="${serverPrefix}/js/book/common.js"></script>
<%@include file="/page/snippets/version2/footer.jsp" %>
<script type="text/javascript">
		seajs.use(["jQuery","table"],function($,table){
				$("div[table]").each(function(){
					var el=$(this);
					table({
						context:el,
						label:"*[label='"+el.attr("table")+"']",
						className:el.attr("cn"),
						content:"*[content='"+el.attr("table")+"']"
					});
				});
				$(".menu_border dd").hover(function(){
					$(this).addClass("have_bg");
				},function(){
					$(this).removeClass("have_bg");
				});
			});
		seajs.use([ 'jQuery', 'roller', 'shopslide' ], function($, Roller, ShopSlide) {
			Roller({
				context : $("div.right_box"),
				paging : true,
				page : 3,
				selector : {
					container : "div.pro_goods",
					page : "div.new_tab a",
					next : "a.next_page",
					prev : "a.pre_page",
					box : "div.album_box",
					items : "dl"
				},
				className : {
					pageSelected : "current_no"
				}
			});
			new ShopSlide({
				speed : 3000
			});
		});
	</script>



</body>
</html>
