<%@page pageEncoding="UTF-8" %>
<%@include file="../../snippets/tags.jsp"%>
 <dl class="re_goods fl" fragment="${fragment.id}">
        <dt><a  href="http://list.winxuan.com/${contentList[0].product.category.id}" title="${fragment.name}"> ${fragment.name}</a></dt>
        
         <c:forEach var="content" items="${contentList}"  varStatus="status">
      <c:choose>
          <c:when test="${status.count ==1 }">
	         <dd class="txt_center"><a target="_blank" href="${content.url}" title="${content.name}"><img src="${content.imageUrl}" alt="${content.name}"></a></dd>
              <dd><b class="fb red"><fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0" ></fmt:formatNumber>折</b><a target="_blank" class="link1" href="${content.url}" title="${content.name}">
              <winxuan-string:substr length="8" content="${content.name}"></winxuan-string:substr>
              </a></dd>
           <dd>
          </c:when>
          <c:otherwise>
           <p><b class="fb red"><fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0" ></fmt:formatNumber>折</b>
           <a target="_blank" href="${content.url}" title="${content.name}">${content.name}</a></p>
          </c:otherwise>
        </c:choose>
     
     </c:forEach>
       </dd>
      </dl>