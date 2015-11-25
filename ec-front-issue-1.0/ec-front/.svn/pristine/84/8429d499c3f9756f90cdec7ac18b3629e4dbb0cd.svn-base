<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="pro_goods" fragment="${fragment.id}">
<dl class="sort_tab goods_sort_tab">
      <dt></dt>
      <dd class="current_tab"><a href="#" title="">${fragment.name}</a></dd>
    </dl>
    <div class="new_tab"><a class="current_no" href="javascript:;">1</a><a href="javascript:;">2</a><a href="javascript:;">3</a></div>
    <div class="new_album" >
    <a href="javascript:;" class="pre_page"></a>
    <a href="javascript:;" class="next_page"></a>
    <div class="album_box small_box">
    
    	<c:forEach var="content" items="${contentList}"  varStatus="status">
		    <dl class="goods_rush music_goods">
		        <dt> <a href="${content.url}" title="${content.name}"><img class="book_img" src="${content.imageUrl}" alt="${content.name}"></a></dt>
		        <dd class="goods_tit"><a href="${content.url}" title="${content.name}"><winxuan-string:substr length="12" content="${content.name}"/></a></dd>
		        <dd><del class="l_gray">定价：￥ ${content.listPrice}</del><br>
		          文轩价：<b class="red fb">￥${content.effectivePrice}</b></dd>
		      </dl>
    	</c:forEach>
    
    </div>
    </div>
</div>