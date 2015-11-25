<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
 				<!-- info-main -->
                  <div class="info-main">
                      <div class="name">
                          <h1>
						${productSale.sellName} 
					</h1>
					 <c:if test="${!empty productSale.subheading}">
                          		<h3>${ productSale.subheading}</h3>
                           </c:if>
                          <%-- <c:if test="${!empty productSale.EBook.productSeptember}">
                          <div class="ebook-tip">（本书有电子版本）<a class="ebook-view-btn" href="http://www.winxuan.com/product/${productSale.EBook.id}">查看电子书 <b>￥${productSale.EBook.effectivePrice}</b></a></div>
                          </c:if> --%>
                      </div>
                      <div class="attr">
                          <dl class="price-o">
                              <dt class="attr-title">定　　价 ：</dt>
                              <dd><b>￥${product.listPrice }</b></dd>
                          </dl>
                          <dl class="price-n">
                              <dt class="attr-title">文 轩 价 ：</dt>
                              <dd><b>￥${productSale.effectivePrice}</b>
                              <c:choose>
                           		<c:when test="${productSale.discount>=1||productSale.discount==0}"></c:when>
                           		<c:otherwise>
                           			<span style="font-size: 13px;color: #e4393d;font-family: Microsoft Yahei;">（<fmt:formatNumber value="${productSale.discount*10}" pattern="#0.0"/>折）</span>
                           		</c:otherwise>
	                          </c:choose>
                              <a class="link" bind="pricecutNotify" href="javascript:void(0);" data-price="${productSale.effectivePrice }" data-id="${productSale.id }">（降价通知）</a></dd>
                          </dl>
                          <dl class="express">
                              <dt class="attr-title">配 送 至 ：</dt>
                              <dd class="cf"><div class="store"><div class="store-input"><span>中国四川省成都市</span><b></b></div>

                                  <!-- store-pop -->
                                  <div class="store-pop">
                                      <div class="store-pop-main">
                                          <div class="col-cascade-tab">
                                              <div class="title">
                                                  <div class="tab tab-1 cf">
                                                      <div class="tab-item current">中国<i></i></div>
                                                      <div class="tab-item">四川省<i></i></div>
                                                      <div class="tab-item">成都市<i></i></div>
                                                      <div class="tab-item">请选择<i></i></div>
                                                  </div>
                                              </div>
                                              <div class="cont">
                                                  <div class="col-text-tab">
                                                      <div class="title">
                                                          <div id="area-list-nav" class="tab tab-2 cf">
                                                          </div>
                                                      </div>
                                                      <div class="cont">
                                                          <ul id="area-list" class="list-text-4 cf">
                                                          </ul>
                                                      </div>
                                                  </div>
                                              </div>
                                          </div>
                                          <a class="close" href="#"></a>
                                      </div>
                                  </div>
                                  <!-- store-pop end -->


                              </div>
                              <c:choose>
		                    <c:when test="${productSale.supplyType.id != 13102 && productSale.supplyType.id !=13103}">
		                   		<c:choose>
		                   			<c:when test="${productSale.canSale}">
		                   			 	<c:choose>
		                   			 		<c:when test="${productSale.avalibleQuantity<=5}">
		                   			 			<span class="status">仅剩${productSale.avalibleQuantity}件</span>
		                   			 		</c:when>
		                   			 		<c:otherwise>
		                   			 			<span class="status">现在有货</span>
		                   			 		</c:otherwise>
		                   			 	</c:choose>	            				
		                   			</c:when>
		                   			<c:otherwise>
		                   				<span class="status">暂时缺货</span>
		                   			</c:otherwise>
		                   		</c:choose>
		                	</c:when>
		                	<c:otherwise>
		                		<c:if test="${productSale.supplyType.id == 13102}">
		               				<c:choose>
		               					<c:when test="${productSale.preSaleCanBuy }">
		               						<span class="status">预售商品</span><span class="express-time"><c:if test="${!empty productSale.booking.presellDescription}">（预计发货时间${productSale.booking.presellDescription}）</c:if></span>
		               					</c:when>
		               					<c:otherwise>
		               						<span class="status">暂时缺货</span>
		               					</c:otherwise>
		               				</c:choose>
		                		</c:if>
		                		<c:if test="${productSale.supplyType.id == 13103}">
		               				<span class="red fb">订购商品</span>
		                		</c:if>
		                	</c:otherwise>
		                </c:choose>	
                              </dd>
                              <cache:fragmentPageCache idleSeconds="86400" key="PAGE_PRODUCT_CATEGORY_WEEKANDNEWS_715100">
								<c:import url="/fragment/715100">
									<c:param value="${cacheKey}" name="cacheKey" />
								</c:import>
							  </cache:fragmentPageCache>
                          </dl>
                              <c:if test="${!empty product.authorUrl}">
	                          	  <dl class="author">
	                              	  <dt class="attr-title">作　　者 ：</dt>
		                              <dd>
		                				 ${product.authorUrl}
					                  </dd>
			                      </dl>
                    		  </c:if>
                          <dl class="class">
                              <c:forEach var="category" items="${product.categories}" varStatus="status">
	                      <c:if test="${status.index==0}">
                              	<dt class="attr-title">所属分类 ：</dt>
		                  </c:if>
		                  <dd>${category.allCategoryName}</dd>
	                     </c:forEach>
                          </dl>
	                        <c:if test="${not empty promotions}">
	                          <dl class="sale">
	                              	  <dt class="attr-title">促销活动 ：</dt>
	                              	  <dd><span class="status">
										${promotions[0].title}
									  </span>
									  	<c:if test="${not empty promotions[0].advertUrl}">
									 	  <a class="link" href="${promotions[0].advertUrl}">详情 &gt;&gt;</a>
									 	</c:if>
									  </dd>
	                          </dl>
							</c:if>
                      </div>
                      <div class="purchase">
                          <dl class="buy-num">
                              <dt class="attr-title">购买数量 ：</dt>
                              <dd><span class="buy-num-active"><a href="javascript:void(0);" class="reduce-btn" bind="reduce" title="减少">-</a><input type="text" class="buy-num-text" value="1"><a href="javascript:void(0);" class="add-btn" bind="increase" title="增加">+</a></span> 件
                              <span id="totalSale" class="bought-num">此商品已售出<b class="red fb">0</b>本</span></dd>
                          </dl>
                          <dl class="buy-active">
                              <dd>
                              <c:choose>
                                 <c:when test="${productSale.supplyType.id == 13102}">  
                                  <c:choose>
                      			<c:when test="${productSale.preSaleCanBuy}">
                                <a class="buy-btn-1 active-pre"  href="http://www.winxuan.com/shoppingcart/modify?p=${productSale.id}" id="addtocart" bind="addToCart" data-id="${productSale.id}" data-ref="input.buy-num-text" target="_blank" title="加入购物车"></a>
                                </c:when>
                                <c:otherwise>
                               <a class="buy-btn-1 active-out" href="javascript:void(0);"  bind="arrivalNotify"  data-id="${productSale.id}"  target="_blank" title="加入购物车"></a>
                                </c:otherwise>
                                </c:choose>
                                 </c:when>
                              <c:otherwise>
								<c:choose>
									<c:when test="${productSale.canSale}">
									 <a class="buy-btn-1 active-normal"  href="http://www.winxuan.com/shoppingcart/modify?p=${productSale.id}" id="addtocart" bind="addToCart" data-id="${productSale.id}" data-ref="input.buy-num-text" target="_blank" title="加入购物车"></a>
									</c:when>
									<c:otherwise>
									 <a class="buy-btn-1 active-out" href="javascript:void(0);"  bind="arrivalNotify"  data-id="${productSale.id}"  target="_blank" title="加入购物车"></a>
									</c:otherwise>
                                </c:choose>
                                </c:otherwise>
                                </c:choose>
                                <c:if test="${!empty productSale.EBook.productSeptember}">
                              <a class="buy-btn-2" href="http://www.winxuan.com/product/${productSale.EBook.id}" title="购买电子书">购买电子书 <b>￥${productSale.EBook.effectivePrice}</b></a>
                              </c:if>
                              </dd>
                          </dl>
                          <dl class="buy-service">
                              <dt class="attr-title">服　　务 ：</dt>
                              <dd><span>由"${productSale.shop.shopName}"直接销售和发货，并提供售后服务</span></dd>
                              <dd><span class="character"><a href="http://www.winxuan.com/help/shopping_insurance.html" title="正品低价" target="_blank">正品低价</a>|<a href="http://www.winxuan.com/help/delivery_info_dynamic_query.html" title="闪电发货" target="_blank">闪电发货</a>|<a href="http://www.winxuan.com/help/payment_on_arrival.html" title="货到付款" target="_blank">货到付款</a>|<a href="http://www.winxuan.com/help/return_flow.html" title="高效退换货" target="_blank">高效退换货</a></span></dd>
                          </dl>
                      </div>
                  </div>
                  <!-- info-mian end -->        
