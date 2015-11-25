<%@page pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<h4 class="box_title txt_center"><b class="fb f14">${fragment.name}</b></h4>
            <dl class="directory" fragment="${fragment.id}">
            <c:forEach var="category" items="${fragment.category.children}">
              <dd>
                    <h2><a href="${category.categoryHref}" title="${category.alias}">${category.alias}</a></h2>
                     <p>
                       <c:forEach var="children" items="${category.children}">
                         <a href="${children.categoryHref}" title="">${children.alias}</a>
                       </c:forEach>
                     </p>
                </dd>
            </c:forEach>     
               <dt><a href="http://www.winxuan.com/catalog_book.html">图书详细分类>></a></dt>
            </dl>