<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set var="des" value="" />
<c:forEach var="description" items="${product.descriptionList }"
    varStatus="status">
    <c:if test="${status.index == 0}">
        <c:set var="des" value="${description.content}"/>
    </c:if>
</c:forEach>

<c:set var="subdes">
    <winxuan-string:substr length='30' content='${des}' />
</c:set>

<!-- 文轩网，九月网搜索词 -->
<title><c:out value="${product.name}" />-<c:out
        value="${product.author}"></c:out>-<c:out
        value="${product.category.parent.name}" />-文轩网</title>
<meta name="description" content="<c:set var="description1"><winxuan-string:substr length='120' content='${descWithOutHtmlTag}' /></c:set>${description1}" />
<meta name="keywords"
    content="<c:out value="${product.name}"/>,<c:out value="${product.author}"/>,<c:out value="${product.manufacturer}"/>,<c:out value="${product.barcode}"/>,图书" />
<c:choose>
    <c:when test="${productSale.storageType.id==6004}">
        <jsp:include page="/page/snippets/v2/meta.jsp" flush="true" >
            <jsp:param name="type" value="details" />
            <jsp:param value="true" name="ebook" />
        </jsp:include>
    </c:when>
    <c:otherwise>
        <jsp:include page="/page/snippets/v2/meta.jsp" flush="true" >
            <jsp:param name="type" value="details" />
            <jsp:param value="false" name="ebook" />
        </jsp:include>
    </c:otherwise>
