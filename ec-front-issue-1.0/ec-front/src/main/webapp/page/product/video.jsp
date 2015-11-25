<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<c:set var="subdes"><winxuan-string:substr length='30' content='${des}'/></c:set>
<title><c:out value="${product.name}"/>,<c:out value="${product.category.name}"/>,音像-文轩网</title>
<meta name="description" content="文轩网(winxuan.com)提供<c:out value="${product.name}"/>特卖,<c:out value="${product.name}"/>讲述:<c:out value="${subdes}" />,<c:out value="${product.author}"/>,<c:out value="${product.manufacturer}"/>,<c:out value="${product.barcode}"/>"/>
<meta name="keywords" content="<c:out value="${product.name}"/>,<c:out value="${product.author}"/>,<c:out value="${product.manufacturer}"/>,<c:out value="${product.barcode}"/>,音像"/>

<jsp:include page="../snippets/version2/meta.jsp">
	<jsp:param value="details" name="type"/>
</jsp:include>
</head> 
<body>
<jsp:include page="../snippets/version2/header.jsp">
	<jsp:param value="media" name="label"/> 
</jsp:include>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   ${ product.category.allCategoryName} > ${productSale.effectiveName}</span></div> 
      <input type="hidden" value="${productSale.id}" class="productSaleId"/>
    <div class="left_box">
        <c:if test="${!empty alsoViewedList}">
        <div class="left_container">
            <h4 class="fb">浏览过此商品的顾客还看过</h4>
            <dl class="also_viewed">
            <c:set var="productRecommends" value ="${alsoViewedList}"></c:set>
                <c:forEach var="productRecommend" items="${ productRecommends}" varStatus="status">
                   <c:if test="${status.first }">
                  <dt  <c:if test="${!status.first }">style="display: none"</c:if>><img src="${productRecommend.product.imageUrlFor55px }" alt="${productRecommend.effectiveName}"><a  target="_blank" href="${productRecommend.product.url}" class="link4" title="${productRecommend.effectiveName }">${productRecommend.effectiveName }</a><br>
                    价格：<span class="fb red">￥${productRecommend.salePrice}</span></dt>
                   </c:if>
                <dd <c:if test="${status.first }">style="display: none"</c:if>><a target="_blank" class="link4" title="${productRecommend.effectiveName }" href="${productRecommend.product.url}">${productRecommend.effectiveName }</a></dd>
                </c:forEach> 
            </dl>
        </div>
        </c:if>
       <%-- <c:if test="${!empty hotSaleList }">
        <div class="left_container">
            <h4 class="fb">同类热销产品</h4>
            <c:forEach var="hotSale" items="${hotSaleList}">
            <dl class="also_viewed">
                <dt><img alt="${hotSale.effectiveName }" src="${hotSale.product.smallImageUrl }"><a  target="_blank" class="link4" href="${hotSale.product.url }" title="${hotSale.effectiveName }"><winxuan-string:substr length="17" content="${hotSale.effectiveName }"></winxuan-string:substr></a><br>
                  	  价格：<span class="fb red">${hotSale.salePrice}</span></dt>
            </dl>
            </c:forEach>
        </div>
        </c:if> --%>
        <c:import url="/product/hotsalelist?code=${product.category.code}"/>
          <div class="left_container" bind="authorEl">
            <h4 class="fb">
            <c:if test="${!empty product.authors }">
                    <c:forEach var="author" items="${product.authors}" varStatus="status">
                    	 <c:if test="${status.first }">
                    		<c:set var="author2" value="${author}"></c:set>
                    	    <a target="_blank" class="fr link4" href="http://search.winxuan.com/search?author=<winxuan-string:encode content='${author}' type='utf-8'/>">更多</a>
                    	 </c:if>      	 
                    </c:forEach>
                    </c:if>
         	   该作者其他作品</h4>
            <dl class="also_viewed" bind="otherProduct" param="${author2}">    
            </dl>
        </div>
        <div class="left_container yourhistory">
            <h4 class="fb">你的浏览历史</h4>
            <dl class="also_viewed" id="also_viewed"></dl>
        </div>
    </div>
    <div class="right_box">
        <h1 class="goods_title">${productSale.effectiveName}
			 <c:choose>
        	<c:when test="${!empty productSale.promValue}">
        		(${productSale.promValue})
        	</c:when>
        	<c:otherwise>
        		<c:if test="${!empty productSale.subheading}">
                    	(${ productSale.subheading})
                </c:if> 
        	</c:otherwise>
        </c:choose> 
		<c:if test="${productSale.needFreeFee }"> 
       		 <span class="promotion" href="javascript:;">免运费</span>
        </c:if>  
        </h1>
        <div class="goods_info">
        	<c:set var="performance" value="${productSale.performance}"></c:set>
            <div class="share_info">
                <div class="goods_pic">
                <c:if test="${productSale.needFreeFee }"> 
                <%-- 
                	<img class="pro_icon" src="http://www.winxuancdn.com/css2/images/ads/promotions_icon.png" alt="促销名称"/>
                 --%>
                </c:if>
                <img src="${product.imageUrlFor200px }" alt="<c:out value="${productSale.effectiveName}"></c:out>" class="item_img" /></div>
                <c:if test="${!empty product.imageUrlFor600px }">
                	<a target="_blank" class="view_bimg" href="${product.imageUrlFor600px }" target="_blank">查看大图</a>
                </c:if>
                <dl>
                    <dd id="totalSale" class="hide">已售：<b class="red fb"></b> 件</dd>
                    <dd><span class="fl">评分：</span><div class="com_star fl"><b style="width:${avgRank*20}%;"></b></div>
                      <c:if test="${!empty rankCount&&rankCount>0}">
               		  （<a class="link4" href="/product/${productSale.id }/comment/list">已有<b id="totalComment">${rankCount}</b>人评价</a>）
                    </c:if>
                    </dd>
                    <dd>收藏：<b id="totalFavorite"></b>人</dd>
						<dd>
							<div id="ckepop" style="height:25px"> 
							<span class="jiathis_txt">分享到：</span> <a
								class="jiathis_button_qzone"></a> <a
								class="jiathis_button_tsina"></a> <a class="jiathis_button_tqq"></a>
							<a class="jiathis_button_renren"></a> <a
								class="jiathis_button_kaixin001"></a> <a
								href="http://www.jiathis.com/share"
								class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
							<a class="jiathis_counter_style"></a>
							<%-- 			 <a title="推荐到新浪微博" id="sina" href="javascript:void(0)">sina</a><a title="推荐到腾讯微博" id="qzone" href="javascript:void(0)">qzone</a><a title="推荐到人人" id="renren" href="javascript:void(0)">renren</a><a title="推荐到开心网" id="kaixing" href="javascript:void(0)">kaixing</a><a title="推荐到豆瓣" id="douban" href="javascript:void(0)">douban</a> 
                 				 --%> 
                  			 </div> 
						</dd>
				</dl>
            </div>
             <div class="detail_info">
                <ul class="price_info">
                    <li>文&nbsp;轩&nbsp;价：<b class="fb">￥${productSale.effectivePrice}</b>&nbsp;<a class="link4" bind="pricecutNotify" href="javascript:;" data-id="${productSale.id }">降价通知</a></li>
                    <li>定&nbsp;&nbsp;&nbsp;&nbsp;价：￥${product.listPrice }</li>
                    <c:choose>
                    	<c:when test="${productSale.discount>=1||productSale.discount==0}"></c:when>
                    	<c:otherwise>
	                   		<li>折&nbsp;&nbsp;扣：
	                   			 <span class="red fb">
		                   			 <span class="red fb"><fmt:formatNumber value="${productSale.discount*10}" pattern="#0.0"/>折</span> 
		                   			 （为你节省<span class="red fb">￥${product.listPrice-productSale.effectivePrice}</span>）
	                   			 </span>
	                   		</li>
                    	</c:otherwise>
                    </c:choose>
                   	<li>库&nbsp;&nbsp;&nbsp;&nbsp;存：
                    <c:choose>
                    <c:when test="${productSale.supplyType.id != 13102 && productSale.supplyType.id !=13103}">
                    		<c:choose>
                    			<c:when test="${productSale.canSale}">
                    			 	<c:choose>
                    			 		<c:when test="${productSale.avalibleQuantity<=5}">
                    			 			<span class="red fb">仅剩${productSale.avalibleQuantity}件</span>
                    			 		</c:when>
                    			 		<c:otherwise>
                    			 			<span class="red fb">现在有货</span>
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
                					<span class="red fb">预售商品</span>（预计发货时间${productSale.booking.presellDescription}）
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
                </ul>
                <dl class="ohter_info">
                	<dd>歌手/演奏：  
                		<c:choose>                  	  	
                		<c:when test="${!empty product.authors }">           	
                    		<c:forEach var="author" items="${product.authors}" varStatus="status">
                    	 		<a target="_blank" class="link1" href="http://search.winxuan.com/search?author=<winxuan-string:encode content='${author}' type='utf-8'/>" title="${author}">${author}</a><c:if test="${!status.last}">//</c:if>
                  			 </c:forEach>
                    	</c:when>
                    	<c:otherwise>
                    		//
                    	</c:otherwise> 
                    	</c:choose> 
                    </dd> 
                    <dd>发行：//</dd> 
                </dl>
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
                     			  		<td><button class="order_butb" bind="presell" data-id="${productSale.id}" data-region="成都" data-date="${productSale.booking.presellDescription}"></button></td>
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
											<td><button class="addtocart" id="addToCart" bind="addToCart" data-id="${productSale.id}" data-ref="input.goods_num"></button>
											</td>
											<td><a class="coll_but" href="javascript:;" id="addToFavorite" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
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
                <dl class="ohter_info">
                    <dd>发货方：文轩网</dd>
                    <dd>卖家：${productSale.shop.shopName}</dd>               
                </dl>
            </div>
            <div class="hei1"></div>
        </div>
        
        
        <jsp:include page="productInfo.jsp"/>
        
    </div>
    <div class="hei10"></div>
</div>
<script type="text/javascript">
	seajs.use(["jQuery","returnTop"], function($,returnTop){});
</script>

<script type="text/javascript" src="${serverPrefix}/js/product.js?${version}"></script>

<script type="text/javascript" src="${serverPrefix}/js/visit.js?${version}"></script>
<script type="text/javascript" src="http://v2.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
<jsp:include page="../snippets/version2/footer.jsp"></jsp:include>
<script type="text/javascript" >
var _ozurltail="#${product.secondLevelCategoryId}";
var _ozprm="cid99=${product.category.id}&press=${product.manufacturer}&author=${product.author}";
</script>
</body>
</html>
