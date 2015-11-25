<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
 <dl class="goods_leftmenu">
<c:forEach var="category" items="${mall.children}" varStatus="status">
	<c:if test="${category.logicDisplay}">
		<dd data-id="${category.id}">
			<a href="${category.categoryHref}" title="${category.alias}">${category.alias}</a>
		</dd>
	</c:if>
</c:forEach>
</dl>
<%-- 
                 <dd data-id="8087"><a href="http://list.winxuan.com/8087" title="箱包">箱包</a></dd>
			      <dd data-id="8121"><a href="http://list.winxuan.com/8121" title="手表">手表</a></dd>
			      <dd data-id="7959"><a href="http://list.winxuan.com/7959" title="数码产品">数码产品</a></dd>
			      <dd data-id="7971"><a href="http://list.winxuan.com/7971" title="生活家电">生活家电</a></dd>
			      <dd data-id="7975"><a href="http://list.winxuan.com/7975" title="家居用品">家居用品</a></dd>
			      <dd data-id="7999"><a href="http://list.winxuan.com/7999" title="茶叶">茶叶</a></dd>
			      <dd data-id="8000"><a href="http://list.winxuan.com/8000" title="美妆">美妆</a></dd>
			      <dd data-id="8003"><a href="http://list.winxuan.com/8003" title="食品">食品</a></dd>
			      <dd data-id="8004"><a href="http://list.winxuan.com/8004" title="母婴">母婴</a></dd>
			      <dd data-id="8020"><a href="http://list.winxuan.com/8020" title="围巾/帽子">围巾/帽子</a></dd>
			      <dd data-id="8028"><a href="http://list.winxuan.com/8028" title="玩具">玩具</a></dd>
			      <dt><a href="http://www.winxuan.com/catalog_book.html">全部图书</a>/<a href="http://www.winxuan.com/catalog_media.html">音像</a>/<a href="http://www.winxuan.com/catalog_mall.html">百货分类</a> &gt;&gt;</dt>
	
	<c:forEach var="category" items="${morebook}" varStatus="status">
			<c:if test="${category.available}">
				<a href="${category.categoryHref}">${category.alias}</a>
			</c:if>
		</c:forEach>
--%>