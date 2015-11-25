<%@page pageEncoding="UTF-8" %>
<%@include file="/page/snippets/tags.jsp"%>


<c:if test="${fn:length(contentList)>0}">


 <h3 class="pro_title"><a href="javascript:void(0);" title="">${fragment.name}</a></h3>
    <div class="flash_box" fragment="${fragment.id}">
    <p class="txt_right page_info" id="hot_page">第1页 (共4页)</p>
    <a id="hot_left_roll" class="left_roll" href="javascript:void(0);"></a> <a id="hot_right_roll" class="right_roll" href="javascript:void(0);"></a>
      <div class="recom_goods_box">
        <ul id="hot_book_scroll">
         <c:forEach var="content" items="${contentList}"  varStatus="status">
         <li>
            <p class="goods_picture"><a href="${content.url }" title="${content.name}"><img alt="${content.name}" src="${content.imageUrl}"></a></p>
            <strong><a  class="link1" target="_blank" href="${content.url}" title="${content.name}">${content.name}</a></strong> 
            <p>
            <c:if test="${content.author!= null}">${content.author} 著作 </c:if>
           </p>
            <span class="goods_price"><b class="red fb">￥${content.salePrice}</b></span>
            
             </li>
         </c:forEach>
          
 
        </ul>
      </div>
    </div>
    
    </c:if>