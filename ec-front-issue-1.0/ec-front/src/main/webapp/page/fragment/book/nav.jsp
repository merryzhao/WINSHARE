<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="left_container" fragment="${fragment.id}">
				<h4 class="box_title txt_center">
					<b class="fb f14">图书分类</b>
				</h4>
<dl class="directory">
    <c:forEach var="content"  items="${contentList}" varStatus="status">
        <c:if test="${content.src=='Y'}">
					<dd>
						<h2>
							<a href="${content.href}" target="_blank" title="${content.title}">${content.name}</a>
						</h2>
						  <p>
						   <c:forEach var="childen"  items="${contentList}" varStatus="status">
						   <c:if test="${childen.src == content.name }">
						       <a href="${childen.href}" target="_blank" title="${childen.title}">${childen.name}</a>
						   </c:if>
						    </c:forEach>
					     </p>
			  </dd>
         </c:if> 
	</c:forEach>			
					<dt>
						<a href="http://www.winxuan.com/catalog_book.html">图书详细分类&gt;&gt;</a>
					</dt>
				</dl>
</div>