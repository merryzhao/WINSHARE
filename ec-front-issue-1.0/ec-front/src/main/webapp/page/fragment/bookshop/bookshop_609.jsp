<%@page pageEncoding="UTF-8" %><%@include file="../../snippets/tags.jsp"%>
<%@include file="/page/snippets/tags.jsp"%>


<c:if test="${fn:length(contentList)>0}">
 <h3 class="pro_title margin10"><a href="javascript:void(0);">${fragment.name}</a></h3>
    <div class="flash_box" fragment="${fragment.id}">
    <p class="txt_right page_info" id="specail_page">第1页 (共4页)</p>
    <a id="special_left_roll" class="left_roll" href="javascript:void(0);"></a> <a id="special_right_roll" class="right_roll" href="javascript:void(0);"></a>
      <div class="recom_goods_box">
        <ul id="special_book_scroll">
        
         <c:forEach var="content" items="${contentList}"  varStatus="status">
          <li>
            <p class="goods_picture"><a target="_blank" href="${content.url}" title="${content.name}"><img alt="${content.name}" src="${content.imageUrl}"></a></p>
            <strong><a class="link1" target="_blank" href="${content.url}" title="${content.name}">${content.name}</a></strong> 
            <p class="txt_left special"><del>定价：￥${content.product.listPrice}</del><br>特价：<b class="red fb">￥${content.effectivePrice}</b>(<fmt:formatNumber value="${content.discount*10}" pattern="#0.0"/>折)</p> </li>
         </c:forEach>
        
        
        </ul>
      </div>
    </div>
   </c:if>