<%@page pageEncoding="utf-8"%>
<%@include file="../snippets/tags.jsp"%>


 <div class="border_s2 margin10">
      <h2 class="sort_tit">店铺信息</h2>
      <dl class="seller_info">
    
        <dt><a href="http://www.winxuan.com/shop/${shop.id}" title="${shop.name}"><img src="${shop.logoUrl}" alt="${shop.name }"></a><br>
          <a href="http://www.winxuan.com/shop/${shop.id}">${shop.name}</a></dt>
        <dd>开店时间：<fmt:formatDate value="${shop.createDate }" type="date" /> </dd>
        <dd>所 在 地：${shop.address}</dd>
        <dd>服务保障：<b class="support"></b></dd>
        <dd>服务评价：<span class="com_star_s"><b style="width:${shopRankAvgrank*20}%;"></b></span>(共<a class="red haveline" href="javascript:;">${shopRankCount}</a>人)</dd>

      </dl>
    </div>
 <div class="border_s2 margin10">
      <h2 class="sort_tit">客服信息</h2>
      <dl class="seller_info">
     	<c:forEach items="${serviceTimes}" var="serviceTime" varStatus="status">
      		<dd>时间：${serviceTime}</dd>
       </c:forEach>
     	<c:forEach items="${phones}" var="phone" varStatus="status">
      		<dd>客服电话：${phone}</dd>
       </c:forEach>           
     	<c:forEach items="${serviceQqNos}" var="serviceQqNo" varStatus="status">
      		<dd>客  服     QQ：<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${serviceQqNo}&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:${serviceQqNo}:41" alt="在线客服" title="在线客服"></a></dd>
       </c:forEach>
      </dl>
    </div>
    <div class="border_s2 margin10">
      <h2 class="sort_tit">店内搜索</h2>
     <form class="serach_from" action="http://search.winxuan.com/search" method="GET" >
     <input class="key_word" name="shopid" type="hidden" value="${shop.id}"/>
        <div class="shop_search margin10">关键字:<input class="key_words" name="keyword" type="text"></div>
        <div class="shop_search">价&nbsp;&nbsp;格:<input class="rang" name="minprice" type="text">&nbsp;到&nbsp;<input class="rang" name="maxprice" type="text">
        </div>
        <p class="shop_search">
          <input class="s_but" name="" type="submit" value="搜索">
        </p>
     </form>
    </div>
    <div class="border_s2 margin10">
      <h2 class="sort_tit">店内商品</h2>
      <ul class="shop_goods">
       <li><a href="http://www.winxuan.com/shop/${shop.id}/category/0">所有商品<%-- (<c:out value="${productListSize}"></c:out>) --%></a></li>
       <c:forEach var="shopCategory" items="${shopCategorys}" varStatus="status">    
              <li><a href="http://www.winxuan.com/shop/${shop.id}/category/${shopCategory.id}" title="${shopCategory.name}">${shopCategory.name }</a></li>
       </c:forEach>
      </ul>
    </div>
