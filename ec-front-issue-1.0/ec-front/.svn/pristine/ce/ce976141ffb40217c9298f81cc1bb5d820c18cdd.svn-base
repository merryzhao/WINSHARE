<%@page pageEncoding="UTF-8" %><%@include file="/page/snippets/tags.jsp"%>
    
   
    <div class="top_ten fr margin10" fragment="${fragment.id}">
     <h4>${fragment.name}</h4>
      <ol>
     <c:forEach var="content" items="${contentList}"  varStatus="status">
 
      
        
        <c:choose>
          <c:when test="${status.count ==1 }">
	          <li class="first"><span class="no1 fl"></span><a target="_blank" title="${content.name}" href="${content.url}"><img class="fl" src="${content.imageUrl}" alt="${content.name}"></a>
	          <p><a  title="张小娴散文选集" target="_blank" href="${content.url}"> <winxuan-string:substr length="14" content="${content.name}"></winxuan-string:substr></a><br>
	            <del>定价：￥${content.listPrice}</del><br>
	                           文轩价：<b class="red fb">￥${content.salePrice}</b></p>
	        </li>
          </c:when>
          <c:otherwise>
            <li><span class="no${status.count}"></span><a title="${content.name}" href="${content.url}"><winxuan-string:substr length="14" content="${content.name}"></winxuan-string:substr></a> </li>
          
          </c:otherwise>
        </c:choose>
     
     </c:forEach>
      
        <li class="txt_right"></li>
      </ol>
        
       
    
    </div>
