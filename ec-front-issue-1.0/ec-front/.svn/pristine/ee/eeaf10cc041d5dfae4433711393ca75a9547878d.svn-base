<%@page pageEncoding="UTF-8" %><%@include file="../../snippets/tags.jsp"%>
<div class="new_books" fragment="${fragment.id}">
        <h4>${fragment.name}</h4>
        <div class="item_box">
         	<c:forEach var="content" items="${contentList}"  varStatus="status">
		        <dl class="new_item">
		       
		          <dt><a target="_blank" href="${content.url}" title=" ${content.name}"><img src="${content.imageUrl}" alt="${content.name}"></a></dt>
		          <dd><strong><a href="${content.url}" target="_blank" class="link1" title=" ${content.name}"> <winxuan-string:substr length="14" content="${content.name}"></winxuan-string:substr></a></strong></dd>
		          <dd>出版:<fmt:formatDate  value="${content.product.productionDate}" pattern="yyyy年MM月dd日"></fmt:formatDate><br>
		            <b class="red fb">￥ ${content.salePrice}</b> <del>￥ ${content.listPrice}</del></dd>
		        </dl>
        	</c:forEach>
        </div>
       
        <div class="shop_pages"><a class="pre_page2" id='new_pre2' >上一页</a><a class="pre_page" id='new_pre' href="javascript:;">上一页</a> <span id="page_new">第1页 共4页</span> <a class="next_page" href="javascript:;" id='new_next'>下一页</a><a class="next_page2" id='new_next2' >下一页</a></div>
      </div>