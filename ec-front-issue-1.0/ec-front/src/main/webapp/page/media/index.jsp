<%@page pageEncoding="UTF-8"%><%@include file="../../page/snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>音像频道首页-文轩网</title>
<meta name="description" content="文轩网音像频道,新华文轩旗下新华书店网上书店,文轩网作为

新华书店网上商城提供网络书店网上买书,快递送货上门"/>
<meta name="keywords" content="文轩网,新华文轩,新华书店,音像,音像店,音像制品,音像制品网,

音像制品批发"/>

<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="index" name="type"/>
</jsp:include>

</head>

<body class="mediaIndex">
<jsp:include page="/page/snippets/version2/header.jsp">
		<jsp:param value="media" name="label" />
</jsp:include>
<div class="layout">
<div class="your_path cl">你现在的位置：<a href="http://www.winxuan.com/" class="link3">

文轩网</a> &gt;  <c:import url="/fragment/40099"/> </div>
  <div class="left_box">
    <div class="music_leftmenu">
      <h2><a href="http://list.winxuan.com/7791" target="_blank" title="音乐">音乐

</a></h2>
      <ul>
        <li data-id="7806"><a href="http://list.winxuan.com/7806" title="流行音乐">流行音

乐</a></li>
        <li data-id="7796"><a href="http://list.winxuan.com/7796" title="古典音乐">古典音

乐</a></li>
        <li data-id="7792"><a href="http://list.winxuan.com/7792" title="休闲音乐">休闲音

乐</a></li>
        <li data-id="7793"><a href="http://list.winxuan.com/7793" title="儿童音乐">儿童音

乐</a></li>
        <li data-id="7795"><a href="http://list.winxuan.com/7795" title="发烧碟">发烧碟

</a></li>
        <li data-id="7805"><a href="http://list.winxuan.com/7805" title="民族音乐">民族音

乐</a></li>
        <li data-id="7809"><a href="http://list.winxuan.com/7809" title="电子音乐">电子音

乐</a></li>
        <li data-id="7815"><a href="http://list.winxuan.com/7815" title="进口音乐">进口音

乐</a></li>
        <li data-id="7799"><a href="http://list.winxuan.com/7799" title="影视音乐">影视音

乐</a></li>
        <li data-id="7801"><a href="http://list.winxuan.com/7801" title="戏曲/曲艺">戏曲/

曲艺</a></li>
      </ul>

      <h2><a href="http://list.winxuan.com/7788" target="_blank" title="影视">影视

</a></h2>
      <ul>
        <li data-id="7810"><a href="http://list.winxuan.com/7810" title="电影">电影

</a></li>
        <li data-id="7811"><a href="http://list.winxuan.com/7811" title="电视剧">电视剧

</a></li>
        <li data-id="7794"><a href="http://list.winxuan.com/7794" title="卡通动画">卡通动

画</a></li>
        <li data-id="7797"><a href="http://list.winxuan.com/7797" title="少儿">少儿

</a></li>
        <li data-id="7808"><a href="http://list.winxuan.com/7808" title="生活">生活

</a></li>
        <li data-id="7812"><a href="http://list.winxuan.com/7812" title="科普">科普

</a></li>
        <li data-id="7800"><a href="http://list.winxuan.com/7800" title="戏剧">戏剧

</a></li>
      </ul>
       <h2><a href="http://list.winxuan.com/7790" target="_blank" title="软件">软件

</a></h2>
      <ul>
        <li data-id="7807"><a href="http://list.winxuan.com/7807" title="游戏">游戏

</a></li>
        <li data-id="7798"><a href="http://list.winxuan.com/7798" title="工具">工具

</a></li>
        <li data-id="7802"><a href="http://list.winxuan.com/7802" title="教育">教育

</a></li>
      </ul>
      <h2><a href="http://list.winxuan.com/7789" target="_blank" title="教学">教学

</a></h2>
      <ul>
        <li data-id="7813"><a href="http://list.winxuan.com/7813" title="管理">管理

</a></li>
        <li data-id="7814"><a href="http://list.winxuan.com/7814" title="英语学习">英语学

习</a></li>
        <li data-id="7803"><a href="http://list.winxuan.com/7803" title="教育考试">教育考

试</a></li>
        <li data-id="7957"><a href="http://list.winxuan.com/7957" title="小语种">小语种

</a></li>
        <li data-id="7804"><a href="http://list.winxuan.com/7804" title="教辅">教辅

</a></li>
      </ul>
      <p class="txt_center"><a class="khaki" 

href="http://www.winxuan.com/catalog_media.html">详细音像分类 &gt;&gt;</a></p>
    </div>
    <script>seajs.use("MediaMenu",function(MediaMenu){new MediaMenu();})</script>
  </div>
  <div class="right_box">
    <jsp:include page="../../page/fragment/media/adsbox.jsp" flush="true"></jsp:include>
    <jsp:include page="../../page/fragment/media/hotnews.jsp" flush="true"></jsp:include>
    <div class="hei10"></div>
    <jsp:include page="../../page/fragment/media/flashsale.jsp" 

flush="true"></jsp:include>
    <jsp:include page="../../page/fragment/media/express.jsp" flush="true"></jsp:include>
  </div>
           
  <jsp:include page="../../page/fragment/media/recommend.jsp"></jsp:include>
  <jsp:include page="../../page/fragment/media/hotsale.jsp"></jsp:include>
  <c:import url="/fragment/490"></c:import>
  <%-- 
  <jsp:include page="../../page/fragment/media/clearancesale.jsp"></jsp:include>
--%>
</div>
<%@include file="/page/snippets/version2/footer.jsp" %>
</body>
</html>
