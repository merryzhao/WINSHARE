<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div class="newreleases left_container" fragment="${fragment.id}" cachekey="${param.cacheKey }">
            	<h4 class="fb">${fragment.name }</h4>
            	<c:if test="${fn:length(contentList) <= 0}">
            	暂无内容
            	</c:if>
            	<c:forEach var="content" items="${contentList}" varStatus="status">
            	<dl class="also_viewed" >
                  		<dt>
                  			<div class="newproductimg">
                  			<a href="${content.url}" target="_blank"><img src="${content.imageUrl }" alt="${content.name}"></a>
                  			 </div>
                  			 <div class="pcontent">
                  			   <div class="pname">
                  			     <a href="${content.url}" class="red" title="${content.name}" target="_blank">${content.name}</a>
                  			   </div>             			   <div class="pinfo"><c:set var="descriptions" value="${content.product.descriptionList}"></c:set>
                  			  <c:forEach var="description" items="${descriptions}">
                  			  <c:if test="${description.productMeta.id == 9 }">
						           ${description.noTagContent}
			                   	</c:if>                  			  
                  			  </c:forEach>
                  			   </div>
                  			   <div class="extra">
                  			      <a href="${content.url}" class="grey"  target="_blank">深度了解>></a>
                  			   </div>
                  			   
                  			   </div>                  			 
                  			 </dt>
                  			
            	</dl>
            	
            	</c:forEach>            
        	</div>