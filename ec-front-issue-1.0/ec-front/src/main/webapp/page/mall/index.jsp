<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>百货频道首页-文轩网</title>
<meta name="description" content="文轩网百货频道提供,箱包,手表,数码,家电,家居,美妆,食品,茶叶,母婴,玩具,帽子,围巾等百货商品,快递送货上门。"/>
<meta name="keywords" content="文轩网,百货,箱包,手表,数码,家电,家居,美妆,食品,茶叶,母婴,玩具,帽子,围巾"/>

<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="mall" name="type" />
</jsp:include>
</head>
<body class="mall">
	<jsp:include page="/page/snippets/version2/header.jsp">
		<jsp:param value="mall" name="label" />
	</jsp:include>
		<div class="layout">
		 <div class="your_path cl">你现在的位置：<a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  <c:import url="/fragment/50099" /></div>
		<div class="left_box">
			  <%--<dl class="goods_leftmenu">
			      <dd data-id="8087"><a href="http://list.winxuan.com/8087" title="箱包">箱包</a></dd>
			      <dd data-id="8121"><a href="http://list.winxuan.com/8121" title="手表">手表</a></dd>
			      <dd data-id="7959"><a href="http://list.winxuan.com/7959" title="数码产品">数码产品</a></dd>
			      <dd data-id="7971"><a href="http://list.winxuan.com/7971" title="生活家电">生活家电</a></dd>
			      <dd data-id="7975"><a href="http://list.winxuan.com/7975" title="家居用品">家居用品</a></dd>
			      <dd data-id="7999"><a href="http://list.winxuan.com/7999" title="茶叶">茶叶</a></dd>
			      <dd data-id="8000"><a href="http://list.winxuan.com/8000" title="美妆">美妆</a></dd>
			      <dd data-id="8003"><a href="http://list.winxuan.com/8003" title="食品">食品</a></dd>
			      <dd data-id="8004"><a href="http://list.winxuan.com/8004" title="母婴">母婴</a></dd>
			      <dd data-id="8020"><a href="http://list.winxuan.com/8020" title="">围巾/帽子</a></dd>
			      <dd data-id="8028"><a href="http://list.winxuan.com/8028" title="玩具">玩具</a></dd>
			      <dt><a href="http://www.winxuan.com/catalog_book.html">全部图书</a>/<a href="http://www.winxuan.com/catalog_media.html">音像</a>/<a href="http://www.winxuan.com/catalog_mall.html">百货分类</a> &gt;&gt;</dt>
			    </dl>--%>
			 	 <c:import url="http://www.winxuan.com/category/mall/nav"></c:import>
			 	<script>seajs.use("MallMenu",function(MallMenu){new MallMenu();});</script>
   				 <h4 class="business_tit margin10">入住商家</h4>
  				 <jsp:include page="/page/fragment/mall/mallmerchant.jsp" flush="true" />
  </div>
		<div class="right_box">
			<jsp:include page="/page/fragment/mall/adsbox.jsp" flush="true" />
			<jsp:include page="/page/fragment/mall/notice.jsp" flush="true" />
			<div class="hei10"></div>
			<jsp:include page="/page/fragment/mall/flashsale.jsp" flush="true" />
		   <div class="hei10"></div>
			<div table="hotandnew" cn="current_sort">
				<dl class="sort_tab">
					<dt></dt>
					<dd class="current_sort" label="hotandnew">
						<a href="javascript:;">新品上架</a>
					</dd>
					<dd label="hotandnew">
						<a href="javascript:;">热销专区</a>
					</dd>
				</dl>
			     <jsp:include page="/page/fragment/mall/newproduct.jsp" flush="true"></jsp:include>
	             <jsp:include page="/page/fragment/mall/hotproduct.jsp" flush="true"></jsp:include>
			</div>
		</div>
		<jsp:include page="/page/fragment/mall/1F.jsp" flush="true"></jsp:include>
		<jsp:include page="/page/fragment/mall/2F.jsp" flush="true"></jsp:include>
		<jsp:include page="/page/fragment/mall/3F.jsp" flush="true"></jsp:include>
		<jsp:include page="/page/fragment/mall/4F.jsp" flush="true"></jsp:include>
		<jsp:include page="/page/fragment/mall/5F.jsp" flush="true"></jsp:include>
		<jsp:include page="/page/fragment/mall/6F.jsp" flush="true"></jsp:include>
		</div>
		<%-- 去掉百货中的微薄信息 <jsp:include page="/page/fragment/mall/about.jsp"></jsp:include> --%>
	    <jsp:include page="/page/snippets/version2/footer.jsp"></jsp:include>
</body>
</html>
