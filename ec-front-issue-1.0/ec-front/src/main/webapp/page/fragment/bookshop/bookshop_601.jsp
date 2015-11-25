<%@page pageEncoding="UTF-8" %>
<%@include file="/page/snippets/tags.jsp"%>
    <div class="shopads_box" fragment="${fragment.id}">
        <jsp:include page="../null.jsp"/>
 
        <ul class="rotation2">
        <c:forEach var="content" items="${contentList}"  varStatus="status">
          <li>${status.count}</li>
    	</c:forEach>
        </ul>
        <ul class="shop_ads">
         <c:forEach var="content" items="${contentList}"  varStatus="status">
            <li><a target="_blank" href="${content.url}" title="${content.title}"><img src="${content.src}" alt="${content.name}"></a></li>
    	</c:forEach>
        </ul>
     </div>
     
     
<script>
seajs.use("slider",function(slider){slider()});
</script>