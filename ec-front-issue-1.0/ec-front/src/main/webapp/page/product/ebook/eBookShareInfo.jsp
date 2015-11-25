<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
    <div class="share_info">
                <div class="goods_pic goods_dci">
                <c:if test="${false}"> 
                	<img class="pro_icon" src="${serverPrefix}css2/images/ads/promotions_icon.png" alt="促销名称"/>
                	
                </c:if>
                <img class="ebook_ico" src="${serverPrefix}css2/images/ads/tag_ebook.png" alt="电子书标识"/>
                
                <img src="${productSale.product.imageUrlFor200px }" alt="<c:out value="${product.name}"></c:out>" class="item_img" />
                 <c:if test="${not empty  dciData}">
					<a class="dci_data" data-bookid="${dciData.bookId}" href="http://www.winxuan.com/product/dciLog?bookId=${dciData.bookId}&src=1"><img src="http://img0.winxuancdn.com/24.png">
					   <div class="dci-info" style="display:none;">
					       <div class="arrow"></div>
					       <h4>DCI信息</h4>
					       <ul>
					           <li>许可方：${dciData.partA}</li>
					           <li>被许可方：${dciData.partB}</li>
					           <li>类别：${dciData.workType}</li>
					           <li>名称：${productSale.effectiveName}</li>
					           <li>DCI码：${dciData.dci}</li>
					       </ul>
					   </div>
					</a>
				</c:if>
                </div>
                <c:if test="${!empty product.imageUrlFor600px }">
                	<a class="view_bimg" href="${product.imageUrlFor600px }" target="_blank">查看大图</a>
                	<c:if test="${productSale.vendor != '87' }">
                	<a class="view_eimg" href="http://www.9yue.com/drm/read.html?bookId=${productSale.productSeptember.septemberBookId}" target="_blank">在线试读</a>
                	</c:if>
                	<c:if test="${productSale.vendor == '87' }">
                	<a class="view_eimg" href="http://www.9yue.com/reader/tryreadfunder.html?id=${productSale.productSeptember.septemberBookId}#tryread" target="_blank">在线试读</a>
                	</c:if>
                </c:if>
                <dl class="dl_top_30">
                    <dd><span class="fl">评分：</span><div class="com_star fl"><b style="width:${avgRank*20}%;"></b></div>
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