</c:choose>
</head>
<body class="bookproduct">
<div class="wrap">
   <c:if test="${productSale.storageType.id==6004}">
    <jsp:include page="../snippets/v2/header.jsp">
    <jsp:param value="ebook" name="label" />
    </jsp:include>
    </c:if>
   
    <c:if test="${productSale.storageType.id!=6004}">
    <jsp:include page="../snippets/v2/header.jsp">
    <jsp:param value="book" name="label" />
    </jsp:include>
    </c:if>
    <%-- nav --%>
   	<jsp:include page="/page/snippets/v2/navigation.jsp">
	 	<jsp:param value="false" name="index" />
	</jsp:include>
    <!-- main -->
    <div class="main cf">
        <div class="bread cf">
           <!--   <h1><a href="#">图书</a></h1>-->
            <div class="base-nav">${ product.category.allCategoryName}</div>
        </div>
     <input type="hidden" value="${productSale.id}" class="productSaleId" />
        <!-- side-detail -->
        <aside class="side-detail">
            <div class="line">
            <div class="unit">

                    <!-- col-base-1 -->
                    <c:if test="${!empty alsoBoughtList}">
                    <div class="col col-base-1">
                        <div class="title">
                            <h2>购买了此商品的人还买了</h2>
                        </div>
                        <div class="cont">

                            <!-- list-base-1 -->
                            <ul class="list list-base-1">
                            <c:forEach var="productBought" items="${ alsoBoughtList}"
							varStatus="status">
                                <!-- list-base-1 li (loop) -->
                                <li>
                                    <div class="cell cell-style-1">
                                        <div class="img"><a href="${productBought.url}" target="_blank"><img src="${productBought.product.imageUrlFor55px }" alt="${productBought.effectiveName }"></a></div>
                                        <div class="name"><a href="${productBought.url}" target="_blank">${productBought.effectiveName }</a></div>
                                        <div class="price-n"><b>￥${productBought.salePrice}</b><s>￥${productBought.listPrice}</s></div>
                                    </div>
                                </li>
                               </c:forEach>
                            </ul>
                            <!-- list-base-1 end -->

                        </div>
                    </div>
                    <!-- col-base-1 end -->
                  </c:if>
                </div>
                   <div class="unit">

                  <!-- col-base-1 -->
					<cache:fragmentPageCache idleSeconds="86400"
						key="PAGE_PRODUCT_CATEGORY_WEEKANDNEWS_${product.category.code}">
						<c:import url="/product/weekhotsalelist?code=${product.category.code}">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
					</cache:fragmentPageCache>
                  <!-- col-base-1 end -->

                </div>
                <div class="unit">
                     <cache:fragmentPageCache idleSeconds="86400"
								key="FRAGMENT_3017">
                    <c:import url="/fragment/3017">
                    <c:param value="${cacheKey}" name="cacheKey" />
                    </c:import>
                      </cache:fragmentPageCache>
                </div>
               
                <%--     <cache:fragmentPageCache idleSeconds="86400"
								key="FRAGMENT_limitbuy">
                    <c:import url="/product/limitbuy">
                    <c:param value="${cacheKey}" name="cacheKey" />
                    </c:import>
                      </cache:fragmentPageCache>
             
                 --%>
               <c:if test="${!empty product.authors }">
                <div class="unit" bind="otherProductWithAuthors">
                     <div class="col col-base-1">
                        <div class="title">
                            <h2>该作者的其他作品</h2>
                        </div>
                        <div class="cont" bind="authorEl">
                               <ul class="list list-text-2" bind="otherProduct"
					           param="<winxuan-string:encode content='${product.author}' type='utf-8'/>">
                               </ul>
						<c:forEach var="author" items="${product.authors}"
							varStatus="status">
							<c:if test="${status.first }">
									 <div class="attach"><a class="link" href="http://search.winxuan.com/search?author=<winxuan-string:encode content='${author}' type='utf-8'/>" target="_blank">更多 &gt;&gt;</a></div>
							</c:if>
						</c:forEach>
                        </div>
                    </div> 
                    <!-- col-base-1 end -->
                 
                

                </div>
                 </c:if>
                <div class="unit">

                    <!-- col-base-1 -->
                   <div class="col col-base-1">
                        <div class="title">
                            <h2>您的最近浏览记录</h2>
                        </div>
                        <div class="cont">

                            <!-- list-text-1 -->
                            <ul class="list list-text-2">
                              <dl class="also_viewed" id="also_viewed"></dl>
                            </ul>
                            <!-- list-text-1 end -->
 
                        </div>
                    </div>
                    <!-- col-base-1 end -->

                </div>

            </div>
        </aside>
        <!-- side-detail end -->

        <!-- mian-detail -->
        <div class="line mian-detail">
            <div class="unit">

                <!-- cell-detail -->
                <div class="cell cell-detail">

                   <jsp:include page="product_info.jsp"/>

                    <!-- info-side -->
                    <div class="info-side">
                        <div class="img">
                        <a href="${product.imageUrlFor600px }" class="jqzoom" target="_blank">
						<img src="${product.imageUrlFor350px}" alt="<c:out value="${product.name}"></c:out>">
                        </a>
                       	<c:if test="${not empty tagurl}">
                           	<div class="tag"><img src="${tagurl}"></img></div>
                        </c:if>
                        <div class="zoom"></div>
                        </div>
                        <div class="share">
                            <div id="ckepop" class="share_jiathis"> 
                                <span class="jiathis_txt">分享到：</span> 
                                <a class="jiathis_button_qzone"></a> 
                                <a class="jiathis_button_tsina"></a> 
                                <a class="jiathis_button_tqq"></a>
                                <%--<a class="jiathis_button_renren"></a> 
                                <a class="jiathis_button_kaixin001"></a>--%>
                                <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                                <a class="jiathis_counter_style"></a>
                            </div>
                            <a class="collect-btn" href="javascript:void(0);" id="addToFavorite" bind="addToFavorite" data-id="${productSale.id}"></a>
                            <%--<a class="zoom-btn" target="_blank" href="${product.imageUrlFor600px }"><b></b>查看大图</a>--%>
                        </div>
                        <%--<div class="collect"><a class="collect-btn" href="javascript:void(0);" id="addToFavorite" bind="addToFavorite" data-id="${productSale.id}">加入收藏</a><span>收藏人气：<b id="totalFavorite">0</b></span></div>--%>
                    </div>
                    <!-- info-side end -->

                </div>
                <!-- cell-detail end -->

            </div>
         <c:if test="${productSale.saleStatus.id==13002}">
            <div class="unit comb-buy" style="display:none;">

                <!-- col-base-2-tab -->
                <div class="col col-base-2">
                    <div class="title">
                        <div class="tab">
                            <div class="tab-item current">最佳搭配</div>
                        </div>
                    </div>
                    <div class="cont">

                        <!-- assemble-main -->
                        <div class="assemble-main cf">
                            <div class="assemble-master fl">

                                <!-- cell-style-3 -->
                                <div class="cell cell-style-3">
                                    <div class="img"><a href="${productSale.url }" target="_blank"><img src="${productSale.product.imageUrlFor130px}" alt="${productSale.sellName}"></a></div>
                                    <div class="name master-book" data-product-id="${productSale.id}" data-list-price="${productSale.product.listPrice}" data-sale-price="${productSale.salePrice}"><a href="${productSale.url} " target="_blank">${productSale.sellName} </a></div>
                                    <s class="operation"></s>
                                </div>
                                <!-- cell-style-3 end -->

                            </div>
                            <div class="assemble-slave fl">
                                <div class="assemble-slave-overflow">
                                </div>
                            </div>
                            <div class="assemble-total fr">
                                <div class="assemble-buy">
                                    <h5>购买最佳搭配</h5>
                                    <div class="pirce-o">定　价：<b  id="list-price">￥${productSale.product.listPrice}</b></div>
                                    <div class="pirce-n">文轩价：<b  id="sale-price">￥${productSale.salePrice}</b></div>
                                    <div class="buy-active"><a href="javascript:void(0);" class="buy-btn-3  comb_buy" title="购买此套餐"></a></div>
                                </div>
                                <s class="operation"></s>
                            </div>
                        </div>
                        <!-- assemble-main end -->

                    </div>
                </div>
                <!-- col-base-2-tab end -->

            </div>
            </c:if>
            <div class="unit">
              <c:set var="metaimg" value="false"></c:set>
	         <c:forEach items="${product.imageList}" var="img">
		<c:if test="${img.type == 5}">
			<c:set var="metaimg" value="true" />
		</c:if>
	    </c:forEach>
	    
                <!-- col-base-2-tab -->
                <div class="col col-base-2">
                    <a name="p-intro"></a>
                    <div class="title atf-head"><!-- 滑动到此时添加fixed -->
                        <div class="tab">
                            <div class="tab-item current"><a href="#p-intro">商品介绍</a><s></s></div>
                            <div class="tab-item tab-item-2"><a href="#p-comm">商品评论</a><a class="link" href="/product/${productSale.id}/comment/list" target="_blank">(${commentCount+tmallCommentCount}条)</a><s></s></div>
                            <div class="tab-item tab-item-2"><a href="#p-const">商品咨询</a><a class="link" href="/product/${productSale.id}/question/list" target="_blank">(${questionCount}条)</a><s></s></div>
                        </div>
                        <div class="lift cf">
                            <div class="lift-purchase">
                                <span class="lift-title">快速直达</span>
                                <c:set var="descriptions" value="${product.descriptionList }"></c:set>
							<c:forEach var="description" items="${descriptions}" varStatus="status">
								<c:if test="${!empty description.content && description.content!='null' && description.name!='摘要'}">
                                <a href="#status${status.index}">${description.name}</a>
                                </c:if>
                                </c:forEach>
                                   <c:if test="${metaimg}">
                                   <a href="#recomm">书摘插图</a>
                                   </c:if>
                            </div>
                            <div class="lift-price"><span class="price-n"><b>￥${productSale.effectivePrice}</b>
                            	<c:choose>
                            		<c:when test="${productSale.discount>=1||productSale.discount==0}"></c:when>
                            		<c:otherwise>
                            			<span style="font-size: 13px;color: #e4393d;font-family: Microsoft Yahei;">（<fmt:formatNumber value="${productSale.discount*10}" pattern="#0.0"/>折）</span>
                            		</c:otherwise>
                            	</c:choose>
                            </span>
                              <c:choose>
                                 <c:when test="${productSale.supplyType.id == 13102}">  
                                  <c:choose>
                      			<c:when test="${productSale.preSaleCanBuy}">
                                <a class="buy-btn-4 active-pre"  href="http://www.winxuan.com/shoppingcart/modify?p=${productSale.id}" id="addtocart" data-id="${productSale.id}" data-ref="input.buy-num-text" target="_blank" title="加入购物车"></a>
                                </c:when>
                                
                                </c:choose>
                                 </c:when>
                              <c:otherwise>
								<c:choose>
									<c:when test="${productSale.canSale}">
									 <a class="buy-btn-4 active-normal"  href="http://www.winxuan.com/shoppingcart/modify?p=${productSale.id}" id="addtocart" data-id="${productSale.id}" data-ref="input.buy-num-text" target="_blank" title="加入购物车"></a>
									</c:when>
									
                                </c:choose>
                                </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="cont">
                        <!-- list-text-3 -->
                        <ul class="list-text-3 cf">
                         <c:set value="0" var = "i"/>
                   		    <li>作　者：<c:if test="${!empty product.authorUrl}">${product.authorUrl}</c:if></li>
                            <li>出版社：<a target="_bland" class="link" href="http://search.winxuan.com/search?manufacturer=<winxuan-string:encode content='${product.manufacturer }' type='utf-8'/>">${product.manufacturer}</a></li>
                            <li>出版时间：<fmt:formatDate value="${product.productionDate }" pattern="yyyy-MM-dd"/></li>
                            <c:forEach var="extend" items="${product.extendList}" varStatus="status">
                            <c:if test="${extend.productMeta.id ==1 }"><c:set var="meta1" value="${extend.value }"></c:set></c:if>
                            <c:if test="${extend.productMeta.id ==2 }"><c:set var="meta2" value="${extend.value }"></c:set></c:if>
                            <c:if test="${extend.productMeta.id ==8 }"><c:set var="meta8" value="${extend.value }"></c:set></c:if>
                            <c:if test="${extend.productMeta.id ==3 }"><c:set var="meta3" value="${extend.value }"></c:set></c:if>
                            <c:if test="${extend.productMeta.id ==4 }"><c:set var="meta4" value="${extend.value }"></c:set></c:if>
                            <c:if test="${extend.productMeta.id ==16 }"><c:set var="meta16" value="${extend.value }"></c:set></c:if>
                            <c:if test="${extend.productMeta.id ==5 }"><c:set var="meta5" value="${extend.value }"></c:set></c:if>
                            <c:if test="${extend.productMeta.id ==6 }"><c:set var="meta6" value="${extend.value }"></c:set></c:if>
                            </c:forEach>
                    	    <li>开　本：${empty meta1?'无':meta1}</li>
                    	    <li>页　数：${empty meta2?'无':meta2}</li>
                    	    <li>印刷时间：${empty meta8?'无':meta8}</li>
                    	    <li>字　数：${empty meta3?'无':meta3}</li>
                    	    <li>装　帧：${empty meta4?'无':meta4}</li>
                    	    <li>语　　种：${empty meta16?'无':meta16}</li>
                    	    <li>版　次：${empty meta5?'无':meta5}</li>
                    	    <li>印　次：${empty meta6?'无':meta6}</li>
                    	    <li>I S B N：${empty product.barcode?'无':product.barcode}</li>
                        </ul>
                        <!-- list-text-3 end -->

                    </div>
                </div>
                <!-- col-base-2-tab end -->

            </div>
				<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_3010">
					<div class="unit">

						<!-- slave-banner -->
						<c:import url="/fragment/3010">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
						<!-- slave-banner end -->

					</div>
				</cache:fragmentPageCache>
				
				<div class="unit">
					<div class="col col-more-1-tab sort-tabs">
						<!-- col-more-1-tab -->
						<div class="title">
							<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_3018">
								<c:import url="/fragment/3018">
									<c:param value="${cacheKey}" name="cacheKey" />
								</c:import>
							</cache:fragmentPageCache>
						</div>
						<div class="cont">
							<jsp:include page="fragment/product_3018.jsp" />
						</div>
					</div>
					<!-- col-more-1-tab end -->
				</div>
				
				<div class="unit">
					<c:set var="descriptions" value="${product.descriptionList }"></c:set>
					<c:forEach var="description" items="${descriptions}"
						varStatus="status">
						<c:if
							test="${!empty description.content && description.content!='null' && description.name!='摘要'}">
							<!-- col-base-2-tab -->
							<div class="col col-base-2 col-goods-infotext">
								<a name="status${status.index}"></a>
								<div class="title">
									<div class="tab">
										<h4 class="tab-item current">${description.name}</h4>
									</div>
								</div>
								<div class="cont morereader">

									<!-- text-words-1 -->
									<div class="text-words-1">
										<c:out value="${description.content} " escapeXml="false"></c:out>
									</div>

									<!-- text-words end -->
									<a class="show-more" style="display: none;">显示全部&gt;&gt;</a> <a
										class="hide-more" style="display: none;">隐藏全部&gt;&gt;</a>
								</div>
							</div>
							<!-- col-base-2-tab end -->
						</c:if>
					</c:forEach>
				</div>


				<c:set var="metaimg" value="false"></c:set>
	         <c:forEach items="${product.imageList}" var="img">
		<c:if test="${img.type == 5}">
			<c:set var="metaimg" value="true" />
		</c:if>
	    </c:forEach>
	       <c:if test="${metaimg}">
	       <div class="unit">
                <!-- col-base-2-tab -->
                <div class="col col-base-2 col-goods-infotext">
                    <div class="title">
                        <div class="tab">
                            <h4 class="tab-item current">书摘插图</h4>
                        </div>
                    </div>
                    <div class="cont  morereader">
                     <a name="recomm"></a>
                        <!-- text-words-1 -->
                        <div class="text-words-1">
                        <c:forEach items="${product.imageList}" var="img">
						<c:if test="${img.type == 5}">
							<img src="${img.url}">
							<br>
						</c:if>
					    </c:forEach>
                        </div>
                        <a class="show-more" style="display:none;">显示全部&gt;&gt;</a>
                        <a class="hide-more" style="display:none;">隐藏全部&gt;&gt;</a>
                        <!-- text-words end -->

                    </div>
                </div>
                <!-- col-base-2-tab end -->

            </div>
            </c:if>
            <div class="unit">

                <!-- col-base-2-tab -->
                <div class="col col-base-2">
                    <a name="p-comm"></a>
                    <div class="title">
                        <div class="tab">
                            <div class="tab-item"><a href="#p-intro">商品介绍</a><s></s></div>
                            <div class="tab-item tab-item-2 current"><a href="#p-comm">商品评论</a><a class="link" href="/product/${productSale.id}/comment/list" target="_blank">(${commentCount+tmallCommentCount}条)</a><s></s></div>
                            <div class="tab-item tab-item-2"><a href="#p-const">商品咨询</a><a class="link" href="/product/${productSale.id}/question/list" target="_blank">(${questionCount}条)</a><s></s></div>
                        </div>
                    </div>
                    <div class="cont">
                        <!-- comment-star -->
                        <div class="comment-star rating_results">
                            <dl class="score">
                                <dt>用户评分：</dt>
                                <dd><b id="avgStarScore">${aveRank}</b>星</dd>
                                <dd>共<s id="allScoreCount">${rankCount}</s>人参与</dd>
                            </dl>
                            <dl class="star-list">
                                <dd class="star5"><span class="star"></span><span class="star-line">
                                <s id="shareByFiveStar" style="width:${rankRate.shareByFiveStar*100}%;"></s>
                                </span><span id="commentCountByFiveStar">${rankRate.rankCountByFiveStar}</span>人</dd>
                                <dd class="star4"><span class="star"></span><span class="star-line">
                                <s id="shareByFourStar" style="width:${rankRate.shareByFourStar*100}%;"></s>
                                </span><span id="commentCountByFourStar">${rankRate.rankCountByFourStar}</span>人</dd>
                                <dd class="star3"><span class="star"></span><span class="star-line">
                                <s id="shareByThreeStar" style="width:${rankRate.shareByThreeStar*100}%;"></s>
                                </span><span id="commentCountByThreeStar">${rankRate.rankCountByThreeStar }</span>人</dd>
                                <dd class="star2"><span class="star"></span><span class="star-line">
                                <s id="shareByTwoStar" style="width:${rankRate.shareByTwoStar*100}%;"></s>
                                </span><span id="commentCountByTwoStar">${rankRate.rankCountByTwoStar}</span>人</dd>
                                <dd class="star1"><span class="star"></span><span class="star-line">
                                <s id="shareByOneStar" style="width:${rankRate.shareByOneStar*100}%;"></s>
                                </span><span id="commentCountByOneStar">${rankRate.rankCountByOneStar}</span>人</dd>
                            </dl>
                            <dl class="grade">
                                <dt>我来评分：</dt>
                                <dd class="star-big" id="do_score" param="1"><b class="star-1"></b><b class="star-2"></b><b class="star-3"></b><b class="star-4"></b><b class="star-5"></b></dd>
                                <dd><a class="comment-btn-1" href="/product/${productSale.id }/comment/_new" title="我来写评论" target="_blank"></a>
                                <span class="text">写点评<a href="http://www.winxuan.com/help/score.html" target="_blank">送积分 &gt;&gt;</a></span></dd>
                            </dl>
                        </div>
                        <!-- comment-star end -->

                    </div>
                </div>
                <!-- col-base-2-tab end -->

            </div>
            
            <div class="unit">

                <!-- col-base-2-tab -->
                <div class="col col-base-2 comment">
                    <div class="title">
                        <div class="tab">
                            <div class="tab-item tab-item-2 current">全部评论<s></s></div>
                            <div class="tab-item tab-item-2">好评<a class="link" href="/product/${productSale.id}/comment/list" target="_blank">(${fn:length(praiseCustomerComment)}条)</a><s></s></div>
                            <div class="tab-item tab-item-2">中评<a class="link" href="/product/${productSale.id}/comment/list" target="_blank">(${fn:length(middlerCustomerComment)}条)</a><s></s></div>
                            <div class="tab-item tab-item-2">差评<a class="link" href="/product/${productSale.id}/comment/list" target="_blank">(${fn:length(badCustomerComment)}条)</a><s></s></div>
                            <div class="tab-item tab-item-2">天猫评论</div>
                        </div>
                    </div>
                     <jsp:include page="comment/new_blend.jsp" /> 
               <!-- col-base-2-tab end -->
     		</div>
                    
					<jsp:include page="paperBook/product_blend.jsp" />	
        </div>
        <!-- mian-detail -->
 
    </div>
    <!-- main end -->
</div>
<jsp:include page="../snippets/v2/footer.jsp"></jsp:include>
    <script type="text/javascript"
        src="${serverPrefix}js/v2/product.js?${version}"></script>
    <script type="text/javascript"
        src="${serverPrefix}js/v2/visit.js?${version}"></script>
    <script type="text/javascript"
        src="http://v2.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
    <script type="text/javascript">
        var _ozurltail = "#${product.secondLevelCategoryId}";
        var _ozprm = "cid99=${productSale.bookStorageType}${product.category.id}&press=${product.manufacturer}&author=${product.author}";
    </script>
</body>
</html>
