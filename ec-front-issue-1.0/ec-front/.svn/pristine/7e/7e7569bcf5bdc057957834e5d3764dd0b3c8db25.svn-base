<%@page pageEncoding="UTF-8" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <h3 class="fb box_title">${fragment.category.name}专区</h3>
      <h4><a class="khaki" href="http://www.winxuan.com/catalog_book.html">图书分类大全>></a></h4>
      <dl class="library_catalog" fragment="${fragment.id}">
        <dt><span name="typename" style="font-weight:700">${fragment.category.name}</span></dt>
        <c:forEach var="content" items="${fragment.category.validChildren}"  varStatus="status">
           <dd><a href="http://list.winxuan.com/${content.id}">${content.name}</a></dd>
        </c:forEach>
      </dl>
      
      <div style="display: none;">
      <dl class="library_catalog">
        <dt>特色分类</dt>
        <dd><a href="javascript:;">畅销排行榜</a>(<span name="typename">管理</span>)</dd>
        <dd><a href="javascript:;">新书排行榜</a>(<span name="typename">管理</span>)</dd>
        <dd><a href="javascript:;">推荐排行榜</a>(<span name="typename">管理</span>)</dd>
      </dl>
      </div>