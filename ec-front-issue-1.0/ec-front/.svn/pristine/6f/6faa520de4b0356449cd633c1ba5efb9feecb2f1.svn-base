<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
    <div class="share_info">
                <div class="goods_pic">
                <c:if test="${false}">
                	<img class="pro_icon" src="${serverPrefix}css2/images/ads/promotions_icon.png" alt="促销名称"/>
                    </a>
                </c:if>
                <c:if test="${productSale.hasPromotion}"><img class="pro_icon" src="${serverPrefix}images/ads/pro_icon_2.png"></c:if>
                <a href="${productSale.product.imageUrlFor600px }" class="jqzoom">
                    <img src="${productSale.product.imageUrlFor200px }" alt="<c:out value="${product.name}"></c:out>" class="item_img" />
                </a>
                </div>
                <c:if test="${!empty product.imageUrlFor600px }">
                	
                	<c:if test="${!empty productSale.EBook.productSeptember}">
                	<a class="view_bimg" href="${product.imageUrlFor600px }" target="_blank">查看大图</a>
                	<a class="view_eimg" href="http://www.9yue.com/drm/read.html?bookId=${productSale.EBook.productSeptember.septemberBookId}" target="_blank">在线试读</a>
                    </c:if>
                    
                    <c:if test="${empty productSale.EBook.productSeptember}">
                    <a class="view_bimg view_bimg_left" href="${product.imageUrlFor600px }" target="_blank">查看大图</a>
                    </c:if>
                    
                </c:if>
                <dl>
                    <dd id="totalSale" class="hide">已售：<b class="red fb"></b> 本</dd>
                    <dd><span class="fl">评分：</span><div class="com_star fl"><b style="width:${aveRank*20}%;"></b></div>
                    
                    <c:if test="${!empty rankCount&&rankCount>0}">
               		     （<a class="link4" href="/product/${productSale.id }/comment/list">已有<b id="totalComment">${rankCount}</b>人评价</a>）
                    </c:if>
					
                    </dd>
                    <dd>收藏：<b id="totalFavorite">0</b>人</dd>
						<dd>
							<div id="ckepop" style="height:25px"> 
								<span class="jiathis_txt">分享到：</span> 
								<a class="jiathis_button_qzone"></a> 
								<a class="jiathis_button_tsina"></a> 
								<a class="jiathis_button_tqq"></a>
								<a class="jiathis_button_renren"></a> 
								<a class="jiathis_button_kaixin001"></a> 
								<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
								<a class="jiathis_counter_style"></a>
                  			 </div> 
						</dd>
				</dl>
            </div>