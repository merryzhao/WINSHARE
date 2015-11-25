<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>

       <ul class="pro_bookstore" fragment="${fragment.id}">
          <dl class="artists_recom">
           <jsp:include page="../null.jsp"/>
           <c:forEach var="content" items="${contentList}">
             <dd>
             <a class="pro_img" href="${content.url }">
             <img src="${content.imageUrl}" alt="<c:out value="${content.product.name }"></c:out>"></a>
              <h3><a href="http://search.winxuan.com/search?author=<winxuan-string:encode content="${content.author}"></winxuan-string:encode>" class="khaki fb haveline">${content.author }</a> 推荐：</h3>
              <p class="book_name"><a class="link4" href="${content.url }" title="<c:out value="${content.name}"></c:out>">${content.name}</a></p>
              <p><b class="fb red f14">￥${content.effectivePrice}</b> <b class="l_gray"> <fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>折</b></p>
             </dd>
            </c:forEach>
            </dl>
         </ul>