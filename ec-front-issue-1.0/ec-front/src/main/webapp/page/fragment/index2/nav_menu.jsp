<%@page pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.winxuan.com/cache" prefix="cache" %>
<!-- 栏目主体(cont) -->
<div class="cont" fragment="${fragment.id}" cachekey="${param.cacheKey }">
	<jsp:include page="../null.jsp"/>
	<c:forEach var="content" items="${contentList}">
		${content.content}
	</c:forEach>
</div>
<%-- 栏目主体(cont) end --%>
<%-- 菜单列表(list) --%>
<%-- <dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">少儿</a></dt>
	<dd><a href="#" target="_blank">0-2岁低龄</a><b></b></dd>
	<dd><a href="#" target="_blank">3-6岁学龄前</a></dd>
	<dd><a href="#" target="_blank">7-10岁少儿</a><b></b></dd>
	<dd><a href="#" target="_blank">11-14岁</a></dd>
	<dd><a href="#" target="_blank">启蒙与认知</a><b></b></dd>
	<dd><a href="#" target="_blank">科普百科</a></dd>
	<dd><a href="#" target="_blank">家庭教育</a><b></b></dd>
	<dd><a href="#" target="_blank">少儿绘本</a></dd>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">小说</a></dt>
	<dd><a href="#" target="_blank">现当代小说</a><b></b></dd>
	<dd><a href="#" target="_blank">世界名著</a></dd>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">文学</a></dt>
	<dd><a href="#" target="_blank">传记</a><b></b></dd>
	<dd><a href="#" target="_blank">青春与动漫绘本</a></dd>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">学习考试</a></dt>
	<dd><a href="#" target="_blank">教材与教辅</a><b></b></dd>
	<dd><a href="#" target="_blank">医学类考试</a></dd>
	<dd><a href="#" target="_blank">建筑类考试</a><b></b></dd>
	<dd><a href="#" target="_blank">辞典与工具书</a></dd>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">经济管理</a></dt>
	<dd><a href="#" target="_blank">管理学</a><b></b></dd>
	<dd><a href="#" target="_blank">经济与金融</a><b></b></dd>
	<dd><a href="#" target="_blank">投资理财</a></dd>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">励志与成功</a></dt>
	<dd><a href="#" target="_blank">心理学</a><b></b></dd>
	<dd><a href="#" target="_blank">心灵励志</a></dd>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">童书</a></dt>
	<dd><a href="#" target="_blank">儿童文学</a><b></b></dd>
	<dd><a href="#" target="_blank">童话故事</a><b></b></dd>
	<dd><a href="#" target="_blank">必读名著</a></dd>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">生活</a></dt>
	<dd><a href="#" target="_blank">家庭与育儿</a><b></b></dd>
	<dd><a href="#" target="_blank">运动健身</a></dd>
	<dd><a href="#" target="_blank">烹饪与美食</a><b></b></dd>
	<dd><a href="#" target="_blank">时尚美容</a></dd>
	<dd><a href="#" target="_blank">旅游与地图</a><b></b></dd>
	<dd><a href="#" target="_blank">家居手工</a></dd>
	<dd><a href="#" target="_blank">婚恋两性</a></dd>
	
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">科技</a></dt>
	<dd><a href="#" target="_blank">计算机与网络</a><b></b></dd>
	<dd><a href="#" target="_blank">科普读物</a></dd>
	<dd><a href="#" target="_blank">工业技术</a><b></b></dd>
	<dd><a href="#" target="_blank">医学</a><b></b></dd>
	<dd><a href="#" target="_blank">建筑</a></dd>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">艺术与摄影</a></dt>
	<dd><a href="#" target="_blank">摄影</a><b></b></dd>
	<dd><a href="#" target="_blank">绘画</a><b></b></dd>
	<dd><a href="#" target="_blank">书法与字帖</a></dd>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">人文社科</a></dt>
	<dd><a href="#" target="_blank">哲学与宗教</a><b></b></dd>
	<dd><a href="#" target="_blank">历史</a><b></b></dd>
	<dd><a href="#" target="_blank">政治</a></dd>
	<dd><a href="#" target="_blank">书法与字帖</a><b></b></dd>
	<dd><a href="#" target="_blank">法律</a><b></b></dd>
	<dd><a href="#" target="_blank">军事</a></dd>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">套装书</a></dt>
</dl>
<dl class="list list-mainmenu">
	<dt><a href="#" target="_blank">音像·百货·电子书</a></dt>
</dl>
<dl class="list list-mainmenu list-mainmenu-last">
	<dt><a href="#" target="_blank">全部商品分类&gt;&gt;</a></dt>
</dl> --%>
<%-- 菜单列表(list) end --%>