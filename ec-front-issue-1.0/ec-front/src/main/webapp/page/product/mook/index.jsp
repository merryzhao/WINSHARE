<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${product.name},${product.category.name},杂志-文轩网</title>
<meta name="description" content="文轩网(winxuan.com)提供${product.name}全面信息,包括${product.name}报价、图片、参数、用户评论等信息，网购${product.name}送货上门"/>
<meta name="keywords" content="${product.name},杂志,文轩网,新华文轩,文轩网百货"/>

<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="details" name="type"/>
</jsp:include>
</head> 

<jsp:include page="/page/snippets/version2/header.jsp">
	<jsp:param value="mall" name="label"/> 
</jsp:include>
</head> 
<body class="mookshop">
<div class="layout">
    <div class="your_path cl">你现在的位置： <span> <a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  ${ product.category.allCategoryName} > ${product.name}</span></div> 
      <input type="hidden" value="${productSale.id}" class="productSaleId" />
       <div class="left_box">
          <div class="border_s2 margin10">
      <h2 class="sort_tit txt_center">店铺信息</h2>
      <dl class="seller_info">
    
        <dt><a href="http://www.winxuan.com/shop/${shop.id}" title="${shop.name}"><img src="${shop.logoUrl}" alt="${shop.name }"></a><br>
          <a href="http://www.winxuan.com/shop/${shop.id}">${shop.name}</a></dt>
        <dd>开店时间：<fmt:formatDate value="${shop.createDate }" type="date" /> </dd>
        <dd>所  在   地：<span>${shop.address}</span></dd>
        <dd>服务保障：<b class="support"></b></dd>
        <dd>服务评价：<span class="com_star_s"><b style="width:${shopRankAvgrank*20}%;"></b></span>(共<a class="red haveline" href="javascript:;">${shopRankCount}</a>人)</dd>

      </dl>
    </div>
 <div class="border_s2 margin10">
      <h2 class="sort_tit txt_center">客服信息</h2>
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
     <c:if test="${!empty alsoViewedList}"> 
        	<div class="left_container border_s2">
            	<h2 class="sort_tit txt_center">其他顾客看过</h2>
            	<dl class="also_viewed" >
               		<c:set var="productRecommends" value ="${alsoViewedList}"></c:set>
                	<c:forEach var="productRecommend" items="${ productRecommends}" varStatus="status">
                  		<c:if test="${status.first }">
                  			<dt  <c:if test="${!status.first }">style="display: none"</c:if>><a href="${productRecommend.product.url}" target="_blank"><img src="${productRecommend.product.imageUrlFor55px }" alt="${productRecommend.product.name }"></a><a href="${productRecommend.product.url}" class="link4" title="${productRecommend.product.name }"  target="_blank">
                  			<winxuan-string:substr length="20" content="${productRecommend.product.name }"></winxuan-string:substr>
                  			</a><br>
                  			  	文轩价：<span class="fb red">￥${productRecommend.salePrice}</span></dt>
                   		</c:if>
                		<dd <c:if test="${status.first }">style="display: none"</c:if>><a class="link4" title="${productRecommend.product.name }" href="${productRecommend.product.url}" target="_blank">${productRecommend.product.name }</a></dd>
                	</c:forEach>            
            	</dl>
        	</div>
       </c:if>
    
      <h2 class="sort_tit margin10 txt_center">卖家店内同类商品</h2>
      <ul class="viewed_goods border_s2">      
      <c:if test="${fn:length(similarProductSale) <= 0}"><div class="nocontent">卖家店内没有同类商品</div></c:if>
     
         <c:forEach var="productSale" items="${similarProductSale}" varStatus="status">
           <c:if test="${status.count<6}">
            <li><a href="${productSale.id}" title="${productSale.sellName}${productSale.subheading}"><img class="fl" src="${productSale.product.imageUrlFor55px}" alt=""></a><h3>${status.count}.<a href="${productSale.id}" title="${productSale.sellName}"> ${productSale.sellName}<b class="orange">${productSale.subheading}</b></a></h3><b class="red fb">￥${productSale.salePrice}</b></li>
           </c:if>
         </c:forEach>
      
      </ul>
    
    
    
     
       
         <%-- <h2 class="sort_tit txt_center margin10">最近浏览商品</h2>
          <ol class="viewed_goods">
          </ol> --%>
        <div class="left_container yourhistory border_s2">
            <h2 class="sort_tit txt_center">您的浏览历史</h2>
            <dl class="also_viewed" id="also_viewed"></dl>
        </div>
        <div class="border_s2 margin10">
      <h2 class="sort_tit txt_center">店内搜索</h2>
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
       
  </div>
    <div class="right_box">
        <h1 class="goods_title">${productSale.sellName} 
                <c:if test="${!empty productSale.subheading}">
                    	(${ productSale.subheading})
                </c:if>
                <c:if test="${!empty productSale.promValue}">
        		   <font style="color:#ff0000;"> ${productSale.promValue}</font>    
        	    </c:if>               
                <c:if test="${!empty promotions}">
        		   <font style="color:#ff0000;">
					<c:forEach items="${promotions}" var="promotion" varStatus="status">
						<c:if test="${!empty promotion.description}">${promotion.description}&nbsp;</c:if>
					</c:forEach>
				</font>    
        	    </c:if>               
         </h1>
        <div class="goods_info">
            <div class="share_info">
                <div class="goods_pic">
                <img src="${product.imageUrlFor200px}" alt="<c:out value="${productSale.sellName}"></c:out>" class="item_img" /></div>
                <a class="view_bimg" href="${product.imageUrlFor600px }" target="_blank">查看大图</a>
            </div>
            <div class="detail_info">
                <ul class="price_info">
                    <li>文轩价：<b class="fb">￥${productSale.effectivePrice}</b>&nbsp;<a class="link4" bind="pricecutNotify" href="javascript:;" data-id="${productSale.id }">降价通知</a></li>
                    <li>定&nbsp;&nbsp;价：￥${product.listPrice }</li>
                    <li>折&nbsp;&nbsp;扣：<span class="red fb">${fn:substringBefore(productSale.discount*100,".")}折</span> (为你节省<span class="red fb">￥${product.listPrice-productSale.effectivePrice}</span>)</li>
                    <c:if test="${productSale.supplyType.id != 13102}"> 
                  	<li>库&nbsp;&nbsp;存：
                   <c:choose>
                    <c:when test="${productSale.supplyType.id != 13102 && productSale.supplyType.id !=13103}">
                   		<c:choose>
                   			<c:when test="${productSale.canSale}">
                   			 	<c:choose>
                   			 		<c:when test="${productSale.avalibleQuantity<=5}">
                   			 			<span class="red fb">仅剩${productSale.avalibleQuantity}件</span>（预计1-2天由${productSale.shop.shopName}从<c:if test="${!empty productSale.shop.storage}">${productSale.shop.storage}</c:if><c:if test="${empty productSale.shop.storage}">成都</c:if>发货）
                   			 		</c:when>
                   			 		<c:otherwise>
                   			 			<span class="red fb">现在有货</span>（预计1-2天由${productSale.shop.shopName}从<c:if test="${!empty productSale.shop.storage}">${productSale.shop.storage}</c:if><c:if test="${empty productSale.shop.storage}">成都</c:if>发货）
                   			 		</c:otherwise>
                   			 	</c:choose>	            				
                   			</c:when>
                   			<c:otherwise>
                   				<span class="red fb">现在没货</span>（建议点击下面的“到货通知”）
                   			</c:otherwise>
                   		</c:choose>
                	</c:when>
                	<c:otherwise>
                		<c:if test="${productSale.supplyType.id == 13102}">
               				<c:choose>
               					<c:when test="${productSale.preSaleCanBuy }">
               						<span class="red fb">预售商品</span>（预计发货时间2011年12月27号）
               					</c:when>
               					<c:otherwise>
               						<span class="red fb">现在没货</span>（建议点击下面的“到货通知”）
               					</c:otherwise>
               				</c:choose>
                		</c:if>
                		<c:if test="${productSale.supplyType.id == 13103}">
               				<span class="red fb">订购商品</span>
                		</c:if>
                	</c:otherwise>
                	</c:choose>	
          			</li>
                 </c:if>  
                  <li>卖&nbsp;&nbsp;家：${productSale.shop.shopName}</li>
                  <li>运&nbsp;&nbsp;费：${productSale.shop.deliveryFee}</li>
                  <c:if test="${!empty productSale.shop.buyArea}">
                  	<li>购买区域：${productSale.shop.buyArea}</li>
                  </c:if>
                 <%-- 
                 <c:if test="${!empty productSale.promValue}">
                    	<li>促销语：${productSale.promValue}</li>
                  </c:if>
                  --%>  
                </ul>
                 <table class="addbuttons">
                 <c:choose>
                 <c:when test="${productSale.supplyType.id == 13102}">              
                      	               		
                      			<%-- <c:if test="${productSale.preSaleHasShow || productSale.preSaleCanBuy}">
                      					新品预售
                    				<c:if test="${!empty productSale.booking}">
                    					(该商品预售时间：<fmt:formatDate value="${productSale.booking.startDate }" pattern="yyyy-MM-dd"/> 到  <fmt:formatDate value="${productSale.booking.endDate }" pattern="yyyy-MM-dd"/>)<br/>
                    					<c:if test="${!empty productSale.booking.description }">
                    						<c:out value="${productSale.booking.description}"></c:out>
                    					</c:if>
                    				</c:if>
                      			</c:if> --%>
                      			<c:choose>
                      			<c:when test="${productSale.preSaleCanBuy}">
                      				<tr>
                       					 <td colspan="2"><b class="fb">我要买：</b>
                           			 		<input class="goods_num" type="text" size="3" value="1"/>件
                           			 	</td>
                  					 </tr>
                   					 <tr>
                     			  		<td><button class="order_butb" bind="presell" data-id="${productSale.id}" data-region="成都" data-date=" <fmt:formatDate value="${productSale.booking.endDate }" pattern="yyyy年MM月dd日"/>"></button></td>
                       			 		<td><a class="coll_but" href="javascript:;" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a></td>
                   					 </tr>
                      			</c:when>
                      			<c:otherwise>
                      				<tr>
                        				<td><button class="notice_butb" bind="arrivalNotify" data-id="${productSale.id}"></button></td>
                       					<td><a class="coll_but" href="javascript:;" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a></td>
                    				</tr>
                      			</c:otherwise>
                      			</c:choose>       		      		
                  		</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${productSale.canSale}">
										<tr>
											<td colspan="2"><b class="fb">我要买：</b> <input class="goods_num" type="text" size="3" value="1"/> 件
											</td>
										</tr>
										<tr>
											<td><button class="addtocart" bind="addToCart" data-id="${productSale.id}" data-ref="input.goods_num"></button>
											</td>
											<td><a class="coll_but" href="javascript:;" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
											</td>
										</tr> 
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="2"><b class="fb">我要买：</b> <input class="goods_num" type="text" size="3" value="1" disabled="disabled"/> 件
											</td>
										</tr>
										<tr>
											<td><button class="notice_butb" bind="arrivalNotify" data-id="${productSale.id}"></button>
											</td>
											<td><a class="coll_but" href="javascript:;" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
                </table>
            </div>
            <div class="hei1"></div>
        </div>
        <div class="prom_detail seller_tips">
            <dl>
             <dt>卖家温馨提示</dt>
              <c:forEach var="activityShow" items="${activityShows}" varStatus="status">
                 <dd>    ${status.count}、${activityShow.title}</dd>  
              </c:forEach>
             
             </dl>
        </div>
        <%-- 
        <div class="combination margin10">
         <h2>组合购买</h2>
         <div class="this_goods fl"><img alt="商品名字" src="/goods/pro123.jpg"></div>
         <div class="com_goods fl">
         <dl>
           <dt>+</dt>
           <dd><a href="#" title=""><img alt="" src="/goods/mer1.png"> <p>奥林巴斯（OLYMPUS）SZ-10 数码相机 黑色<b class="orange">1400万像素 3.0英寸液晶屏 18倍光学变焦</b></p></a><input name="" type="checkbox" value=""> <b class="fb red">￥1500.00</b></dd>
           <dd><a href="#" title=""><img alt="" src="/goods/mer1.png"> <p>奥林巴斯（OLYMPUS）SZ-10 数码相机 黑色<b class="orange">1400万像素 3.0英寸液晶屏 18倍光学变焦</b></p></a><input name="" type="checkbox" value=""> <b class="fb red">￥1500.00</b></dd>
           <dd><a href="#" title=""><img alt="" src="/goods/mer1.png"> <p>奥林巴斯（OLYMPUS）SZ-10 数码相机 黑色<b class="orange">1400万像素 3.0英寸液晶屏 18倍光学变焦</b></p></a><input name="" type="checkbox" value=""> <b class="fb red">￥1500.00</b></dd>
           <dd><a href="#" title=""><img alt="" src="/goods/mer1.png"> <p>奥林巴斯（OLYMPUS）SZ-10 数码相机 黑色<b class="orange">1400万像素 3.0英寸液晶屏 18倍光学变焦</b></p></a><input name="" type="checkbox" value=""> <b class="fb red">￥1500.00</b></dd>
         </dl>
         </div>
         <div class="com_price fr">您选择了<b class="red">4</b>件商品：<br>搭配价：<b class="fb red">￥5032.00</b><p class="txt_center"><input class="shop_now" name="" type="button" value="立即购买"></p></div>
         <div class="hei1"></div>
        </div>
         --%>
        
        
        
        
        
        
        <jsp:include page="../productInfo.jsp"/>
    </div>
    <div class="hei10"></div>
</div>

<script type="text/javascript">
seajs.use(["jQuery","toolkit","returnTop"],function($,ToolKit,returnTop){
	new ToolKit({context:$("div.detail_info")});	
});
</script>
<script type="text/javascript" src="${serverPrefix}/js/book/common.js?${version}"></script>
<%-- <script type="text/javascript" src="http://www.winxuan.com/js/ajaxpage/mallajax.js"></script>
<script type="text/javascript" src="http://www.winxuan.com/js/ajaxpage/mallvisit.js"></script> --%>
<script type="text/javascript" src="${serverPrefix}js/product.js?${version}"></script>
<script type="text/javascript" src="${serverPrefix}js/visit.js?${version}"></script>
<jsp:include page="/page/snippets/version2/footer.jsp"></jsp:include>
</body>
</html>